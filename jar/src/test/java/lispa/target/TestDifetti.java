package lispa.target;
import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.fatti.DifettoProdottoFacade;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

public class TestDifetti
extends TestCase 
{

	
	public void testFillDifettiProdotto() throws Exception 
	{
		Log4JConfiguration.inizialize();
	
//		Logger logger = Logger.getLogger(DmAlmFillTarget.class); 
		
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp("2015-02-17 21:00:00", "yyyy-MM-dd HH:mm:00");
		
		
		

		DifettoProdottoFacade.execute( dataEsecuzione);
		
		
//		
	
	}
}