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
	
	private static QSireHistoryProjectgroup stgProjectGroups = QSireHistoryProjectgroup.sireHistoryProjectgroup;
	private static lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap fonteSireSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap.urimap;
	private static Logger logger = Logger.getLogger(SireHistoryProjectGroupDAO.class);

	public static void fillSireHistoryProjectGroup() throws SQLException, DAOException {
		
		ConnectionManager cm   = null;
		Connection 	 	  connOracle = null;
		Connection        pgConnection = null;
		List<Tuple>       projectgroups = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connOracle = cm.getConnectionOracle();
			pgConnection = cm.getConnectionSIREHistory();
			projectgroups = new ArrayList<Tuple>();
			
			connOracle.setAutoCommit(false);
			
			PostgresTemplates dialect = new PostgresTemplates()
			{ {
			    setPrintSchema(true);
			}};
			
			SQLQuery query 		 = new SQLQuery(pgConnection, dialect); 
			
			projectgroups = query.from(fonteProjectGroups)
					.list(
							//fonteProjectGroups.all()
							
							fonteProjectGroups.cLocation,
							StringTemplate.create("0 as c_is_local"),
							StringTemplate.create("(SELECT a.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " a WHERE a.c_id = " + fonteProjectGroups.cPk + ") as c_pk"),
							StringTemplate.create("(SELECT b.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " b WHERE b.c_id = " + fonteProjectGroups.fkUriParent + ") as fk_uri_parent"),
							StringTemplate.create("(SELECT c.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " c WHERE c.c_id = " + fonteProjectGroups.fkParent + ") as fk_parent"),
							fonteProjectGroups.cName,
							fonteProjectGroups.cDeleted,
							fonteProjectGroups.cRev,
							StringTemplate.create("(SELECT d.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " d WHERE d.c_id = " + fonteProjectGroups.cUri + ") as c_uri")
							);
			
			for(Tuple row : projectgroups) {
				new SQLInsertClause(connOracle, dialect, stgProjectGroups)
				.columns(
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
								row.get(fonteProjectGroups.cLocation),
								row.get(fonteProjectGroups.cIsLocal),
								row.get(fonteProjectGroups.cPk),
								row.get(fonteProjectGroups.fkUriParent),
								row.get(fonteProjectGroups.fkParent),
								row.get(fonteProjectGroups.cName),
								row.get(fonteProjectGroups.cDeleted),
								row.get(fonteProjectGroups.cRev),
								row.get(fonteProjectGroups.cUri),
								DataEsecuzione.getInstance().getDataEsecuzione(),
								 StringTemplate.create("HISTORY_PROJGROUP_SEQ.nextval")
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
