package lispa.sgr.util;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;

public class TestIndex extends TestCase {
	
	public void testIndexDropping() {
	
	SireHistoryWorkitemDAO.dropIndexes();
	SissHistoryWorkitemDAO.dropIndexes();
	
	}

}
