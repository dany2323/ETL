package lispa.target.ODS;

import java.sql.Timestamp;
import lispa.schedulers.facade.target.fatti.DocumentoFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.utils.DateUtils;

import org.junit.Test;

public class TestODS {
private Timestamp dataEsecuzione = DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2014-10-17 10:54:00", "yyyy-MM-dd HH:mm:00"));
	@Test
	public void testDelete() throws Exception {
			DocumentoFacade.execute( dataEsecuzione);
			
	}

}
