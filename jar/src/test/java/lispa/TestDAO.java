package lispa;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentWorkitemLinkedDAO;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;

public class TestDAO extends TestCase {

	public void testGetConnectionOracle() throws Exception {
		DmAlmConfigReaderProperties.setFileProperties("/Users/danielecortis/Documents/Clienti/Lispa/Datamart/Test_locale/props/dm_alm_pg.properties");
		Log4JConfiguration.inizialize();
		try {
//			logger.debug("START fillSireCurrentWorkitemLinked");
			SireCurrentWorkitemLinkedDAO.fillSireCurrentWorkitemLinked();
//			logger.debug("STOP fillSireCurrentWorkitemLinked");
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