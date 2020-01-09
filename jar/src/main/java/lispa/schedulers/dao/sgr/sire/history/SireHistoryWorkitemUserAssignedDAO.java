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
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SireHistoryWorkitemUserAssignedDAO {

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem  fonteHistoryWorkItems  = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRelWorkitemUserAssignee fonteWorkitemAssignees = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRelWorkitemUserAssignee.relWorkitemUserAssignee;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryRelWorkitemUserAssignee stg_WorkitemUserAssignees = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryRelWorkitemUserAssignee.relWorkitemUserAssignee;

	public static void fillSireHistoryWorkitemUserAssigned(EnumWorkitemType type, Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision) throws DAOException, SQLException {
		
		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        connH2 = null;
		List<Tuple>       workItemUserAssignees = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSIREHistory();
			workItemUserAssignees = new ArrayList<Tuple>();
			
			connOracle.setAutoCommit(false);
			
			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
			    setPrintSchema(true);
			}};
			
			if(connH2.isClosed()) {
				if(cm != null) cm.closeConnection(connH2);
				connH2 = cm.getConnectionSIREHistory();
			}
			
			SQLQuery query 		 = new SQLQuery(connH2, dialect); 
			
			workItemUserAssignees = query.from(fonteHistoryWorkItems)
					.join(fonteWorkitemAssignees).on(fonteHistoryWorkItems.cPk.eq(fonteWorkitemAssignees.fkWorkitem))
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev.gt(minRevisionByType.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.list(
							fonteWorkitemAssignees.all()
							);
			
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stg_WorkitemUserAssignees);
			
			int batchcounter = 0;
			
			for(Tuple row : workItemUserAssignees) {
				insert
				.columns(
						stg_WorkitemUserAssignees.fkUser,
						stg_WorkitemUserAssignees.fkUriWorkitem,
						stg_WorkitemUserAssignees.fkWorkitem,
						stg_WorkitemUserAssignees.fkUriUser
				).values(								
						StringUtils.getMaskedValue(row.get(fonteWorkitemAssignees.fkUser)),
						row.get(fonteWorkitemAssignees.fkUriWorkitem),
						row.get(fonteWorkitemAssignees.fkWorkitem),
						StringUtils.getMaskedValue(row.get(fonteWorkitemAssignees.fkUriUser))
				).addBatch();
				
				batchcounter++;
				
				if(batchcounter % DmAlmConstants.BATCH_SIZE == 0 && !insert.isEmpty()) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect, stg_WorkitemUserAssignees);
				}
			}
			
			if(!insert.isEmpty()) {
				insert.execute();
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
			if(cm != null) cm.closeConnection(connH2);
			if(cm != null) cm.closeConnection(connOracle);
		}
	}
	
	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates();
			new SQLDeleteClause(connection, dialect, stg_WorkitemUserAssignees).execute();
			connection.commit();
		} catch(Exception e) {
			throw new DAOException(e);
		} finally {
			if(cm != null) cm.closeConnection(connection);
		}

	}
}