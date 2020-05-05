package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentRevision;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryRevision;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

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


public class SireHistoryRevisionDAO {

	private static Logger logger = Logger.getLogger(SireHistoryRevisionDAO.class);
	
	private static lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryRevision fonteRevisions = lispa.schedulers.queryimplementation.fonte.sgr.history.HistoryRevision.revision;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryRevision stgRevisions = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryRevision.revision;

	public static long getMaxRevision() throws Exception
	{

		ConnectionManager cm = null;
		Connection connPg = null;

		List<Long> max = new ArrayList<Long>();
		try{

			cm = ConnectionManager.getInstance();

			connPg = cm.getConnectionSIRECurrent();

			PostgresTemplates dialect = new PostgresTemplates()
			{
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query 		 = new SQLQuery(connPg, dialect); 


			Timestamp lastValid = DateUtils.addSecondsToTimestamp(DataEsecuzione.getInstance().getDataEsecuzione(), -3600);

			max = query.from(fonteRevisions).where(fonteRevisions.cCreated.before(lastValid)).list(fonteRevisions.cName.castToNum(Long.class).max());

			if(max == null || max.size() == 0 || max.get(0) == null)
			{
				return 0;
			}

		}
		
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		}
		finally
		{
			if(cm != null) cm.closeConnection(connPg);
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

			QSireHistoryRevision   stgRevision  = QSireHistoryRevision.sireHistoryRevision;
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

	public static void fillSireHistoryRevision(Timestamp minRevision, long maxRevision) throws SQLException, DAOException {

		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection 	 	  connPg = null;
		List<Tuple>       revisions = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connPg = cm.getConnectionSIRECurrent();

			revisions = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates()
			{
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query= new SQLQuery(connPg, dialect); 

			revisions = query.from(fonteRevisions)
					.where(fonteRevisions.cCreated.gt(minRevision))
					.where(fonteRevisions.cName.castToNum(Long.class).loe(maxRevision))
					.list(fonteRevisions.all());
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgRevisions);
			int batchSizeCounter=0;
			for(Tuple row : revisions) {
								
				insert.columns(
						stgRevisions.cPk,
						stgRevisions.cAuthor,
						stgRevisions.cCreated,
						stgRevisions.cDeleted,
						stgRevisions.cInternalcommit,
						stgRevisions.cMessage,
						stgRevisions.cName,
						stgRevisions.cRepositoryname,
						stgRevisions.cRev,
						stgRevisions.cUri
						)
						.values(row.get(fonteRevisions.cPk),
								StringUtils.getMaskedValue(row.get(fonteRevisions.cAuthor)),
								row.get(fonteRevisions.cCreated),
								row.get(fonteRevisions.cDeleted),
								row.get(fonteRevisions.cInternalCommit),
								row.get(fonteRevisions.cMessage),
								row.get(fonteRevisions.cName),
								row.get(fonteRevisions.cRepositoryname),
								row.get(fonteRevisions.cRev),
								row.get(fonteRevisions.cUri)
								).addBatch();
				batchSizeCounter++;
				if(!revisions.isEmpty() && batchSizeCounter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect, stgRevisions);
					batchSizeCounter = 0;
					
				}
			}
			if(!insert.isEmpty()) {
				insert.execute();
				connOracle.commit();
			}

		}
		catch(Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new DAOException(e);
		}
		finally {
			if(cm != null) cm.closeConnection(connOracle);
		}

	}
	
}