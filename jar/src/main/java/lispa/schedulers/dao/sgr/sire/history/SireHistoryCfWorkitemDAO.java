package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.expr.StringExpression;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryCfWorkitem;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class SireHistoryCfWorkitemDAO {
	private static Logger logger = Logger
			.getLogger(SireHistoryCfWorkitemDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryCfWorkitem fonteCFWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryCfWorkitem.cfWorkitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryCfWorkitem stgCFWorkItems = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryCfWorkitem.sireHistoryCfWorkitem;

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;

	/**
	 * Importo tutti i CF collegati a Workitem del tipo w_type e con C_NAME
	 * uguale a CFName
	 */
	public static void fillSireHistoryCfWorkitemByWorkitemType(long minRevision,
			long maxRevision, EnumWorkitemType w_type, String CFName)
			throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> cfWorkitem = null;
		String customFieldName = null;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;

		try {
			cm = ConnectionManager.getInstance();

			cfWorkitem = new ArrayList<Tuple>();

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();

			connOracle.setAutoCommit(true);

			SQLQuery query = new SQLQuery(pgConnection, dialect);

			cfWorkitem = query.from(fonteHistoryWorkItems)
					.join(fonteCFWorkItems)
					.on(fonteCFWorkItems.fkWorkitem
							.eq(fonteHistoryWorkItems.cPk))
					.where(fonteHistoryWorkItems.cType.eq(w_type.toString()))
					.where(fonteHistoryWorkItems.cRev.gt(minRevision))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.where(fonteCFWorkItems.cName.eq(CFName)).list(
							// fonteCFWorkItems.all()

							fonteCFWorkItems.cDateonlyValue,
							fonteCFWorkItems.cFloatValue,
							fonteCFWorkItems.cStringValue,
							fonteCFWorkItems.cDateValue,
							fonteCFWorkItems.cBooleanValue,
							fonteCFWorkItems.cName,
							fonteCFWorkItems.fkUriWorkitem,
							StringTemplate.create("(select c_rev from "
									+ lispa.schedulers.manager.DmAlmConstants
											.GetPolarionSchemaSireHistory()
									+ ".workitem where workitem.c_pk = fk_workitem) as fk_workitem"),
							fonteCFWorkItems.cLongValue,
							fonteCFWorkItems.cDurationtimeValue,
							fonteCFWorkItems.cCurrencyValue);

			logger.debug("CF_NAME: " + w_type.toString() + " " + CFName
					+ "  SIZE: " + cfWorkitem.size());

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgCFWorkItems);
			int count_batch = 0;

			for (Tuple row : cfWorkitem) {

				count_batch++;
				Object[] val = row.toArray();
				String fkUriWorkitem = val[6] != null
						? (queryConnOracle(connOracle, dialect)
								.from(stgSubterra)
								.where(stgSubterra.cId
										.eq(Long.valueOf(val[6].toString())))
								.where(stgSubterra.cRepo.eq(
										lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
								.count() > 0
										? queryConnOracle(connOracle, dialect)
												.from(stgSubterra)
												.where(stgSubterra.cId
														.eq(Long.valueOf(val[6]
																.toString())))
												.where(stgSubterra.cRepo.eq(
														lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE))
												.list(stgSubterra.cPk).get(0)
										: "")
						: "";
				String fkWorkitem = fkUriWorkitem + "%"
						+ (val[7] != null ? val[7].toString() : "");

				// Applico il cast a timespent solo se esistono dei valori data
				StringExpression dateOnlyValue = null;
				if (val[0] != null) {
					dateOnlyValue = StringTemplate.create(
							"to_timestamp('" + val[0] + "', 'YYYY-MM-DD')");
				}
				StringExpression dateValue = null;
				if (val[3] != null) {
					dateValue = StringTemplate.create("to_timestamp('" + val[3]
							+ "', 'YYYY-MM-DD HH24:MI:SS.FF')");
				}

				insert.columns(stgCFWorkItems.cDateonlyValue,
						stgCFWorkItems.cFloatValue, stgCFWorkItems.cStringValue,
						stgCFWorkItems.cDateValue, stgCFWorkItems.cBooleanValue,
						stgCFWorkItems.cName, stgCFWorkItems.fkUriWorkitem,
						stgCFWorkItems.fkWorkitem, stgCFWorkItems.cLongValue,
						stgCFWorkItems.cDurationtimeValue,
						stgCFWorkItems.cCurrencyValue,
						stgCFWorkItems.dataCaricamento,
						stgCFWorkItems.dmalmHistoryCfWorkItemPk)
						.values(dateOnlyValue, val[1], val[2], dateValue,
								val[4], val[5], fkUriWorkitem, fkWorkitem,
								val[8], val[9], val[10],
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								StringTemplate.create(
										"HISTORY_CF_WORKITEM_SEQ.nextval"))
						.addBatch();

				if (!insert.isEmpty()
						&& count_batch % DmAlmConstants.BATCH_SIZE == 0) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect,
							stgCFWorkItems);
					connOracle.commit();
				}

			}

			if (!insert.isEmpty()) {
				insert.execute();
				connOracle.commit();
			}

			if (cm != null) {
				cm.closeConnection(pgConnection);
			}
			if (cm != null) {
				cm.closeConnection(connOracle);
			}

			logger.info("Custom field '" + CFName + "' dei Work Item di tipo "
					+ w_type.toString() + " importati con successo.");

			// connOracle.commit();

			ErrorManager.getInstance().resetCFDeadlock();
		} catch (Exception e) {
			Throwable cause = e;
			while (cause.getCause() != null)
				cause = cause.getCause();
			String message = cause.getMessage();
			if (StringUtils.findRegex(message, DmalmRegex.REGEXDEADLOCK)) {
				ErrorManager.getInstance().exceptionCFDeadlock(false, e,
						customFieldName);
			} else {
				ErrorManager.getInstance().exceptionOccurred(true, e);
			}
		} finally {
			if (cm != null) {
				cm.closeConnection(pgConnection);
			}
			if (cm != null) {
				cm.closeConnection(connOracle);
			}
		}
	}

	private static SQLQuery queryConnOracle(Connection connOracle,
			PostgresTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}
}