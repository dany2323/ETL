package lispa.target;


import junit.framework.TestCase;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.target.SvecchiamentoFacade;
import lispa.schedulers.manager.Log4JConfiguration;

public class TestSvecchiamento extends TestCase {
	
	public void test() {
		try {
			Log4JConfiguration.inizialize();
			SvecchiamentoFacade.execute();

		} catch (PropertiesReaderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
