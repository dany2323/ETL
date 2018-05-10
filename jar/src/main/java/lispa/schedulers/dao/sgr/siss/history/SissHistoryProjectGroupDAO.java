package lispa.schedulers.dao.sgr.siss.history;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryProjectgroup;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SissHistoryProjectGroupDAO
{

	private static Logger logger = Logger.getLogger(SissHistoryProjectGroupDAO.class); 
	
	private static lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProjectgroup fonteProjectGroups = lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryProjectgroup.projectgroup;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap fonteSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap.urimap;
	private static QSissHistoryProjectgroup stgProjectGroups = QSissHistoryProjectgroup.sissHistoryProjectgroup;
	
	
	public static void fillSissHistoryProjectGroup() throws Exception {
		
		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        pgConnection = null;
		List<Tuple>       projectgroups = null;
		lispa.schedulers.queryimplementation.staging.sgr.QDmalmCurrentSubterraUriMap stgSubterra = QDmalmCurrentSubterraUriMap.currentSubterraUriMap;
		int size_counter = 0;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSISSCurrent();
			projectgroups = new ArrayList<Tuple>();
			
			connOracle.setAutoCommit(false);
			
			PostgresTemplates dialect = new PostgresTemplates()
			{ {
			    setPrintSchema(true);
			}};
			
			SQLQuery query 		 = new SQLQuery(pgConnection, dialect); 
			
			projectgroups = query.from(fonteProjectGroups)
					.join(fonteSubterraUriMap)
					.on(fonteProjectGroups.cUri.castToNum(Long.class).eq(fonteSubterraUriMap.cId))
					.list(
							fonteProjectGroups.cLocation,
							StringTemplate.create("0 as c_is_local"),
							fonteSubterraUriMap.cPk,
							fonteProjectGroups.fkUriParent,
							fonteProjectGroups.cName,
							fonteProjectGroups.cDeleted,
							fonteProjectGroups.cRev,
							StringTemplate.create(fonteSubterraUriMap.cPk + " as c_uri")
							);
			
			logger.debug("fillSissHistoryProjectGroup - projectgroups.size: "+ (projectgroups != null ? projectgroups.size() : 0));
			
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgProjectGroups);
			Timestamp dataEsecuzione = DataEsecuzione.getInstance().getDataEsecuzione();
			for(Tuple row : projectgroups) {
				Object[] val = row.toArray();
				String fkUriParent = val[3] != null ? (queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[3].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).count() > 0 ? queryConnOracle(connOracle, dialect).from(stgSubterra).where(stgSubterra.cId.eq(Long.valueOf(val[3].toString()))).where(stgSubterra.cRepo.eq(lispa.schedulers.constant.DmAlmConstants.REPOSITORY_SISS)).list(stgSubterra.cPk).get(0) : "") : "";
				size_counter++;
				insert.columns(
						stgProjectGroups.cLocation,
						stgProjectGroups.cIsLocal,
						stgProjectGroups.cPk,
						stgProjectGroups.fkUriParent,
						stgProjectGroups.fkParent,
						stgProjectGroups.cName,
						stgProjectGroups.cDeleted,
						stgProjectGroups.cRev,
						stgProjectGroups.cUri,
						stgProjectGroups.dataCaricamento,
						stgProjectGroups.dmalmProjgroupPk
						)
						.values(								
								val[0],
								val[1],
								val[2],
								fkUriParent,
								fkUriParent,
								val[4],
								val[5],
								val[6],
								val[7],
								dataEsecuzione,
								StringTemplate.create("HISTORY_PROJGROUP_SEQ.nextval")
								)
						.addBatch();
				if(!insert.isEmpty() && size_counter == DmAlmConstants.BATCH_SIZE) {
					insert.execute();
					insert = new SQLInsertClause(connOracle, dialect, stgProjectGroups);
					size_counter = 0;
					
				}

			}
			if(!insert.isEmpty())
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

			SQLTemplates dialect 				 = new HSQLDBTemplates();
			SQLQuery query 						 = new SQLQuery(oracle, dialect); 

			max = query.from(stgProjectGroups).list(stgProjectGroups.cRev.max());

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
	
	public static void delete() throws DAOException {
		ConnectionManager cm 	= null;
		Connection connection 	= null;


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect

			new SQLDeleteClause(connection, dialect, stgProjectGroups)
			.execute();

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}
	}
	
	public static void recoverSissHistoryProjectGroup() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSissHistoryProjectgroup stgProjectGroups = QSissHistoryProjectgroup.sissHistoryProjectgroup; 
//			Timestamp ts = DateUtils.stringToTimestamp("2014-05-08 15:54:00", "yyyy-MM-dd HH:mm:ss");
			new SQLDeleteClause(connection, dialect, stgProjectGroups).where(stgProjectGroups.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();
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