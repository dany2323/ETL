package lispa.schedulers.dao.mps;

import java.sql.Connection;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsVerbali;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class StgMpsVerbaliDAO {
	private static Logger logger = Logger.getLogger(StgMpsVerbaliDAO.class);

	public static void recoverStgMpsVerbali() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsVerbali qstgmpsverbali = QDmalmStgMpsVerbali.dmalmStgMpsVerbali;

			new SQLDeleteClause(connection, dialect, qstgmpsverbali).execute();

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
