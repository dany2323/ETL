package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.target.UserRolesSgrFacade;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

public class TestUserRolesSgr extends TestCase {
	
	public void test() {
		try {
			Log4JConfiguration.inizialize();
			
			UserRolesSgrFacade.execute(DateUtils.stringToTimestamp("2017-07-12 11:00:00.0", "yyyy-MM-dd HH:mm:00"));

		} catch (PropertiesReaderException e) {
			e.printStackTrace();
		}
		
	}

}
