package lispa.schedulers.dao.sgr.siss.history;


import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryUser;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;



public class SissHistoryUserDAO
{

	private static Logger logger = Logger.getLogger(SissHistoryUserDAO.class);

	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryUser  fonteUsers= 
			lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryUser.user;

	private static QSissHistoryUser   stgUsers  = QSissHistoryUser.sissHistoryUser;

	public static void fillSissHistoryUser(long minRevision, long maxRevision) throws Exception {

		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        connH2 = null;
		List<Tuple>       users = null;

		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			connH2 = cm.getConnectionSISSHistory();
			users = new ArrayList<Tuple>();

			connOracle.setAutoCommit(false);

			SQLTemplates dialect = new HSQLDBTemplates()
			{ {
				setPrintSchema(true);
			}};

			SQLQuery query 		 = new SQLQuery(connH2, dialect); 

			users = query.from(fonteUsers)
					.where(fonteUsers.cRev.gt(minRevision))
					.where(fonteUsers.cRev.loe(maxRevision))
					.list(
							fonteUsers.all()
							);

			for(Tuple row : users) {
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
						stgUsers.dmalmUserPk
						)
						.values(								
								row.get(fonteUsers.cAvatarfilename),
								row.get(fonteUsers.cDeleted),
								row.get(fonteUsers.cDisablednotifications),
								StringUtils.getMaskedValue(row.get(fonteUsers.cEmail)),
								StringUtils.getMaskedValue(row.get(fonteUsers.cId)),
								row.get(fonteUsers.cInitials),
								row.get(fonteUsers.cIsLocal),
								StringUtils.getMaskedValue(row.get(fonteUsers.cName)),
								// 
								StringUtils.getMaskedValue(row.get(fonteUsers.cPk)),
								row.get(fonteUsers.cRev),
								StringUtils.getMaskedValue(row.get(fonteUsers.cUri)),
								DataEsecuzione.getInstance().getDataEsecuzione(),
								StringTemplate.create("HISTORY_USER_SEQ.nextval")
								)
								.execute();


			}
			connOracle.commit();
		}
		catch(Exception e) {
ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw new DAOException(e);
		}
		finally {
			if(cm != null) cm.closeConnection(connH2);
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

			QSissHistoryUser   stgUsers  = QSissHistoryUser.sissHistoryUser;
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
	
	public static void recoverSissHistoryUser() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryUser stgUsers = QSissHistoryUser.sissHistoryUser;
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


}