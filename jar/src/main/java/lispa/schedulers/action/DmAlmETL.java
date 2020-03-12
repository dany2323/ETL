package lispa.schedulers.action;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.UtilsDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.TotalDao;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckAnomaliaDifettoProdottoFacade;
import lispa.schedulers.facade.cleaning.CheckProjectStorFacade;
import lispa.schedulers.facade.target.SvecchiamentoFacade;
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
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElUnitaOrganizzativeFlat;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.MailUtil;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.StringUtils;

public class DmAlmETL {

	private static Logger logger = Logger.getLogger(DmAlmETL.class);

	/**
	 * La classe principale, che contiene il metodo main(), è DmAlmETL, la quale
	 * invoca sequenzialmente i metodi doWork() delle tre classi
	 * DmAlmFillStaging, DmAlmCleaning e DmAlmFillTarget, che rappresentano
	 * ciascuna rispettivamente le tre fasi del processo ETL.
	 * 
	 * @param args
	 * @throws Exception 
	 */

	public static void main(String[] args) throws Exception {
		DmAlmConfigReaderProperties.setFileProperties(args[1]);
		Log4JConfiguration.inizialize();

		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = ConnectionManager.getInstance().getConnectionOracle();

		QDmalmProject proj = QDmalmProject.dmalmProject;
		DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2020-03-11 00:00:00","yyyy-MM-dd HH:mm:00"));
		
		
		ConnectionManager cm = null;
		connection = null;
		CallableStatement cs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			logger.info("Inizio Bonifica 499");
			String sql = QueryUtils.getCallProcedure("BONIFICA_499",0);
			cs = connection.prepareCall(sql);	
			cs.execute();        
			logger.info("Fine Bonifica 499");

			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if(cs!=null)
				cs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}
		
		TotalDao.refreshTable();
		
		Timestamp dataEsecuzione=DataEsecuzione.getInstance().getDataEsecuzione();
		
		SissHistoryProjectDAO.fillSissHistoryProjectPkNotExist();
		SireHistoryProjectDAO.fillSireHistoryProjectPkNotExist();
		
		logger.info("START fillSireHistoryWorkitemWithNoProjectFk" + new Date());
		SireHistoryWorkitemDAO.fillSireHistoryWorkitemWithNoProjectFk();
		logger.info("Stop fillSireHistoryWorkitemWithNoProjectFk" + new Date());
		logger.info("START fillSissHistoryWorkitemWithNoProjectFk" + new Date());
		SissHistoryWorkitemDAO.fillSissHistoryWorkitemWithNoProjectFk();
		logger.info("Stop fillSissHistoryWorkitemWithNoProjectFk" + new Date());
		logger.info("Start fillSireHistoryCfWorkitemByWorkitemTypeWithNoProject" + new Date());
		SireHistoryCfWorkitemDAO.fillSireHistoryCfWorkitemByWorkitemTypeWithNoProject();
		logger.info("Stop fillSireHistoryCfWorkitemByWorkitemTypeWithNoProject" + new Date());
		logger.info("Start fillSissHistoryCfWorkitemByWorkitemTypeWithNoProject" + new Date());
		SissHistoryCfWorkitemDAO.fillSissHistoryCfWorkitemByWorkitemTypeWithNoProject();
		logger.info("Stop fillSissHistoryCfWorkitemByWorkitemTypeWithNoProject" + new Date());


		List <DmalmProject >stagingProjects = ProjectSgrCmDAO.getAllProjectMinData(DataEsecuzione.getInstance().getDataEsecuzione());

		for (DmalmProject project : stagingProjects) { 
			List<Timestamp> row = new SQLQuery(connection,dialect)
					.from(proj)
					.where(proj.idProject.eq(project.getIdProject()))
					.where(proj.idRepository.eq(project.getIdRepository()))
					.where(proj.cPk.gt(project.getcPk()))
					.list(proj.dtInizioValidita.min());
			if(row.get(0)!=null) {					
				
				ProjectSgrCmDAO.insertProjectWithDtFineValidita(DateUtils.addSecondsToTimestamp(row.get(0),-1), project);
			}
			else {
				ProjectSgrCmDAO.insertProject(project);
			}
		}
		

		logger.info("START PeiFacade.execute " + new Date()); 
		PeiFacade.execute(dataEsecuzione);

		logger.info("START BuildFacade.execute " + new Date());  
		BuildFacade.execute(dataEsecuzione);

		logger.info("START ProgettoESEFacade.execute "+ new Date()); 
		ProgettoEseFacade.execute(dataEsecuzione);

		logger.info("START TestCaseFacade.execute " + new Date());  
		TestCaseFacade.execute(dataEsecuzione);

		logger.info("START FaseFacade.execute " + new Date()); 
		FaseFacade.execute(dataEsecuzione);

		logger.info("START SottoprogrammaFacade.execute " + new Date()); 
		SottoprogrammaFacade.execute(dataEsecuzione);

		logger.info("START ProgrammaFacade.execute " + new Date()); 
		ProgrammaFacade.execute(dataEsecuzione);

			logger.info("START ProgettoSviluppoDemandFacade.execute " 
				+ new Date());
		ProgettoSviluppoDemandFacade.execute(dataEsecuzione);

		logger.info("START TaskItFacade.execute " + new Date());  
		TaskItFacade.execute(dataEsecuzione);

		logger.info("START ReleaseServiziFacade.execute " + new Date()); 
		ReleaseServiziFacade.execute(dataEsecuzione);

		logger.info("START ProgettoDemandFacade.execute " + new Date()); 
		ProgettoDemandFacade.execute(dataEsecuzione);

		logger.info("START RichiestaManutenzioneFacade.execute " 
				+ new Date());
		RichiestaManutenzioneFacade.execute(dataEsecuzione);
		
		logger.info("START ClassificatoreFacade.execute "); 
		ClassificatoreFacade.execute(dataEsecuzione);

		logger.info("START RichiestaGestioneFacade.execute " 
				+ new Date());
		RichiestaGestioneFacade.execute(dataEsecuzione);


		logger.info("START Progetto_Svil_Svil_Facade.execute " 
				+ new Date());
		ProgettoSviluppoSviluppoFacade.execute(dataEsecuzione);

		logger.info("START AnomaliaAssistenzaFacade.execute " 
				+ new Date());
		AnomaliaAssistenzaFacade.execute(dataEsecuzione);

		logger.info("START AnomaliaFacade.execute " + new Date()); 
		AnomaliaProdottoFacade.execute(dataEsecuzione);

		logger.info("START TaskFacade.execute " + new Date()); 
		TaskFacade.execute(dataEsecuzione);

		logger.info("START ReleaseITFacade.execute " + new Date()); 
		ReleaseItFacade.execute(dataEsecuzione);

		logger.info("START DifettoFacade.execute " + new Date()); 
		DifettoProdottoFacade.execute(dataEsecuzione);

		logger.info("START ManutenzioneFacade.execute " + new Date()); 
		ManutenzioneFacade.execute(dataEsecuzione);

		logger.info("START DocumentoFacade.execute " + new Date()); 
		DocumentoFacade.execute(dataEsecuzione);

		logger.info("START ReleaseDiProgettoFacade.execute " 
				+ new Date());
		ReleaseDiProgettoFacade.execute(dataEsecuzione);
		
		// DM_ALM-350
		logger.info("START RichiestaSupporto.execute " 
				+ new Date());
		RichiestaSupportoFacade.execute(dataEsecuzione);
		
		CheckProjectStorFacade.execute(); 
		QueryManager qm = QueryManager.getInstance();
		
		qm.executeMultipleStatementsFromFile(
				DmAlmConstants.M_UPDATE_RANK_FATTI,
				DmAlmConstants.M_SEPARATOR);
		qm.executeMultipleStatementsFromFile(
				DmAlmConstants.M_UPDATE_TEMPO_FATTI,
				DmAlmConstants.M_SEPARATOR);
		qm.executeMultipleStatementsFromFile(
				DmAlmConstants.M_UPDATE_AT_FATTI,
				DmAlmConstants.M_SEPARATOR);
		qm.executeMultipleStatementsFromFile(
				DmAlmConstants.M_UPDATE_UO_FATTI,
				DmAlmConstants.M_SEPARATOR);
		TotalDao.refreshTable();
//		String ambiente = DmAlmConfigReader.getInstance()
//				.getProperty(DmAlmConfigReaderProperties.DM_ALM_AMBIENTE);
//
//		logger.info("START KILL_BO_SESSIONS PROCEDURE");
//		try {
//			UtilsDAO.killsBOSessions();
//		} catch (DAOException e1) {
//			logger.info(e1.getMessage());
//		}
//		logger.info("STOP KILL_BO_SESSIONS PROCEDURE");
//		logger.info("START Recupero project mancanti");
//		DmAlmFillStaging.doWork(); // Commentato Thread ORESTE all'interno
//		logger.info("STOP Recupero project mancanti");
//
//		if (!RecoverManager.getInstance().isRecovered()) {
//
//			try {
//				ConnectionManager.getInstance().dismiss();
//			} catch (Exception e) {
//				logger.debug(e);
//			}
//
//			logger.info("START DmAlmFillTarget.doWork()");
//			DmAlmFillTarget.doWork();
//			logger.info("STOP DmAlmFillTarget.doWork()");
//
//			try {
//				ConnectionManager.getInstance().dismiss();
//			} catch (Exception e) {
//				logger.debug(e);
//			}
//
//			logger.info("START DmAlmCleaning.doWork()");
//			DmAlmCleaning.doWork(); // Commentati Cleaning Oreste all'interno
//			logger.info("STOP DmAlmCleaning.doWork()");
//
//			// ATTIVITA' POST TARGET
//			// se non è stato eseguito il recover passo agli step successivi
//			if (!RecoverManager.getInstance().isRecovered()) {
//				logger.info("START DmAlmCheckAnnullamenti.doWork()");
//				DmAlmCheckAnnullamenti.doWork();
//				logger.info("STOP DmAlmCheckAnnullamenti.doWork()");
//
//				// se errore non eseguo gli step successivi
//				if (!ErrorManager.getInstance().hasError()) {
//					logger.info(
//							"START DmAlmCheckLinkSferaSgrCmElettra.doWork()");
//					DmAlmCheckLinkSferaSgrCmElettra.doWork();
//					logger.info(
//							"STOP DmAlmCheckLinkSferaSgrCmElettra.doWork()");
//				}
//
//				// se errore non eseguo gli step successivi
//				if (!ErrorManager.getInstance().hasError()) {
//					logger.info("START DmAlmCheckChangedWorkitem.doWork()");
//					DmAlmCheckChangedWorkitem.doWork();
//					logger.info("STOP DmAlmCheckChangedWorkitem.doWork()");
//				}
//
//				// gestione esecuzione effettuata direttamente nel facade
//				CheckAnomaliaDifettoProdottoFacade.execute();
//
//				// Gestione delle filiere, gestione esecuzione effettuata
//				// direttamente nel facade
//				DmAlmFiliere.doWork();
//
//				// pulizia tabelle esiti e errori caricamento
//				SvecchiamentoFacade.execute();
//
//				// se errore in uno degli step precedenti eseguo il ripristino
//				// di tutto
//				if (ErrorManager.getInstance().hasError()) {
//					// SFERA/ELETTRA/SGRCM
//					if (ExecutionManager.getInstance().isExecutionSfera()
//							|| ExecutionManager.getInstance()
//									.isExecutionElettraSgrcm()
//							|| ExecutionManager.getInstance()
//									.isExecutionCalipso()) {
//						RecoverManager.getInstance()
//								.startRecoverTargetByProcedure();
//						RecoverManager.getInstance().startRecoverStaging();
//					}
//
//					// MPS
//					if (ExecutionManager.getInstance().isExecutionMps()) {
//						RecoverManager.getInstance().startRecoverTrgMps();
//						RecoverManager.getInstance().startRecoverStgMps();
//					}
//				}
//			}
//		}
//
//		if (!RecoverManager.getInstance().isRecovered()) {
//			List<String> listMessage = StringUtils
//					.getLogFromStoredProcedureByTimestamp(
//							DmAlmConstants.STORED_PROCEDURE_VERIFICA_ESITO_ETL);
//			if (listMessage != null) {
//				for (String message : listMessage) {
//					logger.info(message);
//				}
//			} else {
//				logger.info(
//						"*** NESSUNA INFORMAZIONE DAI LOG DELLA STORED PROCEDURE ***");
//			}
//		}
//		// se errore nella Stored Procedure effettuo il ripristino di tutto
//		if (ErrorManager.getInstance().hasError()
//				&& !RecoverManager.getInstance().isRecovered()) {
//			// SFERA/ELETTRA/SGRCM
//			if (ExecutionManager.getInstance().isExecutionSfera()
//					|| ExecutionManager.getInstance()
//							.isExecutionElettraSgrcm()) {
//				RecoverManager.getInstance().startRecoverTargetByProcedure();
//				RecoverManager.getInstance().startRecoverStaging();
//			}
//
//			// MPS
//			if (ExecutionManager.getInstance().isExecutionMps()) {
//				RecoverManager.getInstance().startRecoverTrgMps();
//				RecoverManager.getInstance().startRecoverStgMps();
//			}
//		}
//
//		// gestione della mail di notifica
//		String esito = "";
//		String esitoBody = "";
//
//		if (RecoverManager.getInstance().isRecovered()) {
//			esito = DmAlmConstants.ETLKO;
//			esitoBody = esito + "\n\nRollback Effettuato. Causa Errore: "
//					+ "\n\n" + ErrorManager.getInstance().getException();
//		} else {
//			esito = DmAlmConstants.ETLOK;
//
//			if (RecoverManager.getInstance().isRecoveredStagingMps())
//				esito += DmAlmConstants.ETLMPSKO;
//
//			esitoBody = DmAlmConstants.ETLOK;
//			esitoBody += "\nEDMA/SGR_CM: "
//					+ (ExecutionManager.getInstance().isExecutionElettraSgrcm()
//							? "Eseguito"
//							: "NON eseguito");
//			esitoBody += "\nSFERA: "
//					+ (ExecutionManager.getInstance().isExecutionSfera()
//							? "Eseguito"
//							: "NON eseguito");
//			esitoBody += "\nMPS: "
//					+ (ExecutionManager.getInstance().isExecutionMps()
//							? "Eseguito"
//							: "NON eseguito");
//			if (RecoverManager.getInstance().isRecoveredStagingMps())
//				esitoBody += DmAlmConstants.ETLMPSKO;
//		}
//
//		logger.info("START MailUtil.sendMail: " + ambiente + " - " + esito);
//
//		logger.info("*** Fine Esecuzione DmAlmEtl v"
//				+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");
//
//		MailUtil.sendMail(ambiente + " - " + DmAlmConstants.SUBJECT
//				+ new Timestamp(System.currentTimeMillis()) + " - " + esito,
//				ambiente + " - " + esitoBody,
//				DataEsecuzione.getInstance().getDataEsecuzione());
	}
}