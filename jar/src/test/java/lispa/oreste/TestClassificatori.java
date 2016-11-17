package lispa.oreste;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.dao.oreste.AmbienteTecnologicoDAO;
import lispa.schedulers.dao.oreste.ClassificatoriDAO;
import lispa.schedulers.facade.cleaning.CheckOresteAmbienteTecnologicoFacade;
import lispa.schedulers.facade.cleaning.CheckOresteClassificatoriFacade;
import lispa.schedulers.facade.cleaning.CheckOresteFunzionalitaFacade;
import lispa.schedulers.facade.cleaning.CheckOresteModuliFacade;
import lispa.schedulers.facade.cleaning.CheckOresteProdottiFacade;
import lispa.schedulers.facade.cleaning.CheckOresteSottosistemiFacade;
import lispa.schedulers.facade.cleaning.CheckSGRSIREProjectFacade;
import lispa.schedulers.facade.cleaning.CheckSGRSISSProjectFacade;
import lispa.schedulers.facade.target.AmbienteTecnologicoFacade;
import lispa.schedulers.facade.target.ClassificatoriFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.oreste.OresteDmAlmClassificatori;
import lispa.schedulers.queryimplementation.staging.oreste.QStgClassificatori;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class TestClassificatori extends TestCase {
	Logger logger = Logger.getLogger(TestClassificatori.class);

	public void testRunAsyncCheckOresteClassificatori() throws Exception {

		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-04-16 15:07:00", "yyyy-MM-dd HH:mm:00");

		// CANCELLO I DATI DI OGGI

		// CONTROLLI
		java.util.Date dateInizio = new java.util.Date();

		Thread t1 = new Thread(new CheckOresteClassificatoriFacade(logger,
				dataEsecuzione));

		Thread t2 = new Thread(new CheckOresteProdottiFacade(logger,
				dataEsecuzione));

		Thread t3 = new Thread(new CheckOresteSottosistemiFacade(logger,
				dataEsecuzione));

		Thread t4 = new Thread(new CheckOresteModuliFacade(logger,
				dataEsecuzione));

		Thread t5 = new Thread(new CheckOresteFunzionalitaFacade(logger,
				dataEsecuzione));

		Thread t6 = new Thread(new CheckOresteAmbienteTecnologicoFacade(logger,
				dataEsecuzione));

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();

		Timestamp dataEsecuzioneProj = DateUtils.stringToTimestamp(
				"2014-03-28 18:45:00", "yyyy-MM-dd HH:mm:00");

		Thread t7 = new Thread(new CheckSGRSIREProjectFacade(logger,
				dataEsecuzioneProj));

		Thread t8 = new Thread(new CheckSGRSISSProjectFacade(logger,
				dataEsecuzioneProj));
		t7.start();
		t8.start();
		// t1.join();
		// t2.join();
		// t3.join();
		// t4.join();
		// t5.join();
		// t6.join();
		// t7.join();
		// t8.join();
		while (t1.isAlive() || t2.isAlive() || t3.isAlive() || t4.isAlive()
				|| t5.isAlive() || t6.isAlive() || t7.isAlive() || t8.isAlive()) {
			// donothing
		}

		// Calcolo il tempo di esecuzione dei thread
		java.util.Date dateFine = new java.util.Date();

		long diff = dateFine.getTime() - dateInizio.getTime();

		logger.debug(diff);
	}

	public void testGetDuplicatiNomeClassificatore() throws Exception {

	}

	public void testConnectionLISPA() throws Exception {
		ClassificatoriDAO.fillClassificatori();
	}

	public void testStoricizzazione() throws Exception {
		// DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2014-12-10 10:41:00.0",
		// "yyyy-MM-dd HH:mm:00"));
		// ClassificatoriDAO.delete(DataEsecuzione.getInstance().getDataEsecuzione());
		System.out.println("Inizio");
		AmbienteTecnologicoDAO.fillAmbienteTecnologico();
		System.out.println("STG AMBTECN");
		ClassificatoriDAO.fillClassificatori();
		System.out.println("STG CLASSF");
		ClassificatoriFacade.execute(DataEsecuzione.getInstance()
				.getDataEsecuzione());
		System.out.println("FACADE CLASF");
		AmbienteTecnologicoFacade.execute(DataEsecuzione.getInstance()
				.getDataEsecuzione());
		System.out.println("FACADE AMB_TECN");
	}

	public void testFillTargetClassificatoreStoricizzato() throws Exception {
		ClassificatoriFacade.execute(DataEsecuzione.getInstance()
				.getDataEsecuzione());
	}

	public void t() throws Exception {
		ClassificatoriDAO.delete(DataEsecuzione.getInstance()
				.getDataEsecuzione());
		ClassificatoriDAO.fillClassificatori();
	}

	public void testGetAllClassificatori() throws Exception {

	}

	// public void testCodiceClassificatoreIsNull() throws Exception {
	// Logger logger = Logger.getLogger(DmAlmCleaning.class);
	//
	// CheckOresteClassificatoriFacade.checkCodiciClassificatoriNull(logger,
	// DataEsecuzione.getInstance().getDataEsecuzione());
	// }
	//
	// public void testCodiceClassificatoreDuplicato() throws Exception {
	// Logger logger = Logger.getLogger(DmAlmCleaning.class);
	//
	// CheckOresteClassificatoriFacade.checkCodiciClassificatoriDuplicati(logger,
	// DataEsecuzione.getInstance().getDataEsecuzione());
	// }

	public void testFillClassificatoriStaging() throws Exception {

		try {
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection connection = cm.getConnectionOracle();

			OresteDmAlmClassificatori viewClassificatori = OresteDmAlmClassificatori.dmAlmClassificatori;
			QStgClassificatori stgClassificatori = QStgClassificatori.stgClassificatori;

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			// CODICE_TIPOLOGIA VARCHAR2
			// CODICE_CLASSIFICATORE VARCHAR2
			// ID NUMBER
			// DATA_CARICAMENTO TIMESTAMP(6)

			new SQLInsertClause(connection, dialect, stgClassificatori)
					.columns(stgClassificatori.codiceTipologia,
							stgClassificatori.codiceClassificatore,
							stgClassificatori.id,
							stgClassificatori.dataCaricamento,
							stgClassificatori.dmalmStgClassificatoriPk

					)
					.select(new SQLSubQuery()
							.from(viewClassificatori)
							.list(viewClassificatori.codiceTipologia,
									viewClassificatori.codiceClassificatore,
									viewClassificatori.id,
									StringTemplate
											.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))"),
									StringTemplate
											.create("STG_CLASSIFICATORI_SEQ.nextval")))
					.execute();

		} catch (Throwable e) {

		}

	}

	public void testGetDuplicateCodiciClassificatori() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> classificatori = null;

		/*
		 * select * from DMALM_STG_CLASSIFICATORI b where
		 * b.CODICE_CLASSIFICATORE in ( select a.CODICE_CLASSIFICATORE from
		 * DMALM_STG_CLASSIFICATORI a group by a.CODICE_CLASSIFICATORE having
		 * count(*) > 1 )
		 */
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgClassificatori stgClassificatori = QStgClassificatori.stgClassificatori;

			classificatori =

			query.from(stgClassificatori)
					.where(stgClassificatori.codiceClassificatore
							.in(new SQLSubQuery()
									.from(stgClassificatori)
									.groupBy(
											stgClassificatori.codiceClassificatore)
									.having(stgClassificatori.codiceClassificatore
											.count().gt(1))
									.list(stgClassificatori.codiceClassificatore)))
					.list(stgClassificatori.all());

			logger.debug(classificatori.size());

		} catch (Exception e) {

		} finally {
			if (connection != null) {
				connection.close();
			}
		}

	}

	public void testGetCodiciClassificatoriNull() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> classificatori = null;

		/*
		 * select * from DMALM_STG_CLASSIFICATORI where CODICE_CLASSIFICATORE is
		 * null
		 */
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgClassificatori stgClassificatori = QStgClassificatori.stgClassificatori;

			classificatori =

			query.from(stgClassificatori)
					.where(stgClassificatori.codiceClassificatore.isNull())
					.list(stgClassificatori.all());

			logger.debug(classificatori.size());

		} catch (Exception e) {

		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
}