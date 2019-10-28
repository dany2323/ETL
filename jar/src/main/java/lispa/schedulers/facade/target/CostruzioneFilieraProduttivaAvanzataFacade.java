package lispa.schedulers.facade.target;

import lispa.schedulers.dao.target.FilieraProduttivaAvanzataDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;

import org.apache.log4j.Logger;

public class CostruzioneFilieraProduttivaAvanzataFacade {
	private static Logger logger = Logger
			.getLogger(CostruzioneFilieraProduttivaAvanzataFacade.class);

	public static void execute() {
		try {
			// ELETTRA/SGRCM
			if (!ExecutionManager.getInstance().isExecutionElettraSgrcm())
				return;

			// Se si è verificato un errore precedente non eseguo l'elaborazione
			if (ErrorManager.getInstance().hasError()) {
				return;
			}

			logger.info("START CostruzioneFilieraProduttivaAvanzataFacade.execute()");

			FilieraProduttivaAvanzataDAO.insert();

			logger.info("STOP CostruzioneFilieraProduttivaAvanzataFacade.execute()");
		} catch (DAOException de) {
			logger.error(de.getMessage(), de);

			ErrorManager.getInstance().exceptionOccurred(true, de);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
}
