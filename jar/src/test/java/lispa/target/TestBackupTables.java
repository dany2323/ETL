package lispa.target;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.RecoverManager;

public class TestBackupTables extends TestCase{
	
	public static void testT_tables() {
		Timestamp dataEsecuzione = DataEsecuzione.getInstance()
				.getDataEsecuzione();
		RecoverManager.getInstance().prepareTargetForRecover(dataEsecuzione);
	}

}
