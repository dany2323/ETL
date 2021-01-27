package lispa.schedulers.svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import javax.xml.parsers.DocumentBuilderFactory;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.xml.DmAlmProjectRoles;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class ProjectRolesXML {

	private static DmAlmProjectRoles dmAlmProjectRoles = DmAlmProjectRoles.dmAlmProjectRoles;
	private static String url = "";
	private static String name = "";
	private static char[] psw;
	private static String filePath = "";
	
	public static void fillProjectRoles(String myrepository) throws Exception {

		try {
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.SVILUPPO);
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.DEMAND);
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.DEMAND2016);
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.ASSISTENZA);
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.IT);
			fillTemplatesProjectRoles(myrepository, DmAlmConstants.SERDEP);

			fillGlobalProjectRoles(myrepository);

			fillGlobalRoles(myrepository);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}

	public static String getTemplateConfigProps(String myrepo, String template) {
		String configProp = null;
		switch (myrepo) {
		case DmAlmConstants.REPOSITORY_SIRE:
			switch (template) {
			case DmAlmConstants.SVILUPPO:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_PROJECTROLES_FILE_SVILUPPO;
				break;
			case DmAlmConstants.DEMAND:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_PROJECTROLES_FILE_DEMAND;
				break;
			case DmAlmConstants.DEMAND2016:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_PROJECTROLES_FILE_DEMAND2016;
				break;
			case DmAlmConstants.ASSISTENZA:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_PROJECTROLES_FILE_ASSISTENZA;
				break;
			case DmAlmConstants.IT:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_PROJECTROLES_FILE_INTEGRAZIONETECNICA;
				break;
			case DmAlmConstants.SERDEP:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_PROJECTROLES_FILE_SERVIZIDEPLOY;
				break;
			}
		case DmAlmConstants.REPOSITORY_SISS:
			switch (template) {
			case DmAlmConstants.SVILUPPO:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_PROJECTROLES_FILE_SVILUPPO;
				break;
			case DmAlmConstants.DEMAND:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_PROJECTROLES_FILE_DEMAND;
				break;
			case DmAlmConstants.DEMAND2016:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_PROJECTROLES_FILE_DEMAND2016;
				break;
			case DmAlmConstants.ASSISTENZA:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_PROJECTROLES_FILE_ASSISTENZA;
				break;
			case DmAlmConstants.IT:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_PROJECTROLES_FILE_INTEGRAZIONETECNICA;
				break;
			case DmAlmConstants.SERDEP:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_PROJECTROLES_FILE_SERVIZIDEPLOY;
				break;
			}
		}
		return configProp;

	}

	public static void fillTemplatesProjectRoles(String myrepository,
			String template) throws Exception {

		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		try {
			cm = ConnectionManager.getInstance();
			DAVRepositoryFactory.setup();
			connection = cm.getConnectionOracle();

			if (myrepository.equals(DmAlmConstants.REPOSITORY_SIRE)) {

				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_PSW).toCharArray();
				filePath = DmAlmConfigReader.getInstance().getProperty(
						getTemplateConfigProps(myrepository, template));
			} else if (myrepository.equals(DmAlmConstants.REPOSITORY_SISS)) {

				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_PSW).toCharArray();
				filePath = DmAlmConfigReader.getInstance().getProperty(
						getTemplateConfigProps(myrepository, template));
			}

			connection.setAutoCommit(false);

			SVNRepository repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, psw);
			repository.setAuthenticationManager(authManager);

			SVNProperties fileProperties = new SVNProperties();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			SVNURL root = repository.getRepositoryRoot(true);
			String absolutepath = root + filePath;
			filePath = SVNURLUtil.getRelativeURL(root,
					SVNURL.parseURIEncoded(absolutepath), false);
			SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
			repository.getFile(filePath, -1, fileProperties, baos);
			String mimeType = fileProperties
					.getStringValue(SVNProperty.MIME_TYPE);

			boolean isTextType = SVNProperty.isTextMimeType(mimeType);
			String xmlContent = "";
			if (isTextType) {
				xmlContent = baos.toString();
			} else {
				throw new Exception("");
			}

			if (nodeKind == SVNNodeKind.FILE) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				Document doc = dbFactory.newDocumentBuilder().parse(
						new ByteArrayInputStream(xmlContent.getBytes()));
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("role");

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						new SQLInsertClause(connection, dialect,
								dmAlmProjectRoles)
								.columns(
										dmAlmProjectRoles.ruolo,
										dmAlmProjectRoles.descrizione,
										dmAlmProjectRoles.repository)
								.values(eElement.getTextContent(),
										template,
										myrepository)
								.execute();

					}
				}

				connection.commit();
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			cm.closeConnection(connection);
		}
	}

	public static void fillGlobalProjectRoles(String myrepository)
			throws Exception {

		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		try {
			cm = ConnectionManager.getInstance();
			DAVRepositoryFactory.setup();
			connection = cm.getConnectionOracle();

			if (myrepository.equals(DmAlmConstants.REPOSITORY_SIRE)) {
				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_PSW).toCharArray();
				filePath = DmAlmConfigReader
						.getInstance()
						.getProperty(
								DmAlmConfigReaderProperties.SIRE_SVN_PROJECTROLES_GLOBAL_1_FILE);
			} else if (myrepository.equals(DmAlmConstants.REPOSITORY_SISS)) {
				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_PSW).toCharArray();
				filePath = DmAlmConfigReader
						.getInstance()
						.getProperty(
								DmAlmConfigReaderProperties.SISS_SVN_PROJECTROLES_GLOBAL_1_FILE);
			}

			connection.setAutoCommit(false);

			SVNRepository repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, psw);
			repository.setAuthenticationManager(authManager);

			SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
			SVNProperties fileProperties = new SVNProperties();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			repository.getFile(filePath, -1, fileProperties, baos);
			String mimeType = fileProperties
					.getStringValue(SVNProperty.MIME_TYPE);

			boolean isTextType = SVNProperty.isTextMimeType(mimeType);
			String xmlContent = "";
			if (isTextType) {
				xmlContent = baos.toString();
			} else {
				throw new Exception("");
			}

			if (nodeKind == SVNNodeKind.FILE) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				Document doc = dbFactory.newDocumentBuilder().parse(
						new ByteArrayInputStream(xmlContent.getBytes()));
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("role");

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						new SQLInsertClause(connection, dialect,
								dmAlmProjectRoles)
								.columns(
										dmAlmProjectRoles.ruolo,
										dmAlmProjectRoles.descrizione,
										dmAlmProjectRoles.repository)
								.values(eElement.getTextContent(),
										DmAlmConstants.GLOBAL,
										myrepository)
								.execute();
					}
				}

				connection.commit();
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			cm.closeConnection(connection);
		}
	}

	public static void fillGlobalRoles(String myrepository) throws Exception {

		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		try {
			cm = ConnectionManager.getInstance();
			DAVRepositoryFactory.setup();
			connection = cm.getConnectionOracle();

			if (myrepository.equals(DmAlmConstants.REPOSITORY_SIRE)) {
				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_PSW).toCharArray();
				filePath = DmAlmConfigReader
						.getInstance()
						.getProperty(
								DmAlmConfigReaderProperties.SIRE_SVN_PROJECTROLES_GLOBAL_2_FILE);
			} else if (myrepository.equals(DmAlmConstants.REPOSITORY_SISS)) {

				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_PSW).toCharArray();
				filePath = DmAlmConfigReader
						.getInstance()
						.getProperty(
								DmAlmConfigReaderProperties.SISS_SVN_PROJECTROLES_GLOBAL_2_FILE);
			}

			connection.setAutoCommit(false);

			SVNRepository repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, psw);
			repository.setAuthenticationManager(authManager);

			SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
			SVNProperties fileProperties = new SVNProperties();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			repository.getFile(filePath, -1, fileProperties, baos);
			String mimeType = fileProperties
					.getStringValue(SVNProperty.MIME_TYPE);

			boolean isTextType = SVNProperty.isTextMimeType(mimeType);
			String xmlContent = "";
			if (isTextType) {
				xmlContent = baos.toString();
			} else {
				throw new Exception("");
			}

			if (nodeKind == SVNNodeKind.FILE) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				Document doc = dbFactory.newDocumentBuilder().parse(
						new ByteArrayInputStream(xmlContent.getBytes()));
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("role");

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						new SQLInsertClause(connection, dialect,
								dmAlmProjectRoles)
								.columns(
										dmAlmProjectRoles.ruolo,
										dmAlmProjectRoles.descrizione,
										dmAlmProjectRoles.repository)
								.values(
										eElement.getTextContent(),
										DmAlmConstants.GLOBAL,
										myrepository
								).execute();
					}
				}

				connection.commit();
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			cm.closeConnection(connection);
		}
	}
}