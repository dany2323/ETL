//package lispa.target;
//
//import java.util.Date;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.Timestamp;
//
//import org.apache.log4j.Logger;
//import junit.framework.TestCase;
//import lispa.schedulers.constant.DmAlmConstants;
//import lispa.schedulers.dao.target.fatti.AnomaliaProdottoDummyDAO;
//import lispa.schedulers.dao.target.fatti.DifettoProdottoDummyDAO;
//import lispa.schedulers.exception.DAOException;
//import lispa.schedulers.manager.ConnectionManager;
//import lispa.schedulers.manager.DmAlmConfigReaderProperties;
//import lispa.schedulers.manager.Log4JConfiguration;
//import lispa.schedulers.manager.QueryManager;
//import lispa.schedulers.utils.DateUtils;
//
//public class TestAnomaliaProdottoDummy extends TestCase {
//
//	private static Logger logger = Logger.getLogger(TestAnomaliaProdottoDummy.class);
//	
//	public void testAnomaliaProdottoDummy() throws Exception {
//		Log4JConfiguration.inizialize();
//		
//		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
//				"2019-04-03 20:40:00", "yyyy-MM-dd HH:mm:00");
//
//		Date endDate = dataEsecuzione;
//		String endYearMonth = DateUtils.dateToString(endDate, "yyyyMM");
//
//		// gestione anomalia prodotto
//		Timestamp startDate = AnomaliaProdottoDummyDAO.getStartDate();
//		if (startDate != null) {
//			startDate = DateUtils.getFistDayMonth(startDate);
//			Timestamp nextDate = DateUtils.addMonthsToTimestamp(startDate, 1);
//
//			String startYearMonth = DateUtils.dateToString(startDate, "yyyyMM");
//			String nextYearMonth = DateUtils.dateToString(nextDate, "yyyyMM");
//
//			logger.info("CheckAnomaliaDifettoProdottoFacade - anomalia - startYearMonth: "
//					+ startYearMonth);
//			logger.info("CheckAnomaliaDifettoProdottoFacade - anomalia - endYearMonth: "
//					+ endYearMonth);
//
//			while (Integer.parseInt(nextYearMonth) <= Integer
//					.parseInt(endYearMonth)) {
//				logger.info("dataEsecuzione: " +dataEsecuzione+ "- nextDate: "+nextDate+ "- startYearMonth: "+startYearMonth+" - nextYearMonth: "+nextYearMonth);
//				insertAnomaliaDummyValue(dataEsecuzione, nextDate, startYearMonth, nextYearMonth);
//
//				startDate = DateUtils.addMonthsToTimestamp(startDate, 1);
//				nextDate = DateUtils.addMonthsToTimestamp(startDate, 1);
//
//				startYearMonth = DateUtils.dateToString(startDate, "yyyyMM");
//				nextYearMonth = DateUtils.dateToString(nextDate, "yyyyMM");
//			}
//		}
//
////		// gestione difetto prodotto
////		startDate = DifettoProdottoDummyDAO.getStartDate();
////		if (startDate != null) {
////			startDate = DateUtils.getFistDayMonth(startDate);
////			Timestamp nextDate = DateUtils.addMonthsToTimestamp(startDate, 1);
////
////			String startYearMonth = DateUtils.dateToString(startDate, "yyyyMM");
////			String nextYearMonth = DateUtils.dateToString(nextDate, "yyyyMM");
////
////			logger.info("CheckAnomaliaDifettoProdottoFacade - difetto - startYearMonth: "
////					+ startYearMonth);
////			logger.info("CheckAnomaliaDifettoProdottoFacade - difetto - endYearMonth: "
////					+ endYearMonth);
////
//////			while (Integer.parseInt(nextYearMonth) <= Integer
//////					.parseInt(endYearMonth)) {
////				logger.info("dataEsecuzione: " +dataEsecuzione+ "- nextDate: "+nextDate+ "- startYearMonth: "+startYearMonth+" - nextYearMonth: "+nextYearMonth);
////				insertDifettoDummyValue(dataEsecuzione, nextDate, startYearMonth, nextYearMonth);
////
////				startDate = DateUtils.addMonthsToTimestamp(startDate, 1);
////				nextDate = DateUtils.addMonthsToTimestamp(startDate, 1);
////
////				startYearMonth = DateUtils.dateToString(startDate, "yyyyMM");
////				nextYearMonth = DateUtils.dateToString(nextDate, "yyyyMM");
//////			}
////		} else {
////			logger.info("CheckAnomaliaDifettoProdottoFacade - difetto - Nessun difetto");
////		}
//	}
//	
//	private static void insertAnomaliaDummyValue(Timestamp dataEsecuzione,
//			Timestamp dataPrimoGiornoMese, String startYearMonth,
//			String nextYearMonth) throws DAOException {
//		ConnectionManager cm = null;
//		Connection connection = null;
//		PreparedStatement ps = null;
//
//		try {
//			cm = ConnectionManager.getInstance();
//			connection = cm.getConnectionOracle();
//
//			String sql = QueryManager.getInstance().getQuery(
//					DmAlmConfigReaderProperties.TEST_SQL_ANOMALIA_PRODOTTO_DUMMY);
//
//			ps = connection.prepareStatement(sql);
//
//			// DT_CARICAMENTO_RECORD_ANOMALIA
//			ps.setTimestamp(1, dataEsecuzione);
//			// DT_STORICIZZAZIONE
//			ps.setTimestamp(2, dataPrimoGiornoMese);
//			// DT_OSSERVAZIONE
//			ps.setTimestamp(3, dataPrimoGiornoMese);
//			ps.setString(4, startYearMonth);
//			ps.setString(5, startYearMonth);
//			ps.setString(6, startYearMonth);
//			ps.setString(7, nextYearMonth);
//			ps.setString(8, nextYearMonth);
//			// DT_OSSERVAZIONE
//			ps.setTimestamp(9, dataPrimoGiornoMese);
//			ps.setString(10, startYearMonth);
//			ps.setString(11, startYearMonth);
//			ps.setString(12, startYearMonth);
//			ps.setString(13, nextYearMonth);
//			ps.setString(14, nextYearMonth);
//			
//			ResultSet res = ps.executeQuery();
//			while (res.next()) {
//				logger.info(res.getInt(1));
//			}
//			
////			while (res.next()) {
////				logger.info("AnomaliaProdottoDummyDAO.insertDummyValue - yearMonth: "
////						+ nextYearMonth + " - insertRow: " + "DMALM_ANM_PROD_DUMMY_PK: "+res.getInt("DMALM_ANM_PROD_DUMMY_PK")+" - DMALM_STRUTTURA_ORG_FK_01: "+res.getInt("DMALM_STRUTTURA_ORG_FK_01")+
////						" - DMALM_PROJECT_FK_02: "+res.getInt("DMALM_PROJECT_FK_02")+ " - DMALM_STATO_WORKITEM_FK_03: "+res.getInt("DMALM_STATO_WORKITEM_FK_03")+ 
////						" - TEMPO_PK: "+res.getInt("TEMPO_PK")+" - DMALM_AREA_TEMATICA_FK_05: "+res.getInt("DMALM_AREA_TEMATICA_FK_05")+
////						" - CD_ANOMALIA: "+res.getString("CD_ANOMALIA")+" - DT_CREAZIONE_ANOMALIA: "+res.getTimestamp("DT_CREAZIONE_ANOMALIA")+
////						" - DT_RISOLUZIONE_ANOMALIA: "+res.getTimestamp("DT_RISOLUZIONE_ANOMALIA")+" - DT_MODIFICA_RECORD_ANOMALIA: "+res.getTimestamp("DT_MODIFICA_RECORD_ANOMALIA")+
////						" - DT_CHIUSURA_ANOMALIA: "+res.getTimestamp("DT_CHIUSURA_ANOMALIA")+" - DT_CAMBIO_STATO_ANOMALIA: "+res.getTimestamp("DT_CAMBIO_STATO_ANOMALIA")+
////						" - DS_ANOMALIA: "+res.getString("DS_ANOMALIA")+" - MOTIVO_RISOLUZIONE_ANOMALIA: "+res.getString("MOTIVO_RISOLUZIONE_ANOMALIA")+
////						" - ID_AUTORE_ANOMALIA: "+res.getString("ID_AUTORE_ANOMALIA")+" - DS_AUTORE_ANOMALIA: "+res.getString("DS_AUTORE_ANOMALIA")+
////						" - NUMERO_TESTATA_ANOMALIA: "+res.getString("NUMERO_TESTATA_ANOMALIA")+" - NUMERO_LINEA_ANOMALIA: "+res.getString("NUMERO_LINEA_ANOMALIA")+
////						" - SEVERITY: "+res.getString("SEVERITY")+" - ID_ANOMALIA_ASSISTENZA: "+res.getString("ID_ANOMALIA_ASSISTENZA")+
////						" - TICKET_SIEBEL_ANOMALIA_ASS: "+res.getString("TICKET_SIEBEL_ANOMALIA_ASS")+" - DT_APERTURA_TICKET: "+res.getTimestamp("DT_APERTURA_TICKET")+
////						" - DT_CHIUSURA_TICKET: "+res.getTimestamp("DT_CHIUSURA_TICKET")+" - EFFORT_ANALISI: "+res.getString("EFFORT_ANALISI")+
////						" - EFFORT_COSTO_SVILUPPO: "+res.getString("EFFORT_COSTO_SVILUPPO")+" - TEMPO_TOT_RISOLUZIONE_ANOMALIA: "+res.getString("TEMPO_TOT_RISOLUZIONE_ANOMALIA")+
////						" - ID_REPOSITORY: "+res.getString("ID_REPOSITORY")+" - RANK_STATO_ANOMALIA_MESE: "+res.getString("RANK_STATO_ANOMALIA_MESE")+
////						" - STG_PK: "+res.getString("STG_PK")+" - ANNULLATO: "+res.getString("ANNULLATO")+
////						" - DESCRIZIONE_ANOMALIA: "+res.getString("DESCRIZIONE_ANOMALIA")+" - DMALM_USER_FK_06: "+res.getInt("DMALM_USER_FK_06")+
////						" - URI_ANOMALIA_PRODOTTO: "+res.getString("URI_ANOMALIA_PRODOTTO")+" - CHANGED: "+res.getString("CHANGED"));
////			}
//
//			if (ps != null) {
//				ps.close();
//			}
//		} catch (Exception e) {
//			throw new DAOException(e);
//
//		} finally {
//			if (cm != null) {
//				cm.closeConnection(connection);
//			}
//		}
//	}
//	
//	private static void insertDifettoDummyValue(Timestamp dataEsecuzione,
//			Timestamp dataPrimoGiornoMese, String startYearMonth,
//			String nextYearMonth) throws DAOException {
//		ConnectionManager cm = null;
//		Connection connection = null;
//		PreparedStatement ps = null;
//
//		try {
//			cm = ConnectionManager.getInstance();
//			connection = cm.getConnectionOracle();
//
//			String sql = QueryManager.getInstance().getQuery(
//					DmAlmConfigReaderProperties.TEST_SQL_DIFETTO_PRODOTTO_DUMMY);
//
//			ps = connection.prepareStatement(sql);
//
//			// DT_CARICAMENTO_RECORD_ANOMALIA
//			ps.setTimestamp(1, dataEsecuzione);
//			// DT_STORICIZZAZIONE
//			ps.setTimestamp(2, dataPrimoGiornoMese);
//			// DT_OSSERVAZIONE
//			ps.setTimestamp(3, dataPrimoGiornoMese);
//			ps.setString(4, startYearMonth);
//			ps.setString(5, startYearMonth);
//			ps.setString(6, startYearMonth);
//			ps.setString(7, nextYearMonth);
//			ps.setString(8, nextYearMonth);
//			// DT_OSSERVAZIONE
//			ps.setTimestamp(9, dataPrimoGiornoMese);
//			ps.setString(10, startYearMonth);
//			ps.setString(11, startYearMonth);
//			ps.setString(12, startYearMonth);
//			ps.setString(13, nextYearMonth);
//			ps.setString(14, nextYearMonth);
//
//			ResultSet res = ps.executeQuery();
//
//			while (res.next()) {
//				logger.info("DifettoProdottoDummyDAO.insertDummyValue - yearMonth: "
//						+ nextYearMonth + " - insertRow: " + res.getInt("DMALM_ANM_PROD_DUMMY_PK"));
//			}
//
//			if (ps != null) {
//				ps.close();
//			}
//		} catch (Exception e) {
//			throw new DAOException(e);
//
//		} finally {
//			if (cm != null) {
//				cm.closeConnection(connection);
//			}
//		}
//	}
//}