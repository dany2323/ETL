package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.StringUtils;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SissHistoryProjectDAO {

	private static Logger logger = Logger.getLogger(SissHistoryProjectDAO.class);
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject fonteProjects = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject.project;
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject fonteProjects2 = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProject.project;
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRevision fonteRevisions = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRevision.revision;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryProject stg_Projects = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryProject.project;

	public static void fillSissHistoryProject(long minRevision, long maxRevision)
			throws Exception {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection connH2 = null;
		List<Tuple> projects = null;

		try {
			logger.debug("SissHistoryProjectDAO.fillSissHistoryProject - minRevision: " + minRevision + ", maxRevision: " + maxRevision);
			
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSISSHistory();
			projects = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(connH2, dialect);

			projects = query
					.from(fonteProjects, fonteRevisions)
					.where(fonteRevisions.cName.castToNum(Long.class).eq(
							fonteProjects.cRev))
					.where(fonteProjects.cRev.gt(minRevision))
					.where(fonteProjects.cRev.loe(maxRevision))
					.where(fonteProjects.cLocation.notLike("default:/GRACO%"))
					.where(fonteProjects.cId.notIn(new SQLSubQuery()
							.from(fonteProjects2)
							.where(fonteProjects2.cName.like("%{READONLY}%"))
							.list(fonteProjects2.cId)))
					.list(fonteProjects.cTrackerprefix, fonteProjects.cIsLocal,
							fonteProjects.cPk, fonteProjects.fkUriLead,
							fonteProjects.cDeleted, fonteProjects.cFinish,
							fonteProjects.cUri, fonteProjects.cStart,
							fonteProjects.fkUriProjectgroup,
							fonteProjects.cActive, fonteProjects.cLocation,
							fonteProjects.fkProjectgroup, fonteProjects.fkLead,
							fonteProjects.cLockworkrecordsdate,
							fonteProjects.cName, fonteProjects.cId,
							fonteProjects.cRev, fonteProjects.cDescription,
							fonteRevisions.cName, fonteRevisions.cCreated);
			
			logger.debug("SissHistoryProjectDAO.fillSissHistoryProject - projects.size: " + (projects==null?"NULL":projects.size()));
			
			for (Tuple row : projects) {
				new SQLInsertClause(connOracle, dialect, stg_Projects)
				.columns(stg_Projects.cTrackerprefix,
						stg_Projects.cIsLocal, stg_Projects.cPk,
						stg_Projects.fkUriLead, stg_Projects.cDeleted,
						stg_Projects.cFinish, stg_Projects.cUri,
						stg_Projects.cStart,
						stg_Projects.fkUriProjectgroup,
						stg_Projects.cActive, stg_Projects.cLocation,
						stg_Projects.fkProjectgroup, stg_Projects.fkLead,
						stg_Projects.cLockworkrecordsdate,
						stg_Projects.cName, stg_Projects.cId,
						stg_Projects.cDescription)
				.values(row.get(fonteProjects.cTrackerprefix),
								row.get(fonteProjects.cIsLocal),
								row.get(fonteProjects.cPk),
								StringUtils.getMaskedValue(row.get(fonteProjects.fkUriLead)),
								row.get(fonteProjects.cDeleted),
								row.get(fonteProjects.cFinish),
								row.get(fonteProjects.cUri),
								row.get(fonteProjects.cStart),
								row.get(fonteProjects.fkUriProjectgroup),
								row.get(fonteProjects.cActive),
								row.get(fonteProjects.cLocation),
								row.get(fonteProjects.fkProjectgroup),
								StringUtils.getMaskedValue(row.get(fonteProjects.fkLead)),
								row.get(fonteProjects.cLockworkrecordsdate),
								row.get(fonteProjects.cName),
								row.get(fonteProjects.cId),
								row.get(fonteProjects.cRev),
								row.get(fonteProjects.cDescription)).execute();
			}

			connOracle.commit();
			ConnectionManager.getInstance().dismiss();
		} catch (Exception e) {
			Throwable cause = e;
			while (cause.getCause() != null)
				cause = cause.getCause();
			String message = cause.getMessage();
			if (StringUtils.findRegex(message, DmalmRegex.REGEXDEADLOCK) || StringUtils.findRegex(message, DmalmRegex.REGEXLOCKTABLE)) {
				ErrorManager.getInstance().exceptionDeadlock(false, e);
			} else {
				ErrorManager.getInstance().exceptionOccurred(true, e);
			}
		} finally {
			cm.closeQuietly(connOracle);
			cm.closeQuietly(connH2);
		}
	}

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			max = query.from(stg_Projects).list(stg_Projects.cRev.max());

			if (max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			cm.closeQuietly(oracle);
		}

		return max.get(0).longValue();
	}
	
	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stg_Projects)
				.execute();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			cm.closeQuietly(OracleConnection);
		}
	}
}