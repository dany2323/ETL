package lispa.sgr.sire.history;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;

import org.apache.log4j.Logger;

public class TestSIREHistoryRevision extends TestCase {
	final Logger logger = Logger.getLogger(TestSIREHistoryRevision.class);

	public void testFillSireRevision() throws Exception {

		Timestamp sireminimum = SireHistoryRevisionDAO.getMinRevision();
		java.util.Date dateInizio = new java.util.Date();

		SireHistoryRevisionDAO.fillSireHistoryRevision(sireminimum, 2500);
		java.util.Date dateFine = new java.util.Date();

		long diff = dateFine.getTime() - dateInizio.getTime();
		logger.debug("diff: " + diff);
	}
}
