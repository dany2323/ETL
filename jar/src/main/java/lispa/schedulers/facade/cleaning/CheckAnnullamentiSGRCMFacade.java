package lispa.schedulers.facade.cleaning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
import lispa.schedulers.bean.target.fatti.DmalmRichiestaGestione;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaManutenzione;
import lispa.schedulers.bean.target.fatti.DmalmSottoprogramma;
import lispa.schedulers.bean.target.fatti.DmalmTask;
import lispa.schedulers.bean.target.fatti.DmalmTaskIt;
import lispa.schedulers.bean.target.fatti.DmalmTestcase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.ErroriCaricamentoDAO;
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
import lispa.schedulers.dao.target.fatti.RichiestaGestioneDAO;
import lispa.schedulers.dao.target.fatti.RichiestaManutenzioneDAO;
import lispa.schedulers.dao.target.fatti.SottoprogrammaDAO;
import lispa.schedulers.dao.target.fatti.TaskDAO;
import lispa.schedulers.dao.target.fatti.TaskItDAO;
import lispa.schedulers.dao.target.fatti.TestCaseDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
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

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class CheckAnnullamentiSGRCMFacade {

	private static Logger logger = Logger
			.getLogger(CheckAnnullamentiSGRCMFacade.class);
	private static QDmalmDifettoProdotto difetto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
	private static QDmalmAnomaliaProdotto anomalia = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;
	private static QDmalmProgettoSviluppoSvil progettoSviluppoSvil = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
	private static QDmalmDocumento documento = QDmalmDocumento.dmalmDocumento;
	private static QDmalmManutenzione manutenzione = QDmalmManutenzione.dmalmManutenzione;
	private static QDmalmTestcase testcase = QDmalmTestcase.dmalmTestcase;
	private static QDmalmTask task = QDmalmTask.dmalmTask;
	private static QDmalmReleaseDiProgetto releaseDiProgetto = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;
	private static QDmalmProgramma programma = QDmalmProgramma.dmalmProgramma;
	private static QDmalmSottoprogramma sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;
	private static QDmalmProgettoDemand progettoDemand = QDmalmProgettoDemand.dmalmProgettoDemand;
	private static QDmalmProgettoSviluppoDem progettoSviluppoDem = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
	private static QDmalmRichiestaManutenzione richiestaManutenzione = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;
	private static QDmalmFase fase = QDmalmFase.dmalmFase;
	private static QDmalmPei pei = QDmalmPei.dmalmPei;
	private static QDmalmProgettoEse progettoEse = QDmalmProgettoEse.dmalmProgettoEse;
	private static QDmalmReleaseIt releaseIT = QDmalmReleaseIt.dmalmReleaseIt;
	private static QDmalmBuild build = QDmalmBuild.dmalmBuild;
	private static QDmalmTaskIt taskIT = QDmalmTaskIt.dmalmTaskIt;
	private static QDmalmRichiestaGestione richiestaGestione = QDmalmRichiestaGestione.dmalmRichiestaGestione;
	private static QDmalmAnomaliaAssistenza anomaliaAssistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
	private static QDmalmReleaseServizi releaseServizi = QDmalmReleaseServizi.dmalmReleaseServizi;
	private static QDmalmClassificatore classificatore = QDmalmClassificatore.dmalmClassificatore;
	private static SQLTemplates dialect = new HSQLDBTemplates();

	private static List<String> unmarkedProjectsSire, unmarkedProjectsSiss,
			deletedSireProjects, deletedSissProjects,
			alreadyUnmarkedProjectsPathsSire, alreadyUnmarkedProjectsPathsSiss,
			reactivatedProjectsSiss, reactivatedProjectsSire;
	private static List<Tuple> currentProjectsSiss, currentProjectsSire;
	private static String urlSire, urlSiss;
	QDmalmProject proj = QDmalmProject.dmalmProject;

	public static void execute() {

		try {
			/**
			 * recupero la lista dei progetti nello stato UNMARKED
			 */
			urlSire = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.UNMARKED_PROJECTS_FILE_SIRE);
			urlSiss = DmAlmConfigReader.getInstance().getProperty(
					DmAlmConfigReaderProperties.UNMARKED_PROJECTS_FILE_SISS);
			/**
			 * Projects nella lista degli annullati SISS/SIRE
			 */
			unmarkedProjectsSire = ProjectSgrCmDAO
					.getUnmarkedProjectsPathsFromFile(urlSire,
							DmAlmConstants.REPOSITORY_SIRE);
			unmarkedProjectsSiss = ProjectSgrCmDAO
					.getUnmarkedProjectsPathsFromFile(urlSiss,
							DmAlmConstants.REPOSITORY_SISS);
			/**
			 * Projects già annullati (non c'è necessità di annullare ad ogni
			 * esecuzione)
			 */
			alreadyUnmarkedProjectsPathsSire = ProjectSgrCmDAO
					.getAlreadyUnmarkedProjects(DmAlmConstants.REPOSITORY_SIRE);
			alreadyUnmarkedProjectsPathsSiss = ProjectSgrCmDAO
					.getAlreadyUnmarkedProjects(DmAlmConstants.REPOSITORY_SISS);

			currentProjectsSire = ProjectSgrCmDAO
					.getCurrent(DmAlmConstants.REPOSITORY_SIRE);
			currentProjectsSiss = ProjectSgrCmDAO
					.getCurrent(DmAlmConstants.REPOSITORY_SISS);
			/**
			 * eseguo la query per trovare i progetti presenti alla data
			 * dell'esecuzione precedente, ma non più presenti alla data di
			 * esecuzione attuale
			 */

			for (String projectLocation : unmarkedProjectsSire) {
				/**
				 * se compare nel file dei project unmarked, imposto il flag
				 * UNMARKED al progetto e a tutti i workitem figli. Il field
				 * ANNULLATO viene posto ad UNMARKED solo una volta, se lo è già
				 * non faccio update.
				 */

				if (!alreadyUnmarkedProjectsPathsSire.contains(projectLocation)) {
					// search project tramite path e data di fine validita 9999
					DmalmProject p = ProjectSgrCmDAO.getProjectByPath(
							projectLocation, DmAlmConstants.REPOSITORY_SIRE);
					// update dt fine validità su questo
					if (p != null) {
						if (p.getDtInizioValidita().equals(
								DataEsecuzione.getInstance()
										.getDataEsecuzione())) {
							// se già è stato storicizzato oggi -> update
							// semplice
							p.setAnnullato(DmAlmConstants.LOGICAMENTE);
							p.setDtAnnullamento(DataEsecuzione.getInstance().getDataEsecuzione());
							ProjectSgrCmDAO.updateDmalmProject(p);
						} else {
							ProjectSgrCmDAO.updateDataFineValiditaAnnullamento(
									DataEsecuzione.getInstance()
											.getDataEsecuzione(), p);
							p.setAnnullato(DmAlmConstants.LOGICAMENTE);
							p.setDtAnnullamento(DataEsecuzione.getInstance()
									.getDataEsecuzione());
							p.setDtInizioValidita(DataEsecuzione.getInstance()
									.getDataEsecuzione());
							// insert update nuovo record
							ProjectSgrCmDAO.insertProjectUpdate(DataEsecuzione
									.getInstance().getDataEsecuzione(), p,
									false);
						}
					}

				}

			}

			for (String projectLocation : unmarkedProjectsSiss) {

				/**
				 * se compare nel file dei project unmarked, imposto il flag
				 * UNMARKED al progetto e a tutti i workitem figli Il field
				 * ANNULLATO viene posto ad UNMARKED solo una volta, se lo è già
				 * non faccio update
				 */
				if (!alreadyUnmarkedProjectsPathsSiss.contains(projectLocation)) {
					// search project tramite path e data di fine validita 9999
					DmalmProject p = ProjectSgrCmDAO.getProjectByPath(
							projectLocation, DmAlmConstants.REPOSITORY_SISS);
					// update dt fine validità su questo
					if (p != null) {
						if (p.getDtInizioValidita().equals(
								DataEsecuzione.getInstance()
										.getDataEsecuzione())) {
							// se già è stato storicizzato oggi -> update
							// semplice
							p.setAnnullato(DmAlmConstants.LOGICAMENTE);
							p.setDtAnnullamento(DataEsecuzione.getInstance().getDataEsecuzione());
							ProjectSgrCmDAO.updateDmalmProject(p);
						} else {
							ProjectSgrCmDAO.updateDataFineValiditaAnnullamento(
									DataEsecuzione.getInstance()
											.getDataEsecuzione(), p);
							p.setAnnullato(DmAlmConstants.LOGICAMENTE);
							p.setDtAnnullamento(DataEsecuzione.getInstance()
									.getDataEsecuzione());
							p.setDtInizioValidita(DataEsecuzione.getInstance()
									.getDataEsecuzione());
							// insert update nuovo record
							ProjectSgrCmDAO.insertProjectUpdate(DataEsecuzione
									.getInstance().getDataEsecuzione(), p,
									false);
						}
					}
				}
			}

			deletedSireProjects = ProjectSgrCmDAO
					.getDeletedProjectsPaths(DmAlmConstants.REPOSITORY_SIRE);
			deletedSissProjects = ProjectSgrCmDAO
					.getDeletedProjectsPaths(DmAlmConstants.REPOSITORY_SISS);
			logger.debug("TROVATI " + deletedSireProjects.size()
					+ " PROJECTs ELIMINATI FISICAMENTE IN SIRE CON ID: "
					+ deletedSireProjects);
			logger.debug("TROVATI " + deletedSissProjects.size()
					+ " PROJECTs ELIMINATI FISICAMENTE IN SISS CON ID: "
					+ deletedSissProjects);

			for (String projectId : deletedSireProjects) {
				/**
				 * altrimenti imposto il flag ELIMINATO FISICAMENTE al progetto
				 * e a tutti i workitem figli
				 */

				ProjectSgrCmDAO.setAnnullato(projectId,
						DmAlmConstants.FISICAMENTE,
						DmAlmConstants.REPOSITORY_SIRE);

				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SIRE_CURRENT_PROJECT,
						DmAlmConstants.TARGET_SGR_SIRE_CURRENT_PROJECT,
						"projectId: " + projectId + "§ idRepository: "
								+ DmAlmConstants.REPOSITORY_SIRE,
						DmAlmConstants.SGR_PROJECT_ANNULLATO_FISICAMENTE,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						DataEsecuzione.getInstance().getDataEsecuzione());
			}

			for (String projectId : deletedSissProjects) {
				/**
				 * altrimenti imposto il flag ELIMINATO FISICAMENTE al progetto
				 * e a tutti i workitem figli
				 */
				ProjectSgrCmDAO.setAnnullato(projectId,
						DmAlmConstants.FISICAMENTE,
						DmAlmConstants.REPOSITORY_SISS);

				ErroriCaricamentoDAO.insert(
						DmAlmConstants.FONTE_SGR_SISS_CURRENT_PROJECT,
						DmAlmConstants.TARGET_SGR_SISS_CURRENT_PROJECT,
						"projectId: " + projectId + ", idRepository: "
								+ DmAlmConstants.REPOSITORY_SISS,
						DmAlmConstants.SGR_PROJECT_ANNULLATO_FISICAMENTE,
						DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
						DataEsecuzione.getInstance().getDataEsecuzione());
			}

			alreadyUnmarkedProjectsPathsSire.removeAll(unmarkedProjectsSire);
			alreadyUnmarkedProjectsPathsSiss.removeAll(unmarkedProjectsSiss);
			
			for (String string : alreadyUnmarkedProjectsPathsSire) {
				logger.debug("Project da Riattivare Sire " + string);
			}
			for (String string : alreadyUnmarkedProjectsPathsSiss) {
				logger.debug("Project da Riattivare Siss " + string);
			}
			
			// Recupero quelli da riattivare
			reactivatedProjectsSire = alreadyUnmarkedProjectsPathsSire;
			reactivatedProjectsSiss = alreadyUnmarkedProjectsPathsSiss;
			ProjectSgrCmDAO.setReactivated(currentProjectsSire,
					reactivatedProjectsSire, DmAlmConstants.REPOSITORY_SIRE);
			ProjectSgrCmDAO.setReactivated(currentProjectsSiss,
					reactivatedProjectsSiss, DmAlmConstants.REPOSITORY_SISS);

			annullaWorkitemFigli();

			//riattivo i wi figli
			List<DmalmProject> pListSire = ProjectSgrCmDAO.getReactivated(
					currentProjectsSire, reactivatedProjectsSire, DmAlmConstants.REPOSITORY_SIRE);
			List<DmalmProject> pListSiss = ProjectSgrCmDAO.getReactivated(
					currentProjectsSiss, reactivatedProjectsSiss, DmAlmConstants.REPOSITORY_SISS);
			pListSire.addAll(pListSiss);
			riattivaWiFigli(pListSire);

			annullaWorkitemDeleted();
		}

		catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}

	}

	private static void riattivaWiFigli(List<DmalmProject> pList)
			throws DAOException, PropertiesReaderException {
		try {
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			Timestamp dataChiusura = DateUtils.addSecondsToTimestamp(
					dataEsecuzione, -1);

			for (Workitem_Type type : Workitem_Type.values()) {
				ConnectionManager cm = null;
				Connection conn = null;
				List<Integer> pk = new ArrayList<Integer>();
				for (DmalmProject p : pList) {
					String idProject = p.getIdProject();
					String repo = p.getIdRepository();
					List<DmalmProject> pHistory = ProjectSgrCmDAO
							.getHistoryProject(idProject, repo, dataChiusura);
					if (pHistory != null) {
						for (DmalmProject history : pHistory) {

							try {
								cm = ConnectionManager.getInstance();
								conn = cm.getConnectionOracle();

								SQLQuery query = new SQLQuery(conn, dialect);

								switch (type.name()) {
								case "anomalia":
									pk = query
											.from(anomalia)
											.where(anomalia.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(anomalia.rankStatoAnomalia
													.eq(new Double(1)))
											.list(anomalia.dmalmAnomaliaProdottoPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmAnomaliaProdotto a = AnomaliaProdottoDAO
													.getAnomaliaProdotto(i);
											if (a != null) {
												if (a.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													a.setAnnullato("");
													a.setDtAnnullamento(null);
													a.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													AnomaliaProdottoDAO
															.updateDmalmAnomaliaProdotto(a);
												} else {
													AnomaliaProdottoDAO
															.updateRankFlagUltimaSituazione(
																	a,
																	new Double(
																			0),
																	new Short(
																			"0"));
													a.setAnnullato("");
													a.setDtStoricizzazione(dataEsecuzione);
													a.setDtAnnullamento(null);
													a.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													AnomaliaProdottoDAO
															.insertAnomaliaProdottoUpdate(
																	dataEsecuzione,
																	a, false);
												}
											}
										}
									}
									break;
								case "defect":
									pk = query
											.from(difetto)
											.where(difetto.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.list(difetto.dmalmDifettoProdottoPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmDifettoProdotto d = DifettoDAO
													.getDifetto(i);
											if (d != null) {
												if (d.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													d.setAnnullato("");
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													DifettoDAO
															.updateDmalmDifettoProdotto(d);
												} else {
													DifettoDAO
															.updateRankFlagUltimaSituazione(
																	d,
																	new Double(
																			0),
																	new Short(
																			"0"));
													d.setAnnullato("");
													d.setDtStoricizzazione(dataEsecuzione);
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													DifettoDAO
															.insertDifettoProdottoUpdate(
																	dataEsecuzione,
																	d, false);
												}
											}
										}
									}
									break;
								case "srqs":
									pk = query
											.from(progettoSviluppoSvil)
											.where(progettoSviluppoSvil.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.list(progettoSviluppoSvil.dmalmProgSvilSPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmProgettoSviluppoSvil pss = ProgettoSviluppoSviluppoDAO
													.getProgettoSviluppoSviluppo(i);
											if (pss != null) {
												if (pss.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													pss.setAnnullato("");
													pss.setDtAnnullamento(null);
													pss.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ProgettoSviluppoSviluppoDAO
															.updateProgettoSviluppoSvil(pss);
												} else {
													ProgettoSviluppoSviluppoDAO
															.updateRank(pss,
																	new Double(
																			0));
													pss.setAnnullato("");
													pss.setDtStoricizzazione(dataEsecuzione);
													pss.setDtAnnullamento(null);
													pss.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ProgettoSviluppoSviluppoDAO
															.insertProgettoSviluppoSvilUpdate(
																	dataEsecuzione,
																	pss, false);
												}
											}
										}
									}
									break;
								case "documento":
									pk = query
											.from(documento)
											.where(documento.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.list(documento.dmalmDocumentoPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmDocumento d = DocumentoDAO
													.getDocumento(i);
											if (d != null) {
												if (d.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													d.setAnnullato("");
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													DocumentoDAO
															.updateDocumento(d);
												} else {
													DocumentoDAO.updateRank(d,
															new Double(0));
													d.setAnnullato("");
													d.setDtStoricizzazione(dataEsecuzione);
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													DocumentoDAO
															.insertDocumentoUpdate(
																	dataEsecuzione,
																	d, false);
												}
											}
										}
									}
									break;
								case "sman":
									pk = query
											.from(manutenzione)
											.where(manutenzione.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.list(manutenzione.dmalmManutenzionePk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmManutenzione m = ManutenzioneDAO
													.getManutenzione(i);
											if (m != null) {
												if (m.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													m.setAnnullato("");
													m.setDtAnnullamento(null);
													m.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ManutenzioneDAO
															.updateManutenzione(m);
												} else {
													ManutenzioneDAO.updateRank(
															m, new Double(0));
													m.setAnnullato("");
													m.setDtStoricizzazione(dataEsecuzione);
													m.setDtAnnullamento(null);
													m.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ManutenzioneDAO
															.insertManutenzioneUpdate(
																	dataEsecuzione,
																	m, false);
												}
											}
										}
									}
									break;
								case "testcase":
									pk = query
											.from(testcase)
											.where(testcase.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.list(testcase.dmalmTestcasePk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmTestcase t = TestCaseDAO
													.getTestCase(i);
											if (t != null) {
												if (t.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													t.setAnnullato("");
													t.setDtAnnullamento(null);
													t.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													TestCaseDAO
															.updateTestCase(t);
												} else {
													TestCaseDAO.updateRank(t,
															new Double(0));
													t.setAnnullato("");
													t.setDtStoricizzazione(dataEsecuzione);
													t.setDtAnnullamento(null);
													t.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													TestCaseDAO
															.insertTestCaseUpdate(
																	dataEsecuzione,
																	t, false);
												}
											}
										}
									}
									break;
								case "task":
									pk = query
											.from(task)
											.where(task.dmalmProjectFk02.eq(p
													.getDmalmProjectPk()))
											.list(task.dmalmTaskPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmTask t = TaskDAO.getTask(i);
											if (t != null) {
												if (t.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													t.setAnnullato("");
													t.setDtAnnullamento(null);
													t.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													TaskDAO.updateTask(t);
												} else {
													TaskDAO.updateRank(t,
															new Double(0));
													t.setAnnullato("");
													t.setDtStoricizzazione(dataEsecuzione);
													t.setDtAnnullamento(null);
													t.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													TaskDAO.insertTaskUpdate(
															dataEsecuzione, t,
															false);
												}
											}
										}
									}
									break;
								case "release":
									pk = query
											.from(releaseDiProgetto)
											.where(releaseDiProgetto.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.list(releaseDiProgetto.dmalmReleasediprogPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmReleaseDiProgetto r = ReleaseDiProgettoDAO
													.getReleaseDiProgetto(i);
											if (r != null) {
												if (r.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													r.setAnnullato("");
													r.setDtAnnullamento(null);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ReleaseDiProgettoDAO
															.updateReleaseDiProgetto(r);
												} else {
													ReleaseDiProgettoDAO
															.updateRank(r,
																	new Double(
																			0));
													r.setAnnullato("");
													r.setDtStoricizzazione(dataEsecuzione);
													r.setDtAnnullamento(null);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ReleaseDiProgettoDAO
															.insertReleaseDiProgettoUpdate(
																	dataEsecuzione,
																	r, false);
												}
											}
										}
									}
									break;
								case "programma":
									pk = query
											.from(programma)
											.where(programma.dmalmProjectFk02.eq(history
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
													d.setAnnullato("");
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ProgrammaDAO
															.updateProgramma(d);
												} else {
													ProgrammaDAO.updateRank(d,
															new Double(0));
													d.setAnnullato("");
													d.setDtStoricizzazione(dataEsecuzione);
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ProgrammaDAO
															.insertProgrammaUpdate(
																	dataEsecuzione,
																	d, false);
												}
											}
										}
									}
									break;
								case "sottoprogramma":
									pk = query
											.from(sottoprogramma)
											.where(sottoprogramma.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.list(sottoprogramma.dmalmSottoprogrammaPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmSottoprogramma s = SottoprogrammaDAO
													.getSottoprogramma(i);
											if (s != null) {
												if (s.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													s.setAnnullato("");
													s.setDtAnnullamento(null);
													s.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													SottoprogrammaDAO
															.updateSottoprogramma(s);
												} else {
													SottoprogrammaDAO
															.updateRank(s,
																	new Double(
																			0));
													s.setAnnullato("");
													s.setDtStoricizzazione(dataEsecuzione);
													s.setDtAnnullamento(null);
													s.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													SottoprogrammaDAO
															.insertSottoprogrammaUpdate(
																	dataEsecuzione,
																	s, false);
												}
											}
										}
									}
									break;
								case "rqd":
									pk = query
											.from(progettoDemand)
											.where(progettoDemand.dmalmProjectFk02.eq(history
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
													d.setAnnullato("");
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ProgettoDemandDAO
															.updateProgettoDemand(d);
												} else {
													ProgettoDemandDAO
															.updateRank(d,
																	new Double(
																			0));
													d.setAnnullato("");
													d.setDtStoricizzazione(dataEsecuzione);
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ProgettoDemandDAO
															.insertProgettoDemandUpdate(
																	dataEsecuzione,
																	d, false);
												}
											}
										}
									}
									break;
								case "drqs":
									pk = query
											.from(progettoSviluppoDem)
											.where(progettoSviluppoDem.dmalmProjectFk02.eq(history
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
													d.setAnnullato("");
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ProgettoSviluppoDemandDAO
															.updateProgettoSviluppoDem(d);
												} else {
													ProgettoSviluppoDemandDAO
															.updateRank(d,
																	new Double(
																			0));
													d.setAnnullato("");
													d.setDtStoricizzazione(dataEsecuzione);
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ProgettoSviluppoDemandDAO
															.insertProgettoSviluppoDemUpdate(
																	dataEsecuzione,
																	d, false);
												}
											}
										}
									}
									break;
								case "dman":
									pk = query
											.from(richiestaManutenzione)
											.where(richiestaManutenzione.dmalmProjectFk02.eq(history
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
													r.setAnnullato("");
													r.setDtAnnullamento(null);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													RichiestaManutenzioneDAO
															.updateRichiestaManutenzione(r);
												} else {
													RichiestaManutenzioneDAO
															.updateRank(r,
																	new Double(
																			0));
													r.setAnnullato("");
													r.setDtStoricizzazione(dataEsecuzione);
													r.setDtAnnullamento(null);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													RichiestaManutenzioneDAO
															.insertRichiestaManutenzioneUpdate(
																	dataEsecuzione,
																	r, false);
												}
											}
										}
									}
									break;
								case "fase":
									pk = query
											.from(fase)
											.where(fase.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.list(fase.dmalmFasePk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmFase f = FaseDAO.getFase(i);
											if (f != null) {
												if (f.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													f.setAnnullato("");
													f.setDtAnnullamento(null);
													f.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													FaseDAO.updateFase(f);
												} else {
													FaseDAO.updateRank(f,
															new Double(0));
													f.setAnnullato("");
													f.setDtStoricizzazione(dataEsecuzione);
													f.setDtAnnullamento(null);
													f.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													FaseDAO.insertFaseUpdate(
															dataEsecuzione, f,
															false);
												}
											}
										}
									}
									break;
								case "pei":
									pk = query
											.from(pei)
											.where(pei.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.list(pei.dmalmPeiPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmPei f = PeiDAO.getPei(i);
											if (f != null) {
												if (f.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													f.setAnnullato("");
													f.setDtAnnullamento(null);
													f.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													PeiDAO.updatePei(f);
												} else {
													PeiDAO.updateRank(f,
															new Double(0));
													f.setAnnullato("");
													f.setDtStoricizzazione(dataEsecuzione);
													f.setDtAnnullamento(null);
													f.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													PeiDAO.insertPeiUpdate(
															dataEsecuzione, f,
															false);
												}
											}

										}
									}
									break;
								case "progettoese":
									pk = query
											.from(progettoEse)
											.where(progettoEse.dmalmProjectFk02.eq(history
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
													d.setAnnullato("");
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ProgettoEseDAO
															.updateProgettoEse(d);
												} else {
													ProgettoEseDAO.updateRank(
															d, new Double(0));
													d.setAnnullato("");
													d.setDtStoricizzazione(dataEsecuzione);
													d.setDtAnnullamento(null);
													d.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ProgettoEseDAO
															.insertProgettoEseUpdate(
																	dataEsecuzione,
																	d, false);
												}
											}
										}
									}
									break;
								case "release_it":
									pk = query
											.from(releaseIT)
											.where(releaseIT.dmalmProjectFk02.eq(history
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
													r.setAnnullato("");
													r.setDtAnnullamento(null);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ReleaseItDAO
															.updateReleaseIt(r);
												} else {
													ReleaseItDAO.updateRank(r,
															new Double(0));
													r.setAnnullato("");
													r.setDtStoricizzazione(dataEsecuzione);
													r.setDtAnnullamento(null);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ReleaseItDAO
															.insertReleaseItUpdate(
																	dataEsecuzione,
																	r, false);
												}
											}
										}
									}
									break;
								case "build":
									pk = query
											.from(build)
											.where(build.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.list(build.dmalmBuildPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmBuild b = BuildDAO.getBuild(i);
											if (b != null) {
												if (b.getDtStoricizzazione()
														.equals(dataEsecuzione)) {
													// già storicizzato oggi
													b.setAnnullato("");
													b.setDtAnnullamento(null);
													b.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													BuildDAO.updateBuild(b);
												} else {
													BuildDAO.updateRank(b,
															new Double(0));
													b.setAnnullato("");
													b.setDtStoricizzazione(dataEsecuzione);
													b.setDtAnnullamento(null);
													b.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													BuildDAO.insertBuildUpdate(
															dataEsecuzione, b,
															false);
												}
											}
										}
									}
									break;
								case "taskit":
									pk = query
											.from(taskIT)
											.where(taskIT.dmalmProjectFk02.eq(history
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
													t.setAnnullato("");
													t.setDtAnnullamento(null);
													t.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													TaskItDAO.updateTaskIt(t);
												} else {
													TaskItDAO.updateRank(t,
															new Double(0));
													t.setAnnullato("");
													t.setDtStoricizzazione(dataEsecuzione);
													t.setDtAnnullamento(null);
													t.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													TaskItDAO
															.insertTaskItUpdate(
																	dataEsecuzione,
																	t, false);
												}
											}
										}
									}
									break;
								case "richiesta_gestione":
									pk = query
											.from(richiestaGestione)
											.where(richiestaGestione.dmalmProjectFk02.eq(history
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
													r.setAnnullato("");
													r.setDtAnnullamento(null);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													RichiestaGestioneDAO
															.updateTask(r);
												} else {
													RichiestaGestioneDAO
															.updateRank(r,
																	new Double(
																			0));
													r.setAnnullato("");
													r.setDtStoricizzazione(dataEsecuzione);
													r.setDtAnnullamento(null);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													RichiestaGestioneDAO
															.insertRichiestaGestioneUpdate(
																	dataEsecuzione,
																	r, false);
												}
											}
										}
									}
									break;
								case "anomalia_assistenza":
									pk = query
											.from(anomaliaAssistenza)
											.where(anomaliaAssistenza.dmalmProjectFk02.eq(history
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
													a.setAnnullato("");
													a.setDtAnnullamento(null);
													a.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													AnomaliaAssistenzaDAO
															.updateAnomaliaAssistenza(a);
												} else {
													AnomaliaAssistenzaDAO
															.updateRank(a,
																	new Double(
																			0));
													a.setAnnullato("");
													a.setDtStoricizzazione(dataEsecuzione);
													a.setDtAnnullamento(null);
													a.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													AnomaliaAssistenzaDAO
															.insertAnomaliaAssistenzaUpdate(
																	dataEsecuzione,
																	a, false);
												}
											}
										}
									}
									break;
								case "release_ser":
									pk = query
											.from(releaseServizi)
											.where(releaseServizi.dmalmProjectFk02.eq(history
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
													r.setAnnullato("");
													r.setDtAnnullamento(null);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ReleaseServiziDAO
															.updateReleaseServizi(r);
												} else {
													ReleaseServiziDAO
															.updateRank(r,
																	new Double(
																			0));
													r.setAnnullato("");
													r.setDtStoricizzazione(dataEsecuzione);
													r.setDtAnnullamento(null);
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ReleaseServiziDAO
															.insertReleaseServiziUpdate(
																	dataEsecuzione,
																	r, false);
												}
											}
										}
									}
									break;

								case "classificatore_demand":
									pk = query
											.from(classificatore)
											.where(classificatore.dmalmProjectFk02.eq(history
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
													c.setAnnullato("");
													c.setDtAnnullamento(null);
													c.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ClassificatoreDAO
															.updateClass(c);
												} else {
													ClassificatoreDAO
															.updateRank(c,
																	new Double(
																			0));
													c.setAnnullato("");
													c.setDtStoricizzazione(dataEsecuzione);
													c.setDtAnnullamento(null);
													c.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ClassificatoreDAO
															.insertClassUpdate(
																	dataEsecuzione,
																	c, false);
												}
											}
										}
									}
									break;

								}

							} catch (Exception e) {
								ErrorManager.getInstance().exceptionOccurred(
										true, e);

							} finally {
								if (cm != null)
									cm.closeConnection(conn);
							}
						}
					}
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}

	}

	public static void annullaWorkitemFigli() throws DAOException,
			PropertiesReaderException {
		try {

			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			Timestamp dataChiusura = DateUtils.addSecondsToTimestamp(
					dataEsecuzione, -1);
			String unmarked = DmAlmConstants.LOGICAMENTE;
			List<DmalmProject> pNew = ProjectSgrCmDAO.getProjectToLinkWi(
					unmarked, dataEsecuzione);

			for (Workitem_Type type : Workitem_Type.values()) {
				ConnectionManager cm = null;
				Connection conn = null;
				List<Integer> pk = new ArrayList<Integer>();
				if (pNew != null) {
					for (DmalmProject p : pNew) {
						String idProject = p.getIdProject();
						String repo = p.getIdRepository();
						List<DmalmProject> pHistory = ProjectSgrCmDAO
								.getHistoryProject(idProject, repo,
										dataChiusura);
						if (pHistory != null) {
							for (DmalmProject history : pHistory) {

								try {
									cm = ConnectionManager.getInstance();
									conn = cm.getConnectionOracle();

									SQLQuery query = new SQLQuery(conn, dialect);

									switch (type.name()) {
									case "anomalia":
										pk = query
												.from(anomalia)
												.where(anomalia.dmalmProjectFk02.eq(history
														.getDmalmProjectPk()))
												.where(anomalia.rankStatoAnomalia
														.eq(new Double(1)))
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
														AnomaliaProdottoDAO
																.updateDmalmAnomaliaProdotto(a);
													} else {
														AnomaliaProdottoDAO
																.updateRankFlagUltimaSituazione(
																		a,
																		new Double(
																				0),
																		new Short(
																				"0"));
														a.setAnnullato(unmarked);
														a.setDtStoricizzazione(dataEsecuzione);
														a.setDtAnnullamento(dataEsecuzione);
														a.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														AnomaliaProdottoDAO
																.insertAnomaliaProdottoUpdate(
																		dataEsecuzione,
																		a,
																		false);
													}
												}
											}
										}
										break;
									case "defect":
										pk = query
												.from(difetto)
												.where(difetto.dmalmProjectFk02.eq(history
														.getDmalmProjectPk()))
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
														DifettoDAO
																.updateDmalmDifettoProdotto(d);
													} else {
														DifettoDAO
																.updateRankFlagUltimaSituazione(
																		d,
																		new Double(
																				0),
																		new Short(
																				"0"));
														d.setAnnullato(unmarked);
														d.setDtStoricizzazione(dataEsecuzione);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														DifettoDAO
																.insertDifettoProdottoUpdate(
																		dataEsecuzione,
																		d,
																		false);
													}
												}
											}
										}
										break;
									case "srqs":
										pk = query
												.from(progettoSviluppoSvil)
												.where(progettoSviluppoSvil.dmalmProjectFk02.eq(history
														.getDmalmProjectPk()))
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
														ProgettoSviluppoSviluppoDAO
																.updateProgettoSviluppoSvil(pss);
													} else {
														ProgettoSviluppoSviluppoDAO
																.updateRank(
																		pss,
																		new Double(
																				0));
														pss.setAnnullato(unmarked);
														pss.setDtStoricizzazione(dataEsecuzione);
														pss.setDtAnnullamento(dataEsecuzione);
														pss.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgettoSviluppoSviluppoDAO
																.insertProgettoSviluppoSvilUpdate(
																		dataEsecuzione,
																		pss,
																		false);
													}
												}
											}
										}
										break;
									case "documento":
										pk = query
												.from(documento)
												.where(documento.dmalmProjectFk02.eq(history
														.getDmalmProjectPk()))
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
														DocumentoDAO
																.updateDocumento(d);
													} else {
														DocumentoDAO
																.updateRank(
																		d,
																		new Double(
																				0));
														d.setAnnullato(unmarked);
														d.setDtStoricizzazione(dataEsecuzione);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														DocumentoDAO
																.insertDocumentoUpdate(
																		dataEsecuzione,
																		d,
																		false);
													}
												}
											}
										}
										break;
									case "sman":
										pk = query
												.from(manutenzione)
												.where(manutenzione.dmalmProjectFk02.eq(history
														.getDmalmProjectPk()))
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
														ManutenzioneDAO
																.updateManutenzione(m);
													} else {
														ManutenzioneDAO
																.updateRank(
																		m,
																		new Double(
																				0));
														m.setAnnullato(unmarked);
														m.setDtStoricizzazione(dataEsecuzione);
														m.setDtAnnullamento(dataEsecuzione);
														m.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ManutenzioneDAO
																.insertManutenzioneUpdate(
																		dataEsecuzione,
																		m,
																		false);
													}
												}
											}
										}
										break;
									case "testcase":
										pk = query
												.from(testcase)
												.where(testcase.dmalmProjectFk02.eq(history
														.getDmalmProjectPk()))
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
														TestCaseDAO
																.updateTestCase(t);
													} else {
														TestCaseDAO.updateRank(
																t,
																new Double(0));
														t.setAnnullato(unmarked);
														t.setDtStoricizzazione(dataEsecuzione);
														t.setDtAnnullamento(dataEsecuzione);
														t.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														TestCaseDAO
																.insertTestCaseUpdate(
																		dataEsecuzione,
																		t,
																		false);
													}
												}
											}
										}
										break;
									case "task":
										pk = query
												.from(task)
												.where(task.dmalmProjectFk02.eq(p
														.getDmalmProjectPk()))
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
														TaskDAO.updateTask(t);
													} else {
														TaskDAO.updateRank(t,
																new Double(0));
														t.setAnnullato(unmarked);
														t.setDtStoricizzazione(dataEsecuzione);
														t.setDtAnnullamento(dataEsecuzione);
														t.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														TaskDAO.insertTaskUpdate(
																dataEsecuzione,
																t, false);
													}
												}
											}
										}
										break;
									case "release":
										pk = query
												.from(releaseDiProgetto)
												.where(releaseDiProgetto.dmalmProjectFk02.eq(history
														.getDmalmProjectPk()))
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
														ReleaseDiProgettoDAO
																.updateReleaseDiProgetto(r);
													} else {
														ReleaseDiProgettoDAO
																.updateRank(
																		r,
																		new Double(
																				0));
														r.setAnnullato(unmarked);
														r.setDtStoricizzazione(dataEsecuzione);
														r.setDtAnnullamento(dataEsecuzione);
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ReleaseDiProgettoDAO
																.insertReleaseDiProgettoUpdate(
																		dataEsecuzione,
																		r,
																		false);
													}
												}
											}
										}
										break;
									case "programma":
										pk = query
												.from(programma)
												.where(programma.dmalmProjectFk02.eq(history
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
														ProgrammaDAO
																.updateProgramma(d);
													} else {
														ProgrammaDAO
																.updateRank(
																		d,
																		new Double(
																				0));
														d.setAnnullato(unmarked);
														d.setDtStoricizzazione(dataEsecuzione);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgrammaDAO
																.insertProgrammaUpdate(
																		dataEsecuzione,
																		d,
																		false);
													}
												}
											}
										}
										break;
									case "sottoprogramma":
										pk = query
												.from(sottoprogramma)
												.where(sottoprogramma.dmalmProjectFk02.eq(history
														.getDmalmProjectPk()))
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
														SottoprogrammaDAO
																.updateSottoprogramma(s);
													} else {
														SottoprogrammaDAO
																.updateRank(
																		s,
																		new Double(
																				0));
														s.setAnnullato(unmarked);
														s.setDtStoricizzazione(dataEsecuzione);
														s.setDtAnnullamento(dataEsecuzione);
														s.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														SottoprogrammaDAO
																.insertSottoprogrammaUpdate(
																		dataEsecuzione,
																		s,
																		false);
													}
												}
											}
										}
										break;
									case "rqd":
										pk = query
												.from(progettoDemand)
												.where(progettoDemand.dmalmProjectFk02.eq(history
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
														ProgettoDemandDAO
																.updateProgettoDemand(d);
													} else {
														ProgettoDemandDAO
																.updateRank(
																		d,
																		new Double(
																				0));
														d.setAnnullato(unmarked);
														d.setDtStoricizzazione(dataEsecuzione);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgettoDemandDAO
																.insertProgettoDemandUpdate(
																		dataEsecuzione,
																		d,
																		false);
													}
												}
											}
										}
										break;
									case "drqs":
										pk = query
												.from(progettoSviluppoDem)
												.where(progettoSviluppoDem.dmalmProjectFk02.eq(history
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
														ProgettoSviluppoDemandDAO
																.updateProgettoSviluppoDem(d);
													} else {
														ProgettoSviluppoDemandDAO
																.updateRank(
																		d,
																		new Double(
																				0));
														d.setAnnullato(unmarked);
														d.setDtStoricizzazione(dataEsecuzione);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgettoSviluppoDemandDAO
																.insertProgettoSviluppoDemUpdate(
																		dataEsecuzione,
																		d,
																		false);
													}
												}
											}
										}
										break;
									case "dman":
										pk = query
												.from(richiestaManutenzione)
												.where(richiestaManutenzione.dmalmProjectFk02.eq(history
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
														RichiestaManutenzioneDAO
																.updateRichiestaManutenzione(r);
													} else {
														RichiestaManutenzioneDAO
																.updateRank(
																		r,
																		new Double(
																				0));
														r.setAnnullato(unmarked);
														r.setDtStoricizzazione(dataEsecuzione);
														r.setDtAnnullamento(dataEsecuzione);
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														RichiestaManutenzioneDAO
																.insertRichiestaManutenzioneUpdate(
																		dataEsecuzione,
																		r,
																		false);
													}
												}
											}
										}
										break;
									case "fase":
										pk = query
												.from(fase)
												.where(fase.dmalmProjectFk02.eq(history
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
														FaseDAO.updateFase(f);
													} else {
														FaseDAO.updateRank(f,
																new Double(0));
														f.setAnnullato(unmarked);
														f.setDtStoricizzazione(dataEsecuzione);
														f.setDtAnnullamento(dataEsecuzione);
														f.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														FaseDAO.insertFaseUpdate(
																dataEsecuzione,
																f, false);
													}
												}
											}
										}
										break;
									case "pei":
										pk = query
												.from(pei)
												.where(pei.dmalmProjectFk02.eq(history
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
														PeiDAO.updatePei(f);
													} else {
														PeiDAO.updateRank(f,
																new Double(0));
														f.setAnnullato(unmarked);
														f.setDtStoricizzazione(dataEsecuzione);
														f.setDtAnnullamento(dataEsecuzione);
														f.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														PeiDAO.insertPeiUpdate(
																dataEsecuzione,
																f, false);
													}
												}

											}
										}
										break;
									case "progettoese":
										pk = query
												.from(progettoEse)
												.where(progettoEse.dmalmProjectFk02.eq(history
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
														ProgettoEseDAO
																.updateProgettoEse(d);
													} else {
														ProgettoEseDAO
																.updateRank(
																		d,
																		new Double(
																				0));
														d.setAnnullato(unmarked);
														d.setDtStoricizzazione(dataEsecuzione);
														d.setDtAnnullamento(dataEsecuzione);
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgettoEseDAO
																.insertProgettoEseUpdate(
																		dataEsecuzione,
																		d,
																		false);
													}
												}
											}
										}
										break;
									case "release_it":
										pk = query
												.from(releaseIT)
												.where(releaseIT.dmalmProjectFk02.eq(history
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
														ReleaseItDAO
																.updateReleaseIt(r);
													} else {
														ReleaseItDAO
																.updateRank(
																		r,
																		new Double(
																				0));
														r.setAnnullato(unmarked);
														r.setDtStoricizzazione(dataEsecuzione);
														r.setDtAnnullamento(dataEsecuzione);
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ReleaseItDAO
																.insertReleaseItUpdate(
																		dataEsecuzione,
																		r,
																		false);
													}
												}
											}
										}
										break;
									case "build":
										pk = query
												.from(build)
												.where(build.dmalmProjectFk02.eq(history
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
														BuildDAO.updateBuild(b);
													} else {
														BuildDAO.updateRank(b,
																new Double(0));
														b.setAnnullato(unmarked);
														b.setDtStoricizzazione(dataEsecuzione);
														b.setDtAnnullamento(dataEsecuzione);
														b.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														BuildDAO.insertBuildUpdate(
																dataEsecuzione,
																b, false);
													}
												}
											}
										}
										break;
									case "taskit":
										pk = query
												.from(taskIT)
												.where(taskIT.dmalmProjectFk02.eq(history
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
														TaskItDAO
																.updateTaskIt(t);
													} else {
														TaskItDAO.updateRank(t,
																new Double(0));
														t.setAnnullato(unmarked);
														t.setDtStoricizzazione(dataEsecuzione);
														t.setDtAnnullamento(dataEsecuzione);
														t.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														TaskItDAO
																.insertTaskItUpdate(
																		dataEsecuzione,
																		t,
																		false);
													}
												}
											}
										}
										break;
									case "richiesta_gestione":
										pk = query
												.from(richiestaGestione)
												.where(richiestaGestione.dmalmProjectFk02.eq(history
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
														RichiestaGestioneDAO
																.updateTask(r);
													} else {
														RichiestaGestioneDAO
																.updateRank(
																		r,
																		new Double(
																				0));
														r.setAnnullato(unmarked);
														r.setDtStoricizzazione(dataEsecuzione);
														r.setDtAnnullamento(dataEsecuzione);
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														RichiestaGestioneDAO
																.insertRichiestaGestioneUpdate(
																		dataEsecuzione,
																		r,
																		false);
													}
												}
											}
										}
										break;
									case "anomalia_assistenza":
										pk = query
												.from(anomaliaAssistenza)
												.where(anomaliaAssistenza.dmalmProjectFk02.eq(history
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
														AnomaliaAssistenzaDAO
																.updateAnomaliaAssistenza(a);
													} else {
														AnomaliaAssistenzaDAO
																.updateRank(
																		a,
																		new Double(
																				0));
														a.setAnnullato(unmarked);
														a.setDtStoricizzazione(dataEsecuzione);
														a.setDtAnnullamento(dataEsecuzione);
														a.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														AnomaliaAssistenzaDAO
																.insertAnomaliaAssistenzaUpdate(
																		dataEsecuzione,
																		a,
																		false);
													}
												}
											}
										}
										break;
									case "release_ser":
										pk = query
												.from(releaseServizi)
												.where(releaseServizi.dmalmProjectFk02.eq(history
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
														ReleaseServiziDAO
																.updateReleaseServizi(r);
													} else {
														ReleaseServiziDAO
																.updateRank(
																		r,
																		new Double(
																				0));
														r.setAnnullato(unmarked);
														r.setDtStoricizzazione(dataEsecuzione);
														r.setDtAnnullamento(dataEsecuzione);
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ReleaseServiziDAO
																.insertReleaseServiziUpdate(
																		dataEsecuzione,
																		r,
																		false);
													}
												}
											}
										}
										break;

									case "classificatore_demand":
										pk = query
												.from(classificatore)
												.where(classificatore.dmalmProjectFk02.eq(history
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
														ClassificatoreDAO
																.updateClass(c);
													} else {
														ClassificatoreDAO
																.updateRank(
																		c,
																		new Double(
																				0));
														c.setAnnullato(unmarked);
														c.setDtStoricizzazione(dataEsecuzione);
														c.setDtAnnullamento(dataEsecuzione);
														c.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ClassificatoreDAO
																.insertClassUpdate(
																		dataEsecuzione,
																		c,
																		false);
													}
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
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}

	}

	public static void annullaWorkitemDeleted() throws DAOException,
			PropertiesReaderException, SQLException {

		ConnectionManager cm = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			logger.debug("START ANNULLAMENTO DELETED WORKITEMS ");

			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SET_ANNULLATO_DELETED_WORKITEM);
			ps = conn.prepareStatement(sql);

			rs = ps.executeQuery();
			logger.debug("ESEGUITO: " + sql);

			SQLUpdateClause update_anomalia = new SQLUpdateClause(conn,
					dialect, anomalia);
			SQLUpdateClause update_testcase = new SQLUpdateClause(conn,
					dialect, testcase);
			SQLUpdateClause update_task = new SQLUpdateClause(conn, dialect,
					task);
			SQLUpdateClause update_taskIT = new SQLUpdateClause(conn, dialect,
					taskIT);
			SQLUpdateClause update_progetto_svil_s = new SQLUpdateClause(conn,
					dialect, progettoSviluppoSvil);
			SQLUpdateClause update_progetto_dem = new SQLUpdateClause(conn,
					dialect, progettoDemand);
			SQLUpdateClause update_progetto_svil_d = new SQLUpdateClause(conn,
					dialect, progettoSviluppoDem);
			SQLUpdateClause update_pei = new SQLUpdateClause(conn, dialect, pei);
			SQLUpdateClause update_build = new SQLUpdateClause(conn, dialect,
					build);
			SQLUpdateClause update_release = new SQLUpdateClause(conn, dialect,
					releaseDiProgetto);
			SQLUpdateClause update_releaseIT = new SQLUpdateClause(conn,
					dialect, releaseIT);
			SQLUpdateClause update_progetto_ese = new SQLUpdateClause(conn,
					dialect, progettoEse);
			SQLUpdateClause update_programma = new SQLUpdateClause(conn,
					dialect, programma);
			SQLUpdateClause update_sottoprogramma = new SQLUpdateClause(conn,
					dialect, sottoprogramma);
			SQLUpdateClause update_fase = new SQLUpdateClause(conn, dialect,
					fase);
			SQLUpdateClause update_richiesta_gestione = new SQLUpdateClause(
					conn, dialect, richiestaGestione);
			SQLUpdateClause update_richiesta_man = new SQLUpdateClause(conn,
					dialect, richiestaManutenzione);
			SQLUpdateClause update_manutenzione = new SQLUpdateClause(conn,
					dialect, manutenzione);
			SQLUpdateClause update_rel_ser = new SQLUpdateClause(conn, dialect,
					releaseServizi);
			SQLUpdateClause update_difetto = new SQLUpdateClause(conn, dialect,
					difetto);
			SQLUpdateClause update_documento = new SQLUpdateClause(conn,
					dialect, documento);
			SQLUpdateClause update_anomalia_ass = new SQLUpdateClause(conn,
					dialect, anomaliaAssistenza);
			SQLUpdateClause update_classificatore = new SQLUpdateClause(
					conn, dialect, classificatore);

			while (rs.next()) {
				String targetTipologiaWi = "DMALM_WI_GENERICO";

				switch (rs.getString("TYPE")) {
				case "testcase":
					update_testcase
							.where(testcase.cdTestcase.eq(rs
									.getString("CODICE")))
							.where(testcase.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(testcase.annullato, DmAlmConstants.FISICAMENTE)
							.set(testcase.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_testcase = new SQLUpdateClause(conn, dialect,
							testcase);

					targetTipologiaWi = DmAlmConstants.TARGET_TESTCASE;
					break;
				case "pei":
					update_pei
							.where(pei.cdPei.eq(rs.getString("CODICE")))
							.where(pei.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(pei.annullato, DmAlmConstants.FISICAMENTE)
							.set(pei.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_pei = new SQLUpdateClause(conn, dialect, pei);

					targetTipologiaWi = DmAlmConstants.TARGET_PEI;
					break;
				case "build":
					update_build
							.where(build.cdBuild.eq(rs.getString("CODICE")))
							.where(build.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(build.annullato, DmAlmConstants.FISICAMENTE)
							.set(build.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_build = new SQLUpdateClause(conn, dialect, build);

					targetTipologiaWi = DmAlmConstants.TARGET_BUILD;
					break;
				case "richiesta_gestione":
					update_richiesta_gestione
							.where(richiestaGestione.cdRichiestaGest.eq(rs
									.getString("CODICE")))
							.where(richiestaGestione.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(richiestaGestione.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(richiestaGestione.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_richiesta_gestione = new SQLUpdateClause(conn,
							dialect, richiestaGestione);

					targetTipologiaWi = DmAlmConstants.TARGET_RICHIESTA_GESTIONE;
					break;
				case "progettoese":
					update_progetto_ese
							.where(progettoEse.cdProgettoEse.eq(rs
									.getString("CODICE")))
							.where(progettoEse.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(progettoEse.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(progettoEse.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_progetto_ese = new SQLUpdateClause(conn, dialect,
							progettoEse);

					targetTipologiaWi = DmAlmConstants.TARGET_PROGETTO_ESE;
					break;
				case "fase":
					update_fase
							.where(fase.cdFase.eq(rs.getString("CODICE")))
							.where(fase.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(fase.annullato, DmAlmConstants.FISICAMENTE)
							.set(fase.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_fase = new SQLUpdateClause(conn, dialect, fase);

					targetTipologiaWi = DmAlmConstants.TARGET_FASE;
					break;
				case "sottoprogramma":
					update_sottoprogramma
							.where(sottoprogramma.cdSottoprogramma.eq(rs
									.getString("CODICE")))
							.where(sottoprogramma.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(sottoprogramma.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(sottoprogramma.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_sottoprogramma = new SQLUpdateClause(conn, dialect,
							sottoprogramma);

					targetTipologiaWi = DmAlmConstants.TARGET_SOTTOPROGRAMMA;
					break;
				case "programma":
					update_programma
							.where(programma.cdProgramma.eq(rs
									.getString("CODICE")))
							.where(programma.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(programma.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(programma.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_programma = new SQLUpdateClause(conn, dialect,
							programma);

					targetTipologiaWi = DmAlmConstants.TARGET_PROGRAMMA;
					break;
				case "taskit":
					update_taskIT
							.where(taskIT.cdTaskIt.eq(rs.getString("CODICE")))
							.where(taskIT.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(taskIT.annullato, DmAlmConstants.FISICAMENTE)
							.set(taskIT.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_taskIT = new SQLUpdateClause(conn, dialect, taskIT);

					targetTipologiaWi = DmAlmConstants.TARGET_TASKIT;
					break;
				case "release_ser":
					update_rel_ser
							.where(releaseServizi.cdRelServizi.eq(rs
									.getString("CODICE")))
							.where(releaseServizi.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(releaseServizi.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(releaseServizi.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_rel_ser = new SQLUpdateClause(conn, dialect,
							releaseServizi);

					targetTipologiaWi = DmAlmConstants.TARGET_RELEASE_SERVIZI;
					break;
				case "drqs":
					update_progetto_svil_d
							.where(progettoSviluppoDem.cdProgSvilD.eq(rs
									.getString("CODICE")))
							.where(progettoSviluppoDem.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(progettoSviluppoDem.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(progettoSviluppoDem.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_progetto_svil_d = new SQLUpdateClause(conn, dialect,
							progettoSviluppoDem);

					targetTipologiaWi = DmAlmConstants.TARGET_PROGETTO_SVILUPPO_DEMAND;
					break;
				case "dman":
					update_richiesta_man
							.where(richiestaManutenzione.cdRichiestaManutenzione
									.eq(rs.getString("CODICE")))
							.where(richiestaManutenzione.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(richiestaManutenzione.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(richiestaManutenzione.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_richiesta_man = new SQLUpdateClause(conn, dialect,
							richiestaManutenzione);

					targetTipologiaWi = DmAlmConstants.TARGET_RICHIESTA_MANUTENZIONE;
					break;
				case "rqd":
					update_progetto_dem
							.where(progettoDemand.cdProgettoDemand.eq(rs
									.getString("CODICE")))
							.where(progettoDemand.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(progettoDemand.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(progettoDemand.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_progetto_dem = new SQLUpdateClause(conn, dialect,
							progettoDemand);

					targetTipologiaWi = DmAlmConstants.TARGET_PROGETTO_DEMAND;
					break;
				case "anomalia":
					update_anomalia
							.where(anomalia.cdAnomalia.eq(rs
									.getString("CODICE")))
							.where(anomalia.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(anomalia.annullato, DmAlmConstants.FISICAMENTE)
							.set(anomalia.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_anomalia = new SQLUpdateClause(conn, dialect,
							anomalia);

					targetTipologiaWi = DmAlmConstants.TARGET_ANOMALIA;
					break;
				case "anomalia_assistenza":
					update_anomalia_ass
							.where(anomaliaAssistenza.cdAnomaliaAss.eq(rs
									.getString("CODICE")))
							.where(anomaliaAssistenza.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(anomaliaAssistenza.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(anomaliaAssistenza.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_anomalia_ass = new SQLUpdateClause(conn, dialect,
							anomaliaAssistenza);

					targetTipologiaWi = DmAlmConstants.TARGET_ANOMALIA_ASSISTENZA;
					break;
				case "srqs":
					update_progetto_svil_s
							.where(progettoSviluppoSvil.cdProgSvilS.eq(rs
									.getString("CODICE")))
							.where(progettoSviluppoSvil.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(progettoSviluppoSvil.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(progettoSviluppoSvil.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_progetto_svil_s = new SQLUpdateClause(conn, dialect,
							progettoSviluppoSvil);

					targetTipologiaWi = DmAlmConstants.TARGET_PROGETTO_SVILUPPO_SVILUPPO;
					break;
				case "task":
					update_task
							.where(task.cdTask.eq(rs.getString("CODICE")))
							.where(task.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(task.annullato, DmAlmConstants.FISICAMENTE)
							.set(task.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_task = new SQLUpdateClause(conn, dialect, task);

					targetTipologiaWi = DmAlmConstants.TARGET_TASK;
					break;
				case "defect":
					update_difetto
							.where(difetto.cdDifetto.eq(rs.getString("CODICE")))
							.where(difetto.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(difetto.annullato, DmAlmConstants.FISICAMENTE)
							.set(difetto.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_difetto = new SQLUpdateClause(conn, dialect, difetto);

					targetTipologiaWi = DmAlmConstants.TARGET_DIFETTO;
					break;
				case "sman":
					update_manutenzione
							.where(manutenzione.cdManutenzione.eq(rs
									.getString("CODICE")))
							.where(manutenzione.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(manutenzione.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(manutenzione.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_manutenzione = new SQLUpdateClause(conn, dialect,
							manutenzione);

					targetTipologiaWi = DmAlmConstants.TARGET_MANUTENZIONE;
					break;
				case "release_it":
					update_releaseIT
							.where(releaseIT.cdReleaseIt.eq(rs
									.getString("CODICE")))
							.where(releaseIT.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(releaseIT.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(releaseIT.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_releaseIT = new SQLUpdateClause(conn, dialect,
							releaseIT);

					targetTipologiaWi = DmAlmConstants.TARGET_RELEASE_IT;
					break;
				case "documento":
					update_documento
							.where(documento.cdDocumento.eq(rs
									.getString("CODICE")))
							.where(documento.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(documento.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(documento.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_documento = new SQLUpdateClause(conn, dialect,
							documento);

					targetTipologiaWi = DmAlmConstants.TARGET_DOCUMENTO;
					break;
				case "release":
					update_release
							.where(releaseDiProgetto.cdReleasediprog.eq(rs
									.getString("CODICE")))
							.where(releaseDiProgetto.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(releaseDiProgetto.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(releaseDiProgetto.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_release = new SQLUpdateClause(conn, dialect,
							releaseDiProgetto);

					targetTipologiaWi = DmAlmConstants.TARGET_RELEASE_DI_PROG;
					break;

				case "classificatore":
					update_classificatore
							.where(classificatore.cd_classificatore.eq(rs
									.getString("CODICE")))
							.where(classificatore.idRepository.eq(rs
									.getString("ID_REPOSITORY")))
							.set(classificatore.annullato,
									DmAlmConstants.FISICAMENTE)
							.set(classificatore.dtAnnullamento,
									DataEsecuzione.getInstance()
											.getDataEsecuzione()).execute();
					update_classificatore = new SQLUpdateClause(conn,
							dialect, classificatore);

					targetTipologiaWi = DmAlmConstants.TARGET_CLASSIFICATORE;
					break;
				default:
					logger.info("Tipologia Workitem non gestita - "
							+ "codiceWi: " + rs.getString("CODICE")
							+ ", tipoWi: " + rs.getString("TYPE")
							+ ", idRepository: "
							+ rs.getString("ID_REPOSITORY")
							+ ", targetTipologiaWi: " + targetTipologiaWi);
				}

				conn.commit();

				try {
					logger.debug("ErroriCaricamentoDAO.insert - "
							+ "codiceWi: " + rs.getString("CODICE")
							+ ", tipoWi: " + rs.getString("TYPE")
							+ ", idRepository: "
							+ rs.getString("ID_REPOSITORY")
							+ ", targetTipologiaWi: " + targetTipologiaWi);

					ErroriCaricamentoDAO.insert(
							DmAlmConstants.FONTE_SGR_CURRENT_WORKITEMS,
							targetTipologiaWi,
							"codiceWi: " + rs.getString("CODICE")
									+ "§ tipoWi: " + rs.getString("TYPE")
									+ "§ idRepository: "
									+ rs.getString("ID_REPOSITORY"),
							DmAlmConstants.SGR_WI_ANNULLATO_FISICAMENTE,
							DmAlmConstants.FLAG_ERRORE_NON_BLOCCANTE,
							DataEsecuzione.getInstance().getDataEsecuzione());
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}

			logger.debug("STOP ANNULLAMENTO DELETED WORKITEMS ");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {

			if (ps != null)
				ps.close();
			if (cm != null)
				cm.closeConnection(conn);
		}

	}

}
