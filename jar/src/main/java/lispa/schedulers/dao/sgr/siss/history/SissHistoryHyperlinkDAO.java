package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryHyperlink;
import lispa.schedulers.utils.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SissHistoryHyperlinkDAO {

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryHyperlink fonteHyperlink = SissHistoryHyperlink.structWorkitemHyperlinks;
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryHyperlink stg_Hyperlink = lispa.schedulers.queryimplementation.staging.sgr.siss.history.SissHistoryHyperlink.structWorkitemHyperlinks;
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem  fonteHistoryWorkItems  = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;
	
	public static void fillSissHistoryHyperlink(String type, Map<String, Long> minRevisionByType, long maxRevision) throws DAOException {
		
		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection connH2 = null;
		List<Tuple> hyperlinks = null;

		try {
			
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSISSHistory();
			hyperlinks = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			if(connH2.isClosed()) {
				cm.closeConnection(connH2);
				connH2 = cm.getConnectionSISSHistory();
			}
		
			SQLQuery query = new SQLQuery(connH2, dialect);

			hyperlinks = query.from(fonteHyperlink)
					.join(fonteHistoryWorkItems)
					.on(fonteHistoryWorkItems.cPk.eq(fonteHyperlink.fkPWorkitem))
					.where(fonteHistoryWorkItems.cType.eq(type))
					.where(fonteHistoryWorkItems.cRev.gt(minRevisionByType.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.list(fonteHyperlink.all());

			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stg_Hyperlink);
			
			int batchcounter = 0;
			
			for (Tuple row : hyperlinks) {
				batchcounter++;
				
				insert.columns(
						stg_Hyperlink.cRole,
						stg_Hyperlink.cUrl,
						stg_Hyperlink.fkPWorkitem,
						stg_Hyperlink.fkUriPWorkitem
				).values(
						row.get(fonteHyperlink.cRole),
						row.get(fonteHyperlink.cUrl),
						row.get(fonteHyperlink.fkPWorkitem),
						row.get(fonteHyperlink.fkUriPWorkitem)
				)
				.addBatch();
				
				if(batchcounter % DmAlmConstants.BATCH_SIZE == 0 && !insert.isEmpty()) {
					insert.execute();
					insert  = new SQLInsertClause(connOracle, dialect, stg_Hyperlink);
				}
				
			}
			
			if(!insert.isEmpty()) {
				insert.execute();
			}
			
			connOracle.commit();
			
		} catch (Exception e) {
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
			cm.closeConnection(connOracle);
			cm.closeConnection(connH2);
		}
	}
	
	public static void delete() throws DAOException {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stg_Hyperlink)
				.execute();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			cm.closeConnection(OracleConnection);
		}
	}
}