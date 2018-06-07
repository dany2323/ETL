package lispa.sgr;

import static lispa.schedulers.constant.DmAlmConstants.DEFAULT_DAY_DELETE;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_RETRY;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import junit.framework.TestCase;
import lispa.schedulers.action.DmAlmETL;
import lispa.schedulers.action.DmAlmFiliere;
import lispa.schedulers.action.DmAlmFillStaging;
import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.bean.target.sfera.DmalmAsmProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmProdottiArchitettureDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.target.DifettoProdottoOdsDAO;
import lispa.schedulers.dao.target.DmAlmSourceElProdEccezDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.dao.target.fatti.DifettoDAO;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaProjectElFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaUnitaOrganizzativaFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateIntTecnicaFacade;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.facade.target.fatti.RichiestaSupportoFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDifettoProdotto;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdottiArchitetture;
import lispa.schedulers.runnable.staging.SGRCMSireRunnable;
import lispa.schedulers.runnable.staging.SGRCMSissRunnable;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.EnumUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

public class TestWI extends TestCase {

	private static Logger logger = Logger.getLogger(DmAlmETL.class);
	private int retry;
	private int wait;
	private Map<Workitem_Type, Long> minRevisionsByType;
	
	private static QDmalmAsm dmalmAsm = QDmalmAsm.dmalmAsm;
	private static QDmalmElProdottiArchitetture qDmalmElProdottiArchitetture = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static QDmalmAsmProdottiArchitetture qDmalmAsmProdottiArchitetture = QDmalmAsmProdottiArchitetture.qDmalmAsmProdottiArchitetture;
	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;

	public void testProvenienzaDifetto(){
		try {
			Log4JConfiguration.inizialize();

			Timestamp dataEsecuzione=DateUtils.stringToTimestamp("22-05-2018 10:40:00", "dd-MM-yyyy HH:mm:ss");

			
			logger.debug("START CheckLinkAsmSferaProdottiArchFacade");

			boolean asmLegataTappo = false;
			String[] multiAsm = null;
			String unitaOrganizzativa = null;
			String unitaOrganizzativaDoppia = null;
			List<Tuple> asmList = new ArrayList<Tuple>();
			List<Tuple> productList = new ArrayList<Tuple>();
			List<String> asmLinkedList = new ArrayList<String>();
			List<Tuple> prodLink = new ArrayList<Tuple>();

			/**
			 * step 1 - Chiusura associazioni Prodotti chiusi: leggo tutte le
			 * relazioni "aperte" che hanno un puntamento ad un prodotto chiuso
			 * (è stato storicizzato il prodotto ma non la asm), queste
			 * relazioni le chiudo; lo step 3 partendo dalla Asm creerà una
			 * relazione nuova verso il prodotto "aperto"
			 */

			// tutti i Prodotti chiusi con relazione ad Asm ancora aperta
			asmList = DmAlmAsmProdottiArchitettureDAO
					.getAllRelationsToCloseCauseProductsClosed();

			// update DataFineValidita su tabella relazione
			for (Tuple asmProductRow : asmList) {
				if (asmProductRow != null) {
					DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
					relazione.setDmalmAsmPk(asmProductRow
							.get(qDmalmAsmProdottiArchitetture.dmalmAsmPk));
					relazione
							.setDmalmProdottoPk(asmProductRow
									.get(qDmalmAsmProdottiArchitetture.dmalmProdottoPk));
					relazione
							.setDataInizioValidita(asmProductRow
									.get(qDmalmAsmProdottiArchitetture.dtInizioValidita));
					relazione
							.setDataFineValidita(asmProductRow
									.get(qDmalmElProdottiArchitetture.dataFineValidita));

					logger.debug("update AsmProdottiArchitetture chiusura DataFineValidita per Prodotto chiuso - dmalmAsmPk: "
							+ relazione.getDmalmAsmPk()
							+ ", dmalmProdottoPk: "
							+ relazione.getDmalmProdottoPk()
							+ ", dataInizioValidita: "
							+ relazione.getDataInizioValidita()
							+ ", dataFineValidita: "
							+ relazione.getDataFineValidita());

					DmAlmAsmProdottiArchitettureDAO
							.closeRelAsmProdottiArchitetture(relazione);
				}
			}

			/** step 2 - Chiusura associazioni Asm chiuse */

			// legge tutte le asm chiuse con associazioni aperte da chiudere
			asmList = DmAlmAsmDAO.getAllRelationsToCloseCauseAsmsClosed();

			// update DataFineValidita su tabella relazione
			for (Tuple asmProductRow : asmList) {
				if (asmProductRow != null) {
					DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
					relazione.setDmalmAsmPk(asmProductRow
							.get(qDmalmAsmProdottiArchitetture.dmalmAsmPk));
					relazione
							.setDmalmProdottoPk(asmProductRow
									.get(qDmalmAsmProdottiArchitetture.dmalmProdottoPk));
					relazione
							.setDataInizioValidita(asmProductRow
									.get(qDmalmAsmProdottiArchitetture.dtInizioValidita));
					relazione.setDataFineValidita(asmProductRow
							.get(dmalmAsm.dataFineValidita));

					logger.debug("update AsmProdottiArchitetture chiusura DataFineValidita per Asm Chiusa - dmalmAsmPk: "
							+ relazione.getDmalmAsmPk()
							+ ", dmalmProdottoPk: "
							+ relazione.getDmalmProdottoPk()
							+ ", dataInizioValidita: "
							+ relazione.getDataInizioValidita()
							+ ", dataFineValidita: "
							+ relazione.getDataFineValidita());

					DmAlmAsmProdottiArchitettureDAO
							.closeRelAsmProdottiArchitetture(relazione);
				}
			}

			/** step 3 - Associazione Asm Aperte con Prodotto Aperto */

			// legge tutte le asm non associate
			asmList = DmAlmAsmDAO.getAllAsmToLinkWithProductArchitecture();

			// insert su tabella relazione
			for (Tuple asmRow : asmList) {
				if (asmRow != null) {
					String applicazione = asmRow.get(dmalmAsm.applicazione);

					// toglie #ANNULLATO LOGICAMENTE##
					if (applicazione
							.startsWith(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE_STARTSWITH)) {
						applicazione = applicazione.substring(33,
								applicazione.length());
					}

					// gestione multi applicazione
					asmLegataTappo = false;
					unitaOrganizzativa = "";
					unitaOrganizzativaDoppia = "";
					asmLinkedList = new ArrayList<String>();
					multiAsm = applicazione.split("\\.\\.");

					for (String asmName : multiAsm) {
						List<Tuple> dmAlmSourceElProdEccezzRow=DmAlmSourceElProdEccezDAO.getRow(asmName);
						if(!(dmAlmSourceElProdEccezzRow!=null && dmAlmSourceElProdEccezzRow.size()==1 && dmAlmSourceElProdEccezzRow.get(0).get(dmAlmSourceElProdEccez.tipoElProdEccezione).equals(1))){
							// toglie eventuali moduli dopo il nome Asm
							if (asmName.contains(".")) {
								asmName = asmName
										.substring(0, asmName.indexOf("."));
							}
						}
						// scarta nomi duplicati
						if (!asmLinkedList.contains(asmName)) {
							asmLinkedList.add(asmName);

							// ricerca Prodotto per Asm (Applicazione = Sigla)
							productList = ElettraProdottiArchitettureDAO
									.getProductByAcronym(asmName);

							if (productList.size() == 0) {
								if (asmRow
										.get(qDmalmAsmProdottiArchitetture.dmalmProdottoPk) == null
										&& !asmLegataTappo) {
									logger.info("Asm da legare al tappo Prodotto: "
											+ asmName
											+ ", applicazione: "
											+ asmRow.get(dmalmAsm.applicazione));

									asmLegataTappo = true;

									DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
									relazione.setDmalmAsmPk(asmRow
											.get(dmalmAsm.dmalmAsmPk));
									relazione.setDmalmProdottoPk(0);

									DmAlmAsmProdottiArchitettureDAO
											.insertRelAsmProdottiArchitetture(
													dataEsecuzione, relazione);
								}

								logger.info("Errore gestito - Nessun Prodotto per asmName: "
										+ asmName
										+ ", applicazione: "
										+ asmRow.get(dmalmAsm.applicazione));

								
							} else {
								for (Tuple productRow : productList) {
									if (productRow != null) {
										if (unitaOrganizzativa
												.equalsIgnoreCase("")) {
											unitaOrganizzativa = productRow
													.get(qDmalmElProdottiArchitetture.unitaOrganizzativaFk)
													.toString();
										} else {
											if (!unitaOrganizzativa
													.equalsIgnoreCase(productRow
															.get(qDmalmElProdottiArchitetture.unitaOrganizzativaFk)
															.toString())) {
												unitaOrganizzativaDoppia = productRow
														.get(qDmalmElProdottiArchitetture.unitaOrganizzativaFk)
														.toString();
											}
										}

										if (asmRow
												.get(qDmalmAsmProdottiArchitetture.dmalmProdottoPk) != null) {
											DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
											relazione.setDmalmAsmPk(asmRow
													.get(dmalmAsm.dmalmAsmPk));
											relazione
													.setDmalmProdottoPk(asmRow
															.get(qDmalmAsmProdottiArchitetture.dmalmProdottoPk));
											relazione
													.setDataInizioValidita(asmRow
															.get(qDmalmAsmProdottiArchitetture.dtInizioValidita));
											relazione
													.setDataFineValidita(DateUtils
															.addSecondsToTimestamp(
																	dataEsecuzione,
																	-1));

											logger.debug("update AsmProdotto chiusura relazione Tappo per Asm legata a nuovo Prodotto - dmalmAsmPk: "
													+ relazione.getDmalmAsmPk()
													+ ", dmalmProdottoSeq: "
													+ relazione
															.getDmalmProdottoPk()
													+ ", dataInizioValidita: "
													+ relazione
															.getDataInizioValidita()
													+ ", dataFineValidita: "
													+ relazione
															.getDataFineValidita());

											DmAlmAsmProdottiArchitettureDAO
													.closeRelAsmProdottiArchitetture(relazione);
										}

										DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
										relazione.setDmalmAsmPk(asmRow
												.get(dmalmAsm.dmalmAsmPk));
										relazione
												.setDmalmProdottoPk(productRow
														.get(qDmalmElProdottiArchitetture.prodottoPk));

										DmAlmAsmProdottiArchitettureDAO
												.insertRelAsmProdottiArchitetture(
														dataEsecuzione,
														relazione);
									}
								}
							}
						}

						// Nei casi di Asm multiple se nell'asociazione
						// Asm/Prodotto riscontro che i prodotti associati sono
						// relativi ad U.O. diverse segnalo l'incongruenza
						if (!unitaOrganizzativaDoppia.equalsIgnoreCase("")) {
							logger.info("Errore gestito - Asm associata a più U.O. applicazione: "
									+ asmRow.get(dmalmAsm.applicazione)
									+ ", U.O. 1: "
									+ unitaOrganizzativa
									+ ", U.O. 2: " + unitaOrganizzativaDoppia);

							ErroriCaricamentoDAO
									.insert(DmAlmConstants.FONTE_MISURA,
											DmAlmConstants.WRONG_LINK_ASM_UNITAORGANIZZATIVA,
											"APPLICAZIONE: "
													+ asmRow.get(dmalmAsm.applicazione)
													+ ", U.O 1: "
													+ unitaOrganizzativa
													+ ", U.O 2: "
													+ unitaOrganizzativaDoppia,
											DmAlmConstants.WRONG_LINK_ASM_PRODOTTO,
											DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
											dataEsecuzione);
						}

					} // multi asm
				}
			}

			/* Step 4 - Collegamento prodotti non associati alle ASM */

			prodLink = ProdottiArchitettureDAO.getAllProductArchToLinkWithAsm();
			if (prodLink.size() > 0) {
				for (Tuple row : prodLink) {
					logger.info("Collego il prodotto "
							+ row.get(qDmalmElProdottiArchitetture.prodottoPk)
							+ " al tappo ASM");
					
					DmalmAsmProdottiArchitetture relazione = new DmalmAsmProdottiArchitetture();
					relazione.setDmalmAsmPk(0);
					relazione.setDmalmProdottoPk(row
							.get(qDmalmElProdottiArchitetture.prodottoPk));

					DmAlmAsmProdottiArchitettureDAO
							.insertRelAsmProdottiArchitetture(dataEsecuzione,
									relazione);
				}
			}

			/*
			 * Step 5 chiusura prodotti architetture che hanno già una relazione
			 * con ASM
			 */
			ProdottiArchitettureDAO.closeProductArchDuplicate();

			logger.debug("STOP CheckLinkAsmSferaProdottiArchFacade");
	
			
			//CheckLinkAsmSferaUnitaOrganizzativaFacade.execute(DateUtils.stringToTimestamp("2018-02-01 20:40:00",
			//		"yyyy-MM-dd HH:mm:00"));
			//CheckLinkAsmSferaProjectElFacade.execute();
			
			//CheckLinkAsmSferaProjectElFacade.execute();
			//loadWiAndCustomFieldInStaging("sup",0L,100000L);

			/*RichiestaSupportoFacade.execute(DateUtils.stringToTimestamp("2018-05-03 20:40:00",
							"yyyy-MM-dd HH:mm:00")); */
			/*DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2018-05-03 20:40:00",
					"yyyy-MM-dd HH:mm:00"));
			RecoverManager.getInstance().startRecoverTarget();
			
			RecoverManager.getInstance().startRecoverStaging();*/
//			
//			List<DmalmDifettoProdotto> staging_difettoprodotto = new ArrayList<DmalmDifettoProdotto>();
//			List<Tuple> target_difettoprodotto = new ArrayList<Tuple>();
//			QDmalmDifettoProdotto dif = QDmalmDifettoProdotto.dmalmDifettoProdotto;
//
//			int righeNuove = 0;
//			int righeModificate = 0;
//
//			Date dtInizioCaricamento = new Date();
//			Date dtFineCaricamento = null;
//
//			DmalmDifettoProdotto difetto_tmp = null;
//
//			String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;
//
//			Timestamp dataEsecuzione = DateUtils.stringToTimestamp("05-09-2017 10:40:00", "dd-MM-yyyy HH:mm:ss");
//			staging_difettoprodotto = DifettoDAO
//					.getAllDifettoProdotto(dataEsecuzione );
//
//			/*DifettoProdottoOdsDAO.delete();
//
//			logger.debug("START -> Popolamento Difetto ODS, "
//					+ staging_difettoprodotto.size() + " difetti");
//
//			DifettoProdottoOdsDAO.insert(staging_difettoprodotto,
//					dataEsecuzione);
//			*/
//			
//			List<DmalmDifettoProdotto> x = DifettoProdottoOdsDAO.getAll();
//
//			logger.debug("STOP -> Popolamento Difetto ODS, "
//					+ staging_difettoprodotto.size() + " difetti");
//
//				for (DmalmDifettoProdotto difetto : x) {
//					if(difetto.getCdDifetto().equals("SIMP-744")) {
//
//					// Ricerco nel db target un record con idProject =
//					// project.getIdProject e data fine validita uguale a 31-12-9999
//					difetto_tmp = difetto;
//					target_difettoprodotto = DifettoDAO.getDifettoProdotto(difetto);
//		
//					// se non trovo almento un record, inserisco il project nel
//					// target
//					if (target_difettoprodotto.size() == 0) {
//						difetto.setDtCambioStatoDifetto(difetto
//								.getDtModificaRecordDifetto());
//						righeNuove++;
//						DifettoDAO.insertDifettoProdotto(difetto);
//					} else {
//						boolean modificato = false;
//		
//						for (Tuple row : target_difettoprodotto) {
//							if (row != null) {
//		
//								if (BeanUtils.areDifferent(
//										row.get(dif.dmalmStatoWorkitemFk03),
//										difetto.getDmalmStatoWorkitemFk03())) {
//									difetto.setDtCambioStatoDifetto(difetto
//											.getDtModificaRecordDifetto());
//									modificato = true;
//								} else {
//									difetto.setDtCambioStatoDifetto(row
//											.get(dif.dtCambioStatoDifetto));
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(
//												row.get(dif.dmalmProjectFk02),
//												difetto.getDmalmProjectFk02())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(
//												row.get(dif.numeroTestataDifetto),
//												difetto.getNumeroTestataDifetto())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(
//												row.get(dif.numeroLineaDifetto),
//												difetto.getNumeroLineaDifetto())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(
//												row.get(dif.dmalmUserFk06),
//												difetto.getDmalmUserFk06())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(row.get(dif.uri),
//												difetto.getUri())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(row.get(dif.annullato),
//												difetto.getAnnullato())) {
//									modificato = true;
//								}
//								if (!modificato
//										&& BeanUtils.areDifferent(row.get(dif.provenienzaDifetto),
//												difetto.getProvenienzaDifetto())) {
//									modificato = true;
//								}
//		
//								if (modificato) {
//									righeModificate++;
//		
//									// STORICIZZO
//									// imposto a zero il rank e il flagUltimaSituazione
//									DifettoDAO.updateRankFlagUltimaSituazione(difetto,
//											new Double(0), new Short("0"));
//		
//									// inserisco un nuovo record
//									DifettoDAO.insertDifettoProdottoUpdate(
//											dataEsecuzione, difetto, true);
//								} else {
//									// Aggiorno lo stesso
//									DifettoDAO.updateDmalmDifettoProdotto(difetto);
//								}
//							}
//						}
//					}
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadWiAndCustomFieldInStaging(String typeWi,long minRev, long maxRev) throws Exception {
		Map<Workitem_Type, Long> minRevisionsByType = SissHistoryWorkitemDAO
				.getMinRevisionByType();
		// Drop degli indici prima dell'elaborazione di HISTORY_WORKITEM e
		// HISTORY_CF_WORKITEM
		logger.debug("START DROP SISS INDEXES");
		//SissHistoryWorkitemDAO.dropIndexes();
		logger.debug("STOP DROP SISS INDEXES");

		retry = Integer.parseInt(DmAlmConfigReader.getInstance()
				.getProperty(DMALM_DEADLOCK_RETRY));
		wait = Integer.parseInt(DmAlmConfigReader.getInstance()
				.getProperty(DMALM_DEADLOCK_WAIT));

		logger.debug("START SissHistoryWorkitem - numero wi: "
				+ Workitem_Type.values().length);

		Workitem_Type type = null;
		for (Workitem_Type type2 : Workitem_Type.values()) {
			if(type2.toString().equals(typeWi)){
				type = type2;
			}
		}

		logger.debug("START TYPE: SISS " + type.toString());
		int tentativi = 0;
		ErrorManager.getInstance().resetDeadlock();
		ErrorManager.getInstance().resetCFDeadlock();
		boolean inDeadLock = false;
		boolean cfDeadlock = false;

		tentativi++;
		logger.debug("Tentativo " + tentativi);
		
		for(long i=minRevisionsByType.get(type);i<maxRev;i=i+10000)
		{
			minRevisionsByType.put(type, i);
			long j=i+10000;
			logger.info("Carico da rev "+i+" a "+j);
			SissHistoryWorkitemDAO.fillSissHistoryWorkitem(
					minRevisionsByType, j, type);
			inDeadLock = ErrorManager.getInstance().hasDeadLock();
		
//			if (!inDeadLock) {
//				List<String> customFields = EnumUtils
//						.getCFEnumerationByType(type);
//	
//				SissHistoryCfWorkitemDAO
//				.fillSissHistoryCfWorkitemByWorkitemType(
//						minRevisionsByType.get(type),
//						j, type, customFields);
//				cfDeadlock = ErrorManager.getInstance().hascfDeadLock();
//			}
//	
//			logger.debug("Fine tentativo " + tentativi + " - WI deadlock "
//					+ inDeadLock + " - CF deadlock " + cfDeadlock);
//	
//			if (inDeadLock || cfDeadlock) {
//				while (inDeadLock || cfDeadlock) {
//	
//					tentativi++;
//	
//					if (tentativi > retry) {
//						logger.debug("Raggiunto limite tentativi: "
//								+ tentativi);
//						Exception e = new Exception("Deadlock detected");
//						ErrorManager.getInstance().exceptionOccurred(true,
//								e);
//						return;
//					}
//	
//					logger.debug("Errore, aspetto 3 minuti");
//					logger.debug("Tentativo " + tentativi);
//					TimeUnit.MINUTES.sleep(wait);
//	
//					if (inDeadLock) {
//						SissHistoryWorkitemDAO.fillSissHistoryWorkitem(
//								minRevisionsByType, j, type);
//						inDeadLock = ErrorManager.getInstance()
//								.hasDeadLock();
//						if (!inDeadLock) {
//							logger.debug("Non in deadlock -> provo i CF");
//							List<String> customFields = EnumUtils
//									.getCFEnumerationByType(type);
//	
//							SissHistoryCfWorkitemDAO
//							.fillSissHistoryCfWorkitemByWorkitemType(
//									minRevisionsByType.get(type),
//									j, type,
//									customFields);
//							cfDeadlock = ErrorManager.getInstance()
//									.hascfDeadLock();
//							logger.debug("I CF sono in deadlock "
//									+ cfDeadlock);
//						}
//					} else {
//						if (cfDeadlock) {
//							logger.debug("Scarico soltanto i CF");
//	
//							List<String> customFields = EnumUtils
//									.getCFEnumerationByType(type);
//	
//							SissHistoryCfWorkitemDAO
//							.fillSissHistoryCfWorkitemByWorkitemType(
//									minRevisionsByType
//									.get(type),
//									j, type,
//									customFields);
//	
//							cfDeadlock = ErrorManager.getInstance()
//									.hascfDeadLock();
//	
//							logger.debug("I CF sono in deadlock "
//									+ cfDeadlock);
//						}
//					}
//				}
//			}
		}
		logger.debug("START delete not matching CFs SISS");
		SissHistoryCfWorkitemDAO.deleteNotMatchingCFS();
		logger.debug("STOP delete not matching CFs SISS");

		logger.debug("START Update CF SISS");
		SissHistoryCfWorkitemDAO.updateCFonWorkItem();
		logger.debug("STOP Update CF SISS");

		// Rebuild degli indici dopo l'elaborazione di HISTORY_WORKITEM e
		// HISTORY_CF_WORKITEM
		logger.debug("START REBUILD SISS INDEXES");
		SissHistoryWorkitemDAO.rebuildIndexes();
		logger.debug("STOP REBUILD SISS INDEXES");

		ConnectionManager.getInstance().dismiss();
	}

}
