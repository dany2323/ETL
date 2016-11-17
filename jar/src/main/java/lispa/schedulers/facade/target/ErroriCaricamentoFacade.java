package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;


public class ErroriCaricamentoFacade {



	public static void delete (Logger logger, Timestamp dataEsecuzione) {	
		
		try
		{
			
			logger.debug("START ErroriCaricamentoDAO.delete "+new Date());
			
			ErroriCaricamentoDAO.delete(dataEsecuzione);
			
			logger.debug("STOP ErroriCaricamentoDAO.delete "+new Date());
			
			
		}
		catch (DAOException e) 
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
		}
		catch(SQLException e)
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
		}
		
	}
	
}