package lispa.schedulers.dao.elettra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.elettra.QElettraFunzionalita;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElFunzionalita;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class StgElFunzionalitaDAO {
	private static Logger logger = Logger.getLogger(StgElFunzionalitaDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElFunzionalita qStgElFunzionalita = QStgElFunzionalita.stgElFunzionalita;

			new SQLDeleteClause(connection, dialect, qStgElFunzionalita).where(
					qStgElFunzionalita.dataCaricamento.lt(dataEsecuzioneDelete)
							.or(qStgElFunzionalita.dataCaricamento.gt(DateUtils
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

			QElettraFunzionalita qElettraFunzionalita = QElettraFunzionalita.elettraFunzionalita;
			QStgElFunzionalita qStgElFunzionalita = QStgElFunzionalita.stgElFunzionalita;

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connectionFonteElettra, dialect);
			
			logger.info("Lettura tabella DM_ALM_EL_FUNZIONALITA");
			List<Tuple> moduli = query.from(qElettraFunzionalita).list(
					qElettraFunzionalita.all());
			logger.info("Fine lettura tabella DM_ALM_EL_FUNZIONALITA");
			
			for (Tuple row : moduli) {
				righeInserite += new SQLInsertClause(connection, dialect,
						qStgElFunzionalita)
						.columns(qStgElFunzionalita.funzionaliaPk,
								qStgElFunzionalita.idFunzionalitaEdma,
								qStgElFunzionalita.idEdmaPadre,
								qStgElFunzionalita.idFunzionalia,
								qStgElFunzionalita.tipoOggetto,
								qStgElFunzionalita.siglaProdotto,
								qStgElFunzionalita.siglaSottosistema,
								qStgElFunzionalita.siglaModulo,
								qStgElFunzionalita.siglaFunzionalita,
								qStgElFunzionalita.nome,
								qStgElFunzionalita.descrizioneFunzionalita,
								qStgElFunzionalita.annullato,
								qStgElFunzionalita.dataAnnullamento,
								qStgElFunzionalita.categoria,
								qStgElFunzionalita.linguaggi,
								qStgElFunzionalita.tipiElaborazione,
								qStgElFunzionalita.dataCaricamento)
						.values(StringTemplate
								.create("STG_FUNZIONALITA_SEQ.nextval"),
								row.get(qElettraFunzionalita.idEdmaFunzionalitaOreste),
								row.get(qElettraFunzionalita.idEdmaPadreFunzionalitaOreste),
								row.get(qElettraFunzionalita.idFunzionalitaOreste) == null ? row
										.get(qElettraFunzionalita.idEdmaFunzionalitaOreste)
										: row.get(qElettraFunzionalita.idFunzionalitaOreste),
								row.get(qElettraFunzionalita.tipoFunzionalitaOreste),
								row.get(qElettraFunzionalita.siglaProdottoFunzionalita),
								row.get(qElettraFunzionalita.siglaSottosistemaFunzionalita),
								row.get(qElettraFunzionalita.siglaModuloFunzionalita),
								row.get(qElettraFunzionalita.siglaFunzionalitaOreste),
								row.get(qElettraFunzionalita.nomeFunzionalitaOreste),
								row.get(qElettraFunzionalita.descrizioneFunzionalitaOreste),
								row.get(qElettraFunzionalita.funzionalitaOresteAnnullato),
								row.get(qElettraFunzionalita.dfvFunzionalitaOresteAnnullato),
								row.get(qElettraFunzionalita.categoriaFunzionalita),
								row.get(qElettraFunzionalita.linguaggioProgrammazione),
								row.get(qElettraFunzionalita.tipoElaborazione),
								DataEsecuzione.getInstance()
										.getDataEsecuzione()).execute();
			}

			connection.commit();

			logger.debug("StgElFunzionalitaDAO.fillStaging - righeInserite: "
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

	public static List<Tuple> checkStatoFunzionalitaProdottoAnnullato(
			QStgElFunzionalita stgElFunzionalita, String siglaProdotto,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> el = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			el = query
					.from(stgElFunzionalita)
					.where(stgElFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(stgElFunzionalita.nome.notLike("%ANNULLATO%"))
					.where(stgElFunzionalita.siglaProdotto
							.equalsIgnoreCase(siglaProdotto))
					.list(stgElFunzionalita.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return el;
	}

	public static List<Tuple> getFunzionalitaAnnullateByProdotto(
			QStgElFunzionalita stgElFunzionalita, String siglaProdotto,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> funzionalita = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			funzionalita =

			query.from(stgElFunzionalita)
					.where(stgElFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(stgElFunzionalita.siglaProdotto.eq(siglaProdotto))
					.where(stgElFunzionalita.nome.like("%ANNULLATO%"))
					.list(stgElFunzionalita.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return funzionalita;
	}

	public static List<Tuple> checkStatoFunzionalitaModuloAnnullato(
			QStgElFunzionalita stgElFunzionalita, String siglaModulo,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> el = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			el = query
					.from(stgElFunzionalita)
					.where(stgElFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(stgElFunzionalita.nome.notLike("%ANNULLATO%"))
					.where(stgElFunzionalita.siglaModulo
							.equalsIgnoreCase(siglaModulo))
					.list(stgElFunzionalita.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return el;
	}

	public static List<Tuple> getFunzionalitaAnnullateByModulo(
			QStgElFunzionalita stgElFunzionalita, String siglaModulo,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> funzionalita = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			funzionalita =

			query.from(stgElFunzionalita)
					.where(stgElFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(stgElFunzionalita.siglaModulo.eq(siglaModulo))
					.where(stgElFunzionalita.nome.like("%ANNULLATO%"))
					.list(stgElFunzionalita.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return funzionalita;
	}

	public static List<Tuple> getDuplicateNomeFunzionalita(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		QStgElFunzionalita stgElFunzionalita = QStgElFunzionalita.stgElFunzionalita;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			moduli = query
					.from(stgElFunzionalita)
					.where(stgElFunzionalita.nome.notLike("%ANNULLATO%"))
					.where(stgElFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.groupBy(stgElFunzionalita.siglaProdotto)
					.groupBy(stgElFunzionalita.siglaModulo)
					.groupBy(stgElFunzionalita.nome)
					.having(stgElFunzionalita.nome.count().gt(1))
					.list(stgElFunzionalita.siglaProdotto,
							stgElFunzionalita.siglaModulo,
							stgElFunzionalita.nome);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return moduli;
	}

	public static List<Tuple> getNomeFunzionalitaNull(Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> moduli = new ArrayList<Tuple>();

		QStgElFunzionalita stgElFunzionalita = QStgElFunzionalita.stgElFunzionalita;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			moduli = query.from(stgElFunzionalita)
					.where(stgElFunzionalita.nome.isNull())
					.list(stgElFunzionalita.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return moduli;
	}

	public static List<Tuple> getFunzionalitaAnnullate(
			QStgElFunzionalita stgElFunzionalita, Timestamp dataEsecuzione)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> funzionalitaAnnullate = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			funzionalitaAnnullate =

			query.from(stgElFunzionalita)
					.where(stgElFunzionalita.dataCaricamento.eq(dataEsecuzione))
					.where(stgElFunzionalita.nome.like("%ANNULLATO%"))
					.list(stgElFunzionalita.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return funzionalitaAnnullate;
	}

	public static void recoverElFunzionalita() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElFunzionalita qStgElFunzionalita = QStgElFunzionalita.stgElFunzionalita;

			new SQLDeleteClause(connection, dialect, qStgElFunzionalita).where(
					qStgElFunzionalita.dataCaricamento.eq(DataEsecuzione
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
