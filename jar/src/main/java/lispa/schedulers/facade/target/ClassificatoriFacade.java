package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmClassificatori;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ClassificatoreDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmClassificatori;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class ClassificatoriFacade {
	
	private static Logger logger = Logger.getLogger(ClassificatoriFacade.class);

	public static void execute ( Timestamp dataEsecuzione) throws Exception, DAOException {	

		List<DmalmClassificatori>  staging_classificatori = new ArrayList<DmalmClassificatori>();
		List<Tuple> 				target_classificatori = new ArrayList<Tuple>();
		QDmalmClassificatori clasf 						  = QDmalmClassificatori.dmalmClassificatori;
		
		int righeNuove = 0;
		int righeModificate = 0;
		
		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		DmalmClassificatori classificatore_tmp = null;
		
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_classificatori  = ClassificatoreDAO.getAllClassificatori(dataEsecuzione);
			
			for(DmalmClassificatori classificatore : staging_classificatori)
			{   
				classificatore_tmp = classificatore;
				
				target_classificatori = ClassificatoreDAO.getClassificatore(classificatore);

				if(target_classificatori.size()==0)
				{
					righeNuove++;					
					ClassificatoreDAO.insertClassificatore(classificatore, dataEsecuzione);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target_classificatori)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(clasf.codiceClassificatore), classificatore.getCodiceClassificatore()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(clasf.tipoClassificatore), classificatore.getTipoClassificatore()))
							{
								modificato = true;
							}
							
							if(modificato)
							{
								righeModificate++;								
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								ClassificatoreDAO.updateDataFineValidita(dataEsecuzione, classificatore);

								// inserisco un nuovo record
								ClassificatoreDAO.insertClassificatoreUpdate(dataEsecuzione, classificatore);							
							}
							else
							{
								// Aggiorno lo stesso
								ClassificatoreDAO.updateClassificatore(classificatore);
							}
						}
					}
				}
			}
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(classificatore_tmp));
			logger.error(e.getMessage(), e);
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(classificatore_tmp));
			logger.error(e.getMessage(), e);
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally
		{
			dtFineCaricamento = new Date();
			
			try {
				EsitiCaricamentoDAO.updateETLTargetInfo
				(
							dataEsecuzione, 
							stato, 
							new Timestamp(dtInizioCaricamento.getTime()), 
							new Timestamp(dtFineCaricamento.getTime()), 
							DmAlmConstants.TARGET_ORESTE_CLASSIFICATORI, 
							righeNuove, 
							righeModificate
				);
				
			} catch (DAOException | SQLException e)
			{
				logger.error(e.getMessage(), e);
				
			}
		}

	}
}