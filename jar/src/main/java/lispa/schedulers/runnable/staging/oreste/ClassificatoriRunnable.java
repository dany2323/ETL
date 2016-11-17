package lispa.schedulers.runnable.staging.oreste;

import java.sql.SQLException;
import java.util.Date;

import lispa.schedulers.dao.oreste.ClassificatoriDAO;
import lispa.schedulers.exception.DAOException;

import org.apache.log4j.Logger;

public class ClassificatoriRunnable implements Runnable {
	
	private Logger logger;
	
	public ClassificatoriRunnable(Logger logger) {
		
		this.logger = logger;
		
	}

	@Override
	public void run() {
		
		try {
			
			logger.debug("START fillClassificatori  "+new Date());
			
			ClassificatoriDAO.fillClassificatori();
			
			logger.debug("STOP  fillClassificatori"+new Date());
			
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
