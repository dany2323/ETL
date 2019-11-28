package lispa.schedulers.svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.util.HashSet;
import java.util.Set;
import javax.xml.parsers.DocumentBuilderFactory;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.xml.DmAlmStatoWorkitem;
import lispa.schedulers.utils.EnumUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;
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

public class StatoWorkItemXML {

	private static DmAlmStatoWorkitem dmAlmStatoWorkitem = DmAlmStatoWorkitem.dmAlmStatoWorkitem;
	
	public static void fillStatoWorkItem(String myrepository,
			EnumWorkitemType workitemType) throws Exception {

		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		String url = "";
		String name = "";
		String psw = "";
		String filePath = "";
		String repos = "";

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			DAVRepositoryFactory.setup();

			if (myrepository.equals(DmAlmConstants.REPOSITORY_SIRE)) {

				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SIRE_SVN_PSW);
				repos = DmAlmConstants.REPOSITORY_SIRE;

			} else if (myrepository.equals(DmAlmConstants.REPOSITORY_SISS)) {

				url = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_URL);
				name = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
				psw = DmAlmConfigReader.getInstance().getProperty(
						DmAlmConfigReaderProperties.SISS_SVN_PSW);
				repos = DmAlmConstants.REPOSITORY_SISS;
			}

			filePath = DmAlmConfigReader.getInstance().getProperty(
					getWorkitemStatusFilePathByWorkitemType(workitemType));

			SVNRepository repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			ISVNAuthenticationManager authManager = SVNWCUtil
					.createDefaultAuthenticationManager(name, psw.toCharArray());
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
				Set<String> stati = new HashSet<String>();

				if (workitemType.equals(Workitem_Type.EnumWorkitemType.anomalia_assistenza)
						|| workitemType.equals(Workitem_Type.EnumWorkitemType.taskit)) {

					NodeList nList = doc.getElementsByTagName("transition");

					for (int temp = 0; temp < nList.getLength(); temp++) {

						Node nNode = nList.item(temp);
						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;
							stati.add(eElement.getAttribute("from"));
							stati.add(eElement.getAttribute("to"));
						}
					}

					for (String stato : stati) {
						if (!stati.isEmpty()) {

							new SQLInsertClause(connection, dialect,
									dmAlmStatoWorkitem)
									.columns(
											dmAlmStatoWorkitem.descrizione,
											dmAlmStatoWorkitem.iconUrl,
											dmAlmStatoWorkitem.id,
											dmAlmStatoWorkitem.name,
											dmAlmStatoWorkitem.sortOrder,
											dmAlmStatoWorkitem.repository,
											dmAlmStatoWorkitem.workitemType,
											dmAlmStatoWorkitem.template)
									.values(StringTemplate
											.create("DM_ALM_STATO_WORKITEM_SEQ.nextval"),
											null,
											null,
											stato,
											stato,
											null,
											Expressions.constant(repos),
											Expressions.constant(workitemType
													.toString()),
											DataEsecuzione.getInstance()
													.getDataEsecuzione(),
											EnumUtils
													.getTemplateByWorkItem(workitemType)

									).execute();
						}
					}
				} else {
					NodeList nList = doc.getElementsByTagName("option");

					for (int temp = 0; temp < nList.getLength(); temp++) {
						Node nNode = nList.item(temp);

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {

							Element eElement = (Element) nNode;

							new SQLInsertClause(connection, dialect,
									dmAlmStatoWorkitem)
									.columns(
											dmAlmStatoWorkitem.descrizione,
											dmAlmStatoWorkitem.iconUrl,
											dmAlmStatoWorkitem.id,
											dmAlmStatoWorkitem.name,
											dmAlmStatoWorkitem.sortOrder,
											dmAlmStatoWorkitem.repository,
											dmAlmStatoWorkitem.workitemType,
											dmAlmStatoWorkitem.template)
									.values(eElement.getAttribute("description"),
											eElement.getAttribute("iconURL"),
											eElement.getAttribute("id"),
											eElement.getAttribute("name"),
											eElement.getAttribute("sortOrder"),
											Expressions.constant(repos),
											Expressions.constant(workitemType
													.toString()),
											EnumUtils
													.getTemplateByWorkItem(workitemType)

									).execute();
						}
					}
				}

				connection.commit();
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	private static String getWorkitemStatusFilePathByWorkitemType(
			EnumWorkitemType type) {

		String path = null;

		switch (type.toString()) {

		case "anomalia":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_ANOMALIA_FILE;
			break;

		case "anomalia_assistenza":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_ANOMALIAASSISTENZA_FILE;
			break;

		case "build":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_BUILD_FILE;
			break;

		case "defect":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_DIFETTO_FILE;
			break;

		case "documento":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_DOCUMENTO_FILE;
			break;

		case "fase":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_FASE_FILE;
			break;

		case "sman":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_MANUTENZIONE_FILE;
			break;

		case "pei":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_PEI_FILE;
			break;

		case "rqd":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_PROGETTODEMAND_FILE;
			break;

		case "progettoese":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_PROGETTOESE_FILE;
			break;

		case "drqs":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_PROGETTOSVILUPPODEMAND_FILE;
			break;

		case "srqs":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_PROGETTOSVILUPPOSVILUPPO_FILE;
			break;

		case "programma":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_PROGRAMMA_FILE;
			break;

		case "release_ser":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_RELEASESERVIZI_FILE;
			break;

		case "release_it":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_RELEASEIT_FILE;
			break;

		case "release":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_RELEASEDIPROGETTO_FILE;
			break;

		case "richiesta_gestione":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_RICHIESTAGESTIONE_FILE;
			break;

		case "dman":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_RICHIESTAMANUTENZIONE_FILE;
			break;

		case "sottoprogramma":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_SOTTOPROGRAMMA_FILE;
			break;

		case "task":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_TASK_FILE;
			break;

		case "taskit":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_TASKIT_FILE;
			break;

		case "testcase":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_TESTCASE_FILE;
			break;

		case "classificatore_demand":
			path = DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_CLASSIFICATORE_FILE;
			break;

		case "classificatore":
			path= DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_CLASSIFICATORE_GENERICO_FILE;
			break;
		case "sup":
			path=DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_SUP;
			break;
		case "rfc":
			path=DmAlmConfigReaderProperties.SIRE_SVN_STATOWORKITEM_RFC;
			break;
		default:
			path = "";
			break;
		}

		return path;
	}
}