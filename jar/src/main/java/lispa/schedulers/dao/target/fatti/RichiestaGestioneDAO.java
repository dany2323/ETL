package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_RICHIESTA_GESTIONE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import lispa.schedulers.bean.target.fatti.DmalmRichiestaGestione;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaGestione;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

public class RichiestaGestioneDAO {

	private static Logger logger = Logger.getLogger(RichiestaGestioneDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmRichiestaGestione rchgs = QDmalmRichiestaGestione.dmalmRichiestaGestione;

	public static List<DmalmRichiestaGestione> getAllRichiestaGestione(
			Timestamp dataEsecuzione) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmRichiestaGestione bean = null;
		List<DmalmRichiestaGestione> richiesteGestione = new ArrayList<DmalmRichiestaGestione>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.richiesta_gestione));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					SQL_RICHIESTA_GESTIONE);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmRichiestaGestione();

				bean.setCategoria(rs.getString("CF_categoria"));
				bean.setCdRichiestaGest(rs.getString("CD_RICH_GESTIONE"));
				bean.setDataChiusura(rs
						.getTimestamp("DATA_CHIUSURA_RICH_GESTIONE"));
				bean.setDataDisponibilita(rs.getTimestamp("CF_data_disp"));
				bean.setDescrizioneRichiestaGest(rs
						.getString("DESCRIZIONE_RICH_GESTIONE"));
				bean.setDmalmProjectFk02(rs.getLong("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDmalmRichiestaGestPk(rs
						.getLong("DMALM_RICH_GESTIONE_PK"));
				bean.setDsAutoreRichiestaGest(rs
						.getString("NOME_AUTORE_RICH_GESTIONE"));
				bean.setDtCaricamentoRichiestaGest(dataEsecuzione);
				bean.setDtCreazioneRichiestaGest(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD_RG"));
				bean.setDtModificaRichiestaGest(rs
						.getTimestamp("DATA_MODIFICA_RICH_GESTIONE"));
				bean.setDtRisoluzioneRichiestaGest(rs
						.getTimestamp("DATA_RISOLUZIONE_RICH_GESTIONE"));
				bean.setDtScadenzaRichiestaGest(rs
						.getTimestamp("DATA_SCADENZA_RICH_GESTIONE"));
				bean.setIdAutoreRichiestaGest(rs
						.getString("ID_AUTORE_RICH_GESTIONE"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzioneRichGest(rs
						.getString("MOTIVO_RISOLUZIONE_RICH_GEST"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_RICH_GESTIONE_PK"));
				bean.setTicketid(rs.getString("CF_ticketid"));
				bean.setTitoloRichiestaGest(rs
						.getString("TITOLO_RICH_GESTIONE"));
				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));
				bean.setTagAlm(rs.getString("TAG_ALM"));
				bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
				richiesteGestione.add(bean);
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

		return richiesteGestione;
	}

	public static List<Tuple> getRichiestaGestione(
			DmalmRichiestaGestione richiesta) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> richieste = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			richieste = query
					.from(rchgs)
					.where(rchgs.cdRichiestaGest.equalsIgnoreCase(richiesta
							.getCdRichiestaGest()))
					.where(rchgs.idRepository.equalsIgnoreCase(richiesta
							.getIdRepository()))
					.where(rchgs.rankStatoRichiestaGest.eq(new Double(1)))
					.list(rchgs.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return richieste;

	}

	public static void insertRichiestaGestione(DmalmRichiestaGestione richiesta)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, rchgs)
					.columns(rchgs.categoria, rchgs.cdRichiestaGest,
							rchgs.dataChiusura, rchgs.dataDisponibilita,
							rchgs.descrizioneRichiestaGest,
							rchgs.dmalmProjectFk02, rchgs.dmalmRichiestaGestPk,
							rchgs.dmalmStatoWorkitemFk03,
							rchgs.dmalmStrutturaOrgFk01, rchgs.dmalmTempoFk04,
							rchgs.dsAutoreRichiestaGest,
							rchgs.dtCambioStatoRichiestaGest,
							rchgs.dtCaricamentoRichiestaGest,
							rchgs.dtCreazioneRichiestaGest,
							rchgs.dtModificaRichiestaGest,
							rchgs.dtRisoluzioneRichiestaGest,
							rchgs.dtScadenzaRichiestaGest,
							rchgs.dtStoricizzazione,
							rchgs.idAutoreRichiestaGest, rchgs.idRepository,
							rchgs.motivoRisoluzioneRichGest,
							rchgs.rankStatoRichiestaGest, rchgs.stgPk,
							rchgs.ticketid, rchgs.titoloRichiestaGest,
							rchgs.dmalmUserFk06, rchgs.uri,
							rchgs.dtAnnullamento,
							rchgs.severity, rchgs.priority,
							rchgs.tsTagAlm,rchgs.tagAlm)
					.values(richiesta.getCategoria(),
							richiesta.getCdRichiestaGest(),
							richiesta.getDataChiusura(),
							richiesta.getDataDisponibilita(),
							richiesta.getDescrizioneRichiestaGest(),
							richiesta.getDmalmProjectFk02(),
							richiesta.getDmalmRichiestaGestPk(),
							richiesta.getDmalmStatoWorkitemFk03(),
							richiesta.getDmalmStrutturaOrgFk01(),
							richiesta.getDmalmTempoFk04(),
							richiesta.getDsAutoreRichiestaGest(),
							richiesta.getDtCambioStatoRichiestaGest(),
							richiesta.getDtCaricamentoRichiestaGest(),
							richiesta.getDtCreazioneRichiestaGest(),
							richiesta.getDtModificaRichiestaGest(),
							richiesta.getDtRisoluzioneRichiestaGest(),
							richiesta.getDtScadenzaRichiestaGest(),
							richiesta.getDtModificaRichiestaGest(),
							richiesta.getIdAutoreRichiestaGest(),
							richiesta.getIdRepository(),
							richiesta.getMotivoRisoluzioneRichGest(),
							new Double(1), richiesta.getStgPk(),
							richiesta.getTicketid(),
							richiesta.getTitoloRichiestaGest(),
							richiesta.getDmalmUserFk06(), richiesta.getUri(),
							richiesta.getDtAnnullamento(),
							richiesta.getSeverity(), richiesta.getPriority(),
							richiesta.getTsTagAlm(),richiesta.getTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmRichiestaGestione richiesta,
			Double double1) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, rchgs)
					.where(rchgs.cdRichiestaGest.equalsIgnoreCase(richiesta
							.getCdRichiestaGest()))
					.where(rchgs.idRepository.equalsIgnoreCase(richiesta
							.getIdRepository()))
					.set(rchgs.rankStatoRichiestaGest, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertRichiestaGestioneUpdate(Timestamp dataEsecuzione,
			DmalmRichiestaGestione richiesta, boolean pkValue)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, rchgs)
					.columns(rchgs.categoria, rchgs.cdRichiestaGest,
							rchgs.dataChiusura, rchgs.dataDisponibilita,
							rchgs.descrizioneRichiestaGest,
							rchgs.dmalmProjectFk02, rchgs.dmalmRichiestaGestPk,
							rchgs.dmalmStatoWorkitemFk03,
							rchgs.dmalmStrutturaOrgFk01, rchgs.dmalmTempoFk04,
							rchgs.dsAutoreRichiestaGest,
							rchgs.dtCambioStatoRichiestaGest,
							rchgs.dtCaricamentoRichiestaGest,
							rchgs.dtCreazioneRichiestaGest,
							rchgs.dtModificaRichiestaGest,
							rchgs.dtRisoluzioneRichiestaGest,
							rchgs.dtScadenzaRichiestaGest,
							rchgs.dtStoricizzazione,
							rchgs.idAutoreRichiestaGest, rchgs.idRepository,
							rchgs.motivoRisoluzioneRichGest,
							rchgs.rankStatoRichiestaGest, rchgs.stgPk,
							rchgs.ticketid, rchgs.titoloRichiestaGest,
							rchgs.dmalmUserFk06, rchgs.uri,
							rchgs.dtAnnullamento, rchgs.changed,
							rchgs.annullato,
							rchgs.severity, rchgs.priority,
							rchgs.tsTagAlm,rchgs.tagAlm)
					.values(richiesta.getCategoria(),
							richiesta.getCdRichiestaGest(),
							richiesta.getDataChiusura(),
							richiesta.getDataDisponibilita(),
							richiesta.getDescrizioneRichiestaGest(),
							richiesta.getDmalmProjectFk02(),
							pkValue == true ? richiesta
									.getDmalmRichiestaGestPk() : StringTemplate
									.create("HISTORY_WORKITEM_SEQ.nextval"),
							richiesta.getDmalmStatoWorkitemFk03(),
							richiesta.getDmalmStrutturaOrgFk01(),
							richiesta.getDmalmTempoFk04(),
							richiesta.getDsAutoreRichiestaGest(),
							richiesta.getDtCambioStatoRichiestaGest(),
							richiesta.getDtCaricamentoRichiestaGest(),
							richiesta.getDtCreazioneRichiestaGest(),
							richiesta.getDtModificaRichiestaGest(),
							richiesta.getDtRisoluzioneRichiestaGest(),
							richiesta.getDtScadenzaRichiestaGest(),
							pkValue == true ? richiesta
									.getDtModificaRichiestaGest() : richiesta
									.getDtStoricizzazione(),
							richiesta.getIdAutoreRichiestaGest(),
							richiesta.getIdRepository(),
							richiesta.getMotivoRisoluzioneRichGest(),
							pkValue == true ? new Short("1") : richiesta
									.getRankStatoRichiestaGest(),
							richiesta.getStgPk(), richiesta.getTicketid(),
							richiesta.getTitoloRichiestaGest(),
							richiesta.getDmalmUserFk06(), richiesta.getUri(),
							richiesta.getDtAnnullamento(),
							richiesta.getChanged(), richiesta.getAnnullato(),
							richiesta.getSeverity(), richiesta.getPriority(),
							richiesta.getTsTagAlm(),richiesta.getTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateTask(DmalmRichiestaGestione richiesta)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, rchgs)

					.where(rchgs.cdRichiestaGest.equalsIgnoreCase(richiesta
							.getCdRichiestaGest()))
					.where(rchgs.idRepository.equalsIgnoreCase(richiesta
							.getIdRepository()))
					.where(rchgs.rankStatoRichiestaGest.eq(new Double(1)))
					.set(rchgs.categoria, richiesta.getCategoria())
					.set(rchgs.cdRichiestaGest, richiesta.getCdRichiestaGest())
					.set(rchgs.dataChiusura, richiesta.getDataChiusura())
					.set(rchgs.dataDisponibilita,
							richiesta.getDataDisponibilita())
					.set(rchgs.descrizioneRichiestaGest,
							richiesta.getDescrizioneRichiestaGest())
					.set(rchgs.dmalmProjectFk02,
							richiesta.getDmalmProjectFk02())
					.set(rchgs.dmalmStatoWorkitemFk03,
							richiesta.getDmalmStatoWorkitemFk03())
					.set(rchgs.dmalmStrutturaOrgFk01,
							richiesta.getDmalmStrutturaOrgFk01())
					.set(rchgs.dmalmTempoFk04, richiesta.getDmalmTempoFk04())
					.set(rchgs.dsAutoreRichiestaGest,
							richiesta.getDsAutoreRichiestaGest())
					.set(rchgs.dtCambioStatoRichiestaGest,
							richiesta.getDtCambioStatoRichiestaGest())
					.set(rchgs.dtCreazioneRichiestaGest,
							richiesta.getDtCreazioneRichiestaGest())
					.set(rchgs.dtModificaRichiestaGest,
							richiesta.getDtModificaRichiestaGest())
					.set(rchgs.dtRisoluzioneRichiestaGest,
							richiesta.getDtRisoluzioneRichiestaGest())
					.set(rchgs.dtScadenzaRichiestaGest,
							richiesta.getDtScadenzaRichiestaGest())
					.set(rchgs.idAutoreRichiestaGest,
							richiesta.getIdAutoreRichiestaGest())
					.set(rchgs.idRepository, richiesta.getIdRepository())
					.set(rchgs.motivoRisoluzioneRichGest,
							richiesta.getMotivoRisoluzioneRichGest())
					.set(rchgs.stgPk, richiesta.getStgPk())
					.set(rchgs.uri, richiesta.getUri())
					.set(rchgs.ticketid, richiesta.getTicketid())
					.set(rchgs.titoloRichiestaGest,
							richiesta.getTitoloRichiestaGest())
					.set(rchgs.dtAnnullamento, richiesta.getDtAnnullamento())
					.set(rchgs.annullato, richiesta.getAnnullato())
					.set(rchgs.severity, richiesta.getSeverity())
					.set(rchgs.priority, richiesta.getPriority())
					.set(rchgs.tagAlm, richiesta.getTagAlm())
					.set(rchgs.tsTagAlm, richiesta.getTsTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmRichiestaGestione getRichiestaGestione(Long pk)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> richieste = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			richieste = query.from(rchgs)
					.where(rchgs.dmalmRichiestaGestPk.eq(pk)).list(rchgs.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (richieste.size() > 0) {
			Tuple t = richieste.get(0);
			DmalmRichiestaGestione r = new DmalmRichiestaGestione();

			r.setAnnullato(t.get(rchgs.annullato));
			r.setCategoria(t.get(rchgs.categoria));
			r.setCdRichiestaGest(t.get(rchgs.cdRichiestaGest));
			r.setChanged(t.get(rchgs.changed));
			r.setDataChiusura(t.get(rchgs.dataChiusura));
			r.setDataDisponibilita(t.get(rchgs.dataDisponibilita));
			r.setDescrizioneRichiestaGest(t.get(rchgs.descrizioneRichiestaGest));
			r.setDmalmProjectFk02(t.get(rchgs.dmalmProjectFk02));
			r.setDmalmRichiestaGestPk(t.get(rchgs.dmalmRichiestaGestPk));
			r.setDmalmStatoWorkitemFk03(t.get(rchgs.dmalmStatoWorkitemFk03));
			r.setDmalmStrutturaOrgFk01(t.get(rchgs.dmalmStrutturaOrgFk01));
			r.setDmalmTempoFk04(t.get(rchgs.dmalmTempoFk04));
			r.setDmalmUserFk06(t.get(rchgs.dmalmUserFk06));
			r.setDsAutoreRichiestaGest(t.get(rchgs.dsAutoreRichiestaGest));
			r.setDtAnnullamento(t.get(rchgs.dtAnnullamento));
			r.setDtCambioStatoRichiestaGest(t
					.get(rchgs.dtCambioStatoRichiestaGest));
			r.setDtCaricamentoRichiestaGest(t
					.get(rchgs.dtCaricamentoRichiestaGest));
			r.setDtCreazioneRichiestaGest(t.get(rchgs.dtCreazioneRichiestaGest));
			r.setDtModificaRichiestaGest(t.get(rchgs.dtModificaRichiestaGest));
			r.setDtRisoluzioneRichiestaGest(t
					.get(rchgs.dtRisoluzioneRichiestaGest));
			r.setDtScadenzaRichiestaGest(t.get(rchgs.dtScadenzaRichiestaGest));
			r.setDtStoricizzazione(t.get(rchgs.dtStoricizzazione));
			r.setIdAutoreRichiestaGest(t.get(rchgs.idAutoreRichiestaGest));
			r.setIdRepository(t.get(rchgs.idRepository));
			r.setMotivoRisoluzioneRichGest(t
					.get(rchgs.motivoRisoluzioneRichGest));
			r.setRankStatoRichiestaGest(t.get(rchgs.rankStatoRichiestaGest));
			r.setRankStatoRichiestaGestMese(t
					.get(rchgs.rankStatoRichiestaGestMese));
			r.setStgPk(t.get(rchgs.stgPk));
			r.setTicketid(t.get(rchgs.ticketid));
			r.setTitoloRichiestaGest(t.get(rchgs.titoloRichiestaGest));
			r.setUri(t.get(rchgs.uri));
			//DM_ALM-320
			r.setSeverity(t.get(rchgs.severity));
			r.setPriority(t.get(rchgs.priority));
			r.setTagAlm(t.get(rchgs.tagAlm));
			r.setTsTagAlm(t.get(rchgs.tsTagAlm));
			
			return r;

		} else
			return null;
	}

	public static boolean checkEsistenzaRichiesta(DmalmRichiestaGestione r,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(rchgs)
					.where(rchgs.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(rchgs.cdRichiestaGest.eq(r.getCdRichiestaGest()))
					.where(rchgs.idRepository.eq(r.getIdRepository()))
					.list(rchgs.all());

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
