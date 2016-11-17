package lispa.schedulers.runnable.staging.sire.current;

import java.util.Date;

import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.svn.SISSSchedeServizioXML;

import org.apache.log4j.Logger;

public class SissSchedeServizioRunnable implements Runnable {

	private Logger logger;

	public SissSchedeServizioRunnable(Logger logger) {

		this.logger = logger;

	}

	@Override
	public void run() {

		try {
			logger.debug("START fillSchedeServizio SISS " + new Date());
			SISSSchedeServizioXML.fillSISSHistorySchedeServizio();
			logger.debug("STOP fillSchedeServizio SISS " + new Date());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

	
}
