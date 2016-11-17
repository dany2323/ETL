package lispa.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_TARGET_LOG_DELETE;

import java.sql.PreparedStatement;
import org.apache.log4j.Logger;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;

public class TestLogDebug extends TestCase{
	private static Logger logger = Logger.getLogger(TestLogDebug.class);
	private static int days;
	public static void testElapsed () throws DAOException, Exception {
		try {
			days = Integer.parseInt(DmAlmConfigReader.getInstance().getProperty(DMALM_TARGET_LOG_DELETE));
			logger.debug("DELETE ELAPSED LOG FROM DMALM_LOG_DEBUG");
			PreparedStatement ps = ConnectionManager.getInstance().getConnectionOracle().prepareStatement(QueryManager.getInstance().getQuery(DmAlmConfigReaderProperties.SQL_TARGET_LOG_DELETE));
			ps.setInt(1, days);
			ps.execute();
			
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		
	}

}
