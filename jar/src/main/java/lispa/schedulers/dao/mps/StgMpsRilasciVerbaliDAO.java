package lispa.schedulers.dao.mps;

import java.sql.Connection;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsRilasciVerbali;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class StgMpsRilasciVerbaliDAO {

	private static Logger logger = Logger.getLogger(StgMpsRilasciVerbaliDAO.class);

	public static void recoverStgMpsRilasciVerbali() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsRilasciVerbali qstgmpsrilasciverbali = QDmalmStgMpsRilasciVerbali.dmalmStgMpsRilasciVerbali;

			new SQLDeleteClause(connection, dialect, qstgmpsrilasciverbali)
					.execute();

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
