package lispa.schedulers.runnable.staging.sire.history;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;

public class SireHistoryRevisionRunnable implements Runnable {
	private Timestamp revision_minRevision;
	private long polarion_maxRevision;
	public Logger logger;


	public SireHistoryRevisionRunnable(Timestamp revision_minRevision,long polarion_maxRevision,Logger logger) {
		this.revision_minRevision = revision_minRevision;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger; 
		
	}


	@Override
	public void run() {
		try {
			logger.debug("START SireHistoryRevision.fill()");
			SireHistoryRevisionDAO.fillSireHistoryRevision(revision_minRevision, polarion_maxRevision);
			logger.debug("STOP SireHistoryRevision.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}

	}

}
