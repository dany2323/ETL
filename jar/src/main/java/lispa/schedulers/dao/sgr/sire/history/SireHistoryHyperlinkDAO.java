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
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
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
	
	public static void fillSireHistoryHyperlink(Map<EnumWorkitemType, Long> minRevisionByType, long maxRevision) throws SQLException, DAOException {
		
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
			
			for(EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {
				
			if(connH2.isClosed()) {
				if(cm != null) cm.closeConnection(connH2);
				connH2 = cm.getConnectionSIREHistory();
			}

			SQLQuery query = new SQLQuery(connH2, dialect);

			hyperlinks = query.from(fonteHyperlink)
					.join(fonteHistoryWorkItems)
					.on(fonteHistoryWorkItems.cPk.eq(fonteHyperlink.fkPWorkitem))
					.where(fonteHistoryWorkItems.cType.eq(type.toString()))
					.where(fonteHistoryWorkItems.cRev.gt(minRevisionByType.get(type)))
					.where(fonteHistoryWorkItems.cRev.loe(maxRevision))
					.list(fonteHyperlink.all());
			
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgHyperlink);
			
			int batchcounter = 0;

			for (Tuple row : hyperlinks) {
				
				batchcounter++;
				
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
								
								row.get(fonteHyperlink.cRole),
								row.get(fonteHyperlink.cUrl),
								row.get(fonteHyperlink.fkPWorkitem),
								row.get(fonteHyperlink.fkUriPWorkitem),
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
			if(cm != null) cm.closeConnection(connH2);
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
