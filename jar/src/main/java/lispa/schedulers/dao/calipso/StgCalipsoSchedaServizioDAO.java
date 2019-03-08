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

public class StgCalipsoSchedaServizioDAO {

	public static void recoverStgCalipsoSchedaServizio() throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		Timestamp dataCaricamento = DataEsecuzione.getInstance().getDataEsecuzione();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			String sql = QueryUtils.getCallProcedure("CALIPSO.RECOVER_STAGING", 1);

			cs = connection.prepareCall(sql);
			cs.setTimestamp(1, dataCaricamento);
			cs.execute();
			
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

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
