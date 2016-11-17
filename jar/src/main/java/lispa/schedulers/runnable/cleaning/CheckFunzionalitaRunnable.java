package lispa.schedulers.runnable.cleaning;

import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.facade.cleaning.CheckOresteFunzionalitaFacade;

import org.apache.log4j.Logger;

public class CheckFunzionalitaRunnable implements Runnable {
	private Logger logger; 
	private Timestamp dataEsecuzione;
	
	public CheckFunzionalitaRunnable(Logger logger, Timestamp date) {
		this.logger = logger;
		this.dataEsecuzione = date;
	}
	@Override
	public void run() {
		logger.info("START CheckOresteFunzionalitaFacade.execute "+new Date());
		CheckOresteFunzionalitaFacade.execute(logger,dataEsecuzione);
		logger.info("STOP CheckOresteFunzionalitaFacade.execute "+new Date());
	}

}
