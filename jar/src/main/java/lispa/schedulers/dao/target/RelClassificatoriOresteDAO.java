package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;

import org.apache.log4j.Logger;

public class RelClassificatoriOresteDAO {
	
	private static Logger					logger				= Logger.getLogger(RelClassificatoriOresteDAO.class);

	public static int insertRelClassificatoriOreste(Timestamp dataEsecuzione) throws DAOException {
		
		int res = 0;
		
		ConnectionManager cm = null;
		Connection conn = null;
		
		PreparedStatement ps = null;
		
		try {
		
			
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			
			ps = conn.prepareStatement(
					QueryManager.getInstance().getQuery(DmAlmConfigReaderProperties.SQL_REL_AMBIENTITECNOLOGICI_CLASSIFICATORI));
			
			ps.setTimestamp(1, dataEsecuzione);
			
			res += ps.executeUpdate();
			
			cm.closeConnection(conn);
			ps.close();
			conn = cm.getConnectionOracle();
			
			ps = conn.prepareStatement(
					QueryManager.getInstance().getQuery(DmAlmConfigReaderProperties.SQL_REL_MODULI_CLASSIFICATORI));
			
			ps.setTimestamp(1, dataEsecuzione);
			
			res += ps.executeUpdate();
			
			cm.closeConnection(conn);
			ps.close();
			conn = cm.getConnectionOracle();
			
			ps = conn.prepareStatement(
					QueryManager.getInstance().getQuery(DmAlmConfigReaderProperties.SQL_REL_FUNZIONALITA_CLASSIFICATORI));
			
			ps.setTimestamp(1, dataEsecuzione);
			
			res += ps.executeUpdate();
			
			cm.closeConnection(conn);
			ps.close();
			conn = cm.getConnectionOracle();
			
			ps = conn.prepareStatement(
					QueryManager.getInstance().getQuery(DmAlmConfigReaderProperties.SQL_REL_PRODOTTI_CLASSIFICATORI));
			
			ps.setTimestamp(1, dataEsecuzione);
			
			res += ps.executeUpdate();
			ps.close();
			
			
			
		}
		catch(Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			
			logger.error(e.getMessage(), e);
		}
		finally {
		
				try {					if(ps!=null) ps.close();
					if(cm != null) cm.closeConnection( conn);
				} catch(SQLException e) {logger.debug(e);}

		}
		return res;
		
	}
	
}
