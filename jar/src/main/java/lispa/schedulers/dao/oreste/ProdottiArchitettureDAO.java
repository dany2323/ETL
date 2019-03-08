package lispa.schedulers.dao.oreste;

import java.sql.Connection;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.oreste.QStgProdottiArchitetture;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class ProdottiArchitettureDAO {
	
	public static void recoverProdottiArchitetture() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgProdottiArchitetture qstgProdottiArchitetture = QStgProdottiArchitetture.stgProdottiArchitetture;

			new SQLDeleteClause(connection, dialect, qstgProdottiArchitetture)
					.where(qstgProdottiArchitetture.dataCaricamento
							.eq(DataEsecuzione.getInstance()
									.getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}
}