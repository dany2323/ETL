package lispa.schedulers.svn;

import java.sql.Connection;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSchedeServizio;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class SIRESchedeServizioXML {

	private static Logger logger = Logger
			.getLogger(SIRESchedeServizioXML.class);

	public static void recoverSIRESchedeServizio() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmSchedeServizio qDmalmSchedeServizio = QDmalmSchedeServizio.dmalmSchedeServizio;

			new SQLDeleteClause(connection, dialect, qDmalmSchedeServizio)
					.where(qDmalmSchedeServizio.dtCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();

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
