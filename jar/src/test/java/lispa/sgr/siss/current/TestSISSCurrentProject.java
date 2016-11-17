package lispa.sgr.siss.current;


import java.sql.Connection;
import java.util.Iterator;
import java.util.List;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

import junit.framework.TestCase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.siss.current.QSissCurrentProject;

public class TestSISSCurrentProject
extends TestCase 
{

	public void testFillStaging() throws Exception
	{

		//		C_TRACKERPREFIX	VARCHAR2
		//		C_IS_LOCAL	NUMBER
		//		C_PK	VARCHAR2
		//		FK_URI_LEAD	VARCHAR2
		//		C_DELETED	NUMBER
		//		C_FINISH	DATE
		//		C_URI	VARCHAR2
		//		C_START	DATE
		//		FK_URI_PROJECTGROUP	VARCHAR2
		//		C_ACTIVE	NUMBER
		//		C_LOCATION	VARCHAR2
		//		FK_PROJECTGROUP	VARCHAR2
		//		FK_LEAD	VARCHAR2
		//		C_LOCKWORKRECORDSDATE	DATE
		//		C_NAME	VARCHAR2
		//		C_ID	VARCHAR2
		//		C_REV	NUMBER
		//		DATA_CARICAMENTO	TIMESTAMP(6)


		Connection OracleConnection = null;
		Connection H2Connection = null;

		try{

			ConnectionManager cm = ConnectionManager.getInstance();

			H2Connection = cm.getConnectionSISSCurrent();

			OracleConnection = cm.getConnectionOracle();
			OracleConnection.setAutoCommit(false);

			QSissCurrentProject   stgProject  = QSissCurrentProject.sissCurrentProject;
			lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentProject fonteProjects  = lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentProject.project;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(H2Connection, dialect); 


			List<Tuple> cfworkitems = query.from(fonteProjects)
					.list(new QTuple(
							StringTemplate.create("SUBSTRING("+fonteProjects.cTrackerprefix+",0,4000)") ,
							StringTemplate.create("CASEWHEN ("+fonteProjects.cIsLocal+"= 'true', 1,0 )") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.cPk+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.fkUriLead+",0,4000)") ,
							StringTemplate.create("CASEWHEN ("+fonteProjects.cDeleted+"= 'true', 1,0 )") ,
							StringTemplate.create("FORMATDATETIME("+fonteProjects.cFinish+", {ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("+fonteProjects.cUri+",0,4000)") ,
							StringTemplate.create("FORMATDATETIME("+fonteProjects.cStart+", {ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("+fonteProjects.fkUriProjectgroup+",0,4000)") ,
							StringTemplate.create("CASEWHEN ("+fonteProjects.cActive+"= 'true', 1,0 )") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.cLocation+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.fkProjectgroup+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.fkLead+",0,4000)") ,
							StringTemplate.create("FORMATDATETIME("+fonteProjects.cLockworkrecordsdate+", {ts 'yyyy-MM-dd 00:00:00'})"),
							StringTemplate.create("SUBSTRING("+fonteProjects.cName+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.cId+",0,4000)") 
							)
							);

			Iterator<Tuple> i = cfworkitems.iterator();
			Object[] el= null;


			
			while (i.hasNext()) {

				el= ((Tuple)i.next()).toArray();

				new SQLInsertClause(OracleConnection, dialect, stgProject)
				.columns(
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
						stgProject.dataCaricamento
						)
						.values(
								el[0] , 
								el[1] , 
								el[2] , 
								el[3] , 
								el[4] , 
								el[5] , 
								el[6] , 
								el[7] , 
								el[8] , 
								el[9] , 
								el[10] ,
								el[11] ,
								el[12] ,
								el[13] ,
								el[14] ,
								el[15] ,
								StringTemplate.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))")
								)
								.execute();


			}

			OracleConnection.commit();

			

		}
		catch(Throwable e)
		{

			
			
		}



	}
	
	public void testUpdateCleaningInfo() throws Exception
	{
		EsitiCaricamentoDAO.updateCleaningInfo
		(
				DataEsecuzione.getInstance().getDataEsecuzione(),
				DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT, 
				2, 
				1
		);
	}
}