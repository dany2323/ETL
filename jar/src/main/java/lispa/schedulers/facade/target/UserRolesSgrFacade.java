package lispa.schedulers.facade.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

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
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
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

			SQLTemplates dialect = new HSQLDBTemplates() {
				{
					setPrintSchema(true);
				}
			};

			UserRolesSgrDAO.deleteUserRoles();

			logger.info("Inizio gestione ruoli globali");
			logger.info("Inizio gestione SIRE");
			List<DmalmUserRolesSgr> userRolesGlobal = UserRolesSgrDAO
					.getUserRolesForProject(DmAlmConstants.REPOSITORY_SIRE, DmAlmConstants.GLOBAL);
			for (DmalmUserRolesSgr projectUserRole : userRolesGlobal) {
				UserRolesSgrDAO.insertUserRole(projectUserRole, 0, dataEsecuzione);
			}
			logger.info("Fine gestione SIRE");
			logger.info("Inizio gestione SISS");

			userRolesGlobal = UserRolesSgrDAO.getUserRolesForProject(DmAlmConstants.REPOSITORY_SISS,
					DmAlmConstants.GLOBAL);
			for (DmalmUserRolesSgr projectUserRole : userRolesGlobal) {
				UserRolesSgrDAO.insertUserRole(projectUserRole, 0, dataEsecuzione);
			}
			logger.info("Fine gestione SISS");

			logger.info("Fine gestione ruoli globali");
			SQLQuery query = new SQLQuery(oraConn, dialect);

			logger.debug("fillCurrentUserRoles - query project");

			List<Tuple> dmalmProjects = query.from(project)
					.where(project.dtFineValidita.eq(DateUtils.getDtFineValidita9999()))
					.where(project.annullato.isNull()).orderBy(project.idProject.asc(), project.cRev.desc())
					.list(project.dmalmProjectPrimaryKey, project.pathProject, project.idProject, project.cRev,
							project.cCreated, project.idRepository);

			for (Tuple prj : dmalmProjects) {

				List<DmalmUserRolesSgr> userRolesGroupedByProjID = new ArrayList<DmalmUserRolesSgr>();
				userRolesGroupedByProjID = UserRolesSgrDAO.getUserRolesForProject(prj.get(project.idRepository),
						prj.get(project.idProject));

				List<DmalmUserRolesSgr> listaNuovi = new ArrayList<DmalmUserRolesSgr>();

				for (DmalmUserRolesSgr projectUserRole : userRolesGroupedByProjID) {
					int fkProject = prj.get(project.dmalmProjectPrimaryKey);

					UserRolesSgrDAO.insertUserRole(projectUserRole, fkProject, dataEsecuzione);
					righeNuove += 1;

				}

				// nuovi UserRoles
				for (DmalmUserRolesSgr projectUserRole : listaNuovi) {
					int fkProject = prj.get(project.dmalmProjectPrimaryKey);
					UserRolesSgrDAO.insertUserRole(projectUserRole, fkProject, dataEsecuzione);
					righeNuove += 1;
				}

				logger.info("fillCurrentUserRoles - Fine gestione progetto " + prj.get(project.idProject) + " - "
						+ prj.get(project.idRepository));
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
				EsitiCaricamentoDAO.insert(dataEsecuzione, DmAlmConstants.TARGET_USERROLES, stato,
						new Timestamp(dtInizioCaricamento.getTime()), new Timestamp(dtFineCaricamento.getTime()),
						righeNuove, righeModificate, 0, 0);
				logger.info("STOP EsitiCaricamentoDAO.insert");

			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	private static boolean presenteSuLista(DmalmUserRolesSgr utenteRuolo, List<DmalmUserRolesSgr> lista) {
		boolean result = false;

		for (DmalmUserRolesSgr userRole : lista) {
			if (userRole.getUserid().equalsIgnoreCase(utenteRuolo.getUserid())
					&& userRole.getRuolo().equalsIgnoreCase(utenteRuolo.getRuolo())) {
				result = true;
				break;
			}
		}

		return result;
	}

}