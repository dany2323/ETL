package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryWorkitem;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SissHistoryWorkitemDAO {

	private static Logger logger = Logger.getLogger(SissHistoryWorkitemDAO.class);
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryWorkitem stg_WorkItems = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryWorkitem.workitem;
	
	public static Map<EnumWorkitemType, Long> getMinRevisionByType()
			throws Exception {

		HashMap<EnumWorkitemType, Long> map = new HashMap<EnumWorkitemType, Long>();

		SQLTemplates dialect = new HSQLDBTemplates();

		ConnectionManager cm = null;
		Connection oracle = null;
		QSissHistoryWorkitem stg_WorkItems = QSissHistoryWorkitem.sissHistoryWorkitem;
		try {
			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {

				List<Long> max = new ArrayList<Long>();

				cm = ConnectionManager.getInstance();
				oracle = cm.getConnectionOracle();

				SQLQuery query = new SQLQuery(oracle, dialect);

				max = query.from(stg_WorkItems)
						.where(stg_WorkItems.cType.eq(type.toString()))
						.list(stg_WorkItems.cRev.max());

				if (max == null || max.size() == 0 || max.get(0) == null) {
					map.put(type, 0L);
				} else {
					map.put(type, max.get(0).longValue());
				}

			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			cm.closeConnection(oracle);
		}

		return map;

	}

	public static void fillSissHistoryWorkitem(
			Map<EnumWorkitemType, Long> minRevisionsByType, long maxRevision,
			EnumWorkitemType type) throws Exception {

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection SissHistoryConnection = null;

		List<Tuple> historyworkitems = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();

			SissHistoryConnection = cm.getConnectionSISSHistory();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(true);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery queryHistory = null;

			if (!ConnectionManager.getInstance().isAlive(SissHistoryConnection)) {
				if (cm != null)
					cm.closeConnection(SissHistoryConnection);
				SissHistoryConnection = cm.getConnectionSISSHistory();
			}

			queryHistory = new SQLQuery(SissHistoryConnection, dialect);

			historyworkitems = queryHistory
					.from(fonteHistoryWorkItems)
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev.gt(minRevisionsByType
							.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.list(fonteHistoryWorkItems.all());

			logger.debug("TYPE: SISS " + type.toString() + "  SIZE: "
					+ historyworkitems.size());

			SQLInsertClause insert = new SQLInsertClause(OracleConnection,
					dialect, stg_WorkItems);

			int batch_size_counter = 0;

			for (Tuple hist : historyworkitems) {batch_size_counter++;
				insert.columns(stg_WorkItems.fkModule, stg_WorkItems.cIsLocal,
						stg_WorkItems.cPriority, stg_WorkItems.cAutosuspect,
						stg_WorkItems.cResolution, stg_WorkItems.cCreated,
						stg_WorkItems.cOutlinenumber, stg_WorkItems.fkProject,
						stg_WorkItems.cDeleted, stg_WorkItems.cPlannedend,
						stg_WorkItems.cUpdated, stg_WorkItems.fkAuthor,
						stg_WorkItems.cUri, stg_WorkItems.fkUriModule,
						stg_WorkItems.cTimespent, stg_WorkItems.cStatus,
						stg_WorkItems.cSeverity, stg_WorkItems.cResolvedon,
						stg_WorkItems.fkUriProject, stg_WorkItems.cTitle,
						stg_WorkItems.cId, stg_WorkItems.cRev,
						stg_WorkItems.cPlannedstart, stg_WorkItems.fkUriAuthor,
						stg_WorkItems.cDuedate, stg_WorkItems.cRemainingestimate,
						stg_WorkItems.cType, stg_WorkItems.cPk,
						stg_WorkItems.cLocation, stg_WorkItems.fkTimepoint,
						stg_WorkItems.cInitialestimate,
						stg_WorkItems.fkUriTimepoint,
						stg_WorkItems.cPreviousstatus)
						.values(hist.get(fonteHistoryWorkItems.fkModule),
								hist.get(fonteHistoryWorkItems.cIsLocal),
								hist.get(fonteHistoryWorkItems.cPriority),
								hist.get(fonteHistoryWorkItems.cAutosuspect),
								hist.get(fonteHistoryWorkItems.cResolution),
								hist.get(fonteHistoryWorkItems.cCreated),
								hist.get(fonteHistoryWorkItems.cOutlinenumber),
								hist.get(fonteHistoryWorkItems.fkProject),
								hist.get(fonteHistoryWorkItems.cDeleted),
								hist.get(fonteHistoryWorkItems.cPlannedend),
								hist.get(fonteHistoryWorkItems.cUpdated),
								StringUtils.getMaskedValue(hist.get(fonteHistoryWorkItems.fkAuthor)),
								hist.get(fonteHistoryWorkItems.cUri),
								hist.get(fonteHistoryWorkItems.fkUriModule),
								hist.get(fonteHistoryWorkItems.cTimespent),
								hist.get(fonteHistoryWorkItems.cStatus),
								hist.get(fonteHistoryWorkItems.cSeverity),
								hist.get(fonteHistoryWorkItems.cResolvedon),
								hist.get(fonteHistoryWorkItems.fkUriProject),
								hist.get(fonteHistoryWorkItems.cTitle)!=null?hist.get(fonteHistoryWorkItems.cTitle).substring(0, Math.min(hist.get(fonteHistoryWorkItems.cTitle).length(), 999)):null,
								hist.get(fonteHistoryWorkItems.cId),
								hist.get(fonteHistoryWorkItems.cRev),
								hist.get(fonteHistoryWorkItems.cPlannedstart),
								StringUtils.getMaskedValue(hist.get(fonteHistoryWorkItems.fkUriAuthor)),
								hist.get(fonteHistoryWorkItems.cDuedate),
								hist.get(fonteHistoryWorkItems.cRemainingestimate),
								hist.get(fonteHistoryWorkItems.cType),
								hist.get(fonteHistoryWorkItems.cPk),
								hist.get(fonteHistoryWorkItems.cLocation),
								hist.get(fonteHistoryWorkItems.fkTimepoint),
								hist.get(fonteHistoryWorkItems.cInitialestimate),
								hist.get(fonteHistoryWorkItems.fkUriTimepoint),
								hist.get(fonteHistoryWorkItems.cPreviousstatus)
						).addBatch();
				if (!historyworkitems.isEmpty()
						&& batch_size_counter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					insert = new SQLInsertClause(OracleConnection, dialect,
							stg_WorkItems);
					batch_size_counter = 0;
				}
			}

			if (!insert.isEmpty()) {
				insert.execute();
			}

			logger.debug("STOP TYPE: SISS " + type.toString() + "  SIZE: "
					+ historyworkitems.size());

			ErrorManager.getInstance().resetDeadlock();
		} catch (Exception e) {
			Throwable cause = e;
			while (cause.getCause() != null)
				cause = cause.getCause();
			String message = cause.getMessage();
			if (StringUtils.findRegex(message, DmalmRegex.REGEXDEADLOCK)) {
				ErrorManager.getInstance().exceptionDeadlock(false, e);
			} else {
				ErrorManager.getInstance().exceptionOccurred(true, e);
			}
		} finally {
			cm.closeConnection(OracleConnection);
			cm.closeConnection(SissHistoryConnection);
		}
	}
	
	public static void delete(EnumWorkitemType type) throws Exception {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stg_WorkItems)
				.where(stg_WorkItems.cType.eq(type.toString()))
				.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			cm.closeConnection(OracleConnection);
		}
	}
}