package lispa.schedulers.runnable.staging.siss.history;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectGroupDAO;
import lispa.schedulers.exception.DAOException;


public class SissHistoryProjectGroupRunnable implements Runnable {
	private Logger logger;
	
	public SissHistoryProjectGroupRunnable(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void run() {
		try {	
			logger.debug("START SissHistoryProjectGroup.fill()");
			SissHistoryProjectGroupDAO.fillSissHistoryProjectGroup();
			logger.debug("STOP SissHistoryProjectGroup.fill()");
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}