package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProjectProdottiArchitetture;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.QDmalmProjectProdottiArchitetture;
import lispa.schedulers.queryimplementation.target.elettra.QDmalmElProdottiArchitetture;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class DmAlmProjectProdottiArchitettureDAO {
	private static Logger logger = Logger
			.getLogger(DmAlmProjectProdottoDAO.class);
	private static QDmalmElProdottiArchitetture dmalmProdottiArchitetture = QDmalmElProdottiArchitetture.qDmalmElProdottiArchitetture;
	private static QDmalmProject dmalmProject = QDmalmProject.dmalmProject;
	private static QDmalmProjectProdottiArchitetture projectProdotto = QDmalmProjectProdottiArchitetture.qDmalmProjectProdottiArchitetture;

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

			// tutti i ProdottiArchitetture chiusi con relazione a Project
			// ancora aperta
			relList = query
					.from(dmalmProdottiArchitetture)
					.join(projectProdotto)
					.on(projectProdotto.dmalmProdottoPk
							.eq(dmalmProdottiArchitetture.prodottoPk))
					.where(dmalmProdottiArchitetture.dataFineValidita
							.ne(DateUtils.getDtFineValidita9999()))
					.where(projectProdotto.dtFineValidita.eq(DateUtils
							.getDtFineValidita9999()))
					.list(projectProdotto.dmalmProjectPk,
							projectProdotto.dmalmProdottoPk,
							projectProdotto.dtInizioValidita,
							dmalmProdottiArchitetture.dataFineValidita);
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
							.getDtFineValidita9999()))
					.where(projectProdotto.dtFineValidita.eq(DateUtils
							.getDtFineValidita9999()))
					.list(projectProdotto.dmalmProjectPk,
							projectProdotto.dmalmProdottoPk,
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

	public static void closeRelProjectProdotto(
			DmalmProjectProdottiArchitetture relazione) throws DAOException {
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
					.where(projectProdotto.dmalmProdottoPk.eq(relazione
							.getDmalmProdottoPk()))
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

	public static List<Tuple> getAllProjectToLinkWithProduct()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> relList = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(connection, dialect);

			// tutti i Project di tipo Sviluppo con Sigla NOT NULL non associati
			// a Prodotto o associati al record Tappo
			relList = query
					.from(dmalmProject)
					.leftJoin(projectProdotto)
					.on(projectProdotto.dmalmProjectPk.eq(
							dmalmProject.dmalmProjectPrimaryKey).and(
							projectProdotto.dtFineValidita.eq(DateUtils
									.getDtFineValidita9999())))
					.where(dmalmProject.dtFineValidita.eq(DateUtils
							.getDtFineValidita9999()))
					.where(dmalmProject.cTemplate.eq("SVILUPPO"))
					//.where(dmalmProject.siglaProject.isNotNull())
					.where(projectProdotto.dmalmProdottoPk.isNull().or(
							(projectProdotto.dmalmProdottoPk.eq(0))))
					.list(dmalmProject.dmalmProjectPrimaryKey,
							dmalmProject.siglaProject,
							dmalmProject.nomeCompletoProject,
							projectProdotto.dmalmProjectPk,
							projectProdotto.dmalmProdottoPk,
							projectProdotto.dtInizioValidita,
							projectProdotto.dtFineValidita);
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return relList;
	}

	public static void insertRelProjectProdotto(Timestamp dataEsecuzione,
			DmalmProjectProdottiArchitetture relazione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLInsertClause(connection, dialect, projectProdotto)
					.columns(projectProdotto.dmalmProjectPk,
							projectProdotto.dmalmProdottoPk,
							projectProdotto.dtInizioValidita,
							projectProdotto.dtFineValidita)
					.values(relazione.getDmalmProjectPk(),
							relazione.getDmalmProdottoPk(), dataEsecuzione,
							DateUtils.getDtFineValidita9999()).execute();

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
					DmAlmConstants.SQL_CLOSE_PROJECT_PRODOTTI_ARCHITETTURE, separator);
		} catch (Exception e) {
			throw new DAOException(e);
		}
	}
}
