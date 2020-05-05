package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.dml.SQLInsertClause;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class SissHistoryCfWorkitemDAO {
	private static Logger logger = Logger
			.getLogger(SissHistoryCfWorkitemDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryCfWorkitem fonteCFWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryCfWorkitem.cfWorkitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryCfWorkitem stgCFWorkItems = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryCfWorkitem.cfWorkitem;


	private static lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryWorkitem.workitem;

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
					.where(fonteCFWorkItems.cName.eq(CFName))
					.list(fonteCFWorkItems.all());

			logger.debug("CF_NAME: " + w_type.toString() + " " + CFName
					+ "  SIZE: " + cfWorkitem.size());

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgCFWorkItems);
			int countBatch = 0;

			for (Tuple row : cfWorkitem) {

				insert.columns(stgCFWorkItems.cDateonlyValue,
						stgCFWorkItems.cFloatValue, stgCFWorkItems.cStringValue,
						stgCFWorkItems.cDateValue, stgCFWorkItems.cBooleanValue,
						stgCFWorkItems.cName, stgCFWorkItems.fkUriWorkitem,
						stgCFWorkItems.fkWorkitem, stgCFWorkItems.cLongValue,
						stgCFWorkItems.cDurationtimeValue,
						stgCFWorkItems.cCurrencyValue)
						.values(row.get(fonteCFWorkItems.cDateonlyValue),
								row.get(fonteCFWorkItems.cFloatValue),
								row.get(fonteCFWorkItems.cStringValue),
								row.get(fonteCFWorkItems.cDateValue),
								row.get(fonteCFWorkItems.cBooleanValue),
								row.get(fonteCFWorkItems.cName),
								row.get(fonteCFWorkItems.fkUriWorkitem),
								row.get(fonteCFWorkItems.fkWorkitem),
								row.get(fonteCFWorkItems.cLongValue),
								row.get(fonteCFWorkItems.cDurationtimeValue),
								row.get(fonteCFWorkItems.cCurrencyValue))
						.addBatch();

				if (!insert.isEmpty()
						&& countBatch % DmAlmConstants.BATCH_SIZE == 0) {
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