package lispa.schedulers.facade.calipso.staging;

import java.sql.SQLException;
import java.sql.Timestamp;

import lispa.schedulers.dao.mps.StgMpsAttivitaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ErrorManager;

import org.apache.log4j.Logger;

public class StagingCalipsoFacade {

	private static Logger logger = Logger.getLogger(StagingCalipsoFacade.class);

	public static void executeStaging(Timestamp dataEsecuzioneDelete) throws PropertiesReaderException {
		try {
			
			if (ErrorManager.getInstance().hasError())
				return;

			logger.info("START StagingElettraFacade.executeStaging");

			deleteStaging(dataEsecuzioneDelete);
			fillStaging();

			logger.info("STOP StagingElettraFacade.executeStaging");
			
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
			
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
			
		}
	}

	private static void deleteStaging(Timestamp dataEsecuzioneDelete) {

		try {
			logger.debug("START CALIPSO deleteStagingCalipso");
			StgMpsAttivitaDAO.delete();
			logger.debug("STOP CALIPSO deleteStagingCalipso");

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}
}
