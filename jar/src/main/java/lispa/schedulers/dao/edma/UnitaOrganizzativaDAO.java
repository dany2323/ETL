package lispa.schedulers.dao.edma;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.edma.EdmaDmAlmUnitaOrganizzative;
import lispa.schedulers.queryimplementation.staging.edma.QStgUnitaOrganizzative;
import lispa.schedulers.queryimplementation.target.QDmalmStrutturaOrganizzativa;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class UnitaOrganizzativaDAO {

	private static Logger logger = Logger
			.getLogger(UnitaOrganizzativaDAO.class);

	public static long fillUnitaOrganizzativa() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		long righeInserite = 0;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			EdmaDmAlmUnitaOrganizzative qViewUnitaOrganizzative = EdmaDmAlmUnitaOrganizzative.dmAlmUnitaOrganizzative;
			QStgUnitaOrganizzative qStgUnitaOrganizzative = QStgUnitaOrganizzative.stgUnitaOrganizzative;

			SQLTemplates dialect = new HSQLDBTemplates(); //

			SQLQuery query = new SQLQuery(connection, dialect);

			List<Tuple> unitaOrganizzative = query
					.from(qViewUnitaOrganizzative).list(
							qViewUnitaOrganizzative.all());

			for (Tuple row : unitaOrganizzative) {

				righeInserite += new SQLInsertClause(connection, dialect,
						qStgUnitaOrganizzative)
						.columns(qStgUnitaOrganizzative.id,
								qStgUnitaOrganizzative.codice,
								qStgUnitaOrganizzative.dataInizioValidita,
								qStgUnitaOrganizzative.dataFineValidita,
								qStgUnitaOrganizzative.descrizione,
								qStgUnitaOrganizzative.dataAttivazione,
								qStgUnitaOrganizzative.dataDisattivazione,
								qStgUnitaOrganizzative.note,
								qStgUnitaOrganizzative.interno,
								qStgUnitaOrganizzative.codResponsabile,
								qStgUnitaOrganizzative.indirizzoEmail,
								qStgUnitaOrganizzative.idTipologiaUfficio,
								qStgUnitaOrganizzative.idGradoUfficio,
								qStgUnitaOrganizzative.idSede,
								qStgUnitaOrganizzative.codSuperiore,
								qStgUnitaOrganizzative.codEnte,
								qStgUnitaOrganizzative.codVisibilita,
								qStgUnitaOrganizzative.tipoPersona,
								qStgUnitaOrganizzative.dataCaricamento,
								qStgUnitaOrganizzative.dmalmStgUnitaOrgPk

						)
						.values(row.get(qViewUnitaOrganizzative.id),
								row.get(qViewUnitaOrganizzative.codice),

								row.get(qViewUnitaOrganizzative.dataInizioValidita) != null ? row
										.get(qViewUnitaOrganizzative.dataInizioValidita)
										.substring(0, 19)
										: null,
								row.get(qViewUnitaOrganizzative.dataFineValidita) != null ? row
										.get(qViewUnitaOrganizzative.dataFineValidita)
										.substring(0, 19)
										: null,
								row.get(qViewUnitaOrganizzative.descrizione),
								row.get(qViewUnitaOrganizzative.dataAttivazione) != null ? row
										.get(qViewUnitaOrganizzative.dataAttivazione)
										.substring(0, 19)
										: null,
								row.get(qViewUnitaOrganizzative.dataDisattivazione) != null ? row
										.get(qViewUnitaOrganizzative.dataDisattivazione)
										.substring(0, 19)
										: null,
								row.get(qViewUnitaOrganizzative.note),
								row.get(qViewUnitaOrganizzative.interno),
								row.get(qViewUnitaOrganizzative.codResponsabile),
								row.get(qViewUnitaOrganizzative.indirizzoEmail),
								row.get(qViewUnitaOrganizzative.idTipologiaUfficio),
								row.get(qViewUnitaOrganizzative.idGradoUfficio),
								row.get(qViewUnitaOrganizzative.idSede),
								row.get(qViewUnitaOrganizzative.codSuperiore),
								row.get(qViewUnitaOrganizzative.codEnte),
								row.get(qViewUnitaOrganizzative.codVisibilita),
								null,
								DataEsecuzione.getInstance()
										.getDataEsecuzione(),
								StringTemplate
										.create("STG_UNITA_ORGANIZZATIVE_SEQ.nextval"))
						.execute();
			}

			connection.commit();

		} catch (Exception e) {
			lispa.schedulers.manager.ErrorManager.getInstance()
					.exceptionOccurred(true, e);

			righeInserite = 0;
			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return righeInserite;

	}

	public static List<Tuple> getUnitaOrganizzativaByCodiceArea(
			QStgUnitaOrganizzative qstgUnitaOrganizzative, String codiceArea,
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> unitaOrganizzativa = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			unitaOrganizzativa = query.from(qstgUnitaOrganizzative)
					.where(qstgUnitaOrganizzative.dataCaricamento
							.eq(dataEsecuzione))
					.where(qstgUnitaOrganizzative.codice
							.equalsIgnoreCase(codiceArea))
					.list(qstgUnitaOrganizzative.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return unitaOrganizzativa;
	}

	public static void delete(Timestamp dataEsecuzioneDelete) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgUnitaOrganizzative qstgUnitaOrganizzative = QStgUnitaOrganizzative.stgUnitaOrganizzative;

			new SQLDeleteClause(connection, dialect, qstgUnitaOrganizzative)
					.where(qstgUnitaOrganizzative.dataCaricamento.lt(
							dataEsecuzioneDelete).or(
							qstgUnitaOrganizzative.dataCaricamento.gt(DateUtils
									.addDaysToTimestamp(DataEsecuzione
											.getInstance().getDataEsecuzione(),
											-1)))).execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgUnitaOrganizzative qstgUnitaOrganizzative = QStgUnitaOrganizzative.stgUnitaOrganizzative;

			new SQLDeleteClause(connection, dialect, qstgUnitaOrganizzative)
					.where(qstgUnitaOrganizzative.dataCaricamento.eq(date))
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverUO() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgUnitaOrganizzative qstgUnitaOrganizzative = QStgUnitaOrganizzative.stgUnitaOrganizzative;

			new SQLDeleteClause(connection, dialect, qstgUnitaOrganizzative)
					.where(qstgUnitaOrganizzative.dataCaricamento
							.eq(DataEsecuzione.getInstance()
									.getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static long getFK_UO_DemandbyProjectName(String projectCName,
			Timestamp data_modifica) {
		ConnectionManager cm = null;
		Connection connOracle = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmStrutturaOrganizzativa qDmalmStrutturaOrganizzativa = QDmalmStrutturaOrganizzativa.dmalmStrutturaOrganizzativa;

			String[] splittedCName = projectCName.split("\\.");
			String uoCode = splittedCName[0];
			uoCode = DmAlmConstants.UO_SUFFIX + uoCode;

			SQLQuery queryUo = new SQLQuery(connOracle, dialect);
			List<Integer> uoPks = queryUo
					.from(qDmalmStrutturaOrganizzativa)
					.where(qDmalmStrutturaOrganizzativa.cdArea.eq(uoCode))
					.where(qDmalmStrutturaOrganizzativa.dtInizioValidita
							.before(data_modifica))
					.where(qDmalmStrutturaOrganizzativa.dtFineValidita
							.after(data_modifica))
					.list(qDmalmStrutturaOrganizzativa.dmalmStrutturaOrgPk);

			if (uoPks != null && uoPks.size() != 0) {
				return (long) uoPks.get(0);
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	public static long getFK_UO_ITbyProjectName(String projectCName,
			Timestamp data_modifica) {
		ConnectionManager cm = null;
		Connection connOracle = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmStrutturaOrganizzativa qDmalmStrutturaOrganizzativa = QDmalmStrutturaOrganizzativa.dmalmStrutturaOrganizzativa;
			String uoCode = DmAlmConstants.UO_IT;
			SQLQuery queryUo = new SQLQuery(connOracle, dialect);
			List<Integer> uoPks = queryUo
					.from(qDmalmStrutturaOrganizzativa)
					.where(qDmalmStrutturaOrganizzativa.cdArea.eq(uoCode))
					.where(qDmalmStrutturaOrganizzativa.dtInizioValidita
							.before(data_modifica))
					.where(qDmalmStrutturaOrganizzativa.dtFineValidita
							.after(data_modifica))
					.list(qDmalmStrutturaOrganizzativa.dmalmStrutturaOrgPk);
			if (uoPks != null && uoPks.size() != 0)
				return (long) uoPks.get(0);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return 0;
	}

	public static long getFK_UO_AssistenzabyProjectName(String projectCName,
			Timestamp data_modifica) {
		ConnectionManager cm = null;
		Connection connOracle = null;
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmStrutturaOrganizzativa qDmalmStrutturaOrganizzativa = QDmalmStrutturaOrganizzativa.dmalmStrutturaOrganizzativa;
			String[] splittedCName = projectCName.split("Assistenza.");
			String uoCode = splittedCName[splittedCName.length - 1];
			uoCode = DmAlmConstants.UO_SUFFIX + uoCode;
			SQLQuery queryUo = new SQLQuery(connOracle, dialect);
			List<Integer> uoPks = queryUo
					.from(qDmalmStrutturaOrganizzativa)
					.where(qDmalmStrutturaOrganizzativa.cdArea.eq(uoCode))
					.where(qDmalmStrutturaOrganizzativa.dtInizioValidita
							.before(data_modifica))
					.where(qDmalmStrutturaOrganizzativa.dtFineValidita
							.after(data_modifica))
					.list(qDmalmStrutturaOrganizzativa.dmalmStrutturaOrgPk);
			if (uoPks != null && uoPks.size() != 0) {
				return (long) uoPks.get(0);
			}

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return 0;
	}
}