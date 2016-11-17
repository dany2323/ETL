package lispa.schedulers.runnable.staging.oreste;

import java.sql.SQLException;
import java.util.Date;

import lispa.schedulers.dao.oreste.FunzionalitaDAO;
import lispa.schedulers.exception.DAOException;

import org.apache.log4j.Logger;

public class FunzionalitaRunnable implements Runnable {
	
	private Logger logger;
	
	public FunzionalitaRunnable(Logger logger) {
		
		this.logger = logger;
		
	}

	@Override
	public void run() {
		
		try {
			
			logger.debug("START fillFunzionalita  "+new Date());
			
			FunzionalitaDAO.fillFunzionalita();
			
			logger.debug("STOP  fillFunzionalita "+new Date());
			
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
