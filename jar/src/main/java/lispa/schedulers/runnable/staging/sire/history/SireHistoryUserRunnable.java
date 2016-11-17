package lispa.schedulers.runnable.staging.sire.history;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryUserDAO;

import org.apache.log4j.Logger;

public class SireHistoryUserRunnable implements Runnable {
	private long user_minRevision;
	private long polarion_maxRevision;
	public Logger logger;


	public SireHistoryUserRunnable(long user_minRevision,long polarion_maxRevision, Logger logger) {
		this.user_minRevision = user_minRevision;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}

	@Override
	public void run() {
		try{
			logger.debug("START SireHistoryUser.fill()");
			SireHistoryUserDAO.fillSireHistoryUser(user_minRevision, polarion_maxRevision);
			logger.debug("STOP SireHistoryUser.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
		
	}

}
