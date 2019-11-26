package lispa.schedulers.runnable.staging.sire.history;

import java.util.Date;

import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.svn.SIRESchedeServizioXML;

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
			SIRESchedeServizioXML.fillSIREHistorySchedeServizio();
			logger.debug("STOP fillSchedeServizio SIRE " + new Date());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

}
