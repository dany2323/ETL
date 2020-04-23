package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryCfWorkitem;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryCfWorkitem;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class SissHistoryCfWorkitemDAO {
	private static Logger logger = Logger
			.getLogger(SissHistoryCfWorkitemDAO.class);

	private static SissHistoryCfWorkitem fonteCFWorkItems = SissHistoryCfWorkitem.cfWorkitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryCfWorkitem stgCFWorkItems = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryCfWorkitem.cfWorkitem;


	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;

	public static void fillSissHistoryCfWorkitemByWorkitemType(long minRevision,
			long maxRevision, EnumWorkitemType w_type, String CFName)
			throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> cfWorkitem = null;
		String customFieldName = null;
		boolean scaricaCF = false;
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
			pgConnection = cm.getConnectionSISSHistory();

			connOracle.setAutoCommit(true);

			SQLQuery query = new SQLQuery(pgConnection, dialect);

			logger.info("Leggo il custom field '" + CFName
					+ "' dai Work Item di tipo " + w_type.toString());

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
											.GetPolarionSchemaSissHistory()
									+ ".workitem where workitem.c_pk = fk_workitem) as fk_workitem"),
							fonteCFWorkItems.cLongValue,
							fonteCFWorkItems.cDurationtimeValue,
							fonteCFWorkItems.cCurrencyValue);

			logger.debug("CF_NAME: " + w_type.toString() + " " + CFName
					+ "  SIZE: " + cfWorkitem.size());

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgCFWorkItems);
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			int count_batch = 0;

			for (Tuple row : cfWorkitem) {

				Object[] vals = row.toArray();
				String fkUriWorkitem = vals[6] != null
						? (queryConnOracle(connOracle, dialect)
								.from(stgSubterra)
								.where(stgSubterra.cId
										.eq(Long.valueOf(vals[6].toString())))
								.where(stgSubterra.cRepo.eq(
										lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS))
								.count() > 0
										? queryConnOracle(connOracle, dialect)
												.from(stgSubterra)
												.where(stgSubterra.cId
														.eq(Long.valueOf(vals[6]
																.toString())))
												.where(stgSubterra.cRepo.eq(
														lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS))
												.list(stgSubterra.cPk).get(0)
										: "")
						: "";
				String fkWorkitem = fkUriWorkitem + "%"
						+ (vals[7] != null ? vals[7].toString() : "");

				count_batch++;

				// Applico il cast a timespent solo se esistono dei valori data
				StringExpression dateOnlyValue = null;
				if (vals[0] != null) {
					dateOnlyValue = StringTemplate.create(
							"to_timestamp('" + vals[0] + "', 'YYYY-MM-DD')");
				}
				StringExpression dateValue = null;
				if (vals[3] != null) {
					dateValue = StringTemplate.create("to_timestamp('" + vals[3]
							+ "', 'YYYY-MM-DD HH24:MI:SS.FF')");
				}

				insert.columns(stgCFWorkItems.cDateonlyValue,
						stgCFWorkItems.cFloatValue, stgCFWorkItems.cStringValue,
						stgCFWorkItems.cDateValue, stgCFWorkItems.cBooleanValue,
						stgCFWorkItems.cName, stgCFWorkItems.fkUriWorkitem,
						stgCFWorkItems.fkWorkitem, stgCFWorkItems.cLongValue,
						stgCFWorkItems.cDurationtimeValue,
						stgCFWorkItems.cCurrencyValue
//						stgCFWorkItems.dataCaricamento,
//						stgCFWorkItems.dmalmCfWorkitemPk
						)
						.values(dateOnlyValue, vals[1], vals[2], dateValue,
								vals[4], vals[5], fkUriWorkitem, fkWorkitem,
								vals[8], vals[9], vals[10]

								
//								dataEsecuzione,
//								StringTemplate.create(
//										"HISTORY_CF_WORKITEM_SEQ.nextval")
								)
						.addBatch();

				if (!insert.isEmpty()
						&& count_batch % DmAlmConstants.BATCH_SIZE == 0) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect,
							stgCFWorkItems);
				}

			}

			if (!insert.isEmpty()) {
				insert.execute();
			}

			if (cm != null) {
				cm.closeConnection(pgConnection);
			}
			if (cm != null) {
				cm.closeConnection(connOracle);
			}

			logger.info("Custom field '" + CFName + "' dei Work Item di tipo "
					+ w_type.toString() + " importati con successo.");

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