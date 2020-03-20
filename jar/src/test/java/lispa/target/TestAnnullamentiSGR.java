package lispa.target;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLUpdateClause;

import junit.framework.TestCase;
import lispa.schedulers.bean.annullamenti.project.DmAlmProjectUnmarked;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaAssistenza;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto;
import lispa.schedulers.bean.target.fatti.DmalmBuild;
import lispa.schedulers.bean.target.fatti.DmalmClassificatore;
import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.bean.target.fatti.DmalmDocumento;
import lispa.schedulers.bean.target.fatti.DmalmFase;
import lispa.schedulers.bean.target.fatti.DmalmManutenzione;
import lispa.schedulers.bean.target.fatti.DmalmPei;
import lispa.schedulers.bean.target.fatti.DmalmProgettoDemand;
import lispa.schedulers.bean.target.fatti.DmalmProgettoEse;
import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoDem;
import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoSvil;
import lispa.schedulers.bean.target.fatti.DmalmProgramma;
import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.bean.target.fatti.DmalmReleaseIt;
import lispa.schedulers.bean.target.fatti.DmalmReleaseServizi;
import lispa.schedulers.bean.target.fatti.DmalmRfc;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaGestione;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaManutenzione;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaSupporto;
import lispa.schedulers.bean.target.fatti.DmalmSottoprogramma;
import lispa.schedulers.bean.target.fatti.DmalmTask;
import lispa.schedulers.bean.target.fatti.DmalmTaskIt;
import lispa.schedulers.bean.target.fatti.DmalmTestcase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.fatti.AnomaliaAssistenzaDAO;
import lispa.schedulers.dao.target.fatti.AnomaliaProdottoDAO;
import lispa.schedulers.dao.target.fatti.BuildDAO;
import lispa.schedulers.dao.target.fatti.ClassificatoreDAO;
import lispa.schedulers.dao.target.fatti.DifettoDAO;
import lispa.schedulers.dao.target.fatti.DocumentoDAO;
import lispa.schedulers.dao.target.fatti.FaseDAO;
import lispa.schedulers.dao.target.fatti.ManutenzioneDAO;
import lispa.schedulers.dao.target.fatti.PeiDAO;
import lispa.schedulers.dao.target.fatti.ProgettoDemandDAO;
import lispa.schedulers.dao.target.fatti.ProgettoEseDAO;
import lispa.schedulers.dao.target.fatti.ProgettoSviluppoDemandDAO;
import lispa.schedulers.dao.target.fatti.ProgettoSviluppoSviluppoDAO;
import lispa.schedulers.dao.target.fatti.ProgrammaDAO;
import lispa.schedulers.dao.target.fatti.ReleaseDiProgettoDAO;
import lispa.schedulers.dao.target.fatti.ReleaseItDAO;
import lispa.schedulers.dao.target.fatti.ReleaseServiziDAO;
import lispa.schedulers.dao.target.fatti.RfcDAO;
import lispa.schedulers.dao.target.fatti.RichiestaGestioneDAO;
import lispa.schedulers.dao.target.fatti.RichiestaManutenzioneDAO;
import lispa.schedulers.dao.target.fatti.RichiestaSupportoDAO;
import lispa.schedulers.dao.target.fatti.SottoprogrammaDAO;
import lispa.schedulers.dao.target.fatti.TaskDAO;
import lispa.schedulers.dao.target.fatti.TaskItDAO;
import lispa.schedulers.dao.target.fatti.TestCaseDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.annullamenti.project.QDmAlmProjectUnmarked;
import lispa.schedulers.queryimplementation.target.QDmalmProject;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmBuild;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmClassificatore;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDifettoProdotto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDocumento;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmFase;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmManutenzione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmPei;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoDemand;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoEse;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoDem;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoSvil;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgramma;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseDiProgetto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseIt;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseServizi;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaGestione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaManutenzione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSottoprogramma;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTask;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTaskIt;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTestcase;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

public class TestAnnullamentiSGR extends TestCase {

	private static Logger logger = Logger.getLogger(TestAnnullamentiSGR.class);
	private static List<DmAlmProjectUnmarked> unmarkedProjectsSire, unmarkedProjectsSiss;
//	private static List<String> alreadyUnmarkedProjectsPathsSire, alreadyUnmarkedProjectsPathsSiss;

	public void testAnnullamentiSgr() {
		DmAlmConfigReaderProperties.setFileProperties("/Users/lucaporro/LISPA/DataMart/props/dm_alm.properties");
		DataEsecuzione.getInstance().setDataEsecuzione(DateUtils.stringToTimestamp("2020-03-11 00:00:00.0", "yyyy-MM-dd HH:mm:00"));

		try {
			Log4JConfiguration.inizialize();
//			unmarkedProjectsSire = getUnmarkedProjects(DmAlmConstants.REPOSITORY_SIRE);
//			unmarkedProjectsSiss = getUnmarkedProjects(DmAlmConstants.REPOSITORY_SISS);
//			/**
//			 * Projects già annullati (non c'è necessità di annullare ad ogni
//			 * esecuzione)
//			 */
////			alreadyUnmarkedProjectsPathsSire = ProjectSgrCmDAO
////					.getAlreadyUnmarkedProjects(DmAlmConstants.REPOSITORY_SIRE);
////			alreadyUnmarkedProjectsPathsSiss = ProjectSgrCmDAO
////					.getAlreadyUnmarkedProjects(DmAlmConstants.REPOSITORY_SISS);
//
//			for (DmAlmProjectUnmarked projectUn : unmarkedProjectsSire) {
//				logger.info("unmarkedProjectsSire SIRE: "+projectUn.getPath()+" dataCaricamento: "+projectUn.getDataCaricamento());
////				if (!alreadyUnmarkedProjectsPathsSire.contains(projectUn.getPath())) {
//					DmalmProject p = getProjectByPath(projectUn.getPath(), DmAlmConstants.REPOSITORY_SIRE, projectUn.getDataCaricamento());
//					if (p != null) {
//						if (p.getDtInizioValidita().equals(projectUn.getDataCaricamento())) {
//
//							p.setAnnullato(DmAlmConstants.LOGICAMENTE);
//							p.setDtAnnullamento(projectUn.getDataCaricamento());
//							updateDmalmProject(p, projectUn.getDataCaricamento());
//							logger.info("update fatto!");
//						} else {
////								ProjectSgrCmDAO.updateDataFineValiditaAnnullamento(
////										DataEsecuzione.getInstance()
////												.getDataEsecuzione(), p);
////								p.setAnnullato(DmAlmConstants.LOGICAMENTE);
////								p.setDtAnnullamento(DataEsecuzione.getInstance()
////										.getDataEsecuzione());
////								p.setDtInizioValidita(DataEsecuzione.getInstance()
////										.getDataEsecuzione());
////								// insert update nuovo record
////								ProjectSgrCmDAO.insertProjectUpdate(DataEsecuzione
////										.getInstance().getDataEsecuzione(), p,
////										false);
//							logger.info("dt_inizio_validita<>data_esecuzione"+p.getIdProject());
//						}
//					}
////				}
//			}
//
//			for (DmAlmProjectUnmarked projectUn : unmarkedProjectsSiss) {
//				logger.info("unmarkedProjectsSiss SISS: "+projectUn.getPath()+" dataCaricamento: "+projectUn.getDataCaricamento());
////				if (!alreadyUnmarkedProjectsPathsSiss.contains(projectUn.getPath())) {
//					DmalmProject p = getProjectByPath(projectUn.getPath(), DmAlmConstants.REPOSITORY_SISS, projectUn.getDataCaricamento());
//					if (p != null) {
//						if (p.getDtInizioValidita().equals(projectUn.getDataCaricamento())) {
//
//							p.setAnnullato(DmAlmConstants.LOGICAMENTE);
//							p.setDtAnnullamento(projectUn.getDataCaricamento());
//							updateDmalmProject(p, projectUn.getDataCaricamento());
//							logger.info("update fatto!");
//						} else {
////								ProjectSgrCmDAO.updateDataFineValiditaAnnullamento(
////										DataEsecuzione.getInstance()
////												.getDataEsecuzione(), p);
////								p.setAnnullato(DmAlmConstants.LOGICAMENTE);
////								p.setDtAnnullamento(DataEsecuzione.getInstance()
////										.getDataEsecuzione());
////								p.setDtInizioValidita(DataEsecuzione.getInstance()
////										.getDataEsecuzione());
////								// insert update nuovo record
////								ProjectSgrCmDAO.insertProjectUpdate(DataEsecuzione
////										.getInstance().getDataEsecuzione(), p,
////										false);
//							logger.info("dt_inizio_validita<>data_esecuzione"+p.getIdProject());
//						}
//					}
////				}
//			}
			List<Timestamp> listDtCaricamento = getDtCaricamentoUnmarkedProjects();
			for (Timestamp dtCaricamento : listDtCaricamento) {
				annullaWorkitemFigli(dtCaricamento);
			}
		}
		

		catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
	
	private static void updateDmalmProject(DmalmProject project, Timestamp dataCaricamento) throws DAOException {

		QDmalmProject proj = QDmalmProject.dmalmProject;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, proj)
					.where(proj.idProject.eq(project.getIdProject()))
					.where(proj.idRepository.eq(project.getIdRepository()))
					.where(proj.dtCaricamento.eq(dataCaricamento))
					.set(proj.dmalmAreaTematicaFk01,
							project.getDmalmAreaTematicaFk01())
					.set(proj.flAttivo, project.getFlAttivo())
					.set(proj.idRepository, project.getIdRepository())
					.set(proj.nomeCompletoProject,
							project.getNomeCompletoProject())
					.set(proj.pathProject, project.getPathProject())
					.set(proj.serviceManagers, project.getServiceManagers())
					.set(proj.siglaProject, project.getSiglaProject())
					.set(proj.dmalmStrutturaOrgFk02,
							project.getDmalmStrutturaOrgFk02())
					.set(proj.dmalmUnitaOrganizzativaFk,
							project.getDmalmUnitaOrganizzativaFk())
					.set(proj.cTrackerprefix, project.getcTrackerprefix())
					.set(proj.cIsLocal, project.getcIsLocal())
					.set(proj.cPk, project.getcPk())
					.set(proj.fkUriLead, project.getFkUriLead())
					.set(proj.cDeleted, project.getcDeleted())
					.set(proj.cFinish, project.getcFinish())
					.set(proj.cUri, project.getcUri())
					.set(proj.cStart, project.getcStart())
					.set(proj.fkUriProjectgroup, project.getFkUriProjectgroup())
					.set(proj.cActive, project.getcActive())
					.set(proj.fkProjectgroup, project.getFkProjectgroup())
					.set(proj.fkLead, project.getFkLead())
					.set(proj.cLockworkrecordsdate,
							project.getcLockworkrecordsdate())
					.set(proj.cRev, project.getcRev())
					.set(proj.cDescription, project.getcDescription())
					.set(proj.annullato, project.getAnnullato())
					.set(proj.dtAnnullamento, project.getDtAnnullamento()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	
	}
	
	private static List<DmAlmProjectUnmarked> getUnmarkedProjects(String repo) throws IOException, DAOException {
		QDmAlmProjectUnmarked projectUnmarked = QDmAlmProjectUnmarked.projectUnmarked;
		SQLTemplates dialect = new HSQLDBTemplates();
		List<Tuple> tupleProjectUnmarked = new ArrayList<Tuple>();
		List<DmAlmProjectUnmarked> listProjectUnmarked = new ArrayList<DmAlmProjectUnmarked>();
		Date primoLuglio = DateUtils.stringToDate("2019-07-01 00:00:00", "yyyy-MM-dd 00:00:00");
		Timestamp tsPrimoLuglio = new Timestamp(primoLuglio.getTime());
		ConnectionManager cm = null;
		Connection connectionOracle = null;

		try {

			cm = ConnectionManager.getInstance();
			connectionOracle = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connectionOracle, dialect);
			tupleProjectUnmarked = query.from(projectUnmarked)
					.where(projectUnmarked.repository.eq(repo))
					.where(projectUnmarked.dataCaricamento.goe(tsPrimoLuglio))
					.list(projectUnmarked.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connectionOracle);
			}
		}
		if (tupleProjectUnmarked.size() > 0) {
			for (Tuple t : tupleProjectUnmarked) {
				DmAlmProjectUnmarked p = new DmAlmProjectUnmarked();
				p.setcTrackerprefix(t.get(projectUnmarked.cTrackerprefix));
				p.setDataCaricamento(t.get(projectUnmarked.dataCaricamento));
				p.setPath("default:/"+t.get(projectUnmarked.path));
				p.setRepository(t.get(projectUnmarked.repository));

				listProjectUnmarked.add(p);
			}

			return listProjectUnmarked;
		} else {
			return listProjectUnmarked;
		}
	}
	
	private static List<Timestamp> getDtCaricamentoUnmarkedProjects() throws IOException, DAOException {

		QDmAlmProjectUnmarked projectUnmarked = QDmAlmProjectUnmarked.projectUnmarked;
		SQLTemplates dialect = new HSQLDBTemplates();
		List<Timestamp> listProjectUnmarked = new ArrayList<Timestamp>();
		Date primoLuglio = DateUtils.stringToDate("2019-07-01 00:00:00", "yyyy-MM-dd 00:00:00");
		Timestamp tsPrimoLuglio = new Timestamp(primoLuglio.getTime());
		ConnectionManager cm = null;
		Connection connectionOracle = null;

		try {

			cm = ConnectionManager.getInstance();
			connectionOracle = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(connectionOracle, dialect);
				listProjectUnmarked = query.from(projectUnmarked)
					.where(projectUnmarked.dataCaricamento.goe(tsPrimoLuglio))
					.distinct()
					.list(projectUnmarked.dataCaricamento);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connectionOracle);
			}
		}
			return listProjectUnmarked;
	}
	
	private static DmalmProject getProjectByPath(String path, String repo, Timestamp dataCaricamento)
			throws DAOException {

		QDmalmProject proj = QDmalmProject.dmalmProject;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;
		path = path + "/%";
		List<Tuple> projects = new ArrayList<Tuple>();
		DmalmProject ret = new DmalmProject();
		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query
					.from(proj)
					.where(proj.pathProject.like(path))
					.where(proj.dtCaricamento.eq(dataCaricamento))
					.where(proj.idRepository.eq(repo)).list(proj.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (projects.size() > 0) {
			Tuple t = projects.get(0);
			ret.setAnnullato(t.get(proj.annullato));
			ret.setcActive(t.get(proj.cActive));
			ret.setcCreated(t.get(proj.cCreated));
			ret.setcDeleted(t.get(proj.cDeleted));
			ret.setcDescription(t.get(proj.cDescription));
			ret.setcFinish(t.get(proj.cFinish));
			ret.setcIsLocal(t.get(proj.cIsLocal));
			ret.setcLockworkrecordsdate(t.get(proj.cLockworkrecordsdate));
			ret.setcPk(t.get(proj.cPk));
			ret.setcRev(t.get(proj.cRev));
			ret.setcStart(t.get(proj.cStart));
			ret.setcTemplate(t.get(proj.cTemplate));
			ret.setcTrackerprefix(t.get(proj.cTrackerprefix));
			ret.setcUri(t.get(proj.cUri));
			ret.setDmalmAreaTematicaFk01(t.get(proj.dmalmAreaTematicaFk01));
			ret.setDmalmProjectPk(t.get(proj.dmalmProjectPrimaryKey));
			ret.setDmalmStrutturaOrgFk02(t.get(proj.dmalmStrutturaOrgFk02));
			ret.setDmalmUnitaOrganizzativaFk(t.get(proj.dmalmUnitaOrganizzativaFk));
			ret.setDmalmUnitaOrganizzativaFlatFk(t.get(proj.dmalmUnitaOrganizzativaFlatFk));
			ret.setDtAnnullamento(t.get(proj.dtAnnullamento));
			ret.setDtCaricamento(t.get(proj.dtCaricamento));
			ret.setDtFineValidita(t.get(proj.dtFineValidita));
			ret.setDtInizioValidita(t.get(proj.dtInizioValidita));
			ret.setFkLead(t.get(proj.fkLead));
			ret.setFkProjectgroup(t.get(proj.fkProjectgroup));
			ret.setFkUriLead(t.get(proj.fkUriLead));
			ret.setFkUriProjectgroup(t.get(proj.fkUriProjectgroup));
			ret.setFlAttivo(t.get(proj.flAttivo));
			ret.setIdProject(t.get(proj.idProject));
			ret.setIdRepository(t.get(proj.idRepository));
			ret.setNomeCompletoProject(t.get(proj.nomeCompletoProject));
			ret.setPathProject(t.get(proj.pathProject));
			ret.setServiceManagers(t.get(proj.serviceManagers));
			ret.setSiglaProject(t.get(proj.siglaProject));

			return ret;
		} else {
			return null;
		}
	}
	
	private static List<DmalmProject> getProjectToLinkWi(String annullato,
			Timestamp dt_esecuzione) throws DAOException {

		QDmalmProject proj = QDmalmProject.dmalmProject;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> projects = new ArrayList<Tuple>();
		List<DmalmProject> ret = new ArrayList<DmalmProject>();
		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query
					.from(proj)
					.where(proj.annullato.eq(annullato))
					.where(proj.dtAnnullamento.eq(dt_esecuzione))
					.list(proj.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (projects.size() > 0) {
			for (Tuple t : projects) {
				DmalmProject p = new DmalmProject();
				p.setAnnullato(t.get(proj.annullato));
				p.setcActive(t.get(proj.cActive));
				p.setcCreated(t.get(proj.cCreated));
				p.setcDeleted(t.get(proj.cDeleted));
				p.setcDescription(t.get(proj.cDescription));
				p.setcFinish(t.get(proj.cFinish));
				p.setcIsLocal(t.get(proj.cIsLocal));
				p.setcLockworkrecordsdate(t.get(proj.cLockworkrecordsdate));
				p.setcPk(t.get(proj.cPk));
				p.setcRev(t.get(proj.cRev));
				p.setcStart(t.get(proj.cStart));
				p.setcTemplate(t.get(proj.cTemplate));
				p.setcTrackerprefix(t.get(proj.cTrackerprefix));
				p.setcUri(t.get(proj.cUri));
				p.setDmalmAreaTematicaFk01(t.get(proj.dmalmAreaTematicaFk01));
				p.setDmalmProjectPk(t.get(proj.dmalmProjectPrimaryKey));
				p.setDmalmStrutturaOrgFk02(t.get(proj.dmalmStrutturaOrgFk02));
				p.setDmalmUnitaOrganizzativaFk(t.get(proj.dmalmUnitaOrganizzativaFk));
				p.setDmalmUnitaOrganizzativaFlatFk(t.get(proj.dmalmUnitaOrganizzativaFlatFk));
				p.setDtAnnullamento(t.get(proj.dtAnnullamento));
				p.setDtCaricamento(t.get(proj.dtCaricamento));
				p.setDtFineValidita(t.get(proj.dtFineValidita));
				p.setDtInizioValidita(t.get(proj.dtInizioValidita));
				p.setFkLead(t.get(proj.fkLead));
				p.setFkProjectgroup(t.get(proj.fkProjectgroup));
				p.setFkUriLead(t.get(proj.fkUriLead));
				p.setFkUriProjectgroup(t.get(proj.fkUriProjectgroup));
				p.setFlAttivo(t.get(proj.flAttivo));
				p.setIdProject(t.get(proj.idProject));
				p.setIdRepository(t.get(proj.idRepository));
				p.setNomeCompletoProject(t.get(proj.nomeCompletoProject));
				p.setPathProject(t.get(proj.pathProject));
				p.setServiceManagers(t.get(proj.serviceManagers));
				p.setSiglaProject(t.get(proj.siglaProject));

				ret.add(p);
			}

			return ret;
		} else {
			return null;
		}
	}
	
	private static void annullaWorkitemFigli(Timestamp dataEsecuzione) throws DAOException,
		PropertiesReaderException {
		QDmalmDifettoProdotto difetto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
		QDmalmAnomaliaProdotto anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
		QDmalmProgettoSviluppoSvil progettoSviluppoSvil = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
		QDmalmDocumento documento = QDmalmDocumento.dmalmDocumento;
		QDmalmManutenzione manutenzione = QDmalmManutenzione.dmalmManutenzione;
		QDmalmTestcase testcase = QDmalmTestcase.dmalmTestcase;
		QDmalmTask task = QDmalmTask.dmalmTask;
		QDmalmReleaseDiProgetto releaseDiProgetto = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
		QDmalmProgramma programma = QDmalmProgramma.dmalmProgramma;
		QDmalmSottoprogramma sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;
		QDmalmProgettoDemand progettoDemand = QDmalmProgettoDemand.dmalmProgettoDemand;
		QDmalmProgettoSviluppoDem progettoSviluppoDem = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
		QDmalmRichiestaManutenzione richiestaManutenzione = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
		QDmalmFase fase = QDmalmFase.dmalmFase;
		QDmalmPei pei = QDmalmPei.dmalmPei;
		QDmalmProgettoEse progettoEse = QDmalmProgettoEse.dmalmProgettoEse;
		QDmalmReleaseIt releaseIT = QDmalmReleaseIt.dmalmReleaseIt;
		QDmalmBuild build = QDmalmBuild.dmalmBuild;
		QDmalmTaskIt taskIT = QDmalmTaskIt.dmalmTaskIt;
		QDmalmRichiestaGestione richiestaGestione = QDmalmRichiestaGestione.dmalmRichiestaGestione;
		QDmalmAnomaliaAssistenza anomaliaAssistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
		QDmalmReleaseServizi releaseServizi = QDmalmReleaseServizi.dmalmReleaseServizi;
		QDmalmClassificatore classificatore = QDmalmClassificatore.dmalmClassificatore;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {

			String unmarked = DmAlmConstants.LOGICAMENTE;
			List<DmalmProject> pNew = getProjectToLinkWi(DmAlmConstants.LOGICAMENTE, dataEsecuzione);

			for (EnumWorkitemType type : Workitem_Type.EnumWorkitemType.values()) {
				ConnectionManager cm = null;
				Connection conn = null;
				List<Integer> pk = new ArrayList<Integer>();
				if (pNew != null) {
					for (DmalmProject p : pNew) {
logger.info("project: "+p.getDmalmProjectPk()+" - "+p.getIdProject()+" - "+p.getIdRepository());
								try {
									cm = ConnectionManager.getInstance();
									conn = cm.getConnectionOracle();

									SQLQuery query = new SQLQuery(conn, dialect);

									switch (type.name()) {
									case "anomalia":
										pk = query
												.from(anomalia)
												.where(anomalia.dmalmProjectFk02.eq(p.getDmalmProjectPk()))
												.list(anomalia.dmalmAnomaliaProdottoPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {

												DmalmAnomaliaProdotto a = AnomaliaProdottoDAO
														.getAnomaliaProdotto(i);
												if (a != null) {
													if (a.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														a.setAnnullato(unmarked);
														a.setDtAnnullamento(dataEsecuzione);
														a.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														updateDmalmAnomaliaProdotto(a);
													}
												}
											}
										}
										break;
									case "defect":
										pk = query
												.from(difetto)
												.where(difetto.dmalmProjectFk02.eq(p.getDmalmProjectPk()))
												.list(difetto.dmalmDifettoProdottoPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {

												DmalmDifettoProdotto d = DifettoDAO
														.getDifetto(i);
												if (d != null) {
													if (d.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														d.setAnnullato(unmarked);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														updateDmalmDifettoProdotto(d);
													}
												}
											}
										}
										break;
									case "srqs":
										pk = query
												.from(progettoSviluppoSvil)
												.where(progettoSviluppoSvil.dmalmProjectFk02.eq(p.getDmalmProjectPk()))
												.list(progettoSviluppoSvil.dmalmProgSvilSPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {

												DmalmProgettoSviluppoSvil pss = ProgettoSviluppoSviluppoDAO
														.getProgettoSviluppoSviluppo(i);
												if (pss != null) {
													if (pss.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														pss.setAnnullato(unmarked);
														pss.setDtAnnullamento(dataEsecuzione);
														pss.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														
														updateProgettoSviluppoSvil(pss);
													}
												}
											}
										}
										break;
									case "documento":
										pk = query
												.from(documento)
												.where(documento.dmalmProjectFk02.eq(p.getDmalmProjectPk()))
												.list(documento.dmalmDocumentoPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {

												DmalmDocumento d = DocumentoDAO
														.getDocumento(i);
												if (d != null) {
													if (d.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														d.setAnnullato(unmarked);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														updateDocumento(d);
													}
												}
											}
										}
										break;
									case "sman":
										pk = query
												.from(manutenzione)
												.where(manutenzione.dmalmProjectFk02.eq(p.getDmalmProjectPk()))
												.list(manutenzione.dmalmManutenzionePk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmManutenzione m = ManutenzioneDAO
														.getManutenzione(i);
												if (m != null) {
													if (m.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														m.setAnnullato(unmarked);
														m.setDtAnnullamento(dataEsecuzione);
														m.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														
														updateManutenzione(m);
													}
												}
											}
										}
										break;
									case "testcase":
										pk = query
												.from(testcase)
												.where(testcase.dmalmProjectFk02.eq(p.getDmalmProjectPk()))
												.list(testcase.dmalmTestcasePk);
										if (pk.size() > 0) {
											for (Integer i : pk) {

												DmalmTestcase t = TestCaseDAO
														.getTestCase(i);
												if (t != null) {
													if (t.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														t.setAnnullato(unmarked);
														t.setDtAnnullamento(dataEsecuzione);
														t.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														updateTestCase(t);
													}
												}
											}
										}
										break;
									case "task":
										pk = query
												.from(task)
												.where(task.dmalmProjectFk02.eq(p.getDmalmProjectPk()))
												.list(task.dmalmTaskPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {

												DmalmTask t = TaskDAO
														.getTask(i);
												if (t != null) {
													if (t.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														t.setAnnullato(unmarked);
														t.setDtAnnullamento(dataEsecuzione);
														t.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														
														updateTask(t);
													}
												}
											}
										}
										break;
									case "release":
										pk = query
												.from(releaseDiProgetto)
												.where(releaseDiProgetto.dmalmProjectFk02.eq(p.getDmalmProjectPk()))
												.list(releaseDiProgetto.dmalmReleasediprogPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {

												DmalmReleaseDiProgetto r = ReleaseDiProgettoDAO
														.getReleaseDiProgetto(i);
												if (r != null) {
													if (r.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														r.setAnnullato(unmarked);
														r.setDtAnnullamento(dataEsecuzione);
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														
														updateReleaseDiProgetto(r);
													}
												}
											}
										}
										break;
									case "programma":
										pk = query
												.from(programma)
												.where(programma.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(programma.dmalmProgrammaPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {

												DmalmProgramma d = ProgrammaDAO
														.getProgramma(i);
												if (d != null) {
													if (d.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														d.setAnnullato(unmarked);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														
														updateProgramma(d);
													}
												}
											}
										}
										break;
									case "sottoprogramma":
										pk = query
												.from(sottoprogramma)
												.where(sottoprogramma.dmalmProjectFk02.eq(p.getDmalmProjectPk()))
												.list(sottoprogramma.dmalmSottoprogrammaPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {

												DmalmSottoprogramma s = SottoprogrammaDAO
														.getSottoprogramma(i);
												if (s != null) {
													if (s.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														s.setAnnullato(unmarked);
														s.setDtAnnullamento(dataEsecuzione);
														s.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														
														updateSottoprogramma(s);
													}
												}
											}
										}
										break;
									case "rqd":
										pk = query
												.from(progettoDemand)
												.where(progettoDemand.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(progettoDemand.dmalmProgettoDemandPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmProgettoDemand d = ProgettoDemandDAO
														.getProgettoDemand(i);
												if (d != null) {
													if (d.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														d.setAnnullato(unmarked);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());

														updateProgettoDemand(d);
													}
												}
											}
										}
										break;
									case "drqs":
										pk = query
												.from(progettoSviluppoDem)
												.where(progettoSviluppoDem.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(progettoSviluppoDem.dmalmProgSvilDPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmProgettoSviluppoDem d = ProgettoSviluppoDemandDAO
														.getProgettoSviluppoDemand(i);
												if (d != null) {
													if (d.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														d.setAnnullato(unmarked);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());

														updateProgettoSviluppoDem(d);
													}
												}
											}
										}
										break;
									case "dman":
										pk = query
												.from(richiestaManutenzione)
												.where(richiestaManutenzione.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(richiestaManutenzione.dmalmRichManutenzionePk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmRichiestaManutenzione r = RichiestaManutenzioneDAO
														.getRichiestaManutenzione(i);
												if (r != null) {
													if (r.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														r.setAnnullato(unmarked);
														r.setDtAnnullamento(dataEsecuzione);
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());

														updateRichiestaManutenzione(r);
													}
												}
											}
										}
										break;
									case "fase":
										pk = query
												.from(fase)
												.where(fase.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(fase.dmalmFasePk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmFase f = FaseDAO
														.getFase(i);
												if (f != null) {
													if (f.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														f.setAnnullato(unmarked);
														f.setDtAnnullamento(dataEsecuzione);
														f.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														
														updateFase(f);
													}
												}
											}
										}
										break;
									case "pei":
										pk = query
												.from(pei)
												.where(pei.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(pei.dmalmPeiPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmPei f = PeiDAO.getPei(i);
												if (f != null) {
													if (f.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														f.setAnnullato(unmarked);
														f.setDtAnnullamento(dataEsecuzione);
														f.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														
														updatePei(f);
													}
												}

											}
										}
										break;
									case "progettoese":
										pk = query
												.from(progettoEse)
												.where(progettoEse.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(progettoEse.dmalmProgettoEsePk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmProgettoEse d = ProgettoEseDAO
														.getProgettoEse(i);
												if (d != null) {
													if (d.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														d.setAnnullato(unmarked);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());

														updateProgettoEse(d);
													}
												}
											}
										}
										break;
									case "release_it":
										pk = query
												.from(releaseIT)
												.where(releaseIT.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(releaseIT.dmalmReleaseItPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmReleaseIt r = ReleaseItDAO
														.getReleaseIt(i);
												if (r != null) {
													if (r.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														r.setAnnullato(unmarked);
														r.setDtAnnullamento(dataEsecuzione);
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());

														updateReleaseIt(r);
													}
												}
											}
										}
										break;
									case "build":
										pk = query
												.from(build)
												.where(build.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(build.dmalmBuildPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmBuild b = BuildDAO
														.getBuild(i);
												if (b != null) {
													if (b.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														b.setAnnullato(unmarked);
														b.setDtAnnullamento(dataEsecuzione);
														b.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														
														updateBuild(b);
													}												}
											}
										}
										break;
									case "taskit":
										pk = query
												.from(taskIT)
												.where(taskIT.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(taskIT.dmalmTaskItPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmTaskIt t = TaskItDAO
														.getTaskIt(i);
												if (t != null) {
													if (t.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														t.setAnnullato(unmarked);
														t.setDtAnnullamento(dataEsecuzione);
														t.setDmalmProjectFk02(p
																.getDmalmProjectPk());

														updateTaskIt(t);
													}
												}
											}
										}
										break;
									case "richiesta_gestione":
										pk = query
												.from(richiestaGestione)
												.where(richiestaGestione.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(richiestaGestione.dmalmRichiestaGestPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmRichiestaGestione r = RichiestaGestioneDAO
														.getRichiestaGestione(i);
												if (r != null) {
													if (r.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														r.setAnnullato(unmarked);
														r.setDtAnnullamento(dataEsecuzione);
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														
														updateRichiestaGestione(r);
													}
												}
											}
										}
										break;
									case "anomalia_assistenza":
										pk = query
												.from(anomaliaAssistenza)
												.where(anomaliaAssistenza.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(anomaliaAssistenza.dmalmAnomaliaAssPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmAnomaliaAssistenza a = AnomaliaAssistenzaDAO
														.getAnomaliaAssistenza(i);
												if (a != null) {
													if (a.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														a.setAnnullato(unmarked);
														a.setDtAnnullamento(dataEsecuzione);
														a.setDmalmProjectFk02(p
																.getDmalmProjectPk());

														updateAnomaliaAssistenza(a);
													}												}
											}
										}
										break;
									case "release_ser":
										pk = query
												.from(releaseServizi)
												.where(releaseServizi.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(releaseServizi.dmalmRelServiziPk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmReleaseServizi r = ReleaseServiziDAO
														.getReleaseServizi(i);
												if (r != null) {
													if (r.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														r.setAnnullato(unmarked);
														r.setDtAnnullamento(dataEsecuzione);
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());

														updateReleaseServizi(r);
													}
												}
											}
										}
										break;

									case "classificatore_demand":
										pk = query
												.from(classificatore)
												.where(classificatore.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
												.list(classificatore.dmalmClassificatorePk);
										if (pk.size() > 0) {
											for (Integer i : pk) {
												DmalmClassificatore c = ClassificatoreDAO
														.getClassificatore(i);
												if (c != null) {
													if (c.getDtStoricizzazione()
															.equals(dataEsecuzione)) {
														// già storicizzato oggi
														c.setAnnullato(unmarked);
														c.setDtAnnullamento(dataEsecuzione);
														c.setDmalmProjectFk02(p
																.getDmalmProjectPk());

														updateClass(c);
													}												}
											}
										}
										break;
										
									case "sup":
										List<DmalmRichiestaSupporto> richieste = RichiestaSupportoDAO
												.getRichiestaSupporto(p.getDmalmProjectPk(), 0);
										for (DmalmRichiestaSupporto richiesta : richieste) {
											DmalmRichiestaSupporto r = RichiestaSupportoDAO
													.getRichiestaSupporto(richiesta.getDmalmRichiestaSupportoPk());
											if (r != null) {
												if (r.getDataStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													r.setAnnullato(unmarked);
													r.setDataAnnullamento(dataEsecuzione);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
//													RichiestaSupportoDAO.updateRichiestaSupporto(r);
												}
											}
										}
										break;
										
									case "rfc":
										List<DmalmRfc> rfcs = RfcDAO.getRfc(p.getDmalmProjectPk(), 0);
										for (DmalmRfc rfc : rfcs) {
											DmalmRfc r = RfcDAO.getRfc(rfc.getDmalmRfcPk());
											if (r != null) {
												if (r.getDataStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													r.setAnnullato(unmarked);
													r.setDataAnnullamento(dataEsecuzione);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
//													RfcDAO.updateRfc(r);
												}
											}
										}
										break;

									}

								} catch (Exception e) {
									ErrorManager.getInstance()
											.exceptionOccurred(true, e);

								} finally {
									if (cm != null)
										cm.closeConnection(conn);
								}
						}
					}
				}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
	}
	
	private static void updateDmalmAnomaliaProdotto(DmalmAnomaliaProdotto anomalia) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		QDmalmAnomaliaProdotto anomaliaProdotto = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
		SQLTemplates dialect = new HSQLDBTemplates();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			new SQLUpdateClause(connection, dialect, anomaliaProdotto)

					.where(anomaliaProdotto.dmalmAnomaliaProdottoPk
							.eq(anomalia.getDmalmAnomaliaProdottoPk()))

					.set(anomaliaProdotto.dtRisoluzioneAnomalia,
							anomalia.getDtRisoluzioneAnomalia())
					.set(anomaliaProdotto.dmalmProjectFk02,
							anomalia.getDmalmProjectFk02())
					.set(anomaliaProdotto.dmalmStrutturaOrgFk01,
							anomalia.getDmalmStrutturaOrgFk01())
					.set(anomaliaProdotto.dmalmAreaTematicaFk05,
							anomalia.getDmalmAreaTematicaFk05())
					.set(anomaliaProdotto.dtModificaRecordAnomalia,
							anomalia.getDtModificaRecordAnomalia())
					.set(anomaliaProdotto.motivoRisoluzioneAnomalia,
							anomalia.getMotivoRisoluzioneAnomalia())
					.set(anomaliaProdotto.severity, anomalia.getSeverity())
					.set(anomaliaProdotto.dtChiusuraAnomalia,
							anomalia.getDtChiusuraAnomalia())
					.set(anomaliaProdotto.effortCostoSviluppo,
							anomalia.getEffortCostoSviluppo())
					.set(anomaliaProdotto.idAutoreAnomalia,
							anomalia.getIdAutoreAnomalia())
					.set(anomaliaProdotto.dsAutoreAnomalia,
							anomalia.getDsAutoreAnomalia())
					.set(anomaliaProdotto.dsAnomalia, anomalia.getDsAnomalia())
					.set(anomaliaProdotto.ticketSiebelAnomaliaAss,
							anomalia.getTicketSiebelAnomaliaAss())
					.set(anomaliaProdotto.dtChiusuraTicket,
							anomalia.getDtChiusuraTicket())
					.set(anomaliaProdotto.stgPk, anomalia.getStgPk())
					.set(anomaliaProdotto.uri, anomalia.getUri())
					.set(anomaliaProdotto.dtAnnullamento,
							anomalia.getDtAnnullamento())
					.set(anomaliaProdotto.contestazione,
							anomalia.getContestazione())
					.set(anomaliaProdotto.noteContestazione,
							anomalia.getNoteContestazione())
					.set(anomaliaProdotto.annullato, anomalia.getAnnullato())
					.set(anomaliaProdotto.dataDisponibilita, anomalia.getDtDisponibilita())
					// DM_ALM-320
					.set(anomaliaProdotto.priority, anomalia.getPriority())
					.set(anomaliaProdotto.tagAlm, anomalia.getTagAlm())
					.set(anomaliaProdotto.tsTagAlm, anomalia.getTsTagAlm())
					.execute();

			// DM_ALM-142
			// .set(anomaliaProdotto.idAnomaliaAssistenza,
			// anomalia.getIdAnomaliaAssistenza())
			// .set(anomaliaProdotto.dtAperturaTicket,
			// anomalia.getDtAperturaTicket())

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
	
	private static void updateDocumento(DmalmDocumento documento) throws DAOException {

		QDmalmDocumento doc = QDmalmDocumento.dmalmDocumento;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, doc)

					.where(doc.dmalmDocumentoPk
						.eq(documento.getDmalmDocumentoPk()))
					
					.set(doc.assigneesDocumento,
							documento.getAssigneesDocumento())
					.set(doc.cdDocumento, documento.getCdDocumento())
					.set(doc.classificazione, documento.getClassificazione())
					.set(doc.codice, documento.getCodice())
					.set(doc.descrizioneDocumento,
							documento.getDescrizioneDocumento())
					.set(doc.dmalmAreaTematicaFk05,
							documento.getDmalmAreaTematicaFk05())
					.set(doc.dmalmProjectFk02, documento.getDmalmProjectFk02())
					.set(doc.dmalmStatoWorkitemFk03,
							documento.getDmalmStatoWorkitemFk03())
					.set(doc.dmalmStrutturaOrgFk01,
							documento.getDmalmStrutturaOrgFk01())
					.set(doc.dmalmTempoFk04, documento.getDmalmTempoFk04())
					.set(doc.dsAutoreDocumento,
							documento.getDsAutoreDocumento())
					.set(doc.dtCreazioneDocumento,
							documento.getDtCreazioneDocumento())
					.set(doc.dtModificaDocumento,
							documento.getDtModificaDocumento())
					.set(doc.dtRisoluzioneDocumento,
							documento.getDtRisoluzioneDocumento())
					.set(doc.dtScadenzaDocumento,
							documento.getDtScadenzaDocumento())
					.set(doc.idAutoreDocumento,
							documento.getIdAutoreDocumento())
					.set(doc.idRepository, documento.getIdRepository())
					.set(doc.motivoRisoluzioneDocumento,
							documento.getMotivoRisoluzioneDocumento())
					.set(doc.numeroLinea, documento.getNumeroLinea())
					.set(doc.numeroTestata, documento.getNumeroTestata())
					.set(doc.tipo, documento.getTipo())
					.set(doc.titoloDocumento, documento.getTitoloDocumento())
					.set(doc.versione, documento.getVersione())
					.set(doc.stgPk, documento.getStgPk())
					.set(doc.uri, documento.getUri())
					.set(doc.dtAnnullamento, documento.getDtAnnullamento())
					.set(doc.annullato, documento.getAnnullato())
					// DM_ALM-320
					.set(doc.severity, documento.getSeverity())
					.set(doc.priority, documento.getPriority())
					.set(doc.tagAlm, documento.getTagAlm())
					.set(doc.tsTagAlm, documento.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateDmalmDifettoProdotto(DmalmDifettoProdotto difetto)	throws DAOException {

		QDmalmDifettoProdotto difettoProdotto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, difettoProdotto)

					.where(difettoProdotto.dmalmDifettoProdottoPk.eq(difetto
							.getDmalmDifettoProdottoPk()))

					.set(difettoProdotto.dtRisoluzioneDifetto,
							difetto.getDtRisoluzioneDifetto())
					.set(difettoProdotto.dtModificaRecordDifetto,
							difetto.getDtModificaRecordDifetto())
					.set(difettoProdotto.dtChiusuraDifetto,
							difetto.getDtChiusuraDifetto())
					.set(difettoProdotto.dtCreazioneDifetto,
							difetto.getDtCreazioneDifetto())
					.set(difettoProdotto.tempoTotRisoluzioneDifetto,
							difetto.getTempoTotRisoluzioneDifetto())
					.set(difettoProdotto.dmalmProjectFk02,
							difetto.getDmalmProjectFk02())
					.set(difettoProdotto.dmalmStrutturaOrgFk01,
							difetto.getDmalmStrutturaOrgFk01())
					.set(difettoProdotto.dmalmStatoWorkitemFk03,
							difetto.getDmalmStatoWorkitemFk03())
					.set(difettoProdotto.idkAreaTematica,
							difetto.getIdkAreaTematica())
					.set(difettoProdotto.idAutoreDifetto,
							difetto.getIdAutoreDifetto())
					.set(difettoProdotto.dsAutoreDifetto,
							difetto.getDsAutoreDifetto())
					.set(difettoProdotto.motivoRisoluzioneDifetto,
							difetto.getMotivoRisoluzioneDifetto())
					.set(difettoProdotto.severity, difetto.getSeverity())
					.set(difettoProdotto.dsDifetto, difetto.getDsDifetto())
					.set(difettoProdotto.provenienzaDifetto,difetto.getProvenienzaDifetto())
					.set(difettoProdotto.causaDifetto,
							difetto.getCausaDifetto())
					.set(difettoProdotto.naturaDifetto,
							difetto.getNaturaDifetto())
					.set(difettoProdotto.stgPk, difetto.getStgPk())
					.set(difettoProdotto.uri, difetto.getUri())
					.set(difettoProdotto.effortCostoSviluppo,
							difetto.getEffortCostoSviluppo())
					.set(difettoProdotto.dtAnnullamento,
							difetto.getDtAnnullamento())
					.set(difettoProdotto.annullato, difetto.getAnnullato())
					.set(difettoProdotto.dataDisponibilita, difetto.getDtDisponibilita())
					//DM_ALM-320
					.set(difettoProdotto.priority, difetto.getPriority())
					.set(difettoProdotto.tagAlm, difetto.getTagAlm())
					.set(difettoProdotto.tsTagAlm, difetto.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
	
	private static void updateProgettoSviluppoSvil(
			DmalmProgettoSviluppoSvil progettoSviluppo) throws DAOException {

		QDmalmProgettoSviluppoSvil progetto = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progetto)

					.where(progetto.dmalmProgSvilSPk
							.eq(progettoSviluppo.getDmalmProgSvilSPk()))

					.set(progetto.codice, progettoSviluppo.getCodice())
					.set(progetto.dataChiusuraProgSvilS,
							progettoSviluppo.getDataChiusuraProgSvilS())
					.set(progetto.dataDisponibilitaEffettiva,
							progettoSviluppo.getDataDisponibilitaEffettiva())
					.set(progetto.dataDisponibilitaPianificata,
							progettoSviluppo.getDataDisponibilitaPianificata())
					.set(progetto.dataInizio, progettoSviluppo.getDataInizio())
					.set(progetto.dataInizioEff,
							progettoSviluppo.getDataInizioEff())
					.set(progetto.descrizioneProgSvilS,
							progettoSviluppo.getDescrizioneProgSvilS())
					.set(progetto.dmalmAreaTematicaFk05,
							progettoSviluppo.getDmalmAreaTematicaFk05())
					.set(progetto.dmalmProjectFk02,
							progettoSviluppo.getDmalmProjectFk02())
					.set(progetto.dmalmStatoWorkitemFk03,
							progettoSviluppo.getDmalmStatoWorkitemFk03())
					.set(progetto.dmalmStrutturaOrgFk01,
							progettoSviluppo.getDmalmStrutturaOrgFk01())
					.set(progetto.dmalmTempoFk04,
							progettoSviluppo.getDmalmTempoFk04())
					.set(progetto.dsAutoreProgSvilS,
							progettoSviluppo.getDsAutoreProgSvilS())
					.set(progetto.dtCaricamentoProgSvilS,
							progettoSviluppo.getDtCaricamentoProgSvilS())
					.set(progetto.dtCreazioneProgSvilS,
							progettoSviluppo.getDtCreazioneProgSvilS())
					.set(progetto.dtModificaProgSvilS,
							progettoSviluppo.getDtModificaProgSvilS())
					.set(progetto.dtRisoluzioneProgSvilS,
							progettoSviluppo.getDtRisoluzioneProgSvilS())
					.set(progetto.dtScadenzaProgSvilS,
							progettoSviluppo.getDtScadenzaProgSvilS())
					.set(progetto.durataEffettivaProgSvilS,
							progettoSviluppo.getDurataEffettivaProgSvilS())
					.set(progetto.fornitura, progettoSviluppo.getFornitura())
					.set(progetto.idAutoreProgSvilS,
							progettoSviluppo.getIdAutoreProgSvilS())
					.set(progetto.idRepository,
							progettoSviluppo.getIdRepository())
					.set(progetto.motivoRisoluzioneProgSvilS,
							progettoSviluppo.getMotivoRisoluzioneProgSvilS())
					.set(progetto.numeroLinea,
							progettoSviluppo.getNumeroLinea())
					.set(progetto.numeroTestata,
							progettoSviluppo.getNumeroTestata())
					.set(progetto.titoloProgSvilS,
							progettoSviluppo.getTitoloProgSvilS())
					.set(progetto.stgPk, progettoSviluppo.getStgPk())
					.set(progetto.uri, progettoSviluppo.getUri())
					.set(progetto.dtAnnullamento,
							progettoSviluppo.getDtAnnullamento())
					.set(progetto.annullato, progettoSviluppo.getAnnullato())
					//DM_ALM-320
					.set(progetto.severity, progettoSviluppo.getSeverity())
					.set(progetto.priority, progettoSviluppo.getPriority())
					.set(progetto.tagAlm, progettoSviluppo.getTagAlm())
					.set(progetto.tsTagAlm, progettoSviluppo.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateManutenzione(DmalmManutenzione manutenzione)
			throws DAOException {

		QDmalmManutenzione man = QDmalmManutenzione.dmalmManutenzione;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, man)

					.where(man.dmalmManutenzionePk.eq(manutenzione
							.getDmalmManutenzionePk()))

					.set(man.assigneesManutenzione,
							manutenzione.getAssigneesManutenzione())
					.set(man.cdManutenzione, manutenzione.getCdManutenzione())
					.set(man.codice, manutenzione.getCodice())
					.set(man.dataDispEff, manutenzione.getDataDispEff())
					.set(man.dataDisponibilita,
							manutenzione.getDataDisponibilita())
					.set(man.dataInizio, manutenzione.getDataInizio())
					.set(man.dataInizioEff, manutenzione.getDataInizioEff())
					.set(man.dataRilascioInEs,
							manutenzione.getDataRilascioInEs())
					.set(man.descrizioneManutenzione,
							manutenzione.getDescrizioneManutenzione())
					.set(man.dmalmAreaTematicaFk05,
							manutenzione.getDmalmAreaTematicaFk05())
					.set(man.dmalmProjectFk02,
							manutenzione.getDmalmProjectFk02())
					.set(man.dmalmStatoWorkitemFk03,
							manutenzione.getDmalmStatoWorkitemFk03())
					.set(man.dmalmStrutturaOrgFk01,
							manutenzione.getDmalmStrutturaOrgFk01())
					.set(man.dmalmTempoFk04, manutenzione.getDmalmTempoFk04())
					.set(man.dsAutoreManutenzione,
							manutenzione.getDsAutoreManutenzione())
					.set(man.dtCreazioneManutenzione,
							manutenzione.getDtCreazioneManutenzione())
					.set(man.dtModificaManutenzione,
							manutenzione.getDtModificaManutenzione())
					.set(man.dtRisoluzioneManutenzione,
							manutenzione.getDtRisoluzioneManutenzione())
					.set(man.dtScadenzaManutenzione,
							manutenzione.getDtScadenzaManutenzione())
					.set(man.fornitura, manutenzione.getFornitura())
					.set(man.idAutoreManutenzione,
							manutenzione.getIdAutoreManutenzione())
					.set(man.idRepository, manutenzione.getIdRepository())
					.set(man.motivoRisoluzioneManutenzion,
							manutenzione.getMotivoRisoluzioneManutenzion())
					.set(man.numeroLinea, manutenzione.getNumeroLinea())
					.set(man.numeroTestata, manutenzione.getNumeroTestata())
					.set(man.priorityManutenzione,
							manutenzione.getPriorityManutenzione())
					.set(man.severityManutenzione,
							manutenzione.getSeverityManutenzione())
					.set(man.tempoTotaleRisoluzione,
							manutenzione.getTempoTotaleRisoluzione())
					.set(man.titoloManutenzione,
							manutenzione.getTitoloManutenzione())
					.set(man.stgPk, manutenzione.getStgPk())
					.set(man.uri, manutenzione.getUri())
					.set(man.dtAnnullamento, manutenzione.getDtAnnullamento())
					.set(man.annullato, manutenzione.getAnnullato())
					.set(man.tagAlm, manutenzione.getTagAlm())
					.set(man.tsTagAlm, manutenzione.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateTestCase(DmalmTestcase testcase)
			throws DAOException {

		QDmalmTestcase tstcs = QDmalmTestcase.dmalmTestcase;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, tstcs)

					.where(tstcs.dmalmTestcasePk.eq(testcase
							.getDmalmTestcasePk()))

					.set(tstcs.cdTestcase, testcase.getCdTestcase())
					.set(tstcs.dataEsecuzioneTestcase,
							testcase.getDataEsecuzioneTestcase())
					.set(tstcs.codice, testcase.getCodice())
					.set(tstcs.descrizioneTestcase,
							testcase.getDescrizioneTestcase())
					.set(tstcs.dmalmAreaTematicaFk05,
							testcase.getDmalmAreaTematicaFk05())
					.set(tstcs.dmalmProjectFk02, testcase.getDmalmProjectFk02())
					.set(tstcs.dmalmStatoWorkitemFk03,
							testcase.getDmalmStatoWorkitemFk03())
					.set(tstcs.dmalmStrutturaOrgFk01,
							testcase.getDmalmStrutturaOrgFk01())
					.set(tstcs.dmalmTempoFk04, testcase.getDmalmTempoFk04())
					.set(tstcs.dsAutoreTestcase, testcase.getDsAutoreTestcase())
					.set(tstcs.dtCreazioneTestcase,
							testcase.getDtCreazioneTestcase())
					.set(tstcs.dtModificaTestcase,
							testcase.getDtModificaTestcase())
					.set(tstcs.dtRisoluzioneTestcase,
							testcase.getDtRisoluzioneTestcase())
					.set(tstcs.dtScadenzaTestcase,
							testcase.getDtScadenzaTestcase())
					.set(tstcs.dtStoricizzazione,
							testcase.getDtStoricizzazione())
					.set(tstcs.idAutoreTestcase, testcase.getIdAutoreTestcase())
					.set(tstcs.idRepository, testcase.getIdRepository())
					.set(tstcs.rankStatoTestcase, new Double(1))
					.set(tstcs.motivoRisoluzioneTestcase,
							testcase.getMotivoRisoluzioneTestcase())
					.set(tstcs.numeroLinea, testcase.getNumeroLinea())
					.set(tstcs.numeroTestata, testcase.getNumeroTestata())
					.set(tstcs.titoloTestcase, testcase.getTitoloTestcase())
					.set(tstcs.stgPk, testcase.getStgPk())
					.set(tstcs.uri, testcase.getUri())
					.set(tstcs.dtAnnullamento, testcase.getDtAnnullamento())
					.set(tstcs.annullato, testcase.getAnnullato())
					//DM_ALM-320
					.set(tstcs.severity, testcase.getSeverity())
					.set(tstcs.priority, testcase.getPriority())
					.set(tstcs.tagAlm, testcase.getTagAlm())
					.set(tstcs.tsTagAlm, testcase.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}
	
	private static void updateTask(DmalmTask task) throws DAOException {

		QDmalmTask tsk = QDmalmTask.dmalmTask;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, tsk)

					.where(tsk.dmalmTaskPk.eq(task.getDmalmTaskPk()))

					.set(tsk.cdTask, task.getCdTask())
					.set(tsk.codice, task.getCodice())
					.set(tsk.dataChiusuraTask, task.getDataChiusuraTask())
					.set(tsk.dataFineEffettiva, task.getDataFineEffettiva())
					.set(tsk.dataFinePianificata, task.getDataFinePianificata())
					.set(tsk.dataInizioEffettivo, task.getDataInizioEffettivo())
					.set(tsk.dataInizioPianificato,
							task.getDataInizioPianificato())
					.set(tsk.descrizioneTask, task.getDescrizioneTask())
					.set(tsk.dmalmAreaTematicaFk05,
							task.getDmalmAreaTematicaFk05())
					.set(tsk.dmalmProjectFk02, task.getDmalmProjectFk02())
					.set(tsk.dmalmStatoWorkitemFk03,
							task.getDmalmStatoWorkitemFk03())
					.set(tsk.dmalmStrutturaOrgFk01,
							task.getDmalmStrutturaOrgFk01())
					.set(tsk.dmalmTempoFk04, task.getDmalmTempoFk04())
					.set(tsk.dsAutoreTask, task.getDsAutoreTask())
					.set(tsk.dtCreazioneTask, task.getDtCreazioneTask())
					.set(tsk.dtModificaTask, task.getDtModificaTask())
					.set(tsk.dtRisoluzioneTask, task.getDtRisoluzioneTask())
					.set(tsk.dtScadenzaTask, task.getDtScadenzaTask())
					.set(tsk.idAutoreTask, task.getIdAutoreTask())
					.set(tsk.idRepository, task.getIdRepository())
					.set(tsk.initialCost, task.getInitialCost())
					.set(tsk.motivoRisoluzioneTask,
							task.getMotivoRisoluzioneTask())
					.set(tsk.numeroLinea, task.getNumeroLinea())
					.set(tsk.numeroTestata, task.getNumeroTestata())
					.set(tsk.priorityTask, task.getPriorityTask())
					.set(tsk.severityTask, task.getSeverityTask())
					.set(tsk.taskType, task.getTaskType())
					.set(tsk.tempoTotaleRisoluzione,
							task.getTempoTotaleRisoluzione())
					.set(tsk.titoloTask, task.getTitoloTask())
					.set(tsk.stgPk, task.getStgPk())
					.set(tsk.uri, task.getUri())
					.set(tsk.dtAnnullamento, task.getDtAnnullamento())
					.set(tsk.annullato, task.getAnnullato())
					.set(tsk.tagAlm, task.getTagAlm())
					.set(tsk.tsTagAlm, task.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateReleaseDiProgetto(DmalmReleaseDiProgetto release)
			throws DAOException {

		QDmalmReleaseDiProgetto rls = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, rls)

					.where(rls.dmalmReleasediprogPk.eq(release
							.getDmalmReleasediprogPk()))
					
					.set(rls.cdReleasediprog, release.getCdReleasediprog())
					.set(rls.codice, release.getCodice())
					.set(rls.dataDisponibilitaEff,
							release.getDataDisponibilitaEff())
					.set(rls.dataPassaggioInEsercizio,
							release.getDataPassaggioInEsercizio())
					.set(rls.descrizioneReleasediprog,
							release.getDescrizioneReleasediprog())
					.set(rls.dmalmProjectFk02, release.getDmalmProjectFk02())
					.set(rls.dmalmStatoWorkitemFk03,
							release.getDmalmStatoWorkitemFk03())
					.set(rls.dmalmStrutturaOrgFk01,
							release.getDmalmStrutturaOrgFk01())
					.set(rls.dmalmTempoFk04, release.getDmalmTempoFk04())
					.set(rls.dmalmAreaTematicaFk05,
							release.getDmalmAreaTematicaFk05())
					.set(rls.dsAutoreReleasediprog,
							release.getDsAutoreReleasediprog())
					.set(rls.dtCreazioneReleasediprog,
							release.getDtCreazioneReleasediprog())
					.set(rls.dtModificaReleasediprog,
							release.getDtModificaReleasediprog())
					.set(rls.dtRisoluzioneReleasediprog,
							release.getDtRisoluzioneReleasediprog())
					.set(rls.dtScadenzaReleasediprog,
							release.getDtScadenzaReleasediprog())
					.set(rls.fornitura, release.getFornitura())
					.set(rls.fr, release.getFr())
					.set(rls.idAutoreReleasediprog,
							release.getIdAutoreReleasediprog())
					.set(rls.idRepository, release.getIdRepository())
					.set(rls.motivoRisoluzioneRelProg,
							release.getMotivoRisoluzioneRelProg())
					.set(rls.numeroLinea, release.getNumeroLinea())
					.set(rls.numeroTestata, release.getNumeroTestata())
					.set(rls.titoloReleasediprog,
							release.getTitoloReleasediprog())
					.set(rls.versione, release.getVersione())
					.set(rls.stgPk, release.getStgPk())
					.set(rls.uri, release.getUri())
					.set(rls.dtAnnullamento, release.getDtAnnullamento())
					.set(rls.dtInizioQF, release.getDtInizioQF())
					.set(rls.dtFineQF, release.getDtFineQF())
					.set(rls.numQuickFix, release.getNumQuickFix())
					.set(rls.annullato, release.getAnnullato())
					//DM_ALM-320
					.set(rls.severity, release.getSeverity())
					.set(rls.priority, release.getPriority())
					.set(rls.typeRelease, release.getTypeRelease())
					.set(rls.tagAlm, release.getTagAlm())
					.set(rls.tsTagAlm, release.getTsTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateProgramma(DmalmProgramma program)
			throws DAOException {
		
		QDmalmProgramma prog = QDmalmProgramma.dmalmProgramma;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, prog)
					.where(prog.dmalmProgrammaPk.eq(program
							.getDmalmProgrammaPk()))
					
					.set(prog.cdProgramma, program.getCdProgramma())
					.set(prog.descrizioneProgramma,
							program.getDescrizioneProgramma())
					.set(prog.dmalmProjectFk02, program.getDmalmProjectFk02())
					.set(prog.dmalmStatoWorkitemFk03,
							program.getDmalmStatoWorkitemFk03())
					.set(prog.dmalmStrutturaOrgFk01,
							program.getDmalmStrutturaOrgFk01())
					.set(prog.dmalmTempoFk04, program.getDmalmTempoFk04())
					.set(prog.dsAutoreProgramma, program.getDsAutoreProgramma())
					.set(prog.dtCreazioneProgramma,
							program.getDtCreazioneProgramma())
					.set(prog.dtModificaProgramma,
							program.getDtModificaProgramma())
					.set(prog.dtRisoluzioneProgramma,
							program.getDtRisoluzioneProgramma())
					.set(prog.dtScadenzaProgramma,
							program.getDtScadenzaProgramma())
					.set(prog.idAutoreProgramma, program.getIdAutoreProgramma())
					.set(prog.idRepository, program.getIdRepository())
					.set(prog.motivoRisoluzioneProgramma,
							program.getMotivoRisoluzioneProgramma())
					.set(prog.numeroLinea, program.getNumeroLinea())
					.set(prog.numeroTestata, program.getNumeroTestata())
					.set(prog.titoloProgramma, program.getTitoloProgramma())
					.set(prog.cfContratto, program.getCfContratto())
					.set(prog.cfReferenteRegionale,
							program.getCfReferenteRegionale())
					.set(prog.cfServiceManager, program.getCfServiceManager())
					.set(prog.cfTipologia, program.getCfTipologia())
					.set(prog.stgPk, program.getStgPk())
					.set(prog.uri, program.getUri())
					.set(prog.codice, program.getCodice())
					.set(prog.dtAnnullamento, program.getDtAnnullamento())
					.set(prog.annullato, program.getAnnullato())
					.set(prog.severity, program.getSeverity())
					//DM_ALM-320
					.set(prog.priority, program.getPriority())
					.set(prog.tagAlm, program.getTagAlm())
					.set(prog.tsTagAlm, program.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateSottoprogramma(DmalmSottoprogramma subprogram)
			throws DAOException {
		
		QDmalmSottoprogramma sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, sottoprogramma)
					.where(sottoprogramma.dmalmSottoprogrammaPk
							.eq(subprogram.getDmalmSottoprogrammaPk()))
					
					.set(sottoprogramma.idRepository,
							subprogram.getIdRepository())
					.set(sottoprogramma.cdSottoprogramma,
							subprogram.getCdSottoprogramma())
					.set(sottoprogramma.dtCreazioneSottoprogramma,
							subprogram.getDtCreazioneSottoprogramma())
					.set(sottoprogramma.dmalmProjectFk02,
							subprogram.getDmalmProjectFk02())
					.set(sottoprogramma.dmalmStatoWorkitemFk03,
							subprogram.getDmalmStatoWorkitemFk03())
					.set(sottoprogramma.dtCambioStatoSottoprogramma,
							subprogram.getDtCambioStatoSottoprogramma())
					.set(sottoprogramma.dtScadenzaSottoprogramma,
							subprogram.getDtScadenzaSottoprogramma())
					.set(sottoprogramma.dtModificaSottoprogramma,
							subprogram.getDtModificaSottoprogramma())
					.set(sottoprogramma.idAutoreSottoprogramma,
							subprogram.getIdAutoreSottoprogramma())
					.set(sottoprogramma.dsAutoreSottoprogramma,
							subprogram.getDsAutoreSottoprogramma())
					.set(sottoprogramma.titoloSottoprogramma,
							subprogram.getTitoloSottoprogramma())
					.set(sottoprogramma.motivoRisoluzioneSottoprogr,
							subprogram.getMotivoRisoluzioneSottoprogr())
					.set(sottoprogramma.dtRisoluzioneSottoprogramma,
							subprogram.getDtRisoluzioneSottoprogramma())
					.set(sottoprogramma.descrizioneSottoprogramma,
							subprogram.getDescrizioneSottoprogramma())
					.set(sottoprogramma.codice, subprogram.getCodice())
					.set(sottoprogramma.numeroTestata,
							subprogram.getNumeroTestata())
					.set(sottoprogramma.numeroLinea,
							subprogram.getNumeroLinea())
					.set(sottoprogramma.dtCompletamento,
							subprogram.getDtCompletamento())
					.set(sottoprogramma.dmalmTempoFk04,
							subprogram.getDmalmTempoFk04())
					.set(sottoprogramma.stgPk, subprogram.getStgPk())
					.set(sottoprogramma.uri, subprogram.getUri())
					.set(sottoprogramma.dtAnnullamento,
							subprogram.getDtAnnullamento())
					.set(sottoprogramma.annullato, subprogram.getAnnullato())
					//DM_ALM-320
					.set(sottoprogramma.severity, subprogram.getSeverity())
					.set(sottoprogramma.priority, subprogram.getPriority())
					.set(sottoprogramma.tagAlm, subprogram.getTagAlm())
					.set(sottoprogramma.tsTagAlm, subprogram.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateProgettoDemand(DmalmProgettoDemand progetto)
			throws DAOException {
		
		QDmalmProgettoDemand progettoDemand = QDmalmProgettoDemand.dmalmProgettoDemand;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progettoDemand)
					.where(progettoDemand.dmalmProgettoDemandPk
							.eq(progetto.getDmalmProgettoDemandPk()))
					
					.set(progettoDemand.cdProgettoDemand,
							progetto.getCdProgettoDemand())
					.set(progettoDemand.descrizioneProgettoDemand,
							progetto.getDescrizioneProgettoDemand())
					.set(progettoDemand.dmalmProjectFk02,
							progetto.getDmalmProjectFk02())
					.set(progettoDemand.dmalmStatoWorkitemFk03,
							progetto.getDmalmStatoWorkitemFk03())
					.set(progettoDemand.dmalmStrutturaOrgFk01,
							progetto.getDmalmStrutturaOrgFk01())
					.set(progettoDemand.dmalmTempoFk04,
							progetto.getDmalmTempoFk04())
					.set(progettoDemand.dsAutoreProgettoDemand,
							progetto.getDsAutoreProgettoDemand())
					.set(progettoDemand.dtCreazioneProgettoDemand,
							progetto.getDtCreazioneProgettoDemand())
					.set(progettoDemand.dtModificaProgettoDemand,
							progetto.getDtModificaProgettoDemand())
					.set(progettoDemand.dtRisoluzioneProgettoDemand,
							progetto.getDtRisoluzioneProgettoDemand())
					.set(progettoDemand.dtScadenzaProgettoDemand,
							progetto.getDtScadenzaProgettoDemand())
					.set(progettoDemand.idAutoreProgettoDemand,
							progetto.getIdAutoreProgettoDemand())
					.set(progettoDemand.idRepository,
							progetto.getIdRepository())
					.set(progettoDemand.motivoRisoluzioneProgDem,
							progetto.getMotivoRisoluzioneProgDem())
					.set(progettoDemand.titoloProgettoDemand,
							progetto.getTitoloProgettoDemand())
					.set(progettoDemand.tempoTotaleRisoluzione,
							progetto.getTempoTotaleRisoluzione())
					.set(progettoDemand.dtChiusuraProgettoDemand,
							progetto.getDtChiusuraProgettoDemand())
					.set(progettoDemand.codice, progetto.getCodice())
					.set(progettoDemand.cfOwnerDemand,
							progetto.getCfOwnerDemand())
					.set(progettoDemand.cfDtEnunciazione,
							progetto.getCfDtEnunciazione())
					.set(progettoDemand.cfDtDisponibilitaEff,
							progetto.getCfDtDisponibilitaEff())
					.set(progettoDemand.cfDtDisponibilita,
							progetto.getCfDtDisponibilita())
					.set(progettoDemand.cfReferenteSviluppo,
							progetto.getCfReferenteSviluppo())
					.set(progettoDemand.cfReferenteEsercizio,
							progetto.getCfReferenteEsercizio())
					.set(progettoDemand.aoid, progetto.getAoid())
					.set(progettoDemand.fornitura, progetto.getFornitura())
					.set(progettoDemand.stgPk, progetto.getStgPk())
					.set(progettoDemand.uri, progetto.getUri())
					.set(progettoDemand.codObiettivoAziendale,
							progetto.getCodObiettivoAziendale())
					.set(progettoDemand.codObiettivoUtente,
							progetto.getCodObiettivoUtente())
					.set(progettoDemand.cfClassificazione,
							progetto.getCfClassificazione())
					.set(progettoDemand.dtAnnullamento,
							progetto.getDtAnnullamento())
					.set(progettoDemand.annullato, progetto.getAnnullato())
					//DM_ALM-320
					.set(progettoDemand.severity, progetto.getSeverity())
					.set(progettoDemand.priority, progetto.getPriority())
					.set(progettoDemand.tagAlm, progetto.getTagAlm())
					.set(progettoDemand.tsTagAlm, progetto.getTsTagAlm())
					.execute();
			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateProgettoSviluppoDem(
			DmalmProgettoSviluppoDem progettoSviluppoDem) throws DAOException {
		
		QDmalmProgettoSviluppoDem progetto = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progetto)
					.where(progetto.dmalmProgSvilDPk
							.eq(progettoSviluppoDem.getDmalmProgSvilDPk()))

					.set(progetto.cdProgSvilD,
							progettoSviluppoDem.getCdProgSvilD())
					.set(progetto.cfCodice, progettoSviluppoDem.getCfCodice())
					.set(progetto.cfDataDispEffettiva,
							progettoSviluppoDem.getCfDataDispEffettiva())
					.set(progetto.cfDataDispPianificata,
							progettoSviluppoDem.getCfDataDispPianificata())
					.set(progetto.cfDataInizio,
							progettoSviluppoDem.getCfDataInizio())
					.set(progetto.cfDataInizioEff,
							progettoSviluppoDem.getCfDataInizioEff())
					.set(progetto.cfFornitura,
							progettoSviluppoDem.getCfFornitura())
					.set(progetto.descrizioneProgSvilD,
							progettoSviluppoDem.getDescrizioneProgSvilD())
					.set(progetto.dmalmProjectFk02,
							progettoSviluppoDem.getDmalmProjectFk02())
					.set(progetto.dmalmStatoWorkitemFk03,
							progettoSviluppoDem.getDmalmStatoWorkitemFk03())
					.set(progetto.dmalmStrutturaOrgFk01,
							progettoSviluppoDem.getDmalmStrutturaOrgFk01())
					.set(progetto.dmalmTempoFk04,
							progettoSviluppoDem.getDmalmTempoFk04())
					.set(progetto.dsAutoreProgSvilD,
							progettoSviluppoDem.getDsAutoreProgSvilD())
					.set(progetto.dtCreazioneProgSvilD,
							progettoSviluppoDem.getDtCreazioneProgSvilD())
					.set(progetto.dtModificaProgSvilD,
							progettoSviluppoDem.getDtModificaProgSvilD())
					.set(progetto.dtPassaggioEsercizio,
							progettoSviluppoDem.getDtPassaggioEsercizio())
					.set(progetto.dtRisoluzioneProgSvilD,
							progettoSviluppoDem.getDtRisoluzioneProgSvilD())
					.set(progetto.dtScadenzaProgSvilD,
							progettoSviluppoDem.getDtScadenzaProgSvilD())
					.set(progetto.idAutoreProgSvilD,
							progettoSviluppoDem.getIdAutoreProgSvilD())
					.set(progetto.idRepository,
							progettoSviluppoDem.getIdRepository())
					.set(progetto.motivoRisoluzioneProgSvilD,
							progettoSviluppoDem.getMotivoRisoluzioneProgSvilD())
					.set(progetto.priorityProgettoSvilDemand,
							progettoSviluppoDem.getPriorityProgettoSvilDemand())
					.set(progetto.severityProgettoSvilDemand,
							progettoSviluppoDem.getSeverityProgettoSvilDemand())
					.set(progetto.tempoTotaleRisoluzione,
							progettoSviluppoDem.getTempoTotaleRisoluzione())
					.set(progetto.titoloProgSvilD,
							progettoSviluppoDem.getTitoloProgSvilD())
					.set(progetto.stgPk, progettoSviluppoDem.getStgPk())
					.set(progetto.uri, progettoSviluppoDem.getUri())
					.set(progetto.dtAnnullamento,
							progettoSviluppoDem.getDtAnnullamento())
					.set(progetto.annullato, progettoSviluppoDem.getAnnullato())
					.set(progetto.tagAlm, progettoSviluppoDem.getTagAlm())
					.set(progetto.tsTagAlm, progettoSviluppoDem.getTsTagAlm()).execute();

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateRichiestaManutenzione(
			DmalmRichiestaManutenzione richiesta) throws DAOException {

		QDmalmRichiestaManutenzione rch_man = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, rch_man)

					.where(rch_man.dmalmRichManutenzionePk
							.eq(richiesta.getDmalmRichManutenzionePk()))

					.set(rch_man.cdRichiestaManutenzione,
							richiesta.getCdRichiestaManutenzione())
					.set(rch_man.classeDiFornitura,
							richiesta.getClasseDiFornitura())
					.set(rch_man.codice, richiesta.getCodice())
					.set(rch_man.dataChiusuraRichManut,
							richiesta.getDataChiusuraRichManut())
					.set(rch_man.dataDispEffettiva,
							richiesta.getDataDispEffettiva())
					.set(rch_man.dataDispPianificata,
							richiesta.getDataDispPianificata())
					.set(rch_man.dataInizioEffettivo,
							richiesta.getDataInizioEffettivo())
					.set(rch_man.dataInizioPianificato,
							richiesta.getDataInizioPianificato())
					.set(rch_man.descrizioneRichManutenzione,
							richiesta.getDescrizioneRichManutenzione())
					.set(rch_man.dmalmProjectFk02,
							richiesta.getDmalmProjectFk02())
					.set(rch_man.dmalmStatoWorkitemFk03,
							richiesta.getDmalmStatoWorkitemFk03())
					.set(rch_man.dmalmStrutturaOrgFk01,
							richiesta.getDmalmStrutturaOrgFk01())
					.set(rch_man.dmalmTempoFk04, richiesta.getDmalmTempoFk04())
					.set(rch_man.dsAutoreRichManutenzione,
							richiesta.getDsAutoreRichManutenzione())
					.set(rch_man.dtCreazioneRichManutenzione,
							richiesta.getDtCreazioneRichManutenzione())
					.set(rch_man.dtModificaRichManutenzione,
							richiesta.getDtModificaRichManutenzione())
					.set(rch_man.dtRisoluzioneRichManut,
							richiesta.getDtRisoluzioneRichManut())
					.set(rch_man.dtScadenzaRichManutenzione,
							richiesta.getDtScadenzaRichManutenzione())
					.set(rch_man.durataEffettivaRichMan,
							richiesta.getDurataEffettivaRichMan())
					.set(rch_man.idAutoreRichManutenzione,
							richiesta.getIdAutoreRichManutenzione())
					.set(rch_man.idRepository, richiesta.getIdRepository())
					.set(rch_man.motivoRisoluzioneRichManut,
							richiesta.getMotivoRisoluzioneRichManut())
					.set(rch_man.titoloRichiestaManutenzione,
							richiesta.getTitoloRichiestaManutenzione())
					.set(rch_man.stgPk, richiesta.getStgPk())
					.set(rch_man.uri, richiesta.getUri())
					.set(rch_man.dtAnnullamento, richiesta.getDtAnnullamento())
					.set(rch_man.annullato, richiesta.getAnnullato())
					.set(rch_man.severity, richiesta.getSeverity())
					.set(rch_man.priority, richiesta.getPriority())
					.set(rch_man.tagAlm, richiesta.getTagAlm())
					.set(rch_man.tsTagAlm, richiesta.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateFase(DmalmFase fase) throws DAOException {

		QDmalmFase fs = QDmalmFase.dmalmFase;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, fs)

					.where(fs.dmalmFasePk.eq(fase.getDmalmFasePk()))

					.set(fs.applicabile, fase.getApplicabile())
					.set(fs.cdFase, fase.getCdFase())
					.set(fs.codice, fase.getCodice())
					.set(fs.dataFineBaseline, fase.getDataFineBaseline())
					.set(fs.dataFineEffettiva, fase.getDataFineEffettiva())
					.set(fs.dataFinePianificata, fase.getDataFinePianificata())
					.set(fs.dataInizioBaseline, fase.getDataInizioBaseline())
					.set(fs.dataInizioEffettivo, fase.getDataInizioEffettivo())
					.set(fs.dataInizioPianificato,
							fase.getDataInizioPianificato())
					.set(fs.dataPassaggioInEsecuzione,
							fase.getDataPassaggioInEsecuzione())
					.set(fs.descrizioneFase, fase.getDescrizioneFase())
					.set(fs.dmalmProjectFk02, fase.getDmalmProjectFk02())
					.set(fs.dmalmStatoWorkitemFk03,
							fase.getDmalmStatoWorkitemFk03())
					.set(fs.dmalmStrutturaOrgFk01,
							fase.getDmalmStrutturaOrgFk01())
					.set(fs.dmalmTempoFk04, fase.getDmalmTempoFk04())
					.set(fs.dsAutoreFase, fase.getDsAutoreFase())
					.set(fs.dtCreazioneFase, fase.getDtCreazioneFase())
					.set(fs.dtModificaFase, fase.getDtModificaFase())
					.set(fs.dtRisoluzioneFase, fase.getDtRisoluzioneFase())
					.set(fs.dtScadenzaFase, fase.getDtScadenzaFase())
					.set(fs.durataEffettivaFase, fase.getDurataEffettivaFase())
					.set(fs.idAutoreFase, fase.getIdAutoreFase())
					.set(fs.idRepository, fase.getIdRepository())
					.set(fs.motivoRisoluzioneFase,
							fase.getMotivoRisoluzioneFase())
					.set(fs.titoloFase, fase.getTitoloFase())
					.set(fs.stgPk, fase.getStgPk()).set(fs.uri, fase.getUri())
					.set(fs.dtAnnullamento, fase.getDtAnnullamento())
					.set(fs.annullato, fase.getAnnullato())
					.set(fs.severity, fase.getSeverity())
					.set(fs.priority, fase.getPriority())
					.set(fs.tagAlm, fase.getTagAlm())
					.set(fs.tsTagAlm, fase.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updatePei(DmalmPei pei) throws Exception {

		QDmalmPei p = QDmalmPei.dmalmPei;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, p)

					.where(p.dmalmPeiPk.eq(pei.getDmalmPeiPk()))

					.set(p.cdPei, pei.getCdPei())
					.set(p.codice, pei.getCodice())
					.set(p.descrizionePei, pei.getDescrizionePei())
					.set(p.dmalmProjectFk02, pei.getDmalmProjectFk02())
					.set(p.dmalmStatoWorkitemFk03,
							pei.getDmalmStatoWorkitemFk03())
					.set(p.dmalmStrutturaOrgFk01,
							pei.getDmalmStrutturaOrgFk01())
					.set(p.dmalmTempoFk04, pei.getDmalmTempoFk04())
					.set(p.dsAutorePei, pei.getDsAutorePei())
					.set(p.dtCreazionePei, pei.getDtCreazionePei())
					.set(p.dtModificaPei, pei.getDtModificaPei())
					.set(p.dtPrevistaComplReq, pei.getDtPrevistaComplReq())
					.set(p.dtPrevistaPassInEs, pei.getDtPrevistaPassInEs())
					.set(p.dtRisoluzionePei, pei.getDtRisoluzionePei())
					.set(p.dtScadenzaPei, pei.getDtScadenzaPei())
					.set(p.idAutorePei, pei.getIdAutorePei())
					.set(p.idRepository, pei.getIdRepository())
					.set(p.motivoRisoluzionePei, pei.getMotivoRisoluzionePei())
					.set(p.titoloPei, pei.getTitoloPei())
					.set(p.stgPk, pei.getStgPk()).set(p.uri, pei.getUri())
					.set(p.dtAnnullamento, pei.getDtAnnullamento())
					.set(p.annullato, pei.getAnnullato())
					.set(p.severity, pei.getSeverity())
					.set(p.priority, pei.getPriority())
					.set(p.tagAlm, pei.getTagAlm())
					.set(p.tsTagAlm, pei.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateProgettoEse(DmalmProgettoEse progetto)
			throws DAOException {
		
		QDmalmProgettoEse progettoEse = QDmalmProgettoEse.dmalmProgettoEse;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progettoEse)
					.where(progettoEse.dmalmProgettoEsePk.eq(progetto
							.getDmalmProgettoEsePk()))
					.set(progettoEse.cdProgettoEse, progetto.getCdProgettoEse())
					.set(progettoEse.descrizioneProgettoEse,
							progetto.getDescrizioneProgettoEse())
					.set(progettoEse.dmalmProjectFk02,
							progetto.getDmalmProjectFk02())
					.set(progettoEse.dmalmStatoWorkitemFk03,
							progetto.getDmalmStatoWorkitemFk03())
					.set(progettoEse.dmalmStrutturaOrgFk01,
							progetto.getDmalmStrutturaOrgFk01())
					.set(progettoEse.dmalmTempoFk04,
							progetto.getDmalmTempoFk04())
					.set(progettoEse.dsAutoreProgettoEse,
							progetto.getDsAutoreProgettoEse())
					.set(progettoEse.dtCreazioneProgettoEse,
							progetto.getDtCreazioneProgettoEse())
					.set(progettoEse.dtModificaProgettoEse,
							progetto.getDtModificaProgettoEse())
					.set(progettoEse.dtRisoluzioneProgettoEse,
							progetto.getDtRisoluzioneProgettoEse())
					.set(progettoEse.dtScadenzaProgettoEse,
							progetto.getDtScadenzaProgettoEse())
					.set(progettoEse.idAutoreProgettoEse,
							progetto.getIdAutoreProgettoEse())
					.set(progettoEse.idRepository, progetto.getIdRepository())
					.set(progettoEse.motivoRisoluzioneProgEse,
							progetto.getMotivoRisoluzioneProgEse())
					.set(progettoEse.rankStatoProgettoEse, new Double(1))
					.set(progettoEse.titoloProgettoEse,
							progetto.getTitoloProgettoEse())
					.set(progettoEse.cfCodice, progetto.getCfCodice())
					.set(progettoEse.stgPk, progetto.getStgPk())
					.set(progettoEse.uri, progetto.getUri())
					.set(progettoEse.cfDtUltimaSottomissione,
							progetto.getCfDtUltimaSottomissione())
					.set(progettoEse.dtAnnullamento,
							progetto.getDtAnnullamento())
					.set(progettoEse.annullato, progetto.getAnnullato())
					.set(progettoEse.severity, progetto.getSeverity())
					.set(progettoEse.priority, progetto.getPriority())
					.set(progettoEse.tagAlm, progetto.getTagAlm())
					.set(progettoEse.tsTagAlm, progetto.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateReleaseIt(DmalmReleaseIt release)
			throws DAOException {
		
		QDmalmReleaseIt releaseIt = QDmalmReleaseIt.dmalmReleaseIt;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, releaseIt)
					.where(releaseIt.dmalmReleaseItPk.eq(release
							.getDmalmReleaseItPk()))
					
					.set(releaseIt.cdReleaseIt, release.getCdReleaseIt())
					.set(releaseIt.descrizioneReleaseIt,
							release.getDescrizioneReleaseIt())
					.set(releaseIt.dmalmProjectFk02,
							release.getDmalmProjectFk02())
					.set(releaseIt.dmalmStatoWorkitemFk03,
							release.getDmalmStatoWorkitemFk03())
					.set(releaseIt.dmalmStrutturaOrgFk01,
							release.getDmalmStrutturaOrgFk01())
					.set(releaseIt.dmalmTempoFk04, release.getDmalmTempoFk04())
					.set(releaseIt.dsAutoreReleaseIt,
							release.getDsAutoreReleaseIt())
					.set(releaseIt.dtCreazioneReleaseIt,
							release.getDtCreazioneReleaseIt())
					.set(releaseIt.dtModificaReleaseIt,
							release.getDtModificaReleaseIt())
					.set(releaseIt.dtRisoluzioneReleaseIt,
							release.getDtRisoluzioneReleaseIt())
					.set(releaseIt.dtScadenzaReleaseIt,
							release.getDtScadenzaReleaseIt())
					.set(releaseIt.dtStoricizzazione,
							release.getDtModificaReleaseIt())
					.set(releaseIt.idAutoreReleaseIt,
							release.getIdAutoreReleaseIt())
					.set(releaseIt.idRepository, release.getIdRepository())
					.set(releaseIt.motivoRisoluzioneReleaseIt,
							release.getMotivoRisoluzioneReleaseIt())
					.set(releaseIt.rankStatoReleaseIt, new Double(1))
					.set(releaseIt.durataEffRelease,
							release.getDurataEffRelease())
					.set(releaseIt.titoloReleaseIt,
							release.getTitoloReleaseIt())
					.set(releaseIt.stgPk, release.getStgPk())
					.set(releaseIt.uri, release.getUri())
					.set(releaseIt.dtFineRelease, release.getDtFineRelease())
					.set(releaseIt.dtDisponibilitaEffRelease,
							release.getDtDisponibilitaEffRelease())
					.set(releaseIt.dtRilascioRelease,
							release.getDtRilascioRelease())
					.set(releaseIt.dtInizioRelease,
							release.getDtInizioRelease())
					.set(releaseIt.dtAnnullamento, release.getDtAnnullamento())
					.set(releaseIt.annullato, release.getAnnullato())
					.set(releaseIt.severity, release.getSeverity())
					.set(releaseIt.priority,release.getPriority())
					.set(releaseIt.typeRelease, release.getTypeRelease())
					.set(releaseIt.motivoSospensione, release.getMotivoSospensione())
					.set(releaseIt.counterQf, release.getCounterQf())
					.set(releaseIt.giorniQf, release.getGiorniQf())
					.set(releaseIt.tagAlm, release.getTagAlm())
					.set(releaseIt.tsTagAlm, release.getTsTagAlm())
					.execute();

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateBuild(DmalmBuild build) throws DAOException {

		QDmalmBuild b = QDmalmBuild.dmalmBuild;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			new SQLUpdateClause(connection, dialect, b)
					.where(b.dmalmBuildPk.eq(build
							.getDmalmBuildPk()))
					.set(b.cdBuild, build.getCdBuild())
					.set(b.descrizioneBuild,
							build.getDescrizioneBuild())
					.set(b.dmalmProjectFk02,
							build.getDmalmProjectFk02())
					.set(b.dmalmStatoWorkitemFk03,
							build.getDmalmStatoWorkitemFk03())
					.set(b.dmalmStrutturaOrgFk01,
							build.getDmalmStrutturaOrgFk01())
					.set(b.dmalmTempoFk04,
							build.getDmalmTempoFk04())
					.set(b.dsAutoreBuild, build.getDsAutoreBuild())
					.set(b.dtCreazioneBuild,
							build.getDtCreazioneBuild())
					.set(b.dtModificaBuild,
							build.getDtModificaBuild())
					.set(b.dtRisoluzioneBuild,
							build.getDtRisoluzioneBuild())
					.set(b.dtScadenzaBuild,
							build.getDtScadenzaBuild())
					.set(b.idAutoreBuild, build.getIdAutoreBuild())
					.set(b.idRepository, build.getIdRepository())
					.set(b.motivoRisoluzioneBuild,
							build.getMotivoRisoluzioneBuild())
					.set(b.titoloBuild, build.getTitoloBuild())
					.set(b.stgPk, build.getStgPk())
					.set(b.uri, build.getUri())
					.set(b.codice, build.getCodice())
					.set(b.dtAnnullamento,
							build.getDtAnnullamento())
					.set(b.annullato, build.getAnnullato())
					.set(b.severity, build.getSeverity())
					.set(b.priority, build.getPriority())
					.set(b.tagAlm, build.getTagAlm())
					.set(b.tsTagAlm, build.getTsTagAlm())
					.execute();
					
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateTaskIt(DmalmTaskIt task) throws DAOException {
		
		QDmalmTaskIt taskit = QDmalmTaskIt.dmalmTaskIt;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, taskit)
					.where(taskit.dmalmTaskItPk.eq(task.getDmalmTaskItPk()))
					.set(taskit.cdTaskIt, task.getCdTaskIt())
					.set(taskit.descrizioneTaskIt, task.getDescrizioneTaskIt())
					.set(taskit.dmalmProjectFk02, task.getDmalmProjectFk02())
					.set(taskit.dmalmStatoWorkitemFk03,
							task.getDmalmStatoWorkitemFk03())
					.set(taskit.dmalmStrutturaOrgFk01,
							task.getDmalmStrutturaOrgFk01())
					.set(taskit.dmalmTempoFk04, task.getDmalmTempoFk04())
					.set(taskit.dsAutoreTaskIt, task.getDsAutoreTaskIt())
					.set(taskit.dtCreazioneTaskIt, task.getDtCreazioneTaskIt())
					.set(taskit.dtModificaTaskIt, task.getDtModificaTaskIt())
					.set(taskit.dtRisoluzioneTaskIt,
							task.getDtRisoluzioneTaskIt())
					.set(taskit.dtScadenzaTaskIt, task.getDtScadenzaTaskIt())
					.set(taskit.dtStoricizzazione, task.getDtModificaTaskIt())
					.set(taskit.idAutoreTaskIt, task.getIdAutoreTaskIt())
					.set(taskit.idRepository, task.getIdRepository())
					.set(taskit.motivoRisoluzioneTaskIt,
							task.getMotivoRisoluzioneTaskIt())
					.set(taskit.rankStatoTaskIt, new Double(1))
					.set(taskit.titoloTaskIt, task.getTitoloTaskIt())
					.set(taskit.stgPk, task.getStgPk())
					.set(taskit.uri, task.getUri())
					.set(taskit.avanzamento, task.getAvanzamento())
					.set(taskit.codice, task.getCodice())
					.set(taskit.dtFineEffettiva, task.getDtFineEffettiva())
					.set(taskit.dtFinePianificata, task.getDtFinePianificata())
					.set(taskit.dtInizioEffettivo, task.getDtInizioEffettivo())
					.set(taskit.dtInizioPianificato,
							task.getDtInizioPianificato())
					.set(taskit.durataEffettiva, task.getDurataEffettiva())
					.set(taskit.priorityTaskIt, task.getPriorityTaskIt())
					.set(taskit.severityTaskIt, task.getSeverityTaskIt())
					.set(taskit.tipoTask, task.getTipoTask())
					.set(taskit.dtAnnullamento, task.getDtAnnullamento())
					.set(taskit.annullato, task.getAnnullato())
					.set(taskit.tagAlm, task.getTagAlm())
					.set(taskit.tsTagAlm, task.getTsTagAlm())
					.execute();
			connection.commit();
		}

		catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateRichiestaGestione(DmalmRichiestaGestione richiesta)
			throws DAOException {

		QDmalmRichiestaGestione rchgs = QDmalmRichiestaGestione.dmalmRichiestaGestione;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, rchgs)

					.where(rchgs.dmalmRichiestaGestPk.eq(richiesta
							.getDmalmRichiestaGestPk()))
					.set(rchgs.categoria, richiesta.getCategoria())
					.set(rchgs.cdRichiestaGest, richiesta.getCdRichiestaGest())
					.set(rchgs.dataChiusura, richiesta.getDataChiusura())
					.set(rchgs.dataDisponibilita,
							richiesta.getDataDisponibilita())
					.set(rchgs.descrizioneRichiestaGest,
							richiesta.getDescrizioneRichiestaGest())
					.set(rchgs.dmalmProjectFk02,
							richiesta.getDmalmProjectFk02())
					.set(rchgs.dmalmStatoWorkitemFk03,
							richiesta.getDmalmStatoWorkitemFk03())
					.set(rchgs.dmalmStrutturaOrgFk01,
							richiesta.getDmalmStrutturaOrgFk01())
					.set(rchgs.dmalmTempoFk04, richiesta.getDmalmTempoFk04())
					.set(rchgs.dsAutoreRichiestaGest,
							richiesta.getDsAutoreRichiestaGest())
					.set(rchgs.dtCambioStatoRichiestaGest,
							richiesta.getDtCambioStatoRichiestaGest())
					.set(rchgs.dtCreazioneRichiestaGest,
							richiesta.getDtCreazioneRichiestaGest())
					.set(rchgs.dtModificaRichiestaGest,
							richiesta.getDtModificaRichiestaGest())
					.set(rchgs.dtRisoluzioneRichiestaGest,
							richiesta.getDtRisoluzioneRichiestaGest())
					.set(rchgs.dtScadenzaRichiestaGest,
							richiesta.getDtScadenzaRichiestaGest())
					.set(rchgs.idAutoreRichiestaGest,
							richiesta.getIdAutoreRichiestaGest())
					.set(rchgs.idRepository, richiesta.getIdRepository())
					.set(rchgs.motivoRisoluzioneRichGest,
							richiesta.getMotivoRisoluzioneRichGest())
					.set(rchgs.stgPk, richiesta.getStgPk())
					.set(rchgs.uri, richiesta.getUri())
					.set(rchgs.ticketid, richiesta.getTicketid())
					.set(rchgs.titoloRichiestaGest,
							richiesta.getTitoloRichiestaGest())
					.set(rchgs.dtAnnullamento, richiesta.getDtAnnullamento())
					.set(rchgs.annullato, richiesta.getAnnullato())
					.set(rchgs.severity, richiesta.getSeverity())
					.set(rchgs.priority, richiesta.getPriority())
					.set(rchgs.tagAlm, richiesta.getTagAlm())
					.set(rchgs.tsTagAlm, richiesta.getTsTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateAnomaliaAssistenza(
			DmalmAnomaliaAssistenza anomalia_assistenza) throws DAOException {
		
		QDmalmAnomaliaAssistenza anomass = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			new SQLUpdateClause(connection, dialect, anomass)
					.where(anomass.dmalmAnomaliaAssPk
							.eq(anomalia_assistenza
									.getDmalmAnomaliaAssPk()))
					.set(anomass.cdAnomaliaAss,
							anomalia_assistenza.getCdAnomaliaAss())
					.set(anomass.dmalmProjectFk02,
							anomalia_assistenza.getDmalmProjectFk02())
					.set(anomass.dmalmStatoWorkitemFk03,
							anomalia_assistenza.getDmalmStatoWorkitemFk03())
					.set(anomass.dmalmStrutturaOrgFk01,
							anomalia_assistenza.getDmalmStrutturaOrgFk01())
					.set(anomass.dmalmTempoFk04,
							anomalia_assistenza.getDmalmTempoFk04())
					.set(anomass.descrizioneAnomaliaAss,
							anomalia_assistenza.getDescrizioneAnomaliaAss())
					.set(anomass.dsAutoreAnomaliaAss,
							anomalia_assistenza.getDsAutoreAnomaliaAss())
					.set(anomass.aoid, anomalia_assistenza.getAoid())
					.set(anomass.ca, anomalia_assistenza.getCa())
					.set(anomass.cs, anomalia_assistenza.getCs())
					.set(anomass.dtCambioStatoAnomaliaAss,
							anomalia_assistenza.getDtCambioStatoAnomaliaAss())
					.set(anomass.dtCreazioneAnomaliaAss,
							anomalia_assistenza.getDtCreazioneAnomaliaAss())
					.set(anomass.dtModificaAnomaliaAss,
							anomalia_assistenza.getDtModificaAnomaliaAss())
					.set(anomass.dtRisoluzioneAnomaliaAss,
							anomalia_assistenza.getDtRisoluzioneAnomaliaAss())
					.set(anomass.dtScadenzaAnomaliaAss,
							anomalia_assistenza.getDtScadenzaAnomaliaAss())
					.set(anomass.frequenza, anomalia_assistenza.getFrequenza())
					.set(anomass.idAutoreAnomaliaAss,
							anomalia_assistenza.getIdAutoreAnomaliaAss())
					.set(anomass.idRepository,
							anomalia_assistenza.getIdRepository())
					.set(anomass.motivoRisoluzioneAnomaliaAs,
							anomalia_assistenza
									.getMotivoRisoluzioneAnomaliaAs())
					.set(anomass.platform, anomalia_assistenza.getPlatform())
					.set(anomass.priority, anomalia_assistenza.getPriority())
					.set(anomass.prodCod, anomalia_assistenza.getProdCod())
					.set(anomass.segnalazioni,
							anomalia_assistenza.getSegnalazioni())
					.set(anomass.severity, anomalia_assistenza.getSeverity())
					.set(anomass.so, anomalia_assistenza.getSo())
					.set(anomass.stChiuso, anomalia_assistenza.getStChiuso())
					.set(anomass.stgPk, anomalia_assistenza.getStgPk())
					.set(anomass.uri, anomalia_assistenza.getUri())
					.set(anomass.tempoTotaleRisoluzione,
							anomalia_assistenza.getTempoTotaleRisoluzione())
					.set(anomass.ticketid, anomalia_assistenza.getTicketid())
					.set(anomass.titoloAnomaliaAss,
							anomalia_assistenza.getTitoloAnomaliaAss())
					.set(anomass.dtAnnullamento,
							anomalia_assistenza.getDtAnnullamento())
					.set(anomass.annullato, anomalia_assistenza.getAnnullato())
					.set(anomass.tagAlm, anomalia_assistenza.getTagAlm())
					.set(anomass.tsTagAlm, anomalia_assistenza.getTsTagAlm()).execute();

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateReleaseServizi(DmalmReleaseServizi release)
			throws DAOException {
		
		QDmalmReleaseServizi releaseservizi = QDmalmReleaseServizi.dmalmReleaseServizi;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, releaseservizi)
					.where(releaseservizi.dmalmRelServiziPk.eq(release
							.getDmalmRelServiziPk()))

					.set(releaseservizi.cdRelServizi, release.getCdRelServizi())
					.set(releaseservizi.descrizioneRelServizi,
							release.getDescrizioneRelServizi())
					.set(releaseservizi.dmalmProjectFk02,
							release.getDmalmProjectFk02())
					.set(releaseservizi.dmalmStatoWorkitemFk03,
							release.getDmalmStatoWorkitemFk03())
					.set(releaseservizi.dmalmStrutturaOrgFk01,
							release.getDmalmStrutturaOrgFk01())
					.set(releaseservizi.dmalmTempoFk04,
							release.getDmalmTempoFk04())
					.set(releaseservizi.dsAutoreRelServizi,
							release.getDsAutoreRelServizi())
					.set(releaseservizi.dtCreazioneRelServizi,
							release.getDtCreazioneRelServizi())
					.set(releaseservizi.dtModificaRelServizi,
							release.getDtModificaRelServizi())
					.set(releaseservizi.dtRisoluzioneRelServizi,
							release.getDtRisoluzioneRelServizi())
					.set(releaseservizi.dtScadenzaRelServizi,
							release.getDtScadenzaRelServizi())
					.set(releaseservizi.dtStoricizzazione,
							release.getDtModificaRelServizi())
					.set(releaseservizi.idAutoreRelServizi,
							release.getIdAutoreRelServizi())
					.set(releaseservizi.idRepository, release.getIdRepository())
					.set(releaseservizi.motivoRisoluzioneRelServizi,
							release.getMotivoRisoluzioneRelServizi())
					.set(releaseservizi.rankStatoRelServizi, new Double(1))
					.set(releaseservizi.titoloRelServizi,
							release.getTitoloRelServizi())
					.set(releaseservizi.stgPk, release.getStgPk())
					.set(releaseservizi.uri, release.getUri())
					.set(releaseservizi.motivoSospensioneReleaseSer,
							release.getMotivoSospensioneReleaseSer())
					.set(releaseservizi.previstoFermoServizioRel,
							release.getPrevistoFermoServizioRel())
					.set(releaseservizi.richiestaAnalisiImpattiRel,
							release.getRichiestaAnalisiImpattiRel())
					.set(releaseservizi.dtAnnullamento,
							release.getDtAnnullamento())
					.set(releaseservizi.annullato, release.getAnnullato())
					.set(releaseservizi.severity,
							release.getSeverity())
					.set(releaseservizi.priority,
							release.getPriority())
					.set(releaseservizi.tagAlm, release.getTagAlm())
					.set(releaseservizi.tsTagAlm, release.getTsTagAlm())
					.execute();
			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
	
	private static void updateClass(DmalmClassificatore classificatore)
			throws Exception {
		
		QDmalmClassificatore qClassificatore = QDmalmClassificatore.dmalmClassificatore;
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, qClassificatore)

					.where(qClassificatore.dmalmClassificatorePk.eq(classificatore
							.getDmalmClassificatorePk()))

					.set(qClassificatore.cd_classificatore,
							classificatore.getCd_classificatore())
					.set(qClassificatore.cf_ambito, classificatore.getCf_ambito())
					.set(qClassificatore.cf_area, classificatore.getCf_area())
					.set(qClassificatore.cf_riferimenti, classificatore.getCf_riferimenti())
					.set(qClassificatore.cf_scheda_servizio,
							classificatore.getCf_scheda_servizio())
					.set(qClassificatore.dmalmStrutturaOrgFk01,
							classificatore.getDmalmStrutturaOrgFk01())
					.set(qClassificatore.dmalmTempoFk04, classificatore.getDmalmTempoFk04())
					.set(qClassificatore.dmalmProjectFk02, classificatore.getDmalmProjectFk02())
					.set(qClassificatore.dmalmStatoWorkitemFk03,
							classificatore.getDmalmStatoWorkitemFk03())
					.set(qClassificatore.dmalmUserFk06, classificatore.getDmalmUserFk06())
					.set(qClassificatore.dsAutoreClassificatore,
							classificatore.getDsAutoreClassificatore())
					.set(qClassificatore.dtCambioStatoClassif,
							classificatore.getDtCambioStatoClassif())
					.set(qClassificatore.dtCaricamentoClassif,
							classificatore.getDtCaricamentoClassif())
					.set(qClassificatore.dtCreazioneClassif,
							classificatore.getDtCreazioneClassif())
					.set(qClassificatore.dtModificaClassif,
							classificatore.getDtModificaClassif())
					.set(qClassificatore.dtRisoluzioneClassif,
							classificatore.getDtRisoluzioneClassif())
					.set(qClassificatore.dtScadenzaProgSvil,
							classificatore.getDtScadenzaProgSvil())
					.set(qClassificatore.idAutoreClassificatore,
							classificatore.getIdAutoreClassificatore())
					.set(qClassificatore.stgPk, classificatore.getStgPk())
					.set(qClassificatore.uriClassficatore, classificatore.getUriClassficatore())
					.set(qClassificatore.titoloClassificatore,
							classificatore.getTitoloClassificatore())
					.set(qClassificatore.annullato, classificatore.getAnnullato())
					.set(qClassificatore.rmResponsabiliProgetto, classificatore.getRmResponsabiliProgetto())
					.set(qClassificatore.progettoInDeroga, classificatore.isProgettoInDeroga())
					.set(qClassificatore.assigneeProgettoItInDeroga, classificatore.getAssigneeProgettoItInDeroga())
					.set(qClassificatore.locationSorgenti, classificatore.getLocationSorgenti())
					.set(qClassificatore.type, classificatore.getType())
					.set(qClassificatore.codiceServizi,  classificatore.getCodiceServizi())
					.set(qClassificatore.severity, classificatore.getSeverity())
					.set(qClassificatore.priority,  classificatore.getPriority())
					.set(qClassificatore.tagAlm,  classificatore.getTagAlm())
					.set(qClassificatore.tsTagAlm,  classificatore.getTsTagAlm())
					
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}
}