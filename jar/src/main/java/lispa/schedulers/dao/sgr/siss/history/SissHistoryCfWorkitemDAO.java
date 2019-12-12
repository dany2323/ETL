package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryCfWorkitem;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SissHistoryCfWorkitemDAO {
	
	private static Logger logger = Logger.getLogger(SissHistoryCfWorkitemDAO.class);

	private static SissHistoryCfWorkitem fonteCFWorkItems = SissHistoryCfWorkitem.cfWorkitem;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryCfWorkitem stg_CFWorkItems = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryCfWorkitem.cfWorkitem;
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem fonteHistoryWorkItems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;

	public static void fillSissHistoryCfWorkitemByWorkitemType(
			long minRevision, long maxRevision, EnumWorkitemType w_type,
			String CFName) throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection connH2 = null;
		List<Tuple> cfWorkitem = null;

		try {
			cm = ConnectionManager.getInstance();
			cfWorkitem = new ArrayList<Tuple>();

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSISSHistory();

			connOracle.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connH2, dialect);

			cfWorkitem = query
					.from(fonteHistoryWorkItems)
					.join(fonteCFWorkItems)
					.on(fonteCFWorkItems.fkWorkitem
							.eq(fonteHistoryWorkItems.cPk))
					.where(fonteHistoryWorkItems.cType.eq(w_type.toString()))
					.where(fonteHistoryWorkItems.cRev.gt(minRevision))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.where(fonteCFWorkItems.cName.eq(CFName))
					.list(fonteCFWorkItems.all());

			logger.debug("CF_NAME: " + w_type.toString() + " " + CFName
					+ "  SIZE: " + cfWorkitem.size());

			SQLInsertClause insert = new SQLInsertClause(connOracle,
					dialect, stg_CFWorkItems);
			int count_batch = 0;

			for (Tuple row : cfWorkitem) {

				count_batch++;

				insert.columns(stg_CFWorkItems.cDateonlyValue,
						stg_CFWorkItems.cFloatValue,
						stg_CFWorkItems.cStringValue,
						stg_CFWorkItems.cTextValue,
						stg_CFWorkItems.cDateValue,
						stg_CFWorkItems.cBooleanValue, 
						stg_CFWorkItems.cName,
						stg_CFWorkItems.fkUriWorkitem,
						stg_CFWorkItems.fkWorkitem,
						stg_CFWorkItems.cLongValue,
						stg_CFWorkItems.cDurationtimeValue,
						stg_CFWorkItems.cCurrencyValue)
						.values(row.get(fonteCFWorkItems.cDateonlyValue),
								row.get(fonteCFWorkItems.cFloatValue),
								row.get(fonteCFWorkItems.cStringValue),
								row.get(fonteCFWorkItems.cTextValue),
								row.get(fonteCFWorkItems.cDateValue),
								row.get(fonteCFWorkItems.cBooleanValue),
								row.get(fonteCFWorkItems.cName),
								row.get(fonteCFWorkItems.fkUriWorkitem),
								row.get(fonteCFWorkItems.fkWorkitem),
								row.get(fonteCFWorkItems.cLongValue),
								row.get(fonteCFWorkItems.cDurationtimeValue),
								row.get(fonteCFWorkItems.cCurrencyValue))
						.addBatch();

				if (!insert.isEmpty()
						&& count_batch % DmAlmConstants.BATCH_SIZE == 0) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect, stg_CFWorkItems);
				}
			}

			if (!insert.isEmpty()) {
				insert.execute();
			}

			connOracle.commit();
			
			if (cm != null) {
				cm.closeConnection(connH2);
			}
			if (cm != null) {
				cm.closeConnection(connOracle);
			}
			
			ErrorManager.getInstance().resetCFDeadlock();
			
		} catch (Exception e) {
			Throwable cause = e;
			while (cause.getCause() != null)
				cause = cause.getCause();
			String message = cause.getMessage();
			if (StringUtils.findRegex(message, DmalmRegex.REGEXDEADLOCK)) {
				ErrorManager.getInstance().exceptionCFDeadlock(false, e, CFName);
			} else {
				ErrorManager.getInstance().exceptionOccurred(true, e);
			}

		} finally {
			if (cm != null) {
				cm.closeConnection(connH2);
			}
			if (cm != null) {
				cm.closeConnection(connOracle);
			}
		}
	}
}