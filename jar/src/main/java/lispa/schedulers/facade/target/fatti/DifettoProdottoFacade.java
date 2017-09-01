package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.DifettoProdottoOdsDAO;
import lispa.schedulers.dao.target.fatti.DifettoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDifettoProdotto;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class DifettoProdottoFacade {

	private static Logger logger = Logger
			.getLogger(DifettoProdottoFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		List<DmalmDifettoProdotto> staging_difettoprodotto = new ArrayList<DmalmDifettoProdotto>();
		List<Tuple> target_difettoprodotto = new ArrayList<Tuple>();
		QDmalmDifettoProdotto dif = QDmalmDifettoProdotto.dmalmDifettoProdotto;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmDifettoProdotto difetto_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			staging_difettoprodotto = DifettoDAO
					.getAllDifettoProdotto(dataEsecuzione);

			DifettoProdottoOdsDAO.delete();

			logger.debug("START -> Popolamento Difetto ODS, "
					+ staging_difettoprodotto.size() + " difetti");

			DifettoProdottoOdsDAO.insert(staging_difettoprodotto,
					dataEsecuzione);

			List<DmalmDifettoProdotto> x = DifettoProdottoOdsDAO.getAll();

			logger.debug("STOP -> Popolamento Difetto ODS, "
					+ staging_difettoprodotto.size() + " difetti");

			for (DmalmDifettoProdotto difetto : x) {
				// Ricerco nel db target un record con idProject =
				// project.getIdProject e data fine validita uguale a 31-12-9999
				difetto_tmp = difetto;
				target_difettoprodotto = DifettoDAO.getDifettoProdotto(difetto);

				// se non trovo almento un record, inserisco il project nel
				// target
				if (target_difettoprodotto.size() == 0) {
					difetto.setDtCambioStatoDifetto(difetto
							.getDtModificaRecordDifetto());
					righeNuove++;
					DifettoDAO.insertDifettoProdotto(difetto);
				} else {
					boolean modificato = false;

					for (Tuple row : target_difettoprodotto) {
						if (row != null) {

							if (BeanUtils.areDifferent(
									row.get(dif.dmalmStatoWorkitemFk03),
									difetto.getDmalmStatoWorkitemFk03())) {
								difetto.setDtCambioStatoDifetto(difetto
										.getDtModificaRecordDifetto());
								modificato = true;
							} else {
								difetto.setDtCambioStatoDifetto(row
										.get(dif.dtCambioStatoDifetto));
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(dif.dmalmProjectFk02),
											difetto.getDmalmProjectFk02())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(dif.numeroTestataDifetto),
											difetto.getNumeroTestataDifetto())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(dif.numeroLineaDifetto),
											difetto.getNumeroLineaDifetto())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(dif.dmalmUserFk06),
											difetto.getDmalmUserFk06())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(row.get(dif.uri),
											difetto.getUri())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(row.get(dif.annullato),
											difetto.getAnnullato())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(row.get(dif.provenienzaDifetto),
											difetto.getProvenienzaDifetto())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// imposto a zero il rank e il flagUltimaSituazione
								DifettoDAO.updateRankFlagUltimaSituazione(difetto,
										new Double(0), new Short("0"));

								//3-8-17
								difetto.setProvenienzaDifetto(row.get(dif.provenienzaDifetto));
								
								// inserisco un nuovo record
								DifettoDAO.insertDifettoProdottoUpdate(
										dataEsecuzione, difetto, true);
							} else {
								// Aggiorno lo stesso
								DifettoDAO.updateDmalmDifettoProdotto(difetto);
							}
						}
					}
				}
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(difetto_tmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(difetto_tmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_DIFETTO, stato, new Timestamp(
								dtInizioCaricamento.getTime()), new Timestamp(
								dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);

			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}