package lispa;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckChangingWorkitemFacade;
import lispa.schedulers.manager.Log4JConfiguration;

public class TestChangedWI extends TestCase{


	public void testFirst() throws DAOException, PropertiesReaderException {
		
		Log4JConfiguration.inizialize();

		CheckChangingWorkitemFacade.execute();

		//		List<Total> totals = TotalDao.changedWorkitemList(DmAlmConstants.REPOSITORY_SIRE);
		//		List<Tuple> cwihistory = null;
		//		List<Tuple> filteredcwhistory = null;
		//		for(Total t : totals) {
		//			cwihistory = TotalDao.getHistorySingleChangedWI(t.getCodice());
		//			filteredcwhistory = TotalDao.filterChangedWiHisory(cwihistory);
		//		}
		//	}

	}
}
