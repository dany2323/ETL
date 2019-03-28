package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_MANUTENZIONE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmManutenzione;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmManutenzione;
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

public class ManutenzioneDAO {

	private static Logger logger = Logger.getLogger(ManutenzioneDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmManutenzione man = QDmalmManutenzione.dmalmManutenzione;

	public static List<DmalmManutenzione> getAllManutenzione(
			Timestamp dataEsecuzione) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmManutenzione bean = null;
		List<DmalmManutenzione> manutenzioni = new ArrayList<DmalmManutenzione>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.sman));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_MANUTENZIONE);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmManutenzione();

				bean.setCdManutenzione(rs.getString("CD_MANUTENZIONE"));
				bean.setCodice(rs.getString("CODICE_MANUTENZIONE"));
				bean.setDataDispEff(rs.getTimestamp("CF_DATA_DISP_EFFETTIVA"));
				bean.setDataDisponibilita(rs
						.getTimestamp("CF_DATA_DISP_PIANIFICATA"));
				bean.setDataInizio(rs
						.getTimestamp("CF_DATA_INIZIO_PIANIFICATO"));
				bean.setDataInizioEff(rs.getTimestamp("CF_DATA_INIZIO_EFF"));
				bean.setDataRilascioInEs(rs
						.getTimestamp("DATA_RILASCIO_MANUTENZIONE"));
				bean.setDescrizioneManutenzione(rs.getString("DESCRIPTION"));
				bean.setDmalmManutenzionePk(rs.getInt("DMALM_MANUTENZIONE_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreManutenzione(rs
						.getString("NOME_AUTORE_MANUTENZIONE"));
				bean.setDtCaricamentoManutenzione(dataEsecuzione);
				bean.setDtCreazioneManutenzione(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaManutenzione(rs
						.getTimestamp("DATA_MODIFICA_MANUTENZIONE"));
				bean.setDtRisoluzioneManutenzione(rs
						.getTimestamp("DATA_RISOLUZIONE_MANUTENZIONE"));
				bean.setDtScadenzaManutenzione(rs
						.getTimestamp("DATA_SCADENZA_MANUTENZIONE"));
				bean.setFornitura(rs.getString("CF_FORNITURA"));
				bean.setIdAutoreManutenzione(rs
						.getString("ID_AUTORE_MANUTENZIONE"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzioneManutenzion(rs
						.getString("MOTIVO_RISOLUZIONE"));
				bean.setNumeroLinea(StringUtils.getLinea(rs
						.getString("CODICE_INTERVENTO")));
				bean.setNumeroTestata(StringUtils.getTestata(rs
						.getString("CODICE_INTERVENTO")));
				bean.setPriorityManutenzione(rs
						.getString("PRIORITA_MANUTENZIONE"));
				bean.setSeverityManutenzione(rs
						.getString("SEVERITY_MANUTENZIONE"));
				bean.setTempoTotaleRisoluzione(rs
						.getInt("TEMPO_TOTALE_RISOLUZIONE")
						- rs.getInt("GIORNI_FESTIVI"));
				bean.setTitoloManutenzione(rs.getString("TITOLO_MANUTENZIONE"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_MANUTENZIONE_PK"));

				manutenzioni.add(bean);
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

		return manutenzioni;
	}

	public static List<Tuple> getManutenzione(DmalmManutenzione manutenzione)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> manutenzioni = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			manutenzioni = query
					.from(man)
					.where(man.cdManutenzione.equalsIgnoreCase(manutenzione
							.getCdManutenzione()))
					.where(man.idRepository.equalsIgnoreCase(manutenzione
							.getIdRepository()))
					.where(man.rankStatoManutenzione.eq(new Double(1)))
					.list(man.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return manutenzioni;

	}

	public static void insertManutenzione(DmalmManutenzione manutenzione)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, man)
					.columns(man.assigneesManutenzione, man.cdManutenzione,
							man.codice, man.dataDispEff, man.dataDisponibilita,
							man.dataInizio, man.dataInizioEff,
							man.dataRilascioInEs, man.descrizioneManutenzione,
							man.dmalmAreaTematicaFk05, man.dmalmManutenzionePk,
							man.dmalmProjectFk02, man.dmalmStatoWorkitemFk03,
							man.dmalmStrutturaOrgFk01, man.dmalmTempoFk04,
							man.dsAutoreManutenzione,
							man.dtCambioStatoManutenzione,
							man.dtCaricamentoManutenzione,
							man.dtCreazioneManutenzione,
							man.dtModificaManutenzione,
							man.dtRisoluzioneManutenzione,
							man.dtScadenzaManutenzione, man.dtStoricizzazione,
							man.fornitura, man.idAutoreManutenzione,
							man.idRepository, man.motivoRisoluzioneManutenzion,
							man.numeroLinea, man.numeroTestata,
							man.priorityManutenzione,
							man.rankStatoManutenzione,
							man.severityManutenzione,
							man.tempoTotaleRisoluzione, man.titoloManutenzione,
							man.stgPk, man.dmalmUserFk06, man.uri,
							man.dmalmProgettoSferaFk, man.dtAnnullamento)
					.values(manutenzione.getAssigneesManutenzione(),
							manutenzione.getCdManutenzione(),
							manutenzione.getCodice(),
							manutenzione.getDataDispEff(),
							manutenzione.getDataDisponibilita(),
							manutenzione.getDataInizio(),
							manutenzione.getDataInizioEff(),
							manutenzione.getDataRilascioInEs(),
							manutenzione.getDescrizioneManutenzione(),
							manutenzione.getDmalmAreaTematicaFk05(),
							manutenzione.getDmalmManutenzionePk(),
							manutenzione.getDmalmProjectFk02(),
							manutenzione.getDmalmStatoWorkitemFk03(),
							manutenzione.getDmalmStrutturaOrgFk01(),
							manutenzione.getDmalmTempoFk04(),
							manutenzione.getDsAutoreManutenzione(),
							manutenzione.getDtCambioStatoManutenzione(),
							manutenzione.getDtCaricamentoManutenzione(),
							manutenzione.getDtCreazioneManutenzione(),
							manutenzione.getDtModificaManutenzione(),
							manutenzione.getDtRisoluzioneManutenzione(),
							manutenzione.getDtScadenzaManutenzione(),
							manutenzione.getDtModificaManutenzione(),
							manutenzione.getFornitura(),
							manutenzione.getIdAutoreManutenzione(),
							manutenzione.getIdRepository(),
							manutenzione.getMotivoRisoluzioneManutenzion(),
							manutenzione.getNumeroLinea(),
							manutenzione.getNumeroTestata(),
							manutenzione.getPriorityManutenzione(),
							new Double(1),
							manutenzione.getSeverityManutenzione(),
							manutenzione.getTempoTotaleRisoluzione(),
							manutenzione.getTitoloManutenzione(),
							manutenzione.getStgPk(),
							manutenzione.getDmalmUserFk06(),
							manutenzione.getUri(),
							manutenzione.getDmalmProgettoSferaFk(),
							manutenzione.getDtAnnullamento()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmManutenzione manutenzione, Double double1)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, man)
					.where(man.cdManutenzione.equalsIgnoreCase(manutenzione
							.getCdManutenzione()))
					.where(man.idRepository.equalsIgnoreCase(manutenzione
							.getIdRepository()))
					.set(man.rankStatoManutenzione, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertManutenzioneUpdate(Timestamp dataEsecuzione,
			DmalmManutenzione manutenzione, boolean pkValue)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, man)
					.columns(man.assigneesManutenzione, man.cdManutenzione,
							man.codice, man.dataDispEff, man.dataDisponibilita,
							man.dataInizio, man.dataInizioEff,
							man.dataRilascioInEs, man.descrizioneManutenzione,
							man.dmalmAreaTematicaFk05, man.dmalmManutenzionePk,
							man.dmalmProjectFk02, man.dmalmStatoWorkitemFk03,
							man.dmalmStrutturaOrgFk01, man.dmalmTempoFk04,
							man.dsAutoreManutenzione,
							man.dtCambioStatoManutenzione,
							man.dtCaricamentoManutenzione,
							man.dtCreazioneManutenzione,
							man.dtModificaManutenzione,
							man.dtRisoluzioneManutenzione,
							man.dtScadenzaManutenzione, man.dtStoricizzazione,
							man.fornitura, man.idAutoreManutenzione,
							man.idRepository, man.motivoRisoluzioneManutenzion,
							man.numeroLinea, man.numeroTestata,
							man.priorityManutenzione,
							man.rankStatoManutenzione,
							man.severityManutenzione,
							man.tempoTotaleRisoluzione, man.titoloManutenzione,
							man.stgPk, man.dmalmUserFk06, man.uri,
							man.dmalmProgettoSferaFk, man.dtAnnullamento,
							man.changed, man.annullato)
					.values(manutenzione.getAssigneesManutenzione(),
							manutenzione.getCdManutenzione(),
							manutenzione.getCodice(),
							manutenzione.getDataDispEff(),
							manutenzione.getDataDisponibilita(),
							manutenzione.getDataInizio(),
							manutenzione.getDataInizioEff(),
							manutenzione.getDataRilascioInEs(),
							manutenzione.getDescrizioneManutenzione(),
							manutenzione.getDmalmAreaTematicaFk05(),
							pkValue == true ? manutenzione
									.getDmalmManutenzionePk() : StringTemplate
									.create("HISTORY_WORKITEM_SEQ.nextval"),
							manutenzione.getDmalmProjectFk02(),
							manutenzione.getDmalmStatoWorkitemFk03(),
							manutenzione.getDmalmStrutturaOrgFk01(),
							manutenzione.getDmalmTempoFk04(),
							manutenzione.getDsAutoreManutenzione(),
							manutenzione.getDtCambioStatoManutenzione(),
							manutenzione.getDtCaricamentoManutenzione(),
							manutenzione.getDtCreazioneManutenzione(),
							manutenzione.getDtModificaManutenzione(),
							manutenzione.getDtRisoluzioneManutenzione(),
							manutenzione.getDtScadenzaManutenzione(),
							pkValue == true ? manutenzione
									.getDtModificaManutenzione() : manutenzione
									.getDtStoricizzazione(),
							manutenzione.getFornitura(),
							manutenzione.getIdAutoreManutenzione(),
							manutenzione.getIdRepository(),
							manutenzione.getMotivoRisoluzioneManutenzion(),
							manutenzione.getNumeroLinea(),
							manutenzione.getNumeroTestata(),
							manutenzione.getPriorityManutenzione(),
							pkValue == true ? new Short("1")  : manutenzione.getRankStatoManutenzione(),
							manutenzione.getSeverityManutenzione(),
							manutenzione.getTempoTotaleRisoluzione(),
							manutenzione.getTitoloManutenzione(),
							manutenzione.getStgPk(),
							manutenzione.getDmalmUserFk06(),
							manutenzione.getUri(),
							manutenzione.getDmalmProgettoSferaFk(),
							manutenzione.getDtAnnullamento(),
							manutenzione.getChanged(),
							manutenzione.getAnnullato()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateManutenzione(DmalmManutenzione manutenzione)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, man)

					.where(man.cdManutenzione.equalsIgnoreCase(manutenzione
							.getCdManutenzione()))
					.where(man.idRepository.equalsIgnoreCase(manutenzione
							.getIdRepository()))
					.where(man.rankStatoManutenzione.eq(new Double(1)))

					.set(man.assigneesManutenzione,
							manutenzione.getAssigneesManutenzione())
					.set(man.cdManutenzione, manutenzione.getCdManutenzione())
					.set(man.codice, manutenzione.getCodice())
					.set(man.dataDispEff, manutenzione.getDataDispEff())
					.set(man.dataDisponibilita,
							manutenzione.getDataDisponibilita())
					.set(man.dataInizio, manutenzione.getDataInizio())
					.set(man.dataInizioEff, manutenzione.getDataInizioEff())
					.set(man.dataRilascioInEs,
							manutenzione.getDataRilascioInEs())
					.set(man.descrizioneManutenzione,
							manutenzione.getDescrizioneManutenzione())
					.set(man.dmalmAreaTematicaFk05,
							manutenzione.getDmalmAreaTematicaFk05())
					.set(man.dmalmProjectFk02,
							manutenzione.getDmalmProjectFk02())
					.set(man.dmalmStatoWorkitemFk03,
							manutenzione.getDmalmStatoWorkitemFk03())
					.set(man.dmalmStrutturaOrgFk01,
							manutenzione.getDmalmStrutturaOrgFk01())
					.set(man.dmalmTempoFk04, manutenzione.getDmalmTempoFk04())
					.set(man.dsAutoreManutenzione,
							manutenzione.getDsAutoreManutenzione())
					.set(man.dtCreazioneManutenzione,
							manutenzione.getDtCreazioneManutenzione())
					.set(man.dtModificaManutenzione,
							manutenzione.getDtModificaManutenzione())
					.set(man.dtRisoluzioneManutenzione,
							manutenzione.getDtRisoluzioneManutenzione())
					.set(man.dtScadenzaManutenzione,
							manutenzione.getDtScadenzaManutenzione())
					.set(man.fornitura, manutenzione.getFornitura())
					.set(man.idAutoreManutenzione,
							manutenzione.getIdAutoreManutenzione())
					.set(man.idRepository, manutenzione.getIdRepository())
					.set(man.motivoRisoluzioneManutenzion,
							manutenzione.getMotivoRisoluzioneManutenzion())
					.set(man.numeroLinea, manutenzione.getNumeroLinea())
					.set(man.numeroTestata, manutenzione.getNumeroTestata())
					.set(man.priorityManutenzione,
							manutenzione.getPriorityManutenzione())
					.set(man.severityManutenzione,
							manutenzione.getSeverityManutenzione())
					.set(man.tempoTotaleRisoluzione,
							manutenzione.getTempoTotaleRisoluzione())
					.set(man.titoloManutenzione,
							manutenzione.getTitoloManutenzione())
					.set(man.stgPk, manutenzione.getStgPk())
					.set(man.uri, manutenzione.getUri())
					.set(man.dtAnnullamento, manutenzione.getDtAnnullamento())
					.set(man.annullato, manutenzione.getAnnullato())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmManutenzione getManutenzione(Integer pk)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> manutenzioni = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			manutenzioni = query.from(man)
					.where(man.dmalmManutenzionePk.eq(pk)).list(man.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (manutenzioni.size() > 0) {
			Tuple t = manutenzioni.get(0);
			DmalmManutenzione m = new DmalmManutenzione();

			m.setAnnullato(t.get(man.annullato));
			m.setAssigneesManutenzione(t.get(man.assigneesManutenzione));
			m.setCdManutenzione(t.get(man.cdManutenzione));
			m.setChanged(t.get(man.changed));
			m.setCodice(t.get(man.codice));
			m.setDataDispEff(t.get(man.dataDispEff));
			m.setDataDisponibilita(t.get(man.dataDisponibilita));
			m.setDataInizio(t.get(man.dataInizio));
			m.setDataInizioEff(t.get(man.dataInizioEff));
			m.setDataRilascioInEs(t.get(man.dataRilascioInEs));
			m.setDescrizioneManutenzione(t.get(man.descrizioneManutenzione));
			m.setDmalmAreaTematicaFk05(t.get(man.dmalmAreaTematicaFk05));
			m.setDmalmManutenzionePk(t.get(man.dmalmManutenzionePk));
			m.setDmalmProgettoSferaFk(t.get(man.dmalmProgettoSferaFk));
			m.setDmalmProjectFk02(t.get(man.dmalmProjectFk02));
			m.setDmalmStatoWorkitemFk03(t.get(man.dmalmStatoWorkitemFk03));
			m.setDmalmStrutturaOrgFk01(t.get(man.dmalmStrutturaOrgFk01));
			m.setDmalmTempoFk04(t.get(man.dmalmTempoFk04));
			m.setDmalmUserFk06(t.get(man.dmalmUserFk06));
			m.setDsAutoreManutenzione(t.get(man.dsAutoreManutenzione));
			m.setDtAnnullamento(t.get(man.dtAnnullamento));
			m.setDtCambioStatoManutenzione(t.get(man.dtCambioStatoManutenzione));
			m.setDtCaricamentoManutenzione(t.get(man.dtCaricamentoManutenzione));
			m.setDtCreazioneManutenzione(t.get(man.dtCreazioneManutenzione));
			m.setDtModificaManutenzione(t.get(man.dtModificaManutenzione));
			m.setDtRisoluzioneManutenzione(t.get(man.dtRisoluzioneManutenzione));
			m.setDtScadenzaManutenzione(t.get(man.dtScadenzaManutenzione));
			m.setDtStoricizzazione(t.get(man.dtStoricizzazione));
			m.setFornitura(t.get(man.fornitura));
			m.setIdAutoreManutenzione(t.get(man.idAutoreManutenzione));
			m.setIdRepository(t.get(man.idRepository));
			m.setMotivoRisoluzioneManutenzion(t
					.get(man.motivoRisoluzioneManutenzion));
			m.setNumeroLinea(t.get(man.numeroLinea));
			m.setNumeroTestata(t.get(man.numeroTestata));
			m.setPriorityManutenzione(t.get(man.priorityManutenzione));
			m.setRankStatoManutenzione(t.get(man.rankStatoManutenzione));
			m.setRankStatoManutenzioneMese(t.get(man.rankStatoManutenzioneMese));
			m.setSeverityManutenzione(t.get(man.severityManutenzione));
			m.setStgPk(t.get(man.stgPk));
			m.setTempoTotaleRisoluzione(t.get(man.tempoTotaleRisoluzione));
			m.setTitoloManutenzione(t.get(man.titoloManutenzione));
			m.setUri(t.get(man.uri));

			return m;
		} else
			return null;
	}

	public static boolean checkEsistenzaManutenzione(DmalmManutenzione m,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(man)
					.where(man.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(man.cdManutenzione.eq(m.getCdManutenzione()))
					.where(man.idRepository.eq(m.getIdRepository()))
					.list(man.all());

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
