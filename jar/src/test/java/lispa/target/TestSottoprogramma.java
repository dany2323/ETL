package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.fatti.SottoprogrammaFacade;
import lispa.schedulers.utils.DateUtils;

import org.junit.Test;

public class TestSottoprogramma extends TestCase{

	@Test
	public void testSubProgram() {
		try {
			SottoprogrammaFacade.execute(DateUtils.stringToTimestamp("2014-05-21 09:02:00.0","yyyy-MM-dd HH:mm:00"));
		} catch (Exception e) {
			// 
			
		}
	}

}
