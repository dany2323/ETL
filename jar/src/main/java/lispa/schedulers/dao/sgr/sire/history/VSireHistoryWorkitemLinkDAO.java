package lispa.schedulers.dao.sgr.sire.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_VSIREWORKITEMLINK;

import java.sql.Connection;
import java.sql.PreparedStatement;

import lispa.schedulers.action.DmAlmETL;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireWorkitemLink;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class VSireHistoryWorkitemLinkDAO {
	
	private static Logger logger = Logger.getLogger(DmAlmETL.class); 

	public static void fillVSireWorkitemLink() throws Exception {
		
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		PreparedStatement ps = null;
		
		try {
			cm 			= ConnectionManager.getInstance();
			connection 	= cm.getConnectionOracle();
			
			connection.setAutoCommit(false);
			
			String sql = QueryManager.getInstance().getQuery(SQL_VSIREWORKITEMLINK);
			
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
			QSireWorkitemLink stgSireWorkitemLink    = QSireWorkitemLink.vSireWorkitemLink;
						
					new SQLDeleteClause(connection, dialect, stgSireWorkitemLink)
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
	
	public static void recoverVSireWorkitemLink() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireWorkitemLink stgSireWorkitemLink = QSireWorkitemLink.vSireWorkitemLink;

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
