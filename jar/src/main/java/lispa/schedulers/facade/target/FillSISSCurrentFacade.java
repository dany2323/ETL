package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ProjectRolesDAO;
import lispa.schedulers.dao.StatoWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentProjectDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentRevisionDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentSubterraUriMapDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentWorkitemLinkedDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.runnable.staging.siss.current.SissCurrentProjectRunnable;
import lispa.schedulers.runnable.staging.siss.current.SissProjectRolesRunnable;
import lispa.schedulers.runnable.staging.siss.current.SissStatoWorkitemRunnable;
import lispa.schedulers.runnable.staging.siss.current.SissWorkItemLinkRolesRunnable;

import org.apache.log4j.Logger;

public class FillSISSCurrentFacade {

	public static void execute(Logger logger) {
		Thread project, projectRoles, statoWorkitem, workItemLinkRoles;
		try {

			// Righe sotto commentate perchè da DM_ALM-8 non abbiamo più
			// effettuato
			// l'import dei dati dagli schema di Current

			// logger.debug("START SissCurrentWorkitemDAO.fillSissCurrentWorkitem "+new
			// Date());
			// List<Tuple> workitems = new ArrayList<Tuple>();
			// workitems = SissCurrentWorkitemDAO.fillSissCurrentWorkitem();
			// logger.debug("STOP  "+new Date());
			//
			// logger.debug("START SissCurrentCfWorkitemDAO.fillSissCurrentCfWorkitem "+new
			// Date());
			// SissCurrentCfWorkitemDAO.fillSissCurrentCfWorkitem(workitems);
			// logger.debug("STOP  "+new Date());
			//
			// logger.debug("START SissCurrentProjectGroupDAO.fillSissCurrentProjectGroup "+new
			// Date());
			// SissCurrentProjectGroupDAO.fillSissCurrentProjectGroup();
			// logger.debug("STOP  "+new Date());
			//
			// logger.debug("START SissCurrentUserDAO.fillSissCurrentUser "+new
			// Date());
			// SissCurrentUserDAO.fillSissCurrentUser();
			// logger.debug("STOP  "+new Date());
			
			//Viene popolata la tabella di staging DMALM_CURRENT_REVISION
			logger.debug("START fillSissCurrentRevision");
			SissCurrentRevisionDAO.fillSissCurrentRevision();
			logger.debug("STOP fillSissCurrentRevision");
			
			// Viene popolata la tabella di staging DMALM_CURRENT_SUBTERRA_URI_MAP
			logger.debug("START fillSissCurrentSubterraUriMap");
			SissCurrentSubterraUriMapDAO.fillSissCurrentSubterraUriMap();
			logger.debug("STOP fillSissCurrentSubterraUriMap");
			
			logger.debug("START fillSissCurrentWorkitemLinked");
			SissCurrentWorkitemLinkedDAO.fillSissCurrentWorkitemLinked();
			logger.debug("STOP fillSissCurrentWorkitemLinked");
			
			// logger.debug("START SissCurrentWorkitemUserAssignedDAO.fillSissCurrentWorkitemUserAssigned "+new
			// Date());
			// SissCurrentWorkitemUserAssignedDAO.fillSissCurrentWorkitemUserAssigned();
			// logger.debug("STOP  "+new Date());

			// logger.debug("START SissCurrentProjectDAO.fillSissCurrentProject "+new
			// Date());
			// SissCurrentProjectDAO.fillSissCurrentProject();
			// logger.debug("STOP  "+new Date());

			// FONTI XML

			// logger.debug("START  ProjectRolesXML.fillProjectRoles SISS"+ new
			// Date());
			// ProjectRolesXML.fillProjectRoles(DmAlmConstants.REPOSITORY_SISS);
			// logger.debug("STOP  fillProjectRoles SISS "+ new Date());

			// logger.debug("START  StatoWorkItemXML.fillStatoWorkItem Anomalia SISS "+
			// new Date());
			// StatoWorkItemXML.fillStatoWorkItem(DmAlmConstats.REPOSITORY_SISS,
			// DmAlmConstats.WORKITEM_TYPE_ANOMALIA);
			// logger.debug("STOP  fillStatoWorkItem Anomalia SISS "+ new
			// Date());

			// for(Workitem_Type type : Workitem_Type.values()) {
			// logger.debug("START  StatoWorkItemXML.fillStatoWorkItem Difetto SISS "+
			// new Date());
			// StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SISS,
			// type);
			// logger.debug("STOP  fillStatoWorkItem Difetto SISS "+ new
			// Date());
			// }

			// logger.debug("START  SISSUserRolesXML.fillUserRoles SISS "+ new
			// Date());
			// SISSUserRolesXML.fillSISSUserRoles();
			// logger.debug("STOP   fillUserRoles SISS "+ new Date());

			project = new Thread(new SissCurrentProjectRunnable(logger));
			projectRoles = new Thread(new SissProjectRolesRunnable(logger));
			statoWorkitem = new Thread(new SissStatoWorkitemRunnable(logger));
			// userRoles = new Thread(new SissUserRolesRunnable(logger));

			workItemLinkRoles = new Thread(new SissWorkItemLinkRolesRunnable(
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

		}

	}

	public static void delete(Logger logger, Timestamp dataEsecuzione) {

		try {
			/*
			 * Commentato perchè l'import dallo schema di Current non viene più
			 * effettuato DM_ALM-8
			 * 
			 * SissCurrentCfWorkitemDAO.delete(dataEsecuzione);
			 * 
			 * SissCurrentProjectGroupDAO.delete(dataEsecuzione);
			 * 
			 * SissCurrentUserDAO.delete(dataEsecuzione);
			 * 
			 * SissCurrentWorkitemDAO.delete(dataEsecuzione);
			 * 
			 * SissCurrentWorkitemUserAssignedDAO.delete(dataEsecuzione);
			 */

			SissCurrentRevisionDAO.delete();
			
			SissCurrentSubterraUriMapDAO.delete();
			
			SissCurrentWorkitemLinkedDAO.delete(dataEsecuzione);

			SissCurrentProjectDAO.delete(dataEsecuzione);

			// XML
			ProjectRolesDAO.delete(dataEsecuzione,
					DmAlmConstants.REPOSITORY_SISS);

			StatoWorkitemDAO.delete(dataEsecuzione,
					DmAlmConstants.REPOSITORY_SISS);
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

}