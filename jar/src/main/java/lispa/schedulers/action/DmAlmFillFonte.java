package lispa.schedulers.action;

import static lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE;
import static lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS;
import static lispa.schedulers.constant.DmAlmConstants.SCHEMA_CURRENT;
import static lispa.schedulers.constant.DmAlmConstants.SCHEMA_HISTORY;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.facade.calipso.staging.StagingCalipsoFacade;
import lispa.schedulers.facade.fonteutils.UtilsFonteFacade;
import lispa.schedulers.facade.mps.staging.StgMpsFacade;
import lispa.schedulers.facade.sfera.staging.StgMisuraFacade;
import lispa.schedulers.facade.sgr.staging.FillSGRCMFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import org.apache.log4j.Logger;

public class DmAlmFillFonte {

	final private static Logger logger = Logger.getLogger(DmAlmFillFonte.class);

	public static void doWork() {

		try {

			// ELETTRA/SGRCM
			if (ExecutionManager.getInstance().isExecutionElettraSgrcm()) {
				UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_PENDING);
				logger.info("START FillFonteSgrCmSire");
				// SIRE CURRENT
				FillSGRCMFacade.execute(REPOSITORY_SIRE, SCHEMA_CURRENT, logger);
				ConnectionManager.getInstance().dismiss();
				if (!ErrorManager.getInstance().hasError()) {
					// SIRE HISTORY
					FillSGRCMFacade.execute(REPOSITORY_SIRE, SCHEMA_HISTORY, logger);
					ConnectionManager.getInstance().dismiss();
					logger.info("STOP FillFonteSgrCmSire");
					if (!ErrorManager.getInstance().hasError()) {
						logger.info("START FillFonteSgrCmSiss");
						// SISS CURRENT
						FillSGRCMFacade.execute(REPOSITORY_SISS, SCHEMA_CURRENT, logger);
						ConnectionManager.getInstance().dismiss();
						if (!ErrorManager.getInstance().hasError()) {
							// SISS HISTORY
							FillSGRCMFacade.execute(REPOSITORY_SISS, SCHEMA_HISTORY, logger);
							ConnectionManager.getInstance().dismiss();
							if (!ErrorManager.getInstance().hasError()) {
								UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_OK);
								logger.info("STOP FillFonteSgrCmSiss");
							} else {
								UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_KO);
								logger.info("STOP FillFonteSgrCmSiss");
							}
						} else {
							UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_KO);
							logger.info("STOP FillFonteSgrCmSiss");
						}
					} else {
						UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_KO);
						logger.info("STOP FillFonteSgrCmSire");
					}
				} else {
					UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SGR, DmAlmConstants.CARICAMENTO_FONTE_KO);
					logger.info("STOP FillFonteSgrCmSire");
				}
				
				// Gestione Elettra
//				StagingElettraFacade.executeStaging();
			}
			
			// SFERA
			ErrorManager.getInstance().resetError();
			if (ExecutionManager.getInstance().isExecutionSfera()) {
				UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SFERA, DmAlmConstants.CARICAMENTO_FONTE_PENDING);
				StgMisuraFacade.fillStgMisura();
			}
			if (!ErrorManager.getInstance().hasError()) {
				UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SFERA, DmAlmConstants.CARICAMENTO_FONTE_OK);
			} else {
				UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_SFERA, DmAlmConstants.CARICAMENTO_FONTE_KO);
			}
			
			// MPS
			ErrorManager.getInstance().resetError();
			if (ExecutionManager.getInstance().isExecutionMps()) {
				UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_MPS, DmAlmConstants.CARICAMENTO_FONTE_PENDING);
				logger.info("START: FillFonte MPS");
				StgMpsFacade.fillStgMps();
				logger.info("STOP: FillFonte MPS");
			}
			if (!ErrorManager.getInstance().hasError()) {
				UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_MPS, DmAlmConstants.CARICAMENTO_FONTE_OK);
			} else {
				UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_MPS, DmAlmConstants.CARICAMENTO_FONTE_KO);
			}
						
			//CALIPSO
			ErrorManager.getInstance().resetError();
			if (ExecutionManager.getInstance().isExecutionCalipso()) {
				UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_CALIPSO, DmAlmConstants.CARICAMENTO_FONTE_PENDING);
				StagingCalipsoFacade.executeStaging();
			}
			if (!ErrorManager.getInstance().hasError()) {
				UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_CALIPSO, DmAlmConstants.CARICAMENTO_FONTE_OK);
			} else {
				UtilsFonteFacade.caricamentoFonte(DmAlmConstants.FONTE_CALIPSO, DmAlmConstants.CARICAMENTO_FONTE_KO);
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
