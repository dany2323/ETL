package lispa.schedulers.dao.sgr.sire.history;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.history.QSireHistoryProjectgroup;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SireHistoryProjectGroupDAO
{

	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryProjectgroup fonteProjectGroups = lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryProjectgroup.projectgroup;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap fonteSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentSubterraUriMap.urimap;

	private static QSireHistoryProjectgroup stgProjectGroups = QSireHistoryProjectgroup.sireHistoryProjectgroup;
	private static Logger logger = Logger.getLogger(SireHistoryProjectGroupDAO.class);

	public static void fillSireHistoryProjectGroup() throws SQLException, DAOException {
		
		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        pgConnection = null;
		List<Tuple>       projectgroups = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIRECurrent();
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
							StringTemplate.create("(SELECT b.c_pk FROM subterra_uri_map b WHERE b.c_id = " + fonteProjectGroups.fkUriParent +") as fk_uri_parent"),
							StringTemplate.create("(SELECT c.c_pk FROM subterra_uri_map c WHERE c.c_id = " + fonteProjectGroups.fkUriParent + ") as fk_parent"),
							fonteProjectGroups.cName,
							fonteProjectGroups.cDeleted,
							fonteProjectGroups.cRev,
							StringTemplate.create(fonteSubterraUriMap.cPk + " as c_uri")
							);
			
			SQLInsertClause insert = new SQLInsertClause(connOracle, dialect, stgProjectGroups);
			int n_righe_inserite = 0;
			
			for(Tuple row : projectgroups) {
				Object[] val = row.toArray();
				
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
						stgProjectGroups.sireHistoryProjGroupPk
						)
						.values(								
								val[0],
								val[1],
								val[2],
								val[3],
								val[4],
								val[5],
								val[6],
								val[7],
								val[8],
								DataEsecuzione.getInstance().getDataEsecuzione(),
								 StringTemplate.create("HISTORY_PROJGROUP_SEQ.nextval")
					).addBatch();
					
					n_righe_inserite++;
			
				if (!insert.isEmpty()) {
					if (n_righe_inserite % lispa.schedulers.constant.DmAlmConstants.BATCH_SIZE == 0) {
						insert.execute();
						connOracle.commit();
						insert = new SQLInsertClause(connOracle, dialect, stgProjectGroups);
					}
				}
	
			}
			if (!insert.isEmpty()) {
				insert.execute();
				connOracle.commit();
			}
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
	public static void recoverSireHistoryProjectGroup() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
	
			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QSireHistoryProjectgroup stgProjectGroups = QSireHistoryProjectgroup.sireHistoryProjectgroup; 
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
	
		
} 
