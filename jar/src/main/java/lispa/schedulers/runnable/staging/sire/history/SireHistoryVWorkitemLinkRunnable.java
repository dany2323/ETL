package lispa.schedulers.runnable.staging.sire.history;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.sire.history.VSireHistoryWorkitemLinkDAO;



public class SireHistoryVWorkitemLinkRunnable implements Runnable{
	
	public Logger logger;
	

	
	public SireHistoryVWorkitemLinkRunnable(Logger logger) {
		this.logger = logger;
	}



	@Override
	public void run() {
		
		try {
			logger.debug("START VSireHistoryLink.fill()");
			VSireHistoryWorkitemLinkDAO.fillVSireWorkitemLink();
			logger.debug("STOP VSireHistoryLink.fill()");
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			
		}
		
		
	}

}
