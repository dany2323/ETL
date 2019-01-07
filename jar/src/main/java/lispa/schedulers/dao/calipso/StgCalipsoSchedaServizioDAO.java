package lispa.schedulers.dao.calipso;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.QueryUtils;
import org.apache.log4j.Logger;


public class StgCalipsoSchedaServizioDAO {
	private static Logger logger = Logger.getLogger(StgCalipsoSchedaServizioDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			String sql = QueryUtils.getCallProcedure("CALIPSO.DELETE_STAGING", 1);

			cs = connection.prepareCall(sql);
			cs.setTimestamp(1, dataEsecuzioneDelete);
			cs.execute();
			
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void fillStaging() throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		Timestamp dataCaricamento = DataEsecuzione.getInstance().getDataEsecuzione();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			String sql = QueryUtils.getCallProcedure("CALIPSO.FILL_STAGING", 1);

			cs = connection.prepareCall(sql);
			cs.setTimestamp(1, dataCaricamento);
			cs.execute();
			
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
	}
}
