package lispa;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DM_ALM_PSW;

import java.sql.Connection;

import it.lispa.jotm.datasource.crypt.EncryptionHelper;
import it.lispa.jotm.datasource.crypt.support.BaseEncryptionHelper;
import junit.framework.TestCase;
import lispa.schedulers.manager.ConnectionManager;

public class TestConnection extends TestCase {

	private static EncryptionHelper encryptionHelper = new BaseEncryptionHelper();
	public static void testGetConnection() throws Exception {
		
		ConnectionManager cm = null;
		Connection conn = null;
		
		try  {
			String pass_dm_alm = encryptionHelper.decrypt("8AF9A9520D0E313A3F3DE89708BDD1F6DA8985A580A38F7411F3747B3F4D");
			String pass_dm_alm_preprod = encryptionHelper.decrypt("IOe3LleqpwAkIk3UwBbSZQ==");
			String enc_pass_dm_alm_preprod = encryptionHelper.encrypt("dm_alm_pre_1706");
			
			System.out.println(pass_dm_alm+" - "+pass_dm_alm_preprod+" enc: "+enc_pass_dm_alm_preprod);
//		cm = ConnectionManager.getInstance();
//		conn = cm.getConnectionSIREHistory();
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
