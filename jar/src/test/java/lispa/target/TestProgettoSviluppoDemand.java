package lispa.target;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.fatti.ProgettoSviluppoDemandFacade;
import lispa.schedulers.utils.DateUtils;

public class TestProgettoSviluppoDemand extends TestCase {
	
public void testFillTargetProgettoSviluppoDemand() throws Exception {
		
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-06-19 17:21:00", "yyyy-MM-dd HH:mm:00");
		
		ProgettoSviluppoDemandFacade.execute(dataEsecuzione);
		
	}
	

}
