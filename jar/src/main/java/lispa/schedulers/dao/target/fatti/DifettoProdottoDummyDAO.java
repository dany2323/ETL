package lispa.schedulers.dao.target.fatti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDifettoProdotto;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

public class DifettoProdottoDummyDAO {
	private static Logger logger = Logger
			.getLogger(DifettoProdottoDummyDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmDifettoProdotto difettoProdotto = QDmalmDifettoProdotto.dmalmDifettoProdotto;

	public static Timestamp getStartDate() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Timestamp> dataStoricizzazione = query.from(difettoProdotto)
					.list(difettoProdotto.dtStoricizzazione.min());

			return dataStoricizzazione.get(0);
		} catch (Exception e) {
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertDummyValue(Timestamp dataEsecuzione,
			Timestamp dataPrimoGiornoMese, String startYearMonth,
			String nextYearMonth) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		int res = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_DIFETTO_PRODOTTO_DUMMY);

			ps = connection.prepareStatement(sql);

			// DT_CARICAMENTO_RECORD_DIFETTO
			ps.setTimestamp(1, dataEsecuzione);
			// DT_STORICIZZAZIONE
			ps.setTimestamp(2, dataPrimoGiornoMese);
			// DT_OSSERVAZIONE
			ps.setTimestamp(3, dataPrimoGiornoMese);
			ps.setString(4, startYearMonth);
			ps.setString(5, startYearMonth);
			ps.setString(6, startYearMonth);
			ps.setString(7, nextYearMonth);
			ps.setString(8, nextYearMonth);
			// DT_OSSERVAZIONE
			ps.setTimestamp(9, dataPrimoGiornoMese);
			ps.setString(10, startYearMonth);
			ps.setString(11, startYearMonth);
			ps.setString(12, startYearMonth);
			ps.setString(13, nextYearMonth);
			ps.setString(14, nextYearMonth);

			res = ps.executeUpdate();

			if (res > 0) {
				logger.debug("DifettoProdottoDummyDAO.insertDummyValue - nextYearMonth: "
						+ nextYearMonth + " - insertRow: " + res);
			}

			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateUltimaVersione(String endYearMonth)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConstants.SQL_UPDATE_ULTIMA_VERSIONE_DIFETTO_DUMMY);

			ps = connection.prepareStatement(sql);

			ps.setString(1, endYearMonth);

			ps.executeUpdate();

			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
