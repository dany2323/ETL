package lispa.schedulers.dao.elettra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElAmbienteTecnologicoClassificatori;
import lispa.schedulers.utils.DateUtils;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class StgElAmbienteTecnologicoClassificatoriDAO {
	private static Logger logger = Logger
			.getLogger(StgElAmbienteTecnologicoClassificatoriDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElAmbienteTecnologicoClassificatori qStgElAmbienteTecnologicoClassificatori = QStgElAmbienteTecnologicoClassificatori.stgElAmbienteTecnologicoClassificatori;

			new SQLDeleteClause(connection, dialect,
					qStgElAmbienteTecnologicoClassificatori)
					.where(qStgElAmbienteTecnologicoClassificatori.dataCaricamento
							.lt(dataEsecuzioneDelete)
							.or(qStgElAmbienteTecnologicoClassificatori.dataCaricamento
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

	public static void recoverElAmbienteClass() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElAmbienteTecnologicoClassificatori qStgElAmbienteTecnologicoClassificatori = QStgElAmbienteTecnologicoClassificatori.stgElAmbienteTecnologicoClassificatori;

			new SQLDeleteClause(connection, dialect,
					qStgElAmbienteTecnologicoClassificatori).where(
					qStgElAmbienteTecnologicoClassificatori.dataCaricamento
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
