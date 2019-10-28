package lispa.schedulers.action;

import lispa.schedulers.facade.target.CostruzioneFilieraAnomalieFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraProduttivaAvanzataFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraProduttivaFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateAssFunzionaleFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateDemandFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateIntTecnicaFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateServiziFacade;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateSviluppoFacade;
import lispa.schedulers.manager.ErrorManager;

import org.apache.log4j.Logger;

public class DmAlmFiliere {
	final private static Logger logger = Logger
			.getLogger(DmAlmFiliere.class);
	
	public static void doWork() {
		// Se si è verificato un errore precedente non eseguo l'elaborazione
		if (ErrorManager.getInstance().hasError()) {
			return;
		}
					
		logger.info("START DmAlmFiliere.doWork()");
		
		// gestione esecuzione effettuata direttamente nel facade
		CostruzioneFilieraProduttivaFacade.execute();
		
		// gestione esecuzione effettuata direttamente nel facade
		CostruzioneFilieraAnomalieFacade.execute();
		
		// gestione esecuzione effettuata direttamente nel facade
		CostruzioneFilieraTemplateIntTecnicaFacade.execute();
		
		// gestione esecuzione effettuata direttamente nel facade
		CostruzioneFilieraTemplateDemandFacade.execute();
		
		// gestione esecuzione effettuata direttamente nel facade
		CostruzioneFilieraTemplateSviluppoFacade.execute();

		// gestione esecuzione effettuata direttamente nel facade
		CostruzioneFilieraTemplateServiziFacade.execute();

		// gestione esecuzione effettuata direttamente nel facade
		CostruzioneFilieraTemplateAssFunzionaleFacade.execute();
		
		// gestione esecuzione effettuata direttamente nel facade
		CostruzioneFilieraProduttivaAvanzataFacade.execute();
				
		logger.info("STOP DmAlmFiliere.doWork()");
	}
}
