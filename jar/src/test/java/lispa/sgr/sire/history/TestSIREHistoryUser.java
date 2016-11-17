package lispa.sgr.sire.history;


import junit.framework.TestCase;
import lispa.schedulers.facade.target.UserRolesSgrFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.Log4JConfiguration;

public class TestSIREHistoryUser
extends TestCase 
{
	
	public void testFillStaging() throws Exception {
		Log4JConfiguration.inizialize();
//		SireHistoryUserDAO.fillSireHistoryUser(0);
	}

	public void testStoricizzazione() throws Exception {
		
		UserRolesSgrFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
	}
	
	
}