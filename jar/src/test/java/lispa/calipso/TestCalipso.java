package lispa.calipso;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_CALIPSO_PATH;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_CALIPSO_SCHEDA_SERVIZIO_EXCEL;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_CALIPSO_SOURCE_PATH_FILE;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_STAGING_DAY_DELETE;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import junit.framework.TestCase;
import lispa.schedulers.bean.staging.calipso.DmalmStgCalipsoSchedaServizio;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.calipso.staging.StagingCalipsoFacade;
import lispa.schedulers.facade.calipso.target.CalipsoSchedaServizioFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;

public class TestCalipso extends TestCase{
	
	private static Logger logger = Logger.getLogger(TestCalipso.class);
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
			
//			CalipsoSchedaServizioFacade.execute(DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2019-06-14 00:00:00", "yyyy-MM-dd HH:mm:00")));
//			putExcelCalipso();
			getDataExcelCalipso();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void putExcelCalipso() throws IOException, PropertiesReaderException {
		
		File file = new File(DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_PATH));
		String renameFile = "mv ";
		String wgetFile = "wget ";
		String chmod = "chmod 755 ";
		String fileSourceCalipso = DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_SOURCE_PATH_FILE) + DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_SCHEDA_SERVIZIO_EXCEL);
		String fileCalipso = DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_PATH) + DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_SCHEDA_SERVIZIO_EXCEL);
		logger.debug("START StagingCalipsoFacade.putExcelCalipso");
		
		try {
			Runtime.getRuntime().exec(renameFile + fileCalipso + " " + fileCalipso + "." + DateUtils.dateToString(DataEsecuzione.getInstance().getDataEsecuzione(), "yyyy-MM-dd").replace("-", "_")).waitFor();
			Runtime.getRuntime().exec(wgetFile + fileSourceCalipso, null, file).waitFor();
			Runtime.getRuntime().exec(chmod + fileCalipso).waitFor();
		} catch (InterruptedException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
		
		logger.debug("STOP StagingCalipsoFacade.putExcelCalipso");
	}
	
	private static List<DmalmStgCalipsoSchedaServizio> getDataExcelCalipso() throws IOException, PropertiesReaderException {
		List<DmalmStgCalipsoSchedaServizio> listExcelCalipso = new ArrayList<>();
		XSSFWorkbook wb = null;
		
		try {
			logger.debug("START StagingCalipsoFacade.getDataExcelCalipso");
			String fileCalipso = DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_PATH) + DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_SCHEDA_SERVIZIO_EXCEL);
			File fCalipso = new File(fileCalipso);
			if (fCalipso.exists()) {
				logger.debug("File Calipso found!");
				wb = new XSSFWorkbook(fileCalipso);
			    
			    XSSFSheet sheet = wb.getSheet(DmAlmConstants.CALIPSO_SHEET_NAME_SCHEDA_SERVIZIO);
			    XSSFRow row;
			    int rows = sheet.getPhysicalNumberOfRows();
			} else {
				logger.debug("File Calipso not found!");
			}
			


		    logger.debug("STOP StagingCalipsoFacade.getDataExcelCalipso");
		    
		} catch(IOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (wb != null) {
				wb.close();
			}
		}
		
		return listExcelCalipso;
	}
}
