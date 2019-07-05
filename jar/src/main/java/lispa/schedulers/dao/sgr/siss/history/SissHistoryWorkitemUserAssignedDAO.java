package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
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

	public static void fillSissHistoryWorkitemUserAssigned(
			Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision)
			throws Exception {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection connH2 = null;
		List<Tuple> workItemUserAssignees = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSISSHistory();
			workItemUserAssignees = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType
					.values()) {

				if (connH2.isClosed()) {
					if (cm != null)
						cm.closeConnection(connH2);
					connH2 = cm.getConnectionSISSHistory();
				}

				SQLQuery query = new SQLQuery(connH2, dialect);

				workItemUserAssignees = query.from(fonteHistoryWorkItems)
						.join(fonteWorkitemAssignees)
						.on(fonteHistoryWorkItems.cPk
								.eq(fonteWorkitemAssignees.fkWorkitem))
						.where(fonteHistoryWorkItems.cType.eq(type.toString()))
						.where(fonteHistoryWorkItems.cRev
								.gt(minRevisionByType.get(type)))
						.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
						.list(fonteWorkitemAssignees.all());

				SQLInsertClause insert = new SQLInsertClause(connOracle,
						dialect, stgWorkitemUserAssignees);

				int batchcounter = 0;

				for (Tuple row : workItemUserAssignees) {
					insert.columns(stgWorkitemUserAssignees.fkUser,
							stgWorkitemUserAssignees.fkUriWorkitem,
							stgWorkitemUserAssignees.fkWorkitem,
							stgWorkitemUserAssignees.fkUriUser,
							stgWorkitemUserAssignees.dataCaricamento,
							stgWorkitemUserAssignees.dmalmWorkUserAssPk)
							.values(StringUtils.getMaskedValue(
									row.get(fonteWorkitemAssignees.fkUser)),
									row.get(fonteWorkitemAssignees.fkUriWorkitem),
									row.get(fonteWorkitemAssignees.fkWorkitem),
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
						insert = new SQLInsertClause(connOracle, dialect,
								stgWorkitemUserAssignees);
					}

				}

				if (!insert.isEmpty()) {
					insert.execute();
				}

				connOracle.commit();
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connH2);
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

}