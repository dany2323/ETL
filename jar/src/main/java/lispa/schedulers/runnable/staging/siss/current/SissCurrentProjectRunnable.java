package lispa.schedulers.runnable.staging.siss.current;

import java.sql.SQLException;
import java.util.Date;

import lispa.schedulers.dao.sgr.siss.current.SissCurrentProjectDAO;
import lispa.schedulers.exception.DAOException;

import org.apache.log4j.Logger;

public class SissCurrentProjectRunnable implements Runnable {
	
	private Logger logger;
	
	public SissCurrentProjectRunnable(Logger logger) {
		
		this.logger = logger;
		
	}

	@Override
	public void run() {
		try {
			logger.debug("START fillSissCurrentProject "+ new Date());
			SissCurrentProjectDAO.fillSissCurrentProject();
			logger.debug("STOP  fillSissCurrentProject "+ new Date());
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
