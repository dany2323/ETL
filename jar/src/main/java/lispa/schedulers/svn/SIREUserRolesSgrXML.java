package lispa.schedulers.svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import javax.xml.parsers.DocumentBuilderFactory;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.xml.DmAlmUserRolesSgr;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;
import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.util.SVNURLUtil;
import org.tmatesoft.svn.core.io.SVNFileRevision;
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


public class SIREUserRolesSgrXML {

	private static Logger logger = Logger.getLogger(SIREUserRolesSgrXML.class);
	private static DmAlmUserRolesSgr userRolesSgr = DmAlmUserRolesSgr.dmAlmUserRolesSgr;
	static String url = "";
	static String name = "";
	static char[] psw = null;

	static SVNRepository repository;
	static ISVNAuthenticationManager authManager;

	public static void fillUserRolesSgr(String projectLocation) throws Exception {
		
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;
		DAVRepositoryFactory.setup();

		String filePath = "";

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			url = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_URL);
			name = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
			psw = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_PSW).toCharArray();
			filePath = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_USER_ROLES_FILE);

			SVNRepository repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, psw);
			repository.setAuthenticationManager(authManager);
			SVNURL root = repository.getRepositoryRoot(true);

			String absolutepath = root + projectLocation;
			projectLocation = SVNURLUtil.getRelativeURL(root,
					SVNURL.parseURIEncoded(absolutepath), false);
			filePath = projectLocation
					.concat(DmAlmConfigReader
							.getInstance()
							.getProperty(
									DmAlmConfigReaderProperties.SIRE_SVN_USER_ROLES_FILE));

			SVNNodeKind nodeKind = repository.checkPath(filePath, -1);
			SVNProperties fileProperties = null;
			ByteArrayOutputStream baos = null;
			fileProperties = new SVNProperties();

			SVNFileRevision svnfr = new SVNFileRevision(filePath, -1, fileProperties, fileProperties);

			baos = new ByteArrayOutputStream();
			if(repository.checkPath(svnfr.getPath(), svnfr.getRevision()) == SVNNodeKind.NONE){
				if(svnfr.getRevision() == -1)
					logger.info("Il path SVN " + svnfr.getPath() + " non esiste alla revisione HEAD");
				else	
					logger.info("Il path SVN " + svnfr.getPath() + " non esiste alla revisione " + svnfr.getRevision());
			}
			
			repository.getFile(svnfr.getPath(), svnfr.getRevision(),
					fileProperties, baos);
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
				
				Document doc = dbFactory.newDocumentBuilder()
						.parse(new ByteArrayInputStream(xmlContent
								.getBytes()));
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("user");

				String data = svnfr.getRevisionProperties()
						.getSVNPropertyValue(SVNProperty.COMMITTED_DATE)
						.getString().toString().replace("T", " ")
						.replaceFirst(DmalmRegex.REGEXSVNDATE, "");

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						NodeList ruoli = eElement.getChildNodes();

						for (int tempruolo = 0; tempruolo < ruoli
								.getLength(); tempruolo++) {

							Node ruolo = ruoli.item(tempruolo);

							if (ruolo.getNodeType() == Node.ELEMENT_NODE) {

								Element el = (Element) ruolo;
								
								new SQLInsertClause(connection, dialect, userRolesSgr)
										.columns(
												userRolesSgr.origine,
												userRolesSgr.userId,
												userRolesSgr.ruolo,
												userRolesSgr.repository,
												userRolesSgr.dtModifica)
										.values(projectLocation,
												StringUtils.getMaskedValue(eElement.getAttribute("name")),
												el.getAttribute("name"),
												DmAlmConstants.REPOSITORY_SIRE,
												DateUtils.stringToTimestamp(data,"yyyy-MM-dd HH:mm:ss")
										).execute();
							}
						}
					}
				}
			}
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
