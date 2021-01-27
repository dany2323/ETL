package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectGroupDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryUserDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemLinkedDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemUserAssignedDAO;
import lispa.schedulers.dao.target.SchedeServizioDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem;
import lispa.schedulers.runnable.staging.sire.current.SireSchedeServizioRunnable;
import lispa.schedulers.runnable.staging.sire.current.SireUserRolesRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryAttachmentRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryHyperlinkRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryProjectGroupRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryProjectRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryRevisionRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryUserRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryWorkitemUserAssignedRunnable;
import lispa.schedulers.utils.EnumUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class FillSIREHistoryFacade {

	static SireHistoryWorkitem fonteWorkItems = SireHistoryWorkitem.workitem;

	public static void execute(Logger logger) {
		try {

			logger.debug("START fillSireHistory  " + new Date());
			fillSireHistory(logger);
			logger.debug("STOP fillSireHistory  " + new Date());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}

	public static void delete(Logger logger, Timestamp dataEsecuzione) {

		try {
			logger.debug("START SireHistoryProjectGroup.delete  " + new Date());
			SireHistoryProjectGroupDAO.delete();
			logger.debug("STOP SireHistoryProjectGroup.delete  " + new Date());

			logger.debug("START SireHistoryLinkedWorkitems.delete  "
					+ new Date());
			SireHistoryWorkitemLinkedDAO.delete();
			logger.debug("STOP SireHistoryLinkedWorkitems.delete  "
					+ new Date());

			//logger.debug("START UserRolesDAO.delete  " + new Date());
			//UserRolesDAO.delete(dataEsecuzione, DmAlmConstants.REPOSITORY_SIRE);
			//logger.debug("STOP UserRolesDAO.delete  " + new Date());

			logger.debug("START SireHistoryCfWorkitemDAO.delete  " + new Date());
			SireHistoryCfWorkitemDAO.delete();
			logger.debug("STOP SireHistoryCfWorkitemDAO.delete  " + new Date());

			logger.debug("START SireHistoryWorkitemDAO.delete  " + new Date());
			SireHistoryWorkitemDAO.delete(dataEsecuzione);
			logger.debug("STOP SireHistoryWorkitemDAO.delete  " + new Date());

			logger.debug("START SchedeServizioDAO.delete  " + new Date());
			SchedeServizioDAO.delete(dataEsecuzione,
					DmAlmConstants.REPOSITORY_SIRE);
			logger.debug("STOP SchedeServizioDAO.delete  " + new Date());
			
			SireHistoryWorkitemUserAssignedDAO.delete();
		
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

	public static void fillSireHistory(Logger logger) {

		Thread revision, user, project, projectGroup, workitemUserAssignee, attachment, hyperlink, userRoles, schede_servizio;
		// Thread workitemLinked, vLink;

		try {
			logger.debug("START SIREHistoryFacade.fill()");

			Map<EnumWorkitemType, Long> minRevisionsByType = SireHistoryWorkitemDAO
					.getMinRevisionByType();

			// Specifica: recupero il numero di revisione più alto alla fonte
			// polarion.REVISION
			// presente nella fonte fino ad un ora prima della data di
			// esecuzione dell ETL
			long polarion_maxRevision = SireHistoryRevisionDAO.getMaxRevision();

			// Recupero il numero di revisione più alto presente nella tabella
			// dmalm.USER
			long user_minRevision = SireHistoryUserDAO.getMinRevision();

			// Recupero il numero di revisione più alto presente nella tabella
			// dmalm.PROJECT
			long project_minRevision = SireHistoryProjectDAO.getMinRevision();

			// Recupero la MAX DT_CREAZIONE nella tabella dmalm.REVISION
			Timestamp revision_minRevision = SireHistoryRevisionDAO
					.getMinRevision();

			// Recupero il numero di revisione più alto presente nella tabella
			// dmalm.ATTACHMENT
			long attachment_minRevision = SireHistoryAttachmentDAO
					.getMinRevision();

			revision = new Thread(new SireHistoryRevisionRunnable(
					revision_minRevision, polarion_maxRevision, logger));
			user = new Thread(new SireHistoryUserRunnable(user_minRevision,
					polarion_maxRevision, logger));
			project = new Thread(new SireHistoryProjectRunnable(
					project_minRevision, polarion_maxRevision, logger));
			projectGroup = new Thread(new SireHistoryProjectGroupRunnable(
					logger));
			workitemUserAssignee = new Thread(
					new SireHistoryWorkitemUserAssignedRunnable(
							minRevisionsByType, polarion_maxRevision, logger));

			attachment = new Thread(new SireHistoryAttachmentRunnable(
					attachment_minRevision, polarion_maxRevision, logger));
			hyperlink = new Thread(new SireHistoryHyperlinkRunnable(
					minRevisionsByType, polarion_maxRevision, logger));
			userRoles = new Thread(new SireUserRolesRunnable(logger));

			schede_servizio = new Thread(new SireSchedeServizioRunnable(logger));

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
			// workitem.start();

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
			logger.debug("START DROP SIRE INDEXES");
			SireHistoryWorkitemDAO.dropIndexes();
			logger.debug("STOP DROP SIRE INDEXES");

			logger.debug("START SireHistoryWorkitem - numero wi: "
					+ Workitem_Type.EnumWorkitemType.values().length);
			
			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {
				logger.debug("START TYPE: SIRE " + type.toString());
				SireHistoryWorkitemDAO.fillSireHistoryWorkitem(
						minRevisionsByType, polarion_maxRevision, type);
				List<String> customFields = EnumUtils.getCFEnumerationByType(type);
					SireHistoryCfWorkitemDAO.fillSireHistoryCfWorkitemByWorkitemType(
							minRevisionsByType.get(type), polarion_maxRevision, type, customFields);
			}

			logger.debug("START delete not matching CFs SIRE");
			SireHistoryCfWorkitemDAO.deleteNotMatchingCFS();
			logger.debug("STOP delete not matching CFs SIRE");

			logger.debug("START Update CF SIRE");
			SireHistoryCfWorkitemDAO.updateCFonWorkItem();
			logger.debug("STOP Update CF SIRE");

			// Rebuild degli indici dopo l'elaborazione di HISTORY_WORKITEM e
			// HISTORY_CF_WORKITEM
			logger.debug("START REBUILD SIRE INDEXES");
			SireHistoryWorkitemDAO.rebuildIndexes();
			logger.debug("STOP REBUILD SIRE INDEXES");

			ConnectionManager.getInstance().dismiss();

			userRoles.start();
			userRoles.join();

			logger.debug("STOP SIREHistoryFacade.fill()");
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}
}