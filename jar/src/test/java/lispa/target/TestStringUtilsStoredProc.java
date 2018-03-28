package lispa.target;

import java.sql.Timestamp;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.utils.StringUtils;

public class TestStringUtilsStoredProc extends TestCase{
	
	public static void testT_tables() {
		try {
			Log4JConfiguration.inizialize();
			List<String> arr = StringUtils.getLogFromStoredProcedureByTimestamp(DmAlmConstants.STORED_PROCEDURE_VERIFICA_ESITO_ETL);
		} catch (PropertiesReaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
