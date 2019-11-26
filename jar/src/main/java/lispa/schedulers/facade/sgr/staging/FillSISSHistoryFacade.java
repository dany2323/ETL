package lispa.schedulers.facade.sgr.staging;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_RETRY;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryUserDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem;
import lispa.schedulers.runnable.staging.siss.history.SissSchedeServizioRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryAttachmentRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryHyperlinkRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryProjectGroupRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryProjectRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryRevisionRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryUserRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryWorkitemUserAssignedRunnable;
import lispa.schedulers.utils.EnumUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

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

	public static void fillSissHistory(Logger logger) {

		Thread revision, user, project, projectGroup, workitemUserAssignee, attachment, hyperlink, schede_servizio;

		try {
			logger.debug("START SISSHistoryFacade.fill()");

			Map<EnumWorkitemType, Long> minRevisionsByType = SissHistoryWorkitemDAO.getMinRevisionByType();
			long user_minRevision = SissHistoryUserDAO.getMinRevision();
			long project_minRevision = SissHistoryProjectDAO.getMinRevision();
			long polarion_maxRevision = SissHistoryRevisionDAO.getMaxRevision();
			Timestamp revision_minRevision = SissHistoryRevisionDAO.getMinRevision();
			long attachment_minRevision = SissHistoryAttachmentDAO.getMinRevision();

			revision = new Thread(new SissHistoryRevisionRunnable(
					revision_minRevision, polarion_maxRevision, logger));
			user = new Thread(new SissHistoryUserRunnable(user_minRevision,
					polarion_maxRevision, logger));
			project = new Thread(new SissHistoryProjectRunnable(
					project_minRevision, polarion_maxRevision, logger));
			projectGroup = new Thread(new SissHistoryProjectGroupRunnable(
					logger));
			workitemUserAssignee = new Thread(new SissHistoryWorkitemUserAssignedRunnable(
					minRevisionsByType, polarion_maxRevision, logger));
			attachment = new Thread(new SissHistoryAttachmentRunnable(
					attachment_minRevision, polarion_maxRevision, logger));
			hyperlink = new Thread(new SissHistoryHyperlinkRunnable(
					minRevisionsByType, polarion_maxRevision, logger));
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

			attachment.start();
			attachment.join();

			hyperlink.start();
			hyperlink.join();

			schede_servizio.start();
			schede_servizio.join();

			retry = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_RETRY));
			wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			
			logger.debug("START SissHistoryWorkitem - numero wi: "
					+ Workitem_Type.EnumWorkitemType.values().length);
			
			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {
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

					SissHistoryCfWorkitemDAO.fillSissHistoryCfWorkitemByWorkitemType(
							minRevisionsByType.get(type), Long.MAX_VALUE, type, customFields);
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

								SissHistoryCfWorkitemDAO.fillSissHistoryCfWorkitemByWorkitemType(
										minRevisionsByType.get(type), Long.MAX_VALUE, type,	customFields);
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

								SissHistoryCfWorkitemDAO.fillSissHistoryCfWorkitemByWorkitemType(
									minRevisionsByType.get(type), Long.MAX_VALUE, type, customFields);
								
								cfDeadlock = ErrorManager.getInstance()
											.hascfDeadLock();
								
								logger.debug("I CF sono in deadlock "
											+ cfDeadlock);
							}
						}
					}
				}
			}

			ConnectionManager.getInstance().dismiss();

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