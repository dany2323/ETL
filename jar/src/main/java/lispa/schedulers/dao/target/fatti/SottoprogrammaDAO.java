package lispa.schedulers.dao.target.fatti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
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
import lispa.schedulers.bean.target.fatti.DmalmSottoprogramma;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.TempoDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmSottoprogramma;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.StringUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

public class SottoprogrammaDAO {
	private static Logger logger = Logger.getLogger(AnomaliaProdottoDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmSottoprogramma sottoprogramma = QDmalmSottoprogramma.dmalmSottoprogramma;

	public static List<DmalmSottoprogramma> getAllSottoprogramma(
			Timestamp dataEsecuzione) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmSottoprogramma bean = null;
		List<DmalmSottoprogramma> sottoprogrammi = new ArrayList<DmalmSottoprogramma>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.sottoprogramma));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_SOTTOPROGRAMMA);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmSottoprogramma();

				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_SOTTOPROGRAMMA_PK"));
				bean.setDmalmSottoprogrammaPk(rs
						.getInt("DMALM_SOTTOPROGRAMMA_PK"));
				bean.setCdSottoprogramma(rs.getString("CD_SOTTOPROGRAMMA"));
				bean.setCodice(rs.getString("CF_CODICE_SOTTOSOTTOPROGRAMMA"));
				bean.setDtCreazioneSottoprogramma(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD_SP"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDtScadenzaSottoprogramma(rs
						.getTimestamp("DATA_SCADENZA"));
				bean.setDtModificaSottoprogramma(rs
						.getTimestamp("DATA_MODIFICA_SOTTOPROGRAMMA"));
				bean.setIdAutoreSottoprogramma(rs
						.getString("ID_AUTORE_SOTTOPROGRAMMA"));
				bean.setDsAutoreSottoprogramma(rs
						.getString("NOME_AUTORE_SOTTOPROGRAMMA"));
				bean.setTitoloSottoprogramma(rs
						.getString("TITOLO_SOTTOPROGRAMMA"));
				bean.setDtRisoluzioneSottoprogramma(rs
						.getTimestamp("DATA_RISOLUZIONE_SP"));
				bean.setMotivoRisoluzioneSottoprogr(rs
						.getString("MOTIVO_RISOLUZIONE_SP"));
				bean.setDescrizioneSottoprogramma(rs
						.getString("DESCRIZIONE_SOTTOPROGRAMMA"));
				bean.setNumeroLinea(StringUtils.getLinea(rs
						.getString("CF_COD_INTERVENTO_SP")));
				bean.setNumeroTestata(StringUtils.getTestata(rs
						.getString("CF_COD_INTERVENTO_SP")));
				bean.setDmalmTempoFk04(TempoDAO.getTempoPkBy(DateUtils
						.stringToTimestamp(new SimpleDateFormat(
								"yyyy-MM-dd 00:00:00").format(rs
								.getTimestamp("DATA_MODIFICA_SOTTOPROGRAMMA")),
								"yyyy-MM-dd 00:00:00")));
				bean.setDtCaricamentoSottoprogramma(dataEsecuzione);
				bean.setDtCompletamento(rs
						.getTimestamp("DATA_COMPLETAMENTO_SP"));

				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));
				
				sottoprogrammi.add(bean);
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

		return sottoprogrammi;
	}

	public static List<Tuple> getSottoprogramma(
			DmalmSottoprogramma sottoprogramma) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> sottoprogrammi = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmSottoprogramma sp = QDmalmSottoprogramma.dmalmSottoprogramma;

			sottoprogrammi = query
					.from(sp)
					.where(sp.cdSottoprogramma.equalsIgnoreCase(sottoprogramma
							.getCdSottoprogramma()))
					.where(sp.idRepository.equalsIgnoreCase(sottoprogramma
							.getIdRepository()))
					.where(sp.rankStatoSottoprogramma.eq(new Double(1)))
					.list(sp.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return sottoprogrammi;

	}

	public static void insertSottoprogramma(DmalmSottoprogramma subprogram)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, sottoprogramma)
					.columns(sottoprogramma.idRepository,
							sottoprogramma.dmalmSottoprogrammaPk,
							sottoprogramma.cdSottoprogramma,
							sottoprogramma.dtCreazioneSottoprogramma,
							sottoprogramma.dmalmProjectFk02,
							sottoprogramma.dmalmStatoWorkitemFk03,
							sottoprogramma.dtCambioStatoSottoprogramma,
							sottoprogramma.dtScadenzaSottoprogramma,
							sottoprogramma.dtModificaSottoprogramma,
							sottoprogramma.idAutoreSottoprogramma,
							sottoprogramma.dsAutoreSottoprogramma,
							sottoprogramma.titoloSottoprogramma,
							sottoprogramma.motivoRisoluzioneSottoprogr,
							sottoprogramma.dtRisoluzioneSottoprogramma,
							sottoprogramma.descrizioneSottoprogramma,
							sottoprogramma.codice,
							sottoprogramma.numeroTestata,
							sottoprogramma.numeroLinea,
							sottoprogramma.dtCompletamento,
							sottoprogramma.rankStatoSottoprogramma,
							sottoprogramma.dtStoricizzazione,
							sottoprogramma.dmalmTempoFk04,
							sottoprogramma.dtCaricamentoSottoprogramma,
							sottoprogramma.stgPk, sottoprogramma.dmalmUserFk06,
							sottoprogramma.uri, sottoprogramma.dtAnnullamento,
							sottoprogramma.severity, sottoprogramma.priority)
					.values(subprogram.getIdRepository(),
							subprogram.getDmalmSottoprogrammaPk(),
							subprogram.getCdSottoprogramma(),
							subprogram.getDtCreazioneSottoprogramma(),
							subprogram.getDmalmProjectFk02(),
							subprogram.getDmalmStatoWorkitemFk03(),
							subprogram.getDtCambioStatoSottoprogramma(),
							subprogram.getDtScadenzaSottoprogramma(),
							subprogram.getDtModificaSottoprogramma(),
							subprogram.getIdAutoreSottoprogramma(),
							subprogram.getDsAutoreSottoprogramma(),
							subprogram.getTitoloSottoprogramma(),
							subprogram.getMotivoRisoluzioneSottoprogr(),
							subprogram.getDtRisoluzioneSottoprogramma(),
							subprogram.getDescrizioneSottoprogramma(),
							subprogram.getCodice(),
							subprogram.getNumeroTestata(),
							subprogram.getNumeroLinea(),
							subprogram.getDtCompletamento(), new Double(1),
							subprogram.getDtModificaSottoprogramma(),
							subprogram.getDmalmTempoFk04(),
							subprogram.getDtCaricamentoSottoprogramma(),
							subprogram.getStgPk(),
							subprogram.getDmalmUserFk06(), subprogram.getUri(),
							subprogram.getDtAnnullamento(),
							subprogram.getSeverity(), subprogram.getPriority()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	/**
	 * 
	 * @param subprogram
	 *            sottoprogramma il cui record andrà inserito nel target
	 * @param double1
	 *            indica l'ultimo record inserito nella storicizzazione a cui
	 *            fare riferimento
	 * @throws DAOException
	 */
	public static void updateRank(DmalmSottoprogramma subprogram, Double double1)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, sottoprogramma)
					.where(sottoprogramma.cdSottoprogramma
							.equalsIgnoreCase(subprogram.getCdSottoprogramma()))
					.where(sottoprogramma.idRepository
							.equalsIgnoreCase(subprogram.getIdRepository()))
					.set(sottoprogramma.rankStatoSottoprogramma, double1)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertSottoprogrammaUpdate(Timestamp dataEsecuzione,
			DmalmSottoprogramma subprogram, boolean pkValue)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, sottoprogramma)
					.columns(sottoprogramma.idRepository,
							sottoprogramma.dmalmSottoprogrammaPk,
							sottoprogramma.cdSottoprogramma,
							sottoprogramma.dtCreazioneSottoprogramma,
							sottoprogramma.dmalmProjectFk02,
							sottoprogramma.dmalmStatoWorkitemFk03,
							sottoprogramma.dtCambioStatoSottoprogramma,
							sottoprogramma.dtScadenzaSottoprogramma,
							sottoprogramma.dtModificaSottoprogramma,
							sottoprogramma.idAutoreSottoprogramma,
							sottoprogramma.dsAutoreSottoprogramma,
							sottoprogramma.titoloSottoprogramma,
							sottoprogramma.motivoRisoluzioneSottoprogr,
							sottoprogramma.dtRisoluzioneSottoprogramma,
							sottoprogramma.descrizioneSottoprogramma,
							sottoprogramma.codice,
							sottoprogramma.numeroTestata,
							sottoprogramma.numeroLinea,
							sottoprogramma.dtCompletamento,
							sottoprogramma.rankStatoSottoprogramma,
							sottoprogramma.dtStoricizzazione,
							sottoprogramma.dmalmTempoFk04,
							sottoprogramma.dtCaricamentoSottoprogramma,
							sottoprogramma.stgPk, sottoprogramma.dmalmUserFk06,
							sottoprogramma.uri, sottoprogramma.dtAnnullamento,
							sottoprogramma.changed, sottoprogramma.annullato,
							sottoprogramma.severity, sottoprogramma.priority)
					.values(subprogram.getIdRepository(),
							pkValue == true ? subprogram
									.getDmalmSottoprogrammaPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							subprogram.getCdSottoprogramma(),
							subprogram.getDtCreazioneSottoprogramma(),
							subprogram.getDmalmProjectFk02(),
							subprogram.getDmalmStatoWorkitemFk03(),
							subprogram.getDtCambioStatoSottoprogramma(),
							subprogram.getDtScadenzaSottoprogramma(),
							subprogram.getDtModificaSottoprogramma(),
							subprogram.getIdAutoreSottoprogramma(),
							subprogram.getDsAutoreSottoprogramma(),
							subprogram.getTitoloSottoprogramma(),
							subprogram.getMotivoRisoluzioneSottoprogr(),
							subprogram.getDtRisoluzioneSottoprogramma(),
							subprogram.getDescrizioneSottoprogramma(),
							subprogram.getCodice(),
							subprogram.getNumeroTestata(),
							subprogram.getNumeroLinea(),
							subprogram.getDtCompletamento(),
							pkValue == true ? new Short("1")  : subprogram.getRankStatoSottoprogramma(),
							pkValue == true ? subprogram
									.getDtModificaSottoprogramma() : subprogram
									.getDtStoricizzazione(),
							subprogram.getDmalmTempoFk04(),
							subprogram.getDtCaricamentoSottoprogramma(),
							subprogram.getStgPk(),
							subprogram.getDmalmUserFk06(), subprogram.getUri(),
							subprogram.getDtAnnullamento(),
							subprogram.getChanged(), subprogram.getAnnullato(),
							subprogram.getSeverity(), subprogram.getPriority())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateSottoprogramma(DmalmSottoprogramma subprogram)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, sottoprogramma)
					.where(sottoprogramma.cdSottoprogramma
							.equalsIgnoreCase(subprogram.getCdSottoprogramma()))
					.where(sottoprogramma.idRepository
							.equalsIgnoreCase(subprogram.getIdRepository()))
					.where(sottoprogramma.rankStatoSottoprogramma
							.eq(new Double(1)))
					.set(sottoprogramma.idRepository,
							subprogram.getIdRepository())
					.set(sottoprogramma.cdSottoprogramma,
							subprogram.getCdSottoprogramma())
					.set(sottoprogramma.dtCreazioneSottoprogramma,
							subprogram.getDtCreazioneSottoprogramma())
					.set(sottoprogramma.dmalmProjectFk02,
							subprogram.getDmalmProjectFk02())
					.set(sottoprogramma.dmalmStatoWorkitemFk03,
							subprogram.getDmalmStatoWorkitemFk03())
					.set(sottoprogramma.dtCambioStatoSottoprogramma,
							subprogram.getDtCambioStatoSottoprogramma())
					.set(sottoprogramma.dtScadenzaSottoprogramma,
							subprogram.getDtScadenzaSottoprogramma())
					.set(sottoprogramma.dtModificaSottoprogramma,
							subprogram.getDtModificaSottoprogramma())
					.set(sottoprogramma.idAutoreSottoprogramma,
							subprogram.getIdAutoreSottoprogramma())
					.set(sottoprogramma.dsAutoreSottoprogramma,
							subprogram.getDsAutoreSottoprogramma())
					.set(sottoprogramma.titoloSottoprogramma,
							subprogram.getTitoloSottoprogramma())
					.set(sottoprogramma.motivoRisoluzioneSottoprogr,
							subprogram.getMotivoRisoluzioneSottoprogr())
					.set(sottoprogramma.dtRisoluzioneSottoprogramma,
							subprogram.getDtRisoluzioneSottoprogramma())
					.set(sottoprogramma.descrizioneSottoprogramma,
							subprogram.getDescrizioneSottoprogramma())
					.set(sottoprogramma.codice, subprogram.getCodice())
					.set(sottoprogramma.numeroTestata,
							subprogram.getNumeroTestata())
					.set(sottoprogramma.numeroLinea,
							subprogram.getNumeroLinea())
					.set(sottoprogramma.dtCompletamento,
							subprogram.getDtCompletamento())
					.set(sottoprogramma.dmalmTempoFk04,
							subprogram.getDmalmTempoFk04())
					.set(sottoprogramma.stgPk, subprogram.getStgPk())
					.set(sottoprogramma.uri, subprogram.getUri())
					.set(sottoprogramma.dtAnnullamento,
							subprogram.getDtAnnullamento())
					.set(sottoprogramma.annullato, subprogram.getAnnullato())
					//DM_ALM-320
					.set(sottoprogramma.severity, subprogram.getSeverity())
					.set(sottoprogramma.priority, subprogram.getPriority())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmSottoprogramma getSottoprogramma(Integer pk)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> sottoprogrammi = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmSottoprogramma sp = QDmalmSottoprogramma.dmalmSottoprogramma;

			sottoprogrammi = query.from(sp)
					.where(sp.dmalmSottoprogrammaPk.eq(pk)).list(sp.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (sottoprogrammi.size() > 0) {
			Tuple t = sottoprogrammi.get(0);
			DmalmSottoprogramma s = new DmalmSottoprogramma();

			s.setAnnullato(t.get(sottoprogramma.annullato));
			s.setCdSottoprogramma(t.get(sottoprogramma.cdSottoprogramma));
			s.setChanged(t.get(sottoprogramma.changed));
			s.setCodice(t.get(sottoprogramma.codice));
			s.setDescrizioneSottoprogramma(t
					.get(sottoprogramma.descrizioneSottoprogramma));
			s.setDmalmProjectFk02(t.get(sottoprogramma.dmalmProjectFk02));
			s.setDmalmSottoprogrammaPk(t
					.get(sottoprogramma.dmalmSottoprogrammaPk));
			s.setDmalmStatoWorkitemFk03(t
					.get(sottoprogramma.dmalmStatoWorkitemFk03));
			s.setDmalmStrutturaOrgFk01(t
					.get(sottoprogramma.dmalmStrutturaOrgFk01));
			s.setDmalmTempoFk04(t.get(sottoprogramma.dmalmTempoFk04));
			s.setDmalmUserFk06(t.get(sottoprogramma.dmalmUserFk06));
			s.setDsAutoreSottoprogramma(t
					.get(sottoprogramma.dsAutoreSottoprogramma));
			s.setDtAnnullamento(t.get(sottoprogramma.dtAnnullamento));
			s.setDtCambioStatoSottoprogramma(t
					.get(sottoprogramma.dtCambioStatoSottoprogramma));
			s.setDtCaricamentoSottoprogramma(t
					.get(sottoprogramma.dtCaricamentoSottoprogramma));
			s.setDtCompletamento(t.get(sottoprogramma.dtCompletamento));
			s.setDtCreazioneSottoprogramma(t
					.get(sottoprogramma.dtCreazioneSottoprogramma));
			s.setDtModificaSottoprogramma(t
					.get(sottoprogramma.dtModificaSottoprogramma));
			s.setDtRisoluzioneSottoprogramma(t
					.get(sottoprogramma.dtRisoluzioneSottoprogramma));
			s.setDtScadenzaSottoprogramma(t
					.get(sottoprogramma.dtScadenzaSottoprogramma));
			s.setDtStoricizzazione(t.get(sottoprogramma.dtStoricizzazione));
			s.setIdAutoreSottoprogramma(t
					.get(sottoprogramma.idAutoreSottoprogramma));
			s.setIdRepository(t.get(sottoprogramma.idRepository));
			s.setMotivoRisoluzioneSottoprogr(t
					.get(sottoprogramma.motivoRisoluzioneSottoprogr));
			s.setNumeroLinea(t.get(sottoprogramma.numeroLinea));
			s.setNumeroTestata(t.get(sottoprogramma.numeroTestata));
			s.setRankStatoSottoprogramma(t
					.get(sottoprogramma.rankStatoSottoprogramma));
			s.setRankStatoSottoprogrammaMese(t
					.get(sottoprogramma.rankStatoSottoprogrammaMese));
			s.setStgPk(t.get(sottoprogramma.stgPk));
			s.setTitoloSottoprogramma(t
					.get(sottoprogramma.titoloSottoprogramma));
			s.setUri(t.get(sottoprogramma.uri));
			//DM_ALM-320
			s.setSeverity(t.get(sottoprogramma.severity));
			s.setPriority(t.get(sottoprogramma.priority));

			return s;

		} else
			return null;

	}

	public static boolean checkEsistenzaSottoProgramma(DmalmSottoprogramma s,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(sottoprogramma)
					.where(sottoprogramma.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(sottoprogramma.cdSottoprogramma.eq(s.getCdSottoprogramma()))
					.where(sottoprogramma.idRepository.eq(s.getIdRepository()))
					.list(sottoprogramma.all());

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
