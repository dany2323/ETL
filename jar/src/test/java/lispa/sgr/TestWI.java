package lispa.sgr;

import static lispa.schedulers.constant.DmAlmConstants.DEFAULT_DAY_DELETE;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_RETRY;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_STAGING_DAY_DELETE;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import junit.framework.TestCase;
import lispa.schedulers.action.DmAlmETL;
import lispa.schedulers.bean.target.DmalmPersonale;
import lispa.schedulers.bean.target.elettra.DmalmElPersonale;
import lispa.schedulers.bean.target.elettra.DmalmElProdottiArchitetture;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.dao.sfera.StgMisuraDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.elettra.target.ElettraUnitaOrganizzativeFacade;
import lispa.schedulers.facade.sfera.staging.StgMisuraFacade;
import lispa.schedulers.facade.sfera.target.MisuraFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElPersonale;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdottiArchitetture;
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
	private static QDmalmElPersonale qDmalmElPersonale = QDmalmElPersonale.qDmalmElPersonale;
	QDmalmAsm asm = QDmalmAsm.dmalmAsm;
	private static QDmalmElUnitaOrganizzativeFlat flat= QDmalmElUnitaOrganizzativeFlat.qDmalmElUnitaOrganizzativeFlat;

	public void testProvenienzaDifetto(){
		try {
			Log4JConfiguration.inizialize();
			DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2018-12-17 23:40:00","yyyy-MM-dd HH:mm:00"));
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
			
			StgMisuraFacade.fillStgMisura();
			
			MisuraFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
//			List<DmalmElPersonale> allPersonaleRecord = ElettraPersonaleDAO.getAllPersonale();
//			int recordStoricizzati=0;
//			ElettraUnitaOrganizzativeFacade.fillElettraUnitaOrganizzativeFlat(DataEsecuzione.getInstance().getDataEsecuzione());
//			List<Tuple> activeAsm = DmAlmAsmDAO.gettAllAsmTargetActive();
//			for(Tuple row:activeAsm){
//				Tuple Flat= DmAlmAsmDAO.getUOFlatById(row.get(asm.unitaOrganizzativaFlatFk));
//				DmalmAsm applicazioni = DmAlmAsmDAO.getBeanFromTuple(row);
//				if(Flat.get(flat.dataFineValidita).before(row.get(asm.dataFineValidita))){
//					// corrente
//					System.out.println("ASM Da storicizzare "+applicazioni.getDmalmAsmPk());
//					DmAlmAsmDAO.updateDataFineValidita(DateUtils.addSecondsToTimestamp(Flat.get(flat.dataFineValidita),1), applicazioni);
////					DmAlmAsmDAO.updateDataFineValidita(
////							new Timestamp(DateUtils.addSecondsToDate(Flat.get(flat.dataFineValidita).getTime())),1), DmAlmAsmDAO.getBeanFromTuple(row));
//
//					// inserisco un nuovo record
//
//					DmAlmAsmDAO.insertAsmUpdate(DateUtils.addSecondsToTimestamp(Flat.get(flat.dataFineValidita),1),
//							applicazioni);
//					
//				}
//			}
//			for(DmalmElPersonale row:allPersonaleRecord){
//				if(row.getUnitaOrganizzativaFlatFk()!=null){
//					DmalmElUnitaOrganizzativeFlat UOFlat = ElettraPersonaleDAO.getFlatUOByPk(row.getUnitaOrganizzativaFlatFk());
////					System.out.println("Chiavi "+row.get(qDmalmElPersonale.personalePk));
//					if(UOFlat.getDataFineValidita().before(row.getDataFineValidita())){
//						System.out.println("Attenzione, qualcosa non va su personale "+row.getPersonalePk());
//						ElettraPersonaleDAO.updateDataFineValidita(
//								new Timestamp(DateUtils.addSecondsToDate(UOFlat.getDataFineValidita(),1).getTime()),
//								row.getPersonalePk());
//
//						// inserisco un nuovo record
//						row.setPersonalePk(null);
//						row.setDataCaricamento(DataEsecuzione.getInstance().getDataEsecuzione());
//						ElettraPersonaleDAO.insertPersonaleUpdate(
//								new Timestamp(DateUtils.addSecondsToDate(UOFlat.getDataFineValidita(),1).getTime()), row);
//						recordStoricizzati++;
//					}
//				}
//
//			}
//			ElettraUnitaOrganizzativeFacade.fillElettraUnitaOrganizzativeFlat(DataEsecuzione.getInstance().getDataEsecuzione());
//			List<Tuple> listaProgettiNonMovimentati = ProjectSgrCmDAO
//					.getAllProjectNotInHistory(DataEsecuzione.getInstance().getDataEsecuzione());
//			QDmalmProject proj = QDmalmProject.dmalmProject;
////			HashMap<Tuple, Timestamp> 
//			for (Tuple row : listaProgettiNonMovimentati) {
//				
//				DmalmElUnitaOrganizzativeFlat UoFlat = ElettraUnitaOrganizzativeDAO.getUOFlatByPk(row.get(proj.dmalmUnitaOrganizzativaFlatFk));
//				if(UoFlat != null && UoFlat.getDataFineValidita().before(DateUtils.setDtFineValidita9999())){
//					System.out.println("Attenzione, qualcosa non va su Proj "+row.get(proj.idProject));
//					DmalmProject bean = new DmalmProject();
//
//					bean.setIdProject(row.get(proj.idProject));
//					bean.setIdRepository(row.get(proj.idRepository));
//					bean.setcTemplate(row.get(proj.cTemplate));
//					bean.setDmalmAreaTematicaFk01(row
//							.get(proj.dmalmAreaTematicaFk01));
//					bean.setDmalmStrutturaOrgFk02(row.get(proj.dmalmStrutturaOrgFk02));
//					bean.setDmalmUnitaOrganizzativaFk(row.get(proj.dmalmUnitaOrganizzativaFk));
//					bean.setDmalmUnitaOrganizzativaFlatFk(row.get(proj.dmalmUnitaOrganizzativaFlatFk));
//					bean.setFlAttivo(row.get(proj.flAttivo));
//					bean.setPathProject(row.get(proj.pathProject));
//					bean.setDtInizioValidita(new Timestamp(DateUtils.addSecondsToDate(UoFlat.getDataFineValidita(), 1).getTime()));
//					bean.setcCreated(row.get(proj.cCreated));
//					bean.setServiceManagers(row.get(proj.serviceManagers));
//					bean.setcTrackerprefix(row.get(proj.cTrackerprefix));
//					bean.setcIsLocal(row.get(proj.cIsLocal));
//					bean.setcPk(row.get(proj.cPk));
//					bean.setFkUriLead(row.get(proj.fkUriLead));
//					bean.setcDeleted(row.get(proj.cDeleted));
//					bean.setcFinish(row.get(proj.cFinish));
//					bean.setcUri(row.get(proj.cUri));
//					bean.setcStart(row.get(proj.cStart));
//					bean.setFkUriProjectgroup(row
//							.get(proj.fkUriProjectgroup));
//					bean.setcActive(row.get(proj.cActive));
//					bean.setFkProjectgroup(row.get(proj.fkProjectgroup));
//					bean.setFkLead(row.get(proj.fkLead));
//					bean.setcLockworkrecordsdate(row
//							.get(proj.cLockworkrecordsdate));
//					bean.setcRev(row.get(proj.cRev));
//					bean.setcDescription(row.get(proj.cDescription));
//					bean.setSiglaProject(row.get(proj.siglaProject));
//					bean.setNomeCompletoProject(row
//							.get(proj.nomeCompletoProject));
//					bean.setDtCaricamento(DataEsecuzione.getInstance().getDataEsecuzione());
//
//					ProjectSgrCmDAO.updateDataFineValidita(new Timestamp(DateUtils.addSecondsToDate(UoFlat.getDataFineValidita(),1).getTime()),
//							bean); 
//
//					// inserisco un nuovo record
//					ProjectSgrCmDAO.insertProjectUpdate(new Timestamp(DateUtils.addSecondsToDate(UoFlat.getDataFineValidita(),1).getTime()),
//							bean, false);
////					DataEsecuzione.getInstance().getDataEsecuzione()
//				}
//			}
//			CheckProjectStorFacade.execute();
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
