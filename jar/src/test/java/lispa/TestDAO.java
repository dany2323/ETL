package lispa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;
import lispa.schedulers.manager.ConnectionManager;

public class TestDAO extends TestCase {

	public void testGetConnectionOracle() throws Exception {

		try {
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection c = cm.getConnectionOracle();

			Statement stmt = c.createStatement();
			String selectquery = "select * from PRODOTTI_ARCHITETTURE";

			ResultSet rs = stmt.executeQuery(selectquery);

			while (rs.next()) {

			}
		} catch (Throwable e) {

		}
	}

	public void testGetConnectionH2() throws Exception {

		try {
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection c = cm.getConnectionSIRECurrent();

			Statement stmt = c.createStatement();
			String selectquery = "select * from PROJECT";

			ResultSet rs = stmt.executeQuery(selectquery);

			while (rs.next()) {

			}
		} catch (Throwable e) {

		}
	}
}