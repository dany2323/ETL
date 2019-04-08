package lispa.schedulers.dao.target.fatti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmReleaseIt;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseIt;
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

public class ReleaseItDAO {

	private static Logger logger = Logger.getLogger(ReleaseItDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmReleaseIt releaseIt = QDmalmReleaseIt.dmalmReleaseIt;

	public static List<DmalmReleaseIt> getAllReleaseIt(Timestamp dataEsecuzione)
			throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmReleaseIt bean = null;
		List<DmalmReleaseIt> releases = new ArrayList<DmalmReleaseIt>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.release_it));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_RELEASE_IT);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmReleaseIt();

				bean.setCdReleaseIt(rs.getString("CD_RELEASEIT"));
				bean.setDescrizioneReleaseIt(rs.getString("DESCRIPTION"));
				bean.setDmalmReleaseItPk(rs.getInt("DMALM_RELEASEIT_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreReleaseIt(rs.getString("NOME_AUTORE_RELEASEIT"));
				bean.setDtCaricamentoReleaseIt(dataEsecuzione);
				bean.setDtCreazioneReleaseIt(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaReleaseIt(rs
						.getTimestamp("DATA_MODIFICA_RELEASEIT"));
				bean.setIdAutoreReleaseIt(rs.getString("ID_AUTORE_RELEASEIT"));
				bean.setDtRisoluzioneReleaseIt(rs
						.getTimestamp("DATA_RISOLUZIONE_RELIT"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setDtScadenzaReleaseIt(rs
						.getTimestamp("DATA_SCADENZA_RELEASEIT"));
				bean.setMotivoRisoluzioneReleaseIt(rs
						.getString("MOTIVO_RISOLUZIONE_RELIT"));
				bean.setDurataEffRelease(rs.getInt("DURATA_EFF_RELEASE_IT")
						- rs.getInt("GIORNI_FESTIVI"));
				bean.setTitoloReleaseIt(rs.getString("TITOLO_RELEASEIT"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_RELEASEIT_PK"));
				bean.setDtDisponibilitaEffRelease(rs
						.getTimestamp("DATA_DISPONIBILITA_EFF"));
				bean.setDtFineRelease(rs.getTimestamp("DATA_FINE_IT"));
				bean.setDtInizioRelease(rs.getTimestamp("DATA_INIZIO_IT"));
				bean.setDtRilascioRelease(rs
						.getTimestamp("DATA_RILASCIO_AD_ESERCIZIO"));
				bean.setPriority(rs.getString("PRIORITY"));
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setTypeRelease(rs.getString("TYPE_RELEASE"));
				bean.setMotivoSospensione(rs.getString("MOTIVO_SOSPENSIONE"));
				bean.setCounterQf(rs.getInt("COUNTER_QF"));
				bean.setGiorniQf(rs.getInt("GIORNI_QF"));
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

	public static List<Tuple> getReleaseIt(DmalmReleaseIt release)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> releases = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			releases = query
					.from(releaseIt)
					.where(releaseIt.cdReleaseIt.equalsIgnoreCase(release
							.getCdReleaseIt()))
					.where(releaseIt.idRepository.equalsIgnoreCase(release
							.getIdRepository()))
					.where(releaseIt.rankStatoReleaseIt.eq(new Double(1)))
					.list(releaseIt.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return releases;

	}

	public static void insertReleaseIt(DmalmReleaseIt release)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, releaseIt)
					.columns(releaseIt.cdReleaseIt,
							releaseIt.descrizioneReleaseIt,
							releaseIt.dmalmReleaseItPk,
							releaseIt.dmalmProjectFk02,
							releaseIt.dmalmStatoWorkitemFk03,
							releaseIt.dmalmStrutturaOrgFk01,
							releaseIt.dmalmTempoFk04,
							releaseIt.dsAutoreReleaseIt,
							releaseIt.dtCambioStatoReleaseIt,
							releaseIt.dtCaricamentoReleaseIt,
							releaseIt.dtCreazioneReleaseIt,
							releaseIt.dtModificaReleaseIt,
							releaseIt.dtRisoluzioneReleaseIt,
							releaseIt.dtScadenzaReleaseIt,
							releaseIt.dtStoricizzazione,
							releaseIt.idAutoreReleaseIt,
							releaseIt.idRepository,
							releaseIt.motivoRisoluzioneReleaseIt,
							releaseIt.rankStatoReleaseIt,
							releaseIt.durataEffRelease,
							releaseIt.titoloReleaseIt, releaseIt.stgPk,
							releaseIt.dtFineRelease,
							releaseIt.dtDisponibilitaEffRelease,
							releaseIt.dtRilascioRelease,
							releaseIt.dtInizioRelease, releaseIt.dmalmUserFk06,
							releaseIt.uri, 
							releaseIt.dtAnnullamento,
							releaseIt.severity,
							releaseIt.priority,
							releaseIt.typeRelease,
							releaseIt.motivoSospensione,
							releaseIt.counterQf,
							releaseIt.giorniQf,
							releaseIt.tsTagAlm,releaseIt.tagAlm)
					.values(release.getCdReleaseIt(),
							release.getDescrizioneReleaseIt(),
							release.getDmalmReleaseItPk(),
							release.getDmalmProjectFk02(),
							release.getDmalmStatoWorkitemFk03(),
							release.getDmalmStrutturaOrgFk01(),
							release.getDmalmTempoFk04(),
							release.getDsAutoreReleaseIt(),
							release.getDtCambioStatoReleaseIt(),
							release.getDtCaricamentoReleaseIt(),
							release.getDtCreazioneReleaseIt(),
							release.getDtModificaReleaseIt(),
							release.getDtRisoluzioneReleaseIt(),
							release.getDtScadenzaReleaseIt(),
							release.getDtModificaReleaseIt(),
							release.getIdAutoreReleaseIt(),
							release.getIdRepository(),
							release.getMotivoRisoluzioneReleaseIt(),
							new Double(1), release.getDurataEffRelease(),
							release.getTitoloReleaseIt(), release.getStgPk(),
							release.getDtFineRelease(),
							release.getDtDisponibilitaEffRelease(),
							release.getDtRilascioRelease(),
							release.getDtInizioRelease(),
							release.getDmalmUserFk06(), release.getUri(),
							release.getDtAnnullamento(),
							release.getSeverity(),
							release.getPriority(),
							release.getTypeRelease(),
							release.getMotivoSospensione(),
							release.getCounterQf(),
							release.getGiorniQf(),
							release.getTsTagAlm(),release.getTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmReleaseIt release, Double double1)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, releaseIt)
					.where(releaseIt.cdReleaseIt.equalsIgnoreCase(release
							.getCdReleaseIt()))
					.where(releaseIt.idRepository.equalsIgnoreCase(release
							.getIdRepository()))
					.set(releaseIt.rankStatoReleaseIt, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertReleaseItUpdate(Timestamp dataEsecuzione,
			DmalmReleaseIt release, boolean pkValue) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, releaseIt)
					.columns(releaseIt.cdReleaseIt,
							releaseIt.descrizioneReleaseIt,
							releaseIt.dmalmReleaseItPk,
							releaseIt.dmalmProjectFk02,
							releaseIt.dmalmStatoWorkitemFk03,
							releaseIt.dmalmStrutturaOrgFk01,
							releaseIt.dmalmTempoFk04,
							releaseIt.dsAutoreReleaseIt,
							releaseIt.dtCambioStatoReleaseIt,
							releaseIt.dtCaricamentoReleaseIt,
							releaseIt.dtCreazioneReleaseIt,
							releaseIt.dtModificaReleaseIt,
							releaseIt.dtRisoluzioneReleaseIt,
							releaseIt.dtScadenzaReleaseIt,
							releaseIt.dtStoricizzazione,
							releaseIt.idAutoreReleaseIt,
							releaseIt.idRepository,
							releaseIt.motivoRisoluzioneReleaseIt,
							releaseIt.rankStatoReleaseIt,
							releaseIt.durataEffRelease,
							releaseIt.titoloReleaseIt, releaseIt.stgPk,
							releaseIt.dtFineRelease,
							releaseIt.dtDisponibilitaEffRelease,
							releaseIt.dtRilascioRelease,
							releaseIt.dtInizioRelease, releaseIt.dmalmUserFk06,
							releaseIt.uri, 
							releaseIt.dtAnnullamento, 
							releaseIt.changed, 
							releaseIt.annullato,
							releaseIt.severity,
							releaseIt.priority,
							releaseIt.typeRelease,
							releaseIt.motivoSospensione,
							releaseIt.counterQf,
							releaseIt.giorniQf,
							releaseIt.tsTagAlm,releaseIt.tagAlm)
					.values(release.getCdReleaseIt(),
							release.getDescrizioneReleaseIt(),
							pkValue == true ? release.getDmalmReleaseItPk() : StringTemplate
									.create("HISTORY_WORKITEM_SEQ.nextval"),
							release.getDmalmProjectFk02(),
							release.getDmalmStatoWorkitemFk03(),
							release.getDmalmStrutturaOrgFk01(),
							release.getDmalmTempoFk04(),
							release.getDsAutoreReleaseIt(),
							release.getDtCambioStatoReleaseIt(),
							release.getDtCaricamentoReleaseIt(),
							release.getDtCreazioneReleaseIt(),
							release.getDtModificaReleaseIt(),
							release.getDtRisoluzioneReleaseIt(),
							release.getDtScadenzaReleaseIt(),
							pkValue == true ? release.getDtModificaReleaseIt() : release.getDtStoricizzazione(),
							release.getIdAutoreReleaseIt(),
							release.getIdRepository(),
							release.getMotivoRisoluzioneReleaseIt(),
							pkValue == true ? new Short("1")  : release.getRankStatoReleaseIt(),
							release.getDurataEffRelease(),
							release.getTitoloReleaseIt(), release.getStgPk(),
							release.getDtFineRelease(),
							release.getDtDisponibilitaEffRelease(),
							release.getDtRilascioRelease(),
							release.getDtInizioRelease(),
							release.getDmalmUserFk06(), release.getUri(),
							release.getDtAnnullamento(),
							release.getChanged(),
							release.getAnnullato(),
							release.getSeverity(),
							release.getPriority(),
							release.getTypeRelease(),
							release.getMotivoSospensione(),
							release.getCounterQf(),
							release.getGiorniQf(),
							release.getTsTagAlm(),release.getTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateReleaseIt(DmalmReleaseIt release)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, releaseIt)
					.where(releaseIt.cdReleaseIt.equalsIgnoreCase(release
							.getCdReleaseIt()))
					.where(releaseIt.idRepository.equalsIgnoreCase(release
							.getIdRepository()))
					.where(releaseIt.rankStatoReleaseIt.eq(new Double(1)))
					.set(releaseIt.cdReleaseIt, release.getCdReleaseIt())
					.set(releaseIt.descrizioneReleaseIt,
							release.getDescrizioneReleaseIt())
					.set(releaseIt.dmalmProjectFk02,
							release.getDmalmProjectFk02())
					.set(releaseIt.dmalmStatoWorkitemFk03,
							release.getDmalmStatoWorkitemFk03())
					.set(releaseIt.dmalmStrutturaOrgFk01,
							release.getDmalmStrutturaOrgFk01())
					.set(releaseIt.dmalmTempoFk04, release.getDmalmTempoFk04())
					.set(releaseIt.dsAutoreReleaseIt,
							release.getDsAutoreReleaseIt())
					.set(releaseIt.dtCreazioneReleaseIt,
							release.getDtCreazioneReleaseIt())
					.set(releaseIt.dtModificaReleaseIt,
							release.getDtModificaReleaseIt())
					.set(releaseIt.dtRisoluzioneReleaseIt,
							release.getDtRisoluzioneReleaseIt())
					.set(releaseIt.dtScadenzaReleaseIt,
							release.getDtScadenzaReleaseIt())
					.set(releaseIt.dtStoricizzazione,
							release.getDtModificaReleaseIt())
					.set(releaseIt.idAutoreReleaseIt,
							release.getIdAutoreReleaseIt())
					.set(releaseIt.idRepository, release.getIdRepository())
					.set(releaseIt.motivoRisoluzioneReleaseIt,
							release.getMotivoRisoluzioneReleaseIt())
					.set(releaseIt.rankStatoReleaseIt, new Double(1))
					.set(releaseIt.durataEffRelease,
							release.getDurataEffRelease())
					.set(releaseIt.titoloReleaseIt,
							release.getTitoloReleaseIt())
					.set(releaseIt.stgPk, release.getStgPk())
					.set(releaseIt.uri, release.getUri())
					.set(releaseIt.dtFineRelease, release.getDtFineRelease())
					.set(releaseIt.dtDisponibilitaEffRelease,
							release.getDtDisponibilitaEffRelease())
					.set(releaseIt.dtRilascioRelease,
							release.getDtRilascioRelease())
					.set(releaseIt.dtInizioRelease,
							release.getDtInizioRelease())
					.set(releaseIt.dtAnnullamento, release.getDtAnnullamento())
					.set(releaseIt.annullato, release.getAnnullato())
					.set(releaseIt.severity, release.getSeverity())
					.set(releaseIt.priority,release.getPriority())
					.set(releaseIt.typeRelease, release.getTypeRelease())
					.set(releaseIt.motivoSospensione, release.getMotivoSospensione())
					.set(releaseIt.counterQf, release.getCounterQf())
					.set(releaseIt.giorniQf, release.getGiorniQf())
					.set(releaseIt.tagAlm, release.getTagAlm())
					.set(releaseIt.tsTagAlm, release.getTsTagAlm())
					.execute();

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmReleaseIt getReleaseIt(Integer pk) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> releases = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			releases = query
					.from(releaseIt)
					.where(releaseIt.dmalmReleaseItPk.eq(pk))
					.list(releaseIt.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
		
		if(releases.size()>0){
			Tuple t = releases.get(0);
			DmalmReleaseIt r = new DmalmReleaseIt();
			
			r.setAnnullato(t.get(releaseIt.annullato));
			r.setCdReleaseIt(t.get(releaseIt.cdReleaseIt));
			r.setChanged(t.get(releaseIt.changed));
			r.setDescrizioneReleaseIt(t.get(releaseIt.descrizioneReleaseIt));
			r.setDmalmProjectFk02(t.get(releaseIt.dmalmProjectFk02));
			r.setDmalmReleaseItPk(t.get(releaseIt.dmalmReleaseItPk));
			r.setDmalmStatoWorkitemFk03(t.get(releaseIt.dmalmStatoWorkitemFk03));
			r.setDmalmStrutturaOrgFk01(t.get(releaseIt.dmalmStrutturaOrgFk01));
			r.setDmalmTempoFk04(t.get(releaseIt.dmalmTempoFk04));
			r.setDmalmUserFk06(t.get(releaseIt.dmalmUserFk06));
			r.setDsAutoreReleaseIt(t.get(releaseIt.dsAutoreReleaseIt));
			r.setDtAnnullamento(t.get(releaseIt.dtAnnullamento));
			r.setDtCambioStatoReleaseIt(t.get(releaseIt.dtCambioStatoReleaseIt));
			r.setDtCaricamentoReleaseIt(t.get(releaseIt.dtCaricamentoReleaseIt));
			r.setDtCreazioneReleaseIt(t.get(releaseIt.dtCreazioneReleaseIt));
			r.setDtDisponibilitaEffRelease(t.get(releaseIt.dtDisponibilitaEffRelease));
			r.setDtFineRelease(t.get(releaseIt.dtFineRelease));
			r.setDtInizioRelease(t.get(releaseIt.dtInizioRelease));
			r.setDtModificaReleaseIt(t.get(releaseIt.dtModificaReleaseIt));
			r.setDtRilascioRelease(t.get(releaseIt.dtRilascioRelease));
			r.setDtRisoluzioneReleaseIt(t.get(releaseIt.dtRisoluzioneReleaseIt));
			r.setDtScadenzaReleaseIt(t.get(releaseIt.dtScadenzaReleaseIt));
			r.setDtStoricizzazione(t.get(releaseIt.dtStoricizzazione));
			r.setDurataEffRelease(t.get(releaseIt.durataEffRelease));
			r.setIdAutoreReleaseIt(t.get(releaseIt.idAutoreReleaseIt));
			r.setIdRepository(t.get(releaseIt.idRepository));
			r.setMotivoRisoluzioneReleaseIt(t.get(releaseIt.motivoRisoluzioneReleaseIt));
			r.setRankStatoReleaseIt(t.get(releaseIt.rankStatoReleaseIt));
			r.setRankStatoReleaseItMese(t.get(releaseIt.rankStatoReleaseItMese));
			r.setStgPk(t.get(releaseIt.stgPk));
			r.setTitoloReleaseIt(t.get(releaseIt.titoloReleaseIt));
			r.setUri(t.get(releaseIt.uri));
			r.setTypeRelease(t.get(releaseIt.typeRelease));
			r.setMotivoSospensione(t.get(releaseIt.motivoSospensione));
			r.setCounterQf(t.get(releaseIt.counterQf));
			r.setGiorniQf(t.get(releaseIt.giorniQf));
			r.setTagAlm(t.get(releaseIt.tagAlm));
			r.setTsTagAlm(t.get(releaseIt.tsTagAlm));
			return r;
			
		}else return null;
		
	}

	public static boolean checkEsistenzaRelease(DmalmReleaseIt r, DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(releaseIt)
					.where(releaseIt.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(releaseIt.cdReleaseIt.eq(r.getCdReleaseIt()))
					.where(releaseIt.idRepository.eq(r.getIdRepository()))
					.list(releaseIt.all());

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
