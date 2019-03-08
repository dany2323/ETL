package lispa.schedulers.dao.oreste;

import java.sql.Connection;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.oreste.QStgSottosistemi;

public class SottosistemiDAO {

	public static void recoverSottosistemi() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgSottosistemi  qstgSottosistemi   = QStgSottosistemi.stgSottosistemi;

			new SQLDeleteClause(connection, dialect, qstgSottosistemi)
			.where(qstgSottosistemi.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}
}