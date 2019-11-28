package lispa.schedulers.utils;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ExecutionManager;
import org.apache.log4j.Logger;
import com.google.common.io.Files;

public class MisuraUtils {
	private static Logger logger = Logger.getLogger(MisuraUtils.class);
	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.getDataEsecuzione();

	public static String currentSferaFile() {
		logger.debug("START currentSferaFile");

		String sfera_current_path = DmAlmConstants.SFERA_CURRENT;
		File sfera_directory = new File(DmAlmConstants.SFERA_PATH);

		final String file_name_date = new SimpleDateFormat(
				DmAlmConstants.DATE_SFERA_FILENAME).format(dataEsecuzione);

		logger.debug("currentSferaFile - file_name_date: " + file_name_date);

		final String complete_name = DmAlmConstants.FILENAME_EXPORT
				+ file_name_date;

		logger.debug("currentSferaFile - complete_name: " + complete_name);

		FilenameFilter fnf = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.contains(file_name_date);
			}
		};

		Timestamp mytime = null;
		Timestamp mysecondtime = null;
		String selectedTime = null;
		String generalTime = null;
		File myselectedfile = null;
		String[] current_list = sfera_directory.list(fnf);

		if (current_list == null || current_list.length == 0) {
			logger.info("Attenzione nessun file SFERA da elaborare : "
					+ complete_name);
			logger.debug("STOP currentSferaFile");
			// Se non viene trovato un file da elaborare inutile proseguire con
			// l'etl di SFERA
			ExecutionManager.getInstance().setExecutionSfera(false);

			return null;
		} else {
			for (String current : current_list) {
				if (selectedTime == null) {
					myselectedfile = new File(sfera_directory
							+ System.getProperty("file.separator") + current);
					selectedTime = current.replace(
							DmAlmConstants.FILENAME_EXPORT, "").replace(
							DmAlmConstants.EXP_SFERA_EXTENSION, "");
					logger.debug("FILE :" + myselectedfile);
					mytime = DateUtils.stringToTimestamp(selectedTime,
							"yyyy_MM_dd_HH_mm_ss");

				}

				generalTime = current.replace(DmAlmConstants.FILENAME_EXPORT,
						"").replace(DmAlmConstants.EXP_SFERA_EXTENSION, "");
				mysecondtime = DateUtils.stringToTimestamp(generalTime,
						"yyyy_MM_dd_HH_mm_ss");

				if (mysecondtime.after(mytime)) {
					myselectedfile = new File(sfera_directory
							+ System.getProperty("file.separator") + current);
				}

			}
			
			File mytargetfile = new File(
					sfera_current_path
							+ System.getProperty("file.separator")
							+ DmAlmConstants.FILENAME_EXPORT
							+ new SimpleDateFormat(
									DmAlmConstants.TIMESTAMP_SFERA_FILENAME)
									.format(mysecondtime)
							+ DmAlmConstants.EXP_SFERA_EXTENSION);
			
			try {
				Files.copy(myselectedfile, mytargetfile);

			} catch (IOException e) {
				logger.error(e.getMessage());
			}

			logger.debug("STOP currentSferaFile");
			
			return mytargetfile.getAbsolutePath();
		}
	}
}
