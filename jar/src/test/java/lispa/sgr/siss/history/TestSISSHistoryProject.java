package lispa.sgr.siss.history;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckSGRSISSProjectFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class TestSISSHistoryProject extends TestCase {
	private static Logger logger;

	public void testMaxRevision() {
		try {
			Log4JConfiguration.inizialize();
			long maxRevisions = SissHistoryRevisionDAO.getMaxRevision();
			Timestamp minRevision = SissHistoryRevisionDAO.getMinRevision();
			SissHistoryRevisionDAO.fillSissHistoryRevision(minRevision, maxRevisions);
			logger.debug(maxRevisions);
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
	}
//	public void testFillStaging() throws Exception {
//
//		// C_TRACKERPREFIX VARCHAR2
//		// C_IS_LOCAL NUMBER
//		// C_PK VARCHAR2
//		// FK_URI_LEAD VARCHAR2
//		// C_DELETED NUMBER
//		// C_FINISH DATE
//		// C_URI VARCHAR2
//		// C_START DATE
//		// FK_URI_PROJECTGROUP VARCHAR2
//		// C_ACTIVE NUMBER
//		// C_LOCATION VARCHAR2
//		// FK_PROJECTGROUP VARCHAR2
//		// FK_LEAD VARCHAR2
//		// C_LOCKWORKRECORDSDATE DATE
//		// C_NAME VARCHAR2
//		// C_ID VARCHAR2
//		// C_REV NUMBER
//
//	}

//	public void testTemplateAll() throws Exception {
//
//		// SissHistoryProjectDAO.setTemplateAll();
//
//	}

//	public void testFillProjectStaging() throws Exception {
//		long minRevisionProjects = SissHistoryProjectDAO.getMinRevision();
//		long maxRevisions = SissHistoryRevisionDAO.getMaxRevision();
//
//		SissHistoryProjectDAO.fillSissHistoryProject(minRevisionProjects,
//				maxRevisions);
//	}
//	public void testGetProject() throws DAOException, PropertiesReaderException {
//		Log4JConfiguration.inizialize();
//		logger = Logger.getLogger(ConnectionManager.class);
//		CheckSGRSISSProjectFacade.execute(logger, DateUtils.stringToTimestamp(
//				"2014-07-14 09:50:00", "yyyy-MM-dd HH:mm:00"));
//	}

}