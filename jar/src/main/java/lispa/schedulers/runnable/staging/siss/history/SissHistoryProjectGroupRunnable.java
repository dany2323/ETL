package lispa.schedulers.runnable.staging.siss.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectGroupDAO;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;

public class SissHistoryProjectGroupRunnable implements Runnable {

	public Logger logger;
	public SissHistoryProjectGroupRunnable(Logger logger) {
		this.logger = logger;
	}
	@Override
	public void run() {
		try {
			logger.debug("START SissHistoryProjectGroup.fill()");
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			
			int tentativi_deadlock = 0;
			ErrorManager.getInstance().resetDeadlock();
			boolean inDeadlock = false;
			SissHistoryProjectGroupDAO.delete();
			
			tentativi_deadlock++;
			logger.debug("Tentativo " + tentativi_deadlock);
			SissHistoryProjectGroupDAO.fillSissHistoryProjectGroup();
			inDeadlock = ErrorManager.getInstance().hasDeadLock();
			while(inDeadlock) {
				tentativi_deadlock++;
				logger.debug("inDeadlock, aspetto 3 minuti");
				TimeUnit.MINUTES.sleep(wait);
				logger.debug("Tentativo " + tentativi_deadlock);
				ErrorManager.getInstance().resetDeadlock();
				SissHistoryProjectGroupDAO.fillSissHistoryProjectGroup();
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
			}
			logger.debug("Fine tentativo " + tentativi_deadlock);
			
			logger.debug("STOP SissHistoryProjectGroup.fill()");
			
			
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
		

		
	}

}
