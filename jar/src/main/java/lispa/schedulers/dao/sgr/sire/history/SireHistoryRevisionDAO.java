package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryRevision;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SireHistoryRevisionDAO {

	private static Logger logger = Logger.getLogger(SireHistoryRevisionDAO.class);
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision fonteRevisions = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryRevision.revision;
	private static lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryRevision stg_Revisions = lispa.schedulers.queryimplementation.staging.sgr.sire.history.SireHistoryRevision.revision;

	public static long getMaxRevision() throws Exception {

		ConnectionManager cm = null;
		Connection connH2 = null;


		List<Long> max = new ArrayList<Long>();
		try{

			cm 	   = ConnectionManager.getInstance();
			connH2 = cm.getConnectionSIREHistory();

			Timestamp lastValid = DateUtils.addSecondsToTimestamp(DataEsecuzione.getInstance().getDataEsecuzione(), -3600);

			SireHistoryRevision  fonteRevisions  = SireHistoryRevision.revision;

			SQLTemplates dialect 				 = new HSQLDBTemplates(){ {
				setPrintSchema(true);
			}};
			SQLQuery query 						 = new SQLQuery(connH2, dialect); 

			max = query.from(fonteRevisions).where(fonteRevisions.cCreated.before(lastValid)).list(fonteRevisions.cName.castToNum(Long.class).max());

			if(max == null || max.size() == 0 || max.get(0) == null) {
				return 0;
			}
		} catch (Exception e)	{
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		} finally {
			if(cm != null) cm.closeConnection(connH2);
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

			if(max == null || max.size() == 0 || max.get(0) == null) {
				return DateUtils.stringToTimestamp("1900-01-01 00:00:00", "yyyy-MM-dd 00:00:00");
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new DAOException(e);
		} finally {
			if(cm != null) cm.closeConnection(oracle);
		}

		return max.get(0);
	} 

	public static void fillSireHistoryRevision(Timestamp minRevision, long maxRevision) throws SQLException, DAOException {

		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        connH2 = null;
		List<Tuple>       revisions = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSIREHistory();
			revisions = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
				setPrintSchema(true);
			}};

			SQLQuery query 		 = new SQLQuery(connH2, dialect); 

			revisions = query.from(fonteRevisions)
					.where(fonteRevisions.cCreated.gt(minRevision))
					.where(fonteRevisions.cName.castToNum(Long.class).loe(maxRevision))
					.list(fonteRevisions.all());
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stg_Revisions);

			int batch_size_counter = 0;
			for(Tuple row : revisions) {
				batch_size_counter++;

				insert
				.columns(stg_Revisions.cPk,
						stg_Revisions.cAuthor,
						stg_Revisions.cCreated,
						stg_Revisions.cDeleted,
						stg_Revisions.cInternalcommit,
						stg_Revisions.cIsLocal,
						stg_Revisions.cMessage,
						stg_Revisions.cName,
						stg_Revisions.cRepositoryname,
						stg_Revisions.cRev,
						stg_Revisions.cUri
				).values(								
						row.get(fonteRevisions.cPk),
						StringUtils.getMaskedValue(row.get(fonteRevisions.cAuthor)),
						row.get(fonteRevisions.cCreated),
						row.get(fonteRevisions.cDeleted),
						row.get(fonteRevisions.cInternalcommit),
						row.get(fonteRevisions.cIsLocal),
						row.get(fonteRevisions.cMessage) != null && row.get(fonteRevisions.cMessage).length() > 4000 ? row.get(fonteRevisions.cMessage).substring(0, 4000) : row.get(fonteRevisions.cMessage),
								row.get(fonteRevisions.cName),
								row.get(fonteRevisions.cRepositoryname),
								row.get(fonteRevisions.cRev),
								row.get(fonteRevisions.cUri)
						);
				insert.addBatch();
				if(!revisions.isEmpty() && batch_size_counter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect, stg_Revisions);
					batch_size_counter = 0;
					
				}
			}
			if(!revisions.isEmpty()) {
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
		} finally {
			if(cm != null) cm.closeConnection(connOracle);
			if(cm != null) cm.closeConnection(connH2);
		}
	}
	
	public static void delete() throws Exception {
		ConnectionManager cm = null;
		Connection OracleConnection = null;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			new SQLDeleteClause(OracleConnection, dialect, stg_Revisions)
				.execute();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(OracleConnection);
			}
		}
	}
}