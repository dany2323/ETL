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
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryWorkitemLinked;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SissHistoryWorkitemLinkedDAO
{

	private static Logger logger = Logger.getLogger(SissHistoryWorkitemLinkedDAO.class); 	
	
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryStructWorkitemLinkedworkitems fonteLinkedWorkitems = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;
		
	private static QSissHistoryWorkitemLinked stgLinkedWorkitems = QSissHistoryWorkitemLinked.sissHistoryWorkitemLinked;

	public static void fillSissHistoryWorkitemLinked(Map<EnumWorkitemType, Long> minRevisionsByType, long maxRevision) throws Exception {
		
		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		List<Tuple>       linkedWorkitems = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			linkedWorkitems = new ArrayList<Tuple>();
			
			connOracle.setAutoCommit(false);
			
			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
			    setPrintSchema(true);
			}};
			
				
						
			SQLQuery query 		 = new SQLQuery(connOracle, dialect); 
			
			linkedWorkitems = query.from
					(fonteLinkedWorkitems)
					.list(
							fonteLinkedWorkitems.all()
							);
			
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgLinkedWorkitems);
			int batchcounter = 0;
			
			for(Tuple row : linkedWorkitems) {
				insert
				.columns(
						stgLinkedWorkitems.cRevision,
						stgLinkedWorkitems.cRole,
						stgLinkedWorkitems.fkPWorkitem,
						stgLinkedWorkitems.fkUriPWorkitem,
						stgLinkedWorkitems.fkUriWorkitem,
						stgLinkedWorkitems.fkWorkitem,
						stgLinkedWorkitems.cSuspect,
						stgLinkedWorkitems.dataCaricamento,
						stgLinkedWorkitems.dmalmWorkLinkedPk
						)
						.values(								
								row.get(fonteLinkedWorkitems.cRevision),
								row.get(fonteLinkedWorkitems.cRole),
								row.get(fonteLinkedWorkitems.fkPWorkitem),
								row.get(fonteLinkedWorkitems.fkUriPWorkitem),
								row.get(fonteLinkedWorkitems.fkUriWorkitem),
								row.get(fonteLinkedWorkitems.fkWorkitem),
								row.get(fonteLinkedWorkitems.cSuspect),
								DataEsecuzione.getInstance().getDataEsecuzione(),
								 StringTemplate.create("HISTORY_WORK_LINKED_SEQ.nextval")
								)
								.addBatch();

				batchcounter++;
				
				if(batchcounter % DmAlmConstants.BATCH_SIZE == 0 && ! insert.isEmpty()) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect, stgLinkedWorkitems);
				}
				
			}
			
			if(!insert.isEmpty()) {
				insert.execute();
			}
			
			connOracle.commit();
			
		}
		catch(Exception e) {
ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new DAOException(e);
		}
		finally {
			if(cm != null) cm.closeConnection(connOracle);
		}
		
	} 
	public static void recoverSissHistoryWorkItemLinked() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryWorkitemLinked stgLinkedWorkitems = QSissHistoryWorkitemLinked.sissHistoryWorkitemLinked;
//			Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00", "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgLinkedWorkitems).where(stgLinkedWorkitems.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
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
	
	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryWorkitemLinked stgLinkedWorkitems = QSissHistoryWorkitemLinked.sissHistoryWorkitemLinked;
//			Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00", "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgLinkedWorkitems).execute();
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