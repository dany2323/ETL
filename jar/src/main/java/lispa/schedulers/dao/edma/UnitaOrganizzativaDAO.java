package lispa.schedulers.dao.edma;

import java.sql.Connection;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.edma.QStgUnitaOrganizzative;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class UnitaOrganizzativaDAO {

	private static Logger logger = Logger.getLogger(UnitaOrganizzativaDAO.class);

	public static void recoverUO() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgUnitaOrganizzative qstgUnitaOrganizzative = QStgUnitaOrganizzative.stgUnitaOrganizzative;

			new SQLDeleteClause(connection, dialect, qstgUnitaOrganizzative)
					.where(qstgUnitaOrganizzative.dataCaricamento
							.eq(DataEsecuzione.getInstance()
									.getDataEsecuzione())).execute();
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