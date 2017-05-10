package lispa.schedulers.dao.sgr.sire.current;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.PostgresTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.QTuple;
import com.mysema.query.types.template.StringTemplate;
import com.mysema.query.sql.postgres.*;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.sire.current.QSireCurrentProject;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

public class SireCurrentProjectDAO
{

	private static Logger logger = Logger.getLogger(SireCurrentProjectDAO.class); 

	public static long fillSireCurrentProject() throws Exception
	{

		ConnectionManager cm = null;
		Connection OracleConnection = null;
		Connection pgConnection = null;
		long n_righe_inserite = 0;

		try{

			cm = ConnectionManager.getInstance();
			pgConnection = cm.getConnectionSIRECurrent();
			OracleConnection = cm.getConnectionOracle();

			OracleConnection.setAutoCommit(false);

			QSireCurrentProject   stgProject  = QSireCurrentProject.sireCurrentProject;
	
			PostgresTemplates dialect = new PostgresTemplates()
			{ {
				setPrintSchema(true);
			}};
			
            lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentProject fonteProjects  = lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireCurrentProject.project;
            lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap fonteSireSubterraUriMap =lispa.schedulers.queryimplementation.fonte.sgr.sire.current.SireSubterraUriMap.urimap;
			
            SQLQuery query = new SQLQuery(pgConnection, dialect); 

             
            List<Tuple> project = query.from(fonteProjects)
                    //                    .where(fonteProjects.cLocation.like("default:/Sviluppo/%"))
                    .where((fonteProjects.cLocation.length().subtract(fonteProjects.cLocation.toString().replaceAll("/", "").length())).goe(4))
                    .where(fonteProjects.cLocation.notLike("default:/GRACO%"))
                    .list(new QTuple(
                            StringTemplate.create("SUBSTRING("+fonteProjects.cTrackerprefix+",0,4000)") ,
                            StringTemplate.create("0") ,
                            StringTemplate.create("(SELECT a.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " a WHERE a.c_id = " + fonteProjects.cUri + ") as c_pk"),
                            StringTemplate.create("(SELECT b.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " b WHERE b.c_id = " + fonteProjects.fkUriLead + ") as fk_uri_lead"),
                            StringTemplate.create("CASE WHEN "+fonteProjects.cDeleted+"= 'true' THEN 1 ELSE 0 END") ,
                            fonteProjects.cFinish,
                            StringTemplate.create("(SELECT c.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " c WHERE c.c_id = " + fonteProjects.cUri + ") as c_uri"),
                            fonteProjects.cStart,
                            StringTemplate.create("(SELECT d.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " d WHERE d.c_id = " + fonteProjects.fkUriProjectgroup + ") as FK_URI_PROJECTGROUP"),
                            StringTemplate.create("CASE WHEN "+fonteProjects.cActive+"= 'true' THEN 1 ELSE 0 END") ,
                            StringTemplate.create("SUBSTRING("+fonteProjects.cLocation+",0,4000)") ,
                            StringTemplate.create("(SELECT e.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " e WHERE e.c_id = " + fonteProjects.fkUriProjectgroup + ") as fk_projectgroup"),
                            StringTemplate.create("(SELECT f.c_pk FROM " + fonteSireSubterraUriMap.getSchemaName() + "." + fonteSireSubterraUriMap.getTableName() + " f WHERE f.c_id = " + fonteProjects.fkUriLead + ") as fk_lead"),
                            StringTemplate.create("to_char("+fonteProjects.cLockworkrecordsdate+", 'yyyy-MM-dd 00:00:00')"),
                            StringTemplate.create("SUBSTRING("+fonteProjects.cName+",0,4000)") ,
                            StringTemplate.create("SUBSTRING("+fonteProjects.cId+",0,4000)") 
                            )
                            );


            Iterator<Tuple> i = project.iterator();
            Object[] el= null;

            while (i.hasNext()) {

                el= ((Tuple)i.next()).toArray();
                
                new SQLInsertClause(OracleConnection, dialect, stgProject)
				.columns(
						stgProject.cTrackerprefix,
						stgProject.cIsLocal,
						stgProject.cPk,
						stgProject.fkUriLead,
						stgProject.cDeleted,
						stgProject.cFinish,
						stgProject.cUri,
						stgProject.cStart,
						stgProject.fkUriProjectgroup,
						stgProject.cActive,
						stgProject.cLocation,
						stgProject.fkProjectgroup,
						stgProject.fkLead,
						stgProject.cLockworkrecordsdate,
						stgProject.cName,
						stgProject.cId,
						stgProject.dataCaricamento,
						stgProject.sireCurrentProjectPk
						)
						.values(
                                el[0] , 
                                el[1] , 
                                el[2] , 
                                StringUtils.getMaskedValue((String)el[3]) , 
                                el[4] , 
                                el[5] , 
                                el[6] , 
                                el[7] , 
                                el[8] , 
                                el[9] , 
                                el[10] ,
                                el[11] ,
                                StringUtils.getMaskedValue((String)el[12]) ,
                                el[13] ,
                                el[14] ,
                                el[15] ,
								DataEsecuzione.getInstance().getDataEsecuzione(),
								StringTemplate.create("CURRENT_PROJECT_SEQ.nextval")
								)
								.execute();

				n_righe_inserite++;

            }
			
			
/*
			Statement ps = pgConnection.createStatement();
			ResultSet rs = ps.executeQuery(
	    			"select  " +
	                "SUBSTRING(prj.c_trackerprefix, 0, 4000) c_trackerprefix, " +
	    			"0 as c_is_local, " +
	    			"subterra1.c_pk as c_pk, " +
	    			"subterra2.c_pk as fk_uri_lead, " +
	    			"case when c_deleted then 1 else 0 end as c_deleted, " +
	    			"prj.c_finish, " +
	    			"prj.c_uri as c_uri, " +
	    			"prf.c_start as c_start, " +
	    			"subterra2.c_pk as fk_uri_projectgroup, " +
	    			"case when prj.c_active then 1 else 0 end as c_active, " +
	    			"SUBSTRING(prj.c_location, 0, 4000) as c_location, " + 
	    			"subterra2.c_pk as fk_projectgroup, " +
	    			"subterra2.c_pk as fk_lead, " +
	    			"prj.c_lockworkrecordsdate,	" + 
	    			"substring(prj.c_name, 0, 4000) as c_name, " +
	    			"substring(prj.c_id, 0, 4000) as c_id " +
	    			"from polarion.project prj " +
	    			"left join subterra_uri_map subterra1 on prj.c_pk = subterra1.c_id " +
					"left join subterra_uri_map subterra2 on prj.fk_lead = subterra2.c_id " +
					"left join subterra_uri_map subterra3 on prj.fk_projectgroup = subterra3.c_id " +
	    			"WHERE " +
	    			"prj.c_location not like 'default:/GRACO%' AND " +
	    			"(length(prj.c_location) - length(REPLACE(prj.c_location, '/', ''))) >= 4"
			);

			while (rs.next()) {
				
				new SQLInsertClause(OracleConnection, dialect, stgProject)
				.columns(
						stgProject.cTrackerprefix,
						stgProject.cIsLocal,
						stgProject.cPk,
						stgProject.fkUriLead,
						stgProject.cDeleted,
						stgProject.cFinish,
						stgProject.cUri,
						stgProject.cStart,
						stgProject.fkUriProjectgroup,
						stgProject.cActive,
						stgProject.cLocation,
						stgProject.fkProjectgroup,
						stgProject.fkLead,
						stgProject.cLockworkrecordsdate,
						stgProject.cName,
						stgProject.cId,
						stgProject.dataCaricamento,
						stgProject.sireCurrentProjectPk
						)
						.values(
								rs.getObject(1) , 
								rs.getObject(2) , 
								rs.getObject(3) , 
								StringUtils.getMaskedValue(rs.getString(4)) , 
								rs.getObject(5) , 
								rs.getObject(6) , 
								rs.getObject(7) , 
								rs.getObject(8) , 
								rs.getObject(9) , 
								rs.getObject(10) , 
								rs.getObject(11) ,
								rs.getObject(12) ,
								StringUtils.getMaskedValue(rs.getString(13)) ,
								rs.getObject(14) ,
								rs.getObject(15) ,
								rs.getObject(16) ,
								DataEsecuzione.getInstance().getDataEsecuzione(),
								StringTemplate.create("CURRENT_PROJECT_SEQ.nextval")
								)
								.execute();

				n_righe_inserite++;
	    	}*/

			OracleConnection.commit();

		}
		catch (Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			
			n_righe_inserite=0;
			throw new DAOException(e);
		}
		finally
		{
			if(cm != null) cm.closeConnection(OracleConnection);
			if(cm != null) cm.closeConnection(pgConnection);
		}

		return n_righe_inserite;
	}

	public static List<Tuple> getC_NameNull(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> project 	= new ArrayList<Tuple>();

		try
		{
			cm 			= ConnectionManager.getInstance();
			connection 	= cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QSireCurrentProject   stgProject 	 	= QSireCurrentProject.sireCurrentProject;

			project = 
					query.from(stgProject)
					.where(stgProject.dataCaricamento.eq(dataEsecuzione))
					.where(stgProject.cName.isNull()).list(stgProject.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return project;
	}

	public static List<Tuple> getAllProjectSW(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> project = new ArrayList<Tuple>();

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QSireCurrentProject   stgProject  = QSireCurrentProject.sireCurrentProject;

			//	select  DMALM_SIRE_CURRENT_PROJECT.* from DMALM_SIRE_CURRENT_PROJECT where ltrim(c_name) like 'SW-%'

			project = 

					query.from(stgProject)
					.where(stgProject.dataCaricamento.eq(dataEsecuzione))
					.where(stgProject.cName.trim().like("SW-%")).list(stgProject.all()) ;

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return project;
	}

	public static List<Tuple> getAllProjectC_Name(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;
		List<Tuple> project = new ArrayList<Tuple>();

		try{
			cm 			= ConnectionManager.getInstance();
			connection 	= cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			SQLQuery query 							= new SQLQuery(connection, dialect); 
			QSireCurrentProject   stgProject  = QSireCurrentProject.sireCurrentProject;

			project = 

					query.from(stgProject)
					.where(stgProject.dataCaricamento.eq(dataEsecuzione))
					.where(stgProject.cName.trim().like("SW-%"))
					.list(
							stgProject.cName, // il campo C_NAME
							StringTemplate.create("INSTR(C_NAME,'{')"), // la posizione del carattere  {
							StringTemplate.create("INSTR(C_NAME,'}')"), // la posizione del carattere  }
							StringTemplate.create("REPLACE(REPLACE(REGEXP_SUBSTR(C_NAME, '{[a-zA-Z0-9_.*!?-]+}'), '{',''),'}','') as name"), // il contenuto delle parentesi { }
							StringTemplate.create("SUBSTR(C_NAME, INSTR(C_NAME,'}')+1, LENGTH(C_NAME)-INSTR(C_NAME,'}') )")); // la descrizione, quello che viene dopo  il caratter "}"

		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		finally
		{
			if(cm != null) cm.closeConnection(connection);
		}

		return project;
	}

	public static void delete(Timestamp dataEsecuzione) throws Exception
	{
		ConnectionManager cm 	= null;
		Connection connection 	= null;


		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect 					= new HSQLDBTemplates(); // SQL-dialect
			QSireCurrentProject   stgProject  = QSireCurrentProject.sireCurrentProject;

			new SQLDeleteClause(connection, dialect, stgProject)
			.where(stgProject.dataCaricamento.lt(dataEsecuzione).or(stgProject.dataCaricamento
					.gt(DateUtils.addDaysToTimestamp(DataEsecuzione.getInstance().getDataEsecuzione(),-1))))
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

	public static void deleteInDate(Timestamp date) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireCurrentProject   stgProject  = QSireCurrentProject.sireCurrentProject;

			new SQLDeleteClause(connection, dialect, stgProject)
			.where(stgProject.dataCaricamento.eq(
					date)).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}

	public static void recoverSireCurrentProject() throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QSireCurrentProject   stgProject  = QSireCurrentProject.sireCurrentProject;

			new SQLDeleteClause(connection, dialect, stgProject)
			.where(stgProject.dataCaricamento.eq(DataEsecuzione.getInstance().getDataEsecuzione())).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}

	}

}