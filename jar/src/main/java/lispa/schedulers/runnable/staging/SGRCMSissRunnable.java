package lispa.schedulers.runnable.staging;

import static lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS;
import static lispa.schedulers.constant.DmAlmConstants.SCHEMA_CURRENT;
import static lispa.schedulers.constant.DmAlmConstants.SCHEMA_HISTORY;

import java.sql.Timestamp;

import lispa.schedulers.facade.target.FillSGRCMFacade;

import org.apache.log4j.Logger;

public class SGRCMSissRunnable implements Runnable {
	
	private Logger logger;
	private Timestamp dataEsecuzioneDeleted;
	
	public SGRCMSissRunnable(Logger logger, Timestamp dataEsecuzioneDeleted) {
		
		this.logger = logger;
		this.dataEsecuzioneDeleted = dataEsecuzioneDeleted;
		
	}

	@Override
	public void run() {
		logger.info("START FillStagingSgrCmSiss");
		
		// SISS CURRENT
		FillSGRCMFacade.delete(REPOSITORY_SISS, SCHEMA_CURRENT, logger, dataEsecuzioneDeleted);
		FillSGRCMFacade.execute(REPOSITORY_SISS, SCHEMA_CURRENT, logger);
		
		// SISS HISTORY
		FillSGRCMFacade.delete(REPOSITORY_SISS, SCHEMA_HISTORY, logger, dataEsecuzioneDeleted);
		FillSGRCMFacade.execute(REPOSITORY_SISS, SCHEMA_HISTORY, logger);
		
		logger.info("STOP FillStagingSgrCmSiss");
	}

}
