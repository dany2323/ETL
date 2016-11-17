package lispa.schedulers.facade.elettra.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.elettra.DmalmElAmbienteTecnologicoClassificatori;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.elettra.ElettraAmbienteTecnologicoClassificatoriDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElAmbienteTecnologicoClassificatori;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ElettraAmbienteTecnologicoClassificatoriFacade {
	private static Logger logger = Logger
			.getLogger(ElettraAmbienteTecnologicoClassificatoriFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {
		if (ErrorManager.getInstance().hasError())
			return;

		List<DmalmElAmbienteTecnologicoClassificatori> staging = new ArrayList<DmalmElAmbienteTecnologicoClassificatori>();
		List<Tuple> target = new ArrayList<Tuple>();
		QDmalmElAmbienteTecnologicoClassificatori atc = QDmalmElAmbienteTecnologicoClassificatori.qDmalmElAmbienteTecnologicoClassificatori;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmElAmbienteTecnologicoClassificatori ambientetecnologicoclassificatoriTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.info("START ElettraAmbienteTecnologicoClassificatoriFacade.execute");

			staging = ElettraAmbienteTecnologicoClassificatoriDAO
					.getAllAmbienteTecnologicoClassificatori(dataEsecuzione);

			for (DmalmElAmbienteTecnologicoClassificatori ambientetecnologicoclassificatori : staging) {
				ambientetecnologicoclassificatoriTmp = ambientetecnologicoclassificatori;
				target = ElettraAmbienteTecnologicoClassificatoriDAO
						.getAmbienteTecnologicoClassificatori(ambientetecnologicoclassificatori);

				if (target.size() == 0) {
					righeNuove++;
					ElettraAmbienteTecnologicoClassificatoriDAO
							.insertAmbienteTecnologicoClassificatori(
									ambientetecnologicoclassificatori,
									dataEsecuzione);
				} else {
					boolean modificato = false;

					for (Tuple row : target) {
						if (row != null) {
							if (BeanUtils.areDifferent(
									ambientetecnologicoclassificatori
											.getNomeAmbienteTecnologico(), row
											.get(atc.nomeAmbienteTecnologico))) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											ambientetecnologicoclassificatori
													.getDescrizioneAmbienteTecnologico(),
											row.get(atc.descrizioneAmbienteTecnologico))) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									ambientetecnologicoclassificatori
											.getNomeClassificatore(), row
											.get(atc.nomeClassificatore))) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									ambientetecnologicoclassificatori
											.getDescrizioneClassificatore(),
									row.get(atc.descrizioneClassificatore))) {
								modificato = true;
							}
							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								ElettraAmbienteTecnologicoClassificatoriDAO
										.updateDataFineValidita(
												dataEsecuzione,
												row.get(atc.ambienteTecnologicoClassificatoriPk));

								// inserisco un nuovo record
								ElettraAmbienteTecnologicoClassificatoriDAO
										.insertAmbienteTecnologicoClassificatoriUpdate(
												dataEsecuzione,
												ambientetecnologicoclassificatori);
							} else {
								// Aggiorno lo stesso
								ElettraAmbienteTecnologicoClassificatoriDAO
										.updateAmbienteTecnologicoClassificatori(
												row.get(atc.ambienteTecnologicoClassificatoriPk),
												ambientetecnologicoclassificatori);
							}
						}
					}
				}
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils
					.objectToString(ambientetecnologicoclassificatoriTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils
					.objectToString(ambientetecnologicoclassificatoriTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO
						.insert(dataEsecuzione,
								DmAlmConstants.TARGET_ELETTRA_AMBIENTETECNOLOGICOCLASSIFICATORI,
								stato,
								new Timestamp(dtInizioCaricamento.getTime()),
								new Timestamp(dtFineCaricamento.getTime()),
								righeNuove, righeModificate, 0, 0);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("STOP ElettraAmbienteTecnologicoClassificatoriFacade.execute");
		}

	}
}
