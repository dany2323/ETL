package lispa.schedulers.facade.cleaning;

import java.sql.Connection;
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
import lispa.schedulers.bean.target.fatti.DmalmRichiestaSupporto;
import lispa.schedulers.bean.target.fatti.DmalmSottoprogramma;
import lispa.schedulers.bean.target.fatti.DmalmTask;
import lispa.schedulers.bean.target.fatti.DmalmTaskIt;
import lispa.schedulers.bean.target.fatti.DmalmTestcase;
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
import lispa.schedulers.dao.target.fatti.RichiestaSupportoDAO;
import lispa.schedulers.dao.target.fatti.SottoprogrammaDAO;
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
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLSubQuery;
import com.mysema.query.sql.SQLTemplates;

public class CheckProjectStorFacade {

	private static Logger logger = Logger
			.getLogger(CheckProjectStorFacade.class);
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

			Timestamp dataStoricizzazione;
			for (Workitem_Type type : Workitem_Type.values()) {
				ConnectionManager cm = null;
				Connection conn = null;
				List<Integer> pk = new ArrayList<Integer>();
				logger.info("Storicizzo type: "+type);
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
								case "anomalia":
									QDmalmAnomaliaProdotto anomalia2 = new QDmalmAnomaliaProdotto(
											"anomalia2");
									pk = query
											.from(anomalia)
											.where(anomalia.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(anomalia.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(anomalia2)
															.where(anomalia.dmalmProjectFk02
																	.eq(anomalia2.dmalmProjectFk02))
															.where(anomalia.cdAnomalia
																	.eq(anomalia2.cdAnomalia))
															.list(anomalia2.dtStoricizzazione
																	.max())))
											.orderBy(anomalia.rankStatoAnomalia.desc(), anomalia.dtModificaRecordAnomalia.desc(), anomalia.dmalmAnomaliaProdottoPk.desc())						
											.list(anomalia.dmalmAnomaliaProdottoPk);

									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmAnomaliaProdotto a = AnomaliaProdottoDAO
													.getAnomaliaProdotto(i);
											
											if (a != null) {
												boolean exist = AnomaliaProdottoDAO
														.checkEsistenzaAnomalia(
																a, p);
												if (!exist) {
													//System.out.println("Pk"+i+" CD_ANOMALIA"+a.getCdAnomalia()+" Progetto da storicizzare : "+p.getDmalmProjectPk());
													if(dataEsecuzione.compareTo(a.getDtStoricizzazione())>0){
														if (a.getRankStatoAnomalia() == 1) {
															AnomaliaProdottoDAO
																	.updateRankFlagUltimaSituazione(
																			a,
																			new Double(
																					0),
																			new Short(
																					"0"));
													}
														
														a.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														a.setDtStoricizzazione(p
																.getDtInizioValidita());
														AnomaliaProdottoDAO
																.insertAnomaliaProdottoUpdate(
																		dataEsecuzione,
																		a, false);
													}else{
														a.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														AnomaliaProdottoDAO.updateDmalmAnomaliaProdotto(a);
													}
												}
											}
										}
									}
									break;
								case "defect":
									QDmalmDifettoProdotto difetto2 = new QDmalmDifettoProdotto(
											"difetto2");
									pk = query
											.from(difetto)
											.where(difetto.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(difetto.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(difetto2)
															.where(difetto.dmalmProjectFk02
																	.eq(difetto2.dmalmProjectFk02))
															.where(difetto.cdDifetto
																	.eq(difetto2.cdDifetto))
															.list(difetto2.dtStoricizzazione
																	.max())))
											.orderBy(difetto.rankStatoDifetto.desc(), difetto.dtModificaRecordDifetto.desc(), difetto.dmalmDifettoProdottoPk.desc())						
											.list(difetto.dmalmDifettoProdottoPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmDifettoProdotto d = DifettoDAO
													.getDifetto(i);

											if (d != null) {
												boolean exist = DifettoDAO
														.checkEsistenzaDifetto(
																d, p);
												//System.out.println("Pk"+pk+" CD_DIFETTO"+d.getCdDifetto()+" Progetto da storicizzare : "+p.getDmalmProjectPk());
												if (!exist) {
													if(dataEsecuzione.compareTo(d.getDtStoricizzazione())>0){
														if (d.getRankStatoDifetto() == 1) {
															DifettoDAO
																	.updateRankFlagUltimaSituazione(
																			d,
																			new Double(
																					0),
																			new Short(
																					"0"));
														}
														d.setDtStoricizzazione(p
																.getDtInizioValidita());
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														DifettoDAO
																.insertDifettoProdottoUpdate(
																		dataEsecuzione,
																		d, false);
													}else{
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														DifettoDAO.updateDmalmDifettoProdotto(d);
													}
												}
											}
										}
									}
									break;
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
													if(dataEsecuzione.compareTo(pss.getDtStoricizzazione())>0){
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
													}else{
														pss.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgettoSviluppoSviluppoDAO.updateProgettoSviluppoSvil(pss);
													}
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
													if(dataEsecuzione.compareTo(d.getDtStoricizzazione())>0){
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
													else{
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														DocumentoDAO.updateDocumento(d);
													}
												}
											}
										}
									}
									break;
								case "sman":
									QDmalmManutenzione manutenzione2 = new QDmalmManutenzione(
											"manutenzione2");
									pk = query
											.from(manutenzione)
											.where(manutenzione.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(manutenzione.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(manutenzione2)
															.where(manutenzione.dmalmProjectFk02
																	.eq(manutenzione2.dmalmProjectFk02))
															.where(manutenzione.cdManutenzione
																	.eq(manutenzione2.cdManutenzione))
															.list(manutenzione2.dtStoricizzazione
																	.max())))
											.orderBy(manutenzione.rankStatoManutenzione.desc(), manutenzione.dtModificaManutenzione.desc(), manutenzione.dmalmManutenzionePk.desc())
											.list(manutenzione.dmalmManutenzionePk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmManutenzione m = ManutenzioneDAO
													.getManutenzione(i);
											if (m != null) {
												boolean exist = ManutenzioneDAO
														.checkEsistenzaManutenzione(
																m, p);
												if (!exist) {
													if(dataEsecuzione.compareTo(m.getDtStoricizzazione())>0){
														if (m.getRankStatoManutenzione() == 1) {
															ManutenzioneDAO
																	.updateRank(
																			m,
																			new Double(
																					0));
														}
														m.setDtStoricizzazione(p
																.getDtInizioValidita());
														m.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ManutenzioneDAO
																.insertManutenzioneUpdate(
																		dataEsecuzione,
																		m, false);
													}else{
														m.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ManutenzioneDAO.updateManutenzione(m);
													}
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
													if(dataEsecuzione.compareTo(t.getDtStoricizzazione())>0){
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
													}else{
														t.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														TestCaseDAO.updateTestCase(t);
														
													}
														
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
													if(dataEsecuzione.compareTo(t.getDtStoricizzazione())>0){
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
													}else{
														t.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														TaskDAO.updateTask(t);
													}
												}
											}
										}
									}
									break;
								case "release":
									QDmalmReleaseDiProgetto releaseDiProgetto2 = new QDmalmReleaseDiProgetto(
											"releaseDiProgetto2");
									pk = query
											.from(releaseDiProgetto)
											.where(releaseDiProgetto.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(releaseDiProgetto.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(releaseDiProgetto2)
															.where(releaseDiProgetto.dmalmProjectFk02
																	.eq(releaseDiProgetto2.dmalmProjectFk02))
															.where(releaseDiProgetto.cdReleasediprog
																	.eq(releaseDiProgetto2.cdReleasediprog))
															.list(releaseDiProgetto2.dtStoricizzazione
																	.max())))
											.orderBy(releaseDiProgetto.rankStatoReleasediprog.desc(), releaseDiProgetto.dtModificaReleasediprog.desc(), releaseDiProgetto.dmalmReleasediprogPk.desc())
											.list(releaseDiProgetto.dmalmReleasediprogPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmReleaseDiProgetto r = ReleaseDiProgettoDAO
													.getReleaseDiProgetto(i);
											if (r != null) {
												boolean exist = ReleaseDiProgettoDAO
														.checkEsistenzaRelease(
																r, p);
												if (!exist) {
													if(dataEsecuzione.compareTo(r.getDtStoricizzazione())>0){
														if (r.getRankStatoReleasediprog() == 1) {
															ReleaseDiProgettoDAO
																	.updateRank(
																			r,
																			new Double(
																					0));
														}
														r.setDtStoricizzazione(p
																.getDtInizioValidita());
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ReleaseDiProgettoDAO
																.insertReleaseDiProgettoUpdate(
																		dataEsecuzione,
																		r, false);
													}else {
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ReleaseDiProgettoDAO.updateReleaseDiProgetto(r);
													}
												}
											}
										}
									}
									break;
								case "programma":
									QDmalmProgramma programma2 = new QDmalmProgramma(
											"programma2");
									pk = query
											.from(programma)
											.where(programma.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(programma.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(programma2)
															.where(programma.dmalmProjectFk02
																	.eq(programma2.dmalmProjectFk02))
															.where(programma.cdProgramma
																	.eq(programma2.cdProgramma))
															.list(programma2.dtStoricizzazione
																	.max())))
											.orderBy(programma.rankStatoProgramma.desc(), programma.dtModificaProgramma.desc(), programma.dmalmProgrammaPk.desc())
											.list(programma.dmalmProgrammaPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmProgramma d = ProgrammaDAO
													.getProgramma(i);
											if (d != null) {
												boolean exist = ProgrammaDAO
														.checkEsistenzaProgramma(
																d, p);
												if (!exist) {
													if(dataEsecuzione.compareTo(d.getDtStoricizzazione())>0){
														if (d.getRankStatoProgramma() == 1) {
															ProgrammaDAO
																	.updateRank(
																			d,
																			new Double(
																					0));
														}
														d.setDtStoricizzazione(p
																.getDtInizioValidita());
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgrammaDAO
																.insertProgrammaUpdate(
																		dataEsecuzione,
																		d, false);
													}else {
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgrammaDAO.updateProgramma(d);
													}
												}
											}
										}
									}
									break;
								case "sottoprogramma":
									QDmalmSottoprogramma sottoprogramma2 = new QDmalmSottoprogramma(
											"sottoprogramma2");
									pk = query
											.from(sottoprogramma)
											.where(sottoprogramma.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(sottoprogramma.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(sottoprogramma2)
															.where(sottoprogramma.dmalmProjectFk02
																	.eq(sottoprogramma2.dmalmProjectFk02))
															.where(sottoprogramma.cdSottoprogramma
																	.eq(sottoprogramma2.cdSottoprogramma))
															.list(sottoprogramma2.dtStoricizzazione
																	.max())))
											.orderBy(sottoprogramma.rankStatoSottoprogramma.desc(), sottoprogramma.dtModificaSottoprogramma.desc(), sottoprogramma.dmalmSottoprogrammaPk.desc())
											.list(sottoprogramma.dmalmSottoprogrammaPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {

											DmalmSottoprogramma s = SottoprogrammaDAO
													.getSottoprogramma(i);
											if (s != null) {
												boolean exist = SottoprogrammaDAO
														.checkEsistenzaSottoProgramma(
																s, p);
												if (!exist) {
													if(dataEsecuzione.compareTo(s.getDtStoricizzazione())>0){
														if (s.getRankStatoSottoprogramma() == 1) {
															SottoprogrammaDAO
																	.updateRank(
																			s,
																			new Double(
																					0));
														}
														s.setDtStoricizzazione(p
																.getDtInizioValidita());
														s.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														SottoprogrammaDAO
																.insertSottoprogrammaUpdate(
																		dataEsecuzione,
																		s, false);
													}else{
														s.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														SottoprogrammaDAO.updateSottoprogramma(s);
													}
												}
											}
										}
									}
									break;
								case "rqd":
									QDmalmProgettoDemand progettoDemand2 = new QDmalmProgettoDemand(
											"progettoDemand2");
									pk = query
											.from(progettoDemand)
											.where(progettoDemand.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(progettoDemand.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(progettoDemand2)
															.where(progettoDemand.dmalmProjectFk02
																	.eq(progettoDemand2.dmalmProjectFk02))
															.where(progettoDemand.cdProgettoDemand
																	.eq(progettoDemand2.cdProgettoDemand))
															.list(progettoDemand2.dtStoricizzazione
																	.max())))
											.orderBy(progettoDemand.rankStatoProgettoDemand.desc(), progettoDemand.dtModificaProgettoDemand.desc(), progettoDemand.dmalmProgettoDemandPk.desc())
											.list(progettoDemand.dmalmProgettoDemandPk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmProgettoDemand d = ProgettoDemandDAO
													.getProgettoDemand(i);
											if (d != null) {
												boolean exist = ProgettoDemandDAO
														.checkEsistenzaProgetto(
																d, p);
												if (!exist) {
													if(dataEsecuzione.compareTo(d.getDtStoricizzazione())>0){
														if (d.getRankStatoProgettoDemand() == 1) {
															ProgettoDemandDAO
																	.updateRank(
																			d,
																			new Double(
																					0));
														}
														d.setDtStoricizzazione(p
																.getDtInizioValidita());
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgettoDemandDAO
																.insertProgettoDemandUpdate(
																		dataEsecuzione,
																		d, false);
													}else {
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgettoDemandDAO.updateProgettoDemand(d);
													}
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
													if(dataEsecuzione.compareTo(d.getDtStoricizzazione())>0){
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
													} else {
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgettoSviluppoDemandDAO.updateProgettoSviluppoDem(d);
													}
												}
											}
										}
									}
									break;
								case "dman":
									QDmalmRichiestaManutenzione richiestaManutenzione2 = new QDmalmRichiestaManutenzione(
											"richiestaManutenzione2");
									pk = query
											.from(richiestaManutenzione)
											.where(richiestaManutenzione.dmalmProjectFk02.eq(history
													.getDmalmProjectPk()))
											.where(richiestaManutenzione.dtStoricizzazione
													.in(new SQLSubQuery()
															.from(richiestaManutenzione2)
															.where(richiestaManutenzione.dmalmProjectFk02
																	.eq(richiestaManutenzione2.dmalmProjectFk02))
															.where(richiestaManutenzione.cdRichiestaManutenzione
																	.eq(richiestaManutenzione2.cdRichiestaManutenzione))
															.list(richiestaManutenzione2.dtStoricizzazione
																	.max())))
											.orderBy(richiestaManutenzione.rankStatoRichManutenzione.desc(), richiestaManutenzione.dtModificaRichManutenzione.desc(), richiestaManutenzione.dmalmRichManutenzionePk.desc())
											.list(richiestaManutenzione.dmalmRichManutenzionePk);
									if (pk.size() > 0) {
										for (Integer i : pk) {
											DmalmRichiestaManutenzione r = RichiestaManutenzioneDAO
													.getRichiestaManutenzione(i);
											if (r != null) {
												boolean exist = RichiestaManutenzioneDAO
														.checkEsistenzaRichiesta(
																r, p);
												if (!exist) {
													if(dataEsecuzione.compareTo(r.getDtStoricizzazione())>0){
														if (r.getRankStatoRichManutenzione() == 1) {
															RichiestaManutenzioneDAO
																	.updateRank(
																			r,
																			new Double(
																					0));
														}
														r.setDtStoricizzazione(p
																.getDtInizioValidita());
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														RichiestaManutenzioneDAO
																.insertRichiestaManutenzioneUpdate(
																		dataEsecuzione,
																		r, false);
													}else{
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														RichiestaManutenzioneDAO.updateRichiestaManutenzione(r);
													}
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
													if(dataEsecuzione.compareTo(f.getDtStoricizzazione())>0){
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
													} else{
														f.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														FaseDAO.updateFase(f);
													}
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
													if(dataEsecuzione.compareTo(f.getDtStoricizzazione())>0){
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
													} else{
														f.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														PeiDAO.updatePei(f);
													}
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
													if(dataEsecuzione.compareTo(d.getDtStoricizzazione())>0){
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
													}else{
														d.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ProgettoEseDAO.updateProgettoEse(d);
													}
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
												if(dataEsecuzione.compareTo(r.getDtStoricizzazione())>0){
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
												}else{
													r.setDmalmProjectFk02(p
															.getDmalmProjectPk());
													ReleaseItDAO.updateReleaseIt(r);
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
													if(dataEsecuzione.compareTo(b.getDtStoricizzazione())>0){
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
													} else{
														b.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														BuildDAO.updateBuild(b);
													}
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
													
													if(dataEsecuzione.compareTo(t.getDtStoricizzazione())>0){
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
													} else {
														t.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														TaskItDAO.updateTaskIt(t);
													}
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
													if(dataEsecuzione.compareTo(r.getDtStoricizzazione())>0){
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
													} else {
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														RichiestaGestioneDAO.updateTask(r);
													}
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
													if(dataEsecuzione.compareTo(a.getDtStoricizzazione())>0){
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
													} else {
														a.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														AnomaliaAssistenzaDAO.updateAnomaliaAssistenza(a);
													}
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
													if(dataEsecuzione.compareTo(r.getDtStoricizzazione())>0){
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
													}else {
														r.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ReleaseServiziDAO.updateReleaseServizi(r);
													}
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
													if(dataEsecuzione.compareTo(c.getDtStoricizzazione())>0){
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
													}else {
														c.setDmalmProjectFk02(p
																.getDmalmProjectPk());
														ClassificatoreDAO.updateClass(c);
													}
												}
											}
										}
									}
									break;
									
								case "sup":
									List<DmalmRichiestaSupporto> richieste = RichiestaSupportoDAO
														.getRichiestaSupporto(history.getDmalmProjectPk(), 1);
									for (DmalmRichiestaSupporto richiesta : richieste) {
										DmalmRichiestaSupporto r = RichiestaSupportoDAO
												.getRichiestaSupporto(richiesta.getDmalmRichiestaSupportoPk());
										if (r != null) {
											boolean exist = RichiestaSupportoDAO.checkEsistenzaRichiestaSupporto(r, p);
											if (!exist) {
												if(dataEsecuzione.compareTo(r.getDataStoricizzazione())>0){
													if (r.getRankStatoRichSupporto() == 1) {
														RichiestaSupportoDAO.updateRank(r, new Double(0));
													}
													r.setDataStoricizzazione(p.getDtInizioValidita());
													r.setDmalmProjectFk02(p.getDmalmProjectPk());
													RichiestaSupportoDAO.insertRichiestaSupportoUpdate(dataEsecuzione, r, false);
												} else {
													r.setDmalmProjectFk02(p.getDmalmProjectPk());
													RichiestaSupportoDAO.updateRichiestaSupporto(r);
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
}
