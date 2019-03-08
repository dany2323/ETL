package lispa.schedulers.dao.sgr.sire.current;

import java.sql.Connection;
import java.sql.Timestamp;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireCurrentWorkitemLinked;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class SireCurrentWorkitemLinkedDAO {

	private static Logger logger = Logger.getLogger(SireCurrentWorkitemLinkedDAO.class);

	public static void delete(Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireCurrentWorkitemLinked stgProjectgroup = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;

			new SQLDeleteClause(connection, dialect, stgProjectgroup).where(
					stgProjectgroup.dataCaricamento.lt(dataEsecuzione).or(
							stgProjectgroup.dataCaricamento.eq(DataEsecuzione
									.getInstance().getDataEsecuzione())))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverSireCurrentWorkitemLinked() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireCurrentWorkitemLinked stgProjectgroup = QSireCurrentWorkitemLinked.sireCurrentWorkitemLinked;

			new SQLDeleteClause(connection, dialect, stgProjectgroup).where(
					stgProjectgroup.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
