package lispa.schedulers.runnable.staging.oreste;

import java.sql.SQLException;
import java.util.Date;

import lispa.schedulers.dao.oreste.AmbienteTecnologicoDAO;
import lispa.schedulers.exception.DAOException;

import org.apache.log4j.Logger;

public class AmbientiTecnologiciRunnable implements Runnable {
	
	private Logger logger;
	
	public AmbientiTecnologiciRunnable(Logger logger) {
		
		this.logger = logger;
		
	}

	@Override
	public void run() {
		
		try {
			
			logger.debug("START fillAmbienteTecnologico  "+new Date());
			
			AmbienteTecnologicoDAO.fillAmbienteTecnologico();
			
			logger.debug("STOP  fillAmbienteTecnologico "+new Date());	
			
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
