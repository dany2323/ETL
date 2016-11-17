package lispa.schedulers.runnable.staging.sire.current;

import java.sql.SQLException;
import java.util.Date;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.svn.StatoWorkItemXML;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

public class SireStatoWorkitemRunnable implements Runnable {
	
	private Logger logger;
	
	public SireStatoWorkitemRunnable(Logger logger) {
		
		this.logger = logger;
		
	}

	@Override
	public void run() {
		
		try {
			for(Workitem_Type type : Workitem_Type.values()) {
				logger.debug("START  fillStatoWorkItem SIRE " + type.toString() + " " + new Date());
				StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SIRE, type);
				logger.debug("STOP  fillStatoWorkItem  SIRE " + type.toString() + " " + new Date());
			}
		}
		catch (DAOException e) 
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
		}
		catch(SQLException e)
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
		}
		catch(Exception e)
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
		}
		
	}

}
