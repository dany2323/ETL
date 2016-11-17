package lispa.schedulers.runnable;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.runnable.cleaning.CleaningRunnable;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

public class DmalmCleaningTest extends TestCase {
	private static Logger logger = Logger.getLogger(DmalmCleaningTest.class);

	public void testDmAlmCleaning() {
		try {
			Log4JConfiguration.inizialize();
			
			logger.debug("START DmalmCleaningTest - testDmAlmCleaning");
			
			Timestamp dateEsecuzione = DateUtils.stringToTimestamp(
					"2015-12-09 08:00:00", "yyyy-MM-dd HH:mm:00");

			Thread cleaning = new Thread(new CleaningRunnable(logger,
					dateEsecuzione));
			
			cleaning.start();
			cleaning.join();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
