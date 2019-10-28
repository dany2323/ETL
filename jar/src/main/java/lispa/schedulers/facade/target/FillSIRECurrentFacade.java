package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;

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

import org.apache.log4j.Logger;

public class FillSIRECurrentFacade {

	public static void execute(Logger logger) {

		//Thread userRoles;
		Thread project, projectRoles, statoWorkitem, workItemLinkRoles;

		try {

			// Righe sotto commentate perchè da DM_ALM-8 non abbiamo più
			// effettuato
			// l'import dei dati dagli schema di Current

			// logger.debug("START  fillSireCurrentWorkitem "+ new Date());
			// List<Tuple> workitems = new ArrayList<Tuple>();
			// workitems = SireCurrentWorkitemDAO.fillSireCurrentWorkitem();
			// logger.debug("STOP   fillSireCurrentWorkitem "+ new Date());
			//
			// logger.debug("START fillSireCurrentCfWorkitem "+ new Date());
			// SireCurrentCfWorkitemDAO.fillSireCurrentCfWorkitem(workitems);
			// logger.debug("STOP fillSireCurrentCfWorkitem "+ new Date());
			//
			// logger.debug("START fillSireCurrentProjectGroup "+ new Date());
			// SireCurrentProjectGroupDAO.fillSireCurrentProjectGroup();
			// logger.debug("STOP  fillSireCurrentProjectGroup "+ new Date());
			//
			// logger.debug("START fillSireCurrentUser "+ new Date());
			// SireCurrentUserDAO.fillSireCurrentUser();
			// logger.debug("STOP  fillSireCurrentUser "+ new Date());

			logger.debug("START fillSireCurrentWorkitemLinked");
			SireCurrentWorkitemLinkedDAO.fillSireCurrentWorkitemLinked();
			logger.debug("STOP fillSireCurrentWorkitemLinked");

			// logger.debug("START  fillSireCurrentWorkitemUserAssigned "+ new
			// Date());
			// SireCurrentWorkitemUserAssignedDAO.fillSireCurrentWorkitemUserAssigned();
			// logger.debug("STOP   fillSireCurrentWorkitemUserAssigned "+ new
			// Date());

			project = new Thread(new SireCurrentProjectRunnable(logger));

			// FONTI XML
			projectRoles = new Thread(new SireProjectRolesRunnable(logger));

			// userRoles = new Thread(new SireUserRolesRunnable(logger));

			statoWorkitem = new Thread(new SireStatoWorkitemRunnable(logger));

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