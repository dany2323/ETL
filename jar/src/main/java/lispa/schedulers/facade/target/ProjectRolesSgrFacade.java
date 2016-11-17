package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmProjectRolesSgr;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ProjectRolesSgrDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;

public class ProjectRolesSgrFacade {

	private static Logger logger = Logger
			.getLogger(ProjectRolesSgrFacade.class);

	public static void execute(Timestamp dataEsecuzione) {

		List<DmalmProjectRolesSgr> staging = new ArrayList<DmalmProjectRolesSgr>();
		List<Tuple> target = new ArrayList<Tuple>();

		int righeNuove = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try {
			staging = ProjectRolesSgrDAO.getAllProjectRoles(dataEsecuzione);

			for (DmalmProjectRolesSgr projectroles : staging) {

				target = ProjectRolesSgrDAO
						.getDmalmProjectRolesSgr(projectroles);

				// se non trovo almento un record, inserisco lo stato nel target

				if (target.size() == 0) {
					righeNuove++;
					ProjectRolesSgrDAO.insertProjectRolesSgr(projectroles);
				} else {
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

				EsitiCaricamentoDAO.insert(dataEsecuzione,
						DmAlmConstants.TARGET_PROJECTROLES, stato,
						new Timestamp(dtInizioCaricamento.getTime()),
						new Timestamp(dtFineCaricamento.getTime()), righeNuove,
						0, 0, 0);
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);

			}

		}
	}
}