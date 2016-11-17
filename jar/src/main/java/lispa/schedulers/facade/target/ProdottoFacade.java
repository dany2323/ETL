package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;

import lispa.schedulers.bean.target.DmalmProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.target.ProdottoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmProdotto;
import lispa.schedulers.utils.BeanUtils;
import lispa.schedulers.utils.LogUtils;

public class ProdottoFacade {
	
	private static Logger logger = Logger.getLogger(ProdottoFacade.class);

	/**
	 * Il metodo implementa le fasi di popolamento dello staging per la tabella  DMALM_PRODOTTO
	 * 
	 * @param dataEsecuzione
	 * @throws Exception
	 * @throws DAOException
	 */
	
	/*
	 * Il metodo prende in input la data di elaborazione, a partire da questa data estraiamo i dati dalle relative tabelle di staging
	 * La query eseguita è stata scritta nel file PRODOTTO.SQL presente nel modulo properties
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
	public static void execute (Timestamp dataEsecuzione)throws DAOException, Exception {	

		List<DmalmProdotto>  staging_prodotti = new ArrayList<DmalmProdotto>();
		List<Tuple> 		  target_prodotti = new ArrayList<Tuple>();
		QDmalmProdotto prod 				  = QDmalmProdotto.dmalmProdotto;
		
		int righeNuove = 0;
		int righeModificate = 0;
		
		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento 	 = null;
		
		DmalmProdotto prodotto_tmp = null;
		
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;

		try
		{
			staging_prodotti  = ProdottoDAO.getAllProdotti(dataEsecuzione);
			
			for(DmalmProdotto prodotto : staging_prodotti)
			{   
				prodotto_tmp = prodotto;
				target_prodotti = ProdottoDAO.getProdotto(prodotto);
				
				if(target_prodotti.size()==0)
				{				
					righeNuove++;
					ProdottoDAO.insertProdotto(prodotto, dataEsecuzione);
				}
				else
				{
					boolean modificato = false;
					

					for(Tuple row : target_prodotti)
					{
					
						if(row !=null)
						{
							if(BeanUtils.areDifferent(row.get(prod.sigla), prodotto.getSigla()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(prod.nome), prodotto.getNome()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(prod.dmalmUnitaOrganizzativaFk01), prodotto.getDmalm_unitaorganizzativa_fk01()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(prod.dmalmPersonaleFk02), prodotto.getDmalm_personale_fk02()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(prod.sigla), prodotto.getSigla()))
							{
								modificato = true;
							}
							if(BeanUtils.areDifferent(row.get(prod.annullato), prodotto.getAnnullato()))
							{
								modificato = true;
							}

							if(modificato)
							{
								righeModificate++;
								
								// STORICIZZO
								// aggiorno la data di fine validita sul record corrente
								ProdottoDAO.updateDataFineValidita(dataEsecuzione, prodotto);

								// inserisco un nuovo record
								ProdottoDAO.insertProdottoUpdate(dataEsecuzione, prodotto);							
							}
							else
							{
								// Aggiorno lo stesso
								ProdottoDAO.updateProdotto(prodotto);
							}
						}
					}
				}
			}
		}
		catch (DAOException e) 
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(prodotto_tmp));
			logger.error(e.getMessage(), e);
			
			stato = DmAlmConstants.CARICAMENTO_TERMINATO_CON_ERRORE;
		}
		catch(Exception e)
		{
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(LogUtils.objectToString(prodotto_tmp));
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
							DmAlmConstants.TARGET_ORESTE_PRODOTTO, 
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