package lispa.schedulers.facade.sgr.staging;

import java.util.Date;

import org.apache.log4j.Logger;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentProjectDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentSubterraUriMapDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentWorkitemLinkedDAO;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.svn.LinkedWorkItemRolesXML;
import lispa.schedulers.svn.ProjectRolesXML;
import lispa.schedulers.svn.StatoWorkItemXML;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class FillSISSCurrentFacade {

	public static void execute(Logger logger) {
		try {

			logger.debug("START fillSissCurrentSubterraUriMap");
			SissCurrentSubterraUriMapDAO.fillSissCurrentSubterraUriMap();
			logger.debug("STOP fillSissCurrentSubterraUriMap");
			
			logger.debug("START fillSissCurrentWorkitemLinked");
			SissCurrentWorkitemLinkedDAO.fillSissCurrentWorkitemLinked();
			logger.debug("STOP fillSissCurrentWorkitemLinked");

			logger.debug("START fillSissCurrentProject "+ new Date());
			SissCurrentProjectDAO.fillSissCurrentProject();
			logger.debug("STOP  fillSissCurrentProject "+ new Date());

			logger.debug("START fillSissCurrentWorkitem "+ new Date());
			SissCurrentWorkitemDAO.fillSissCurrentWorkitems();
			logger.debug("STOP  fillSissCurrentWorkitem "+ new Date());
			
			logger.debug("START  fillProjectRoles SISS "+ new Date());
			ProjectRolesXML.fillProjectRoles(DmAlmConstants.REPOSITORY_SISS);
			logger.debug("STOP  fillProjectRoles SISS "+ new Date());

			for(EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {
				logger.debug("START  fillStatoWorkItem SISS " + type.toString() + " " + new Date());
				StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SISS, type);
				logger.debug("STOP  fillStatoWorkItem  SISS " + type.toString() + " " + new Date());
			}
			
			logger.debug("START  fillLinkedWorkItemRoles SISS "+ new Date());
			LinkedWorkItemRolesXML.fillLinkedWorkItemRoles(DmAlmConstants.REPOSITORY_SISS);
			logger.debug("STOP   fillLinkedWorkItemRoles SISS "+ new Date());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}
}
