package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.target.fatti.ProgettoDemandFacade;
import lispa.schedulers.utils.DateUtils;

public class TestProgettoDemand extends TestCase{

	public static void testProgettoDemand() throws DAOException, Exception {
		
		ProgettoDemandFacade.execute(DateUtils.stringToTimestamp("2014-11-24 15:09:00","yyyy-MM-dd HH:mm:00"));
		
	}

}
