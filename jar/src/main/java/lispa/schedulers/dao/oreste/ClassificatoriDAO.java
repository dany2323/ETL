package lispa.schedulers.dao.oreste;

import java.sql.Connection;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.oreste.QStgClassificatori;

public class ClassificatoriDAO {

	public static void  recoverClassificatori() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgClassificatori  stgClassificatori   = QStgClassificatori.stgClassificatori;

			new SQLDeleteClause(connection, dialect, stgClassificatori)
			.where(stgClassificatori.dataCaricamento.eq(
					DataEsecuzione.getInstance().getDataEsecuzione())).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}


}