package lispa.schedulers.runnable.staging.sire.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectGroupDAO;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;

public class SireHistoryProjectGroupRunnable implements Runnable {

	public Logger logger;
	public SireHistoryProjectGroupRunnable(Logger logger) {
		this.logger = logger;
	}
	@Override
	public void run() {
		try {
			logger.debug("START SireHistoryProjectGroup.fill()");
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			
			int tentativi_deadlock = 0;
			ErrorManager.getInstance().resetDeadlock();
			boolean inDeadlock = false;

			tentativi_deadlock++;
			logger.debug("Tentativo " + tentativi_deadlock);
			SireHistoryProjectGroupDAO.fillSireHistoryProjectGroup();
			inDeadlock = ErrorManager.getInstance().hasDeadLock();
			while(inDeadlock) {
				tentativi_deadlock++;
				logger.debug("inDeadlock, aspetto 3 minuti");
				TimeUnit.MINUTES.sleep(wait);
				logger.debug("Tentativo " + tentativi_deadlock);
				ErrorManager.getInstance().resetDeadlock();
				SireHistoryProjectGroupDAO.delete();
				SireHistoryProjectGroupDAO.fillSireHistoryProjectGroup();
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
			}
			logger.debug("Fine tentativo " + tentativi_deadlock);
			
			logger.debug("STOP SireHistoryProjectGroup.fill()");
			
			
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
		

		
	}

}
