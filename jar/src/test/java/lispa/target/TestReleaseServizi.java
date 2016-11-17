package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.target.fatti.ReleaseServiziFacade;
import lispa.schedulers.utils.DateUtils;

public class TestReleaseServizi extends TestCase {
	
	public void testReleaseServizi() throws DAOException, Exception {
		ReleaseServiziFacade.execute(DateUtils.stringToTimestamp("2014-06-06 13:36:00.0","yyyy-MM-dd HH:mm:00"));
	}
}
