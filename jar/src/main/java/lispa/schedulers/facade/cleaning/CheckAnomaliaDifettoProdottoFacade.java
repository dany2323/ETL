package lispa.schedulers.facade.cleaning;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto;
import lispa.schedulers.dao.target.fatti.AnomaliaProdottoDAO;
import lispa.schedulers.dao.target.fatti.AnomaliaProdottoDummyDAO;
import lispa.schedulers.dao.target.fatti.DifettoDAO;
import lispa.schedulers.dao.target.fatti.DifettoProdottoDummyDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

/***
 * elaborazione post Target per anomaliaProdotto e difettoProdotto viene
 * aggiunta una riga mensile nella tabella dummy nel caso il wi non sia stato
 * movimentato nel mese
 */
public class CheckAnomaliaDifettoProdottoFacade {
	private static Logger logger = Logger
			.getLogger(CheckAnomaliaDifettoProdottoFacade.class);

	private static QDmalmAnomaliaProdotto anomaliaProdotto = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
	private static QDmalmAnomaliaAssistenza anomaliaAssistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;

	public static void execute() {
		try {
			// ELETTRA/SGRCM
			if (!ExecutionManager.getInstance().isExecutionElettraSgrcm())
				return;

			// Se si è verificato un errore precedente non eseguo l'elaborazione
			if (ErrorManager.getInstance().hasError()) {
				return;
			}

			logger.info("START CheckAnomaliaDifettoProdottoFacade.execute()");

			// DM_ALM-142 - gestione idAnomaliaAssistenza
			gestioneIdAnomaliaAssistenza();

			if (ErrorManager.getInstance().hasError()) {
				return;
			}
			
			// DM_ALM-69 - gestione tabelle DUMMY
			gestioneTabelleDummy();

			logger.info("STOP CheckAnomaliaDifettoProdottoFacade.execute()");
		} catch (DAOException de) {
			logger.error(de.getMessage(), de);

			ErrorManager.getInstance().exceptionOccurred(true, de);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}

	private static void gestioneIdAnomaliaAssistenza() throws DAOException,
			SQLException {
		String anomaliaPrec = "";
		String idrepositoryPrec = "";
		String idAnomaliaAssistenzaPrec = "";
		String listaAnomaliaAssistenza = "";
		Timestamp dataAperturaTicketPrec = null;
		Timestamp dataCreazioneAnomaliaAss = null;

		List<Tuple> linkAnomaliaAssistenza = AnomaliaProdottoDAO
				.getLinkAnomaliaAssistenza();

		logger.debug("gestioneIdAnomaliaAssistenza - linkAnomaliaAssistenza.size: "
				+ linkAnomaliaAssistenza.size());

		if (linkAnomaliaAssistenza != null && linkAnomaliaAssistenza.size() > 0) {
			for (Tuple row : linkAnomaliaAssistenza) {
				if (row != null) {
					if (!row.get(anomaliaProdotto.cdAnomalia).equals(
							anomaliaPrec)) {
						if (!anomaliaPrec.equals("")) {
							elaboraIdAnomaliaAssistenza(anomaliaPrec,
									idrepositoryPrec, idAnomaliaAssistenzaPrec,
									listaAnomaliaAssistenza,
									dataAperturaTicketPrec,
									dataCreazioneAnomaliaAss);
						}

						listaAnomaliaAssistenza = "";
						if (row.get(anomaliaAssistenza.cdAnomaliaAss) != null)
							listaAnomaliaAssistenza = row
									.get(anomaliaAssistenza.cdAnomaliaAss)
									+ ";";

						dataAperturaTicketPrec = row
								.get(anomaliaProdotto.dtAperturaTicket);
						dataCreazioneAnomaliaAss = row
								.get(anomaliaAssistenza.dtCreazioneAnomaliaAss);
						idAnomaliaAssistenzaPrec = row
								.get(anomaliaProdotto.idAnomaliaAssistenza) == null ? ""
								: row.get(anomaliaProdotto.idAnomaliaAssistenza);
						anomaliaPrec = row.get(anomaliaProdotto.cdAnomalia);
						idrepositoryPrec = row
								.get(anomaliaProdotto.idRepository);
					} else {
						if (row.get(anomaliaAssistenza.cdAnomaliaAss) != null)
							listaAnomaliaAssistenza += row
									.get(anomaliaAssistenza.cdAnomaliaAss)
									+ ";";
					}
				}
			}

			if (!anomaliaPrec.equals("")) {
				elaboraIdAnomaliaAssistenza(anomaliaPrec, idrepositoryPrec,
						idAnomaliaAssistenzaPrec, listaAnomaliaAssistenza,
						dataAperturaTicketPrec, dataCreazioneAnomaliaAss);
			}
		}
	}

	private static void elaboraIdAnomaliaAssistenza(String anomaliaPrec,
			String idRepositoryPrec, String idAnomaliaAssistenzaPrec,
			String listaAnomaliaAssistenza, Timestamp dataAperturaTicketPrec,
			Timestamp dataCreazioneAnomaliaAss) throws DAOException {

		if (dataAperturaTicketPrec != null || dataCreazioneAnomaliaAss != null) {
			// toglie ultimo ";" dalla lista degli id
			if (listaAnomaliaAssistenza.length() > 0) {
				listaAnomaliaAssistenza = listaAnomaliaAssistenza.substring(0,
						listaAnomaliaAssistenza.length() - 1);
			}

			// se listaAnomaliaAssistenza è diversa dalla lista precedente o la
			// data creazione e diversa dalla precedente data apertura effettuo
			// l'update
			// viene effettuato un update e non una storicizzazione in quanto i
			// due valori non sono campi SCD
			// data creazione = MIN data apetura ticket per gli n ticket
			// associati
			if (!idAnomaliaAssistenzaPrec.equals(listaAnomaliaAssistenza)
					|| (dataAperturaTicketPrec != null && dataCreazioneAnomaliaAss == null)
					|| (dataAperturaTicketPrec == null && dataCreazioneAnomaliaAss != null)
					|| dataAperturaTicketPrec
							.compareTo(dataCreazioneAnomaliaAss) != 0) {
				DmalmAnomaliaProdotto anomalia = new DmalmAnomaliaProdotto();
				anomalia.setCdAnomalia(anomaliaPrec);
				anomalia.setIdRepository(idRepositoryPrec);
				anomalia.setIdAnomaliaAssistenza(listaAnomaliaAssistenza);
				anomalia.setDtAperturaTicket(dataCreazioneAnomaliaAss);
				
				AnomaliaProdottoDAO.updateIdAnomaliaAssistenza(anomalia);
			}
		}
	}

	private static void gestioneTabelleDummy() throws DAOException, Exception {
		Timestamp dataEsecuzione = DataEsecuzione.getInstance()
				.getDataEsecuzione();

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
				AnomaliaProdottoDummyDAO.insertDummyValue(
						dataEsecuzione, nextDate, startYearMonth, nextYearMonth);

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
				DifettoProdottoDummyDAO.insertDummyValue(
						dataEsecuzione, nextDate, startYearMonth, nextYearMonth);

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
		AnomaliaProdottoDAO.updateUltimaVersione(endYearMonth);

		// update FL_ULTIMA_SITUAZIONE = 0 su difetto se ultima versione
		// presente su Dummy
		DifettoDAO.updateUltimaVersione(endYearMonth);
	}
}
