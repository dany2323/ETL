package lispa.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import junit.framework.TestCase;
import lispa.schedulers.manager.ConnectionManager;

public class TestOracleTuning extends TestCase {

	public static void testFetchSize() throws Exception {

		for (int i = 1; i < 10000; i = i + 100) {
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection conn = cm.getConnectionOracle();
			String sql = "SELECT * FROM DMALM_PROGETTO_SVILUPPO_SVIL";
			PreparedStatement ps = conn.prepareStatement(sql);

			ps.setFetchSize(i);

			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				continue;
			}
		}
	}
}
