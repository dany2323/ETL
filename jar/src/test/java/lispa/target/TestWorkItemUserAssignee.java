package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.target.WorkitemUserAssigneeFacade;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

public class TestWorkItemUserAssignee extends TestCase {

	private static Logger logger = Logger
			.getLogger(TestWorkItemUserAssignee.class);

	public static void testWIAssignee() throws DAOException, Exception {
		logger.debug("testWIAssignee");
		
		WorkitemUserAssigneeFacade.execute(DateUtils.stringToTimestamp(
				"2014-10-11 21:00:00", "yyyy-MM-dd HH:mm:00"));
	}
}
