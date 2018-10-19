package lispa.schedulers.action;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiEdmaFacade;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiElettraFacade;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiSGRCMFacade;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiSferaFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;

import java.sql.SQLException;

import org.apache.log4j.Logger;

public class DmAlmCheckAnnullamenti {

	private static Logger logger = Logger
			.getLogger(DmAlmCheckAnnullamenti.class);

	protected static void doWork() throws PropertiesReaderException {

		try {
			// ELETTRA/SGRCM
			if (ExecutionManager.getInstance().isExecutionElettraSgrcm()) {

				// se errore non eseguo gli step successivi
				if (!ErrorManager.getInstance().hasError()) {
					logger.debug("START CheckAnnullamentiSGRCMFacade");
					CheckAnnullamentiSGRCMFacade.execute();
					logger.debug("STOP CheckAnnullamentiSGRCMFacade");
				}

				// se errore non eseguo gli step successivi
				if (!ErrorManager.getInstance().hasError()) {
					logger.debug("START CheckAnnullamentiEdmaFacade");
					CheckAnnullamentiEdmaFacade.execute();
					logger.debug("STOP CheckAnnullamentiEdmaFacade");
				}

				// se errore non eseguo gli step successivi
				if (!ErrorManager.getInstance().hasError()) {
					logger.debug("START CheckAnnullamentiElettraFacade");
					CheckAnnullamentiElettraFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
					logger.debug("STOP CheckAnnullamentiElettraFacade");
				}
			}

			// CHECK ANNULLAMENTI DI SFERA
			if (ExecutionManager.getInstance().isExecutionSfera()) {
				logger.debug("START CheckAnnullamentiSferaFacade");
				CheckAnnullamentiSferaFacade.execute();
				logger.debug("STOP CheckAnnullamentiSferaFacade");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		} catch (SQLException e) {
			logger.error(e.getMessage(), e);
		}

	}

}
