package lispa.schedulers.runnable.staging.oreste;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.oreste.ModuliDAO;
import lispa.schedulers.exception.DAOException;

public class ModuliRunnable implements Runnable {
	
	private Logger logger;
	
	public ModuliRunnable(Logger logger) {
		
		this.logger = logger;
		
	}

	@Override
	public void run() {
		
		try {
			
			logger.debug("START fillModuli  "+new Date());
			
			ModuliDAO.fillModuli();
			
			logger.debug("STOP  fillModuli "+new Date());
			
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
