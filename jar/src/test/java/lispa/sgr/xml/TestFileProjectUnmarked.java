package lispa.sgr.xml;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiEdmaFacade;
import lispa.schedulers.facade.cleaning.CheckAnnullamentiSGRCMFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.queryimplementation.annullamenti.project.QDmAlmProjectUnmarked;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class TestFileProjectUnmarked extends TestCase {

	private final static String SEPARATOR = "|";
	private final static String UNMARKED = "UNMARKED";
	private static Logger logger = Logger
			.getLogger(TestFileProjectUnmarked.class);

	public void testGetUnmarkedProjects() throws Exception {

		DataEsecuzione.getInstance().setDataEsecuzione(
				DateUtils.stringToTimestamp("2014-12-02 19:28:00",
						"yyyy-MM-dd HH:mm:00"));
		try {

			System.out.println("START CheckAnnullamentiSGRCMFacade");
			CheckAnnullamentiSGRCMFacade.execute();
			System.out.println("STOP CheckAnnullamentiSGRCMFacade");

			System.out.println("START CheckAnnullamentiEdmaFacade");
			CheckAnnullamentiEdmaFacade.execute();
			System.out.println("STOP CheckAnnullamentiEdmaFacade");

		} catch (DAOException e) {
			logger.error(e.getMessage(), e);
		}

		// List<String> l1 =
		// getUnmarkedProjectsPathsFromFile(URL_PROJECT_UNMARKED_SIRE,
		// DmAlmConstants.REPOSITORY_SIRE);

		// List<String> l2 =
		// getUnmarkedProjectsPathsFromFile(URL_PROJECT_UNMARKED_SISS,
		// DmAlmConstants.REPOSITORY_SISS);

	}

	/**
	 * Il file ha questo formato --UNMARKED | tracker_prefix | path_project--
	 * 
	 * @param link
	 *            l'URL dove si trova il file da leggere
	 * @return la lista di path dei project.
	 * @throws IOException
	 * @throws DAOException
	 */
	public static List<String> getUnmarkedProjectsPathsFromFile(String link,
			String repo) throws IOException, DAOException {

		BufferedReader in = null;
		URL url = null;
		List<String> projectLocations = new ArrayList<String>();

		ConnectionManager cm = null;
		Connection connectionOracle = null;

		try {

			url = new URL(link);
			in = new BufferedReader(new InputStreamReader(url.openStream()));

			cm = ConnectionManager.getInstance();
			connectionOracle = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates();

			QDmAlmProjectUnmarked projectsUnmarked = QDmAlmProjectUnmarked.projectUnmarked;

			String inputLine;
			String[] inputParsed;
			while ((inputLine = in.readLine()) != null) {
				inputParsed = inputLine.split(SEPARATOR);
				if (inputParsed.length > 4) {

					switch (inputParsed[0].trim()) {

					case UNMARKED:
						String trackerPrefix = inputParsed[2].trim();
						String projectLocation = inputParsed[4].trim();
						projectLocations.add(projectLocation);
						new SQLInsertClause(connectionOracle, dialect,
								projectsUnmarked)
								.columns(projectsUnmarked.cTrackerprefix,
										projectsUnmarked.dataCaricamento,
										projectsUnmarked.path,
										projectsUnmarked.repository)
								.values(trackerPrefix,
										DataEsecuzione.getInstance()
												.getDataEsecuzione(),
										projectLocation, repo).execute();

						break;

					default:
						break;

					}

				}
			}

		} catch (Exception e) {

		} finally {
			in.close();
			if (cm != null)
				cm.closeConnection(connectionOracle);
		}
		return projectLocations;

	}

}
