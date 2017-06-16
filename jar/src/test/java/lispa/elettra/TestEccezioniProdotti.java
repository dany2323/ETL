package lispa.elettra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.target.CheckDmalmSourceElProdFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.Log4JConfiguration;

public class TestEccezioniProdotti extends TestCase{
	
	public void testEccezioniProdotti() {
		
		
		try {
			Log4JConfiguration.inizialize();
			ConnectionManager.getInstance().getConnectionOracle();
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			CheckDmalmSourceElProdFacade.execute(dataEsecuzione);
			
			
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (PropertiesReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
