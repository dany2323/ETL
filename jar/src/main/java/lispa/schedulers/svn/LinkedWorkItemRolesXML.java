package lispa.schedulers.svn;

import java.sql.Connection;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmWorkitemLinkRoles;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class LinkedWorkItemRolesXML {

	private static final Logger logger = Logger
			.getLogger(LinkedWorkItemRolesXML.class);

	public static void recoverLinkedWorkItemRoles() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmAlmWorkitemLinkRoles qDmAlmWorkitemLinkRoles = QDmAlmWorkitemLinkRoles.dmAlmWorkitemLinkRoles;
			new SQLDeleteClause(connection, dialect, qDmAlmWorkitemLinkRoles)
					.where(qDmAlmWorkitemLinkRoles.datacaricamento
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
