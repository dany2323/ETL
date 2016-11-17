package lispa.sgr.siss.history;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;

import org.apache.log4j.Logger;

public class TestSISSHistoryRevision extends TestCase {
	final Logger logger = Logger.getLogger(TestSISSHistoryRevision.class);

	public void testFillSissHistoryRevision() throws Exception {

		Timestamp sissminimum = SissHistoryRevisionDAO.getMinRevision();
		java.util.Date dateInizio = new java.util.Date();

		SissHistoryRevisionDAO.fillSissHistoryRevision(sissminimum, 50000);
		java.util.Date dateFine = new java.util.Date();

		long diff = dateFine.getTime() - dateInizio.getTime();
		logger.debug("diff: " + diff);
	}
}
