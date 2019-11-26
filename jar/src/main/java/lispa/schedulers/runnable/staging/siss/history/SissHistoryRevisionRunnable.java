package lispa.schedulers.runnable.staging.siss.history;

import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.exception.DAOException;

public class SissHistoryRevisionRunnable implements Runnable {
	
	private Timestamp minDate;
	private long maxRevision;
	private Logger logger;
	
	public SissHistoryRevisionRunnable(Timestamp minDate, long maxRevision, Logger logger) {
		
		this.minDate = minDate;
		this.maxRevision = maxRevision;
		this.logger = logger;
		
	}

	@Override
	public void run() {
		
		try {
			logger.debug("START SissHistoryRevision.fill()");
			SissHistoryRevisionDAO.fillSissHistoryRevision(minDate, maxRevision);
			logger.debug("STOP SissHistoryRevision.fill()");
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
