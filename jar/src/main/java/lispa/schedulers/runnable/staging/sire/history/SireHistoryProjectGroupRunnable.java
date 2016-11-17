package lispa.schedulers.runnable.staging.sire.history;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectGroupDAO;

public class SireHistoryProjectGroupRunnable implements Runnable {

	public Logger logger;
	public SireHistoryProjectGroupRunnable(Logger logger) {
		this.logger = logger;
	}
	@Override
	public void run() {
		try {
			logger.debug("START SireHistoryProjectGroup.fill()");
			SireHistoryProjectGroupDAO.fillSireHistoryProjectGroup();
			logger.debug("STOP SireHistoryProjectGroup.fill()");
			
			
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
		

		
	}

}
