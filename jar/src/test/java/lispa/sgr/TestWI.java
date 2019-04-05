package lispa.sgr;

import static lispa.schedulers.constant.DmAlmConstants.DEFAULT_DAY_DELETE;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_RETRY;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_STAGING_DAY_DELETE;

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
import lispa.schedulers.bean.staging.elettra.StgElProdotti;
import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.bean.target.sfera.DmalmAsmProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.elettra.StgElPersonaleDAO;
import lispa.schedulers.dao.elettra.StgElProdottiDAO;
import lispa.schedulers.dao.elettra.StgElUnitaOrganizzativeDAO;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.dao.sfera.DmAlmAsmProdottiArchitettureDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.target.DifettoProdottoOdsDAO;
import lispa.schedulers.dao.target.DmAlmSourceElProdEccezDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.elettra.ElettraFunzionalitaDAO;
import lispa.schedulers.dao.target.elettra.ElettraModuliDAO;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.dao.target.fatti.DifettoDAO;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiElettraFacade;
import lispa.schedulers.facade.cleaning.CheckProjectStorFacade;
import lispa.schedulers.facade.elettra.StagingElettraFacade;
import lispa.schedulers.facade.elettra.target.ElettraFunzionalitaFacade;
import lispa.schedulers.facade.elettra.target.ElettraModuliFacade;
import lispa.schedulers.facade.elettra.target.ElettraPersonaleFacade;
import lispa.schedulers.facade.elettra.target.ElettraProdottiArchitettureFacade;
import lispa.schedulers.facade.elettra.target.ElettraUnitaOrganizzativeFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaProdottiArchFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaProdottoFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaProjectElFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaStrutturaOrganizzativaFacade;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaUnitaOrganizzativaFacade;
import lispa.schedulers.facade.sfera.staging.StgMisuraFacade;
import lispa.schedulers.facade.sfera.target.AsmFacade;
import lispa.schedulers.facade.sfera.target.MisuraFacade;
import lispa.schedulers.facade.sfera.target.ProgettoSferaFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateAssFunzionaleFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateIntTecnicaFacade;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.facade.target.fatti.ClassificatoreFacade;
import lispa.schedulers.facade.target.fatti.RichiestaSupportoFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
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
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class TestWI extends TestCase {

	private static Logger logger = Logger.getLogger(DmAlmETL.class);
	private int retry;
	private int wait;
	private Map<EnumWorkitemType, Long> minRevisionsByType;
	
	private static QDmalmAsm dmalmAsm = QDmalmAsm.dmalmAsm;
	private static QDmalmElProdottiArchitetture qDmalmElProdottiArchitetture = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static QDmalmAsmProdottiArchitetture qDmalmAsmProdottiArchitetture = QDmalmAsmProdottiArchitetture.qDmalmAsmProdottiArchitetture;
	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;

	public void testProvenienzaDifetto(){
		try {
			Log4JConfiguration.inizialize();
			//DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2018-07-01 00:00:00","yyyy-MM-dd HH:mm:00"));
			int days;
			try {
				days = Integer.parseInt(DmAlmConfigReader.getInstance()
						.getProperty(DMALM_STAGING_DAY_DELETE));
			} catch (PropertiesReaderException | NumberFormatException e) {
				days = DEFAULT_DAY_DELETE;
				logger.debug(e.getMessage(), e);
			}
			final Timestamp dataEsecuzioneDeleted = DateUtils
					.getAddDayToDate(-days);
			
			//loadWiAndCustomFieldInStaging("classificatore", 0L, 2000000L);
//			DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2018-09-04 11:47:00","yyyy-MM-dd HH:mm:00"));
//
//			ClassificatoreFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
			
			//CostruzioneFilieraTemplateAssFunzionaleFacade.execute();
//			StgMisuraFacade.deleteStgMisura(logger, dataEsecuzioneDeleted);
//			StgMisuraFacade.FillStgMisura();
//			
//			AsmFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
//			
//			// step 1 crea legami Asm/Prodotto
//			// Prodotto Oreste
//			CheckLinkAsmSferaProdottoFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione(), false);
//			// ProdottiArchitetture Elettra
//			//CheckLinkAsmSferaProdottiArchFacade.execute(dataEsecuzione, false);
//			
//			// step 2 crea fk Asm/UO (Edma)
//			// Struttura Organizzativa Edma
//			CheckLinkAsmSferaStrutturaOrganizzativaFacade
//					.execute(DataEsecuzione.getInstance().getDataEsecuzione());
//
//			// step 3 crea i nuovi legami per eventuali asm storicizzate da
//			// CheckLinkAsmSferaStrutturaOrganizzativaFacade
//			// Prodotto Oreste
//			//CheckLinkAsmSferaProdottoFacade.execute(dataEsecuzione, false);
//			// ProdottiArchitetture Elettra
//			CheckLinkAsmSferaProdottiArchFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione(), false);
//
//			// step 4 crea fk Asm/UO (Elettra)
//			// Unità Organizzativa Elettra
//			CheckLinkAsmSferaUnitaOrganizzativaFacade
//					.execute(DataEsecuzione.getInstance().getDataEsecuzione());
//
//			// step 5 crea i nuovi legami per eventuali asm storicizzate da
//			// CheckLinkAsmSferaUnitaOrganizzativaFacade
//			// Prodotto Oreste
//			//CheckLinkAsmSferaProdottoFacade.execute(dataEsecuzione, true);
//			// ProdottiArchitetture Elettra
//			CheckLinkAsmSferaProdottiArchFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione(), true);
//			
//			ProgettoSferaFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());

			//MisuraFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());

//			if (ErrorManager.getInstance().hasError()) {
//				RecoverManager.getInstance().startRecoverTarget();
//				RecoverManager.getInstance().startRecoverStaging();
//
//				// MPS
//				if (ExecutionManager.getInstance().isExecutionMps())
//					RecoverManager.getInstance().startRecoverStgMps();
//
//				return;
//			}
			//Carica Elettrra nellos taging
//			StagingElettraFacade.executeStaging(dataEsecuzioneDeleted);
//			ElettraFunzionalitaFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
//			//Carica le UO
//			StgElUnitaOrganizzativeDAO.fillStaging();
//			ElettraUnitaOrganizzativeFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
//			
			//StgElProdottiDAO.fillStaging();
			//ElettraProdottiArchitettureFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
			//StgElPersonaleDAO.fillStaging();
			//ElettraPersonaleFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
			
			//Annulla le UO
			
			//CheckAnnullamentiElettraFacade.execute(DateUtils.stringToTimestamp("2018-07-01 00:00:00","yyyy-MM-dd HH:mm:00"));
			//Aggiorna le UO
//			ProjectSgrCmFacade.storicizzaProjectElettra(DateUtils.stringToTimestamp("2018-07-01 00:00:00","yyyy-MM-dd HH:mm:00"));
//			//Storicizza i WI figli dei project per cambio UO
//			CheckProjectStorFacade.execute();
//			
//			//Aggiorna 
//			ProjectSgrCmFacade.aggiornaUOFlatProject();
//			
//			
//			//Triplice
//			CheckLinkAsmSferaProjectElFacade.execute();
			
			//Annulla Elettra
			//CheckAnnullamentiElettraFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
			
			//Date di annullamento dei prodotti
//			List <Integer> prodottiAnnullati=ElettraProdottiArchitettureDAO.getAllTargetProdottoAnnullati();
//			
//			for(Integer prod:prodottiAnnullati){
//				List <String> applicazioni=ProdottiArchitettureDAO.getAsmByProductPk(prod);
//				if(applicazioni!=null && applicazioni.size()>0){
//					for(String app:applicazioni){
//						if(app.contains(DmAlmConstants.SFERA_ANNULLATO_LOGICAMENTE) || app.contains(DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE)){
//							Date dataAnnullamento=DateUtils.getDataAnnullamento(app, logger);
//							logger.info("Prodotto PK:"+prod+" Data annullamento :"+dataAnnullamento);
//							if(dataAnnullamento!=null){
//								ElettraProdottiArchitettureDAO.updateDataAnnullamento(new Timestamp(dataAnnullamento.getTime()), prod);
//							}
//						}
//					}
//				}
//			}
			
			//DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2016-02-28 21:00:00","yyyy-MM-dd HH:mm:00"));
			//CheckProjectStorFacade.execute();
			//RecoverManager.getInstance().startRecoverTarget();
			
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
		Map<EnumWorkitemType, Long> minRevisionsByType = SireHistoryWorkitemDAO
				.getMinRevisionByType();
		// Drop degli indici prima dell'elaborazione di HISTORY_WORKITEM e
		// HISTORY_CF_WORKITEM
		logger.debug("START DROP SIRE INDEXES");
		//SissHistoryWorkitemDAO.dropIndexes();
		logger.debug("STOP DROP SIRE INDEXES");

		retry = Integer.parseInt(DmAlmConfigReader.getInstance()
				.getProperty(DMALM_DEADLOCK_RETRY));
		wait = Integer.parseInt(DmAlmConfigReader.getInstance()
				.getProperty(DMALM_DEADLOCK_WAIT));

		logger.debug("START SireHistoryWorkitem - numero wi: "
				+ Workitem_Type.EnumWorkitemType.values().length);

		EnumWorkitemType type = null;
		for (EnumWorkitemType type2 : Workitem_Type.EnumWorkitemType.values()) {
			if(type2.toString().equals(typeWi)){
				type = type2;
			}
		}

		logger.debug("START TYPE: SIRE " + type.toString());
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
			SireHistoryWorkitemDAO.fillSireHistoryWorkitem(
					minRevisionsByType, j, type);
			inDeadLock = ErrorManager.getInstance().hasDeadLock();
		
			if (!inDeadLock) {
				List<String> customFields = EnumUtils
						.getCFEnumerationByType(type);
	
				SireHistoryCfWorkitemDAO
				.fillSireHistoryCfWorkitemByWorkitemType(
						minRevisionsByType.get(type),
						j, type, customFields);
				cfDeadlock = ErrorManager.getInstance().hascfDeadLock();
			}
	
			logger.debug("Fine tentativo " + tentativi + " - WI deadlock "
					+ inDeadLock + " - CF deadlock " + cfDeadlock);
	
			if (inDeadLock || cfDeadlock) {
				while (inDeadLock || cfDeadlock) {
	
					tentativi++;
	
					if (tentativi > retry) {
						logger.debug("Raggiunto limite tentativi: "
								+ tentativi);
						Exception e = new Exception("Deadlock detected");
						ErrorManager.getInstance().exceptionOccurred(true,
								e);
						return;
					}
	
					logger.debug("Errore, aspetto 3 minuti");
					logger.debug("Tentativo " + tentativi);
					TimeUnit.MINUTES.sleep(wait);
	
					if (inDeadLock) {
						SireHistoryWorkitemDAO.fillSireHistoryWorkitem(
								minRevisionsByType, j, type);
						inDeadLock = ErrorManager.getInstance()
								.hasDeadLock();
						if (!inDeadLock) {
							logger.debug("Non in deadlock -> provo i CF");
							List<String> customFields = EnumUtils
									.getCFEnumerationByType(type);
	
							SireHistoryCfWorkitemDAO
							.fillSireHistoryCfWorkitemByWorkitemType(
									minRevisionsByType.get(type),
									j, type,
									customFields);
							cfDeadlock = ErrorManager.getInstance()
									.hascfDeadLock();
							logger.debug("I CF sono in deadlock "
									+ cfDeadlock);
						}
					} else {
						if (cfDeadlock) {
							logger.debug("Scarico soltanto i CF");
	
							List<String> customFields = EnumUtils
									.getCFEnumerationByType(type);
	
							SireHistoryCfWorkitemDAO
							.fillSireHistoryCfWorkitemByWorkitemType(
									minRevisionsByType
									.get(type),
									j, type,
									customFields);
	
							cfDeadlock = ErrorManager.getInstance()
									.hascfDeadLock();
	
							logger.debug("I CF sono in deadlock "
									+ cfDeadlock);
						}
					}
				}
			}
		}
//		logger.debug("START delete not matching CFs SISS");
//		SissHistoryCfWorkitemDAO.deleteNotMatchingCFS();
//		logger.debug("STOP delete not matching CFs SISS");
//
//		logger.debug("START Update CF SISS");
//		SissHistoryCfWorkitemDAO.updateCFonWorkItem();
//		logger.debug("STOP Update CF SISS");

		// Rebuild degli indici dopo l'elaborazione di HISTORY_WORKITEM e
		// HISTORY_CF_WORKITEM
//		logger.debug("START REBUILD SISS INDEXES");
//		SissHistoryWorkitemDAO.rebuildIndexes();
//		logger.debug("STOP REBUILD SISS INDEXES");

		ConnectionManager.getInstance().dismiss();
	}

}
