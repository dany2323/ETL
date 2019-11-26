package lispa.schedulers.action;

import java.sql.Timestamp;
import org.apache.log4j.Logger;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.UtilsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.utils.MailUtil;

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
		DmAlmConfigReaderProperties.setFileProperties(args[1]);
		Log4JConfiguration.inizialize();

		String ambiente = DmAlmConfigReader.getInstance().getProperty(
				DmAlmConfigReaderProperties.DM_ALM_AMBIENTE);

		logger.info("*** Eseguo DmAlmEtl v"
				+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");

		logger.info("Ambiente: " + ambiente);

		logger.info("Esecuzione SFERA: "
				+ ExecutionManager.getInstance().isExecutionSfera());
		/*
		 * logger.info("Esecuzione EDMA/ORESTE/SGR_CM: " +
		 * ExecutionManager.getInstance().isExecutionElettraSgrcm());
		 */
		logger.info("Esecuzione EDMA/SGR_CM: "
				+ ExecutionManager.getInstance().isExecutionElettraSgrcm());

		logger.info("Esecuzione MPS: "
				+ ExecutionManager.getInstance().isExecutionMps());

		logger.info("START KILL_BO_SESSIONS PROCEDURE");
		try {
			UtilsDAO.killsBOSessions();
		} catch (DAOException e1) {
			logger.info(e1.getMessage());
		}
		logger.info("STOP KILL_BO_SESSIONS PROCEDURE");

		logger.info("START DmAlmFillStaging.doWork()");
		DmAlmFillStaging.doWork(); // Commentato Thread ORESTE all'interno
		logger.info("STOP DmAlmFillStaging.doWork()");

		// se errore nella Stored Procedure effettuo il ripristino di tutto
		if (ErrorManager.getInstance().hasError()
				&& !RecoverManager.getInstance().isRecovered()) {
			// SFERA/ELETTRA/SGRCM
			if (ExecutionManager.getInstance().isExecutionSfera()
					|| ExecutionManager.getInstance()
							.isExecutionElettraSgrcm()) {
				RecoverManager.getInstance().startRecoverTargetByProcedure();
				RecoverManager.getInstance().startRecoverStaging();
			}

			// MPS
			if (ExecutionManager.getInstance().isExecutionMps()) {
				RecoverManager.getInstance().startRecoverTrgMps();
				RecoverManager.getInstance().startRecoverStgMps();
			}
		}

		// gestione della mail di notifica
		String esito = "";
		String esitoBody = "";

		if (RecoverManager.getInstance().isRecovered()) {
			esito = DmAlmConstants.ETLKO;
			esitoBody = esito + "\n\nRollback Effettuato. Causa Errore: "
					+ "\n\n" + ErrorManager.getInstance().getException();
		} else {
			esito = DmAlmConstants.ETLOK;

			if (RecoverManager.getInstance().isRecoveredStagingMps())
				esito += DmAlmConstants.ETLMPSKO;

			esitoBody = DmAlmConstants.ETLOK;
			esitoBody += "\nEDMA/SGR_CM: "
					+ (ExecutionManager.getInstance().isExecutionElettraSgrcm()
							? "Eseguito"
							: "NON eseguito");
			esitoBody += "\nSFERA: "
					+ (ExecutionManager.getInstance().isExecutionSfera()
							? "Eseguito"
							: "NON eseguito");
			esitoBody += "\nMPS: "
					+ (ExecutionManager.getInstance().isExecutionMps()
							? "Eseguito"
							: "NON eseguito");
			esitoBody += "\nCALIPSO: "
					+ (ExecutionManager.getInstance().isExecutionCalipso()
							? "Eseguito"
							: "NON eseguito");
			if (RecoverManager.getInstance().isRecoveredStagingMps())
				esitoBody += DmAlmConstants.ETLMPSKO;
		}

		logger.info("START MailUtil.sendMail: " + ambiente + " - " + esito);

		logger.info("*** Fine Esecuzione DmAlmEtl v"
				+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");

		MailUtil.sendMail(ambiente + " - " + DmAlmConstants.SUBJECT
				+ new Timestamp(System.currentTimeMillis()) + " - " + esito,
				ambiente + " - " + esitoBody,
				DataEsecuzione.getInstance().getDataEsecuzione());
	}
}