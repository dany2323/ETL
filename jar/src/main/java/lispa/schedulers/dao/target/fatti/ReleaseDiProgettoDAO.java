package lispa.schedulers.dao.target.fatti;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_RELEASE_DI_PROGETTO;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SQL_RELEASE_DI_PROGETTO_NOT_IN_TARGET;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseDiProgetto;
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

public class ReleaseDiProgettoDAO {

	private static Logger logger = Logger.getLogger(TaskDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmReleaseDiProgetto rls = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;

	public static List<DmalmReleaseDiProgetto> getAllReleaseDiProgetto(
			Timestamp dataEsecuzione) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmReleaseDiProgetto bean = null;
		List<DmalmReleaseDiProgetto> releases = new ArrayList<DmalmReleaseDiProgetto>(
				QueryUtils.getCountByWIType(Workitem_Type.release));

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					SQL_RELEASE_DI_PROGETTO);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);

			ps.setTimestamp(1, dataEsecuzione);
			ps.setTimestamp(2, dataEsecuzione);

			rs = ps.executeQuery();

			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmReleaseDiProgetto();

				bean.setCdReleasediprog(rs.getString("CD_REL_DI_PROG"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setDataDisponibilitaEff(rs
						.getTimestamp("DATA_DISPONIBILITA_EFFETTIVA"));
				bean.setDataPassaggioInEsercizio(rs
						.getTimestamp("DATA_PASSAGGIO_IN_ESERC"));
				bean.setDescrizioneReleasediprog(rs
						.getString("DESCRIZIONE_REL_DI_PROG"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDmalmReleasediprogPk(rs.getInt("DMALM_REL_DI_PROG_PK"));
				bean.setDsAutoreReleasediprog(rs
						.getString("NOME_AUTORE_REL_DI_PROG"));
				bean.setDtCaricamentoReleasediprog(dataEsecuzione);
				bean.setDtCreazioneReleasediprog(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaReleasediprog(rs
						.getTimestamp("DATA_MODIFICA_REL_DI_PROG"));
				bean.setDtRisoluzioneReleasediprog(rs
						.getTimestamp("DATA_RISOLUZIONE_REL_DI_PROG"));
				bean.setDtScadenzaReleasediprog(rs
						.getTimestamp("DATA_SCADENZA_REL_DI_PROG"));
				bean.setFornitura(rs.getString("CLASSE_DI_FORNITURA"));
				bean.setFr(rs.getBoolean("FIRST_RELEASE"));
				bean.setIdAutoreReleasediprog(rs
						.getString("ID_AUTORE_REL_DI_PROG"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzioneRelProg(rs
						.getString("MOTIVO_RISOL_REL_DI_PROG"));
				bean.setNumeroLinea(StringUtils.getLinea(rs
						.getString("CODICE_INTERVENTO")));
				bean.setNumeroTestata(StringUtils.getTestata(rs
						.getString("CODICE_INTERVENTO")));
				bean.setTitoloReleasediprog(rs.getString("TITOLO_REL_DI_PROG"));
				bean.setVersione(rs.getString("VERSIONE"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_REL_DI_PROG_PK"));

				String registry = rs.getString("REGISTRY");
				Timestamp inizio = null;
				Timestamp fine = null;
				Integer qf = null;
				if (registry != null) {
					try {
						String[] regToSplit = registry.split(",");

						if (regToSplit.length > 0) {
							inizio = DateUtils.stringToTimestamp(regToSplit[0], "yyyy-MM-dd HH:mm");
							if (StringUtils.hasText(regToSplit[1])) {
								fine = DateUtils
										.stringToTimestamp(regToSplit[1], "yyyy-MM-dd HH:mm");
							}
							qf = Integer.parseInt(regToSplit[2]);
						}
					} catch (Exception e) {
						logger.error(
								"Registry non valorizzato correttamente - CdRelease: "
										+ bean.getCdReleasediprog()
										+ ", registry: " + registry, e);
					}
				}
				bean.setDtInizioQF(inizio);
				bean.setDtFineQF(fine);
				bean.setNumQuickFix(qf);

				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));
				bean.setTypeRelease(rs.getString("TYPE_RELEASE"));
				
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
	
	public static List<DmalmReleaseDiProgetto> getAllReleaseDiProgettoNotInTarget(
			Timestamp dataEsecuzione) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmReleaseDiProgetto bean = null;
		List<DmalmReleaseDiProgetto> releases = new ArrayList<DmalmReleaseDiProgetto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					SQL_RELEASE_DI_PROGETTO_NOT_IN_TARGET);
			ps = connection.prepareStatement(sql);

			ps.setFetchSize(200);
			
			rs = ps.executeQuery();

			//logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmReleaseDiProgetto();

				bean.setCdReleasediprog(rs.getString("CD_REL_DI_PROG"));
				bean.setCodice(rs.getString("CODICE"));
				bean.setDataDisponibilitaEff(rs
						.getTimestamp("DATA_DISPONIBILITA_EFFETTIVA"));
				bean.setDataPassaggioInEsercizio(rs
						.getTimestamp("DATA_PASSAGGIO_IN_ESERC"));
				bean.setDescrizioneReleasediprog(rs
						.getString("DESCRIZIONE_REL_DI_PROG"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs
						.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setDmalmReleasediprogPk(rs.getInt("DMALM_REL_DI_PROG_PK"));
				bean.setDsAutoreReleasediprog(rs
						.getString("NOME_AUTORE_REL_DI_PROG"));
				bean.setDtCaricamentoReleasediprog(dataEsecuzione);
				bean.setDtCreazioneReleasediprog(rs
						.getTimestamp("DATA_INSERIMENTO_RECORD"));
				bean.setDtModificaReleasediprog(rs
						.getTimestamp("DATA_MODIFICA_REL_DI_PROG"));
				bean.setDtRisoluzioneReleasediprog(rs
						.getTimestamp("DATA_RISOLUZIONE_REL_DI_PROG"));
				bean.setDtScadenzaReleasediprog(rs
						.getTimestamp("DATA_SCADENZA_REL_DI_PROG"));
				bean.setFornitura(rs.getString("CLASSE_DI_FORNITURA"));
				bean.setFr(rs.getBoolean("FIRST_RELEASE"));
				bean.setIdAutoreReleasediprog(rs
						.getString("ID_AUTORE_REL_DI_PROG"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setMotivoRisoluzioneRelProg(rs
						.getString("MOTIVO_RISOL_REL_DI_PROG"));
				bean.setNumeroLinea(StringUtils.getLinea(rs
						.getString("CODICE_INTERVENTO")));
				bean.setNumeroTestata(StringUtils.getTestata(rs
						.getString("CODICE_INTERVENTO")));
				bean.setTitoloReleasediprog(rs.getString("TITOLO_REL_DI_PROG"));
				bean.setVersione(rs.getString("VERSIONE"));
				bean.setUri(rs.getString("URI_WI"));
				bean.setStgPk(rs.getString("STG_REL_DI_PROG_PK"));

				String registry = rs.getString("REGISTRY");
				Timestamp inizio = null;
				Timestamp fine = null;
				Integer qf = null;
				if (registry != null) {
					try {
						String[] regToSplit = registry.split(",");

						if (regToSplit.length > 0) {
							inizio = DateUtils.stringToTimestamp(regToSplit[0], "yyyy-MM-dd HH:mm");
							if (StringUtils.hasText(regToSplit[1])) {
								fine = DateUtils
										.stringToTimestamp(regToSplit[1], "yyyy-MM-dd HH:mm");
							}
							qf = Integer.parseInt(regToSplit[2]);
						}
					} catch (Exception e) {
						logger.error(
								"Registry non valorizzato correttamente - CdRelease: "
										+ bean.getCdReleasediprog()
										+ ", registry: " + registry, e);
					}
				}
				bean.setDtInizioQF(inizio);
				bean.setDtFineQF(fine);
				bean.setNumQuickFix(qf);

				//DM_ALM-320
				bean.setSeverity(rs.getString("SEVERITY"));
				bean.setPriority(rs.getString("PRIORITY"));
				bean.setTypeRelease(rs.getString("TYPE_RELEASE"));
				
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

	public static List<Tuple> getReleaseDiProgetto(
			DmalmReleaseDiProgetto release) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> releases = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			releases = query
					.from(rls)
					.where(rls.cdReleasediprog.equalsIgnoreCase(release
							.getCdReleasediprog()))
					.where(rls.idRepository.equalsIgnoreCase(release
							.getIdRepository()))
					.where(rls.rankStatoReleasediprog.eq(new Double(1)))
					.list(rls.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return releases;

	}
	
	public static List<Tuple> getReleaseDiProgettoFromStagingPK(
			DmalmReleaseDiProgetto release) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> releases = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			releases = query
					.from(rls)
					.where(rls.stgPk.equalsIgnoreCase(release
							.getStgPk()))
					.list(rls.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return releases;

	}

	public static void insertReleaseDiProgetto(DmalmReleaseDiProgetto release)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, rls)
					.columns(rls.cdReleasediprog, rls.codice,
							rls.dataDisponibilitaEff,
							rls.dataPassaggioInEsercizio,
							rls.descrizioneReleasediprog, rls.dmalmProjectFk02,
							rls.dmalmReleasediprogPk,
							rls.dmalmStatoWorkitemFk03,
							rls.dmalmStrutturaOrgFk01, rls.dmalmTempoFk04,
							rls.dsAutoreReleasediprog,
							rls.dtCambioStatoReleasediprog,
							rls.dtCaricamentoReleasediprog,
							rls.dtCreazioneReleasediprog,
							rls.dtModificaReleasediprog,
							rls.dtRisoluzioneReleasediprog,
							rls.dtScadenzaReleasediprog, rls.dtStoricizzazione,
							rls.fornitura, rls.fr, rls.idAutoreReleasediprog,
							rls.idRepository, rls.motivoRisoluzioneRelProg,
							rls.numeroLinea, rls.numeroTestata,
							rls.rankStatoReleasediprog,
							rls.titoloReleasediprog, rls.versione, rls.stgPk,
							rls.dmalmAreaTematicaFk05, rls.dmalmUserFk06,
							rls.uri, rls.dtAnnullamento, rls.dtInizioQF,
							rls.dtFineQF, rls.numQuickFix,
							rls.severity, rls.priority, rls.typeRelease)
					.values(release.getCdReleasediprog(), release.getCodice(),
							release.getDataDisponibilitaEff(),
							release.getDataPassaggioInEsercizio(),
							release.getDescrizioneReleasediprog(),
							release.getDmalmProjectFk02(),
							release.getDmalmReleasediprogPk(),
							release.getDmalmStatoWorkitemFk03(),
							release.getDmalmStrutturaOrgFk01(),
							release.getDmalmTempoFk04(),
							release.getDsAutoreReleasediprog(),
							release.getDtCambioStatoReleasediprog(),
							release.getDtCaricamentoReleasediprog(),
							release.getDtCreazioneReleasediprog(),
							release.getDtModificaReleasediprog(),
							release.getDtRisoluzioneReleasediprog(),
							release.getDtScadenzaReleasediprog(),
							release.getDtModificaReleasediprog(),
							release.getFornitura(), release.getFr(),
							release.getIdAutoreReleasediprog(),
							release.getIdRepository(),
							release.getMotivoRisoluzioneRelProg(),
							release.getNumeroLinea(),
							release.getNumeroTestata(), new Double(1),
							release.getTitoloReleasediprog(),
							release.getVersione(), release.getStgPk(),
							release.getDmalmAreaTematicaFk05(),
							release.getDmalmUserFk06(), release.getUri(),
							release.getDtAnnullamento(),
							release.getDtInizioQF(), release.getDtFineQF(),
							release.getNumQuickFix(),
							//DM_ALM-320
							release.getSeverity(), release.getPriority(),
							release.getTypeRelease()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateRank(DmalmReleaseDiProgetto release, Double double1)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, rls)
					.where(rls.cdReleasediprog.equalsIgnoreCase(release
							.getCdReleasediprog()))
					.where(rls.idRepository.equalsIgnoreCase(release
							.getIdRepository()))
					.set(rls.rankStatoReleasediprog, double1).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertReleaseDiProgettoUpdate(Timestamp dataEsecuzione,
			DmalmReleaseDiProgetto release, boolean pkValue)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLInsertClause(connection, dialect, rls)
					.columns(rls.cdReleasediprog, rls.codice,
							rls.dataDisponibilitaEff,
							rls.dataPassaggioInEsercizio,
							rls.descrizioneReleasediprog, rls.dmalmProjectFk02,
							rls.dmalmReleasediprogPk,
							rls.dmalmStatoWorkitemFk03,
							rls.dmalmStrutturaOrgFk01, rls.dmalmTempoFk04,
							rls.dsAutoreReleasediprog,
							rls.dtCambioStatoReleasediprog,
							rls.dtCaricamentoReleasediprog,
							rls.dtCreazioneReleasediprog,
							rls.dtModificaReleasediprog,
							rls.dtRisoluzioneReleasediprog,
							rls.dtScadenzaReleasediprog, rls.dtStoricizzazione,
							rls.fornitura, rls.fr, rls.idAutoreReleasediprog,
							rls.idRepository, rls.motivoRisoluzioneRelProg,
							rls.numeroLinea, rls.numeroTestata,
							rls.rankStatoReleasediprog,
							rls.titoloReleasediprog, rls.versione, rls.stgPk,
							rls.dmalmAreaTematicaFk05, rls.dmalmUserFk06,
							rls.uri, rls.dtAnnullamento, rls.dtInizioQF,
							rls.dtFineQF, rls.numQuickFix, rls.changed,
							rls.annullato,
							rls.severity, rls.priority, rls.typeRelease)
					.values(release.getCdReleasediprog(),
							release.getCodice(),
							release.getDataDisponibilitaEff(),
							release.getDataPassaggioInEsercizio(),
							release.getDescrizioneReleasediprog(),
							release.getDmalmProjectFk02(),
							pkValue == true ? release.getDmalmReleasediprogPk()
									: StringTemplate
											.create("HISTORY_WORKITEM_SEQ.nextval"),
							release.getDmalmStatoWorkitemFk03(),
							release.getDmalmStrutturaOrgFk01(),
							release.getDmalmTempoFk04(),
							release.getDsAutoreReleasediprog(),
							release.getDtCambioStatoReleasediprog(),
							release.getDtCaricamentoReleasediprog(),
							release.getDtCreazioneReleasediprog(),
							release.getDtModificaReleasediprog(),
							release.getDtRisoluzioneReleasediprog(),
							release.getDtScadenzaReleasediprog(),
							pkValue == true ? release
									.getDtModificaReleasediprog() : release
									.getDtStoricizzazione(),
							release.getFornitura(), release.getFr(),
							release.getIdAutoreReleasediprog(),
							release.getIdRepository(),
							release.getMotivoRisoluzioneRelProg(),
							release.getNumeroLinea(),
							release.getNumeroTestata(), 
							pkValue == true ? new Short("1")  : release.getRankStatoReleasediprog(),
							release.getTitoloReleasediprog(),
							release.getVersione(), release.getStgPk(),
							release.getDmalmAreaTematicaFk05(),
							release.getDmalmUserFk06(), release.getUri(),
							release.getDtAnnullamento(),
							release.getDtInizioQF(), release.getDtFineQF(),
							release.getNumQuickFix(), release.getChanged(),
							release.getAnnullato(),
							//DM_ALM-320
							release.getSeverity(), release.getPriority(),
							release.getTypeRelease()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void updateReleaseDiProgetto(DmalmReleaseDiProgetto release)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, rls)

					.where(rls.cdReleasediprog.equalsIgnoreCase(release
							.getCdReleasediprog()))
					.where(rls.idRepository.equalsIgnoreCase(release
							.getIdRepository()))
					.where(rls.rankStatoReleasediprog.eq(new Double(1)))
					.set(rls.cdReleasediprog, release.getCdReleasediprog())
					.set(rls.codice, release.getCodice())
					.set(rls.dataDisponibilitaEff,
							release.getDataDisponibilitaEff())
					.set(rls.dataPassaggioInEsercizio,
							release.getDataPassaggioInEsercizio())
					.set(rls.descrizioneReleasediprog,
							release.getDescrizioneReleasediprog())
					.set(rls.dmalmProjectFk02, release.getDmalmProjectFk02())
					.set(rls.dmalmStatoWorkitemFk03,
							release.getDmalmStatoWorkitemFk03())
					.set(rls.dmalmStrutturaOrgFk01,
							release.getDmalmStrutturaOrgFk01())
					.set(rls.dmalmTempoFk04, release.getDmalmTempoFk04())
					.set(rls.dmalmAreaTematicaFk05,
							release.getDmalmAreaTematicaFk05())
					.set(rls.dsAutoreReleasediprog,
							release.getDsAutoreReleasediprog())
					.set(rls.dtCreazioneReleasediprog,
							release.getDtCreazioneReleasediprog())
					.set(rls.dtModificaReleasediprog,
							release.getDtModificaReleasediprog())
					.set(rls.dtRisoluzioneReleasediprog,
							release.getDtRisoluzioneReleasediprog())
					.set(rls.dtScadenzaReleasediprog,
							release.getDtScadenzaReleasediprog())
					.set(rls.fornitura, release.getFornitura())
					.set(rls.fr, release.getFr())
					.set(rls.idAutoreReleasediprog,
							release.getIdAutoreReleasediprog())
					.set(rls.idRepository, release.getIdRepository())
					.set(rls.motivoRisoluzioneRelProg,
							release.getMotivoRisoluzioneRelProg())
					.set(rls.numeroLinea, release.getNumeroLinea())
					.set(rls.numeroTestata, release.getNumeroTestata())
					.set(rls.titoloReleasediprog,
							release.getTitoloReleasediprog())
					.set(rls.versione, release.getVersione())
					.set(rls.stgPk, release.getStgPk())
					.set(rls.uri, release.getUri())
					.set(rls.dtAnnullamento, release.getDtAnnullamento())
					.set(rls.dtInizioQF, release.getDtInizioQF())
					.set(rls.dtFineQF, release.getDtFineQF())
					.set(rls.numQuickFix, release.getNumQuickFix())
					.set(rls.annullato, release.getAnnullato())
					//DM_ALM-320
					.set(rls.severity, release.getSeverity())
					.set(rls.priority, release.getPriority())
					.set(rls.typeRelease, release.getTypeRelease()).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static DmalmReleaseDiProgetto getReleaseDiProgetto(Integer pk)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> releases = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			releases = query.from(rls).where(rls.dmalmReleasediprogPk.eq(pk))
					.list(rls.all());
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		if (releases.size() > 0) {
			Tuple t = releases.get(0);
			DmalmReleaseDiProgetto r = new DmalmReleaseDiProgetto();

			r.setAnnullato(t.get(rls.annullato));
			r.setCdReleasediprog(t.get(rls.cdReleasediprog));
			r.setChanged(t.get(rls.changed));
			r.setCodice(t.get(rls.codice));
			r.setDataDisponibilitaEff(t.get(rls.dataDisponibilitaEff));
			r.setDataPassaggioInEsercizio(t.get(rls.dataPassaggioInEsercizio));
			r.setDescrizioneReleasediprog(t.get(rls.descrizioneReleasediprog));
			r.setDmalmAreaTematicaFk05(t.get(rls.dmalmAreaTematicaFk05));
			r.setDmalmProjectFk02(t.get(rls.dmalmProjectFk02));
			r.setDmalmReleasediprogPk(t.get(rls.dmalmReleasediprogPk));
			r.setDmalmStatoWorkitemFk03(t.get(rls.dmalmStatoWorkitemFk03));
			r.setDmalmStrutturaOrgFk01(t.get(rls.dmalmStrutturaOrgFk01));
			r.setDmalmTempoFk04(t.get(rls.dmalmTempoFk04));
			r.setDmalmUserFk06(t.get(rls.dmalmUserFk06));
			r.setDsAutoreReleasediprog(t.get(rls.dsAutoreReleasediprog));
			r.setDtAnnullamento(t.get(rls.dtAnnullamento));
			r.setDtCambioStatoReleasediprog(t
					.get(rls.dtCambioStatoReleasediprog));
			r.setDtCaricamentoReleasediprog(t
					.get(rls.dtCaricamentoReleasediprog));
			r.setDtCreazioneReleasediprog(t.get(rls.dtCreazioneReleasediprog));
			r.setDtFineQF(t.get(rls.dtFineQF));
			r.setDtInizioQF(t.get(rls.dtInizioQF));
			r.setDtModificaReleasediprog(t.get(rls.dtModificaReleasediprog));
			r.setDtRisoluzioneReleasediprog(t
					.get(rls.dtRisoluzioneReleasediprog));
			r.setDtScadenzaReleasediprog(t.get(rls.dtScadenzaReleasediprog));
			r.setDtStoricizzazione(t.get(rls.dtStoricizzazione));
			r.setFornitura(t.get(rls.fornitura));
			r.setFr(t.get(rls.fr));
			r.setIdAutoreReleasediprog(t.get(rls.idAutoreReleasediprog));
			r.setIdRepository(t.get(rls.idRepository));
			r.setMotivoRisoluzioneRelProg(t.get(rls.motivoRisoluzioneRelProg));
			r.setNumeroLinea(t.get(rls.numeroLinea));
			r.setNumeroTestata(t.get(rls.numeroTestata));
			r.setNumQuickFix(t.get(rls.numQuickFix));
			r.setRankStatoReleasediprog(t.get(rls.rankStatoReleasediprog));
			r.setRankStatoReleasediprogMese(t
					.get(rls.rankStatoReleasediprogMese));
			r.setStgPk(t.get(rls.stgPk));
			r.setTitoloReleasediprog(t.get(rls.titoloReleasediprog));
			r.setUri(t.get(rls.uri));
			r.setVersione(t.get(rls.versione));
			//DM_ALM-320
			r.setSeverity(t.get(rls.severity));
			r.setPriority(t.get(rls.priority));
			r.setTypeRelease(t.get(rls.typeRelease));
			return r;

		} else
			return null;
	}

	public static boolean checkEsistenzaRelease(DmalmReleaseDiProgetto r,
			DmalmProject p) throws DAOException{
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> list = new ArrayList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			list = query
					.from(rls)
					.where(rls.dmalmProjectFk02.eq(p
							.getDmalmProjectPk()))
					.where(rls.cdReleasediprog.eq(r.getCdReleasediprog()))
					.where(rls.idRepository.eq(r.getIdRepository()))
					.list(rls.all());

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
