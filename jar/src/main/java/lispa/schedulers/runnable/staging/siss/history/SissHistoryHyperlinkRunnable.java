package lispa.schedulers.runnable.staging.siss.history;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryHyperlinkDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class SissHistoryHyperlinkRunnable implements Runnable {
	private Map<EnumWorkitemType, Long> minRevisionByType;
	private long maxRevision;
	private Logger logger;
	
	public SissHistoryHyperlinkRunnable(Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision,
			Logger logger) {
		
		this.minRevisionByType = minRevisionByType;
		this.maxRevision = maxRevision;
		this.logger = logger;
	}



	@Override
	public void run() {
		try {
			logger.debug("START SissHistoryHyperlink.fill()");
			SissHistoryHyperlinkDAO.fillSissHistoryHyperlink(minRevisionByType, maxRevision);
			logger.debug("STOP SissHistoryHyperlink.fill()");
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
