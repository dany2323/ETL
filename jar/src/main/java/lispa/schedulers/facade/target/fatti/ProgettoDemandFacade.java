package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.fatti.DmalmProgettoDemand;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ProgettoDemandOdsDAO;
import lispa.schedulers.dao.target.fatti.ProgettoDemandDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoDemand;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ProgettoDemandFacade {

	private static Logger logger = Logger.getLogger(ProgettoDemandFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		List<DmalmProgettoDemand> staging_progettoDemand = new ArrayList<DmalmProgettoDemand>();
		List<Tuple> target_progettoDemand = new ArrayList<Tuple>();
		QDmalmProgettoDemand progDemand = QDmalmProgettoDemand.dmalmProgettoDemand;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		DmalmProgettoDemand progetto_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			staging_progettoDemand = ProgettoDemandDAO
					.getAllProgettoDemand(dataEsecuzione);

			ProgettoDemandOdsDAO.delete();

			logger.debug("START -> Popolamento ProgettoDemand ODS, "
					+ staging_progettoDemand.size() + " progetti");

			ProgettoDemandOdsDAO.insert(staging_progettoDemand, dataEsecuzione);

			List<DmalmProgettoDemand> x = ProgettoDemandOdsDAO.getAll();

			logger.debug("STOP -> ProgettoDemand ODS, "
					+ staging_progettoDemand.size() + " progetti");

			for (DmalmProgettoDemand progetto : x) {

				progetto_tmp = progetto;
				// Ricerco nel db target un record con idProject =
				// project.getIdProject e data fine validita uguale a 31-12-9999

				target_progettoDemand = ProgettoDemandDAO
						.getProgettoDemand(progetto);

				// se non trovo almento un record, inserisco il project nel
				// target
				if (target_progettoDemand.size() == 0) {
					righeNuove++;
					progetto.setDtCambioStatoProgettoDem(progetto
							.getDtModificaProgettoDemand());
					ProgettoDemandDAO.insertProgettoDemand(progetto);
				} else {
					boolean modificato = false;

					for (Tuple row : target_progettoDemand) {

						if (row != null) {
							if (BeanUtils.areDifferent(
									row.get(progDemand.dmalmStatoWorkitemFk03),
									progetto.getDmalmStatoWorkitemFk03())) {
								progetto.setDtCambioStatoProgettoDem(progetto
										.getDtModificaProgettoDemand());
								modificato = true;
							} else {
								progetto.setDtCambioStatoProgettoDem(row
										.get(progDemand.dtCambioStatoProgettoDem));
							}

							if (!modificato
									&& BeanUtils
											.areDifferent(
													row.get(progDemand.dtScadenzaProgettoDemand),
													progetto.getDtScadenzaProgettoDemand())) {
								modificato = true;
							}

							if (!modificato
									&& BeanUtils.areDifferent(row
											.get(progDemand.dmalmProjectFk02),
											progetto.getDmalmProjectFk02())) {
								modificato = true;
							}

							if (!modificato
									&& BeanUtils
											.areDifferent(
													row.get(progDemand.tempoTotaleRisoluzione),
													progetto.getTempoTotaleRisoluzione())) {
								modificato = true;
							}

							if (!modificato
									&& BeanUtils
											.areDifferent(
													row.get(progDemand.descrizioneProgettoDemand),
													progetto.getDescrizioneProgettoDemand())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(progDemand.dmalmUserFk06),
											progetto.getDmalmUserFk06())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(row
											.get(progDemand.cfDtEnunciazione),
											progetto.getCfDtEnunciazione())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(progDemand.uri),
											progetto.getUri())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(progDemand.annullato),
											progetto.getAnnullato())) {
								modificato = true;
							}
							//DM_ALM-320
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(progDemand.severity),
											progetto.getSeverity())) {
								modificato = true;
							}
							if (!modificato
									&& BeanUtils.areDifferent(
											row.get(progDemand.priority),
											progetto.getPriority())) {
								modificato = true;
							}
							
							
							if (modificato) {
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								ProgettoDemandDAO.updateRank(progetto,
										new Double(0));

								// inserisco un nuovo record
								ProgettoDemandDAO.insertProgettoDemandUpdate(
										dataEsecuzione, progetto, true);
							} else {
								progetto.setDtCambioStatoProgettoDem(row
										.get(progDemand.dtCambioStatoProgettoDem));
								// Aggiorno lo stesso
								ProgettoDemandDAO
										.updateProgettoDemand(progetto);
							}
						}
					}
				}
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(progetto_tmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(progetto_tmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {

				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_PROGETTO_DEMAND, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);

			}
		}
	}
}
