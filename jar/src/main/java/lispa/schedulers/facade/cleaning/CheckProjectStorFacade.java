package lispa.schedulers.facade.cleaning;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaAssistenza;
import lispa.schedulers.bean.target.fatti.DmalmBuild;
import lispa.schedulers.bean.target.fatti.DmalmClassificatore;
import lispa.schedulers.bean.target.fatti.DmalmDocumento;
import lispa.schedulers.bean.target.fatti.DmalmFase;
import lispa.schedulers.bean.target.fatti.DmalmPei;
import lispa.schedulers.bean.target.fatti.DmalmProgettoEse;
import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoDem;
import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoSvil;
import lispa.schedulers.bean.target.fatti.DmalmReleaseIt;
import lispa.schedulers.bean.target.fatti.DmalmReleaseServizi;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaGestione;
import lispa.schedulers.bean.target.fatti.DmalmTask;
import lispa.schedulers.bean.target.fatti.DmalmTaskIt;
import lispa.schedulers.bean.target.fatti.DmalmTestcase;
import lispa.schedulers.dao.target.ProjectSgrCmDAO;
import lispa.schedulers.dao.target.fatti.AnomaliaAssistenzaDAO;
import lispa.schedulers.dao.target.fatti.BuildDAO;
import lispa.schedulers.dao.target.fatti.ClassificatoreDAO;
import lispa.schedulers.dao.target.fatti.DocumentoDAO;
import lispa.schedulers.dao.target.fatti.FaseDAO;
import lispa.schedulers.dao.target.fatti.PeiDAO;
import lispa.schedulers.dao.target.fatti.ProgettoEseDAO;
import lispa.schedulers.dao.target.fatti.ProgettoSviluppoDemandDAO;
import lispa.schedulers.dao.target.fatti.ProgettoSviluppoSviluppoDAO;
import lispa.schedulers.dao.target.fatti.ReleaseItDAO;
import lispa.schedulers.dao.target.fatti.ReleaseServiziDAO;
import lispa.schedulers.dao.target.fatti.RichiestaGestioneDAO;
import lispa.schedulers.dao.target.fatti.TaskDAO;
import lispa.schedulers.dao.target.fatti.TaskItDAO;
import lispa.schedulers.dao.target.fatti.TestCaseDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.ExecutionManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmBuild;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmClassificatore;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDocumento;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmFase;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmPei;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoEse;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoDem;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoSvil;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseIt;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseServizi;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaGestione;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTask;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTaskIt;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTestcase;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;

import org.apache.log4j.Logger;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;

public class CheckProjectStorFacade {

	private static Logger logger = Logger.getLogger(CheckProjectStorFacade.class);
	private static QDmalmProgettoSviluppoSvil progettoSviluppoSvil = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;
	private static QDmalmDocumento documento = QDmalmDocumento.dmalmDocumento;
	private static QDmalmTestcase testcase = QDmalmTestcase.dmalmTestcase;
	private static QDmalmTask task = QDmalmTask.dmalmTask;
	private static QDmalmProgettoSviluppoDem progettoSviluppoDem = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;
	private static QDmalmFase fase = QDmalmFase.dmalmFase;
	private static QDmalmPei pei = QDmalmPei.dmalmPei;
	private static QDmalmProgettoEse progettoEse = QDmalmProgettoEse.dmalmProgettoEse;
	private static QDmalmReleaseIt releaseIT = QDmalmReleaseIt.dmalmReleaseIt;
	private static QDmalmBuild build = QDmalmBuild.dmalmBuild;
	private static QDmalmTaskIt taskIT = QDmalmTaskIt.dmalmTaskIt;
	private static QDmalmRichiestaGestione richiestaGestione = QDmalmRichiestaGestione.dmalmRichiestaGestione;
	private static QDmalmAnomaliaAssistenza anomaliaAssistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;
	private static QDmalmReleaseServizi releaseServizi = QDmalmReleaseServizi.dmalmReleaseServizi;
	private static QDmalmClassificatore classificatoreDem = QDmalmClassificatore.dmalmClassificatore;
	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void execute() {

		// ELETTRA/SGRCM
		if (!ExecutionManager.getInstance().isExecutionElettraSgrcm())
			return;

		// Se si è verificato un errore precedente non eseguo l'elaborazione
		if (ErrorManager.getInstance().hasError()) {
			return;
		}

		logger.info("START CheckProjectStorFacade.execute()");
		List<DmalmProject> pNew = new ArrayList<DmalmProject>();
		
		try {
			Timestamp dataEsecuzione = DataEsecuzione.getInstance()
					.getDataEsecuzione();
			// dtchiusura deve essere la beetween delle ultime due date
			// dello staging
			Timestamp dataChiusura = DateUtils.addSecondsToTimestamp(
					dataEsecuzione, -1);

			pNew = ProjectSgrCmDAO.getProjectNuovi(dataEsecuzione);

			if (pNew != null) {
				logger.debug("Storicizza WI figli di " + pNew.size()
						+ " Project");
				storicizzaWI(pNew, dataEsecuzione, dataChiusura);
			}
			storicizzaWI();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		}
		logger.info("STOP CheckProjectStorFacade");

	}

	private static void storicizzaWI(List<DmalmProject> pNew,
			Timestamp dataEsecuzione, Timestamp dataChiusura)
			throws DAOException, PropertiesReaderException {
		try {

			for (EnumWorkitemType type : EnumWorkitemType.values()) {
				ConnectionManager cm = null;
				Connection conn = null;
				List<Integer> pk = new ArrayList<Integer>();
				
				if(type.name().equals("anomalia")) {
					System.out.println("");
				} else if(type.name().equals("defect")) {
					System.out.println("");
				} else if(type.name().equals("sup")) {
					System.out.println("");
				} else if(type.name().equals("sman") || type.name().equals("dman")) {
					System.out.println("");
				} else if(type.name().equals("rqd")) {
					System.out.println("");
				} else if(type.name().equals("programma")) {
					System.out.println("");
				} else if(type.name().equals("release")) {
					System.out.println("");
				} else if(type.name().equals("sottoprogramma")) {
					System.out.println("");
				} else {
					logger.info("Storicizzo type: "+type);
				}
				for (DmalmProject p : pNew) {
					String idProject = p.getIdProject();
					String repo = p.getIdRepository();
					dataChiusura = DateUtils.addSecondsToTimestamp(
							p.getDtInizioValidita(), -1);
					List<DmalmProject> pHistory = ProjectSgrCmDAO
							.getHistoryProject(idProject, repo, dataChiusura);
					if (pHistory != null) {
						for (DmalmProject history : pHistory) {
							try {
								cm = ConnectionManager.getInstance();
								conn = cm.getConnectionOracle();

								SQLQuery query = new SQLQuery(conn, dialect);
								switch (type.name()) {
								case "srqs":
									QDmalmProgettoSviluppoSvil progetto2 = new QDmalmProgettoSviluppoSvil(
											"progetto2");
									pk = query
											.from(progettoSviluppoSvil)
											.where(progettoSviluppoSvil.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(progettoSviluppoSvil.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(progetto2)
															.where(progettoSviluppoSvil.dmalmProjectFk02
																	.eq(progetto2.dmalmProjectFk02))
															.where(progettoSviluppoSvil.cdProgSvilS
																	.eq(progetto2.cdProgSvilS))
															.list(progetto2.dtStoricizzazione
																	.max())))
											.orderBy(progettoSviluppoSvil.rankStatoProgSvilS.desc(), progettoSviluppoSvil.dtModificaProgSvilS.desc(), progettoSviluppoSvil.dmalmProgSvilSPk.desc())
											.list(progettoSviluppoSvil.dmalmProgSvilSPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmProgettoSviluppoSvil pss = ProgettoSviluppoSviluppoDAO
													.getProgettoSviluppoSviluppo(i);
											if (pss != null) {
												boolean exist = ProgettoSviluppoSviluppoDAO
														.checkEsistenzaProgetto(
																pss, p);
												if (!exist) {
													if (pss.getRankStatoProgSvilS() == 1) {
														ProgettoSviluppoSviluppoDAO
																.updateRank(
																		pss,
																		new Double(
																				0));
													}
													pss.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmDocumento documento2 = new QDmalmDocumento(
											"documento2");
									pk = query
											.from(documento)
											.where(documento.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(documento.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(documento2)
															.where(documento.dmalmProjectFk02
																	.eq(documento2.dmalmProjectFk02))
															.where(documento.cdDocumento
																	.eq(documento2.cdDocumento))
															.list(documento2.dtStoricizzazione
																	.max())))
											.orderBy(documento.rankStatoDocumento.desc(), documento.dtModificaDocumento.desc(), documento.dmalmDocumentoPk.desc())
											.list(documento.dmalmDocumentoPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmDocumento d = DocumentoDAO
													.getDocumento(i);
											if (d != null) {
												boolean exist = DocumentoDAO
														.checkEsistenzaDocumento(
																d, p);
												if (!exist) {
													if (d.getRankStatoDocumento() == 1) {
														DocumentoDAO
																.updateRank(
																		d,
																		new Double(
																				0));
													}
													d.setDtStoricizzazione(p
															.getDtInizioValidita());
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
								case "testcase":
									QDmalmTestcase testcase2 = new QDmalmTestcase(
											"testcase2");
									pk = query
											.from(testcase)
											.where(testcase.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(testcase.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(testcase2)
															.where(testcase.dmalmProjectFk02
																	.eq(testcase2.dmalmProjectFk02))
															.where(testcase.cdTestcase
																	.eq(testcase2.cdTestcase))
															.list(testcase2.dtStoricizzazione
																	.max())))
											.orderBy(testcase.rankStatoTestcase.desc(), testcase.dtModificaTestcase.desc(), testcase.dmalmTestcasePk.desc())
											.list(testcase.dmalmTestcasePk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmTestcase t = TestCaseDAO
													.getTestCase(i);
											if (t != null) {
												boolean exist = TestCaseDAO
														.checkEsistenzaTestCase(
																t, p);
												if (!exist) {
													if (t.getRankStatoTestcase() == 1) {
														TestCaseDAO.updateRank(
																t,
																new Double(0));
													}
													t.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmTask task2 = new QDmalmTask("task2");
									pk = query
											.from(task)
											.where(task.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(task.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(task2)
															.where(task.dmalmProjectFk02
																	.eq(task2.dmalmProjectFk02))
															.where(task.cdTask
																	.eq(task2.cdTask))
															.list(task2.dtStoricizzazione
																	.max())))
											.orderBy(task.rankStatoTask.desc(), task.dtModificaTask.desc(), task.dmalmTaskPk.desc())
											.list(task.dmalmTaskPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmTask t = TaskDAO.getTask(i);
											if (t != null) {
												boolean exist = TaskDAO
														.checkEsistenzaTask(t,
																p);
												if (!exist) {
													if (t.getRankStatoTask() == 1) {
														TaskDAO.updateRank(t,
																new Double(0));
													}
													t.setDtStoricizzazione(p
															.getDtInizioValidita());
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
								case "drqs":
									QDmalmProgettoSviluppoDem progettoSviluppoDem2 = new QDmalmProgettoSviluppoDem(
											"progettoSviluppoDem2");
									pk = query
											.from(progettoSviluppoDem)
											.where(progettoSviluppoDem.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(progettoSviluppoDem.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(progettoSviluppoDem2)
															.where(progettoSviluppoDem.dmalmProjectFk02
																	.eq(progettoSviluppoDem2.dmalmProjectFk02))
															.where(progettoSviluppoDem.cdProgSvilD
																	.eq(progettoSviluppoDem2.cdProgSvilD))
															.list(progettoSviluppoDem2.dtStoricizzazione
																	.max())))
											.orderBy(progettoSviluppoDem.rankStatoProgSvilD.desc(), progettoSviluppoDem.dtModificaProgSvilD.desc(), progettoSviluppoDem.dmalmProgSvilDPk.desc())
											.list(progettoSviluppoDem.dmalmProgSvilDPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmProgettoSviluppoDem d = ProgettoSviluppoDemandDAO
													.getProgettoSviluppoDemand(i);
											if (d != null) {
												boolean exist = ProgettoSviluppoDemandDAO
														.checkEsistenzaProgetto(
																d, p);
												if (!exist) {
													if (d.getRankStatoProgSvilD() == 1) {
														ProgettoSviluppoDemandDAO
																.updateRank(
																		d,
																		new Double(
																				0));
													}
													d.setDtStoricizzazione(p
															.getDtInizioValidita());
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
								case "fase":
									QDmalmFase fase2 = new QDmalmFase("fase2");
									pk = query
											.from(fase)
											.where(fase.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(fase.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(fase2)
															.where(fase.dmalmProjectFk02
																	.eq(fase2.dmalmProjectFk02))
															.where(fase.cdFase
																	.eq(fase2.cdFase))
															.list(fase2.dtStoricizzazione
																	.max())))
											.orderBy(fase.rankStatoFase.desc(), fase.dtModificaFase.desc(), fase.dmalmFasePk.desc())
											.list(fase.dmalmFasePk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmFase f = FaseDAO.getFase(i);
											if (f != null) {
												boolean exist = FaseDAO
														.checkEsistenzaFase(f,
																p);
												if (!exist) {
													if (f.getRankStatoFase() == 1) {
														FaseDAO.updateRank(f,
																new Double(0));
													}
													f.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmPei pei2 = new QDmalmPei("pei2");
									pk = query
											.from(pei)
											.where(pei.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(pei.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(pei2)
															.where(pei.dmalmProjectFk02
																	.eq(pei2.dmalmProjectFk02))
															.where(pei.cdPei
																	.eq(pei2.cdPei))
															.list(pei2.dtStoricizzazione
																	.max())))
											.orderBy(pei.rankStatoPei.desc(), pei.dtModificaPei.desc(), pei.dmalmPeiPk.desc())
											.list(pei.dmalmPeiPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmPei f = PeiDAO.getPei(i);
											if (f != null) {
												boolean exist = PeiDAO
														.checkEsistenzaPei(f, p);
												if (!exist) {
													if (f.getRankStatoPei() == 1) {
														PeiDAO.updateRank(f,
																new Double(0));
													}
													f.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmProgettoEse progettoEse2 = new QDmalmProgettoEse(
											"progettoEse2");
									pk = query
											.from(progettoEse)
											.where(progettoEse.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(progettoEse.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(progettoEse2)
															.where(progettoEse.dmalmProjectFk02
																	.eq(progettoEse2.dmalmProjectFk02))
															.where(progettoEse.cdProgettoEse
																	.eq(progettoEse2.cdProgettoEse))
															.list(progettoEse2.dtStoricizzazione
																	.max())))
											.orderBy(progettoEse.rankStatoProgettoEse.desc(), progettoEse.dtModificaProgettoEse.desc(), progettoEse.dmalmProgettoEsePk.desc())
											.list(progettoEse.dmalmProgettoEsePk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmProgettoEse d = ProgettoEseDAO
													.getProgettoEse(i);
											if (d != null) {
												boolean exist = ProgettoEseDAO
														.checkEsistenzaProgetto(
																d, p);
												if (!exist) {
													if (d.getRankStatoProgettoEse() == 1) {
														ProgettoEseDAO
																.updateRank(
																		d,
																		new Double(
																				0));
													}
													d.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmReleaseIt releaseIT2 = new QDmalmReleaseIt(
											"releaseIT2");
									pk = query
											.from(releaseIT)
											.where(releaseIT.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(releaseIT.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(releaseIT2)
															.where(releaseIT.dmalmProjectFk02
																	.eq(releaseIT2.dmalmProjectFk02))
															.where(releaseIT.cdReleaseIt
																	.eq(releaseIT2.cdReleaseIt))
															.list(releaseIT2.dtStoricizzazione
																	.max())))
											.orderBy(releaseIT.rankStatoReleaseIt.desc(), releaseIT.dtModificaReleaseIt.desc(), releaseIT.dmalmReleaseItPk.desc())
											.list(releaseIT.dmalmReleaseItPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmReleaseIt r = ReleaseItDAO
													.getReleaseIt(i);
											if (r != null) {
												boolean exist = ReleaseItDAO
														.checkEsistenzaRelease(
																r, p);
												if (!exist) {
													if (r.getRankStatoReleaseIt() == 1) {
														ReleaseItDAO
																.updateRank(
																		r,
																		new Double(
																				0));
													}
													r.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmBuild build2 = new QDmalmBuild(
											"build2");
									pk = query
											.from(build)
											.where(build.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(build.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(build2)
															.where(build.dmalmProjectFk02
																	.eq(build2.dmalmProjectFk02))
															.where(build.cdBuild
																	.eq(build2.cdBuild))
															.list(build2.dtStoricizzazione
																	.max())))
											.orderBy(build.rankStatoBuild.desc(), build.dtModificaBuild.desc(), build.dmalmBuildPk.desc())
											.list(build.dmalmBuildPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmBuild b = BuildDAO.getBuild(i);
											if (b != null) {
												boolean exist = BuildDAO
														.checkEsistenzaBuild(b,
																p);
												if (!exist) {
													if (b.getRankStatoBuild() == 1) {
														BuildDAO.updateRank(b,
																new Double(0));
													}
													b.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmTaskIt taskIT2 = new QDmalmTaskIt(
											"taskIT2");
									pk = query
											.from(taskIT)
											.where(taskIT.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(taskIT.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(taskIT2)
															.where(taskIT.dmalmProjectFk02
																	.eq(taskIT2.dmalmProjectFk02))
															.where(taskIT.cdTaskIt
																	.eq(taskIT2.cdTaskIt))
															.list(taskIT2.dtStoricizzazione
																	.max())))
											.orderBy(taskIT.rankStatoTaskIt.desc(), taskIT.dtModificaTaskIt.desc(), taskIT.dmalmTaskItPk.desc())
											.list(taskIT.dmalmTaskItPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmTaskIt t = TaskItDAO
													.getTaskIt(i);
											if (t != null) {
												boolean exist = TaskItDAO
														.checkEsistenzaTask(t,
																p);
												if (!exist) {
													if (t.getRankStatoTaskIt() == 1) {
														TaskItDAO.updateRank(t,
																new Double(0));
													}
													t.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmRichiestaGestione richiestaGestione2 = new QDmalmRichiestaGestione(
											"richiestaGestione2");
									pk = query
											.from(richiestaGestione)
											.where(richiestaGestione.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(richiestaGestione.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(richiestaGestione2)
															.where(richiestaGestione.dmalmProjectFk02
																	.eq(richiestaGestione2.dmalmProjectFk02))
															.where(richiestaGestione.cdRichiestaGest
																	.eq(richiestaGestione2.cdRichiestaGest))
															.list(richiestaGestione2.dtStoricizzazione
																	.max())))
											.orderBy(richiestaGestione.rankStatoRichiestaGest.desc(), richiestaGestione.dtModificaRichiestaGest.desc(), richiestaGestione.dmalmRichiestaGestPk.desc())
											.list(richiestaGestione.dmalmRichiestaGestPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmRichiestaGestione r = RichiestaGestioneDAO
													.getRichiestaGestione(i);
											if (r != null) {
												boolean exist = RichiestaGestioneDAO
														.checkEsistenzaRichiesta(
																r, p);
												if (!exist) {
													if (r.getRankStatoRichiestaGest() == 1) {
														RichiestaGestioneDAO
																.updateRank(
																		r,
																		new Double(
																				0));
													}
													r.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmAnomaliaAssistenza anomaliaAssistenza2 = new QDmalmAnomaliaAssistenza(
											"anomaliaAssistenza2");
									pk = query
											.from(anomaliaAssistenza)
											.where(anomaliaAssistenza.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(anomaliaAssistenza.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(anomaliaAssistenza2)
															.where(anomaliaAssistenza.dmalmProjectFk02
																	.eq(anomaliaAssistenza2.dmalmProjectFk02))
															.where(anomaliaAssistenza.cdAnomaliaAss
																	.eq(anomaliaAssistenza2.cdAnomaliaAss))
															.list(anomaliaAssistenza2.dtStoricizzazione
																	.max())))
											.orderBy(anomaliaAssistenza.rankStatoAnomaliaAss.desc(), anomaliaAssistenza.dtModificaAnomaliaAss.desc(), anomaliaAssistenza.dmalmAnomaliaAssPk.desc())
											.list(anomaliaAssistenza.dmalmAnomaliaAssPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmAnomaliaAssistenza a = AnomaliaAssistenzaDAO
													.getAnomaliaAssistenza(i);
											if (a != null) {
												boolean exist = AnomaliaAssistenzaDAO
														.checkEsistenzaAnomalia(
																a, p);
												if (!exist) {
													if (a.getRankStatoAnomaliaAss() == 1) {
														AnomaliaAssistenzaDAO
																.updateRank(
																		a,
																		new Double(
																				0));
													}
													a.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmReleaseServizi releaseServizi2 = new QDmalmReleaseServizi(
											"releaseServizi2");
									pk = query
											.from(releaseServizi)
											.where(releaseServizi.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(releaseServizi.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(releaseServizi2)
															.where(releaseServizi.dmalmProjectFk02
																	.eq(releaseServizi2.dmalmProjectFk02))
															.where(releaseServizi.cdRelServizi
																	.eq(releaseServizi2.cdRelServizi))
															.list(releaseServizi2.dtStoricizzazione
																	.max())))
											.orderBy(releaseServizi.rankStatoRelServizi.desc(), releaseServizi.dtModificaRelServizi.desc(), releaseServizi.dmalmRelServiziPk.desc())
											.list(releaseServizi.dmalmRelServiziPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmReleaseServizi r = ReleaseServiziDAO
													.getReleaseServizi(i);
											if (r != null) {
												boolean exist = ReleaseServiziDAO
														.checkEsistenzaRelease(
																r, p);
												if (!exist) {
													if (r.getRankStatoRelServizi() == 1) {
														ReleaseServiziDAO
																.updateRank(
																		r,
																		new Double(
																				0));
													}
													r.setDtStoricizzazione(p
															.getDtInizioValidita());
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
									QDmalmClassificatore classificatoreDem2 = new QDmalmClassificatore(
											"classificatoreDem2");
									pk = query
											.from(classificatoreDem)
											.where(classificatoreDem.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(classificatoreDem.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(classificatoreDem2)
															.where(classificatoreDem.dmalmProjectFk02
																	.eq(classificatoreDem2.dmalmProjectFk02))
															.where(classificatoreDem.cd_classificatore
																	.eq(classificatoreDem2.cd_classificatore))
															.list(classificatoreDem2.dtStoricizzazione
																	.max())))
											.orderBy(classificatoreDem.rankStatoClassificatore.desc(), classificatoreDem.dtModificaClassif.desc(), classificatoreDem.dmalmClassificatorePk.desc())
											.list(classificatoreDem.dmalmClassificatorePk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmClassificatore c = ClassificatoreDAO
													.getClassificatore(i);
											if (c != null) {
												boolean exist = ClassificatoreDAO
														.checkEsistenzaClassificatore(
																c, p);
												if (!exist) {
													if (c.getRankStatoClassificatore() == 1) {
														ClassificatoreDAO
																.updateRank(
																		c,
																		new Double(
																				0));
													}
													c.setDtStoricizzazione(p
															.getDtInizioValidita());
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
	
	private static void storicizzaWI() throws DAOException, SQLException {
		
		ConnectionManager cm = null;
		Connection conn = null;
		CallableStatement call = null;
		try {
			Iterator<EnumWorkitemType> itEnumWiTypeKeySet = Workitem_Type.getEnumMapWiTypeStoredProcedure().keySet().iterator();
			while (itEnumWiTypeKeySet.hasNext()) {
				EnumWorkitemType enumWorkitemType = itEnumWiTypeKeySet.next();
				logger.info("Storicizzo type: "+enumWorkitemType);
				String sql = QueryUtils.getCallProcedure("STORICIZZA_WI_BY_PROJECT."+ Workitem_Type.getEnumMapWiTypeStoredProcedure().get(enumWorkitemType), 0);
				logger.debug("Inizio chiamata alla Stored Procedure "+Workitem_Type.getEnumMapWiTypeStoredProcedure().get(enumWorkitemType));
				cm = ConnectionManager.getInstance();
				conn = cm.getConnectionOracle();
				call = conn.prepareCall(sql);
		        call.execute();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null)
				cm.closeConnection(conn);
		}
	}
}
