package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_PROGETTO_SVILUPPO_SVILUPPO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmProgettoSviluppoSvil;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoSviluppoSvil;
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

public class ProgettoSviluppoSviluppoDAO {

	private static Logger logger = Logger.getLogger(AnomaliaProdottoDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProgettoSviluppoSvil progetto = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;

	public static List<DmalmProgettoSviluppoSvil> getAllProgettoSviluppoSviluppo(
			Timestamp dataEsecuzione) throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmProgettoSviluppoSvil bean = null;
		List<DmalmProgettoSviluppoSvil> progettiSviluppo = new ArrayList<DmalmProgettoSviluppoSvil>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.srqs));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					SQL_PROGETTO_SVILUPPO_SVILUPPO);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmProgettoSviluppoSvil();

				bean.setCdProgSvilS(rs.getString("CD_PROG_SVIL_S"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setDataChiusuraProgSvilS(rs
						.getTimestamp("DATA_CHIUSURA_PROG_SVIL_S"));
				bean.setDataDisponibilitaEffettiva(rs
						.getTimestamp("DATA_DISPONIBILITA_EFFETTIVA"));
				bean.setDataDisponibilitaPianificata(rs
						.getTimestamp("DATA_DISPONIBILITA_PIANIFICATA"));
				bean.setDataInizio(rs.getTimestamp("DATA_INIZIO_PIANIFICATO"));
				bean.setDataInizioEff(rs.getTimestamp("DATA_INIZIO_EFFETTIVO"));
				bean.setDescrizioneProgSvilS(rs
						.getString("DESCRIZIONE_PROG_SVIL_S"));
				bean.setDmalmProgSvilSPk(rs.getInt("DMALM_PROG_SVIL_S_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreProgSvilS(rs
						.getString("NOME_AUTORE_PROG_SVIL_S"));
				bean.setDtCreazioneProgSvilS(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaProgSvilS(rs
						.getTimestamp("DATA_MODIFICA_PROG_SVIL_S"));
				bean.setDtRisoluzioneProgSvilS(rs
						.getTimestamp("DATA_RISOLUZIONE_PROG_SVIL_S"));
				bean.setDtScadenzaProgSvilS(rs
						.getTimestamp("DATA_SCADENZA_PROG_SVIL_S"));
				bean.setDurataEffettivaProgSvilS((short) (rs
						.getInt("TEMPO_TOTALE_RISOLUZIONE") - rs
						.getInt("GIORNI_FESTIVI")));
				bean.setFornitura(rs.getString("CLASSE_DI_FORNITURA"));
				bean.setIdAutoreProgSvilS(rs.getString("ID_AUTORE_PROG_SVIL_S"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzioneProgSvilS(rs
						.getString("MOTIVO_RISOL_PROG_SVIL_S"));
				bean.setNumeroLinea(StringUtils.getLinea(rs
						.getString("CODICE_INTERVENTO")));
				bean.setNumeroTestata(StringUtils.getTestata(rs
						.getString("CODICE_INTERVENTO")));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_PROG_SVIL_S_PK"));
				bean.setTitoloProgSvilS(rs.getString("TITOLO_PROG_SVIL_S"));
				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));
				bean.setTagAlm(rs.getString("TAG_ALM"));
				bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
				
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

	public static List<Tuple> getProgettoSviluppoSviluppo(
			DmalmProgettoSviluppoSvil progettoSviluppo) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> progetti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmProgettoSviluppoSvil progetto = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;

			progetti = query
					.from(progetto)
					.where(progetto.cdProgSvilS
							.equalsIgnoreCase(progettoSviluppo.getCdProgSvilS()))
					.where(progetto.idRepository
							.equalsIgnoreCase(progettoSviluppo
									.getIdRepository()))
					.where(progetto.rankStatoProgSvilS.eq(new Double(1)))
					.list(progetto.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return progetti;

	}

	public static void insertProgettoSviluppoSviluppo(
			DmalmProgettoSviluppoSvil progettoSviluppo) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, progetto)
					.columns(progetto.cdProgSvilS, progetto.codice,
							progetto.dataChiusuraProgSvilS,
							progetto.dataDisponibilitaEffettiva,
							progetto.dataDisponibilitaPianificata,
							progetto.dataInizio, progetto.dataInizioEff,
							progetto.descrizioneProgSvilS,
							progetto.dmalmAreaTematicaFk05,
							progetto.dmalmProgSvilSPk,
							progetto.dmalmProjectFk02,
							progetto.dmalmStatoWorkitemFk03,
							progetto.dmalmStrutturaOrgFk01,
							progetto.dmalmTempoFk04,
							progetto.dsAutoreProgSvilS,
							progetto.dtCambioStatoProgSvilS,
							progetto.dtCaricamentoProgSvilS,
							progetto.dtCreazioneProgSvilS,
							progetto.dtModificaProgSvilS,
							progetto.dtRisoluzioneProgSvilS,
							progetto.dtScadenzaProgSvilS,
							progetto.durataEffettivaProgSvilS,
							progetto.fornitura, progetto.idAutoreProgSvilS,
							progetto.idRepository,
							progetto.motivoRisoluzioneProgSvilS,
							progetto.numeroLinea, progetto.numeroTestata,
							progetto.titoloProgSvilS,
							progetto.rankStatoProgSvilS,
							progetto.dtStoricizzazione, progetto.stgPk,
							progetto.dmalmUserFk06, progetto.uri,
							progetto.dmalmProgettoSferaFk,
							progetto.dtAnnullamento,
							progetto.severity,
							progetto.priority,
							progetto.tsTagAlm,progetto.tagAlm)
					.values(progettoSviluppo.getCdProgSvilS(),
							progettoSviluppo.getCodice(),
							progettoSviluppo.getDataChiusuraProgSvilS(),
							progettoSviluppo.getDataDisponibilitaEffettiva(),
							progettoSviluppo.getDataDisponibilitaPianificata(),
							progettoSviluppo.getDataInizio(),
							progettoSviluppo.getDataInizioEff(),
							progettoSviluppo.getDescrizioneProgSvilS(),
							progettoSviluppo.getDmalmAreaTematicaFk05(),
							progettoSviluppo.getDmalmProgSvilSPk(),
							progettoSviluppo.getDmalmProjectFk02(),
							progettoSviluppo.getDmalmStatoWorkitemFk03(),
							progettoSviluppo.getDmalmStrutturaOrgFk01(),
							progettoSviluppo.getDmalmTempoFk04(),
							progettoSviluppo.getDsAutoreProgSvilS(),
							progettoSviluppo.getDtCambioStatoProgSvilS(),
							progettoSviluppo.getDtCaricamentoProgSvilS(),
							progettoSviluppo.getDtCreazioneProgSvilS(),
							progettoSviluppo.getDtModificaProgSvilS(),
							progettoSviluppo.getDtRisoluzioneProgSvilS(),
							progettoSviluppo.getDtScadenzaProgSvilS(),
							progettoSviluppo.getDurataEffettivaProgSvilS(),
							progettoSviluppo.getFornitura(),
							progettoSviluppo.getIdAutoreProgSvilS(),
							progettoSviluppo.getIdRepository(),
							progettoSviluppo.getMotivoRisoluzioneProgSvilS(),
							progettoSviluppo.getNumeroLinea(),
							progettoSviluppo.getNumeroTestata(),
							progettoSviluppo.getTitoloProgSvilS(),
							new Double(1),
							progettoSviluppo.getDtModificaProgSvilS(),
							progettoSviluppo.getStgPk(),
							progettoSviluppo.getDmalmUserFk06(),
							progettoSviluppo.getUri(),
							progettoSviluppo.getDmalmProgettoSferaFk(),
							progettoSviluppo.getDtAnnullamento(),
							//DM_ALM-320
							progettoSviluppo.getSeverity(),
							progettoSviluppo.getPriority(),
							progettoSviluppo.getTsTagAlm(),progettoSviluppo.getTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmProgettoSviluppoSvil progettoSviluppo,
			Double double1) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progetto)
					.where(progetto.cdProgSvilS
							.equalsIgnoreCase(progettoSviluppo.getCdProgSvilS()))
					.where(progetto.idRepository
							.equalsIgnoreCase(progettoSviluppo
									.getIdRepository()))
					.set(progetto.rankStatoProgSvilS, double1).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertProgettoSviluppoSvilUpdate(
			Timestamp dataEsecuzione,
			DmalmProgettoSviluppoSvil progettoSviluppo, boolean pkValue)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, progetto)
					.columns(progetto.cdProgSvilS, progetto.codice,
							progetto.dataChiusuraProgSvilS,
							progetto.dataDisponibilitaEffettiva,
							progetto.dataDisponibilitaPianificata,
							progetto.dataInizio, progetto.dataInizioEff,
							progetto.descrizioneProgSvilS,
							progetto.dmalmAreaTematicaFk05,
							progetto.dmalmProgSvilSPk,
							progetto.dmalmProjectFk02,
							progetto.dmalmStatoWorkitemFk03,
							progetto.dmalmStrutturaOrgFk01,
							progetto.dmalmTempoFk04,
							progetto.dsAutoreProgSvilS,
							progetto.dtCambioStatoProgSvilS,
							progetto.dtCaricamentoProgSvilS,
							progetto.dtCreazioneProgSvilS,
							progetto.dtModificaProgSvilS,
							progetto.dtRisoluzioneProgSvilS,
							progetto.dtScadenzaProgSvilS,
							progetto.durataEffettivaProgSvilS,
							progetto.fornitura, progetto.idAutoreProgSvilS,
							progetto.idRepository,
							progetto.motivoRisoluzioneProgSvilS,
							progetto.numeroLinea, progetto.numeroTestata,
							progetto.titoloProgSvilS,
							progetto.rankStatoProgSvilS,
							progetto.dtStoricizzazione, progetto.stgPk,
							progetto.dmalmUserFk06, progetto.uri,
							progetto.dmalmProgettoSferaFk,
							progetto.dtAnnullamento, progetto.changed,
							progetto.annullato,
							progetto.severity,
							progetto.priority,
							progetto.tsTagAlm,progetto.tagAlm)
					.values(progettoSviluppo.getCdProgSvilS(),
							progettoSviluppo.getCodice(),
							progettoSviluppo.getDataChiusuraProgSvilS(),
							progettoSviluppo.getDataDisponibilitaEffettiva(),
							progettoSviluppo.getDataDisponibilitaPianificata(),
							progettoSviluppo.getDataInizio(),
							progettoSviluppo.getDataInizioEff(),
							progettoSviluppo.getDescrizioneProgSvilS(),
							progettoSviluppo.getDmalmAreaTematicaFk05(),
							pkValue == true ? progettoSviluppo
									.getDmalmProgSvilSPk() : StringTemplate
									.create("HISTORY_WORKITEM_SEQ.nextval"),
							progettoSviluppo.getDmalmProjectFk02(),
							progettoSviluppo.getDmalmStatoWorkitemFk03(),
							progettoSviluppo.getDmalmStrutturaOrgFk01(),
							progettoSviluppo.getDmalmTempoFk04(),
							progettoSviluppo.getDsAutoreProgSvilS(),
							progettoSviluppo.getDtCambioStatoProgSvilS(),
							progettoSviluppo.getDtCaricamentoProgSvilS(),
							progettoSviluppo.getDtCreazioneProgSvilS(),
							progettoSviluppo.getDtModificaProgSvilS(),
							progettoSviluppo.getDtRisoluzioneProgSvilS(),
							progettoSviluppo.getDtScadenzaProgSvilS(),
							progettoSviluppo.getDurataEffettivaProgSvilS(),
							progettoSviluppo.getFornitura(),
							progettoSviluppo.getIdAutoreProgSvilS(),
							progettoSviluppo.getIdRepository(),
							progettoSviluppo.getMotivoRisoluzioneProgSvilS(),
							progettoSviluppo.getNumeroLinea(),
							progettoSviluppo.getNumeroTestata(),
							progettoSviluppo.getTitoloProgSvilS(),
							pkValue == true ? new Short("1")  : progettoSviluppo.getRankStatoProgSvilS(),
							pkValue == true ? progettoSviluppo
									.getDtModificaProgSvilS()
									: progettoSviluppo.getDtStoricizzazione(),
							progettoSviluppo.getStgPk(),
							progettoSviluppo.getDmalmUserFk06(),
							progettoSviluppo.getUri(),
							progettoSviluppo.getDmalmProgettoSferaFk(),
							progettoSviluppo.getDtAnnullamento(),
							progettoSviluppo.getChanged(),
							progettoSviluppo.getAnnullato(),
							//DM_ALM-320
							progettoSviluppo.getSeverity(),
							progettoSviluppo.getPriority(),
							progettoSviluppo.getTsTagAlm(),progettoSviluppo.getTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateProgettoSviluppoSvil(
			DmalmProgettoSviluppoSvil progettoSviluppo) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progetto)

					.where(progetto.cdProgSvilS
							.equalsIgnoreCase(progettoSviluppo.getCdProgSvilS()))
					.where(progetto.idRepository
							.equalsIgnoreCase(progettoSviluppo
									.getIdRepository()))
					.where(progetto.rankStatoProgSvilS.eq(new Double(1)))

					.set(progetto.codice, progettoSviluppo.getCodice())
					.set(progetto.dataChiusuraProgSvilS,
							progettoSviluppo.getDataChiusuraProgSvilS())
					.set(progetto.dataDisponibilitaEffettiva,
							progettoSviluppo.getDataDisponibilitaEffettiva())
					.set(progetto.dataDisponibilitaPianificata,
							progettoSviluppo.getDataDisponibilitaPianificata())
					.set(progetto.dataInizio, progettoSviluppo.getDataInizio())
					.set(progetto.dataInizioEff,
							progettoSviluppo.getDataInizioEff())
					.set(progetto.descrizioneProgSvilS,
							progettoSviluppo.getDescrizioneProgSvilS())
					.set(progetto.dmalmAreaTematicaFk05,
							progettoSviluppo.getDmalmAreaTematicaFk05())
					.set(progetto.dmalmProjectFk02,
							progettoSviluppo.getDmalmProjectFk02())
					.set(progetto.dmalmStatoWorkitemFk03,
							progettoSviluppo.getDmalmStatoWorkitemFk03())
					.set(progetto.dmalmStrutturaOrgFk01,
							progettoSviluppo.getDmalmStrutturaOrgFk01())
					.set(progetto.dmalmTempoFk04,
							progettoSviluppo.getDmalmTempoFk04())
					.set(progetto.dsAutoreProgSvilS,
							progettoSviluppo.getDsAutoreProgSvilS())
					.set(progetto.dtCaricamentoProgSvilS,
							progettoSviluppo.getDtCaricamentoProgSvilS())
					.set(progetto.dtCreazioneProgSvilS,
							progettoSviluppo.getDtCreazioneProgSvilS())
					.set(progetto.dtModificaProgSvilS,
							progettoSviluppo.getDtModificaProgSvilS())
					.set(progetto.dtRisoluzioneProgSvilS,
							progettoSviluppo.getDtRisoluzioneProgSvilS())
					.set(progetto.dtScadenzaProgSvilS,
							progettoSviluppo.getDtScadenzaProgSvilS())
					.set(progetto.durataEffettivaProgSvilS,
							progettoSviluppo.getDurataEffettivaProgSvilS())
					.set(progetto.fornitura, progettoSviluppo.getFornitura())
					.set(progetto.idAutoreProgSvilS,
							progettoSviluppo.getIdAutoreProgSvilS())
					.set(progetto.idRepository,
							progettoSviluppo.getIdRepository())
					.set(progetto.motivoRisoluzioneProgSvilS,
							progettoSviluppo.getMotivoRisoluzioneProgSvilS())
					.set(progetto.numeroLinea,
							progettoSviluppo.getNumeroLinea())
					.set(progetto.numeroTestata,
							progettoSviluppo.getNumeroTestata())
					.set(progetto.titoloProgSvilS,
							progettoSviluppo.getTitoloProgSvilS())
					.set(progetto.stgPk, progettoSviluppo.getStgPk())
					.set(progetto.uri, progettoSviluppo.getUri())
					.set(progetto.dtAnnullamento,
							progettoSviluppo.getDtAnnullamento())
					.set(progetto.annullato, progettoSviluppo.getAnnullato())
					//DM_ALM-320
					.set(progetto.severity, progettoSviluppo.getSeverity())
					.set(progetto.priority, progettoSviluppo.getPriority())
					.set(progetto.tagAlm, progettoSviluppo.getTagAlm())
					.set(progetto.tsTagAlm, progettoSviluppo.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmProgettoSviluppoSvil getProgettoSviluppoSviluppo(
			Integer pk) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			QDmalmProgettoSviluppoSvil progetto = QDmalmProgettoSviluppoSvil.dmalmProgettoSviluppoSvil;

			list = query.from(progetto).where(progetto.dmalmProgSvilSPk.eq(pk))
					.list(progetto.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (list.size() > 0) {
			Tuple t = list.get(0);

			DmalmProgettoSviluppoSvil p = new DmalmProgettoSviluppoSvil();

			p.setAnnullato(t.get(progetto.annullato));
			p.setCdProgSvilS(t.get(progetto.cdProgSvilS));
			p.setChanged(t.get(progetto.changed));
			p.setCodice(t.get(progetto.codice));
			p.setDataChiusuraProgSvilS(t.get(progetto.dataChiusuraProgSvilS));
			p.setDataDisponibilitaEffettiva(t
					.get(progetto.dataDisponibilitaEffettiva));
			p.setDataDisponibilitaPianificata(t
					.get(progetto.dataDisponibilitaPianificata));
			p.setDataInizio(t.get(progetto.dataInizio));
			p.setDataInizioEff(t.get(progetto.dataInizioEff));
			p.setDescrizioneProgSvilS(t.get(progetto.descrizioneProgSvilS));
			p.setDmalmAreaTematicaFk05(t.get(progetto.dmalmAreaTematicaFk05));
			p.setDmalmProgettoSferaFk(t.get(progetto.dmalmProgettoSferaFk));
			p.setDmalmProgSvilSPk(t.get(progetto.dmalmProgSvilSPk));
			p.setDmalmProjectFk02(t.get(progetto.dmalmProjectFk02));
			p.setDmalmStatoWorkitemFk03(t.get(progetto.dmalmStatoWorkitemFk03));
			p.setDmalmStrutturaOrgFk01(t.get(progetto.dmalmStrutturaOrgFk01));
			p.setDmalmTempoFk04(t.get(progetto.dmalmTempoFk04));
			p.setDmalmUserFk06(t.get(progetto.dmalmUserFk06));
			p.setDsAutoreProgSvilS(t.get(progetto.dsAutoreProgSvilS));
			p.setDtAnnullamento(t.get(progetto.dtAnnullamento));
			p.setDtCambioStatoProgSvilS(t.get(progetto.dtCambioStatoProgSvilS));
			p.setDtCaricamentoProgSvilS(t.get(progetto.dtCaricamentoProgSvilS));
			p.setDtCreazioneProgSvilS(t.get(progetto.dtCreazioneProgSvilS));
			p.setDtModificaProgSvilS(t.get(progetto.dtModificaProgSvilS));
			p.setDtRisoluzioneProgSvilS(t.get(progetto.dtRisoluzioneProgSvilS));
			p.setDtScadenzaProgSvilS(t.get(progetto.dtScadenzaProgSvilS));
			p.setDtStoricizzazione(t.get(progetto.dtStoricizzazione));
			p.setDurataEffettivaProgSvilS(t
					.get(progetto.durataEffettivaProgSvilS));
			p.setFornitura(t.get(progetto.fornitura));
			p.setIdAutoreProgSvilS(t.get(progetto.idAutoreProgSvilS));
			p.setIdRepository(t.get(progetto.idRepository));
			p.setMotivoRisoluzioneProgSvilS(t
					.get(progetto.motivoRisoluzioneProgSvilS));
			p.setNumeroLinea(t.get(progetto.numeroLinea));
			p.setNumeroTestata(t.get(progetto.numeroTestata));
			p.setRankStatoProgSvilS(t.get(progetto.rankStatoProgSvilS));
			p.setRankStatoProgSvilSMese(t.get(progetto.rankStatoProgSvilSMese));
			p.setStgPk(t.get(progetto.stgPk));
			p.setTitoloProgSvilS(t.get(progetto.stgPk));
			p.setUri(t.get(progetto.uri));
			//DM_ALM-320
			p.setSeverity(t.get(progetto.severity));
			p.setPriority(t.get(progetto.priority));
			p.setTagAlm(t.get(progetto.tagAlm));
			p.setTsTagAlm(t.get(progetto.tsTagAlm));
			
			return p;

		} else
			return null;
	}

	public static boolean checkEsistenzaProgetto(DmalmProgettoSviluppoSvil pss,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> progetti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			progetti = query
					.from(progetto)
					.where(progetto.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(progetto.cdProgSvilS.eq(pss.getCdProgSvilS()))
					.where(progetto.idRepository.eq(pss.getIdRepository()))
					.list(progetto.all());

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (progetti.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

}
