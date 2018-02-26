package lispa.sfera;

import junit.framework.TestCase;
import lispa.schedulers.facade.sfera.CheckLinkSferaSgrCmFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.utils.DateUtils;

import java.sql.Timestamp;

import org.junit.Test;

public class testCheckLinkSferaSGR extends TestCase {

	@Test
	public void test() {
		 Timestamp dataEsecuzione = DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2018-02-21 22:40:00.0",
		 "yyyy-MM-dd HH:mm:00"));
		
		
		 CheckLinkSferaSgrCmFacade.execute(dataEsecuzione);
	}
}
