package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.fatti.ReleaseDiProgettoFacade;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

public class TestReleaseDiProgetto extends TestCase {

	private static Logger logger = Logger.getLogger(TestReleaseDiProgetto.class);

	public void testfillReleaseDiProgettoTarget() throws Exception {
		logger.debug("testfillreleaseDiProgettoTarget");

		ReleaseDiProgettoFacade.execute(DateUtils.stringToTimestamp(
				"2018-01-09 12:16:00.0", "yyyy-MM-dd HH:mm:00"));
	}
}
