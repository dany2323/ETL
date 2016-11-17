package lispa.schedulers.runnable.cleaning;

import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.facade.cleaning.CheckOresteClassificatoriFacade;

import org.apache.log4j.Logger;

public class CheckClassificatoriRunnable implements Runnable {
	private Logger logger; 
	private Timestamp dataEsecuzione;
	
	public CheckClassificatoriRunnable(Logger logger, Timestamp date) {
		this.logger = logger;
		this.dataEsecuzione = date;
	}

	@Override
	public void run() {
		logger.info("START CheckOresteClassificatoriFacade.execute "+new Date());
		CheckOresteClassificatoriFacade.execute(logger, dataEsecuzione);
		logger.info("STOP CheckOresteClassificatoriFacade.execute "+new Date());
		
	}

}
