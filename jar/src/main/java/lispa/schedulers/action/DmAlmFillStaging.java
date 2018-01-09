package lispa.schedulers.action;

import static lispa.schedulers.constant.DmAlmConstants.DEFAULT_DAY_DELETE;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_STAGING_DAY_DELETE;

import java.sql.Timestamp;

import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.elettra.StagingElettraFacade;
import lispa.schedulers.facade.mps.staging.StgMpsFacade;
import lispa.schedulers.facade.sfera.staging.StgMisuraFacade;
import lispa.schedulers.facade.target.FillCurrentWorkitems;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.runnable.staging.EdmaRunnable;
import lispa.schedulers.runnable.staging.OresteRunnable;
import lispa.schedulers.runnable.staging.SGRCMSireRunnable;
import lispa.schedulers.runnable.staging.SGRCMSissRunnable;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

/**
 * - Acquisizione dati dalle fonti - I dati vengono estratti da sistemi sorgenti
 * quali database transazionali o comuni file di testo e vengono scritti nella
 * staging area. Tale copia ha lo scopo di consolidare i dati (cioè rendere
 * omogenei dati provenienti da tipologie di sorgenti diverse).
 * 
 * @author fdeangel
 * 
 */
public class DmAlmFillStaging {

	final private static Logger logger = Logger
			.getLogger(DmAlmFillStaging.class);

	private static int days;

	public static void doWork() {

		Thread edma, oreste, siss, sire;

		/**
		 * Come prima cosa si provvede alla cancellazione dei dati nell’area di
		 * staging con una data di inserimento uguale a quella attuale o
		 * precedente di un numero determinato (impostabile tramite file di
		 * properties) di giorni la data attuale. Questa scelta è stata presa
		 * prima di tutto per garantire la consistenza dei dati e la gestione
		 * della transazionalità: infatti nel caso di una doppia esecuzione del
		 * batch nello stesso giorno, l’inserimento nell’area di staging di dati
		 * con la stessa DATA_DI_CARICAMENTO provocherebbe una violazione di
		 * chiave. Inoltre in questo modo si offre la possibilità di cancellare
		 * dati nell’area di staging troppo vecchi od obsoleti, evitando così un
		 * sovradimensionamento di tale area (che potrebbe diventare una
		 * criticità per le performance), trattenendo però al contempo i dati
		 * relativi agli ultimi caricamenti in modo da poter effettuare
		 * operazioni di troubleshooting nel caso del verificarsi di errori.
		 * 
		 */
		try {
			days = Integer.parseInt(DmAlmConfigReader.getInstance()
					.getProperty(DMALM_STAGING_DAY_DELETE));
		} catch (PropertiesReaderException | NumberFormatException e) {
			days = DEFAULT_DAY_DELETE;
			logger.debug(e.getMessage(), e);
		}

		try {

			// Cancello i record piu vecchi di X giorni
			final Timestamp dataEsecuzioneDeleted = DateUtils
					.getAddDayToDate(-days);

			// ELETTRA/SGRCM
			if (ExecutionManager.getInstance().isExecutionElettraSgrcm()) {
				/**
				 * Per quanto riguarda EDMA, le tabelle fonte sono
				 * UNITA_ORGANIZZATIVE e PERSONALE. Tutti gli attributi di tutti
				 * i record vengono copiati nelle relative tabelle nell’area di
				 * staging senza la necessità di applicare alcuna logica
				 * applicativa.
				 */
				// Commento per DM_ALM-228
				/*
				edma = new Thread(new EdmaRunnable(logger,
						dataEsecuzioneDeleted));
				*/
				
				// ORESTE
				/**
				 * La stessa cosa avviene per le tabelle fonte in ORESTE, ovvero
				 * PRODOTTI_ARCHITETTURE, MODULI, FUNZIONALITA, SOTTOSISTEMI,
				 * CLASSIFICATORI e AMBIENTE_TECNOLOGICO. Le tabelle di EDMA e
				 * ORESTE sono alimentate dalle viste fornite da LISPA. Le viste
				 * riportano in ogni momento la situazione corrente e quindi il
				 * popolamento dell’area di staging per queste due fonti
				 * consiste sempre nella copia della situazione corrente.
				 */
				// Commento per DM_ALM-228
				/*
				oreste = new Thread(new OresteRunnable(logger,
						dataEsecuzioneDeleted));
				*/
				/**
				 * Per quanto riguarda invece le fonti SGR_CM, le fonti sono sia
				 * di tipo tabellare che di tipo XML. La logica di popolamento
				 * delle tabelle di staging prevede una copia dei dati in
				 * “append”, ovvero si provvede per ogni esecuzione ad
				 * identificare il numero di revisione più alto e ad estrarre
				 * dalla fonte solo gli oggetti Polarion (User, Project,
				 * Workitem..) che hanno un numero di revisione maggiore. Questa
				 * strategia permette di mantenere nell’area di staging la
				 * completa storia di Polarion, consentendo così di risalire
				 * alle storicizzazioni di ogni oggetto. Per le tabelle che
				 * compongono questa parte di area di staging, non viene
				 * effettuata l’operazione di cancellazione di dati più vecchi
				 * di un certo numero di giorni
				 */

				// SIRE
				sire = new Thread(new SGRCMSireRunnable(logger,
						dataEsecuzioneDeleted));

				// SISS
				siss = new Thread(new SGRCMSissRunnable(logger,
						dataEsecuzioneDeleted));

				//edma.start();
				//edma.join();

				if (lispa.schedulers.manager.ErrorManager.getInstance()
						.hasError()) {
					logger.fatal("ERRORE: Inizio procedura di ripristino");
					RecoverManager.getInstance().startRecoverStaging();
					return;
				}

				//oreste.start();
				//oreste.join();

				if (lispa.schedulers.manager.ErrorManager.getInstance()
						.hasError()) {
					logger.fatal("ERRORE: Inizio procedura di ripristino");
					RecoverManager.getInstance().startRecoverStaging();
					return;
				}

				// Gestione Elettra
				StagingElettraFacade.executeStaging(dataEsecuzioneDeleted);
				if (lispa.schedulers.manager.ErrorManager.getInstance()
						.hasError()) {
					logger.fatal("ERRORE: Inizio procedura di ripristino");
					RecoverManager.getInstance().startRecoverStaging();
					return;
				}

				// SGR_CM parte dolo dopo il completamento di EDMA e ORESTE
				siss.start();
				siss.join();

				if (lispa.schedulers.manager.ErrorManager.getInstance()
						.hasError()) {
					logger.fatal("ERRORE: Inizio procedura di ripristino");
					RecoverManager.getInstance().startRecoverStaging();

					return;
				}

				sire.start();
				sire.join();

				if (lispa.schedulers.manager.ErrorManager.getInstance()
						.hasError()) {
					logger.fatal("ERRORE: Inizio procedura di ripristino");
					RecoverManager.getInstance().startRecoverStaging();

					return;
				}

				FillCurrentWorkitems.delete(logger);
				FillCurrentWorkitems.execute(logger);

				if (lispa.schedulers.manager.ErrorManager.getInstance()
						.hasError()) {
					logger.fatal("ERRORE: Inizio procedura di ripristino");
					RecoverManager.getInstance().startRecoverStaging();
					return;
				}
			}

			if (lispa.schedulers.manager.ErrorManager.getInstance().hasError()) {
				logger.fatal("ERRORE: Inizio procedura di ripristino");
				RecoverManager.getInstance().startRecoverStaging();
				return;
			}

			// SFERA
			if (ExecutionManager.getInstance().isExecutionSfera()) {
				StgMisuraFacade.deleteStgMisura(logger, dataEsecuzioneDeleted);
				StgMisuraFacade.FillStgMisura();
			}
			
			// Aggiunto checkpoint di recovery in data 19/04/17
			if (lispa.schedulers.manager.ErrorManager.getInstance()
					.hasError()) {
				logger.fatal("ERRORE: Inizio procedura di ripristino");
				RecoverManager.getInstance().startRecoverStaging();
				return;
			}

			// MPS
			if (ExecutionManager.getInstance().isExecutionMps()) {

				if (!ErrorManager.getInstance().hasError()) {
					logger.info("START: FillStaging MPS");

					// Svuotamento tabelle Staging MPS (non c'è storicizzazione)
					StgMpsFacade.deleteStgMps();

					StgMpsFacade.fillStgMps();

					if (ErrorManager.getInstance().hasError()) {
						logger.fatal("ERRORE: Inizio procedura di ripristino MPS");

						// in caso di errore viene chiamato soltanto il recover
						// di MPS
						RecoverManager.getInstance().startRecoverStgMps();
						// reset dell'errore in modo da mandare avanti l'ETL
						// senza MPS
						ErrorManager.getInstance().resetError();
					}

					logger.info("STOP: FillStaging MPS");
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}
	}
}
