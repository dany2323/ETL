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
import lispa.schedulers.bean.target.fatti.DmalmRfc;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class RfcDAO {

	private static Logger logger = Logger.getLogger(RfcDAO.class);

	public static List<DmalmRfc> getAllRfc(Timestamp dataEsecuzione) 
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		DmalmRfc bean = null;
		List<DmalmRfc> rfc = new LinkedList<DmalmRfc>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			String sql = QueryUtils.getCallFunction(
					"RFC.GET_ALL_RFC", 1);
			try (CallableStatement cs = connection.prepareCall(sql);) {
				cs.registerOutParameter(1, OracleTypes.CURSOR);
				cs.setTimestamp(2, dataEsecuzione);
				cs.setFetchSize(75);
				cs.execute();
				// return the result set
				try (ResultSet rs = (ResultSet) cs.getObject(1);) {

					logger.debug("Query Eseguita!");

					while (rs.next()) {
						// Elabora il risultato
						bean = new DmalmRfc();
						
						bean.setIdRepository(rs.getString("CD_RFC"));
						bean.setDmalmRfcPk(rs.getInt("DMALM_RFC_PK"));
						bean.setDmalmProjectFk02(rs.getLong("DMALM_PROJECT_FK_02"));
						bean.setDmalmStatoWorkitemFk03(rs.getInt("DMALM_STATO_WORKITEM_FK_03"));
						bean.setDmalmTempoFk04(rs.getInt("DMALM_TEMPO_FK_04"));
						bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
						bean.setRankStatoRfc(rs.getInt("RANK_STATO_RFC"));
						bean.setRankStatoRfcMese(rs.getInt("RANK_STATO_RFC_MESE"));
						bean.setTimespent(rs.getFloat("TIMESPENT"));
					    bean.setTagAlm(rs.getString("TAG_ALM"));
					    bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
						bean.setAnnullato(rs.getString("ANNULLATO"));
						bean.setStgPk(rs.getString("STG_PK"));
						bean.setUriRfc(rs.getString("URI_RFC"));
						bean.setDataStoricizzazione(rs.getTimestamp("DT_STORICIZZAZIONE"));
						bean.setDataModificaRecord(rs.getTimestamp("DATA_MODIFICA_RECORD"));
						bean.setDescription(rs.getString("DESCRIPTION"));
						bean.setTipologiaIntervento(rs.getString("TIPOLOGIA_INTERVENTO"));
						bean.setTipologiaDatiTrattati(rs.getString("TIPOLOGIA_DATI_TRATTATI"));
						bean.setRichiestaIt(rs.getString("RICHIESTA_IT"));
						bean.setInfrastrutturaEsistente(rs.getString("INFRASTRUTTURA_ESISTENTE"));
						bean.setCambiamentoRichiesto(rs.getString("CAMBIAMENTO_RICHIESTO"));
						bean.setDescrizioneUtenza(rs.getString("DESCRIZIONE_UTENZA"));
						bean.setRequisitiDiUtilizzo(rs.getString("REQUISITI_DI_UTILIZZO"));
						bean.setModalitaGestione(rs.getString("MODALITA_GESTIONE"));
						
						rfc.add(bean);
					}
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {

			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return rfc;
	}

	public static List<DmalmRfc> getRfc(DmalmRfc rfc)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		DmalmRfc bean = null;
		List<DmalmRfc> rfcs = new LinkedList<DmalmRfc>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryUtils.getCallFunction(
					"RFC.GET_RFC", 1);
			Object[] objRfc = rfc.getObject(rfc, true);
			Struct structObj = connection.createStruct("RFCTYPE", objRfc);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.registerOutParameter(1, OracleTypes.CURSOR);
				ocs.setObject(2, structObj);
				ocs.execute();

				// return the result set
				try (ResultSet rs = (ResultSet) ocs.getObject(1);) {
					while (rs.next()) {
						bean = new DmalmRfc();
						
						bean.setIdRepository(rs.getString("CD_RFC"));
						bean.setDmalmRfcPk(rs.getInt("DMALM_RFC_PK"));
						bean.setDmalmProjectFk02(rs.getLong("DMALM_PROJECT_FK_02"));
						bean.setDmalmStatoWorkitemFk03(rs.getInt("DMALM_STATO_WORKITEM_FK_03"));
						bean.setDmalmTempoFk04(rs.getInt("DMALM_TEMPO_FK_04"));
						bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
						bean.setRankStatoRfc(rs.getInt("RANK_STATO_RFC"));
						bean.setRankStatoRfcMese(rs.getInt("RANK_STATO_RFC_MESE"));
						bean.setTimespent(rs.getFloat("TIMESPENT"));
					    bean.setTagAlm(rs.getString("TAG_ALM"));
					    bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
						bean.setAnnullato(rs.getString("ANNULLATO"));
						bean.setStgPk(rs.getString("STG_PK"));
						bean.setUriRfc(rs.getString("URI_RFC"));
						bean.setDataStoricizzazione(rs.getTimestamp("DT_STORICIZZAZIONE"));
						bean.setDataModificaRecord(rs.getTimestamp("DATA_MODIFICA_RECORD"));
						bean.setDescription(rs.getString("DESCRIPTION"));
						bean.setTipologiaIntervento(rs.getString("TIPOLOGIA_INTERVENTO"));
						bean.setTipologiaDatiTrattati(rs.getString("TIPOLOGIA_DATI_TRATTATI"));
						bean.setRichiestaIt(rs.getString("RICHIESTA_IT"));
						bean.setInfrastrutturaEsistente(rs.getString("INFRASTRUTTURA_ESISTENTE"));
						bean.setCambiamentoRichiesto(rs.getString("CAMBIAMENTO_RICHIESTO"));
						bean.setDescrizioneUtenza(rs.getString("DESCRIZIONE_UTENZA"));
						bean.setRequisitiDiUtilizzo(rs.getString("REQUISITI_DI_UTILIZZO"));
						bean.setModalitaGestione(rs.getString("MODALITA_GESTIONE"));
						
						rfcs.add(bean);
					}
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return rfcs;

	}

	public static void insertRfc(DmalmRfc rfc, Timestamp dataEsecuzione) 
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure(
					"RFC.INSERT_RFC", 2);
			Object[] objRfc = rfc.getObject(rfc, true);
			Struct structObj = connection.createStruct("RFCTYPE",
					objRfc);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.setObject(1, structObj);
				ocs.setTimestamp(2, dataEsecuzione);
				ocs.execute();
				connection.commit();
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateRank(DmalmRfc rfc, Double double1) 
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			String sql = QueryUtils
					.getCallProcedure("RFC.UPDATE_RANK", 2);
			Object[] objRfc = rfc.getObject(rfc, true);
			Struct structObj = connection.createStruct("RFCTYPE",
					objRfc);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.setObject(1, structObj);
				ocs.setDouble(2, double1);
				ocs.execute();

				connection.commit();
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {

			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void insertRfcUpdate(Timestamp dataEsecuzione,
			DmalmRfc rfc, boolean pkValue)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure(
					"RFC.INSERT_UPDATE_RFC", 2);
			Object[] objRfc = rfc.getObject(rfc, pkValue);
			Struct structObj = connection.createStruct("RFCTYPE",
					objRfc);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.setObject(1, structObj);
				ocs.setTimestamp(2, dataEsecuzione);
				ocs.execute();
				connection.commit();
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void updateRfc(DmalmRfc rfc)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure(
					"RFC.UPDATE_RFC", 1);
			Object[] objRfc = rfc.getObject(rfc, true);
			Struct structObj = connection.createStruct("RFCTYPE",
					objRfc);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.setObject(1, structObj);
				ocs.execute();
				connection.commit();
			}

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static DmalmRfc getRfc(Integer pk)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		DmalmRfc bean = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryUtils.getCallFunction(
					"RFC.GET_RFC_BY_PK", 1);

			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.registerOutParameter(1, OracleTypes.CURSOR);
				ocs.setInt(2, pk);
				ocs.execute();

				try (ResultSet rs = (ResultSet) ocs.getObject(1);) {

					while (rs.next()) {
						// Elabora il risultato
						bean = new DmalmRfc();
						
						bean.setIdRepository(rs.getString("CD_RFC"));
						bean.setDmalmRfcPk(rs.getInt("DMALM_RFC_PK"));
						bean.setDmalmProjectFk02(rs.getLong("DMALM_PROJECT_FK_02"));
						bean.setDmalmStatoWorkitemFk03(rs.getInt("DMALM_STATO_WORKITEM_FK_03"));
						bean.setDmalmTempoFk04(rs.getInt("DMALM_TEMPO_FK_04"));
						bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
						bean.setRankStatoRfc(rs.getInt("RANK_STATO_RFC"));
						bean.setRankStatoRfcMese(rs.getInt("RANK_STATO_RFC_MESE"));
						bean.setTimespent(rs.getFloat("TIMESPENT"));
					    bean.setTagAlm(rs.getString("TAG_ALM"));
					    bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
						bean.setAnnullato(rs.getString("ANNULLATO"));
						bean.setStgPk(rs.getString("STG_PK"));
						bean.setUriRfc(rs.getString("URI_RFC"));
						bean.setDataStoricizzazione(rs.getTimestamp("DT_STORICIZZAZIONE"));
						bean.setDataModificaRecord(rs.getTimestamp("DATA_MODIFICA_RECORD"));
						bean.setDescription(rs.getString("DESCRIPTION"));
						bean.setTipologiaIntervento(rs.getString("TIPOLOGIA_INTERVENTO"));
						bean.setTipologiaDatiTrattati(rs.getString("TIPOLOGIA_DATI_TRATTATI"));
						bean.setRichiestaIt(rs.getString("RICHIESTA_IT"));
						bean.setInfrastrutturaEsistente(rs.getString("INFRASTRUTTURA_ESISTENTE"));
						bean.setCambiamentoRichiesto(rs.getString("CAMBIAMENTO_RICHIESTO"));
						bean.setDescrizioneUtenza(rs.getString("DESCRIZIONE_UTENZA"));
						bean.setRequisitiDiUtilizzo(rs.getString("REQUISITI_DI_UTILIZZO"));
						bean.setModalitaGestione(rs.getString("MODALITA_GESTIONE"));
					}
				}

			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return bean;
	}

	public static List<DmalmRfc> getRfc(Long pk_proj, Integer typeQuery)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<DmalmRfc> rfcs = new LinkedList<DmalmRfc>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = "";
			switch (typeQuery) {
				case 0 :
					sql = QueryUtils.getCallFunction(
							"RFC.GET_PK_RFC_BY_PK_HIS", 1);
					break;
				case 1 :
					sql = QueryUtils.getCallFunction(
							"RFC.GET_PK_RFC_BY_PK_HIS_SUB", 1);
					break;
				default :
					break;
			}

			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.registerOutParameter(1, OracleTypes.CURSOR);
				ocs.setLong(2, pk_proj);
				ocs.execute();

				// return the result set
				try (ResultSet rs = (ResultSet) ocs.getObject(1);) {

					while (rs.next()) {
						// Elabora il risultato
						DmalmRfc bean = new DmalmRfc();
						
						bean.setIdRepository(rs.getString("CD_RFC"));
						bean.setDmalmRfcPk(rs.getInt("DMALM_RFC_PK"));
						bean.setDmalmProjectFk02(rs.getLong("DMALM_PROJECT_FK_02"));
						bean.setDmalmStatoWorkitemFk03(rs.getInt("DMALM_STATO_WORKITEM_FK_03"));
						bean.setDmalmTempoFk04(rs.getInt("DMALM_TEMPO_FK_04"));
						bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
						bean.setRankStatoRfc(rs.getInt("RANK_STATO_RFC"));
						bean.setRankStatoRfcMese(rs.getInt("RANK_STATO_RFC_MESE"));
						bean.setTimespent(rs.getFloat("TIMESPENT"));
					    bean.setTagAlm(rs.getString("TAG_ALM"));
					    bean.setTsTagAlm(rs.getTimestamp("TS_TAG_ALM"));
						bean.setAnnullato(rs.getString("ANNULLATO"));
						bean.setStgPk(rs.getString("STG_PK"));
						bean.setUriRfc(rs.getString("URI_RFC"));
						bean.setDataStoricizzazione(rs.getTimestamp("DT_STORICIZZAZIONE"));
						bean.setDataModificaRecord(rs.getTimestamp("DATA_MODIFICA_RECORD"));
						bean.setDescription(rs.getString("DESCRIPTION"));
						bean.setTipologiaIntervento(rs.getString("TIPOLOGIA_INTERVENTO"));
						bean.setTipologiaDatiTrattati(rs.getString("TIPOLOGIA_DATI_TRATTATI"));
						bean.setRichiestaIt(rs.getString("RICHIESTA_IT"));
						bean.setInfrastrutturaEsistente(rs.getString("INFRASTRUTTURA_ESISTENTE"));
						bean.setCambiamentoRichiesto(rs.getString("CAMBIAMENTO_RICHIESTO"));
						bean.setDescrizioneUtenza(rs.getString("DESCRIZIONE_UTENZA"));
						bean.setRequisitiDiUtilizzo(rs.getString("REQUISITI_DI_UTILIZZO"));
						bean.setModalitaGestione(rs.getString("MODALITA_GESTIONE"));
						
						rfcs.add(bean);
					}
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return rfcs;
	}

	public static void updateWIRfcDeleted(
			DmalmRfc rfc, Timestamp dataEsecuzione)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure(
					"RFC.UPDATE_WI_RFC_DELETE", 2);
			Object[] objRfc = rfc.getObject(rfc, true);
			Struct structObj = connection.createStruct("RFCTYPE",
					objRfc);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.setObject(1, structObj);
				ocs.setTimestamp(2, dataEsecuzione);
				ocs.execute();
				connection.commit();
			}

		} catch (Exception e) {
			// ErrorManager.getInstance().exceptionOccurred(true, e);
			if (rfc != null) {
				logger.info(
						"Attenzione, non sono riuscito ad eliminare item con PK "
								+ rfc.getDmalmRfcPk());
			}

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static boolean checkEsistenzaRfc(
			DmalmRfc rfc, DmalmProject p)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		String flag = "FALSE";
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallFunction(
					"RFC.CHECK_ESISTENZA_RFC", 2);
			Object[] objRfc = rfc.getObject(rfc, true);
			Struct structObj = connection.createStruct("RFCTYPE",
					objRfc);
			ocs = (OracleCallableStatement) connection.prepareCall(sql);
			ocs.registerOutParameter(1, OracleTypes.VARCHAR);
			ocs.setObject(2, structObj);
			ocs.setLong(3, p.getDmalmProjectPk());
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

	// verifica se una RFC:
	// non è stata inserita, quindi effettua una insert in tabella
	// è stata inserita, quindi aggiorna il rank, effettua una update della
	// RFC per storicizzarla
	// ed infine effettua una insert con i i nuovi dati
	public static void checkAlteredRfc(
			List<DmalmRfc> rfcs, Timestamp dataEsecuzione)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryUtils.getCallProcedure(
					"RFC.CHECK_ALTERED_ROW_RFC", 2);
			for (DmalmRfc rfc : rfcs) {
				Object[] objRfc = rfc.getObject(rfc, true);
				Struct structObj = connection.createStruct("RFCTYPE",
						objRfc);
				try (OracleCallableStatement ocs = (OracleCallableStatement) connection
						.prepareCall(sql);) {
					ocs.setObject(1, structObj);
					ocs.setTimestamp(2, dataEsecuzione);
					ocs.execute();
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

}
