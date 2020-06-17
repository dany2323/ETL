package lispa.schedulers.manager;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.calipso.StgCalipsoSchedaServizioDAO;
import lispa.schedulers.dao.edma.PersonaleDAO;
import lispa.schedulers.dao.edma.UnitaOrganizzativaDAO;
import lispa.schedulers.dao.elettra.StgElAmbienteTecnologicoClassificatoriDAO;
import lispa.schedulers.dao.elettra.StgElAmbienteTecnologicoDAO;
import lispa.schedulers.dao.elettra.StgElClassificatoriDAO;
import lispa.schedulers.dao.elettra.StgElFunzionalitaDAO;
import lispa.schedulers.dao.elettra.StgElModuliDAO;
import lispa.schedulers.dao.elettra.StgElPersonaleDAO;
import lispa.schedulers.dao.elettra.StgElProdottiDAO;
import lispa.schedulers.dao.elettra.StgElUnitaOrganizzativeDAO;
import lispa.schedulers.dao.sfera.StgMisuraDAO;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentProjectDAO;
import lispa.schedulers.dao.sgr.sire.current.SireCurrentWorkitemLinkedDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryHyperlinkDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectGroupDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryUserDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemLinkedDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemUserAssignedDAO;
import lispa.schedulers.dao.sgr.sire.history.VSireHistoryWorkitemLinkDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentProjectDAO;
import lispa.schedulers.dao.sgr.siss.current.SissCurrentWorkitemLinkedDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryHyperlinkDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectGroupDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryUserDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemLinkedDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemUserAssignedDAO;
import lispa.schedulers.dao.sgr.siss.history.VSissHistoryWorkitemLinkDAO;
import lispa.schedulers.facade.mps.staging.StgMpsFacade;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.svn.LinkedWorkItemRolesXML;
import lispa.schedulers.svn.ProjectRolesXML;
import lispa.schedulers.svn.SIRESchedeServizioXML;
import lispa.schedulers.svn.SISSSchedeServizioXML;
import lispa.schedulers.svn.StatoWorkItemXML;

import java.sql.Timestamp;

import org.apache.log4j.Logger;

public class RecoverManager {

	private static Logger logger = Logger.getLogger(RecoverManager.class);

	private static RecoverManager instance;

	private static boolean isRecovered;
	private static boolean isRecoveredStagingMps;

	public synchronized boolean isRecovered() {
		return isRecovered;
	}

	public synchronized void setRecovered(boolean isRecovered) {
		RecoverManager.isRecovered = isRecovered;
	}

	public synchronized boolean isRecoveredStagingMps() {
		return isRecoveredStagingMps;
	}

	public synchronized void setRecoveredStagingMps(
			boolean isRecoveredStagingMps) {
		RecoverManager.isRecoveredStagingMps = isRecoveredStagingMps;
	}

	private RecoverManager() {
		setRecovered(false);
		setRecoveredStagingMps(false);
	}

	public synchronized static RecoverManager getInstance() {

		if (instance == null)
			instance = new RecoverManager();
		return instance;

	}

	public synchronized void startSireRecover() {
		/**
		 * RECOVER SIRE
		 */
		try {
			// CURRENT
			SireCurrentProjectDAO.recoverSireCurrentProject();

			// HISTORY
			SireHistoryAttachmentDAO.recoverSireHistoryAttachement();
			SireHistoryCfWorkitemDAO.recoverSireHistoryCfWorkItem();
			SireHistoryHyperlinkDAO.recoverSireHistoryHyperlink();
			SireHistoryProjectDAO.recoverSireHistoryProject();
			SireHistoryProjectGroupDAO.recoverSireHistoryProjectGroup();
			SireHistoryRevisionDAO.recoverSireHistoryRevision();
			SireHistoryUserDAO.recoverSireHistoryUser();
			SireHistoryWorkitemDAO.recoverSireHistoryWorkitem();
			SireHistoryWorkitemLinkedDAO.recoverSireHistoryWorkItemLinked();
			SireHistoryWorkitemUserAssignedDAO
					.recoverSireHistoryWIUserAssigned();
			VSireHistoryWorkitemLinkDAO.recoverVSireWorkitemLink();

		} catch (Exception e) {
			logger.debug(e.getMessage(), e);

		}
	}

	public synchronized void startSissRecover() {
		/**
		 * RECOVER SISS
		 */
		try {
			// CURRENT
			SissCurrentProjectDAO.recoverSissCurrentProject();

			// HISTORY
			SissHistoryAttachmentDAO.recoverSissHistoryAttachement();
			SissHistoryCfWorkitemDAO.recoverSissHistoryCfWorkItem();
			SissHistoryHyperlinkDAO.recoverSissHistoryHyperlink();
			SissHistoryProjectDAO.recoverSissHistoryProject();
			SissHistoryProjectGroupDAO.recoverSissHistoryProjectGroup();
			SissHistoryRevisionDAO.recoverSissHistoryRevision();
			SissHistoryUserDAO.recoverSissHistoryUser();
			SissHistoryWorkitemDAO.recoverSissHistoryWorkitem();
			SissHistoryWorkitemLinkedDAO.recoverSissHistoryWorkItemLinked();
			SissHistoryWorkitemUserAssignedDAO
					.recoverSissHistoryWIUserAssigned();
			VSissHistoryWorkitemLinkDAO.recoverVSissWorkitemLink();

		} catch (Exception e) {
			logger.debug(e.getMessage(), e);

		}

	}
	
	public synchronized boolean prepareTargetForRecover(Timestamp dataEsecuzione) {

		logger.info("START PREPARE TARGET FOR RECOVER");

		boolean flag = false;
		QueryManager qm = null;

		try {

			qm = QueryManager.getInstance();

//			String separator = ";";

			// cancella tutto il contenuto delle tabelle di backup
//			qm.executeMultipleStatementsFromFile(
//					DmAlmConstants.TRUNCATE_BACKUP_TABLES, separator);

			// inserisce il contenuto delle tabelle di target nelle tabelle di
			// backup
//			qm.executeMultipleStatementsFromFile(DmAlmConstants.BACKUP_TARGET,
//					separator);
			
			// DM_ALM-325
			// cancella tutto il contenuto delle tabelle di backup e
			// inserisce il contenuto delle tabelle di target nelle tabelle di
			// backup
			String separatorTable = ":";
			String separatorLine = ";";
			flag = qm.executeMultipleStatementsFromFile(DmAlmConstants.BACKUP_TARGET_WITH_PROCEDURE,
					separatorTable, separatorLine, dataEsecuzione);
//			logger.info("LA PREPARE TARGET è DISABILITATA");
//			flag=true;
			if(!flag) {
				setRecovered(true);
				throw new Exception(DmAlmConstants.ERROR_CARICAMENTO_BACKUP);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}

		logger.info("STOP PREPARE TARGET FOR RECOVER");
		
		return flag;
	}

	// MPS
	public synchronized void prepareMpsTargetForRecover() {

		logger.info("START PREPARE TARGET MPS FOR RECOVER");

		QueryManager qm = null;

		try {

			qm = QueryManager.getInstance();

			String separator = ";";

			// cancella tutto il contenuto delle tabelle di backup
			qm.executeMultipleStatementsFromFile(
					DmAlmConstants.TRUNCATE_BACKUP_MPS_TABLES, separator);

			// inserisce il contenuto delle tabelle di target nelle tabelle di
			// backup
			qm.executeMultipleStatementsFromFile(
					DmAlmConstants.BACKUP_MPS_TARGET, separator);

			// cancella tutto il contenuto delle tabelle di target in quanto non
			// c'è storicizzazione
			qm.executeMultipleStatementsFromFile(
					DmAlmConstants.TRUNCATE_MPS_TABLES, separator);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		logger.info("STOP PREPARE TARGET MPS FOR RECOVER");

	}

	/**
	 * Per quanto riguarda le tabelle target, non si può applicare lo stesso
	 * ragionamento dell’area di staging. La procedura di ripristino quindi
	 * avviene tramite delle tabelle di backup (riconoscibili dal prefisso ‘T_’)
	 * Si procede come segue: - prima di iniziare con l’elaborazione del target,
	 * si copia tutto il contenuto delle stesse in delle tabelle di backup - si
	 * procede con l’elaborazione del target e, a seconda del suo esito, si
	 * possono verificare due situazioni: a. esito OK: l’elaborazione avviene
	 * senza errori ed il contenuto delle tabelle di backup può essere
	 * cancellato b. esito KO: si verifica un errore durante l’elaborazione per
	 * cui è necessario interrompere la stessa. Si provvede a copiare il
	 * contenuto delle tabelle di backup nelle tabelle di target per riportare
	 * lo stesso ad una situazione consistente (ovvero la situazione
	 * dell’esecuzione precedente).
	 */

	public synchronized void startRecoverTargetByProcedure() { 
		 
		logger.info("START RECOVER TARGET"); 
		 
		try { 
			 
			QueryManager.getInstance().executeStoredProcedure(); 
		} catch (Exception e) { 
			logger.error(e.getMessage(), e); 
			ErrorManager.getInstance().exceptionOccurred(true, e); 
		} 
 
		logger.info("STOP RECOVER TARGET");		 
	}
	
	/**
	 * Come previsto dalle specifiche XXXX, durante la fase di staging viene
	 * effettuato un controllo di consistenza relativo all’esito delle procedure
	 * avviate dalla fase di staging stessa. Tale controllo assicura che il
	 * riempimento dell’area di staging e tutti i controlli formali relativi
	 * vadano a buon fine senza alcuna eccezione; in caso contrario lo stato
	 * dell’area di staging deve essere riportato, privo di modifiche, allo
	 * stadio in cui si trovava prima che iniziassero le operazioni di
	 * estrazione di dati. Nell’eventualità di errore, a ripristino dello stato
	 * originario avvenuto, l’ETL si interrompe notificando esito negativo.
	 * Nello specifico i controlli di correttezza avvengono al termine del
	 * riempimento delle aree logiche che costituiscono l’area di staging,
	 * rispettivamente: EDMA, ORESTE, SGR_CM SISS, SGR_CM SIRE. Alcuni errori
	 * sono considerati bloccanti e determinano l’avvio della procedura di
	 * ripristino: gli errori compresi nel range ORA-00000 – ORA-62001, gli
	 * errori che causano Connection Timed Out e Connection Refused; in tutti
	 * gli altri casi L’ETL ignora l’errore e la fase di staging continua
	 * normalmente. L’ErrorManger è la classe predisposta alla notifica
	 * dell’errore. Se uno dei suddetti errori bloccanti viene riscontrato, una
	 * variabile globale isError è inizializzata. All’atto del controllo di
	 * consistenza un check sulla questa variabile è effettuato; nel caso in cui
	 * essa si trovi inizializzata a true, la procedura di ripristino ha inizio
	 * riportando l’area di staging allo stato in cui si trovava all’istante
	 * immediatamente prima rispetto alla partenza delle procedure di staging
	 * con data di esecuzione corrente. Tutte le tabelle dell’area di staging
	 * riportano un attributo fondamentale per la procedura di ripristino:
	 * DATA_CARICAMENTO. Tramite tale attributo all’atto dell’avvio della
	 * procedura tutte le righe (dati) inserite con DATA_CARICAMENTO uguale alla
	 * data di esecuzione corrente verranno eliminate. La data di esecuzione è
	 * una variabile globale (dataEsecuzione) relativa all’intera esecuzione
	 * dell’ETL e che rimane immutata per l’intero ciclo ETL.
	 * 
	 */

	public synchronized void startRecoverStaging() {
		try {
			logger.info("[RECOVER ALL] START RECOVER STAGING");
			ConnectionManager.getInstance().dismiss();

			// SFERA
			logger.debug("START recover Sfera");
			StgMisuraDAO.recoverStgMisura();
			
			// CALIPSO
			logger.debug("START recover Calipso");
			StgCalipsoSchedaServizioDAO.recoverStgCalipsoSchedaServizio();
			
			// SIRE CURRENT
			logger.debug("START recover SIRE Current");
			SireCurrentProjectDAO.recoverSireCurrentProject();
			SireCurrentWorkitemLinkedDAO.recoverSireCurrentWorkitemLinked();

			// SIRE HISTORY
			logger.debug("START recover SIRE History");
			SireHistoryAttachmentDAO.recoverSireHistoryAttachement();
			SireHistoryCfWorkitemDAO.recoverSireHistoryCfWorkItem();
			SireHistoryHyperlinkDAO.recoverSireHistoryHyperlink();
			SireHistoryProjectDAO.recoverSireHistoryProject();
			SireHistoryProjectGroupDAO.recoverSireHistoryProjectGroup();
			SireHistoryRevisionDAO.recoverSireHistoryRevision();
			SireHistoryUserDAO.recoverSireHistoryUser();
			SireHistoryWorkitemDAO.recoverSireHistoryWorkitem();
			SireHistoryWorkitemLinkedDAO.recoverSireHistoryWorkItemLinked();
			SireHistoryWorkitemUserAssignedDAO
					.recoverSireHistoryWIUserAssigned();
			VSireHistoryWorkitemLinkDAO.recoverVSireWorkitemLink();

			// SISS CURRENT
			logger.debug("START recover SISS Current");
			SissCurrentProjectDAO.recoverSissCurrentProject();
			SissCurrentWorkitemLinkedDAO.recoverSissCurrentWorkitemLinked();

			// SISS HISTORY
			logger.debug("START recover SISS History");
			SissHistoryAttachmentDAO.recoverSissHistoryAttachement();
			SissHistoryCfWorkitemDAO.recoverSissHistoryCfWorkItem();
			SissHistoryHyperlinkDAO.recoverSissHistoryHyperlink();
			SissHistoryProjectDAO.recoverSissHistoryProject();
			SissHistoryProjectGroupDAO.recoverSissHistoryProjectGroup();
			SissHistoryRevisionDAO.recoverSissHistoryRevision();
			SissHistoryUserDAO.recoverSissHistoryUser();
			SissHistoryWorkitemDAO.recoverSissHistoryWorkitem();
			SissHistoryWorkitemLinkedDAO.recoverSissHistoryWorkItemLinked();
			SissHistoryWorkitemUserAssignedDAO
					.recoverSissHistoryWIUserAssigned();
			VSissHistoryWorkitemLinkDAO.recoverVSissWorkitemLink();

			// XML
			logger.debug("START recover XML");
			LinkedWorkItemRolesXML.recoverLinkedWorkItemRoles();
			ProjectRolesXML.recoverAllProjectRoles();
			StatoWorkItemXML.recoverStatoWorkitem();
			SIRESchedeServizioXML.recoverSIRESchedeServizio();
			SISSSchedeServizioXML.recoverSISSSchedeServizio();

			// EDMA
			logger.debug("START recover Edma");
			PersonaleDAO.recoverPersonale();
			UnitaOrganizzativaDAO.recoverUO();

			// ORESTE
			/*
			logger.debug("START recover Oreste");
			AmbienteTecnologicoDAO.recoverAmbienteTecnologico();
			ProdottiArchitettureDAO.recoverProdottiArchitetture();
			SottosistemiDAO.recoverSottosistemi();
			ModuliDAO.recoverModuli();
			FunzionalitaDAO.recoverFunzionalita();
			ClassificatoriDAO.recoverClassificatori();
			*/
			// ELETTRA
			logger.debug("START recover Elettra");
			StgElUnitaOrganizzativeDAO.recoverElUnitaOrganizzativa();
			StgElPersonaleDAO.recoverElPersonale();
			StgElClassificatoriDAO.recoverElClassificatori();
			StgElProdottiDAO.recoverElProdotti();
			StgElModuliDAO.recoverElModuli();
			StgElFunzionalitaDAO.recoverElFunzionalita();
			StgElAmbienteTecnologicoDAO.recoverElAmbienteTec();
			StgElAmbienteTecnologicoClassificatoriDAO.recoverElAmbienteClass();
			
			// UTIL
			logger.debug("START recover Util");
			ErroriCaricamentoDAO.recoverErroriCaricamento();
			EsitiCaricamentoDAO.recoverEsitiCaricamento();

			setRecovered(true);

			logger.info("[RECOVER ALL] STOP RECOVER STAGING");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	public void startRecoverStgMps() {
		try {
			logger.info("[RECOVER MPS] START RECOVER STAGING MPS");

			logger.debug("START recover Mps");
			StgMpsFacade.recoverStaging();

			setRecoveredStagingMps(true);

			logger.info("[RECOVER MPS] STOP RECOVER STAGING MPS");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}

	public void startRecoverTrgMps() {
		try {
			logger.info("[RECOVER MPS] START RECOVER TARGET MPS");

			QueryManager qm = null;

			try {

				qm = QueryManager.getInstance();

				String separator = ";";

				// cancella tutto il contenuto delle tabelle target
				qm.executeMultipleStatementsFromFile(
						DmAlmConstants.DELETE_TARGET_MPS_TABLES, separator);

				// inserisce il contenuto delle tabelle di backup nelle tabelle
				// target
				// ovvero riporta il target allo stato precedente all'inizio
				// dell'esecuzione dell'ETL
				qm.executeMultipleStatementsFromFile(
						DmAlmConstants.RECOVER_MPS_TARGET, separator);

				// flag valido sia per lo staging che per il target
				setRecoveredStagingMps(true);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}

			logger.info("[RECOVER MPS] STOP RECOVER TARGET MPS");
		} catch (Exception e) {
			logger.error(e.toString(), e);
		}
	}
}
