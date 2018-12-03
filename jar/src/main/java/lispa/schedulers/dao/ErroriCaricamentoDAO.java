package lispa.schedulers.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.QErroriCaricamentoDmAlm;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class ErroriCaricamentoDAO {

	private static Logger logger = Logger.getLogger(ErroriCaricamentoDAO.class);

//	public static void insert(String sorgente, String target, String record,
//			String motivoerrore, String flagerrore, Timestamp dataCaricamento)
//			throws DAOException, SQLException {
//
//		ConnectionManager cm = null;
//		Connection connection = null;
//
//		try {
//			cm = ConnectionManager.getInstance();
//			connection = cm.getConnectionOracle();
//
//			connection.setAutoCommit(false);
//
//			QErroriCaricamentoDmAlm qErroriCaricamento = QErroriCaricamentoDmAlm.erroriCaricamentoDmAlm;
//
//			SQLTemplates dialect = new HSQLDBTemplates();
//
//			new SQLInsertClause(connection, dialect, qErroriCaricamento)
//					.columns(qErroriCaricamento.dataCaricamento,
//							qErroriCaricamento.entitaSorgente,
//							qErroriCaricamento.entitaTarget,
//							qErroriCaricamento.recordErrore,
//							qErroriCaricamento.motivoErrore,
//							qErroriCaricamento.flagErrore,
//							qErroriCaricamento.errorePk)
//					.values(dataCaricamento, sorgente, target,
//							StringUtils.truncate(record, 4000), motivoerrore,
//							flagerrore,
//							StringTemplate.create("ERRORI_SEQ.nextval"))
//					.execute();
//
//			connection.commit();
//
//		} catch (Exception e) {
//			logger.error(e.getMessage(), e);
//
//			throw new DAOException(e);
//		} finally {
//			if (cm != null)
//				cm.closeConnection(connection);
//		}
//	}

	/*
	 * metodo utilizzato per inserire la chiave primaria delle tabelle ASM
	 * legate all'ID_PROGETTO della della DMALM_STG_MISURA
	 */
	public static void insert(String sorgente, String target, String record,
			String motivoerrore, String flagerrore, Integer pkTarget, Timestamp dataCaricamento)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QErroriCaricamentoDmAlm qErroriCaricamento = QErroriCaricamentoDmAlm.erroriCaricamentoDmAlm;

			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLInsertClause(connection, dialect, qErroriCaricamento)
					.columns(qErroriCaricamento.dataCaricamento,
							qErroriCaricamento.entitaSorgente,
							qErroriCaricamento.entitaTarget,
							qErroriCaricamento.recordErrore,
							qErroriCaricamento.motivoErrore,
							qErroriCaricamento.flagErrore,
							qErroriCaricamento.errorePk,
							qErroriCaricamento.pkTarget)
					.values(dataCaricamento, sorgente, target,
							StringUtils.truncate(record, 4000), motivoerrore,
							flagerrore,
							StringTemplate.create("ERRORI_SEQ.nextval"),
							pkTarget)
					.execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	public static void delete(Timestamp dataCaricamento) throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QErroriCaricamentoDmAlm qErroriCaricamento = QErroriCaricamentoDmAlm.erroriCaricamentoDmAlm;

			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLDeleteClause(connection, dialect, qErroriCaricamento).where(
					qErroriCaricamento.dataCaricamento.eq(dataCaricamento))
					.execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QErroriCaricamentoDmAlm qErroriCaricamento = QErroriCaricamentoDmAlm.erroriCaricamentoDmAlm;

			new SQLDeleteClause(connection, dialect, qErroriCaricamento).where(
					qErroriCaricamento.dataCaricamento.eq(date)).execute();

		} catch (Exception e) {

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void recoverErroriCaricamento() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QErroriCaricamentoDmAlm errori = QErroriCaricamentoDmAlm.erroriCaricamentoDmAlm;

			new SQLDeleteClause(connection, dialect, errori).where(
					errori.dataCaricamento.eq(DataEsecuzione.getInstance()
							.getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
}