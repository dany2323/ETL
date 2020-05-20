package lispa.schedulers.action;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectGroupDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryUserDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryUserDAO;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryProjectgroup;

public class DmAlmETL {

	private static Logger logger = Logger.getLogger(DmAlmETL.class);
	/**
	 * La classe principale, che contiene il metodo main(), è DmAlmETL, la quale
	 * invoca sequenzialmente i metodi doWork() delle tre classi
	 * DmAlmFillStaging, DmAlmCleaning e DmAlmFillTarget, che rappresentano
	 * ciascuna rispettivamente le tre fasi del processo ETL.
	 * 
	 * @param args
	 * @throws PropertiesReaderException
	 */
	
	public static void main(String[] args) throws PropertiesReaderException {
		DmAlmConfigReaderProperties.setFileProperties("/Users/danielecortis/Documents/Clienti/Lispa/Datamart/Test_locale/props/dm_alm.properties");
		Log4JConfiguration.inizialize();
		
		try {
//			long user_minRevision = SireHistoryUserDAO.getMinRevision();
//			long polarion_maxRevision = SireHistoryRevisionDAO.getMaxRevision();
//
//			SireHistoryUserDAO.fillSireHistoryUser(user_minRevision, polarion_maxRevision);
			
//			long user_minRevision = SissHistoryUserDAO.getMinRevision();
//			long polarion_maxRevision = SireHistoryRevisionDAO.getMaxRevision();
//			long revision_minRevision = SireHistoryProjectDAO.getMinRevision();
//			SireHistoryProjectDAO.fillSireHistoryProject(revision_minRevision, polarion_maxRevision);
			
			SireHistoryProjectGroupDAO.fillSireHistoryProjectGroup();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		if (args.length > 2) {
//			DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp(args[2]));
//		}
//		logger.info("DataEsecuzione: " + DataEsecuzione.getInstance().getDataEsecuzione());
//		String ambiente = DmAlmConfigReader.getInstance().getProperty(
//				DmAlmConfigReaderProperties.DM_ALM_AMBIENTE);
//
//		logger.info("*** Eseguo DmAlmEtl v"
//				+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");
//
//		logger.info("Ambiente: " + ambiente);
//
//		logger.info("Esecuzione SFERA: "
//				+ ExecutionManager.getInstance().isExecutionSfera());
//		/*
//		 * logger.info("Esecuzione EDMA/ORESTE/SGR_CM: " +
//		 * ExecutionManager.getInstance().isExecutionElettraSgrcm());
//		 */
//		logger.info("Esecuzione EDMA/SGR_CM: "
//				+ ExecutionManager.getInstance().isExecutionElettraSgrcm());
//
//		logger.info("Esecuzione MPS: "
//				+ ExecutionManager.getInstance().isExecutionMps());
//
//		logger.info("START KILL_BO_SESSIONS PROCEDURE");
//		try {
//			UtilsDAO.killsBOSessions();
//		} catch (DAOException e1) {
//			logger.info(e1.getMessage());
//		}
//		logger.info("STOP KILL_BO_SESSIONS PROCEDURE");
//
//		logger.info("START DmAlmFillStaging.doWork()");
//		DmAlmFillStaging.doWork(); // Commentato Thread ORESTE all'interno
//		logger.info("STOP DmAlmFillStaging.doWork()");
//
//		if (!RecoverManager.getInstance().isRecovered()) {
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
//
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
//									.isExecutionElettraSgrcm()) {
//						RecoverManager.getInstance().startRecoverTargetByProcedure();
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
//			esitoBody += "\nCALIPSO: "
//					+ (ExecutionManager.getInstance().isExecutionCalipso()
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