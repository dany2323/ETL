package lispa.schedulers.facade.calipso.staging;

import java.sql.SQLException;
import java.sql.Timestamp;
import lispa.schedulers.dao.calipso.StgCalipsoSchedaServizioDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import org.apache.log4j.Logger;

public class StagingCalipsoFacade {

	private static Logger logger = Logger.getLogger(StagingCalipsoFacade.class);
	
	public static void executeStaging(Timestamp dataEsecuzioneDelete) {
		try {
			
			if (ErrorManager.getInstance().hasError())
				return;

			logger.info("START StagingCalipsoFacade.executeStaging");

			deleteStaging(dataEsecuzioneDelete);
			fillStaging();

			logger.info("STOP StagingCalipsoFacade.executeStaging");
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}

	private static void deleteStaging(Timestamp dataEsecuzioneDelete) {

		try {
			logger.debug("START CALIPSO deleteStaging");
			StgCalipsoSchedaServizioDAO.deleteStaging(dataEsecuzioneDelete);
			logger.debug("STOP CALIPSO deleteStaging");

		} catch (DAOException | SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}
	
	private static void fillStaging() {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START StagingCalipsoFacade.fillStaging");

			StgCalipsoSchedaServizioDAO.fillStaging();
			
			logger.debug("STOP StagingCalipsoFacade.fillStaging");
		} catch (DAOException | SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
}
