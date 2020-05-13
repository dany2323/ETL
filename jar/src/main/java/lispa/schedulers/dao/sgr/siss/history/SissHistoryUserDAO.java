package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryUser;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SissHistoryUserDAO {

	private static Logger logger = Logger.getLogger(SissHistoryUserDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryUser  fonteUsers= 
			lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryUser.user;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryUser stgUsers = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryUser.user;
	public static void fillSissHistoryUser(long minRevision, long maxRevision)
			throws Exception {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> users = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSISSHistory();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(pgConnection, dialect);

			users = query.from(fonteUsers)
					.where(fonteUsers.cRev.gt(minRevision))
					.where(fonteUsers.cRev.loe(maxRevision))
					.list(fonteUsers.all());

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgUsers);
			int nRigheInserite = 0;
			for (Tuple row : users) {

				insert.columns(stgUsers.cAvatarfilename, stgUsers.cDeleted,
						stgUsers.cDisablednotifications, stgUsers.cEmail,
						stgUsers.cId, stgUsers.cInitials,
						stgUsers.cName, stgUsers.cPk, stgUsers.cRev,
						stgUsers.cUri)
						.values(row.get(fonteUsers.cAvatarfilename),
								row.get(fonteUsers.cDeleted),
								row.get(fonteUsers.cDisablednotifications),
								StringUtils.getMaskedValue(
										row.get(fonteUsers.cEmail)),
								StringUtils.getMaskedValue(
										row.get(fonteUsers.cId)),
								StringUtils.getMaskedValue(
										row.get(fonteUsers.cInitials)),
								StringUtils.getMaskedValue(
										row.get(fonteUsers.cName)),
								row.get(fonteUsers.cPk),
								row.get(fonteUsers.cRev),
								row.get(fonteUsers.cUri))
						.addBatch();
				nRigheInserite++;

				if (!insert.isEmpty()
						&& nRigheInserite == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					connOracle.commit();
					insert = new SQLInsertClause(connOracle, dialect, stgUsers);
					nRigheInserite = 0;
				}

			}
			if (!insert.isEmpty()) {
				insert.execute();
				connOracle.commit();

			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(pgConnection);
			if (cm != null)
				cm.closeConnection(connOracle);
		}

	}

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			QSissHistoryUser stgUsers = QSissHistoryUser.sissHistoryUser;
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			max = query.from(stgUsers).list(stgUsers.cRev.max());

			if (max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(oracle);
		}

		return max.get(0).longValue();
	}

	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stgUsers).execute();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(OracleConnection);
			}
		}
	}

	private static SQLQuery queryConnOracle(Connection connOracle,
			PostgresTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}

}