package lispa.schedulers.facade.cleaning;

import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.bean.target.sfera.DmalmMisura;
import lispa.schedulers.bean.target.sfera.DmalmProgettoSfera;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.dao.sfera.DmAlmMisuraDAO;
import lispa.schedulers.dao.sfera.DmAlmProgettoSferaDAO;
import lispa.schedulers.dao.sfera.StgMisuraDAO;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmMisura;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmProgettoSfera;
import lispa.schedulers.utils.MisuraUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckAnnullamentiSferaFacade {
	private static Logger logger = Logger
			.getLogger(CheckAnnullamentiSferaFacade.class);

	public static void execute() {
		try {
			QDmalmAsm asm = QDmalmAsm.dmalmAsm;
			QDmalmProgettoSfera progetto = QDmalmProgettoSfera.dmalmProgettoSfera;
			QDmalmMisura misura = QDmalmMisura.dmalmMisura;

			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();

			int totalAsmRemoved = 0;
			int totalProjectRemoved = 0;
			int totalMeasuresRemoved = 0;

			/**
			 * eseguo le query per trovare le Asm, i Progetti e le Misure
			 * presenti alla data dell'esecuzione precedente, ma non più
			 * presenti alla data di esecuzione attuale (eliminate fisicamente).
			 * Gli oggetti annullati sono storicizzati e inseriti con nuova
			 * validità e con il campo ANNULLATO impostato a
			 * "ANNULLATO FISICAMENTE"
			 */

			List<DmalmMisura> asmRemovedList = StgMisuraDAO.getRemoveFromFile(
					"ASM", dataEsecuzione);

			List<DmalmMisura> projectRemovedList = StgMisuraDAO
					.getRemoveFromFile("PRJ", dataEsecuzione);

			List<DmalmMisura> measuresRemovedList = StgMisuraDAO
					.getRemoveFromFile("MIS", dataEsecuzione);

			for (DmalmMisura asmRemoved : asmRemovedList) {
				logger.debug("Riga da annullare - ASM: "
						+ asmRemoved.getIdAsm());

				DmalmAsm asmToRemove = new DmalmAsm();
				asmToRemove.setIdAsm(asmRemoved.getIdAsm());
				List<Tuple> target = DmAlmAsmDAO.getAsm(asmToRemove);

				if (target.size() > 0) {
					for (Tuple row : target) {
						if (row != null) {
							// se non è già annullato proseguo
							if (row.get(asm.annullato) == null
									|| !row.get(asm.annullato)
											.equalsIgnoreCase(
													DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE)) {
								// setto i valori del Target da duplicare
								asmToRemove = MisuraUtils.tuplaToAsm(row);
								asmToRemove.setDataCaricamento(dataEsecuzione);
								asmToRemove
										.setAnnullato(DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE);

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								DmAlmAsmDAO.updateDataFineValidita(
										dataEsecuzione, asmToRemove);

								// inserisco un nuovo record con impostato
								// ANNULLATO FISICAMENTE
								DmAlmAsmDAO.insertAsmUpdate(dataEsecuzione,
										asmToRemove);

								totalAsmRemoved++;
							}
						}
					}
				}
			}

			for (DmalmMisura projectRemoved : projectRemovedList) {
				logger.debug("Riga da annullare - ASM: "
						+ projectRemoved.getIdAsm() + ", PROJECT: "
						+ projectRemoved.getIdProgetto());

				DmalmProgettoSfera projectToRemove = new DmalmProgettoSfera();
				projectToRemove.setIdAsm(projectRemoved.getIdAsm());
				projectToRemove.setIdProgetto(projectRemoved.getIdProgetto());
				List<Tuple> target = DmAlmProgettoSferaDAO
						.getProgetto(projectToRemove);

				if (target.size() > 0) {
					for (Tuple row : target) {
						if (row != null) {
							// se non è già annullato proseguo
							if (row.get(progetto.annullato) == null
									|| !row.get(progetto.annullato)
											.equalsIgnoreCase(
													DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE)) {

								logger.debug(" - annullo Progetto: "
										+ projectRemoved.getIdAsm() + " - "
										+ projectRemoved.getIdProgetto());

								// setto i valori del Target da duplicare
								projectToRemove.setDataFineEffettiva(row
										.get(progetto.dataFineEffettiva));
								projectToRemove.setDataAvvio(row
										.get(progetto.dataAvvio));
								projectToRemove.setIdProgetto(row
										.get(progetto.idProgetto));
								projectToRemove.setImpegnoEffettivo(row
										.get(progetto.impegnoEffettivo));
								projectToRemove.setIncludeInBenchmarkingDb(row
										.get(progetto.includeInBenchmarkingDb));
								projectToRemove.setNotePrj(row
										.get(progetto.notePrj));
								projectToRemove.setpPrjADisposizione01(row
										.get(progetto.pPrjADisposizione01));
								projectToRemove.setpPrjADisposizione02(row
										.get(progetto.pPrjADisposizione02));
								projectToRemove.setpPrjAmbTecnPortali(row
										.get(progetto.pPrjAmbTecnPortali));
								projectToRemove.setpPrjAuditIndexVerify(row
										.get(progetto.pPrjAuditIndexVerify));
								projectToRemove.setpPrjAuditMonitore(row
										.get(progetto.pPrjAuditMonitore));
								projectToRemove.setpPrjCodRdi(row
										.get(progetto.pPrjCodRdi));
//								projectToRemove.setpPrjDtConsegnaMppForn(row
//										.get(progetto.pPrjDtConsegnaMppForn));
								projectToRemove.setpPrjFccFattCorrezTotal(row
										.get(progetto.pPrjFccFattCorrezTotal));
								projectToRemove.setpPrjFlAmbTecTransBatRep(row
										.get(progetto.pPrjFlAmbTecTransBatRep));
								projectToRemove
										.setpPrjFlAmbTecnPiatEnterpr(row
												.get(progetto.pPrjFlAmbTecnPiatEnterpr));
								projectToRemove
										.setpPrjFlAmbitoTecnFuturo01(row
												.get(progetto.pPrjFlAmbitoTecnFuturo01));
								projectToRemove
										.setpPrjFlAmbitoTecnFuturo02(row
												.get(progetto.pPrjFlAmbitoTecnFuturo02));
								projectToRemove.setpPrjFlApplLgFpEdma(row
										.get(progetto.pPrjFlApplLgFpEdma));
								projectToRemove.setpPrjFlApplLgFpWeb(row
										.get(progetto.pPrjFlApplLgFpWeb));
								projectToRemove.setpPrjFlApplicLgFpDwh(row
										.get(progetto.pPrjFlApplicLgFpDwh));
								projectToRemove
										.setpPrjFlApplicLgFpFuturo01(row
												.get(progetto.pPrjFlApplicLgFpFuturo01));
								projectToRemove
										.setpPrjFlApplicLgFpFuturo02(row
												.get(progetto.pPrjFlApplicLgFpFuturo02));
								projectToRemove.setpPrjFlagAmbTecnGis(row
										.get(progetto.pPrjFlagAmbTecnGis));
								projectToRemove.setpPrjFlagApplLgFpGis(row
										.get(progetto.pPrjFlagApplLgFpGis));
								projectToRemove.setpPrjFlagApplLgFpMware(row
										.get(progetto.pPrjFlagApplLgFpMware));
								projectToRemove.setpPrjImportoAConsuntivo(row
										.get(progetto.pPrjImportoAConsuntivo));
								projectToRemove
										.setpPrjImportoRdiAPreventivo(row
												.get(progetto.pPrjImportoRdiAPreventivo));
								projectToRemove
										.setpPrjIndexAlmValidProgAsm(row
												.get(progetto.pPrjIndexAlmValidProgAsm));
								projectToRemove.setpPrjMfcAConsuntivo(row
										.get(progetto.pPrjMfcAConsuntivo));
								projectToRemove.setpPrjMfcAPreventivo(row
										.get(progetto.pPrjMfcAPreventivo));
								projectToRemove
										.setpPrjMpPercentCicloDiVita(row
												.get(progetto.pPrjMpPercentCicloDiVita));
								projectToRemove
										.setpPrjPunPrezzoUnitNominal(row
												.get(progetto.pPrjPunPrezzoUnitNominal));
								projectToRemove.setPrjCls(row
										.get(progetto.prjCls));
								projectToRemove.setVersionePrj(row
										.get(progetto.versionePrj));
								projectToRemove.setDataModifica(dataEsecuzione);
								projectToRemove.setDmalmStgMisuraPk(row
										.get(progetto.dmalmStgMisuraPk));
								projectToRemove
										.setpPrjFornitoreSviluppoMev(row
												.get(progetto.pPrjFornitoreSviluppoMev));
								projectToRemove.setNomeProgetto(row
										.get(progetto.nomeProgetto));
								projectToRemove.setTipoProgetto(row
										.get(progetto.tipoProgetto));
								projectToRemove.setCosto(row
										.get(progetto.costo));
								projectToRemove.setDurataEffettiva(row
										.get(progetto.durataEffettiva));
								projectToRemove.setStaffMedio(row
										.get(progetto.staffMedio));
								projectToRemove
										.setDataCaricamento(dataEsecuzione);
								projectToRemove.setpPrjFornitoreMpp(row
										.get(progetto.pPrjFornitoreMpp));
								projectToRemove.setCicloDiVita(row
										.get(progetto.cicloDiVita));
								projectToRemove.setIdAsm(row
										.get(progetto.idAsm));
								projectToRemove
										.setDmalmAsmFk(DmAlmProgettoSferaDAO.getAsmByID(
												row.get(progetto.idAsm),
												dataEsecuzione));
								projectToRemove
										.setAnnullato(DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE);

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								DmAlmProgettoSferaDAO.updateDataFineValidita(
										dataEsecuzione, projectToRemove);
								;

								// inserisco un nuovo record
								DmAlmProgettoSferaDAO.insertProgettoSfera(
										projectToRemove, dataEsecuzione);

								totalProjectRemoved++;
							}
						}
					}
				}
			}

			for (DmalmMisura misuraRemoved : measuresRemovedList) {
				logger.debug("Riga da annullare - ASM: "
						+ misuraRemoved.getIdAsm() + ", PROJECT: "
						+ misuraRemoved.getIdProgetto() + ", MISURA: "
						+ misuraRemoved.getIdMsr());

				List<Tuple> target = DmAlmMisuraDAO.getMisura(misuraRemoved);

				if (target.size() > 0) {
					for (Tuple row : target) {
						if (row != null) {
							// se non è già annullato proseguo

							if (row.get(misura.annullato) == null
									|| !row.get(misura.annullato)
											.equalsIgnoreCase(
													DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE)) {

								logger.debug(" - annullo Misura: "
										+ misuraRemoved.getIdAsm() + " - "
										+ misuraRemoved.getIdProgetto() + " - "
										+ misuraRemoved.getIdMsr());
								logger.debug(" - annullo Misura - nuova PK calcolata con select: "
										+ misuraRemoved.getDmalmMisuraPk());

								misuraRemoved.setA1Num(row.get(misura.a1Num));
								misuraRemoved.setA1Ufp(row.get(misura.a1Ufp));
								misuraRemoved.setA2Num(row.get(misura.a2Num));
								misuraRemoved.setA2Ufp(row.get(misura.a1Ufp));
								misuraRemoved.setAdjmax(row.get(misura.adjmax));
								misuraRemoved.setAdjmin(row.get(misura.adjmin));
								misuraRemoved.setAdjufp(row.get(misura.adjufp));
								misuraRemoved.setAmbito(row.get(misura.ambito));
								misuraRemoved.setApproccio(row
										.get(misura.approccio));
								misuraRemoved.setB1Num(row.get(misura.b1Num));
								misuraRemoved.setB1Ufp(row.get(misura.b1Ufp));
								misuraRemoved.setB2Num(row.get(misura.b2Num));
								misuraRemoved.setB2Ufp(row.get(misura.b2Ufp));
								misuraRemoved.setB3Num(row.get(misura.b3Num));
								misuraRemoved.setB3Ufp(row.get(misura.b3Ufp));
								misuraRemoved.setB5Num(row.get(misura.b5Num));
								misuraRemoved.setB5Ufp(row.get(misura.b5Ufp));
								misuraRemoved.setBfpNum(row.get(misura.bfpNum));
								misuraRemoved.setBfpUfp(row.get(misura.bfpUfp));
								misuraRemoved.setC1Num(row.get(misura.c1Num));
								misuraRemoved.setC1Ufp(row.get(misura.c1Ufp));
								misuraRemoved.setC2Num(row.get(misura.c2Num));
								misuraRemoved.setC2Ufp(row.get(misura.c2Ufp));
								misuraRemoved.setConfine(row
										.get(misura.confine));
								misuraRemoved.setCrudNum(row
										.get(misura.crudNum));
								misuraRemoved.setCrudUfp(row
										.get(misura.crudUfp));
								misuraRemoved.setD1Num(row.get(misura.d1Num));
								misuraRemoved.setD1Ufp(row.get(misura.d1Ufp));
								misuraRemoved.setD2Num(row.get(misura.d2Num));
								misuraRemoved.setD2Ufp(row.get(misura.d2Ufp));
								misuraRemoved.setDataConsolidamento(row
										.get(misura.dataConsolidamento));
								misuraRemoved.setDataCreazione(row
										.get(misura.dataCreazione));
								misuraRemoved.setDataFineVerificafp(row
										.get(misura.dataFineVerificafp));
								misuraRemoved.setDataInizioVerificaFp(row
										.get(misura.dataInizioVerificaFp));
								misuraRemoved.setDataRiferimento(row
										.get(misura.dataRiferimento));
								misuraRemoved.setDmalmStgMisuraPk(row
										.get(misura.dmalmStgMisuraPk));
								misuraRemoved.setEifNum(row.get(misura.eifNum));
								misuraRemoved.setEifUfp(row.get(misura.eifUfp));
								misuraRemoved.setEiNum(row.get(misura.eiNum));
								misuraRemoved.setEiUfp(row.get(misura.eiUfp));
								misuraRemoved.setEoNum(row.get(misura.eoNum));
								misuraRemoved.setEoUfp(row.get(misura.eoUfp));
								misuraRemoved.setEqNum(row.get(misura.eqNum));
								misuraRemoved.setEqUfp(row.get(misura.eqUfp));
								misuraRemoved.setEsperienza(row
										.get(misura.esperienza));
								misuraRemoved.setFaseCicloDiVita(row
										.get(misura.faseCicloDiVita));
								misuraRemoved.setFonti(row.get(misura.fonti));
								misuraRemoved.setFpNonPesatiMax(row
										.get(misura.fpNonPesatiMax));
								misuraRemoved.setFpNonPesatiMin(row
										.get(misura.fpNonPesatiMin));
								misuraRemoved.setFpNonPesatiUfp(row
										.get(misura.fpNonPesatiUfp));
								misuraRemoved.setFpPesatiMax(row
										.get(misura.fpPesatiMax));
								misuraRemoved.setFpPesatiMin(row
										.get(misura.fpPesatiMin));
								misuraRemoved.setFpPesatiUfp(row
										.get(misura.fpPesatiUfp));
								misuraRemoved.setFuNum(row.get(misura.fuNum));
								misuraRemoved.setFuUfp(row.get(misura.fuUfp));
								misuraRemoved.setGdgNum(row.get(misura.gdgNum));
								misuraRemoved.setGdgUfp(row.get(misura.gdgUfp));
								misuraRemoved.setGeifNum(row
										.get(misura.geifNum));
								misuraRemoved.setGeifUfp(row
										.get(misura.geifUfp));
								misuraRemoved.setGeiNum(row.get(misura.geiNum));
								misuraRemoved.setGeiUfp(row.get(misura.geiUfp));
								misuraRemoved.setGeoNum(row.get(misura.geoNum));
								misuraRemoved.setGeoUfp(row.get(misura.geoUfp));
								misuraRemoved.setGeqNum(row.get(misura.geqNum));
								misuraRemoved.setGeqUfp(row.get(misura.geqUfp));
								misuraRemoved.setGilfNum(row
										.get(misura.gilfNum));
								misuraRemoved.setGilfUfp(row
										.get(misura.gilfUfp));
								misuraRemoved.setGpNum(row.get(misura.gpNum));
								misuraRemoved.setGpUfp(row.get(misura.gpUfp));
								misuraRemoved.setIdMsr(row.get(misura.idMsr));
								misuraRemoved.setIfpNum(row.get(misura.ifpNum));
								misuraRemoved.setIfpUfp(row.get(misura.ifpUfp));
								misuraRemoved.setIlfNum(row.get(misura.ilfNum));
								misuraRemoved.setIlfUfp(row.get(misura.ilfUfp));
								misuraRemoved.setLdgNum(row.get(misura.ldgNum));
								misuraRemoved.setLdgUfp(row.get(misura.ldgUfp));
								misuraRemoved.setLinkDocumentale(row
										.get(misura.linkDocumentale));
								misuraRemoved.setMetodo(row.get(misura.metodo));
								misuraRemoved.setMfNum(row.get(misura.mfNum));
								misuraRemoved.setMfUfp(row.get(misura.mfUfp));
								misuraRemoved.setMldgNum(row
										.get(misura.mldgNum));
								misuraRemoved.setMldgUfp(row
										.get(misura.mldgUfp));
								misuraRemoved.setModello(row
										.get(misura.modello));
								misuraRemoved.setMpNum(row.get(misura.mpNum));
								misuraRemoved.setMpUfp(row.get(misura.mpUfp));
								misuraRemoved.setNomeMisura(row
										.get(misura.nomeMisura));
								misuraRemoved.setNoteMsr(row
										.get(misura.noteMsr));
								misuraRemoved.setPercentualeDiScostamento(row
										.get(misura.percentualeDiScostamento));
								misuraRemoved.setPfNum(row.get(misura.pfNum));
								misuraRemoved.setPfUfp(row.get(misura.pfUfp));
								misuraRemoved.setPostVerAddCfp(row
										.get(misura.postVerAddCfp));
								misuraRemoved.setPostVerChg(row
										.get(misura.postVerChg));
								misuraRemoved.setPostVerDel(row
										.get(misura.postVerDel));
								misuraRemoved.setPostVerFp(row
										.get(misura.postVerFp));
								misuraRemoved.setPreVerAddCfp(row
										.get(misura.preVerAddCfp));
								misuraRemoved.setPreVerChg(row
										.get(misura.preVerChg));
								misuraRemoved.setPreVerDel(row
										.get(misura.preVerDel));
								misuraRemoved.setPreVerFp(row
										.get(misura.preVerFp));
								misuraRemoved.setProgettoSfera(row
										.get(misura.progettoSfera));
								misuraRemoved.setResponsabile(row
										.get(misura.responsabile));
								misuraRemoved.setScopo(row.get(misura.scopo));
								misuraRemoved.setStatoMisura(row
										.get(misura.statoMisura));
								misuraRemoved.setTpNum(row.get(misura.tpNum));
								misuraRemoved.setTpUfp(row.get(misura.tpUfp));
								misuraRemoved.setUgdgNum(row
										.get(misura.ugdgNum));
								misuraRemoved.setUgdgUfp(row
										.get(misura.ugdgUfp));
								misuraRemoved.setUgepNum(row
										.get(misura.ugepNum));
								misuraRemoved.setUgepUfp(row
										.get(misura.ugepUfp));
								misuraRemoved.setUgoNum(row.get(misura.ugoNum));
								misuraRemoved.setUgoUfp(row.get(misura.ugoUfp));
								misuraRemoved.setUgpNum(row.get(misura.ugpNum));
								misuraRemoved.setUgpUfp(row.get(misura.ugpUfp));
								misuraRemoved.setUtenteMisuratore(row
										.get(misura.utenteMisuratore));
								misuraRemoved.setValoreScostamento(row
										.get(misura.valoreScostamento));
								misuraRemoved.setVersioneMsr(row
										.get(misura.versioneMsr));
								misuraRemoved.setDataModifica(dataEsecuzione);
								misuraRemoved
										.setDataStoricizzazione(dataEsecuzione);
								misuraRemoved.setRankStatoMisura(new Double(1));
								misuraRemoved.setIdProgetto(row
										.get(misura.idProgetto));
								misuraRemoved.setIdAsm(row.get(misura.idAsm));
								misuraRemoved
										.setDataCaricamento(dataEsecuzione);
								misuraRemoved.setDmalmPrjFk(DmAlmMisuraDAO
										.getProjectById(
												row.get(misura.idProgetto),
												dataEsecuzione));
								misuraRemoved
										.setAnnullato(DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE);

								// STORICIZZO
								DmAlmMisuraDAO.updateRankMisura(misuraRemoved,
										new Double(0));

								// inserisco un nuovo record
								DmAlmMisuraDAO.insertMisure(dataEsecuzione,
										misuraRemoved);

								totalMeasuresRemoved++;
							}
						}
					}
				}
			}

			logger.info("Totale ASM annullate fisicamente: " + totalAsmRemoved);
			logger.info("Totale PROGETTI annullati fisicamente: "
					+ totalProjectRemoved);
			logger.info("Totale MISURE annullate fisicamente: "
					+ totalMeasuresRemoved);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
}
