package lispa.schedulers.dao.sgr.siss.current;

import java.sql.Connection; 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List; 
import lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentWorkitem;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException; 
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import com.mysema.query.Tuple; 
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery; 
import com.mysema.query.sql.SQLTemplates; 
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SissCurrentWorkitemDAO {

	private static SissCurrentWorkitem sissCurrentWorkitem = SissCurrentWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.current.SissCurrentWorkitem stgCurrentWorkitems = lispa.schedulers.queryimplementation.staging.sgr.siss.current.SissCurrentWorkitem.workitem; 
 
	public static void fillSissCurrentWorkitems() throws SQLException,DAOException { 
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection pgConnection = null;

		List<Tuple> historyworkitems = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			

			pgConnection = cm.getConnectionSISSCurrent();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(true);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery queryHistory = null;

			if (!ConnectionManager.getInstance().isAlive(pgConnection)) {
				if (cm != null)
					cm.closeConnection(pgConnection);
				pgConnection = cm.getConnectionSISSCurrent();
			}

			queryHistory = new SQLQuery(pgConnection, dialect);
			historyworkitems = queryHistory.from(sissCurrentWorkitem)
					.where(sissCurrentWorkitem.cId.isNotNull()).list(
							sissCurrentWorkitem.all());

			

			SQLInsertClause insert = new SQLInsertClause(OracleConnection,
					dialect, stgCurrentWorkitems);

			int batchSizeCounter = 0;

			for (Tuple hist : historyworkitems) {

				
				batchSizeCounter++;
				insert.columns(stgCurrentWorkitems.fkModule,
						stgCurrentWorkitems.cPriority, stgCurrentWorkitems.cResolution,
						stgCurrentWorkitems.cCreated, stgCurrentWorkitems.cOutlinenumber,
						stgCurrentWorkitems.fkProject, stgCurrentWorkitems.cDeleted,
						stgCurrentWorkitems.cPlannedend, stgCurrentWorkitems.cUpdated,
						stgCurrentWorkitems.fkAuthor, stgCurrentWorkitems.cUri,
						stgCurrentWorkitems.fkUriModule, stgCurrentWorkitems.cTimespent,
						stgCurrentWorkitems.cStatus, stgCurrentWorkitems.cSeverity,
						stgCurrentWorkitems.cResolvedon, stgCurrentWorkitems.fkUriProject,
						stgCurrentWorkitems.cTitle, stgCurrentWorkitems.cId,
						stgCurrentWorkitems.cRev, stgCurrentWorkitems.cPlannedstart,
						stgCurrentWorkitems.fkUriAuthor, stgCurrentWorkitems.cDuedate,
						stgCurrentWorkitems.cRemainingestimate, stgCurrentWorkitems.cType,
						stgCurrentWorkitems.cPk, stgCurrentWorkitems.cLocation,
						stgCurrentWorkitems.fkTimepoint, stgCurrentWorkitems.cInitialestimate,
						stgCurrentWorkitems.fkUriTimepoint,
						stgCurrentWorkitems.cPreviousstatus)
						.values(hist.get(sissCurrentWorkitem.fkModule),
								hist.get(sissCurrentWorkitem.cPriority),
								hist.get(sissCurrentWorkitem.cResolution),
								hist.get(sissCurrentWorkitem.cCreated),
								hist.get(sissCurrentWorkitem.cOutlinenumber),
								hist.get(sissCurrentWorkitem.fkProject),
								hist.get(sissCurrentWorkitem.cDeleted),
								hist.get(sissCurrentWorkitem.cPlannedend),
								hist.get(sissCurrentWorkitem.cUpdated),
								hist.get(sissCurrentWorkitem.fkAuthor), 
								hist.get(sissCurrentWorkitem.cUri),
								hist.get(sissCurrentWorkitem.fkUriModule),
								hist.get(sissCurrentWorkitem.cTimespent),
								hist.get(sissCurrentWorkitem.cStatus),
								hist.get(sissCurrentWorkitem.cSeverity),
								hist.get(sissCurrentWorkitem.cResolvedon),
								hist.get(sissCurrentWorkitem.fkUriProject),
								hist.get(sissCurrentWorkitem.cTitle),
								hist.get(sissCurrentWorkitem.cId),
								hist.get(sissCurrentWorkitem.cRev),
								hist.get(sissCurrentWorkitem.cPlannedstart),
								hist.get(sissCurrentWorkitem.fkUriAuthor),
								hist.get(sissCurrentWorkitem.cDuedate),
								hist.get(sissCurrentWorkitem.cRemainingestimate),
								hist.get(sissCurrentWorkitem.cType),
								hist.get(sissCurrentWorkitem.cPk),
								hist.get(sissCurrentWorkitem.cLocation),
								hist.get(sissCurrentWorkitem.fkTimepoint),
								hist.get(sissCurrentWorkitem.cInitialestimate),
								hist.get(sissCurrentWorkitem.fkUriTimepoint),
								hist.get(sissCurrentWorkitem.cPreviousstatus))
						.addBatch();
				if (!historyworkitems.isEmpty()
						&& batchSizeCounter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					batchSizeCounter = 0;
					insert = new SQLInsertClause(OracleConnection, dialect,
							stgCurrentWorkitems);
					OracleConnection.commit();
				}
			}

			if (!insert.isEmpty()) {
				insert.execute();
				OracleConnection.commit();
			}
			

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
				cm.closeConnection(pgConnection);
			}
		}
	} 
}