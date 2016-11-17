package lispa.schedulers.dao.elettra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.elettra.QElettraPersonale;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElPersonale;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class StgElPersonaleDAO {
	private static Logger logger = Logger.getLogger(StgElPersonaleDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElPersonale qstgElPersonale = QStgElPersonale.stgElPersonale;

			new SQLDeleteClause(connection, dialect, qstgElPersonale).where(
					qstgElPersonale.dataCaricamento.lt(dataEsecuzioneDelete)
							.or(qstgElPersonale.dataCaricamento.gt(DateUtils
									.addDaysToTimestamp(DataEsecuzione
											.getInstance().getDataEsecuzione(),
											-1)))).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void fillStaging() throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;
		Connection connectionFonteElettra = null;
		long righeInserite = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			connectionFonteElettra = cm.getConnectionOracleFonteElettra();

			QElettraPersonale qElettraPersonale = QElettraPersonale.elettraPersonale;
			QStgElPersonale qStgElPersonale = QStgElPersonale.stgElPersonale;

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connectionFonteElettra, dialect);
			
			logger.info("Lettura tabella DM_ALM_EL_PERSONALE");
			List<Tuple> personale = query.from(qElettraPersonale).list(
					qElettraPersonale.all());
			logger.info("Fine lettura tabella DM_ALM_EL_PERSONALE");

			for (Tuple row : personale) {
				righeInserite += new SQLInsertClause(connection, dialect,
						qStgElPersonale)
						.columns(qStgElPersonale.personalePk,
								qStgElPersonale.idEdma,
								qStgElPersonale.codicePersonale,
								qStgElPersonale.dataInizioValidita,
								qStgElPersonale.dataFineValidita,
								qStgElPersonale.dataAttivazione,
								qStgElPersonale.dataDisattivazione,
								qStgElPersonale.note, qStgElPersonale.interno,
								qStgElPersonale.codiceResponsabile,
								qStgElPersonale.indirizzoEmail,
								qStgElPersonale.nome, qStgElPersonale.cognome,
								qStgElPersonale.matricola,
								qStgElPersonale.codiceFiscale,
								qStgElPersonale.identificatore,
								qStgElPersonale.idGrado,
								qStgElPersonale.idSede,
								qStgElPersonale.codiceUOSuperiore,
								qStgElPersonale.codiceEnte,
								qStgElPersonale.codiceVisibilita,
								qStgElPersonale.dataCaricamento)
						.values(StringTemplate
								.create("STG_PERSONALE_SEQ.nextval"),
								row.get(qElettraPersonale.idEdma),
								row.get(qElettraPersonale.codicePersonale),
								row.get(qElettraPersonale.dataInizioValidita),
								row.get(qElettraPersonale.dataFineValidita),
								row.get(qElettraPersonale.dataAttivazione),
								row.get(qElettraPersonale.dataDisattivazione),
								row.get(qElettraPersonale.note),
								row.get(qElettraPersonale.flagInterno),
								row.get(qElettraPersonale.codiceResponsabile),
								row.get(qElettraPersonale.indirizzoEmail),
								row.get(qElettraPersonale.nome),
								row.get(qElettraPersonale.cognome),
								row.get(qElettraPersonale.matricola),
								row.get(qElettraPersonale.codiceFiscale),
								row.get(qElettraPersonale.identificatore),
								row.get(qElettraPersonale.idGrado),
								row.get(qElettraPersonale.idSede),
								row.get(qElettraPersonale.codiceUOSuperiore),
								row.get(qElettraPersonale.codiceEnte),
								row.get(qElettraPersonale.codiceVisibilita),
								DataEsecuzione.getInstance()
										.getDataEsecuzione()).execute();
			}

			connection.commit();

			logger.debug("StgElPersonaleDAO.fillStaging - righeInserite: "
					+ righeInserite);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
			if (cm != null)
				cm.closeConnection(connectionFonteElettra);
		}
	}

	public static List<Tuple> getPersonaleByCodice(
			QStgElPersonale qstgElPersonale, String codice,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> personale = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			personale =

			query.from(qstgElPersonale)
					.where(qstgElPersonale.dataCaricamento.eq(dataEsecuzione))
					.where(qstgElPersonale.codicePersonale
							.equalsIgnoreCase(codice.toUpperCase()))
					.list(qstgElPersonale.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return personale;
	}

	public static void recoverElPersonale() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElPersonale qstgElPersonale = QStgElPersonale.stgElPersonale;

			new SQLDeleteClause(connection, dialect, qstgElPersonale).where(
					qstgElPersonale.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
