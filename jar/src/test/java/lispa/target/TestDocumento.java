package lispa.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.RelClassificatoriOresteFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

public class TestDocumento extends TestCase {

	private static Logger logger = Logger.getLogger(TestDocumento.class);

	public void testUpdateCFCa() throws Exception {

		Timestamp dataEsecuzione = DataEsecuzione.getInstance()
				.setDataEsecuzione(
						DateUtils.stringToTimestamp("2014-07-22 22:18:00",
								"yyyy-MM-dd HH:mm:00"));

		logger.debug("testUpdateCFCa");
		
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection connection = cm.getConnectionOracle();

		String sql = QueryManager.getInstance().getQuery(
				"update_cf_ca_siss.sql");
		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setTimestamp(1, dataEsecuzione);

		System.out.println(ps.executeUpdate());

	}

	public void testFillHyperlink2() throws Exception {

		Timestamp dataEsecuzione = DataEsecuzione.getInstance()
				.setDataEsecuzione(
						DateUtils.stringToTimestamp("2014-07-08 17:32:00",
								"yyyy-MM-dd HH:mm:00"));

		ConnectionManager cm = ConnectionManager.getInstance();
		Connection connection = cm.getConnectionOracle();

		String sql = QueryManager.getInstance().getQuery("hyperlink2.sql");
		PreparedStatement ps = connection.prepareStatement(sql);

		ps.setTimestamp(1, dataEsecuzione);
		ps.setTimestamp(2, dataEsecuzione);

		ps.execute();

	}

	public void testMultipleQueries() throws Exception {

		QueryManager.getInstance().executeMultipleStatementsFromFile(
				"m_resetranks.sql", ";");
		QueryManager.getInstance().executeMultipleStatementsFromFile(
				"m_updateranksinmonth.sql", ";");
		QueryManager.getInstance().executeMultipleStatementsFromFile(
				"m_update_uo_fatti.sql", ";");
		QueryManager.getInstance().executeMultipleStatementsFromFile(
				"m_update_tempo_fatti.sql", ";");
		QueryManager.getInstance().executeMultipleStatementsFromFile(
				"m_update_annullamento_fatti.sql", ";");

	}

	public void testFillTarget() throws Exception {
		Timestamp dataEsecuzione = DataEsecuzione.getInstance()
				.setDataEsecuzione(
						DateUtils.stringToTimestamp("2014-07-09 17:12:00",
								"yyyy-MM-dd HH:mm:00"));

		RelClassificatoriOresteFacade.execute(dataEsecuzione);

		ConnectionManager.sysoutInfo();

	}

}
