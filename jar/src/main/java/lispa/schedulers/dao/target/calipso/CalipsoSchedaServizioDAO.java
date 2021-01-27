package lispa.schedulers.dao.target.calipso;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import org.apache.log4j.Logger;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.QueryUtils;

public class CalipsoSchedaServizioDAO {

	private static Logger logger = Logger.getLogger(CalipsoSchedaServizioDAO.class);
	
	public static void insertSchedaServizio(Timestamp dataCaricamento) throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		
		try {
			logger.info("START - CalipsoSchedaServizioDAO.insertSchedaServizio");
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);
			String sql = QueryUtils.getCallProcedure("CALIPSO.FILL_TARGET", 2);
			cs = connection.prepareCall(sql);
			cs.setTimestamp(1, dataCaricamento);
			cs.setTimestamp(2, DateUtils.getDtFineValidita9999());
			cs.execute();
			
			connection.commit();
			
			logger.info("STOP - CalipsoSchedaServizioDAO.insertSchedaServizio");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cs != null) {
				cs.close();
			}
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
	
	public static int getRigheInseriteModificateSchedaServizio(String type, Timestamp dataCaricamento)  throws DAOException, SQLException {
		int righe = 0;
		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			String sql = QueryUtils.getCallFunction("CALIPSO.GET_RIGHE_INSERITE", 3);

			cs = connection.prepareCall(sql);
			cs.registerOutParameter(1, Types.INTEGER);
			cs.setString(2, type);
			cs.setTimestamp(3, dataCaricamento);
			cs.setTimestamp(4, DateUtils.getDtFineValidita9999());
			cs.executeUpdate();
			
			righe = cs.getInt(1);
			
			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cs != null) {
				cs.close();
			}
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		
		return righe;
	}
}
