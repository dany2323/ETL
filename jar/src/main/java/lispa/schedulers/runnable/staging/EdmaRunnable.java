package lispa.schedulers.runnable.staging;

import java.sql.Timestamp;

import lispa.schedulers.facade.target.FillEdmaFacade;

import org.apache.log4j.Logger;

public class EdmaRunnable implements Runnable {
	
	private Logger logger;
	private Timestamp dataEsecuzioneDeleted;
	
	public EdmaRunnable(Logger logger, Timestamp dataEsecuzioneDeleted) {
		
		this.logger = logger;
		this.dataEsecuzioneDeleted = dataEsecuzioneDeleted;
		
	}

	@Override
	public void run() {
		logger.info("START: FillStaging EDMA");
		FillEdmaFacade.delete(logger, dataEsecuzioneDeleted);
		FillEdmaFacade.execute(logger);
		logger.info("STOP: FillStaging EDMA");
	}

}
