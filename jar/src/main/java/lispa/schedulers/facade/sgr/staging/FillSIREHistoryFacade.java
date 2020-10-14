package lispa.schedulers.facade.sgr.staging;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

import lispa.schedulers.dao.UtilsDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryUserDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.runnable.staging.sire.history.SireSchedeServizioRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireUserRolesSgrRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryAttachmentRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryHyperlinkRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryProjectGroupRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryProjectRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryRevisionRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryUserRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryWorkitemUserAssignedRunnable;

public class FillSIREHistoryFacade {

	public static void execute(Logger logger) {
		try {

			logger.debug("START fillSireHistory  " + new Date());
			fillSireHistory(logger);
			logger.debug("STOP fillSireHistory  " + new Date());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}

	public static void fillSireHistory(Logger logger) {

		Thread revision, user, project, projectGroup, workitemUserAssignee, attachment, hyperlink, schede_servizio, userRolesSgr;

		try {
			logger.debug("START SIREHistoryFacade.fill()");

			Map<String, Long> minRevisionsByType = SireHistoryWorkitemDAO.getMinRevisionByType();
			long polarion_maxRevision = SireHistoryRevisionDAO.getMaxRevision();
			long user_minRevision = SireHistoryUserDAO.getMinRevision();
			long project_minRevision = SireHistoryProjectDAO.getMinRevision();
			Timestamp revision_minRevision = SireHistoryRevisionDAO.getMinRevision();
			long attachment_minRevision = SireHistoryAttachmentDAO.getMinRevision();
			
			
			revision = new Thread(new SireHistoryRevisionRunnable(
					revision_minRevision, polarion_maxRevision, logger));
			user = new Thread(new SireHistoryUserRunnable(user_minRevision,
					polarion_maxRevision, logger));
			project = new Thread(new SireHistoryProjectRunnable(
					project_minRevision, polarion_maxRevision, logger));
			projectGroup = new Thread(new SireHistoryProjectGroupRunnable(
					logger));
			workitemUserAssignee = new Thread(new SireHistoryWorkitemUserAssignedRunnable(
							minRevisionsByType, polarion_maxRevision, logger));
			attachment = new Thread(new SireHistoryAttachmentRunnable(
					attachment_minRevision, polarion_maxRevision, logger));
			hyperlink = new Thread(new SireHistoryHyperlinkRunnable(
					minRevisionsByType, polarion_maxRevision, logger));
			schede_servizio = new Thread(new SireSchedeServizioRunnable(logger));
			userRolesSgr = new Thread(new SireUserRolesSgrRunnable(logger));

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

			attachment.start();
			attachment.join();

			hyperlink.start();
			hyperlink.join();

			schede_servizio.start();
			schede_servizio.join();

			userRolesSgr.start();
			userRolesSgr.join();
			
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			
			logger.debug("START SireHistoryWorkitem - numero wi: "
					+ minRevisionsByType.keySet().size());
			for (String type : minRevisionsByType.keySet()) {
				logger.debug("START TYPE: SIRE " + type.toString());
				int tentativi_wi_deadlock = 0;
				int tentativi_cf_deadlock = 0;
				ErrorManager.getInstance().resetDeadlock();
				ErrorManager.getInstance().resetCFDeadlock();
				boolean inDeadlock = false;
				boolean cfDeadlock = false;

				tentativi_wi_deadlock++;
				logger.debug("Tentativo Workitem " + tentativi_wi_deadlock);
				SireHistoryWorkitemDAO.fillSireHistoryWorkitem(type, minRevisionsByType, polarion_maxRevision);
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
				while(inDeadlock) {
					tentativi_wi_deadlock++;
					logger.debug("inDeadlock, aspetto 3 minuti");
					TimeUnit.MINUTES.sleep(wait);
					logger.debug("Tentativo Workitem " + tentativi_wi_deadlock);
					ErrorManager.getInstance().resetDeadlock();
					SireHistoryWorkitemDAO.delete(type, minRevisionsByType, polarion_maxRevision);
					SireHistoryWorkitemDAO.fillSireHistoryWorkitem(type, minRevisionsByType, polarion_maxRevision);
					inDeadlock = ErrorManager.getInstance().hasDeadLock();
				}
				logger.debug("Fine tentativo " + tentativi_wi_deadlock + " - WI deadlock "	+ inDeadlock);
				
				tentativi_cf_deadlock++;
				logger.debug("Tentativo CF " + tentativi_cf_deadlock);
				List<String> customFields = UtilsDAO.getCfByWorkitem(type);
				for (String customField : customFields) {
					SireHistoryCfWorkitemDAO.fillSireHistoryCfWorkitemByWorkitemType(
							minRevisionsByType.get(type), polarion_maxRevision, type, customField);
					cfDeadlock = ErrorManager.getInstance().hascfDeadLock();
					while(cfDeadlock) {
						tentativi_cf_deadlock++;
						logger.debug("cfDeadLock, aspetto 3 minuti");
						TimeUnit.MINUTES.sleep(wait);
						logger.debug("Tentativo CF " + tentativi_cf_deadlock);
						ErrorManager.getInstance().resetCFDeadlock();
						customFields = UtilsDAO.getCfByWorkitem(type);
						SireHistoryCfWorkitemDAO.fillSireHistoryCfWorkitemByWorkitemType(
								minRevisionsByType.get(type), polarion_maxRevision, type, customField);
						cfDeadlock = ErrorManager.getInstance().hascfDeadLock();
					}
				}
				logger.debug("Fine tentativo " + tentativi_cf_deadlock + " - CF deadlock "	+ cfDeadlock);
			}

			logger.debug("STOP SIREHistoryFacade.fill()");
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
}