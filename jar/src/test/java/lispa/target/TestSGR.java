package lispa.target;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import junit.framework.TestCase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.EsitiCaricamentoDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryProjectDAO;
import lispa.schedulers.dao.sgr.sire.history.SireHistoryRevisionDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryAttachmentDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryProjectDAO;
import lispa.schedulers.dao.sgr.siss.history.SissHistoryRevisionDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.facade.target.AreaTematicaSgrCmFacade;
import lispa.schedulers.facade.target.FillEdmaFacade;
import lispa.schedulers.facade.target.ProjectRolesSgrFacade;
import lispa.schedulers.facade.target.ProjectSgrCmFacade;
import lispa.schedulers.facade.target.UserSgrCmFacade;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.manager.RecoverManager;
import lispa.schedulers.queryimplementation.staging.QDmalmEsitiCaricamenti;
import lispa.schedulers.queryimplementation.target.QDmalmLinkedWorkitems;
import lispa.schedulers.runnable.staging.SGRCMSissRunnable;
import lispa.schedulers.utils.DateUtils;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;

public class TestSGR extends TestCase

{
	private static Logger logger = Logger.getLogger(TestSGR.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();

	public void testProps() throws Exception {

	}

	public void testThreadManager() throws Exception {

		try {

			// Thread user = new Thread (new SireHistoryUserRunnable(370000,
			// Long.MAX_VALUE, logger));

			// Thread project = new Thread(new SireHistoryProjectRunnable(0,
			// Long.MAX_VALUE, logger));
			// Thread projectGroup = new Thread(new
			// SireHistoryProjectGroupRunnable(logger));
			// Thread workitemUserAssignee = new Thread(new
			// SireHistoryWorkitemUserAssignedRunnable(workitem_minRevision,
			// polarion_maxRevision, logger));
			// Thread workitem = new Thread(new
			// SireHistoryWorkitemRunnable(workitem_minRevision,
			// polarion_maxRevision, logger));
			// Thread workitemLinked = new Thread(new
			// SireHistoryWorkitemLinkedRunnable(workitem_minRevision,
			// polarion_maxRevision, logger));
			// Thread vLink = new Thread(new
			// SireHistoryVWorkitemLinkRunnable(logger));
			//
			//
			// user.start();
			// project.start();
			// projectGroup.start();
			// workitemUserAssignee.start();
			// workitem.start();
			// workitemLinked.start();
			// vLink.start();

			// user.join();
			// project.join();projectGroup.join();workitemUserAssignee.join();workitem.join();workitemLinked.join();vLink.join();
		} catch (Exception e) {

		}

	}

	public void testFillSissHistoryMultithread() throws Exception {

		Log4JConfiguration.inizialize();

		Thread siss = new Thread(new SGRCMSissRunnable(logger, DataEsecuzione
				.getInstance().getDataEsecuzione()));

		siss.start();

		siss.join();

		ConnectionManager.printInfo();

	}

	public void testConnectionPool() throws Exception {

		// SireHistoryWorkitemDAO.fillSireHistoryWorkitem(350000,
		// Long.MAX_VALUE);
		//
		//
		//
		// SireHistoryAttachmentDAO.fillSireHistoryAttachment(0,
		// Long.MAX_VALUE);
		//
		//
		//
		// SissCurrentProjectDAO.fillSissCurrentProject();
		//
		//
		//
		// SissHistoryWorkitemLinkedDAO.fillSissHistoryWorkitemLinked(450000,
		// Long.MAX_VALUE);
		//
		//
		//
		// SissHistoryHyperlinkDAO.fillSissHistoryHyperlink(450000,
		// Long.MAX_VALUE);
		//

		FillEdmaFacade.execute(logger);

		// FillOresteFacade.execute(logger);

	}

	public void testRecordSentinella() throws Exception {
		EsitiCaricamentoDAO.inserisciRecordSentinella();

		//
	}

	public void testFillAttachment() throws Exception {

		SireHistoryAttachmentDAO.fillSireHistoryAttachment(
				SireHistoryAttachmentDAO.getMinRevision(), Long.MAX_VALUE);

		SissHistoryAttachmentDAO.fillSissHistoryAttachment(
				SissHistoryAttachmentDAO.getMinRevision(), Long.MAX_VALUE);

	}

	public void testFillProject() throws Exception {

		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-06-27 17:11:00", "yyyy-MM-dd HH:mm:00");

		ProjectSgrCmFacade.execute(dataEsecuzione);

	}

	public void testUserRolesSGR() throws Exception {

		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-02-17 00:00:00", "yyyy-MM-dd 00:00:00");
		//
		// UserRolesSgrFacade.execute(dataEsecuzione);

		// SIREUserRolesXML.fillSIREUserRoles();

		// SireHistoryProjectDAO.fillSireHistoryProject(0);
		// SissHistoryProjectDAO.fillSissHistoryProject(0);
		ProjectSgrCmFacade.execute(dataEsecuzione);

	}

	public void testEsitiCaricamento() throws Exception {

		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-01-29 00:00:00", "yyyy-MM-dd 00:00:00");
		String stato = DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE;
		Date dtInizioCaricamento = new Date();
		Date dtFineCaricamento = new Date();
		int righeNuove = 0;

		try {

			EsitiCaricamentoDAO.insert(dataEsecuzione,
					DmAlmConstants.TARGET_USERROLES, stato, new Timestamp(
							dtInizioCaricamento.getTime()), new Timestamp(
							dtFineCaricamento.getTime()), righeNuove, 0, 0, 0);
		} catch (Throwable e) {

		}
	}

	public void testProjectRolesSGR() throws Exception {

		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-01-29 00:00:00", "yyyy-MM-dd 00:00:00");
		ProjectRolesSgrFacade.execute(dataEsecuzione);

	}

	public void testAreaTematica() throws Exception {
		Timestamp dataEsecuzione = DateUtils.stringToTimestamp(
				"2014-02-12 00:00:00", "yyyy-MM-dd 00:00:00");
		AreaTematicaSgrCmFacade.execute(dataEsecuzione);
	}

	public void testFillProjectGroupStaging() throws Exception {
		SireHistoryProjectDAO.fillSireHistoryProject(313186, Long.MAX_VALUE);
		SissHistoryProjectDAO.fillSissHistoryProject(438479, Long.MAX_VALUE);

	}

	public void testFillProjectStaging() throws Exception {
		long minRevisionProjectr = SireHistoryProjectDAO.getMinRevision();
		long maxRevisionr = SireHistoryRevisionDAO.getMaxRevision();
		long minRevisionProjects = SissHistoryProjectDAO.getMinRevision();
		long maxRevisions = SissHistoryRevisionDAO.getMaxRevision();

		SireHistoryProjectDAO.fillSireHistoryProject(minRevisionProjectr,
				maxRevisionr);
		SissHistoryProjectDAO.fillSissHistoryProject(minRevisionProjects,
				maxRevisions);
	}

	public void testGetDescriptionsWorkitem() throws Exception {

		// SissHistoryWorkitemDAO.updateDescriptions(450000, Long.MAX_VALUE);

		// SireHistoryWorkitemDAO.updateDescriptions(350000, Long.MAX_VALUE);

	}

	public void testDmalmSGRUser() throws DAOException, Exception {
		UserSgrCmFacade.execute(DateUtils.stringToTimestamp(
				"2014-07-02 16:42:00", "yyyy-MM-dd HH:mm:00"));
	}

	public void testSetUnmarked() throws Exception {

		// ProjectSgrCmDAO.setUNMARKED("default:/Sviluppo/SIRE/A355.Area.Sistemi.Territoriali/Tutela.Valorizzazione.ambientale/BONIFICHE/ANBONI_GEST/.polarion/polarion-project.xml");

	}

	public static boolean alreadyExecuted(String target) {

		boolean res;

		QDmalmEsitiCaricamenti qEsitiCaricamento = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;

		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			List<String> esito = new SQLQuery(connection, dialect)
					.from(qEsitiCaricamento)
					.where(qEsitiCaricamento.entitaTarget.eq(target))
					.where(qEsitiCaricamento.dataCaricamento.eq(DataEsecuzione
							.getInstance().getDataEsecuzione()))
					.list(qEsitiCaricamento.statoEsecuzione);

			res = (esito != null && esito.size() == 0) ? false : esito.get(0)
					.equals(DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			res = false;
		} finally {
			try {
				if (cm != null)
					cm.closeConnection(connection);
			} catch (Exception e) {

			}
		}

		return res;
	}

	public static void testdelete() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;

		QDmalmLinkedWorkitems linkedWorkitemsTarget = QDmalmLinkedWorkitems.dmalmLinkedWorkitems;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			// I LinkedWorkitems vengono cancellati e reinseriti ogni giorno da
			// zero
			new SQLDeleteClause(connection, dialect, linkedWorkitemsTarget)
					.execute();

		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			e.printStackTrace();

		} finally {

			try {
				if (ps != null)
					ps.close();
			} catch (SQLException e) {
			}
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public void testRecoverAll() throws PropertiesReaderException {
		Log4JConfiguration.inizialize();

		logger.info("*** Eseguo Test testRecoverAll **** ");

		DataEsecuzione.getInstance().setDataEsecuzione(
				DateUtils.stringToTimestamp("2018-04-09 20:40:00.0",
						"yyyy-MM-dd HH:mm:00"));

		RecoverManager.getInstance().startRecoverStaging();
		RecoverManager.getInstance().startRecoverTarget();

	}

}