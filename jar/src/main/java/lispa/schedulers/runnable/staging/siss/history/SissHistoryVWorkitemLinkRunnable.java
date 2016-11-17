package lispa.schedulers.runnable.staging.siss.history;

import java.sql.SQLException;

import lispa.schedulers.dao.sgr.siss.history.VSissHistoryWorkitemLinkDAO;
import lispa.schedulers.exception.DAOException;

import org.apache.log4j.Logger;

public class SissHistoryVWorkitemLinkRunnable implements Runnable {
	private Logger logger;

	public SissHistoryVWorkitemLinkRunnable(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void run() {
		try {
			logger.debug("START VSissHistoryLink.fill()");
			VSissHistoryWorkitemLinkDAO.fillVSissWorkitemLink();
			logger.debug("STOP VSissHistoryLink.fill()");
		} 
		catch (DAOException e) {
			logger.error(e.getMessage(), e);
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}
		
	}

}
