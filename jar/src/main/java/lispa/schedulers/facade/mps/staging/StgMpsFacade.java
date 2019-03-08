package lispa.schedulers.facade.mps.staging;

import lispa.schedulers.dao.mps.StgMpsAttivitaDAO;
import lispa.schedulers.dao.mps.StgMpsContrattiDAO;
import lispa.schedulers.dao.mps.StgMpsFirmatariVerbaleDAO;
import lispa.schedulers.dao.mps.StgMpsRespContrattoDAO;
import lispa.schedulers.dao.mps.StgMpsRespOffertaDAO;
import lispa.schedulers.dao.mps.StgMpsRilasciDAO;
import lispa.schedulers.dao.mps.StgMpsRilasciVerbaliDAO;
import lispa.schedulers.dao.mps.StgMpsVerbaliDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;

import org.apache.log4j.Logger;

public class StgMpsFacade {

	private static Logger logger = Logger.getLogger(StgMpsFacade.class);

	public static void recoverStaging() {
		try {

			logger.debug("StgMpsFacade.recoverStaging - START recoverStgMpsAttivita");
			StgMpsAttivitaDAO.recoverStgMpsAttivita();
			logger.debug("StgMpsFacade.recoverStaging - STOP recoverStgMpsAttivita");

			logger.debug("StgMpsFacade.recoverStaging - START recoverStgMpsContratti");
			StgMpsContrattiDAO.recoverStgMpsContratti();
			logger.debug("StgMpsFacade.recoverStaging - STOP recoverStgMpsContratti");
			
			logger.debug("StgMpsFacade.recoverStaging - START recoverStgMpsFirmatariVerbale");
			StgMpsFirmatariVerbaleDAO.recoverStgMpsFirmatariVerbale();
			logger.debug("StgMpsFacade.recoverStaging - STOP recoverStgMpsFirmatariVerbale");
			
			logger.debug("StgMpsFacade.recoverStaging - START recoverStgMpsRespContratto");
			StgMpsRespContrattoDAO.recoverStgMpsRespContratto();
			logger.debug("StgMpsFacade.recoverStaging - STOP recoverStgMpsRespContratto");

			logger.debug("StgMpsFacade.recoverStaging - START recoverStgMpsVerbali");
			StgMpsVerbaliDAO.recoverStgMpsVerbali();
			logger.debug("StgMpsFacade.recoverStaging - STOP recoverStgMpsVerbali");
			
			logger.debug("StgMpsFacade.recoverStaging - START recoverStgMpsRilasciVerbali");
			StgMpsRilasciVerbaliDAO.recoverStgMpsRilasciVerbali();
			logger.debug("StgMpsFacade.recoverStaging - STOP recoverStgMpsRilasciVerbali");

			logger.debug("StgMpsFacade.recoverStaging - START recoverStgMpsRilasci");
			StgMpsRilasciDAO.recoverStgMpsRilasci();
			logger.debug("StgMpsFacade.recoverStaging - STOP recoverStgMpsRilasci");
			
			logger.debug("StgMpsFacade.recoverStaging - START recoverStgMpsRespOfferta");
			StgMpsRespOffertaDAO.recoverStgMpsRespOfferta();
			logger.debug("StgMpsFacade.recoverStaging - STOP recoverStgMpsRespOfferta");
			
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}
}
