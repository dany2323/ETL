package lispa.schedulers.action;

import java.sql.Timestamp;

import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.sfera.CheckLinkAsmSferaProjectElFacade;
import lispa.schedulers.facade.sfera.CheckLinkSferaSgrCmFacade;
import lispa.schedulers.facade.target.CheckDmalmSourceElProdFacade;
import lispa.schedulers.facade.target.CheckLinkProjectSgrCmProdottiArchFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ExecutionManager;
import org.apache.log4j.Logger;

public class DmAlmCheckLinkSferaSgrCmElettra {
	private static Logger logger = Logger
			.getLogger(DmAlmCheckLinkSferaSgrCmElettra.class);

	protected static void doWork() throws PropertiesReaderException {

		try {
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();

			// ELETTRA/SGRCM
			if (ExecutionManager.getInstance().isExecutionElettraSgrcm()) {
				
				//Validation Data on DMALM_SOURCE_EL_PROD_ECCEZ
				CheckDmalmSourceElProdFacade.execute(dataEsecuzione);
				
				// Prodotto Oreste
				//CheckLinkProjectSgrCmProdottoFacade.execute(dataEsecuzione); //TODO non ho commentato ancora nulla
				// ProdottiArchitetture Elettra
				CheckLinkProjectSgrCmProdottiArchFacade.execute(dataEsecuzione);
			}

			// ELETTRA/SGRCM e SFERA
			if (ExecutionManager.getInstance().isExecutionElettraSgrcm() || ExecutionManager.getInstance().isExecutionSfera()) {
				// Asm/Project/Prodotto Oreste
//				CheckLinkAsmSferaProjectFacade.execute();
				// Asm/Project/ProdottiArchitetture Elettra
				CheckLinkAsmSferaProjectElFacade.execute();

				// controllo progetto sfera verso wi sgrcm
				CheckLinkSferaSgrCmFacade.execute(dataEsecuzione);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
