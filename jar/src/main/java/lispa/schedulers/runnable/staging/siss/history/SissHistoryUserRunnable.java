package lispa.schedulers.runnable.staging.siss.history;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryUserDAO;
import lispa.schedulers.exception.DAOException;

public class SissHistoryUserRunnable implements Runnable {
	
	private long minRevisionUser, maxRevision;
	private Logger logger;
	
	public SissHistoryUserRunnable(long minRevisionUser, long maxRevision, Logger logger) {
		
		this.logger = logger;
		this.maxRevision = maxRevision;
		this.minRevisionUser = minRevisionUser;
		
	}

	@Override
	public void run() {
		
		try {
			logger.debug("START SissHistoryUser.fill()");
			SissHistoryUserDAO.fillSissHistoryUser(minRevisionUser, maxRevision);
			logger.debug("STOP SissHistoryUser.fill()");
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
