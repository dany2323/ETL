package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_RICHIESTA_MANUTENZIONE;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaManutenzione;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmRichiestaManutenzione;
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

public class RichiestaManutenzioneDAO {

	private static Logger logger = Logger
			.getLogger(RichiestaManutenzioneDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmRichiestaManutenzione rch_man = QDmalmRichiestaManutenzione.dmalmRichiestaManutenzione;

	public static List<DmalmRichiestaManutenzione> getAllRichiestaManutenzione(
			Timestamp dataEsecuzione) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmRichiestaManutenzione bean = null;
		List<DmalmRichiestaManutenzione> richieste = new ArrayList<DmalmRichiestaManutenzione>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.dman));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					SQL_RICHIESTA_MANUTENZIONE);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmRichiestaManutenzione();

				bean.setCdRichiestaManutenzione(rs
						.getString("CD_RICH_MANUTENZIONE"));
				bean.setClasseDiFornitura(rs.getString("CLASSE_DI_FORNITURA"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setDataChiusuraRichManut(rs
						.getTimestamp("DATA_RISOLUZIONE_RICH_MAN"));
				bean.setDataDispEffettiva(rs
						.getTimestamp("DATA_DISPONIBILITA_EFFETTIVA"));
				bean.setDataDispPianificata(rs
						.getTimestamp("DATA_DISPONIBILITA_PIANIFICATA"));
				bean.setDataInizioEffettivo(rs
						.getTimestamp("DATA_INIZIO_EFFETTIVO"));
				bean.setDataInizioPianificato(rs
						.getTimestamp("DATA_INIZIO_PIANIFICATO"));
				bean.setDescrizioneRichManutenzione(rs
						.getString("DESCRIZIONE_RICH_MAN"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDmalmRichManutenzionePk(rs
						.getInt("DMALM_RICH_MANUTENZIONE_PK"));
				bean.setDsAutoreRichManutenzione(rs
						.getString("NOME_AUTORE_RICH_MAN"));
				bean.setDtCaricamentoRichManut(dataEsecuzione);
				bean.setDtCreazioneRichManutenzione(rs
						.getTimestamp("DATA_CREAZIONE_MAN"));
				bean.setDtModificaRichManutenzione(rs
						.getTimestamp("DATA_MODIFICA_RICH_MAN"));
				bean.setDtRisoluzioneRichManut(rs
						.getTimestamp("DATA_RISOLUZIONE_RICH_MAN"));
				bean.setDtScadenzaRichManutenzione(rs
						.getTimestamp("DATA_SCADENZA_RICH_MAN"));
				bean.setDurataEffettivaRichMan((short) (rs
						.getInt("TEMPO_TOTALE_RISOLUZIONE") - rs
						.getInt("GIORNI_FESTIVI")));
				bean.setIdAutoreRichManutenzione(rs
						.getString("ID_AUTORE_RICH_MAN"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzioneRichManut(rs
						.getString("MOTIVO_RISOL_RICH_MAN"));
				bean.setTitoloRichiestaManutenzione(rs
						.getString("TITOLO_RICH_MANUTENZIONE"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_RICH_MANUTENZIONE_PK"));
				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));
				
				richieste.add(bean);
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

		return richieste;
	}

	public static List<Tuple> getRichiestaManutenzione(
			DmalmRichiestaManutenzione richiesta) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> richieste = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			richieste = query
					.from(rch_man)
					.where(rch_man.cdRichiestaManutenzione
							.equalsIgnoreCase(richiesta
									.getCdRichiestaManutenzione()))
					.where(rch_man.idRepository.equalsIgnoreCase(richiesta
							.getIdRepository()))
					.where(rch_man.rankStatoRichManutenzione.eq(new Double(1)))
					.list(rch_man.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return richieste;

	}

	public static void insertRichiestaManutenzione(
			DmalmRichiestaManutenzione richiesta) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, rch_man)
					.columns(rch_man.cdRichiestaManutenzione,
							rch_man.classeDiFornitura, rch_man.codice,
							rch_man.dataChiusuraRichManut,
							rch_man.dataDispEffettiva,
							rch_man.dataDispPianificata,
							rch_man.dataInizioEffettivo,
							rch_man.dataInizioPianificato,
							rch_man.descrizioneRichManutenzione,
							rch_man.dmalmProjectFk02,
							rch_man.dmalmRichManutenzionePk,
							rch_man.dmalmStatoWorkitemFk03,
							rch_man.dmalmStrutturaOrgFk01,
							rch_man.dmalmTempoFk04,
							rch_man.dsAutoreRichManutenzione,
							rch_man.dtCambioStatoRichManut,
							rch_man.dtCaricamentoRichManut,
							rch_man.dtCreazioneRichManutenzione,
							rch_man.dtModificaRichManutenzione,
							rch_man.dtRisoluzioneRichManut,
							rch_man.dtScadenzaRichManutenzione,
							rch_man.dtStoricizzazione,
							rch_man.durataEffettivaRichMan,
							rch_man.idAutoreRichManutenzione,
							rch_man.idRepository,
							rch_man.motivoRisoluzioneRichManut,
							rch_man.rankStatoRichManutenzione,
							rch_man.titoloRichiestaManutenzione, rch_man.stgPk,
							rch_man.dmalmUserFk06, rch_man.uri,
							rch_man.dtAnnullamento,
							rch_man.severity, rch_man.priority)
					.values(richiesta.getCdRichiestaManutenzione(),
							richiesta.getClasseDiFornitura(),
							richiesta.getCodice(),
							richiesta.getDataChiusuraRichManut(),
							richiesta.getDataDispEffettiva(),
							richiesta.getDataDispPianificata(),
							richiesta.getDataInizioEffettivo(),
							richiesta.getDataInizioPianificato(),
							richiesta.getDescrizioneRichManutenzione(),
							richiesta.getDmalmProjectFk02(),
							richiesta.getDmalmRichManutenzionePk(),
							richiesta.getDmalmStatoWorkitemFk03(),
							richiesta.getDmalmStrutturaOrgFk01(),
							richiesta.getDmalmTempoFk04(),
							richiesta.getDsAutoreRichManutenzione(),
							richiesta.getDtCambioStatoRichManut(),
							richiesta.getDtCaricamentoRichManut(),
							richiesta.getDtCreazioneRichManutenzione(),
							richiesta.getDtModificaRichManutenzione(),
							richiesta.getDtRisoluzioneRichManut(),
							richiesta.getDtScadenzaRichManutenzione(),
							richiesta.getDtModificaRichManutenzione(),
							richiesta.getDurataEffettivaRichMan(),
							richiesta.getIdAutoreRichManutenzione(),
							richiesta.getIdRepository(),
							richiesta.getMotivoRisoluzioneRichManut(),
							new Double(1),
							richiesta.getTitoloRichiestaManutenzione(),
							richiesta.getStgPk(), richiesta.getDmalmUserFk06(),
							richiesta.getUri(), richiesta.getDtAnnullamento(),
							richiesta.getSeverity(), richiesta.getPriority())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmRichiestaManutenzione richiesta,
			Double double1) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, rch_man)
					.where(rch_man.cdRichiestaManutenzione
							.equalsIgnoreCase(richiesta
									.getCdRichiestaManutenzione()))
					.where(rch_man.idRepository.equalsIgnoreCase(richiesta
							.getIdRepository()))
					.set(rch_man.rankStatoRichManutenzione, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertRichiestaManutenzioneUpdate(
			Timestamp dataEsecuzione, DmalmRichiestaManutenzione richiesta,
			boolean pkValue) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, rch_man)
					.columns(rch_man.cdRichiestaManutenzione,
							rch_man.classeDiFornitura, rch_man.codice,
							rch_man.dataChiusuraRichManut,
							rch_man.dataDispEffettiva,
							rch_man.dataDispPianificata,
							rch_man.dataInizioEffettivo,
							rch_man.dataInizioPianificato,
							rch_man.descrizioneRichManutenzione,
							rch_man.dmalmProjectFk02,
							rch_man.dmalmRichManutenzionePk,
							rch_man.dmalmStatoWorkitemFk03,
							rch_man.dmalmStrutturaOrgFk01,
							rch_man.dmalmTempoFk04,
							rch_man.dsAutoreRichManutenzione,
							rch_man.dtCambioStatoRichManut,
							rch_man.dtCaricamentoRichManut,
							rch_man.dtCreazioneRichManutenzione,
							rch_man.dtModificaRichManutenzione,
							rch_man.dtRisoluzioneRichManut,
							rch_man.dtScadenzaRichManutenzione,
							rch_man.dtStoricizzazione,
							rch_man.durataEffettivaRichMan,
							rch_man.idAutoreRichManutenzione,
							rch_man.idRepository,
							rch_man.motivoRisoluzioneRichManut,
							rch_man.rankStatoRichManutenzione,
							rch_man.titoloRichiestaManutenzione, rch_man.stgPk,
							rch_man.dmalmUserFk06, rch_man.uri,
							rch_man.dtAnnullamento, rch_man.changed,
							rch_man.annullato,
							rch_man.severity, rch_man.priority)
					.values(richiesta.getCdRichiestaManutenzione(),
							richiesta.getClasseDiFornitura(),
							richiesta.getCodice(),
							richiesta.getDataChiusuraRichManut(),
							richiesta.getDataDispEffettiva(),
							richiesta.getDataDispPianificata(),
							richiesta.getDataInizioEffettivo(),
							richiesta.getDataInizioPianificato(),
							richiesta.getDescrizioneRichManutenzione(),
							richiesta.getDmalmProjectFk02(),
							pkValue == true ? richiesta
									.getDmalmRichManutenzionePk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							richiesta.getDmalmStatoWorkitemFk03(),
							richiesta.getDmalmStrutturaOrgFk01(),
							richiesta.getDmalmTempoFk04(),
							richiesta.getDsAutoreRichManutenzione(),
							richiesta.getDtCambioStatoRichManut(),
							richiesta.getDtCaricamentoRichManut(),
							richiesta.getDtCreazioneRichManutenzione(),
							richiesta.getDtModificaRichManutenzione(),
							richiesta.getDtRisoluzioneRichManut(),
							richiesta.getDtScadenzaRichManutenzione(),
							pkValue == true ? richiesta
									.getDtModificaRichManutenzione()
									: richiesta.getDtStoricizzazione(),
							richiesta.getDurataEffettivaRichMan(),
							richiesta.getIdAutoreRichManutenzione(),
							richiesta.getIdRepository(),
							richiesta.getMotivoRisoluzioneRichManut(),
							pkValue == true ? new Short("1")  : richiesta.getRankStatoRichManutenzione(),
							richiesta.getTitoloRichiestaManutenzione(),
							richiesta.getStgPk(), richiesta.getDmalmUserFk06(),
							richiesta.getUri(), richiesta.getDtAnnullamento(),
							richiesta.getChanged(), richiesta.getAnnullato(),
							richiesta.getSeverity(), richiesta.getPriority())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRichiestaManutenzione(
			DmalmRichiestaManutenzione richiesta) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, rch_man)

					.where(rch_man.cdRichiestaManutenzione
							.equalsIgnoreCase(richiesta
									.getCdRichiestaManutenzione()))
					.where(rch_man.idRepository.equalsIgnoreCase(richiesta
							.getIdRepository()))
					.where(rch_man.rankStatoRichManutenzione.eq(new Double(1)))

					.set(rch_man.cdRichiestaManutenzione,
							richiesta.getCdRichiestaManutenzione())
					.set(rch_man.classeDiFornitura,
							richiesta.getClasseDiFornitura())
					.set(rch_man.codice, richiesta.getCodice())
					.set(rch_man.dataChiusuraRichManut,
							richiesta.getDataChiusuraRichManut())
					.set(rch_man.dataDispEffettiva,
							richiesta.getDataDispEffettiva())
					.set(rch_man.dataDispPianificata,
							richiesta.getDataDispPianificata())
					.set(rch_man.dataInizioEffettivo,
							richiesta.getDataInizioEffettivo())
					.set(rch_man.dataInizioPianificato,
							richiesta.getDataInizioPianificato())
					.set(rch_man.descrizioneRichManutenzione,
							richiesta.getDescrizioneRichManutenzione())
					.set(rch_man.dmalmProjectFk02,
							richiesta.getDmalmProjectFk02())
					.set(rch_man.dmalmStatoWorkitemFk03,
							richiesta.getDmalmStatoWorkitemFk03())
					.set(rch_man.dmalmStrutturaOrgFk01,
							richiesta.getDmalmStrutturaOrgFk01())
					.set(rch_man.dmalmTempoFk04, richiesta.getDmalmTempoFk04())
					.set(rch_man.dsAutoreRichManutenzione,
							richiesta.getDsAutoreRichManutenzione())
					.set(rch_man.dtCreazioneRichManutenzione,
							richiesta.getDtCreazioneRichManutenzione())
					.set(rch_man.dtModificaRichManutenzione,
							richiesta.getDtModificaRichManutenzione())
					.set(rch_man.dtRisoluzioneRichManut,
							richiesta.getDtRisoluzioneRichManut())
					.set(rch_man.dtScadenzaRichManutenzione,
							richiesta.getDtScadenzaRichManutenzione())
					.set(rch_man.durataEffettivaRichMan,
							richiesta.getDurataEffettivaRichMan())
					.set(rch_man.idAutoreRichManutenzione,
							richiesta.getIdAutoreRichManutenzione())
					.set(rch_man.idRepository, richiesta.getIdRepository())
					.set(rch_man.motivoRisoluzioneRichManut,
							richiesta.getMotivoRisoluzioneRichManut())
					.set(rch_man.titoloRichiestaManutenzione,
							richiesta.getTitoloRichiestaManutenzione())
					.set(rch_man.stgPk, richiesta.getStgPk())
					.set(rch_man.uri, richiesta.getUri())
					.set(rch_man.dtAnnullamento, richiesta.getDtAnnullamento())
					.set(rch_man.annullato, richiesta.getAnnullato())
					.set(rch_man.severity, richiesta.getSeverity())
					.set(rch_man.priority, richiesta.getPriority())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmRichiestaManutenzione getRichiestaManutenzione(Integer pk)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> richieste = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			richieste = query.from(rch_man)
					.where(rch_man.dmalmRichManutenzionePk.eq(pk))
					.list(rch_man.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (richieste.size() > 0) {
			Tuple t = richieste.get(0);
			DmalmRichiestaManutenzione r = new DmalmRichiestaManutenzione();

			r.setAnnullato(t.get(rch_man.annullato));
			r.setCdRichiestaManutenzione(t.get(rch_man.cdRichiestaManutenzione));
			r.setChanged(t.get(rch_man.changed));
			r.setClasseDiFornitura(t.get(rch_man.classeDiFornitura));
			r.setCodice(t.get(rch_man.codice));
			r.setDataChiusuraRichManut(t.get(rch_man.dataChiusuraRichManut));
			r.setDataDispEffettiva(t.get(rch_man.dataDispEffettiva));
			r.setDataDispPianificata(t.get(rch_man.dataDispPianificata));
			r.setDataInizioEffettivo(t.get(rch_man.dataInizioEffettivo));
			r.setDataInizioPianificato(t.get(rch_man.dataInizioPianificato));
			r.setDescrizioneRichManutenzione(t
					.get(rch_man.descrizioneRichManutenzione));
			r.setDmalmProjectFk02(t.get(rch_man.dmalmProjectFk02));
			r.setDmalmRichManutenzionePk(t.get(rch_man.dmalmRichManutenzionePk));
			r.setDmalmStatoWorkitemFk03(t.get(rch_man.dmalmStatoWorkitemFk03));
			r.setDmalmStrutturaOrgFk01(t.get(rch_man.dmalmStrutturaOrgFk01));
			r.setDmalmTempoFk04(t.get(rch_man.dmalmTempoFk04));
			r.setDmalmUserFk06(t.get(rch_man.dmalmUserFk06));
			r.setDsAutoreRichManutenzione(t
					.get(rch_man.dsAutoreRichManutenzione));
			r.setDtAnnullamento(t.get(rch_man.dtAnnullamento));
			r.setDtCambioStatoRichManut(t.get(rch_man.dtCambioStatoRichManut));
			r.setDtCaricamentoRichManut(t.get(rch_man.dtCaricamentoRichManut));
			r.setDtCreazioneRichManutenzione(t
					.get(rch_man.dtCreazioneRichManutenzione));
			r.setDtModificaRichManutenzione(t
					.get(rch_man.dtModificaRichManutenzione));
			r.setDtRisoluzioneRichManut(t.get(rch_man.dtRisoluzioneRichManut));
			r.setDtScadenzaRichManutenzione(t
					.get(rch_man.dtScadenzaRichManutenzione));
			r.setDtStoricizzazione(t.get(rch_man.dtStoricizzazione));
			r.setDurataEffettivaRichMan(t.get(rch_man.durataEffettivaRichMan));
			r.setIdAutoreRichManutenzione(t
					.get(rch_man.idAutoreRichManutenzione));
			r.setIdRepository(t.get(rch_man.idRepository));
			r.setMotivoRisoluzioneRichManut(t
					.get(rch_man.motivoRisoluzioneRichManut));
			r.setRankStatoRichManutenzione(t
					.get(rch_man.rankStatoRichManutenzione));
			r.setRankStatoRichManutMese(t.get(rch_man.rankStatoRichManutMese));
			r.setStgPk(t.get(rch_man.stgPk));
			r.setTitoloRichiestaManutenzione(t
					.get(rch_man.titoloRichiestaManutenzione));
			r.setUri(t.get(rch_man.uri));
			//DM_ALM-320
			r.setSeverity(t.get(rch_man.severity));
			r.setPriority(t.get(rch_man.priority));

			return r;

		} else
			return null;
	}

	public static boolean checkEsistenzaRichiesta(DmalmRichiestaManutenzione r,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(rch_man)
					.where(rch_man.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(rch_man.cdRichiestaManutenzione.eq(r.getCdRichiestaManutenzione()))
					.where(rch_man.idRepository.eq(r.getIdRepository()))
					.list(rch_man.all());

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
