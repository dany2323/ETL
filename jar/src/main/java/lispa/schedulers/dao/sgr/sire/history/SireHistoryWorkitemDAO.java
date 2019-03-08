package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryWorkitem;
import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class SireHistoryWorkitemDAO {

	private static Logger logger = Logger.getLogger(SireHistoryWorkitemDAO.class);

	public static void delete(Timestamp dataEsecuzioneDeleted) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			// elimina le righe vecchie dallo Staging lasciando la max(revision)
			// per ogni tipologia di workitem
			// sono inoltre lasciate le revision relative a:
			// 'chiuso', 'chiuso_falso', 'in_esercizio', 'in_esecuzione', 'completo', 'eseguito'
			// i "defect" non sono eliminati altrimenti la query non riesce a calcolare la data chiusura
			// nei casi di riapertura e nuova chiusura
			
			String sql = QueryManager
					.getInstance()
					.getQuery(
							DmAlmConfigReaderProperties.SQL_DELETE_SIRE_HISTORY_WORKITEM);

			ps = connection.prepareStatement(sql);
			ps.setTimestamp(1, dataEsecuzioneDeleted);
			ps.setTimestamp(2, DataEsecuzione.getInstance().getDataEsecuzione());

			ps.executeUpdate();
			ps.close();

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

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireHistoryWorkitem stgWorkItems = QSireHistoryWorkitem.sireHistoryWorkitem;

			new SQLDeleteClause(connection, dialect, stgWorkItems).where(
					stgWorkItems.dataCaricamento.eq(date)).execute();

		} catch (Exception e) {

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverSireHistoryWorkitem() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryWorkitem stgWorkItems = QSireHistoryWorkitem.sireHistoryWorkitem;
			// Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00",
			// "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgWorkItems).where(
					stgWorkItems.dataCaricamento.eq(DataEsecuzione
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