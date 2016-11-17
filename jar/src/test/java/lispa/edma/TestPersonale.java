package lispa.edma;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.action.DmAlmFillTarget;
import lispa.schedulers.dao.edma.PersonaleDAO;
import lispa.schedulers.facade.target.PersonaleEdmaFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.edma.EdmaDmAlmPersonale;
import lispa.schedulers.queryimplementation.staging.edma.QStgPersonale;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class TestPersonale extends TestCase {

	private static Logger logger = Logger.getLogger(DmAlmFillTarget.class);

	public void testXOR() throws Exception {
		logger.debug("testXOR");
	}

	public void testConnectionLISPA() throws Exception {
		PersonaleDAO.fillPersonale();
	}

	public void testStoricizzazionePersonale() throws Exception {
		Timestamp dataEsecuzione = DataEsecuzione.getInstance()
				.getDataEsecuzione();

		PersonaleEdmaFacade.execute(dataEsecuzione);
	}

	public void testFillPersonale() throws Exception {

		PersonaleDAO.fillPersonale();
	}

	public void testFillStaging() throws Exception {

		try {
			ConnectionManager cm = ConnectionManager.getInstance();
			Connection connection = cm.getConnectionOracle();

			EdmaDmAlmPersonale qViewPersonale = EdmaDmAlmPersonale.dmAlmPersonale;
			QStgPersonale qStgPersonale = QStgPersonale.stgPersonale;

			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLInsertClause(connection, dialect, qStgPersonale)
					.columns(qStgPersonale.id, qStgPersonale.codice,
							qStgPersonale.dataInizioValidita,
							qStgPersonale.dataFineValidita,
							qStgPersonale.dataAttivazione,
							qStgPersonale.dataDisattivazione,
							qStgPersonale.note, qStgPersonale.interno,
							qStgPersonale.codResponsabile,
							qStgPersonale.indirizzoEmail, qStgPersonale.nome,
							qStgPersonale.cognome, qStgPersonale.matricola,
							qStgPersonale.codiceFiscale,
							qStgPersonale.identficatore,
							qStgPersonale.idGradoUfficio, qStgPersonale.idSede,
							qStgPersonale.codSuperiore, qStgPersonale.codEnte,
							qStgPersonale.codVisibilita,
							qStgPersonale.tipopersona,
							qStgPersonale.dataCaricamento,
							qStgPersonale.dmalmStgPersonalePk)
					.select(new SQLSubQuery().from(qViewPersonale).list(
							qViewPersonale.id,
							qViewPersonale.codice,
							qViewPersonale.dataInizioValidita,
							qViewPersonale.dataFineValidita,
							qViewPersonale.dataAttivazione,
							qViewPersonale.dataDisattivazione,
							qViewPersonale.note,
							qViewPersonale.interno,
							qViewPersonale.codResponsabile,
							qViewPersonale.indirizzoEmail,
							qViewPersonale.nome,
							qViewPersonale.cognome,
							qViewPersonale.matricola,
							qViewPersonale.codiceFiscale,
							qViewPersonale.identficatore,
							qViewPersonale.idGradoUfficio,
							qViewPersonale.idSede,
							qViewPersonale.codSuperiore,
							qViewPersonale.codEnte,
							qViewPersonale.codVisibilita,
							null,
							StringTemplate
									.create("TO_CHAR(sysdate, 'DD/MM/YYYY')"),
							StringTemplate.create("STG_PERSONALE_SEQ.nextval")

					)).execute();

		} catch (Throwable e) {

		}

	}

	// public static void testAnnullamenti() throws DAOException {
	// Timestamp ieri,oggi;
	// ieri =
	// DateUtils.stringToTimestamp("2014-06-17 09:14:00","yyyy-MM-dd HH:mm:00");
	// oggi =
	// DateUtils.stringToTimestamp("2014-06-18 17:02:00.0","yyyy-MM-dd HH:mm:00");
	// CheckAnnullamentiEdmaFacade.checkAnnullamentiPersonale(oggi, ieri);
	// }

	public static List<Tuple> getPersonaleByCodice() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> personale = new ArrayList<Tuple>();

		QStgPersonale qstgPersonale = QStgPersonale.stgPersonale;

		String codice = "GMASTRANGELO";

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			String oggi_str = DateUtils.dateToString(new Date(),
					"yyyy-MM-dd 00:00:00");
			Date today_dt = DateUtils.stringToDate(oggi_str,
					"yyyy-MM-dd 00:00:00");

			Timestamp dataCaricamento = new Timestamp(today_dt.getTime());

			personale =

			query.from(qstgPersonale)
					.where(qstgPersonale.codice.equalsIgnoreCase(codice
							.toUpperCase()))
					.where(qstgPersonale.dataCaricamento.eq(dataCaricamento))
					.list(qstgPersonale.all());

		} catch (Exception e) {

		} finally {
			if (connection != null) {
				connection.close();
			}
		}

		return personale;
	}
}