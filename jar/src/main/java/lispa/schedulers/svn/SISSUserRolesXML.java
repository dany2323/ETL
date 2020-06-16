package lispa.schedulers.svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;

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


/**
 * degli user-roles, si recupera tutta la storia navigando il repository SVN in
 * ordine di revisione. Ad ogni elaborazione si ricava dallo staging l’ultima
 * revisione del file già inserita relativa a ciascun project. Tale revisione
 * rappresenta la minima a partire dalla quale l’ETL alla data corrente
 * analizzarà il file presente nel repository SVN
 * 
 * @author fdeangel
 * 
 */
public class SISSUserRolesXML {
	private static Logger logger = Logger.getLogger(SISSUserRolesXML.class);

	static String url = "";
	static String name = "";
	static String psw = "";

	static SVNRepository repository;
	static ISVNAuthenticationManager authManager;

	public static String getProjectSVNPath(String clocation) {
		if (clocation.startsWith("default:/")) {
			int start = clocation.indexOf(":/") + 1;

			int end = clocation.indexOf(".polarion") - 1;

			return clocation.substring(start, end);
		} else {
			return null;
		}
	}

	public static List<String> getUtentiIT() throws Exception {
		Connection connection = null;
		ConnectionManager cm = null;

		String url = "";
		String name = "";
		char[] psw = null;
		String filePath = "";

		List<String> utentiIT = new ArrayList<String>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			DAVRepositoryFactory.setup();

			connection.setAutoCommit(false);

			url = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_URL);
			name = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
			psw = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_PSW).toCharArray();
			filePath = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_USER_ROLES_FILE);

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

				NodeList nList = doc.getElementsByTagName("user");

				for (int temp = 0; temp < nList.getLength(); temp++) {

					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {

						Element eElement = (Element) nNode;

						NodeList ruoli = eElement.getChildNodes();

						for (int tempruolo = 0; tempruolo < ruoli.getLength(); tempruolo++) {

							Node ruolo = ruoli.item(tempruolo);

							if (ruolo.getNodeType() == Node.ELEMENT_NODE) {

								Element el = (Element) ruolo;

								if (el.getAttribute("name").equals("IT")) {

									utentiIT.add(eElement.getAttribute("name")
											.toUpperCase());
								}
							}
						}
					}
				}

				connection.commit();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			cm.closeConnection(connection);
		}

		return utentiIT;
	}
}