package lispa.schedulers.runnable.staging.siss.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryHyperlinkDAO;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import org.apache.log4j.Logger;

public class SissHistoryHyperlinkRunnable implements Runnable {
	private Map<String, Long> minRevisionByType;
	private long polarion_maxRevision;
	public Logger logger; 

	public SissHistoryHyperlinkRunnable(Map<String, Long> minRevisionByType, long polarion_maxRevision, Logger logger) {
		this.minRevisionByType = minRevisionByType;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}
	@Override
	public void run() {
		try{
			logger.debug("START SissHistoryHyperlink.fill()");
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			for (String type : minRevisionByType.keySet()) {
				logger.debug("START TYPE: SIRE " + type.toString());
				int tentativi_deadlock = 0;
				ErrorManager.getInstance().resetDeadlock();
				boolean inDeadlock = false;

				tentativi_deadlock++;
				logger.debug("Tentativo " + tentativi_deadlock);
				SissHistoryHyperlinkDAO.fillSissHistoryHyperlink(type, minRevisionByType, polarion_maxRevision);
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
				while(inDeadlock) {
					tentativi_deadlock++;
					logger.debug("inDeadlock, aspetto 3 minuti");
					TimeUnit.MINUTES.sleep(wait);
					logger.debug("Tentativo " + tentativi_deadlock);
					ErrorManager.getInstance().resetDeadlock();
					SissHistoryHyperlinkDAO.delete();
					SissHistoryHyperlinkDAO.fillSissHistoryHyperlink(type, minRevisionByType, polarion_maxRevision);
					inDeadlock = ErrorManager.getInstance().hasDeadLock();
				}
				logger.debug("Fine tentativo " + tentativi_deadlock);
				
			}
			logger.debug("STOP SissHistoryHyperlink.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
	}


}
