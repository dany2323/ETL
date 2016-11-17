package lispa.schedulers.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmUserRoles;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class UserRolesDAO {

	private static QDmAlmUserRoles qDmAlmUserRoles = QDmAlmUserRoles.dmAlmUserRoles;

	public static long getMinRevision(String projID, String repo)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<Long> lista = new ArrayList<Long>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();

			lista = new SQLQuery(connection, dialect).from(qDmAlmUserRoles)
					.where(qDmAlmUserRoles.idProject.eq(projID))
					.where(qDmAlmUserRoles.repository.eq(repo))
					.list(qDmAlmUserRoles.revision.max());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		if (lista == null || lista.size() == 0 || lista.get(0) == null)
			return 0;
		return lista.get(0).longValue();

	}

	public static long getMinRevisionGlobal(String repo)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<Long> lista = new ArrayList<Long>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();

			lista = new SQLQuery(connection, dialect).from(qDmAlmUserRoles)
					.where(qDmAlmUserRoles.repository.eq(repo))
					.list(qDmAlmUserRoles.revision.max());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		if (lista == null || lista.size() == 0 || lista.get(0) == null)
			return 0;
		return lista.get(0).longValue();

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

			new SQLDeleteClause(connection, dialect, qDmAlmUserRoles)
					.where(qDmAlmUserRoles.repository
							.equalsIgnoreCase(repository))
					.where(qDmAlmUserRoles.dataCaricamento.lt(dataCaricamento)
							.or(qDmAlmUserRoles.dataCaricamento.gt(DateUtils
									.addDaysToTimestamp(DataEsecuzione
											.getInstance().getDataEsecuzione(),
											-1)))).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

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

			QDmAlmUserRoles qDmAlmUserRoles = QDmAlmUserRoles.dmAlmUserRoles;

			new SQLDeleteClause(connection, dialect, qDmAlmUserRoles).where(
					qDmAlmUserRoles.dataCaricamento.eq(date)).execute();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	/**
	 * Estraggo la LISTAGG dei Service Manager per ogni storicizzazione di un
	 * project per il più recente blocco di userRoles valido
	 * 
	 * @param projectPk
	 * @param repository
	 * @param dataModifica
	 * @return
	 * @throws Exception
	 * @throws DAOException
	 */
	public static String getServiceManager(String projectPk, String repository,
			Timestamp dataModifica) throws Exception, DAOException

	{

		if (projectPk == null)
			return null;

		ConnectionManager cm = null;
		Connection connection = null;

		List<String> serviceManagers = new ArrayList<String>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query = new SQLQuery(connection, dialect);

			QDmAlmUserRoles qDmAlmUserRoles = QDmAlmUserRoles.dmAlmUserRoles;
			QDmAlmUserRoles qDmAlmUserRoles2 = QDmAlmUserRoles.dmAlmUserRoles;
			serviceManagers = query
					.from(qDmAlmUserRoles)
					.distinct()
					.where(qDmAlmUserRoles.idProject
							.equalsIgnoreCase(projectPk))
					.where(qDmAlmUserRoles.repository.eq(repository))
					.where(qDmAlmUserRoles.dataModifica.in(new SQLSubQuery()
							.from(qDmAlmUserRoles2)
							.where(qDmAlmUserRoles2.idProject
									.equalsIgnoreCase(projectPk))
							.where(qDmAlmUserRoles2.repository.eq(repository))
							.where(qDmAlmUserRoles2.dataModifica
									.lt(dataModifica))
							.list(qDmAlmUserRoles2.dataModifica.max())))

					.where(qDmAlmUserRoles.ruolo.eq("SM"))
					.list(qDmAlmUserRoles.utente);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return StringUtils.ListToString(serviceManagers);
	}

}