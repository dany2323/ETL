package lispa.schedulers.dao.sgr.sire.current;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireCurrentProject;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SireCurrentProjectDAO {

	private static Logger logger = Logger
			.getLogger(SireCurrentProjectDAO.class);

	public static long fillSireCurrentProject() throws Exception {

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		long nRigheInserite = 0;

		try {

			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(false);

			QSireCurrentProject stgProject = QSireCurrentProject.sireCurrentProject;
			lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentProject fonteProjects = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentProject.project;
			lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap fonteSireSubterraUriMap = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap.urimap;

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};
			SQLQuery query = new SQLQuery(OracleConnection, dialect);

			List<Tuple> project = query.from(fonteProjects)
					// .where(fonteProjects.cLocation.like("default:/Sviluppo/%"))
					.where((fonteProjects.cLocation.length()
							.subtract(fonteProjects.cLocation.toString()
									.replaceAll("/", "").length())).goe(4))
					.where(fonteProjects.cLocation.notLike("default:/GRACO%"))
					.list(new QTuple(
							fonteProjects.cTrackerprefix,
							StringTemplate.create("0"),
							StringTemplate.create("(SELECT a.c_pk FROM "
									+ fonteSireSubterraUriMap.getSchemaName()
									+ "."
									+ fonteSireSubterraUriMap.getTableName()
									+ " a WHERE a.c_id = " + fonteProjects.cUri
									+ " ) as c_pk"),
							StringTemplate.create("(SELECT b.c_pk FROM "
									+ fonteSireSubterraUriMap.getSchemaName()
									+ "."
									+ fonteSireSubterraUriMap.getTableName()
									+ " b WHERE b.c_id = "
									+ fonteProjects.fkUriLead
									+ " ) as fk_uri_lead"),
							StringTemplate.create(
									"CASE WHEN " + fonteProjects.cDeleted
											+ "= 'true' THEN 1 ELSE 0 END"),
							fonteProjects.cFinish,
							StringTemplate.create("(SELECT c.c_pk FROM "
									+ fonteSireSubterraUriMap.getSchemaName()
									+ "."
									+ fonteSireSubterraUriMap.getTableName()
									+ " c WHERE c.c_id = " + fonteProjects.cUri
									+ " ) as c_uri"),
							fonteProjects.cStart,
							StringTemplate.create("(SELECT d.c_pk FROM "
									+ fonteSireSubterraUriMap.getSchemaName()
									+ "."
									+ fonteSireSubterraUriMap.getTableName()
									+ " d WHERE d.c_id = "
									+ fonteProjects.fkUriProjectgroup
									+ " ) as FK_URI_PROJECTGROUP"),
							StringTemplate
									.create("NVL("+fonteProjects.cActive
											+ ",0)"),
							fonteProjects.cLocation,
							StringTemplate.create("(SELECT e.c_pk FROM "
									+ fonteSireSubterraUriMap.getSchemaName()
									+ "."
									+ fonteSireSubterraUriMap.getTableName()
									+ " e WHERE e.c_id = "
									+ fonteProjects.fkUriProjectgroup
									+ " ) as fk_projectgroup"),
							StringTemplate.create("(SELECT f.c_pk FROM "
									+ fonteSireSubterraUriMap.getSchemaName()
									+ "."
									+ fonteSireSubterraUriMap.getTableName()
									+ " f WHERE f.c_id = "
									+ fonteProjects.fkUriLead
									+ " ) as fk_lead"),
							StringTemplate.create("to_char("
									+ fonteProjects.cLockworkrecordsdate
									+ ", 'yyyy-MM-dd 00:00:00')"),
							fonteProjects.cName,
							fonteProjects.cId));

			SQLInsertClause insert = new SQLInsertClause(OracleConnection,
					dialect, stgProject);

			for (Tuple el : project) {

				insert.columns(
						stgProject.cTrackerprefix,
						stgProject.cIsLocal,
						stgProject.cPk,
						stgProject.fkUriLead,
						stgProject.cDeleted,
						stgProject.cFinish,
						stgProject.cUri, 
						stgProject.cStart,
						stgProject.fkUriProjectgroup, 
						stgProject.cActive,
						stgProject.cLocation, 
						stgProject.fkProjectgroup,
						stgProject.fkLead, 
						stgProject.cLockworkrecordsdate,
						stgProject.cName, 
						stgProject.cId,
						stgProject.dataCaricamento,
						stgProject.sireCurrentProjectPk)
						.values(el.get(fonteProjects.cTrackerprefix),
								el.get(fonteProjects.cIsLocal),
								el.get(fonteProjects.cPk),
								el.get(fonteProjects.fkUriLead),
								el.get(fonteProjects.cDeleted),
								el.get(fonteProjects.cFinish),
								el.get(fonteProjects.cUri),
								el.get(fonteProjects.cStart),
								el.get(fonteProjects.fkUriProjectgroup),
								el.get(fonteProjects.cActive),
								el.get(fonteProjects.cLocation),
								el.get(fonteProjects.fkProjectgroup),
								el.get(fonteProjects.fkLead),
								el.get(fonteProjects.cLockworkrecordsdate),
								el.get(fonteProjects.cName),
								el.get(fonteProjects.cId),
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								StringTemplate
										.create("CURRENT_PROJECT_SEQ.nextval"))
						.addBatch();

				nRigheInserite++;

				if (!insert.isEmpty()) {
					if (nRigheInserite % DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						OracleConnection.commit();
						insert = new SQLInsertClause(OracleConnection, dialect,
								stgProject);
					}
				}

			}
			if (!insert.isEmpty()) {

				insert.execute();
				OracleConnection.commit();
				insert = new SQLInsertClause(OracleConnection, dialect,
						stgProject);

			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			nRigheInserite = 0;
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(OracleConnection);
		}

		return nRigheInserite;
	}

	public static List<Tuple> getC_NameNull(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> project = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QSireCurrentProject stgProject = QSireCurrentProject.sireCurrentProject;

			project = query.from(stgProject)
					.where(stgProject.dataCaricamento.eq(dataEsecuzione))
					.where(stgProject.cName.isNull()).list(stgProject.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return project;
	}

	public static List<Tuple> getAllProjectSW(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> project = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QSireCurrentProject stgProject = QSireCurrentProject.sireCurrentProject;

			// select DMALM_SIRE_CURRENT_PROJECT.* from
			// DMALM_SIRE_CURRENT_PROJECT where ltrim(c_name) like 'SW-%'

			project =

					query.from(stgProject)
							.where(stgProject.dataCaricamento
									.eq(dataEsecuzione))
							.where(stgProject.cName.trim().like("SW-%"))
							.list(stgProject.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return project;
	}

	public static List<Tuple> getAllProjectC_Name(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> project = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QSireCurrentProject stgProject = QSireCurrentProject.sireCurrentProject;

			project =

					query.from(stgProject)
							.where(stgProject.dataCaricamento
									.eq(dataEsecuzione))
							.where(stgProject.cName.trim().like("SW-%"))
							.list(stgProject.cName, // il campo C_NAME
									StringTemplate.create("INSTR(C_NAME,'{')"), // la
																				// posizione
																				// del
																				// carattere
																				// {
									StringTemplate.create("INSTR(C_NAME,'}')"), // la
																				// posizione
																				// del
																				// carattere
																				// }
									StringTemplate
											.create("REPLACE(REPLACE(REGEXP_SUBSTR(C_NAME, '{[a-zA-Z0-9_.*!?-]+}'), '{',''),'}','') as name"), // il
																																				// contenuto
																																				// delle
																																				// parentesi
																																				// {
																																				// }
									StringTemplate.create(
											"SUBSTR(C_NAME, INSTR(C_NAME,'}')+1, LENGTH(C_NAME)-INSTR(C_NAME,'}') )")); // la
																														// descrizione,
																														// quello
																														// che
																														// viene
																														// dopo
																														// il
																														// caratter
																														// "}"

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return project;
	}

	public static void delete(Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireCurrentProject stgProject = QSireCurrentProject.sireCurrentProject;

			new SQLDeleteClause(connection, dialect, stgProject)
					.where(stgProject.dataCaricamento.lt(dataEsecuzione)
							.or(stgProject.dataCaricamento.gt(
									DateUtils.addDaysToTimestamp(DataEsecuzione
											.getInstance().getDataEsecuzione(),
											-1))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireCurrentProject stgProject = QSireCurrentProject.sireCurrentProject;

			new SQLDeleteClause(connection, dialect, stgProject)
					.where(stgProject.dataCaricamento.eq(date)).execute();

		} catch (Exception e) {

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void recoverSireCurrentProject() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireCurrentProject stgProject = QSireCurrentProject.sireCurrentProject;

			new SQLDeleteClause(connection, dialect, stgProject)
					.where(stgProject.dataCaricamento.eq(
							DataEsecuzione.getInstance().getDataEsecuzione()))
					.execute();

		} catch (Exception e) {

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

}