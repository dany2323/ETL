package lispa.sfera;

import junit.framework.TestCase;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiSferaFacade;
import lispa.schedulers.manager.Log4JConfiguration;

import org.junit.Test;

public class testCheckAnnullamentiSfera extends TestCase {

	@Test
	public void test() {
		 try{
		 Log4JConfiguration.inizialize();
		
		 CheckAnnullamentiSferaFacade.execute();
		 } catch (Exception e)
		 {
			 System.out.println(""+e.getMessage());
		 }
	}
}
