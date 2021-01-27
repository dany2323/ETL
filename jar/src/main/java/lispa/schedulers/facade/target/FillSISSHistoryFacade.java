package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectGroupDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryUserDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemLinkedDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemUserAssignedDAO;
import lispa.schedulers.dao.target.SchedeServizioDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem;
import lispa.schedulers.runnable.staging.siss.current.SissSchedeServizioRunnable;
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
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class FillSISSHistoryFacade {

	static SissHistoryWorkitem fonteWorkItems = SissHistoryWorkitem.workitem;

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

			//logger.debug("START UserRolesDAO.delete  " + new Date());
			//UserRolesDAO.delete(dataEsecuzione, DmAlmConstants.REPOSITORY_SISS);
			//logger.debug("STOP UserRolesDAO.delete  " + new Date());

			logger.debug("START SissHistoryCfWorkitemDAO.delete  " + new Date());
			SissHistoryCfWorkitemDAO.delete();
			logger.debug("STOP SissHistoryCfWorkitemDAO.delete  " + new Date());

			logger.debug("START SissHistoryWorkitemDAO.delete  " + new Date());
			SissHistoryWorkitemDAO.delete(dataEsecuzione);
			logger.debug("STOP SissHistoryWorkitemDAO.delete  " + new Date());

			logger.debug("START SchedeServizioDAO.delete  " + new Date());
			SchedeServizioDAO.delete(dataEsecuzione,
					DmAlmConstants.REPOSITORY_SISS);
			SissHistoryWorkitemUserAssignedDAO.delete();
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

			Map<EnumWorkitemType, Long> minRevisionsByType = SissHistoryWorkitemDAO
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

			logger.debug("START SissHistoryWorkitem - numero wi: "
					+ Workitem_Type.EnumWorkitemType.values().length);
			
			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {
				logger.debug("START TYPE: SISS " + type.toString());
				SissHistoryWorkitemDAO.fillSissHistoryWorkitem(
						minRevisionsByType, polarion_maxRevision, type);
				List<String> customFields = EnumUtils.getCFEnumerationByType(type);
				SissHistoryCfWorkitemDAO.fillSissHistoryCfWorkitemByWorkitemType(
						minRevisionsByType.get(type), polarion_maxRevision, type, customFields);
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