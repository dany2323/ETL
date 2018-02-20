package lispa.schedulers.dao.target;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

import lispa.schedulers.bean.target.fatti.DmalmTestcase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmTestcaseOds;

public class TestCaseOdsDAO {

	private static Logger logger = Logger.getLogger(DocumentoOdsDAO.class);

	private static QDmalmTestcaseOds testCaseODS = QDmalmTestcaseOds.dmalmTestcaseOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, testCaseODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmTestcase> staging_testcases,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmTestcase testcase : staging_testcases) {

				new SQLInsertClause(connection, dialect, testCaseODS)
						.columns(testCaseODS.cdTestcase, testCaseODS.codice,
								testCaseODS.dataEsecuzioneTestcase,
								testCaseODS.descrizioneTestcase,
								testCaseODS.dmalmAreaTematicaFk05,
								testCaseODS.dmalmProjectFk02,
								testCaseODS.dmalmStatoWorkitemFk03,
								testCaseODS.dmalmStrutturaOrgFk01,
								testCaseODS.dmalmTempoFk04,
								testCaseODS.dmalmTestcasePk,
								testCaseODS.dsAutoreTestcase,
								testCaseODS.dtCambioStatoTestcase,
								testCaseODS.dtCaricamentoTestcase,
								testCaseODS.dtCreazioneTestcase,
								testCaseODS.dtModificaTestcase,
								testCaseODS.dtRisoluzioneTestcase,
								testCaseODS.dtScadenzaTestcase,
								testCaseODS.dtStoricizzazione,
								testCaseODS.idAutoreTestcase,
								testCaseODS.idRepository,
								testCaseODS.motivoRisoluzioneTestcase,
								testCaseODS.numeroLinea,
								testCaseODS.numeroTestata,
								testCaseODS.rankStatoTestcase,
								testCaseODS.titoloTestcase, testCaseODS.stgPk,
								testCaseODS.dmalmUserFk06, testCaseODS.uri,
								testCaseODS.severity, testCaseODS.priority)
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
								testcase.getTitoloTestcase(),
								testcase.getStgPk(),
								testcase.getDmalmUserFk06(), testcase.getUri(),
								testcase.getSeverity(), testcase.getPriority())
						.execute();

			}

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmTestcase> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmTestcase> resultListEl = new LinkedList<DmalmTestcase>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(testCaseODS)
					.orderBy(testCaseODS.cdTestcase.asc())
					.orderBy(testCaseODS.dtModificaTestcase.asc())
					.list(testCaseODS.all());
			
			for (Tuple result : list) {
				DmalmTestcase resultEl = new DmalmTestcase();
				resultEl.setCdTestcase(result.get(testCaseODS.cdTestcase));
				resultEl.setCodice(result.get(testCaseODS.codice));
				resultEl.setDataEsecuzioneTestcase(result.get(testCaseODS.dataEsecuzioneTestcase));
				resultEl.setDescrizioneTestcase(result.get(testCaseODS.descrizioneTestcase));
				resultEl.setDmalmAreaTematicaFk05(result.get(testCaseODS.dmalmAreaTematicaFk05));
				resultEl.setDmalmProjectFk02(result.get(testCaseODS.dmalmProjectFk02));
				resultEl.setDmalmUserFk06(result.get(testCaseODS.dmalmUserFk06));
				resultEl.setDmalmStatoWorkitemFk03(result.get(testCaseODS.dmalmStatoWorkitemFk03));
				resultEl.setDmalmStrutturaOrgFk01(result.get(testCaseODS.dmalmStrutturaOrgFk01));
				resultEl.setDmalmTempoFk04(result.get(testCaseODS.dmalmTempoFk04));
				resultEl.setDmalmTestcasePk(result.get(testCaseODS.dmalmTestcasePk));
				resultEl.setDsAutoreTestcase(result.get(testCaseODS.dsAutoreTestcase));
				resultEl.setDtCambioStatoTestcase(result.get(testCaseODS.dtCambioStatoTestcase));
				resultEl.setDtCaricamentoTestcase(result.get(testCaseODS.dtCaricamentoTestcase));
				resultEl.setDtCreazioneTestcase(result.get(testCaseODS.dtCreazioneTestcase));
				resultEl.setDtModificaTestcase(result.get(testCaseODS.dtModificaTestcase));
				resultEl.setDtRisoluzioneTestcase(result.get(testCaseODS.dtRisoluzioneTestcase));
				resultEl.setDtScadenzaTestcase(result.get(testCaseODS.dtScadenzaTestcase));
				resultEl.setDtStoricizzazione(result.get(testCaseODS.dtStoricizzazione));
				resultEl.setIdAutoreTestcase(result.get(testCaseODS.idAutoreTestcase));
				resultEl.setIdRepository(result.get(testCaseODS.idRepository));
				resultEl.setMotivoRisoluzioneTestcase(result.get(testCaseODS.motivoRisoluzioneTestcase));
				resultEl.setNumeroLinea(result.get(testCaseODS.numeroLinea));
				resultEl.setNumeroTestata(result.get(testCaseODS.numeroTestata));
				resultEl.setRankStatoTestcase(result.get(testCaseODS.rankStatoTestcase));
				resultEl.setRankStatoTestcaseMese(result.get(testCaseODS.rankStatoTestcaseMese));
				resultEl.setStgPk(result.get(testCaseODS.stgPk));
				resultEl.setUri(result.get(testCaseODS.uri));
				resultEl.setTitoloTestcase(result.get(testCaseODS.titoloTestcase));
				resultEl.setSeverity(result.get(testCaseODS.severity));
				resultEl.setPriority(result.get(testCaseODS.priority));
				resultListEl.add(resultEl);
			}

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return resultListEl;

	}

}
