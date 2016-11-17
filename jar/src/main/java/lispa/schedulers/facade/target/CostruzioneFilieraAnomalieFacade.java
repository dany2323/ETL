package lispa.schedulers.facade.target;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.dao.target.FilieraAnomalieDAO;
import lispa.schedulers.dao.target.LinkedWorkitemsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

/***
 * elaborazione post Target: DM_ALM-149 Costruzione della filiera delle Anomalie
 * partendo dalla tabella LINKED_WORKITEMS tipologie di workitem da prendere in
 * considerazione: Anomalia assistenza (anomalia_assistenza), Anomalia Prodotto
 * (anomalia), Manutenzione (sman), Progetto Sviluppo Sviluppo (srqs), Release
 * (release), Release IT (release_it), Release Ser (release_ser)
 */

public class CostruzioneFilieraAnomalieFacade {
	private static Logger logger = Logger
			.getLogger(CostruzioneFilieraAnomalieFacade.class);

	public static void execute() {
		try {
			// ELETTRA/SGRCM
			if (!ExecutionManager.getInstance().isExecutionElettraSgrcm())
				return;

			// Se si è verificato un errore precedente non eseguo l'elaborazione
			if (ErrorManager.getInstance().hasError()) {
				return;
			}

			logger.info("START CostruzioneFilieraAnomalieFacade.execute()");

			Integer idFiliera = 0;

			// data inizio filiera: primo Gennaio dell'Anno Corrente -2
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			String annoEsecuzione = DateUtils.dateToString(dataEsecuzione,
					"yyyy-MM-dd HH:mm:ss").substring(0, 4);
			Integer anno = new Integer(annoEsecuzione) - 2;
			Timestamp dataInizioFiliera = DateUtils.stringToTimestamp(
					anno.toString() + "-01-01 00:00:00", "yyyy-MM-dd HH:mm:ss");

			logger.info("CostruzioneFilieraAnomalieFacade - dataInizioFiliera: "
					+ dataInizioFiliera);

			List<DmalmLinkedWorkitems> insertedWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// ad ogni esecuzione la filiera è svuotata e ricaricata nuovamente
			FilieraAnomalieDAO.delete();

			// creazione filiera
			List<DmalmLinkedWorkitems> startWorkitemsList = LinkedWorkitemsDAO
					.getStartWorkitemsAnomalie(dataInizioFiliera);
			logger.debug("CostruzioneFilieraAnomalieFacade - listaAnomalie.size: "
					+ startWorkitemsList.size());

			if (startWorkitemsList.size() > 0) {
				idFiliera = gestisciLista(idFiliera, startWorkitemsList,
						insertedWorkitemsList);
			}

			// creazione filiera corta
			startWorkitemsList = LinkedWorkitemsDAO
					.getStartWorkitemsAnomalieCorta(dataInizioFiliera);
			logger.debug("CostruzioneFilieraAnomalieFacade - listaAnomalieCorta.size: "
					+ startWorkitemsList.size());

			if (startWorkitemsList.size() > 0) {
				idFiliera = gestisciLista(idFiliera, startWorkitemsList,
						insertedWorkitemsList);
			}
			
			// creazione filiera per Anomalie non in filiera
			List<Tuple> noLinkedList = LinkedWorkitemsDAO
					.getNoLinkedAnomalieWorkitems(dataInizioFiliera);
			logger.debug("CostruzioneFilieraAnomalieFacade - listaAnomalieNonInFiliera.size: "
					+ noLinkedList.size());

			inserisciListaNoLinked(idFiliera, noLinkedList);

			logger.info("STOP CostruzioneFilieraAnomalieFacade.execute()");
		} catch (DAOException de) {
			logger.error(de.getMessage(), de);

			//ErrorManager.getInstance().exceptionOccurred(true, de);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			//ErrorManager.getInstance().exceptionOccurred(true, e);
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
						.getNextWorkitemsAnomalie(linkedWorkitem);
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
				// se non ho figli per "anomalia", "sman" o "srqs" verifico se
				// posso
				// risalire verso release
				if (linkedWorkitem.getTipoWiFiglio().equalsIgnoreCase(
						"anomalia")
						|| linkedWorkitem.getTipoWiFiglio().equalsIgnoreCase(
								"sman")
						|| linkedWorkitem.getTipoWiFiglio().equalsIgnoreCase(
								"srqs")) {
					List<DmalmLinkedWorkitems> startWorkitemsAscendingList = LinkedWorkitemsDAO
							.getStartWorkitemsAscendingAnomalie(linkedWorkitem);

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

		for (DmalmLinkedWorkitems insertedWorkitem : insertedWorkitemsList) {
			livello++;

			if (insertedWorkitem.getTipoWiPadre().equalsIgnoreCase(
					tipoWiPrecedente))
				sottoLivello++;
			else
				sottoLivello = 1;

			FilieraAnomalieDAO.insert(idFiliera, livello, sottoLivello,
					false, insertedWorkitem);

			tipoWiPrecedente = insertedWorkitem.getTipoWiPadre();
		}

		livello++;

		if (lastWorkitem.getTipoWiPadre().equalsIgnoreCase(tipoWiPrecedente))
			sottoLivello++;
		else
			sottoLivello = 1;

		FilieraAnomalieDAO.insert(idFiliera, livello, sottoLivello,
				true, lastWorkitem);
	}

	private static void inserisciListaNoLinked(Integer idFiliera,
			List<Tuple> insertedWorkitemsList) throws Exception {

		Integer livello = 1;
		Integer sottoLivello = 1;

		for (Tuple insertedWorkitem : insertedWorkitemsList) {
			idFiliera++;
			FilieraAnomalieDAO.insertNoLinked(idFiliera, livello, sottoLivello,
					insertedWorkitem);
		}
	}
}
