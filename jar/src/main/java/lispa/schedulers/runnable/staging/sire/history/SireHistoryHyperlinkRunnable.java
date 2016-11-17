package lispa.schedulers.runnable.staging.sire.history;

import java.util.Map;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryHyperlinkDAO;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

public class SireHistoryHyperlinkRunnable implements Runnable {
	private Map<Workitem_Type, Long> minRevisionByType;
	private long polarion_maxRevision;
	public Logger logger; 

	public SireHistoryHyperlinkRunnable(Map<Workitem_Type, Long> minRevisionByType, long polarion_maxRevision, Logger logger) {
		this.minRevisionByType = minRevisionByType;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}
	@Override
	public void run() {
		try{
			logger.debug("START SireHistoryHyperlink.fill()");
			SireHistoryHyperlinkDAO.fillSireHistoryHyperlink(minRevisionByType, polarion_maxRevision);
			logger.debug("STOP SireHistoryHyperlink.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
	}


}
