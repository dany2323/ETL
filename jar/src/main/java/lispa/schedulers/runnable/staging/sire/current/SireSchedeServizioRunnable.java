package lispa.schedulers.runnable.staging.sire.current;

import java.util.Date;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.svn.SchedeServizioXML;

import org.apache.log4j.Logger;

public class SireSchedeServizioRunnable implements Runnable {

	private Logger logger;

	public SireSchedeServizioRunnable(Logger logger) {

		this.logger = logger;

	}

	@Override
	public void run() {

		try {
			logger.debug("START fillSchedeServizio SIRE " + new Date());
			SchedeServizioXML.fillSchedeServizio(DmAlmConstants.REPOSITORY_SIRE);
			logger.debug("STOP fillSchedeServizio SIRE " + new Date());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

}
