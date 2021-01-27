package lispa.schedulers.facade;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.UtilsDAO;
import lispa.schedulers.exception.DAOException;

public class KillBoSessionFacade {
	
	private static Logger logger = Logger.getLogger(KillBoSessionFacade.class);
	
	public static void execute() {
		logger.info("START KILL_BO_SESSIONS PROCEDURE");
		try {
			UtilsDAO.killsBOSessions();
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		logger.info("STOP KILL_BO_SESSIONS PROCEDURE");
	}
}
