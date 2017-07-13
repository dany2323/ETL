package lispa.schedulers.facade.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_RETRY;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.UserRolesDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectGroupDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryUserDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemLinkedDAO;
import lispa.schedulers.dao.sgr.siss.history.VSissHistoryWorkitemLinkDAO;
import lispa.schedulers.dao.target.SchedeServizioDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem;
import lispa.schedulers.runnable.staging.sire.current.SissSchedeServizioRunnable;
import lispa.schedulers.runnable.staging.siss.current.SissUserRolesRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryAttachmentRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryHyperlinkRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryProjectGroupRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryProjectRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryRevisionRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryUserRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryWorkitemUserAssignedRunnable;
import lispa.schedulers.utils.EnumUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

/**
 * In particolare, il popolamento delle tabelle di staging di SGR_CM avviene
 * come segue: - Tabella Workitem: si recupera il numero massimo di revisione
 * (x) presente nella tabella al momento dell’esecuzione, e la si riempie con i
 * record che alla fonte presentano una revisione maggiore di x e che sono stati
 * inseriti in Polarion da almeno un’ora (per ragione di integrità dei dati). -
 * Tabella CFWorkitem: si inseriscono esclusivamente i Custom Fields relativi ai
 * Workitems inseriti nel passo precedente. - Tabella User: si recupera il
 * numero massimo di revisione (x) presente nella tabella al momento
 * dell’esecuzione, e la si riempie con i record che alla fonte presentano una
 * revisione maggiore di x e che sono stati inseriti in Polarion da almeno
 * un’ora (per ragione di integrità dei dati). - Tabella Project: si recupera il
 * numero massimo di revisione (x) presente nella tabella al momento
 * dell’esecuzione, e la si riempie con i record che alla fonte presentano una
 * revisione maggiore di x e che sono stati inseriti in Polarion da almeno
 * un’ora (per ragione di integrità dei dati). Il Template del project viene
 * recuperato accedendo alla relativa repository SVN e analizzando il contenuto
 * del file “template-verison.ini” presente al path riportato nella colonna
 * C_LOCATION. In il template è il valore assunto dal campo “template-id”
 * riportato nel file succitato. Alla tabella Project è stata aggiunta la
 * colonna c_updated assente nella tabella fonte. - Tabella ProjectGroup: per
 * questa tabella si procede in maniera differente. Visto che alla fonte ogni
 * ProjectGroup presenta un numero di revisione = 0, si provvede per ogni
 * esecuzione a cancellare e a ricaricare completamente tale tabella. - Tabella
 * WorkitemLinked: si inseriscono esclusivamente i Link relativi ai Workitems
 * inseriti nel primo passo. - Tabella WorkitemUserAssignee: si inseriscono
 * esclusivamente gli UserAssignee relativi ai workitem inseriti nel primo
 * passo. - Tabella Attachment: si inseriscono esclusivamente gli Attachment
 * relativi ai workitem inseriti nel primo passo - Tabella Hyperlink: si
 * inseriscono esclusivamente gli Hyperlink relativi ai workitem inseriti nel
 * primo passo.
 * 
 * In alcuni casi specifici non viene effettuata la copia totale dei dati nelle
 * fonti, ma vengono applicati dei filtri che consentono di inserire nell’area
 * di staging solo i record che effettivamente sono necessari. Per esempio
 * vengono considerati i soli Workitem che hanno il campo C_TYPE uguale a
 * 'documento', 'testcase', 'pei', 'build', 'progettoese', ‘fase', 'defect',
 * 'release', 'sottoprogramma', ‘programma', 'taskit', 'anomalia',
 * 'anomalia_assistenza', 'release_it', 'sman', 'release_ser', 'drqs', 'dman',
 * 'rqd', 'richiesta_gestione', 'srqs', 'task.‘anomalia’, ’anomalia_assistenza’
 * o ’defect’ ed i soli Project il cui campo C_LOCATION inizia con
 * ‘default:/Sviluppo/’. Anche il popolamento dei Custom fields avviene in base
 * ad un filtro il quale tiene conto solamente di quelli di interesse a seconda
 * del tipo di Workitem.
 * 
 * @author fdeangel
 * 
 */
public class FillSISSHistoryFacade {

	static SissHistoryWorkitem fonteWorkItems = SissHistoryWorkitem.workitem;

	private static int retry;

	private static int wait;

	public static void execute(Logger logger) {
		try {
			logger.debug("START fillSissHistory  " + new Date());
			fillSissHistory(logger);
			logger.debug("STOP fillSissHistory  " + new Date());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}

	public static void delete(Logger logger, Timestamp dataEsecuzione) {

		try {
			logger.debug("START SissHistoryProjectGroup.delete  " + new Date());
			SissHistoryProjectGroupDAO.delete();
			logger.debug("STOP SissHistoryProjectGroup.delete  " + new Date());

			logger.debug("START SissHistoryLinkedWorkitems.delete  "
					+ new Date());
			SissHistoryWorkitemLinkedDAO.delete();
			logger.debug("STOP SissHistoryLinkedWorkitems.delete  "
					+ new Date());

			logger.debug("START VSissWorkitemDAOLink.delete  " + new Date());
			VSissHistoryWorkitemLinkDAO.delete();
			logger.debug("STOP VSissWorkitemLink.delete  " + new Date());

			//logger.debug("START UserRolesDAO.delete  " + new Date());
			//UserRolesDAO.delete(dataEsecuzione, DmAlmConstants.REPOSITORY_SISS);
			//logger.debug("STOP UserRolesDAO.delete  " + new Date());

			logger.debug("START SissHistoryCfWorkitemDAO.delete  " + new Date());
			SissHistoryCfWorkitemDAO.delete(dataEsecuzione);
			logger.debug("STOP SissHistoryCfWorkitemDAO.delete  " + new Date());

			logger.debug("START SissHistoryWorkitemDAO.delete  " + new Date());
			SissHistoryWorkitemDAO.delete(dataEsecuzione);
			logger.debug("STOP SissHistoryWorkitemDAO.delete  " + new Date());

			logger.debug("START SchedeServizioDAO.delete  " + new Date());
			SchedeServizioDAO.delete(dataEsecuzione,
					DmAlmConstants.REPOSITORY_SISS);
			logger.debug("STOP SchedeServizioDAO.delete  " + new Date());
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

	public static void fillSissHistory(Logger logger) {

		Thread revision, user, project, projectGroup, userRoles, workitemUserAssignee, attachment, hyperlink, schede_servizio;
		// Thread workitemLinked, vLink;

		try {
			logger.debug("START SISSHistoryFacade.fill()");

			Map<Workitem_Type, Long> minRevisionsByType = SissHistoryWorkitemDAO
					.getMinRevisionByType();

			long user_minRevision = SissHistoryUserDAO.getMinRevision();
			long project_minRevision = SissHistoryProjectDAO.getMinRevision();

			long polarion_maxRevision = SissHistoryRevisionDAO.getMaxRevision();
			Timestamp revision_minRevision = SissHistoryRevisionDAO
					.getMinRevision();

			// Recupero il numero di revisione più alto presente nella tabella
			// dmalm.ATTACHMENT
			long attachment_minRevision = SissHistoryAttachmentDAO
					.getMinRevision();

			revision = new Thread(new SissHistoryRevisionRunnable(
					revision_minRevision, polarion_maxRevision, logger));
			user = new Thread(new SissHistoryUserRunnable(user_minRevision,
					polarion_maxRevision, logger));
			project = new Thread(new SissHistoryProjectRunnable(
					project_minRevision, polarion_maxRevision, logger));
			projectGroup = new Thread(new SissHistoryProjectGroupRunnable(
					logger));
			workitemUserAssignee = new Thread(
					new SissHistoryWorkitemUserAssignedRunnable(
							minRevisionsByType, polarion_maxRevision, logger));

			// workitem = new Thread(new
			// SissHistoryWorkitemRunnable(minRevisionsByType,
			// polarion_maxRevision, logger));

			// 8.10.2015 i dati sono letti da Current e non più da History
			// workitemLinked = new Thread(new
			// SissHistoryWorkitemLinkedRunnable(minRevisionsByType,
			// polarion_maxRevision, logger));

			// 2.2.2016 DM_ALM-142 non più utilizzata
			// vLink = new Thread(new SissHistoryVWorkitemLinkRunnable(logger));

			attachment = new Thread(new SissHistoryAttachmentRunnable(
					attachment_minRevision, polarion_maxRevision, logger));
			hyperlink = new Thread(new SissHistoryHyperlinkRunnable(
					minRevisionsByType, polarion_maxRevision, logger));
			userRoles = new Thread(new SissUserRolesRunnable(logger));
			schede_servizio = new Thread(new SissSchedeServizioRunnable(logger));

			project.start();
			project.join();

			revision.start();
			revision.join();

			user.start();
			user.join();

			projectGroup.start();
			projectGroup.join();

			workitemUserAssignee.start();
			workitemUserAssignee.join();

			// //workitem.start();

			// workitemLinked.start();
			// workitemLinked.join();

			attachment.start();
			attachment.join();

			hyperlink.start();
			hyperlink.join();

			schede_servizio.start();
			schede_servizio.join();
			// Thread.currentThread().setPriority(Thread.MAX_PRIORITY);

			// Drop degli indici prima dell'elaborazione di HISTORY_WORKITEM e
			// HISTORY_CF_WORKITEM
			logger.debug("START DROP SISS INDEXES");
			SissHistoryWorkitemDAO.dropIndexes();
			logger.debug("STOP DROP SISS INDEXES");

			retry = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_RETRY));
			wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			
			logger.debug("START SissHistoryWorkitem - numero wi: "
					+ Workitem_Type.values().length);
			
			for (Workitem_Type type : Workitem_Type.values()) {
				logger.debug("START TYPE: SISS " + type.toString());
				int tentativi = 0;
				ErrorManager.getInstance().resetDeadlock();
				ErrorManager.getInstance().resetCFDeadlock();
				boolean inDeadLock = false;
				boolean cfDeadlock = false;

				tentativi++;
				logger.debug("Tentativo " + tentativi);
				SissHistoryWorkitemDAO.fillSissHistoryWorkitem(
						minRevisionsByType, Long.MAX_VALUE, type);
				inDeadLock = ErrorManager.getInstance().hasDeadLock();
				if (!inDeadLock) {
					List<String> customFields = EnumUtils
							.getCFEnumerationByType(type);

					SissHistoryCfWorkitemDAO
							.fillSissHistoryCfWorkitemByWorkitemType(
									minRevisionsByType.get(type),
									Long.MAX_VALUE, type, customFields);
					cfDeadlock = ErrorManager.getInstance().hascfDeadLock();
				}
				
				logger.debug("Fine tentativo " + tentativi + " - WI deadlock "
						+ inDeadLock + " - CF deadlock " + cfDeadlock);
				
				if (inDeadLock || cfDeadlock) {
					while (inDeadLock || cfDeadlock) {

						tentativi++;

						if (tentativi > retry) {
							logger.debug("Raggiunto limite tentativi: "
									+ tentativi);
							Exception e = new Exception("Deadlock detected");
							ErrorManager.getInstance().exceptionOccurred(true,
									e);
							return;
						}

						logger.debug("Errore, aspetto 3 minuti");
						logger.debug("Tentativo " + tentativi);
						TimeUnit.MINUTES.sleep(wait);
						
						if (inDeadLock) {
							SissHistoryWorkitemDAO.fillSissHistoryWorkitem(
									minRevisionsByType, Long.MAX_VALUE, type);
							inDeadLock = ErrorManager.getInstance()
									.hasDeadLock();
							if (!inDeadLock) {
								logger.debug("Non in deadlock -> provo i CF");
								List<String> customFields = EnumUtils
										.getCFEnumerationByType(type);

								SissHistoryCfWorkitemDAO
										.fillSissHistoryCfWorkitemByWorkitemType(
												minRevisionsByType.get(type),
												Long.MAX_VALUE, type,
												customFields);
								cfDeadlock = ErrorManager.getInstance()
										.hascfDeadLock();
								logger.debug("I CF sono in deadlock "
										+ cfDeadlock);
							}
						} else {
							if (cfDeadlock) {
								logger.debug("Scarico soltanto i CF");

								List<String> customFields = EnumUtils
											.getCFEnumerationByType(type);

								SissHistoryCfWorkitemDAO
											.fillSissHistoryCfWorkitemByWorkitemType(
													minRevisionsByType
															.get(type),
													Long.MAX_VALUE, type,
													customFields);
								
								cfDeadlock = ErrorManager.getInstance()
											.hascfDeadLock();
								
								logger.debug("I CF sono in deadlock "
											+ cfDeadlock);
							}
						}
					}
				}
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

			// TRIFASICO
			// List<Thread> workitems = new ArrayList<Thread>();
			//
			// for(int i = 0; i < 7; i++) {
			// Workitem_Type type = Workitem_Type.values()[i];
			// Thread wi = new Thread(new
			// SissHistoryWorkitemRunnable(minRevisionsByType, Long.MAX_VALUE,
			// logger, type));
			// if(type.toString().equalsIgnoreCase(DmAlmConstants.WORKITEM_TYPE_DOCUMENTO))
			// wi.setPriority((Thread.NORM_PRIORITY + Thread.MAX_PRIORITY) / 2);
			// workitems.add(wi);
			// wi.start();
			// }
			// for(Thread wi : workitems)
			// wi.join();
			//
			// workitems.clear();
			//
			// for(int i = 7; i < 14; i++) {
			// Workitem_Type type = Workitem_Type.values()[i];
			// Thread wi = new Thread(new
			// SissHistoryWorkitemRunnable(minRevisionsByType, Long.MAX_VALUE,
			// logger, type));
			// if(type.toString().equalsIgnoreCase(DmAlmConstants.WORKITEM_TYPE_RELEASEPROGETTO))
			// wi.setPriority((Thread.NORM_PRIORITY + Thread.MAX_PRIORITY) / 2);
			// workitems.add(wi);
			// wi.start();
			// }
			// for(Thread wi : workitems)
			// wi.join();
			//
			// workitems.clear();
			//
			// for(int i = 14; i < Workitem_Type.values().length; i++) {
			// Workitem_Type type = Workitem_Type.values()[i];
			// Thread wi = new Thread(new
			// SissHistoryWorkitemRunnable(minRevisionsByType, Long.MAX_VALUE,
			// logger, type));
			// if(type.toString().equalsIgnoreCase(DmAlmConstants.WORKITEM_TYPE_MANUTENZIONE))
			// wi.setPriority((Thread.NORM_PRIORITY + Thread.MAX_PRIORITY) / 2);
			// workitems.add(wi);
			// wi.start();
			// }
			// for(Thread wi : workitems)
			// wi.join();
			//
			// workitems.clear();

			// workitem.join();

			// vLink.start();
			// vLink.join();

			userRoles.start();
			userRoles.join();

			logger.debug("STOP SISSHistoryFacade.fill()");
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}
}