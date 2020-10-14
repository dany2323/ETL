package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryHyperlink;
import lispa.schedulers.utils.StringUtils;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SireHistoryHyperlinkDAO {

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryHyperlink fonteHyperlink = SireHistoryHyperlink.structWorkitemHyperlinks;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryHyperlink stg_Hyperlink = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryHyperlink.structWorkitemHyperlinks;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem  fonteHistoryWorkItems  = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;
	
	public static void fillSireHistoryHyperlink(String type, Map<String, Long> minRevisionByType, long maxRevision) throws SQLException, DAOException {
		
		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection connH2 = null;
		List<Tuple> hyperlinks = null;

		try {
			
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSIREHistory();
			hyperlinks = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};
			
			if(connH2.isClosed()) {
				cm.closeConnection(connH2);
				connH2 = cm.getConnectionSIREHistory();
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
					).addBatch();
				
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
			cm.closeConnection(connH2);
			cm.closeConnection(connOracle);
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
	
	public static void delete(String type, Map<String, Long> minRevisionByType, long maxRevision) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryWorkitem stg_HistoryWorkItems = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryWorkitem.workitem;
			SQLTemplates dialect = new HSQLDBTemplates();
			new SQLDeleteClause(connection, dialect, stg_Hyperlink)
				.where(stg_Hyperlink.fkPWorkitem.in(new SQLSubQuery()
					.from(stg_HistoryWorkItems)
					.where(stg_HistoryWorkItems.cType.eq(type))
					.where(stg_HistoryWorkItems.cRev.gt(minRevisionByType.get(type)))
					.where(stg_HistoryWorkItems.cRev.loe(maxRevision))
					.list(stg_HistoryWorkItems.cPk)))
				.execute();
				
			connection.commit();
		} catch(Exception e) {
			throw new DAOException(e);
		} finally {
			cm.closeConnection(connection);
		}
	}
}