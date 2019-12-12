package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class SissHistoryWorkitemUserAssignedDAO {

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee fonteWorkitemAssignees = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee.relWorkitemUserAssignee;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryRelWorkitemUserAssignee stg_WorkitemUserAssignees = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryRelWorkitemUserAssignee.relWorkitemUserAssignee;

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
						dialect, stg_WorkitemUserAssignees);

				int batchcounter = 0;

				for (Tuple row : workItemUserAssignees) {
					insert.columns(stg_WorkitemUserAssignees.fkUser,
							stg_WorkitemUserAssignees.fkUriWorkitem,
							stg_WorkitemUserAssignees.fkWorkitem,
							stg_WorkitemUserAssignees.fkUriUser)
							.values(StringUtils.getMaskedValue(
									row.get(fonteWorkitemAssignees.fkUser)),
									row.get(fonteWorkitemAssignees.fkUriWorkitem),
									row.get(fonteWorkitemAssignees.fkWorkitem),
									StringUtils.getMaskedValue(row.get(
											fonteWorkitemAssignees.fkUriUser))
							).addBatch();

					batchcounter++;

					if (batchcounter % DmAlmConstants.BATCH_SIZE == 0
							&& !insert.isEmpty()) {
						insert.execute();
						insert = new SQLInsertClause(connOracle, dialect,
								stg_WorkitemUserAssignees);
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
}