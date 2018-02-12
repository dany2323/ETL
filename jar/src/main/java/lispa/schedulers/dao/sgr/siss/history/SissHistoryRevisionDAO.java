package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.expr.StringExpression;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRevision;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryRevision;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;


public class SissHistoryRevisionDAO {

	private static Logger logger = Logger.getLogger(SissHistoryRevisionDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRevision fonteRevisions = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryRevision.revision;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap fonteSubterraUriMap = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap.urimap;
	private static QSissHistoryRevision stgRevisions = QSissHistoryRevision.sissHistoryRevision;


	public static long getMaxRevision() throws Exception
	{

		ConnectionManager cm = null;
		Connection pgConnection = null;


		List<Long> max = new ArrayList<Long>();
		try{

			cm 	   = ConnectionManager.getInstance();
			pgConnection = cm.getConnectionSISSHistory();

			Timestamp lastValid = DateUtils.addSecondsToTimestamp(DataEsecuzione.getInstance().getDataEsecuzione(), -3600);

			SissHistoryRevision  fonteRevisions  = SissHistoryRevision.revision;

			PostgresTemplates dialect 				 = new PostgresTemplates(){ {
				setPrintSchema(true);
			}};
			SQLQuery query 						 = new SQLQuery(pgConnection, dialect); 

			max = query.from(fonteRevisions).where(fonteRevisions.cCreated.before(lastValid)).list(fonteRevisions.cName.castToNum(Long.class).max());

			if(max == null || max.size() == 0 || max.get(0) == null)
			{
				return 0;
			}

		}
		catch (PropertiesReaderException e) 
		{
			logger.error(e.getMessage(), e);
			
		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		}
		finally
		{
			if(cm != null) cm.closeConnection(pgConnection);
		}

		return max.get(0).longValue();
	}

	public static Timestamp getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Timestamp> max = new ArrayList<Timestamp>();
		try{

			cm 	   = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			QSissHistoryRevision   stgRevision  = QSissHistoryRevision.sissHistoryRevision;
			SQLTemplates dialect 				 = new HSQLDBTemplates();
			SQLQuery query 						 = new SQLQuery(oracle, dialect); 

			max = query.from(stgRevision).list(stgRevision.cCreated.max());

			if(max == null || max.size() == 0 || max.get(0) == null)
			{
				return DateUtils.stringToTimestamp("1900-01-01 00:00:00", "yyyy-MM-dd 00:00:00");
			}

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		}
		finally
		{
			if(cm != null) cm.closeConnection(oracle);
		}

		return max.get(0);
	} 

	public static void fillSissHistoryRevision(Timestamp minRevision, long maxRevision) throws SQLException, DAOException {

		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        pgConn = null;
		List<Tuple>       revisions = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConn = cm.getConnectionSISSCurrent();
			revisions = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
				setPrintSchema(true);
			}};

			SQLQuery query 		 = new SQLQuery(pgConn, dialect); 

			revisions = query.from(fonteRevisions)
					.join(fonteSubterraUriMap)
					.on(fonteRevisions.cUri.castToNum(Long.class).eq(fonteSubterraUriMap.cId))
					.where(fonteRevisions.cCreated.gt(minRevision))
					.where(fonteRevisions.cName.castToNum(Long.class).loe(maxRevision))
					.list(
							fonteSubterraUriMap.cPk,
							fonteRevisions.cAuthor,
							fonteRevisions.cCreated,
							fonteRevisions.cDeleted,
							fonteRevisions.cInternalcommit,
							StringTemplate.create("0 as c_is_local"),
							fonteRevisions.cMessage,
							fonteRevisions.cName,
							fonteRevisions.cRepositoryname,
							fonteRevisions.cRev,
							StringTemplate.create(fonteSubterraUriMap.cPk + " as c_uri")
							);
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgRevisions);
			
			int size_counter = 0;
			
			
				for(Tuple row : revisions) {

					Object[] vals = row.toArray();
					
					//Applico il cast a timespent solo se esistono dei valori data 
					StringExpression dateValue = null;
					if(vals[2] != null) {
						dateValue = StringTemplate.create("to_timestamp('"+vals[2]+"', 'YYYY-MM-DD HH24:MI:SS.FF')");
					}
					
					size_counter++;
					insert.columns(
							stgRevisions.cPk,
							stgRevisions.cAuthor,
							stgRevisions.cCreated,
							stgRevisions.cDeleted,
							stgRevisions.cInternalcommit,
							stgRevisions.cIsLocal,
							stgRevisions.cMessage,
							stgRevisions.cName,
							stgRevisions.cRepositoryname,
							stgRevisions.cRev,
							stgRevisions.cUri,
							stgRevisions.sissHistoryRevisionPk,
							stgRevisions.dataCaricamento
							).values(								
							vals[0],
							StringUtils.getMaskedValue((String)vals[1]),
							dateValue,
							vals[3],
							vals[4],
							vals[5],
							vals[6] != null && vals[6].toString().length() > 4000 ? vals[6].toString().substring(0,  4000) : vals[6],
									vals[7],
									vals[8],
									vals[9],
									vals[10],
									StringTemplate.create("HISTORY_REVISION_SEQ.nextval"),
									DataEsecuzione.getInstance().getDataEsecuzione()
							)
							.addBatch();
					if(!revisions.isEmpty() && size_counter == DmAlmConstants.BATCH_SIZE) {
						insert.execute();
						insert = new SQLInsertClause(connOracle, dialect, stgRevisions);
						size_counter = 0;
						
					}

				}
				if(!revisions.isEmpty())
				{
					insert.execute();
				}
		
				connOracle.commit();
		}
		catch(Exception e) {
ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new DAOException(e);
		}
		finally {
			if(cm != null) cm.closeConnection(pgConn);
			if(cm != null) cm.closeConnection(connOracle);
		}

	}
	
	public static void recoverSissHistoryRevision() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryRevision stgRevisions = QSissHistoryRevision.sissHistoryRevision;
//			Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00", "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgRevisions).where(stgRevisions.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
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
