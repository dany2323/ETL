package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.fatti.BuildFacade;
import lispa.schedulers.utils.DateUtils;

public class TestBuild extends TestCase {
	
	public void testfillbuildTarget() throws Exception {
		BuildFacade.execute(DateUtils.stringToTimestamp("2014-06-03 17:08:00","yyyy-MM-dd HH:mm:00"));
	}

}
