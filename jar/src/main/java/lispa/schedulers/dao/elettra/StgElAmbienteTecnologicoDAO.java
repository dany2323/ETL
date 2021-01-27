package lispa.schedulers.dao.elettra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.elettra.QElettraAmbienteTecnologico;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElAmbienteTecnologico;
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

public class StgElAmbienteTecnologicoDAO {
	private static Logger logger = Logger
			.getLogger(StgElAmbienteTecnologicoDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElAmbienteTecnologico qStgElAmbientetecnologico = QStgElAmbienteTecnologico.stgElAmbienteTecnologico;

			new SQLDeleteClause(connection, dialect, qStgElAmbientetecnologico)
					.where(qStgElAmbientetecnologico.dataCaricamento.lt(
							dataEsecuzioneDelete).or(
							qStgElAmbientetecnologico.dataCaricamento
									.gt(DateUtils.addDaysToTimestamp(
											DataEsecuzione.getInstance()
													.getDataEsecuzione(), -1))))
					.execute();

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
		long righeInserite = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			QElettraAmbienteTecnologico qElettraAmbienteTecnologico = QElettraAmbienteTecnologico.elettraAmbienteTecnologico;
			QStgElAmbienteTecnologico qStgElAmbientetecnologico = QStgElAmbienteTecnologico.stgElAmbienteTecnologico;

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> moduli = query.from(qElettraAmbienteTecnologico).list(
					qElettraAmbienteTecnologico.all());

			for (Tuple row : moduli) {
				righeInserite += new SQLInsertClause(connection, dialect,
						qStgElAmbientetecnologico)
						.columns(
								qStgElAmbientetecnologico.ambienteTecnologicoPk,
								qStgElAmbientetecnologico.idAmbienteTecnologicoEdma,
								qStgElAmbientetecnologico.idAmbienteTecnologicoEdmaPadre,
								qStgElAmbientetecnologico.idAmbienteTecnologico,
								qStgElAmbientetecnologico.siglaProdotto,
								qStgElAmbientetecnologico.siglaModulo,
								qStgElAmbientetecnologico.nome,
								qStgElAmbientetecnologico.architettura,
								qStgElAmbientetecnologico.infrastruttura,
								qStgElAmbientetecnologico.sistemaOperativo,
								qStgElAmbientetecnologico.tipoLayer,
								qStgElAmbientetecnologico.versioneSistemaOperativo,
								qStgElAmbientetecnologico.descrizioneAmbienteTecnologico,
								qStgElAmbientetecnologico.dataCaricamento)
						.values(StringTemplate
								.create("STG_AMBIENTE_TECNOLOGICO_SEQ.nextval"),
								row.get(qElettraAmbienteTecnologico.idAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.idEdmaAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.idEdmaPadreAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.siglaProdArchPadreAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.siglaModuloAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.nomeAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.classifArchitetturaRiferimentoAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.classifInfrastruttureAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.classifSistemaOperativoAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.classifTipiLayerAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.versioneSistemaOperativoAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologico.descrizioneAmbienteTecnologico),
								DataEsecuzione.getInstance()
										.getDataEsecuzione()).execute();
			}

			connection.commit();

			logger.debug("StgElAmbienteTecnologicoDAO.fillStaging - righeInserite: "
					+ righeInserite);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static List<Tuple> getDuplicateNomeAmbienteTecnologico(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> ambiente = new ArrayList<Tuple>();
		QStgElAmbienteTecnologico stgElAmbienteTecnologico = QStgElAmbienteTecnologico.stgElAmbienteTecnologico;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			ambiente =

			query.from(stgElAmbienteTecnologico)
					.where(stgElAmbienteTecnologico.nome.in(new SQLSubQuery()
							.from(stgElAmbienteTecnologico)
							.where(stgElAmbienteTecnologico.dataCaricamento
									.eq(dataEsecuzione))
							.groupBy(stgElAmbienteTecnologico.nome)
							.having(stgElAmbienteTecnologico.nome.count().gt(1))
							.list(stgElAmbienteTecnologico.nome)))
					.where(stgElAmbienteTecnologico.dataCaricamento
							.eq(dataEsecuzione))
					.list(stgElAmbienteTecnologico.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return ambiente;
	}

	public static List<Tuple> getNomeAmbienteTecnologicoNull(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> ambienti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);
			// QStgAmbienteTecnologico qstgAmbienteTecnologico =
			// QStgAmbienteTecnologico.stgAmbienteTecnologico;
			QStgElAmbienteTecnologico stgElAmbienteTecnologico = QStgElAmbienteTecnologico.stgElAmbienteTecnologico;

			ambienti =

			query.from(stgElAmbienteTecnologico)
					.where(stgElAmbienteTecnologico.dataCaricamento
							.eq(dataEsecuzione))
					.where(stgElAmbienteTecnologico.nome.isNull())
					.list(stgElAmbienteTecnologico.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return ambienti;
	}

	public static void recoverElAmbienteTec() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElAmbienteTecnologico qStgElAmbientetecnologico = QStgElAmbienteTecnologico.stgElAmbienteTecnologico;

			new SQLDeleteClause(connection, dialect, qStgElAmbientetecnologico)
					.where(qStgElAmbientetecnologico.dataCaricamento
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
