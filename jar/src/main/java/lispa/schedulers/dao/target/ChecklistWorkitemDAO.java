package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import org.apache.log4j.Logger;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleCallableStatement;

public class ChecklistWorkitemDAO {

	private static Logger logger = Logger.getLogger(ChecklistWorkitemDAO.class);
	
	public static void createChecklistWiTrsl() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			logger.info("START ChecklistWorkitemDAO.createChecklistWiTrsl");
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure(
					"CHECKLIST_WORKITEM.CREATE_CHECKLIST_WI_TRSL", 0);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.execute();
				connection.commit();
			}
			logger.info("STOP ChecklistWorkitemDAO.createChecklistWiTrsl");

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
	
	public static void insertChecklistWiTrsl(Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			logger.info("START ChecklistWorkitemDAO.insertChecklistWiTrsl");
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure(
					"CHECKLIST_WORKITEM.INSERT_CHECKLIST_WI_TRSL", 1);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.setTimestamp(1, dataEsecuzione);
				ocs.execute();
				connection.commit();
			}
			logger.info("START ChecklistWorkitemDAO.insertChecklistWiTrsl");

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}