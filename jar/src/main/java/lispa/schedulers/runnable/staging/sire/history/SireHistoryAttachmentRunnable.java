package lispa.schedulers.runnable.staging.sire.history;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryAttachmentDAO;

import org.apache.log4j.Logger;

public class SireHistoryAttachmentRunnable implements Runnable {
	private long attachment_minRevision;
	private long polarion_maxRevision;
	public Logger logger; 

	public SireHistoryAttachmentRunnable(long attachment_minRevision,long polarion_maxRevision, Logger logger) {
		this.attachment_minRevision = attachment_minRevision;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}

	@Override
	public void run() {
		try {
		logger.debug("START SireHistoryAttachment.fill()");
		SireHistoryAttachmentDAO.fillSireHistoryAttachment(attachment_minRevision, polarion_maxRevision);
		logger.debug("STOP SireHistoryAttachment.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
	}

}
