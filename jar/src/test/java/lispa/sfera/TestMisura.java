package lispa.sfera;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import junit.framework.TestCase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sfera.StgMisuraDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.cleaning.CheckSferaMisureFacade;
import lispa.schedulers.facade.sfera.CheckLinkSferaSgrCmFacade;
import lispa.schedulers.facade.sfera.target.AsmFacade;
import lispa.schedulers.facade.sfera.target.MisuraFacade;
import lispa.schedulers.facade.sfera.target.ProgettoSferaFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.MisuraUtils;

import org.apache.log4j.Logger;

public class TestMisura extends TestCase {

	private static Logger logger = Logger.getLogger(TestMisura.class);

	// private static Timestamp dataEsecuzione =
	// DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2015-05-12 12:29:00.0",
	// "yyyy-MM-dd HH:mm:00"));
	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.getDataEsecuzione();

	public static void testMisuraDao() throws Exception {
		StgMisuraDAO.FillStgMisura();

		// MisuraUtils.chiaveNaturaleGenerator("0",dataEsecuzione,t,"Msr");
		CheckSferaMisureFacade.execute(logger, dataEsecuzione);
		AsmFacade.execute(dataEsecuzione);
		ProgettoSferaFacade.execute(dataEsecuzione);
		MisuraFacade.execute(dataEsecuzione);

	}

	public static void testCheckMPP() throws DAOException {
		// CheckSferaMisureFacade.checkMPPType(logger, dataEsecuzione);
		// CheckSferaMisureFacade.checkMPPMisureType(logger,dataEsecuzione);
		// CheckSferaMisureFacade.checkMPPProjectName(logger,dataEsecuzione);
		// CheckSferaMisureFacade.checkMPPMisureName(logger, dataEsecuzione);
		// CheckSferaMisureFacade.checkMPPMisureApproach(logger,
		// dataEsecuzione);
		// CheckSferaMisureFacade.checkMPPUtenteMisuratore(logger,
		// dataEsecuzione);
		// CheckSferaMisureFacade.checkMPPMeasureStatus(logger, dataEsecuzione);
		// CheckSferaMisureFacade.checkMPPMeasurePairType(logger,dataEsecuzione);
		// CheckSferaMisureFacade.checkMPPMeasureStatus(logger, dataEsecuzione);
		// CheckSferaMisureFacade.checkPersonalCodAsm(logger, dataEsecuzione);
		// CheckSferaMisureFacade.checkPersonalDenominazioneUtenteFinale(logger,
		// dataEsecuzione);
		// CheckSferaMisureFacade.checkPersonalCodAsmConfinanti(logger,
		// dataEsecuzione);

	}

	public static void testDateRegex() throws IOException, DAOException,
			PropertiesReaderException {
		// String test =
		// "1_IO_SGR_CM;2_IO-SPOC;3_O_SPV;4_I_PROV_7;   NECA ;FOL; 5_I_IDM;";
		// String test2 = "ASI";
		// String[] splitted = null;
		// String[] splitted2 = null;

		// System.out.println(StringUtils.matchRegex("PAT-002-B",
		// DmalmRegex.REGEXNOMEMISURA));
		// System.out.println(StringUtils.matchRegex("PAT-002-B#REspinta",
		// DmalmRegex.REGEXTYPEB));
		// System.out.println(StringUtils.matchRegex("PAT-002-B",
		// DmalmRegex.REGEXTYPEB));
		// System.out.println(StringUtils.matchRegex("PAT-002-C",
		// DmalmRegex.REGEXTYPEC));
		// System.out.println(StringUtils.matchRegex("PAT-001-C",
		// DmalmRegex.REGEXTYPEC));
		// System.out.println(StringUtils.matchRegex("A..B..A",
		// DmalmRegex.REGEXPATTERNORESTE));
		// splitted = test.split(DmalmRegex.REGEXFLUSSI_IO);
		// System.out.println(test2.matches(DmalmRegex.FLAG_VALUES));
		// splitted = Arrays.copyOfRange(splitted,1,splitted.length );
		// System.out.println(MisuraDAO.getPersonalDenominazioneUtenti(dataEsecuzione));
		// CheckSferaMisureFacade.checkPersonalCodAsmConfinanti(logger,
		// dataEsecuzione);
		// CheckSferaMisureFacade.checkPersonalCodFlussiIo(logger,
		// dataEsecuzione);
		MisuraUtils.currentSferaFile();
		String pathCSVSfera = DmAlmConfigReader.getInstance().getProperty(
				DmAlmConfigReaderProperties.DMALM_SFERA_PATH);
		String file_name_date = new SimpleDateFormat(
				DmAlmConstants.DATE_SFERA_FILENAME).format(dataEsecuzione);
		System.out.println(pathCSVSfera + DmAlmConstants.FILENAME_EXPORT
				+ file_name_date + DmAlmConstants.EXP_SFERA_EXTENSION);
		// CheckSferaMisureFacade.checkAllFlags(logger, dataEsecuzione);
	}

	public static void testCheckLinkProgeSferaWi() throws Exception {
		try {
			Log4JConfiguration.inizialize();

			DataEsecuzione.getInstance().setDataEsecuzione(
					DateUtils.stringToTimestamp("2015-11-27 10:00:00",
							"yyyy-MM-dd HH:mm:00"));

			//DmAlmProgettoSferaDAO.checkLinkProgeSferaWi(DataEsecuzione.getInstance().getDataEsecuzione());
			CheckLinkSferaSgrCmFacade.execute(DataEsecuzione.getInstance().getDataEsecuzione());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
		}
	}

}
