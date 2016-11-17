package lispa.sgr;

import java.sql.SQLException;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiSGRCMFacade;
import lispa.schedulers.facade.target.FillCurrentWorkitems;
import lispa.schedulers.manager.Log4JConfiguration;

import org.apache.log4j.Logger;


public class testCurrentWI extends TestCase {
	private static Logger logger = Logger.getLogger(testCurrentWI.class); 
	
	public static void testStgCurrentWI() throws DAOException, PropertiesReaderException, SQLException {
		Log4JConfiguration.inizialize();
		FillCurrentWorkitems.delete(logger);
		FillCurrentWorkitems.execute(logger);
		CheckAnnullamentiSGRCMFacade.annullaWorkitemDeleted();
		
		


		
	}


}
