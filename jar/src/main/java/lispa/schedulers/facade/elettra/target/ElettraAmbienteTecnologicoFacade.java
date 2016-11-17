package lispa.schedulers.facade.elettra.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElAmbienteTecnologico;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraAmbienteTecnologicoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElAmbienteTecnologico;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ElettraAmbienteTecnologicoFacade {
	private static Logger logger = Logger
			.getLogger(ElettraAmbienteTecnologicoFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {
		if (ErrorManager.getInstance().hasError())
			return;

		List<DmalmElAmbienteTecnologico> staging_ambientitecnologici = new ArrayList<DmalmElAmbienteTecnologico>();
		List<Tuple> target_ambientitecnologici = new ArrayList<Tuple>();
		QDmalmElAmbienteTecnologico qDmalmElAmbienteTecnologico = QDmalmElAmbienteTecnologico.qDmalmElAmbienteTecnologico;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmElAmbienteTecnologico ambienteTecnologicoTMP = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.info("START ElettraAmbienteTecnologicoFacade.execute");

			staging_ambientitecnologici = ElettraAmbienteTecnologicoDAO
					.getAllAmbienteTecnologico(dataEsecuzione);

			for (DmalmElAmbienteTecnologico ambienteTecnologico : staging_ambientitecnologici) {
				ambienteTecnologicoTMP = ambienteTecnologico;
				// Ricerco nel db target un record con idAmbienteTecnologico =
				// ambienteTecnologico.getIdAmbienteTecnologico e data fine
				// validita max
				target_ambientitecnologici = ElettraAmbienteTecnologicoDAO
						.getAmbienteTecnologico(ambienteTecnologico);

				// se non trovo almento un record, inserisco il nuovo
				// ambienteTecnologico nel target
				if (target_ambientitecnologici.size() == 0) {
					righeNuove++;

					ElettraAmbienteTecnologicoDAO
							.insertAmbienteTecnologico(ambienteTecnologico);
				} else {
					boolean modificato = false;

					for (Tuple row : target_ambientitecnologici) {
						if (row != null) {
							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElAmbienteTecnologico.prodottoFk),
											ambienteTecnologico.getProdottoFk())) {
								modificato = true;
							}

							if (BeanUtils.areDifferent(row
									.get(qDmalmElAmbienteTecnologico.moduloFk),
									ambienteTecnologico.getModuloFk())) {
								modificato = true;
							}

							if (BeanUtils.areDifferent(
									row.get(qDmalmElAmbienteTecnologico.nome),
									ambienteTecnologico.getNome())) {
								modificato = true;
							}

							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElAmbienteTecnologico.annullato),
											ambienteTecnologico.getAnnullato())) {
								modificato = true;
							}

							if (BeanUtils
									.areDifferent(
											row.get(qDmalmElAmbienteTecnologico.dataAnnullamento),
											ambienteTecnologico
													.getDataAnnullamento())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								ElettraAmbienteTecnologicoDAO
										.updateDataFineValidita(
												dataEsecuzione,
												row.get(qDmalmElAmbienteTecnologico.ambienteTecnologicoPk));

								// inserisco un nuovo record
								ElettraAmbienteTecnologicoDAO
										.insertAmbienteTecnologicoUpdate(
												dataEsecuzione,
												ambienteTecnologico);
							} else {
								// Aggiorno comunque i dati senza storicizzare
								ElettraAmbienteTecnologicoDAO
										.updateAmbienteTecnologico(
												row.get(qDmalmElAmbienteTecnologico.ambienteTecnologicoPk),
												ambienteTecnologico);
							}
						}
					}
				}
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(ambienteTecnologicoTMP));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(ambienteTecnologicoTMP));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.updateETLTargetInfo(dataEsecuzione, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()),
						DmAlmConstants.TARGET_ELETTRA_AMBIENTETECNOLOGICO,
						righeNuove, righeModificate);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("STOP ElettraAmbienteTecnologicoFacade.execute");
		}
	}
}
