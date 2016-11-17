package lispa.schedulers.action;

import java.sql.Timestamp;

import lispa.schedulers.facade.cleaning.CheckElettraFacade;
import lispa.schedulers.facade.cleaning.CheckSferaMisureFacade;
import lispa.schedulers.facade.target.ErroriCaricamentoFacade;
import lispa.schedulers.facade.target.EsitiCaricamentoFacade;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.runnable.cleaning.CleaningRunnable;

import org.apache.log4j.Logger;

/**
 * - I dati subiscono quindi un processo di trasformazione, 
 * per fare in modo che siano più aderenti coerenti alla logica di business del sistema di analisi
 *  per cui viene sviluppato. Ha anche lo scopo di identificare scarti ed errori, facendo distinzione 
 *  tra errori non bloccanti, ovvero dati che nonostante non siano formalmente corretti possono comunque 
 *  essere analizzati, ed errori bloccanti, cioè dati che vanno scartati e corretti manualmente.
 * @author fdeangel
 *
 */
public class DmAlmCleaning {

	private static Logger logger = Logger.getLogger(DmAlmCleaning.class); 

	/**
	 * In questa fase i dati delle fonti vengono sottoposti a controlli di tipo formale, logico e 
	 * di integrità referenziale e modificati in base a regole automatizzabili. L’esito di tali controlli 
	 * può essere positivo o negativo. I controlli vengono effettuati a livello di attributo, ovvero essi 
	 * sono specifici per ogni colonna delle tabelle fonte, e servono a rendere più corretta ed utilizzabile 
	 * l’informazione che verrà poi messa a disposizione nel DB target.
	 * A seconda dell’esito dei controlli si hanno 3 scenari ed altrettanti comportamenti diversi:
	 * •	controllo soddisfatto:
	 * il dato viene inserito nel DB target con esito positivo del caricamento
	 * •	controllo fallito non bloccante:
	 * il dato viene inserito nel DB target con esito negativo e viene aggiornata la tabella degli errori
	 *  di caricamento
	 *  •	controllo fallito bloccante :
	 *  il dato non viene inserito nel DB target in quanto necessita di una correzione manuale 
	 *  e viene aggiornata la tabella degli errori di caricamento.
	 *  I record nelle tabelle di EDMA_LISPA non sono sottoposti ad alcun controllo, 
	 *  in quanto si ipotizza che siano corretti alla fonte.
	 *  Tutte le tabelle di ORESTE vengono sottoposte a controlli di tipo formale e logico.
	 *   Inoltre vengono sottoposti 
	 *   a controlli e trasformazioni anche le tabelle PROJECT_SGR e CF_WORKITEM_SGR, e sono volti soprattutto  
	 *    a verificare la coerenza delle informazioni contenute in SGR_CM con i dati presenti in EDMA e Oreste.
	 *    Il fallimento di tali controlli viene tracciato nella tabella DMALM_ERRORI_CARICAMENTO. 
	 *    Nel dettaglio, tutte le logiche di trasformazione ed i controlli da effettuare sono indicati 
	 *    nel paragrafo 3.7 4.3 Regole di controllo Dati sorgenti del documento di analisi funzionale.
	 */
	protected static void doWork() {

		try {
	
			Timestamp dataEsecuzione = DataEsecuzione.getInstance().getDataEsecuzione();
			
			// CANCELLO I DATI DI OGGI 
			ErroriCaricamentoFacade.delete(logger, dataEsecuzione);
	
			EsitiCaricamentoFacade.delete(logger, dataEsecuzione);
	
			// CONTROLLI ELETTRA/SGRCM
			if (ExecutionManager
					.getInstance().isExecutionElettraSgrcm()) {
				
				//Edma/Oreste/SgrCm
				Thread cleaning = new Thread(new CleaningRunnable(logger,dataEsecuzione));
				cleaning.start();
				cleaning.join();
				
				//Elettra
				CheckElettraFacade.execute(logger, dataEsecuzione);
			}

			// CLEANING DI SFERA
			if (ExecutionManager
					.getInstance().isExecutionSfera()) {
				
				CheckSferaMisureFacade.execute(logger, dataEsecuzione);
			}
		}
		catch(Exception e)
		{
			logger.error(e.getMessage(), e);
		}
	}
}
