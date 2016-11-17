package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.fatti.ProgrammaFacade;
import lispa.schedulers.utils.DateUtils;

public class TestProgramma extends TestCase{

	public TestProgramma() {
		
	}
	
	public void testProgram() {
		try {
			ProgrammaFacade.execute(DateUtils.stringToTimestamp("2014-06-12 18:02:00","yyyy-MM-dd HH:mm:00"));
		} catch (Exception e) {
			// 
			
		}
	}
}