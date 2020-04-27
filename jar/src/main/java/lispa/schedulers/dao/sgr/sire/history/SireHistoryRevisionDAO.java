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

//	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision fonteRevisions = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision.revision;
//	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap fonteSubterraUriMap = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap.urimap;
	
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision fonteRevisions = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision.revision;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryRevision stgRevisions = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryRevision.revision;


	private static QDmalmCurrentSubterraUriMap fonteSubterraUriMap = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;

	public static long getMaxRevision() throws Exception
	{

		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try{

			cm 	   = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			Timestamp lastValid = DateUtils.addSecondsToTimestamp(DataEsecuzione.getInstance().getDataEsecuzione(), -3600);

			SQLTemplates dialect 				 = new HSQLDBTemplates();
			SQLQuery query 						 = new SQLQuery(oracle, dialect); 

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
			if(cm != null) cm.closeConnection(oracle);
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
		List<Tuple>       revisions = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			revisions = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates()
			{
				{
					setPrintSchema(true);
				}
			};

			SQLQuery query 		 = new SQLQuery(connOracle, dialect); 

			revisions = query.from(fonteRevisions)
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
			Timestamp dataEsecuzione = DataEsecuzione.getInstance().getDataEsecuzione();
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgRevisions);
			int batch_size_counter=0;
			for(Tuple row : revisions) {
				
				Object[] vals = row.toArray();
				
				//Applico il cast a timespent solo se esistono dei valori data 
				StringExpression dateValue = null;
				if(vals[2] != null) {
					dateValue = StringTemplate.create("to_timestamp('"+vals[2]+"', 'YYYY-MM-DD HH24:MI:SS.FF')");
				}

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
						stgRevisions.cUri
						)
						.values(								
								vals[0],
								StringUtils.getMaskedValue((String)vals[1]),
								dateValue,
								vals[3],
								vals[4],
								vals[5],
								vals[6] != null && vals[6].toString().length() > 4000 ? vals[6].toString().substring(0, 4000) : vals[6],
								vals[7],
								vals[8],
								vals[9],
								vals[10]
								).addBatch();
				batch_size_counter++;
				if(!revisions.isEmpty() && batch_size_counter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect, stgRevisions);
					batch_size_counter = 0;
					
				}
			}
			if(!revisions.isEmpty()) {
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