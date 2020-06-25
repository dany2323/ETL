package lispa.schedulers.facade;

import org.apache.log4j.Logger;
import lispa.schedulers.dao.UtilsDAO;
import lispa.schedulers.exception.DAOException;

public class UtilsFonteFacade {
	
private static Logger logger = Logger.getLogger(UtilsFonteFacade.class);
	
	public static void caricamentoFonte(String fonte, String stato) {
		logger.info("START SET FONTE "+fonte+" - STATO "+stato);
		try {
			UtilsDAO.setCaricamentoFonte(fonte, stato);
		} catch (DAOException e) {
			logger.error(e.getMessage());
		}
		logger.info("STOP SET FONTE "+fonte+" - STATO "+stato);
	}
}