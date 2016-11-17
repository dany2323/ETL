package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.target.fatti.TestCaseFacade;
import lispa.schedulers.utils.DateUtils;

public class TestWorkItemGroupOne extends TestCase{
	
	public void testBuildPeiTC () throws DAOException, Exception {
		
		//BuildFacade.execute(DateUtils.stringToTimestamp("2014-06-09 11:46:00","yyyy-MM-dd HH:mm:00"));
		
		
//		PeiFacade.execute(DateUtils.stringToTimestamp("2014-06-09 11:46:00","yyyy-MM-dd HH:mm:00"));
		
		
		TestCaseFacade.execute(DateUtils.stringToTimestamp("2014-06-09 14:37:00","yyyy-MM-dd HH:mm:00"));
		
	}
	

}
