package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.fatti.ReleaseItFacade;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

public class TestReleaseIt extends TestCase {

	private static Logger logger = Logger.getLogger(TestReleaseIt.class);

	public void testfillreleaseItTarget() throws Exception {
		logger.debug("testfillreleaseItTarget");

		ReleaseItFacade.execute(DateUtils.stringToTimestamp(
				"2014-05-30 17:33:00.0", "yyyy-MM-dd HH:mm:00"));
	}
}
