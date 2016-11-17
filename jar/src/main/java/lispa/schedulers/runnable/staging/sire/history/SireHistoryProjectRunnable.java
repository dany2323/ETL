package lispa.schedulers.runnable.staging.sire.history;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;

public class SireHistoryProjectRunnable implements Runnable {
	private long project_minRevision;
	private long polarion_maxRevision;
	public Logger logger;

	public SireHistoryProjectRunnable(long project_minRevision,long polarion_maxRevision, Logger logger) {
		this.project_minRevision = project_minRevision;
		this.polarion_maxRevision = polarion_maxRevision;
		this.logger = logger;
	}

	@Override
	public void run() {
		try {
			logger.debug("START SireHistoryProject.fill()");
			SireHistoryProjectDAO.fillSireHistoryProject(project_minRevision, polarion_maxRevision);
			logger.debug("STOP SireHistoryProject.fill()");
		}
		catch(Exception e) {
			logger.error(e.getMessage(),e);
			
		}
		
	}

}
