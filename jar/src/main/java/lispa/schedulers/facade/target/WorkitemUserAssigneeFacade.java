package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.WorkitemUserAssigneeDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.target.fatti.AnomaliaProdottoFacade;

import org.apache.log4j.Logger;

public class WorkitemUserAssigneeFacade {

	private static Logger logger = Logger
			.getLogger(AnomaliaProdottoFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		int righeNuove = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			righeNuove = WorkitemUserAssigneeDAO
					.insertWorkitemUserAssignee(dataEsecuzione);
		} catch (DAOException e) {
			// l'errore non è bloccante i workitem assignees sono recuperabili
			// manualmente
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			// l'errore non è bloccante i workitem assignees sono recuperabili
			// manualmente
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {

				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_WORKITEMASSIGNEES, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						0, 0, 0);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
