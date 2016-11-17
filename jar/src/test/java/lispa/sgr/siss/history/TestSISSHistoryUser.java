package lispa.sgr.siss.history;


import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.VSireHistoryWorkitemLinkDAO;
import lispa.schedulers.dao.sgr.siss.history.VSissHistoryWorkitemLinkDAO;

public class TestSISSHistoryUser
extends TestCase 
{
	
	public void testFillStagingNewLogic() throws Exception {

		VSissHistoryWorkitemLinkDAO.fillVSissWorkitemLink();
		VSireHistoryWorkitemLinkDAO.fillVSireWorkitemLink();
		
	}

	public void testFillStaging() throws Exception
	{

	}
}