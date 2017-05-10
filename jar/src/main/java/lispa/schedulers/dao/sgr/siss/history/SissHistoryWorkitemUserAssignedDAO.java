package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryRelWorkUserAss;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SissHistoryWorkitemUserAssignedDAO
{

	private static Logger logger = Logger.getLogger(SissHistoryWorkitemUserAssignedDAO.class); 
	
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem  fonteHistoryWorkItems  = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap fonteSireSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap.urimap;
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee fonteWorkitemAssignees = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRelWorkitemUserAssignee.relWorkitemUserAssignee;
	
	private static QSissHistoryRelWorkUserAss stgWorkitemUserAssignees = QSissHistoryRelWorkUserAss.sissHistoryRelWorkUserAss;

	public static void fillSissHistoryWorkitemUserAssigned(Map<Workitem_Type, Long> minRevisionByType, long maxRevision) throws Exception {
		
		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        pgConnection = null;
		List<Tuple>       workItemUserAssignees = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSISSHistory();
			workItemUserAssignees = new ArrayList<Tuple>();
			
			connOracle.setAutoCommit(false);
			
			PostgresTemplates dialect = new PostgresTemplates()
			{ {
			    setPrintSchema(true);
			}};
			
			for(Workitem_Type type : Workitem_Type.values()) {
				
			if(pgConnection.isClosed()) {
				if(cm != null) cm.closeConnection(pgConnection);
				pgConnection = cm.getConnectionSISSHistory();
			}
			
			SQLQuery query 		 = new SQLQuery(pgConnection, dialect); 
			
			workItemUserAssignees = query.from(fonteHistoryWorkItems)
					.join(fonteWorkitemAssignees).on(fonteHistoryWorkItems.cPk.eq(fonteWorkitemAssignees.fkWorkitem))
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev.gt(minRevisionByType.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.list(
							//fonteWorkitemAssignees.all()
							
							StringTemplate.create("(SELECT a.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " a WHERE a.c_id = " + fonteWorkitemAssignees.fkUriUser + ") || '%' || c_rev as fk_user"),
							StringTemplate.create("(SELECT b.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " b WHERE b.c_id = " + fonteWorkitemAssignees.fkUriWorkitem + ") as fk_uri_workitem"),
							StringTemplate.create("(SELECT c.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " c WHERE c.c_id = " + fonteWorkitemAssignees.fkUriWorkitem + ") || '%' || c_rev as fk_workitem"),
							StringTemplate.create("(SELECT d.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " d WHERE d.c_id = " + fonteWorkitemAssignees.fkUriUser + ") as fk_uri_user")
							);
			
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgWorkitemUserAssignees);
			
			int batchcounter = 0;
			
			for(Tuple row : workItemUserAssignees) {
				Object[] vals = row.toArray();
				
				insert
				.columns(
						stgWorkitemUserAssignees.fkUser,
						stgWorkitemUserAssignees.fkUriWorkitem,
						stgWorkitemUserAssignees.fkWorkitem,
						stgWorkitemUserAssignees.fkUriUser,
						stgWorkitemUserAssignees.dataCaricamento,
						stgWorkitemUserAssignees.dmalmWorkUserAssPk
						)
						.values(								
								vals[0],
								StringUtils.getMaskedValue((String)vals[1]),
								vals[2],
								StringUtils.getMaskedValue((String)vals[3]),
								DataEsecuzione.getInstance().getDataEsecuzione(),
								 StringTemplate.create("HISTORY_WORKUSERASS_SEQ.nextval")
								)
								.addBatch();

				batchcounter++;
				
				if(batchcounter % DmAlmConstants.BATCH_SIZE == 0 && !insert.isEmpty()) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect, stgWorkitemUserAssignees);
				}
				
			}
			
			if(!insert.isEmpty()) {
				insert.execute();
			}
			
			connOracle.commit();
			}
		}
		catch(Exception e) {
ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new DAOException(e);
		}
		finally {
			if(cm != null) cm.closeConnection(pgConnection);
			if(cm != null) cm.closeConnection(connOracle);
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
//			Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00", "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgWorkitemUserAssignees).where(stgWorkitemUserAssignees.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		}
		catch(Exception e){
			logger.error(e.getMessage(), e);
			
			
			throw new DAOException(e);
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}
	
}