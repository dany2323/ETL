package lispa.schedulers.facade.sfera.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmMisura;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.sfera.DmAlmMisuraDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmMisura;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class MisuraFacade {
	private static Logger logger = Logger.getLogger(MisuraFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		// Se errore precedente non eseguo nulla
		if (ErrorManager.getInstance().hasError())
			return;

		logger.info("START MisuraFacade.execute");

		List<DmalmMisura> misureStg = new ArrayList<DmalmMisura>();
		List<Tuple> target = new ArrayList<Tuple>();
		QDmalmMisura misura = QDmalmMisura.dmalmMisura;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmMisura misuraTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			misureStg = DmAlmMisuraDAO.getAllMisure(dataEsecuzione);

			for (DmalmMisura misure : misureStg) {
				misuraTmp = misure;
				target = DmAlmMisuraDAO.getMisura(misure);

				// Uso il campo Annullato in quanto i campo applicazione non
				// esiste
				String parApplicazione = misure.getAnnullato();

				// Annullamento
				if (misure.getAnnullato().startsWith(
						DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)) {
					misure.setAnnullato(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE);
				} else {
					misure.setAnnullato(null);
				}
				// Annullamento

				// se non trovo almento un record, inserisco la nuova struttura
				// organizzativa nel target

				if (target.size() == 0 && misure.getNomeMisura().startsWith("UFFICIOSO-")) {
					righeNuove++;

					DmAlmMisuraDAO.insertMisure(dataEsecuzione, misure);

					rinascitaFisicaMisura(dataEsecuzione, misure,
							parApplicazione, misure.getProgettoSfera());

				} else {

					for (Tuple row : target) {
						// aggiorno la data di fine validita del record corrente

						if (row != null && !isUfficiosoInTargetAndStaging(row.get(misura.nomeMisura),misure.getNomeMisura())) {
							boolean modificato = false;

							if (BeanUtils.areDifferent(
									row.get(misura.dmalmPrjFk),
									misure.getDmalmPrjFk())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(misura.approccio),
									misure.getApproccio())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(misura.fpNonPesatiUfp),
									misure.getFpNonPesatiUfp())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(misura.statoMisura),
									misure.getStatoMisura())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(row.get(misura.modello),
									misure.getModello())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(row.get(misura.adjufp),
									misure.getAdjufp())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(misura.linkDocumentale),
									misure.getLinkDocumentale())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(misura.utenteMisuratore),
									misure.getUtenteMisuratore())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(row.get(misura.metodo),
									misure.getMetodo())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(misura.dataFineVerificafp),
									misure.getDataFineVerificafp())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(misura.dataInizioVerificaFp),
									misure.getDataInizioVerificaFp())) {
								modificato = true;
							}

							// -- SCD per annullamento
							if (BeanUtils.areDifferent(
									row.get(misura.annullato),
									misure.getAnnullato())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								DmAlmMisuraDAO.updateRankMisura(misure,
										new Double(0));

								// inserisco un nuovo record

								DmAlmMisuraDAO.insertMisure(dataEsecuzione,
										misure);
							} else {
								// Aggiorno lo stesso
								DmAlmMisuraDAO.updateMisura(misure);
							}
						}
					}
				}
			}

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(misuraTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(misuraTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_MISURA, stato, new Timestamp(
								dtInizioCaricamento.getTime()), new Timestamp(
								dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);
			}

			logger.info("STOP MisuraFacade.execute");
		}
	}

	private static boolean isUfficiosoInTargetAndStaging(String nomeProgettoTarget, String nomeProgettoStaging) {
		if(nomeProgettoTarget.startsWith("UFFICIOSO-") && nomeProgettoTarget.charAt(14)=='#' &&
				nomeProgettoStaging.startsWith("UFFICIOSO-") && nomeProgettoStaging.charAt(14)=='#')
			return true;
		return false;
	}


	private static void rinascitaFisicaMisura(Timestamp dataEsecuzione,
			DmalmMisura misure, String parApplicazione, String parNomeProgetto) {
		try {

			QDmalmMisura misura = QDmalmMisura.dmalmMisura;

			List<Tuple> target = DmAlmMisuraDAO.getMisuraByName(misure,
					parApplicazione, parNomeProgetto);

			if (target.size() > 0) {

				logger.debug("RINASCITA MISURA - getMisuraByName: "
						+ target.size());

				for (Tuple row : target) {
					if (row != null) {

						DmalmMisura misuraRinascita = new DmalmMisura();

						misuraRinascita.setDmalmMisuraPk(DmAlmMisuraDAO
								.getMisuraPk());

						logger.debug("RINASCITA MISURA - misuraPk: "
								+ misuraRinascita.getDmalmMisuraPk());

						misuraRinascita.setA1Num(row.get(misura.a1Num));
						misuraRinascita.setA1Ufp(row.get(misura.a1Ufp));
						misuraRinascita.setA2Num(row.get(misura.a2Num));
						misuraRinascita.setA2Ufp(row.get(misura.a1Ufp));
						misuraRinascita.setAdjmax(row.get(misura.adjmax));
						misuraRinascita.setAdjmin(row.get(misura.adjmin));
						misuraRinascita.setAdjufp(row.get(misura.adjufp));
						misuraRinascita.setAmbito(row.get(misura.ambito));
						misuraRinascita.setApproccio(row.get(misura.approccio));
						misuraRinascita.setB1Num(row.get(misura.b1Num));
						misuraRinascita.setB1Ufp(row.get(misura.b1Ufp));
						misuraRinascita.setB2Num(row.get(misura.b2Num));
						misuraRinascita.setB2Ufp(row.get(misura.b2Ufp));
						misuraRinascita.setB3Num(row.get(misura.b3Num));
						misuraRinascita.setB3Ufp(row.get(misura.b3Ufp));
						misuraRinascita.setB5Num(row.get(misura.b5Num));
						misuraRinascita.setB5Ufp(row.get(misura.b5Ufp));
						misuraRinascita.setBfpNum(row.get(misura.bfpNum));
						misuraRinascita.setBfpUfp(row.get(misura.bfpUfp));
						misuraRinascita.setC1Num(row.get(misura.c1Num));
						misuraRinascita.setC1Ufp(row.get(misura.c1Ufp));
						misuraRinascita.setC2Num(row.get(misura.c2Num));
						misuraRinascita.setC2Ufp(row.get(misura.c2Ufp));
						misuraRinascita.setConfine(row.get(misura.confine));
						misuraRinascita.setCrudNum(row.get(misura.crudNum));
						misuraRinascita.setCrudUfp(row.get(misura.crudUfp));
						misuraRinascita.setD1Num(row.get(misura.d1Num));
						misuraRinascita.setD1Ufp(row.get(misura.d1Ufp));
						misuraRinascita.setD2Num(row.get(misura.d2Num));
						misuraRinascita.setD2Ufp(row.get(misura.d2Ufp));
						misuraRinascita.setDataConsolidamento(row
								.get(misura.dataConsolidamento));
						misuraRinascita.setDataCreazione(row
								.get(misura.dataCreazione));
						misuraRinascita.setDataFineVerificafp(row
								.get(misura.dataFineVerificafp));
						misuraRinascita.setDataInizioVerificaFp(row
								.get(misura.dataInizioVerificaFp));
						misuraRinascita.setDataRiferimento(row
								.get(misura.dataRiferimento));
						misuraRinascita.setDmalmStgMisuraPk(row
								.get(misura.dmalmStgMisuraPk));
						misuraRinascita.setEifNum(row.get(misura.eifNum));
						misuraRinascita.setEifUfp(row.get(misura.eifUfp));
						misuraRinascita.setEiNum(row.get(misura.eiNum));
						misuraRinascita.setEiUfp(row.get(misura.eiUfp));
						misuraRinascita.setEoNum(row.get(misura.eoNum));
						misuraRinascita.setEoUfp(row.get(misura.eoUfp));
						misuraRinascita.setEqNum(row.get(misura.eqNum));
						misuraRinascita.setEqUfp(row.get(misura.eqUfp));
						misuraRinascita.setEsperienza(row
								.get(misura.esperienza));
						misuraRinascita.setFaseCicloDiVita(row
								.get(misura.faseCicloDiVita));
						misuraRinascita.setFonti(row.get(misura.fonti));
						misuraRinascita.setFpNonPesatiMax(row
								.get(misura.fpNonPesatiMax));
						misuraRinascita.setFpNonPesatiMin(row
								.get(misura.fpNonPesatiMin));
						misuraRinascita.setFpNonPesatiUfp(row
								.get(misura.fpNonPesatiUfp));
						misuraRinascita.setFpPesatiMax(row
								.get(misura.fpPesatiMax));
						misuraRinascita.setFpPesatiMin(row
								.get(misura.fpPesatiMin));
						misuraRinascita.setFpPesatiUfp(row
								.get(misura.fpPesatiUfp));
						misuraRinascita.setFuNum(row.get(misura.fuNum));
						misuraRinascita.setFuUfp(row.get(misura.fuUfp));
						misuraRinascita.setGdgNum(row.get(misura.gdgNum));
						misuraRinascita.setGdgUfp(row.get(misura.gdgUfp));
						misuraRinascita.setGeifNum(row.get(misura.geifNum));
						misuraRinascita.setGeifUfp(row.get(misura.geifUfp));
						misuraRinascita.setGeiNum(row.get(misura.geiNum));
						misuraRinascita.setGeiUfp(row.get(misura.geiUfp));
						misuraRinascita.setGeoNum(row.get(misura.geoNum));
						misuraRinascita.setGeoUfp(row.get(misura.geoUfp));
						misuraRinascita.setGeqNum(row.get(misura.geqNum));
						misuraRinascita.setGeqUfp(row.get(misura.geqUfp));
						misuraRinascita.setGilfNum(row.get(misura.gilfNum));
						misuraRinascita.setGilfUfp(row.get(misura.gilfUfp));
						misuraRinascita.setGpNum(row.get(misura.gpNum));
						misuraRinascita.setGpUfp(row.get(misura.gpUfp));
						misuraRinascita.setIdMsr(row.get(misura.idMsr));
						misuraRinascita.setIfpNum(row.get(misura.ifpNum));
						misuraRinascita.setIfpUfp(row.get(misura.ifpUfp));
						misuraRinascita.setIlfNum(row.get(misura.ilfNum));
						misuraRinascita.setIlfUfp(row.get(misura.ilfUfp));
						misuraRinascita.setLdgNum(row.get(misura.ldgNum));
						misuraRinascita.setLdgUfp(row.get(misura.ldgUfp));
						misuraRinascita.setLinkDocumentale(row
								.get(misura.linkDocumentale));
						misuraRinascita.setMetodo(row.get(misura.metodo));
						misuraRinascita.setMfNum(row.get(misura.mfNum));
						misuraRinascita.setMfUfp(row.get(misura.mfUfp));
						misuraRinascita.setMldgNum(row.get(misura.mldgNum));
						misuraRinascita.setMldgUfp(row.get(misura.mldgUfp));
						misuraRinascita.setModello(row.get(misura.modello));
						misuraRinascita.setMpNum(row.get(misura.mpNum));
						misuraRinascita.setMpUfp(row.get(misura.mpUfp));
						misuraRinascita.setNomeMisura(row
								.get(misura.nomeMisura));
						misuraRinascita.setNoteMsr(row.get(misura.noteMsr));
						misuraRinascita.setPercentualeDiScostamento(row
								.get(misura.percentualeDiScostamento));
						misuraRinascita.setPfNum(row.get(misura.pfNum));
						misuraRinascita.setPfUfp(row.get(misura.pfUfp));
						misuraRinascita.setPostVerAddCfp(row
								.get(misura.postVerAddCfp));
						misuraRinascita.setPostVerChg(row
								.get(misura.postVerChg));
						misuraRinascita.setPostVerDel(row
								.get(misura.postVerDel));
						misuraRinascita.setPostVerFp(row.get(misura.postVerFp));
						misuraRinascita.setPreVerAddCfp(row
								.get(misura.preVerAddCfp));
						misuraRinascita.setPreVerChg(row.get(misura.preVerChg));
						misuraRinascita.setPreVerDel(row.get(misura.preVerDel));
						misuraRinascita.setPreVerFp(row.get(misura.preVerFp));
						misuraRinascita.setProgettoSfera(row
								.get(misura.progettoSfera));
						misuraRinascita.setResponsabile(row
								.get(misura.responsabile));
						misuraRinascita.setScopo(row.get(misura.scopo));
						misuraRinascita.setStatoMisura(row
								.get(misura.statoMisura));
						misuraRinascita.setTpNum(row.get(misura.tpNum));
						misuraRinascita.setTpUfp(row.get(misura.tpUfp));
						misuraRinascita.setUgdgNum(row.get(misura.ugdgNum));
						misuraRinascita.setUgdgUfp(row.get(misura.ugdgUfp));
						misuraRinascita.setUgepNum(row.get(misura.ugepNum));
						misuraRinascita.setUgepUfp(row.get(misura.ugepUfp));
						misuraRinascita.setUgoNum(row.get(misura.ugoNum));
						misuraRinascita.setUgoUfp(row.get(misura.ugoUfp));
						misuraRinascita.setUgpNum(row.get(misura.ugpNum));
						misuraRinascita.setUgpUfp(row.get(misura.ugpUfp));
						misuraRinascita.setUtenteMisuratore(row
								.get(misura.utenteMisuratore));
						misuraRinascita.setValoreScostamento(row
								.get(misura.valoreScostamento));
						misuraRinascita.setVersioneMsr(row
								.get(misura.versioneMsr));
						misuraRinascita.setDataModifica(dataEsecuzione);
						misuraRinascita.setDataStoricizzazione(dataEsecuzione);
						misuraRinascita.setIdProgetto(row
								.get(misura.idProgetto));
						misuraRinascita.setIdAsm(row.get(misura.idAsm));
						misuraRinascita.setDataCaricamento(dataEsecuzione);
						misuraRinascita.setDmalmPrjFk(DmAlmMisuraDAO
								.getProjectById(row.get(misura.idProgetto),
										dataEsecuzione));
						misuraRinascita.setRankStatoMisura(new Double(1));
						misuraRinascita.setAnnullato(null);

						logger.debug(" - RIPRISTINO MISURA: "
								+ misuraRinascita.getNomeMisura());

						// STORICIZZO
						// aggiorno la data di fine validita sul record
						// corrente
						DmAlmMisuraDAO.updateRankMisura(misuraRinascita,
								new Double(0));

						// inserisco un nuovo record
						DmAlmMisuraDAO.insertMisure(dataEsecuzione,
								misuraRinascita);

					}
				}
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		}

	}

}
