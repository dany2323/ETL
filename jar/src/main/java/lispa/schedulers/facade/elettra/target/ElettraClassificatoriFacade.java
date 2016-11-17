package lispa.schedulers.facade.elettra.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElClassificatori;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraClassificatoriDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElClassificatori;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ElettraClassificatoriFacade {
	private static Logger logger = Logger
			.getLogger(ElettraClassificatoriFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {
		if (ErrorManager.getInstance().hasError())
			return;

		List<DmalmElClassificatori> staging_classificatori = new ArrayList<DmalmElClassificatori>();
		List<Tuple> target_classificatori = new ArrayList<Tuple>();
		QDmalmElClassificatori qDmalmElClassificatori = QDmalmElClassificatori.qDmalmElClassificatori;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmElClassificatori classificatoreTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.info("START ElettraClassificatoriFacade.execute");

			staging_classificatori = ElettraClassificatoriDAO
					.getAllClassificatori(dataEsecuzione);

			for (DmalmElClassificatori classificatore : staging_classificatori) {
				classificatoreTmp = classificatore;
				// Ricerco nel db target un record con idClassificatore =
				// classificatore.getIdClassificatore e data fine validita
				// 31/12/9999
				target_classificatori = ElettraClassificatoriDAO
						.getClassificatore(classificatore);

				// se non trovo almento un record, inserisco il nuovo
				// classificatore nel target
				if (target_classificatori.size() == 0) {
					righeNuove++;

					ElettraClassificatoriDAO
							.insertClassificatore(classificatore);
				} else {
					boolean modificato = false;

					for (Tuple row : target_classificatori) {
						if (row != null) {
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElClassificatori.codiceClassificatore),
											classificatore
													.getCodiceClassificatore())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElClassificatori.tipoClassificatore),
											classificatore
													.getTipoClassificatore())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								ElettraClassificatoriDAO
										.updateDataFineValidita(
												dataEsecuzione,
												row.get(qDmalmElClassificatori.classificatorePk));

								// inserisco un nuovo record
								ElettraClassificatoriDAO
										.insertClassificatoreUpdate(classificatore);
							} else {
								// Aggiorno comunque i dati senza storicizzare
								ElettraClassificatoriDAO
										.updateClassificatore(
												row.get(qDmalmElClassificatori.classificatorePk),
												classificatore);
							}
						}
					}
				}
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(classificatoreTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(classificatoreTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.updateETLTargetInfo(dataEsecuzione, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()),
						DmAlmConstants.TARGET_ELETTRA_CLASSIFICATORI,
						righeNuove, righeModificate);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("STOP ElettraClassificatoriFacade.execute");
		}
	}
}
