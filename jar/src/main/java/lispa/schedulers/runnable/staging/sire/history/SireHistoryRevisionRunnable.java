package lispa.schedulers.runnable.staging.sire.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;

public class SireHistoryRevisionRunnable implements Runnable {
	private Timestamp revision_minRevision;
	private long polarion_maxRevision;
	public Logger logger;


	public SireHistoryRevisionRunnable(Timestamp revision_minRevision,long polarion_maxRevision,Logger logger) {
		this.revision_minRevision = revision_minRevision;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger; 
		
	}


	@Override
	public void run() {
		try {
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			
			logger.debug("START SireHistoryRevision.fill()");
			int tentativi_deadlock = 0;
			ErrorManager.getInstance().resetDeadlock();
			boolean inDeadlock = false;

			tentativi_deadlock++;
			logger.debug("Tentativo " + tentativi_deadlock);
			SireHistoryRevisionDAO.fillSireHistoryRevision(revision_minRevision, polarion_maxRevision);
			inDeadlock = ErrorManager.getInstance().hasDeadLock();
			while(inDeadlock) {
				tentativi_deadlock++;
				logger.debug("inDeadlock, aspetto 3 minuti");
				TimeUnit.MINUTES.sleep(wait);
				logger.debug("Tentativo " + tentativi_deadlock);
				ErrorManager.getInstance().resetDeadlock();
				SireHistoryRevisionDAO.delete();
				SireHistoryRevisionDAO.fillSireHistoryRevision(revision_minRevision, polarion_maxRevision);
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
			}
			logger.debug("Fine tentativo " + tentativi_deadlock);
			
			logger.debug("STOP SireHistoryRevision.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}

	}

}
