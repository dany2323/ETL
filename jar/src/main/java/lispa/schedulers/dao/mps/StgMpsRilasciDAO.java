package lispa.schedulers.dao.mps;

import java.sql.Connection;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsRilasci;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class StgMpsRilasciDAO {
	private static Logger logger = Logger.getLogger(StgMpsRilasciDAO.class);

	public static void recoverStgMpsRilasci() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsRilasci qstgmpsrilasci = QDmalmStgMpsRilasci.dmalmStgMpsRilasci;

			new SQLDeleteClause(connection, dialect, qstgmpsrilasci).execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
