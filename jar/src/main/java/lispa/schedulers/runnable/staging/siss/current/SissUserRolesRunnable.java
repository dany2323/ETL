package lispa.schedulers.runnable.staging.siss.current;

import java.util.Date;

import lispa.schedulers.svn.SISSUserRolesXML;

import org.apache.log4j.Logger;

public class SissUserRolesRunnable implements Runnable {
	
	private Logger logger;
	
	public SissUserRolesRunnable(Logger logger) {
	
		this.logger = logger;
		
	}

	@Override
	public void run() {

		try {
			logger.debug("START fillUserRoles SISS "+ new Date());
			SISSUserRolesXML.fillSISSHistoryUserRoles();
			logger.debug("STOP fillUserRoles SISS "+ new Date());
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		
	}

}
