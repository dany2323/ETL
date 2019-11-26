package lispa.schedulers.svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import javax.xml.parsers.DocumentBuilderFactory;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.queryimplementation.staging.sgr.xml.DmAlmWorkitemLinkRoles;
import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
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

public class LinkedWorkItemRolesXML {

	private static final Logger logger = Logger.getLogger(LinkedWorkItemRolesXML.class);

	public LinkedWorkItemRolesXML() {

	}

	public static void fillLinkedWorkItemRoles(String myrepository)
			throws Exception {

		try {
			fillTemplateLinkedWorkItemRoles(myrepository,
					DmAlmConstants.SVILUPPO);
			fillTemplateLinkedWorkItemRoles(myrepository, DmAlmConstants.DEMAND);
			fillTemplateLinkedWorkItemRoles(myrepository, DmAlmConstants.DEMAND2016);
			fillTemplateLinkedWorkItemRoles(myrepository,
					DmAlmConstants.ASSISTENZA);
			fillTemplateLinkedWorkItemRoles(myrepository, DmAlmConstants.IT);
			fillTemplateLinkedWorkItemRoles(myrepository, DmAlmConstants.SERDEP);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}

	public static String getTemplateConfigProps(String myrepo, String template) {
		String configProp = null;
		
		switch (myrepo) {
		case DmAlmConstants.REPOSITORY_SIRE:
			switch (template) {
			case DmAlmConstants.SVILUPPO:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_LINKEDWORKITEMROLES_SVILUPPO;
				break;
			case DmAlmConstants.DEMAND:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_LINKEDWORKITEMROLES_DEMAND;
				break;
			case DmAlmConstants.ASSISTENZA:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_LINKEDWORKITEMROLES_ASSISTENZA;
				break;
			case DmAlmConstants.IT:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_LINKEDWORKITEMROLES_INTEGRAZIONETECNICA;
				break;
			case DmAlmConstants.SERDEP:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_LINKEDWORKITEMROLES_SERVIZIDEPLOY;
				break;
			case DmAlmConstants.DEMAND2016:
				configProp = DmAlmConfigReaderProperties.SIRE_SVN_LINKEDWORKITEMROLES_DEMAND2016;
				break;
			}
		case DmAlmConstants.REPOSITORY_SISS:
			switch (template) {
			case DmAlmConstants.SVILUPPO:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_LINKEDWORKITEMROLES_SVILUPPO;
				break;
			case DmAlmConstants.DEMAND:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_LINKEDWORKITEMROLES_DEMAND;
				break;
			case DmAlmConstants.ASSISTENZA:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_LINKEDWORKITEMROLES_ASSISTENZA;
				break;
			case DmAlmConstants.IT:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_LINKEDWORKITEMROLES_INTEGRAZIONETECNICA;
				break;
			case DmAlmConstants.SERDEP:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_LINKEDWORKITEMROLES_SERVIZIDEPLOY;
				break;
			case DmAlmConstants.DEMAND2016:
				configProp = DmAlmConfigReaderProperties.SISS_SVN_LINKEDWORKITEMROLES_DEMAND2016;
				break;
			}
		}
		
		return configProp;
	}

	public static void fillTemplateLinkedWorkItemRoles(String myrepository,
			String template) throws SQLException, DAOException {

		DmAlmWorkitemLinkRoles dmAlmLinkRoles = DmAlmWorkitemLinkRoles.dmAlmWorkitemLinkRoles;
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		String url = "";
		String name = "";
		String psw = "";
		String filePath = "";

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
						DmAlmConfigReaderProperties.SIRE_SVN_PSW);
				filePath = DmAlmConfigReader.getInstance().getProperty(
						getTemplateConfigProps(myrepository, template));

			} else if (myrepository.equals(DmAlmConstants.REPOSITORY_SISS)) {

				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_PSW);
				filePath = DmAlmConfigReader.getInstance().getProperty(
						getTemplateConfigProps(myrepository, template));
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
			
			boolean parent = false;
			if (nodeKind == SVNNodeKind.FILE) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();
				Document doc = dbFactory.newDocumentBuilder().parse(
						new ByteArrayInputStream(xmlContent.getBytes()));
				doc.getDocumentElement().normalize();

				NodeList nList = doc.getElementsByTagName("option");

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						if (!eElement.getAttribute("parent").equals("")) {
							parent = true;
						} else {
							parent = false;
						}

						new SQLInsertClause(connection, dialect, dmAlmLinkRoles)
								.columns(dmAlmLinkRoles.idRuolo,
										dmAlmLinkRoles.descrizione,
										dmAlmLinkRoles.repository,
										dmAlmLinkRoles.templates,
										dmAlmLinkRoles.nomeRuolo,
										dmAlmLinkRoles.nomeRuoloInverso,
										dmAlmLinkRoles.parent)
								.values(eElement.getAttribute("id"),
										eElement.getAttribute("description"),
										myrepository.toString(), template,
										eElement.getAttribute("name"),
										eElement.getAttribute("oppositeName"),
										parent).execute();
					}
				}
				connection.commit();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}