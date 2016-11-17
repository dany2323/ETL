package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.fatti.TaskFacade;
import lispa.schedulers.utils.DateUtils;

public class TestTask extends TestCase{

	public void testTask() {
		try {
			TaskFacade.execute(DateUtils.stringToTimestamp("2014-06-17 09:14:00","yyyy-MM-dd HH:mm:00"));
		} catch (Exception e) {
			// 
			
		}
	}
}
