package lispa.schedulers.runnable.staging.sire.current;

import java.util.Date;

import org.apache.log4j.Logger;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.svn.LinkedWorkItemRolesXML;


public class SireWorkItemLinkRolesRunnable implements Runnable {
	
	private final String myrepository = DmAlmConstants.REPOSITORY_SIRE;
	private Logger logger;

	
	public SireWorkItemLinkRolesRunnable(Logger logger) {
		this.logger = logger;
	}



	@Override
	public void run() {
		try {
			logger.debug("START  fillLinkedWorkItemRoles SIRE "+ new Date());
			LinkedWorkItemRolesXML.fillLinkedWorkItemRoles(myrepository);
			logger.debug("STOP   fillLinkedWorkItemRoles SIRE "+ new Date());
		} catch (Exception e) {
			
		}
		
	}

}
