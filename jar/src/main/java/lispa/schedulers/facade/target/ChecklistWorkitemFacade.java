package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.log4j.Logger;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ChecklistWorkitemDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;

public class ChecklistWorkitemFacade {
	
	private static Logger logger = Logger.getLogger(ChecklistWorkitemFacade.class);

	public static void execute (Timestamp dataEsecuzione) {	

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try	{
			ChecklistWorkitemDAO.createChecklistWiTrsl();
			ChecklistWorkitemDAO.insertChecklistWiTrsl(dataEsecuzione);
		}
		catch (DAOException e) {
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
				
				EsitiCaricamentoDAO.updateETLTargetInfo	(
					dataEsecuzione, 
					stato, 
					new Timestamp(dtInizioCaricamento.getTime()), 
					new Timestamp(dtFineCaricamento.getTime()), 
					DmAlmConstants.TARGET_CHECKLIST_WORKITEM_TRSL, 
					0, 
					0
				);
			} catch (DAOException | SQLException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}