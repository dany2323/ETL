package lispa.schedulers.runnable.cleaning;

import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.facade.cleaning.CheckOresteAmbienteTecnologicoFacade;

import org.apache.log4j.Logger;

public class CheckAmbienteTecnologicoRunnable implements Runnable {
	private Logger logger; 
	private Timestamp dataEsecuzione;
	
	public CheckAmbienteTecnologicoRunnable(Logger logger, Timestamp date) {
		this.logger = logger;
		this.dataEsecuzione = date;
	}
	@Override
	public void run() {
		logger.info("START CheckOresteAmbienteTecnologicoFacade.execute "+new Date());
		CheckOresteAmbienteTecnologicoFacade.execute(logger,dataEsecuzione);
		logger.info("STOP CheckOresteAmbienteTecnologicoFacade.execute "+new Date());
	}

}
