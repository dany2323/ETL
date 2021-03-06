package lispa.schedulers.facade.elettra;

import java.sql.SQLException;
import java.sql.Timestamp;

import lispa.schedulers.dao.elettra.StgElAmbienteTecnologicoClassificatoriDAO;
import lispa.schedulers.dao.elettra.StgElAmbienteTecnologicoDAO;
import lispa.schedulers.dao.elettra.StgElClassificatoriDAO;
import lispa.schedulers.dao.elettra.StgElFunzionalitaDAO;
import lispa.schedulers.dao.elettra.StgElModuliDAO;
import lispa.schedulers.dao.elettra.StgElPersonaleDAO;
import lispa.schedulers.dao.elettra.StgElProdottiDAO;
import lispa.schedulers.dao.elettra.StgElUnitaOrganizzativeDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ErrorManager;

import org.apache.log4j.Logger;

/**
 * Provvede al caricamento delle tabelle di staging Elettra con i dati presenti
 * nelle tabelle fonte relative
 * 
 * @param logger
 */

public class StagingElettraFacade {
	private static Logger logger = Logger.getLogger(StagingElettraFacade.class);
	
	public static void executeStaging(Timestamp dataEsecuzioneDelete) {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.info("START StagingElettraFacade.executeStaging");

			deleteStaging(dataEsecuzioneDelete);
			fillStaging();

			logger.info("STOP StagingElettraFacade.executeStaging");
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}
	
	private static void deleteStaging(Timestamp dataEsecuzioneDelete) {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START StagingElettraFacade.deleteStaging older than "
					+ dataEsecuzioneDelete);

			StgElUnitaOrganizzativeDAO.deleteStaging(dataEsecuzioneDelete);
			StgElPersonaleDAO.deleteStaging(dataEsecuzioneDelete);
			StgElClassificatoriDAO.deleteStaging(dataEsecuzioneDelete);
			StgElProdottiDAO.deleteStaging(dataEsecuzioneDelete);
			StgElModuliDAO.deleteStaging(dataEsecuzioneDelete);
			StgElFunzionalitaDAO.deleteStaging(dataEsecuzioneDelete);
			StgElAmbienteTecnologicoDAO.deleteStaging(dataEsecuzioneDelete);
			StgElAmbienteTecnologicoClassificatoriDAO.deleteStaging(dataEsecuzioneDelete);

			logger.debug("STOP StagingElettraFacade.deleteStaging");
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}

	private static void fillStaging() throws PropertiesReaderException {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START StagingElettraFacade.fillStaging");

			StgElUnitaOrganizzativeDAO.fillStaging();
			StgElPersonaleDAO.fillStaging();
			StgElClassificatoriDAO.fillStaging();
			StgElProdottiDAO.fillStaging();
			StgElModuliDAO.fillStaging();
			StgElFunzionalitaDAO.fillStaging();
			StgElAmbienteTecnologicoDAO.fillStaging();
			StgElAmbienteTecnologicoClassificatoriDAO.fillStaging();
			
			logger.debug("STOP StagingElettraFacade.fillStaging");
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}

}
