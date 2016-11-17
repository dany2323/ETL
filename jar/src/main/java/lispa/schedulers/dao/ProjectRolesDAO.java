package lispa.schedulers.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmProjectRoles;
import lispa.schedulers.utils.DateUtils;

public class ProjectRolesDAO
{


	private static Logger logger = Logger.getLogger(ProjectRolesDAO.class); 

	public static void delete( Timestamp dataCaricamento, String repository) throws DAOException, SQLException
	{

		ConnectionManager cm = null;
		Connection connection = null;

		try{
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			QDmAlmProjectRoles projectroles = QDmAlmProjectRoles.dmAlmProjectRoles;

			SQLTemplates dialect = new HSQLDBTemplates(); 

			new SQLDeleteClause(connection, dialect, projectroles)
			.where(projectroles.repository.equalsIgnoreCase(repository))
			.where(projectroles.dataCaricamento.lt(dataCaricamento).or(projectroles.dataCaricamento
					.gt(DateUtils.addDaysToTimestamp(DataEsecuzione.getInstance().getDataEsecuzione(),-1))))
			.execute();

			connection.commit();

		}
		catch (Exception e)
		{
			logger.error(e.getMessage(), e);
			
			
			throw new DAOException(e);
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
			
			QDmAlmProjectRoles projectroles = QDmAlmProjectRoles.dmAlmProjectRoles;
			
			new SQLDeleteClause(connection, dialect, projectroles)
					.where(projectroles.dataCaricamento.eq(
							date)).execute();

		} catch (Exception e) 
		{
			
		} 
		finally 
		{
			if(cm != null) cm.closeConnection(connection);
		}
	}





}