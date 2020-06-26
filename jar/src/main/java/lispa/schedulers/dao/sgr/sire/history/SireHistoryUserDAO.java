package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryUser;
import lispa.schedulers.utils.StringUtils;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SireHistoryUserDAO {

	private static Logger logger = Logger.getLogger(SireHistoryUserDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryUser  fonteUsers= 
			lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryUser.user;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryUser stg_Users = 
			lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryUser.user;

	public static void fillSireHistoryUser(long minRevision, long maxRevision) throws SQLException, DAOException {

		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        connH2 = null;
		List<Tuple>       users = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSIREHistory();
			users = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
				setPrintSchema(true);
			}};

			SQLQuery query 		 = new SQLQuery(connH2, dialect); 

			users = query.from(fonteUsers)
					.where(fonteUsers.cRev.gt(minRevision))
					.where(fonteUsers.cRev.loe(maxRevision))
					.list(
							fonteUsers.all()
							);

			for(Tuple row : users) {
				new SQLInsertClause(connOracle, dialect, stg_Users)
				.columns(
						stg_Users.cAvatarfilename,
						stg_Users.cDeleted,
						stg_Users.cDisablednotifications,
						stg_Users.cEmail,
						stg_Users.cId,
						stg_Users.cInitials,
						stg_Users.cIsLocal,
						stg_Users.cName,
						stg_Users.cPk,
						stg_Users.cRev,
						stg_Users.cUri
						)
						.values(								
								row.get(fonteUsers.cAvatarfilename),
								row.get(fonteUsers.cDeleted),
								row.get(fonteUsers.cDisablednotifications),
								StringUtils.getMaskedValue(row.get(fonteUsers.cEmail)),
								StringUtils.getMaskedValue(row.get(fonteUsers.cId)),
								row.get(fonteUsers.cInitials),
								row.get(fonteUsers.cIsLocal),
								StringUtils.getMaskedValue(row.get(fonteUsers.cName)),
								StringUtils.getMaskedValue(row.get(fonteUsers.cPk)),
								row.get(fonteUsers.cRev),
								StringUtils.getMaskedValue(row.get(fonteUsers.cUri))
							).execute();
			}
			connOracle.commit();
		} catch(Exception e) {
			Throwable cause = e;
			while (cause.getCause() != null)
				cause = cause.getCause();
			String message = cause.getMessage();
			if (StringUtils.findRegex(message, DmalmRegex.REGEXDEADLOCK) || StringUtils.findRegex(message, DmalmRegex.REGEXLOCKTABLE)) {
				ErrorManager.getInstance().exceptionDeadlock(false, e);
			} else {
				ErrorManager.getInstance().exceptionOccurred(true, e);
			}
		} finally {
			cm.closeConnection(connH2);
			cm.closeConnection(connOracle);
		}
	} 

	public static long getMinRevision() throws DAOException {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		QSireHistoryUser stg_Users = QSireHistoryUser.sireHistoryUser;
		try{

			cm 	   = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			SQLTemplates dialect 				 = new HSQLDBTemplates();
			SQLQuery query 						 = new SQLQuery(oracle, dialect); 

			max = query.from(stg_Users).list(stg_Users.cRev.max());

			if(max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			cm.closeConnection(oracle);
		}

		return max.get(0).longValue();
	}
	
	public static void delete() throws DAOException {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stg_Users)
				.execute();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		} finally {
			cm.closeConnection(OracleConnection);
		}
	}
	
	public static void delete(long minRevision, long maxRevision) throws DAOException {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stg_Users)
				.where(stg_Users.cRev.gt(minRevision))
				.where(stg_Users.cRev.loe(maxRevision))
				.execute();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		} finally {
			cm.closeConnection(OracleConnection);
		}
	}
}