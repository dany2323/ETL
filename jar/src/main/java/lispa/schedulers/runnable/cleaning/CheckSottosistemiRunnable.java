package lispa.schedulers.runnable.cleaning;

import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.facade.cleaning.CheckOresteSottosistemiFacade;

import org.apache.log4j.Logger;

public class CheckSottosistemiRunnable implements Runnable {
	private Logger logger; 
	private Timestamp dataEsecuzione;
	
	public CheckSottosistemiRunnable(Logger logger, Timestamp date) {
		this.logger = logger;
		this.dataEsecuzione = date;
	}
	@Override
	public void run() {
		logger.info("START CheckOresteSottosistemiFacade.execute "+new Date());
		CheckOresteSottosistemiFacade.execute(logger,dataEsecuzione);
		logger.info("STOP CheckOresteSottosistemiFacade.execute "+new Date());
		
	}

}
