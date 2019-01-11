package lispa.schedulers.facade.calipso.staging;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_CALIPSO_SOURCE_PATH_FILE;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_CALIPSO_PATH;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_CALIPSO_SCHEDA_SERVIZIO_EXCEL;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import lispa.schedulers.bean.staging.calipso.DmalmStgCalipsoSchedaServizio;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.calipso.StgCalipsoSchedaServizioDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class StagingCalipsoFacade {

	private static Logger logger = Logger.getLogger(StagingCalipsoFacade.class);
	
	public static void executeStaging(Timestamp dataEsecuzioneDelete) {
		try {
			
			if (ErrorManager.getInstance().hasError())
				return;

			logger.info("START StagingCalipsoFacade.executeStaging");

			deleteDmAlmStagingFromExcel();
			fillDmAlmStagingFromExcel();

			deleteStaging(dataEsecuzioneDelete);
			fillStaging();

			logger.info("STOP StagingCalipsoFacade.executeStaging");
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		}
	}

	private static void deleteDmAlmStagingFromExcel() {

		try {
			logger.debug("START CALIPSO deleteDmAlmStagingFromExcel");
			StgCalipsoSchedaServizioDAO.deleteDmAlmStagingFromExcel();
			logger.debug("STOP CALIPSO deleteDmAlmStagingFromExcel");

		} catch (DAOException | SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}
	
	private static void fillDmAlmStagingFromExcel() throws PropertiesReaderException {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START StagingCalipsoFacade.fillDmAlmStagingFromExcel");
			putExcelCalipso();
			List<DmalmStgCalipsoSchedaServizio> listExcelCalipso = getDataExcelCalipso();
			StgCalipsoSchedaServizioDAO.fillDmAlmStagingFromExcel(listExcelCalipso);
			
			logger.debug("STOP StagingCalipsoFacade.fillDmAlmStagingFromExcel");
		} catch (IOException | DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
	
	private static void deleteStaging(Timestamp dataEsecuzioneDelete) {

		try {
			logger.debug("START CALIPSO deleteStaging");
			StgCalipsoSchedaServizioDAO.deleteStaging(dataEsecuzioneDelete);
			logger.debug("STOP CALIPSO deleteStaging");

		} catch (DAOException | SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}
	
	private static void fillStaging() {
		try {
			if (ErrorManager.getInstance().hasError())
				return;

			logger.debug("START StagingCalipsoFacade.fillStaging");

			StgCalipsoSchedaServizioDAO.fillStaging();
			
			logger.debug("STOP StagingCalipsoFacade.fillStaging");
		} catch (DAOException | SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
	
	private static void putExcelCalipso() throws IOException, PropertiesReaderException {
		
		File file = new File(DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_PATH));
		String deleteFile = "rm -r ";
		String wgetFile = "wget ";
		String chmod = "chmod 755 ";
		String fileSourceCalipso = DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_SOURCE_PATH_FILE) + DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_SCHEDA_SERVIZIO_EXCEL);
		String fileCalipso = DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_PATH) + DmAlmConfigReader.getInstance().getProperty(DMALM_CALIPSO_SCHEDA_SERVIZIO_EXCEL);
		logger.debug("START StagingCalipsoFacade.putExcelCalipso");
		
		try {
			Runtime.getRuntime().exec(deleteFile + fileCalipso).waitFor();
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
			wb = new XSSFWorkbook(fileCalipso);
		    
		    XSSFSheet sheet = wb.getSheet(DmAlmConstants.CALIPSO_SHEET_NAME_SCHEDA_SERVIZIO);
		    XSSFRow row;
		    int rows = sheet.getPhysicalNumberOfRows();

		    for(int r = 6; r < rows; r++) {
		    		row = sheet.getRow(r);
			    if(row != null && row.getCell(2) != null && !row.getCell(2).getCellTypeEnum().equals(CellType.BLANK)) {
				    	DmalmStgCalipsoSchedaServizio excelCalipso = new DmalmStgCalipsoSchedaServizio();
				    	excelCalipso.setCodiceServizio(getDataCell(row.getCell(2)));
				    	excelCalipso.setServizioBusiness(getDataCell(row.getCell(3)));
				    	excelCalipso.setAmbito(getDataCell(row.getCell(4)));
				    	excelCalipso.setCliente(getDataCell(row.getCell(5)));
				    	excelCalipso.setResponsabileStruttura(getDataCell(row.getCell(6)));
				    	excelCalipso.setResponsabileServizio(getDataCell(row.getCell(7)));
				    	excelCalipso.setResponsabileGestione(getDataCell(row.getCell(8)));
				    	excelCalipso.setReferenteGestione(getDataCell(row.getCell(9)));
				    	excelCalipso.setReferenteApplicazione(getDataCell(row.getCell(10)));
				    	excelCalipso.setSoftwareSupporto(getDataCell(row.getCell(11)));
				    	excelCalipso.setAmbitoManutenzioneOrdinarSW(getDataCell(row.getCell(12)));
				    	excelCalipso.setTipologiaIncarico(getDataCell(row.getCell(13)));
				    	excelCalipso.setSchedaIncarico(getDataCell(row.getCell(14)));
				    	excelCalipso.setStatoServizio(getDataCell(row.getCell(15)));
				    	excelCalipso.setTipologiaInfrastruttura(getDataCell(row.getCell(16)));
				    	excelCalipso.setClasseServizioInfrstrttrl(getDataCell(row.getCell(17)));
				    	excelCalipso.setAmbitoAssiGestRL(getDataCell(row.getCell(18)));
				    	excelCalipso.setDataUltimoAggiornamento(getDataCell(row.getCell(19)));
				    listExcelCalipso.add(excelCalipso);
			    }
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
	
	private static String getDataCell(XSSFCell cell) throws IOException {
		String cellValue = "";
		if (cell != null) {
			switch(cell.getCellTypeEnum()) {
				case STRING : cellValue = cell.getStringCellValue().replaceAll("[\n\r]", " ");
					break;
				case BLANK: cellValue = null;
					break;
				case BOOLEAN: cellValue = String.valueOf(cell.getBooleanCellValue());
					break;
				case ERROR: throw new IOException();
				case FORMULA: 
					switch(cell.getCachedFormulaResultTypeEnum()) {
	        				case NUMERIC: 
	        					if(HSSFDateUtil.isCellDateFormatted(cell)) {
	        						SimpleDateFormat format = new SimpleDateFormat("dd-mm-yy");
	        						cellValue = String.valueOf(format.format(cell.getDateCellValue()));
	        					} else {
	        						cellValue = String.valueOf(cell.getNumericCellValue());
	        					}
	        					break;
	        				case STRING: cellValue = cell.getStringCellValue().replaceAll("[\n\r]", " ");
	        					break;
	        				case ERROR: throw new IOException();
	        				default:
	        					break;
					}
					break;
				case NUMERIC: 
					if(HSSFDateUtil.isCellDateFormatted(cell)) {
						SimpleDateFormat format = new SimpleDateFormat("dd-MMM-yy");
						cellValue = String.valueOf(format.format(cell.getDateCellValue()));
					} else {
						cellValue = String.valueOf(cell.getNumericCellValue());
					}
					break;
				default:
					break;
			}
		}
		return cellValue;
	}
}
