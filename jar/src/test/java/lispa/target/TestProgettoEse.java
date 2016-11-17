package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.target.fatti.ProgettoEseFacade;
import lispa.schedulers.utils.DateUtils;

public class TestProgettoEse extends TestCase {

	public static void testProgettoEse() throws DAOException, Exception {

		ProgettoEseFacade.execute(DateUtils.stringToTimestamp("2014-05-29 13:01:00","yyyy-MM-dd HH:mm:00"));

	}
}
