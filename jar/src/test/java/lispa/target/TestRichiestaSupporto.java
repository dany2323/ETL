package lispa.target;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.fatti.RichiestaSupportoFacade;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

public class TestRichiestaSupporto extends TestCase {
	
	private Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
			"2018-05-28 22:40:00", "yyyy-MM-dd HH:mm:00");
	
	public void testFillRichiestaSupporto() throws Exception {
		Log4JConfiguration.inizialize();
		RichiestaSupportoFacade.execute(dataEsecuzione);
	}
	

}
