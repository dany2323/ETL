package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import lispa.schedulers.bean.target.DmalmHyperlink;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.HyperlinkDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.LogUtils;

import org.apache.log4j.Logger;

public class HyperlinkFacade {

	private static Logger logger = Logger.getLogger(HyperlinkFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	

		int righeNuove = 0;
		
		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmHyperlink hyperlink_tmp = null;
		
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;
		
		try {
//			//La query restituisce solo i link da inserire nel target
//			staging_hyperlinks = HyperlinkDAO.getAllHyperlink(dataEsecuzione);
//
//			righeNuove = staging_hyperlinks.size();
//			HyperlinkDAO.insertHyperlinkList(staging_hyperlinks);
			
			righeNuove = HyperlinkDAO.insertHyperlinks(dataEsecuzione);
					
			
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(hyperlink_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(hyperlink_tmp));
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
							DmAlmConstants.TARGET_HYPERLINK, 
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
				
			}
		}
	}
	
}
