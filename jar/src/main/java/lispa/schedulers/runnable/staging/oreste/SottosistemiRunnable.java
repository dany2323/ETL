package lispa.schedulers.runnable.staging.oreste;

import java.sql.SQLException;
import java.util.Date;

import lispa.schedulers.dao.oreste.SottosistemiDAO;
import lispa.schedulers.exception.DAOException;

import org.apache.log4j.Logger;

public class SottosistemiRunnable implements Runnable {
	
	private Logger logger;

	public SottosistemiRunnable(Logger logger) {
		
		this.logger = logger;
		
	}
	
	@Override
	public void run() {
		
		try {
			
			logger.debug("START fillSottosistemi  "+new Date());
			
			SottosistemiDAO.fillSottosistemi();
			
			logger.debug("STOP  fillSottosistemi "+new Date());
			
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
