package lispa.target;


import junit.framework.TestCase;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.target.UserElPersonaleFacade;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

public class TestUserElPersonale extends TestCase {
	
	public void test() {
		try {
			Log4JConfiguration.inizialize();
			UserElPersonaleFacade.execute(DateUtils.stringToTimestamp("2017-10-30 11:00:00.0", "yyyy-MM-dd HH:mm:00"));

		} catch (PropertiesReaderException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
