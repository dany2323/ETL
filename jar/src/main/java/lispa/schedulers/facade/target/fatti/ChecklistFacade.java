package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lispa.schedulers.bean.target.fatti.DmalmChecklist;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ChecklistOdsDAO;
import lispa.schedulers.dao.target.fatti.ChecklistDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import org.apache.log4j.Logger;

public class ChecklistFacade {

private static Logger logger = Logger.getLogger(ChecklistFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		List<DmalmChecklist> staging_checklist = new ArrayList<DmalmChecklist>();
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try	{
			staging_checklist  = ChecklistDAO.getAllChecklist(dataEsecuzione);
			ChecklistOdsDAO.delete();
			logger.debug("START -> Popolamento Checklist ODS, "+staging_checklist.size()+ " checklist");
			ChecklistOdsDAO.insert(staging_checklist, dataEsecuzione);
			List<DmalmChecklist> checklists = ChecklistOdsDAO.getAll();
			logger.debug("STOP -> Checklist ODS, "+staging_checklist.size()+ " checklist");
			ChecklistDAO.checkAlteredRowChecklist(checklists, dataEsecuzione);
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} catch(Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		} finally {
			dtFineCaricamento = new Date();
			try {
				EsitiCaricamentoDAO.insert (
							dataEsecuzione,
							DmAlmConstants.TARGET_CHECKLIST, 
							stato, 
							new Timestamp(dtInizioCaricamento.getTime()), 
							new Timestamp(dtFineCaricamento.getTime()), 
							righeNuove, 
							righeModificate, 
							0, 
							0
				);	
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}