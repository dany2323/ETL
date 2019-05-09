package lispa.schedulers.dao.target.fatti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoDem;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoDem;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

public class ProgettoSviluppoDemandDAO {

	private static Logger logger = Logger.getLogger(AnomaliaProdottoDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProgettoSviluppoDem progetto = QDmalmProgettoSviluppoDem.dmalmProgettoSviluppoDem;

	public static List<DmalmProgettoSviluppoDem> getAllProgettoSviluppoDemand(
			Timestamp dataEsecuzione) throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmProgettoSviluppoDem bean = null;
		List<DmalmProgettoSviluppoDem> progettiSviluppo = new ArrayList<DmalmProgettoSviluppoDem>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.drqs));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_PROGETTO_SVILUPPO_DEMAND);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmProgettoSviluppoDem();

				bean.setCdProgSvilD(rs.getString("CD_PROG_SVIL_D"));
				bean.setCfCodice(rs.getString("CODICE"));
				bean.setCfDataDispEffettiva(rs
						.getTimestamp("DATA_DISPONIBILITA_EFFETTIVA"));
				bean.setCfDataDispPianificata(rs
						.getTimestamp("DATA_DISPONIBILITA_PIANIFICATA"));
				bean.setCfDataInizioEff(rs
						.getTimestamp("DATA_INIZIO_EFFETTIVO"));
				bean.setCfDataInizio(rs.getTimestamp("DATA_INIZIO_PIANIFICATO"));
				bean.setCfFornitura(rs.getString("CLASSE_DI_FORNITURA"));
				bean.setDescrizioneProgSvilD(rs
						.getString("DESCRIZIONE_PROG_SVIL_D"));
				bean.setDmalmProgSvilDPk(rs.getInt("DMALM_PROG_SVIL_D_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreProgSvilD(rs
						.getString("NOME_AUTORE_PROG_SVIL_D"));
				bean.setDtCaricamentoProgSvilD(dataEsecuzione);
				bean.setDtCreazioneProgSvilD(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaProgSvilD(rs
						.getTimestamp("DATA_MODIFICA_PROG_SVIL_D"));
				bean.setDtPassaggioEsercizio(rs
						.getTimestamp("DATA_PASS_IN_ES_PROG_SVIL_D"));
				bean.setDtRisoluzioneProgSvilD(rs
						.getTimestamp("DATA_RISOLUZIONE_PROG_SVIL_D"));
				bean.setIdAutoreProgSvilD(rs.getString("ID_AUTORE_PROG_SVIL_D"));
				bean.setDtScadenzaProgSvilD(rs
						.getTimestamp("DATA_SCADENZA_PROG_SVIL_D"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzioneProgSvilD(rs
						.getString("MOTIVO_RISOL_PROG_SVIL_D"));
				bean.setPriorityProgettoSvilDemand(rs
						.getString("PRIORITA_PROG_SVIL_D"));
				bean.setSeverityProgettoSvilDemand(rs
						.getString("SEVERITY_PROG_SVIL_D"));
				bean.setTempoTotaleRisoluzione(rs
						.getInt("TEMPO_TOTALE_RISOLUZIONE")
						- rs.getInt("GIORNI_FESTIVI"));
				bean.setTitoloProgSvilD(rs.getString("TITOLO_PROG_SVIL_D"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_PROG_SVIL_D_PK"));

				progettiSviluppo.add(bean);
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

		return progettiSviluppo;
	}

	public static List<Tuple> getProgettoSviluppoDemand(
			DmalmProgettoSviluppoDem progettoSviluppo) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> progetti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			progetti = query
					.from(progetto)
					.where(progetto.cdProgSvilD
							.equalsIgnoreCase(progettoSviluppo.getCdProgSvilD()))
					.where(progetto.idRepository
							.equalsIgnoreCase(progettoSviluppo
									.getIdRepository()))
					.where(progetto.rankStatoProgSvilD.eq(new Double(1)))
					.list(progetto.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return progetti;

	}

	public static void insertProgettoSviluppoDemand(
			DmalmProgettoSviluppoDem progettoSviluppo) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, progetto)
					.columns(progetto.cdProgSvilD, progetto.cfCodice,
							progetto.cfDataDispEffettiva,
							progetto.cfDataDispPianificata,
							progetto.cfDataInizio, progetto.cfDataInizioEff,
							progetto.cfFornitura,
							progetto.descrizioneProgSvilD,
							progetto.dmalmProgSvilDPk,
							progetto.dmalmProjectFk02,
							progetto.dmalmStatoWorkitemFk03,
							progetto.dmalmStrutturaOrgFk01,
							progetto.dmalmTempoFk04,
							progetto.dsAutoreProgSvilD,
							progetto.dtCambioStatoProgSvilD,
							progetto.dtCaricamentoProgSvilD,
							progetto.dtCreazioneProgSvilD,
							progetto.dtModificaProgSvilD,
							progetto.dtPassaggioEsercizio,
							progetto.dtRisoluzioneProgSvilD,
							progetto.dtScadenzaProgSvilD,
							progetto.dtStoricizzazione,
							progetto.idAutoreProgSvilD, progetto.idRepository,
							progetto.motivoRisoluzioneProgSvilD,
							progetto.priorityProgettoSvilDemand,
							progetto.rankStatoProgSvilD,
							progetto.severityProgettoSvilDemand,
							progetto.tempoTotaleRisoluzione,
							progetto.titoloProgSvilD, progetto.stgPk,
							progetto.dmalmUserFk06, progetto.uri,
							progetto.dtAnnullamento)
					.values(progettoSviluppo.getCdProgSvilD(),
							progettoSviluppo.getCfCodice(),
							progettoSviluppo.getCfDataDispEffettiva(),
							progettoSviluppo.getCfDataDispPianificata(),
							progettoSviluppo.getCfDataInizio(),
							progettoSviluppo.getCfDataInizioEff(),
							progettoSviluppo.getCfFornitura(),
							progettoSviluppo.getDescrizioneProgSvilD(),
							progettoSviluppo.getDmalmProgSvilDPk(),
							progettoSviluppo.getDmalmProjectFk02(),
							progettoSviluppo.getDmalmStatoWorkitemFk03(),
							progettoSviluppo.getDmalmStrutturaOrgFk01(),
							progettoSviluppo.getDmalmTempoFk04(),
							progettoSviluppo.getDsAutoreProgSvilD(),
							progettoSviluppo.getDtCambioStatoProgSvilD(),
							progettoSviluppo.getDtCaricamentoProgSvilD(),
							progettoSviluppo.getDtCreazioneProgSvilD(),
							progettoSviluppo.getDtModificaProgSvilD(),
							progettoSviluppo.getDtPassaggioEsercizio(),
							progettoSviluppo.getDtRisoluzioneProgSvilD(),
							progettoSviluppo.getDtScadenzaProgSvilD(),
							progettoSviluppo.getDtModificaProgSvilD(),
							progettoSviluppo.getIdAutoreProgSvilD(),
							progettoSviluppo.getIdRepository(),
							progettoSviluppo.getMotivoRisoluzioneProgSvilD(),
							progettoSviluppo.getPriorityProgettoSvilDemand(),
							new Double(1),
							progettoSviluppo.getSeverityProgettoSvilDemand(),
							progettoSviluppo.getTempoTotaleRisoluzione(),
							progettoSviluppo.getTitoloProgSvilD(),
							progettoSviluppo.getStgPk(),
							progettoSviluppo.getDmalmUserFk06(),
							progettoSviluppo.getUri(),
							progettoSviluppo.getDtAnnullamento()).execute();
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmProgettoSviluppoDem progettoSviluppo,
			Double double1) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progetto)
					.where(progetto.cdProgSvilD
							.equalsIgnoreCase(progettoSviluppo.getCdProgSvilD()))
					.where(progetto.idRepository
							.equalsIgnoreCase(progettoSviluppo
									.getIdRepository()))
					.set(progetto.rankStatoProgSvilD, double1).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertProgettoSviluppoDemUpdate(
			Timestamp dataEsecuzione,
			DmalmProgettoSviluppoDem progettoSviluppo, boolean pkValue)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, progetto)
					.columns(progetto.cdProgSvilD, progetto.cfCodice,
							progetto.cfDataDispEffettiva,
							progetto.cfDataDispPianificata,
							progetto.cfDataInizio, progetto.cfDataInizioEff,
							progetto.cfFornitura,
							progetto.descrizioneProgSvilD,
							progetto.dmalmProgSvilDPk,
							progetto.dmalmProjectFk02,
							progetto.dmalmStatoWorkitemFk03,
							progetto.dmalmStrutturaOrgFk01,
							progetto.dmalmTempoFk04,
							progetto.dsAutoreProgSvilD,
							progetto.dtCambioStatoProgSvilD,
							progetto.dtCaricamentoProgSvilD,
							progetto.dtCreazioneProgSvilD,
							progetto.dtModificaProgSvilD,
							progetto.dtPassaggioEsercizio,
							progetto.dtRisoluzioneProgSvilD,
							progetto.dtScadenzaProgSvilD,
							progetto.dtStoricizzazione,
							progetto.idAutoreProgSvilD, progetto.idRepository,
							progetto.motivoRisoluzioneProgSvilD,
							progetto.priorityProgettoSvilDemand,
							progetto.rankStatoProgSvilD,
							progetto.severityProgettoSvilDemand,
							progetto.tempoTotaleRisoluzione,
							progetto.titoloProgSvilD, progetto.stgPk,
							progetto.dmalmUserFk06, progetto.uri,
							progetto.dtAnnullamento, progetto.changed,
							progetto.annullato)
					.values(progettoSviluppo.getCdProgSvilD(),
							progettoSviluppo.getCfCodice(),
							progettoSviluppo.getCfDataDispEffettiva(),
							progettoSviluppo.getCfDataDispPianificata(),
							progettoSviluppo.getCfDataInizio(),
							progettoSviluppo.getCfDataInizioEff(),
							progettoSviluppo.getCfFornitura(),
							progettoSviluppo.getDescrizioneProgSvilD(),
							pkValue == true ? progettoSviluppo
									.getDmalmProgSvilDPk() : StringTemplate
									.create("HISTORY_WORKITEM_SEQ.nextval"),
							progettoSviluppo.getDmalmProjectFk02(),
							progettoSviluppo.getDmalmStatoWorkitemFk03(),
							progettoSviluppo.getDmalmStrutturaOrgFk01(),
							progettoSviluppo.getDmalmTempoFk04(),
							progettoSviluppo.getDsAutoreProgSvilD(),
							progettoSviluppo.getDtCambioStatoProgSvilD(),
							progettoSviluppo.getDtCaricamentoProgSvilD(),
							progettoSviluppo.getDtCreazioneProgSvilD(),
							progettoSviluppo.getDtModificaProgSvilD(),
							progettoSviluppo.getDtPassaggioEsercizio(),
							progettoSviluppo.getDtRisoluzioneProgSvilD(),
							progettoSviluppo.getDtScadenzaProgSvilD(),
							pkValue == true ? progettoSviluppo
									.getDtModificaProgSvilD()
									: progettoSviluppo.getDtStoricizzazione(),
							progettoSviluppo.getIdAutoreProgSvilD(),
							progettoSviluppo.getIdRepository(),
							progettoSviluppo.getMotivoRisoluzioneProgSvilD(),
							progettoSviluppo.getPriorityProgettoSvilDemand(),
							pkValue == true ? new Short("1")  : progettoSviluppo.getRankStatoProgSvilD(),
							progettoSviluppo.getSeverityProgettoSvilDemand(),
							progettoSviluppo.getTempoTotaleRisoluzione(),
							progettoSviluppo.getTitoloProgSvilD(),
							progettoSviluppo.getStgPk(),
							progettoSviluppo.getDmalmUserFk06(),
							progettoSviluppo.getUri(),
							progettoSviluppo.getDtAnnullamento(),
							progettoSviluppo.getChanged(),
							progettoSviluppo.getAnnullato()).execute();
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateProgettoSviluppoDem(
			DmalmProgettoSviluppoDem progettoSviluppoDem) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progetto)
					.where(progetto.cdProgSvilD
							.equalsIgnoreCase(progettoSviluppoDem
									.getCdProgSvilD()))
					.where(progetto.idRepository
							.equalsIgnoreCase(progettoSviluppoDem
									.getIdRepository()))
					.where(progetto.rankStatoProgSvilD.eq(new Double(1)))
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
					.set(progetto.annullato, progettoSviluppoDem.getAnnullato()).execute();

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmProgettoSviluppoDem getProgettoSviluppoDemand(Integer pk)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> progetti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			progetti = query.from(progetto)
					.where(progetto.dmalmProgSvilDPk.eq(pk))
					.list(progetto.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (progetti.size() > 0) {
			Tuple t = progetti.get(0);
			DmalmProgettoSviluppoDem p = new DmalmProgettoSviluppoDem();

			p.setAnnullato(t.get(progetto.annullato));
			p.setAssignee(t.get(progetto.assignee));
			p.setCdProgSvilD(t.get(progetto.cdProgSvilD));
			p.setCfCodice(t.get(progetto.cfCodice));
			p.setCfDataDispEffettiva(t.get(progetto.cfDataDispEffettiva));
			p.setCfDataDispPianificata(t.get(progetto.cfDataDispPianificata));
			p.setCfDataInizio(t.get(progetto.cfDataInizio));
			p.setCfDataInizioEff(t.get(progetto.cfDataInizioEff));
			p.setCfFornitura(t.get(progetto.cfFornitura));
			p.setChanged(t.get(progetto.changed));
			p.setDescrizioneProgSvilD(t.get(progetto.descrizioneProgSvilD));
			p.setDmalmProgSvilDPk(t.get(progetto.dmalmProgSvilDPk));
			p.setDmalmProjectFk02(t.get(progetto.dmalmProjectFk02));
			p.setDmalmStatoWorkitemFk03(t.get(progetto.dmalmStatoWorkitemFk03));
			p.setDmalmStrutturaOrgFk01(t.get(progetto.dmalmStrutturaOrgFk01));
			p.setDmalmTempoFk04(t.get(progetto.dmalmTempoFk04));
			p.setDmalmUserFk06(t.get(progetto.dmalmUserFk06));
			p.setDsAutoreProgSvilD(t.get(progetto.dsAutoreProgSvilD));
			p.setDtAnnullamento(t.get(progetto.dtAnnullamento));
			p.setDtCambioStatoProgSvilD(t.get(progetto.dtCambioStatoProgSvilD));
			p.setDtCaricamentoProgSvilD(t.get(progetto.dtCaricamentoProgSvilD));
			p.setDtCreazioneProgSvilD(t.get(progetto.dtCreazioneProgSvilD));
			p.setDtModificaProgSvilD(t.get(progetto.dtModificaProgSvilD));
			p.setDtPassaggioEsercizio(t.get(progetto.dtPassaggioEsercizio));
			p.setDtRisoluzioneProgSvilD(t.get(progetto.dtRisoluzioneProgSvilD));
			p.setDtScadenza(t.get(progetto.dtScadenzaProgSvilD));
			p.setDtScadenzaProgSvilD(t.get(progetto.dtScadenzaProgSvilD));
			p.setDtStoricizzazione(t.get(progetto.dtStoricizzazione));
			p.setIdAutoreProgSvilD(t.get(progetto.idAutoreProgSvilD));
			p.setIdRepository(t.get(progetto.idRepository));
			p.setMotivoRisoluzioneProgSvilD(t
					.get(progetto.motivoRisoluzioneProgSvilD));
			p.setRankStatoProgSvilD(t.get(progetto.rankStatoProgSvilD));
			p.setRankStatoProgSvilDMese(t.get(progetto.rankStatoProgSvilDMese));
			p.setPriorityProgettoSvilDemand(t
					.get(progetto.priorityProgettoSvilDemand));
			p.setSeverityProgettoSvilDemand(t
					.get(progetto.severityProgettoSvilDemand));
			p.setStgPk(t.get(progetto.stgPk));
			p.setTempoTotaleRisoluzione(t.get(progetto.tempoTotaleRisoluzione));
			p.setTitoloProgSvilD(t.get(progetto.titoloProgSvilD));
			p.setUri(t.get(progetto.uri));

			return p;

		} else
			return null;

	}

	public static boolean checkEsistenzaProgetto(DmalmProgettoSviluppoDem d,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progetto)
					.where(progetto.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(progetto.cdProgSvilD.eq(d.getCdProgSvilD()))
					.where(progetto.idRepository.eq(d.getIdRepository()))
					.list(progetto.all());

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
