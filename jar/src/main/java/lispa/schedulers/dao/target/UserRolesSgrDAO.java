package lispa.schedulers.dao.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_USERROLES;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNNodeKind;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.internal.util.SVNURLUtil;
import org.tmatesoft.svn.core.io.SVNFileRevision;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Projections;

import lispa.schedulers.bean.target.DmalmUserRolesSgr;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.constant.DmalmRegex;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.staging.sgr.xml.QDmAlmUserRoles;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.QDmalmUserRolesSgr;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.StringUtils;

public class UserRolesSgrDAO {

	private static Logger logger = Logger.getLogger(UserRolesSgrDAO.class);

	private static QDmalmUserRolesSgr dmalmUserRoles = QDmalmUserRolesSgr.dmalmUserRolesSgr;
	private static QDmalmUserRolesSgr dmalmUserRoles2 = QDmalmUserRolesSgr.dmalmUserRolesSgr;

	private static QDmalmProject dmalmProject = QDmalmProject.dmalmProject;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static Collection<List<DmalmUserRolesSgr>> getAllUserRolesGroupedByProjectIDandRevision(
			Timestamp dataEsecuzione, String ID, String repository)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		DmalmUserRolesSgr bean = null;
		Map<String, List<DmalmUserRolesSgr>> userroles = new HashMap<String, List<DmalmUserRolesSgr>>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_USERROLES);

			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);
			ps.setTimestamp(1, dataEsecuzione);
			ps.setString(2, ID);
			ps.setString(3, repository);

			rs = ps.executeQuery();
			logger.info("userroles.sql eseguito");


			while (rs.next()) {
				bean = new DmalmUserRolesSgr();

				bean.setDmalmUserRolesPk(rs.getInt("DMALM_USER_ROLES_PK"));
				bean.setOrigine(rs.getString("ORIGINE"));
				bean.setUserid(rs.getString("USERID"));
				bean.setRuolo(rs.getString("RUOLO"));
				bean.setRepository(rs.getString("REPOSITORY"));
				bean.setDtCaricamento(dataEsecuzione);
				bean.setDtInizioValidita(DateUtils.setDtInizioValidita1900());
				bean.setDtFineValidita(DateUtils.setDtFineValidita9999());
				bean.setDtModifica(rs.getTimestamp("DT_MODIFICA"));

				List<DmalmUserRolesSgr> list = userroles.get(bean.getOrigine()
						+ "@" + bean.getRepository() + "@"
						+ bean.getDtModifica());
				if (list == null) {
					list = new ArrayList<DmalmUserRolesSgr>();
					userroles.put(
							bean.getOrigine() + "@" + bean.getRepository()
									+ "@" + bean.getDtModifica(), list);
				}

				list.add(bean);
			}
			
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		List<List<DmalmUserRolesSgr>> list = new ArrayList<List<DmalmUserRolesSgr>>(
				userroles.values());
		Collections.sort(list, new Comparator<List<DmalmUserRolesSgr>>() {

			@Override
			public int compare(List<DmalmUserRolesSgr> o1,
					List<DmalmUserRolesSgr> o2) {
				return o1.get(0).getDtModifica()
						.compareTo(o2.get(0).getDtModifica());
			}

		});

		return list;
	}

	public static List<Tuple> getDistinctProjectID() throws Exception {

		QDmAlmUserRoles stgUserRoles = QDmAlmUserRoles.dmAlmUserRoles;

		ConnectionManager cm = null;
		Connection conn = null;
		List<Tuple> IDs = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();

			IDs = new SQLQuery(conn, dialect).from(stgUserRoles).distinct()
					.list(stgUserRoles.idProject, stgUserRoles.repository);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null)
				cm.closeConnection(conn);
		}
		return IDs;

	}

	public static List<DmalmUserRolesSgr> getUserRolesByProjectID(
			String projID, String repo) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<DmalmUserRolesSgr> userRoles = new ArrayList<DmalmUserRolesSgr>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			userRoles = new SQLQuery(connection, dialect)
					.from(dmalmUserRoles)
					.where(dmalmUserRoles.origine.eq(projID))
					.where(dmalmUserRoles.repository.eq(repo))
					.where(dmalmUserRoles.dtFineValidita.in(new SQLSubQuery()
							.from(dmalmUserRoles2).list(
									dmalmUserRoles2.dtFineValidita.max())))
					.list(Projections.bean(DmalmUserRolesSgr.class,
							dmalmUserRoles.all()));

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return userRoles;

	}

	public static void insertUserRolesUpdate(List<DmalmUserRolesSgr> userRoles,
			int fkProject, Timestamp c_created) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					dmalmUserRoles);
			int i = 0;
			for (DmalmUserRolesSgr bean : userRoles) {
				bean.setDtInizioValidita(c_created);
				bean.setDmalmProjectFk01(fkProject);
				insert.columns(dmalmUserRoles.dmalmUserRolesPk,
						dmalmUserRoles.origine, dmalmUserRoles.userid,
						dmalmUserRoles.ruolo, dmalmUserRoles.dtCaricamento,
						dmalmUserRoles.dtInizioValidita,
						dmalmUserRoles.dtFineValidita,
						dmalmUserRoles.repository,
						dmalmUserRoles.dmalmProjectFk01)
						.values(bean.getDmalmUserRolesPk(), bean.getOrigine(),
								bean.getUserid(), bean.getRuolo(),
								bean.getDtCaricamento(),
								bean.getDtInizioValidita(),
								bean.getDtFineValidita(), bean.getRepository(),
								fkProject).addBatch();
				i++;
				if (i % DmAlmConstants.BATCH_SIZE == 0) {
					insert.execute();
					insert = new SQLInsertClause(connection, dialect,
							dmalmUserRoles);
				}
			}

			if (!insert.isEmpty())
				insert.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}
	
	public static void insertUserRoleUpdate(DmalmUserRolesSgr projectUserRole, int fkProject, Timestamp c_created, Timestamp dataEsecuzione) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					dmalmUserRoles);
			int i = 0;

			insert.columns(dmalmUserRoles.dmalmUserRolesPk,
					dmalmUserRoles.origine, dmalmUserRoles.userid,
					dmalmUserRoles.ruolo, dmalmUserRoles.dtCaricamento,
					dmalmUserRoles.dtInizioValidita,
					dmalmUserRoles.dtFineValidita,
					dmalmUserRoles.repository,
					dmalmUserRoles.dmalmProjectFk01)
					.values(getMaxPk(), 
							projectUserRole.getOrigine(),
							projectUserRole.getUserid(), 
							projectUserRole.getRuolo(),
							dataEsecuzione,
							c_created,
							DateUtils.setDtFineValidita9999(), 
							projectUserRole.getRepository(),
							fkProject).addBatch();
			i++;
			if (i % DmAlmConstants.BATCH_SIZE == 0) {
				insert.execute();
				insert = new SQLInsertClause(connection, dialect,
						dmalmUserRoles);
			}
			

			if (!insert.isEmpty())
				insert.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}	
	}

	public static void insertUserRoles(List<DmalmUserRolesSgr> userRoles,
			int fkProject) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					dmalmUserRoles);
			int i = 0;
			for (DmalmUserRolesSgr bean : userRoles) {
				bean.setDmalmProjectFk01(fkProject);
				insert.columns(dmalmUserRoles.dmalmUserRolesPk,
						dmalmUserRoles.origine, dmalmUserRoles.userid,
						dmalmUserRoles.ruolo, dmalmUserRoles.dtCaricamento,
						dmalmUserRoles.dtInizioValidita,
						dmalmUserRoles.dtFineValidita,
						dmalmUserRoles.repository,
						dmalmUserRoles.dmalmProjectFk01)
						.values(bean.getDmalmUserRolesPk(), bean.getOrigine(),
								bean.getUserid(), bean.getRuolo(),
								bean.getDtCaricamento(),
								bean.getDtInizioValidita(),
								bean.getDtFineValidita(), bean.getRepository(),
								fkProject).addBatch();
				i++;
				if (i % DmAlmConstants.BATCH_SIZE == 0) {
					insert.execute();
					insert = new SQLInsertClause(connection, dialect,
							dmalmUserRoles);
				}
			}

			if (!insert.isEmpty())
				insert.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}
	
	public static void insertUserRole(DmalmUserRolesSgr userRole,
			int fkProject, Timestamp dataEsecuzione) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					dmalmUserRoles);
			int i = 0;
			userRole.setDmalmProjectFk01(fkProject);
			insert.columns(dmalmUserRoles.dmalmUserRolesPk,
					dmalmUserRoles.origine, dmalmUserRoles.userid,
					dmalmUserRoles.ruolo, dmalmUserRoles.dtCaricamento,
					dmalmUserRoles.dtInizioValidita,
					dmalmUserRoles.dtFineValidita,
					dmalmUserRoles.repository,
					dmalmUserRoles.dmalmProjectFk01)
					.values(getMaxPk(), userRole.getOrigine(),
							userRole.getUserid(), userRole.getRuolo(),
							dataEsecuzione,
							dataEsecuzione,
							DateUtils.setDtFineValidita9999(), 
							userRole.getRepository(),
							fkProject).addBatch();
			i++;
			if (i % DmAlmConstants.BATCH_SIZE == 0) {
				insert.execute();
				insert = new SQLInsertClause(connection, dialect,
						dmalmUserRoles);
			}
			

			if (!insert.isEmpty())
				insert.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateDataFineValidita(String projID, String repo,
			Timestamp c_created) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmUserRoles)
					.where(dmalmUserRoles.origine.eq(projID))
					.where(dmalmUserRoles.repository.eq(repo))
					.where(dmalmUserRoles.dtFineValidita.in(new SQLSubQuery()
							.from(dmalmUserRoles)
							.where(dmalmUserRoles.origine.eq(projID))
							.where(dmalmUserRoles.repository.eq(repo))
							.list(dmalmUserRoles.dtFineValidita.max())))
					.set(dmalmUserRoles.dtFineValidita,
							DateUtils.addSecondsToTimestamp(c_created, -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateDataFineValiditaUserRole(
			DmalmUserRolesSgr userRole, Timestamp c_created)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, dmalmUserRoles)
					.where(dmalmUserRoles.dmalmUserRolesPk.eq(userRole
							.getDmalmUserRolesPk()))
					.set(dmalmUserRoles.dtFineValidita,
							DateUtils.addSecondsToTimestamp(c_created, -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static int getFkProject(String projId, String repo,
			Timestamp c_created) throws DAOException {

		if (projId == null || projId.equals(DmAlmConstants.GLOBAL))
			return 0;

		ConnectionManager cm = null;
		Connection connection = null;

		int res = 0;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			List<Integer> fk = new SQLQuery(connection, dialect)
					.from(dmalmProject)
					.where(dmalmProject.idProject.eq(projId))
					.where(dmalmProject.idRepository.eq(repo))
					.where(dmalmProject.dtInizioValidita.loe(c_created))
					.where(dmalmProject.dtFineValidita.goe(c_created))
					.list(dmalmProject.dmalmProjectPrimaryKey);

			if (fk == null || fk.size() == 0)
				return 0;
			else
				return fk.get(0);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return res;

	}
	
	public static List<DmalmUserRolesSgr> getUserRolesForProjectAtRevision(String myrepo,
			String projectId, String projectLocation, long c_rev,
			Timestamp c_created, SVNRepository repository) throws Exception {
		
		List<DmalmUserRolesSgr> projectUserRoles = new ArrayList<DmalmUserRolesSgr>();

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
									DmAlmConfigReaderProperties.SIRE_SVN_USER_ROLES_FILE));

			SVNNodeKind nodeKind = repository.checkPath(filePath, c_rev);
			SVNProperties fileProperties = null;
			ByteArrayOutputStream baos = null;
			fileProperties = new SVNProperties();

			SVNFileRevision svnfr = new SVNFileRevision(filePath, c_rev, fileProperties, fileProperties);

			baos = new ByteArrayOutputStream();
			if(repository.checkPath(svnfr.getPath(), svnfr.getRevision()) == SVNNodeKind.NONE){
				if(svnfr.getRevision() == -1)
					logger.info("Il path SVN " + svnfr.getPath() + " non esiste alla revisione HEAD");
				else	
					logger.info("Il path SVN " + svnfr.getPath() + " non esiste alla revisione " + svnfr.getRevision());
				return projectUserRoles;
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

								DmalmUserRolesSgr userRolesSgr = new DmalmUserRolesSgr();
								userRolesSgr.setOrigine(projectId);
								userRolesSgr.setUserid(StringUtils.getMaskedValue(eElement.getAttribute("name")));
								userRolesSgr.setRuolo(el.getAttribute("name"));
								userRolesSgr.setRepository(myrepo);
								userRolesSgr.setDtModifica(DateUtils.stringToTimestamp(data,"yyyy-MM-dd HH:mm:ss"));

								projectUserRoles.add(userRolesSgr);
								
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
				
		return projectUserRoles;
	}

	
	private static Integer getMaxPk() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Integer resUserRolesSgrPk = 0;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.USER_ROLES_SGR_PK_MAXVAL);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();
			rs.next();
			resUserRolesSgrPk = rs.getInt("DMALM_USER_ROLES_PK")+1;
			
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return resUserRolesSgrPk;
	}


/**
 * Cancellazione dei record tabella DMALM_USER_ROLES_SGR 
 * che non hanno più alcuna corrispondenza in Polarion
 * DM_ALM-292
 * @param userRole
 * @throws DAOException
 * @throws SQLException
 */
	public static void deleteUserRolesDeletedInPolarion(DmalmUserRolesSgr userRole)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			connection.setAutoCommit(false);

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConstants.DELETE_OLD_USER_ROLES);

			ps = connection.prepareStatement(sql);
			
			ps.setInt(1, userRole.getDmalmUserRolesPk());

			ps.executeUpdate();
			
			connection.commit();

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
	
	/**
	 * Cancellazione dei dati storici nella tabella DMALM_USER_ROLES_SGR
	 * DM_ALM-292
	 * @throws DAOException
	 * @throws SQLException
	 */
		public static void deleteUserRolesHistoricalData() throws DAOException, SQLException {
			ConnectionManager cm = null;
			Connection connection = null;
			PreparedStatement ps = null;

			try {
				cm = ConnectionManager.getInstance();
				connection = cm.getConnectionOracle();
				
				connection.setAutoCommit(false);
				
				String query="";
				
				query = "delete from dmalm_user_roles_sgr "
						+ "where dt_fine_validita < ?";

				ps = connection.prepareStatement(query);

				ps.setTimestamp(1, DateUtils.setDtFineValidita9999());
				
				ps.executeUpdate();
				
				connection.commit();

			} catch (SQLException e) {
				ErrorManager.getInstance().exceptionOccurred(true, e);
				logger.error(e.getMessage(), e);
			} catch (DAOException e) {
				ErrorManager.getInstance().exceptionOccurred(true, e);
				logger.error(e.getMessage(), e);
			} catch (Exception e) {
				ErrorManager.getInstance().exceptionOccurred(true, e);
				logger.error(e.getMessage(), e);
			} finally {
				if (ps != null) {
					ps.close();
				}
				if (cm != null) {
					cm.closeConnection(connection);
				}
			}
		}
		
/**
 * Data la PK aggiorna la FK del progetto e la Data di caricamento
 * @param projectUserRole
 * @param fkProject
 * @param dataEsecuzione
 * @throws DAOException
 */
		public static void updateUserRoles(DmalmUserRolesSgr projectUserRole, int fkProject, 
				Timestamp dataEsecuzione) throws DAOException {

			ConnectionManager cm = null;
			Connection connection = null;

			try {

				cm = ConnectionManager.getInstance();
				connection = cm.getConnectionOracle();

				connection.setAutoCommit(false);

				new SQLUpdateClause(connection, dialect, dmalmUserRoles)
						.where(dmalmUserRoles.dmalmUserRolesPk.eq(projectUserRole.getDmalmUserRolesPk()))
						.set(dmalmUserRoles.dmalmProjectFk01, fkProject)
						.set(dmalmUserRoles.dtCaricamento, dataEsecuzione)
						.execute();

				connection.commit();

			} catch (Exception e) {
				ErrorManager.getInstance().exceptionOccurred(true, e);

			} finally {
				if (cm != null)
					cm.closeConnection(connection);
			}

		}				
}