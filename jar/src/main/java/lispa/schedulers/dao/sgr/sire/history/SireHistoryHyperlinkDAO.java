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
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryHyperlink;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryHyperlink;
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

public class SireHistoryHyperlinkDAO {

	private static Logger logger = Logger
			.getLogger(SireHistoryHyperlinkDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryHyperlink fonteHyperlink = SireHistoryHyperlink.structWorkitemHyperlinks;
	
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryHyperlink stgHyperlink = QSireHistoryHyperlink.dmalmSireHistoryHyperlink;

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem  fonteHistoryWorkItems  = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem.workitem;
	
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap fonteSireSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap.urimap;
	
	public static void fillSireHistoryHyperlink(Map<Workitem_Type, Long> minRevisionByType, long maxRevision) throws SQLException, DAOException {
		
		ConnectionManager cm = null;
		Connection connOracle = null;
		Connection pgConnection = null;
		List<Tuple> hyperlinks = null;

		try {
			
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
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
				pgConnection = cm.getConnectionSIREHistory();
			}

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
							StringTemplate.create("(SELECT a.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSire() + " a WHERE a.c_id = " + fonteHyperlink.fkUriPWorkitem + ") || '%' || (select c_rev from " + lispa.schedulers.manager.DmAlmConstants.GetPolarionSchemaSireHistory() + ".workitem where workitem.c_pk = fk_p_workitem) as fk_p_workitem"),
							StringTemplate.create("(SELECT b.c_pk FROM " + lispa.schedulers.manager.DmAlmConstants.GetDbLinkPolarionCurrentSire() + " b WHERE b.c_id = " + fonteHyperlink.fkUriPWorkitem + ") as fk_uri_p_workitem")
							);
			
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgHyperlink);
			
			int batchcounter = 0;

			for (Tuple row : hyperlinks) {
				
				batchcounter++;
				
				Object[] val = row.toArray();
				
				insert
						.columns(
								
								stgHyperlink.cRole,
								stgHyperlink.cUri,
								stgHyperlink.fkPWorkitem,
								stgHyperlink.fkUriPWorkitem,
								stgHyperlink.dataCaricamento,
								stgHyperlink.sireHistoryHyperlinkPk
								
						)
								
						.values(
								val[0],val[1],val[2],val[3],
								DataEsecuzione.getInstance().getDataEsecuzione(),
								StringTemplate.create("HISTORY_HYPERLINK_SEQ.nextval")
										
						)
						.addBatch();
				
				if(batchcounter % DmAlmConstants.BATCH_SIZE == 0 && !insert.isEmpty()) {
					insert.execute();
					insert  = new SQLInsertClause(connOracle, dialect, stgHyperlink);
				}
				
			}
			
			if(!insert.isEmpty()) {
				insert.execute();
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
	public static void recoverSireHistoryHyperlink() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryHyperlink stgHyperlink = QSireHistoryHyperlink.dmalmSireHistoryHyperlink;
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
