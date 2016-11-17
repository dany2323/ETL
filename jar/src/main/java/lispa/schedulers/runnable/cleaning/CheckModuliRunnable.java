package lispa.schedulers.runnable.cleaning;

import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.facade.cleaning.CheckOresteModuliFacade;

import org.apache.log4j.Logger;

public class CheckModuliRunnable implements Runnable {
	private Logger logger; 
	private Timestamp dataEsecuzione;
	
	public CheckModuliRunnable(Logger logger, Timestamp date) {
		this.logger = logger;
		this.dataEsecuzione = date;
	}
	@Override
	public void run() {
		logger.info("START CheckOresteModuliFacade.execute "+new Date());
		CheckOresteModuliFacade.execute(logger,dataEsecuzione);
		logger.info("STOP CheckOresteModuliFacade.execute "+new Date());
	}

}
