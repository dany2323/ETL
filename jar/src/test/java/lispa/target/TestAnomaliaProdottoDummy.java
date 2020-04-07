package lispa.target;

import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import org.apache.log4j.Logger;
import junit.framework.TestCase;
import lispa.schedulers.dao.target.fatti.AnomaliaProdottoDAO;
import lispa.schedulers.dao.target.fatti.AnomaliaProdottoDummyDAO;
import lispa.schedulers.dao.target.fatti.DifettoDAO;
import lispa.schedulers.dao.target.fatti.DifettoProdottoDummyDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.utils.DateUtils;
public class TestAnomaliaProdottoDummy extends TestCase {

	private static Logger logger = Logger.getLogger(TestAnomaliaProdottoDummy.class);
	
	public void testAnomaliaProdottoDummy() throws Exception {
		DmAlmConfigReaderProperties.setFileProperties("/Users/lucaporro/LISPA/DataMart/props/dm_alm.properties");
		Log4JConfiguration.inizialize();
		
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2020-04-06 21:00:00", "yyyy-MM-dd HH:mm:00");

		gestioneTabelleDummy(dataEsecuzione);
	}
	
	private static void gestioneTabelleDummy(Timestamp dataEsecuzione) throws DAOException, Exception {

		Date endDate = dataEsecuzione;
		String endYearMonth = DateUtils.dateToString(endDate, "yyyyMM");

		// elimina record mese DUMMY duplicati presenti su tabella
		// principale a seguito di insert successive (sia anomalia che
		// difetto)
		AnomaliaProdottoDummyDAO.deleteMonthDuplicateValue();

		// gestione anomalia prodotto
		Timestamp startDate = AnomaliaProdottoDummyDAO.getStartDate();
		if (startDate != null) {
			startDate = DateUtils.getFistDayMonth(startDate);
			Timestamp nextDate = DateUtils.addMonthsToTimestamp(startDate, 1);

			String startYearMonth = DateUtils.dateToString(startDate, "yyyyMM");
			String nextYearMonth = DateUtils.dateToString(nextDate, "yyyyMM");

			logger.debug("CheckAnomaliaDifettoProdottoFacade - anomalia - startYearMonth: "
					+ startYearMonth);
			logger.debug("CheckAnomaliaDifettoProdottoFacade - anomalia - endYearMonth: "
					+ endYearMonth);

			while (Integer.parseInt(nextYearMonth) <= Integer
					.parseInt(endYearMonth)) {
				insertDummyValueAnomalia(dataEsecuzione, nextDate, startYearMonth, nextYearMonth);

				startDate = DateUtils.addMonthsToTimestamp(startDate, 1);
				nextDate = DateUtils.addMonthsToTimestamp(startDate, 1);

				startYearMonth = DateUtils.dateToString(startDate, "yyyyMM");
				nextYearMonth = DateUtils.dateToString(nextDate, "yyyyMM");
			}
		}

		// gestione difetto prodotto
		startDate = DifettoProdottoDummyDAO.getStartDate();
		if (startDate != null) {
			startDate = DateUtils.getFistDayMonth(startDate);
			Timestamp nextDate = DateUtils.addMonthsToTimestamp(startDate, 1);

			String startYearMonth = DateUtils.dateToString(startDate, "yyyyMM");
			String nextYearMonth = DateUtils.dateToString(nextDate, "yyyyMM");

			logger.debug("CheckAnomaliaDifettoProdottoFacade - difetto - startYearMonth: "
					+ startYearMonth);
			logger.debug("CheckAnomaliaDifettoProdottoFacade - difetto - endYearMonth: "
					+ endYearMonth);

			while (Integer.parseInt(nextYearMonth) <= Integer
					.parseInt(endYearMonth)) {
				insertDummyValueDifetto(dataEsecuzione, nextDate, startYearMonth, nextYearMonth);

				startDate = DateUtils.addMonthsToTimestamp(startDate, 1);
				nextDate = DateUtils.addMonthsToTimestamp(startDate, 1);

				startYearMonth = DateUtils.dateToString(startDate, "yyyyMM");
				nextYearMonth = DateUtils.dateToString(nextDate, "yyyyMM");
			}
		} else {
			logger.debug("CheckAnomaliaDifettoProdottoFacade - difetto - Nessun difetto");
		}


		// update FL_ULTIMA_SITUAZIONE = 0 su tutti i record Dummy (sia
		// anomalia che difetto)
		AnomaliaProdottoDummyDAO.resetUltimaVersione();

		// update FL_ULTIMA_SITUAZIONE = 1 su ultima versione anomaliaDummy
		AnomaliaProdottoDummyDAO.updateUltimaVersione(endYearMonth);

		// update FL_ULTIMA_SITUAZIONE = 1 su ultima versione difettoDummy
		DifettoProdottoDummyDAO.updateUltimaVersione(endYearMonth);

		// update FL_ULTIMA_SITUAZIONE = 0 su anomalia se ultima versione
		// presente su Dummy
//		AnomaliaProdottoDAO.updateUltimaVersione(endYearMonth);

		// update FL_ULTIMA_SITUAZIONE = 0 su difetto se ultima versione
		// presente su Dummy
//		DifettoDAO.updateUltimaVersione(endYearMonth);
	}
	
	private static void insertDummyValueAnomalia(Timestamp dataEsecuzione,
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
					DmAlmConfigReaderProperties.SQL_ANOMALIA_PRODOTTO_DUMMY);

			ps = connection.prepareStatement(sql);

			// DT_CARICAMENTO_RECORD_ANOMALIA
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
				logger.debug("AnomaliaProdottoDummyDAO.insertDummyValue - yearMonth: "
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
	
	private static void insertDummyValueDifetto(Timestamp dataEsecuzione,
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

			// DT_CARICAMENTO_RECORD_ANOMALIA
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
				logger.debug("AnomaliaProdottoDummyDAO.insertDummyValue - yearMonth: "
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
}