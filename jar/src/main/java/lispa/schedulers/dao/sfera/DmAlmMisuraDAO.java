package lispa.schedulers.dao.sfera;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import lispa.schedulers.bean.target.sfera.DmalmMisura;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.dao.target.PeiOdsDAO;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmAsm;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmMisura;
import lispa.schedulers.queryimplementation.target.sfera.QDmalmProgettoSfera;

import org.apache.log4j.Logger;

import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;
import com.mysema.query.sql.dml.SQLUpdateClause;

public class DmAlmMisuraDAO {
	private static Logger logger = Logger.getLogger(PeiOdsDAO.class);

	private static QDmalmMisura misura = QDmalmMisura.dmalmMisura;

	private static QDmalmProgettoSfera prog = QDmalmProgettoSfera.dmalmProgettoSfera;

	private static QDmalmAsm asm = QDmalmAsm.dmalmAsm;
	
	private static SQLTemplates dialect = new HSQLDBTemplates();

	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLDeleteClause(connection, dialect, misura).execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static List<DmalmMisura> getAllMisure(Timestamp dataEsecuzione)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		DmalmMisura bean = null;
		List<DmalmMisura> misure = new ArrayList<DmalmMisura>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SQL_STG_MISURES);
			ps = connection.prepareStatement(sql);

			ps.setTimestamp(1, dataEsecuzione);
			rs = ps.executeQuery();
			
			logger.debug("Query Eseguita!");
			
			while (rs.next()) {
				// Elabora il risultato
				bean = new DmalmMisura();
				bean.setA1Num(rs.getDouble("A_1_NUM"));
				bean.setA1Ufp(rs.getDouble("A_1_UFP"));
				bean.setA2Num(rs.getDouble("A_2_NUM"));
				bean.setA2Ufp(rs.getDouble("A_2_UFP"));
				bean.setAdjmax(rs.getDouble("ADJMAX"));
				bean.setAdjmin(rs.getDouble("ADJMIN"));
				bean.setAdjufp(rs.getDouble("ADJUFP"));
				bean.setAmbito(rs.getString("AMBITO"));
				bean.setApproccio(rs.getString("APPROCCIO"));
				bean.setB1Num(rs.getDouble("B_1_NUM"));
				bean.setB1Ufp(rs.getDouble("B_1_UFP"));
				bean.setB2Num(rs.getDouble("B_2_NUM"));
				bean.setB2Ufp(rs.getDouble("B_2_UFP"));
				bean.setB3Num(rs.getDouble("B_3_NUM"));
				bean.setB3Ufp(rs.getDouble("B_3_UFP"));
				bean.setB5Num(rs.getDouble("B_5_NUM"));
				bean.setB5Ufp(rs.getDouble("B_5_UFP"));
				bean.setBfpNum(rs.getDouble("BFP_NUM"));
				bean.setBfpUfp(rs.getDouble("BFP_UFP"));
				bean.setC1Num(rs.getDouble("C_1_NUM"));
				bean.setC1Ufp(rs.getDouble("C_1_UFP"));
				bean.setC2Num(rs.getDouble("C_2_NUM"));
				bean.setC2Ufp(rs.getDouble("C_2_UFP"));
				bean.setConfine(rs.getString("CONFINE"));
				bean.setCrudNum(rs.getDouble("CRUD_NUM"));
				bean.setCrudUfp(rs.getDouble("CRUD_UFP"));
				bean.setD1Num(rs.getDouble("D_1_NUM"));
				bean.setD1Ufp(rs.getDouble("D_1_UFP"));
				bean.setD2Num(rs.getDouble("D_2_NUM"));
				bean.setD2Ufp(rs.getDouble("D_2_UFP"));
				bean.setDataConsolidamento(rs.getTimestamp("DT_CONSOLIDAMENTO"));
				bean.setDataCreazione(rs.getTimestamp("DT_CREAZIONE"));
				bean.setDataFineVerificafp(rs
						.getTimestamp("DT_FINE_VERIFICA_FP"));
				bean.setDataInizioVerificaFp(rs
						.getTimestamp("DT_INIZIO_VERIFICA_FP"));
				bean.setDataRiferimento(rs.getTimestamp("DT_RIFERIMENTO"));
				bean.setDmalmStgMisuraPk(rs.getInt("DMALM_STG_MISURA_PK"));
				bean.setEifNum(rs.getDouble("EI_NUM"));
				bean.setEifUfp(rs.getDouble("EI_UFP"));
				bean.setEiNum(rs.getDouble("EIF_NUM"));
				bean.setEiUfp(rs.getDouble("EIF_UFP"));
				bean.setEoNum(rs.getDouble("EO_NUM"));
				bean.setEoUfp(rs.getDouble("EO_UFP"));
				bean.setEqNum(rs.getDouble("EQ_NUM"));
				bean.setEqUfp(rs.getDouble("EQ_UFP"));
				bean.setEsperienza(rs.getString("ESPERIENZA"));
				bean.setFaseCicloDiVita(rs.getString("FASE_CICLO_DI_VITA"));
				bean.setFonti(rs.getString("FONTI"));
				bean.setFpNonPesatiMax(rs.getDouble("FP_NON_PESATI_MAX"));
				bean.setFpNonPesatiMin(rs.getDouble("FP_NON_PESATI_MIN"));
				bean.setFpNonPesatiUfp(rs.getDouble("FP_NON_PESATI_UFP"));
				bean.setFpPesatiMax(rs.getDouble("FP_PESATI_MAX"));
				bean.setFpPesatiMin(rs.getDouble("FP_PESATI_MIN"));
				bean.setFpPesatiUfp(rs.getDouble("FP_PESATI_UFP"));
				bean.setFuNum(rs.getDouble("FU_NUM"));
				bean.setFuUfp(rs.getDouble("FU_UFP"));
				bean.setGdgNum(rs.getDouble("GDG_NUM"));
				bean.setGdgUfp(rs.getDouble("GDG_UFP"));
				bean.setGeifNum(rs.getDouble("GEI_NUM"));
				bean.setGeifUfp(rs.getDouble("GEI_UFP"));
				bean.setGeiNum(rs.getDouble("GEIF_NUM"));
				bean.setGeiUfp(rs.getDouble("GEIF_UFP"));
				bean.setGeoNum(rs.getDouble("GEO_NUM"));
				bean.setGeoUfp(rs.getDouble("GEO_UFP"));
				bean.setGeqNum(rs.getDouble("GEQ_NUM"));
				bean.setGeqUfp(rs.getDouble("GEQ_UFP"));
				bean.setGilfNum(rs.getDouble("GILF_NUM"));
				bean.setGilfUfp(rs.getDouble("GILF_UFP"));
				bean.setGpNum(rs.getDouble("GP_NUM"));
				bean.setGpUfp(rs.getDouble("GP_UFP"));
				bean.setIdMsr(rs.getInt("ID_MSR"));
				bean.setIfpNum(rs.getDouble("IFP_NUM"));
				bean.setIfpUfp(rs.getDouble("IFP_UFP"));
				bean.setIlfNum(rs.getDouble("ILF_NUM"));
				bean.setIlfUfp(rs.getDouble("ILF_UFP"));
				bean.setLdgNum(rs.getDouble("LDG_NUM"));
				bean.setLdgUfp(rs.getDouble("LDG_UFP"));
				bean.setLinkDocumentale(rs.getString("LINK_DOCUMENTALE"));
				bean.setMetodo(rs.getString("METODO"));
				bean.setMfNum(rs.getDouble("MF_NUM"));
				bean.setMfUfp(rs.getDouble("MF_UFP"));
				bean.setMldgNum(rs.getDouble("MLDG_NUM"));
				bean.setMldgUfp(rs.getDouble("MLDG_UFP"));
				bean.setModello(rs.getString("MODELLO"));
				bean.setMpNum(rs.getDouble("MP_NUM"));
				bean.setMpUfp(rs.getDouble("MP_UFP"));
				bean.setNomeMisura(rs.getString("NOME_MISURA"));
				bean.setNoteMsr(rs.getString("NOTE_MSR"));
				bean.setPercentualeDiScostamento(rs
						.getDouble("PERCENTUALE_DI_SCOSTAMENTO"));
				bean.setPfNum(rs.getDouble("PF_NUM"));
				bean.setPfUfp(rs.getDouble("PF_UFP"));
				bean.setPostVerAddCfp(rs.getInt("POST_VER_ADD_CFP"));
				bean.setPostVerChg(rs.getInt("POST_VER_CHG"));
				bean.setPostVerDel(rs.getInt("POST_VER_DEL"));
				bean.setPostVerFp(rs.getInt("POST_VER_FP"));
				bean.setPreVerAddCfp(rs.getInt("PRE_VER_ADD_CFP"));
				bean.setPreVerChg(rs.getInt("PRE_VER_CHG"));
				bean.setPreVerDel(rs.getInt("PRE_VER_DEL"));
				bean.setPreVerFp(rs.getInt("PRE_VER_FP"));
				bean.setProgettoSfera(rs.getString("PROGETTO_SFERA"));
				bean.setResponsabile(rs.getString("RESPONSABILE"));
				bean.setScopo(rs.getString("SCOPO"));
				bean.setStatoMisura(rs.getString("STATO_MISURA"));
				bean.setTpNum(rs.getDouble("TP_NUM"));
				bean.setTpUfp(rs.getDouble("TP_UFP"));
				bean.setUgdgNum(rs.getDouble("UGDG_NUM"));
				bean.setUgdgUfp(rs.getDouble("UGDG_UFP"));
				bean.setUgepNum(rs.getDouble("UGEP_NUM"));
				bean.setUgepUfp(rs.getString("UGEP_UFP"));
				bean.setUgoNum(rs.getDouble("UGO_NUM"));
				bean.setUgoUfp(rs.getDouble("UGO_UFP"));
				bean.setUgpNum(rs.getDouble("UGP_NUM"));
				bean.setUgpUfp(rs.getDouble("UGP_UFP"));
				bean.setUtenteMisuratore(rs.getString("UTENTE_MISURATORE"));
				bean.setValoreScostamento(rs.getDouble("VALORE_SCOSTAMENTO"));
				bean.setVersioneMsr(rs.getString("VERSIONE_MSR"));
				
				// Uso il campo Annullato in quanto i campi applicazione non esiste
				bean.setAnnullato(rs.getString("APPLICAZIONE"));
				
				bean.setDataModifica(dataEsecuzione);
				bean.setDataStoricizzazione(bean.getDataModifica());
				bean.setRankStatoMisura(new Double(1));
				bean.setDmalmMisuraPk(rs.getInt("DMALM_MISURA_PK"));
				bean.setIdProgetto(rs.getShort("ID_PROGETTO"));
				bean.setIdAsm(rs.getShort("ID_ASM"));
				bean.setDataCaricamento(dataEsecuzione);
				bean.setDmalmPrjFk(getProjectById(rs.getShort("ID_PROGETTO"),
						dataEsecuzione));
				misure.add(bean);
			}
			
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misure;
	}

	public static Integer getProjectById(short idProg, Timestamp dt_modifica)
			throws DAOException {
		QDmalmProgettoSfera progetto = QDmalmProgettoSfera.dmalmProgettoSfera;
		ConnectionManager cm = null;
		Connection connection = null;
		int progettoFk = 0;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);
			progettoFk = query.from(QDmalmProgettoSfera.dmalmProgettoSfera)
					.where(progetto.idProgetto.eq(idProg))
					.where(progetto.dataFineValidita.goe(dt_modifica))
					.where(progetto.dataInizioValidita.loe(dt_modifica))
					.orderBy(progetto.dataInizioValidita.desc())
					.list(progetto.dmalmProgettoSferaPk).get(0);

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		
		return progettoFk;
	}

	public static List<Tuple> getMisura(DmalmMisura misure) throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> misures = new LinkedList<Tuple>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			misures = query.from(misura)
					.where(misura.idAsm.eq(misure.getIdAsm()))
					.where(misura.idProgetto.eq(misure.getIdProgetto()))
					.where(misura.idMsr.eq(misure.getIdMsr()))
					.where(misura.rankStatoMisura.eq(new Double(1)))
					.list(misura.all());
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return misures;
	}

	public static void insertMisure(Timestamp dataEsecuzione, DmalmMisura misure)
			throws DAOException, SQLException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			SQLInsertClause insert = new SQLInsertClause(connection, dialect,
					misura);
			
			insert.columns(misura.dmalmMisuraPk,
					misura.dmalmStgMisuraPk,
					misura.a1Num,
					misura.a1Ufp,
					misura.a2Num,
					misura.a2Ufp,
					misura.adjmax,
					misura.adjmin,
					misura.adjufp,
					misura.ambito,
					misura.approccio,
					misura.b1Num,
					misura.b1Ufp,
					misura.b2Num,
					misura.b2Ufp,
					misura.b3Num,
					misura.b3Ufp,
					misura.b5Num,
					misura.b5Ufp,
					misura.bfpNum,
					misura.bfpUfp,
					misura.c1Num,
					misura.c1Ufp,
					misura.c2Num,
					misura.c2Ufp,
					misura.confine,
					misura.crudNum,
					misura.crudUfp,
					misura.d1Num,
					misura.d1Ufp,
					misura.d2Num,
					misura.d2Ufp,
					misura.dataConsolidamento,
					misura.dataCreazione,
					misura.dataFineVerificafp,
					misura.dataModifica,
					misura.dataInizioVerificaFp,
					misura.dataRiferimento,
					misura.eiNum,
					misura.eiUfp,
					misura.eifNum,
					misura.eifUfp,
					misura.eoNum,
					misura.eoUfp,
					misura.eqNum,
					misura.eqUfp,
					misura.esperienza,
					misura.faseCicloDiVita,
					misura.fonti,
					misura.fpNonPesatiMax,
					misura.fpNonPesatiMin,
					misura.fpNonPesatiUfp,
					misura.fpPesatiMax,
					misura.fpPesatiMin,
					misura.fpPesatiUfp,
					misura.fuNum,
					misura.fuUfp,
					misura.gdgNum,
					misura.gdgUfp,
					misura.geiNum,
					misura.geiUfp,
					misura.geifNum,
					misura.geifUfp,
					misura.geoNum,
					misura.geoUfp,
					misura.geqNum,
					misura.geqUfp,
					misura.gilfNum,
					misura.gilfUfp,
					misura.gpNum,
					misura.gpUfp,
					misura.idMsr,
					misura.ifpNum,
					misura.ifpUfp,
					misura.ilfNum,
					misura.ilfUfp,
					misura.ldgNum,
					misura.ldgUfp,
					misura.linkDocumentale,
					misura.metodo,
					misura.mfNum,
					misura.mfUfp,
					misura.mldgNum,
					misura.mldgUfp,
					misura.modello,
					misura.mpNum,
					misura.mpUfp,
					misura.nomeMisura,
					misura.noteMsr,
					misura.percentualeDiScostamento,
					misura.pfNum,
					misura.pfUfp,
					misura.postVerAddCfp,
					misura.postVerChg,
					misura.postVerDel,
					misura.postVerFp,
					misura.preVerAddCfp,
					misura.preVerChg,
					misura.preVerDel,
					misura.preVerFp,
					misura.responsabile,
					misura.scopo,
					misura.statoMisura,
					misura.tpNum,
					misura.tpUfp,
					misura.ugdgNum,
					misura.ugdgUfp,
					misura.ugepNum,
					misura.ugepUfp,
					misura.ugoNum,
					misura.ugoUfp,
					misura.ugpNum,
					misura.ugpUfp,
					misura.utenteMisuratore,
					misura.valoreScostamento,
					misura.versioneMsr,
					misura.progettoSfera)
				.values(misure.getDmalmMisuraPk(),
						misure.getDmalmStgMisuraPk(),
						misure.getA1Num(),
						misure.getA1Ufp(),
						misure.getA2Num(),
						misure.getA2Ufp(),
						misure.getAdjmax(),
						misure.getAdjmin(),
						misure.getAdjufp(),
						misure.getAmbito(),
						misure.getApproccio(),
						misure.getB1Num(),
						misure.getB1Ufp(),
						misure.getB2Num(),
						misure.getB2Ufp(),
						misure.getB3Num(),
						misure.getB3Ufp(),
						misure.getB5Num(),
						misure.getB5Ufp(),
						misure.getBfpNum(),
						misure.getBfpUfp(),
						misure.getC1Num(),
						misure.getC1Ufp(),
						misure.getC2Num(),
						misure.getC2Ufp(),
						misure.getConfine(),
						misure.getCrudNum(),
						misure.getCrudUfp(),
						misure.getD1Num(),
						misure.getD1Ufp(),
						misure.getD2Num(),
						misure.getD2Ufp(),
						misure.getDataConsolidamento(),
						misure.getDataCreazione(),
						misure.getDataFineVerificafp(),
						misure.getDataModifica(),
						misure.getDataInizioVerificaFp(),
						misure.getDataRiferimento(),
						misure.getEifNum(),
						misure.getEifUfp(),
						misure.getEiNum(),
						misure.getEiUfp(),
						misure.getEoNum(),
						misure.getEoUfp(),
						misure.getEqNum(),
						misure.getEqUfp(),
						misure.getEsperienza(),
						misure.getFaseCicloDiVita(),
						misure.getFonti(),
						misure.getFpNonPesatiMax(),
						misure.getFpNonPesatiMin(),
						misure.getFpNonPesatiUfp(),
						misure.getFpPesatiMax(),
						misure.getFpPesatiMin(),
						misure.getFpPesatiUfp(),
						misure.getFuNum(),
						misure.getFuUfp(),
						misure.getGdgNum(),
						misure.getGdgUfp(),
						misure.getGeifNum(),
						misure.getGeifUfp(),
						misure.getGeiNum(),
						misure.getGeiUfp(),
						misure.getGeoNum(),
						misure.getGeoUfp(),
						misure.getGeqNum(),
						misure.getGeqUfp(),
						misure.getGilfNum(),
						misure.getGilfUfp(),
						misure.getGpNum(),
						misure.getGpUfp(),
						misure.getIdMsr(),
						misure.getIfpNum(),
						misure.getIfpUfp(),
						misure.getIlfNum(),
						misure.getIlfUfp(),
						misure.getLdgNum(),
						misure.getLdgUfp(),
						misure.getLinkDocumentale(),
						misure.getMetodo(),
						misure.getMfNum(),
						misure.getMfUfp(),
						misure.getMldgNum(),
						misure.getMldgUfp(),
						misure.getModello(),
						misure.getMpNum(),
						misure.getMpUfp(),
						misure.getNomeMisura(),
						misure.getNoteMsr(),
						misure.getPercentualeDiScostamento(),
						misure.getPfNum(),
						misure.getPfUfp(),
						misure.getPostVerAddCfp(),
						misure.getPostVerChg(),
						misure.getPostVerDel(),
						misure.getPostVerFp(),
						misure.getPreVerAddCfp(),
						misure.getPreVerChg(),
						misure.getPreVerDel(),
						misure.getPreVerFp(),
						misure.getResponsabile(),
						misure.getScopo(),
						misure.getStatoMisura(),
						misure.getTpNum(),
						misure.getTpUfp(),
						misure.getUgdgNum(),
						misure.getUgdgUfp(),
						misure.getUgepNum(),
						misure.getUgepUfp(),
						misure.getUgoNum(),
						misure.getUgoUfp(),
						misure.getUgpNum(),
						misure.getUgpUfp(),
						misure.getUtenteMisuratore(),
						misure.getValoreScostamento(),
						misure.getVersioneMsr(),
						misure.getProgettoSfera())
				.execute();
			
			// modifica al codice per cambio libreria mysema
//			insert.populate(misure).execute();

			connection.commit();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);
			
			throw e;
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateRankMisura(DmalmMisura misure, Double double1)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, misura)
					.where(misura.idAsm.eq(misure.getIdAsm()))
					.where(misura.idProgetto.eq(misure.getIdProgetto()))
					.where(misura.idMsr.eq(misure.getIdMsr()))
					.set(misura.rankStatoMisura, double1).execute();
			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateMisura(DmalmMisura misure) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, misura)
					.where(misura.idAsm.eq(misure.getIdAsm()))
					.where(misura.idProgetto.eq(misure.getIdProgetto()))
					.where(misura.idMsr.eq(misure.getIdMsr()))
					.where(misura.rankStatoMisura.eq(new Double(1)))
					.set(misura.a1Num, misure.getA1Num())
					.set(misura.a1Ufp, misure.getA1Ufp())
					.set(misura.a2Num, misure.getA2Num())
					.set(misura.a2Ufp, misure.getA2Ufp())
					.set(misura.adjmax, misure.getAdjmax())
					.set(misura.adjmin, misure.getAdjmin())
					.set(misura.adjufp, misure.getAdjufp())
					.set(misura.ambito, misure.getAmbito())
					.set(misura.approccio, misure.getApproccio())
					.set(misura.b1Num, misure.getB1Num())
					.set(misura.b1Ufp, misure.getB1Ufp())
					.set(misura.b2Num, misure.getB2Num())
					.set(misura.b2Ufp, misure.getB2Ufp())
					.set(misura.b3Num, misure.getB3Num())
					.set(misura.b3Ufp, misure.getB3Ufp())
					.set(misura.b5Num, misure.getB5Num())
					.set(misura.b5Ufp, misure.getB5Ufp())
					.set(misura.bfpNum, misure.getBfpNum())
					.set(misura.bfpUfp, misure.getBfpUfp())
					.set(misura.c1Num, misure.getC1Num())
					.set(misura.c1Ufp, misure.getC1Ufp())
					.set(misura.c2Num, misure.getC2Num())
					.set(misura.c2Ufp, misure.getC2Ufp())
					.set(misura.confine, misure.getConfine())
					.set(misura.crudNum, misure.getCrudNum())
					.set(misura.crudUfp, misure.getCrudUfp())
					.set(misura.d1Num, misure.getD1Num())
					.set(misura.d1Ufp, misure.getD1Ufp())
					.set(misura.d2Num, misure.getD2Num())
					.set(misura.d2Ufp, misure.getD2Ufp())
					.set(misura.dataConsolidamento,
							misure.getDataConsolidamento())
					.set(misura.dataCreazione, misure.getDataCreazione())
					.set(misura.dataFineVerificafp,
							misure.getDataFineVerificafp())
					.set(misura.dataModifica, misure.getDataModifica())
					.set(misura.dataInizioVerificaFp,
							misure.getDataInizioVerificaFp())
					.set(misura.dataRiferimento, misure.getDataRiferimento())
					.set(misura.eiNum, misure.getEifNum())
					.set(misura.eiUfp, misure.getEifUfp())
					.set(misura.eifNum, misure.getEiNum())
					.set(misura.eifUfp, misure.getEiUfp())
					.set(misura.eoNum, misure.getEoNum())
					.set(misura.eoUfp, misure.getEoUfp())
					.set(misura.eqNum, misure.getEqNum())
					.set(misura.eqUfp, misure.getEqUfp())
					.set(misura.esperienza, misure.getEsperienza())
					.set(misura.faseCicloDiVita, misure.getFaseCicloDiVita())
					.set(misura.fonti, misure.getFonti())
					.set(misura.fpNonPesatiMax, misure.getFpNonPesatiMax())
					.set(misura.fpNonPesatiMin, misure.getFpNonPesatiMin())
					.set(misura.fpNonPesatiUfp, misure.getFpNonPesatiUfp())
					.set(misura.fpPesatiMax, misure.getFpPesatiMax())
					.set(misura.fpPesatiMin, misure.getFpPesatiMin())
					.set(misura.fpPesatiUfp, misure.getFpPesatiUfp())
					.set(misura.fuNum, misure.getFuNum())
					.set(misura.fuUfp, misure.getFuUfp())
					.set(misura.gdgNum, misure.getGdgNum())
					.set(misura.gdgUfp, misure.getGdgUfp())
					.set(misura.geiNum, misure.getGeifNum())
					.set(misura.geiUfp, misure.getGeifUfp())
					.set(misura.geifNum, misure.getGeiNum())
					.set(misura.geifUfp, misure.getGeiUfp())
					.set(misura.geoNum, misure.getGeoNum())
					.set(misura.geoUfp, misure.getGeoUfp())
					.set(misura.geqNum, misure.getGeqNum())
					.set(misura.geqUfp, misure.getGeqUfp())
					.set(misura.gilfNum, misure.getGilfNum())
					.set(misura.gilfUfp, misure.getGilfUfp())
					.set(misura.gpNum, misure.getGpNum())
					.set(misura.gpUfp, misure.getGpUfp())
					.set(misura.idMsr, misure.getIdMsr())
					.set(misura.ifpNum, misure.getIfpNum())
					.set(misura.ifpUfp, misure.getIfpUfp())
					.set(misura.ilfNum, misure.getIlfNum())
					.set(misura.ilfUfp, misure.getIlfUfp())
					.set(misura.ldgNum, misure.getLdgNum())
					.set(misura.ldgUfp, misure.getLdgUfp())
					.set(misura.linkDocumentale, misure.getLinkDocumentale())
					.set(misura.metodo, misure.getMetodo())
					.set(misura.mfNum, misure.getMfNum())
					.set(misura.mfUfp, misure.getMfUfp())
					.set(misura.mldgNum, misure.getMldgNum())
					.set(misura.mldgUfp, misure.getMldgUfp())
					.set(misura.modello, misure.getModello())
					.set(misura.mpNum, misure.getMpNum())
					.set(misura.mpUfp, misure.getMpUfp())
					.set(misura.nomeMisura, misure.getNomeMisura())
					.set(misura.noteMsr, misure.getNoteMsr())
					.set(misura.percentualeDiScostamento,
							misure.getPercentualeDiScostamento())
					.set(misura.pfNum, misure.getPfNum())
					.set(misura.pfUfp, misure.getPfUfp())
					.set(misura.postVerAddCfp, misure.getPostVerAddCfp())
					.set(misura.postVerChg, misure.getPostVerChg())
					.set(misura.postVerDel, misure.getPostVerDel())
					.set(misura.postVerFp, misure.getPostVerFp())
					.set(misura.preVerAddCfp, misure.getPreVerAddCfp())
					.set(misura.preVerChg, misure.getPreVerChg())
					.set(misura.preVerDel, misure.getPreVerDel())
					.set(misura.preVerFp, misure.getPreVerFp())
					.set(misura.responsabile, misure.getResponsabile())
					.set(misura.scopo, misure.getScopo())
					.set(misura.statoMisura, misure.getStatoMisura())
					.set(misura.tpNum, misure.getTpNum())
					.set(misura.tpUfp, misure.getTpUfp())
					.set(misura.ugdgNum, misure.getUgdgNum())
					.set(misura.ugdgUfp, misure.getUgdgUfp())
					.set(misura.ugepNum, misure.getUgepNum())
					.set(misura.ugepUfp, misure.getUgepUfp())
					.set(misura.ugoNum, misure.getUgoNum())
					.set(misura.ugoUfp, misure.getUgoUfp())
					.set(misura.ugpNum, misure.getUgpNum())
					.set(misura.ugpUfp, misure.getUgpUfp())
					.set(misura.utenteMisuratore, misure.getUtenteMisuratore())
					.set(misura.valoreScostamento,
							misure.getValoreScostamento())
					.set(misura.versioneMsr, misure.getVersioneMsr())
					.set(misura.progettoSfera, misure.getProgettoSfera())
					.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateAnnullatoMisura(DmalmMisura misure)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			new SQLUpdateClause(connection, dialect, misura)
					.where(misura.idAsm.eq(misure.getIdAsm()))
					.where(misura.idProgetto.eq(misure.getIdProgetto()))
					.where(misura.idMsr.eq(misure.getIdMsr()))
					.where(misura.rankStatoMisura.eq(new Double(1)))
					.set(misura.annullato, DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE).execute();
			
			connection.commit();
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
	
	public static List<Tuple> getMisuraByName(DmalmMisura msr, String parApplicazione, String parNomeProgetto)
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> mis = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			mis = query
					.from(misura)
					.join(prog).on(prog.dmalmProgettoSferaPk.eq(misura.dmalmPrjFk))
					.join(asm).on(asm.dmalmAsmPk.eq(prog.dmalmAsmFk))
					.where(misura.nomeMisura.eq(msr.getNomeMisura()))
					.where(misura.annullato.eq(DmAlmConstants.SFERA_ANNULLATO_FISICAMENTE))
					.where(misura.rankStatoMisura.eq(new Double(1)))
					.where(prog.nomeProgetto.eq(parNomeProgetto))
					.where(asm.applicazione.eq(parApplicazione))
					.list(misura.all());
					
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mis;
	}
	
	public static Integer getMisuraPk()
			throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		Integer resMisuraPk = 0;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryManager.getInstance().getQuery(
					DmAlmConfigReaderProperties.SFERA_MISURA_PK_NEXTVAL);
			ps = connection.prepareStatement(sql);

			rs = ps.executeQuery();
			rs.next();
			resMisuraPk = rs.getInt("MISURA_PK");
			
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
			logger.error(e.getMessage(), e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return resMisuraPk;
	}
	
	public static List<Tuple> checkLinkMisureSferaWi(Short idProgetto)
			throws Exception {
		ConnectionManager cm = null;
		Connection connection = null;

		List<Tuple> mis = new ArrayList<Tuple>();

		try {

			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);

			mis = query
					.from(misura)
					.where(misura.idProgetto.eq(idProgetto))
					.where(misura.rankStatoMisura.eq(new Double(1)))
					.where(misura.nomeMisura.notLike("%NOWI%"))
					.where(misura.annullato.notLike("ANNULLATO%"))
					.where(misura.statoMisura.notIn("Consolidata", "Sospesa"))
					.list(misura.all());
					
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return mis;
	}
}
