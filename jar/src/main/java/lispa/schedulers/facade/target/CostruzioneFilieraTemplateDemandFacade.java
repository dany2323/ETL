package lispa.schedulers.facade.target;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.FilieraTemplateDemandDAO;
import lispa.schedulers.dao.target.FilieraTemplateDocumentiDAO;
import lispa.schedulers.dao.target.LinkedWorkitemsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

/***
 * Elaborazione post Target: DM_ALM-191 Revisione universo
 * partendo dalla tabella LINKED_WORKITEMS tipologie di workitem da prendere in
 * considerazione per il template Demand: 
 * a. Programma (programma)
 * b. Sottoprogramma (sottoprogramma)
 * c. Progetto Demand (rqd)
 * d. Richiesta di Manutenzione (dman)
 * e. Progetto Ese (progettoese)
 * f. Progetto Sviluppo Demand (drqs)
 * g. Pei (pei)
 * h. fase (fase)
 * x. Documento (documento)
 */

public class CostruzioneFilieraTemplateDemandFacade {
	private static Logger logger = Logger
			.getLogger(CostruzioneFilieraTemplateDemandFacade.class);
	
	public static void execute() {
		try {
			// ELETTRA/SGRCM
			if (!ExecutionManager.getInstance().isExecutionElettraSgrcm())
				return;

			// Se si è verificato un errore precedente non eseguo l'elaborazione
			if (ErrorManager.getInstance().hasError()) {
				return;
			}

			logger.info("START CostruzioneFilieraTemplateDemandFacade.execute()");

			Integer idFiliera = 0;

			// data inizio filiera: primo Gennaio del 1900
			
			Timestamp dataInizioFiliera = DateUtils.setDtInizioValidita1900();

			logger.info("CostruzioneFilieraTemplateDemandFacade - dataInizioFiliera: " + dataInizioFiliera);
			
			List<DmalmLinkedWorkitems> insertedWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// ad ogni esecuzione la filiera del template Demand è svuotata e ricaricata
			// nuovamente
			FilieraTemplateDemandDAO.delete();
			FilieraTemplateDocumentiDAO.delete(DmAlmConstants.DEMAND);

			// creazione filiera template Demand
			List<DmalmLinkedWorkitems> startWorkitemsList = LinkedWorkitemsDAO
					.getStartWorkitemsTemplateDemand(dataInizioFiliera);
			logger.debug("CostruzioneFilieraTemplateDemandFacade - lista.size: "
					+ startWorkitemsList.size());

			if (startWorkitemsList.size() > 0) {
				idFiliera = gestisciLista(idFiliera, startWorkitemsList,
						insertedWorkitemsList);
			}
			
			logger.info("STOP CostruzioneFilieraTemplateDemandFacade.execute()");
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
						.getNextWorkitemsTemplateDemand(linkedWorkitem);
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
				// altrimenti se non ho figli inserisco la lista e il wi corrente
				// senza salvarlo nella lista in una nuova filiera
				idFiliera++;
				inserisciLista(idFiliera, insertedWorkitemsList,
						linkedWorkitem);
			}
		}

		return idFiliera;
	}
	
	private static Integer gestisciListaAddWiBuild(Integer idFiliera,
			List<DmalmLinkedWorkitems> linkedWorkitems,
			List<DmalmLinkedWorkitems> insertedWorkitemsList) throws Exception {

		for (DmalmLinkedWorkitems linkedWorkitem : linkedWorkitems) {
			List<DmalmLinkedWorkitems> nextWorkitemsList = new LinkedList<DmalmLinkedWorkitems>();

			// se il figlio è già presente nella catena non cerco ulteriori
			// figli per evitare cicli infiniti
			if (!figlioPresente(insertedWorkitemsList, linkedWorkitem)) {
				nextWorkitemsList = LinkedWorkitemsDAO
						.getNextWorkitemsAddBuildTemplate(linkedWorkitem);
			}

			if (nextWorkitemsList.size() > 0) {
				// se ho dei figli salvo il wi nella lista degli inseriti e
				// continuo il ciclo
				insertedWorkitemsList.add(linkedWorkitem);

				idFiliera = gestisciListaAddWiBuild(idFiliera, nextWorkitemsList,
						insertedWorkitemsList);

				// tolgo l'item dalla lista per non averlo in un ramo diverso
				// dal suo
				insertedWorkitemsList.remove(linkedWorkitem);
			} else {
				// altrimenti se non ho figli inserisco la lista e il wi corrente
				// senza salvarlo nella lista in una nuova filiera
				idFiliera++;
				inserisciLista(idFiliera, insertedWorkitemsList,
						linkedWorkitem);
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

			FilieraTemplateDemandDAO.insert(idFiliera, livello, sottoLivello,
					false, insertedWorkitem);

			tipoWiPrecedente = insertedWorkitem.getTipoWiPadre();
		}

		livello++;

		if (lastWorkitem.getTipoWiPadre().equalsIgnoreCase(tipoWiPrecedente))
			sottoLivello++;
		else
			sottoLivello = 1;

		FilieraTemplateDemandDAO.insert(idFiliera, livello, sottoLivello,
				true, lastWorkitem);
	}
}
