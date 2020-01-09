package lispa.schedulers.runnable.staging.siss.history;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryAttachmentDAO;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class SissHistoryAttachmentRunnable implements Runnable {
	private long attachment_minRevision;
	private long polarion_maxRevision;
	public Logger logger; 

	public SissHistoryAttachmentRunnable(long attachment_minRevision,long polarion_maxRevision, Logger logger) {
		this.attachment_minRevision = attachment_minRevision;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}

	@Override
	public void run() {
		try {
		logger.debug("START SissHistoryAttachment.fill()");
		int wait = Integer.parseInt(DmAlmConfigReader.getInstance()
				.getProperty(DMALM_DEADLOCK_WAIT));
		
		int tentativi_deadlock = 0;
		ErrorManager.getInstance().resetDeadlock();
		boolean inDeadlock = false;

		tentativi_deadlock++;
		logger.debug("Tentativo " + tentativi_deadlock);
		SissHistoryAttachmentDAO.fillSissHistoryAttachment(attachment_minRevision, polarion_maxRevision);
		inDeadlock = ErrorManager.getInstance().hasDeadLock();
		while(inDeadlock) {
			tentativi_deadlock++;
			logger.debug("inDeadlock, aspetto 3 minuti");
			TimeUnit.MINUTES.sleep(wait);
			logger.debug("Tentativo " + tentativi_deadlock);
			ErrorManager.getInstance().resetDeadlock();
			SissHistoryAttachmentDAO.delete();
			SissHistoryAttachmentDAO.fillSissHistoryAttachment(attachment_minRevision, polarion_maxRevision);
			inDeadlock = ErrorManager.getInstance().hasDeadLock();
		}
		logger.debug("Fine tentativo " + tentativi_deadlock);
		
		logger.debug("STOP SissHistoryAttachment.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
	}

}
