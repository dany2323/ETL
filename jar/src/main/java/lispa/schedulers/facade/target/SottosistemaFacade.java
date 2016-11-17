package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmSottosistema;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.SottosistemaDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmSottosistema;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class SottosistemaFacade {
	
	private static Logger logger = Logger.getLogger(SottosistemaFacade.class);

	/**
	 * Il metodo implementa le fasi di popolamento dello staging per la tabella  DMALM_SOTTOSISTEMA
	 * 
	 * @param dataEsecuzione
	 * @throws Exception
	 * @throws DAOException
	 */
	
	/*
	 * Il metodo prende in input la data di elaborazione, a partire da questa data estraiamo i dati dalle relative tabelle di staging
	 * La query eseguita è stata scritta nel file SOTTOSISTEMA.SQL presente nel modulo properties
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

		List<DmalmSottosistema> staging = new ArrayList<DmalmSottosistema>();
		List<Tuple> 		  target = new ArrayList<Tuple>();

		QDmalmSottosistema ssis 				  = QDmalmSottosistema.dmalmSottosistema;
		
		int righeNuove = 0;
		int righeModificate = 0;
		
		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		DmalmSottosistema sottosistema_tmp = null;
		
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;
		
		try
		{
			staging  = SottosistemaDAO.getAllSottosistemi(dataEsecuzione);
			
			for(DmalmSottosistema sottosistema : staging)
			{   
				sottosistema_tmp = sottosistema;
				target = SottosistemaDAO.getSottosistema(sottosistema);
				
				if(target.size()==0)
				{					
					righeNuove++;
					SottosistemaDAO.insertSottosistema(sottosistema, dataEsecuzione);
				}
				else
				{
					boolean modificato = false;

					for(Tuple row : target)
					{
						
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(ssis.siglaSottosistema), sottosistema.getSiglaSottosistema()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(ssis.nome), sottosistema.getNome()))
							{
								modificato = true;
							}
							
							if(BeanUtils.areDifferent(row.get(ssis.annullato), sottosistema.getAnnullato()))
							{
								modificato = true;
							}
							
							if(BeanUtils.areDifferent(row.get(ssis.dtAnnullamento), sottosistema.getDtAnnullamento()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(ssis.dmalmProdottoFk01), sottosistema.getDmalmProdottoFk01()))
							{
								modificato = true;
							}
							

							if(modificato)
							{
								righeModificate++;
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								SottosistemaDAO.updateDataFineValidita(dataEsecuzione, sottosistema);

								// inserisco un nuovo record
								SottosistemaDAO.insertSottosistemaUpdate(dataEsecuzione, sottosistema);	
								}
							else
							{
								// Aggiorno lo stesso
								SottosistemaDAO.updateSottosistema(sottosistema);
							}
						}
					}
				}
			}
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(sottosistema_tmp));
			logger.error(e.getMessage(), e);
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(sottosistema_tmp));
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
							DmAlmConstants.TARGET_ORESTE_SOTTOSISTEMA, 
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