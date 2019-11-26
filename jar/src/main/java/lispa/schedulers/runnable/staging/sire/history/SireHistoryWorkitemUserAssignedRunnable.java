package lispa.schedulers.runnable.staging.sire.history;

import java.util.Map;
import org.apache.log4j.Logger;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemUserAssignedDAO;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class SireHistoryWorkitemUserAssignedRunnable implements Runnable {
	private Map<EnumWorkitemType, Long> minRevisionByType;
	private long polarion_maxRevision;
	public Logger logger; 

	public SireHistoryWorkitemUserAssignedRunnable(Map<EnumWorkitemType, Long> minRevisionByType, long polarion_maxRevision, Logger logger) {
		this.minRevisionByType = minRevisionByType;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
		
	}
	@Override
	public void run() {
		try{
			logger.debug("START SireHistoryWorkitemUserAss.fill()");
			SireHistoryWorkitemUserAssignedDAO.fillSireHistoryWorkitemUserAssigned(minRevisionByType, polarion_maxRevision);
			logger.debug("STOP SireHistoryWorkitemUserAss.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
		
	}

}
