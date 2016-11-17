package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmAreaTematica;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.AreaTematicaSgrCmDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.LogUtils;

public class AreaTematicaSgrCmFacade {
	
	private static Logger logger = Logger.getLogger(AreaTematicaSgrCmFacade.class);

	public static void execute (Timestamp dataEsecuzione) throws Exception, DAOException {	

		List<DmalmAreaTematica> staging_areatematica = new ArrayList<DmalmAreaTematica>();
		List<Tuple> target_areaTematica = new ArrayList<Tuple>();
		
		int righeNuove = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		DmalmAreaTematica area_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;
		
		try
		{
			staging_areatematica  = AreaTematicaSgrCmDAO.getAllAreaTematica(dataEsecuzione);
			
			for(DmalmAreaTematica area : staging_areatematica)
			{   
				area_tmp = area;
				// Ricerco nel db target un record con cdAreatematica = area.getCdAreaTematica()
				target_areaTematica = AreaTematicaSgrCmDAO.getAreaTematica(area);
	
				// se non trovo almento un record, inserisco l'area tematica nel target
				if(target_areaTematica.size()==0)
				{
					righeNuove++;
					
					AreaTematicaSgrCmDAO.insertAreaTematica(area);
				}
			}
		}
		catch (DAOException e) 
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally
		{
			dtFineCaricamento = new Date();

			try {
				
				EsitiCaricamentoDAO.insert
				(
							dataEsecuzione,
							DmAlmConstants.TARGET_AREATEMATICA, 
							stato, 
							new Timestamp(dtInizioCaricamento.getTime()), 
							new Timestamp(dtFineCaricamento.getTime()), 
							righeNuove, 
							0, 
							0, 
							0
				);	
			} catch (DAOException | SQLException e) {
				logger.error(LogUtils.objectToString(area_tmp));
				logger.error(e.getMessage(), e);
				
			}
			
		}
	}
}