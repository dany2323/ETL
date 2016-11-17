package lispa.schedulers.dao.elettra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.elettra.QElettraAmbienteTecnologicoClassificatori;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElAmbienteTecnologicoClassificatori;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class StgElAmbienteTecnologicoClassificatoriDAO {
	private static Logger logger = Logger
			.getLogger(StgElAmbienteTecnologicoClassificatoriDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElAmbienteTecnologicoClassificatori qStgElAmbienteTecnologicoClassificatori = QStgElAmbienteTecnologicoClassificatori.stgElAmbienteTecnologicoClassificatori;

			new SQLDeleteClause(connection, dialect,
					qStgElAmbienteTecnologicoClassificatori)
					.where(qStgElAmbienteTecnologicoClassificatori.dataCaricamento
							.lt(dataEsecuzioneDelete)
							.or(qStgElAmbienteTecnologicoClassificatori.dataCaricamento
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
		Connection connectionFonteElettra = null;
		long righeInserite = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			connectionFonteElettra = cm.getConnectionOracleFonteElettra();

			QElettraAmbienteTecnologicoClassificatori qElettraAmbienteTecnologicoClassificatori = QElettraAmbienteTecnologicoClassificatori.elettraAmbienteTecnologicoClassificatori;
			QStgElAmbienteTecnologicoClassificatori qStgElAmbienteTecnologicoClassificatori = QStgElAmbienteTecnologicoClassificatori.stgElAmbienteTecnologicoClassificatori;

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connectionFonteElettra, dialect);
			
			logger.info("Lettura tabella DM_ALM_EL_AMBTECN_CLASSIFIC");
			List<Tuple> moduli = query.from(
					qElettraAmbienteTecnologicoClassificatori).list(
					qElettraAmbienteTecnologicoClassificatori.all());
			logger.info("Fine lettura tabella DM_ALM_EL_AMBTECN_CLASSIFIC");
			
			for (Tuple row : moduli) {
				righeInserite += new SQLInsertClause(connection, dialect,
						qStgElAmbienteTecnologicoClassificatori)
						.columns(
								qStgElAmbienteTecnologicoClassificatori.ambienteTecnologicoClassificatoriPk,
								qStgElAmbienteTecnologicoClassificatori.idAmbienteTecnologico,
								qStgElAmbienteTecnologicoClassificatori.nomeAmbienteTecnologico,
								qStgElAmbienteTecnologicoClassificatori.descrizioneAmbienteTecnologico,
								qStgElAmbienteTecnologicoClassificatori.idClassificatore,
								qStgElAmbienteTecnologicoClassificatori.nomeClassificatore,
								qStgElAmbienteTecnologicoClassificatori.descrizioneClassificatore,
								qStgElAmbienteTecnologicoClassificatori.dataCaricamento)
						.values(StringTemplate
								.create("STG_AMBTECN_CLASSIF_SEQ.nextval"),
								row.get(qElettraAmbienteTecnologicoClassificatori.idAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologicoClassificatori.nomeAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologicoClassificatori.descrizioneAmbienteTecnologico),
								row.get(qElettraAmbienteTecnologicoClassificatori.idClassificatore),
								row.get(qElettraAmbienteTecnologicoClassificatori.nomeClassificatore),
								row.get(qElettraAmbienteTecnologicoClassificatori.descrizioneClassificatore),
								DataEsecuzione.getInstance()
										.getDataEsecuzione()).execute();
			}

			connection.commit();

			logger.debug("StgElAmbienteTecnologicoClassificatoriDAO.fillStaging - righeInserite: "
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

	public static void recoverElAmbienteClass() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElAmbienteTecnologicoClassificatori qStgElAmbienteTecnologicoClassificatori = QStgElAmbienteTecnologicoClassificatori.stgElAmbienteTecnologicoClassificatori;

			new SQLDeleteClause(connection, dialect,
					qStgElAmbienteTecnologicoClassificatori).where(
					qStgElAmbienteTecnologicoClassificatori.dataCaricamento
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
