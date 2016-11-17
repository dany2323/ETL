package lispa.schedulers.facade.elettra.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElFunzionalita;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraFunzionalitaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElFunzionalita;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ElettraFunzionalitaFacade {
	private static Logger logger = Logger
			.getLogger(ElettraFunzionalitaFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {
		if (ErrorManager.getInstance().hasError())
			return;

		logger.info("START ElettraFunzionalitaFacade.execute");

		List<DmalmElFunzionalita> staging = new ArrayList<DmalmElFunzionalita>();
		List<Tuple> target = new ArrayList<Tuple>();
		QDmalmElFunzionalita funz = QDmalmElFunzionalita.qDmalmElFunzionalita;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmElFunzionalita funzionalitaTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			staging = ElettraFunzionalitaDAO.getAllFunzionalita(dataEsecuzione);

			for (DmalmElFunzionalita funzionalita : staging) {
				funzionalitaTmp = funzionalita;
				target = ElettraFunzionalitaDAO.getFunzionalita(funzionalita);

				if (target.size() == 0) {
					righeNuove++;
					ElettraFunzionalitaDAO.insertFunzionalita(funzionalita,
							dataEsecuzione);
				} else {
					boolean modificato = false;

					for (Tuple row : target) {
						if (row != null) {
							if (BeanUtils.areDifferent(
									funzionalita.getSiglaFunzionalita(),
									row.get(funz.siglaFunzionalita))) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(funzionalita.getNome(),
									row.get(funz.nome))) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									funzionalita.getDtAnnullamento(),
									row.get(funz.dtAnnullamento))) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									funzionalita.getAnnullato(),
									row.get(funz.annullato))) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								ElettraFunzionalitaDAO.updateDataFineValidita(
										dataEsecuzione,
										row.get(funz.funzionalitaPk));

								// inserisco un nuovo record
								ElettraFunzionalitaDAO
										.insertFunzionalitaUpdate(
												dataEsecuzione, funzionalita);
							} else {
								// Aggiorno lo stesso
								ElettraFunzionalitaDAO.updateFunzionalita(
										row.get(funz.funzionalitaPk),
										funzionalita);
							}
						}
					}
				}
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(funzionalitaTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(funzionalitaTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.updateETLTargetInfo(dataEsecuzione, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()),
						DmAlmConstants.TARGET_ELETTRA_FUNZIONALITA, righeNuove,
						righeModificate);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("STOP ElettraFunzionalitaFacade.execute");
		}
	}
}
