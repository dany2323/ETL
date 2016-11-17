package lispa.schedulers.runnable.staging.siss.current;

import java.sql.SQLException;
import java.util.Date;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.svn.ProjectRolesXML;

import org.apache.log4j.Logger;

public class SissProjectRolesRunnable implements Runnable {
	
	private Logger logger;
	
	public SissProjectRolesRunnable(Logger logger) {
		
		this.logger = logger;
		
	}

	@Override
	public void run() {
		
		try {
			logger.debug("START  fillProjectRoles SISS "+ new Date());
			ProjectRolesXML.fillProjectRoles(DmAlmConstants.REPOSITORY_SISS);
			logger.debug("STOP  fillProjectRoles SISS "+ new Date());
		}
		catch (DAOException e) 
		{
			logger.error(e.getMessage(), e);
			
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage(), e);
			
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
			
		}
		
	}

}
