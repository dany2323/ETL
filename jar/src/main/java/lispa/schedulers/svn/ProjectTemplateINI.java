package lispa.schedulers.svn;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Connection;
import java.util.Properties;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.queryimplementation.staging.sgr.xml.DmAlmTemplateProject;
import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.util.SVNURLUtil;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class ProjectTemplateINI {

	private static Logger logger = Logger.getLogger(ProjectTemplateINI.class);

	static String url = "";
	static String name = "";
	static char[] psw = null;

	static SVNRepository repository;
	static ISVNAuthenticationManager authManager;

	public ProjectTemplateINI(String myrepository) {

		try {
			if (myrepository.equalsIgnoreCase(DmAlmConstants.REPOSITORY_SIRE)) {
				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_PSW).toCharArray();
			} else if (myrepository
					.equalsIgnoreCase(DmAlmConstants.REPOSITORY_SISS)) {
				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_PSW).toCharArray();;
			}

			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			authManager = SVNWCUtil.createDefaultAuthenticationManager(name,
					psw);

			repository.setAuthenticationManager(authManager);

		} catch (PropertiesReaderException e) {
			logger.error(e.getMessage(), e);

		} catch (SVNException e) {
			logger.error(e.getMessage(), e);

		}
	}

	public static void fillProjectTemplateIniFile(Connection connOracle, String pathLocation,
			long revision, String myRepository) {

		String filePath = "";
		String iniContent = "";

		DAVRepositoryFactory.setup();

		SVNProperties fileProperties = new SVNProperties();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		new ProjectTemplateINI(myRepository);
		try {
			repository.setAuthenticationManager(authManager);
			SVNURL root = repository.getRepositoryRoot(true);
			String projectLocation = getProjectTemplateSVNPath(pathLocation);
			String absolutepath = root + projectLocation;
			filePath = SVNURLUtil.getRelativeURL(root,
					SVNURL.parseURIEncoded(absolutepath), false);
			
			repository.getFile(filePath, revision, fileProperties, baos);
			String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
			boolean isTextType = SVNProperty.isTextMimeType(mimeType);

			if (isTextType) {
				iniContent = baos.toString();
				String templateId = parsePropertiesString(iniContent);
				DmAlmTemplateProject dmalmTemplateProject = DmAlmTemplateProject.dmAlmTemplateProject; 
				connOracle.setAutoCommit(false);

				SQLTemplates dialect = new HSQLDBTemplates() {
					{
						setPrintSchema(true);
					}
				};
				new SQLInsertClause(connOracle, dialect, dmalmTemplateProject)
					.columns(dmalmTemplateProject.pathLocation,
							dmalmTemplateProject.templateId,
							dmalmTemplateProject.rev,
							dmalmTemplateProject.idRepository)
					.values(projectLocation, templateId, revision, myRepository).execute();
				
				connOracle.commit();
			} else {
				logger.error("Impossibile trovare una location per il file INI "
								+ projectLocation);
			}
		} catch (Exception e){
			logger.error(e.getMessage(), e);
		}

	}

	public static boolean existProjectTemplateIni(Connection connOracle, String projectLocation,
			long revision, String myRepository) throws SVNException, Exception {

		DmAlmTemplateProject dmalmTemplateProject = DmAlmTemplateProject.dmAlmTemplateProject; 
		connOracle.setAutoCommit(false);

		SQLTemplates dialect = new HSQLDBTemplates() {
			{
				setPrintSchema(true);
			}
		};
		SQLQuery query = new SQLQuery(connOracle, dialect);
		long count = query.from(dmalmTemplateProject)
				.where(dmalmTemplateProject.pathLocation.eq(projectLocation))
				.where(dmalmTemplateProject.rev.eq(revision))
				.where(dmalmTemplateProject.idRepository.eq(myRepository))
				.count();

		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

	private static String getProjectTemplateSVNPath(String clocation) {

		if (clocation.startsWith("default:/")) {
			int start = clocation.lastIndexOf(":/") + 1;

			int end = clocation.indexOf("polarion-project.xml") - 1;

			return clocation.substring(start, end).concat(
					"/template-version.ini");
		} else {
			return null;
		}
	}

	private static String parsePropertiesString(String s) throws IOException {

		final Properties p = new Properties();
		p.load(new StringReader(s));

		return p.getProperty("template-id").toUpperCase();
	}
}