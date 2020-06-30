package lispa.schedulers.action;

import org.apache.log4j.Logger;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.KillBoSessionFacade;
import lispa.schedulers.facade.UtilsFonteFacade;
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
		DmAlmConfigReaderProperties.setFileProperties(args[1]);
		Log4JConfiguration.inizialize();
		DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp(args[2], "yyyy-MM-dd HH:mm:00"));
																					
		String ambiente = DmAlmConfigReader.getInstance().getProperty(
				DmAlmConfigReaderProperties.DM_ALM_AMBIENTE);

		logger.info("*** Eseguo DmAlmEtl FONTE v."
				+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");

		logger.info("Caricamento dati dalla fonte sull Ambiente: " + ambiente);

		logger.info("Esecuzione SGR_CM/ELETTRA: "
				+ ExecutionManager.getInstance().isExecutionElettraSgrcm());
		
		logger.info("Esecuzione SFERA: "
				+ ExecutionManager.getInstance().isExecutionSfera());

		logger.info("Esecuzione MPS: "
				+ ExecutionManager.getInstance().isExecutionMps());

		logger.info("Esecuzione CALIPSO: "
				+ ExecutionManager.getInstance().isExecutionCalipso());
		
		KillBoSessionFacade.execute();

		if (ExecutionManager.getInstance().isExecutionSfera()) {
			UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SFERA, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		}
		if (ExecutionManager.getInstance().isExecutionElettraSgrcm()) {
			UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		}
		if (ExecutionManager.getInstance().isExecutionMps()) {
			UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_MPS, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		}
		if (ExecutionManager.getInstance().isExecutionCalipso()) {
			UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_CALIPSO, DmAlmConstants.CARICAMENTO_FONTE_INIT);
		}
		logger.info("START DELETE_DATI_FONTE_TABELLE");
		UtilsFonteFacade.deleteDatiFonteTabelle();
		logger.info("STOP DELETE_DATI_FONTE_TABELLE");
		
		logger.info("START DmAlmFillFonte.doWork()");
		DmAlmFillFonte.doWork();
		logger.info("STOP DmAlmFillFonte.doWork()");

		if (!ErrorManager.getInstance().hasError()) {
			logger.info("*** Fine Esecuzione DmAlmEtl FONTE v."
					+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");
		}
	}
}