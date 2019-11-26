package lispa.schedulers.runnable.staging.siss.history;

import java.sql.SQLException;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.exception.DAOException;

public class SissHistoryProjectRunnable implements Runnable {
	
	private long minRevisionProject, maxRevision;
	private Logger logger;
	
	public SissHistoryProjectRunnable(long minRevisionProject, long maxRevision, Logger logger) {
		
		this.logger = logger;
		this.minRevisionProject = minRevisionProject;
		this.maxRevision = maxRevision;
		
	}

	@Override
	public void run() {
		
		try {
			logger.debug("START SissHistoryProject.fill()");
			SissHistoryProjectDAO.fillSissHistoryProject(minRevisionProject, maxRevision);
			logger.debug("STOP SissHistoryProject.fill()");
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}