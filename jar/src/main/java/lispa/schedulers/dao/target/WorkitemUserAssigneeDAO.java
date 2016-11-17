package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;

import org.apache.log4j.Logger;

public class WorkitemUserAssigneeDAO {
	private static Logger logger = Logger
			.getLogger(WorkitemUserAssigneeDAO.class);

	public static int insertWorkitemUserAssignee(Timestamp dataEsecuzione)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;

		int res = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_WORKITEM_USERASSIGNEES);
			ps = connection.prepareStatement(sql);

			res = ps.executeUpdate();

			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			// l'errore non è bloccante i workitem assignees sono recuperabili
			// manualmente
			logger.debug(e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return res;
	}
}
