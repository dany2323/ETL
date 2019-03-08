package lispa.schedulers.dao.elettra;

import java.sql.Connection;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElUnitaOrganizzative;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class StgElUnitaOrganizzativeDAO {
	private static Logger logger = Logger.getLogger(StgElUnitaOrganizzativeDAO.class);

	public static void recoverElUnitaOrganizzativa() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElUnitaOrganizzative qstgElUnitaOrganizzative = QStgElUnitaOrganizzative.stgElUnitaOrganizzative;

			new SQLDeleteClause(connection, dialect, qstgElUnitaOrganizzative)
					.where(qstgElUnitaOrganizzative.dataCaricamento
							.eq(DataEsecuzione.getInstance()
									.getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
