package lispa.schedulers.facade.target;

import java.sql.SQLException;
import java.sql.Timestamp;

import lispa.schedulers.dao.oreste.AmbienteTecnologicoDAO;
import lispa.schedulers.dao.oreste.ClassificatoriDAO;
import lispa.schedulers.dao.oreste.FunzionalitaDAO;
import lispa.schedulers.dao.oreste.ModuliDAO;
import lispa.schedulers.dao.oreste.ProdottiArchitettureDAO;
import lispa.schedulers.dao.oreste.SottosistemiDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.runnable.staging.oreste.AmbientiTecnologiciRunnable;
import lispa.schedulers.runnable.staging.oreste.ClassificatoriRunnable;
import lispa.schedulers.runnable.staging.oreste.FunzionalitaRunnable;
import lispa.schedulers.runnable.staging.oreste.ModuliRunnable;
import lispa.schedulers.runnable.staging.oreste.ProdottiArchitettureRunnable;
import lispa.schedulers.runnable.staging.oreste.SottosistemiRunnable;

import org.apache.log4j.Logger;



public class FillOresteFacade {


	/**
	 *Provvede al caricamento delle tabelle di staging
	 *-DMALM_STG_AMBIENTE_TECNOLOGICO
	 *-DMALM_STG_CLASSIFICATORI
	 *-DMALM_STG_FUNZIONALITA
	 *-DMALM_STG_MODULI
	 *-DMALM_STG_PROD_ARCHITETTURE
	 *-DMALM_STG_SOTTOSISTEMI
	 *con i dati presenti nelle relative fonti
	 *presenti alla data di elaborazione
	 *
	 * @param logger
	 */
	public static void execute (Logger logger) {	
		
		Thread prodotti, moduli, funzionalita, sottosistemi, classificatori, ambienti;
		
		try
		{
		
			prodotti = new Thread(new ProdottiArchitettureRunnable(logger));
			
			moduli = new Thread(new ModuliRunnable(logger));
			
			funzionalita = new Thread(new FunzionalitaRunnable(logger));
			
			sottosistemi = new Thread(new SottosistemiRunnable(logger));
			
			classificatori = new Thread(new ClassificatoriRunnable(logger));
			
			ambienti = new Thread(new AmbientiTecnologiciRunnable(logger));
			
			sottosistemi.start();
			classificatori.start();
			ambienti.start();
			prodotti.start();
			moduli.start();
			funzionalita.start();
			
			funzionalita.join();
			sottosistemi.join();
			classificatori.join();
			ambienti.join();
			prodotti.join();
			moduli.join();
			
		}
		catch(InterruptedException e)
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
		}

		
	}
	
	/**
	 * Provvede al cancellamento delle tabelle di staging
	 *-DMALM_STG_AMBIENTE_TECNOLOGICO
	 *-DMALM_STG_CLASSIFICATORI
	 *-DMALM_STG_FUNZIONALITA
	 *-DMALM_STG_MODULI
	 *-DMALM_STG_PROD_ARCHITETTURE
	 *-DMALM_STG_SOTTOSISTEMI
	 * di tutti i record che presentano data_caricamento = dataEsecuzione
	 * 
	 * @param logger
	 * @param dataEsecuzione
	 */
	public static void delete (Logger logger, Timestamp dataEsecuzioneDeleted) {	

		try
		{

			
			ProdottiArchitettureDAO.delete(dataEsecuzioneDeleted);
			
			ModuliDAO.delete(dataEsecuzioneDeleted);
			
			FunzionalitaDAO.delete(dataEsecuzioneDeleted);
			
			SottosistemiDAO.delete(dataEsecuzioneDeleted);
			
			ClassificatoriDAO.delete(dataEsecuzioneDeleted);
			
			AmbienteTecnologicoDAO.delete(dataEsecuzioneDeleted);

		}
		catch (DAOException e) 
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
		}
		catch(SQLException e)
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
		}
		catch(Exception e)
		{
ErrorManager.getInstance().exceptionOccurred(true, e);
			
		}

	}
	
}