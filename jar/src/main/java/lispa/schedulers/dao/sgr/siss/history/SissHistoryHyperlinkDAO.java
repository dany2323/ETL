package lispa.schedulers.dao.sgr.siss.history;

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
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryHyperlink;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryHyperlink;
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

public class SissHistoryHyperlinkDAO {

	private static Logger logger = Logger
			.getLogger(SissHistoryHyperlinkDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryHyperlink fonteHyperlink = SissHistoryHyperlink.structWorkitemHyperlinks;
	
	private static lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryHyperlink stgHyperlink = QSissHistoryHyperlink.dmalmSissHistoryHyperlink;
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem  fonteHistoryWorkItems  = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem.workitem;
	
	public static void fillSissHistoryHyperlink(Map<Workitem_Type, Long> minRevisionByType, long maxRevision) throws SQLException, DAOException {
		
		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> hyperlinks = null;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;

		try {
			
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSISSHistory();
			hyperlinks = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates() {
				{
					setPrintSchema(true);
				}
			};

			for(Workitem_Type type : Workitem_Type.values()) {
				
				if(pgConnection.isClosed()) {
					if(cm != null) cm.closeConnection(pgConnection);
					pgConnection = cm.getConnectionSISSHistory();
				}
				logger.info("Lettura hyperlink dei Work Item di tipo "+type.toString());
				
				SQLQuery query = new SQLQuery(pgConnection, dialect);
	
				hyperlinks = query.from(fonteHyperlink)
						.join(fonteHistoryWorkItems)
						.on(fonteHistoryWorkItems.cPk.eq(fonteHyperlink.fkPWorkitem))
						.where(fonteHistoryWorkItems.cType.eq(type.toString()))
						.where(fonteHistoryWorkItems.cRev.gt(minRevisionByType.get(type)))
						.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
						.list(
								//fonteHyperlink.all()
								
								fonteHyperlink.cRole,
								fonteHyperlink.cUrl,
								fonteHyperlink.fkUriPWorkitem,
								StringTemplate.create("(select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSissHistory() + ".workitem where workitem.c_pk = fk_p_workitem) as fk_p_workitem")
								);
	
				SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgHyperlink);
				
				int batchcounter = 0;
				
				for (Tuple row : hyperlinks) {
					batchcounter++;
					
					Object[] vals = row.toArray();
					SQLQuery queryConnOracle = new SQLQuery(connOracle, dialect);
					String fkUriPWorkitem = queryConnOracle.from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[2].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0);
					String fkPWorkitem = fkUriPWorkitem+"%"+vals[3];
					
					insert
							.columns(
									
									stgHyperlink.cRole,
									stgHyperlink.cUri,
									stgHyperlink.fkPWorkitem,
									stgHyperlink.fkUriPWorkitem,
									stgHyperlink.dataCaricamento,
									stgHyperlink.sissHistoryHyperlinkPk
									
							)
									
							.values(
									
									vals[0],
									vals[1],
									fkPWorkitem,
									fkUriPWorkitem,
									DataEsecuzione.getInstance().getDataEsecuzione(),
									StringTemplate.create("HISTORY_HYPERLINK_SEQ.nextval")
											
							)
							.addBatch();
					
					if(batchcounter % DmAlmConstants.BATCH_SIZE == 0 && !insert.isEmpty()) {
						insert.execute();
						logger.info("Hyperlink dei Work Item di tipo "+type.toString()+" importati con successo.");
						insert  = new SQLInsertClause(connOracle, dialect, stgHyperlink);
					}
					
				}
				
				if(!insert.isEmpty()) {
					insert.execute();
					logger.info("Hyperlink dei Work Item di tipo "+type.toString()+" importati con successo.");
				}
				
				connOracle.commit();
				
			}
			
		} catch (Exception e) {
ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new DAOException(e);
		}
		finally {
			if(cm != null) cm.closeConnection(pgConnection);
			if(cm != null) cm.closeConnection(connOracle);
		}
	}
	public static void recoverSissHistoryHyperlink() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryHyperlink stgHyperlink = QSissHistoryHyperlink.dmalmSissHistoryHyperlink;
//			Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00", "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgHyperlink).where(stgHyperlink.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
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
