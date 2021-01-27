package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSchedeServizio;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSchedeServizioTarget;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class SchedeServizioDAO {

	private static Logger logger = Logger.getLogger(SchedeServizioDAO.class);
	private static QDmalmSchedeServizioTarget ss = QDmalmSchedeServizioTarget.dmalmSchedeServizioTarget;
	private static QDmalmSchedeServizio stg = QDmalmSchedeServizio.dmalmSchedeServizio;

	public static List<Tuple> getAllSS(Timestamp dtEsecuzione) throws Exception {
		List<Tuple> ret = new ArrayList<>();
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connection, dialect);
			ret = query.from(stg)
					.where(stg.dtCaricamento.eq(dtEsecuzione)).list(stg.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		
		return ret;
	}

	public static List<Tuple> checkExist(String id, String repo) throws Exception {
		List<Tuple> check = new ArrayList<Tuple>();
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connection, dialect);
			check = query.from(ss).orderBy(ss.dtFineValidita.desc())
					.where(ss.id.eq(id))
					.where(ss.repository.eq(repo)).list(ss.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return check;
	}

	public static void insert(Tuple t, Timestamp dataEsecuzione) throws Exception {
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			new SQLInsertClause(connection, dialect, ss)
					.columns(ss.dmalm_schedeServizio_Pk, ss.id, ss.name,
							ss.sorter, ss.dtCaricamento, ss.dtInizioValidita,
							ss.dtFineValidita, ss.repository)
					.values(t.get(stg.dmalm_schedeServizio_Pk),
							t.get(stg.id), t.get(stg.name), t.get(stg.sorter),
							t.get(stg.dtCaricamento), dataEsecuzione,
							DateUtils.getDtFineValidita9999(), t.get(stg.repository)).execute();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateFineValidita(String id, Timestamp dtChiusura, String repo) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			
			new SQLUpdateClause(connection, dialect,ss)
			.where(ss.id.eq(id)).where(ss.dtFineValidita.eq(DateUtils.getDtFineValidita9999()))
			.where(ss.repository.eq(repo))
			.set(ss.dtFineValidita, DateUtils.addSecondsToTimestamp(dtChiusura, -1)).execute();
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		
	}

	public static void insertSSUpdate(Tuple t, Timestamp dtCaricamento) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			new SQLInsertClause(connection, dialect, ss)
			.columns(ss.dmalm_schedeServizio_Pk, ss.id, ss.name,
					ss.sorter, ss.dtCaricamento, ss.dtInizioValidita,
					ss.dtFineValidita, ss.repository)
			.values(t.get(stg.dmalm_schedeServizio_Pk),
					t.get(stg.id), t.get(stg.name), t.get(stg.sorter),
					t.get(stg.dtCaricamento), dtCaricamento,
					DateUtils.getDtFineValidita9999(), t.get(stg.repository)).execute();
			
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		
	}

	public static void updateSS(Integer pk, Tuple t, Timestamp dtNewCaricamento) throws Exception{

		ConnectionManager cm = null;
		Connection connection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect,ss)
			.where(ss.dmalm_schedeServizio_Pk.eq(pk))
			.set(ss.sorter, t.get(stg.sorter))
			.set(ss.dtInizioValidita, t.get(stg.dtCaricamento))
			.set(ss.dtCaricamento, dtNewCaricamento)
			.execute();
			
		}catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	public static void delete(Timestamp dataCaricamento, String repository)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates();

			new SQLDeleteClause(connection, dialect, stg)
					.where(stg.dtCaricamento.lt(dataCaricamento)
							.or(stg.dtCaricamento.gt(DateUtils
									.addDaysToTimestamp(DataEsecuzione
											.getInstance().getDataEsecuzione(),
											-1))))
					.where(stg.repository.eq(repository)).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

}
