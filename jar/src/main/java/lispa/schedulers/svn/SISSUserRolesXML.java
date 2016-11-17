package lispa.schedulers.svn;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.dao.UserRolesDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.siss.history.QSissHistoryProject;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmUserRoles;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNRevisionProperty;
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

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.types.template.StringTemplate;

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

	private static Timestamp dataEsecuzione = DataEsecuzione.getInstance()
			.getDataEsecuzione();

	static SVNRepository repository;
	static ISVNAuthenticationManager authManager;

	// public static void fillSISSCurrentUserRoles() throws Exception
	// {
	//
	// Connection H2Connection = null;
	// ConnectionManager cm = null;
	//
	// try{
	//
	//
	// cm = ConnectionManager.getInstance();
	// H2Connection = cm.getConnectionSISSCurrent();
	// int numberofusers =
	// QueryUtils.getCountUserRoles(DmAlmConstants.REPOSITORY_SISS);
	// if(numberofusers != 0) {
	//
	// url =
	// DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SISS_SVN_URL);
	// name =
	// DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
	// psw =
	// DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SISS_SVN_PASSWORD);
	//
	// repository = SVNRepositoryFactory.create( SVNURL.parseURIEncoded( url )
	// );
	// authManager = SVNWCUtil.createDefaultAuthenticationManager( name ,
	// psw );
	// repository.setAuthenticationManager( authManager );
	//
	//
	// SQLTemplates dialect = new HSQLDBTemplates(){ {
	// setPrintSchema(true);
	// }};
	//
	// SissCurrentProject project = SissCurrentProject.project;
	//
	// SQLQuery query = new SQLQuery(H2Connection, dialect);
	//
	//
	// List<Tuple> locations = query.distinct()
	// .from(project)
	// .list(project.cLocation, project.cPk, project.cId);
	//
	//
	// for(Tuple el : locations)
	// {
	//
	//
	// String projectSVNPath = getProjectSVNPath(el.get(project.cLocation));
	// if(projectSVNPath!=null)
	// {
	// insertUserRoles(DmAlmConstants.REPOSITORY_SISS, el.get(project.cId),
	// projectSVNPath, el.get(project.cPk),
	// DataEsecuzione.getInstance().getDataEsecuzione());
	// }
	// }
	//
	// insertGlobalUserRoles(DmAlmConstants.REPOSITORY_SISS);
	//
	// }
	// }
	// catch(Exception e)
	// {
	// logger.error(e.getMessage(), e);
	//
	// }
	// finally
	// {
	// if(cm != null) cm.closeConnection(H2Connection);
	// }
	//
	//
	// }

	public static void fillSISSHistoryUserRoles() throws DAOException {
		Connection oraConn = null;
		ConnectionManager cm = null;

		try {
			// 20150623
			logger.debug("fillSISSHistoryUserRoles - inizio");

			cm = ConnectionManager.getInstance();

			oraConn = cm.getConnectionOracle();

			url = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_URL);
			name = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
			psw = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_PSW);

			repository = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(url));
			authManager = SVNWCUtil.createDefaultAuthenticationManager(name,
					psw);
			repository.setAuthenticationManager(authManager);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			QSissHistoryProject project = QSissHistoryProject.sissHistoryProject;

			SQLQuery query = new SQLQuery(oraConn, dialect);

			// 20150623
			logger.debug("fillSISSHistoryUserRoles - query project");

			List<Tuple> locations = query
					.from(project)
					.orderBy(project.cId.asc(), project.cRev.desc())
					.list(project.cLocation, project.cId, project.cRev,
							project.cCreated);

			List<Tuple> filteredLocations = new ArrayList<Tuple>();
			String compare = "";
			for (Tuple l : locations) {
				if (!compare.equals(l.get(project.cId))) {
					filteredLocations.add(l);
					compare = l.get(project.cId);
				}

			}

			// 20150623
			logger.debug("fillSISSHistoryUserRoles - filteredLocations.size: "
					+ filteredLocations.size());

			long minRevision = UserRolesDAO
					.getMinRevisionGlobal(DmAlmConstants.REPOSITORY_SISS);

			// 20150623
			logger.debug("fillSISSHistoryUserRoles - POST getMinRevision, minRevision: "
					+ minRevision);

			for (Tuple el : filteredLocations) {

				// 20150623
				// logger.debug("fillSISSHistoryUserRoles - getProjectSVNPath clocation: "
				// + el.get(project.cLocation));

				String projectSVNPath = getProjectSVNPath(el
						.get(project.cLocation));

				// 20150623
				// logger.debug("fillSISSHistoryUserRoles - getMinRevision cId: "
				// + el.get(project.cId));

				// Spostato esternamente
				// long minRevision =
				// UserRolesDAO.getMinRevision(el.get(project.cId),
				// DmAlmConstants.REPOSITORY_SISS);

				// 20150623
				// logger.debug("fillSISSHistoryUserRoles - POST getMinRevision, minRevision: "
				// + minRevision);

				if (projectSVNPath != null) {
					// 20150623
					// logger.debug("fillSISSHistoryUserRoles - start insertUserRolesfromScratch");

					insertUserRolesfromScratch(DmAlmConstants.REPOSITORY_SISS,
							el.get(project.cId), projectSVNPath,
							el.get(project.cPk), el.get(project.cRev),
							el.get(project.cCreated), minRevision);

					// 20150623
					// logger.debug("fillSISSHistoryUserRoles - stop insertUserRolesfromScratch");
				}
			}

			// 20150623
			logger.debug("fillSISSHistoryUserRoles - start insertGlobalUserRoles");

			insertGlobalUserRoles(DmAlmConstants.REPOSITORY_SISS);

			// 20150623
			logger.debug("fillSISSHistoryUserRoles - fine");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(oraConn);
			}
		}
	}

	public static String getProjectSVNPath(String clocation) {
		if (clocation.startsWith("default:/")) {
			int start = clocation.indexOf(":/") + 1;

			int end = clocation.indexOf(".polarion") - 1;

			return clocation.substring(start, end);
		} else {
			return null;
		}
	}

	public static void insertUserRolesfromScratch(String myrepo,
			String projectname, String projectLocation, String cPk, long c_rev,
			Timestamp c_created, long minRevision) throws Exception {

		QDmAlmUserRoles qDmAlmUserRoles = QDmAlmUserRoles.dmAlmUserRoles;
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		String filePath = "";

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			DAVRepositoryFactory.setup();

			connection.setAutoCommit(false);
			SVNURL root = repository.getRepositoryRoot(true);
			String absolutepath = root + projectLocation;
			projectLocation = SVNURLUtil.getRelativeURL(root,
					SVNURL.parseURIEncoded(absolutepath), false);
			filePath = projectLocation
					.concat(DmAlmConfigReader
							.getInstance()
							.getProperty(
									DmAlmConfigReaderProperties.SISS_SVN_USER_ROLES_FILE));

			SVNNodeKind nodeKind = repository.checkPath(filePath, c_rev);
			SVNProperties fileProperties = null;
			ByteArrayOutputStream baos = null;

			Collection<SVNFileRevision> revisions = new ArrayList<SVNFileRevision>();

			// 20150623
			// logger.debug("insertUserRolesfromScratch - start getFileRevisions, project: "
			// + projectname);

			repository.getFileRevisions(filePath, revisions, minRevision + 1,
					repository.getLatestRevision());

			// 20150623
			// logger.debug("insertUserRolesfromScratch - start loop revisions, size: "
			// + (revisions==null?"NULL":revisions.size()));

			for (SVNFileRevision svnfr : revisions) {
				// 20150623
				// logger.debug("*** insertUserRolesfromScratch - revision letta: "
				// + svnfr.getRevision());

				if (svnfr.getRevision() > minRevision) {
					// 20150623
					// logger.debug("*** OK insertUserRolesfromScratch - revision lavorata: "
					// + svnfr.getRevision());

					fileProperties = new SVNProperties();
					baos = new ByteArrayOutputStream();
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
								.getSVNPropertyValue(SVNRevisionProperty.DATE)
								.getString().toString().replace("T", " ")
								.replaceFirst(DmalmRegex.REGEXSVNDATE, "");

						int i = 0;
						SQLInsertClause insert = new SQLInsertClause(
								connection, dialect, qDmAlmUserRoles);

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

										insert.columns(
												qDmAlmUserRoles.dmAlmUserRolesPk,
												qDmAlmUserRoles.idProject,
												qDmAlmUserRoles.utente,
												qDmAlmUserRoles.ruolo,
												qDmAlmUserRoles.repository,
												qDmAlmUserRoles.dataCaricamento,
												qDmAlmUserRoles.dataModifica,
												qDmAlmUserRoles.revision)
												.values(StringTemplate
														.create("DM_ALM_USER_ROLES_SEQ.nextval"),
														projectname,
														StringUtils.getMaskedValue(eElement.getAttribute("name")),
														el.getAttribute("name"),
														myrepo,
														dataEsecuzione,
														DateUtils
																.stringToTimestamp(
																		data,
																		"yyyy-MM-dd HH:mm:ss"),
														svnfr.getRevision())
												.addBatch();

										i++;
										if (i % DmAlmConstants.BATCH_SIZE == 0) {
											insert.execute();
											insert = new SQLInsertClause(
													connection, dialect,
													qDmAlmUserRoles);
										}
									}
								}
							}
						}

						if (!insert.isEmpty())
							insert.execute();

						connection.commit();
					}
				}

			} // for

			// 20150623
			// logger.debug("insertUserRolesfromScratch - stop loop revisions");
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if(cm != null) { 
				cm.closeConnection(connection);
			}
		}
	}

	// public static void insertUserRoles(String myrepo, String projectname,
	// String projectLocation, String cPk, Timestamp dataEsecuzione) throws
	// Exception
	// {
	//
	// QDmAlmUserRoles qDmAlmUserRoles = QDmAlmUserRoles.dmAlmUserRoles;
	// SQLTemplates dialect = new HSQLDBTemplates();
	// Connection connection = null;
	// ConnectionManager cm = null;
	//
	//
	// String filePath = "";
	//
	// try{
	//
	// cm = ConnectionManager.getInstance();
	// connection = cm.getConnectionOracle();
	// DAVRepositoryFactory.setup( );
	//
	// connection.setAutoCommit(false);
	//
	// filePath =
	// projectLocation.concat(DmAlmConfigReader.getInstance().getProperty(DmAlmConfigReaderProperties.SISS_SVN_USER_ROLES_FILE));
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
	// xmlContent = baos.toString();
	//
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
	// int i=0;
	// SQLInsertClause insert = new SQLInsertClause(connection, dialect,
	// qDmAlmUserRoles);
	//
	// for (int temp = 0; temp < nList.getLength(); temp++) {
	//
	// Node nNode = nList.item(temp);
	//
	// if (nNode.getNodeType() == Node.ELEMENT_NODE) {
	//
	// Element eElement = (Element) nNode;
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
	// insert
	// .columns(
	// qDmAlmUserRoles.dmAlmUserRolesPk,
	// qDmAlmUserRoles.siglaProject,
	// qDmAlmUserRoles.idProject,
	// qDmAlmUserRoles.utente,
	// qDmAlmUserRoles.ruolo,
	// qDmAlmUserRoles.repository,
	// qDmAlmUserRoles.dataCaricamento,
	// qDmAlmUserRoles.cPk,
	// qDmAlmUserRoles.dataModifica
	// )
	// .values
	// (
	// StringTemplate.create("DM_ALM_USER_ROLES_SEQ.nextval"),
	// projectLocation,
	// projectname,
	// eElement.getAttribute("name"),
	// el.getAttribute("name"),
	// myrepo,
	// DataEsecuzione.getInstance().getDataEsecuzione(),
	// cPk,
	// dataEsecuzione
	// )
	// .addBatch();
	//
	// i++;
	// if(i % DmAlmConstants.BATCH_SIZE == 0) {
	// insert.execute();
	// insert = new SQLInsertClause(connection, dialect, qDmAlmUserRoles);
	// }
	//
	// }
	// }
	// }
	// }
	//
	// if(!insert.isEmpty())
	// insert.execute();
	//
	// connection.commit();
	//
	// }
	//
	// }
	// catch(Exception e)
	// {
	// ErrorManager.getInstance().exceptionOccurred(true, e);
	// logger.error(e.getMessage(), e);
	//
	//
	// }
	// finally
	// {
	// if(cm != null) cm.closeConnection(connection);
	// }
	//
	// }

	public static List<String> getUtentiIT() throws Exception {
		Connection connection = null;
		ConnectionManager cm = null;

		String url = "";
		String name = "";
		String psw = "";
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
					DmAlmConfigReaderProperties.SISS_SVN_PSW);
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
			if(cm != null) { 
				cm.closeConnection(connection);
			}
		}

		return utentiIT;
	}

	public static void insertGlobalUserRoles(String myrepo) throws Exception {

		QDmAlmUserRoles qDmAlmUserRoles = QDmAlmUserRoles.dmAlmUserRoles;
		SQLTemplates dialect = new HSQLDBTemplates();
		Connection connection = null;
		ConnectionManager cm = null;

		String url = "";
		String name = "";
		String psw = "";
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
			psw = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_PSW);
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

				int i = 0;
				SQLInsertClause insert = new SQLInsertClause(connection,
						dialect, qDmAlmUserRoles);

				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);

					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;

						NodeList ruoli = eElement.getChildNodes();

						for (int tempruolo = 0; tempruolo < ruoli.getLength(); tempruolo++) {

							Node ruolo = ruoli.item(tempruolo);

							if (ruolo.getNodeType() == Node.ELEMENT_NODE) {

								Element el = (Element) ruolo;

								insert.columns(
										qDmAlmUserRoles.dmAlmUserRolesPk,
										qDmAlmUserRoles.idProject,
										qDmAlmUserRoles.utente,
										qDmAlmUserRoles.ruolo,
										qDmAlmUserRoles.repository,
										qDmAlmUserRoles.dataCaricamento,
										qDmAlmUserRoles.dataModifica)
										.values(StringTemplate
												.create("DM_ALM_USER_ROLES_SEQ.nextval"),
												DmAlmConstants.GLOBAL,
												eElement.getAttribute("name"),
												el.getAttribute("name"),
												myrepo, dataEsecuzione,
												dataEsecuzione).addBatch();

								i++;
								if (i % DmAlmConstants.BATCH_SIZE == 0) {
									insert.execute();
									insert = new SQLInsertClause(connection,
											dialect, qDmAlmUserRoles);
								}
							}
						}
					}
				}

				if (!insert.isEmpty())
					insert.execute();

				connection.commit();
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if(cm != null) { 
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverSISSUserRoles() throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
			QDmAlmUserRoles qDmAlmUserRoles = QDmAlmUserRoles.dmAlmUserRoles;
			new SQLDeleteClause(connection, dialect, qDmAlmUserRoles).where(
					qDmAlmUserRoles.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione())).execute();
			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if(cm != null) { 
				cm.closeConnection(connection);
			}
		}
	}
}