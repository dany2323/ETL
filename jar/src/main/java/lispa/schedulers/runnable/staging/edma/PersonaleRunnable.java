package lispa.schedulers.runnable.staging.edma;

import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.edma.PersonaleDAO;
import lispa.schedulers.exception.DAOException;

public class PersonaleRunnable implements Runnable {
	
	private Logger logger;
	
	public PersonaleRunnable(Logger logger) {
		this.logger = logger;
	}

	@Override
	public void run() {
		try {
			logger.debug("START fillPersonale "+new Date());
			PersonaleDAO.fillPersonale();
			logger.debug("STOP fillPersonale "+new Date());
		}
		catch (DAOException e) 
		{
			logger.error(e.getMessage(), e);
			
		}
		catch(SQLException e)
		{
			logger.error(e.getMessage(), e);
			
		}
	}

}
