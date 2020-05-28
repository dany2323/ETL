package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentRevision;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryProject;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryProject;
import lispa.schedulers.svn.ProjectTemplateINI;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Template_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.expr.StringExpression;
import com.mysema.query.types.template.StringTemplate;

public class SireHistoryProjectDAO {

	private static Logger logger = Logger
			.getLogger(SireHistoryProjectDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryProject fonteProjects = lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryProject.project;
	private static lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryProject fonteProjects2 = lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryProject.project;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryProject stgProjects = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryProject.project;

	public static void fillSireHistoryProject(long minRevision,
			long maxRevision) throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> projects = null;

		try {
			logger.debug(
					"SireHistoryProjectDAO.fillSireHistoryProject - minRevision: "
							+ minRevision + ", maxRevision: " + maxRevision);

			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
			projects = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(pgConnection, dialect);
			projects = query.from(fonteProjects)
					.where(fonteProjects.cLocation.notLike("default:/GRACO%"))
					.where(fonteProjects.cId.notIn(new SQLSubQuery()
							.from(fonteProjects2)
							.where(fonteProjects2.cName.like("%{READONLY}%"))
							.list(fonteProjects2.cId)))
					.list(fonteProjects.all());

			logger.debug(
					"SireHistoryProjectDAO.fillSireHistoryProject - projects.size: "
							+ (projects == null ? "NULL" : projects.size()));

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgProjects);
			int nRigheInserite = 0;
			for (Tuple row : projects) {
				// Applico il cast a timespent solo se esistono dei valori data

				insert.columns(stgProjects.cTrackerprefix, stgProjects.cPk,
						stgProjects.fkUriLead, stgProjects.cDeleted,
						stgProjects.cFinish, stgProjects.cUri,
						stgProjects.cStart, stgProjects.fkUriProjectgroup,
						stgProjects.cActive, stgProjects.cLocation,
						stgProjects.fkProjectgroup, stgProjects.fkLead,
						stgProjects.cLockworkrecordsdate, stgProjects.cName,
						stgProjects.cId, stgProjects.cRev,
						stgProjects.cDescription)
						.values(row.get(fonteProjects.cTrackerprefix),
								row.get(fonteProjects.cPk),
								row.get(fonteProjects.fkUriLead),
								row.get(fonteProjects.cDeleted),
								row.get(fonteProjects.cFinish),
								row.get(fonteProjects.cUri),
								row.get(fonteProjects.cStart),
								row.get(fonteProjects.fkUriProjectgroup),
								row.get(fonteProjects.cActive),
								row.get(fonteProjects.cLocation),
								row.get(fonteProjects.fkProjectgroup),
								row.get(fonteProjects.fkLead),
								row.get(fonteProjects.cLockworkrecordsdate),
								row.get(fonteProjects.cName),
								row.get(fonteProjects.cId),
								row.get(fonteProjects.cRev),
								row.get(fonteProjects.cDescription)

						).addBatch();
				nRigheInserite++;
				if (!insert.isEmpty()) {
					if (nRigheInserite
							% lispa.schedulers.constant.DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						connOracle.commit();
						insert = new SQLInsertClause(connOracle, dialect,
								stgProjects);
					}
				}

			}

			if (!insert.isEmpty()) {
				insert.execute();
				connOracle.commit();
			}
			ConnectionManager.getInstance().dismiss();

			logger.debug("fine projectdao.fill");
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		}

		finally {
			if (cm != null)
				cm.closeConnection(pgConnection);
			if (cm != null)
				cm.closeConnection(connOracle);
		}

	}

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			QSireHistoryProject stgProjects = QSireHistoryProject.sireHistoryProject;
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			max = query.from(stgProjects).list(stgProjects.cRev.max());

			if (max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(oracle);
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
			new SQLDeleteClause(OracleConnection, dialect, stgProjects)
					.execute();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(OracleConnection);
			}
		}
	}
	private static SQLQuery queryConnOracle(Connection connOracle,
			PostgresTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}

}