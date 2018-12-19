package lispa.schedulers.facade.sfera.staging;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.dao.sfera.StgMisuraDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ErrorManager;

import org.apache.log4j.Logger;

/**
 * Provvede al caricamento delle tabelle di staging
 * -DMAlm_STG_MISURA con i dati presenti nelle
 * relative fonti presenti alla data di elaborazione
 *
 * @param logger
 */

public class StgMisuraFacade {
	
	private static Logger logger = Logger.getLogger(StgMisuraFacade.class); 
	
	private StgMisuraFacade() {}
	public static void fillStgMisura() throws PropertiesReaderException {
		try {
			logger.debug("START FillStgMisura "+new Date());
			StgMisuraDAO.FillStgMisura();
			logger.debug("STOP FillStgMisura "+new Date());
		}
		catch (SQLException | DAOException e) 
		{
			logger.error(e.getMessage(), e);
		
		}
		
	}
	
	public static void deleteStgMisura(Logger logger, Timestamp dataEsecuzioneDelete) {

		try {
			logger.debug("START deleteStgMisura older than "+dataEsecuzioneDelete);
			StgMisuraDAO.delete(dataEsecuzioneDelete);
			logger.debug("STOP deleteStgMisura older than "+dataEsecuzioneDelete);

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} 

	}

}
