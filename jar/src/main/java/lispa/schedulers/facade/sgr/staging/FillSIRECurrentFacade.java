package lispa.schedulers.facade.sgr.staging;

import java.util.Date;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.UtilsDAO;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentProjectDAO;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentWorkitemLinkedDAO;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.svn.LinkedWorkItemRolesXML;
import lispa.schedulers.svn.ProjectRolesXML;
import lispa.schedulers.svn.StatoWorkItemXML;
import org.apache.log4j.Logger;

public class FillSIRECurrentFacade {

	public static void execute(Logger logger) {

		try {

			logger.debug("START fillSireCurrentWorkitemLinked");
			SireCurrentWorkitemLinkedDAO.fillSireCurrentWorkitemLinked();
			logger.debug("STOP fillSireCurrentWorkitemLinked");

			logger.debug("START fillSireCurrentProject "+ new Date());
			SireCurrentProjectDAO.fillSireCurrentProject();
			logger.debug("STOP  fillSireCurrentProject "+ new Date());
			
			logger.debug("START fillSireCurrentWorkitem "+ new Date());
			SireCurrentWorkitemDAO.fillSireCurrentWorkitems();
			logger.debug("STOP  fillSireCurrentWorkitem "+ new Date());
			
			logger.debug("START  fillProjectRoles SIRE "+ new Date());
			ProjectRolesXML.fillProjectRoles(DmAlmConstants.REPOSITORY_SIRE);
			logger.debug("STOP  fillProjectRoles SIRE "+ new Date());

			List<String> listIdWorkitem = UtilsDAO.getIdWorkitem();
			for(String type : listIdWorkitem) {
				logger.debug("START  fillStatoWorkItem SIRE " + type.toString() + " " + new Date());
				StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SIRE, type);
				logger.debug("STOP  fillStatoWorkItem  SIRE " + type.toString() + " " + new Date());
			}
			
			logger.debug("START  fillLinkedWorkItemRoles SIRE "+ new Date());
			LinkedWorkItemRolesXML.fillLinkedWorkItemRoles(DmAlmConstants.REPOSITORY_SIRE);
			logger.debug("STOP   fillLinkedWorkItemRoles SIRE "+ new Date());
			
			ConnectionManager.getInstance().dismiss();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
}