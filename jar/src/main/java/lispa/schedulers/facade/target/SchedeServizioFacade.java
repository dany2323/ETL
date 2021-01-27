package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.mysema.query.Tuple;

import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.SchedeServizioDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSchedeServizio;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSchedeServizioTarget;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

public class SchedeServizioFacade {

	private static Logger logger = Logger.getLogger(SchedeServizioFacade.class);
	private static QDmalmSchedeServizioTarget target = QDmalmSchedeServizioTarget.dmalmSchedeServizioTarget;
	private static QDmalmSchedeServizio stg = QDmalmSchedeServizio.dmalmSchedeServizio;
	
	public static void execute(Timestamp dataEsecuzione) {
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtCaricamento = new Date();
		Date dtFineCaricamento = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			List<Tuple> allSS = SchedeServizioDAO.getAllSS(dataEsecuzione);
			for (Tuple t : allSS) {
				List<Tuple> check = SchedeServizioDAO.checkExist(t
						.get(stg.id), t.get(stg.repository));
				if (check.size() == 0) {
					// è un nuovo dato, inserisco semplicemente
					SchedeServizioDAO.insert(t, dataEsecuzione);
				} else {
					if ((BeanUtils.areDifferent(t.get(stg.name), check
							.get(0).get(target.name)) && (check.get(0).get(
							target.dtFineValidita).equals(DateUtils
							.getDtFineValidita9999()))&&(t.get(stg.repository).equals(check
							.get(0).get(target.repository))))) {
						//storicizzo
						Timestamp dtChiusura = dataEsecuzione;
						SchedeServizioDAO.updateFineValidita(check
							.get(0).get(target.id), dtChiusura, check.get(0).get(target.repository));
						SchedeServizioDAO.insertSSUpdate(t, dataEsecuzione);
						logger.debug("Modificato "+check
							.get(0).get(target.id));
					} else {
						// semplice update
						Timestamp dtNewCaricamento = DateUtils.dateToTimestamp(dtCaricamento);
						SchedeServizioDAO.updateSS(check.get(0).get(target.dmalm_schedeServizio_Pk),t,dtNewCaricamento);
					}
				}

			}
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
						DmAlmConstants.TARGET_SCHEDE_SERVIZIO, stato,
						new Timestamp(dtCaricamento.getTime()), new Timestamp(
								dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);

			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
