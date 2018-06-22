package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.CostruzioneFilieraTemplateIntTecnicaFacade;
import lispa.schedulers.manager.Log4JConfiguration;

public class TestFilieraTemplateIntTecnica extends TestCase {
	
public void testFillTargetProgettoSviluppoDemand() throws Exception {
		Log4JConfiguration.inizialize();
		CostruzioneFilieraTemplateIntTecnicaFacade.execute();
	}
}
