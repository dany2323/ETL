package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.util.Date;

import lispa.schedulers.dao.target.CurrentWorkitemsDAO;

import org.apache.log4j.Logger;

public class FillCurrentWorkitems {
	
	
	public static void execute (Logger logger)  {
		
		try {
			logger.debug("START fillCurrentWorkitems "+new Date());
			CurrentWorkitemsDAO.fillSissCurrentWorkitems();
			CurrentWorkitemsDAO.fillSireCurrentWorkitems();
			logger.debug("STOP fillCurrentWorkitems "+new Date());
		}
		
		catch(SQLException e)
		{
			logger.error(e.getMessage(), e);
			
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		
	}
	
public static void delete (Logger logger)  {
		
		try {
			logger.debug("START deleteCurrentWorkitems "+new Date());
			CurrentWorkitemsDAO.deleteCurrentWorkitems();
			logger.debug("STOP deleteCurrentWorkitems "+new Date());
		}
		
		catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		
	}

}
