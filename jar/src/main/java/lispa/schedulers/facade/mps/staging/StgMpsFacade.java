package lispa.schedulers.facade.mps.staging;

import lispa.schedulers.dao.mps.StgMpsAttivitaDAO;
import lispa.schedulers.dao.mps.StgMpsContrattiDAO;
import lispa.schedulers.dao.mps.StgMpsFirmatariVerbaleDAO;
import lispa.schedulers.dao.mps.StgMpsRespContrattoDAO;
import lispa.schedulers.dao.mps.StgMpsRespOffertaDAO;
import lispa.schedulers.dao.mps.StgMpsRilasciDAO;
import lispa.schedulers.dao.mps.StgMpsRilasciVerbaliDAO;
import lispa.schedulers.dao.mps.StgMpsVerbaliDAO;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ErrorManager;
import org.apache.log4j.Logger;

public class StgMpsFacade {

	private static Logger logger = Logger.getLogger(StgMpsFacade.class);

	public static void fillStgMps() throws PropertiesReaderException {
		try {
			logger.debug("StgMpsFacade.fillStgMps - START fillStgMpsAttivita");
			StgMpsAttivitaDAO.fillStgMpsAttivita();
			logger.debug("StgMpsFacade.fillStgMps - STOP fillStgMpsAttivita");
			
			logger.debug("StgMpsFacade.fillStgMps - START fillStgMpsContratti");
			StgMpsContrattiDAO.fillStgMpsContratti();
			logger.debug("StgMpsFacade.fillStgMps - STOP fillStgMpsContratti");
		
			logger.debug("StgMpsFacade.fillStgMps - START fillStgMpsFirmatariVerbale");
			StgMpsFirmatariVerbaleDAO.fillStgMpsFirmatariVerbale();
			logger.debug("StgMpsFacade.fillStgMps - STOP fillStgMpsFirmatariVerbale");
			
			logger.debug("StgMpsFacade.fillStgMps - START fillStgMpsRespContratto");
			StgMpsRespContrattoDAO.fillStgMpsRespContratto();
			logger.debug("StgMpsFacade.fillStgMps - STOP fillStgMpsRespContratto");
			
			logger.debug("StgMpsFacade.fillStgMps - START fillStgMpsVerbali");
			StgMpsVerbaliDAO.fillStgMpsVerbali(); 
			logger.debug("StgMpsFacade.fillStgMps - STOP fillStgMpsVerbali");
			
			logger.debug("StgMpsFacade.fillStgMps - START fillStgMpsRilasciVerbali");
			StgMpsRilasciVerbaliDAO.fillStgMpsRilasciVerbali(); 
			logger.debug("StgMpsFacade.fillStgMps - STOP fillStgMpsRilasciVerbali");
			
			logger.debug("StgMpsFacade.fillStgMps - START fillStgMpsRilasci");
			StgMpsRilasciDAO.fillStgMpsRilasci(); 
			logger.debug("StgMpsFacade.fillStgMps - STOP fillStgMpsRilasci");
			
			logger.debug("StgMpsFacade.fillStgMps - START fillStgMpsRespOfferta");
			StgMpsRespOffertaDAO.fillStgMpsRespOfferta(); 
			logger.debug("StgMpsFacade.fillStgMps - STOP fillStgMpsRespOfferta");
	
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}

	public static void deleteStgMps() {

		try {
			logger.debug("StgMpsFacade.deleteStgMps - START deleteStgMpsAttivita");
			StgMpsAttivitaDAO.delete();
			logger.debug("StgMpsFacade.deleteStgMps - STOP deleteStgMpsAttivita");

			logger.debug("StgMpsFacade.deleteStgMps - START deleteStgMpsContratti");
			StgMpsContrattiDAO.delete();
			logger.debug("StgMpsFacade.deleteStgMps - STOP deleteStgMpsContratti");
			
			logger.debug("StgMpsFacade.deleteStgMps - START deleteStgMpsFirmatariVerbale");
			StgMpsFirmatariVerbaleDAO.delete();
			logger.debug("StgMpsFacade.deleteStgMps - STOP deleteStgMpsFirmatariVerbale");
			
			logger.debug("StgMpsFacade.deleteStgMps - START deleteStgMpsRespContratto");
			StgMpsRespContrattoDAO.delete();
			logger.debug("StgMpsFacade.deleteStgMps - STOP deleteStgMpsRespContratto");
			
			logger.debug("StgMpsFacade.deleteStgMps - START deleteStgMpsVerbali");
			StgMpsVerbaliDAO.delete();
			logger.debug("StgMpsFacade.deleteStgMps - STOP deleteStgMpsVerbali");
			
			logger.debug("StgMpsFacade.deleteStgMps - START deleteStgMpsRilasciVerbali");
			StgMpsRilasciVerbaliDAO.delete();
			logger.debug("StgMpsFacade.deleteStgMps - STOP deleteStgMpsRilasciVerbali");

			logger.debug("StgMpsFacade.deleteStgMps - START deleteStgMpsRilasci");
			StgMpsRilasciDAO.delete();
			logger.debug("StgMpsFacade.deleteStgMps - STOP deleteStgMpsRilasci");

			logger.debug("StgMpsFacade.deleteStgMps - START deleteStgMpsRespOfferta");
			StgMpsRespOffertaDAO.delete();
			logger.debug("StgMpsFacade.deleteStgMps - STOP deleteStgMpsRespOfferta");

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
}
