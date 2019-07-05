package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.AnomaliaProdottoOdsDAO;
import lispa.schedulers.dao.target.fatti.AnomaliaProdottoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class AnomaliaProdottoFacade {

	private static Logger logger = Logger
			.getLogger(AnomaliaProdottoFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		List<DmalmAnomaliaProdotto> staging_anomaliaprodotto = new ArrayList<DmalmAnomaliaProdotto>();
		List<Tuple> target_anomaliaprodotto = new ArrayList<Tuple>();
		QDmalmAnomaliaProdotto anom = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmAnomaliaProdotto anomalia_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			staging_anomaliaprodotto = AnomaliaProdottoDAO
					.getAllAnomaliaProdotto(dataEsecuzione);

			AnomaliaProdottoOdsDAO.delete();

			logger.debug("START -> Popolamento Anomalia ODS, "
					+ staging_anomaliaprodotto.size() + " anomalie");

			AnomaliaProdottoOdsDAO.insert(staging_anomaliaprodotto,
					dataEsecuzione);

			List<DmalmAnomaliaProdotto> x = AnomaliaProdottoOdsDAO.getAll();

			logger.debug("STOP -> Popolamento Anomalia ODS, "
					+ staging_anomaliaprodotto.size() + " anomalie");

			for (DmalmAnomaliaProdotto anomalia : x) {

				anomalia_tmp = anomalia;
				// Ricerco nel db target un record con idProject =
				// project.getIdProject e data fine validita uguale a 31-12-9999

				target_anomaliaprodotto = AnomaliaProdottoDAO
						.getAnomaliaProdotto(anomalia);

				// se non trovo almento un record, inserisco il project nel
				// target
				if (target_anomaliaprodotto.size() == 0) {
					anomalia.setDtCambioStatoAnomalia(anomalia
							.getDtModificaRecordAnomalia());
					righeNuove++;
					AnomaliaProdottoDAO.insertAnomaliaProdotto(anomalia);
				} else {
					boolean modificato = false;

					for (Tuple row : target_anomaliaprodotto) {

						if (row != null) {
							if (BeanUtils.areDifferent(
									row.get(anom.tempoTotRisoluzioneAnomalia),
									anomalia.getTempoTotRisoluzioneAnomalia())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils
											.areDifferent(
													row.get(anom.dmalmStatoWorkitemFk03),
													anomalia.getDmalmStatoWorkitemFk03())) {
								anomalia.setDtCambioStatoAnomalia(anomalia
										.getDtModificaRecordAnomalia());
								modificato = true;
							} else {
								anomalia.setDtCambioStatoAnomalia(row
										.get(anom.dtCambioStatoAnomalia));
							}
							if (!modificato
									&& BeanUtils
											.areDifferent(
													row.get(anom.numeroTestataAnomalia),
													anomalia.getNumeroTestataAnomalia())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(anom.numeroLineaAnomalia),
											anomalia.getNumeroLineaAnomalia())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(anom.dmalmProjectFk02),
											anomalia.getDmalmProjectFk02())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(anom.effortAnalisi),
											anomalia.getEffortAnalisi())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(anom.dmalmUserFk06),
											anomalia.getDmalmUserFk06())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(anom.uri),
											anomalia.getUri())) {
								modificato = true;
							}

							if (modificato && row.get(anom.dmalmProjectFk02)!=0) {
								righeModificate++;
								// STORICIZZO
								// imposto a zero il rank e il
								// flagUltimaSituazione
								AnomaliaProdottoDAO
										.updateRankFlagUltimaSituazione(
												anomalia, new Double(0),
												new Short("0"));

								// inserisco un nuovo record
								AnomaliaProdottoDAO
										.insertAnomaliaProdottoUpdate(
												dataEsecuzione, anomalia, true);

							} else {
								// Aggiorno lo stesso
								AnomaliaProdottoDAO
										.updateDmalmAnomaliaProdotto(anomalia);
							}
						}
					}
				}
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(anomalia_tmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(anomalia_tmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {

				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_ANOMALIA, stato, new Timestamp(
								dtInizioCaricamento.getTime()), new Timestamp(
								dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	public static void updateProjectAndStatus(Timestamp dataEsecuzione) {
		
		List<DmalmAnomaliaProdotto> staging_anomaliaprodotto = new ArrayList<DmalmAnomaliaProdotto>();
		List<Tuple> target_anomaliaprodotto = new ArrayList<Tuple>();
		QDmalmAnomaliaProdotto anom = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmAnomaliaProdotto anomalia_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			staging_anomaliaprodotto = AnomaliaProdottoDAO
					.getAllAnomaliaProdotto(dataEsecuzione);

			AnomaliaProdottoOdsDAO.delete();

			logger.debug("START -> Popolamento Anomalia ODS, "
					+ staging_anomaliaprodotto.size() + " anomalie");

			AnomaliaProdottoOdsDAO.insert(staging_anomaliaprodotto,
					dataEsecuzione);

			List<DmalmAnomaliaProdotto> x = AnomaliaProdottoOdsDAO.getAll();

			logger.debug("STOP -> Popolamento Anomalia ODS, "
					+ staging_anomaliaprodotto.size() + " anomalie");

			for (DmalmAnomaliaProdotto anomalia : x) {

				anomalia_tmp = anomalia;
				// Ricerco nel db target un record con idProject =
				// project.getIdProject e data fine validita uguale a 31-12-9999

				AnomaliaProdottoDAO
						.updateProjectAndStatus(anomalia);

			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(anomalia_tmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(anomalia_tmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {

				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_ANOMALIA, stato, new Timestamp(
								dtInizioCaricamento.getTime()), new Timestamp(
								dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}