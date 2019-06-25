package lispa.schedulers.facade.target;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_RETRY;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.DMALM_DEADLOCK_WAIT;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectGroupDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryUserDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemLinkedDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryWorkitemUserAssignedDAO;
import lispa.schedulers.dao.sgr.sire.history.VSireHistoryWorkitemLinkDAO;
import lispa.schedulers.dao.target.SchedeServizioDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.sire.history.SireHistoryWorkitem;
import lispa.schedulers.runnable.staging.sire.current.SireSchedeServizioRunnable;
import lispa.schedulers.runnable.staging.sire.current.SireUserRolesRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryAttachmentRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryHyperlinkRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryProjectGroupRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryProjectRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryRevisionRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryUserRunnable;
import lispa.schedulers.runnable.staging.sire.history.SireHistoryWorkitemUserAssignedRunnable;
import lispa.schedulers.utils.EnumUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

/**
 * In particolare, il popolamento delle tabelle di staging di SGR_CM avviene
 * come segue: - Tabella Workitem: si recupera il numero massimo di revisione
 * (x) presente nella tabella al momento dell’esecuzione, e la si riempie con i
 * record che alla fonte presentano una revisione maggiore di x e che sono stati
 * inseriti in Polarion da almeno un’ora (per ragione di integrità dei dati). -
 * Tabella CFWorkitem: si inseriscono esclusivamente i Custom Fields relativi ai
 * Workitems inseriti nel passo precedente. - Tabella User: si recupera il
 * numero massimo di revisione (x) presente nella tabella al momento
 * dell’esecuzione, e la si riempie con i record che alla fonte presentano una
 * revisione maggiore di x e che sono stati inseriti in Polarion da almeno
 * un’ora (per ragione di integrità dei dati). - Tabella Project: si recupera il
 * numero massimo di revisione (x) presente nella tabella al momento
 * dell’esecuzione, e la si riempie con i record che alla fonte presentano una
 * revisione maggiore di x e che sono stati inseriti in Polarion da almeno
 * un’ora (per ragione di integrità dei dati). Il Template del project viene
 * recuperato accedendo alla relativa repository SVN e analizzando il contenuto
 * del file “template-verison.ini” presente al path riportato nella colonna
 * C_LOCATION. In il template è il valore assunto dal campo “template-id”
 * riportato nel file succitato. Alla tabella Project è stata aggiunta la
 * colonna c_updated assente nella tabella fonte. - Tabella ProjectGroup: per
 * questa tabella si procede in maniera differente. Visto che alla fonte ogni
 * ProjectGroup presenta un numero di revisione = 0, si provvede per ogni
 * esecuzione a cancellare e a ricaricare completamente tale tabella. - Tabella
 * WorkitemLinked: si inseriscono esclusivamente i Link relativi ai Workitems
 * inseriti nel primo passo. - Tabella WorkitemUserAssignee: si inseriscono
 * esclusivamente gli UserAssignee relativi ai workitem inseriti nel primo
 * passo. - Tabella Attachment: si inseriscono esclusivamente gli Attachment
 * relativi ai workitem inseriti nel primo passo - Tabella Hyperlink: si
 * inseriscono esclusivamente gli Hyperlink relativi ai workitem inseriti nel
 * primo passo.
 * 
 * In alcuni casi specifici non viene effettuata la copia totale dei dati nelle
 * fonti, ma vengono applicati dei filtri che consentono di inserire nell’area
 * di staging solo i record che effettivamente sono necessari. Per esempio
 * vengono considerati i soli Workitem che hanno il campo C_TYPE uguale a
 * 'documento', 'testcase', 'pei', 'build', 'progettoese', ‘fase', 'defect',
 * 'release', 'sottoprogramma', ‘programma', 'taskit', 'anomalia',
 * 'anomalia_assistenza', 'release_it', 'sman', 'release_ser', 'drqs', 'dman',
 * 'rqd', 'richiesta_gestione', 'srqs', 'task.‘anomalia’, ’anomalia_assistenza’
 * o ’defect’ ed i soli Project il cui campo C_LOCATION inizia con
 * ‘default:/Sviluppo/’. Anche il popolamento dei Custom fields avviene in base
 * ad un filtro il quale tiene conto solamente di quelli di interesse a seconda
 * del tipo di Workitem.
 * 
 * @author fdeangel
 * 
 */
public class FillSIREHistoryFacade {

	static SireHistoryWorkitem fonteWorkItems = SireHistoryWorkitem.workitem;

	private static int retry;

	private static int wait;

	public static void execute(Logger logger) {
		try {

			logger.debug("START fillSireHistory  " + new Date());
			fillSireHistory(logger);
			logger.debug("STOP fillSireHistory  " + new Date());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}

	public static void delete(Logger logger, Timestamp dataEsecuzione) {

		try {
			logger.debug("START SireHistoryProjectGroup.delete  " + new Date());
			SireHistoryProjectGroupDAO.delete();
			logger.debug("STOP SireHistoryProjectGroup.delete  " + new Date());

			logger.debug("START SireHistoryLinkedWorkitems.delete  "
					+ new Date());
			SireHistoryWorkitemLinkedDAO.delete();
			logger.debug("STOP SireHistoryLinkedWorkitems.delete  "
					+ new Date());

			logger.debug("START VSireWorkitemDAOLink.delete  " + new Date());
			VSireHistoryWorkitemLinkDAO.delete();
			logger.debug("STOP VSireWorkitemLink.delete  " + new Date());

			//logger.debug("START UserRolesDAO.delete  " + new Date());
			//UserRolesDAO.delete(dataEsecuzione, DmAlmConstants.REPOSITORY_SIRE);
			//logger.debug("STOP UserRolesDAO.delete  " + new Date());

			logger.debug("START SireHistoryCfWorkitemDAO.delete  " + new Date());
			SireHistoryCfWorkitemDAO.delete();
			logger.debug("STOP SireHistoryCfWorkitemDAO.delete  " + new Date());

			logger.debug("START SireHistoryWorkitemDAO.delete  " + new Date());
			SireHistoryWorkitemDAO.delete(dataEsecuzione);
			logger.debug("STOP SireHistoryWorkitemDAO.delete  " + new Date());

			logger.debug("START SchedeServizioDAO.delete  " + new Date());
			SchedeServizioDAO.delete(dataEsecuzione,
					DmAlmConstants.REPOSITORY_SIRE);
			logger.debug("STOP SchedeServizioDAO.delete  " + new Date());
			
			SireHistoryWorkitemUserAssignedDAO.delete();
		
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

	public static void fillSireHistory(Logger logger) {

		Thread revision, user, project, projectGroup, workitemUserAssignee, attachment, hyperlink, userRoles, schede_servizio;
		// Thread workitemLinked, vLink;

		try {
			logger.debug("START SIREHistoryFacade.fill()");

			long polarion_maxRevision = SireHistoryRevisionDAO.getMaxRevision();

			// Recupero il numero di revisione più alto presente nella tabella
			// dmalm.PROJECT
			long project_minRevision = SireHistoryProjectDAO.getMinRevision();

			// Recupero la MAX DT_CREAZIONE nella tabella dmalm.REVISION
			Timestamp revision_minRevision = SireHistoryRevisionDAO
					.getMinRevision();

			// Recupero il numero di revisione più alto presente nella tabella
			// dmalm.ATTACHMENT
		
			revision = new Thread(new SireHistoryRevisionRunnable(
					revision_minRevision, polarion_maxRevision, logger));
			
			project = new Thread(new SireHistoryProjectRunnable(
					0, polarion_maxRevision, logger));
			projectGroup = new Thread(new SireHistoryProjectGroupRunnable(
					logger));

			project.start();
			project.join();

			revision.start();
			revision.join();

			projectGroup.start();
			projectGroup.join();

			ConnectionManager.getInstance().dismiss();

			logger.debug("STOP SIREHistoryFacade.fill()");
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}
}