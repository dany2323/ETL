package lispa.schedulers.runnable.cleaning;

import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.facade.cleaning.CheckSGRSIREProjectFacade;

import org.apache.log4j.Logger;

public class CheckSIREProjectRunnable implements Runnable {
	private Logger logger; 
	private Timestamp dataEsecuzione;
	
	public CheckSIREProjectRunnable(Logger logger, Timestamp date) {
		this.logger = logger;
		this.dataEsecuzione = date;
	} 
	@Override
	public void run() {
		logger.info("START CheckSGRSIREProjectFacade.execute "+new Date());
		CheckSGRSIREProjectFacade.execute(logger,dataEsecuzione);
		logger.info("STOP CheckSGRSIREProjectFacade.execute "+new Date());

	}

}
