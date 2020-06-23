package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_ANOMALIA;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmAnomaliaProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmLinkedWorkitems;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaAssistenza;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmAnomaliaProdotto;
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

public class AnomaliaProdottoDAO {

	private static Logger logger = Logger.getLogger(AnomaliaProdottoDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmAnomaliaProdotto anomaliaProdotto = QDmalmAnomaliaProdotto.dmalmAnomaliaProdotto;

	public static List<DmalmAnomaliaProdotto> getAllAnomaliaProdotto(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmAnomaliaProdotto bean = null;
		List<DmalmAnomaliaProdotto> anomalie = new ArrayList<DmalmAnomaliaProdotto>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.anomalia));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_ANOMALIA);
			ps = connection.prepareStatement(sql);
			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmAnomaliaProdotto();
				bean.setDmalmAnomaliaProdottoPk(rs
						.getInt("DMALM_ANOMALIA_PRODOTTO_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDtCaricamentoRecordAnomalia(dataEsecuzione);
				bean.setDtModificaRecordAnomalia(rs
						.getTimestamp("DATA_MODIFICA_RECORD"));
				bean.setNrGiorniFestivi(rs.getInt("GIORNI_FESTIVI"));
				bean.setCdAnomalia(rs.getString("IDENTIFICATIVO_ANOMALIA"));
				bean.setIdRepository(rs.getString("IDENTIFICATIVO_REPOSITORY"));
				bean.setDsAnomalia(rs.getString("DESCRIZIONE_ANOMALIA"));
				bean.setDsAutoreAnomalia(rs.getString("NOME_AUTORE_ANOMALIA"));

				// DM_ALM-142
				// bean.setDtAperturaTicket(rs
				// .getTimestamp("DATA_APERTURA_TCK_ANOMALIA"));
				// bean.setIdAnomaliaAssistenza(rs
				// .getString("ID_ANOMALIA_ASSISTENZA"));

				bean.setDtChiusuraAnomalia(rs
						.getTimestamp("DATA_CHIUSURA_ANOMALIA"));
				bean.setDtChiusuraTicket(rs
						.getTimestamp("DATA_CHIUSURA_TCK_ANOMALIA"));
				bean.setDtCreazioneAnomalia(rs
						.getTimestamp("DATA_CREAZIONE_ANOMALIA"));
				bean.setDtRisoluzioneAnomalia(rs
						.getTimestamp("DATA_RISOLUZIONE_ANOMALIA"));
				bean.setEffortAnalisi(rs.getInt("EFFORT_ANALISI_ANOMALIA"));
				bean.setEffortCostoSviluppo(rs.getInt("EFFORT_COSTO_SVILUPPO"));
				bean.setIdAutoreAnomalia(rs.getString("USERID_AUTORE_ANOMALIA"));
				bean.setMotivoRisoluzioneAnomalia(rs
						.getString("MOTIVO_RISOLUZIONE"));
				bean.setNumeroLineaAnomalia(StringUtils.getLinea(rs
						.getString("NUMERO_TESTATA_RDI")));
				bean.setNumeroTestataAnomalia(StringUtils.getTestata(rs
						.getString("NUMERO_TESTATA_RDI")));
				bean.setSeverity(rs.getString("SEVERITY_ANOMALIA"));
				bean.setTempoTotRisoluzioneAnomalia(rs
						.getInt("TEMPO_TOTALE_RISOLUZIONE")
						- rs.getInt("GIORNI_FESTIVI"));
				bean.setTicketSiebelAnomaliaAss(rs
						.getString("TICKET_SIEBEL_ANOMALIA"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_PK"));
				bean.setContestazione(rs.getString("CONTESTAZIONE"));
				bean.setNoteContestazione(rs.getString("NOTE_CONTESTAZIONE"));
				bean.setDtDisponibilita(rs.getTimestamp("DATA_DISPONIBILITA"));
				
				//DM_ALM-320
				bean.setPriority(rs.getString("PRIORITY"));
				bean.setTagAlm(rs.getString("TAG_ALM"));
				bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
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

	public static void updateRankFlagUltimaSituazione(
			DmalmAnomaliaProdotto anomalia, double rank, Short ultimaSituazione)
			throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, anomaliaProdotto)
					.where(anomaliaProdotto.cdAnomalia
							.equalsIgnoreCase(anomalia.getCdAnomalia()))
					.where(anomaliaProdotto.idRepository
							.equalsIgnoreCase(anomalia.getIdRepository()))
					.set(anomaliaProdotto.rankStatoAnomalia, rank)
					.set(anomaliaProdotto.flagUltimaSituazione,
							ultimaSituazione).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateDmalmAnomaliaProdotto(
			DmalmAnomaliaProdotto anomalia) throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			new SQLUpdateClause(connection, dialect, anomaliaProdotto)

					.where(anomaliaProdotto.cdAnomalia
							.equalsIgnoreCase(anomalia.getCdAnomalia()))
					.where(anomaliaProdotto.idRepository
							.equalsIgnoreCase(anomalia.getIdRepository()))
					.where(anomaliaProdotto.rankStatoAnomalia.eq(new Double(1)))

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

	public static void insertAnomaliaProdotto(DmalmAnomaliaProdotto bean)
			throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			new SQLInsertClause(connection, dialect, anomaliaProdotto)
					.columns(anomaliaProdotto.cdAnomalia,
							anomaliaProdotto.dmalmAnomaliaProdottoPk,
							anomaliaProdotto.dmalmProjectFk02,
							anomaliaProdotto.dmalmStatoWorkitemFk03,
							anomaliaProdotto.dmalmStrutturaOrgFk01,
							anomaliaProdotto.dmalmTempoFk04,
							anomaliaProdotto.dsAnomalia,
							anomaliaProdotto.dsAutoreAnomalia,
							anomaliaProdotto.dtAperturaTicket,
							anomaliaProdotto.dtCambioStatoAnomalia,
							anomaliaProdotto.dtCaricamentoRecordAnomalia,
							anomaliaProdotto.dtChiusuraAnomalia,
							anomaliaProdotto.dtChiusuraTicket,
							anomaliaProdotto.dtCreazioneAnomalia,
							anomaliaProdotto.dtModificaRecordAnomalia,
							anomaliaProdotto.dtRisoluzioneAnomalia,
							anomaliaProdotto.effortAnalisi,
							anomaliaProdotto.effortCostoSviluppo,
							anomaliaProdotto.idAnomaliaAssistenza,
							anomaliaProdotto.idAutoreAnomalia,
							anomaliaProdotto.dmalmAreaTematicaFk05,
							anomaliaProdotto.motivoRisoluzioneAnomalia,
							anomaliaProdotto.nrGiorniFestivi,
							anomaliaProdotto.numeroLineaAnomalia,
							anomaliaProdotto.numeroTestataAnomalia,
							anomaliaProdotto.rankStatoAnomalia,
							anomaliaProdotto.severity,
							anomaliaProdotto.idRepository,
							anomaliaProdotto.tempoTotRisoluzioneAnomalia,
							anomaliaProdotto.ticketSiebelAnomaliaAss,
							anomaliaProdotto.dtStoricizzazione,
							anomaliaProdotto.stgPk,
							anomaliaProdotto.dmalmUserFk06,
							anomaliaProdotto.uri,
							anomaliaProdotto.flagUltimaSituazione,
							anomaliaProdotto.dtAnnullamento,
							anomaliaProdotto.contestazione,
							anomaliaProdotto.noteContestazione,
							anomaliaProdotto.dataDisponibilita,
							anomaliaProdotto.priority,
							anomaliaProdotto.tagAlm,
							anomaliaProdotto.tsTagAlm)
					.values(bean.getCdAnomalia(),
							bean.getDmalmAnomaliaProdottoPk(),
							bean.getDmalmProjectFk02(),
							bean.getDmalmStatoWorkitemFk03(),
							bean.getDmalmStrutturaOrgFk01(),
							bean.getDmalmTempoFk04(), bean.getDsAnomalia(),
							bean.getDsAutoreAnomalia(),
							bean.getDtAperturaTicket(),
							bean.getDtCambioStatoAnomalia(),
							bean.getDtCaricamentoRecordAnomalia(),
							bean.getDtChiusuraAnomalia(),
							bean.getDtChiusuraTicket(),
							bean.getDtCreazioneAnomalia(),
							bean.getDtModificaRecordAnomalia(),
							bean.getDtRisoluzioneAnomalia(),
							bean.getEffortAnalisi(),
							bean.getEffortCostoSviluppo(),
							bean.getIdAnomaliaAssistenza(),
							bean.getIdAutoreAnomalia(),
							bean.getDmalmAreaTematicaFk05(),
							bean.getMotivoRisoluzioneAnomalia(),
							bean.getNrGiorniFestivi(),
							bean.getNumeroLineaAnomalia(),
							bean.getNumeroTestataAnomalia(), new Double(1),
							bean.getSeverity(), bean.getIdRepository(),
							bean.getTempoTotRisoluzioneAnomalia(),
							bean.getTicketSiebelAnomaliaAss(),
							bean.getDtModificaRecordAnomalia(),
							bean.getStgPk(), bean.getDmalmUserFk06(),
							bean.getUri(), new Short("1"),
							bean.getDtAnnullamento(), bean.getContestazione(),
							bean.getNoteContestazione(),
							bean.getDtDisponibilita(),
							bean.getPriority(),
							bean.getTagAlm(),
							bean.getTsTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertAnomaliaProdottoUpdate(Timestamp dataEsecuzione,
			DmalmAnomaliaProdotto bean, boolean pkValue) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			new SQLInsertClause(connection, dialect, anomaliaProdotto)
					.columns(anomaliaProdotto.cdAnomalia,
							anomaliaProdotto.dmalmAnomaliaProdottoPk,
							anomaliaProdotto.dmalmProjectFk02,
							anomaliaProdotto.dmalmStatoWorkitemFk03,
							anomaliaProdotto.dmalmStrutturaOrgFk01,
							anomaliaProdotto.dmalmTempoFk04,
							anomaliaProdotto.dsAnomalia,
							anomaliaProdotto.dsAutoreAnomalia,
							anomaliaProdotto.dtAperturaTicket,
							anomaliaProdotto.dtCambioStatoAnomalia,
							anomaliaProdotto.dtCaricamentoRecordAnomalia,
							anomaliaProdotto.dtChiusuraAnomalia,
							anomaliaProdotto.dtChiusuraTicket,
							anomaliaProdotto.dtCreazioneAnomalia,
							anomaliaProdotto.dtModificaRecordAnomalia,
							anomaliaProdotto.dtRisoluzioneAnomalia,
							anomaliaProdotto.effortAnalisi,
							anomaliaProdotto.effortCostoSviluppo,
							anomaliaProdotto.idAnomaliaAssistenza,
							anomaliaProdotto.idAutoreAnomalia,
							anomaliaProdotto.dmalmAreaTematicaFk05,
							anomaliaProdotto.motivoRisoluzioneAnomalia,
							anomaliaProdotto.nrGiorniFestivi,
							anomaliaProdotto.numeroLineaAnomalia,
							anomaliaProdotto.numeroTestataAnomalia,
							anomaliaProdotto.rankStatoAnomalia,
							anomaliaProdotto.severity,
							anomaliaProdotto.idRepository,
							anomaliaProdotto.tempoTotRisoluzioneAnomalia,
							anomaliaProdotto.ticketSiebelAnomaliaAss,
							anomaliaProdotto.dtStoricizzazione,
							anomaliaProdotto.stgPk,
							anomaliaProdotto.dmalmUserFk06,
							anomaliaProdotto.uri,
							anomaliaProdotto.flagUltimaSituazione,
							anomaliaProdotto.dtAnnullamento,
							anomaliaProdotto.contestazione,
							anomaliaProdotto.noteContestazione,
							anomaliaProdotto.changed,
							anomaliaProdotto.annullato,
							anomaliaProdotto.dataDisponibilita,
							anomaliaProdotto.priority,
							anomaliaProdotto.tagAlm,
							anomaliaProdotto.tsTagAlm)
					.values(bean.getCdAnomalia(),
							pkValue == true ? bean.getDmalmAnomaliaProdottoPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							bean.getDmalmProjectFk02(),
							bean.getDmalmStatoWorkitemFk03(),
							bean.getDmalmStrutturaOrgFk01(),
							bean.getDmalmTempoFk04(),
							bean.getDsAnomalia(),
							bean.getDsAutoreAnomalia(),
							bean.getDtAperturaTicket(),
							bean.getDtCambioStatoAnomalia(),
							bean.getDtCaricamentoRecordAnomalia(),
							bean.getDtChiusuraAnomalia(),
							bean.getDtChiusuraTicket(),
							bean.getDtCreazioneAnomalia(),
							bean.getDtModificaRecordAnomalia(),
							bean.getDtRisoluzioneAnomalia(),
							bean.getEffortAnalisi(),
							bean.getEffortCostoSviluppo(),
							bean.getIdAnomaliaAssistenza(),
							bean.getIdAutoreAnomalia(),
							bean.getDmalmAreaTematicaFk05(),
							bean.getMotivoRisoluzioneAnomalia(),
							bean.getNrGiorniFestivi(),
							bean.getNumeroLineaAnomalia(),
							bean.getNumeroTestataAnomalia(),
							pkValue == true ? new Short("1")  : bean.getRankStatoAnomalia(),
							bean.getSeverity(),
							bean.getIdRepository(),
							bean.getTempoTotRisoluzioneAnomalia(),
							bean.getTicketSiebelAnomaliaAss(),
							pkValue == true ? bean
									.getDtModificaRecordAnomalia() : bean
									.getDtStoricizzazione(),
							bean.getStgPk(),
							bean.getDmalmUserFk06(),
							bean.getUri(),
							pkValue == true ? new Short("1") : bean
									.getFlagUltimaSituazione(),
							bean.getDtAnnullamento(), bean.getContestazione(),
							bean.getNoteContestazione(), bean.getChanged(),
							bean.getAnnullato(),
							bean.getDtDisponibilita(),
							bean.getPriority(),
							bean.getTagAlm(),
							bean.getTsTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

	}

	public static List<Tuple> getAnomaliaProdotto(DmalmAnomaliaProdotto bean)
			throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> anomalie = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			anomalie = query
					.from(anomaliaProdotto)
					.where(anomaliaProdotto.cdAnomalia.equalsIgnoreCase(bean
							.getCdAnomalia()))
					.where(anomaliaProdotto.idRepository.equalsIgnoreCase(bean
							.getIdRepository()))
					.where(anomaliaProdotto.rankStatoAnomalia.eq(new Double(1)))
					.list(anomaliaProdotto.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return anomalie;
	}

	public static void updateUltimaVersione(String endYearMonth)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConstants.SQL_UPDATE_ULTIMA_VERSIONE_ANOMALIA);

			ps = connection.prepareStatement(sql);

			ps.setString(1, endYearMonth);

			ps.executeUpdate();

			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			throw new DAOException(e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<Tuple> getLinkAnomaliaAssistenza() throws DAOException,
			SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = null;
		QDmalmLinkedWorkitems link = QDmalmLinkedWorkitems.dmalmLinkedWorkitems;
		QDmalmAnomaliaAssistenza anomaliaAssistenza = QDmalmAnomaliaAssistenza.dmalmAnomaliaAssistenza;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(anomaliaProdotto)
					.leftJoin(link)
					.on(link.codiceWiFiglio.eq(anomaliaProdotto.cdAnomalia)
							.and(link.tipoWiPadre.eq("anomalia_assistenza")
									.and(link.tipoWiFiglio.eq("anomalia"))))
					.leftJoin(anomaliaAssistenza)
					.on(anomaliaAssistenza.cdAnomaliaAss.eq(link.codiceWiPadre)
							.and(anomaliaAssistenza.rankStatoAnomaliaAss.eq(
									new Double("1")).and(
									anomaliaAssistenza.annullato.isNull())))
					.where(anomaliaProdotto.rankStatoAnomalia
							.eq(new Double("1")))
					.orderBy(anomaliaProdotto.cdAnomalia.asc())
					.orderBy(anomaliaAssistenza.cdAnomaliaAss.asc(),
							anomaliaAssistenza.dtCreazioneAnomaliaAss.asc())
					.list(anomaliaProdotto.cdAnomalia,
							anomaliaProdotto.idRepository,
							anomaliaProdotto.idAnomaliaAssistenza,
							anomaliaProdotto.dtAperturaTicket,
							anomaliaAssistenza.cdAnomaliaAss,
							anomaliaAssistenza.dtCreazioneAnomaliaAss);

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return list;
	}

	public static void updateIdAnomaliaAssistenza(DmalmAnomaliaProdotto anomalia)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, anomaliaProdotto)
					.where(anomaliaProdotto.cdAnomalia
							.equalsIgnoreCase(anomalia.getCdAnomalia()))
					.where(anomaliaProdotto.idRepository
							.equalsIgnoreCase(anomalia.getIdRepository()))
					.where(anomaliaProdotto.rankStatoAnomalia.eq(new Double(1)))
					.set(anomaliaProdotto.idAnomaliaAssistenza,
							anomalia.getIdAnomaliaAssistenza())
					.set(anomaliaProdotto.dtAperturaTicket,
							anomalia.getDtAperturaTicket()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static DmalmAnomaliaProdotto getAnomaliaProdotto(Integer pk)
			throws DAOException

	{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> anomalie = new ArrayList<Tuple>();
		DmalmAnomaliaProdotto ret = new DmalmAnomaliaProdotto();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			anomalie = query.from(anomaliaProdotto)
					.where(anomaliaProdotto.dmalmAnomaliaProdottoPk.eq(pk))
					.list(anomaliaProdotto.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (anomalie.size() > 0) {
			Tuple t = anomalie.get(0);
			ret.setAnnullato(t.get(anomaliaProdotto.annullato));
			ret.setCdAnomalia(t.get(anomaliaProdotto.cdAnomalia));
			ret.setChanged(t.get(anomaliaProdotto.changed));
			ret.setContestazione(t.get(anomaliaProdotto.contestazione));
			ret.setDescrizioneAnomalia(t
					.get(anomaliaProdotto.descrizioneAnomalia));
			ret.setDmalmAnomaliaProdottoPk(t
					.get(anomaliaProdotto.dmalmAnomaliaProdottoPk));
			ret.setDmalmAreaTematicaFk05(t
					.get(anomaliaProdotto.dmalmAreaTematicaFk05));
			ret.setDmalmProjectFk02(t.get(anomaliaProdotto.dmalmProjectFk02));
			ret.setDmalmStatoWorkitemFk03(t
					.get(anomaliaProdotto.dmalmStatoWorkitemFk03));
			ret.setDmalmStrutturaOrgFk01(t
					.get(anomaliaProdotto.dmalmStrutturaOrgFk01));
			ret.setDmalmTempoFk04(t.get(anomaliaProdotto.dmalmTempoFk04));
			ret.setDmalmUserFk06(t.get(anomaliaProdotto.dmalmUserFk06));
			ret.setDsAnomalia(t.get(anomaliaProdotto.dsAnomalia));
			ret.setDsAutoreAnomalia(t.get(anomaliaProdotto.dsAutoreAnomalia));
			ret.setDtAnnullamento(t.get(anomaliaProdotto.dtAnnullamento));
			ret.setDtAperturaTicket(t.get(anomaliaProdotto.dtAperturaTicket));
			ret.setDtCambioStatoAnomalia(t
					.get(anomaliaProdotto.dtCambioStatoAnomalia));
			ret.setDtCaricamentoRecordAnomalia(t
					.get(anomaliaProdotto.dtCaricamentoRecordAnomalia));
			ret.setDtChiusuraAnomalia(t
					.get(anomaliaProdotto.dtChiusuraAnomalia));
			ret.setDtChiusuraTicket(t.get(anomaliaProdotto.dtChiusuraTicket));
			ret.setDtCreazioneAnomalia(t
					.get(anomaliaProdotto.dtCreazioneAnomalia));
			ret.setDtModificaRecordAnomalia(t
					.get(anomaliaProdotto.dtModificaRecordAnomalia));
			ret.setDtRisoluzioneAnomalia(t
					.get(anomaliaProdotto.dtRisoluzioneAnomalia));
			ret.setDtStoricizzazione(t.get(anomaliaProdotto.dtStoricizzazione));
			ret.setEffortAnalisi(t.get(anomaliaProdotto.effortAnalisi)
					.intValue());
			ret.setEffortCostoSviluppo(t
					.get(anomaliaProdotto.effortCostoSviluppo));
			ret.setFlagUltimaSituazione(t
					.get(anomaliaProdotto.flagUltimaSituazione));
			ret.setIdAnomaliaAssistenza(t
					.get(anomaliaProdotto.idAnomaliaAssistenza));
			ret.setIdAutoreAnomalia(t.get(anomaliaProdotto.idAutoreAnomalia));
			ret.setIdRepository(t.get(anomaliaProdotto.idRepository));
			ret.setMotivoRisoluzioneAnomalia(t
					.get(anomaliaProdotto.motivoRisoluzioneAnomalia));
			ret.setNoteContestazione(t.get(anomaliaProdotto.noteContestazione));
			ret.setNrGiorniFestivi(t.get(anomaliaProdotto.nrGiorniFestivi));
			ret.setNumeroLineaAnomalia(t
					.get(anomaliaProdotto.numeroLineaAnomalia));
			ret.setNumeroTestataAnomalia(t
					.get(anomaliaProdotto.numeroTestataAnomalia));
			ret.setRankStatoAnomalia(t.get(anomaliaProdotto.rankStatoAnomalia));
			ret.setRankStatoAnomaliaMese(t
					.get(anomaliaProdotto.rankStatoAnomaliaMese));
			ret.setSeverity(t.get(anomaliaProdotto.severity));
			ret.setStgPk(t.get(anomaliaProdotto.stgPk));
			ret.setTempoTotRisoluzioneAnomalia(t
					.get(anomaliaProdotto.tempoTotRisoluzioneAnomalia));
			ret.setTicketSiebelAnomaliaAss(t
					.get(anomaliaProdotto.ticketSiebelAnomaliaAss));
			ret.setUri(t.get(anomaliaProdotto.uri));
			ret.setDtDisponibilita(t.get(anomaliaProdotto.dataDisponibilita));
			
			// DM_ALM-320
			ret.setPriority(t.get(anomaliaProdotto.priority));
			ret.setTagAlm(t.get(anomaliaProdotto.tagAlm));
			ret.setTsTagAlm(t.get(anomaliaProdotto.tsTagAlm));
			return ret;
		} else {
			return null;
		}
	}

	public static boolean checkEsistenzaAnomalia(DmalmAnomaliaProdotto a,
			DmalmProject p) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> anomalie = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			anomalie = query
					.from(anomaliaProdotto)
					.where(anomaliaProdotto.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(anomaliaProdotto.cdAnomalia.eq(a.getCdAnomalia()))
					.where(anomaliaProdotto.idRepository.eq(a.getIdRepository()))
					.list(anomaliaProdotto.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (anomalie.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}