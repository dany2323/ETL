package lispa.sgr.siss.history;


import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryUserDAO;

public class TestSISSHistoryUser
extends TestCase 
{
	
//	public void testFillStagingNewLogic() throws Exception {
//
//		VSissHistoryWorkitemLinkDAO.fillVSissWorkitemLink();
//		VSireHistoryWorkitemLinkDAO.fillVSireWorkitemLink();
//		
//	}

	public void testFillStaging() throws Exception {
		long user_minRevision = SissHistoryUserDAO.getMinRevision();
		long polarion_maxRevision = SissHistoryRevisionDAO.getMaxRevision();
		
		SissHistoryUserDAO.fillSissHistoryUser(user_minRevision, polarion_maxRevision);
	}
}