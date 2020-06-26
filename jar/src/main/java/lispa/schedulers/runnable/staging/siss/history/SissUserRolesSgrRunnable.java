package lispa.schedulers.runnable.staging.siss.history;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.svn.SISSUserRolesSgrXML;

public class SissUserRolesSgrRunnable implements Runnable {

	private Logger logger;

	public SissUserRolesSgrRunnable(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void run() {

		try {
			logger.debug("START fillUserRolesSgr SISS " + new Date());
			logger.debug("INIZIO Ruoli Globali");
			SISSUserRolesSgrXML.fillUserRolesSgr("");
			logger.debug("FINE Ruoli Globali");
			List<String> cLocations = SissHistoryProjectDAO.getProjectCLocation();
			for (String cLocation : cLocations) {
				logger.debug("INIZIO Ruoli " + cLocation);
				SISSUserRolesSgrXML.fillUserRolesSgr(cLocation);
				logger.debug("FINE Ruoli " + cLocation);
			}
			logger.debug("STOP fillUserRolesSgr SISS " + new Date());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
}
