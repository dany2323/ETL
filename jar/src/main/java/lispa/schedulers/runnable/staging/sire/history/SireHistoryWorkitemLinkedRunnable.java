package lispa.schedulers.runnable.staging.sire.history;

import java.sql.SQLException;
import java.util.Map;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemLinkedDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

public class SireHistoryWorkitemLinkedRunnable implements Runnable{
	private Map<Workitem_Type, Long> minRevisionByType;
	private long polarion_maxRevision;
	public Logger logger;


	public SireHistoryWorkitemLinkedRunnable(Map<Workitem_Type, Long> minRevisionByType ,long polarion_maxRevision,Logger logger) {
		this.minRevisionByType = minRevisionByType;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}


	@Override
	public void run() {
		try {
			logger.debug("START SireHistoryWorkitemLinked.fill()");
			SireHistoryWorkitemLinkedDAO.fillSireHistoryWorkitemLinked(minRevisionByType, polarion_maxRevision);
			logger.debug("STOP SireHistoryWorkitemLinked.fill()");
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
