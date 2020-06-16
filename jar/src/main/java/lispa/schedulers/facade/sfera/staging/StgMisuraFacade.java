package lispa.schedulers.facade.sfera.staging;

import java.util.Date;
import lispa.schedulers.dao.sfera.StgMisuraDAO;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ErrorManager;
import org.apache.log4j.Logger;

public class StgMisuraFacade {
	
	private static Logger logger = Logger.getLogger(StgMisuraFacade.class); 
	
	public static void fillStgMisura() throws PropertiesReaderException {
		try {
			logger.debug("START FillStgMisura "+new Date());
			StgMisuraDAO.fillStgMisura();
			logger.debug("STOP FillStgMisura "+new Date());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
		
	}
}