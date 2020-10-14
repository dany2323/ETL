package lispa.schedulers.runnable.staging.sire.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.util.Map;
import java.util.concurrent.TimeUnit;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryHyperlinkDAO;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import org.apache.log4j.Logger;

public class SireHistoryHyperlinkRunnable implements Runnable {
	private Map<String, Long> minRevisionByType;
	private long polarion_maxRevision;
	public Logger logger; 

	public SireHistoryHyperlinkRunnable(Map<String, Long> minRevisionByType, long polarion_maxRevision, Logger logger) {
		this.minRevisionByType = minRevisionByType;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}
	@Override
	public void run() {
		try{
			logger.debug("START SireHistoryHyperlink.fill()");
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			for (String type : minRevisionByType.keySet()) {
				logger.debug("START TYPE: SIRE " + type.toString() + " - minRevisionByType: "+minRevisionByType.get(type)+" polarion_maxRevision: "+polarion_maxRevision);
				int tentativi_deadlock = 0;
				ErrorManager.getInstance().resetDeadlock();
				boolean inDeadlock = false;

				tentativi_deadlock++;
				logger.debug("Tentativo " + tentativi_deadlock);
				SireHistoryHyperlinkDAO.fillSireHistoryHyperlink(type, minRevisionByType, polarion_maxRevision);
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
				while(inDeadlock) {
					tentativi_deadlock++;
					logger.debug("inDeadlock, aspetto 3 minuti");
					TimeUnit.MINUTES.sleep(wait);
					logger.debug("Tentativo " + tentativi_deadlock);
					ErrorManager.getInstance().resetDeadlock();
					SireHistoryHyperlinkDAO.delete(type, minRevisionByType, polarion_maxRevision);
					SireHistoryHyperlinkDAO.fillSireHistoryHyperlink(type, minRevisionByType, polarion_maxRevision);
					inDeadlock = ErrorManager.getInstance().hasDeadLock();
				}
				logger.debug("Fine tentativo " + tentativi_deadlock);
				
			}
			logger.debug("STOP SireHistoryHyperlink.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
	}


}
