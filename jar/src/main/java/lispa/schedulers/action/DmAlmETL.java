package lispa.schedulers.action;

import org.apache.log4j.Logger;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.UtilsDAO;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

public class DmAlmETL {

	private static Logger logger = Logger.getLogger(DmAlmETL.class);
	
	public static void main(String[] args) throws PropertiesReaderException {
		DmAlmConfigReaderProperties.setFileProperties("/Users/lucaporro/LISPA/DataMart/props/dm_alm.properties");
		Log4JConfiguration.inizialize();
		DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2020-06-02 21:00:00 yyyy-MM-dd HH:mm:00"));
		
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
		UtilsDAO.killsBOSessions();
		logger.info("STOP KILL_BO_SESSIONS PROCEDURE");

		if (ExecutionManager.getInstance().isExecutionSfera()) {
			UtilsDAO.setCaricamentoFonte(DmAlmConstants.FONTE_SFERA, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		}
		if (ExecutionManager.getInstance().isExecutionElettraSgrcm()) {
			UtilsDAO.setCaricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		}
		if (ExecutionManager.getInstance().isExecutionMps()) {
			UtilsDAO.setCaricamentoFonte(DmAlmConstants.FONTE_MPS, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		}
		if (ExecutionManager.getInstance().isExecutionCalipso()) {
			UtilsDAO.setCaricamentoFonte(DmAlmConstants.FONTE_CALIPSO, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		}
//		logger.info("START DELETE_DATI_FONTE_TABELLE");
//		UtilsDAO.deleteDatiFonteTabelle();
//		logger.info("STOP DELETE_DATI_FONTE_TABELLE");
		
		logger.info("START DmAlmFillFonte.doWork()");
		DmAlmFillFonte.doWork();
		logger.info("STOP DmAlmFillFonte.doWork()");

		if (!ErrorManager.getInstance().hasError()) {
			logger.info("*** Fine Esecuzione DmAlmEtl FONTE v."
					+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");
		}
	}
}