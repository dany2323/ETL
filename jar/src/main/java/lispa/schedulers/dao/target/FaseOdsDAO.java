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

import lispa.schedulers.bean.target.fatti.DmalmFase;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.QDmalmFaseOds;

public class FaseOdsDAO {

	private static Logger logger = Logger.getLogger(TaskOdsDAO.class);

	private static QDmalmFaseOds faseODS = QDmalmFaseOds.dmalmFaseOds;

	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, faseODS).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmFase> staging_fasi,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			for (DmalmFase fase : staging_fasi) {

				new SQLInsertClause(connection, dialect, faseODS)
						.columns(faseODS.applicabile, faseODS.cdFase,
								faseODS.codice, faseODS.dataFineBaseline,
								faseODS.dataFineEffettiva,
								faseODS.dataFinePianificata,
								faseODS.dataInizioBaseline,
								faseODS.dataInizioEffettivo,
								faseODS.dataInizioPianificato,
								faseODS.dataPassaggioInEsecuzione,
								faseODS.descrizioneFase, faseODS.dmalmFasePk,
								faseODS.dmalmProjectFk02,
								faseODS.dmalmStatoWorkitemFk03,
								faseODS.dmalmStrutturaOrgFk01,
								faseODS.dmalmTempoFk04, faseODS.dsAutoreFase,
								faseODS.dtCambioStatoFase,
								faseODS.dtCaricamentoFase,
								faseODS.dtCreazioneFase,
								faseODS.dtModificaFase,
								faseODS.dtRisoluzioneFase,
								faseODS.dtScadenzaFase,
								faseODS.dtStoricizzazione,
								faseODS.durataEffettivaFase,
								faseODS.idAutoreFase, faseODS.idRepository,
								faseODS.motivoRisoluzioneFase,
								faseODS.rankStatoFase, faseODS.titoloFase,
								faseODS.stgPk, faseODS.dmalmUserFk06,
								faseODS.uri,
								faseODS.severity, faseODS.priority)
						.values(fase.getApplicabile(), fase.getCdFase(),
								fase.getCodice(), fase.getDataFineBaseline(),
								fase.getDataFineEffettiva(),
								fase.getDataFinePianificata(),
								fase.getDataInizioBaseline(),
								fase.getDataInizioEffettivo(),
								fase.getDataInizioPianificato(),
								fase.getDataPassaggioInEsecuzione(),
								fase.getDescrizioneFase(),
								fase.getDmalmFasePk(),
								fase.getDmalmProjectFk02(),
								fase.getDmalmStatoWorkitemFk03(),
								fase.getDmalmStrutturaOrgFk01(),
								fase.getDmalmTempoFk04(),
								fase.getDsAutoreFase(),
								fase.getDtCambioStatoFase(),
								fase.getDtCaricamentoFase(),
								fase.getDtCreazioneFase(),
								fase.getDtModificaFase(),
								fase.getDtRisoluzioneFase(),
								fase.getDtScadenzaFase(),
								fase.getDtModificaFase(),
								fase.getDurataEffettivaFase(),
								fase.getIdAutoreFase(), fase.getIdRepository(),
								fase.getMotivoRisoluzioneFase(), new Double(1),
								fase.getTitoloFase(), fase.getStgPk(),
								fase.getDmalmUserFk06(), fase.getUri(),
								fase.getSeverity(), fase.getPriority()

						).execute();
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

	public static List<DmalmFase> getAll() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		List<DmalmFase> resultListEl = new LinkedList<DmalmFase>();


		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(faseODS).orderBy(faseODS.cdFase.asc())
					.orderBy(faseODS.dtModificaFase.asc())
					.list(faseODS.all());
			
			for (Tuple result : list) {
				DmalmFase resultEl = new DmalmFase();
				resultEl.setApplicabile(result.get(faseODS.applicabile));
				resultEl.setCdFase(result.get(faseODS.cdFase));
				resultEl.setCodice(result.get(faseODS.codice));
				resultEl.setDmalmUserFk06(result.get(faseODS.dmalmUserFk06));
				resultEl.setDataFineBaseline(result.get(faseODS.dataFineBaseline));
				resultEl.setDataFineEffettiva(result.get(faseODS.dataFineEffettiva));
				resultEl.setDataFinePianificata(result.get(faseODS.dataFinePianificata));
				resultEl.setDataInizioBaseline(result.get(faseODS.dataInizioBaseline));
				resultEl.setDataInizioEffettivo(result.get(faseODS.dataInizioEffettivo));
				resultEl.setDataInizioPianificato(result.get(faseODS.dataInizioPianificato));
				resultEl.setDataPassaggioInEsecuzione(result.get(faseODS.dataPassaggioInEsecuzione));
				resultEl.setDescrizioneFase(result.get(faseODS.descrizioneFase));
				resultEl.setDmalmFasePk(result.get(faseODS.dmalmFasePk));
				resultEl.setDmalmProjectFk02(result.get(faseODS.dmalmProjectFk02));
				resultEl.setDmalmStatoWorkitemFk03(result.get(faseODS.dmalmStatoWorkitemFk03));
				resultEl.setDmalmStrutturaOrgFk01(result.get(faseODS.dmalmStrutturaOrgFk01));
				resultEl.setDmalmTempoFk04(result.get(faseODS.dmalmTempoFk04));
				resultEl.setDsAutoreFase(result.get(faseODS.dsAutoreFase));
				resultEl.setDtCambioStatoFase(result.get(faseODS.dtCambioStatoFase));
				resultEl.setDtCaricamentoFase(result.get(faseODS.dtCaricamentoFase));
				resultEl.setDtCreazioneFase(result.get(faseODS.dtCreazioneFase));
				resultEl.setDtModificaFase(result.get(faseODS.dtModificaFase));
				resultEl.setDtRisoluzioneFase(result.get(faseODS.dtRisoluzioneFase));
				resultEl.setDtScadenzaFase(result.get(faseODS.dtScadenzaFase));
				resultEl.setDtStoricizzazione(result.get(faseODS.dtStoricizzazione));
				resultEl.setDurataEffettivaFase(result.get(faseODS.durataEffettivaFase));
				resultEl.setIdAutoreFase(result.get(faseODS.idAutoreFase));
				resultEl.setIdRepository(result.get(faseODS.idRepository));
				resultEl.setMotivoRisoluzioneFase(result.get(faseODS.motivoRisoluzioneFase));
				resultEl.setRankStatoFase(result.get(faseODS.rankStatoFase));
				resultEl.setRankStatoFaseMese(result.get(faseODS.rankStatoFaseMese));
				resultEl.setStgPk(result.get(faseODS.stgPk));
				resultEl.setUri(result.get(faseODS.uri));
				resultEl.setTitoloFase(result.get(faseODS.titoloFase));
				resultEl.setSeverity(result.get(faseODS.severity));
				resultEl.setPriority(result.get(faseODS.priority));
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
