package lispa.schedulers.runnable.staging.sire.history;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryUserDAO;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;

public class SireHistoryUserRunnable implements Runnable {
	private long user_minRevision;
	private long polarion_maxRevision;
	public Logger logger;


	public SireHistoryUserRunnable(long user_minRevision,long polarion_maxRevision, Logger logger) {
		this.user_minRevision = user_minRevision;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}

	@Override
	public void run() {
		try{
			logger.debug("START SireHistoryUser.fill() - user_minRevision: "+user_minRevision+" polarion_maxRevision: "+polarion_maxRevision);
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			
			int tentativi_deadlock = 0;
			ErrorManager.getInstance().resetDeadlock();
			boolean inDeadlock = false;

			tentativi_deadlock++;
			logger.debug("Tentativo " + tentativi_deadlock);
			SireHistoryUserDAO.fillSireHistoryUser(user_minRevision, polarion_maxRevision);
			inDeadlock = ErrorManager.getInstance().hasDeadLock();
			while(inDeadlock) {
				tentativi_deadlock++;
				logger.debug("inDeadlock, aspetto 3 minuti");
				TimeUnit.MINUTES.sleep(wait);
				logger.debug("Tentativo " + tentativi_deadlock);
				ErrorManager.getInstance().resetDeadlock();
				SireHistoryUserDAO.delete(user_minRevision, polarion_maxRevision);
				SireHistoryUserDAO.fillSireHistoryUser(user_minRevision, polarion_maxRevision);
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
			}
			logger.debug("Fine tentativo " + tentativi_deadlock);
			
			logger.debug("STOP SireHistoryUser.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
		
	}

}
