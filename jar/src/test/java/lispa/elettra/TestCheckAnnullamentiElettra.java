package lispa.elettra;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiElettraFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.Log4JConfiguration;

public class TestCheckAnnullamentiElettra extends TestCase{
	
	public void testEccezioniProdotti() throws Exception {
		
		
		try {
			Log4JConfiguration.inizialize();
			ConnectionManager.getInstance().getConnectionOracle();
//			//Timestamp dataOggi = DateUtils.stringToTimestamp("2017-08-01 07:16:35","yyyy-MM-dd HH:mm:ss");
//			Timestamp dataOggi = DateUtils.stringToTimestamp("2017-08-02 12:30:00","yyyy-MM-dd HH:mm:ss");
//			//Timestamp dataIeri = DateUtils.getDtPrecedente(dataOggi);
//			Timestamp dataIeri = DateUtils.stringToTimestamp("2017-07-20 10:23:00","yyyy-MM-dd HH:mm:ss");
			
			//CheckAnnullamentiElettraFacade.execute();
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (PropertiesReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
