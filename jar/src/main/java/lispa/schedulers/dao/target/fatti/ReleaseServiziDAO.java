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
import lispa.schedulers.bean.target.fatti.DmalmReleaseServizi;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseServizi;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

public class ReleaseServiziDAO {
	private static Logger logger = Logger.getLogger(ReleaseServiziDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmReleaseServizi releaseservizi = QDmalmReleaseServizi.dmalmReleaseServizi;

	public static List<DmalmReleaseServizi> getAllReleaseServizi(
			Timestamp dataEsecuzione) throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmReleaseServizi bean = null;
		List<DmalmReleaseServizi> releases = new ArrayList<DmalmReleaseServizi>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.release_ser));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_RELEASE_SERVIZI);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);
			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmReleaseServizi();

				bean.setCdRelServizi(rs.getString("CD_RELEASESER"));
				bean.setDescrizioneRelServizi(rs.getString("DESCRIPTION"));
				bean.setDmalmRelServiziPk(rs.getInt("DMALM_RELEASESER_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreRelServizi(rs
						.getString("NOME_AUTORE_RELEASESER"));
				bean.setDtCaricamentoRelServizi(dataEsecuzione);
				bean.setDtCreazioneRelServizi(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaRelServizi(rs
						.getTimestamp("DATA_MODIFICA_RELEASESER"));
				bean.setIdAutoreRelServizi(rs.getString("ID_AUTORE_RELEASESER"));
				bean.setDtRisoluzioneRelServizi(rs
						.getTimestamp("DATA_RISOLUZIONE_RELEASESER"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setDtScadenzaRelServizi(rs
						.getTimestamp("DATA_SCADENZA_RELEASESER"));
				bean.setMotivoRisoluzioneRelServizi(rs
						.getString("MOTIVO_RISOLUZIONE_RELEASESER"));
				bean.setTitoloRelServizi(rs.getString("TITOLO_RELEASESER"));
				bean.setMotivoSospensioneReleaseSer(rs
						.getString("MOTIVO_SOSPENSIONE"));
				bean.setPrevistoFermoServizioRel(rs.getString("PREV_FERMO"));
				bean.setRichiestaAnalisiImpattiRel(rs
						.getString("RICHIESTA_IMPATTI"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_RELEASESER_PK"));
				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));
				bean.setTagAlm(rs.getString("TAG_ALM"));
				bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
				releases.add(bean);
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

		return releases;
	}

	public static List<Tuple> getReleaseServizi(DmalmReleaseServizi release)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> releases = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			releases = query
					.from(releaseservizi)
					.where(releaseservizi.cdRelServizi.equalsIgnoreCase(release
							.getCdRelServizi()))
					.where(releaseservizi.idRepository.equalsIgnoreCase(release
							.getIdRepository()))
					.where(releaseservizi.rankStatoRelServizi.eq(new Double(1)))
					.list(releaseservizi.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return releases;

	}

	public static void insertReleaseServizi(DmalmReleaseServizi release)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, releaseservizi)
					.columns(releaseservizi.cdRelServizi,
							releaseservizi.descrizioneRelServizi,
							releaseservizi.dmalmRelServiziPk,
							releaseservizi.dmalmProjectFk02,
							releaseservizi.dmalmStatoWorkitemFk03,
							releaseservizi.dmalmStrutturaOrgFk01,
							releaseservizi.dmalmTempoFk04,
							releaseservizi.dsAutoreRelServizi,
							releaseservizi.dtCambioStatoRelServizi,
							releaseservizi.dtCaricamentoRelServizi,
							releaseservizi.dtCreazioneRelServizi,
							releaseservizi.dtModificaRelServizi,
							releaseservizi.dtRisoluzioneRelServizi,
							releaseservizi.dtScadenzaRelServizi,
							releaseservizi.dtStoricizzazione,
							releaseservizi.idAutoreRelServizi,
							releaseservizi.idRepository,
							releaseservizi.motivoRisoluzioneRelServizi,
							releaseservizi.rankStatoRelServizi,
							releaseservizi.titoloRelServizi,
							releaseservizi.stgPk,
							releaseservizi.motivoSospensioneReleaseSer,
							releaseservizi.previstoFermoServizioRel,
							releaseservizi.richiestaAnalisiImpattiRel,
							releaseservizi.dmalmUserFk06, releaseservizi.uri,
							releaseservizi.dtAnnullamento,
							releaseservizi.severity, releaseservizi.priority,
							releaseservizi.tsTagAlm,releaseservizi.tagAlm)
					.values(release.getCdRelServizi(),
							release.getDescrizioneRelServizi(),
							release.getDmalmRelServiziPk(),
							release.getDmalmProjectFk02(),
							release.getDmalmStatoWorkitemFk03(),
							release.getDmalmStrutturaOrgFk01(),
							release.getDmalmTempoFk04(),
							release.getDsAutoreRelServizi(),
							release.getDtCambioStatoRelServizi(),
							release.getDtCaricamentoRelServizi(),
							release.getDtCreazioneRelServizi(),
							release.getDtModificaRelServizi(),
							release.getDtRisoluzioneRelServizi(),
							release.getDtScadenzaRelServizi(),
							release.getDtModificaRelServizi(),
							release.getIdAutoreRelServizi(),
							release.getIdRepository(),
							release.getMotivoRisoluzioneRelServizi(),
							new Double(1), release.getTitoloRelServizi(),
							release.getStgPk(),
							release.getMotivoSospensioneReleaseSer(),
							release.getPrevistoFermoServizioRel(),
							release.getRichiestaAnalisiImpattiRel(),
							release.getDmalmUserFk06(), release.getUri(),
							release.getDtAnnullamento(),
							release.getSeverity(), release.getPriority(),
							release.getTsTagAlm(),release.getTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmReleaseServizi release, Double double1)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, releaseservizi)
					.where(releaseservizi.cdRelServizi.equalsIgnoreCase(release
							.getCdRelServizi()))
					.where(releaseservizi.idRepository.equalsIgnoreCase(release
							.getIdRepository()))
					.set(releaseservizi.rankStatoRelServizi, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertReleaseServiziUpdate(Timestamp dataEsecuzione,
			DmalmReleaseServizi release, boolean pkValue) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, releaseservizi)
					.columns(releaseservizi.cdRelServizi,
							releaseservizi.descrizioneRelServizi,
							releaseservizi.dmalmRelServiziPk,
							releaseservizi.dmalmProjectFk02,
							releaseservizi.dmalmStatoWorkitemFk03,
							releaseservizi.dmalmStrutturaOrgFk01,
							releaseservizi.dmalmTempoFk04,
							releaseservizi.dsAutoreRelServizi,
							releaseservizi.dtCambioStatoRelServizi,
							releaseservizi.dtCaricamentoRelServizi,
							releaseservizi.dtCreazioneRelServizi,
							releaseservizi.dtModificaRelServizi,
							releaseservizi.dtRisoluzioneRelServizi,
							releaseservizi.dtScadenzaRelServizi,
							releaseservizi.dtStoricizzazione,
							releaseservizi.idAutoreRelServizi,
							releaseservizi.idRepository,
							releaseservizi.motivoRisoluzioneRelServizi,
							releaseservizi.rankStatoRelServizi,
							releaseservizi.titoloRelServizi,
							releaseservizi.stgPk,
							releaseservizi.motivoSospensioneReleaseSer,
							releaseservizi.previstoFermoServizioRel,
							releaseservizi.richiestaAnalisiImpattiRel,
							releaseservizi.dmalmUserFk06, releaseservizi.uri,
							releaseservizi.dtAnnullamento,
							releaseservizi.changed, releaseservizi.annullato,
							releaseservizi.severity, releaseservizi.priority,
							releaseservizi.tsTagAlm,releaseservizi.tagAlm)
					.values(release.getCdRelServizi(),
							release.getDescrizioneRelServizi(),
							pkValue == true ? release.getDmalmRelServiziPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							release.getDmalmProjectFk02(),
							release.getDmalmStatoWorkitemFk03(),
							release.getDmalmStrutturaOrgFk01(),
							release.getDmalmTempoFk04(),
							release.getDsAutoreRelServizi(),
							release.getDtCambioStatoRelServizi(),
							release.getDtCaricamentoRelServizi(),
							release.getDtCreazioneRelServizi(),
							release.getDtModificaRelServizi(),
							release.getDtRisoluzioneRelServizi(),
							pkValue == true ? release.getDtScadenzaRelServizi()
									: release.getDtStoricizzazione(),
							release.getDtModificaRelServizi(),
							release.getIdAutoreRelServizi(),
							release.getIdRepository(),
							release.getMotivoRisoluzioneRelServizi(),
							pkValue == true ? new Short("1")  : release.getRankStatoRelServizi(), release.getTitoloRelServizi(),
							release.getStgPk(),
							release.getMotivoSospensioneReleaseSer(),
							release.getPrevistoFermoServizioRel(),
							release.getRichiestaAnalisiImpattiRel(),
							release.getDmalmUserFk06(), release.getUri(),
							release.getDtAnnullamento(), release.getChanged(),
							release.getAnnullato(),
							release.getSeverity(), release.getPriority(),
							release.getTsTagAlm(),release.getTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateReleaseServizi(DmalmReleaseServizi release)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, releaseservizi)
					.where(releaseservizi.cdRelServizi.equalsIgnoreCase(release
							.getCdRelServizi()))
					.where(releaseservizi.idRepository.equalsIgnoreCase(release
							.getIdRepository()))
					.where(releaseservizi.rankStatoRelServizi.eq(new Double(1)))

					.set(releaseservizi.cdRelServizi, release.getCdRelServizi())
					.set(releaseservizi.descrizioneRelServizi,
							release.getDescrizioneRelServizi())
					.set(releaseservizi.dmalmProjectFk02,
							release.getDmalmProjectFk02())
					.set(releaseservizi.dmalmStatoWorkitemFk03,
							release.getDmalmStatoWorkitemFk03())
					.set(releaseservizi.dmalmStrutturaOrgFk01,
							release.getDmalmStrutturaOrgFk01())
					.set(releaseservizi.dmalmTempoFk04,
							release.getDmalmTempoFk04())
					.set(releaseservizi.dsAutoreRelServizi,
							release.getDsAutoreRelServizi())
					.set(releaseservizi.dtCreazioneRelServizi,
							release.getDtCreazioneRelServizi())
					.set(releaseservizi.dtModificaRelServizi,
							release.getDtModificaRelServizi())
					.set(releaseservizi.dtRisoluzioneRelServizi,
							release.getDtRisoluzioneRelServizi())
					.set(releaseservizi.dtScadenzaRelServizi,
							release.getDtScadenzaRelServizi())
					.set(releaseservizi.dtStoricizzazione,
							release.getDtModificaRelServizi())
					.set(releaseservizi.idAutoreRelServizi,
							release.getIdAutoreRelServizi())
					.set(releaseservizi.idRepository, release.getIdRepository())
					.set(releaseservizi.motivoRisoluzioneRelServizi,
							release.getMotivoRisoluzioneRelServizi())
					.set(releaseservizi.rankStatoRelServizi, new Double(1))
					.set(releaseservizi.titoloRelServizi,
							release.getTitoloRelServizi())
					.set(releaseservizi.stgPk, release.getStgPk())
					.set(releaseservizi.uri, release.getUri())
					.set(releaseservizi.motivoSospensioneReleaseSer,
							release.getMotivoSospensioneReleaseSer())
					.set(releaseservizi.previstoFermoServizioRel,
							release.getPrevistoFermoServizioRel())
					.set(releaseservizi.richiestaAnalisiImpattiRel,
							release.getRichiestaAnalisiImpattiRel())
					.set(releaseservizi.dtAnnullamento,
							release.getDtAnnullamento())
					.set(releaseservizi.annullato, release.getAnnullato())
					.set(releaseservizi.severity,
							release.getSeverity())
					.set(releaseservizi.priority,
							release.getPriority())
					.set(releaseservizi.tagAlm, release.getTagAlm())
					.set(releaseservizi.tsTagAlm, release.getTsTagAlm())
					.execute();
			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmReleaseServizi getReleaseServizi(Integer pk)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(releaseservizi)
					.where(releaseservizi.dmalmRelServiziPk.eq(pk))
					.list(releaseservizi.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (list.size() > 0) {
			Tuple t = list.get(0);
			DmalmReleaseServizi r = new DmalmReleaseServizi();

			r.setAnnullato(t.get(releaseservizi.annullato));
			r.setCdRelServizi(t.get(releaseservizi.cdRelServizi));
			r.setChanged(t.get(releaseservizi.changed));
			r.setDescrizioneRelServizi(t
					.get(releaseservizi.descrizioneRelServizi));
			r.setDmalmProjectFk02(t.get(releaseservizi.dmalmProjectFk02));
			r.setDmalmRelServiziPk(t.get(releaseservizi.dmalmRelServiziPk));
			r.setDmalmStatoWorkitemFk03(t
					.get(releaseservizi.dmalmStatoWorkitemFk03));
			r.setDmalmStrutturaOrgFk01(t
					.get(releaseservizi.dmalmStrutturaOrgFk01));
			r.setDmalmTempoFk04(t.get(releaseservizi.dmalmTempoFk04));
			r.setDmalmUserFk06(t.get(releaseservizi.dmalmUserFk06));
			r.setDsAutoreRelServizi(t.get(releaseservizi.dsAutoreRelServizi));
			r.setDtAnnullamento(t.get(releaseservizi.dtAnnullamento));
			r.setDtCambioStatoRelServizi(t
					.get(releaseservizi.dtCambioStatoRelServizi));
			r.setDtCaricamentoRelServizi(t
					.get(releaseservizi.dtCaricamentoRelServizi));
			r.setDtCreazioneRelServizi(t
					.get(releaseservizi.dtCreazioneRelServizi));
			r.setDtModificaRelServizi(t
					.get(releaseservizi.dtModificaRelServizi));
			r.setDtRisoluzioneRelServizi(t
					.get(releaseservizi.dtRisoluzioneRelServizi));
			r.setDtScadenzaRelServizi(t
					.get(releaseservizi.dtScadenzaRelServizi));
			r.setDtStoricizzazione(t.get(releaseservizi.dtStoricizzazione));
			r.setIdAutoreRelServizi(t.get(releaseservizi.idAutoreRelServizi));
			r.setIdRepository(t.get(releaseservizi.idRepository));
			r.setMotivoRisoluzioneRelServizi(t
					.get(releaseservizi.motivoRisoluzioneRelServizi));
			r.setMotivoSospensioneReleaseSer(t
					.get(releaseservizi.motivoSospensioneReleaseSer));
			r.setPrevistoFermoServizioRel(t
					.get(releaseservizi.previstoFermoServizioRel));
			r.setRankStatoRelServizi(t.get(releaseservizi.rankStatoRelServizi));
			r.setRankStatoRelServiziMese(t
					.get(releaseservizi.rankStatoRelServiziMese));
			r.setRichiestaAnalisiImpattiRel(t
					.get(releaseservizi.richiestaAnalisiImpattiRel));
			r.setStgPk(t.get(releaseservizi.stgPk));
			r.setTitoloRelServizi(t.get(releaseservizi.titoloRelServizi));
			r.setUri(t.get(releaseservizi.uri));
			//DM_ALM-320
			r.setSeverity(t.get(releaseservizi.severity));
			r.setPriority(t.get(releaseservizi.priority));
			r.setTagAlm(t.get(releaseservizi.tagAlm));
			r.setTsTagAlm(t.get(releaseservizi.tsTagAlm));
			
			return r;

		} else
			return null;

	}

	public static boolean checkEsistenzaRelease(DmalmReleaseServizi r,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(releaseservizi)
					.where(releaseservizi.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(releaseservizi.cdRelServizi.eq(r.getCdRelServizi()))
					.where(releaseservizi.idRepository.eq(r.getIdRepository()))
					.list(releaseservizi.all());

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

	public static void updateProjectAndStatus(DmalmReleaseServizi release) {
		
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, releaseservizi)
					.where(releaseservizi.stgPk.eq(release.getStgPk()))
							
					.set(releaseservizi.dmalmProjectFk02,
							release.getDmalmProjectFk02())
					.set(releaseservizi.dmalmStatoWorkitemFk03,
							release.getDmalmStatoWorkitemFk03())
					.execute();
			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				try {
					cm.closeConnection(connection);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
	}

}
