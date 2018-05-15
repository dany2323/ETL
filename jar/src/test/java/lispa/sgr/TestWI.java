package lispa.sgr;

import static lispa.schedulers.constant.DmAlmConstants.DEFAULT_DAY_DELETE;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_RETRY;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import junit.framework.TestCase;
import lispa.schedulers.action.DmAlmETL;
import lispa.schedulers.action.DmAlmFillStaging;
import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.target.DifettoProdottoOdsDAO;
import lispa.schedulers.dao.target.FilieraTemplateDocumentiDAO;
import lispa.schedulers.dao.target.FilieraTemplateSviluppoDAO;
import lispa.schedulers.dao.target.LinkedWorkitemsDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.fatti.DifettoDAO;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaProjectElFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateIntTecnicaFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateSviluppoFacade;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.facade.target.fatti.RichiestaSupportoFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDifettoProdotto;
import lispa.schedulers.runnable.staging.SGRCMSireRunnable;
import lispa.schedulers.runnable.staging.SGRCMSissRunnable;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.EnumUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

public class TestWI extends TestCase {

	private static Logger logger = Logger.getLogger(DmAlmETL.class);
	private int retry;
	private int wait;
	private Map<Workitem_Type, Long> minRevisionsByType;

	public void testProvenienzaDifetto(){
		try {
			Log4JConfiguration.inizialize();
			

			CostruzioneFilieraTemplateIntTecnicaFacade.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
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
	private static void inserisciLista(Integer idFiliera,
			List<DmalmLinkedWorkitems> insertedWorkitemsList,
			DmalmLinkedWorkitems lastWorkitem) throws Exception {

		Integer livello = 0;
		Integer sottoLivello = 1;
		String tipoWiPrecedente = "";

		for (DmalmLinkedWorkitems insertedWorkitem : insertedWorkitemsList) {
			if (insertedWorkitem.getTipoWiPadre().equalsIgnoreCase(
					tipoWiPrecedente))
				sottoLivello++;
			else {
				livello++;
				sottoLivello = 1;
			}
			
			FilieraTemplateSviluppoDAO.insert(idFiliera, livello, sottoLivello,
					false, insertedWorkitem);

			tipoWiPrecedente = insertedWorkitem.getTipoWiPadre();
		}

		livello++;

		if (lastWorkitem.getTipoWiPadre().equalsIgnoreCase(tipoWiPrecedente))
			sottoLivello++;
		else
			sottoLivello = 1;

		
		FilieraTemplateSviluppoDAO.insert(idFiliera, livello, sottoLivello,
				true, lastWorkitem);
	}


	private void loadWiAndCustomFieldInStaging(String typeWi,long minRev, long maxRev) throws Exception {
		Map<Workitem_Type, Long> minRevisionsByType = SissHistoryWorkitemDAO
				.getMinRevisionByType();
		// Drop degli indici prima dell'elaborazione di HISTORY_WORKITEM e
		// HISTORY_CF_WORKITEM
		logger.debug("START DROP SISS INDEXES");
		//SissHistoryWorkitemDAO.dropIndexes();
		logger.debug("STOP DROP SISS INDEXES");

		retry = Integer.parseInt(DmAlmConfigReader.getInstance()
				.getProperty(DMALM_DEADLOCK_RETRY));
		wait = Integer.parseInt(DmAlmConfigReader.getInstance()
				.getProperty(DMALM_DEADLOCK_WAIT));

		logger.debug("START SissHistoryWorkitem - numero wi: "
				+ Workitem_Type.values().length);

		Workitem_Type type = null;
		for (Workitem_Type type2 : Workitem_Type.values()) {
			if(type2.toString().equals(typeWi)){
				type = type2;
			}
		}

		logger.debug("START TYPE: SISS " + type.toString());
		int tentativi = 0;
		ErrorManager.getInstance().resetDeadlock();
		ErrorManager.getInstance().resetCFDeadlock();
		boolean inDeadLock = false;
		boolean cfDeadlock = false;

		tentativi++;
		logger.debug("Tentativo " + tentativi);
		
		for(long i=minRevisionsByType.get(type);i<maxRev;i=i+10000)
		{
			minRevisionsByType.put(type, i);
			long j=i+10000;
			logger.info("Carico da rev "+i+" a "+j);
			SissHistoryWorkitemDAO.fillSissHistoryWorkitem(
					minRevisionsByType, j, type);
			inDeadLock = ErrorManager.getInstance().hasDeadLock();
		
//			if (!inDeadLock) {
//				List<String> customFields = EnumUtils
//						.getCFEnumerationByType(type);
//	
//				SissHistoryCfWorkitemDAO
//				.fillSissHistoryCfWorkitemByWorkitemType(
//						minRevisionsByType.get(type),
//						j, type, customFields);
//				cfDeadlock = ErrorManager.getInstance().hascfDeadLock();
//			}
//	
//			logger.debug("Fine tentativo " + tentativi + " - WI deadlock "
//					+ inDeadLock + " - CF deadlock " + cfDeadlock);
//	
//			if (inDeadLock || cfDeadlock) {
//				while (inDeadLock || cfDeadlock) {
//	
//					tentativi++;
//	
//					if (tentativi > retry) {
//						logger.debug("Raggiunto limite tentativi: "
//								+ tentativi);
//						Exception e = new Exception("Deadlock detected");
//						ErrorManager.getInstance().exceptionOccurred(true,
//								e);
//						return;
//					}
//	
//					logger.debug("Errore, aspetto 3 minuti");
//					logger.debug("Tentativo " + tentativi);
//					TimeUnit.MINUTES.sleep(wait);
//	
//					if (inDeadLock) {
//						SissHistoryWorkitemDAO.fillSissHistoryWorkitem(
//								minRevisionsByType, j, type);
//						inDeadLock = ErrorManager.getInstance()
//								.hasDeadLock();
//						if (!inDeadLock) {
//							logger.debug("Non in deadlock -> provo i CF");
//							List<String> customFields = EnumUtils
//									.getCFEnumerationByType(type);
//	
//							SissHistoryCfWorkitemDAO
//							.fillSissHistoryCfWorkitemByWorkitemType(
//									minRevisionsByType.get(type),
//									j, type,
//									customFields);
//							cfDeadlock = ErrorManager.getInstance()
//									.hascfDeadLock();
//							logger.debug("I CF sono in deadlock "
//									+ cfDeadlock);
//						}
//					} else {
//						if (cfDeadlock) {
//							logger.debug("Scarico soltanto i CF");
//	
//							List<String> customFields = EnumUtils
//									.getCFEnumerationByType(type);
//	
//							SissHistoryCfWorkitemDAO
//							.fillSissHistoryCfWorkitemByWorkitemType(
//									minRevisionsByType
//									.get(type),
//									j, type,
//									customFields);
//	
//							cfDeadlock = ErrorManager.getInstance()
//									.hascfDeadLock();
//	
//							logger.debug("I CF sono in deadlock "
//									+ cfDeadlock);
//						}
//					}
//				}
//			}
		}
		logger.debug("START delete not matching CFs SISS");
		SissHistoryCfWorkitemDAO.deleteNotMatchingCFS();
		logger.debug("STOP delete not matching CFs SISS");

		logger.debug("START Update CF SISS");
		SissHistoryCfWorkitemDAO.updateCFonWorkItem();
		logger.debug("STOP Update CF SISS");

		// Rebuild degli indici dopo l'elaborazione di HISTORY_WORKITEM e
		// HISTORY_CF_WORKITEM
		logger.debug("START REBUILD SISS INDEXES");
		SissHistoryWorkitemDAO.rebuildIndexes();
		logger.debug("STOP REBUILD SISS INDEXES");

		ConnectionManager.getInstance().dismiss();
	}

}
