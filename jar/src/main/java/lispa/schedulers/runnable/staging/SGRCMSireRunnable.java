package lispa.schedulers.runnable.staging;

import static lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE;
import static lispa.schedulers.constant.DmAlmConstants.SCHEMA_CURRENT;
import static lispa.schedulers.constant.DmAlmConstants.SCHEMA_HISTORY;

import java.sql.Timestamp;

import lispa.schedulers.facade.target.FillSGRCMFacade;

import org.apache.log4j.Logger;

public class SGRCMSireRunnable implements Runnable {
	
	private Logger logger;
	private Timestamp dataEsecuzioneDeleted;
	
	public SGRCMSireRunnable(Logger logger, Timestamp dataEsecuzioneDeleted) {
		
		this.logger = logger;
		this.dataEsecuzioneDeleted = dataEsecuzioneDeleted;
		
	}

	@Override
	public void run() {
		logger.info("START FillStagingSgrCmSire");
				
		// SIRE CURRENT
		FillSGRCMFacade.delete(REPOSITORY_SIRE, SCHEMA_CURRENT, logger, dataEsecuzioneDeleted);
		FillSGRCMFacade.execute(REPOSITORY_SIRE, SCHEMA_CURRENT, logger);
		
		// SIRE HISTORY
		FillSGRCMFacade.delete(REPOSITORY_SIRE, SCHEMA_HISTORY, logger, dataEsecuzioneDeleted);
		FillSGRCMFacade.execute(REPOSITORY_SIRE, SCHEMA_HISTORY, logger);
		
		logger.info("STOP FillStagingSgrCmSire");
	}

}
