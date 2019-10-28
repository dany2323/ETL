package lispa.schedulers.facade.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

import lispa.schedulers.bean.target.DmalmUserRolesSgr;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.UserRolesSgrDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.svn.SIREUserRolesXML;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.DateUtils;

public class UserRolesSgrFacade {

	private static Logger logger = Logger.getLogger(UserRolesSgrFacade.class);
	private static QDmalmProject project = QDmalmProject.dmalmProject;


	/**
	 * gli userRoles vengono presi splittati prima per projectID e poi su queste
	 * collection di liste viene applicato un ulteriore filtro per splittare gli
	 * userRoles con stesso projectID per data_modifica, questo avviene solo al
	 * primo riempimento degli userRoles
	 * 
	 * @param dataEsecuzione
	 */
	public static void execute(Timestamp dataEsecuzione) {
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			logger.debug("fillCurrentUserRoles - inizio");

			ConnectionManager cm = ConnectionManager.getInstance();
			Connection oraConn = cm.getConnectionOracle();

			String urlSiss = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_URL);
			String nameSiss = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_USERNAME);
			String pswSiss = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SISS_SVN_PSW);
			
			String urlSire = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_URL);
			String nameSire = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_USERNAME);
			String pswSire = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.SIRE_SVN_PSW);


			SVNRepository repositorySiss = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(urlSiss));
			ISVNAuthenticationManager authManagerSiss = SVNWCUtil.createDefaultAuthenticationManager(nameSiss,
					pswSiss);
			repositorySiss.setAuthenticationManager(authManagerSiss);
			
			SVNRepository repositorySire = SVNRepositoryFactory.create(SVNURL
					.parseURIEncoded(urlSire));
			ISVNAuthenticationManager authManagerSire = SVNWCUtil.createDefaultAuthenticationManager(nameSire,
					pswSire);
			repositorySire.setAuthenticationManager(authManagerSire);

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};
			
			
			UserRolesSgrDAO.deleteUserRoles();
			
			logger.info("Inizio gestione ruoli globali");
			logger.info("Inizio gestione SIRE");
			List<DmalmUserRolesSgr> userRolesGlobal = UserRolesSgrDAO.getUserRolesForProjectAtRevision(DmAlmConstants.REPOSITORY_SIRE,
					DmAlmConstants.GLOBAL, "", 
					repositorySire);
			for(DmalmUserRolesSgr projectUserRole: userRolesGlobal){
				UserRolesSgrDAO.insertUserRole(projectUserRole, 0, dataEsecuzione);
			}
			logger.info("Fine gestione SIRE");
			logger.info("Inizio gestione SISS");

			userRolesGlobal = UserRolesSgrDAO.getUserRolesForProjectAtRevision(DmAlmConstants.REPOSITORY_SISS,
					DmAlmConstants.GLOBAL, "", 
					repositorySiss);
			for(DmalmUserRolesSgr projectUserRole: userRolesGlobal){
				UserRolesSgrDAO.insertUserRole(projectUserRole, 0, dataEsecuzione);
			}
			logger.info("Fine gestione SISS");
			
			logger.info("Fine gestione ruoli globali");
			SQLQuery query = new SQLQuery(oraConn, dialect);

			logger.debug("fillCurrentUserRoles - query project");

			
			List<Tuple> dmalmProjects = query
					.from(project)
					.where(project.dtFineValidita.eq(DateUtils.setDtFineValidita9999()))
					.where(project.annullato.isNull())
					.orderBy(project.idProject.asc(), project.cRev.desc())
					.list(project.dmalmProjectPrimaryKey,project.pathProject, project.idProject, project.cRev,
							project.cCreated,project.idRepository);
			
			for (Tuple prj : dmalmProjects) {
//				logger.info("fillCurrentUserRoles - Inizio gestione progetto "+prj.get(project.idProject)+" - "+prj.get(project.idRepository));
				String projectSVNPath = SIREUserRolesXML.getProjectSVNPath(prj.get(project.pathProject));

				if (projectSVNPath != null) {
					List<DmalmUserRolesSgr> userRolesGroupedByProjID = new ArrayList<DmalmUserRolesSgr>();
					long revision = -1;
//					if(prj.get(project.cRev) != null) {
//						revision = prj.get(project.cRev);
//					}
					if(prj.get(project.idRepository).equals(DmAlmConstants.REPOSITORY_SISS)) {
						userRolesGroupedByProjID = UserRolesSgrDAO.getUserRolesForProjectAtRevision(prj.get(project.idRepository),
								prj.get(project.idProject), projectSVNPath,
								repositorySiss);
					}
					
					if(prj.get(project.idRepository).equals(DmAlmConstants.REPOSITORY_SIRE)) {
						userRolesGroupedByProjID = UserRolesSgrDAO.getUserRolesForProjectAtRevision(prj.get(project.idRepository),
								prj.get(project.idProject), projectSVNPath,
								repositorySire);
					}
					
					List<DmalmUserRolesSgr> listaNuovi = new ArrayList<DmalmUserRolesSgr>();
					List<DmalmUserRolesSgr> listaVecchi = new ArrayList<DmalmUserRolesSgr>();
				
					for(DmalmUserRolesSgr projectUserRole: userRolesGroupedByProjID){											
						int fkProject=prj.get(project.dmalmProjectPrimaryKey);
						
							UserRolesSgrDAO.insertUserRole(projectUserRole, fkProject, dataEsecuzione);
							righeNuove += 1;
							
						}
					
					
					// nuovi UserRoles
					for(DmalmUserRolesSgr projectUserRole: listaNuovi){	
						int fkProject=prj.get(project.dmalmProjectPrimaryKey);
						UserRolesSgrDAO.insertUserRole(projectUserRole, fkProject, dataEsecuzione);
						righeNuove += 1;						
					}
					

					
				}
				logger.info("fillCurrentUserRoles - Fine gestione progetto "+prj.get(project.idProject)+" - "+prj.get(project.idRepository));
			}
			
			
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();

			try {
				logger.info("START EsitiCaricamentoDAO.insert");
				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_USERROLES, stato, new Timestamp(
								dtInizioCaricamento.getTime()), new Timestamp(
								dtFineCaricamento.getTime()), righeNuove,
						righeModificate, 0, 0);
				logger.info("STOP EsitiCaricamentoDAO.insert");

			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
	private static boolean presenteSuLista(DmalmUserRolesSgr utenteRuolo, List<DmalmUserRolesSgr> lista) {
		boolean result = false;
		
		for(DmalmUserRolesSgr userRole : lista)
		{  
			if(userRole.getUserid().equalsIgnoreCase(utenteRuolo.getUserid()) && userRole.getRuolo().equalsIgnoreCase(utenteRuolo.getRuolo())) {
				result = true;
				break;
			}
		}
		
		return result;
	}

}