package lispa.schedulers.facade.target;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.bean.target.fatti.DmalmProgettoDemand;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.target.FilieraProduttivaDAO;
import lispa.schedulers.dao.target.LinkedWorkitemsDAO;
import lispa.schedulers.dao.target.fatti.ProgettoDemandDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

/***
 * elaborazione post Target: Report KPI DM_ALM-76. Costruzione della filiera
 * produttiva partendo dalla tabella LINKED_WORKITEMS tipologie di workitem da
 * prendere in considerazione: Programma (programma), Sottoprogramma
 * (sottoprogramma), Progetto Demand (rqd), Richiesta di Manutenzione (dman),
 * Manutenzione (sman), Progetto Sviluppo Demand (drqs), Progetto Sviluppo
 * Sviluppo (srqs), Release (release), Release IT (release_it), Release Ser
 * (release_ser)
 */

public class CostruzioneFilieraProduttivaFacade {
	private static Logger logger = Logger
			.getLogger(CostruzioneFilieraProduttivaFacade.class);

	public static void execute() {
		try {
			// ELETTRA/SGRCM
			if (!ExecutionManager.getInstance().isExecutionElettraSgrcm())
				return;

			// Se si è verificato un errore precedente non eseguo l'elaborazione
			if (ErrorManager.getInstance().hasError()) {
				return;
			}

			logger.info("START CostruzioneFilieraProduttivaFacade.execute()");

			Integer idFiliera = 0;

			// data inizio filiera: primo Gennaio dell'Anno Corrente -2
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			String annoEsecuzione = DateUtils.dateToString(dataEsecuzione,
					"yyyy-MM-dd HH:mm:ss").substring(0, 4);
			Integer anno = new Integer(annoEsecuzione) - 2;
			Timestamp dataInizioFiliera = DateUtils.stringToTimestamp(
					anno.toString() + "-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");

			logger.info("CostruzioneFilieraProduttivaFacade - dataInizioFiliera: " + dataInizioFiliera);
			
			List<DmalmLinkedWorkitems> insertedWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// ad ogni esecuzione la filiera produttiva è svuotata e ricaricata
			// nuovamente
			FilieraProduttivaDAO.delete();

			// creazione filiera
			List<DmalmLinkedWorkitems> startWorkitemsList = LinkedWorkitemsDAO
					.getStartWorkitems(dataInizioFiliera);
			logger.debug("CostruzioneFilieraProduttivaFacade - listaProgrammi.size: "
					+ startWorkitemsList.size());

			if (startWorkitemsList.size() > 0) {
				idFiliera = gestisciLista(idFiliera, startWorkitemsList,
						insertedWorkitemsList);
			}

			// creazione filiera per Programmi non in filiera
			List<DmalmLinkedWorkitems> noLinkedList = LinkedWorkitemsDAO
					.getNoLinkedProgramWorkitems(dataInizioFiliera);
			logger.debug("CostruzioneFilieraProduttivaFacade - listaProgrammiNonInFiliera.size: "
					+ noLinkedList.size());

			inserisciListaNoLinked(idFiliera, noLinkedList);

			// gestione segnalazioni eccezioni
			List<DmalmProgettoDemand> progettiDemand = ProgettoDemandDAO
					.getProgettoDemandClassificazioneSviluppoNoFiliera(dataInizioFiliera);
			if (progettiDemand != null && progettiDemand.size() > 0) {
				for (DmalmProgettoDemand progetto : progettiDemand) {
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.TARGET_LINKEDWORKITEMS,
									DmAlmConstants.TARGET_FILIERA_PRODUTTIVA,
									"codiceProgettoDemand: "
											+ progetto.getCdProgettoDemand()
											+ "§ idRepository: "
											+ progetto.getIdRepository(),
									DmAlmConstants.ECCEZIONE_FILIERA_RQD_SVILUPPO_NON_UTILIZZATO,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			logger.info("STOP CostruzioneFilieraProduttivaFacade.execute()");
		} catch (DAOException de) {
			logger.error(de.getMessage(), de);

			ErrorManager.getInstance().exceptionOccurred(true, de);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}

	private static Integer gestisciLista(Integer idFiliera,
			List<DmalmLinkedWorkitems> linkedWorkitems,
			List<DmalmLinkedWorkitems> insertedWorkitemsList) throws Exception {

		for (DmalmLinkedWorkitems linkedWorkitem : linkedWorkitems) {
			List<DmalmLinkedWorkitems> nextWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// se il figlio è già presente nella catena non cerco ulteriori
			// figli per evitare cicli infiniti
			if (!figlioPresente(insertedWorkitemsList, linkedWorkitem)) {
				nextWorkitemsList = LinkedWorkitemsDAO
						.getNextWorkitems(linkedWorkitem);
			}

			if (nextWorkitemsList.size() > 0) {
				// se ho dei figli salvo il wi nella lista degli inseriti e
				// continuo il ciclo
				insertedWorkitemsList.add(linkedWorkitem);

				idFiliera = gestisciLista(idFiliera, nextWorkitemsList,
						insertedWorkitemsList);

				// tolgo l'item dalla lista per non averlo in un ramo diverso
				// dal suo
				insertedWorkitemsList.remove(linkedWorkitem);
			} else {
				// se non ho figli per "srqs", "drqs" o "sman" verifico se posso
				// risalire verso release
				if (linkedWorkitem.getTipoWiFiglio().equalsIgnoreCase("srqs")
						|| linkedWorkitem.getTipoWiFiglio().equalsIgnoreCase(
								"drqs")
						|| linkedWorkitem.getTipoWiFiglio().equalsIgnoreCase(
								"sman")) {
					List<DmalmLinkedWorkitems> startWorkitemsAscendingList = LinkedWorkitemsDAO
							.getStartWorkitemsAscending(linkedWorkitem);

					if (startWorkitemsAscendingList.size() > 0) {
						// se ho dei figli salvo il wi nella lista degli
						// inseriti e continuo il ciclo
						insertedWorkitemsList.add(linkedWorkitem);

						idFiliera = gestisciListaAscendente(idFiliera,
								startWorkitemsAscendingList,
								insertedWorkitemsList);

						// tolgo l'item dalla lista per non averlo in un ramo
						// diverso dal suo
						insertedWorkitemsList.remove(linkedWorkitem);
					} else {
						// se non ho figli inserisco la lista e il wi corrente
						// senza salvarlo nella lista in una nuova filiera
						idFiliera++;
						inserisciLista(idFiliera, insertedWorkitemsList,
								linkedWorkitem);
					}
				} else {
					// altrimenti se non ho figli e non è un wi di tipo "srqs" e
					// "drqs" inserisco la lista e il wi corrente senza salvarlo
					// nella lista in una nuova filiera
					idFiliera++;
					inserisciLista(idFiliera, insertedWorkitemsList,
							linkedWorkitem);
				}
			}
		}

		return idFiliera;
	}

	private static boolean figlioPresente(
			List<DmalmLinkedWorkitems> insertedWorkitemsList,
			DmalmLinkedWorkitems linkedWorkitem) throws Exception {
		boolean presente = false;

		for (DmalmLinkedWorkitems insertedWorkitem : insertedWorkitemsList) {
			if (linkedWorkitem.getFkWiFiglio().equals(
					insertedWorkitem.getFkWiFiglio())
					|| linkedWorkitem.getFkWiFiglio().equals(
							insertedWorkitem.getFkWiPadre())) {

				presente = true;
				break;
			}
		}

		return presente;
	}

	private static Integer gestisciListaAscendente(Integer idFiliera,
			List<DmalmLinkedWorkitems> linkedWorkitems,
			List<DmalmLinkedWorkitems> insertedWorkitemsList) throws Exception {

		for (DmalmLinkedWorkitems linkedWorkitem : linkedWorkitems) {
			List<DmalmLinkedWorkitems> nextWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// se il figlio è già presente nella catena non cerco ulteriori
			// figli per evitare cicli infiniti
			if (!figlioPresente(insertedWorkitemsList, linkedWorkitem)) {
				nextWorkitemsList = LinkedWorkitemsDAO
						.getNextWorkitemsAscending(linkedWorkitem);
			}

			if (nextWorkitemsList.size() > 0) {
				// se ho dei padri salvo il wi nella lista degli inseriti e
				// continuo il ciclo
				insertedWorkitemsList.add(linkedWorkitem);

				idFiliera = gestisciListaAscendente(idFiliera,
						nextWorkitemsList, insertedWorkitemsList);

				// tolgo l'item dalla lista per non averlo in un ramo diverso
				// dal suo
				insertedWorkitemsList.remove(linkedWorkitem);
			} else {
				// se non ho padri inserisco la lista e il wi corrente senza
				// salvarlo nella lista in una nuova filiera
				idFiliera++;
				inserisciLista(idFiliera, insertedWorkitemsList, linkedWorkitem);
			}
		}

		return idFiliera;
	}

	private static void inserisciLista(Integer idFiliera,
			List<DmalmLinkedWorkitems> insertedWorkitemsList,
			DmalmLinkedWorkitems lastWorkitem) throws Exception {

		Integer livello = 0;
		Integer sottoLivello = 1;
		String tipoWiPrecedente = "";
		DmalmLinkedWorkitems firstWorkitem;

		if (insertedWorkitemsList.size() > 0) {
			firstWorkitem = insertedWorkitemsList.get(0);
		} else {
			firstWorkitem = lastWorkitem;
		}

		for (DmalmLinkedWorkitems insertedWorkitem : insertedWorkitemsList) {
			livello++;

			if (insertedWorkitem.getTipoWiFiglio().equalsIgnoreCase(
					tipoWiPrecedente))
				sottoLivello++;
			else
				sottoLivello = 1;

			FilieraProduttivaDAO.insert(idFiliera, livello, sottoLivello,
					firstWorkitem, insertedWorkitem);

			tipoWiPrecedente = insertedWorkitem.getTipoWiFiglio();
		}

		livello++;

		if (lastWorkitem.getTipoWiFiglio().equalsIgnoreCase(tipoWiPrecedente))
			sottoLivello++;
		else
			sottoLivello = 1;

		FilieraProduttivaDAO.insert(idFiliera, livello, sottoLivello,
				firstWorkitem, lastWorkitem);
	}

	private static void inserisciListaNoLinked(Integer idFiliera,
			List<DmalmLinkedWorkitems> insertedWorkitemsList) throws Exception {

		Integer livello = 1;
		Integer sottoLivello = 1;

		for (DmalmLinkedWorkitems insertedWorkitem : insertedWorkitemsList) {
			idFiliera++;
			FilieraProduttivaDAO.insertNoLinked(idFiliera, livello,
					sottoLivello, insertedWorkitem);
		}
	}
}
