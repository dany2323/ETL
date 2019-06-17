package lispa.calipso;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_STAGING_DAY_DELETE;

import java.sql.Timestamp;
import junit.framework.TestCase;
import lispa.schedulers.facade.calipso.staging.StagingCalipsoFacade;
import lispa.schedulers.facade.calipso.target.CalipsoSchedaServizioFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

public class TestCalipso extends TestCase{
	
	private static int days;
	
	public void testCalipsoStaging() {
		
		try {
			DmAlmConfigReaderProperties.setFileProperties("/Users/lucaporro/LISPA/DataMart/props/dm_alm.properties");
			Log4JConfiguration.inizialize();
//			Timestamp dataEsecuzione = DataEsecuzione.getInstance().getDataEsecuzione();
//			ConnectionManager.getInstance().getConnectionOracle();
//			days = Integer.parseInt(DmAlmConfigReader.getInstance()
//					.getProperty(DMALM_STAGING_DAY_DELETE));
//			
//			// Cancello i record piu vecchi di X giorni
//			final Timestamp dataEsecuzioneDeleted = DateUtils.getAddDayToDate(-days);
//			
//			StagingCalipsoFacade.executeStaging(dataEsecuzioneDeleted);
			
			CalipsoSchedaServizioFacade.execute(DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2019-06-14 00:00:00", "yyyy-MM-dd HH:mm:00")));
						
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
