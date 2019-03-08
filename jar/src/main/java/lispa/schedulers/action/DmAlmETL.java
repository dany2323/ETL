package lispa.schedulers.action;

import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.utils.DateUtils;
import org.apache.log4j.Logger;

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
		Log4JConfiguration.inizialize();

		String ambiente = DmAlmConfigReader.getInstance().getProperty(
				DmAlmConfigReaderProperties.DM_ALM_AMBIENTE);

		logger.info("*** Eseguo Recover DmAlmEtl v"
				+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");
		
		logger.info("Ambiente: " + ambiente);
		
		DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp(
				"2019-03-03 23:50:00", "yyyy-MM-dd HH:mm:00"));
		
		RecoverManager.getInstance().startRecoverTargetByProcedure();
		RecoverManager.getInstance().startRecoverStaging();
		RecoverManager.getInstance().startRecoverTrgMps();
		RecoverManager.getInstance().startRecoverStgMps();
		
		logger.info("*** Fine Esecuzione Recover DmAlmEtl v"
				+ DmAlmConfigReaderProperties.VERSIONE_ETL + " ***");
	}	
}