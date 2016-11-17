package lispa.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

public class TestSQL extends TestCase {

	private static Logger logger = Logger.getLogger(TestSQL.class);

	public void testSQL() throws Exception {

		ConnectionManager cm = null;
		QueryManager qm = null;
		Connection oracle = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;

		try {

			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.setDataEsecuzione(
							DateUtils.stringToTimestamp("2014-05-15 09:44:00",
									"yyyy-MM-dd HH:mm:00"));

			cm = ConnectionManager.getInstance();
			qm = QueryManager.getInstance();

			oracle = cm.getConnectionOracle();

			String sql1 = qm
					.getQuery(DmAlmConfigReaderProperties.SQL_PROGRAMMA);
			String sql2 = qm
					.getQuery(DmAlmConfigReaderProperties.SQL_PROGETTO_DEMAND);

			ps1 = oracle.prepareStatement(sql1);
			ps2 = oracle.prepareStatement(sql2);

			ps1.setTimestamp(1, dataEsecuzione);
			ps1.setTimestamp(2, dataEsecuzione);
			ps2.setTimestamp(1, dataEsecuzione);
			ps2.setTimestamp(2, dataEsecuzione);

			rs1 = ps1.executeQuery();
			rs2 = ps2.executeQuery();

			rs1.close();
			rs2.close();
		} catch (Exception e) {
			logger.error(e);
		}
	}

}
