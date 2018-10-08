package lispa.schedulers.facade.sfera.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;
import lispa.schedulers.utils.MisuraUtils;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;

public class AsmFacade {
	private static Logger logger = Logger.getLogger(AsmFacade.class);

	public static void execute(Timestamp dataEsecuzione) throws Exception,
			DAOException {

		// Se errore precedente non eseguo nulla
		if (ErrorManager.getInstance().hasError())
			return;

		logger.info("START AsmFacade.execute");

		List<DmalmAsm> asmStg = new ArrayList<DmalmAsm>();
		List<Tuple> target = new ArrayList<Tuple>();
		QDmalmAsm asm = QDmalmAsm.dmalmAsm;

		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;
		DmalmAsm asmTmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			asmStg = DmAlmAsmDAO.getAllAsm(dataEsecuzione);

			for (DmalmAsm applicazioni : asmStg) {
				asmTmp = applicazioni;
				target = DmAlmAsmDAO.getAsm(applicazioni);

				// Settare sempre il campo ANNULLLAMENTO di applicazioni
				// testando il campo applicazione appena arrivato.
				// Se e' un annullamento valorizzare il campo ANNULLLAMENTO con
				// "ANNULLATO LOGICAMENTE" altrimenti valorizzarlo a blank.
				// Eseguire il test come fosse SCD ed il resto viene
				// automaticamente.
				// Pulire il campo applicazione prima dei test SCD in modo di
				// scriverlo pulito nel target
				// esempio stringa "#ANNULLATO LOGICAMENTE##20120802#GIIL"

				if (applicazioni.getApplicazione().startsWith(
						DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)) {
					applicazioni
							.setAnnullato(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE);

					if (applicazioni.getpAppDataFineValiditaAsm() == null) {
						try {
							String strData = applicazioni.getApplicazione()
									.substring(24, 32);
							Timestamp dataFineValiditaAsm = DateUtils
									.stringToTimestamp(strData + " 00:00:00",
											"yyyyMMdd 00:00:00");
							applicazioni
									.setpAppDataFineValiditaAsm(dataFineValiditaAsm);
						} catch (Exception e) {
						}
					}
				}
				
				// se non trovo almento un record, inserisco la nuova struttura
				// organizzativa nel target
				if (target.size() == 0) {
					righeNuove++;
					// per l'annullamento non toccare nulla ha tutti i dati a
					// posto
					DmAlmAsmDAO.insertAsm(dataEsecuzione, applicazioni);

					rinascitaFisicaAsm(dataEsecuzione, applicazioni);

				} else {

					for (Tuple row : target) {
						// aggiorno la data di fine validita del record corrente

						if (row != null) {
							boolean modificato = false;
							if (BeanUtils.areDifferent(
									row.get(asm.applicazione),
									applicazioni.getApplicazione())) {
								modificato = true;
							}

							if (BeanUtils.areDifferent(
									row.get(asm.pAppDenominazioneAsm),
									applicazioni.getpAppDenominazioneAsm())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppDataUltimoAggiorn),
									applicazioni.getpAppDataUltimoAggiorn())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppAccAuthLastUpdate),
									applicazioni.getpAppAccAuthLastUpdate())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppNomeAuthLastUpdate),
									applicazioni.getpAppNomeAuthLastUpdate())) {
								modificato = true;
							}
							if (BeanUtils
									.areDifferent(
											row.get(asm.pAppDataInizioValiditaAsm),
											applicazioni
													.getpAppDataInizioValiditaAsm())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppDataFineValiditaAsm),
									applicazioni.getpAppDataFineValiditaAsm())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppDenomUtentiFinaliAsm),
									applicazioni.getpAppDenomUtentiFinaliAsm())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppCodAsmConfinanti),
									applicazioni.getpAppCodAsmConfinanti())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppDenomSistTerziConfin),
									applicazioni.getpAppDenomSistTerziConfin())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppCodFlussiIoAsm),
									applicazioni.getpAppCodFlussiIoAsm())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppFlagServizioComune),
									applicazioni.getpAppFlagServizioComune())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppCdAltreAsmCommonServ),
									applicazioni.getpAppCdAltreAsmCommonServ())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppFlagMisurareSvimevFp),
									applicazioni.getpAppFlagMisurareSvimevFp())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppFlagDamisurarePatrFp),
									applicazioni.getpAppFlagDamisurarePatrFp())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppFlagInManutenzione),
									applicazioni.getpAppFlagInManutenzione())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppCdAmbitoManAttuale),
									applicazioni.getpAppCdAmbitoManAttuale())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppCodAmbitoManFuturo),
									applicazioni.getpAppCodAmbitoManFuturo())) {
								modificato = true;
							}
							if (BeanUtils.areDifferent(
									row.get(asm.pAppCodDirezioneDemand),
									applicazioni.getpAppCodDirezioneDemand())) {
								modificato = true;
							}

							if (modificato) {
								righeModificate++;

								// STORICIZZO
								// aggiorno la data di fine validita sul record
								// corrente
								DmAlmAsmDAO.updateDataFineValidita(
										dataEsecuzione, applicazioni);

								// inserisco un nuovo record

								DmAlmAsmDAO.insertAsmUpdate(dataEsecuzione,
										applicazioni);
							} else {
								// Aggiorno lo stesso
								DmAlmAsmDAO.updateAsm(applicazioni);
							}
						}
					}
				}
			}
			
			//DMALM-216 associazione project Unità Organizzativa Flat
			//ricarica il valore della Fk ad ogni esecuzione
			
			recalculateUoFkFlat();
			checkIfAsmContainsBlank(dataEsecuzione);

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(asmTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(asmTmp));
			logger.error(e.getMessage(), e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {

				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_ASM, stato, new Timestamp(
								dtInizioCaricamento.getTime()), new Timestamp(
								dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);
			} catch (DAOException | SQLException e) {
				ErrorManager.getInstance().exceptionOccurred(true, e);
				logger.error(e.getMessage(), e);

			}

			logger.info("STOP AsmFacade.execute");
		}
	}

	private static void checkIfAsmContainsBlank(Timestamp dataEsecuzione) throws Exception {
		List<DmalmAsm> asmList = DmAlmAsmDAO.getAllAsm(dataEsecuzione);
		for(DmalmAsm asm:asmList){
			if(asm.getApplicazione().contains("#"))
				asm.setApplicazione(asm.getApplicazione().substring(0,asm.getApplicazione().indexOf("#")));
			if(asm.getApplicazione().endsWith(" ")){
							ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						 DmAlmConstants.TARGET_PROGETTO_SFERA,
						 "Possibile presenza spazi in ASM PK:"+ asm.getDmalmAsmPk()+" ASM NAME: "+asm.getApplicazione(),
						 DmAlmConstants.ERRORE_SPAZI_NOME_ASM,
						 DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						 MisuraUtils.getPkTarget(DmAlmConstants.PK_TARGET_PROGETTO_SFERA, DmAlmConstants.TARGET_PROGETTO_SFERA, Integer.parseInt(String.valueOf(asm.getIdAsm()))), dataEsecuzione);
				
			}
		}
		
	}

	public static void recalculateUoFkFlat() throws PropertiesReaderException, DAOException, Exception {
		QueryManager qm = QueryManager.getInstance();

		logger.info("INIZIO Update ASM UnitaOrganizzativaFlatFk");
		
		qm.executeMultipleStatementsFromFile(
				DmAlmConstants.M_UPDATE_ASM_UOFLATFK,
				DmAlmConstants.M_SEPARATOR);
		
		logger.info("FINE Update ASM UnitaOrganizzativaFlatFk");
	}

	private static void rinascitaFisicaAsm(Timestamp dataEsecuzione,
			DmalmAsm applicazioni) {
		try {
			QDmalmAsm asm = QDmalmAsm.dmalmAsm;

			List<Tuple> target = DmAlmAsmDAO.getAsmByApplicazione(applicazioni);

			if (target.size() > 0) {
				for (Tuple row : target) {
					if (row != null) {

						DmalmAsm asmRinascita = new DmalmAsm();

						asmRinascita.setIdAsm(row.get(asm.idAsm));
						asmRinascita.setDmalmStgMisuraPk(row
								.get(asm.dmalmStgMisuraPk));
						asmRinascita.setNoteAsm(row.get(asm.noteAsm));
						asmRinascita.setpAppAccAuthLastUpdate(row
								.get(asm.pAppAccAuthLastUpdate));
						asmRinascita.setpAppCdAltreAsmCommonServ(row
								.get(asm.pAppCdAltreAsmCommonServ));
						asmRinascita.setpAppCdAmbitoManAttuale(row
								.get(asm.pAppCdAmbitoManAttuale));
						asmRinascita.setpAppCodAmbitoManFuturo(row
								.get(asm.pAppCodAmbitoManFuturo));
						asmRinascita.setpAppCodAsmConfinanti(row
								.get(asm.pAppCodAsmConfinanti));
						asmRinascita.setpAppCodDirezioneDemand(row
								.get(asm.pAppCodDirezioneDemand));
						asmRinascita.setpAppCodFlussiIoAsm(row
								.get(asm.pAppCodFlussiIoAsm));
						asmRinascita.setpAppDataFineValiditaAsm(row
								.get(asm.pAppDataFineValiditaAsm));
						asmRinascita.setpAppDataInizioValiditaAsm(row
								.get(asm.pAppDataInizioValiditaAsm));
						asmRinascita.setpAppDataUltimoAggiorn(row
								.get(asm.pAppDataUltimoAggiorn));
						asmRinascita.setpAppDenomSistTerziConfin(row
								.get(asm.pAppDenomSistTerziConfin));
						asmRinascita.setpAppDenomUtentiFinaliAsm(row
								.get(asm.pAppDenomUtentiFinaliAsm));
						asmRinascita.setpAppDenominazioneAsm(row
								.get(asm.pAppDenominazioneAsm));
						asmRinascita.setpAppFlagDamisurarePatrFp(row
								.get(asm.pAppFlagDamisurarePatrFp));
						asmRinascita.setpAppFlagInManutenzione(row
								.get(asm.pAppFlagInManutenzione));
						asmRinascita.setpAppFlagMisurareSvimevFp(row
								.get(asm.pAppFlagMisurareSvimevFp));
						asmRinascita.setpAppFlagServizioComune(row
								.get(asm.pAppFlagServizioComune));
						asmRinascita.setpAppIndicValidazioneAsm(row
								.get(asm.pAppIndicValidazioneAsm));
						asmRinascita.setpAppNomeAuthLastUpdate(row
								.get(asm.pAppNomeAuthLastUpdate));
						asmRinascita.setAppCls(row.get(asm.appCls));
						asmRinascita.setApplicazione(row.get(asm.applicazione));
						asmRinascita.setDataDismissione(row
								.get(asm.dataDismissione));
						asmRinascita.setDataInizioEsercizio(row
								.get(asm.dataInizioEsercizio));
						asmRinascita.setDataCaricamento(dataEsecuzione);
						asmRinascita.setFrequenzaUtilizzo(row
								.get(asm.frequenzaUtilizzo));
						asmRinascita.setIncludiInDbPatrimonio(row
								.get(asm.includiInDbPatrimonio));
						asmRinascita.setNumeroUtenti(row.get(asm.numeroUtenti));
						asmRinascita.setPermissions(row.get(asm.permissions));
						asmRinascita.setProprietaLegale(row
								.get(asm.proprietaLegale));
						asmRinascita.setUtilizzata(row.get(asm.utilizzata));
						asmRinascita.setVafPredefinito(row
								.get(asm.vafPredefinito));
						asmRinascita.setAnnullato(null);
						asmRinascita.setStrutturaOrganizzativaFk(row
								.get(asm.strutturaOrganizzativaFk));
						asmRinascita.setUnitaOrganizzativaFk(row
								.get(asm.unitaOrganizzativaFk));
						asmRinascita.setDtPrevistaProssimaMpp(row
								.get(asm.dtPrevistaProssimaMpp));
						asmRinascita.setFip01InizioEsercizio(row
								.get(asm.fip01InizioEsercizio));
						asmRinascita.setFip02IndiceQualita(row
								.get(asm.fip02IndiceQualita));
						asmRinascita.setFip03ComplessEserc(row
								.get(asm.fip03ComplessEserc));
						asmRinascita.setFip04NrPiattaforma(row
								.get(asm.fip04NrPiattaforma));
						asmRinascita.setFip07LingProgPrincipale(row
								.get(asm.fip07LingProgPrincipale));
						asmRinascita.setFip10GradoAccessibilita(row
								.get(asm.fip10GradoAccessibilita));
						asmRinascita.setFip11GradoQualitaCod(row
								.get(asm.fip11GradoQualitaCod));
						asmRinascita.setFip12UtilizzoFramework(row
								.get(asm.fip12UtilizzoFramework));
						asmRinascita.setFip13ComplessitaAlg(row
								.get(asm.fip13ComplessitaAlg));
						asmRinascita.setFip15LivelloCura(row
								.get(asm.fip15LivelloCura));

						logger.debug(" - RIPRISTINO ASM: "
								+ asmRinascita.getApplicazione());

						// STORICIZZO
						// aggiorno la data di fine validita sul record
						// corrente
						DmAlmAsmDAO.updateDataFineValidita(dataEsecuzione,
								asmRinascita);

						// inserisco un nuovo record come rinascita del
						// precedente annullato
						//
						DmAlmAsmDAO.insertAsmUpdate(dataEsecuzione,
								asmRinascita);

					}
				}
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		}

	}

}
