package lispa.schedulers.dao.sgr.siss.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_VSISSWORKITEMLINK;

import java.sql.Connection;
import java.sql.PreparedStatement;

import lispa.schedulers.action.DmAlmETL;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissWorkitemLink;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class VSissHistoryWorkitemLinkDAO {
	
	private static Logger logger = Logger.getLogger(DmAlmETL.class); 

	public static void fillVSissWorkitemLink() throws Exception {
		
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		PreparedStatement ps = null;
		
		try {
			cm 			= ConnectionManager.getInstance();
			connection 	= cm.getConnectionOracle();
			
			connection.setAutoCommit(false);
			
			String sql = QueryManager.getInstance().getQuery(SQL_VSISSWORKITEMLINK);
			
			ps = connection.prepareStatement(sql);
			
			ps.executeUpdate();
			
			ps.close();
		
			connection.commit();
		}
		catch(Exception e) {
			
			logger.error(e.getMessage(), e);
		}
		finally {
			if(cm != null) {
				cm.closeConnection(connection);
			}
		}
		
	}

	public static void delete() throws Exception {
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		
		
		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(){ {
			    setPrintSchema(true);
			}};
			QSissWorkitemLink stgSissWorkitemLink    = QSissWorkitemLink.vSissWorkitemLink;
						
					new SQLDeleteClause(connection, dialect, stgSissWorkitemLink)
					    .execute();

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
	public static void recoverVSissWorkitemLink() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissWorkitemLink stgSireWorkitemLink = QSissWorkitemLink.vSissWorkitemLink;
			new SQLDeleteClause(connection, dialect, stgSireWorkitemLink).where(stgSireWorkitemLink.datacaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();

			connection.commit();
		}
		catch(Exception e){
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		} 
		finally 
		{
			if(cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
	
}
