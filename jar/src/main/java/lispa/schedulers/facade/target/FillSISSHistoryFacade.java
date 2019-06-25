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
import lispa.schedulers.dao.sgr.siss.history.SissHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryCfWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectGroupDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryUserDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemLinkedDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryWorkitemUserAssignedDAO;
import lispa.schedulers.dao.sgr.siss.history.VSissHistoryWorkitemLinkDAO;
import lispa.schedulers.dao.target.SchedeServizioDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.fonte.sgr.siss.history.SissHistoryWorkitem;
import lispa.schedulers.runnable.staging.sire.current.SissSchedeServizioRunnable;
import lispa.schedulers.runnable.staging.siss.current.SissUserRolesRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryAttachmentRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryHyperlinkRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryProjectGroupRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryProjectRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryRevisionRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryUserRunnable;
import lispa.schedulers.runnable.staging.siss.history.SissHistoryWorkitemUserAssignedRunnable;
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
public class FillSISSHistoryFacade {

	static SissHistoryWorkitem fonteWorkItems = SissHistoryWorkitem.workitem;

	private static int retry;

	private static int wait;

	public static void execute(Logger logger) {
		try {
			logger.debug("START fillSissHistory  " + new Date());
			fillSissHistory(logger);
			logger.debug("STOP fillSissHistory  " + new Date());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}

	public static void delete(Logger logger, Timestamp dataEsecuzione) {

		try {
			logger.debug("START SissHistoryProjectGroup.delete  " + new Date());
			SissHistoryProjectGroupDAO.delete();
			logger.debug("STOP SissHistoryProjectGroup.delete  " + new Date());

			logger.debug("START SissHistoryLinkedWorkitems.delete  "
					+ new Date());
			SissHistoryWorkitemLinkedDAO.delete();
			logger.debug("STOP SissHistoryLinkedWorkitems.delete  "
					+ new Date());

			logger.debug("START VSissWorkitemDAOLink.delete  " + new Date());
			VSissHistoryWorkitemLinkDAO.delete();
			logger.debug("STOP VSissWorkitemLink.delete  " + new Date());

			//logger.debug("START UserRolesDAO.delete  " + new Date());
			//UserRolesDAO.delete(dataEsecuzione, DmAlmConstants.REPOSITORY_SISS);
			//logger.debug("STOP UserRolesDAO.delete  " + new Date());

			logger.debug("START SissHistoryCfWorkitemDAO.delete  " + new Date());
			SissHistoryCfWorkitemDAO.delete();
			logger.debug("STOP SissHistoryCfWorkitemDAO.delete  " + new Date());

			logger.debug("START SissHistoryWorkitemDAO.delete  " + new Date());
			SissHistoryWorkitemDAO.delete(dataEsecuzione);
			logger.debug("STOP SissHistoryWorkitemDAO.delete  " + new Date());

			logger.debug("START SchedeServizioDAO.delete  " + new Date());
			SchedeServizioDAO.delete(dataEsecuzione,
					DmAlmConstants.REPOSITORY_SISS);
			SissHistoryWorkitemUserAssignedDAO.delete();
			logger.debug("STOP SchedeServizioDAO.delete  " + new Date());
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}

	}

	public static void fillSissHistory(Logger logger) {

		Thread revision, user, project, projectGroup, userRoles, workitemUserAssignee, attachment, hyperlink, schede_servizio;
		// Thread workitemLinked, vLink;

		try {
			logger.debug("START SISSHistoryFacade.fill()");

			Map<EnumWorkitemType, Long> minRevisionsByType = SissHistoryWorkitemDAO
					.getMinRevisionByType();

			long user_minRevision = SissHistoryUserDAO.getMinRevision();
			long project_minRevision = SissHistoryProjectDAO.getMinRevision();

			long polarion_maxRevision = SissHistoryRevisionDAO.getMaxRevision();
			Timestamp revision_minRevision = SissHistoryRevisionDAO
					.getMinRevision();

			// Recupero il numero di revisione più alto presente nella tabella
			// dmalm.ATTACHMENT
			long attachment_minRevision = SissHistoryAttachmentDAO
					.getMinRevision();

			revision = new Thread(new SissHistoryRevisionRunnable(
					revision_minRevision, polarion_maxRevision, logger));
			
			project = new Thread(new SissHistoryProjectRunnable(
					0, polarion_maxRevision, logger));
			projectGroup = new Thread(new SissHistoryProjectGroupRunnable(
					logger));
			
			
			project.start();
			project.join();

			revision.start();
			revision.join();

			projectGroup.start();
			projectGroup.join();


			logger.debug("STOP SISSHistoryFacade.fill()");
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		}
	}
}