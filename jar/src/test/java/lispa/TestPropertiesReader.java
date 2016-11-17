package lispa;

import junit.framework.TestCase;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;

import org.apache.log4j.Logger;

public class TestPropertiesReader extends TestCase {
	private static Logger logger = Logger.getLogger(TestPropertiesReader.class);

	public void testAddDaysToTimestamp() throws Exception {

	}

	public void testGetProperties() throws Exception {

		String test = DmAlmConfigReader.getInstance().getProperty(
				DmAlmConfigReaderProperties.DM_ALM_HOST);

		logger.debug("test: " + test);
	}
}