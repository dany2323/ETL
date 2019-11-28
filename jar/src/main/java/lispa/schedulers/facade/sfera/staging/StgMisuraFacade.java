package lispa.schedulers.facade.sfera.staging;

import java.sql.SQLException;
import java.util.Date;
import lispa.schedulers.dao.sfera.StgMisuraDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import org.apache.log4j.Logger;

public class StgMisuraFacade {
	
	private static Logger logger = Logger.getLogger(StgMisuraFacade.class); 
	
	public static void fillStgMisura() throws PropertiesReaderException {
		try {
			logger.debug("START FillStgMisura "+new Date());
			StgMisuraDAO.FillStgMisura();
			logger.debug("STOP FillStgMisura "+new Date());
		} catch (SQLException | DAOException e) {
			logger.error(e.getMessage(), e);
		}
		
	}
}