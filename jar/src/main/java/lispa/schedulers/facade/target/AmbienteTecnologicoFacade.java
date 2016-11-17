package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmAmbienteTecnologico;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.AmbienteTecnologicoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmAmbienteTecnologico;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class AmbienteTecnologicoFacade {
	
	private static Logger logger = Logger.getLogger(AmbienteTecnologicoFacade.class);

	public static void execute (Timestamp dataEsecuzione) {	

		List<DmalmAmbienteTecnologico>  	staging = new ArrayList<DmalmAmbienteTecnologico>();
		List<Tuple> 		  				 target = new ArrayList<Tuple>();
		QDmalmAmbienteTecnologico amb 				= QDmalmAmbienteTecnologico.dmalmAmbienteTecnologico;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		DmalmAmbienteTecnologico ambiente_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging  = AmbienteTecnologicoDAO.getAllambienteTecnologico(dataEsecuzione);
			
			for(DmalmAmbienteTecnologico ambiente : staging)
			{   
				ambiente_tmp = ambiente;
				target = AmbienteTecnologicoDAO.getAmbienteTecnologico(ambiente);

				if(target.size()==0)
				{				
					righeNuove++;					
					AmbienteTecnologicoDAO.insertAmbienteTecnologico(ambiente, dataEsecuzione);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target)
					{
						
						if(row !=null)
						{
							
							if(BeanUtils.areDifferent(row.get(amb.nome), ambiente.getNome()))
							{
								modificato = true;
							}
							
							if(BeanUtils.areDifferent(row.get(amb.annullato), ambiente.getAnnullato()))
							{
								modificato = true;
							}
							
							if(BeanUtils.areDifferent(row.get(amb.dtAnnullamento), ambiente.getDtAnnullamento()))
							{
								modificato = true;
							}
							
							if(modificato)
							{
								righeModificate++;
								
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								AmbienteTecnologicoDAO.updateDataFineValidita(dataEsecuzione, ambiente);

								// inserisco un nuovo record
								AmbienteTecnologicoDAO.insertAmbienteTecnologicoUpdate(dataEsecuzione, ambiente);							
							}
							else
							{
								// Aggiorno lo stesso
								AmbienteTecnologicoDAO.updateAmbienteTecnologico(ambiente);
							}
						}
					}
				}
			}
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(ambiente_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(ambiente_tmp));
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
							DmAlmConstants.TARGET_ORESTE_AMBIENTETECNOLOGICO, 
							righeNuove, 
							righeModificate
				);
				
			} catch (DAOException | SQLException e) {

				logger.error(e.getMessage(), e);
				
			}
		}

	}
}