package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProjectProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.QDmalmProjectProdotto;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class DmAlmProjectProdottoDAO {
	private static Logger logger = Logger
			.getLogger(DmAlmProjectProdottoDAO.class);
	private static QDmalmProdotto dmalmProdotto = QDmalmProdotto.dmalmProdotto;
	private static QDmalmProject dmalmProject = QDmalmProject.dmalmProject;
	private static QDmalmProjectProdotto projectProdotto = QDmalmProjectProdotto.dmalmProjectProdotto;

	public static List<Tuple> getAllRelationsToCloseCauseProductsClosed()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> relList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);

			// tutti i Prodotti chiusi con relazione a Project ancora aperta
			relList = query
					.from(dmalmProdotto)
					.join(projectProdotto)
					.on(projectProdotto.dmalmProdottoSeq
							.eq(dmalmProdotto.dmalmProdottoSeq))
					.where(dmalmProdotto.dtFineValidita.ne(DateUtils
							.setDtFineValidita9999()))
					.where(projectProdotto.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.list(projectProdotto.dmalmProjectPk,
							projectProdotto.dmalmProdottoSeq,
							projectProdotto.dtInizioValidita,
							dmalmProdotto.dtFineValidita);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return relList;
	}

	public static List<Tuple> getAllRelationsToCloseCauseProjectClosed()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> relList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);

			// tutti i Prodotti chiusi con relazione a Project ancora aperta
			relList = query
					.from(dmalmProject)
					.join(projectProdotto)
					.on(projectProdotto.dmalmProjectPk
							.eq(dmalmProject.dmalmProjectPrimaryKey))
					.where(dmalmProject.dtFineValidita.ne(DateUtils
							.setDtFineValidita9999()))
					.where(projectProdotto.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.list(projectProdotto.dmalmProjectPk,
							projectProdotto.dmalmProdottoSeq,
							projectProdotto.dtInizioValidita,
							dmalmProject.dtFineValidita);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return relList;
	}

	public static void closeRelProjectProdotto(DmalmProjectProdotto relazione)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLUpdateClause(connection, dialect, projectProdotto)
					.where(projectProdotto.dmalmProjectPk.eq(relazione
							.getDmalmProjectPk()))
					.where(projectProdotto.dmalmProdottoSeq.eq(relazione
							.getDmalmProdottoSeq()))
					.where(projectProdotto.dtInizioValidita.eq(relazione
							.getDataInizioValidita()))
					.set(projectProdotto.dtFineValidita,
							relazione.getDataFineValidita()).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertRelProjectProdotto(Timestamp dataEsecuzione,
			DmalmProjectProdotto relazione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLInsertClause(connection, dialect, projectProdotto)
					.columns(projectProdotto.dmalmProjectPk,
							projectProdotto.dmalmProdottoSeq,
							projectProdotto.dtInizioValidita,
							projectProdotto.dtFineValidita)
					.values(relazione.getDmalmProjectPk(),
							relazione.getDmalmProdottoSeq(), dataEsecuzione,
							DateUtils.setDtFineValidita9999()).execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			logger.error("insertRelProjectProdotto() - rel: "
					+ LogUtils.objectToString(relazione));

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void closeProductDuplicate() throws DAOException {
		try {
			QueryManager qm = null;

			qm = QueryManager.getInstance();

			String separator = ";";

			// chiude i prodotti legati al tappo per i quali esiste anche una
			// relazione a Project
			qm.executeMultipleStatementsFromFile(
					DmAlmConstants.SQL_CLOSE_PROJECT_PRODOTTO, separator);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
