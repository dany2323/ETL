package lispa.schedulers.facade.calipso.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.log4j.Logger;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.calipso.CalipsoSchedaServizioDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;

public class CalipsoSchedaServizioFacade {
	
	private static Logger logger = Logger.getLogger(CalipsoSchedaServizioFacade.class);
	
	public static void execute(Timestamp dataEsecuzione) throws Exception, DAOException {

		if (ErrorManager.getInstance().hasError())
			return;
		
		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
	
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.info("START CalipsoSchedaServizioFacade.execute");

			CalipsoSchedaServizioDAO.insertSchedaServizio(dataEsecuzione);
		} catch (DAOException | SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				int righeNuove = CalipsoSchedaServizioDAO.getRigheInseriteModificateSchedaServizio("INSERITE", dataEsecuzione);
				int righeModificate = CalipsoSchedaServizioDAO.getRigheInseriteModificateSchedaServizio("MODIFICATE", dataEsecuzione);
				EsitiCaricamentoDAO
						.insert(dataEsecuzione,
								DmAlmConstants.TARGET_CALIPSO_SCHEDA_SERVIZIO,
								stato,
								new Timestamp(dtInizioCaricamento.getTime()),
								new Timestamp(dtFineCaricamento.getTime()),
								righeNuove, righeModificate, 0, 0);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("STOP CalipsoSchedaServizioFacade.execute");
		}

	}
}
