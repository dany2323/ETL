package lispa.schedulers.runnable.staging.edma;

import java.sql.SQLException;
import java.util.Date;

import lispa.schedulers.dao.edma.UnitaOrganizzativaDAO;
import lispa.schedulers.exception.DAOException;

import org.apache.log4j.Logger;

public class UnitaOrganizzativaRunnable implements Runnable {

	private Logger logger;
	
	public UnitaOrganizzativaRunnable(Logger logger) {
		this.logger = logger;
	}
	
	@Override
	public void run() {
		try {
			logger.debug("START fillUnitaOrganizzativa "+new Date());
			UnitaOrganizzativaDAO.fillUnitaOrganizzativa();
			logger.debug("STOP fillUnitaOrganizzativa "+new Date());
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
