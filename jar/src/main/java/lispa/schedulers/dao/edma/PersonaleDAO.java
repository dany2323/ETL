package lispa.schedulers.dao.edma;

import java.sql.Connection;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.edma.QStgPersonale;

public class PersonaleDAO {
	private static Logger logger = Logger.getLogger(PersonaleDAO.class); 

	public static void recoverPersonale() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgPersonale  qstgPersonale = QStgPersonale.stgPersonale;

			new SQLDeleteClause(connection, dialect, qstgPersonale)
			.where(qstgPersonale.dataCaricamento.eq(
					DataEsecuzione.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) 
		{
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}

}