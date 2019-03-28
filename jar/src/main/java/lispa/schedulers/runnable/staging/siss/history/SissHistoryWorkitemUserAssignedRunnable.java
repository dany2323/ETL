package lispa.schedulers.runnable.staging.siss.history;

import java.sql.SQLException;
import java.util.Map;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemUserAssignedDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import org.apache.log4j.Logger;

public class SissHistoryWorkitemUserAssignedRunnable implements Runnable {
	
	private Logger logger;
	Map<EnumWorkitemType, Long> minRevisionByType;
	private long polarion_maxRevision;
	
	public SissHistoryWorkitemUserAssignedRunnable(Map<EnumWorkitemType, Long> minRevisionByType, long polarion_maxRevision, Logger logger) {
		
		this.logger = logger;
		this.polarion_maxRevision = polarion_maxRevision;
		this.minRevisionByType = minRevisionByType;
		
	}

	@Override
	public void run() {
		
		try {
			
			logger.debug("START SissHistoryWorkitemUserAss.fill()");
			SissHistoryWorkitemUserAssignedDAO.fillSissHistoryWorkitemUserAssigned(minRevisionByType, polarion_maxRevision);
			logger.debug("STOP SissHistoryWorkitemUserAss.fill()");
		
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
		}

	}

}
