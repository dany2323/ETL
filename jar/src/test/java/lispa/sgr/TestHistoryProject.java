package lispa.sgr;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DM_ALM_USER;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.PROPERTIES_READER_FILE_NAME;

import java.sql.Timestamp;

import org.apache.log4j.Logger;
import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.PropertiesReader;
import lispa.schedulers.utils.DateUtils;

public class TestHistoryProject extends TestCase {
	
	private Logger logger = Logger.getLogger(TestHistoryProject.class);

	public void testFillStaging() throws Exception {
		DmAlmConfigReaderProperties.setFileProperties("/Users/lucaporro/LISPA/DataMart/props/dm_alm.properties");
		Log4JConfiguration.inizialize();
		logger.debug("TestHistoryProject");
		PropertiesReader propertiesReader = new PropertiesReader(PROPERTIES_READER_FILE_NAME);
		logger.debug(propertiesReader.getProperty(DM_ALM_USER));
		DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2019-04-30 10:20:00", "yyyy-MM-dd HH:mm:00"));
		//SISS
		long project_minRevision_siss = SissHistoryProjectDAO.getMinRevision();
		long polarion_maxRevision_siss = SissHistoryRevisionDAO.getMaxRevision();
//		SissHistoryProjectDAO.fillSissHistoryProject(project_minRevision_siss, polarion_maxRevision_siss);
		
		//SIRE
		long project_minRevision_sire = SireHistoryProjectDAO.getMinRevision();
		long polarion_maxRevision_sire = SireHistoryRevisionDAO.getMaxRevision();
//		SireHistoryProjectDAO.fillSireHistoryProject(project_minRevision_sire, polarion_maxRevision_sire);
		Timestamp dataEsecuzione = DataEsecuzione.getInstance().getDataEsecuzione();
		ProjectSgrCmFacade.execute(dataEsecuzione);
	}
}
