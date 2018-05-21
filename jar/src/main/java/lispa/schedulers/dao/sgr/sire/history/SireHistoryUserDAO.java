package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryUser;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;


public class SireHistoryUserDAO
{

	private static Logger logger = Logger.getLogger(SireHistoryUserDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryUser  fonteUsers= 
			lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryUser.user;

	private static QSireHistoryUser   stgUsers  = QSireHistoryUser.sireHistoryUser;
	
	public static void fillSireHistoryUser(long minRevision, long maxRevision) throws SQLException, DAOException {

		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        pgConnection = null;
		List<Tuple>       users = null;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
			users = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			PostgresTemplates dialect = new PostgresTemplates()
			{ {
				setPrintSchema(true);
			}};

			SQLQuery query 		 = new SQLQuery(pgConnection, dialect); 

			users = query.from(fonteUsers)
					.where(fonteUsers.cRev.gt(minRevision))
					.where(fonteUsers.cRev.loe(maxRevision))
					.list(
							//fonteUsers.all()
							
							fonteUsers.cAvatarfilename,
							fonteUsers.cDeleted,
							fonteUsers.cDisablednotifications,
							fonteUsers.cEmail,
							fonteUsers.cId,
							fonteUsers.cInitials,
							StringTemplate.create("0 as c_is_local"),
							fonteUsers.cName,
							fonteUsers.cUri,
							fonteUsers.cRev
							);
			
//			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgUsers);
			int n_righe_inserite = 0;
			for(Tuple row : users) {
				Object[] vals = row.toArray();
				String cUri = vals[8] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[8].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(vals[8].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SIRE)).list(stgSubterra.cPk).get(0) : "") :"";
				String cPk = cUri+"%"+(vals[9] != null ? vals[9].toString() : "");
				
				new SQLInsertClause(connOracle, dialect, stgUsers)
					.columns(
						stgUsers.cAvatarfilename,
						stgUsers.cDeleted,
						stgUsers.cDisablednotifications,
						stgUsers.cEmail,
						stgUsers.cId,
						stgUsers.cInitials,
						stgUsers.cIsLocal,
						stgUsers.cName,
						stgUsers.cPk,
						stgUsers.cRev,
						stgUsers.cUri,
						stgUsers.dataCaricamento,
						stgUsers.dmalmHistoryUserPk
						)
						.values(								
								vals[0],
								vals[1],
								vals[2],
								StringUtils.getMaskedValue((String)vals[3]),
								StringUtils.getMaskedValue((String)vals[4]),
								vals[5],
								vals[6],
								StringUtils.getMaskedValue((String)vals[7]),
								StringUtils.getMaskedValue(cPk),
								vals[9],
								StringUtils.getMaskedValue(cUri),
								DataEsecuzione.getInstance().getDataEsecuzione(),
								StringTemplate.create("HISTORY_USER_SEQ.nextval")
						).execute();
					
					n_righe_inserite++;
			
//					if (!insert.isEmpty()) {
//						if (n_righe_inserite % lispa.schedulers.constant.DmAlmConstants.BATCH_SIZE == 0) {
//							insert.execute();
//							connOracle.commit();
//							insert = new SQLInsertClause(connOracle, dialect, stgUsers);
//						}
//					}
		
				}
//				if (!insert.isEmpty()) {
//					insert.execute();
//					connOracle.commit();
//				}
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

	public static long getMinRevision() throws Exception {
		ConnectionManager cm = null;
		Connection oracle = null;

		List<Long> max = new ArrayList<Long>();
		try{

			cm 	   = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();

			QSireHistoryUser   stgUsers  = QSireHistoryUser.sireHistoryUser;
			SQLTemplates dialect 				 = new HSQLDBTemplates();
			SQLQuery query 						 = new SQLQuery(oracle, dialect); 

			max = query.from(stgUsers).list(stgUsers.cRev.max());

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
	
	public static void recoverSireHistoryUser() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryUser stgUsers = QSireHistoryUser.sireHistoryUser;
//			Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00", "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgUsers).where(stgUsers.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
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