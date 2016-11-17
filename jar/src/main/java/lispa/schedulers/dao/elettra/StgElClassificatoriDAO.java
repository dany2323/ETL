package lispa.schedulers.dao.elettra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.elettra.QElettraClassificatori;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElClassificatori;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class StgElClassificatoriDAO {
	private static Logger logger = Logger
			.getLogger(StgElClassificatoriDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElClassificatori qStgElClassificatori = QStgElClassificatori.stgElClassificatori;

			new SQLDeleteClause(connection, dialect, qStgElClassificatori)
					.where(qStgElClassificatori.dataCaricamento.lt(
							dataEsecuzioneDelete).or(
							qStgElClassificatori.dataCaricamento.gt(DateUtils
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

			QElettraClassificatori qElettraClassificatori = QElettraClassificatori.elettraClassificatori;
			QStgElClassificatori qStgElClassificatori = QStgElClassificatori.stgElClassificatori;

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connectionFonteElettra, dialect);
			
			logger.info("Lettura tabella DM_ALM_EL_CLASSIFICATORI");
			List<Tuple> classificatori = query.from(qElettraClassificatori)
					.list(qElettraClassificatori.all());
			logger.info("Fine lettura tabella DM_ALM_EL_CLASSIFICATORI");

			for (Tuple row : classificatori) {
				righeInserite += new SQLInsertClause(connection, dialect,
						qStgElClassificatori)
						.columns(qStgElClassificatori.classificatorePk,
								qStgElClassificatori.idClassificatore,
								qStgElClassificatori.codiceClassificatore,
								qStgElClassificatori.tipoClassificatore,
								qStgElClassificatori.dataCaricamento)
						.values(StringTemplate
								.create("STG_CLASSIFICATORI_SEQ.nextval"),
								row.get(qElettraClassificatori.idClassificatore),
								row.get(qElettraClassificatori.codiceClassificatore),
								row.get(qElettraClassificatori.tipoClassificatore),
								DataEsecuzione.getInstance()
										.getDataEsecuzione()).execute();
			}

			connection.commit();

			logger.debug("StgElClassificatoriDAO.fillStaging - righeInserite: "
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

	public static List<Tuple> getDuplicateCodiciClassificatori(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> classificatori = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgElClassificatori stgElClassificatori = QStgElClassificatori.stgElClassificatori;

			classificatori =

			query.from(stgElClassificatori)
					.where(stgElClassificatori.dataCaricamento
							.eq(dataEsecuzione))
					.where(stgElClassificatori.codiceClassificatore.in(new SQLSubQuery()
							.from(stgElClassificatori)
							.where(stgElClassificatori.dataCaricamento
									.eq(dataEsecuzione))
							.where(stgElClassificatori.codiceClassificatore
									.notLike("#ANNULLATO%"))
							.groupBy(stgElClassificatori.tipoClassificatore,
									stgElClassificatori.codiceClassificatore)
							.having(stgElClassificatori.codiceClassificatore
									.count().gt(1))
							.list(stgElClassificatori.codiceClassificatore)))
					.list(stgElClassificatori.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return classificatori;
	}

	public static List<Tuple> getElettraCodiciClassificatoriNull(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> classificatori = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			QStgElClassificatori stgElClassificatori = QStgElClassificatori.stgElClassificatori;

			classificatori =

			query.from(stgElClassificatori)
					.where(stgElClassificatori.codiceClassificatore.isNull())
					.where(stgElClassificatori.dataCaricamento
							.eq(dataEsecuzione))
					.list(stgElClassificatori.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return classificatori;
	}

	public static void recoverElClassificatori() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElClassificatori qStgElClassificatori = QStgElClassificatori.stgElClassificatori;

			new SQLDeleteClause(connection, dialect, qStgElClassificatori)
					.where(qStgElClassificatori.dataCaricamento
							.eq(DataEsecuzione.getInstance()
									.getDataEsecuzione())).execute();
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
