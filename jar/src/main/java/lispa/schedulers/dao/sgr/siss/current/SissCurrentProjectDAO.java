package lispa.schedulers.dao.sgr.siss.current;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.siss.current.QSissCurrentProject;
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

public class SissCurrentProjectDAO
{

	private static Logger logger = Logger.getLogger(SissCurrentProjectDAO.class);
	
	public static void delete(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		
		
		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(){ {
			    setPrintSchema(true);
			}};
			QSissCurrentProject   stgProject  = QSissCurrentProject.sissCurrentProject;
						
					new SQLDeleteClause(connection, dialect, stgProject)
						.where(stgProject.dataCaricamento.lt(dataEsecuzione).or(stgProject.dataCaricamento
								.gt(DateUtils.addDaysToTimestamp(DataEsecuzione.getInstance().getDataEsecuzione(),-1))))
					    .execute();

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}
	}
	
	public static void deleteInDate(Timestamp date) throws Exception {
		
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			
			QSissCurrentProject   stgProject  = QSissCurrentProject.sissCurrentProject;
			
			new SQLDeleteClause(connection, dialect, stgProject)
					.where(stgProject.dataCaricamento.eq(
							date)).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}
	}
	
	public static long fillSissCurrentProject() throws Exception
	{

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection H2Connection = null;
		long n_righe_inserite = 0;
		
		try{
			
			cm = ConnectionManager.getInstance();

			H2Connection = cm.getConnectionSISSCurrent();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(false);

			QSissCurrentProject   stgProject  = QSissCurrentProject.sissCurrentProject;
			lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentProject fonteProjects  = lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentProject.project;

			SQLTemplates dialect = new HSQLDBTemplates(){ {
			    setPrintSchema(true);
			}};
			SQLQuery query = new SQLQuery(H2Connection, dialect); 


			List<Tuple> projects = query.from(fonteProjects)
//					.where(fonteProjects.cLocation.like("default:/Sviluppo/%"))
					.where((fonteProjects.cLocation.length().subtract(fonteProjects.cLocation.toString().replaceAll("/", "").length())).goe(4))
					.where(fonteProjects.cLocation.notLike("default:/GRACO%"))
					.list(new QTuple(
							StringTemplate.create("SUBSTRING("+fonteProjects.cTrackerprefix+",0,4000)") ,
							StringTemplate.create("CASEWHEN ("+fonteProjects.cIsLocal+"= 'true', 1,0 )") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.cPk+",0,4000)") ,
							StringTemplate.create("SUBSTRING("+fonteProjects.fkUriLead+",0,4000)") ,
							StringTemplate.create("CASEWHEN ("+fonteProjects.cDeleted+"= 'true', 1,0 )") ,
							fonteProjects.cFinish,
							StringTemplate.create("SUBSTRING("+fonteProjects.cUri+",0,4000)") ,
							fonteProjects.cStart,
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

			Iterator<Tuple> i = projects.iterator();
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
						stgProject.dataCaricamento,
						stgProject.dmalmCurrentProjectPk
						)
						.values(
								el[0] , 
								el[1] , 
								el[2] , 
								StringUtils.getMaskedValue((String)el[3]) , 
								el[4] , 
								el[5] , 
								el[6] , 
								el[7] , 
								el[8] , 
								el[9] , 
								el[10] ,
								el[11] ,
								StringUtils.getMaskedValue((String)el[12]) ,
								el[13] ,
								el[14] ,
								el[15] ,
								DataEsecuzione.getInstance().getDataEsecuzione(),
								StringTemplate.create("CURRENT_PROJECT_SEQ.nextval")
								)
								.execute();
				
				n_righe_inserite++;
			}

			OracleConnection.commit();

		}
	catch (Exception e)
	{
		ErrorManager.getInstance().exceptionOccurred(true, e);
		logger.error(e.getMessage(), e);
		
		n_righe_inserite=0;
		throw new DAOException(e);
	}
	finally
	{
		if(cm != null) cm.closeConnection(OracleConnection);
		if(cm != null) cm.closeConnection(H2Connection);
	}

	return n_righe_inserite;

	}
	
	public static List<Tuple> getC_NameNull(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> project = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QSissCurrentProject   stgProject  = QSissCurrentProject.sissCurrentProject;
			
			project = 
					
					query.from(stgProject)
					.where(stgProject.dataCaricamento.eq(dataEsecuzione))
				    .where(stgProject.cName.isNull()).list(stgProject.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}
		
		return project;
	}
	
	public static List<Tuple> getAllProjectSW(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> project = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QSissCurrentProject   stgProject  = QSissCurrentProject.sissCurrentProject;
			
			//select  DMALM_SIRE_CURRENT_PROJECT.* from DMALM_SIRE_CURRENT_PROJECT where ltrim(c_name) like 'SW-%'
			
			project = 
					
					query.from(stgProject)
					.where(stgProject.dataCaricamento.eq(dataEsecuzione))
				    .where(stgProject.cName.trim().like("SW-%")).list(stgProject.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}
		
		return project;
	}
	
//	select  
//	c_name, 
//	INSTR(C_NAME,'{'), 
//	INSTR(C_NAME,'}'), 
//	REPLACE(REPLACE(REGEXP_SUBSTR(C_NAME, '{[a-zA-Z0-9_.*!?-]+}'), '{',''),'}','')   
//	from DMALM_SIRE_CURRENT_PROJECT 
//	where ltrim(c_name) like 'SW-%'
	
	public static List<Tuple> getAllProjectC_Name(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> project = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QSissCurrentProject   stgProject  = QSissCurrentProject.sissCurrentProject;
			
			//select  DMALM_SIRE_CURRENT_PROJECT.* from DMALM_SIRE_CURRENT_PROJECT where ltrim(c_name) like 'SW-%'
			
			project = 
					
					query.from(stgProject)

				    .where(stgProject.cName.trim().like("SW-%"))
				    .where(stgProject.dataCaricamento.eq(dataEsecuzione))
			    	.list
			    	 	(
			    			stgProject.cName,
			    			StringTemplate.create("INSTR(C_NAME,'{')"),
			    			StringTemplate.create("INSTR(C_NAME,'}')"),
			    			StringTemplate.create("REPLACE(REPLACE(REGEXP_SUBSTR(C_NAME, '{[a-zA-Z0-9_.*!?-]+}'), '{',''),'}','') as name"),
							StringTemplate.create("SUBSTR(C_NAME, INSTR(C_NAME,'}')+1, LENGTH(C_NAME)-INSTR(C_NAME,'}') )")
						);

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}
		
		return project;
	}
	public static void recoverSissCurrentProject() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSissCurrentProject   stgProject  = QSissCurrentProject.sissCurrentProject;

			new SQLDeleteClause(connection, dialect, stgProject)
			.where(stgProject.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}
}