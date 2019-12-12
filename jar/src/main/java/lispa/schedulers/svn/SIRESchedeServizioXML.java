package lispa.schedulers.svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.queryimplementation.staging.sgr.xml.DmAlmSchedeServizio;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

public class SIRESchedeServizioXML {

	private static Logger logger = Logger.getLogger(SIRESchedeServizioXML.class);
	private static DmAlmSchedeServizio stg_SchedeServizio = DmAlmSchedeServizio.dmalmSchedeServizio;
	static String url = "";
	static String name = "";
	static String psw = "";
	static SVNRepository repository;
	static ISVNAuthenticationManager authManager;

	public static void fillSIREHistorySchedeServizio() throws DAOException {
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		String filePath = "";
		
		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			DAVRepositoryFactory.setup();
			url = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_URL);
			name = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
			psw = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_PSW);
			filePath = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_SCHEDE_SERVIZO_FILE);

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

				NodeList nList = doc.getElementsByTagName("option");
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					List<Tuple> check = new ArrayList<Tuple>();
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						check = checkExist(eElement.getAttribute("id"),
								eElement.getAttribute("name"),
								eElement.getAttribute("sortOrder"));
						if (check.size() == 0) {
							new SQLInsertClause(connection, dialect, stg_SchedeServizio)
									.columns(stg_SchedeServizio.id,
											stg_SchedeServizio.name, stg_SchedeServizio.sort_order,
											stg_SchedeServizio.repository)
									.values(eElement.getAttribute("id"),
											eElement.getAttribute("name"),
											eElement.getAttribute("sortOrder"),
											DmAlmConstants.REPOSITORY_SIRE)
									.execute();
						}
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

	private static List<Tuple> checkExist(String id, String name, String sort)
			throws DAOException {
		List<Tuple> check = new ArrayList<Tuple>();
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connection, dialect);
			check = query.from(stg_SchedeServizio).where(stg_SchedeServizio.id.eq(id)).where(stg_SchedeServizio.name.eq(name))
					.where(stg_SchedeServizio.sort_order.eq(Integer.parseInt(sort))).list(stg_SchedeServizio.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return check;
	}
}