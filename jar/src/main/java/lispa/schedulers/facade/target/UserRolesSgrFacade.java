package lispa.schedulers.facade.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

			SQLQuery query = new SQLQuery(oraConn, dialect);

			logger.debug("fillCurrentUserRoles - query project");

			List<Tuple> locations = query
					.from(project)
					.orderBy(project.idProject.asc(), project.cRev.desc())
					.list(project.pathProject, project.idProject, project.cRev,
							project.cCreated,project.idRepository);

			List<Tuple> distinctDmalmProjects = new ArrayList<Tuple>();
			String compare = "";
			for (Tuple l : locations) {
				if (!compare.equals(l.get(project.idProject))) {
					distinctDmalmProjects.add(l);
					compare = l.get(project.idProject);
				}

			}

			logger.debug("fillCurrentUserRoles - distinctDmalmProjects.size: "
					+ distinctDmalmProjects.size());

			for (Tuple prj : distinctDmalmProjects) {
				String projectSVNPath = SIREUserRolesXML.getProjectSVNPath(prj.get(project.pathProject));

				if (projectSVNPath != null) {
					List<DmalmUserRolesSgr> userRolesGroupedByProjID = new ArrayList<DmalmUserRolesSgr>();
					long revision = -1;
					if(prj.get(project.cRev) != null) {
						revision = prj.get(project.cRev);
					}
					if(prj.get(project.idRepository).equals(DmAlmConstants.REPOSITORY_SISS)) {
						userRolesGroupedByProjID = UserRolesSgrDAO.getUserRolesForProjectAtRevision(prj.get(project.idRepository),
								prj.get(project.idProject), projectSVNPath,
								revision, prj.get(project.cCreated), 
								repositorySiss);
					}
					if(prj.get(project.idRepository).equals(DmAlmConstants.REPOSITORY_SIRE)) {
						System.out.println(prj.get(project.idRepository));
						System.out.println(prj.get(project.idProject));
						System.out.println(prj.get(project.cRev));
						System.out.println(prj.get(project.cCreated));
						userRolesGroupedByProjID = UserRolesSgrDAO.getUserRolesForProjectAtRevision(prj.get(project.idRepository),
								prj.get(project.idProject), projectSVNPath,
								revision, prj.get(project.cCreated), 
								repositorySire);
					}
				
					for(DmalmUserRolesSgr projectUserRole: userRolesGroupedByProjID){					
						logger.info("START UserRolesSgrDAO.getUserRolesByProjectID per "+prj.get(project.idProject)+" in "+prj.get(project.idRepository));
						List<DmalmUserRolesSgr> listaTarget = UserRolesSgrDAO.getUserRolesByProjectID(prj.get(project.idProject),
								prj.get(project.idRepository));
						logger.info("STOP UserRolesSgrDAO.getUserRolesByProjectID");
						
						Timestamp c_created = projectUserRole.getDtModifica();
						logger.info("START UserRolesSgrDAO.getFkProject");
						int fkProject = UserRolesSgrDAO.getFkProject(prj.get(project.idProject),
								prj.get(project.idRepository), c_created);
						logger.info("STOP UserRolesSgrDAO.getFkProject");
						
						if (listaTarget.size() == 0) {
							//inserisce tutti
							logger.info("Inserisco nuovo record");
							UserRolesSgrDAO.insertUserRole(projectUserRole, fkProject, dataEsecuzione);
							righeNuove += 1;
						} else {
							if (BeanUtils.areDifferent(fkProject, listaTarget
									.get(0).getDmalmProjectFk01())) {
								//storicizza e inserisce i nuovi
								logger.info("Storicizzo record");
								logger.info("Cambio data fine validità");
								UserRolesSgrDAO.updateDataFineValidita(prj.get(project.idProject),
										prj.get(project.idRepository), c_created);
								
								logger.info("Inserisco nuova versione del record");
								UserRolesSgrDAO.insertUserRoleUpdate(projectUserRole,
										fkProject, c_created);
								
								righeModificate += 1;
							} else {
								//inserisce i nuovi e chiude i vecchi non più presenti
								logger.info("Inserisco i nuovi record e chiudo i vecchi non più presenti");
								List<DmalmUserRolesSgr> listaNuovi = new ArrayList<DmalmUserRolesSgr>();
								
								if(!presenteSuLista(projectUserRole, listaTarget)) {
									listaNuovi.add(projectUserRole);
								}
								
								logger.info("Inserisco nuova versione del record");
								for(DmalmUserRolesSgr nuovo: listaNuovi){
									UserRolesSgrDAO.insertUserRoleUpdate(nuovo,
											fkProject, c_created);
									righeNuove += 1;
								}
								
								for(DmalmUserRolesSgr userRole : listaTarget)
								{  
									if(!presenteSuLista(userRole, userRolesGroupedByProjID)) {
										logger.info("Cambio data fine validità");
										UserRolesSgrDAO.updateDataFineValiditaUserRole(userRole, c_created);
										righeModificate += 1;
									}
								}
							}
						}
					}
				}
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