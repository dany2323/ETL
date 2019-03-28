package lispa.schedulers.dao.target.fatti;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmProgettoDemand;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.QDmalmFilieraProduttiva;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmProgettoDemand;
import lispa.schedulers.utils.QueryUtils;
import lispa.schedulers.utils.enums.Workitem_Type;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.Projections;
import com.mysema.query.types.template.StringTemplate;

public class ProgettoDemandDAO {

	private static Logger logger = Logger.getLogger(ProgettoDemandDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmProgettoDemand progettoDemand = QDmalmProgettoDemand.dmalmProgettoDemand;

	public static List<DmalmProgettoDemand> getAllProgettoDemand(
			Timestamp dataEsecuzione) throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmProgettoDemand bean = null;
		List<DmalmProgettoDemand> progetti = new ArrayList<DmalmProgettoDemand>(
				QueryUtils.getCountByWIType(Workitem_Type.EnumWorkitemType.rqd));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_PROGETTO_DEMAND);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmProgettoDemand();

				bean.setCdProgettoDemand(rs.getString("CD_PROGETTO_DEMAND"));
				bean.setDescrizioneProgettoDemand(rs
						.getString("DESCRIZIONE_PROGETTO_DEM"));
				bean.setDmalmProgettoDemandPk(rs
						.getInt("DMALM_PROGETTO_DEMAND_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDsAutoreProgettoDemand(rs
						.getString("NOME_AUTORE_PROGETTO_DEMAND"));
				bean.setDtCaricamentoProgettoDemand(dataEsecuzione);
				bean.setDtChiusuraProgettoDemand(rs
						.getTimestamp("DATA_CHIUSURA_PROGETTO_DEMAND"));
				bean.setDtCreazioneProgettoDemand(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaProgettoDemand(rs
						.getTimestamp("DATA_MODIFICA_PROGETTO_DEMAND"));
				bean.setIdAutoreProgettoDemand(rs
						.getString("ID_AUTORE_PROGETTO_DEMAND"));
				bean.setDtRisoluzioneProgettoDemand(rs
						.getTimestamp("DATA_RISOLUZIONE_PROGETTO_DEM"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setDtScadenzaProgettoDemand(rs
						.getTimestamp("DATA_SCADENZA_PROGETTO_DEMAND"));
				bean.setMotivoRisoluzioneProgDem(rs
						.getString("MOTIVO_RISOLUZIONE_PROGETTO_D"));
				bean.setTempoTotaleRisoluzione(rs
						.getInt("TEMPO_TOTALE_RISOLUZIONE")
						- rs.getInt("GIORNI_FESTIVI"));
				bean.setTitoloProgettoDemand(rs
						.getString("TITOLO_PROGETTO_DEMAND"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setCfOwnerDemand(rs.getString("OWNER_DEMAND"));
				bean.setCfDtEnunciazione(rs.getTimestamp("DATA_ENUNCIAZIONE"));
				bean.setCfDtDisponibilitaEff(rs
						.getTimestamp("DATA_DISPONIBILITA_EFFETTIVA"));
				bean.setCfDtValidazione(rs
						.getTimestamp("DATA_VALIDAZIONE_PREVISTA"));
				bean.setCfDtDisponibilita(rs
						.getTimestamp("DATA_DISPONIBILITA_PIANIFICATA"));
				bean.setCfReferenteEsercizio(rs
						.getString("REFERENTE_DIREZIONE_ESERCIZIO"));
				bean.setCfReferenteSviluppo(rs
						.getString("REFERENTE_DIREZIONE_SVILUPPO"));
				bean.setAoid(rs.getString("ORACLE_APPLICATION_ID"));
				bean.setFornitura(rs.getString("CLASSE_DI_FORNITURA"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_PROGETTO_DEMAND_PK"));
				bean.setCodObiettivoAziendale(rs
						.getString("CODICE_OBIETTIVO_AZIENDALE"));
				bean.setCodObiettivoUtente(rs
						.getString("CODICE_OBIETTIVO_UTENTE"));
				bean.setCfClassificazione(rs.getString("CLASSIFICAZIONE"));
				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));

				progetti.add(bean);
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

		return progetti;
	}

	public static List<Tuple> getProgettoDemand(DmalmProgettoDemand progetto)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> progetti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			progetti = query
					.from(progettoDemand)
					.where(progettoDemand.cdProgettoDemand
							.equalsIgnoreCase(progetto.getCdProgettoDemand()))
					.where(progettoDemand.idRepository
							.equalsIgnoreCase(progetto.getIdRepository()))
					.where(progettoDemand.rankStatoProgettoDemand
							.eq(new Double(1))).list(progettoDemand.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return progetti;

	}

	public static void insertProgettoDemand(DmalmProgettoDemand progetto)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, progettoDemand)
					.columns(progettoDemand.cdProgettoDemand,
							progettoDemand.descrizioneProgettoDemand,
							progettoDemand.dmalmProgettoDemandPk,
							progettoDemand.dmalmProjectFk02,
							progettoDemand.dmalmStatoWorkitemFk03,
							progettoDemand.dmalmStrutturaOrgFk01,
							progettoDemand.dmalmTempoFk04,
							progettoDemand.dsAutoreProgettoDemand,
							progettoDemand.dtCambioStatoProgettoDem,
							progettoDemand.dtCaricamentoProgettoDemand,
							progettoDemand.dtCreazioneProgettoDemand,
							progettoDemand.dtModificaProgettoDemand,
							progettoDemand.dtRisoluzioneProgettoDemand,
							progettoDemand.dtScadenzaProgettoDemand,
							progettoDemand.dtStoricizzazione,
							progettoDemand.idAutoreProgettoDemand,
							progettoDemand.idRepository,
							progettoDemand.motivoRisoluzioneProgDem,
							progettoDemand.rankStatoProgettoDemand,
							progettoDemand.tempoTotaleRisoluzione,
							progettoDemand.titoloProgettoDemand,
							progettoDemand.dtChiusuraProgettoDemand,
							progettoDemand.codice,
							progettoDemand.cfOwnerDemand,
							progettoDemand.cfDtEnunciazione,
							progettoDemand.cfDtDisponibilitaEff,
							progettoDemand.cfDtDisponibilita,
							progettoDemand.cfReferenteSviluppo,
							progettoDemand.cfReferenteEsercizio,
							progettoDemand.aoid, progettoDemand.fornitura,
							progettoDemand.stgPk, progettoDemand.dmalmUserFk06,
							progettoDemand.uri,
							progettoDemand.codObiettivoAziendale,
							progettoDemand.codObiettivoUtente,
							progettoDemand.cfClassificazione,
							progettoDemand.dtAnnullamento,
							progettoDemand.severity,
							progettoDemand.priority)
					.values(progetto.getCdProgettoDemand(),
							progetto.getDescrizioneProgettoDemand(),
							progetto.getDmalmProgettoDemandPk(),
							progetto.getDmalmProjectFk02(),
							progetto.getDmalmStatoWorkitemFk03(),
							progetto.getDmalmStrutturaOrgFk01(),
							progetto.getDmalmTempoFk04(),
							progetto.getDsAutoreProgettoDemand(),
							progetto.getDtCambioStatoProgettoDem(),
							progetto.getDtCaricamentoProgettoDemand(),
							progetto.getDtCreazioneProgettoDemand(),
							progetto.getDtModificaProgettoDemand(),
							progetto.getDtRisoluzioneProgettoDemand(),
							progetto.getDtScadenzaProgettoDemand(),
							progetto.getDtModificaProgettoDemand(),
							progetto.getIdAutoreProgettoDemand(),
							progetto.getIdRepository(),
							progetto.getMotivoRisoluzioneProgDem(),
							new Double(1),
							progetto.getTempoTotaleRisoluzione(),
							progetto.getTitoloProgettoDemand(),
							progetto.getDtChiusuraProgettoDemand(),
							progetto.getCodice(), progetto.getCfOwnerDemand(),
							progetto.getCfDtEnunciazione(),
							progetto.getCfDtDisponibilitaEff(),
							progetto.getCfDtDisponibilita(),
							progetto.getCfReferenteSviluppo(),
							progetto.getCfReferenteEsercizio(),
							progetto.getAoid(), progetto.getFornitura(),
							progetto.getStgPk(), progetto.getDmalmUserFk06(),
							progetto.getUri(),
							progetto.getCodObiettivoAziendale(),
							progetto.getCodObiettivoUtente(),
							progetto.getCfClassificazione(),
							progetto.getDtAnnullamento(),
							//DM_ALM-320
							progetto.getSeverity(),
							progetto.getPriority()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmProgettoDemand progetto, Double double1)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progettoDemand)
					.where(progettoDemand.cdProgettoDemand
							.equalsIgnoreCase(progetto.getCdProgettoDemand()))
					.where(progettoDemand.idRepository
							.equalsIgnoreCase(progetto.getIdRepository()))
					.set(progettoDemand.rankStatoProgettoDemand, double1)
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insertProgettoDemandUpdate(Timestamp dataEsecuzione,
			DmalmProgettoDemand progetto, boolean pkValue) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, progettoDemand)
					.columns(progettoDemand.cdProgettoDemand,
							progettoDemand.descrizioneProgettoDemand,
							progettoDemand.dmalmProgettoDemandPk,
							progettoDemand.dmalmProjectFk02,
							progettoDemand.dmalmStatoWorkitemFk03,
							progettoDemand.dmalmStrutturaOrgFk01,
							progettoDemand.dmalmTempoFk04,
							progettoDemand.dsAutoreProgettoDemand,
							progettoDemand.dtCambioStatoProgettoDem,
							progettoDemand.dtCaricamentoProgettoDemand,
							progettoDemand.dtCreazioneProgettoDemand,
							progettoDemand.dtModificaProgettoDemand,
							progettoDemand.dtRisoluzioneProgettoDemand,
							progettoDemand.dtScadenzaProgettoDemand,
							progettoDemand.dtStoricizzazione,
							progettoDemand.idAutoreProgettoDemand,
							progettoDemand.idRepository,
							progettoDemand.motivoRisoluzioneProgDem,
							progettoDemand.rankStatoProgettoDemand,
							progettoDemand.tempoTotaleRisoluzione,
							progettoDemand.titoloProgettoDemand,
							progettoDemand.dtChiusuraProgettoDemand,
							progettoDemand.codice,
							progettoDemand.cfOwnerDemand,
							progettoDemand.cfDtEnunciazione,
							progettoDemand.cfDtDisponibilitaEff,
							progettoDemand.cfDtDisponibilita,
							progettoDemand.cfReferenteSviluppo,
							progettoDemand.cfReferenteEsercizio,
							progettoDemand.aoid, progettoDemand.fornitura,
							progettoDemand.stgPk, progettoDemand.dmalmUserFk06,
							progettoDemand.uri,
							progettoDemand.codObiettivoAziendale,
							progettoDemand.codObiettivoUtente,
							progettoDemand.cfClassificazione,
							progettoDemand.dtAnnullamento,
							progettoDemand.changed, progettoDemand.annullato,
							progettoDemand.severity, progettoDemand.priority)
					.values(progetto.getCdProgettoDemand(),
							progetto.getDescrizioneProgettoDemand(),
							pkValue == true ? progetto
									.getDmalmProgettoDemandPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							progetto.getDmalmProjectFk02(),
							progetto.getDmalmStatoWorkitemFk03(),
							progetto.getDmalmStrutturaOrgFk01(),
							progetto.getDmalmTempoFk04(),
							progetto.getDsAutoreProgettoDemand(),
							progetto.getDtCambioStatoProgettoDem(),
							progetto.getDtCaricamentoProgettoDemand(),
							progetto.getDtCreazioneProgettoDemand(),
							progetto.getDtModificaProgettoDemand(),
							progetto.getDtRisoluzioneProgettoDemand(),
							progetto.getDtScadenzaProgettoDemand(),
							pkValue == true ? progetto
									.getDtModificaProgettoDemand() : progetto
									.getDtStoricizzazione(),
							progetto.getIdAutoreProgettoDemand(),
							progetto.getIdRepository(),
							progetto.getMotivoRisoluzioneProgDem(),
							pkValue == true ? new Short("1")  : progetto.getRankStatoProgettoDemand(),
							progetto.getTempoTotaleRisoluzione(),
							progetto.getTitoloProgettoDemand(),
							progetto.getDtChiusuraProgettoDemand(),
							progetto.getCodice(), progetto.getCfOwnerDemand(),
							progetto.getCfDtEnunciazione(),
							progetto.getCfDtDisponibilitaEff(),
							progetto.getCfDtDisponibilita(),
							progetto.getCfReferenteSviluppo(),
							progetto.getCfReferenteEsercizio(),
							progetto.getAoid(), progetto.getFornitura(),
							progetto.getStgPk(), progetto.getDmalmUserFk06(),
							progetto.getUri(),
							progetto.getCodObiettivoAziendale(),
							progetto.getCodObiettivoUtente(),
							progetto.getCfClassificazione(),
							progetto.getDtAnnullamento(),
							progetto.getChanged(), progetto.getAnnullato(),
							progetto.getSeverity(), progetto.getPriority())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateProgettoDemand(DmalmProgettoDemand progetto)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, progettoDemand)
					.where(progettoDemand.cdProgettoDemand
							.equalsIgnoreCase(progetto.getCdProgettoDemand()))
					.where(progettoDemand.idRepository
							.equalsIgnoreCase(progetto.getIdRepository()))
					.where(progettoDemand.rankStatoProgettoDemand
							.eq(new Double(1)))
					.set(progettoDemand.cdProgettoDemand,
							progetto.getCdProgettoDemand())
					.set(progettoDemand.descrizioneProgettoDemand,
							progetto.getDescrizioneProgettoDemand())
					.set(progettoDemand.dmalmProjectFk02,
							progetto.getDmalmProjectFk02())
					.set(progettoDemand.dmalmStatoWorkitemFk03,
							progetto.getDmalmStatoWorkitemFk03())
					.set(progettoDemand.dmalmStrutturaOrgFk01,
							progetto.getDmalmStrutturaOrgFk01())
					.set(progettoDemand.dmalmTempoFk04,
							progetto.getDmalmTempoFk04())
					.set(progettoDemand.dsAutoreProgettoDemand,
							progetto.getDsAutoreProgettoDemand())
					.set(progettoDemand.dtCreazioneProgettoDemand,
							progetto.getDtCreazioneProgettoDemand())
					.set(progettoDemand.dtModificaProgettoDemand,
							progetto.getDtModificaProgettoDemand())
					.set(progettoDemand.dtRisoluzioneProgettoDemand,
							progetto.getDtRisoluzioneProgettoDemand())
					.set(progettoDemand.dtScadenzaProgettoDemand,
							progetto.getDtScadenzaProgettoDemand())
					.set(progettoDemand.idAutoreProgettoDemand,
							progetto.getIdAutoreProgettoDemand())
					.set(progettoDemand.idRepository,
							progetto.getIdRepository())
					.set(progettoDemand.motivoRisoluzioneProgDem,
							progetto.getMotivoRisoluzioneProgDem())
					.set(progettoDemand.titoloProgettoDemand,
							progetto.getTitoloProgettoDemand())
					.set(progettoDemand.tempoTotaleRisoluzione,
							progetto.getTempoTotaleRisoluzione())
					.set(progettoDemand.dtChiusuraProgettoDemand,
							progetto.getDtChiusuraProgettoDemand())
					.set(progettoDemand.codice, progetto.getCodice())
					.set(progettoDemand.cfOwnerDemand,
							progetto.getCfOwnerDemand())
					.set(progettoDemand.cfDtEnunciazione,
							progetto.getCfDtEnunciazione())
					.set(progettoDemand.cfDtDisponibilitaEff,
							progetto.getCfDtDisponibilitaEff())
					.set(progettoDemand.cfDtDisponibilita,
							progetto.getCfDtDisponibilita())
					.set(progettoDemand.cfReferenteSviluppo,
							progetto.getCfReferenteSviluppo())
					.set(progettoDemand.cfReferenteEsercizio,
							progetto.getCfReferenteEsercizio())
					.set(progettoDemand.aoid, progetto.getAoid())
					.set(progettoDemand.fornitura, progetto.getFornitura())
					.set(progettoDemand.stgPk, progetto.getStgPk())
					.set(progettoDemand.uri, progetto.getUri())
					.set(progettoDemand.codObiettivoAziendale,
							progetto.getCodObiettivoAziendale())
					.set(progettoDemand.codObiettivoUtente,
							progetto.getCodObiettivoUtente())
					.set(progettoDemand.cfClassificazione,
							progetto.getCfClassificazione())
					.set(progettoDemand.dtAnnullamento,
							progetto.getDtAnnullamento())
					.set(progettoDemand.annullato, progetto.getAnnullato())
					//DM_ALM-320
					.set(progettoDemand.severity, progetto.getSeverity())
					.set(progettoDemand.priority, progetto.getPriority()).execute();
					

			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static List<DmalmProgettoDemand> getProgettoDemandClassificazioneSviluppoNoFiliera(
			Timestamp dataInizioFiliera) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		QDmalmFilieraProduttiva filieraProduttiva = QDmalmFilieraProduttiva.dmalmFilieraProduttiva;
		List<DmalmProgettoDemand> progetti = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			progetti = query
					.from(progettoDemand)
					.leftJoin(filieraProduttiva)
					.on(filieraProduttiva.codiceWiFiglio.eq(
							progettoDemand.cdProgettoDemand).and(
							filieraProduttiva.idRepositoryFiglio
									.eq(progettoDemand.idRepository)))
					.where(progettoDemand.rankStatoProgettoDemand
							.eq(new Double(1)))
					.where(progettoDemand.annullato.isNull())
					.where(progettoDemand.cfClassificazione
							.equalsIgnoreCase("svi"))
					.where(progettoDemand.dtCreazioneProgettoDemand
							.goe(dataInizioFiliera))
					.where(filieraProduttiva.idFiliera.isNull())
					.where(filieraProduttiva.tipoWiFiglio.eq("rqd"))
					.orderBy(progettoDemand.idRepository.asc())
					.orderBy(progettoDemand.cdProgettoDemand.asc())
					.distinct()
					.list(Projections.bean(DmalmProgettoDemand.class,
							progettoDemand.cdProgettoDemand,
							progettoDemand.idRepository));
		} catch (Exception e) {
			throw new DAOException(e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return progetti;
	}

	public static DmalmProgettoDemand getProgettoDemand(Integer pk)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> progetti = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			progetti = query.from(progettoDemand)
					.where(progettoDemand.dmalmProgettoDemandPk.eq(pk))
					.list(progettoDemand.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (progetti.size() > 0) {
			Tuple t = progetti.get(0);
			DmalmProgettoDemand p = new DmalmProgettoDemand();

			p.setAnnullato(t.get(progettoDemand.annullato));
			p.setAoid(t.get(progettoDemand.aoid));
			p.setCdProgettoDemand(t.get(progettoDemand.cdProgettoDemand));
			p.setCfClassificazione(t.get(progettoDemand.cfClassificazione));
			p.setCfDtDisponibilita(t.get(progettoDemand.cfDtDisponibilita));
			p.setCfDtDisponibilitaEff(t
					.get(progettoDemand.cfDtDisponibilitaEff));
			p.setCfDtEnunciazione(t.get(progettoDemand.cfDtEnunciazione));
			p.setCfDtValidazione(t.get(progettoDemand.cfDtValidazione));
			p.setCfOwnerDemand(t.get(progettoDemand.cfOwnerDemand));
			p.setCfReferenteEsercizio(t
					.get(progettoDemand.cfReferenteEsercizio));
			p.setCfReferenteSviluppo(t.get(progettoDemand.cfReferenteSviluppo));
			p.setChanged(t.get(progettoDemand.changed));
			p.setCodice(t.get(progettoDemand.codice));
			p.setCodObiettivoAziendale(t
					.get(progettoDemand.codObiettivoAziendale));
			p.setCodObiettivoUtente(t.get(progettoDemand.codObiettivoUtente));
			p.setDescrizioneProgettoDemand(t
					.get(progettoDemand.descrizioneProgettoDemand));
			p.setDmalmProgettoDemandPk(t
					.get(progettoDemand.dmalmProgettoDemandPk));
			p.setDmalmProjectFk02(t.get(progettoDemand.dmalmProjectFk02));
			p.setDmalmStatoWorkitemFk03(t
					.get(progettoDemand.dmalmStatoWorkitemFk03));
			p.setDmalmStrutturaOrgFk01(t
					.get(progettoDemand.dmalmStrutturaOrgFk01));
			p.setDmalmTempoFk04(t.get(progettoDemand.dmalmTempoFk04));
			p.setDmalmUserFk06(t.get(progettoDemand.dmalmUserFk06));
			p.setDsAutoreProgettoDemand(t
					.get(progettoDemand.dsAutoreProgettoDemand));
			p.setDtAnnullamento(t.get(progettoDemand.dtAnnullamento));
			p.setDtCambioStatoProgettoDem(t
					.get(progettoDemand.dtCambioStatoProgettoDem));
			p.setDtCaricamentoProgettoDemand(t
					.get(progettoDemand.dtCaricamentoProgettoDemand));
			p.setDtChiusuraProgettoDemand(t
					.get(progettoDemand.dtChiusuraProgettoDemand));
			p.setDtCreazioneProgettoDemand(t
					.get(progettoDemand.dtCreazioneProgettoDemand));
			p.setDtModificaProgettoDemand(t
					.get(progettoDemand.dtModificaProgettoDemand));
			p.setDtRisoluzioneProgettoDemand(t
					.get(progettoDemand.dtRisoluzioneProgettoDemand));
			p.setDtScadenzaProgettoDemand(t
					.get(progettoDemand.dtScadenzaProgettoDemand));
			p.setDtStoricizzazione(t.get(progettoDemand.dtStoricizzazione));
			p.setFornitura(t.get(progettoDemand.fornitura));
			p.setIdAutoreProgettoDemand(t
					.get(progettoDemand.idAutoreProgettoDemand));
			p.setIdRepository(t.get(progettoDemand.idRepository));
			p.setMotivoRisoluzioneProgDem(t
					.get(progettoDemand.motivoRisoluzioneProgDem));
			p.setRankStatoProgettoDemand(t
					.get(progettoDemand.rankStatoProgettoDemand));
			p.setRankStatoProgettoDemMese(t
					.get(progettoDemand.rankStatoProgettoDemMese));
			p.setStgPk(t.get(progettoDemand.stgPk));
			p.setTempoTotaleRisoluzione(t
					.get(progettoDemand.tempoTotaleRisoluzione));
			p.setTitoloProgettoDemand(t
					.get(progettoDemand.titoloProgettoDemand));
			p.setUri(t.get(progettoDemand.uri));
			//DM_ALM-320
			p.setSeverity(t.get(progettoDemand.severity));
			p.setPriority(t.get(progettoDemand.priority));

			return p;

		} else
			return null;
	}

	public static boolean checkEsistenzaProgetto(DmalmProgettoDemand d,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(progettoDemand)
					.where(progettoDemand.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(progettoDemand.cdProgettoDemand.eq(d.getCdProgettoDemand()))
					.where(progettoDemand.idRepository.eq(d.getIdRepository()))
					.list(progettoDemand.all());

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
