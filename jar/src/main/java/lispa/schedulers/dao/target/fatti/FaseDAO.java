package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_FASE;

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
import lispa.schedulers.bean.target.fatti.DmalmFase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmFase;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

public class FaseDAO {

	private static Logger logger = Logger.getLogger(FaseDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmFase fs = QDmalmFase.dmalmFase;

	public static List<DmalmFase> getAllFase(Timestamp dataEsecuzione)
			throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmFase bean = null;
		List<DmalmFase> fasi = new ArrayList<DmalmFase>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.fase));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(SQL_FASE);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(DmAlmConstants.FETCH_SIZE);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmFase();

				bean.setApplicabile(rs.getBoolean("CF_APPLICABILE_FASE"));
				bean.setCdFase(rs.getString("CD_FASE"));
				bean.setCodice(rs.getString("CF_CODICE_FASE"));
				bean.setDataFineBaseline(rs
						.getTimestamp("CF_baseline_end_FASE"));
				bean.setDataFineEffettiva(rs
						.getTimestamp("CF_effettiva_end_FASE"));
				bean.setDataFinePianificata(rs
						.getTimestamp("CF_pianificata_end_FASE"));
				bean.setDataInizioBaseline(rs
						.getTimestamp("CF_baseline_start_FASE"));
				bean.setDataInizioEffettivo(rs
						.getTimestamp("CF_effettiva_start_FASE"));
				bean.setDataInizioPianificato(rs
						.getTimestamp("CF_pianificata_start_FASE"));
				bean.setDataPassaggioInEsecuzione(rs
						.getTimestamp("DATA_PASSAGGIO_ESECUZIONE_FASE"));
				bean.setDescrizioneFase(rs.getString("DESCRIZIONE_FASE"));
				bean.setDmalmFasePk(rs.getInt("DMALM_FASE_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreFase(rs.getString("NOME_AUTORE_FASE"));
				bean.setDtCaricamentoFase(dataEsecuzione);
				bean.setDtCreazioneFase(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD_FASE"));
				bean.setDtModificaFase(rs.getTimestamp("DATA_MODIFICA_FASE"));
				bean.setDtRisoluzioneFase(rs
						.getTimestamp("DATA_RISOLUZIONE_FASE"));
				bean.setDtScadenzaFase(rs.getTimestamp("DATA_SCADENZA_FASE"));
				bean.setDurataEffettivaFase((short) (rs
						.getInt("TEMPO_TOTALE_RISOLUZIONE") - rs
						.getInt("GIORNI_FESTIVI")));
				bean.setIdAutoreFase(rs.getString("ID_AUTORE_FASE"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzioneFase(rs
						.getString("MOTIVO_RISOLUZIONE_FASE"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_FASE_PK"));
				bean.setTitoloFase(rs.getString("TITOLO_FASE"));

				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));
				bean.setTagAlm(rs.getString("TAG_ALM"));
				bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
				
				fasi.add(bean);
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

		return fasi;
	}

	public static List<Tuple> getFase(DmalmFase fase) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> documenti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			documenti = query
					.from(fs)
					.where(fs.cdFase.equalsIgnoreCase(fase.getCdFase()))
					.where(fs.idRepository.equalsIgnoreCase(fase
							.getIdRepository()))
					.where(fs.rankStatoFase.eq(new Double(1))).list(fs.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return documenti;

	}

	public static void insertFase(DmalmFase fase) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, fs)
					.columns(fs.applicabile, fs.cdFase, fs.codice,
							fs.dataFineBaseline, fs.dataFineEffettiva,
							fs.dataFinePianificata, fs.dataInizioBaseline,
							fs.dataInizioEffettivo, fs.dataInizioPianificato,
							fs.dataPassaggioInEsecuzione, fs.descrizioneFase,
							fs.dmalmFasePk, fs.dmalmProjectFk02,
							fs.dmalmStatoWorkitemFk03,
							fs.dmalmStrutturaOrgFk01, fs.dmalmTempoFk04,
							fs.dsAutoreFase, fs.dtCambioStatoFase,
							fs.dtCaricamentoFase, fs.dtCreazioneFase,
							fs.dtModificaFase, fs.dtRisoluzioneFase,
							fs.dtScadenzaFase, fs.dtStoricizzazione,
							fs.durataEffettivaFase, fs.idAutoreFase,
							fs.idRepository, fs.motivoRisoluzioneFase,
							fs.rankStatoFase, fs.titoloFase, fs.stgPk,
							fs.dmalmUserFk06, fs.uri, fs.dtAnnullamento,
							fs.severity, fs.priority,
							fs.tagAlm,	fs.tsTagAlm)
					.values(fase.getApplicabile(), fase.getCdFase(),
							fase.getCodice(), fase.getDataFineBaseline(),
							fase.getDataFineEffettiva(),
							fase.getDataFinePianificata(),
							fase.getDataInizioBaseline(),
							fase.getDataInizioEffettivo(),
							fase.getDataInizioPianificato(),
							fase.getDataPassaggioInEsecuzione(),
							fase.getDescrizioneFase(), fase.getDmalmFasePk(),
							fase.getDmalmProjectFk02(),
							fase.getDmalmStatoWorkitemFk03(),
							fase.getDmalmStrutturaOrgFk01(),
							fase.getDmalmTempoFk04(), fase.getDsAutoreFase(),
							fase.getDtCambioStatoFase(),
							fase.getDtCaricamentoFase(),
							fase.getDtCreazioneFase(),
							fase.getDtModificaFase(),
							fase.getDtRisoluzioneFase(),
							fase.getDtScadenzaFase(), fase.getDtModificaFase(),
							fase.getDurataEffettivaFase(),
							fase.getIdAutoreFase(), fase.getIdRepository(),
							fase.getMotivoRisoluzioneFase(), new Double(1),
							fase.getTitoloFase(), fase.getStgPk(),
							fase.getDmalmUserFk06(), fase.getUri(),
							fase.getDtAnnullamento(),
							fase.getSeverity(), fase.getPriority(),
							fase.getTagAlm(), fase.getTsTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmFase fase, Double double1)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, fs)
					.where(fs.cdFase.equalsIgnoreCase(fase.getCdFase()))
					.where(fs.idRepository.equalsIgnoreCase(fase
							.getIdRepository())).set(fs.rankStatoFase, double1)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertFaseUpdate(Timestamp dataEsecuzione,
			DmalmFase fase, boolean pkValue) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, fs)
					.columns(fs.applicabile, fs.cdFase, fs.codice,
							fs.dataFineBaseline, fs.dataFineEffettiva,
							fs.dataFinePianificata, fs.dataInizioBaseline,
							fs.dataInizioEffettivo, fs.dataInizioPianificato,
							fs.dataPassaggioInEsecuzione, fs.descrizioneFase,
							fs.dmalmFasePk, fs.dmalmProjectFk02,
							fs.dmalmStatoWorkitemFk03,
							fs.dmalmStrutturaOrgFk01, fs.dmalmTempoFk04,
							fs.dsAutoreFase, fs.dtCambioStatoFase,
							fs.dtCaricamentoFase, fs.dtCreazioneFase,
							fs.dtModificaFase, fs.dtRisoluzioneFase,
							fs.dtScadenzaFase, fs.dtStoricizzazione,
							fs.durataEffettivaFase, fs.idAutoreFase,
							fs.idRepository, fs.motivoRisoluzioneFase,
							fs.rankStatoFase, fs.titoloFase, fs.stgPk,
							fs.dmalmUserFk06, fs.uri, fs.dtAnnullamento,
							fs.changed, fs.annullato,
							fs.severity, fs.priority,
							fs.tagAlm,	fs.tsTagAlm)
					.values(fase.getApplicabile(),
							fase.getCdFase(),
							fase.getCodice(),
							fase.getDataFineBaseline(),
							fase.getDataFineEffettiva(),
							fase.getDataFinePianificata(),
							fase.getDataInizioBaseline(),
							fase.getDataInizioEffettivo(),
							fase.getDataInizioPianificato(),
							fase.getDataPassaggioInEsecuzione(),
							fase.getDescrizioneFase(),
							pkValue == true ? fase.getDmalmFasePk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							fase.getDmalmProjectFk02(),
							fase.getDmalmStatoWorkitemFk03(),
							fase.getDmalmStrutturaOrgFk01(),
							fase.getDmalmTempoFk04(),
							fase.getDsAutoreFase(),
							fase.getDtCambioStatoFase(),
							fase.getDtCaricamentoFase(),
							fase.getDtCreazioneFase(),
							fase.getDtModificaFase(),
							fase.getDtRisoluzioneFase(),
							fase.getDtScadenzaFase(),
							pkValue == true ? fase.getDtModificaFase() : fase
									.getDtStoricizzazione(),
							fase.getDurataEffettivaFase(),
							fase.getIdAutoreFase(), fase.getIdRepository(),
							fase.getMotivoRisoluzioneFase(), 
							pkValue == true ? new Short("1")  : fase.getRankStatoFase(),
							fase.getTitoloFase(), fase.getStgPk(),
							fase.getDmalmUserFk06(), fase.getUri(),
							fase.getDtAnnullamento(), fase.getChanged(),
							fase.getAnnullato(),
							fase.getSeverity(), fase.getPriority(),
							fase.getTagAlm(), fase.getTsTagAlm()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateFase(DmalmFase fase) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, fs)

					.where(fs.cdFase.equalsIgnoreCase(fase.getCdFase()))
					.where(fs.idRepository.equalsIgnoreCase(fase
							.getIdRepository()))
					.where(fs.rankStatoFase.eq(new Double(1)))

					.set(fs.applicabile, fase.getApplicabile())
					.set(fs.cdFase, fase.getCdFase())
					.set(fs.codice, fase.getCodice())
					.set(fs.dataFineBaseline, fase.getDataFineBaseline())
					.set(fs.dataFineEffettiva, fase.getDataFineEffettiva())
					.set(fs.dataFinePianificata, fase.getDataFinePianificata())
					.set(fs.dataInizioBaseline, fase.getDataInizioBaseline())
					.set(fs.dataInizioEffettivo, fase.getDataInizioEffettivo())
					.set(fs.dataInizioPianificato,
							fase.getDataInizioPianificato())
					.set(fs.dataPassaggioInEsecuzione,
							fase.getDataPassaggioInEsecuzione())
					.set(fs.descrizioneFase, fase.getDescrizioneFase())
					.set(fs.dmalmProjectFk02, fase.getDmalmProjectFk02())
					.set(fs.dmalmStatoWorkitemFk03,
							fase.getDmalmStatoWorkitemFk03())
					.set(fs.dmalmStrutturaOrgFk01,
							fase.getDmalmStrutturaOrgFk01())
					.set(fs.dmalmTempoFk04, fase.getDmalmTempoFk04())
					.set(fs.dsAutoreFase, fase.getDsAutoreFase())
					.set(fs.dtCreazioneFase, fase.getDtCreazioneFase())
					.set(fs.dtModificaFase, fase.getDtModificaFase())
					.set(fs.dtRisoluzioneFase, fase.getDtRisoluzioneFase())
					.set(fs.dtScadenzaFase, fase.getDtScadenzaFase())
					.set(fs.durataEffettivaFase, fase.getDurataEffettivaFase())
					.set(fs.idAutoreFase, fase.getIdAutoreFase())
					.set(fs.idRepository, fase.getIdRepository())
					.set(fs.motivoRisoluzioneFase,
							fase.getMotivoRisoluzioneFase())
					.set(fs.titoloFase, fase.getTitoloFase())
					.set(fs.stgPk, fase.getStgPk()).set(fs.uri, fase.getUri())
					.set(fs.dtAnnullamento, fase.getDtAnnullamento())
					.set(fs.annullato, fase.getAnnullato())
					.set(fs.severity, fase.getSeverity())
					.set(fs.priority, fase.getPriority())
					.set(fs.tagAlm, fase.getTagAlm())
					.set(fs.tsTagAlm, fase.getTsTagAlm())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmFase getFase(Integer pk) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query.from(fs).where(fs.dmalmFasePk.eq(pk)).list(fs.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (list.size() > 0) {
			Tuple t = list.get(0);
			DmalmFase f = new DmalmFase();

			f.setAnnullato(t.get(fs.annullato));
			f.setApplicabile(t.get(fs.applicabile));
			f.setCdFase(t.get(fs.cdFase));
			f.setChanged(t.get(fs.changed));
			f.setCodice(t.get(fs.codice));
			f.setDataFineBaseline(t.get(fs.dataFineBaseline));
			f.setDataFineEffettiva(t.get(fs.dataFineEffettiva));
			f.setDataFinePianificata(t.get(fs.dataFinePianificata));
			f.setDataInizioBaseline(t.get(fs.dataInizioBaseline));
			f.setDataInizioEffettivo(t.get(fs.dataInizioEffettivo));
			f.setDataInizioPianificato(t.get(fs.dataInizioPianificato));
			f.setDataPassaggioInEsecuzione(t.get(fs.dataPassaggioInEsecuzione));
			f.setDescrizioneFase(t.get(fs.descrizioneFase));
			f.setDmalmFasePk(t.get(fs.dmalmFasePk));
			f.setDmalmProjectFk02(t.get(fs.dmalmProjectFk02));
			f.setDmalmStatoWorkitemFk03(t.get(fs.dmalmStatoWorkitemFk03));
			f.setDmalmStrutturaOrgFk01(t.get(fs.dmalmStrutturaOrgFk01));
			f.setDmalmTempoFk04(t.get(fs.dmalmTempoFk04));
			f.setDmalmUserFk06(t.get(fs.dmalmUserFk06));
			f.setDsAutoreFase(t.get(fs.dsAutoreFase));
			f.setDtAnnullamento(t.get(fs.dtAnnullamento));
			f.setDtCambioStatoFase(t.get(fs.dtCambioStatoFase));
			f.setDtCaricamentoFase(t.get(fs.dtCaricamentoFase));
			f.setDtCreazioneFase(t.get(fs.dtCreazioneFase));
			f.setDtModificaFase(t.get(fs.dtModificaFase));
			f.setDtRisoluzioneFase(t.get(fs.dtRisoluzioneFase));
			f.setDtScadenzaFase(t.get(fs.dtScadenzaFase));
			f.setDtStoricizzazione(t.get(fs.dtStoricizzazione));
			f.setDurataEffettivaFase(t.get(fs.durataEffettivaFase));
			f.setIdAutoreFase(t.get(fs.idAutoreFase));
			f.setIdRepository(t.get(fs.idRepository));
			f.setMotivoRisoluzioneFase(t.get(fs.motivoRisoluzioneFase));
			f.setRankStatoFase(t.get(fs.rankStatoFase));
			f.setRankStatoFaseMese(t.get(fs.rankStatoFaseMese));
			f.setStgPk(t.get(fs.stgPk));
			f.setTitoloFase(t.get(fs.titoloFase));
			f.setUri(t.get(fs.uri));
			//DM_ALM-320
			f.setSeverity(t.get(fs.severity));
			f.setPriority(t.get(fs.priority));
			f.setTagAlm(t.get(fs.tagAlm));
			f.setTsTagAlm(t.get(fs.tsTagAlm));
			
			return f;

		} else
			return null;
	}

	public static boolean checkEsistenzaFase(DmalmFase f, DmalmProject p)throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(fs)
					.where(fs.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(fs.cdFase.eq(f.getCdFase()))
					.where(fs.idRepository.eq(f.getIdRepository()))
					.list(fs.all());

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

	public static void updateProjectAndStatus(DmalmFase fase) {
		
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, fs)

					.where(fs.stgPk.eq(fase.getStgPk()))
					.set(fs.dmalmProjectFk02, fase.getDmalmProjectFk02())
					.set(fs.dmalmStatoWorkitemFk03,
							fase.getDmalmStatoWorkitemFk03())
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
