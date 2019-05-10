package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_DEFECT;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmDifettoProdotto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmDifettoProdotto;
import lispa.schedulers.utils.DateUtils;
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

public class DifettoDAO {

	private static Logger logger = Logger.getLogger(DifettoDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmDifettoProdotto difettoProdotto = QDmalmDifettoProdotto.dmalmDifettoProdotto;
	private static QDmalmDifettoProdotto difetto = QDmalmDifettoProdotto.dmalmDifettoProdotto;

	public static List<DmalmDifettoProdotto> getAllDifettoProdotto(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmDifettoProdotto bean = null;
		List<DmalmDifettoProdotto> difetti = new ArrayList<DmalmDifettoProdotto>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.defect));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_DEFECT);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			//DM_ALM-289: rimuovo liste non più utilizzate per il calcolo del campo PROVENIENZA_DIFETTO
			//List<String> SISSutentiIT = SISSUserRolesXML.getUtentiIT();
			//List<String> SIREutentiIT = SIREUserRolesXML.getUtentiIT();
			//SISSutentiIT.addAll(SIREutentiIT);

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmDifettoProdotto();

				bean.setDmalmDifettoProdottoPk(rs
						.getInt("DMALM_DIFETTO_PRODOTTO_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDtCaricamentoRecordDifetto(dataEsecuzione);
				bean.setDtModificaRecordDifetto(rs
						.getTimestamp("DATA_MODIFICA_RECORD"));
				bean.setNrGiorniFestivi(rs.getInt("GIORNI_FESTIVI"));
				bean.setCdDifetto(rs.getString("IDENTIFICATIVO_DIFETTO"));
				bean.setIdRepository(rs.getString("IDENTIFICATIVO_REPOSITORY"));
				bean.setDsDifetto(rs.getString("DESCRIZIONE_DIFETTO"));
				bean.setDsAutoreDifetto(rs.getString("NOME_AUTORE_DIFETTO"));
				bean.setDtChiusuraDifetto(rs
						.getTimestamp("DATA_CHIUSURA_DIFETTO"));
				bean.setDtCreazioneDifetto(rs
						.getTimestamp("DATA_CREAZIONE_DIFETTO"));
				bean.setDtRisoluzioneDifetto(rs
						.getTimestamp("DATA_RISOLUZIONE_DIFETTO"));
				bean.setIdAutoreDifetto(rs.getString("USERID_AUTORE_DIFETTO"));
				bean.setMotivoRisoluzioneDifetto(rs
						.getString("MOTIVO_RISOLUZIONE"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_PK"));
				bean.setNumeroLineaDifetto(StringUtils.getLinea(rs
						.getString("NUMERO_LINEA_RDI")));
				bean.setNumeroTestataDifetto(StringUtils.getTestata(rs
						.getString("NUMERO_LINEA_RDI")));
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setTempoTotRisoluzioneDifetto(rs
						.getDouble("TEMPO_TOTALE_RISOLUZIONE")
						- rs.getInt("GIORNI_FESTIVI"));
				bean.setProvenienzaDifetto(rs.getString("PROVENIENZA_DIFETTO"));
				bean.setRankStatoDifetto(0.0);
				bean.setCausaDifetto(rs.getString("CAUSA"));
				bean.setNaturaDifetto(rs.getString("NATURA"));
				bean.setEffortCostoSviluppo(rs.getInt("EFFORT_COSTO_SVILUPPO"));
				bean.setDtDisponibilita(rs.getTimestamp("DATA_DISPONIBILITA"));
				//DM_ALM-320
				bean.setPriority(rs.getString("PRIORITY"));

				difetti.add(bean);
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

		return difetti;
	}

	public static void updateDataFineValidita(Timestamp dataFineValidita,
			DmalmDifettoProdotto difetto) throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, difettoProdotto)
					.where(difettoProdotto.cdDifetto.equalsIgnoreCase(difetto
							.getCdDifetto()))
					.where(difettoProdotto.idRepository
							.equalsIgnoreCase(difetto.getIdRepository()))
					.set(difettoProdotto.dtModificaRecordDifetto,
							DateUtils.addDaysToTimestamp(dataFineValidita, -1))
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateRankFlagUltimaSituazione(
			DmalmDifettoProdotto difetto, double rank, Short ultimaSituazione)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, difettoProdotto)
					.where(difettoProdotto.cdDifetto.equalsIgnoreCase(difetto
							.getCdDifetto()))
					.where(difettoProdotto.idRepository
							.equalsIgnoreCase(difetto.getIdRepository()))
					.set(difettoProdotto.rankStatoDifetto, rank)
					.set(difettoProdotto.flagUltimaSituazione, ultimaSituazione)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateDmalmDifettoProdotto(DmalmDifettoProdotto difetto)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, difettoProdotto)

					.where(difettoProdotto.cdDifetto.equalsIgnoreCase(difetto
							.getCdDifetto()))
					.where(difettoProdotto.idRepository
							.equalsIgnoreCase(difetto.getIdRepository()))
					.where(difettoProdotto.rankStatoDifetto.eq(new Double(1)))

					.set(difettoProdotto.dtRisoluzioneDifetto,
							difetto.getDtRisoluzioneDifetto())
					.set(difettoProdotto.dtModificaRecordDifetto,
							difetto.getDtModificaRecordDifetto())
					.set(difettoProdotto.dtChiusuraDifetto,
							difetto.getDtChiusuraDifetto())
					.set(difettoProdotto.dtCreazioneDifetto,
							difetto.getDtCreazioneDifetto())
					.set(difettoProdotto.tempoTotRisoluzioneDifetto,
							difetto.getTempoTotRisoluzioneDifetto())
					.set(difettoProdotto.dmalmProjectFk02,
							difetto.getDmalmProjectFk02())
					.set(difettoProdotto.dmalmStrutturaOrgFk01,
							difetto.getDmalmStrutturaOrgFk01())
					.set(difettoProdotto.dmalmStatoWorkitemFk03,
							difetto.getDmalmStatoWorkitemFk03())
					.set(difettoProdotto.idkAreaTematica,
							difetto.getIdkAreaTematica())
					.set(difettoProdotto.idAutoreDifetto,
							difetto.getIdAutoreDifetto())
					.set(difettoProdotto.dsAutoreDifetto,
							difetto.getDsAutoreDifetto())
					.set(difettoProdotto.motivoRisoluzioneDifetto,
							difetto.getMotivoRisoluzioneDifetto())
					.set(difettoProdotto.severity, difetto.getSeverity())
					.set(difettoProdotto.dsDifetto, difetto.getDsDifetto())
					.set(difettoProdotto.provenienzaDifetto,difetto.getProvenienzaDifetto())
					.set(difettoProdotto.causaDifetto,
							difetto.getCausaDifetto())
					.set(difettoProdotto.naturaDifetto,
							difetto.getNaturaDifetto())
					.set(difettoProdotto.stgPk, difetto.getStgPk())
					.set(difettoProdotto.uri, difetto.getUri())
					.set(difettoProdotto.effortCostoSviluppo,
							difetto.getEffortCostoSviluppo())
					.set(difettoProdotto.dtAnnullamento,
							difetto.getDtAnnullamento())
					.set(difettoProdotto.annullato, difetto.getAnnullato())
					.set(difettoProdotto.dataDisponibilita, difetto.getDtDisponibilita())
					//DM_ALM-320
					.set(difettoProdotto.priority, difetto.getPriority())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertDifettoProdotto(DmalmDifettoProdotto bean)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, difetto)
					.columns(difetto.cdDifetto, difetto.dmalmDifettoProdottoPk,
							difetto.dmalmProjectFk02,
							difetto.dmalmStatoWorkitemFk03,
							difetto.dmalmStrutturaOrgFk01,
							difetto.dmalmTempoFk04, difetto.dsAutoreDifetto,
							difetto.dsDifetto, difetto.idRepository,
							difetto.dtCambioStatoDifetto,
							difetto.dtCaricamentoRecordDifetto,
							difetto.dtChiusuraDifetto,
							difetto.dtCreazioneDifetto,
							difetto.dtModificaRecordDifetto,
							difetto.dtRisoluzioneDifetto,
							difetto.idAutoreDifetto, difetto.idkAreaTematica,
							difetto.motivoRisoluzioneDifetto,
							difetto.nrGiorniFestivi,
							difetto.numeroLineaDifetto,
							difetto.numeroTestataDifetto,
							difetto.provenienzaDifetto,
							difetto.rankStatoDifetto, difetto.severity,
							difetto.causaDifetto, difetto.naturaDifetto,
							difetto.tempoTotRisoluzioneDifetto,
							difetto.dtStoricizzazione, difetto.stgPk,
							difetto.dmalmUserFk06, difetto.uri,
							difetto.effortCostoSviluppo,
							difetto.flagUltimaSituazione,
							difetto.dtAnnullamento,
							difetto.dataDisponibilita,
							difetto.priority)
					.values(bean.getCdDifetto(),
							bean.getDmalmDifettoProdottoPk(),
							bean.getDmalmProjectFk02(),
							bean.getDmalmStatoWorkitemFk03(),
							bean.getDmalmStrutturaOrgFk01(),
							bean.getDmalmTempoFk04(),
							bean.getDsAutoreDifetto(), bean.getDsDifetto(),
							bean.getIdRepository(),
							bean.getDtCambioStatoDifetto(),
							bean.getDtCaricamentoRecordDifetto(),
							bean.getDtChiusuraDifetto(),
							bean.getDtCreazioneDifetto(),
							bean.getDtModificaRecordDifetto(),
							bean.getDtRisoluzioneDifetto(),
							bean.getIdAutoreDifetto(),
							bean.getIdkAreaTematica(),
							bean.getMotivoRisoluzioneDifetto(),
							bean.getNrGiorniFestivi(),
							bean.getNumeroLineaDifetto(),
							bean.getNumeroTestataDifetto(),
							bean.getProvenienzaDifetto(), new Double(1),
							bean.getSeverity(), bean.getCausaDifetto(),
							bean.getNaturaDifetto(),
							bean.getTempoTotRisoluzioneDifetto(),
							bean.getDtModificaRecordDifetto(), bean.getStgPk(),
							bean.getDmalmUserFk06(), bean.getUri(),
							bean.getEffortCostoSviluppo(), new Short("1"),
							bean.getDtAnnullamento(),
							bean.getDtDisponibilita(),
							//DM_ALM-320
							bean.getPriority()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertDifettoProdottoUpdate(Timestamp dataEsecuzione,
			DmalmDifettoProdotto bean, boolean pkValue) throws DAOException,
			Exception

	{

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, difetto)
					.columns(difetto.cdDifetto, difetto.dmalmDifettoProdottoPk,
							difetto.dmalmProjectFk02,
							difetto.dmalmStatoWorkitemFk03,
							difetto.dmalmStrutturaOrgFk01,
							difetto.dmalmTempoFk04, difetto.dsAutoreDifetto,
							difetto.dsDifetto, difetto.idRepository,
							difetto.dtCambioStatoDifetto,
							difetto.dtCaricamentoRecordDifetto,
							difetto.dtChiusuraDifetto,
							difetto.dtCreazioneDifetto,
							difetto.dtModificaRecordDifetto,
							difetto.dtRisoluzioneDifetto,
							difetto.idAutoreDifetto, difetto.idkAreaTematica,
							difetto.motivoRisoluzioneDifetto,
							difetto.nrGiorniFestivi,
							difetto.numeroLineaDifetto,
							difetto.numeroTestataDifetto,
							difetto.provenienzaDifetto,
							difetto.rankStatoDifetto, difetto.severity,
							difetto.causaDifetto, difetto.naturaDifetto,
							difetto.tempoTotRisoluzioneDifetto,
							difetto.dtStoricizzazione, difetto.stgPk,
							difetto.dmalmUserFk06, difetto.uri,
							difetto.effortCostoSviluppo,
							difetto.flagUltimaSituazione,
							difetto.dtAnnullamento, difetto.changed,
							difetto.annullato,
							difetto.dataDisponibilita,
							difetto.priority)
					.values(bean.getCdDifetto(),
							pkValue == true ? bean.getDmalmDifettoProdottoPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							bean.getDmalmProjectFk02(),
							bean.getDmalmStatoWorkitemFk03(),
							bean.getDmalmStrutturaOrgFk01(),
							bean.getDmalmTempoFk04(),
							bean.getDsAutoreDifetto(),
							bean.getDsDifetto(),
							bean.getIdRepository(),
							bean.getDtCambioStatoDifetto(),
							bean.getDtCaricamentoRecordDifetto(),
							bean.getDtChiusuraDifetto(),
							bean.getDtCreazioneDifetto(),
							bean.getDtModificaRecordDifetto(),
							bean.getDtRisoluzioneDifetto(),
							bean.getIdAutoreDifetto(),
							bean.getIdkAreaTematica(),
							bean.getMotivoRisoluzioneDifetto(),
							bean.getNrGiorniFestivi(),
							bean.getNumeroLineaDifetto(),
							bean.getNumeroTestataDifetto(),
							bean.getProvenienzaDifetto(),
							pkValue == true ? new Short("1")  : bean.getRankStatoDifetto(),
							bean.getSeverity(),
							bean.getCausaDifetto(),
							bean.getNaturaDifetto(),
							bean.getTempoTotRisoluzioneDifetto(),
							pkValue == true ? bean.getDtModificaRecordDifetto()
									: bean.getDtStoricizzazione(),
							bean.getStgPk(), bean.getDmalmUserFk06(),
							bean.getUri(), bean.getEffortCostoSviluppo(),
							pkValue == true ? new Short("1")  : bean.getFlagUltimaSituazione(), bean.getDtAnnullamento(),
							bean.getChanged(), bean.getAnnullato(),
							bean.getDtDisponibilita(),
							//DM_ALM-320
							bean.getPriority()).execute();

			connection.commit();

		} catch (Exception e) {

			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<Tuple> getDifettoProdotto(DmalmDifettoProdotto bean)
			throws DAOException

	{

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> projects = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			projects = query
					.from(difetto)
					.where(difetto.cdDifetto.equalsIgnoreCase(bean
							.getCdDifetto()))
					.where(difetto.idRepository.equalsIgnoreCase(bean
							.getIdRepository()))
					.where(difetto.rankStatoDifetto.eq(new Double(1)))
					.list(difetto.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return projects;
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
					DmAlmConstants.SQL_UPDATE_ULTIMA_VERSIONE_DIFETTO);

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

	public static DmalmDifettoProdotto getDifetto(Integer pk)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(difetto)
					.where(difetto.dmalmDifettoProdottoPk.eq(pk))
					.list(difetto.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (list.size() > 0) {
			Tuple t = list.get(0);

			DmalmDifettoProdotto d = new DmalmDifettoProdotto();
			d.setAnnullato(t.get(difetto.annullato));
			d.setCausaDifetto(t.get(difetto.causaDifetto));
			d.setCdDifetto(t.get(difetto.cdDifetto));
			d.setChanged(t.get(difetto.changed));
			d.setDmalmDifettoProdottoPk(t.get(difetto.dmalmDifettoProdottoPk));
			d.setDmalmProjectFk02(t.get(difetto.dmalmProjectFk02));
			d.setDmalmStatoWorkitemFk03(t.get(difetto.dmalmStatoWorkitemFk03));
			d.setDmalmStrutturaOrgFk01(t.get(difetto.dmalmStrutturaOrgFk01));
			d.setDmalmTempoFk04(t.get(difetto.dmalmTempoFk04));
			d.setDmalmUserFk06(t.get(difetto.dmalmUserFk06));
			d.setDsAutoreDifetto(t.get(difetto.dsAutoreDifetto));
			d.setDsDifetto(t.get(difetto.dsDifetto));
			d.setDtAnnullamento(t.get(difetto.dtAnnullamento));
			d.setDtCambioStatoDifetto(t.get(difetto.dtCambioStatoDifetto));
			d.setDtCaricamentoRecordDifetto(t
					.get(difetto.dtCaricamentoRecordDifetto));
			d.setDtChiusuraDifetto(t.get(difetto.dtChiusuraDifetto));
			d.setDtCreazioneDifetto(t.get(difetto.dtCreazioneDifetto));
			d.setDtModificaRecordDifetto(t.get(difetto.dtModificaRecordDifetto));
			d.setDtRisoluzioneDifetto(t.get(difetto.dtRisoluzioneDifetto));
			d.setDtStoricizzazione(t.get(difetto.dtStoricizzazione));
			d.setEffortCostoSviluppo(t.get(difetto.effortCostoSviluppo));
			d.setFlagUltimaSituazione(t.get(difetto.flagUltimaSituazione));
			d.setIdAutoreDifetto(t.get(difetto.idAutoreDifetto));
			d.setIdkAreaTematica(t.get(difetto.idkAreaTematica));
			d.setIdRepository(t.get(difetto.idRepository));
			d.setMotivoRisoluzioneDifetto(t
					.get(difetto.motivoRisoluzioneDifetto));
			d.setNaturaDifetto(t.get(difetto.naturaDifetto));
			d.setNrGiorniFestivi(t.get(difetto.nrGiorniFestivi));
			d.setNumeroLineaDifetto(t.get(difetto.numeroLineaDifetto));
			d.setNumeroTestataDifetto(t.get(difetto.numeroTestataDifetto));
			d.setProvenienzaDifetto(t.get(difetto.provenienzaDifetto));
			d.setRankStatoDifetto(t.get(difetto.rankStatoDifetto));
			d.setSeverity(t.get(difetto.severity));
			d.setStgPk(t.get(difetto.stgPk));
			d.setTempoTotRisoluzioneDifetto(t
					.get(difetto.tempoTotRisoluzioneDifetto));
			d.setUri(t.get(difetto.uri));
			d.setDtDisponibilita(t.get(difetto.dataDisponibilita));
			//DM_ALM-320
			d.setPriority(t.get(difetto.priority));

			return d;

		} else
			return null;

	}

	public static boolean checkEsistenzaDifetto(DmalmDifettoProdotto d,
			DmalmProject p) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> difetti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			difetti = query
					.from(difetto)
					.where(difetto.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(difetto.cdDifetto.eq(d.getCdDifetto()))
					.where(difetto.idRepository.eq(d.getIdRepository()))
					.list(difetto.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (difetti.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
}