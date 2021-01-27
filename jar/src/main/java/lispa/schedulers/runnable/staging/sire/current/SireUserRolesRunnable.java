package lispa.schedulers.runnable.staging.sire.current;

import java.util.Date;

import org.apache.log4j.Logger;

import lispa.schedulers.manager.ErrorManager;

public class SireUserRolesRunnable implements Runnable {

	private Logger logger;

	public SireUserRolesRunnable(Logger logger) {

		this.logger = logger;

	}

	@Override
	public void run() {

		try {
			logger.debug("START fillUserRoles SIRE " + new Date());
			logger.debug("SIREUserRolesXML.fillSIREHistoryUserRoles commentata");
			// SIREUserRolesXML.fillSIREHistoryUserRoles();
			logger.debug("STOP fillUserRoles SIRE " + new Date());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

}
