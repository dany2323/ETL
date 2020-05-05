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
	
	public static void fillSissCurrentProject() throws Exception
	{

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection pgConnection = null;
		long sizeCounter = 0;
		
		try{
			
			cm = ConnectionManager.getInstance();

			pgConnection = cm.getConnectionSISSCurrent();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(false);

			

			PostgresTemplates dialect = new PostgresTemplates(){ {
			    setPrintSchema(true);
			}};
			SQLQuery query = new SQLQuery(pgConnection, dialect); 
			

			 List<Tuple> project = query.from(fonteProjects)
	                    .where(fonteProjects.cLocation.notLike("default:/GRACO%"))
	                    .list(fonteProjects.all());
	            
	            SQLInsertClause insert = new SQLInsertClause(OracleConnection, dialect, stgProject);

	            for(Tuple el:project) {

					insert.columns(
							stgProject.cTrackerprefix,
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
									el.get(fonteProjects.cTrackerprefix),
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
									el.get(fonteProjects.cId)
									)
							.addBatch();
				
				sizeCounter++;
				
				if (!insert.isEmpty()) {
					if (sizeCounter % DmAlmConstants.BATCH_SIZE == 0) {
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
		
		sizeCounter=0;
		throw new DAOException(e);
	}
	finally
	{
		if(cm != null) cm.closeConnection(OracleConnection);
		if(cm != null) cm.closeConnection(pgConnection);
	}


	}
	
	
}