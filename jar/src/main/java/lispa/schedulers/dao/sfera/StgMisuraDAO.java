package lispa.schedulers.dao.sfera;

import java.sql.Connection;
import java.sql.Timestamp;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sfera.QDmalmStgMisura;
import lispa.schedulers.utils.DateUtils;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class StgMisuraDAO {

	private static Logger logger = Logger.getLogger(StgMisuraDAO.class);

	public static void delete(Timestamp dataEsecuzioneDelete)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMisura qstgmisura = QDmalmStgMisura.dmalmStgMisura;

			new SQLDeleteClause(connection, dialect, qstgmisura).where(
					qstgmisura.dataCaricamento.lt(dataEsecuzioneDelete).or(
							qstgmisura.dataCaricamento.gt(DateUtils
									.addDaysToTimestamp(DataEsecuzione
											.getInstance().getDataEsecuzione(),
											-1)))).execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMisura() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmStgMisura stgmisura = QDmalmStgMisura.dmalmStgMisura;
			new SQLDeleteClause(connection, dialect, stgmisura).where(
					stgmisura.dataCaricamento.eq(DataEsecuzione.getInstance()
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
