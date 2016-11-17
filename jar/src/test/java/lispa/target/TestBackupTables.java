package lispa.target;

import junit.framework.TestCase;
import lispa.schedulers.manager.RecoverManager;

public class TestBackupTables extends TestCase{
	
	public static void testT_tables() {
		RecoverManager.getInstance().prepareTargetForRecover();
	}

}
