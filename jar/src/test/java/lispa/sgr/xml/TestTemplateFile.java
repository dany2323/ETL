package lispa.sgr.xml;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import junit.framework.TestCase;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.facade.target.StatoWorkitemSgrCmFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryProject;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.util.SVNURLUtil;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

public class TestTemplateFile extends TestCase {
	private static Logger logger = Logger.getLogger(TestTemplateFile.class);

	public void testGetTemplateName() throws Exception {

		Properties p = new Properties();
		p.load(new FileInputStream("un path al file INI sul mio computer"));

	}

	public void testGetProjectTemplate() {

		QSissHistoryProject project = QSissHistoryProject.sissHistoryProject;

		Connection oracle = null;
		ConnectionManager cm = null;
		List<Tuple> projects = null;

		SQLTemplates dialect = new HSQLDBTemplates() {
			{
				setPrintSchema(true);
			}
		};

		try {
			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(oracle, dialect);

			projects = new ArrayList<Tuple>();

			projects = query
					.from(project)
					.where(project.cPk
							.equalsIgnoreCase("subterra:data-service:objects:/default/TEC_LGSV${Project}TEC_LGSV%12350"))
					.list(project.cUri, project.cLocation);

			for (Tuple location : projects) {

				getTemplateIniFile(getProjectSVNPath(location
						.get(project.cLocation)));

			}

		} catch (Exception e) {

		}
	}

	public static String getProjectSVNPath(String clocation) {

		if (clocation.startsWith("default:/")) {

			int start = clocation.lastIndexOf(":/") + 1;

			int end = clocation.indexOf("polarion-project.xml") - 1;

			return clocation.substring(start, end).concat(
					"/template-version.ini");
		} else {
			return null;
		}

	}

	public static void getTemplateIniFile(String projectLocation)
			throws Exception {

		Connection connection = null;
		ConnectionManager cm = null;

		String url = "";
		String name = "";
		String password = "";
		String filePath = "";

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			DAVRepositoryFactory.setup();

			connection.setAutoCommit(false);

			url = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_URL);
			name = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
			password = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_PSW);

			SVNRepository repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, password);
			repository.setAuthenticationManager(authManager);
			SVNURL root = repository.getRepositoryRoot(true);
			String absolutepath = root + projectLocation;
			filePath = SVNURLUtil.getRelativeURL(root,
					SVNURL.parseURIEncoded(absolutepath), false);
			SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
			if (nodeKind == SVNNodeKind.NONE) {
				System.err.println("There is no entry at '" + absolutepath
						+ "'.");
				System.exit(1);
			} else if (nodeKind == SVNNodeKind.DIR) {
				System.err.println("The entry at '" + filePath
						+ "' is a directory while a file was expected.");
				System.exit(1);
			}
			SVNProperties fileProperties = new SVNProperties();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			long rev = repository.getFile(filePath, -1, fileProperties, baos);
			logger.debug("rev: " + rev);

			String mimeType = fileProperties
					.getStringValue(SVNProperty.MIME_TYPE);

			boolean isTextType = SVNProperty.isTextMimeType(mimeType);
			String iniContent = "";
			if (isTextType) {
				iniContent = baos.toString();
				logger.debug("iniContent: " + iniContent);
			} else {
				throw new Exception("");
			}

			if (nodeKind == SVNNodeKind.FILE) {

			}
		} catch (Exception e) {

			System.out.println(e.getMessage().toString());
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static String parsePropertiesString(String s) throws IOException {

		final Properties p = new Properties();
		p.load(new StringReader(s));

		return p.getProperty("template-id").toUpperCase();
	}

	public static void testfillTemplateColumn() throws Exception {

		// for(Workitem_Type wi : Workitem_Type.values() ){
		// StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SIRE,
		// wi);
		StatoWorkitemSgrCmFacade.execute(DateUtils.stringToTimestamp(
				"2014-06-19 13:00:00", "yyyy-MM-dd HH:mm:ss"));
		// }
	}

	public static void testUpadteTemplate() throws Exception {
		Log4JConfiguration.inizialize();
		SireHistoryProjectDAO.setAllSireTemplate();
	}
}
