package lispa.sgr.sire.current;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import junit.framework.TestCase;
import lispa.schedulers.bean.staging.sgr.ProjectCName;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireCurrentProject;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class TestSIRECurrentProject extends TestCase {
	private static Logger logger = Logger
			.getLogger(TestSIRECurrentProject.class);

	public void testStoricizzazione() throws Exception {
		ProjectSgrCmFacade.execute(DataEsecuzione.getInstance()
				.getDataEsecuzione());
	}

	public void testNomeProjectNull() throws Exception {
		// CheckSGRSIREProjectFacade.checkSIRECodiciIdentificativiObject(logger,
		// DataEsecuzione.getInstance().getDataEsecuzione());
	}

	public void testFillStaging() throws Exception {

		// C_TRACKERPREFIX VARCHAR2
		// C_IS_LOCAL NUMBER
		// C_PK VARCHAR2
		// FK_URI_LEAD VARCHAR2
		// C_DELETED NUMBER
		// C_FINISH DATE
		// C_URI VARCHAR2
		// C_START DATE
		// FK_URI_PROJECTGROUP VARCHAR2
		// C_ACTIVE NUMBER
		// C_LOCATION VARCHAR2
		// FK_PROJECTGROUP VARCHAR2
		// FK_LEAD VARCHAR2
		// C_LOCKWORKRECORDSDATE DATE
		// C_NAME VARCHAR2
		// C_ID VARCHAR2
		// C_REV NUMBER
		// DATA_CARICAMENTO TIMESTAMP(6)

		Connection OracleConnection = null;
		Connection H2Connection = null;

		try {

			ConnectionManager cm = ConnectionManager.getInstance();

			H2Connection = cm.getConnectionSIRECurrent();

			OracleConnection = cm.getConnectionOracle();
			OracleConnection.setAutoCommit(false);

			QSireCurrentProject stgProject = QSireCurrentProject.sireCurrentProject;
			lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentProject fonteProjects = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentProject.project;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(H2Connection, dialect);

			List<Tuple> cfworkitems = query.from(fonteProjects).list(
					new QTuple(StringTemplate.create("SUBSTRING("
							+ fonteProjects.cTrackerprefix + ",0,4000)"),
							StringTemplate.create("CASEWHEN ("
									+ fonteProjects.cIsLocal
									+ "= 'true', 1,0 )"), StringTemplate
									.create("SUBSTRING(" + fonteProjects.cPk
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING("
											+ fonteProjects.fkUriLead
											+ ",0,4000)"), StringTemplate
									.create("CASEWHEN ("
											+ fonteProjects.cDeleted
											+ "= 'true', 1,0 )"),
							StringTemplate.create("FORMATDATETIME("
									+ fonteProjects.cFinish
									+ ", {ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("
									+ fonteProjects.cUri + ",0,4000)"),
							StringTemplate.create("FORMATDATETIME("
									+ fonteProjects.cStart
									+ ", {ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("
									+ fonteProjects.fkUriProjectgroup
									+ ",0,4000)"), StringTemplate
									.create("CASEWHEN ("
											+ fonteProjects.cActive
											+ "= 'true', 1,0 )"),
							StringTemplate.create("SUBSTRING("
									+ fonteProjects.cLocation + ",0,4000)"),
							StringTemplate
									.create("SUBSTRING("
											+ fonteProjects.fkProjectgroup
											+ ",0,4000)"), StringTemplate
									.create("SUBSTRING(" + fonteProjects.fkLead
											+ ",0,4000)"),
							StringTemplate.create("FORMATDATETIME("
									+ fonteProjects.cLockworkrecordsdate
									+ ", {ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("
									+ fonteProjects.cName + ",0,4000)"),
							StringTemplate.create("SUBSTRING("
									+ fonteProjects.cId + ",0,4000)")));

			Iterator<Tuple> i = cfworkitems.iterator();
			Object[] el = null;

			while (i.hasNext()) {

				el = ((Tuple) i.next()).toArray();

				// //
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//
				//

				new SQLInsertClause(OracleConnection, dialect, stgProject)
						.columns(stgProject.cTrackerprefix,
								stgProject.cIsLocal, stgProject.cPk,
								stgProject.fkUriLead, stgProject.cDeleted,
								stgProject.cFinish, stgProject.cUri,
								stgProject.cStart,
								stgProject.fkUriProjectgroup,
								stgProject.cActive, stgProject.cLocation,
								stgProject.fkProjectgroup, stgProject.fkLead,
								stgProject.cLockworkrecordsdate,
								stgProject.cName, stgProject.cId,
								stgProject.dataCaricamento)
						.values(el[0],
								el[1],
								el[2],
								el[3],
								el[4],
								el[5],
								el[6],
								el[7],
								el[8],
								el[9],
								el[10],
								el[11],
								el[12],
								el[13],
								el[14],
								el[15],
								StringTemplate
										.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))"))
						.execute();

			}

			OracleConnection.commit();

		} catch (Throwable e) {

		}

	}

	public void testGetSireProjectLocation() throws Exception {

		ConnectionManager cm = ConnectionManager.getInstance();
		Connection OracleConnection = cm.getConnectionOracle();

		SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
		SQLQuery query = new SQLQuery(OracleConnection, dialect);

		QSireCurrentProject stgProject = QSireCurrentProject.sireCurrentProject;

		List<String> locations = query.from(stgProject).list(
				stgProject.cLocation);

		for (String el : locations) {

			if (testGetProjectSVNPath(el) != null)
				testGetProjectSVNName(el);

		}
	}

	public String testGetProjectSVNPath(String clocation) {

		// String clocation =
		// "default:/Sviluppo/SIRE/A356.Area.Sistemi.Direzionali/Assistenza/.polarion/polarion-project.xml";

		if (clocation.startsWith("default:/Sviluppo/")) {

			//

			int start = clocation.indexOf("Sviluppo") - 1;

			//

			int end = clocation.indexOf(".polarion") - 1;

			return clocation.substring(start, end);

		} else {
			return null;
		}

	}

	public String testGetProjectSVNName(String clocation) {

		// String clocation =
		// "default:/Sviluppo/SIRE/A356.Area.Sistemi.Direzionali/Assistenza/.polarion/polarion-project.xml";

		if (clocation.startsWith("default:/Sviluppo/")) {

			int end = clocation.indexOf(".polarion") - 1;

			clocation = clocation.substring(0, end);

			int start = clocation.lastIndexOf("/") + 1;

			return clocation.substring(start, end);

		} else {
			return null;
		}

	}

	public void testGetAllProjectC_Name() throws Exception {
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

					.where(stgProject.cName.trim().like("SW-%"))
					.list(stgProject.cName,
							StringTemplate.create("INSTR(C_NAME,'{')", "idx"),
							StringTemplate.create("INSTR(C_NAME,'}') as idx2"),
							StringTemplate
									.create("REPLACE(REPLACE(REGEXP_SUBSTR(C_NAME, '{[a-zA-Z0-9_.*!?-]+}'), '{',''),'}','') as name"));

			logger.debug("testUpdateCFCa: " + project.size());
		} catch (Exception e) {

		} finally {
			if (connection != null) {
				connection.close();
			}
		}

	}

	public void testCNameFormat() {
		String c_name = "A..B";

		List<String> object = null;
		List<ProjectCName> projectObject = new ArrayList<ProjectCName>();

		ProjectCName cname = null;

		c_name = c_name.replace("..", "*");

		try {
			StringTokenizer st = new StringTokenizer(c_name, "*");
			StringTokenizer sub = null;

			while (st.hasMoreElements()) {
				sub = new StringTokenizer(st.nextElement().toString(), ".");

				object = new ArrayList<String>();
				while (sub.hasMoreElements()) {
					object.add(sub.nextElement().toString());
				}

				if (object.size() > 0) {
					cname = new ProjectCName();

					if (object.size() == 1) {
						// esiste solo il prodotto
						cname.setProdotto(object.get(0));
					}
					if (object.size() == 2) {
						// esiste solo il prodotto
						cname.setProdotto(object.get(0));
						cname.setModulo(object.get(1));
					}
					if (object.size() == 3) {
						// esiste solo il prodotto
						cname.setProdotto(object.get(0));
						cname.setModulo(object.get(1));
						cname.setFunzionalita(object.get(2));
					}

					projectObject.add(cname);
				}
			}

		} catch (Exception e) {

		}
	}
}