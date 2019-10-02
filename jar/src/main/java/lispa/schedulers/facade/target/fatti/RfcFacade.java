package lispa.schedulers.facade.target.fatti;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

import lispa.schedulers.bean.target.fatti.DmalmRfc;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.RfcOdsDAO;
import lispa.schedulers.dao.target.fatti.RfcDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
//import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class RfcFacade {

private static Logger logger = Logger.getLogger(RfcFacade.class);
	
	public static void execute (Timestamp dataEsecuzione) throws Exception , DAOException {	
		

		List<DmalmRfc> staging_rfc = new ArrayList<DmalmRfc>();
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmRfc richieste_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try	{
			staging_rfc  = RfcDAO.getAllRfc(dataEsecuzione);
			
			RfcOdsDAO.delete();
			
			logger.debug("START -> Popolamento RFC ODS, "+staging_rfc.size()+ " rfc");
			
			RfcOdsDAO.insert(staging_rfc, dataEsecuzione);
			
			List<DmalmRfc> rfc = RfcOdsDAO.getAll();
			
			logger.debug("STOP -> RFC ODS, "+staging_rfc.size()+ " rfc");
			
			RfcDAO.checkAlteredRfc(rfc, dataEsecuzione);
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(richieste_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(richieste_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally {
			dtFineCaricamento = new Date();

			try {
				
				EsitiCaricamentoDAO.insert (
							dataEsecuzione,
							DmAlmConstants.TARGET_RFC, 
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