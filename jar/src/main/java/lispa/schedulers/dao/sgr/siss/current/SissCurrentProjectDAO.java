package lispa.schedulers.dao.sgr.siss.current;

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
import lispa.schedulers.queryimplementation.staging.sgr.siss.current.QSissCurrentProject;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;

public class SissCurrentProjectDAO
{

	private static Logger logger = Logger.getLogger(SissCurrentProjectDAO.class);
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentProject fonteProjects  = lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentProject.project;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.current.SissCurrentProject stgProject  = lispa.schedulers.queryimplementation.staging.sgr.siss.current.SissCurrentProject.project;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap fonteSireSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap.urimap;


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
		Connection pgConnection = null;
		long n_righe_inserite = 0;
		
		try{
			
			cm = ConnectionManager.getInstance();

			pgConnection = cm.getConnectionSISSCurrent();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(false);

			

			PostgresTemplates dialect = new PostgresTemplates(){ {
			    setPrintSchema(true);
			}};
			SQLQuery query = new SQLQuery(pgConnection, dialect); 
			

			List<Tuple> projects = query.from(fonteProjects)
					.where((fonteProjects.cLocation.length().subtract(fonteProjects.cLocation.toString().replaceAll("/", "").length())).goe(4))
					.where(fonteProjects.cLocation.notLike("default:/GRACO%"))
					.list(new QTuple(
		                            StringTemplate.create("SUBSTRING("+fonteProjects.cTrackerprefix+",0,4000)") ,
		                            StringTemplate.create("0") ,
		                            StringTemplate.create("(SELECT a.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " a WHERE a.c_id = " + fonteProjects.cUri + ") as c_pk"),
		                            StringTemplate.create("(SELECT b.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " b WHERE b.c_id = " + fonteProjects.fkUriLead + ") as fk_uri_lead"),
		                            StringTemplate.create("CASE WHEN "+fonteProjects.cDeleted+"= 'true' THEN 1 ELSE 0 END") ,
		                            fonteProjects.cFinish,
		                            StringTemplate.create("(SELECT c.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " c WHERE c.c_id = " + fonteProjects.cUri + ") as c_uri"),
		                            fonteProjects.cStart,
		                            StringTemplate.create("(SELECT d.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " d WHERE d.c_id = " + fonteProjects.fkUriProjectgroup + ") as FK_URI_PROJECTGROUP"),
		                            StringTemplate.create("CASE WHEN "+fonteProjects.cActive+"= 'true' THEN 1 ELSE 0 END") ,
		                            StringTemplate.create("SUBSTRING("+fonteProjects.cLocation+",0,4000)") ,
		                            StringTemplate.create("(SELECT e.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " e WHERE e.c_id = " + fonteProjects.fkUriProjectgroup + ") as fk_projectgroup"),
		                            StringTemplate.create("(SELECT f.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " f WHERE f.c_id = " + fonteProjects.fkUriLead + ") as fk_lead"),
		                            StringTemplate.create("to_char("+fonteProjects.cLockworkrecordsdate+", 'yyyy-MM-dd 00:00:00')"),
		                            StringTemplate.create("SUBSTRING("+fonteProjects.cName+",0,4000)") ,
		                            StringTemplate.create("SUBSTRING("+fonteProjects.cId+",0,4000)") 
		                            )
							);

			logger.debug("fillSissCurrentProject - projects.size: "+ projects.size());
			
			SQLInsertClause insert = new SQLInsertClause(OracleConnection, dialect, stgProject);
			Iterator<Tuple> i = projects.iterator();
			Object[] el= null;
			
			while (i.hasNext()) {

				el= ((Tuple)i.next()).toArray();

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
						stgProject.cId
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
								el[15] 
								)
						.addBatch();
				
				n_righe_inserite++;
				
				if (!insert.isEmpty()) {
					if (n_righe_inserite % DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						OracleConnection.commit();
						insert = new SQLInsertClause(OracleConnection, dialect, stgProject);
					}
				}

			}
			if (!insert.isEmpty()) {
				insert.execute();
				OracleConnection.commit();
			}
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
		if(cm != null) cm.closeConnection(pgConnection);
	}

	return n_righe_inserite;

	}
	
	
}