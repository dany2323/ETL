package lispa.schedulers.dao.oreste;

import java.sql.Connection;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.oreste.QStgAmbienteTecnologico;

public class AmbienteTecnologicoDAO {

	public static void recoverAmbienteTecnologico() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgAmbienteTecnologico  qstgAmbienteTecnologico   = QStgAmbienteTecnologico.stgAmbienteTecnologico;

			new SQLDeleteClause(connection, dialect, qstgAmbienteTecnologico)
			.where(qstgAmbienteTecnologico.dataCaricamento.eq(
					DataEsecuzione.getInstance().getDataEsecuzione())).execute();
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