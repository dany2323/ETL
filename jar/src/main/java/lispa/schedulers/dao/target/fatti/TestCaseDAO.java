package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_TESTCASE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmTestcase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmTestcase;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class TestCaseDAO {

	private static Logger logger = Logger.getLogger(TestCaseDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmTestcase tstcs = QDmalmTestcase.dmalmTestcase;

	public static List<DmalmTestcase> getAllTestcase(Timestamp dataEsecuzione)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmTestcase bean = null;
		List<DmalmTestcase> testcases = new ArrayList<DmalmTestcase>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.testcase));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_TESTCASE);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmTestcase();

				bean.setCdTestcase(rs.getString("CD_TESTCASE"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setDataEsecuzioneTestcase(rs
						.getTimestamp("DATA_ESECUZIONE_TESTCASE"));
				bean.setDescrizioneTestcase(rs
						.getString("DESCRIZIONE_TESTCASE"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDmalmTestcasePk(rs.getInt("DMALM_TESTCASE_PK"));
				bean.setDsAutoreTestcase(rs.getString("NOME_AUTORE_TESTCASE"));
				bean.setDtCaricamentoTestcase(dataEsecuzione);
				bean.setDtCreazioneTestcase(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaTestcase(rs
						.getTimestamp("DATA_MODIFICA_TESTCASE"));
				bean.setDtRisoluzioneTestcase(rs
						.getTimestamp("DATA_RISOLUZIONE_TESTCASE"));
				bean.setDtScadenzaTestcase(rs
						.getTimestamp("DATA_SCADENZA_TESTCASE"));
				bean.setIdAutoreTestcase(rs.getString("ID_AUTORE_TESTCASE"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzioneTestcase(rs
						.getString("MOTIVO_RISOL_TESTCASE"));
				bean.setNumeroLinea(StringUtils.getLinea(rs
						.getString("CODICE_INTERVENTO")));
				bean.setNumeroTestata(StringUtils.getTestata(rs
						.getString("CODICE_INTERVENTO")));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_TESTCASE_PK"));
				bean.setTitoloTestcase(rs.getString("TITOLO_TESTCASE"));
				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));
				
				testcases.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return testcases;
	}

	public static List<Tuple> getTestCase(DmalmTestcase testcase)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> testcases = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			testcases = query
					.from(tstcs)
					.where(tstcs.cdTestcase.equalsIgnoreCase(testcase
							.getCdTestcase()))
					.where(tstcs.idRepository.eq(testcase.getIdRepository()))
					.where(tstcs.rankStatoTestcase.eq(new Double(1)))
					.list(tstcs.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return testcases;

	}

	public static void insertTestCase(DmalmTestcase testcase)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, tstcs)
					.columns(tstcs.cdTestcase, tstcs.codice,
							tstcs.dataEsecuzioneTestcase,
							tstcs.descrizioneTestcase,
							tstcs.dmalmAreaTematicaFk05,
							tstcs.dmalmProjectFk02,
							tstcs.dmalmStatoWorkitemFk03,
							tstcs.dmalmStrutturaOrgFk01, tstcs.dmalmTempoFk04,
							tstcs.dmalmTestcasePk, tstcs.dsAutoreTestcase,
							tstcs.dtCambioStatoTestcase,
							tstcs.dtCaricamentoTestcase,
							tstcs.dtCreazioneTestcase,
							tstcs.dtModificaTestcase,
							tstcs.dtRisoluzioneTestcase,
							tstcs.dtScadenzaTestcase, tstcs.dtStoricizzazione,
							tstcs.idAutoreTestcase, tstcs.idRepository,
							tstcs.motivoRisoluzioneTestcase, tstcs.numeroLinea,
							tstcs.numeroTestata, tstcs.rankStatoTestcase,
							tstcs.titoloTestcase, tstcs.stgPk,
							tstcs.dmalmUserFk06, tstcs.uri,
							tstcs.dtAnnullamento,
							tstcs.severity, tstcs.priority)
					.values(testcase.getCdTestcase(), testcase.getCodice(),
							testcase.getDataEsecuzioneTestcase(),
							testcase.getDescrizioneTestcase(),
							testcase.getDmalmAreaTematicaFk05(),
							testcase.getDmalmProjectFk02(),
							testcase.getDmalmStatoWorkitemFk03(),
							testcase.getDmalmStrutturaOrgFk01(),
							testcase.getDmalmTempoFk04(),
							testcase.getDmalmTestcasePk(),
							testcase.getDsAutoreTestcase(),
							testcase.getDtCambioStatoTestcase(),
							testcase.getDtCaricamentoTestcase(),
							testcase.getDtCreazioneTestcase(),
							testcase.getDtModificaTestcase(),
							testcase.getDtRisoluzioneTestcase(),
							testcase.getDtScadenzaTestcase(),
							testcase.getDtModificaTestcase(),
							testcase.getIdAutoreTestcase(),
							testcase.getIdRepository(),
							testcase.getMotivoRisoluzioneTestcase(),
							testcase.getNumeroLinea(),
							testcase.getNumeroTestata(), new Double(1),
							testcase.getTitoloTestcase(), testcase.getStgPk(),
							testcase.getDmalmUserFk06(), testcase.getUri(),
							testcase.getDtAnnullamento(),
							//DM_ALM-320
							testcase.getSeverity(), testcase.getPriority()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmTestcase testcase, Double double1)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, tstcs)
					.where(tstcs.cdTestcase.equalsIgnoreCase(testcase
							.getCdTestcase()))
					.where(tstcs.idRepository.equalsIgnoreCase(testcase
							.getIdRepository()))
					.set(tstcs.rankStatoTestcase, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertTestCaseUpdate(Timestamp dataEsecuzione,
			DmalmTestcase testcase, boolean pkValue) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, tstcs)
					.columns(tstcs.cdTestcase, tstcs.codice,
							tstcs.dataEsecuzioneTestcase,
							tstcs.descrizioneTestcase,
							tstcs.dmalmAreaTematicaFk05,
							tstcs.dmalmProjectFk02,
							tstcs.dmalmStatoWorkitemFk03,
							tstcs.dmalmStrutturaOrgFk01, tstcs.dmalmTempoFk04,
							tstcs.dmalmTestcasePk, tstcs.dsAutoreTestcase,
							tstcs.dtCambioStatoTestcase,
							tstcs.dtCaricamentoTestcase,
							tstcs.dtCreazioneTestcase,
							tstcs.dtModificaTestcase,
							tstcs.dtRisoluzioneTestcase,
							tstcs.dtScadenzaTestcase, tstcs.dtStoricizzazione,
							tstcs.idAutoreTestcase, tstcs.idRepository,
							tstcs.motivoRisoluzioneTestcase, tstcs.numeroLinea,
							tstcs.numeroTestata, tstcs.rankStatoTestcase,
							tstcs.titoloTestcase, tstcs.stgPk,
							tstcs.dmalmUserFk06, tstcs.uri,
							tstcs.dtAnnullamento, tstcs.changed,
							tstcs.annullato,
							tstcs.severity, tstcs.priority)
					.values(testcase.getCdTestcase(),
							testcase.getCodice(),
							testcase.getDataEsecuzioneTestcase(),
							testcase.getDescrizioneTestcase(),
							testcase.getDmalmAreaTematicaFk05(),
							testcase.getDmalmProjectFk02(),
							testcase.getDmalmStatoWorkitemFk03(),
							testcase.getDmalmStrutturaOrgFk01(),
							testcase.getDmalmTempoFk04(),
							pkValue == true ? testcase.getDmalmTestcasePk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							testcase.getDsAutoreTestcase(),
							testcase.getDtCambioStatoTestcase(),
							testcase.getDtCaricamentoTestcase(),
							testcase.getDtCreazioneTestcase(),
							testcase.getDtModificaTestcase(),
							testcase.getDtRisoluzioneTestcase(),
							testcase.getDtScadenzaTestcase(),
							pkValue == true ? testcase.getDtModificaTestcase()
									: testcase.getDtStoricizzazione(),
							testcase.getIdAutoreTestcase(),
							testcase.getIdRepository(),
							testcase.getMotivoRisoluzioneTestcase(),
							testcase.getNumeroLinea(),
							testcase.getNumeroTestata(), 
							pkValue == true ? new Short("1")  : testcase.getRankStatoTestcase(),
							testcase.getTitoloTestcase(), testcase.getStgPk(),
							testcase.getDmalmUserFk06(), testcase.getUri(),
							testcase.getDtAnnullamento(),
							testcase.getChanged(), testcase.getAnnullato(),
							testcase.getSeverity(), testcase.getPriority())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateTestCase(DmalmTestcase testcase)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, tstcs)

					.where(tstcs.cdTestcase.equalsIgnoreCase(testcase
							.getCdTestcase()))
					.where(tstcs.idRepository.equalsIgnoreCase(testcase
							.getIdRepository()))
					.where(tstcs.rankStatoTestcase.eq(new Double(1)))

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
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmTestcase getTestCase(Integer pk) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> testcases = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			testcases = query.from(tstcs).where(tstcs.dmalmTestcasePk.eq(pk))
					.list(tstcs.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (testcases.size() > 0) {
			Tuple t = testcases.get(0);

			DmalmTestcase e = new DmalmTestcase();

			e.setAnnullato(t.get(tstcs.annullato));
			e.setCdTestcase(t.get(tstcs.cdTestcase));
			e.setChanged(t.get(tstcs.changed));
			e.setCodice(t.get(tstcs.codice));
			e.setDataEsecuzioneTestcase(t.get(tstcs.dataEsecuzioneTestcase));
			e.setDescrizioneTestcase(t.get(tstcs.descrizioneTestcase));
			e.setDmalmAreaTematicaFk05(t.get(tstcs.dmalmAreaTematicaFk05));
			e.setDmalmProjectFk02(t.get(tstcs.dmalmProjectFk02));
			e.setDmalmStatoWorkitemFk03(t.get(tstcs.dmalmStatoWorkitemFk03));
			e.setDmalmStrutturaOrgFk01(t.get(tstcs.dmalmStrutturaOrgFk01));
			e.setDmalmTempoFk04(t.get(tstcs.dmalmTempoFk04));
			e.setDmalmTestcasePk(t.get(tstcs.dmalmTestcasePk));
			e.setDmalmUserFk06(t.get(tstcs.dmalmUserFk06));
			e.setDsAutoreTestcase(t.get(tstcs.dsAutoreTestcase));
			e.setDtAnnullamento(t.get(tstcs.dtAnnullamento));
			e.setDtCambioStatoTestcase(t.get(tstcs.dtCambioStatoTestcase));
			e.setDtCaricamentoTestcase(t.get(tstcs.dtCaricamentoTestcase));
			e.setDtCreazioneTestcase(t.get(tstcs.dtCreazioneTestcase));
			e.setDtModificaTestcase(t.get(tstcs.dtModificaTestcase));
			e.setDtRisoluzioneTestcase(t.get(tstcs.dtRisoluzioneTestcase));
			e.setDtScadenzaTestcase(t.get(tstcs.dtScadenzaTestcase));
			e.setDtStoricizzazione(t.get(tstcs.dtStoricizzazione));
			e.setIdAutoreTestcase(t.get(tstcs.idAutoreTestcase));
			e.setIdRepository(t.get(tstcs.idRepository));
			e.setMotivoRisoluzioneTestcase(t
					.get(tstcs.motivoRisoluzioneTestcase));
			e.setNumeroLinea(t.get(tstcs.numeroLinea));
			e.setNumeroTestata(t.get(tstcs.numeroTestata));
			e.setRankStatoTestcase(t.get(tstcs.rankStatoTestcase));
			e.setRankStatoTestcaseMese(t.get(tstcs.rankStatoTestcaseMese));
			e.setStgPk(t.get(tstcs.stgPk));
			e.setTitoloTestcase(t.get(tstcs.titoloTestcase));
			e.setUri(t.get(tstcs.uri));
			//DM_ALM-320
			e.setSeverity(t.get(tstcs.severity));
			e.setPriority(t.get(tstcs.priority));
			
			return e;
		} else
			return null;
	}

	public static boolean checkEsistenzaTestCase(DmalmTestcase t, DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(tstcs)
					.where(tstcs.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(tstcs.cdTestcase.eq(t.getCdTestcase()))
					.where(tstcs.idRepository.eq(t.getIdRepository()))
					.list(tstcs.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
