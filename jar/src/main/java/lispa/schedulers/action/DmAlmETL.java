package lispa.schedulers.action;

import org.apache.log4j.Logger;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.UtilsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.QueryUtils;

public class DmAlmETL {

	private static Logger logger = Logger.getLogger(DmAlmETL.class);
	
	public static void main(String[] args) throws PropertiesReaderException {
		DmAlmConfigReaderProperties.setFileProperties(args[1]);
		Log4JConfiguration.inizialize();

		String ambiente = DmAlmConfigReader.getInstance().getProperty(
				DmAlmConfigReaderProperties.DM_ALM_AMBIENTE);

		logger.info("*** Eseguo DmAlmEtl FONTE v."
				+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");

		logger.info("Caricamento dati dalla fonte sull Ambiente: " + ambiente);

		logger.info("Esecuzione SFERA: "
				+ ExecutionManager.getInstance().isExecutionSfera());

		logger.info("Esecuzione EDMA/SGR_CM: "
				+ ExecutionManager.getInstance().isExecutionElettraSgrcm());

		logger.info("Esecuzione MPS: "
				+ ExecutionManager.getInstance().isExecutionMps());

		logger.info("Esecuzione CALIPSO: "
				+ ExecutionManager.getInstance().isExecutionCalipso());
		
		logger.info("START KILL_BO_SESSIONS PROCEDURE");
		try {
			UtilsDAO.killsBOSessions();
		} catch (DAOException e1) {
			logger.info(e1.getMessage());
		}
		logger.info("STOP KILL_BO_SESSIONS PROCEDURE");

		QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_SFERA, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_MPS, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_CALIPSO, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		
		logger.info("START DmAlmFillStaging.doWork()");
		DmAlmFillStaging.doWork();
		logger.info("STOP DmAlmFillStaging.doWork()");

		if (!ErrorManager.getInstance().hasError()) {
			logger.info("*** Fine Esecuzione DmAlmEtl FONTE v."
					+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");
		}
	}
}