package lispa.schedulers.facade.sfera.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmProgettoSfera;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.sfera.DmAlmProgettoSferaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmProgettoSfera;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class ProgettoSferaFacade {
	private static Logger logger = Logger.getLogger(ProgettoSferaFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		// Se errore precedente non eseguo nulla
		if (ErrorManager.getInstance().hasError())
			return;

		logger.info("START ProgettoSferaFacade.execute");

		List<DmalmProgettoSfera> progettiStg = new ArrayList<DmalmProgettoSfera>();
		List<Tuple> target = new ArrayList<Tuple>();
		QDmalmProgettoSfera progetto = QDmalmProgettoSfera.dmalmProgettoSfera;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmProgettoSfera progettoTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			progettiStg = DmAlmProgettoSferaDAO.getAllProgetti(dataEsecuzione);

			for (DmalmProgettoSfera project : progettiStg) {
				progettoTmp = project;
				target = DmAlmProgettoSferaDAO.getProgetto(project);

				// Uso il campo Annullato in quanto i campo applicazione non
				// esiste
				String parApplicazione = project.getAnnullato();

				// Annullamento
				// Il dato project.getAnnullato contiene il dato staging
				// appllicazione
				if (project.getAnnullato().startsWith(
						DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)) {
					project.setAnnullato(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE);
				} else {
					project.setAnnullato(null);
				}
				// Annullamento

				// se non trovo almento un record, inserisco la nuova struttura
				// organizzativa nel target

				if (target.size() == 0 && !project.getNomeProgetto().startsWith("UFFICIOSO-")) {
					righeNuove++;

					DmAlmProgettoSferaDAO.insertProgettoSfera(project,
							dataEsecuzione);

					rinascitaFisicaProgettoSfera(dataEsecuzione, project,
							parApplicazione);

				} else {

					for (Tuple row : target) {
						// aggiorno la data di fine validita del record corrente

						if (row != null && !isUfficiosoInTargetAndStaging(row.get(progetto.nomeProgetto),project.getNomeProgetto())) {
							boolean modificato = false;

							if (BeanUtils.areDifferent(
									row.get(progetto.nomeProgetto),
									project.getNomeProgetto())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFornitoreMpp),
									project.getpPrjFornitoreMpp())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjADisposizione01),
									project.getpPrjADisposizione01())) {
								modificato = true;
							}
//							if (BeanUtils.areDifferent(
//									row.get(progetto.pPrjDtConsegnaMppForn),
//									project.getpPrjDtConsegnaMppForn())) {
//								modificato = true;
//							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFornitoreSviluppoMev),
									project.getpPrjFornitoreSviluppoMev())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjCodRdi),
									project.getpPrjCodRdi())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjPunPrezzoUnitNominal),
									project.getpPrjPunPrezzoUnitNominal())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjMfcAPreventivo),
									project.getpPrjMfcAPreventivo())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjMpPercentCicloDiVita),
									project.getpPrjMpPercentCicloDiVita())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFccFattCorrezTotal),
									project.getpPrjFccFattCorrezTotal())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(progetto.pPrjImportoRdiAPreventivo),
											project.getpPrjImportoRdiAPreventivo())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjMfcAConsuntivo),
									project.getpPrjMfcAConsuntivo())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjImportoAConsuntivo),
									project.getpPrjImportoAConsuntivo())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjAuditMonitore),
									project.getpPrjAuditMonitore())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjAuditIndexVerify),
									project.getpPrjAuditIndexVerify())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlApplicLgFpDwh),
									project.getpPrjFlApplicLgFpDwh())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlagApplLgFpGis),
									project.getpPrjFlagApplLgFpGis())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlApplLgFpEdma),
									project.getpPrjFlApplLgFpEdma())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlagApplLgFpMware),
									project.getpPrjFlagApplLgFpMware())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlApplLgFpWeb),
									project.getpPrjFlApplLgFpWeb())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlApplicLgFpFuturo01),
									project.getpPrjFlApplicLgFpFuturo01())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlApplicLgFpFuturo02),
									project.getpPrjFlApplicLgFpFuturo02())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlAmbTecTransBatRep),
									project.getpPrjFlAmbTecTransBatRep())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlagAmbTecnGis),
									project.getpPrjFlagAmbTecnGis())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjAmbTecnPortali),
									project.getpPrjAmbTecnPortali())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlAmbTecnPiatEnterpr),
									project.getpPrjFlAmbTecnPiatEnterpr())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlAmbitoTecnFuturo01),
									project.getpPrjFlAmbitoTecnFuturo01())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(progetto.pPrjFlAmbitoTecnFuturo02),
									project.getpPrjFlAmbitoTecnFuturo02())) {
								modificato = true;
							}

							if (BeanUtils.areDifferent(
									row.get(progetto.dmalmAsmFk),
									project.getDmalmAsmFk())) {
								modificato = true;
							}

							// -- SCD per annullamento
							if (BeanUtils.areDifferent(
									row.get(progetto.annullato),
									project.getAnnullato())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								DmAlmProgettoSferaDAO.updateDataFineValidita(
										dataEsecuzione, project);

								// inserisco un nuovo record
								DmAlmProgettoSferaDAO.insertProgettoSfera(
										project, dataEsecuzione);
							} else {
								// Aggiorno lo stesso
								DmAlmProgettoSferaDAO
										.updateDmalmProgetto(project);
							}
						}
					}
				}
			}

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(progettoTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(progettoTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_PROGETTO_SFERA, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("STOP ProgettoSferaFacade.execute");
		}
	}

	private static boolean isUfficiosoInTargetAndStaging(String nomeProgettoTarget, String nomeProgettoStaging) {
		if(nomeProgettoTarget.startsWith("UFFICIOSO-") && nomeProgettoTarget.charAt(14)=='#' &&
				nomeProgettoStaging.startsWith("UFFICIOSO-") && nomeProgettoStaging.charAt(14)=='#')
			return true;
		return false;
	}

	private static void rinascitaFisicaProgettoSfera(Timestamp dataEsecuzione,
			DmalmProgettoSfera project, String applicazione) {
		try {

			QDmalmProgettoSfera progetto = QDmalmProgettoSfera.dmalmProgettoSfera;

			List<Tuple> target = DmAlmProgettoSferaDAO.getProgettoByName(
					project, applicazione);

			if (target.size() > 0) {
				for (Tuple row : target) {
					if (row != null) {

						DmalmProgettoSfera projectRinascita = new DmalmProgettoSfera();

						projectRinascita.setDataFineEffettiva(row
								.get(progetto.dataFineEffettiva));
						projectRinascita.setDataAvvio(row
								.get(progetto.dataAvvio));
						projectRinascita.setIdProgetto(row
								.get(progetto.idProgetto));
						projectRinascita.setImpegnoEffettivo(row
								.get(progetto.impegnoEffettivo));
						projectRinascita.setIncludeInBenchmarkingDb(row
								.get(progetto.includeInBenchmarkingDb));
						projectRinascita.setNotePrj(row.get(progetto.notePrj));
						projectRinascita.setpPrjADisposizione01(row
								.get(progetto.pPrjADisposizione01));
						projectRinascita.setpPrjADisposizione02(row
								.get(progetto.pPrjADisposizione02));
						projectRinascita.setpPrjAmbTecnPortali(row
								.get(progetto.pPrjAmbTecnPortali));
						projectRinascita.setpPrjAuditIndexVerify(row
								.get(progetto.pPrjAuditIndexVerify));
						projectRinascita.setpPrjAuditMonitore(row
								.get(progetto.pPrjAuditMonitore));
						projectRinascita.setpPrjCodRdi(row
								.get(progetto.pPrjCodRdi));
//						projectRinascita.setpPrjDtConsegnaMppForn(row
//								.get(progetto.pPrjDtConsegnaMppForn));
						projectRinascita.setpPrjFccFattCorrezTotal(row
								.get(progetto.pPrjFccFattCorrezTotal));
						projectRinascita.setpPrjFlAmbTecTransBatRep(row
								.get(progetto.pPrjFlAmbTecTransBatRep));
						projectRinascita.setpPrjFlAmbTecnPiatEnterpr(row
								.get(progetto.pPrjFlAmbTecnPiatEnterpr));
						projectRinascita.setpPrjFlAmbitoTecnFuturo01(row
								.get(progetto.pPrjFlAmbitoTecnFuturo01));
						projectRinascita.setpPrjFlAmbitoTecnFuturo02(row
								.get(progetto.pPrjFlAmbitoTecnFuturo02));
						projectRinascita.setpPrjFlApplLgFpEdma(row
								.get(progetto.pPrjFlApplLgFpEdma));
						projectRinascita.setpPrjFlApplLgFpWeb(row
								.get(progetto.pPrjFlApplLgFpWeb));
						projectRinascita.setpPrjFlApplicLgFpDwh(row
								.get(progetto.pPrjFlApplicLgFpDwh));
						projectRinascita.setpPrjFlApplicLgFpFuturo01(row
								.get(progetto.pPrjFlApplicLgFpFuturo01));
						projectRinascita.setpPrjFlApplicLgFpFuturo02(row
								.get(progetto.pPrjFlApplicLgFpFuturo02));
						projectRinascita.setpPrjFlagAmbTecnGis(row
								.get(progetto.pPrjFlagAmbTecnGis));
						projectRinascita.setpPrjFlagApplLgFpGis(row
								.get(progetto.pPrjFlagApplLgFpGis));
						projectRinascita.setpPrjFlagApplLgFpMware(row
								.get(progetto.pPrjFlagApplLgFpMware));
						projectRinascita.setpPrjImportoAConsuntivo(row
								.get(progetto.pPrjImportoAConsuntivo));
						projectRinascita.setpPrjImportoRdiAPreventivo(row
								.get(progetto.pPrjImportoRdiAPreventivo));
						projectRinascita.setpPrjIndexAlmValidProgAsm(row
								.get(progetto.pPrjIndexAlmValidProgAsm));
						projectRinascita.setpPrjMfcAConsuntivo(row
								.get(progetto.pPrjMfcAConsuntivo));
						projectRinascita.setpPrjMfcAPreventivo(row
								.get(progetto.pPrjMfcAPreventivo));
						projectRinascita.setpPrjMpPercentCicloDiVita(row
								.get(progetto.pPrjMpPercentCicloDiVita));
						projectRinascita.setpPrjPunPrezzoUnitNominal(row
								.get(progetto.pPrjPunPrezzoUnitNominal));
						projectRinascita.setPrjCls(row.get(progetto.prjCls));
						projectRinascita.setVersionePrj(row
								.get(progetto.versionePrj));
						projectRinascita.setDataModifica(dataEsecuzione);
						projectRinascita.setDmalmStgMisuraPk(row
								.get(progetto.dmalmStgMisuraPk));
						projectRinascita.setpPrjFornitoreSviluppoMev(row
								.get(progetto.pPrjFornitoreSviluppoMev));
						projectRinascita.setNomeProgetto(row
								.get(progetto.nomeProgetto));
						projectRinascita.setTipoProgetto(row
								.get(progetto.tipoProgetto));
						projectRinascita.setCosto(row.get(progetto.costo));
						projectRinascita.setDurataEffettiva(row
								.get(progetto.durataEffettiva));
						projectRinascita.setStaffMedio(row
								.get(progetto.staffMedio));
						projectRinascita.setDataCaricamento(dataEsecuzione);
						projectRinascita.setpPrjFornitoreMpp(row
								.get(progetto.pPrjFornitoreMpp));
						projectRinascita.setCicloDiVita(row
								.get(progetto.cicloDiVita));
						projectRinascita.setIdAsm(row.get(progetto.idAsm));
						projectRinascita.setDmalmAsmFk(DmAlmProgettoSferaDAO
								.getAsmByID(row.get(progetto.idAsm),
										dataEsecuzione));
						projectRinascita.setAnnullato(null);

						logger.debug(" - RIPRISTINO PROGETTO SFERA: "
								+ projectRinascita.getNomeProgetto());

						// STORICIZZO
						// aggiorno la data di fine validita sul record
						// corrente
						DmAlmProgettoSferaDAO.updateDataFineValidita(
								dataEsecuzione, projectRinascita);

						// inserisco un nuovo record
						DmAlmProgettoSferaDAO.insertProgettoSfera(
								projectRinascita, dataEsecuzione);

					}
				}
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		}

	}

}
