package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_USER;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmUser;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmUser;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class UserSgrCmDAO {

	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static Logger logger = Logger.getLogger(UserSgrCmDAO.class);
	private static QDmalmUser user = QDmalmUser.dmalmUser;

	public static List<DmalmUser> getAllUser(Timestamp dataEsecuzione)
			throws DAOException {
		{
			ConnectionManager cm = null;
			Connection connection = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			DmalmUser bean = null;
			List<DmalmUser> users = new LinkedList<DmalmUser>();

			try {
				cm = ConnectionManager.getInstance();
				connection = cm.getConnectionOracle();
				String sql = QueryManager.getInstance().getQuery(SQL_USER);
				ps = connection.prepareStatement(sql);

				ps.setTimestamp(1, dataEsecuzione);
				ps.setTimestamp(2, dataEsecuzione);

				rs = ps.executeQuery();

				while (rs.next()) {
					// Elabora il risultato
					bean = new DmalmUser();

					bean.setDmalmUserPk(rs.getInt("DMALM_USER_PK"));
					bean.setDataCaricamento(rs.getTimestamp("DATA_CARICAMENTO"));
					bean.setDeleted(rs.getBoolean("DELETED"));
					bean.setDisabledNotification(rs
							.getBoolean("DISABLED_NOTIFICATION"));
					bean.setIdRepository(rs.getString("ID_REPOSITORY"));
					bean.setIdUser(rs.getString("ID_USER"));
					bean.setInitialsUser(rs.getString("INITIALS_USER"));
					bean.setUserAvatarFilename(rs
							.getString("USER_AVATAR_FILENAME"));
					bean.setUserEmail(rs.getString("USER_EMAIL"));
					bean.setUserName(rs.getString("USER_NAME"));

					users.add(bean);
				}

				if (rs != null) {
					rs.close();
				}
				if (ps != null) {
					ps.close();
				}
			} catch (DAOException e) {
				ErrorManager.getInstance().exceptionOccurred(true, e);
				logger.error(e.getMessage(), e);

			} catch (Exception e) {
				ErrorManager.getInstance().exceptionOccurred(true, e);
				logger.error(e.getMessage(), e);

			} finally {
				if (cm != null) {
					cm.closeConnection(connection);
				}
			}

			return users;
		}
	}

	public static List<Tuple> getUser(DmalmUser utente) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> users = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			users = query
					.from(user)
					.where(user.idUser.eq(utente.getIdUser()))
					.where(user.idRepository.equalsIgnoreCase(utente
							.getIdRepository()))
					.where(user.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999())).list(user.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return users;
	}

	public static void insertUser(DmalmUser utente) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, user)
					.columns(user.dataCaricamento, user.deleted,
							user.disabledNotification, user.dtInizioValidita,
							user.dtFineValidita, user.dmalmUserPk,
							user.idRepository, user.idUser, user.initialsUser,
							user.userAvatarFilename, user.userEmail,
							user.userName)
					.values(utente.getDataCaricamento(),
							utente.getDeleted(),
							utente.getDisabledNotification(),
							DateUtils.setDtInizioValidita1900(), // 01/01/1900
							DateUtils.setDtFineValidita9999(), // 31/12/9999
							utente.getDmalmUserPk(), utente.getIdRepository(),
							utente.getIdUser(), utente.getInitialsUser(),
							utente.getUserAvatarFilename(),
							utente.getUserEmail(), utente.getUserName())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateDataFineValidita(Timestamp dataEsecuzione,
			DmalmUser utente) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			//
			new SQLUpdateClause(connection, dialect, user)
					.where(user.idUser.eq(utente.getIdUser()))
					.where(user.idRepository.eq(utente.getIdRepository()))
					.where(user.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(user.dtFineValidita,
							DateUtils.addSecondsToTimestamp(DateUtils
									.formatDataEsecuzione(dataEsecuzione), -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertUserUpdate(Timestamp dataEsecuzione,
			DmalmUser utente) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, user)
					.columns(user.dataCaricamento, user.deleted,
							user.disabledNotification, user.dtInizioValidita,
							user.dtFineValidita, user.dmalmUserPk,
							user.idRepository, user.idUser, user.initialsUser,
							user.userAvatarFilename, user.userEmail,
							user.userName)
					.values(utente.getDataCaricamento(),
							utente.getDeleted(),
							utente.getDisabledNotification(),
							DateUtils.formatDataEsecuzione(dataEsecuzione),
							DateUtils.setDtFineValidita9999(), // 31/12/9999
							utente.getDmalmUserPk(), utente.getIdRepository(),
							utente.getIdUser(), utente.getInitialsUser(),
							utente.getUserAvatarFilename(),
							utente.getUserEmail(), utente.getUserName())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateUser(DmalmUser utente) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, user)
					.where(user.idUser.eq(utente.getIdUser()))
					.where(user.idRepository.eq(utente.getIdRepository()))
					.where(user.dtFineValidita.eq(DateUtils
							.setDtFineValidita9999()))
					.set(user.initialsUser, utente.getInitialsUser())
					.set(user.idRepository, utente.getIdRepository())
					.set(user.userEmail, utente.getUserEmail())
					.set(user.userName, utente.getUserName())
					.set(user.deleted, utente.getDeleted())
					.set(user.userAvatarFilename,
							utente.getUserAvatarFilename())
					.set(user.disabledNotification,
							utente.getDisabledNotification()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	public static List<Tuple> getUserDistinctByIdUserUsername(QDmalmUser user) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> users = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			users = query
					.distinct()
					.from(user)
					.groupBy(user.idUser, user.userName)
							.list(user.idUser, user.userName);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return users;
	}

}
