package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryStructWorkitemLinkedworkitems;
import lispa.schedulers.utils.StringUtils;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SireHistoryWorkitemLinkedDAO {

	private static Logger logger = Logger.getLogger(SireHistoryWorkitemLinkedDAO.class); 
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryStructWorkitemLinkedworkitems fonteLinkedWorkitems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;
	private static SireHistoryStructWorkitemLinkedworkitems stgLinkedWorkitems = SireHistoryStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;

	public static void fillSireHistoryWorkitemLinked() throws SQLException, DAOException {
		
		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        connH2 = null;
		List<Tuple>       linkedWorkitems = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSIREHistory();
			linkedWorkitems = new ArrayList<Tuple>();
			
			connOracle.setAutoCommit(false);
			
			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
			    setPrintSchema(true);
			}};
			
				
			if(connH2.isClosed()) {
				cm.closeConnection(connH2);
				connH2 = cm.getConnectionSIREHistory();
			}
			
			SQLQuery query 		 = new SQLQuery(connH2, dialect); 
			
			linkedWorkitems =  query.from (fonteLinkedWorkitems)
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
        						stgLinkedWorkitems.cSuspect
						)
						.values
						(								
								row.get(fonteLinkedWorkitems.cRevision),
								row.get(fonteLinkedWorkitems.cRole),
								row.get(fonteLinkedWorkitems.fkPWorkitem),
								row.get(fonteLinkedWorkitems.fkUriPWorkitem),
								row.get(fonteLinkedWorkitems.fkUriWorkitem),
								row.get(fonteLinkedWorkitems.fkWorkitem),
								row.get(fonteLinkedWorkitems.cSuspect)
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
		}
		finally {
			cm.closeConnection(connOracle);
			cm.closeConnection(connH2);
		}
		
	}
	
	public static void delete() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates();
			new SQLDeleteClause(connection, dialect, stgLinkedWorkitems).execute();
			connection.commit();
			
		} catch(Exception e){
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			cm.closeConnection(connection);
		}
	}
}