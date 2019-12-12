package lispa.schedulers.dao.sgr.sire.history;

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
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryWorkitem;
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

public class SireHistoryWorkitemDAO {

	private static Logger logger = Logger.getLogger(SireHistoryWorkitemDAO.class);
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryWorkitem stg_HistoryWorkItems = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryWorkitem.workitem;

	public static Map<EnumWorkitemType, Long> getMinRevisionByType()
			throws Exception {

		HashMap<EnumWorkitemType, Long> map = new HashMap<EnumWorkitemType, Long>();

		SQLTemplates dialect = new HSQLDBTemplates();

		ConnectionManager cm = null;
		Connection oracle = null;
		QSireHistoryWorkitem stgWorkItems = QSireHistoryWorkitem.sireHistoryWorkitem;
		
		try {
			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {

				List<Long> max = new ArrayList<Long>();

				cm = ConnectionManager.getInstance();
				oracle = cm.getConnectionOracle();

				SQLQuery query = new SQLQuery(oracle, dialect);

				max = query.from(stgWorkItems)
						.where(stgWorkItems.cType.eq(type.toString()))
						.list(stgWorkItems.cRev.max());

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
			if (cm != null) {
				cm.closeConnection(oracle);
			}
		}

		return map;

	}

	public static void fillSireHistoryWorkitem(
			Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision,
			EnumWorkitemType type) throws Exception {

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection SireHistoryConnection = null;

		List<Tuple> historyworkitems = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();

			SireHistoryConnection = cm.getConnectionSIREHistory();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(true);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery queryHistory = null;

			if (!ConnectionManager.getInstance().isAlive(SireHistoryConnection)) {
				if (cm != null)
					cm.closeConnection(SireHistoryConnection);
				SireHistoryConnection = cm.getConnectionSIREHistory();
			}

			queryHistory = new SQLQuery(SireHistoryConnection, dialect);
			historyworkitems = queryHistory
					.from(fonteHistoryWorkItems)
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev.gt(minRevisionByType
							.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.list(fonteHistoryWorkItems.all());

			logger.debug("TYPE: SIRE " + type.toString() + "  SIZE: "
					+ historyworkitems.size() + " minRevision: "+ minRevisionByType
					.get(type) + " - maxRevision: "+ maxRevision);

			SQLInsertClause insert = new SQLInsertClause(OracleConnection, dialect, stg_HistoryWorkItems);

			int batch_size_counter = 0;

			for (Tuple hist : historyworkitems) {
				batch_size_counter++;
				insert.columns(stg_HistoryWorkItems.fkModule, stg_HistoryWorkItems.cIsLocal,
						stg_HistoryWorkItems.cPriority, stg_HistoryWorkItems.cAutosuspect,
						stg_HistoryWorkItems.cResolution, stg_HistoryWorkItems.cCreated,
						stg_HistoryWorkItems.cOutlinenumber, stg_HistoryWorkItems.fkProject,
						stg_HistoryWorkItems.cDeleted, stg_HistoryWorkItems.cPlannedend,
						stg_HistoryWorkItems.cUpdated, stg_HistoryWorkItems.fkAuthor,
						stg_HistoryWorkItems.cUri, stg_HistoryWorkItems.fkUriModule,
						stg_HistoryWorkItems.cTimespent, stg_HistoryWorkItems.cStatus,
						stg_HistoryWorkItems.cSeverity, stg_HistoryWorkItems.cResolvedon,
						stg_HistoryWorkItems.fkUriProject, stg_HistoryWorkItems.cTitle,
						stg_HistoryWorkItems.cId, stg_HistoryWorkItems.cRev,
						stg_HistoryWorkItems.cPlannedstart, stg_HistoryWorkItems.fkUriAuthor,
						stg_HistoryWorkItems.cDuedate, stg_HistoryWorkItems.cRemainingestimate,
						stg_HistoryWorkItems.cType, stg_HistoryWorkItems.cPk,
						stg_HistoryWorkItems.cLocation, stg_HistoryWorkItems.fkTimepoint,
						stg_HistoryWorkItems.cInitialestimate,
						stg_HistoryWorkItems.fkUriTimepoint,
						stg_HistoryWorkItems.cPreviousstatus)
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
					batch_size_counter = 0;
					insert = new SQLInsertClause(OracleConnection, dialect,
							stg_HistoryWorkItems);
				}

			}

			if (!insert.isEmpty()) {
				insert.execute();
			}

			logger.debug("STOP TYPE: SIRE " + type.toString() + "  SIZE: "
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
			if (cm != null) {
				cm.closeConnection(OracleConnection);
			}
			if (cm != null) {
				cm.closeConnection(SireHistoryConnection);
			}
		}
	}
	
	public static void delete(EnumWorkitemType type) throws Exception {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stg_HistoryWorkItems)
				.where(stg_HistoryWorkItems.cType.eq(type.toString()))
				.execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(OracleConnection);
			}
		}
	}
}