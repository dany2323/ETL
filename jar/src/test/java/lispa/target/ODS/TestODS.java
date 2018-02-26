package lispa.target.ODS;

import java.sql.Timestamp;
import lispa.schedulers.facade.target.fatti.DocumentoFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

import org.junit.Test;

public class TestODS {
private Timestamp dataEsecuzione = DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2018-01-09 12:16:00", "yyyy-MM-dd HH:mm:00"));
	@Test
	public void testDelete() throws Exception {
		Log4JConfiguration.inizialize();
			DocumentoFacade.execute( dataEsecuzione);
			
	}

}
