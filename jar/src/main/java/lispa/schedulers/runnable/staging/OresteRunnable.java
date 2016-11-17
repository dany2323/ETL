package lispa.schedulers.runnable.staging;

import java.sql.Timestamp;

import lispa.schedulers.facade.target.FillOresteFacade;

import org.apache.log4j.Logger;

public class OresteRunnable implements Runnable {
	
	private Logger logger;
	private Timestamp dataEsecuzioneDeleted;

	public OresteRunnable(Logger logger, Timestamp dataEsecuzioneDeleted) {
	
		this.logger = logger;
		this.dataEsecuzioneDeleted = dataEsecuzioneDeleted;
	
	}
	
	@Override
	public void run() {
		logger.info("START: FillStaging ORESTE");
		FillOresteFacade.delete(logger, dataEsecuzioneDeleted);
		FillOresteFacade.execute(logger);
		logger.info("STOP: FillStaging ORESTE");
	}

}
