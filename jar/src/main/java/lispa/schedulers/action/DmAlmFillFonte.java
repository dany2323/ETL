package lispa.schedulers.action;

import static lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE;
import static lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS;
import static lispa.schedulers.constant.DmAlmConstants.SCHEMA_CURRENT;
import static lispa.schedulers.constant.DmAlmConstants.SCHEMA_HISTORY;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.facade.calipso.staging.StagingCalipsoFacade;
import lispa.schedulers.facade.mps.staging.StgMpsFacade;
import lispa.schedulers.facade.sfera.staging.StgMisuraFacade;
import lispa.schedulers.facade.sgr.staging.FillSGRCMFacade;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.utils.QueryUtils;
import org.apache.log4j.Logger;

public class DmAlmFillFonte {

	final private static Logger logger = Logger.getLogger(DmAlmFillFonte.class);

	public static void doWork() {

		try {

			// ELETTRA/SGRCM
			if (ExecutionManager.getInstance().isExecutionElettraSgrcm()) {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_PENDING);
				logger.info("START FillStagingSgrCmSire");
				// SIRE CURRENT
				FillSGRCMFacade.execute(REPOSITORY_SIRE, SCHEMA_CURRENT, logger);
				// SIRE HISTORY
				FillSGRCMFacade.execute(REPOSITORY_SIRE, SCHEMA_HISTORY, logger);
				logger.info("STOP FillStagingSgrCmSire");

				logger.info("START FillStagingSgrCmSiss");
				// SISS CURRENT
				FillSGRCMFacade.execute(REPOSITORY_SISS, SCHEMA_CURRENT, logger);
				// SISS HISTORY
				FillSGRCMFacade.execute(REPOSITORY_SISS, SCHEMA_HISTORY, logger);
				logger.info("STOP FillStagingSgrCmSiss");

				// Gestione Elettra
//				StagingElettraFacade.executeStaging();
			}
			if (!ErrorManager.getInstance().hasError()) {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_OK);
			} else {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_KO);
			}
			
			// SFERA
			ErrorManager.getInstance().resetError();
			if (ExecutionManager.getInstance().isExecutionSfera()) {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_SFERA, DmAlmConstants.CARICAMENTO_FONTE_PENDING);
				StgMisuraFacade.fillStgMisura();
			}
			if (!ErrorManager.getInstance().hasError()) {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_SFERA, DmAlmConstants.CARICAMENTO_FONTE_OK);
			} else {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_SFERA, DmAlmConstants.CARICAMENTO_FONTE_KO);
			}
			
			// MPS
			ErrorManager.getInstance().resetError();
			if (ExecutionManager.getInstance().isExecutionMps()) {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_MPS, DmAlmConstants.CARICAMENTO_FONTE_PENDING);
				logger.info("START: FillStaging MPS");
				StgMpsFacade.fillStgMps();
				logger.info("STOP: FillStaging MPS");
			}
			if (!ErrorManager.getInstance().hasError()) {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_MPS, DmAlmConstants.CARICAMENTO_FONTE_OK);
			} else {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_MPS, DmAlmConstants.CARICAMENTO_FONTE_KO);
			}
						
			//CALIPSO
			ErrorManager.getInstance().resetError();
			if (ExecutionManager.getInstance().isExecutionCalipso()) {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_CALIPSO, DmAlmConstants.CARICAMENTO_FONTE_PENDING);
				StagingCalipsoFacade.executeStaging();
			}
			if (!ErrorManager.getInstance().hasError()) {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_CALIPSO, DmAlmConstants.CARICAMENTO_FONTE_OK);
			} else {
				QueryUtils.setCaricamentoFonte(DmAlmConstants.FONTE_CALIPSO, DmAlmConstants.CARICAMENTO_FONTE_KO);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}
}
