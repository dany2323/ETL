package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
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
import com.mysema.query.types.template.StringTemplate;

public class SireHistoryProjectDAO {

	private static Logger logger = Logger
			.getLogger(SireHistoryProjectDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryProject fonteProjects = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryProject.project;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryProject fonteProjects2 = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryProject.project;

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision fonteRevisions = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision.revision;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap fonteSireSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap.urimap;

	private static QSireHistoryProject stgProjects = QSireHistoryProject.sireHistoryProject;

	public static void fillSireHistoryProject(long minRevision, long maxRevision)
			throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> projects = null;

		try {
			logger.debug("SireHistoryProjectDAO.fillSireHistoryProject - minRevision: " + minRevision + ", maxRevision: " + maxRevision);

			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
			projects = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates()
			{ {
				setPrintSchema(true);
			}};

			SQLQuery query = new SQLQuery(pgConnection, dialect);
			
			projects = query
					.from(fonteProjects, fonteRevisions)
					.where(fonteRevisions.cName.castToNum(Long.class).eq(
							fonteProjects.cRev))
					.where(fonteProjects.cRev.gt(minRevision).and(fonteProjects.cRev.loe(maxRevision)))
					.where(fonteProjects.cLocation.notLike("default:/GRACO%"))
					.where(fonteProjects.cId.notIn(new SQLSubQuery()
							.from(fonteProjects2)
							.where(fonteProjects2.cName.like("%{READONLY}%"))
							.list(fonteProjects2.cId)))
					.list(
							fonteProjects.cTrackerprefix, 
							StringTemplate.create("0"),
							StringTemplate.create("(SELECT a.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSire() + " a WHERE a.c_id = " + fonteProjects.cUri + ") || '%' || project.c_rev as c_pk"),
							StringTemplate.create("(SELECT b.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSire() + " b WHERE b.c_id = " + fonteProjects.fkUriLead + ") as fk_uri_lead"),
							fonteProjects.cDeleted, 
							fonteProjects.cFinish,
							StringTemplate.create("(SELECT c.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSire() + " c WHERE c.c_id = " + fonteProjects.cUri + ") as c_uri"),
							fonteProjects.cStart,
							StringTemplate.create("(SELECT d.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSire() + " d WHERE d.c_id = " + fonteProjects.fkUriProjectgroup + ") as FK_URI_PROJECTGROUP"),
							fonteProjects.cActive, 
							fonteProjects.cLocation,
							StringTemplate.create("(SELECT e.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSire() + " e WHERE e.c_id = " + fonteProjects.fkUriProjectgroup + ") || '%' || project.c_rev as fk_projectgroup"),
							StringTemplate.create("(SELECT f.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSire() + " f WHERE f.c_id = " + fonteProjects.fkUriLead + ") || '%' || project.c_rev as fk_lead"),
							fonteProjects.cLockworkrecordsdate,
							fonteProjects.cName, 
							fonteProjects.cId,
							fonteProjects.cRev, 
//							fonteRevisions.cName, 
							fonteRevisions.cCreated,
							fonteProjects.cDescription
							);

			logger.debug("SireHistoryProjectDAO.fillSireHistoryProject - projects.size: " + (projects==null?"NULL":projects.size()));

			for (Tuple row : projects) {
				Object[] vals = row.toArray();
				
				new SQLInsertClause(connOracle, dialect, stgProjects)
						.columns(
								stgProjects.cTrackerprefix,
								stgProjects.cIsLocal, 
								stgProjects.cPk,
								stgProjects.fkUriLead, 
								stgProjects.cDeleted,
								stgProjects.cFinish,
								stgProjects.cUri,
								stgProjects.cStart,
								stgProjects.fkUriProjectgroup,
								stgProjects.cActive,
								stgProjects.cLocation,
								stgProjects.fkProjectgroup, 
								stgProjects.fkLead,
								stgProjects.cLockworkrecordsdate,
								stgProjects.cName, 
								stgProjects.cId,
								stgProjects.dataCaricamento,
								stgProjects.sireHistoryProjectPk,
								stgProjects.cRev,
								stgProjects.cCreated,
								stgProjects.cDescription
								)
						.values(
								vals[0],
								vals[1],
								vals[2],
								StringUtils.getMaskedValue((String)vals[3]),
								vals[4],
								vals[5],
								vals[6],
								vals[7],
								vals[8],
								vals[9],
								vals[10],
								vals[11],
								StringUtils.getMaskedValue((String)vals[12]),
								vals[13],
								vals[14],
								vals[15],
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								StringTemplate
										.create("HISTORY_PROJECT_SEQ.nextval"),
										vals[16],
										vals[17],
										vals[18]
								).execute();
			}

			connOracle.commit();
			ConnectionManager.getInstance().dismiss();
			updateProjectTemplate(minRevision, maxRevision);

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

	public static void updateProjectTemplate(long minRevision, long maxRevision) {

		ConnectionManager cm = null;
		Connection oracle = null;
		List<Tuple> projects = null;

		try {
			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			projects = new ArrayList<Tuple>();

			//todo
			projects = query
					.from(stgProjects)
					.distinct()
					.where(stgProjects.cRev.gt(minRevision).and(stgProjects.cRev.loe(maxRevision)))
					.list(stgProjects.cUri, stgProjects.cLocation,
							stgProjects.cRev);

			for (Tuple location : projects) {

				if (location != null
						&& location.get(stgProjects.cLocation) != null
						&& !location.get(stgProjects.cLocation).equals("")) {

					String loc = location.get(stgProjects.cLocation);
					long rev = location.get(stgProjects.cRev);

					logger.debug("ciclo project " + loc);

					setTemplate(loc, rev, DmAlmConstants.REPOSITORY_SIRE);

				}
			}
			setAllSireTemplate();
		} catch (Exception e) {

			logger.error(e.getMessage());
		} finally {
			try {
				if (cm != null)
					cm.closeConnection(oracle);
			} catch (DAOException e) {
			}
		}

	}

	private static void setTemplate(String location, long revision, String repo) {

		ConnectionManager cm = null;
		Connection oracle = null;

		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			logger.debug("get file ini");
			String template = ProjectTemplateINI.getTemplateIniFile(
					ProjectTemplateINI.getProjectSVNPath(location), revision,
					repo);

			new SQLUpdateClause(oracle, dialect, stgProjects)
					.set(stgProjects.cTemplate, template)
					.where(stgProjects.cLocation.eq(location)).execute();

			logger.debug("update eseguito");
		} catch (Exception e) {
			logger.error("IMPOSSIBILE SETTARE IL TEMPLATE");
			logger.error(e.getMessage());
		} finally {
			try {
				if (cm != null)
					cm.closeConnection(oracle);
			} catch (DAOException e) {
				logger.error(e.getMessage());
			}
		}
	}

	public static void setAllSireTemplate() {
		ConnectionManager cm = null;
		Connection oracle = null;
		List<Tuple> template = null;
		List<Tuple> stillNullTemplate = null;
		String tmplt;

		try {
			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);
			logger.debug("START: Query SIRE PROJECTs with Template NOT NULL");
			template = query.from(stgProjects).distinct()
					.where(stgProjects.cTemplate.isNotNull())
					.list(stgProjects.cTemplate, stgProjects.cId);
			logger.debug("START: SET SIRE PROJECTs with Template NULL");
			for (Tuple tupla : template) {
				new SQLUpdateClause(oracle, dialect, stgProjects)
						.where(stgProjects.cId.equalsIgnoreCase(tupla
								.get(stgProjects.cId)))
						.set(stgProjects.cTemplate,
								tupla.get(stgProjects.cTemplate)).execute();
			}
			
			SQLQuery query_1 = new SQLQuery(oracle, dialect);
			stillNullTemplate = query_1
					.from(stgProjects)
					.distinct()
					.where(stgProjects.cTemplate.isNull())
					.list(stgProjects.cId, stgProjects.cRev,
							stgProjects.cLocation);
			
			logger.debug("START SET SIRE PROJECTs WITH TEMPLATE STILL NULL ");
			
			for (Tuple t : stillNullTemplate)
				try {
					tmplt = ProjectTemplateINI.getLastRevisionTemplateIniFile(
							ProjectTemplateINI.getProjectSVNPath(t
									.get(stgProjects.cLocation)),
							DmAlmConstants.REPOSITORY_SIRE);
					new SQLUpdateClause(oracle, dialect, stgProjects)
							.where(stgProjects.cId.equalsIgnoreCase(t
									.get(stgProjects.cId)))
							.where(stgProjects.cTemplate.isNull())
							.set(stgProjects.cTemplate, tmplt).execute();
					logger.info("UPDATE DONE ON PROJECT_ID "
							+ t.get(stgProjects.cId));
				} catch (Exception e) {
					logger.error("IMPOSSIBILE SETTARE IL TEMPLATE: PATH NON ESISTENTE");
					logger.error(e.getMessage());
				}
			oracle.commit();
			logger.debug("STOP: SET SIRE PROJECTs with Template NULL");

		} catch (Exception e) {

			logger.error(e.getMessage());
		} finally {
			try {
				if (cm != null)
					cm.closeConnection(oracle);
			} catch (DAOException e) {

			}
		}

	}

	public static void recoverSireHistoryProject() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryProject stgProjects = QSireHistoryProject.sireHistoryProject;

			new SQLDeleteClause(connection, dialect, stgProjects).where(
					stgProjects.dataCaricamento.eq(DataEsecuzione.getInstance()
							.getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<Tuple> getProjectbyTemplate(Template_Type template)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> projects = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QSissHistoryProject stgProjectA = QSissHistoryProject.sissHistoryProject;
			QSissHistoryProject stgProjectB = QSissHistoryProject.sissHistoryProject;
			projects = query
					.from(stgProjectA)
					.where(stgProjectA.cTemplate.eq(template.toString()))
					.where(stgProjectA.cRev.in(new SQLSubQuery()
							.from(stgProjectB)
							.where(stgProjectB.cId.eq(stgProjectA.cId))
							.groupBy(stgProjectB.cId)
							.list(stgProjectB.cRev.max())))
					.list(stgProjectA.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return projects;
	}

	public static List<String> getProjectsC_Name(List<Tuple> projects)
			throws Exception {
		List<String> allc_name = new ArrayList<String>();
		try {

			String c_name = null;
			for (Tuple project : projects) {
				c_name = project.get(stgProjects.cName);
				allc_name.add(c_name);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {

		}

		return allc_name;
	}

	public static List<String> getProjectsC_Location(List<Tuple> projects) {
		List<String> allc_location = new ArrayList<String>();
		try {

			String c_location = null;
			for (Tuple project : projects) {
				c_location = project.get(stgProjects.cLocation);
				allc_location.add(c_location);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {

		}

		return allc_location;

	}

}