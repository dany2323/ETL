package lispa.schedulers.runnable.staging.sire.history;

import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.svn.SIREUserRolesSgrXML;

public class SireUserRolesSgrRunnable implements Runnable {

	private Logger logger;

	public SireUserRolesSgrRunnable(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void run() {

		try {
			logger.debug("START fillUserRolesSgr SIRE " + new Date());
			logger.debug("INIZIO Ruoli Globali");
			SIREUserRolesSgrXML.fillUserRolesSgr("");
			logger.debug("FINE Ruoli Globali");
			List<String> cLocations = SireHistoryProjectDAO.getProjectCLocation();
			for (String cLocation : cLocations) {
				logger.debug("INIZIO Ruoli " + cLocation);
				SIREUserRolesSgrXML.fillUserRolesSgr(cLocation);
				logger.debug("FINE Ruoli " + cLocation);
			}
			logger.debug("STOP fillUserRolesSgr SIRE " + new Date());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
}
