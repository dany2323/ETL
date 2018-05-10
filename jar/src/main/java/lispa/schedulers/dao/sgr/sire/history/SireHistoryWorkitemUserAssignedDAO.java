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
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryRelWorkUserAss;
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

public class SireHistoryWorkitemUserAssignedDAO
{

	private static Logger logger = Logger.getLogger(SireHistoryWorkitemUserAssignedDAO.class);
	
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem  fonteHistoryWorkItems  = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRelWorkitemUserAssignee fonteWorkitemAssignees = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRelWorkitemUserAssignee.relWorkitemUserAssignee;
	
	private static QSireHistoryRelWorkUserAss stgWorkitemUserAssignees = QSireHistoryRelWorkUserAss.sireHistoryRelWorkUserAss;

	public static void fillSireHistoryWorkitemUserAssigned(Map<Workitem_Type, Long> minRevisionByType, long maxRevision) throws DAOException, SQLException {
		
		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        pgConnection = null;
		List<Tuple>       workItemUserAssignees = null;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
			workItemUserAssignees = new ArrayList<Tuple>();
			
			connOracle.setAutoCommit(false);
			
			PostgresTemplates dialect = new PostgresTemplates()
			{ {
			    setPrintSchema(true);
			}};
			
			for(Workitem_Type type : Workitem_Type.values()) {
				
			if(pgConnection.isClosed()) {
				if(cm != null) cm.closeConnection(pgConnection);
				pgConnection = cm.getConnectionSIREHistory();
			}
			
			SQLQuery query 		 = new SQLQuery(pgConnection, dialect); 
			
			workItemUserAssignees = query.from(fonteHistoryWorkItems)
					.join(fonteWorkitemAssignees).on(fonteHistoryWorkItems.cPk.eq(fonteWorkitemAssignees.fkWorkitem))
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev.gt(minRevisionByType.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.list(
//fonteWorkitemAssignees.all()
							fonteWorkitemAssignees.fkUriUser,
							StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSireHistory() + ".t_user where t_user.c_pk = fk_user) as fk_user"),
							fonteWorkitemAssignees.fkUriWorkitem,
							StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSireHistory() +".workitem where workitem.c_pk = fk_workitem) as fk_workitem")
							);
			
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgWorkitemUserAssignees);
			
			int batchcounter = 0;
			
			for(Tuple row : workItemUserAssignees) {
				Object[] vals = row.toArray();
				String fkUriUser = vals[0] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[0].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[0].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).list(stgSubterra.cPk).get(0) : "") : "";
				String fkUser = fkUriUser+"%"+(vals[1] != null ? vals[1].toString() : "");
				String fkUriWorkitem = vals[2] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[2].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[2].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).list(stgSubterra.cPk).get(0) : "") : "";
				String fkWorkitem = fkUriWorkitem+"%"+(vals[3] != null ? vals[3].toString() : "");
				
				insert
				.columns(
						stgWorkitemUserAssignees.fkUser,
						stgWorkitemUserAssignees.fkUriWorkitem,
						stgWorkitemUserAssignees.fkWorkitem,
						stgWorkitemUserAssignees.fkUriUser,
						stgWorkitemUserAssignees.dataCaricamento,
						stgWorkitemUserAssignees.workitemUserAssignedPK
						)
						.values(								
								StringUtils.getMaskedValue(fkUser),
								fkUriWorkitem,
								fkWorkitem,
								StringUtils.getMaskedValue(fkUriUser),
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
	public static void recoverSireHistoryWIUserAssigned() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryRelWorkUserAss stgWorkitemUserAssignees = QSireHistoryRelWorkUserAss.sireHistoryRelWorkUserAss;
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
	
	private static SQLQuery queryConnOracle(Connection connOracle, PostgresTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}
	
}