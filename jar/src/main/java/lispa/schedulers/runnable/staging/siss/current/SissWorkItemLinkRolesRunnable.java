package lispa.schedulers.runnable.staging.siss.current;

import java.util.Date;

import org.apache.log4j.Logger;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.svn.LinkedWorkItemRolesXML;


public class SissWorkItemLinkRolesRunnable implements Runnable {
	
	private final String myrepository = DmAlmConstants.REPOSITORY_SISS;
	private Logger logger;

	
	public SissWorkItemLinkRolesRunnable(Logger logger) {
			this.logger = logger;
	}



	@Override
	public void run() {
		try {
			logger.debug("START  fillLinkedWorkItemRoles SISS "+ new Date());
			LinkedWorkItemRolesXML.fillLinkedWorkItemRoles(myrepository);
			logger.debug("STOP   fillLinkedWorkItemRoles SISS "+ new Date());
		} catch (Exception e) {
			
		}
		
	}

}
