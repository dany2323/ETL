package lispa.schedulers.dao.target.fatti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaAssistenza;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

public class AnomaliaAssistenzaDAO {

	private static Logger logger = Logger.getLogger(TaskDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmAnomaliaAssistenza anomass = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;

	public static List<DmalmAnomaliaAssistenza> getAllAnomaliaAssistenza(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmAnomaliaAssistenza bean = null;
		List<DmalmAnomaliaAssistenza> anomalie = new ArrayList<DmalmAnomaliaAssistenza>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.anomalia_assistenza));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_ANOMALIA_ASSISTENZA);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmAnomaliaAssistenza();

				bean.setCdAnomaliaAss(rs.getString("CD_ANOM_ASS"));
				bean.setDescrizioneAnomaliaAss(rs.getString("DESCRIPTION"));
				bean.setDmalmAnomaliaAssPk(rs.getInt("DMALM_ANOM_ASS_PK"));

				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));

				bean.setDsAutoreAnomaliaAss(rs
						.getString("NOME_AUTORE_ANOM_ASS"));
				bean.setDtCaricamentoAnomaliaAss(dataEsecuzione);
				bean.setDtCreazioneAnomaliaAss(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaAnomaliaAss(rs
						.getTimestamp("DATA_MODIFICA_ANOM_ASS"));
				bean.setIdAutoreAnomaliaAss(rs.getString("ID_AUTORE_ANOM_ASS"));
				bean.setDtRisoluzioneAnomaliaAss(rs
						.getTimestamp("DATA_RISOLUZIONE_ANOM_ASS"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setDtScadenzaAnomaliaAss(null);
				bean.setMotivoRisoluzioneAnomaliaAs(rs
						.getString("MOTIVO_RISOLUZIONE_ANOM_ASS"));
				bean.setTempoTotaleRisoluzione(rs
						.getInt("TEMPO_TOTALE_RISOLUZIONE")
						- rs.getInt("GIORNI_FESTIVI"));
				bean.setTitoloAnomaliaAss(rs.getString("TITOLO_ANOM_ASS"));
				bean.setCa(rs.getString("CA"));
				bean.setCs(rs.getString("CS"));
				bean.setFrequenza(rs.getString("FREQUENZA"));
				bean.setPlatform(rs.getString("PLATFORM"));
				bean.setProdCod(rs.getString("CODICE_PRODOTTO"));
				bean.setSegnalazioni(rs.getString("SEGNALAZIONI"));
				bean.setSo(rs.getString("SO"));
				bean.setSeverity(rs.getString("SEVERITY_ANOM_ASS"));
				bean.setPriority(rs.getString("PRIORITA_ANOM_ASS"));
				bean.setStChiuso(rs.getTimestamp("STATO_CHIUSO"));
				bean.setTempoTotaleRisoluzione(rs
						.getInt("TEMPO_TOTALE_RISOLUZIONE")
						- rs.getInt("GIORNI_FESTIVI"));
				bean.setTicketid(rs.getString("TICKET_ID"));
				bean.setAoid(rs.getString("AOID"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_ANOM_ASS_PK"));

				anomalie.add(bean);
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

		return anomalie;
	}

	public static List<Tuple> getAnomaliaAssistenza(
			DmalmAnomaliaAssistenza anomalia_assistenza) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> progetti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmAnomaliaAssistenza anomalia = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;

			progetti = query
					.from(anomalia)
					.where(anomalia.cdAnomaliaAss
							.equalsIgnoreCase(anomalia_assistenza
									.getCdAnomaliaAss()))
					.where(anomalia.idRepository
							.equalsIgnoreCase(anomalia_assistenza
									.getIdRepository()))
					.where(anomalia.rankStatoAnomaliaAss.eq(new Double(1)))
					.list(anomalia.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return progetti;
	}

	public static void insertAnomaliaAssistenza(
			DmalmAnomaliaAssistenza anomalia_assistenza) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, anomass)
					.columns(anomass.cdAnomaliaAss, anomass.dmalmAnomaliaAssPk,
							anomass.dmalmProjectFk02,
							anomass.dmalmStatoWorkitemFk03,
							anomass.dmalmStrutturaOrgFk01,
							anomass.dmalmTempoFk04,
							anomass.descrizioneAnomaliaAss,
							anomass.dsAutoreAnomaliaAss, anomass.aoid,
							anomass.ca, anomass.cs,
							anomass.dtCambioStatoAnomaliaAss,
							anomass.dtCaricamentoAnomaliaAss,
							anomass.dtCreazioneAnomaliaAss,
							anomass.dtModificaAnomaliaAss,
							anomass.dtRisoluzioneAnomaliaAss,
							anomass.dtScadenzaAnomaliaAss,
							anomass.dtStoricizzazione, anomass.frequenza,
							anomass.idAutoreAnomaliaAss, anomass.idRepository,
							anomass.motivoRisoluzioneAnomaliaAs,
							anomass.platform, anomass.priority,
							anomass.prodCod, anomass.rankStatoAnomaliaAss,
							anomass.segnalazioni, anomass.severity, anomass.so,
							anomass.stChiuso, anomass.stgPk,
							anomass.tempoTotaleRisoluzione, anomass.ticketid,
							anomass.titoloAnomaliaAss, anomass.dmalmUserFk06,
							anomass.uri, anomass.dtAnnullamento)
					.values(anomalia_assistenza.getCdAnomaliaAss(),
							anomalia_assistenza.getDmalmAnomaliaAssPk(),
							anomalia_assistenza.getDmalmProjectFk02(),
							anomalia_assistenza.getDmalmStatoWorkitemFk03(),
							anomalia_assistenza.getDmalmStrutturaOrgFk01(),
							anomalia_assistenza.getDmalmTempoFk04(),
							anomalia_assistenza.getDescrizioneAnomaliaAss(),
							anomalia_assistenza.getDsAutoreAnomaliaAss(),
							anomalia_assistenza.getAoid(),
							anomalia_assistenza.getCa(),
							anomalia_assistenza.getCs(),
							anomalia_assistenza.getDtCambioStatoAnomaliaAss(),
							anomalia_assistenza.getDtCaricamentoAnomaliaAss(),
							anomalia_assistenza.getDtCreazioneAnomaliaAss(),
							anomalia_assistenza.getDtModificaAnomaliaAss(),
							anomalia_assistenza.getDtRisoluzioneAnomaliaAss(),
							anomalia_assistenza.getDtScadenzaAnomaliaAss(),
							anomalia_assistenza.getDtModificaAnomaliaAss(),
							anomalia_assistenza.getFrequenza(),
							anomalia_assistenza.getIdAutoreAnomaliaAss(),
							anomalia_assistenza.getIdRepository(),
							anomalia_assistenza
									.getMotivoRisoluzioneAnomaliaAs(),
							anomalia_assistenza.getPlatform(),
							anomalia_assistenza.getPriority(),
							anomalia_assistenza.getProdCod(), new Double(1),
							anomalia_assistenza.getSegnalazioni(),
							anomalia_assistenza.getSeverity(),
							anomalia_assistenza.getSo(),
							anomalia_assistenza.getStChiuso(),
							anomalia_assistenza.getStgPk(),
							anomalia_assistenza.getTempoTotaleRisoluzione(),
							anomalia_assistenza.getTicketid(),
							anomalia_assistenza.getTitoloAnomaliaAss(),
							anomalia_assistenza.getDmalmUserFk06(),
							anomalia_assistenza.getUri(),
							anomalia_assistenza.getDtAnnullamento()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateRank(DmalmAnomaliaAssistenza anomalia_assistenza,
			Double double1) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, anomass)
					.where(anomass.cdAnomaliaAss
							.equalsIgnoreCase(anomalia_assistenza
									.getCdAnomaliaAss()))
					.where(anomass.idRepository
							.equalsIgnoreCase(anomalia_assistenza
									.getIdRepository()))
					.set(anomass.rankStatoAnomaliaAss, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertAnomaliaAssistenzaUpdate(Timestamp dataEsecuzione,
			DmalmAnomaliaAssistenza anomalia_assistenza, boolean pkValue)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, anomass)
					.columns(anomass.cdAnomaliaAss, anomass.dmalmAnomaliaAssPk,
							anomass.dmalmProjectFk02,
							anomass.dmalmStatoWorkitemFk03,
							anomass.dmalmStrutturaOrgFk01,
							anomass.dmalmTempoFk04,
							anomass.descrizioneAnomaliaAss,
							anomass.dsAutoreAnomaliaAss, anomass.aoid,
							anomass.ca, anomass.cs,
							anomass.dtCambioStatoAnomaliaAss,
							anomass.dtCaricamentoAnomaliaAss,
							anomass.dtCreazioneAnomaliaAss,
							anomass.dtModificaAnomaliaAss,
							anomass.dtRisoluzioneAnomaliaAss,
							anomass.dtScadenzaAnomaliaAss,
							anomass.dtStoricizzazione, anomass.frequenza,
							anomass.idAutoreAnomaliaAss, anomass.idRepository,
							anomass.motivoRisoluzioneAnomaliaAs,
							anomass.platform, anomass.priority,
							anomass.prodCod, anomass.rankStatoAnomaliaAss,
							anomass.segnalazioni, anomass.severity, anomass.so,
							anomass.stChiuso, anomass.stgPk,
							anomass.tempoTotaleRisoluzione, anomass.ticketid,
							anomass.titoloAnomaliaAss, anomass.dmalmUserFk06,
							anomass.uri, anomass.dtAnnullamento,
							anomass.changed, anomass.annullato)
					.values(anomalia_assistenza.getCdAnomaliaAss(),
							pkValue == true ? anomalia_assistenza
									.getDmalmAnomaliaAssPk() : StringTemplate
									.create("HISTORY_WORKITEM_SEQ.nextval"),
							anomalia_assistenza.getDmalmProjectFk02(),
							anomalia_assistenza.getDmalmStatoWorkitemFk03(),
							anomalia_assistenza.getDmalmStrutturaOrgFk01(),
							anomalia_assistenza.getDmalmTempoFk04(),
							anomalia_assistenza.getDescrizioneAnomaliaAss(),
							anomalia_assistenza.getDsAutoreAnomaliaAss(),
							anomalia_assistenza.getAoid(),
							anomalia_assistenza.getCa(),
							anomalia_assistenza.getCs(),
							anomalia_assistenza.getDtCambioStatoAnomaliaAss(),
							anomalia_assistenza.getDtCaricamentoAnomaliaAss(),
							anomalia_assistenza.getDtCreazioneAnomaliaAss(),
							anomalia_assistenza.getDtModificaAnomaliaAss(),
							anomalia_assistenza.getDtRisoluzioneAnomaliaAss(),
							anomalia_assistenza.getDtScadenzaAnomaliaAss(),
							pkValue == true ? anomalia_assistenza
									.getDtModificaAnomaliaAss()
									: anomalia_assistenza
											.getDtStoricizzazione(),
							anomalia_assistenza.getFrequenza(),
							anomalia_assistenza.getIdAutoreAnomaliaAss(),
							anomalia_assistenza.getIdRepository(),
							anomalia_assistenza
									.getMotivoRisoluzioneAnomaliaAs(),
							anomalia_assistenza.getPlatform(),
							anomalia_assistenza.getPriority(),
							anomalia_assistenza.getProdCod(), 
							pkValue == true ? new Short("1")  : anomalia_assistenza.getRankStatoAnomaliaAss(),
							anomalia_assistenza.getSegnalazioni(),
							anomalia_assistenza.getSeverity(),
							anomalia_assistenza.getSo(),
							anomalia_assistenza.getStChiuso(),
							anomalia_assistenza.getStgPk(),
							anomalia_assistenza.getTempoTotaleRisoluzione(),
							anomalia_assistenza.getTicketid(),
							anomalia_assistenza.getTitoloAnomaliaAss(),
							anomalia_assistenza.getDmalmUserFk06(),
							anomalia_assistenza.getUri(),
							anomalia_assistenza.getDtAnnullamento(),
							anomalia_assistenza.getChanged(),
							anomalia_assistenza.getAnnullato()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateAnomaliaAssistenza(
			DmalmAnomaliaAssistenza anomalia_assistenza) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			new SQLUpdateClause(connection, dialect, anomass)
					.where(anomass.cdAnomaliaAss
							.equalsIgnoreCase(anomalia_assistenza
									.getCdAnomaliaAss()))
					.where(anomass.idRepository
							.equalsIgnoreCase(anomalia_assistenza
									.getIdRepository()))
					.where(anomass.rankStatoAnomaliaAss.eq(new Double(1)))
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
					.set(anomass.annullato, anomalia_assistenza.getAnnullato()).execute();

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static DmalmAnomaliaAssistenza getAnomaliaAssistenza(Integer pk)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmAnomaliaAssistenza anomalia = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;

			list = query.from(anomalia)
					.where(anomalia.dmalmAnomaliaAssPk.eq(pk))
					.list(anomalia.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (list.size() > 0) {
			Tuple t = list.get(0);
			DmalmAnomaliaAssistenza a = new DmalmAnomaliaAssistenza();

			a.setAnnullato(t.get(anomass.annullato));
			a.setAoid(t.get(anomass.aoid));
			a.setCa(t.get(anomass.ca));
			a.setCdAnomaliaAss(t.get(anomass.cdAnomaliaAss));
			a.setChanged(t.get(anomass.changed));
			a.setCs(t.get(anomass.cs));
			a.setDescrizioneAnomaliaAss(t.get(anomass.descrizioneAnomaliaAss));
			a.setDmalmAnomaliaAssPk(t.get(anomass.dmalmAnomaliaAssPk));
			a.setDmalmProjectFk02(t.get(anomass.dmalmProjectFk02));
			a.setDmalmStatoWorkitemFk03(t.get(anomass.dmalmStatoWorkitemFk03));
			a.setDmalmStrutturaOrgFk01(t.get(anomass.dmalmStrutturaOrgFk01));
			a.setDmalmTempoFk04(t.get(anomass.dmalmTempoFk04));
			a.setDmalmUserFk06(t.get(anomass.dmalmUserFk06));
			a.setDsAutoreAnomaliaAss(t.get(anomass.dsAutoreAnomaliaAss));
			a.setDtAnnullamento(t.get(anomass.dtAnnullamento));
			a.setDtCambioStatoAnomaliaAss(t
					.get(anomass.dtCambioStatoAnomaliaAss));
			a.setDtCaricamentoAnomaliaAss(t
					.get(anomass.dtCaricamentoAnomaliaAss));
			a.setDtCreazioneAnomaliaAss(t.get(anomass.dtCreazioneAnomaliaAss));
			a.setDtModificaAnomaliaAss(t.get(anomass.dtModificaAnomaliaAss));
			a.setDtRisoluzioneAnomaliaAss(t
					.get(anomass.dtRisoluzioneAnomaliaAss));
			a.setDtScadenzaAnomaliaAss(t.get(anomass.dtScadenzaAnomaliaAss));
			a.setDtStoricizzazione(t.get(anomass.dtStoricizzazione));
			a.setFrequenza(t.get(anomass.frequenza));
			a.setIdAutoreAnomaliaAss(t.get(anomass.idAutoreAnomaliaAss));
			a.setIdRepository(t.get(anomass.idRepository));
			a.setMotivoRisoluzioneAnomaliaAs(t
					.get(anomass.motivoRisoluzioneAnomaliaAs));
			a.setPlatform(t.get(anomass.platform));
			a.setPriority(t.get(anomass.priority));
			a.setProdCod(t.get(anomass.prodCod));
			a.setRankStatoAnomaliaAss(t.get(anomass.rankStatoAnomaliaAss));
			a.setRankStatoAnomaliaAssMese(t
					.get(anomass.rankStatoAnomaliaAssMese));
			a.setSegnalazioni(t.get(anomass.segnalazioni));
			a.setSeverity(t.get(anomass.severity));
			a.setSo(t.get(anomass.so));
			a.setStChiuso(t.get(anomass.stChiuso));
			a.setStgPk(t.get(anomass.stgPk));
			a.setTempoTotaleRisoluzione(t.get(anomass.tempoTotaleRisoluzione));
			a.setTicketid(t.get(anomass.ticketid));
			a.setTitoloAnomaliaAss(t.get(anomass.titoloAnomaliaAss));
			a.setUri(t.get(anomass.uri));

			return a;
		} else
			return null;
	}

	public static boolean checkEsistenzaAnomalia(DmalmAnomaliaAssistenza a,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(anomass)
					.where(anomass.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(anomass.cdAnomaliaAss.eq(a.getCdAnomaliaAss()))
					.where(anomass.idRepository.eq(a.getIdRepository()))
					.list(anomass.all());

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
