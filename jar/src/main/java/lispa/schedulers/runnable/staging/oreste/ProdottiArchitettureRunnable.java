package lispa.schedulers.runnable.staging.oreste;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.exception.DAOException;

public class ProdottiArchitettureRunnable implements Runnable {
	
	private Logger logger;
	
	public ProdottiArchitettureRunnable(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void run() {
		
		try {
		
			logger.debug("START fillProdottiArchitetture  "+new Date());
		
			ProdottiArchitettureDAO.fillProdottiArchitetture();
		
			logger.debug("STOP  fillProdottiArchitetture "+new Date());
		
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
