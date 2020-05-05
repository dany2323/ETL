package lispa;

import java.sql.Connection;

import it.lispa.jotm.datasource.crypt.EncryptionHelper;
import it.lispa.jotm.datasource.crypt.support.BaseEncryptionHelper;
import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;

public class TestConnection extends TestCase {

	private static EncryptionHelper encryptionHelper = new BaseEncryptionHelper();
	public static void testGetConnection() throws Exception {
		
		ConnectionManager cm = null;
		Connection conn = null;
		DmAlmConfigReaderProperties.setFileProperties("/Users/danielecortis/Documents/Clienti/Lispa/Datamart/Test_locale/props/dm_alm_pg.properties");
		Log4JConfiguration.inizialize();
		try  {
		cm = ConnectionManager.getInstance();
		conn = cm.getConnectionSIREHistory();
		
		long project_minRevision = SissHistoryProjectDAO.getMinRevision();
		long polarion_maxRevision = SissHistoryRevisionDAO.getMaxRevision();
		
		System.out.print("SissHistoryProjectDAO.fillSissHistoryProject - minRevision: " + project_minRevision + ", maxRevision: " + polarion_maxRevision);


//		
		
//		if(cm != null) cm.closeConnection(conn);
//		
//		ConnectionManager.sysoutInfo();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(cm != null) cm.closeConnection(conn);
		}
		
	}
	
}
