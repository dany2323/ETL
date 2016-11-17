package lispa;

import java.sql.Connection;



import junit.framework.TestCase;
import lispa.schedulers.manager.ConnectionManager;

public class TestConnection extends TestCase {

	public static void testGetConnection() throws Exception {
		
		ConnectionManager cm = null;
		Connection conn = null;
		
		try  {
		cm = ConnectionManager.getInstance();
		conn = cm.getConnectionSIREHistory();
//		
		
//		if(cm != null) cm.closeConnection(conn);
//		
//		ConnectionManager.sysoutInfo();
		}
		catch(Exception e) {
			
		}
		finally {
			if(cm != null) cm.closeConnection(conn);
		}
		
	}
	
}
