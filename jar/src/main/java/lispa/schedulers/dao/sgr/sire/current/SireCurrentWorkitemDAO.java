package lispa.schedulers.dao.sgr.sire.current;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentWorkitem;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;

import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SireCurrentWorkitemDAO {

	private static Logger logger = Logger
			.getLogger(SireCurrentWorkitemDAO.class);
	private static SireCurrentWorkitem sireCurrentWorkitem = SireCurrentWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.current.SireCurrentWorkitem stgCurrentWorkitems = lispa.schedulers.queryimplementation.staging.sgr.sire.current.SireCurrentWorkitem.workitem;

	public static void fillSireCurrentWorkitems()
			throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection pgConnection = null;

		List<Tuple> historyworkitems = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();

			pgConnection = cm.getConnectionSIREHistory();
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
				pgConnection = cm.getConnectionSIREHistory();
			}

			queryHistory = new SQLQuery(pgConnection, dialect);
			historyworkitems = queryHistory.from(sireCurrentWorkitem)
					.where(sireCurrentWorkitem.cId.isNotNull())
					.list(sireCurrentWorkitem.all());

			SQLInsertClause insert = new SQLInsertClause(OracleConnection,
					dialect, stgCurrentWorkitems);

			int batchSizeCounter = 0;

			for (Tuple hist : historyworkitems) {

				batchSizeCounter++;
				insert.columns(stgCurrentWorkitems.fkModule,
						stgCurrentWorkitems.cPriority,
						stgCurrentWorkitems.cResolution,
						stgCurrentWorkitems.cCreated,
						stgCurrentWorkitems.cOutlinenumber,
						stgCurrentWorkitems.fkProject,
						stgCurrentWorkitems.cDeleted,
						stgCurrentWorkitems.cPlannedend,
						stgCurrentWorkitems.cUpdated,
						stgCurrentWorkitems.fkAuthor, stgCurrentWorkitems.cUri,
						stgCurrentWorkitems.fkUriModule,
						stgCurrentWorkitems.cTimespent,
						stgCurrentWorkitems.cStatus,
						stgCurrentWorkitems.cSeverity,
						stgCurrentWorkitems.cResolvedon,
						stgCurrentWorkitems.fkUriProject,
						stgCurrentWorkitems.cTitle, stgCurrentWorkitems.cId,
						stgCurrentWorkitems.cRev,
						stgCurrentWorkitems.cPlannedstart,
						stgCurrentWorkitems.fkUriAuthor,
						stgCurrentWorkitems.cDuedate,
						stgCurrentWorkitems.cRemainingestimate,
						stgCurrentWorkitems.cType, stgCurrentWorkitems.cPk,
						stgCurrentWorkitems.cLocation,
						stgCurrentWorkitems.fkTimepoint,
						stgCurrentWorkitems.cInitialestimate,
						stgCurrentWorkitems.fkUriTimepoint,
						stgCurrentWorkitems.cPreviousstatus)
						.values(hist.get(sireCurrentWorkitem.fkModule),
								hist.get(sireCurrentWorkitem.cPriority),
								hist.get(sireCurrentWorkitem.cResolution),
								hist.get(sireCurrentWorkitem.cCreated),
								hist.get(sireCurrentWorkitem.cOutlinenumber),
								hist.get(sireCurrentWorkitem.fkProject),
								hist.get(sireCurrentWorkitem.cDeleted),
								hist.get(sireCurrentWorkitem.cPlannedend),
								hist.get(sireCurrentWorkitem.cUpdated),
								hist.get(sireCurrentWorkitem.fkAuthor),
								hist.get(sireCurrentWorkitem.cUri),
								hist.get(sireCurrentWorkitem.fkUriModule),
								hist.get(sireCurrentWorkitem.cTimespent),
								hist.get(sireCurrentWorkitem.cStatus),
								hist.get(sireCurrentWorkitem.cSeverity),
								hist.get(sireCurrentWorkitem.cResolvedon),
								hist.get(sireCurrentWorkitem.fkUriProject),
								hist.get(sireCurrentWorkitem.cTitle),
								hist.get(sireCurrentWorkitem.cId),
								hist.get(sireCurrentWorkitem.cRev),
								hist.get(sireCurrentWorkitem.cPlannedstart),
								hist.get(sireCurrentWorkitem.fkUriAuthor),
								hist.get(sireCurrentWorkitem.cDuedate),
								hist.get(
										sireCurrentWorkitem.cRemainingestimate),
								hist.get(sireCurrentWorkitem.cType),
								hist.get(sireCurrentWorkitem.cPk),
								hist.get(sireCurrentWorkitem.cLocation),
								hist.get(sireCurrentWorkitem.fkTimepoint),
								hist.get(sireCurrentWorkitem.cInitialestimate),
								hist.get(sireCurrentWorkitem.fkUriTimepoint),
								hist.get(sireCurrentWorkitem.cPreviousstatus))
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