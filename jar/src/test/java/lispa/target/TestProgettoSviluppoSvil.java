package lispa.target;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.fatti.ProgettoSviluppoSviluppoFacade;
import lispa.schedulers.utils.DateUtils;

public class TestProgettoSviluppoSvil extends TestCase {

	public void testFillTargetProgettoSviluppoSvil() throws Exception {
		
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2014-05-20 17:13:00", "yyyy-MM-dd HH:mm:00");
		
		ProgettoSviluppoSviluppoFacade.execute(dataEsecuzione);
		
	}
	
}
