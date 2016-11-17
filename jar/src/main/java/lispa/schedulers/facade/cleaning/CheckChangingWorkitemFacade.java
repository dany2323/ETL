package lispa.schedulers.facade.cleaning;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.Total;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.TotalDao;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QTotal;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmBuild;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmClassificatoreDemand;
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

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class CheckChangingWorkitemFacade {

	private static Logger logger = Logger
			.getLogger(CheckChangingWorkitemFacade.class);
	private static SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
	private static QTotal qTotal = QTotal.total;

	public static void setChangedFieldByRepo(List<Total> totals, String idRepo)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		String type = null;

		try {
			logger.info("START CheckChangingWorkitemFacade.execute()");

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			List<Tuple> cwihistory = null;
			List<Tuple> filteredcwhistory = null;
			connection.setAutoCommit(true);

			for (Total t : totals) {
				cwihistory = TotalDao.getHistorySingleChangedWI(t.getCodice(),
						idRepo);
				filteredcwhistory = TotalDao.filterChangedWiHisory(cwihistory);

				for (Tuple tt : filteredcwhistory) {
					switch (tt.get(qTotal.type)) {

					case "anomalia":

						type = findType(t.getCodice());
						if (type != null && !type.equals("anomalia")) {

							new SQLUpdateClause(
									connection,
									dialect,
									QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto)
									.where(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.dmalmAnomaliaProdottoPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto.changed,
											type).execute();
						}
						break;
					case "documento":

						type = findType(t.getCodice());
						if (type != null && !type.equals("documento")) {

							new SQLUpdateClause(connection, dialect,
									QDmalmDocumento.dmalmDocumento)
									.where(QDmalmDocumento.dmalmDocumento.dmalmDocumentoPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmDocumento.dmalmDocumento.changed,
											type).execute();
						}
						break;

					case "testcase":

						type = findType(t.getCodice());
						if (type != null && !type.equals("testcase")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmTestcase.dmalmTestcase)
									.where(QDmalmTestcase.dmalmTestcase.dmalmTestcasePk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmTestcase.dmalmTestcase.changed,
											type).execute();
						}
						break;
					case "pei":

						type = findType(t.getCodice());
						if (type != null && !type.equals("pei")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmPei.dmalmPei)
									.where(QDmalmPei.dmalmPei.dmalmPeiPk.eq(tt
											.get(qTotal.dmalmPk)))
									.set(QDmalmPei.dmalmPei.changed,
											type).execute();
						}
						break;
					case "build":

						type = findType(t.getCodice());
						if (type != null && !type.equals("build")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmBuild.dmalmBuild)
									.where(QDmalmBuild.dmalmBuild.dmalmBuildPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmBuild.dmalmBuild.changed, type)
									.execute();
						}
						break;
					case "progettoese":

						type = findType(t.getCodice());
						if (type != null && !type.equals("progettoese")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmProgettoEse.dmalmProgettoEse)
									.where(QDmalmProgettoEse.dmalmProgettoEse.dmalmProgettoEsePk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmProgettoEse.dmalmProgettoEse.changed,
											type).execute();
						}
						break;
					case "fase":

						type = findType(t.getCodice());
						if (type != null && !type.equals("fase")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmFase.dmalmFase)
									.where(QDmalmFase.dmalmFase.dmalmFasePk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmFase.dmalmFase.changed, type)
									.execute();
						}
						break;
					case "defect":

						type = findType(t.getCodice());
						if (type != null && !type.equals("defect")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmDifettoProdotto.dmalmDifettoProdotto)
									.where(QDmalmDifettoProdotto.dmalmDifettoProdotto.dmalmDifettoProdottoPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmDifettoProdotto.dmalmDifettoProdotto.changed,
											type).execute();
						}
						break;
					case "release":

						type = findType(t.getCodice());
						if (type != null && !type.equals("release")) {
							new SQLUpdateClause(
									connection,
									dialect,
									QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto)
									.where(QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.dmalmReleasediprogPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto.changed,
											type).execute();
						}
						break;
					case "sottoprogramma":

						type = findType(t.getCodice());
						if (type != null && !type.equals("sottoprogramma")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmSottoprogramma.dmalmSottoprogramma)
									.where(QDmalmSottoprogramma.dmalmSottoprogramma.dmalmSottoprogrammaPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmSottoprogramma.dmalmSottoprogramma.changed,
											type).execute();
						}
						break;
					case "programma":

						type = findType(t.getCodice());
						if (type != null && !type.equals("programma")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmProgramma.dmalmProgramma)
									.where(QDmalmProgramma.dmalmProgramma.dmalmProgrammaPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmProgramma.dmalmProgramma.changed,
											type).execute();
						}
						break;
					case "taskit":

						type = findType(t.getCodice());
						if (type != null && !type.equals("taskit")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmTaskIt.dmalmTaskIt)
									.where(QDmalmTaskIt.dmalmTaskIt.dmalmTaskItPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmTaskIt.dmalmTaskIt.changed, type)
									.execute();
						}
						break;
					case "anomalia_assistenza":

						type = findType(t.getCodice());
						if (type != null && !type.equals("anomalia_assistenza")) {
							new SQLUpdateClause(
									connection,
									dialect,
									QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza)
									.where(QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.dmalmAnomaliaAssPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza.changed,
											type).execute();
						}
						break;
					case "release_it":

						type = findType(t.getCodice());
						if (type != null && !type.equals("release_it")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmReleaseIt.dmalmReleaseIt)
									.where(QDmalmReleaseIt.dmalmReleaseIt.dmalmReleaseItPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmReleaseIt.dmalmReleaseIt.changed,
											type).execute();
						}
						break;
					case "sman":

						type = findType(t.getCodice());
						if (type != null && !type.equals("sman")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmManutenzione.dmalmManutenzione)
									.where(QDmalmManutenzione.dmalmManutenzione.dmalmManutenzionePk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmManutenzione.dmalmManutenzione.changed,
											type).execute();
						}
						break;

					case "release_ser":

						type = findType(t.getCodice());
						if (type != null && !type.equals("release_ser")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmReleaseServizi.dmalmReleaseServizi)
									.where(QDmalmReleaseServizi.dmalmReleaseServizi.dmalmRelServiziPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmReleaseServizi.dmalmReleaseServizi.changed,
											type).execute();
						}
						break;

					case "drqs":

						type = findType(t.getCodice());
						if (type != null && !type.equals("drqs")) {
							new SQLUpdateClause(
									connection,
									dialect,
									QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem)
									.where(QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.dmalmProgSvilDPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem.changed,
											type).execute();
						}
						break;

					case "dman":

						type = findType(t.getCodice());
						if (type != null && !type.equals("dman")) {
							new SQLUpdateClause(
									connection,
									dialect,
									QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione)
									.where(QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.dmalmRichManutenzionePk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione.changed,
											type).execute();
						}
						break;

					case "rqd":

						type = findType(t.getCodice());
						if (type != null && !type.equals("rqd")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmProgettoDemand.dmalmProgettoDemand)
									.where(QDmalmProgettoDemand.dmalmProgettoDemand.dmalmProgettoDemandPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmProgettoDemand.dmalmProgettoDemand.changed,
											type).execute();
						}
						break;

					case "richiesta_gestione":

						type = findType(t.getCodice());
						if (type != null && !type.equals("richiesta_gestione")) {
							new SQLUpdateClause(
									connection,
									dialect,
									QDmalmRichiestaGestione.dmalmRichiestaGestione)
									.where(QDmalmRichiestaGestione.dmalmRichiestaGestione.dmalmRichiestaGestPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmRichiestaGestione.dmalmRichiestaGestione.changed,
											type).execute();
						}
						break;

					case "srqs":

						type = findType(t.getCodice());
						if (type != null && !type.equals("srqs")) {
							new SQLUpdateClause(
									connection,
									dialect,
									QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil)
									.where(QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.dmalmProgSvilSPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil.changed,
											type).execute();
						}
						break;

					case "task":

						type = findType(t.getCodice());
						if (type != null && !type.equals("task")) {
							new SQLUpdateClause(connection, dialect,
									QDmalmTask.dmalmTask)
									.where(QDmalmTask.dmalmTask.dmalmTaskPk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmTask.dmalmTask.changed, type)
									.execute();
						}
						break;

					case "classificatore_demand":

						type = findType(t.getCodice());
						if (type != null
								&& !type.equals("classificatore_demand")) {

							new SQLUpdateClause(
									connection,
									dialect,
									QDmalmClassificatoreDemand.dmalmClassificatoreDemand)
									.where(QDmalmClassificatoreDemand.dmalmClassificatoreDemand.dmalmClassificatorePk
											.eq(tt.get(qTotal.dmalmPk)))
									.set(QDmalmClassificatoreDemand.dmalmClassificatoreDemand.changed,
											type).execute();
						}
						break;

					}
				}
			}

			logger.info("STOP CheckChangingWorkitemFacade.execute()");
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void execute() throws DAOException {
		List<Total> totalsire = TotalDao
				.changedWorkitemList(DmAlmConstants.REPOSITORY_SIRE);
		List<Total> totalsiss = TotalDao
				.changedWorkitemList(DmAlmConstants.REPOSITORY_SISS);

		setChangedFieldByRepo(totalsire, DmAlmConstants.REPOSITORY_SIRE);
		setChangedFieldByRepo(totalsiss, DmAlmConstants.REPOSITORY_SISS);
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		String sql;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			sql = QueryManager.getInstance().getQuery(
					DmAlmConstants.DMALM_CHANGED_WORKITEM);
			ps = connection.prepareStatement(sql);

			ps.executeUpdate();

			connection.commit();

			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

	}

	private static String findType(String codice) throws DAOException {

		String ret = null;
		ConnectionManager cm = null;
		Connection connection = null;
		List<Tuple> t = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			t = query.from(qTotal).where(qTotal.codice.eq(codice))
					.orderBy(qTotal.dtStoricizzazione.desc())
					.list(qTotal.all());

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (t.size() > 0) {
			ret = t.get(0).get(qTotal.type);
		}

		return ret;
	}

}
