package lispa.schedulers.dao.elettra;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.fonte.elettra.QElettraUnitaOrganizzative;
import lispa.schedulers.queryimplementation.staging.elettra.QStgElUnitaOrganizzative;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.NumberUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class StgElUnitaOrganizzativeDAO {
	private static Logger logger = Logger
			.getLogger(StgElUnitaOrganizzativeDAO.class);

	public static void deleteStaging(Timestamp dataEsecuzioneDelete)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElUnitaOrganizzative qstgElUnitaOrganizzative = QStgElUnitaOrganizzative.stgElUnitaOrganizzative;

			new SQLDeleteClause(connection, dialect, qstgElUnitaOrganizzative)
					.where(qstgElUnitaOrganizzative.dataCaricamento.lt(
							dataEsecuzioneDelete).or(
							qstgElUnitaOrganizzative.dataCaricamento
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

			QElettraUnitaOrganizzative qElettraUnitaOrganizzative = QElettraUnitaOrganizzative.elettraUnitaOrganizzative;
			QStgElUnitaOrganizzative qStgElUnitaOrganizzative = QStgElUnitaOrganizzative.stgElUnitaOrganizzative;

			SQLTemplates dialect = new HSQLDBTemplates();

			SQLQuery query = new SQLQuery(connectionFonteElettra, dialect);

			List<Tuple> unitaOrganizzative = query.from(
					qElettraUnitaOrganizzative).list(
					qElettraUnitaOrganizzative.all());

			for (Tuple row : unitaOrganizzative) {
				righeInserite += new SQLInsertClause(connection, dialect,
						qStgElUnitaOrganizzative)
						.columns(
								qStgElUnitaOrganizzative.unitaOrganizzativaPk,
								qStgElUnitaOrganizzative.idEdma,
								qStgElUnitaOrganizzative.codiceArea,
								qStgElUnitaOrganizzative.dataInizioValiditaEdma,
								qStgElUnitaOrganizzative.dataFineValiditaEdma,
								qStgElUnitaOrganizzative.descrizioneArea,
								qStgElUnitaOrganizzative.dataAttivazione,
								qStgElUnitaOrganizzative.dataDisattivazione,
								qStgElUnitaOrganizzative.note,
								qStgElUnitaOrganizzative.interno,
								qStgElUnitaOrganizzative.codiceResponsabile,
								qStgElUnitaOrganizzative.indirizzoEmail,
								qStgElUnitaOrganizzative.idTipologiaUfficio,
								qStgElUnitaOrganizzative.idGradoUfficio,
								qStgElUnitaOrganizzative.idSede,
								qStgElUnitaOrganizzative.codiceUOSuperiore,
								qStgElUnitaOrganizzative.codiceEnte,
								qStgElUnitaOrganizzative.codiceVisibilita,
								qStgElUnitaOrganizzative.dataCaricamento)
						.values(StringTemplate
								.create("STG_UNITA_ORGANIZZATIVE_SEQ.nextval"),
								row.get(qElettraUnitaOrganizzative.idEdma),
								row.get(qElettraUnitaOrganizzative.codiceUnitaOrganizzativa),
								row.get(qElettraUnitaOrganizzative.dataInizioValidita),
								row.get(qElettraUnitaOrganizzative.dataFineValidita),
								row.get(qElettraUnitaOrganizzative.descrizioneUnitaOrganizzativa),
								row.get(qElettraUnitaOrganizzative.dataAttivazione),
								row.get(qElettraUnitaOrganizzative.dataDisattivazione),
								StringUtils.getMaskedValue(row.get(qElettraUnitaOrganizzative.note)),
								NumberUtils.getMaskedValue(row.get(qElettraUnitaOrganizzative.flagInterno)),
								StringUtils.getMaskedValue(row.get(qElettraUnitaOrganizzative.codiceResponsabile)),
								StringUtils.getMaskedValue(row.get(qElettraUnitaOrganizzative.indirizzoEmail)),
								NumberUtils.getMaskedValue(row.get(qElettraUnitaOrganizzative.tipologiaUfficio)),
								NumberUtils.getMaskedValue(row.get(qElettraUnitaOrganizzative.gradoUfficio)),
								NumberUtils.getMaskedValue(row.get(qElettraUnitaOrganizzative.idSede)),
								row.get(qElettraUnitaOrganizzative.codiceUOSuperiore),
								StringUtils.getMaskedValue(row.get(qElettraUnitaOrganizzative.codiceEnte)),
								row.get(qElettraUnitaOrganizzative.codiceVisibilita),
								DataEsecuzione.getInstance()
										.getDataEsecuzione()).execute();
			}

			// gestione Unita Organizzativa Lispa
			righeInserite += new SQLInsertClause(connection, dialect,
					qStgElUnitaOrganizzative)
					.columns(qStgElUnitaOrganizzative.unitaOrganizzativaPk,
							qStgElUnitaOrganizzative.idEdma,
							qStgElUnitaOrganizzative.codiceArea,
							qStgElUnitaOrganizzative.dataInizioValiditaEdma,
							qStgElUnitaOrganizzative.dataFineValiditaEdma,
							qStgElUnitaOrganizzative.descrizioneArea,
							qStgElUnitaOrganizzative.dataAttivazione,
							qStgElUnitaOrganizzative.dataDisattivazione,
							qStgElUnitaOrganizzative.note,
							qStgElUnitaOrganizzative.interno,
							qStgElUnitaOrganizzative.codiceResponsabile,
							qStgElUnitaOrganizzative.indirizzoEmail,
							qStgElUnitaOrganizzative.idTipologiaUfficio,
							qStgElUnitaOrganizzative.idGradoUfficio,
							qStgElUnitaOrganizzative.idSede,
							qStgElUnitaOrganizzative.codiceUOSuperiore,
							qStgElUnitaOrganizzative.codiceEnte,
							qStgElUnitaOrganizzative.codiceVisibilita,
							qStgElUnitaOrganizzative.dataCaricamento)
					.values(StringTemplate
							.create("STG_UNITA_ORGANIZZATIVE_SEQ.nextval"),
							DmAlmConstants.ROOT_UO, DmAlmConstants.ROOT_UO,
							DataEsecuzione.getInstance().getDataEsecuzione(),
							DateUtils.setDtFineValidita9999(),
							DmAlmConstants.ROOT_UO_DESC, null, null, null, 1,
							null, null, 0, 0, 0, DmAlmConstants.ROOT_UO,
							DmAlmConstants.ROOT_UO_CD_ENTE, null,
							DataEsecuzione.getInstance().getDataEsecuzione())
					.execute();

			connection.commit();

			logger.debug("StgElUnitaOrganizzativeDAO.fillStaging - righeInserite: "
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

	public static List<Tuple> getUnitaOrganizzativaByCodiceArea(
			QStgElUnitaOrganizzative qstgElUnitaOrganizzative,
			String codiceArea, Timestamp dataEsecuzione) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> unitaOrganizzativa = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			unitaOrganizzativa =

			query.from(qstgElUnitaOrganizzative)
					.where(qstgElUnitaOrganizzative.dataCaricamento
							.eq(dataEsecuzione))
					.where(qstgElUnitaOrganizzative.codiceArea
							.equalsIgnoreCase(codiceArea))
					.list(qstgElUnitaOrganizzative.all());

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return unitaOrganizzativa;
	}

	public static void recoverElUnitaOrganizzativa() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QStgElUnitaOrganizzative qstgElUnitaOrganizzative = QStgElUnitaOrganizzative.stgElUnitaOrganizzative;

			new SQLDeleteClause(connection, dialect, qstgElUnitaOrganizzative)
					.where(qstgElUnitaOrganizzative.dataCaricamento
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
