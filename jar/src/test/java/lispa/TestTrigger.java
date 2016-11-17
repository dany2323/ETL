package lispa;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.facade.target.ProdottoFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

public class TestTrigger extends TestCase {
	private static Logger logger = Logger.getLogger(TestTrigger.class);
	private Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.setDataEsecuzione(
					DateUtils.stringToTimestamp("2014-10-28 12:00:00",
							"yyyy-MM-dd HH:mm:00"));

	public void testTriggerProdotto() {
		try {
			ProdottoFacade.execute(dataEsecuzione);
		} catch (Exception e) {
			logger.debug(e);
		}
	}
}
