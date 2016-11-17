package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.RelClassificatoriOresteDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;

import org.apache.log4j.Logger;

public class RelClassificatoriOresteFacade {

	private static Logger logger = Logger
			.getLogger(RelClassificatoriOresteFacade.class);

	public static void execute(Timestamp dataEsecuzione) {

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			righeNuove = RelClassificatoriOresteDAO
					.insertRelClassificatoriOreste(dataEsecuzione);
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_REL_CLASSIFICATORI, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);

			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);

			}
		}

	}

}
