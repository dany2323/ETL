package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryProjectgroup;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SireHistoryProjectGroupDAO {

	private static lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryProjectgroup fonteProjectGroups = lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryProjectgroup.projectgroup;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryProjectgroup stgProjectGroups = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryProjectgroup.projectgroup;

	private static Logger logger = Logger
			.getLogger(SireHistoryProjectGroupDAO.class);

	public static void fillSireHistoryProjectGroup()
			throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> projectgroups = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
			projectgroups = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query = new SQLQuery(pgConnection, dialect);

			projectgroups = query.from(fonteProjectGroups)
					.list(fonteProjectGroups.all());

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
					stgProjectGroups);
			int nRigheInserite = 0;

			for (Tuple row : projectgroups) {

				insert.columns(stgProjectGroups.cLocation, stgProjectGroups.cPk,
						stgProjectGroups.fkUriParent, stgProjectGroups.fkParent,
						stgProjectGroups.cName, stgProjectGroups.cDeleted,
						stgProjectGroups.cRev, stgProjectGroups.cUri)
						.values(row.get(fonteProjectGroups.cLocation),
								row.get(fonteProjectGroups.cPk),
								row.get(fonteProjectGroups.fkUriParent),
								row.get(fonteProjectGroups.fkParent),
								row.get(fonteProjectGroups.cName),
								row.get(fonteProjectGroups.cDeleted),
								row.get(fonteProjectGroups.cRev),
								row.get(fonteProjectGroups.cUri))
						.addBatch();

				nRigheInserite++;

				if (!insert.isEmpty()) {
					if (nRigheInserite
							% lispa.schedulers.constant.DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						connOracle.commit();
						insert = new SQLInsertClause(connOracle, dialect,
								stgProjectGroups);
					}
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
		QSireHistoryProjectgroup stgProjectGroups = QSireHistoryProjectgroup.sireHistoryProjectgroup;

		List<Long> max = new ArrayList<Long>();
		try {

			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();
			SQLTemplates dialect = new HSQLDBTemplates();
			SQLQuery query = new SQLQuery(oracle, dialect);

			max = query.from(stgProjectGroups)
					.list(stgProjectGroups.cRev.max());

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

	public static void delete() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			new SQLDeleteClause(connection, dialect, stgProjectGroups)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	private static SQLQuery queryConnOracle(Connection connOracle,
			PostgresTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}

}
