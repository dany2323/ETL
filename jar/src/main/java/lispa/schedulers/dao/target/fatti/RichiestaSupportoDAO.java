package lispa.schedulers.dao.target.fatti;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;
import com.mysema.query.types.template.StringTemplate;

import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaSupporto;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseDiProgetto;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleTypes;

public class RichiestaSupportoDAO {

	private static Logger logger = Logger.getLogger(RichiestaSupportoDAO.class);
	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static QDmalmReleaseDiProgetto rls = QDmalmReleaseDiProgetto.dmalmReleaseDiProgetto;

	public static List<DmalmRichiestaSupporto> getAllRichiestaSupporto(
			Timestamp dataEsecuzione) throws Exception {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		DmalmRichiestaSupporto bean = null;
		List<DmalmRichiestaSupporto> richieste = new LinkedList<DmalmRichiestaSupporto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			String sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.GET_ALL_RICHIESTA_SUPPORTO", 1);
			cs = connection.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setTimestamp(2, dataEsecuzione);
			cs.execute();
			
			//return the result set
            rs = (ResultSet)cs.getObject(1);
            
			logger.debug("Query Eseguita!");

			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmRichiestaSupporto();
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setUriRichiestaSupporto(rs.getString("URI_RICHIESTA_SUPPORTO"));
				bean.setDmalmRichiestaSupportoPk(rs.getInt("DMALM_RICH_SUPPORTO_PK"));
				bean.setStgPk(rs.getString("STG_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setCdRichiestaSupporto(rs.getString("ID_RICHIESTA_SUPPORTO"));
				bean.setDataRisoluzioneRichSupporto(rs.getTimestamp("DATA_RISOLUZIONE_RICH_SUPPORTO"));
				bean.setNrGiorniFestivi(rs.getInt("NR_GIORNI_FESTIVI"));
				bean.setTempoTotRichSupporto(rs.getInt("TEMPO_TOT_RICH_SUPPORTO"));
				bean.setDmalmStatoWorkitemFk03(rs.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDataCreazRichSupporto(rs.getTimestamp("DATA_CREAZ_RICH_SUPPORTO"));
				bean.setDataModificaRecord(rs.getTimestamp("DATA_MODIFICA_RECORD"));
				bean.setDataChiusRichSupporto(rs.getTimestamp("DATA_CHIUS_RICH_SUPPORTO"));
				bean.setUseridRichSupporto(rs.getString("USERID_RICH_SUPPORTO"));
				bean.setNomeRichSupporto(rs.getString("NOME_RICH_SUPPORTO"));
				bean.setMotivoRisoluzione(rs.getString("MOTIVO_RISOLUZIONE"));
				bean.setSeverityRichSupporto(rs.getString("SEVERITY_RICH_SUPPORTO"));
				bean.setDescrizioneRichSupporto(rs.getString("DESCRIZIONE_RICH_SUPPORTO"));
				bean.setNumeroTestataRdi(rs.getString("NUMERO_TESTATA_RDI"));
				bean.setDataDisponibilita(rs.getTimestamp("DATA_DISPONIBILITA"));
				bean.setPriorityRichSupporto(rs.getString("PRIORITY_RICH_SUPPORTO"));
				
				richieste.add(bean);
			}

			if (rs != null) {
				rs.close();
			}
			if (cs != null) {
				cs.close();
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

	public static List<DmalmRichiestaSupporto> getRichiestaSupporto(
			DmalmRichiestaSupporto richiesta) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		DmalmRichiestaSupporto bean = null;
		List<DmalmRichiestaSupporto> richieste = new LinkedList<DmalmRichiestaSupporto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			// in the type map, add the mapping of RICHSUPPTYPE SQL  
		    // type to the DmalmRichiestaSupporto custom Java type 
		    Map map = connection.getTypeMap();
		    map.put("RICHSUPPTYPE", Class.forName("DmalmRichiestaSupporto"));
	        
			String sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.GET_RICHIESTA_SUPPORTO", 1);
			cs = connection.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setObject(2, richiesta, OracleTypes.STRUCT);
			cs.execute();
			
			//return the result set
            rs = (ResultSet)cs.getObject(1);
            
			logger.debug("Query Eseguita!");
			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmRichiestaSupporto();
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setUriRichiestaSupporto(rs.getString("URI_RICHIESTA_SUPPORTO"));
				bean.setDmalmRichiestaSupportoPk(rs.getInt("DMALM_RICH_SUPPORTO_PK"));
				bean.setStgPk(rs.getString("STG_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setCdRichiestaSupporto(rs.getString("ID_RICHIESTA_SUPPORTO"));
				bean.setDataRisoluzioneRichSupporto(rs.getTimestamp("DATA_RISOLUZIONE_RICH_SUPPORTO"));
				bean.setNrGiorniFestivi(rs.getInt("NR_GIORNI_FESTIVI"));
				bean.setTempoTotRichSupporto(rs.getInt("TEMPO_TOT_RICH_SUPPORTO"));
				bean.setDmalmStatoWorkitemFk03(rs.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setDataCreazRichSupporto(rs.getTimestamp("DATA_CREAZ_RICH_SUPPORTO"));
				bean.setDataModificaRecord(rs.getTimestamp("DATA_MODIFICA_RECORD"));
				bean.setDataChiusRichSupporto(rs.getTimestamp("DATA_CHIUS_RICH_SUPPORTO"));
				bean.setUseridRichSupporto(rs.getString("USERID_RICH_SUPPORTO"));
				bean.setNomeRichSupporto(rs.getString("NOME_RICH_SUPPORTO"));
				bean.setMotivoRisoluzione(rs.getString("MOTIVO_RISOLUZIONE"));
				bean.setSeverityRichSupporto(rs.getString("SEVERITY_RICH_SUPPORTO"));
				bean.setDescrizioneRichSupporto(rs.getString("DESCRIZIONE_RICH_SUPPORTO"));
				bean.setNumeroTestataRdi(rs.getString("NUMERO_TESTATA_RDI"));
				bean.setRankStatoRichSupporto(rs.getInt("RANK_STATO_RICH_SUPPORTO"));
				bean.setDataDisponibilita(rs.getTimestamp("DATA_DISPONIBILITA"));
				bean.setPriorityRichSupporto(rs.getString("PRIORITY_RICH_SUPPORTO"));
				
				richieste.add(bean);
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return richieste;

	}

	public static void insertRichiestaSupporto(DmalmRichiestaSupporto richiesta)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			
			// in the type map, add the mapping of RICHSUPPTYPE SQL  
		    // type to the DmalmRichiestaSupporto custom Java type 
		    Map map = connection.getTypeMap();
		    map.put("RICHSUPPTYPE", Class.forName("DmalmRichiestaSupporto"));
	        
			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.INSERT_RICHIESTA_SUPPORTO", 1);
			cs = connection.prepareCall(sql);
			cs.setObject(1, richiesta, OracleTypes.STRUCT);
			cs.execute();

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
