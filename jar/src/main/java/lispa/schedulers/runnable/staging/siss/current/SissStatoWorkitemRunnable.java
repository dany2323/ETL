package lispa.schedulers.runnable.staging.siss.current;

import java.sql.SQLException;
import java.util.Date;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.svn.StatoWorkItemXML;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

public class SissStatoWorkitemRunnable implements Runnable {
	
	private Logger logger;
	
	public SissStatoWorkitemRunnable(Logger logger) {
		
		this.logger = logger;
		
	}

	@Override
	public void run() {
		
		try {
			for(Workitem_Type type : Workitem_Type.values()) {
				logger.debug("START  fillStatoWorkItem SISS "+ type.toString() + " " + new Date());
				StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SISS, type);
				logger.debug("STOP  fillStatoWorkItem SISS " + type.toString() + " " + new Date());
			}
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
