package lispa.schedulers.svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSchedeServizio;

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
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

public class SIRESchedeServizioXML {

	private static Logger logger = Logger
			.getLogger(SIRESchedeServizioXML.class);

	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.getDataEsecuzione();

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

	public static void fillSIREHistorySchedeServizio() throws DAOException {
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		String filePath = "";
		QDmalmSchedeServizio ss = QDmalmSchedeServizio.dmalmSchedeServizio;
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
							new SQLInsertClause(connection, dialect, ss)
									.columns(ss.dmalm_schedeServizio_Pk, ss.id,
											ss.name, ss.sorter,
											ss.dtCaricamento, ss.repository)
									.values(StringTemplate
											.create("DM_ALM_STG_SCHEDE_SERVIZIO_SEQ.nextval"),
											eElement.getAttribute("id"),
											eElement.getAttribute("name"),
											eElement.getAttribute("sortOrder"),
											dataEsecuzione,
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
		QDmalmSchedeServizio ss = QDmalmSchedeServizio.dmalmSchedeServizio;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connection, dialect);
			check = query.from(ss).where(ss.id.eq(id)).where(ss.name.eq(name))
					.where(ss.sorter.eq(Integer.parseInt(sort))).list(ss.dmalm_schedeServizio_Pk, ss.id);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return check;
	}

	public static void recoverSIRESchedeServizio() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmalmSchedeServizio qDmalmSchedeServizio = QDmalmSchedeServizio.dmalmSchedeServizio;

			new SQLDeleteClause(connection, dialect, qDmalmSchedeServizio)
					.where(qDmalmSchedeServizio.dtCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
