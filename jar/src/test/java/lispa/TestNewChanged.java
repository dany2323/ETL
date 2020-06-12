package lispa;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckChangingWorkitemFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.utils.DateUtils;

public class TestNewChanged extends TestCase {
	
	public void testFirst() throws DAOException, PropertiesReaderException {
		DmAlmConfigReaderProperties.setFileProperties("C:/Datamart/props/dm_alm_pg.properties");
		DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2019-06-03 21:00:00", "yyyy-MM-dd HH:mm:00"));
		CheckChangingWorkitemFacade.execute();
	}
	
}
