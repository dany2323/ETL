package lispa.schedulers.facade.cleaning;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.sfera.StgMisuraDAO;
import lispa.schedulers.queryimplementation.staging.sfera.QDmalmStgMisura;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.MisuraUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

public class CheckSferaMisureFacade implements Runnable {
	private Logger logger;
	private Timestamp dataEsecuzione;
	private boolean isAlive = true;
	private static QDmalmStgMisura stgMisura = QDmalmStgMisura.dmalmStgMisura;

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}

	public CheckSferaMisureFacade(Logger logger, Timestamp dataEsecuzione) {
		this.logger = logger;
		this.dataEsecuzione = dataEsecuzione;

	}

	@Override
	public void run() {
		execute(logger, dataEsecuzione);
		setAlive(false);

	}

	public static void execute(Logger logger, Timestamp dataEsecuzione) {
		logger.info("START CheckSferaMisureFacade.execute " + new Date());

		int erroriNonBloccanti = 0;

		try {

			logger.debug("START checkControlloDatiSfera " + new Date());
			erroriNonBloccanti += checkControlloDatiSfera(logger,
					dataEsecuzione);

			logger.debug("Errori non bloccanti inseriti: " + erroriNonBloccanti);

			logger.info("STOP CheckSferaMisureFacade.execute " + new Date());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}

	public static int checkControlloDatiSfera(Logger logger,
			Timestamp dataEsecuzione) {
		List<Tuple> dati_sfera = null;
		int errore = 0;

		Integer idAsmAppoggio = -1;
		Integer idProgettoAppoggio = -1;
		boolean okVerificaAsm = false;
		boolean okVerificaProgetto = false;

		try {

			// logger.debug("INIZIO checkControlloDatiSfera ");

			dati_sfera = StgMisuraDAO.getControlloDatiSfera(dataEsecuzione);

			logger.debug("INIZIO checkControlloDatiSfera - size: "
					+ dati_sfera.size());

			for (Tuple row : dati_sfera) {
				okVerificaAsm = false;
				okVerificaProgetto = false;

				// I controlli Asm vengono effettuati solo una volta per ogni
				// Asm in modo da non appesantire il log
				if (!row.get(stgMisura.idAsm).equals(idAsmAppoggio)) {
					idAsmAppoggio = row.get(stgMisura.idAsm);
					idProgettoAppoggio = -1;
					okVerificaAsm = true;
				}

				// I controlli prj vengono effettuati solo una volta per ogni
				// Prj della stessa Asm in modo da non appesantire il log
				if (!row.get(stgMisura.idProgetto).equals(idProgettoAppoggio)) {
					idProgettoAppoggio = row.get(stgMisura.idProgetto);
					okVerificaProgetto = true;
				}

				String controlloAsm = row
						.get(stgMisura.pAppIndicValidazioneAsm) == null ? "0"
						: row.get(stgMisura.pAppIndicValidazioneAsm) == "00" ? "0"
								: row.get(stgMisura.pAppIndicValidazioneAsm);

				// Devo ogni volta riposizionare i flag ControlloAsm
				// ControlloPrj
				if (!controlloAsm.equalsIgnoreCase("0")
						&& !controlloAsm.equalsIgnoreCase("1")) {
					controlloAsm = "0";
					if (okVerificaAsm) {
						errore++;
						ErroriCaricamentoDAO
								.insert(DmAlmConstants.FONTE_MISURA,
										DmAlmConstants.TARGET_ASM,
										MisuraUtils.MisuraToString(row),
										"APP-ATT:INDICATORE_ALM_PER_VALIDAZIONE_ASM non e' valorizzato correttamente",
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
					}
				}

				// ControlloPrj = "0" vengono effettuati i controlli
				String controlloPrj = row
						.get(stgMisura.pPrjIndexAlmValidProgAsm) == null ? "0"
						: row.get(stgMisura.pPrjIndexAlmValidProgAsm) == "00" ? "0"
								: row.get(stgMisura.pPrjIndexAlmValidProgAsm);

				if (!controlloPrj.equalsIgnoreCase("0")
						&& !controlloPrj.equalsIgnoreCase("1")) {
					controlloPrj = "0";
					if (okVerificaProgetto) {
						errore++;
						ErroriCaricamentoDAO
								.insert(DmAlmConstants.FONTE_MISURA,
										DmAlmConstants.TARGET_PROGETTO_SFERA,
										MisuraUtils.MisuraToString(row),
										"PRJ-ATT:INDICATORE_ALM_PER_VALIDAZ_PROGETTO_ASM non e' valorizzato correttamente",
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
					}
				}

				if (Integer.parseInt(controlloPrj) == 0) {
					if (row.get(stgMisura.idProgetto) == null
							|| row.get(stgMisura.idProgetto).equals(
									new Integer(0))) {
						controlloPrj = "1";
					}
				}

				if (row.get(stgMisura.nomeProgetto) != null
						&& !row.get(stgMisura.nomeProgetto).isEmpty()
						&& row.get(stgMisura.nomeProgetto).startsWith(
								"#UFFICIOSO")) {
					controlloPrj = "1";
				}

				String controlloMea = "0";

				if (Integer.parseInt(controlloPrj) == 0) {
					if (row.get(stgMisura.idMsr) == null
							|| row.get(stgMisura.idMsr).equals(new Integer(0))) {

						controlloMea = "1";

						if ((row.get(stgMisura.nomeMisura) != null && !row.get(
								stgMisura.nomeMisura).isEmpty())
								|| (row.get(stgMisura.approccio) != null && !row
										.get(stgMisura.approccio).isEmpty())
								|| (row.get(stgMisura.nomeProgetto) != null && !row
										.get(stgMisura.nomeProgetto).isEmpty())
								|| (row.get(stgMisura.metodo) != null && !row
										.get(stgMisura.metodo).isEmpty())
								|| (row.get(stgMisura.statoMisura) != null && !row
										.get(stgMisura.statoMisura).isEmpty())
								|| (row.get(stgMisura.fpPesatiMin) != null && !row
										.get(stgMisura.fpPesatiMin).isEmpty())
								|| (row.get(stgMisura.fpPesatiUfp) != null && !row
										.get(stgMisura.fpPesatiUfp).isEmpty())
								|| (row.get(stgMisura.fpPesatiMax) != null && !row
										.get(stgMisura.fpPesatiMax).isEmpty())
								|| (row.get(stgMisura.fpNonPesatiMin) != null && !row
										.get(stgMisura.fpNonPesatiMin)
										.isEmpty())
								|| (row.get(stgMisura.fpNonPesatiUfp) != null && !row
										.get(stgMisura.fpNonPesatiUfp)
										.isEmpty())
								|| (row.get(stgMisura.fpNonPesatiMax) != null && !row
										.get(stgMisura.fpNonPesatiMax)
										.isEmpty())
								|| (row.get(stgMisura.utenteMisuratore) != null && !row
										.get(stgMisura.utenteMisuratore)
										.isEmpty()))
							errore++;
						ErroriCaricamentoDAO.insert(
								DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_MISURA,
								MisuraUtils.MisuraToString(row),
								"IdMea deve essere valorizzato ",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
					}
				}

				if (row.get(stgMisura.nomeMisura) != null
						&& !row.get(stgMisura.nomeMisura).isEmpty()
						&& row.get(stgMisura.nomeMisura).startsWith(
								"#UFFICIOSO")) {
					controlloMea = "1";
				}

				if (Integer.parseInt(controlloAsm) == 0) {
					if (okVerificaAsm) {
						errore += ControlloDatiAsm(logger, row, dataEsecuzione);
					}
					if (Integer.parseInt(controlloPrj) == 0) {
						if (okVerificaProgetto) {
							errore += ControlloDatiPrj(logger, row,
									dataEsecuzione);
						}
						if (controlloMea == "0") {
							errore += ControlloDatiMisura(logger, row,
									dataEsecuzione);
							errore += ControlloDatiDbMisura(logger, row,
									dataEsecuzione);
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return errore;
	}

	private static int ControlloDatiAsm(Logger logger, Tuple row,
			Timestamp dataEsecuzione) {
		int errore = 0;
		Date appDataUltAgg = null;
		Date appDataIniVal = null;
		Date appDataFinVal = null;
		Date appDataEse = null;
		String datoInput = null;

		try {
			if (row.get(stgMisura.idAsm) == null
					|| row.get(stgMisura.idAsm) == 0) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.IDAPP_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.applicazione) == null
					|| row.get(stgMisura.applicazione).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.APPLICAZIONE_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.applicazione) != null
					&& !row.get(stgMisura.applicazione).isEmpty()) {
				datoInput = row.get(stgMisura.applicazione);
				errore += ControlloPatternCodAsm(logger, row, dataEsecuzione,
						datoInput);
			}

			if (row.get(stgMisura.applicazione) != null
					&& !row.get(stgMisura.applicazione).isEmpty()
					&& row.get(stgMisura.applicazione)
							.startsWith(
									DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)
					&& (!row.get(stgMisura.permissions).equals(
							"Admin (RESPAPP);Admin (READWRITE);"))
						||!row.get(stgMisura.permissions).equals(
								"Admin (READWRITE);Admin (RESPAPP);") ) {
				errore++;
				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"PERMISSIONS  valore non permesso : "
								+ row.get(stgMisura.permissions),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.pAppDenominazioneAsm) == null
					|| row.get(stgMisura.pAppDenominazioneAsm).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"APP-ATT:DENOMINAZIONE_ASM e' obbligatorio ",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.vafPredefinito) == null
					|| row.get(stgMisura.vafPredefinito).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.VAF_PREDEFINITO_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else {
				if (!row.get(stgMisura.vafPredefinito).equals("1")) {
					errore++;
					ErroriCaricamentoDAO.insert(
							DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_ASM,
							MisuraUtils.MisuraToString(row),
							DmAlmConstants.VAF_PREDEFINITO_NON_PERMESSO
									+ row.get(stgMisura.vafPredefinito),
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
			}

			if (row.get(stgMisura.pAppDataUltimoAggiorn) == null
					|| row.get(stgMisura.pAppDataUltimoAggiorn).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"APP-ATT:DATA_ULTIMO_AGGIORN e' obbligatorio ",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.pAppNomeAuthLastUpdate) == null
					|| row.get(stgMisura.pAppNomeAuthLastUpdate).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"APP-ATT:NOME_AUTORE_ULTIMO_AGGIORN e' obbligatorio ",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.pAppDataInizioValiditaAsm) == null
					|| row.get(stgMisura.pAppDataInizioValiditaAsm).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"APP-ATT:DATA_INIZIO_VALIDITA_ASM e' obbligatorio",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			//Commentato per DM_ALM-229
//			if (row.get(stgMisura.applicazione) != null
//					&& !row.get(stgMisura.applicazione).isEmpty()
//					&& row.get(stgMisura.applicazione)
//							.startsWith(
//									DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)
//					&& (row.get(stgMisura.pAppDataFineValiditaAsm) == null || row
//							.get(stgMisura.pAppDataFineValiditaAsm).isEmpty())) {
//				errore++;
//				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
//						DmAlmConstants.TARGET_ASM,
//						MisuraUtils.MisuraToString(row),
//						"APP-ATT:DATA_FINE_VALIDITA_ASM e' obbligatorio",
//						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
//						dataEsecuzione);
//
//			}

			if (row.get(stgMisura.pAppDenomUtentiFinaliAsm) == null
					|| row.get(stgMisura.pAppDenomUtentiFinaliAsm).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"APP-ATT:DENOM_UTENTI_FINALI_ASM e' obbligatorio",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.pAppDenomUtentiFinaliAsm) != null
					&& !row.get(stgMisura.pAppDenomUtentiFinaliAsm).isEmpty()
					&& (!row.get(stgMisura.pAppDenomUtentiFinaliAsm)
							.substring(
									row.get(stgMisura.pAppDenomUtentiFinaliAsm)
											.length() - 1,
									row.get(stgMisura.pAppDenomUtentiFinaliAsm)
											.length()).equals(";") || StringUtils
							.matchRegex(
									row.get(stgMisura.pAppDenomUtentiFinaliAsm),
									DmalmRegex.REGEXLOWERCASE))) {

				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"ATT:DENOM_UTENTI_ FINALI_ASM non rispetta il pattern predefinito : "
								+ row.get(stgMisura.pAppDenomUtentiFinaliAsm),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.pAppCodAsmConfinanti) != null
					&& !row.get(stgMisura.pAppCodAsmConfinanti).isEmpty()) {

				datoInput = row.get(stgMisura.pAppCodAsmConfinanti);
				errore += ControlloAsmConfinanti(logger, row, dataEsecuzione,
						datoInput);

			}

			if (row.get(stgMisura.pAppCdAltreAsmCommonServ) != null
					&& !row.get(stgMisura.pAppCdAltreAsmCommonServ).isEmpty()) {

				datoInput = row.get(stgMisura.pAppCdAltreAsmCommonServ);
				errore += ControlloCdAltreAsmCommonServ(logger, row,
						dataEsecuzione, datoInput);

			}

			if (row.get(stgMisura.pAppDenomSistTerziConfin) != null
					&& !row.get(stgMisura.pAppDenomSistTerziConfin).isEmpty()) {

				datoInput = row.get(stgMisura.pAppDenomSistTerziConfin);
				errore += ControlloDenomSistTerziConfin(logger, row,
						dataEsecuzione, datoInput);

			}

			if (row.get(stgMisura.pAppCodFlussiIoAsm) != null
					&& !row.get(stgMisura.pAppCodFlussiIoAsm).isEmpty()) {

				datoInput = row.get(stgMisura.pAppCodFlussiIoAsm);
				errore += ControlloCodFlussiIoAsm(logger, row, dataEsecuzione,
						datoInput);

			}

			if (row.get(stgMisura.pAppFlagServizioComune) == null
					|| row.get(stgMisura.pAppFlagServizioComune).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"APP-ATT:FLAG_ASM_SERVIZIO_COMUNE e' obbligatorio ",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else {
				if (!row.get(stgMisura.pAppFlagServizioComune).equals("SI")
						&& !row.get(stgMisura.pAppFlagServizioComune).equals(
								"NO")) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_ASM,
									MisuraUtils.MisuraToString(row),
									"APP-ATT:FLAG_ASM_SERVIZIO_COMUNE - Valore non permesso :"
											+ row.get(stgMisura.pAppFlagServizioComune),
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			if (row.get(stgMisura.pAppFlagMisurareSvimevFp) == null
					|| row.get(stgMisura.pAppFlagMisurareSvimevFp).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_ASM,
								MisuraUtils.MisuraToString(row),
								"APP-ATT:FLAG_ASM_DA_MISURARE_SVILUPPOMEV_IN_FP e' obbligatorio ",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			} else {
				if (!row.get(stgMisura.pAppFlagMisurareSvimevFp).equals("SI")
						&& !row.get(stgMisura.pAppFlagMisurareSvimevFp).equals(
								"NO")) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_ASM,
									MisuraUtils.MisuraToString(row),
									"APP-ATT:FLAG_ASM_DA_MISURARE_SVILUPPOMEV_IN_FP - Valore non permesso : "
											+ row.get(stgMisura.pAppFlagMisurareSvimevFp),
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			if (row.get(stgMisura.pAppFlagDamisurarePatrFp) == null
					|| row.get(stgMisura.pAppFlagDamisurarePatrFp).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_ASM,
								MisuraUtils.MisuraToString(row),
								"APP-ATT:FLAG_ASM_DA_MISURARE_PATRIMONIALE_IN_FP e' obbligatorio ",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			} else {
				if (!row.get(stgMisura.pAppFlagDamisurarePatrFp).equals("SI")
						&& !row.get(stgMisura.pAppFlagDamisurarePatrFp).equals(
								"NO")) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_ASM,
									MisuraUtils.MisuraToString(row),
									"APP-ATT:FLAG_ASM_DA_MISURARE_PATRIMONIALE_IN_FP - Valore non permesso : "
											+ row.get(stgMisura.pAppFlagDamisurarePatrFp),
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);

				}
			}

			if (row.get(stgMisura.pAppFlagInManutenzione) == null
					|| row.get(stgMisura.pAppFlagInManutenzione).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"APP-ATT:FLAG_ASM_IN_MANUTENZIONE e' obbligatorio ",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else {
				if (!row.get(stgMisura.pAppFlagInManutenzione).equals("SI")
						&& !row.get(stgMisura.pAppFlagInManutenzione).equals(
								"NO")
						&& !row.get(stgMisura.pAppFlagInManutenzione).equals(
								"FORNITORE")
						&& !row.get(stgMisura.pAppFlagInManutenzione).equals(
								"LISPA")) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_ASM,
									MisuraUtils.MisuraToString(row),
									"APP-ATT:FLAG_ASM_IN_MANUTENZIONE - Valore non permesso : "
											+ row.get(stgMisura.pAppFlagInManutenzione),
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			if (row.get(stgMisura.pAppCodDirezioneDemand) == null
					|| row.get(stgMisura.pAppCodDirezioneDemand).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"APP-ATT:COD_DIREZIONE_DEMAND e' obbligatorio ",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.pAppAccAuthLastUpdate) == null
					|| row.get(stgMisura.pAppAccAuthLastUpdate).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_ASM,
								MisuraUtils.MisuraToString(row),
								"APP-ATT:ACCOUNT_AUTORE_ULTIMO_AGGIORN e' obbligatorio ",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

			if ((row.get(stgMisura.utilizzata) == null)
					|| (!row.get(stgMisura.utilizzata).equalsIgnoreCase("0") && !row
							.get(stgMisura.utilizzata).equalsIgnoreCase("1"))) {
				errore++;
				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"UTILIZZATA - Valore non permesso : "
								+ row.get(stgMisura.utilizzata),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.proprietaLegale) == null
					|| (!row.get(stgMisura.proprietaLegale).equalsIgnoreCase(
							"0") && !row.get(stgMisura.proprietaLegale)
							.equalsIgnoreCase("1"))) {
				errore++;
				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"Proprietà legale - Valore non permesso : "
								+ row.get(stgMisura.proprietaLegale),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.includiInDbPatrimonio) == null
					|| (!row.get(stgMisura.includiInDbPatrimonio)
							.equalsIgnoreCase("0") && !row.get(
							stgMisura.includiInDbPatrimonio).equalsIgnoreCase(
							"1"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"Includi nel database di patrimonio - Valore non permesso : "
								+ row.get(stgMisura.includiInDbPatrimonio),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.pAppDataUltimoAggiorn) != null
					&& !row.get(stgMisura.pAppDataUltimoAggiorn).isEmpty()) {
				appDataUltAgg = DateUtils.stringToDate(
						row.get(stgMisura.pAppDataUltimoAggiorn), "dd/MM/yyyy");
				if (appDataUltAgg == null) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_ASM,
									MisuraUtils.MisuraToString(row),
									"APP-ATT:DATA_ULTIMO_AGGIORN la data deve avere il formato gg/mm/aaaa ",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			if (row.get(stgMisura.pAppDataInizioValiditaAsm) != null
					&& !row.get(stgMisura.pAppDataInizioValiditaAsm).isEmpty()) {
				appDataIniVal = DateUtils.stringToDate(
						row.get(stgMisura.pAppDataInizioValiditaAsm),
						"dd/MM/yyyy");
				if (appDataIniVal == null) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_ASM,
									MisuraUtils.MisuraToString(row),
									"APP-ATT:DATA_INIZIO_VALIDITA_ASM la data deve avere il formato gg/mm/aaaa ",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			if (row.get(stgMisura.pAppDataFineValiditaAsm) != null
					&& !row.get(stgMisura.pAppDataFineValiditaAsm).isEmpty()) {
				appDataFinVal = DateUtils.stringToDate(
						row.get(stgMisura.pAppDataFineValiditaAsm),
						"dd/MM/yyyy");
				if (appDataFinVal == null) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_ASM,
									MisuraUtils.MisuraToString(row),
									"APP-ATT:DATA_FINE_VALIDITA_ASM la data deve avere il formato gg/mm/aaaa ",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			if (appDataFinVal != null && appDataIniVal != null) {
				if (appDataFinVal.before(appDataIniVal)) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_ASM,
									MisuraUtils.MisuraToString(row),
									"APP-ATT:DATA_FINE_VALIDITA_ASM minore della data inizio validita' asm ",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			if (appDataUltAgg != null) {
				String strDataEse = dataEsecuzione.toString().substring(0, 10);
				appDataEse = DateUtils.stringToDate(strDataEse, "yyyy-MM-dd");
				if (appDataEse != null) {
					if (appDataUltAgg.after(appDataEse)) {
						errore++;
						ErroriCaricamentoDAO
								.insert(DmAlmConstants.FONTE_MISURA,
										DmAlmConstants.TARGET_ASM,
										MisuraUtils.MisuraToString(row),
										"APP-ATT:DATA_ULTIMO_AGGIORN la data non deve essere superiore alla data corrente ",
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
					}
				} else {
					errore++;
					ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_ASM,
							MisuraUtils.MisuraToString(row),
							"ERRORE SU DATA_ESECUZIONE ",
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return errore;
	}

	private static int ControlloDatiPrj(Logger logger, Tuple row,
			Timestamp dataEsecuzione) {
		int errore = 0;
		try {

			if (row.get(stgMisura.idProgetto) == null
					|| row.get(stgMisura.idProgetto) == 0) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.IDPRJ_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}
			if (row.get(stgMisura.idMsr) == null
					|| row.get(stgMisura.idMsr) == 0) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.IDMEA_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.nomeProgetto) == null
					|| row.get(stgMisura.nomeProgetto).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.PROGETTO_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// PRJ-ATT:AUDIT_MONITORE NON E’ VALORIZZABILE PER PROGETTI DI TIPO
			// ‘Baseline’.
			if (row.get(stgMisura.pPrjAuditMonitore) != null
					&& !row.get(stgMisura.pPrjAuditMonitore).isEmpty()
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")) {
				errore++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_PROGETTO_SFERA,
								MisuraUtils.MisuraToString(row),
								"PRJ-ATT:AUDIT_MONITORE non e’ valorizzabile per progetti di tipo ‘Baseline’ ",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

			// PRJ-ATT:AUDIT_INDICE_VERIFICABILITA NON E’ VALORIZZABILE PER
			// PROGETTI DI TIPO ‘Baseline’.
			if (row.get(stgMisura.pPrjAuditIndexVerify) != null
					&& !row.get(stgMisura.pPrjAuditIndexVerify).isEmpty()
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")) {
				errore++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_PROGETTO_SFERA,
								MisuraUtils.MisuraToString(row),
								"PRJ-ATT:AUDIT_INDICE_VERIFICABILITA non e' valorizzabile per progetti di tipo 'Baseline'",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

			// PRJ-ATT: COD_ RDI NON RISPETTA IL PATTERN PREDEFINITO
			if (row.get(stgMisura.pPrjCodRdi) != null
					&& !row.get(stgMisura.pPrjCodRdi).isEmpty()
					&& !StringUtils.matchRegex(row.get(stgMisura.pPrjCodRdi),
							DmalmRegex.REGEXCODRDI)) {
				errore++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_PROGETTO_SFERA,
								MisuraUtils.MisuraToString(row),
								"PRJ-ATT: COD_ RDI non rispetta il pattern predefinito ",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

			if (row.get(stgMisura.tipoProgetto) != null
					&& (row.get(stgMisura.tipoProgetto).equals(
							"Manutenzione Evolutiva") || row.get(
							stgMisura.tipoProgetto).equals("Sviluppo"))) {
				if (row.get(stgMisura.pPrjFornitoreMpp) != null
						&& !row.get(stgMisura.pPrjFornitoreMpp).isEmpty()) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_PROGETTO_SFERA,
									MisuraUtils.MisuraToString(row),
									"PRJ-ATT:FORNITORE_MPP non e' valorizzabile per progetti di tipo 'Sviluppo' oppure 'Manutenzione evolutiva'",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}
			
			//Aggiunto per DM_ALM-229
			if (row.get(stgMisura.pPrjFornitoreMpp) != null
					&& (!row.get(stgMisura.pPrjFornitoreMpp).isEmpty())
					&& (!row.get(stgMisura.pPrjFornitoreMpp).equals("Lombardia Informatica SpA"))
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURA))
					&& (row.get(stgMisura.nomeMisura).length() >= 7 && Integer
							.parseInt(row.get(stgMisura.nomeMisura).substring(
									4, 7)) == 2)
					&& (row.get(stgMisura.statoMisura).equals("Completata"))) {
				errore++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_PROGETTO_SFERA,
								MisuraUtils.MisuraToString(row),
								"WARNING-Non possono esserci 2 Misure MPP di tipo diverso in uno stesso progetto PATR-",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

			//Commentato per DM_ALM-229
//			if (row.get(stgMisura.nomeProgetto) != null
//					&& row.get(stgMisura.nomeProgetto).startsWith("PATR-")
//					&& row.get(stgMisura.tipoProgetto) != null
//					&& row.get(stgMisura.tipoProgetto).equals("Baseline")) {
//				if (row.get(stgMisura.pPrjFornitoreMpp) == null
//						|| row.get(stgMisura.pPrjFornitoreMpp).isEmpty()) {
//					errore++;
//					ErroriCaricamentoDAO
//							.insert(DmAlmConstants.FONTE_MISURA,
//									DmAlmConstants.TARGET_PROGETTO_SFERA,
//									MisuraUtils.MisuraToString(row),
//									"PRJ-ATT:FORNITORE_MPP e' obbligatorio per progetti di tipo 'Baseline' che hanno codice progetto che inizia per 'PATR-'",
//									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
//									dataEsecuzione);
//				}
//			}

			// pPrjFornitoreSviluppoMev
			if (row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")) {
				if (row.get(stgMisura.pPrjFornitoreSviluppoMev) != null
						&& !row.get(stgMisura.pPrjFornitoreSviluppoMev)
								.isEmpty()) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_PROGETTO_SFERA,
									MisuraUtils.MisuraToString(row),
									"PRJ-ATT:FORNITORE_SVILUPPO_MEV non e' valorizzabile per progetti di tipo 'Baseline'",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}
			//Commentato per DM_ALM-229
//			if (row.get(stgMisura.tipoProgetto) != null
//					&& (row.get(stgMisura.tipoProgetto).equals(
//							"Manutenzione Evolutiva") || row.get(
//							stgMisura.tipoProgetto).equals("Sviluppo"))) {
//				if (row.get(stgMisura.pPrjFornitoreSviluppoMev) == null
//						|| row.get(stgMisura.pPrjFornitoreSviluppoMev)
//								.isEmpty()) {
//					errore++;
//					ErroriCaricamentoDAO
//							.insert(DmAlmConstants.FONTE_MISURA,
//									DmAlmConstants.TARGET_PROGETTO_SFERA,
//									MisuraUtils.MisuraToString(row),
//									"PRJ-ATT:FORNITORE_SVILUPPO_MEV e' obbligatorio per progetti di tipo 'Sviluppo' e 'Manutenzione Evolutiva'",
//									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
//									dataEsecuzione);
//				}
//			}

			// pPrjMpPercentCicloDiVita
			if (row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")) {
				if (row.get(stgMisura.pPrjMpPercentCicloDiVita) != null
						&& !row.get(stgMisura.pPrjMpPercentCicloDiVita)
								.isEmpty()) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_PROGETTO_SFERA,
									MisuraUtils.MisuraToString(row),
									"PRJ-ATT:MP_PERCENT_CICLO_DI_VITA non e' valorizzabile per progetti di tipo 'Baseline'",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			if (row.get(stgMisura.tipoProgetto) != null
					&& (row.get(stgMisura.tipoProgetto).equals(
							"Manutenzione Evolutiva") || row.get(
							stgMisura.tipoProgetto).equals("Sviluppo"))) {
				if (row.get(stgMisura.pPrjMpPercentCicloDiVita) != null
						&& !row.get(stgMisura.pPrjMpPercentCicloDiVita)
								.isEmpty()) {
					try {
						Float appFloat = Float.parseFloat(row
								.get(stgMisura.pPrjMpPercentCicloDiVita));

						if (appFloat.compareTo(new Float(1)) != 0
								&& appFloat.compareTo(new Float(0.75)) != 0
								&& appFloat.compareTo(new Float(0.66)) != 0
								&& appFloat.compareTo(new Float(0.59)) != 0
								&& appFloat.compareTo(new Float(0.50)) != 0
								&& appFloat.compareTo(new Float(0.25)) != 0
								&& appFloat.compareTo(new Float(0.16)) != 0
								&& appFloat.compareTo(new Float(0.09)) != 0) {
							errore++;
							ErroriCaricamentoDAO
									.insert(DmAlmConstants.FONTE_MISURA,
											DmAlmConstants.TARGET_PROGETTO_SFERA,
											MisuraUtils.MisuraToString(row),
											"PRJ-ATT:MP_PERCENT_CICLO_DI_VITA - Valore non permesso : "
													+ row.get(stgMisura.pPrjMpPercentCicloDiVita),
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione);
						}
					} catch (Exception e) {
						errore++;
						ErroriCaricamentoDAO
								.insert(DmAlmConstants.FONTE_MISURA,
										DmAlmConstants.TARGET_PROGETTO_SFERA,
										MisuraUtils.MisuraToString(row),
										"PRJ-ATT:MP_PERCENT_CICLO_DI_VITA - Valore non permesso : "
												+ row.get(stgMisura.pPrjMpPercentCicloDiVita),
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
					}
				}
			}

			// pPrjFccFattCorrezTotal
			if (row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")) {
				if (row.get(stgMisura.pPrjFccFattCorrezTotal) != null
						&& !row.get(stgMisura.pPrjFccFattCorrezTotal).isEmpty()) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_PROGETTO_SFERA,
									MisuraUtils.MisuraToString(row),
									"PRJ-ATT:FCC_FATT_CORREZ_COMPLESSIVO non e' valorizzabile per progetti di tipo 'Baseline' ",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			if (row.get(stgMisura.tipoProgetto) != null
					&& (row.get(stgMisura.tipoProgetto).equals(
							"Manutenzione Evolutiva") || row.get(
							stgMisura.tipoProgetto).equals("Sviluppo"))) {
				if (row.get(stgMisura.pPrjFccFattCorrezTotal) != null
						&& !row.get(stgMisura.pPrjFccFattCorrezTotal).isEmpty()) {
					try {
						Float appFloat = Float.parseFloat(row
								.get(stgMisura.pPrjFccFattCorrezTotal));
						if (appFloat.compareTo(new Float(1)) != 0
								&& appFloat.compareTo(new Float(1.14)) != 0
								&& appFloat.compareTo(new Float(1.15)) != 0
								&& appFloat.compareTo(new Float(1.29)) != 0
								&& appFloat.compareTo(new Float(1.31)) != 0
								&& appFloat.compareTo(new Float(1.48)) != 0) {
							errore++;
							ErroriCaricamentoDAO
									.insert(DmAlmConstants.FONTE_MISURA,
											DmAlmConstants.TARGET_PROGETTO_SFERA,
											MisuraUtils.MisuraToString(row),
											"PRJ-ATT:FCC_FATT_CORREZ_COMPLESSIVO - Valore non permesso : "
													+ row.get(stgMisura.pPrjFccFattCorrezTotal),
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione);
						}
					} catch (Exception e) {
						errore++;
						ErroriCaricamentoDAO
								.insert(DmAlmConstants.FONTE_MISURA,
										DmAlmConstants.TARGET_PROGETTO_SFERA,
										MisuraUtils.MisuraToString(row),
										"PRJ-ATT:FCC_FATT_CORREZ_COMPLESSIVO - Valore non permesso : "
												+ row.get(stgMisura.pPrjFccFattCorrezTotal),
										DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
										dataEsecuzione);
					}
				}
			}

			// Commentato per DM_ALM-229
			// includeInBenchmarkingDb
//			if (row.get(stgMisura.includeInBenchmarkingDb) == null
//					|| (!row.get(stgMisura.includeInBenchmarkingDb)
//							.equalsIgnoreCase("Vero") && !row.get(
//							stgMisura.includeInBenchmarkingDb)
//							.equalsIgnoreCase("Falso"))) {
//				errore++;
//				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
//						DmAlmConstants.TARGET_PROGETTO_SFERA,
//						MisuraUtils.MisuraToString(row),
//						"Includi nel database di benchmarking - Valore non permesso : "
//								+ row.get(stgMisura.includeInBenchmarkingDb),
//						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
//						dataEsecuzione);
//			}

			// pPrjFlApplicLgFpDwh
			if (row.get(stgMisura.pPrjFlApplicLgFpDwh) == null
					|| (!row.get(stgMisura.pPrjFlApplicLgFpDwh).equals("SI") && !row
							.get(stgMisura.pPrjFlApplicLgFpDwh).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_- DWH - Valore non permesso : "
								+ row.get(stgMisura.pPrjFlApplicLgFpDwh),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjFlagApplLgFpGis
			if (row.get(stgMisura.pPrjFlagApplLgFpGis) == null
					|| (!row.get(stgMisura.pPrjFlagApplLgFpGis).equals("SI") && !row
							.get(stgMisura.pPrjFlagApplLgFpGis).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_APPLICABILITA_LG_FP_GIS - Valore non permesso : "
								+ row.get(stgMisura.pPrjFlagApplLgFpGis),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjFlApplLgFpEdma
			if (row.get(stgMisura.pPrjFlApplLgFpEdma) == null
					|| (!row.get(stgMisura.pPrjFlApplLgFpEdma).equals("SI") && !row
							.get(stgMisura.pPrjFlApplLgFpEdma).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_APPLICABILITA_LG_FP_EDMA - Valore non permesso : "
								+ row.get(stgMisura.pPrjFlApplLgFpEdma),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjFlagApplLgFpMware
			if (row.get(stgMisura.pPrjFlagApplLgFpMware) == null
					|| (!row.get(stgMisura.pPrjFlagApplLgFpMware).equals("SI") && !row
							.get(stgMisura.pPrjFlagApplLgFpMware).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_APPLICABILITA_LG_FP_MWARE - Valore non permesso : "
								+ row.get(stgMisura.pPrjFlagApplLgFpMware),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjFlApplLgFpWeb
			if (row.get(stgMisura.pPrjFlApplLgFpWeb) == null
					|| (!row.get(stgMisura.pPrjFlApplLgFpWeb).equals("SI") && !row
							.get(stgMisura.pPrjFlApplLgFpWeb).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_SITIWEB - Valore non permesso : "
								+ row.get(stgMisura.pPrjFlApplLgFpWeb),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjFlApplicLgFpFuturo01
			if (row.get(stgMisura.pPrjFlApplicLgFpFuturo01) == null
					|| (!row.get(stgMisura.pPrjFlApplicLgFpFuturo01).equals(
							"SI") && !row.get(
							stgMisura.pPrjFlApplicLgFpFuturo01).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_futuro-01 - Valore non permesso : "
								+ row.get(stgMisura.pPrjFlApplicLgFpFuturo01),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjFlApplicLgFpFuturo02
			if (row.get(stgMisura.pPrjFlApplicLgFpFuturo02) == null
					|| (!row.get(stgMisura.pPrjFlApplicLgFpFuturo02).equals(
							"SI") && !row.get(
							stgMisura.pPrjFlApplicLgFpFuturo02).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_APPLICABILITA_LG_ FP_futuro-02 - Valore non permesso : "
								+ row.get(stgMisura.pPrjFlApplicLgFpFuturo02),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjFlAmbTecTransBatRep
			if (row.get(stgMisura.pPrjFlAmbTecTransBatRep) == null
					|| (!row.get(stgMisura.pPrjFlAmbTecTransBatRep)
							.equals("SI") && !row.get(
							stgMisura.pPrjFlAmbTecTransBatRep).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_TRANS_BATCH_REP - Valore non permesso : "
								+ row.get(stgMisura.pPrjFlAmbTecTransBatRep),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjFlagAmbTecnGis
			if (row.get(stgMisura.pPrjFlagAmbTecnGis) == null
					|| (!row.get(stgMisura.pPrjFlagAmbTecnGis).equals("SI") && !row
							.get(stgMisura.pPrjFlagAmbTecnGis).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_ GIS - Valore non permesso :"
								+ row.get(stgMisura.pPrjFlagAmbTecnGis),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjAmbTecnPortali
			if (row.get(stgMisura.pPrjAmbTecnPortali) == null
					|| (!row.get(stgMisura.pPrjAmbTecnPortali).equals("SI") && !row
							.get(stgMisura.pPrjAmbTecnPortali).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_PORTALI - Valore non permesso : "
								+ row.get(stgMisura.pPrjAmbTecnPortali),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjFlAmbTecnPiatEnterpr
			if (row.get(stgMisura.pPrjFlAmbTecnPiatEnterpr) == null
					|| (!row.get(stgMisura.pPrjFlAmbTecnPiatEnterpr).equals(
							"SI") && !row.get(
							stgMisura.pPrjFlAmbTecnPiatEnterpr).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_PROGETTO_SFERA,
								MisuraUtils.MisuraToString(row),
								"PRJ-ATT:FLAG_ AMBITO_TECNOLOGICO_ PIATTAF_ SPECIAL_ ENTERPRISE - Valore non permesso : "
										+ row.get(stgMisura.pPrjFlAmbTecnPiatEnterpr),
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

			// pPrjFlAmbitoTecnFuturo01
			if (row.get(stgMisura.pPrjFlAmbitoTecnFuturo01) == null
					|| (!row.get(stgMisura.pPrjFlAmbitoTecnFuturo01).equals(
							"SI") && !row.get(
							stgMisura.pPrjFlAmbitoTecnFuturo01).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_ futuro-01 - Valore non permesso : "
								+ row.get(stgMisura.pPrjFlAmbitoTecnFuturo01),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// pPrjFlAmbitoTecnFuturo02
			if (row.get(stgMisura.pPrjFlAmbitoTecnFuturo02) == null
					|| (!row.get(stgMisura.pPrjFlAmbitoTecnFuturo02).equals(
							"SI") && !row.get(
							stgMisura.pPrjFlAmbitoTecnFuturo02).equals("NO"))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"PRJ-ATT:FLAG_AMBITO_TECNOLOGICO_ futuro-02 - Valore non permesso : "
								+ row.get(stgMisura.pPrjFlAmbitoTecnFuturo02),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);

			}

			// PROGETTO PATR- SFERA NON DI TIPO ‘BASELINE’
			if (row.get(stgMisura.nomeProgetto) != null
					&& row.get(stgMisura.nomeProgetto).startsWith("PATR-")
					&& row.get(stgMisura.tipoProgetto) != null
					&& !row.get(stgMisura.tipoProgetto).equals("Baseline")) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"progetto PATR- sfera non di tipo ‘Baseline’ ",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// PROGETTO BAS SFERA NON DI TIPO ‘BASELINE’
			if (row.get(stgMisura.nomeProgetto) != null
					&& StringUtils.matchRegex(row.get(stgMisura.nomeProgetto),
							DmalmRegex.REGEXNOMEPROGETTOBAS)
					&& row.get(stgMisura.tipoProgetto) != null
					&& !row.get(stgMisura.tipoProgetto).equals("Baseline")) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_PROGETTO_SFERA,
						MisuraUtils.MisuraToString(row),
						"progetto BAS - sfera non di tipo ‘Baseline’ ",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// LA DATA DEL PROGETTO PATR- SFERA NON RISPETTA IL FORMATO
			// PREDEFINITO
			if (row.get(stgMisura.nomeProgetto) != null
					&& row.get(stgMisura.nomeProgetto).startsWith("PATR-")
					&& row.get(stgMisura.nomeProgetto).length() >= 13) {
				Date appDataPrj = DateUtils.stringToDate(
						row.get(stgMisura.nomeProgetto).toString()
								.substring(5, 13), "yyyyMMdd");

				if (appDataPrj == null) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_PROGETTO_SFERA,
									MisuraUtils.MisuraToString(row),
									DmAlmConstants.DATA_SFERA_FORMATO_NON_CORRETTO
											+ row.get(stgMisura.nomeProgetto),
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}

				if (appDataPrj != null && appDataPrj.equals(20130524)) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_PROGETTO_SFERA,
									MisuraUtils.MisuraToString(row),
									"Progetto - la data del progetto sfera e' 20130524 ",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return errore;
	}

	private static int ControlloDatiMisura(Logger logger, Tuple row,
			Timestamp dataEsecuzione) {
		int errore = 0;
		try {

			if (row.get(stgMisura.idMsr) == null
					|| row.get(stgMisura.idMsr).equals(new Integer(0))) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						"IdMea e' obbligatorio ",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// nomeProgetto sta sia su dmalm_misura che su dmalm_progetto_sfera
			if (row.get(stgMisura.nomeProgetto) == null
					|| row.get(stgMisura.nomeProgetto).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.PROGETTO_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.nomeMisura) == null
					|| row.get(stgMisura.nomeMisura).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.MISURA_OBBLIGATORIA,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.approccio) == null
					|| row.get(stgMisura.approccio).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.APPROCIO_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else {
				if (!row.get(stgMisura.approccio).equals("Standard")
						&& !row.get(stgMisura.approccio).equals("E&Q 3.1")) {
					errore++;
					ErroriCaricamentoDAO.insert(
							DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_MISURA,
							MisuraUtils.MisuraToString(row),
							DmAlmConstants.APPROCIO_VALORE_NON_PERMESSO
									+ row.get(stgMisura.approccio),
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
			}

			if (row.get(stgMisura.metodo) == null
					|| row.get(stgMisura.metodo).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.METODO_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else {
				if (!row.get(stgMisura.metodo).equals("FP IFPUG 4.3.1")
						&& !row.get(stgMisura.metodo).equals("SiFP 1.0")) {
					errore++;
					ErroriCaricamentoDAO.insert(
							DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_MISURA,
							MisuraUtils.MisuraToString(row),
							DmAlmConstants.METODO_VALORE_NON_PERMESSO
									+ row.get(stgMisura.metodo),
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
			}

			if (row.get(stgMisura.statoMisura) == null
					|| row.get(stgMisura.statoMisura).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.STATO_MISURA_OBBLOGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.fpPesatiMin) == null
					|| row.get(stgMisura.fpPesatiMin).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.FP_PESATI_MIN_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.fpPesatiUfp) == null
					|| row.get(stgMisura.fpPesatiUfp).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.FP_PESATI_UFP_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.fpPesatiMax) == null
					|| row.get(stgMisura.fpPesatiMax).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.FP_PESATI_MAX_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.fpNonPesatiMin) == null
					|| row.get(stgMisura.fpNonPesatiMin).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.FP_NON_PESATI_MIN_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.fpNonPesatiUfp) == null
					|| row.get(stgMisura.fpNonPesatiUfp).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.FP_NON_PESATI_UFP_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.fpNonPesatiMax) == null
					|| row.get(stgMisura.fpNonPesatiMax).isEmpty()) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						DmAlmConstants.FP_NON_PESATI_MAX_E_OBBLIGATORIO,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			if (row.get(stgMisura.utenteMisuratore) == null
					|| row.get(stgMisura.utenteMisuratore).trim().isEmpty()
					|| row.get(stgMisura.utenteMisuratore).equals("Admin")) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						"Utente misuratore e' obbligatorio e deve assumere valore diverso da 'Admin': "
								+ row.get(stgMisura.utenteMisuratore),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// NOME MISURA ERRATO - PATR
			if (row.get(stgMisura.nomeMisura) != null
					&& row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& row.get(stgMisura.nomeProgetto) != null
					&& row.get(stgMisura.nomeProgetto).startsWith("PATR-")) {
				if (!StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
						DmalmRegex.REGEXNOMEMISURA)) {
					errore++;
					ErroriCaricamentoDAO.insert(
							DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_MISURA,
							MisuraUtils.MisuraToString(row),
							"Misura - nome misura PATR- errato : "
									+ row.get(stgMisura.nomeProgetto),
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
			}

			// NOME MISURA ERRATO - BAS
			if (row.get(stgMisura.nomeMisura) != null
					&& row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& row.get(stgMisura.nomeProgetto) != null
					&& StringUtils.matchRegex(row.get(stgMisura.nomeProgetto),
							DmalmRegex.REGEXNOMEPROGETTOBAS)) {
				if (!StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
						DmalmRegex.REGEXNOMEMISURABAS)) {
					errore++;
					ErroriCaricamentoDAO.insert(
							DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_MISURA,
							MisuraUtils.MisuraToString(row),
							"Misura - nome misura BAS errato : "
									+ row.get(stgMisura.nomeProgetto),
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
			}

			// NOME MISURA ERRATO - ST
			if (row.get(stgMisura.nomeMisura) != null
					&& row.get(stgMisura.tipoProgetto) != null
					&& (row.get(stgMisura.tipoProgetto).equals("Sviluppo") || row
							.get(stgMisura.tipoProgetto).equals(
									"Manutenzione Evolutiva"))) {
				if (!StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
						DmalmRegex.REGEXNOMEMISURAST)) {
					errore++;
					ErroriCaricamentoDAO.insert(
							DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_MISURA,
							MisuraUtils.MisuraToString(row),
							"Misura - nome misura ST errato : "
									+ row.get(stgMisura.tipoProgetto),
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
			}

			// MISURA DI TIPO ‘B’ CON METODO DI MISURAZIONE ERRATO
			if (row.get(stgMisura.nomeMisura) != null
					&& row.get(stgMisura.nomeMisura).startsWith("PAT-")
					&& row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& (row.get(stgMisura.nomeMisura).length() > 8 && row
							.get(stgMisura.nomeMisura).substring(8, 9)
							.equals("B")) && row.get(stgMisura.metodo) != null
					&& !row.get(stgMisura.metodo).equals("FP IFPUG 4.3.1")) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						"Misura - misura di tipo ‘B’ con metodo di misurazione errato : "
								+ row.get(stgMisura.metodo),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// MISURA DI TIPO ‘C’ CON METODO / APPROCCIO ERRATO
			if (row.get(stgMisura.nomeMisura) != null
					&& row.get(stgMisura.nomeMisura).startsWith("PAT-")
					&& (row.get(stgMisura.nomeMisura).length() > 8 && row
							.get(stgMisura.nomeMisura).substring(8, 9)
							.equals("C"))
					&& row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& row.get(stgMisura.metodo) != null
					&& row.get(stgMisura.approccio) != null) {
				if (row.get(stgMisura.metodo).equals("SiFP 1.0")
						&& !row.get(stgMisura.approccio).equals("Standard")) {
					errore++;
					ErroriCaricamentoDAO.insert(
							DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_MISURA,
							MisuraUtils.MisuraToString(row),
							"Misura - misura di tipo ‘C’ con metodo / approccio errato : "
									+ row.get(stgMisura.metodo)
									+ row.get(stgMisura.approccio),
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
				if (row.get(stgMisura.metodo).equals("FP IFPUG 4.3.1")
						&& !row.get(stgMisura.approccio).equals("E&Q 3.1")) {
					errore++;
					ErroriCaricamentoDAO.insert(
							DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_MISURA,
							MisuraUtils.MisuraToString(row),
							"Misura - misura di tipo ‘C’ con metodo / approccio errato : "
									+ row.get(stgMisura.metodo)
									+ row.get(stgMisura.approccio),
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
			}

			// MISURA MPP DEL FORNITORE IN STATO ERRATO
			if (row.get(stgMisura.pPrjFornitoreMpp) != null
					&& (!row.get(stgMisura.pPrjFornitoreMpp).isEmpty())
					&& (row.get(stgMisura.pPrjFornitoreMpp) != "Lombardia Informatica SpA")
					&& row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (row.get(stgMisura.nomeMisura).startsWith("PAT-001-"))
					&& (row.get(stgMisura.statoMisura) != null)
					&& (!row.get(stgMisura.statoMisura).equals("Completata"))) {
				errore++;
				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						"Misura mpp del fornitore in stato errato : "
								+ row.get(stgMisura.statoMisura),
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// WARNING: ESISTONO MISURE PAT-00n-x (n > 2)
			if (row.get(stgMisura.pPrjFornitoreMpp) != null
					&& (!row.get(stgMisura.pPrjFornitoreMpp).isEmpty())
					&& (!row.get(stgMisura.pPrjFornitoreMpp).equals(
							"Lombardia Informatica SpA"))
					&& row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURA))
					&& (row.get(stgMisura.nomeMisura).length() >= 7 && Integer
							.parseInt(row.get(stgMisura.nomeMisura).substring(
									4, 7)) > 2)) {

				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_MISURA,
						MisuraUtils.MisuraToString(row),
						"Esistono misure PAT-00n-x (n > 2) ",
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}

			// NON POSSONO ESISTERE MISURE CON NOME “PAT-00n-x” CON “N” MAGGIORE
			// DI 1 PER IL FORNITORE “Lombardia Informatica SpA”.
			if (row.get(stgMisura.pPrjFornitoreMpp) != null
					&& (!row.get(stgMisura.pPrjFornitoreMpp).isEmpty())
					&& (row.get(stgMisura.pPrjFornitoreMpp)
							.equals("Lombardia Informatica SpA"))
					&& row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURA))
					&& (row.get(stgMisura.nomeMisura).length() >= 7 && Integer
							.parseInt(row.get(stgMisura.nomeMisura).substring(
									4, 7)) > 001)) {

				errore++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_MISURA,
								MisuraUtils.MisuraToString(row),
								"Non possono esistere misure con nome 'PAT-00n-x'  con 'n' maggiore di 1 per il fornitore 'Lombardia Informatica Spa'",
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return errore;
	}

	private static int ControlloDatiDbMisura(Logger logger, Tuple row,
			Timestamp dataEsecuzione) {
		int errore = 0;
		try {

			// MISURA PAT-002-x MANCANTE
			// MISURA PAT-002-x IN STATO ‘COMPLETATA’
			if (row.get(stgMisura.pPrjFornitoreMpp) != null
					&& (!row.get(stgMisura.pPrjFornitoreMpp).isEmpty())
					&& (!row.get(stgMisura.pPrjFornitoreMpp).equals(
							"Lombardia Informatica SpA"))
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURA))) {

				List<Tuple> misurePat002 = StgMisuraDAO.checkPat002(logger,
						row, dataEsecuzione);
				if (misurePat002.size() == 0) {
					errore++;
					ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_MISURA,
							MisuraUtils.MisuraToString(row),
							"Misura PAT-002-x mancante ",
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				} else {
					for (Tuple t : misurePat002) {
						if (t.get(stgMisura.statoMisura).equals("Completata")) {
							errore++;
							ErroriCaricamentoDAO.insert(
									DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_MISURA,
									MisuraUtils.MisuraToString(row), "Misura "
											+ t.get(stgMisura.nomeMisura)
											+ " in stato 'Completata' ",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
						}
					}
				}
			}

			// NON POSSO ESSERCI 2 MISURE MPP DI TIPO DIVERSO IN UNO STESSO
			// PROGETTO PATR-
			if (row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& row.get(stgMisura.nomeProgetto) != null
					&& row.get(stgMisura.nomeProgetto).startsWith("PATR-")
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURA))) {

				List<Tuple> misurePatr = StgMisuraDAO.checkMisurePatr(logger,
						row, dataEsecuzione);

				String mis = null;
				String misPatr = null;

				if (misurePatr.size() > 0) {
					for (Tuple t : misurePatr) {
						if (t.get(stgMisura.nomeMisura).length() > 8) {
							misPatr = t.get(stgMisura.nomeMisura).substring(8,
									9);
							if (mis == null) {
								mis = misPatr;
							}

							if (!(misPatr).equals(mis)) {
								errore++;
								ErroriCaricamentoDAO
										.insert(DmAlmConstants.FONTE_MISURA,
												DmAlmConstants.TARGET_MISURA,
												MisuraUtils.MisuraToString(row),
												"Non posso esserci 2 misure mpp di tipo diverso in uno stesso progetto 'PATR-'",
												DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
												dataEsecuzione);
							}
						}
					}
				}
			}

			// NON POSSONO ESSERCI 2 MISURE MPP CON LO STESSO PROGRESSIVO IN UNO
			// STESSO PROGETTO PATR-
			if (row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& row.get(stgMisura.nomeProgetto) != null
					&& row.get(stgMisura.nomeProgetto).startsWith("PATR-")
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURA))) {

				List<String> misurePatr = new ArrayList<String>();
				misurePatr = StgMisuraDAO.checkCountMisurePatr(logger, row,
						dataEsecuzione);

				for (String t : misurePatr) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_MISURA,
									MisuraUtils.MisuraToString(row),
									"Non possono esserci 2 misure mpp con lo stesso progressivo in uno stesso progetto 'PATR-', misura: "
											+ t,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			// NON POSSONO ESSERCI ‘BUCHI’ NELLA NUMERAZIONE DELLE MISURE MPP IN
			// UNO STESSO PROGETTO PATR-
			if (row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& row.get(stgMisura.nomeProgetto) != null
					&& row.get(stgMisura.nomeProgetto).startsWith("PATR-")
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURA))) {

				if (!StgMisuraDAO.checkBucoNumerazMisurePatr(logger, row,
						dataEsecuzione)) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_MISURA,
									MisuraUtils.MisuraToString(row),
									"Non possono esserci ‘buchi’ nella numerazione delle misure MPP in uno stesso progetto 'PATR-'",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			// NON POSSONO ESSERCI 2 MISURE MPP CON LO STESSO PROGRESSIVO IN UNO
			// STESSO PROGETTO BASELINE SEMPLICE (A01 CON MISURE BAS)
			if (row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& row.get(stgMisura.nomeProgetto) != null
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeProgetto),
							DmalmRegex.REGEXNOMEPROGETTOBAS))
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURABAS))) {

				List<String> misurePatr = StgMisuraDAO.checkCountMisurePatrBas(
						logger, row, dataEsecuzione);

				for (String t : misurePatr) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_MISURA,
									MisuraUtils.MisuraToString(row),
									"Non possono esserci 2 misure mpp con lo stesso progressivo in uno stesso progetto 'Baseline Semplice', misura: "
											+ t,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			// NON POSSONO ESSERCI ‘BUCHI’ NELLA NUMERAZIONE DELLE MISURE MPP IN
			// UNO STESSO PROGETTO BASELINE SEMPLICE (A01 CON MISURE BAS)
			if (row.get(stgMisura.tipoProgetto) != null
					&& row.get(stgMisura.tipoProgetto).equals("Baseline")
					&& row.get(stgMisura.nomeProgetto) != null
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeProgetto),
							DmalmRegex.REGEXNOMEPROGETTOBAS))
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURABAS))) {

				if (!StgMisuraDAO.checkBucoNumerazMisurePatrBas(logger, row,
						dataEsecuzione)) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_MISURA,
									MisuraUtils.MisuraToString(row),
									"Non possono esserci ‘buchi’ nella numerazione delle misure in uno stesso progetto 'Baseline Semplice'",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			// NON POSSONO ESSERCI 2 MISURE CON LO STESSO CODICE IN UNO STESSO
			// MOMENTO DI MISURAZIONE DI UN PROGETTO SVIL/MANUT EVOLUTIVA
			if (row.get(stgMisura.tipoProgetto) != null
					&& (row.get(stgMisura.tipoProgetto).equals("Sviluppo") || row
							.get(stgMisura.tipoProgetto).equals(
									"Manutenzione Evolutiva"))
					&& row.get(stgMisura.nomeProgetto) != null
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURAST))) {

				List<String> misureSt = StgMisuraDAO.checkCountMisureSt(logger,
						row, dataEsecuzione);

				for (String t : misureSt) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_MISURA,
									MisuraUtils.MisuraToString(row),
									"Non possono esserci 2 misure con lo stesso codice in uno stesso momento di misurazione di un progetto svil/man evolutiva, misura: "
											+ t,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

			// NON POSSONO ESSERCI ‘BUCHI’ NELLA NUMERAZIONE DELLE
			// MISURE IN UNO
			// STESSO MOMENTO DI MISURAZIONE DI UN PROGETTO SVIL/MANUT EVOLUTIVA
			if (row.get(stgMisura.tipoProgetto) != null
					&& (row.get(stgMisura.tipoProgetto).equals("Sviluppo") || row
							.get(stgMisura.tipoProgetto).equals(
									"Manutenzione Evolutiva"))
					&& (row.get(stgMisura.nomeMisura) != null)
					&& (StringUtils.matchRegex(row.get(stgMisura.nomeMisura),
							DmalmRegex.REGEXNOMEMISURAST))) {

				List<Tuple> misureSt = new ArrayList<Tuple>();
				misureSt = StgMisuraDAO.checkBucoNumerazMisureSt(logger, row,
						dataEsecuzione);

				if (misureSt.size() > 0) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_MISURA,
									MisuraUtils.MisuraToString(row),
									"Non possono esserci 'buchi' nella numerazione delle misure in uno stesso momento di misurazione di un progetto svil/manut evolutiva",
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return errore;
	}

	private static int ControlloPatternCodAsm(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String datoInput) {

		int errore = 0;
		boolean codiceAsmErrato = false;

		try {
			String codiceAsm = datoInput.trim();

			if (codiceAsm
					.startsWith(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)) {
				try {
					codiceAsm = codiceAsm.substring(33);
				} catch (Exception e) {
					codiceAsmErrato = true;
				}
			}

			if (!codiceAsmErrato) {
				if (codiceAsm.contains("#")) {
					try {
						codiceAsm = codiceAsm.substring(0,
								codiceAsm.indexOf("#") - 1);
					} catch (Exception e) {
						codiceAsmErrato = true;
					}
				}
			}

			if (!codiceAsmErrato) {
				if (codiceAsm.substring(0, 1).equals(".")
						|| codiceAsm.substring(codiceAsm.length() - 1,
								codiceAsm.length()).equals(".")
						|| codiceAsm.indexOf("...") != -1) {
					codiceAsmErrato = true;
				}
			}

			if (codiceAsmErrato) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"Formattazione del pattern di COD_ASM errato : "
								+ datoInput,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return errore;
	}

	private static int ControlloAsmConfinanti(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String datoInput) {

		int errore = 0;
		String allCodAsm = null;
		String appCodAsm = null;
		String[] multiAsm = null;

		try {
			appCodAsm = datoInput.trim();
			if (appCodAsm.contains("#")) {
				allCodAsm = appCodAsm.substring(0, appCodAsm.indexOf("#") - 1);
			} else {
				allCodAsm = appCodAsm;
			}

			if (allCodAsm.substring(0, 1).equals(".")
					|| allCodAsm.substring(allCodAsm.length() - 1,
							allCodAsm.length()).equals(".")
					|| allCodAsm.indexOf("...") != -1
					|| !allCodAsm.substring(allCodAsm.length() - 1,
							allCodAsm.length()).equals(";")) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"APP-ATT: COD_ASM_CONFINANTI  non rispetta il pattern predefinito : "
								+ datoInput,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else {

				multiAsm = allCodAsm.split(";");

				for (String asm : multiAsm) {
					if (asm != null) {
						if (asm.isEmpty()) {
							errore++;
							ErroriCaricamentoDAO
									.insert(DmAlmConstants.FONTE_MISURA,
											DmAlmConstants.TARGET_ASM,
											MisuraUtils.MisuraToString(row),
											"Il COD_ASM confinanti non e' presente / attivo nell'insieme generale dei COD_ASM: Stringa vuota",
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione);
						} else {
							if (!StgMisuraDAO.checkAsmValida(logger, row,
									dataEsecuzione, asm.trim())) {
								errore++;
								ErroriCaricamentoDAO
										.insert(DmAlmConstants.FONTE_MISURA,
												DmAlmConstants.TARGET_ASM,
												MisuraUtils.MisuraToString(row),
												"Il COD_ASM confinanti non e' presente / attivo nell'insieme generale dei COD_ASM: "
														+ asm,
												DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
												dataEsecuzione);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return errore;
	}

	private static int ControlloCdAltreAsmCommonServ(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String datoInput) {

		int errore = 0;
		String allCodAsm = null;
		String[] multiAsm = null;

		try {
			allCodAsm = datoInput.trim();

			if (allCodAsm.substring(0, 1).equals(".")
					|| allCodAsm.substring(allCodAsm.length() - 1,
							allCodAsm.length()).equals(".")
					|| allCodAsm.indexOf("...") != -1
					|| !allCodAsm.substring(allCodAsm.length() - 1,
							allCodAsm.length()).equals(";")) {
				errore++;
				ErroriCaricamentoDAO
						.insert(DmAlmConstants.FONTE_MISURA,
								DmAlmConstants.TARGET_ASM,
								MisuraUtils.MisuraToString(row),
								"ATT:COD_ALTRE_ASM_UTILIZZ_COME_SERV_COMUNI non rispetta il pattern predefinito : "
										+ datoInput,
								DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
								dataEsecuzione);
			} else {

				multiAsm = allCodAsm.split(";");

				for (String asm : multiAsm) {
					if (asm != null) {
						asm = asm.trim();
						if (!StgMisuraDAO.checkAltreAsmValida(logger, row,
								dataEsecuzione, asm)) {
							errore++;
							ErroriCaricamentoDAO
									.insert(DmAlmConstants.FONTE_MISURA,
											DmAlmConstants.TARGET_ASM,
											MisuraUtils.MisuraToString(row),
											"ATT:COD_ALTRE_ASM_UTILIZZ_COME_SERV_COMUNI  non e' presente / attivo / servizio comune nell'insieme generale dei COD_ASM : "
													+ asm,
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione);
						}

						if (row.get(stgMisura.pAppCodAsmConfinanti) != null
								&& (row.get(stgMisura.pAppCodAsmConfinanti)
										.indexOf(asm) == -1)) {
							errore++;
							ErroriCaricamentoDAO
									.insert(DmAlmConstants.FONTE_MISURA,
											DmAlmConstants.TARGET_ASM,
											MisuraUtils.MisuraToString(row),
											"ATT:COD_ALTRE_ASM_UTILIZZ_COME_SERV_COMUNI non sono un sottoinsieme delle COD_ASM CONFINANTI : "
													+ asm,
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione);
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return errore;
	}

	// Sono SW Applicativi NON facenti parte del Catalogo ASM LISPA (ossia che
	// non compaiono nel Catalogo ASM Lispa), con cui l'ASM scambia dati o
	// segnali di controllo
	private static int ControlloDenomSistTerziConfin(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String datoInput) {

		int errore = 0;
		String[] multiAsm = null;

		try {
			if (!datoInput
					.substring(datoInput.length() - 1, datoInput.length())
					.equals(";")) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"DENOM_SIST_TERZEPARTI_CONFINANTI non rispetta il pattern predefinito : "
								+ datoInput,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else {

				multiAsm = datoInput.split(";");

				for (String asm : multiAsm) {
					if (asm != null) {
						asm = asm.trim();
						if (StgMisuraDAO.checkDenomValida(logger, row,
								dataEsecuzione, asm)) {
							errore++;
							ErroriCaricamentoDAO
									.insert(DmAlmConstants.FONTE_MISURA,
											DmAlmConstants.TARGET_ASM,
											MisuraUtils.MisuraToString(row),
											"APP-ATT: DENOM_SIST_TERZEPARTI_CONFINANTI non deve avere codici di ASM valide: "
													+ asm,
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione);
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return errore;
	}

	private static int ControlloCodFlussiIoAsm(Logger logger, Tuple row,
			Timestamp dataEsecuzione, String datoInput) {

		int errore = 0;
		String[] multiAsm = null;

		try {
			if (!datoInput
					.substring(datoInput.length() - 1, datoInput.length())
					.equals(";")) {
				errore++;
				ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
						DmAlmConstants.TARGET_ASM,
						MisuraUtils.MisuraToString(row),
						"APP-ATT: COD_FLUSSI_IO_ASM non rispetta il pattern predefinito: "
								+ datoInput,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						dataEsecuzione);
			} else {

				String errorePattern = "";
				String erroreDenom = "";

				multiAsm = datoInput.split(";");
				Integer contaProg = 0;
				Integer progAsm = 0;
				contaProg = 0;
				for (String asm : multiAsm) {
					asm = asm.trim();

					if (asm != null) {
						contaProg++;
						try {
							progAsm = Integer.parseInt(asm.substring(0,
									(asm.indexOf("_"))));
						} catch (Exception e) {
							progAsm = 0;
						}

						if (progAsm != contaProg) {
							errorePattern += asm + ";";
						} else {
							int firstUnderscore = asm.indexOf("_");
							int secondUnderscore = asm.indexOf("_",
									firstUnderscore + 1);

							if (firstUnderscore == -1 || secondUnderscore == -1) {
								errorePattern += asm + ";";
							} else {
								String strIO = asm.substring(
										firstUnderscore + 1, secondUnderscore);

								if (!strIO.equals("I") && !strIO.equals("O")
										&& !strIO.equals("IO")) {
									errorePattern += asm + ";";
								} else {
									String codAsm = asm
											.substring(secondUnderscore + 1);

									if (row.get(stgMisura.pAppCodAsmConfinanti) != null
											&& (row.get(
													stgMisura.pAppCodAsmConfinanti)
													.indexOf(codAsm) == -1)
											&& (row.get(stgMisura.pAppDenomSistTerziConfin) != null && (row
													.get(stgMisura.pAppDenomSistTerziConfin)
													.indexOf(codAsm) == -1))) {
										erroreDenom += asm + ";";
									}
								}
							}
						}
					}
				}

				if (!errorePattern.isEmpty()) {
					errore++;
					ErroriCaricamentoDAO.insert(DmAlmConstants.FONTE_MISURA,
							DmAlmConstants.TARGET_ASM,
							MisuraUtils.MisuraToString(row),
							"APP-ATT: COD_FLUSSI_IO_ASM non rispetta il pattern predefinito: "
									+ errorePattern,
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							dataEsecuzione);
				}
				if (!erroreDenom.isEmpty()) {
					errore++;
					ErroriCaricamentoDAO
							.insert(DmAlmConstants.FONTE_MISURA,
									DmAlmConstants.TARGET_ASM,
									MisuraUtils.MisuraToString(row),
									"COD_FLUSSI_IO_ASM non coincide con l'insieme unione DENOM_SIST_TERZEPARTI_CONFINANTI + COD_ASM CONFINANTI: "
											+ erroreDenom,
									DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
									dataEsecuzione);
				}

			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return errore;
	}
}