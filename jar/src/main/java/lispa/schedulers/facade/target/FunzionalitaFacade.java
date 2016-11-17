package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmFunzionalita;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.FunzionalitaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmFunzionalita;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class FunzionalitaFacade {
	
	private static Logger logger = Logger.getLogger(FunzionalitaFacade.class);

	public static void execute ( Timestamp dataEsecuzione) throws DAOException, Exception {	

		List<DmalmFunzionalita>  staging = new ArrayList<DmalmFunzionalita>();
		List<Tuple> 		  target = new ArrayList<Tuple>();
		QDmalmFunzionalita funz 				  = QDmalmFunzionalita.dmalmFunzionalita;
		
		int righeNuove = 0;
		int righeModificate = 0;

		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		DmalmFunzionalita funzionalita_tmp = null;

		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging  = FunzionalitaDAO.getAllFunzionalita(dataEsecuzione);
			
			for(DmalmFunzionalita funzionalita : staging)
			{   
				funzionalita_tmp = funzionalita;
				target = FunzionalitaDAO.getFunzionalita(funzionalita);

				if(target.size()==0)
				{				
					righeNuove++;
					FunzionalitaDAO.insertFunzionalita(funzionalita, dataEsecuzione);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target)
					{
						if(row !=null)
						{

							if(BeanUtils.areDifferent(funzionalita.getSiglaFunzionalita(), row.get(funz.siglaFunzionalita)))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(funz.nome), funzionalita.getNome()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(funz.dtAnnullamento), funzionalita.getDtAnnullamento()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(funz.annullato), funzionalita.getAnnullato()))
							{
								modificato = true;
							}

							if(modificato)
							{
								righeModificate++;
																
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								FunzionalitaDAO.updateDataFineValidita(dataEsecuzione, funzionalita);

								// inserisco un nuovo record
								FunzionalitaDAO.insertFunzionalitaUpdate(dataEsecuzione, funzionalita);		
								}
							else
							{
								// Aggiorno lo stesso
								FunzionalitaDAO.updateFunzionalita(funzionalita);
							}
						}
					}
				}
			}
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(funzionalita_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(funzionalita_tmp));
			logger.error(e.getMessage(), e);
			
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		finally
		{
			dtFineCaricamento = new Date();

			try 
			{
				
				EsitiCaricamentoDAO.updateETLTargetInfo
				(
					dataEsecuzione, 
					stato, 
					new Timestamp(dtInizioCaricamento.getTime()), 
					new Timestamp(dtFineCaricamento.getTime()), 
					DmAlmConstants.TARGET_ORESTE_FUNZIONALITA, 
					righeNuove, 
					righeModificate
				);
				
			} 
			catch (DAOException | SQLException e)
			{

				logger.error(e.getMessage(), e);
				
			}
		}
	}
}