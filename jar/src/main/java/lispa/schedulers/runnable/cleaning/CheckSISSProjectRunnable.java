package lispa.schedulers.runnable.cleaning;

import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.facade.cleaning.CheckSGRSISSProjectFacade;

import org.apache.log4j.Logger;

public class CheckSISSProjectRunnable implements Runnable {
	private Logger logger; 
	private Timestamp dataEsecuzione;
	
	public CheckSISSProjectRunnable(Logger logger, Timestamp date) {
		this.logger = logger;
		this.dataEsecuzione = date;
	} 
	@Override
	public void run() {
		logger.info("START CheckSGRSISSProjectFacade.execute "+new Date());
		CheckSGRSISSProjectFacade.execute(logger,dataEsecuzione);
		logger.info("STOP CheckSGRSISSProjectFacade.execute "+new Date());

	}

}
