package lispa.target;

import java.sql.Timestamp;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaSupporto;
import lispa.schedulers.dao.target.RichiestaSupportoOdsDAO;
import lispa.schedulers.dao.target.fatti.RichiestaSupportoDAO;
import lispa.schedulers.facade.target.fatti.RichiestaSupportoFacade;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

public class TestRichiestaSupporto extends TestCase {
	
	private Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
			"2019-02-15 23:50:00", "yyyy-MM-dd HH:mm:00");
	
	public void testFillRichiestaSupporto() throws Exception {
		Log4JConfiguration.inizialize();
//		RichiestaSupportoFacade.execute(dataEsecuzione);
//		DmalmRichiestaSupporto richiesta = RichiestaSupportoDAO.getRichiestaSupporto(12716201);
//		DmalmProject p = new DmalmProject();
//		p.setDmalmProjectPk(51159);
//		RichiestaSupportoDAO.checkEsistenzaRichiestaSupporto(richiesta, p);
		List<DmalmRichiestaSupporto> x = RichiestaSupportoOdsDAO.getAll();
		
		for(DmalmRichiestaSupporto richiesta : x) {
			
			RichiestaSupportoDAO.checkAlteredRichiestaSupporto(richiesta, dataEsecuzione);
			
		}
	}
	

}
