package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_USERROLES;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lispa.schedulers.bean.target.DmalmUserRolesSgr;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmUserRoles;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.QDmalmUserRolesSgr;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Projections;

public class UserRolesSgrDAO {

	private static Logger logger = Logger.getLogger(UserRolesSgrDAO.class);

	private static QDmalmUserRolesSgr dmalmUserRoles = QDmalmUserRolesSgr.dmalmUserRolesSgr;
	private static QDmalmUserRolesSgr dmalmUserRoles2 = QDmalmUserRolesSgr.dmalmUserRolesSgr;

	private static QDmalmProject dmalmProject = QDmalmProject.dmalmProject;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	// public static List<DmalmUserRolesSgr> getAllUserRoles(
	// Timestamp dataEsecuzione, String ID, String repository) throws
	// DAOException {
	// ConnectionManager cm = null;
	// Connection connection = null;
	// PreparedStatement ps = null;
	// ResultSet rs = null;
	// DmalmUserRolesSgr bean = null;
	// List<DmalmUserRolesSgr> userRoles = new LinkedList<DmalmUserRolesSgr>();
	//
	// try {
	// cm = ConnectionManager.getInstance();
	// connection = cm.getConnectionOracle();
	//
	// String sql = QueryManager.getInstance().getQuery(SQL_USERROLES);
	//
	// ps = connection.prepareStatement(sql);
	//
	// ps.setFetchSize(200);
	// ps.setTimestamp(1, dataEsecuzione);
	// ps.setString(2, ID);
	// ps.setString(3, repository);
	//
	// rs = ps.executeQuery();
	//
	// while (rs.next()) {
	// bean = new DmalmUserRolesSgr();
	//
	// bean.setDmalmUserRolesPk(rs.getInt("DMALM_USER_ROLES_PK"));
	// bean.setOrigine(rs.getString("ORIGINE"));
	// bean.setUserid(rs.getString("USERID"));
	// bean.setRuolo(rs.getString("RUOLO"));
	// bean.setRepository(rs.getString("REPOSITORY"));
	// bean.setDtCaricamento(dataEsecuzione);
	// bean.setDtInizioValidita(DateUtils.setDtInizioValidita1900());
	// bean.setDtFineValidita(DateUtils.setDtFineValidita9999());
	// bean.setDtModifica(rs.getTimestamp("DT_MODIFICA"));
	//
	// userRoles.add(bean);
	// }
	// } catch (DAOException e) {
	// logger.error(e.getMessage(), e);
	// throw new DAOException(e);
	// } catch (Exception e) {
	// logger.error(e.getMessage(), e);
	// throw new DAOException(e);
	// } finally {
	// try {
	// if (rs != null)
	// rs.close();
	// } catch (SQLException e) {
	// }
	// try {
	// if (ps != null)
	// ps.close();
	// } catch (SQLException e) {
	// }
	//
	// if (cm != null)
	// cm.closeConnection(connection);
	// }
	//
	// return userRoles;
	// }

	public static Collection<List<DmalmUserRolesSgr>> getAllUserRolesGroupedByProjectIDandRevision(
			Timestamp dataEsecuzione, String ID, String repository)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DmalmUserRolesSgr bean = null;
		Map<String, List<DmalmUserRolesSgr>> userroles = new HashMap<String, List<DmalmUserRolesSgr>>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_USERROLES);

			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);
			ps.setTimestamp(1, dataEsecuzione);
			ps.setString(2, ID);
			ps.setString(3, repository);

			rs = ps.executeQuery();
			logger.info("userroles.sql eseguito");


			while (rs.next()) {
				bean = new DmalmUserRolesSgr();

				bean.setDmalmUserRolesPk(rs.getInt("DMALM_USER_ROLES_PK"));
				bean.setOrigine(rs.getString("ORIGINE"));
				bean.setUserid(rs.getString("USERID"));
				bean.setRuolo(rs.getString("RUOLO"));
				bean.setRepository(rs.getString("REPOSITORY"));
				bean.setDtCaricamento(dataEsecuzione);
				bean.setDtInizioValidita(DateUtils.setDtInizioValidita1900());
				bean.setDtFineValidita(DateUtils.setDtFineValidita9999());
				bean.setDtModifica(rs.getTimestamp("DT_MODIFICA"));

				List<DmalmUserRolesSgr> list = userroles.get(bean.getOrigine()
						+ "@" + bean.getRepository() + "@"
						+ bean.getDtModifica());
				if (list == null) {
					list = new ArrayList<DmalmUserRolesSgr>();
					userroles.put(
							bean.getOrigine() + "@" + bean.getRepository()
									+ "@" + bean.getDtModifica(), list);
				}

				list.add(bean);
			}
			
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		List<List<DmalmUserRolesSgr>> list = new ArrayList<List<DmalmUserRolesSgr>>(
				userroles.values());
		Collections.sort(list, new Comparator<List<DmalmUserRolesSgr>>() {

			@Override
			public int compare(List<DmalmUserRolesSgr> o1,
					List<DmalmUserRolesSgr> o2) {
				return o1.get(0).getDtModifica()
						.compareTo(o2.get(0).getDtModifica());
			}

		});

		return list;
	}

	public static List<Tuple> getDistinctProjectID() throws Exception {

		QDmAlmUserRoles stgUserRoles = QDmAlmUserRoles.dmAlmUserRoles;

		ConnectionManager cm = null;
		Connection conn = null;
		List<Tuple> IDs = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();

			IDs = new SQLQuery(conn, dialect).from(stgUserRoles).distinct()
					.list(stgUserRoles.idProject, stgUserRoles.repository);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null)
				cm.closeConnection(conn);
		}
		return IDs;

	}

	public static List<DmalmUserRolesSgr> getUserRolesByProjectID(
			String projID, String repo) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<DmalmUserRolesSgr> userRoles = new ArrayList<DmalmUserRolesSgr>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			userRoles = new SQLQuery(connection, dialect)
					.from(dmalmUserRoles)
					.where(dmalmUserRoles.origine.eq(projID))
					.where(dmalmUserRoles.repository.eq(repo))
					.where(dmalmUserRoles.dtFineValidita.in(new SQLSubQuery()
							.from(dmalmUserRoles2).list(
									dmalmUserRoles2.dtFineValidita.max())))
					.list(Projections.bean(DmalmUserRolesSgr.class,
							dmalmUserRoles.all()));

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return userRoles;

	}

	public static void insertUserRolesUpdate(List<DmalmUserRolesSgr> userRoles,
			int fkProject, Timestamp c_created) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					dmalmUserRoles);
			int i = 0;
			for (DmalmUserRolesSgr bean : userRoles) {
				bean.setDtInizioValidita(c_created);
				bean.setDmalmProjectFk01(fkProject);
				insert.columns(dmalmUserRoles.dmalmUserRolesPk,
						dmalmUserRoles.origine, dmalmUserRoles.userid,
						dmalmUserRoles.ruolo, dmalmUserRoles.dtCaricamento,
						dmalmUserRoles.dtInizioValidita,
						dmalmUserRoles.dtFineValidita,
						dmalmUserRoles.repository,
						dmalmUserRoles.dmalmProjectFk01)
						.values(bean.getDmalmUserRolesPk(), bean.getOrigine(),
								bean.getUserid(), bean.getRuolo(),
								bean.getDtCaricamento(),
								bean.getDtInizioValidita(),
								bean.getDtFineValidita(), bean.getRepository(),
								fkProject).addBatch();
				i++;
				if (i % DmAlmConstants.BATCH_SIZE == 0) {
					insert.execute();
					insert = new SQLInsertClause(connection, dialect,
							dmalmUserRoles);
				}
			}

			if (!insert.isEmpty())
				insert.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertUserRoles(List<DmalmUserRolesSgr> userRoles,
			int fkProject) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					dmalmUserRoles);
			int i = 0;
			for (DmalmUserRolesSgr bean : userRoles) {
				bean.setDmalmProjectFk01(fkProject);
				insert.columns(dmalmUserRoles.dmalmUserRolesPk,
						dmalmUserRoles.origine, dmalmUserRoles.userid,
						dmalmUserRoles.ruolo, dmalmUserRoles.dtCaricamento,
						dmalmUserRoles.dtInizioValidita,
						dmalmUserRoles.dtFineValidita,
						dmalmUserRoles.repository,
						dmalmUserRoles.dmalmProjectFk01)
						.values(bean.getDmalmUserRolesPk(), bean.getOrigine(),
								bean.getUserid(), bean.getRuolo(),
								bean.getDtCaricamento(),
								bean.getDtInizioValidita(),
								bean.getDtFineValidita(), bean.getRepository(),
								fkProject).addBatch();
				i++;
				if (i % DmAlmConstants.BATCH_SIZE == 0) {
					insert.execute();
					insert = new SQLInsertClause(connection, dialect,
							dmalmUserRoles);
				}
			}

			if (!insert.isEmpty())
				insert.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateDataFineValidita(String projID, String repo,
			Timestamp c_created) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmUserRoles)
					.where(dmalmUserRoles.origine.eq(projID))
					.where(dmalmUserRoles.repository.eq(repo))
					.where(dmalmUserRoles.dtFineValidita.in(new SQLSubQuery()
							.from(dmalmUserRoles)
							.where(dmalmUserRoles.origine.eq(projID))
							.where(dmalmUserRoles.repository.eq(repo))
							.list(dmalmUserRoles.dtFineValidita.max())))
					.set(dmalmUserRoles.dtFineValidita,
							DateUtils.addSecondsToTimestamp(c_created, -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateDataFineValiditaUserRole(
			DmalmUserRolesSgr userRole, Timestamp c_created)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmUserRoles)
					.where(dmalmUserRoles.dmalmUserRolesPk.eq(userRole
							.getDmalmUserRolesPk()))
					.set(dmalmUserRoles.dtFineValidita,
							DateUtils.addSecondsToTimestamp(c_created, -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static int getFkProject(String projId, String repo,
			Timestamp c_created) throws DAOException {

		if (projId == null || projId.equals(DmAlmConstants.GLOBAL))
			return 0;

		ConnectionManager cm = null;
		Connection connection = null;

		int res = 0;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			List<Integer> fk = new SQLQuery(connection, dialect)
					.from(dmalmProject)
					.where(dmalmProject.idProject.eq(projId))
					.where(dmalmProject.idRepository.eq(repo))
					.where(dmalmProject.dtInizioValidita.loe(c_created))
					.where(dmalmProject.dtFineValidita.goe(c_created))
					.list(dmalmProject.dmalmProjectPrimaryKey);

			if (fk == null || fk.size() == 0)
				return 0;
			else
				return fk.get(0);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return res;

	}

}