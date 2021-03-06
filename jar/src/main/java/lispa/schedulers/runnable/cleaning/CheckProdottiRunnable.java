package lispa.schedulers.runnable.cleaning;

import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.facade.cleaning.CheckOresteProdottiFacade;

import org.apache.log4j.Logger;

public class CheckProdottiRunnable implements Runnable {
	private Logger logger; 
	private Timestamp dataEsecuzione;
	
	public CheckProdottiRunnable(Logger logger, Timestamp date) {
		this.logger = logger;
		this.dataEsecuzione = date;
	}
	@Override
	public void run() {
		logger.info("START CheckOresteProdottiFacade.execute "+new Date());
		CheckOresteProdottiFacade.execute(logger, dataEsecuzione);
		logger.info("STOP CheckOresteProdottiFacade.execute "+new Date());
	}

}
