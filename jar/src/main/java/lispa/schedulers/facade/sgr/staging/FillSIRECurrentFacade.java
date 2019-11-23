package lispa.schedulers.facade.sgr.staging;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ProjectRolesDAO;
import lispa.schedulers.dao.StatoWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentProjectDAO;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentWorkitemLinkedDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.runnable.staging.sire.current.SireCurrentProjectRunnable;
import lispa.schedulers.runnable.staging.sire.current.SireProjectRolesRunnable;
import lispa.schedulers.runnable.staging.sire.current.SireStatoWorkitemRunnable;
import lispa.schedulers.runnable.staging.sire.current.SireWorkItemLinkRolesRunnable;
import lispa.schedulers.svn.ProjectRolesXML;
import lispa.schedulers.svn.StatoWorkItemXML;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import org.apache.log4j.Logger;

public class FillSIRECurrentFacade {

	public static void execute(Logger logger) {

		//Thread userRoles;
		Thread project, projectRoles, statoWorkitem, workItemLinkRoles;

		try {

			logger.debug("START fillSireCurrentWorkitemLinked");
			SireCurrentWorkitemLinkedDAO.fillSireCurrentWorkitemLinked();
			logger.debug("STOP fillSireCurrentWorkitemLinked");

			logger.debug("START fillSireCurrentProject "+ new Date());
			SireCurrentProjectDAO.fillSireCurrentProject();
			logger.debug("STOP  fillSireCurrentProject "+ new Date());

			logger.debug("START  fillProjectRoles SIRE "+ new Date());
			ProjectRolesXML.fillProjectRoles(DmAlmConstants.REPOSITORY_SIRE);
			logger.debug("STOP  fillProjectRoles SIRE "+ new Date());

			for(EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {
				logger.debug("START  fillStatoWorkItem SIRE " + type.toString() + " " + new Date());
				StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SIRE, type);
				logger.debug("STOP  fillStatoWorkItem  SIRE " + type.toString() + " " + new Date());
			}
			// userRoles = new Thread(new SireUserRolesRunnable(logger));

			workItemLinkRoles = new Thread(new SireWorkItemLinkRolesRunnable(
					logger));

			project.start();
			project.join();

			projectRoles.start();
			projectRoles.join();

			// userRoles.start();
			// userRoles.join();

			statoWorkitem.start();
			statoWorkitem.join();

			workItemLinkRoles.start();
			workItemLinkRoles.join();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		}

	}

	public static void delete(Logger logger, Timestamp dataEsecuzione) {

		try {
			/*
			 * Commentato perchè l'import dallo schema di Current non viene più
			 * effettuato DM_ALM-8
			 * 
			 * SireCurrentCfWorkitemDAO.delete(dataEsecuzione);
			 * 
			 * SireCurrentProjectGroupDAO.delete(dataEsecuzione);
			 * 
			 * SireCurrentUserDAO.delete(dataEsecuzione);
			 * 
			 * SireCurrentWorkitemDAO.delete(dataEsecuzione);
			 * 
			 * SireCurrentWorkitemUserAssignedDAO.delete(dataEsecuzione);
			 */

			SireCurrentWorkitemLinkedDAO.delete(dataEsecuzione);

			SireCurrentProjectDAO.delete(dataEsecuzione);

			// XML
			ProjectRolesDAO.delete(dataEsecuzione,
					DmAlmConstants.REPOSITORY_SIRE);

			StatoWorkitemDAO.delete(dataEsecuzione,
					DmAlmConstants.REPOSITORY_SIRE);
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

}