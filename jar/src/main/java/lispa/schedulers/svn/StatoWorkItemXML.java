package lispa.schedulers.svn;

import java.sql.Connection;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmStatoWorkitem;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class StatoWorkItemXML {

	private static Logger logger = Logger.getLogger(StatoWorkItemXML.class);

	public static void recoverStatoWorkitem() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmAlmStatoWorkitem qDmAlmStatoWorkitem = QDmAlmStatoWorkitem.dmAlmStatoWorkitem;

			new SQLDeleteClause(connection, dialect, qDmAlmStatoWorkitem)
					.where(qDmAlmStatoWorkitem.dataCaricamento
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