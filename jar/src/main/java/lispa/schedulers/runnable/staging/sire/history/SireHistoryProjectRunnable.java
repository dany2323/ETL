package lispa.schedulers.runnable.staging.sire.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;

public class SireHistoryProjectRunnable implements Runnable {
	private long project_minRevision;
	private long polarion_maxRevision;
	public Logger logger;

	public SireHistoryProjectRunnable(long project_minRevision,long polarion_maxRevision, Logger logger) {
		this.project_minRevision = project_minRevision;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}

	@Override
	public void run() {
		try {
			logger.debug("START SireHistoryProject.fill() - project_minRevision: "+project_minRevision+" polarion_maxRevision: "+polarion_maxRevision);
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			
			int tentativi_deadlock = 0;
			ErrorManager.getInstance().resetDeadlock();
			boolean inDeadlock = false;

			tentativi_deadlock++;
			logger.debug("Tentativo " + tentativi_deadlock);
			SireHistoryProjectDAO.fillSireHistoryProject(project_minRevision, polarion_maxRevision);
			inDeadlock = ErrorManager.getInstance().hasDeadLock();
			while(inDeadlock) {
				tentativi_deadlock++;
				logger.debug("inDeadlock, aspetto 3 minuti");
				TimeUnit.MINUTES.sleep(wait);
				logger.debug("Tentativo " + tentativi_deadlock);
				ErrorManager.getInstance().resetDeadlock();
				SireHistoryProjectDAO.delete(project_minRevision, polarion_maxRevision);
				SireHistoryProjectDAO.fillSireHistoryProject(project_minRevision, polarion_maxRevision);
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
			}
			logger.debug("Fine tentativo " + tentativi_deadlock);
			
			logger.debug("STOP SireHistoryProject.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
		
	}

}
