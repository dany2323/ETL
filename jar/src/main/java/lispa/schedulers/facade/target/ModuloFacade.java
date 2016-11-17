package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmModulo;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ModuloDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmModulo;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class ModuloFacade {
	
	private static Logger logger = Logger.getLogger(ModuloFacade.class);

	/**
	 * Il metodo implementa le fasi di popolamento dello staging per la tabella  DMALM_MODULO
	 * 
	 * @param dataEsecuzione
	 * @throws Exception
	 * @throws DAOException
	 */
	
	/*
	 * Il metodo prende in input la data di elaborazione, a partire da questa data estraiamo i dati dalle relative tabelle di staging
	 * La query eseguita è stata scritta nel file MODULI.SQL presente nel modulo properties
	 * Nella prima parte del metodo execute vengono estratti tutti i record per la data di elaborazione dalla/e tabella/e di staging.
	 * Successivamente, per ogni riga:
	 * -Se non è già presente nella tabella target la inseriamo
	 * -Se è già presente ma le colonne SCD non sono state modificate, ci limitiamo ad aggiornare i campi non SCD
	 * -Se è già presente ma almeno una delle colonne SCD sono state modificate, storicizziamo ovvero valorizziamo la data di inizio validitàà del "vecchio"
	 * record alla data attuale meno un giorno, ed inseriamo il "nuovo" record con data inizio validità uguale alla data di elaborazione e la data di fine
	 * validità uguale al 31-12-9999 00:00:00
	 * 
	 * Nel finally è stata implementata la logica per l'aggiornamento oppure l'inserimento dell'esito per l'entità in questione nella 
	 * tabella DMALM_ESITI_CARICAMENTI
	 */
	
	public static void execute ( Timestamp dataEsecuzione) {	

		List<DmalmModulo>  staging = new ArrayList<DmalmModulo>();
		List<Tuple> 		  target = new ArrayList<Tuple>();
		QDmalmModulo mod 				  = QDmalmModulo.dmalmModulo;
		
		int righeNuove = 0;
		int righeModificate = 0;
		
		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmModulo modulo_tmp = null;
		
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging  = ModuloDAO.getAllModuli(dataEsecuzione);
			
			for(DmalmModulo modulo : staging)
			{   
				
				modulo_tmp = modulo;
				target = ModuloDAO.getModulo(modulo);

				if(target.size()==0)
				{				
					righeNuove++;
					ModuloDAO.insertModulo(modulo, dataEsecuzione);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target)
					{
						
						if(row !=null)
						{							
														
							if(BeanUtils.areDifferent(row.get(mod.siglaModulo), modulo.getSiglaModulo()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(mod.nome), modulo.getNome()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(mod.dmalmPersonaleFk01), modulo.getDmalmPersonaleFk01()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(mod.annullato), modulo.getAnnullato()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(mod.dtAnnullamento), modulo.getDtAnnullamento()))
							{
								modificato = true;
							}
							

							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								ModuloDAO.updateDataFineValidita(dataEsecuzione, modulo);

								// inserisco un nuovo record
								ModuloDAO.insertModuloUpdate(dataEsecuzione, modulo);							}
							else
							{
								// Aggiorno lo stesso
								ModuloDAO.updateModulo(modulo);
							}
						}
					}
				}
			}
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(modulo_tmp));
			logger.error(e.getMessage(), e);
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(modulo_tmp));
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
							DmAlmConstants.TARGET_ORESTE_MODULO, 
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