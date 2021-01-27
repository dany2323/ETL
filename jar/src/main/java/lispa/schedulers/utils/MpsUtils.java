package lispa.schedulers.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Date;

import lispa.schedulers.constant.DmAlmConstants;

import org.apache.log4j.Logger;

public class MpsUtils {
	private static Logger logger = Logger.getLogger(MpsUtils.class);

//	public static String currentMpsFile(String nomeFile) {
//		return currentMpsFile(nomeFile, null);
//	}

//	public static String currentMpsFile(String nomeFile,
//			String nomeFileException) {
//		logger.debug("START MpsUtils.currentMpsFile");
//
//		try {
//			File mps_directory = new File(DmAlmConstants.MPS_PATH);
//			final String complete_name = DmAlmConstants.MPS_PREFISSO_AMBIENTE
//					+ nomeFile;
//			final String complete_name_exc = DmAlmConstants.MPS_PREFISSO_AMBIENTE
//					+ nomeFileException;
//
//			logger.debug("MpsUtils.currentMpsFile - DmAlmConstants.MPS_PATH: "
//					+ DmAlmConstants.MPS_PATH);
//			logger.debug("MpsUtils.currentMpsFile - DmAlmConstants.MPS_PREFISSO_AMBIENTE: "
//					+ DmAlmConstants.MPS_PREFISSO_AMBIENTE);
//			logger.debug("MpsUtils.currentMpsFile - nomeFile: " + complete_name);
//
//			FilenameFilter fnf = new FilenameFilter() {
//				@Override
//				public boolean accept(File dir, String name) {
//					return name.contains(complete_name)
//							&& (complete_name_exc == null || !name
//									.contains(complete_name_exc));
//				}
//			};
//
//			Date selectedDateTime = null;
//			Date currentDateTime = null;
//			String selectedFileTime = null;
//			String currentFileTime = null;
//			File selectedFile = null;
//			String[] current_list = mps_directory.list(fnf);
//
//			logger.debug("MpsUtils.currentMpsFile - current_list.length: "
//					+ (current_list == null ? "NULL" : current_list.length));
//
//			if (current_list != null && current_list.length > 0) {
//				for (String current : current_list) {
//					if (selectedDateTime == null) {
//						selectedFile = new File(mps_directory
//								+ System.getProperty("file.separator")
//								+ current);
//
//						selectedFileTime = current.replace(complete_name, "")
//								.replace(DmAlmConstants.MPS_CSV_EXTENSION, "");
//
//						selectedDateTime = DateUtils.stringToDate(
//								selectedFileTime,
//								DmAlmConstants.MPS_FILENAME_DATE_FORMAT);
//					}
//
//					currentFileTime = current.replace(complete_name, "")
//							.replace(DmAlmConstants.MPS_CSV_EXTENSION, "");
//
//					currentDateTime = DateUtils.stringToDate(currentFileTime,
//							DmAlmConstants.MPS_FILENAME_DATE_FORMAT);
//
//					if (currentDateTime.after(selectedDateTime)) {
//						selectedDateTime = currentDateTime;
//						selectedFile = new File(mps_directory
//								+ System.getProperty("file.separator")
//								+ current);
//					}
//				}
//
//				logger.debug("MpsUtils.currentMpsFile - selectedFile :"
//						+ selectedFile);
//
//				logger.debug("STOP MpsUtils.currentMpsFile");
//
//				if (selectedFile != null) {
//					return selectedFile.getAbsolutePath();
//				} else {
//					logger.debug("STOP MpsUtils.currentMpsFile: selectedFile NULL");
//
//					return null;
//				}
//			} else {
//				logger.debug("STOP MpsUtils.currentMpsFile: Nessun file "
//						+ complete_name + " trovato, return NULL");
//
//				return null;
//			}
//		} catch (Exception e) {
//			logger.error("MpsUtils.currentMpsFile - exception: "
//					+ e.getMessage());
//
//			return null;
//		}
//	}
}
