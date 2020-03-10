package lispa.sgr;

import static lispa.schedulers.constant.DmAlmConstants.DEFAULT_DAY_DELETE;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_RETRY;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_STAGING_DAY_DELETE;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

import junit.framework.TestCase;
import lispa.schedulers.action.DmAlmETL;
import lispa.schedulers.bean.target.DmalmPersonale;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.DmalmProjectUnitaOrganizzativaEccezioni;
import lispa.schedulers.bean.target.elettra.DmalmElPersonale;
import lispa.schedulers.bean.target.elettra.DmalmElProdottiArchitetture;
import lispa.schedulers.bean.target.elettra.DmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.bean.target.sfera.DmalmAsm;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sfera.DmAlmAsmDAO;
import lispa.schedulers.dao.sfera.StgMisuraDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.ProjectUnitaOrganizzativaEccezioniDAO;
import lispa.schedulers.dao.target.StrutturaOrganizzativaEdmaLispaDAO;
import lispa.schedulers.dao.target.TotalDao;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.dao.target.elettra.ElettraUnitaOrganizzativeDAO;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckProjectStorFacade;
import lispa.schedulers.facade.elettra.target.ElettraUnitaOrganizzativeFacade;
import lispa.schedulers.facade.sfera.staging.StgMisuraFacade;
import lispa.schedulers.facade.sfera.target.MisuraFacade;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.facade.target.fatti.AnomaliaAssistenzaFacade;
import lispa.schedulers.facade.target.fatti.AnomaliaProdottoFacade;
import lispa.schedulers.facade.target.fatti.BuildFacade;
import lispa.schedulers.facade.target.fatti.ClassificatoreFacade;
import lispa.schedulers.facade.target.fatti.DifettoProdottoFacade;
import lispa.schedulers.facade.target.fatti.DocumentoFacade;
import lispa.schedulers.facade.target.fatti.FaseFacade;
import lispa.schedulers.facade.target.fatti.ManutenzioneFacade;
import lispa.schedulers.facade.target.fatti.PeiFacade;
import lispa.schedulers.facade.target.fatti.ProgettoDemandFacade;
import lispa.schedulers.facade.target.fatti.ProgettoEseFacade;
import lispa.schedulers.facade.target.fatti.ProgettoSviluppoDemandFacade;
import lispa.schedulers.facade.target.fatti.ProgettoSviluppoSviluppoFacade;
import lispa.schedulers.facade.target.fatti.ProgrammaFacade;
import lispa.schedulers.facade.target.fatti.ReleaseDiProgettoFacade;
import lispa.schedulers.facade.target.fatti.ReleaseItFacade;
import lispa.schedulers.facade.target.fatti.ReleaseServiziFacade;
import lispa.schedulers.facade.target.fatti.RichiestaGestioneFacade;
import lispa.schedulers.facade.target.fatti.RichiestaManutenzioneFacade;
import lispa.schedulers.facade.target.fatti.RichiestaSupportoFacade;
import lispa.schedulers.facade.target.fatti.SottoprogrammaFacade;
import lispa.schedulers.facade.target.fatti.TaskFacade;
import lispa.schedulers.facade.target.fatti.TaskItFacade;
import lispa.schedulers.facade.target.fatti.TestCaseFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryWorkitem;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryWorkitem;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.QTotal;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElPersonale;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsmProdottiArchitetture;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.EnumUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class TestWI extends TestCase {

	private static Logger logger = Logger.getLogger(DmAlmETL.class);
	private int retry;
	private int wait;
	
	private int strutturaOrgFk02;
	private int unitaOrganizzativaFk;
	private int righeModificate;
	private boolean modificato;
	private DmalmProject projectTmp;

	public void testProvenienzaDifetto(){
		try {
			DmAlmConfigReaderProperties.setFileProperties("/Users/danielecortis/Documents/Clienti/Lispa/Datamart/Test_locale/props/dm_alm.properties");
//			DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2020-02-27 22:00:00","yyyy-MM-dd HH:mm:00"));
			DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2020-03-05 00:00:00","yyyy-MM-dd HH:mm:00"));
			RecoverManager.getInstance().startRecoverTargetByProcedure();
 			RecoverManager.getInstance().startRecoverStaging();
 			//TotalDao.refreshTable();
// 			RecoverManager.getInstance().prepareTargetForRecover(dataEsecuzione)
			
//			QDmalmElUnitaOrganizzativeFlat flat= QDmalmElUnitaOrganizzativeFlat.qDmalmElUnitaOrganizzativeFlat;
//			SQLTemplates dialect = new HSQLDBTemplates();
//			Connection connection = ConnectionManager.getInstance().getConnectionOracle();
//
//			QDmalmProject proj = QDmalmProject.dmalmProject;
//			QTotal total=QTotal.total;
//			QSissHistoryWorkitem sisshistoryworkitem=QSissHistoryWorkitem.sissHistoryWorkitem;
//			QSireHistoryWorkitem sirehistoryworkitem=QSireHistoryWorkitem.sireHistoryWorkitem;
//			Log4JConfiguration.inizialize();
//			DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2000-01-01 00:00:00","yyyy-MM-dd HH:mm:00"));
//			
//			
//			Timestamp dataEsecuzione=DataEsecuzione.getInstance().getDataEsecuzione();
//			
//			RecoverManager.getInstance().startRecoverTargetByProcedure();
// 			RecoverManager.getInstance().startRecoverStaging();
//			
//			SissHistoryProjectDAO.fillSissHistoryProjectPkNotExist();
//			SireHistoryProjectDAO.fillSireHistoryProjectPkNotExist();
//			
//			logger.info("START fillSireHistoryWorkitemWithNoProjectFk" + new Date());
//			SireHistoryWorkitemDAO.fillSireHistoryWorkitemWithNoProjectFk();
//			logger.info("Stop fillSireHistoryWorkitemWithNoProjectFk" + new Date());
//			logger.info("START fillSissHistoryWorkitemWithNoProjectFk" + new Date());
//			SissHistoryWorkitemDAO.fillSissHistoryWorkitemWithNoProjectFk();
//			logger.info("Stop fillSissHistoryWorkitemWithNoProjectFk" + new Date());
//			logger.info("Start fillSireHistoryCfWorkitemByWorkitemTypeWithNoProject" + new Date());
//			SireHistoryCfWorkitemDAO.fillSireHistoryCfWorkitemByWorkitemTypeWithNoProject();
//			logger.info("Stop fillSireHistoryCfWorkitemByWorkitemTypeWithNoProject" + new Date());
//			logger.info("Start fillSissHistoryCfWorkitemByWorkitemTypeWithNoProject" + new Date());
//			SissHistoryCfWorkitemDAO.fillSissHistoryCfWorkitemByWorkitemTypeWithNoProject();
//			logger.info("Stop fillSissHistoryCfWorkitemByWorkitemTypeWithNoProject" + new Date());
//
//
//			List <DmalmProject >stagingProjects = ProjectSgrCmDAO.getAllProjectMinData(DataEsecuzione.getInstance().getDataEsecuzione());
//
//			for (DmalmProject project : stagingProjects) { 
//				List<Timestamp> row = new SQLQuery(connection,dialect)
//						.from(proj)
//						.where(proj.idProject.eq(project.getIdProject()))
//						.where(proj.idRepository.eq(project.getIdRepository()))
//						.where(proj.cPk.gt(project.getcPk()))
//						.list(proj.dtInizioValidita.min());
//				if(row.get(0)!=null) {					
//					
//					ProjectSgrCmDAO.insertProjectWithDtFineValidita(DateUtils.addSecondsToTimestamp(row.get(0),-1), project);
//				}
//				else {
//					ProjectSgrCmDAO.insertProject(project);
//				}
//			}
//			
//
//			logger.info("START PeiFacade.execute " + new Date()); 
//			PeiFacade.execute(dataEsecuzione);
//
//			logger.info("START BuildFacade.execute " + new Date());  
//			BuildFacade.execute(dataEsecuzione);
//
//			logger.info("START ProgettoESEFacade.execute "+ new Date()); 
//			ProgettoEseFacade.execute(dataEsecuzione);
//
//			logger.info("START TestCaseFacade.execute " + new Date());  
//			TestCaseFacade.execute(dataEsecuzione);
//
//			logger.info("START FaseFacade.execute " + new Date()); 
//			FaseFacade.execute(dataEsecuzione);
//
//			logger.info("START SottoprogrammaFacade.execute " + new Date()); 
//			SottoprogrammaFacade.execute(dataEsecuzione);
//
//			logger.info("START ProgrammaFacade.execute " + new Date()); 
//			ProgrammaFacade.execute(dataEsecuzione);
//
// 			logger.info("START ProgettoSviluppoDemandFacade.execute " 
//					+ new Date());
//			ProgettoSviluppoDemandFacade.execute(dataEsecuzione);
//
//			logger.info("START TaskItFacade.execute " + new Date());  
//			TaskItFacade.execute(dataEsecuzione);
//
//			logger.info("START ReleaseServiziFacade.execute " + new Date()); 
//			ReleaseServiziFacade.execute(dataEsecuzione);
//
//			logger.info("START ProgettoDemandFacade.execute " + new Date()); 
//			ProgettoDemandFacade.execute(dataEsecuzione);
//
//			logger.info("START RichiestaManutenzioneFacade.execute " 
//					+ new Date());
//			RichiestaManutenzioneFacade.execute(dataEsecuzione);
//			
//			logger.info("START ClassificatoreFacade.execute "); 
//			ClassificatoreFacade.execute(dataEsecuzione);
//
//			logger.info("START RichiestaGestioneFacade.execute " 
//					+ new Date());
//			RichiestaGestioneFacade.execute(dataEsecuzione);
//
//
//			logger.info("START Progetto_Svil_Svil_Facade.execute " 
//					+ new Date());
//			ProgettoSviluppoSviluppoFacade.execute(dataEsecuzione);
//
//			logger.info("START AnomaliaAssistenzaFacade.execute " 
//					+ new Date());
//			AnomaliaAssistenzaFacade.execute(dataEsecuzione);
//
//			logger.info("START AnomaliaFacade.execute " + new Date()); 
//			AnomaliaProdottoFacade.execute(dataEsecuzione);
//
//			logger.info("START TaskFacade.execute " + new Date()); 
//			TaskFacade.execute(dataEsecuzione);
//
//			logger.info("START ReleaseITFacade.execute " + new Date()); 
//			ReleaseItFacade.execute(dataEsecuzione);
//
//			logger.info("START DifettoFacade.execute " + new Date()); 
//			DifettoProdottoFacade.execute(dataEsecuzione);
//
//			logger.info("START ManutenzioneFacade.execute " + new Date()); 
//			ManutenzioneFacade.execute(dataEsecuzione);
//
//			logger.info("START DocumentoFacade.execute " + new Date()); 
//			DocumentoFacade.execute(dataEsecuzione);
//
//			logger.info("START ReleaseDiProgettoFacade.execute " 
//					+ new Date());
//			ReleaseDiProgettoFacade.execute(dataEsecuzione);
//			
//			// DM_ALM-350
//			logger.info("START RichiestaSupporto.execute " 
//					+ new Date());
//			RichiestaSupportoFacade.execute(dataEsecuzione);
//			
//			CheckProjectStorFacade.execute(); 
//			QueryManager qm = QueryManager.getInstance();
//			
//			qm.executeMultipleStatementsFromFile(
//					DmAlmConstants.M_UPDATE_RANK_FATTI,
//					DmAlmConstants.M_SEPARATOR);
//			qm.executeMultipleStatementsFromFile(
//					DmAlmConstants.M_UPDATE_TEMPO_FATTI,
//					DmAlmConstants.M_SEPARATOR);
//			qm.executeMultipleStatementsFromFile(
//					DmAlmConstants.M_UPDATE_AT_FATTI,
//					DmAlmConstants.M_SEPARATOR);
//			qm.executeMultipleStatementsFromFile(
//					DmAlmConstants.M_UPDATE_UO_FATTI,
//					DmAlmConstants.M_SEPARATOR);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private static void updateFlatProject() {
		try {
			QueryManager qm = QueryManager.getInstance();

			logger.info("INIZIO Update Project UnitaOrganizzativaFlatFk");
			qm.executeMultipleStatementsFromFile(
					DmAlmConstants.M_UPDATE_PROJECT_UOFLATFK,
					DmAlmConstants.M_SEPARATOR);
			logger.info("FINE Update Project UnitaOrganizzativaFlatFk");
		} catch (Exception e) {
			//non viene emesso un errore bloccante in quanto la Fk è recuperabile dopo l'esecuzione
			logger.error(e.getMessage(), e);
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
