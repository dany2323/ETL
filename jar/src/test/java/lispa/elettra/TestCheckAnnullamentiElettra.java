package lispa.elettra;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mysema.query.Tuple;

import junit.framework.TestCase;
import lispa.schedulers.bean.target.DmalmAsmProject;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.elettra.DmalmElProdottiArchitetture;
import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoDem;
import lispa.schedulers.dao.target.DmAlmProjectProdottoDAO;
import lispa.schedulers.dao.target.DmAlmSourceElProdEccezDAO;
import lispa.schedulers.dao.target.elettra.ElettraPersonaleDAO;
import lispa.schedulers.dao.target.elettra.ElettraProdottiArchitettureDAO;
import lispa.schedulers.dao.target.fatti.ProgettoSviluppoDemandDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiElettraFacade;
import lispa.schedulers.facade.target.CheckDmalmSourceElProdFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.elettra.QDmAlmSourceElProdEccez;
import lispa.schedulers.utils.DateUtils;

public class TestCheckAnnullamentiElettra extends TestCase{
	
	private static QDmAlmSourceElProdEccez dmAlmSourceElProdEccez= QDmAlmSourceElProdEccez.dmAlmSourceElProd;

	public void testEccezioniProdotti() throws Exception {
		
		
		try {
			Log4JConfiguration.inizialize();
			ConnectionManager.getInstance().getConnectionOracle();
			//Timestamp dataOggi = DateUtils.stringToTimestamp("2017-08-01 07:16:35","yyyy-MM-dd HH:mm:ss");
			Timestamp dataOggi = DateUtils.stringToTimestamp("2017-08-02 12:30:00","yyyy-MM-dd HH:mm:ss");
			//Timestamp dataIeri = DateUtils.getDtPrecedente(dataOggi);
			Timestamp dataIeri = DateUtils.stringToTimestamp("2017-07-20 10:23:00","yyyy-MM-dd HH:mm:ss");
			
			CheckAnnullamentiElettraFacade.checkAnnullamentiElUnitaOrganizzativa(dataOggi, dataIeri);
			
		} catch (DAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  catch (PropertiesReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
