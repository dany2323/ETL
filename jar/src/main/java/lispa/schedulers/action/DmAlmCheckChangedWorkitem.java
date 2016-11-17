package lispa.schedulers.action;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckChangingWorkitemFacade;
import lispa.schedulers.manager.ExecutionManager;

import org.apache.log4j.Logger;

public class DmAlmCheckChangedWorkitem {

	private static Logger logger = Logger
			.getLogger(DmAlmCheckChangedWorkitem.class);

	/***
	 * ChangedWorkitem non imposta ErrorManager a true in caso di errore per non
	 * perdere la lavorazione dell'intero ETL. Il caricamento della tabella
	 * ChangedWorkitems verrà recuperato successivamente
	 */
	protected static void doWork() throws PropertiesReaderException {
		try {
			// ELETTRA/SGRCM
			if (ExecutionManager.getInstance().isExecutionElettraSgrcm()) {

				logger.debug("START CheckChangedWorkitemFacade");
				CheckChangingWorkitemFacade.execute();
				logger.debug("STOP CheckChangedWorkitemFacade");
			}
		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}

	}

}
