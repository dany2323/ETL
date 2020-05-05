package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryRelWorkUserAss;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SissHistoryWorkitemUserAssignedDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryWorkitemUserAssignedDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee fonteWorkitemAssignees = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee.relWorkitemUserAssignee;

	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryRelWorkitemUserAssignee stgWorkitemUserAssignees = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryRelWorkitemUserAssignee.relWorkitemUserAssignee;

	public static void fillSissHistoryWorkitemUserAssigned(
			EnumWorkitemType type, Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision)
			throws Exception {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> workItemUserAssignees = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSISSHistory();
			workItemUserAssignees = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

					

				if (pgConnection.isClosed()) {
					if (cm != null)
						cm.closeConnection(pgConnection);
					pgConnection = cm.getConnectionSISSHistory();
				}

				SQLQuery query = new SQLQuery(pgConnection, dialect);

				
				workItemUserAssignees = query.from(fonteHistoryWorkItems)
						.join(fonteWorkitemAssignees)
						.on(fonteHistoryWorkItems.cPk
								.eq(fonteWorkitemAssignees.fkWorkitem))
						.where(fonteHistoryWorkItems.cType.eq(type.toString()))
						.where(fonteHistoryWorkItems.cRev
								.gt(minRevisionByType.get(type)))
						.where(fonteHistoryWorkItems.cRev.loe(maxRevision)).list(fonteWorkitemAssignees.all());

				SQLInsertClause insert = new SQLInsertClause(connOracle, dialect,
						stgWorkitemUserAssignees);

				int batchcounter = 0;

				for (Tuple row : workItemUserAssignees) {
					

					insert.columns(stgWorkitemUserAssignees.fkUser,
							stgWorkitemUserAssignees.fkUriWorkitem,
							stgWorkitemUserAssignees.fkWorkitem,
							stgWorkitemUserAssignees.fkUriUser)
							.values(row.get(fonteWorkitemAssignees.fkUser),
									row.get(fonteWorkitemAssignees.fkUriWorkitem),
									row.get(fonteWorkitemAssignees.fkWorkitem),
									row.get(fonteWorkitemAssignees.fkUriUser))
							.addBatch();

					batchcounter++;

					if (batchcounter % DmAlmConstants.BATCH_SIZE == 0
							&& !insert.isEmpty()) {
						insert.execute();
						insert = new SQLInsertClause(connOracle, dialect,
								stgWorkitemUserAssignees);
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
	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();
			new SQLDeleteClause(connection, dialect, stgWorkitemUserAssignees)
					.execute();
			connection.commit();
		} catch (Exception e) {
			throw new DAOException(e);
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