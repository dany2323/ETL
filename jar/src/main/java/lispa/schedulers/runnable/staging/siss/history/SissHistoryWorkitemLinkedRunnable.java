package lispa.schedulers.runnable.staging.siss.history;

import java.sql.SQLException;
import java.util.Map;
import org.apache.log4j.Logger;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemLinkedDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class SissHistoryWorkitemLinkedRunnable implements Runnable {
	
	private Logger logger;
	Map<EnumWorkitemType, Long> minRevisionsByType;
	private long polarion_maxRevision;
	
	public SissHistoryWorkitemLinkedRunnable(Map<EnumWorkitemType, Long> minRevisionsByType, long polarion_maxRevision, Logger logger) {
		
		this.logger = logger;
		this.minRevisionsByType = minRevisionsByType;
		this.polarion_maxRevision = polarion_maxRevision;
		
	}

	@Override
	public void run() {
		
		try {
			
			logger.debug("START SissHistoryWorkitemLinked.fill()");
			SissHistoryWorkitemLinkedDAO.fillSissHistoryWorkitemLinked(minRevisionsByType, polarion_maxRevision);
			logger.debug("STOP SissHistoryWorkitemLinked.fill()");
			
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
