package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryRelWorkUserAss;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class SissHistoryWorkitemUserAssignedDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryWorkitemUserAssignedDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee fonteWorkitemAssignees = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee.relWorkitemUserAssignee;

	private static QSissHistoryRelWorkUserAss stgWorkitemUserAssignees = QSissHistoryRelWorkUserAss.sissHistoryRelWorkUserAss;

	public static void  fillSissHistoryWorkitemUserAssigned(
			Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision)
			throws Exception {

		ConnectionManager cm = null;
		Connection connOracle = null;
		List<Tuple> workItemUserAssignees = null;
		lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentSubterraUriMap fonteSissSubterraUriMap = lispa.schedulers.queryimplementation.fonte.sgr.siss.current.SissCurrentSubterraUriMap.urimap;
		lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryUser fonteUsers = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryUser.user;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			workItemUserAssignees = new ArrayList<>();

			connOracle.setAutoCommit(false);

			OracleTemplates dialect = new OracleTemplates();

			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType
					.values()) {

				SQLQuery query = new SQLQuery(connOracle, dialect);

				workItemUserAssignees = query.from(fonteHistoryWorkItems)
						.join(fonteWorkitemAssignees)
						.on(fonteHistoryWorkItems.cPk
								.eq(fonteWorkitemAssignees.fkWorkitem))
						.where(fonteHistoryWorkItems.cType.eq(type.toString()))
						.where(fonteHistoryWorkItems.cRev
								.gt(minRevisionByType.get(type)))
						.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
						.list(fonteWorkitemAssignees.fkUriUser,
								StringTemplate.create("(select c_rev from "
										+ fonteUsers.getSchemaName() + "."
										+ fonteUsers.getTableName() + " where "
										+ fonteUsers.getTableName()
										+ ".c_pk = fk_user) as fk_user"),
								fonteWorkitemAssignees.fkUriWorkitem,
								StringTemplate.create("(select c_rev from "
										+ fonteHistoryWorkItems.getSchemaName()
										+ "."
										+ fonteHistoryWorkItems.getTableName()
										+ " where "
										+ fonteHistoryWorkItems.getTableName()
										+ ".c_pk = fk_workitem) as fk_workitem"));

				SQLInsertClause insert = new SQLInsertClause(connOracle,
						dialect, stgWorkitemUserAssignees);

				int batchcounter = 0;

				for (Tuple row : workItemUserAssignees) {

					String fkUriUser = row
							.get(fonteWorkitemAssignees.fkUriUser) != null
									? (queryConnOracle(connOracle, dialect)
											.from(fonteSissSubterraUriMap)
											.where(fonteSissSubterraUriMap.cId
													.eq(Long.valueOf(row.get(
															fonteWorkitemAssignees.fkUriUser))))
											.count() > 0
													? queryConnOracle(
															connOracle, dialect)
																	.from(fonteSissSubterraUriMap)
																	.where(fonteSissSubterraUriMap.cId
																			.eq(Long.valueOf(
																					row.get(fonteWorkitemAssignees.fkUriUser))))
																	.list(fonteSissSubterraUriMap.cPk)
																	.get(0)
													: "")
									: "";
					String fkUser = fkUriUser + "%"
							+ (row.get(fonteWorkitemAssignees.fkUser) != null
									? row.get(fonteWorkitemAssignees.fkUser)
									: "");
					String fkUriWorkitem = row
							.get(fonteWorkitemAssignees.fkUriWorkitem) != null
									? (queryConnOracle(connOracle, dialect)
											.from(fonteSissSubterraUriMap)
											.where(fonteSissSubterraUriMap.cId
													.eq(Long.valueOf(row.get(
															fonteWorkitemAssignees.fkUriWorkitem))))
											.count() > 0
													? queryConnOracle(
															connOracle, dialect)
																	.from(fonteSissSubterraUriMap)
																	.where(fonteSissSubterraUriMap.cId
																			.eq(Long.valueOf(
																					row.get(fonteWorkitemAssignees.fkUriWorkitem))))
																	.list(fonteSissSubterraUriMap.cPk)
																	.get(0)
													: "")
									: "";
					String fkWorkitem = fkUriWorkitem + "%" + (row
							.get(fonteWorkitemAssignees.fkWorkitem) != null
									? row.get(fonteWorkitemAssignees.fkWorkitem)
									: "");

					insert.columns(
							stgWorkitemUserAssignees.fkUser,
							stgWorkitemUserAssignees.fkUriWorkitem,
							stgWorkitemUserAssignees.fkWorkitem,
							stgWorkitemUserAssignees.fkUriUser,
							stgWorkitemUserAssignees.dataCaricamento,
							stgWorkitemUserAssignees.dmalmWorkUserAssPk)
							.values(StringUtils.getMaskedValue(fkUser),
									fkUriWorkitem,
									fkWorkitem,
									StringUtils.getMaskedValue(row.get(
											fonteWorkitemAssignees.fkUriUser)),
									DataEsecuzione.getInstance()
											.getDataEsecuzione(),
									StringTemplate.create(
											"HISTORY_WORKUSERASS_SEQ.nextval"))
							.addBatch();

					batchcounter++;

					if (batchcounter % DmAlmConstants.BATCH_SIZE == 0
							&& !insert.isEmpty()) {
						insert.execute();
						connOracle.commit();
						insert = new SQLInsertClause(connOracle, dialect,
								stgWorkitemUserAssignees);
					}

				}

				if (!insert.isEmpty()) {
					insert.execute();
					connOracle.commit();
				}

			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connOracle);
		}

	}
	public static void recoverSissHistoryWIUserAssigned() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryRelWorkUserAss stgWorkitemUserAssignees = QSissHistoryRelWorkUserAss.sissHistoryRelWorkUserAss;
			// Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00",
			// "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgWorkitemUserAssignees)
					.where(stgWorkitemUserAssignees.dataCaricamento.eq(
							DataEsecuzione.getInstance().getDataEsecuzione()))
					.execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}
	public static void delete() {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			new SQLDeleteClause(connection, dialect, stgWorkitemUserAssignees)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				try {
					cm.closeConnection(connection);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					logger.error(e.getMessage());
				}
			}
		}

	}

	private static SQLQuery queryConnOracle(Connection connOracle,
			OracleTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}
}