package lispa.target;

import java.sql.Timestamp;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.target.fatti.PeiFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.utils.DateUtils;

public class TestUri extends TestCase {
	public static void testUri() throws DAOException, Exception {
		Timestamp dataEsecuzione = DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2014-11-12 10:41:00.0", "yyyy-MM-dd HH:mm:00"));
	

		
		PeiFacade.execute(dataEsecuzione);
		
		
	}

}
