package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.log4j.Logger;

import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;


public class EsitiCaricamentoFacade {



	public static void delete (Logger logger, Timestamp dataEsecuzione) {	
		
		try
		{
			
			logger.debug("START EsitiCaricamentoDAO.delete "+new Date());
			
			EsitiCaricamentoDAO.delete(dataEsecuzione);
			
			logger.debug("STOP EsitiCaricamentoDAO.delete "+new Date());
			
			
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