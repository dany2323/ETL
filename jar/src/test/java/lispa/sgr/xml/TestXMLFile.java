package lispa.sgr.xml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmProjectRoles;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmStatoWorkitem;
import lispa.schedulers.runnable.staging.sire.current.SireStatoWorkitemRunnable;
import lispa.schedulers.svn.LinkedWorkItemRolesXML;
import lispa.schedulers.svn.ProjectRolesXML;
import lispa.schedulers.svn.SIREUserRolesXML;
import lispa.schedulers.svn.StatoWorkItemXML;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

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
import com.mysema.query.support.Expressions;
import com.mysema.query.types.template.StringTemplate;

public class TestXMLFile extends TestCase {

	private static Logger logger = Logger.getLogger(TestXMLFile.class);

	public void testFillSIREHistoryUserRoles() throws Exception {
		Log4JConfiguration.inizialize();

		logger.debug("START testFillSIREHistoryUserRoles");
		DataEsecuzione.getInstance().setDataEsecuzione(
				DateUtils.stringToTimestamp("2016-05-09 10:00:00",
						"yyyy-MM-dd HH:mm:00"));
		SIREUserRolesXML.fillSIREHistoryUserRoles();

		logger.debug("STOP testFillSIREHistoryUserRoles");
	}

	public void testSireUserRoles() throws Exception {
		Log4JConfiguration.inizialize();

		logger.debug("START testSireUserRoles");
		
		DataEsecuzione.getInstance().setDataEsecuzione(
				DateUtils.stringToTimestamp("2016-05-09 10:00:00",
						"yyyy-MM-dd HH:mm:00"));
		String url = "";
		String name = "";
		String psw = "";
		SVNRepository repository;
		ISVNAuthenticationManager authManager;
		
		url = DmAlmConfigReader.getInstance().getProperty(
				DmAlmConfigReaderProperties.SIRE_SVN_URL);
		name = DmAlmConfigReader.getInstance().getProperty(
				DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
		psw = DmAlmConfigReader.getInstance().getProperty(
				DmAlmConfigReaderProperties.SIRE_SVN_PSW);

		repository = SVNRepositoryFactory.create(SVNURL.parseURIEncoded(url));
		authManager = SVNWCUtil.createDefaultAuthenticationManager(name, psw);
		repository.setAuthenticationManager(authManager);
		
		SVNNodeKind nodeKind = repository.checkPath("/reposire/Sviluppo/SIRE/GEPRE.DDEP/.polarion/security/user-roles.xml", 980415);

		if (nodeKind == SVNNodeKind.FILE) {
			logger.debug("kind File");
		}
		
		logger.debug("STOP testSireUserRoles");
	}

	public void testFillStatiWorkItem() throws Exception {

		String myrepository = DmAlmConstants.REPOSITORY_SISS;
		String myWorkItemType = DmAlmConstants.WORKITEM_TYPE_ANOMALIA;
		QDmAlmStatoWorkitem qDmAlmStatoWorkitem = QDmAlmStatoWorkitem.dmAlmStatoWorkitem;
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection OracleConnection = null;
		ConnectionManager cm = null;

		String url = "";
		String name = "";
		String password = "";
		String filePath = "";
		String repos = "";

		try {

			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			DAVRepositoryFactory.setup();

			if (myWorkItemType.equals(DmAlmConstants.WORKITEM_TYPE_ANOMALIA)) {
				filePath = DmAlmConfigReader
						.getInstance()
						.getProperty(
								DmAlmConfigReaderProperties.SISS_SVN_STATOWORKITEM_ANOMALIA_FILE);
			} else if (myWorkItemType
					.equals(DmAlmConstants.WORKITEM_TYPE_DIFETTO)) {
				filePath = DmAlmConfigReader
						.getInstance()
						.getProperty(
								DmAlmConfigReaderProperties.SISS_SVN_STATOWORKITEM_DIFETTO_FILE);
			}

			if (myrepository.equals(DmAlmConstants.REPOSITORY_SIRE)) {
				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
				password = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_PSW);
				repos = DmAlmConstants.REPOSITORY_SIRE;
			} else if (myrepository.equals(DmAlmConstants.REPOSITORY_SISS)) {
				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
				password = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_PSW);
				repos = DmAlmConstants.REPOSITORY_SISS;
			}

			SVNRepository repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, password);
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
				try {
					baos.writeTo(System.out);
					xmlContent = baos.toString();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
			} else {
				throw new Exception("");
			}

			if (nodeKind == SVNNodeKind.FILE) {

				DocumentBuilderFactory dbFactory = DocumentBuilderFactory
						.newInstance();

				Document doc = dbFactory.newDocumentBuilder().parse(
						new ByteArrayInputStream(xmlContent.getBytes()));
				doc.getDocumentElement().normalize();

				//

				NodeList nList = doc.getElementsByTagName("option");

				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);

					//

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;

						new SQLInsertClause(OracleConnection, dialect,
								qDmAlmStatoWorkitem)
								.columns(
										qDmAlmStatoWorkitem.dmAlmStatoWorkitemPk,
										qDmAlmStatoWorkitem.descrizione,
										qDmAlmStatoWorkitem.iconUrl,
										qDmAlmStatoWorkitem.id,
										qDmAlmStatoWorkitem.name,
										qDmAlmStatoWorkitem.sortOrder,
										qDmAlmStatoWorkitem.repository,
										qDmAlmStatoWorkitem.dataCaricamento)
								.values(StringTemplate
										.create("DM_ALM_STATO_WORKITEM_SEQ.nextval"),
										eElement.getAttribute("description"),
										eElement.getAttribute("iconURL"),
										eElement.getAttribute("id"),
										eElement.getAttribute("name"),
										eElement.getAttribute("sortOrder"),
										Expressions.constant(repos),
										StringTemplate
												.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))")

								).execute();

					}
				}

			}

		} catch (Exception e) {

		}

	}

	public void testFillStatoWorkitemRunnable() throws Exception {

		Thread t;

		t = new Thread(new SireStatoWorkitemRunnable(logger));

		t.start();

		t.join();

	}

	public void testFillProjectRoles() throws Exception {

		String myrepository = "SIRE";

		QDmAlmProjectRoles qDmAlmStatoWorkitem = QDmAlmProjectRoles.dmAlmProjectRoles;
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection OracleConnection = null;
		ConnectionManager cm = null;

		String url = "";
		String name = "";
		String password = "";
		String filePath = "";

		try {

			cm = ConnectionManager.getInstance();
			OracleConnection = cm.getConnectionOracle();
			DAVRepositoryFactory.setup();

			OracleConnection.setAutoCommit(false);

			if (myrepository.equals(DmAlmConstants.REPOSITORY_SIRE)) {

				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
				password = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_PSW);
				filePath = DmAlmConfigReader
						.getInstance()
						.getProperty(
								DmAlmConfigReaderProperties.SIRE_SVN_PROJECTROLES_FILE_SVILUPPO);

			} else if (myrepository.equals(DmAlmConstants.REPOSITORY_SISS)) {

				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
				password = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_PSW);
				filePath = DmAlmConfigReader
						.getInstance()
						.getProperty(
								DmAlmConfigReaderProperties.SISS_SVN_PROJECTROLES_FILE_SVILUPPO);
			}

			SVNRepository repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, password);
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
				try {
					baos.writeTo(System.out);
					xmlContent = baos.toString();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				}
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

						new SQLInsertClause(OracleConnection, dialect,
								qDmAlmStatoWorkitem)
								.columns(
										qDmAlmStatoWorkitem.dmAlmProjectRolesPk,
										qDmAlmStatoWorkitem.ruolo,
										qDmAlmStatoWorkitem.descrizione,
										qDmAlmStatoWorkitem.dataCaricamento)
								.values(StringTemplate
										.create("DM_ALM_PROJECT_ROLES_SEQ.nextval"),
										eElement.getTextContent(),
										"-",
										StringTemplate
												.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))"))
								.execute();

					}
				}

				OracleConnection.commit();

			}

		} catch (Exception e) {

		} finally {
			OracleConnection.close();
		}

	}

	// public void testFillUserRoles() throws Exception
	// {
	//
	//
	// String myrepository = "SIRE";
	// String project = "CTSTRDE";
	// String c_name =
	// "default:/Sviluppo/SIRE/A355.Area.Sistemi.Territoriali/Trasporti/Catasto.Strade/CTSTRDE/.polarion/polarion-project.xml";
	//
	// QDmAlmUserRoles qDmAlmUserRoles = QDmAlmUserRoles.dmAlmUserRoles;
	// SQLTemplates dialect = new HSQLDBTemplates();
	// Connection OracleConnection = null;
	// ConnectionManager cm = null;
	//
	// String url = "";
	// String name = "";
	// String password = "";
	// String filePath = "";
	// String repos = "";
	//
	//
	// try{
	//
	// cm = ConnectionManager.getInstance();
	// OracleConnection = cm.getConnectionOracle();
	// DAVRepositoryFactory.setup( );
	//
	// OracleConnection.setAutoCommit(false);
	//
	//
	// if(myrepository.equals(DmAlmConstants.REPOSITORY_SIRE))
	// {
	//
	// url =
	// DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SIRE_SVN_URL);
	// name =
	// DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
	// password =
	// DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SIRE_SVN_PASSWORD);
	// filePath =
	// "/Sviluppo/SIRE/A355.Area.Sistemi.Territoriali/Trasporti/Catasto.Strade/CTSTRDE/.polarion/security/user-roles.xml";
	// repos = DmAlmConstants.REPOSITORY_SIRE;
	//
	// }
	// else if(myrepository.equals(DmAlmConstants.REPOSITORY_SISS))
	// {
	// url =
	// DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SISS_SVN_URL);
	// name =
	// DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
	// password =
	// DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SISS_SVN_PASSWORD);
	// filePath =
	// DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SISS_SVN_PROJECTROLES_FILE_SVILUPPO);
	// repos = DmAlmConstants.REPOSITORY_SISS;
	// }
	//
	// SVNRepository repository = SVNRepositoryFactory.create(
	// SVNURL.parseURIEncoded( url ) );
	// ISVNAuthenticationManager authManager =
	// SVNWCUtil.createDefaultAuthenticationManager( name , password );
	// repository.setAuthenticationManager( authManager );
	//
	// SVNNodeKind nodeKind = repository.checkPath( filePath , -1 );
	// SVNProperties fileProperties = new SVNProperties();
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	//
	// repository.getFile(filePath, -1, fileProperties, baos);
	// String mimeType = fileProperties.getStringValue(SVNProperty.MIME_TYPE);
	//
	// boolean isTextType = SVNProperty.isTextMimeType(mimeType);
	// String xmlContent ="";
	// if (isTextType)
	// {
	// try
	// {
	// baos.writeTo(System.out);
	// xmlContent = baos.toString();
	// } catch (IOException ioe) {
	// ioe.printStackTrace();
	// }
	// }
	// else
	// {
	// throw new Exception("");
	// }
	//
	// if(nodeKind == SVNNodeKind.FILE){
	//
	// DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	// Document doc = dbFactory.newDocumentBuilder().parse(new
	// ByteArrayInputStream(xmlContent.getBytes()));
	// doc.getDocumentElement().normalize();
	//
	// NodeList nList = doc.getElementsByTagName("user");
	//
	// for (int temp = 0; temp < nList.getLength(); temp++) {
	//
	// Node nNode = nList.item(temp);
	//
	// if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	//
	// Element eElement = (Element) nNode;
	//
	// //
	//
	// NodeList ruoli = eElement.getChildNodes();
	//
	// for (int tempruolo = 0; tempruolo < ruoli.getLength(); tempruolo++) {
	//
	// Node ruolo = ruoli.item(tempruolo);
	//
	// if (ruolo.getNodeType() == Node.ELEMENT_NODE) {
	//
	// Element el = (Element) ruolo;
	//
	// //
	//
	// new SQLInsertClause(OracleConnection, dialect, qDmAlmUserRoles)
	// .columns(
	// qDmAlmUserRoles.dmAlmUserRolesPk,
	// qDmAlmUserRoles.siglaProject,
	// qDmAlmUserRoles.idProject,
	// qDmAlmUserRoles.utente,
	// qDmAlmUserRoles.ruolo,
	// qDmAlmUserRoles.dataCaricamento
	// )
	// .values(
	// StringTemplate.create("DM_ALM_USER_ROLES_SEQ.nextval"),
	// project,
	// c_name,
	// eElement.getAttribute("name"),
	// el.getAttribute("name"),
	// StringTemplate.create("Trunc(to_date(TO_CHAR(sysdate, 'DD/MM/YYYY'), 'dd/MM/YYYY'))")
	// )
	// .execute();
	//
	//
	// }
	// }
	//
	//
	//
	//
	//
	//
	// }
	// }
	//
	// OracleConnection.commit();
	//
	// }
	//
	// }
	// catch(Exception e)
	// {
	// Oracle
	//
	// }
	// finally
	// {
	// OracleConnection.close();
	// }
	//
	// }

	public void testFillAllProjectRoles() throws Exception {
		ProjectRolesXML.fillProjectRoles(DmAlmConstants.REPOSITORY_SISS);
		ProjectRolesXML.fillProjectRoles(DmAlmConstants.REPOSITORY_SIRE);
		// ProjectRolesXML.fillTemplatesProjectRoles(DmAlmConstats.REPOSITORY_SISS,
		// DmAlmConstats.DEMAND);
		// ProjectRolesXML.fillTemplatesProjectRoles(DmAlmConstats.REPOSITORY_SISS,
		// DmAlmConstats.ASSISTENZA);
	}

	public void testFillStatoWorkItem() throws Exception {
		StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SIRE,
				Workitem_Type.pei);
		StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SIRE,
				Workitem_Type.anomalia_assistenza);
		StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SIRE,
				Workitem_Type.taskit);
		StatoWorkItemXML.fillStatoWorkItem(DmAlmConstants.REPOSITORY_SIRE,
				Workitem_Type.build);
	}

	public void testFillLinkedWorkItemRoles() {
		try {
			LinkedWorkItemRolesXML.fillTemplateLinkedWorkItemRoles("SISS",
					DmAlmConstants.DEMAND);
			LinkedWorkItemRolesXML.fillTemplateLinkedWorkItemRoles("SISS",
					DmAlmConstants.SVILUPPO);
			LinkedWorkItemRolesXML.fillTemplateLinkedWorkItemRoles("SISS",
					DmAlmConstants.IT);
			LinkedWorkItemRolesXML.fillTemplateLinkedWorkItemRoles("SISS",
					DmAlmConstants.SERDEP);
			LinkedWorkItemRolesXML.fillTemplateLinkedWorkItemRoles("SISS",
					DmAlmConstants.ASSISTENZA);
			LinkedWorkItemRolesXML.fillTemplateLinkedWorkItemRoles("SIRE",
					DmAlmConstants.DEMAND);
			LinkedWorkItemRolesXML.fillTemplateLinkedWorkItemRoles("SIRE",
					DmAlmConstants.SVILUPPO);
			LinkedWorkItemRolesXML.fillTemplateLinkedWorkItemRoles("SIRE",
					DmAlmConstants.IT);
			LinkedWorkItemRolesXML.fillTemplateLinkedWorkItemRoles("SIRE",
					DmAlmConstants.SERDEP);
			LinkedWorkItemRolesXML.fillTemplateLinkedWorkItemRoles("SIRE",
					DmAlmConstants.ASSISTENZA);
		} catch (SQLException e) {
			//

		} catch (DAOException e) {
			//

		}
	}

	public void testDeleteLinkedWorkItemRoles() {
		try {
			LinkedWorkItemRolesXML.recoverLinkedWorkItemRoles();
		} catch (Exception e) {
			//

		}
	}

	public void testDeleteProjectRoles() {

		try {
			ProjectRolesXML.recoverAllProjectRoles();

		} catch (Exception e) {
			//

		}
	}

	public void testRecoverAll() {
		try {
			StatoWorkItemXML.recoverStatoWorkitem();
			// SIREUserRolesXML.recoverSIREUserRoles();
			// SISSUserRolesXML.recoverSISSUserRoles();

		} catch (Exception e) {

		}

	}

}