package lispa.schedulers.dao.target.fatti;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaSupporto;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class RichiestaSupportoDAO {

	private static Logger logger = Logger.getLogger(RichiestaSupportoDAO.class);

	public static List<DmalmRichiestaSupporto> getAllRichiestaSupporto(Timestamp dataEsecuzione)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		ResultSet rs = null;

		DmalmRichiestaSupporto bean = null;
		List<DmalmRichiestaSupporto> richieste = new LinkedList<DmalmRichiestaSupporto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			// connection.setAutoCommit(false);
			String sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.GET_ALL_RICHIESTA_SUPPORTO", 1);
			cs = connection.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setTimestamp(2, dataEsecuzione);
			cs.setFetchSize(75);
			cs.execute();
			// return the result set
			rs = (ResultSet) cs.getObject(1);

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
				bean.setCdRichiestaSupporto(rs.getString("CD_RICHIESTA_SUPPORTO"));
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
				bean.setCodiceArea(rs.getString("CODICE_AREA"));
				bean.setCodiceProdotto(rs.getString("CODICE_PRODOTTO"));
				bean.setDtScadenzaRichiestaSupporto(rs.getTimestamp("DATA_SCADENZA"));
				bean.setTimespent(rs.getFloat("TIMESPENT"));
				bean.setTagAlm(rs.getString("TAG_ALM"));
				bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
				richieste.add(bean);
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {

			if (cs != null) {
				cs.close();
			}
			if (rs != null) {
				rs.close();
			}
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return richieste;
	}

	public static List<DmalmRichiestaSupporto> getRichiestaSupporto(DmalmRichiestaSupporto richiesta)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		ResultSet rs = null;
		DmalmRichiestaSupporto bean = null;
		List<DmalmRichiestaSupporto> richieste = new LinkedList<DmalmRichiestaSupporto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.GET_RICHIESTA_SUPPORTO", 1);
			Object[] objRichSupp = richiesta.getObject(richiesta, true);
			Struct structObj = connection.createStruct("RICHSUPPTYPE", objRichSupp);
			try(OracleCallableStatement ocs = (OracleCallableStatement) connection.prepareCall(sql);){
				ocs.registerOutParameter(1, OracleTypes.CURSOR);
				ocs.setObject(2, structObj);
				ocs.execute();
	
				// return the result set
				rs = (ResultSet) ocs.getObject(1);
				while (rs.next()) {
	//				logger.info("Cerco di inserire "+rs.getString("STG_PK")+ " ");
					// Elabora il risultato
					bean = new DmalmRichiestaSupporto();
					bean.setIdRepository(rs.getString("ID_REPOSITORY"));
					bean.setUriRichiestaSupporto(rs.getString("URI_RICHIESTA_SUPPORTO"));
					bean.setDmalmRichiestaSupportoPk(rs.getInt("DMALM_RICH_SUPPORTO_PK"));
					bean.setStgPk(rs.getString("STG_PK"));
					bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
					bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
					bean.setCdRichiestaSupporto(rs.getString("CD_RICHIESTA_SUPPORTO"));
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
					bean.setCodiceArea(rs.getString("CODICE_AREA"));
					bean.setCodiceProdotto(rs.getString("CODICE_PRODOTTO"));
					bean.setDtScadenzaRichiestaSupporto(rs.getTimestamp("DATA_SCADENZA"));
					bean.setTimespent(rs.getFloat("TIMESPENT"));
					bean.setTagAlm(rs.getString("TAG_ALM"));
					bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
					richieste.add(bean);
	//				logger.info("Inserito");
	
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (rs != null)
				rs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}

		return richieste;

	}

	public static void insertRichiestaSupporto(DmalmRichiestaSupporto richiesta, Timestamp dataEsecuzione)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.INSERT_RICHIESTA_SUPPORTO", 2);
			Object[] objRichSupp = richiesta.getObject(richiesta, true);
//		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
//		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
			Struct structObj = connection.createStruct("RICHSUPPTYPE", objRichSupp);
			ocs = (OracleCallableStatement) connection.prepareCall(sql);
			ocs.setObject(1, structObj);
			ocs.setTimestamp(2, dataEsecuzione);
			ocs.execute();
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (ocs != null)
				ocs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateRank(DmalmRichiestaSupporto richiesta, Double double1) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.UPDATE_RANK", 2);
			Object[] objRichSupp = richiesta.getObject(richiesta, true);
//		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
//		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
			Struct structObj = connection.createStruct("RICHSUPPTYPE", objRichSupp);
			ocs = (OracleCallableStatement) connection.prepareCall(sql);
			ocs.setObject(1, structObj);
			ocs.setDouble(2, double1);
			ocs.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (ocs != null) {
				ocs.close();
			}

			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertRichiestaSupportoUpdate(Timestamp dataEsecuzione, DmalmRichiestaSupporto richiesta,
			boolean pkValue) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.INSERT_UPDATE_RICH_SUPPORTO", 2);
			Object[] objRichSupp = richiesta.getObject(richiesta, pkValue);
//		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
//		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
			Struct structObj = connection.createStruct("RICHSUPPTYPE", objRichSupp);
			ocs = (OracleCallableStatement) connection.prepareCall(sql);
			ocs.setObject(1, structObj);
			ocs.setTimestamp(2, dataEsecuzione);

			ocs.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (ocs != null)
				ocs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateRichiestaSupporto(DmalmRichiestaSupporto richiesta) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.UPDATE_RICH_SUPPORTO", 1);
			Object[] objRichSupp = richiesta.getObject(richiesta, true);
//		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
//		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
			Struct structObj = connection.createStruct("RICHSUPPTYPE", objRichSupp);
			ocs = (OracleCallableStatement) connection.prepareCall(sql);
			ocs.setObject(1, structObj);
			ocs.execute();

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (ocs != null)
				ocs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static DmalmRichiestaSupporto getRichiestaSupporto(Integer pk) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		ResultSet rs = null;
		DmalmRichiestaSupporto bean = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.GET_RICHIESTA_SUPPORTO_BY_PK", 1);

			try(OracleCallableStatement ocs = (OracleCallableStatement) connection.prepareCall(sql);){
				ocs.registerOutParameter(1, OracleTypes.CURSOR);
				ocs.setInt(2, pk);
				ocs.execute();
	
				// return the result set
				rs = (ResultSet) ocs.getObject(1);
	
				while (rs.next()) {
					// Elabora il risultato
					bean = new DmalmRichiestaSupporto();
					bean.setIdRepository(rs.getString("ID_REPOSITORY"));
					bean.setUriRichiestaSupporto(rs.getString("URI_RICHIESTA_SUPPORTO"));
					bean.setDmalmRichiestaSupportoPk(rs.getInt("DMALM_RICH_SUPPORTO_PK"));
					bean.setStgPk(rs.getString("STG_PK"));
					bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
					bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
					bean.setCdRichiestaSupporto(rs.getString("CD_RICHIESTA_SUPPORTO"));
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
					bean.setAnnullato(rs.getString("ANNULLATO"));
					bean.setDataAnnullamento(rs.getTimestamp("DATA_ANNULLAMENTO"));
					bean.setDataStoricizzazione(rs.getTimestamp("DT_STORICIZZAZIONE"));
					bean.setCodiceArea(rs.getString("CODICE_AREA"));
					bean.setCodiceProdotto(rs.getString("CODICE_PRODOTTO"));
					bean.setDtScadenzaRichiestaSupporto(rs.getTimestamp("DATA_SCADENZA"));
					bean.setTimespent(rs.getFloat("TIMESPENT"));
					bean.setTagAlm(rs.getString("TAG_ALM"));
					bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (rs != null)
				rs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}

		return bean;
	}

	public static List<DmalmRichiestaSupporto> getRichiestaSupporto(Integer pk_proj, Integer typeQuery)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		ResultSet rs = null;
		List<DmalmRichiestaSupporto> richieste = new LinkedList<DmalmRichiestaSupporto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = "";
			switch (typeQuery) {
			case 0:
				sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.GET_PK_RICH_SUPP_BY_PK_HIS", 1);
				break;
			case 1:
				sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.GET_PK_RICH_SUPP_BY_PK_HIS_SUB", 1);
				break;
			default:
				break;
			}

			try(OracleCallableStatement ocs = (OracleCallableStatement) connection.prepareCall(sql);){
				ocs.registerOutParameter(1, OracleTypes.CURSOR);
				ocs.setInt(2, pk_proj);
				ocs.execute();
	
				// return the result set
				rs = (ResultSet) ocs.getObject(1);
	
				while (rs.next()) {
					// Elabora il risultato
					DmalmRichiestaSupporto bean = new DmalmRichiestaSupporto();
					bean.setIdRepository(rs.getString("ID_REPOSITORY"));
					bean.setUriRichiestaSupporto(rs.getString("URI_RICHIESTA_SUPPORTO"));
					bean.setDmalmRichiestaSupportoPk(rs.getInt("DMALM_RICH_SUPPORTO_PK"));
					bean.setStgPk(rs.getString("STG_PK"));
					bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
					bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
					bean.setCdRichiestaSupporto(rs.getString("CD_RICHIESTA_SUPPORTO"));
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
					bean.setAnnullato(rs.getString("ANNULLATO"));
					bean.setDataAnnullamento(rs.getTimestamp("DATA_ANNULLAMENTO"));
					bean.setDataStoricizzazione(rs.getTimestamp("DT_STORICIZZAZIONE"));
					bean.setCodiceArea(rs.getString("CODICE_AREA"));
					bean.setCodiceProdotto(rs.getString("CODICE_PRODOTTO"));
					bean.setDtScadenzaRichiestaSupporto(rs.getTimestamp("DATA_SCADENZA"));
					bean.setTimespent(rs.getFloat("TIMESPENT"));
					bean.setTagAlm(rs.getString("TAG_ALM"));
					bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
					richieste.add(bean);
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {

			if (rs != null)
				rs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}

		return richieste;
	}

	public static void updateWIRichiestaSupportoDeleted(DmalmRichiestaSupporto richiesta, Timestamp dataEsecuzione)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.UPDATE_WI_RICH_SUPPORTO_DELETE", 2);
			Object[] objRichSupp = richiesta.getObject(richiesta, true);
//		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
//		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
			Struct structObj = connection.createStruct("RICHSUPPTYPE", objRichSupp);
			ocs = (OracleCallableStatement) connection.prepareCall(sql);
			ocs.setObject(1, structObj);
			ocs.setTimestamp(2, dataEsecuzione);
			ocs.execute();

			connection.commit();

		} catch (Exception e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			if (richiesta != null)
				logger.info("Attenzione, non sono riuscito ad eliminare item con PK "
						+ richiesta.getDmalmRichiestaSupportoPk());

		} finally {
			if (ocs != null)
				ocs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static boolean checkEsistenzaRichiestaSupporto(DmalmRichiestaSupporto richiesta, DmalmProject p)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		String flag = "FALSE";
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.CHECK_ESISTENZA_RICH_SUPPORTO", 2);
			Object[] objRichSupp = richiesta.getObject(richiesta, true);
//		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
//		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
			Struct structObj = connection.createStruct("RICHSUPPTYPE", objRichSupp);
			ocs = (OracleCallableStatement) connection.prepareCall(sql);
			ocs.registerOutParameter(1, OracleTypes.VARCHAR);
			ocs.setObject(2, structObj);
			ocs.setInt(3, p.getDmalmProjectPk());
			ocs.execute();

			flag = ocs.getString(1);

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (ocs != null) {
				ocs.close();
			}
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		if (flag.equals("TRUE")) {
			return true;
		} else {
			return false;
		}
	}

	// verifica se una RichiestaSupporto:
	// non è stata inserita, quindi effettua una insert in tabella
	// è stata inserita, quindi aggiorna il rank, effettua una update della
	// RichiestaSupporto per storicizzarla
	// ed infine effettua una insert con i i nuovi dati
	public static void checkAlteredRichiestaSupporto(List<DmalmRichiestaSupporto> richieste, Timestamp dataEsecuzione)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		ResultSet rs = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.CHECK_ALTERED_ROW_RICH_SUPP", 2);
			for (DmalmRichiestaSupporto richiesta : richieste) {
				Object[] objRichSupp = richiesta.getObject(richiesta, true);
				Struct structObj = connection.createStruct("RICHSUPPTYPE", objRichSupp);
				try (OracleCallableStatement ocs = (OracleCallableStatement) connection.prepareCall(sql);) {
					ocs.setObject(1, structObj);
					ocs.setTimestamp(2, dataEsecuzione);
					ocs.execute();
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (rs != null)
				rs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

}
