package lispa.schedulers.runnable.staging.siss.history;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemUserAssignedDAO;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class SissHistoryWorkitemUserAssignedRunnable implements Runnable {
	private Map<EnumWorkitemType, Long> minRevisionByType;
	private long polarion_maxRevision;
	public Logger logger; 

	public SissHistoryWorkitemUserAssignedRunnable(Map<EnumWorkitemType, Long> minRevisionByType, long polarion_maxRevision, Logger logger) {
		this.minRevisionByType = minRevisionByType;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
		
	}
	@Override
	public void run() {
		try{
			logger.debug("START SissHistoryWorkitemUserAss.fill()");
			int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_DEADLOCK_WAIT));
			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {
				logger.debug("START TYPE: SIRE " + type.toString());
				int tentativi_deadlock = 0;
				ErrorManager.getInstance().resetDeadlock();
				boolean inDeadlock = false;

				tentativi_deadlock++;
				logger.debug("Tentativo " + tentativi_deadlock);
				SissHistoryWorkitemUserAssignedDAO.fillSissHistoryWorkitemUserAssigned(type, minRevisionByType, polarion_maxRevision);
				inDeadlock = ErrorManager.getInstance().hasDeadLock();
				while(inDeadlock) {
					tentativi_deadlock++;
					logger.debug("inDeadlock, aspetto 3 minuti");
					TimeUnit.MINUTES.sleep(wait);
					logger.debug("Tentativo " + tentativi_deadlock);
					ErrorManager.getInstance().resetDeadlock();
					SissHistoryWorkitemUserAssignedDAO.delete();
					SissHistoryWorkitemUserAssignedDAO.fillSissHistoryWorkitemUserAssigned(type, minRevisionByType, polarion_maxRevision);
					inDeadlock = ErrorManager.getInstance().hasDeadLock();
				}
				logger.debug("Fine tentativo " + tentativi_deadlock);
				
			}
			logger.debug("STOP SissHistoryWorkitemUserAss.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
		
	}

}
