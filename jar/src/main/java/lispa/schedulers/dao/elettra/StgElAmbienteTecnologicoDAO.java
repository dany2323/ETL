package lispa.schedulers.dao.elettra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElAmbienteTecnologico;
import lispa.schedulers.utils.DateUtils;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class StgElAmbienteTecnologicoDAO {
	private static Logger logger = Logger
			.getLogger(StgElAmbienteTecnologicoDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElAmbienteTecnologico qStgElAmbientetecnologico = QStgElAmbienteTecnologico.stgElAmbienteTecnologico;

			new SQLDeleteClause(connection, dialect, qStgElAmbientetecnologico)
					.where(qStgElAmbientetecnologico.dataCaricamento.lt(
							dataEsecuzioneDelete).or(
							qStgElAmbientetecnologico.dataCaricamento
									.gt(DateUtils.addDaysToTimestamp(
											DataEsecuzione.getInstance()
													.getDataEsecuzione(), -1))))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void recoverElAmbienteTec() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElAmbienteTecnologico qStgElAmbientetecnologico = QStgElAmbienteTecnologico.stgElAmbienteTecnologico;

			new SQLDeleteClause(connection, dialect, qStgElAmbientetecnologico)
					.where(qStgElAmbientetecnologico.dataCaricamento
							.eq(DataEsecuzione.getInstance()
									.getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
