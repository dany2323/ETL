package lispa.schedulers.runnable.staging.siss.history;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryAttachmentDAO;
import lispa.schedulers.exception.DAOException;

public class SissHistoryAttachmentRunnable implements Runnable {
	
	private long attachment_minRevision, attachment_maxRevision;
	private Logger logger;
	
	public SissHistoryAttachmentRunnable(long attachment_minRevision,
			long attachment_maxRevision, Logger logger) {
		
		this.attachment_minRevision = attachment_minRevision;
		this.attachment_maxRevision = attachment_maxRevision;
		this.logger = logger;
	}

	

	@Override
	public void run() {
		try {
			logger.debug("START SissHistoryAttachment.fill()");
			SissHistoryAttachmentDAO.fillSissHistoryAttachment(attachment_minRevision, attachment_maxRevision);
			logger.debug("STOP SissHistoryAttachment.fill()");
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}