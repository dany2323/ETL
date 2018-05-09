package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryWorkitemLinked;
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

public class SireHistoryWorkitemLinkedDAO
{

	private static Logger logger = Logger.getLogger(SireHistoryWorkitemLinkedDAO.class); 
	
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryStructWorkitemLinkedworkitems fonteLinkedWorkitems = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryStructWorkitemLinkedworkitems.structWorkitemLinkedworkitems;		
	private static QSireHistoryWorkitemLinked stgLinkedWorkitems = QSireHistoryWorkitemLinked.sireHistoryWorkitemLinked;

	public static void fillSireHistoryWorkitemLinked(Map<Workitem_Type, Long> minRevisionByType, long maxRevision) throws SQLException, DAOException {
		
		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        pgConnection = null;
		List<Tuple>       linkedWorkitems = null;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
			linkedWorkitems = new ArrayList<Tuple>();
			
			connOracle.setAutoCommit(false);
			
			PostgresTemplates dialect = new PostgresTemplates()
			{ {
			    setPrintSchema(true);
			}};
			
				/*mšebela
			if(pgConnection.isClosed()) {
				if(cm != null) cm.closeConnection(pgConnection);
				pgConnection = cm.getConnectionSIREHistory();
			}*/
			
			SQLQuery query 		 = new SQLQuery(pgConnection, dialect); 
			
			linkedWorkitems =  query.from (fonteLinkedWorkitems)
                					.list(
                							fonteLinkedWorkitems.cRevision,
                							fonteLinkedWorkitems.cRole,
                							fonteLinkedWorkitems.fkUriPWorkitem,
                							StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSireHistory() + ".workitem where workitem.c_pk = fk_p_workitem) as fk_p_workitem"),
                							fonteLinkedWorkitems.fkUriWorkitem,
                							StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSireHistory() + ".workitem where workitem.c_pk = fk_workitem) as fk_workitem"),
                							fonteLinkedWorkitems.cSuspect
                						 );
			
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgLinkedWorkitems);
			int batchcounter = 0;
			
			for(Tuple row : linkedWorkitems) {
				
				Object[] vals = row.toArray();
				SQLQuery queryConnOracle = new SQLQuery(connOracle, dialect);
				String fkUriPWorkitem = queryConnOracle.from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[2].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).list(stgSubterra.cPk).get(0);
				String fkPWorkitem = fkUriPWorkitem+"%"+vals[3];
				String fkUriWorkitem = queryConnOracle.from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[4].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).list(stgSubterra.cPk).get(0);
				String fkWorkitem = fkUriWorkitem+"%"+vals[5];
				
				insert
				.columns(
        						stgLinkedWorkitems.cRevision,
        						stgLinkedWorkitems.cRole,
        						stgLinkedWorkitems.fkPWorkitem,
        						stgLinkedWorkitems.fkUriPWorkitem,
        						stgLinkedWorkitems.fkUriWorkitem,
        						stgLinkedWorkitems.fkWorkitem,
        						stgLinkedWorkitems.sSuspect,
        						stgLinkedWorkitems.dataCaricamento,
        						stgLinkedWorkitems.sireHistoryWorkLinkedPk
						)
						.values
						(		
								vals[0],
								vals[1],
								fkPWorkitem,
								fkUriPWorkitem,
								fkUriWorkitem,
								fkWorkitem,
								vals[6],
								
								
								/*
								row.get(fonteLinkedWorkitems.cRevision),
								row.get(fonteLinkedWorkitems.cRole),
								row.get(fonteLinkedWorkitems.fkPWorkitem),
								row.get(fonteLinkedWorkitems.fkUriPWorkitem),
								row.get(fonteLinkedWorkitems.fkUriWorkitem),
								row.get(fonteLinkedWorkitems.fkWorkitem),
								row.get(fonteLinkedWorkitems.cSuspect),
								*/
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
			if(cm != null) cm.closeConnection(pgConnection);
			if(cm != null) cm.closeConnection(connOracle);
		}
		
	}
	
	public static void recoverSireHistoryWorkItemLinked() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryWorkitemLinked stgLinkedWorkitems = QSireHistoryWorkitemLinked.sireHistoryWorkitemLinked;
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
			QSireHistoryWorkitemLinked stgLinkedWorkitems = QSireHistoryWorkitemLinked.sireHistoryWorkitemLinked;
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