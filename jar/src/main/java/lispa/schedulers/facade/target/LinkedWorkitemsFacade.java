package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.bean.target.DmalmLinkedWorkitems;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.LinkedWorkitemsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

public class LinkedWorkitemsFacade {
	
	private static Logger logger = Logger.getLogger(LinkedWorkitemsFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		int righeNuove = 0;
		
		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmLinkedWorkitems linkedWorkitems_tmp = null;
		
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;
		
		try {
			//La query restituisce solo i link da inserire nel target
			
//			staging_linkedWorkitems = LinkedWorkitemsDAO.getAllLinkedWorkitems(dataEsecuzione);
			
//			for(DmalmLinkedWorkitems linkedWorkitems : staging_linkedWorkitems) {
//				
//				linkedWorkitems_tmp = linkedWorkitems;
//				
//				//se le primary key (della fonte) relative ai due workitem linkati compaiono all'interno delle relative tabelle Fatto,
//				//inserisco il link tra di essi
//					
//					if(LinkedWorkitemsDAO.insertLink(linkedWorkitems))
//						righeNuove++;
//					
//				
//				
//			}
//			righeNuove = staging_linkedWorkitems.size();
//			LinkedWorkitemsDAO.insertLinkList(staging_linkedWorkitems);
			
			righeNuove = LinkedWorkitemsDAO.insertLinkedWorkitems(dataEsecuzione);
					
			
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(linkedWorkitems_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(linkedWorkitems_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally
		{
			dtFineCaricamento = new Date();
	
			try {
				
				EsitiCaricamentoDAO.insert
				(
							dataEsecuzione,
							DmAlmConstants.TARGET_LINKEDWORKITEMS, 
							stato, 
							new Timestamp(dtInizioCaricamento.getTime()), 
							new Timestamp(dtFineCaricamento.getTime()), 
							righeNuove, 
							0, 
							0, 
							0
				);	
			} catch (DAOException | SQLException e) {
	
				logger.error(e.getMessage(), e);
				e.printStackTrace();
				
			}
		}
	}
	
}
