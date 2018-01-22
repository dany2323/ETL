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
import com.mysema.query.sql.SQLSubQuery;
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
			
//			Query 1
//			workItemUserAssignees = query.from(fonteHistoryWorkItems)
//			.join(fonteWorkitemAssignees).on(fonteHistoryWorkItems.cPk.eq(fonteWorkitemAssignees.fkWorkitem))
//			.where(fonteHistoryWorkItems.cType.eq(type.toString()))
//			.where(fonteHistoryWorkItems.cRev.gt(minRevisionByType.get(type)))
//			.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
//			.list(
//					//fonteWorkitemAssignees.all()
//					
//					StringTemplate.create("(SELECT a.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " a WHERE a.c_id = " + fonteWorkitemAssignees.fkUriUser + ") || '%' || (select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".t_user where t_user.c_pk = fk_user) as fk_user"),
//					StringTemplate.create("(SELECT b.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " b WHERE b.c_id = " + fonteWorkitemAssignees.fkUriWorkitem + ") as fk_uri_workitem"),
//					StringTemplate.create("(SELECT c.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " c WHERE c.c_id = " + fonteWorkitemAssignees.fkUriWorkitem + ") || '%' || (select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".workitem where workitem.c_pk = fk_workitem) as fk_workitem"),
//					StringTemplate.create("(SELECT d.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " d WHERE d.c_id = " + fonteWorkitemAssignees.fkUriUser + ") as fk_uri_user")
//					);
			
//			Query 2			
			workItemUserAssignees = query.from(fonteWorkitemAssignees)
			.where(fonteWorkitemAssignees.fkWorkitem
					.in(
						new SQLSubQuery().from(fonteHistoryWorkItems)
						.where(fonteHistoryWorkItems.cRev.loe(10000))
						.where(fonteHistoryWorkItems.cType.eq(type.toString()).and(fonteHistoryWorkItems.cRev.gt(minRevisionByType.get(type))))						
						.list(fonteHistoryWorkItems.cPk)
					)
				)
				.list(
						StringTemplate.create("(SELECT a.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " a WHERE a.c_id = " + fonteWorkitemAssignees.fkUriUser + ") || '%' || (select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".t_user where t_user.c_pk = fk_user) as fk_user"),
						StringTemplate.create("(SELECT b.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " b WHERE b.c_id = " + fonteWorkitemAssignees.fkUriWorkitem + ") as fk_uri_workitem"),
						StringTemplate.create("(SELECT c.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " c WHERE c.c_id = " + fonteWorkitemAssignees.fkUriWorkitem + ") || '%' || (select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".workitem where workitem.c_pk = fk_workitem) as fk_workitem"),
						StringTemplate.create("(SELECT d.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " d WHERE d.c_id = " + fonteWorkitemAssignees.fkUriUser + ") as fk_uri_user")
				);
			
//			Query 3
//			workItemUserAssignees = query.from(fonteWorkitemAssignees)
//					.where(fonteWorkitemAssignees.fkWorkitem
//						.in(
//							new SQLSubQuery().from(fonteHistoryWorkItems)
//							.where(fonteHistoryWorkItems.cRev.loe(10000))
//							.where(
//								(fonteHistoryWorkItems.cType.eq("documento")
//								.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//									.from(fonteHistoryWorkItems)
//									.where(fonteHistoryWorkItems.cType.eq("documento"))
//									.list(fonteHistoryWorkItems.cRev.min()))
//								))
//								.or(
//									(fonteHistoryWorkItems.cType.eq("testcase")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("testcase"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("pei")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("pei"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("build")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("build"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("progettoese")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("progettoese"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("fase")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("fase"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("defect")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("defect"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("release")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("release"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("sottoprogramma")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("sottoprogramma"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("programma")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("programma"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("taskit")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("taskit"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("anomalia")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("anomalia"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("anomalia_assistenza")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("anomalia_assistenza"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("release_it")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("release_it"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("sman")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("sman"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("release_ser")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("release_ser"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("drqs")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("drqs"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("dman")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("dman"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("rqd")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("rqd"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("richiesta_gestione")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("richiesta_gestione"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("srqs")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("srqs"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("task")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("task"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("classificatore_demand")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("classificatore_demand"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//								.or(
//									(fonteHistoryWorkItems.cType.eq("classificatore")
//									.and(fonteHistoryWorkItems.cRev.gtAll(new SQLSubQuery()
//										.from(fonteHistoryWorkItems)
//										.where(fonteHistoryWorkItems.cType.eq("classificatore"))
//										.list(fonteHistoryWorkItems.cRev.min()))
//									))
//								)
//							)
//							
//							.list(fonteHistoryWorkItems.cPk)
//						)
//					)
//					.list(
//							StringTemplate.create("(SELECT a.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " a WHERE a.c_id = " + fonteWorkitemAssignees.fkUriUser + ") || '%' || (select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".t_user where t_user.c_pk = fk_user) as fk_user"),
//							StringTemplate.create("(SELECT b.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " b WHERE b.c_id = " + fonteWorkitemAssignees.fkUriWorkitem + ") as fk_uri_workitem"),
//							StringTemplate.create("(SELECT c.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " c WHERE c.c_id = " + fonteWorkitemAssignees.fkUriWorkitem + ") || '%' || (select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".workitem where workitem.c_pk = fk_workitem) as fk_workitem"),
//							StringTemplate.create("(SELECT d.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSiss() + " d WHERE d.c_id = " + fonteWorkitemAssignees.fkUriUser + ") as fk_uri_user")
//					);
			
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
								StringUtils.getMaskedValue((String)vals[0]),
								vals[1],
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