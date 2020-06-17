package lispa.schedulers.dao.target;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Struct;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;

import lispa.schedulers.bean.target.fatti.DmalmRfc;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class RfcOdsDAO {

	private static Logger logger = Logger.getLogger(RfcOdsDAO.class);
	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.DELETE_RICH_SUPPORTO_ODS", 0);

			cs = connection.prepareCall(sql);
			cs.execute();
			
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

	}

	public static void insert(List<DmalmRfc> staging_rfc,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		List <Integer> listPk= new ArrayList<Integer>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
	        String sql = QueryUtils.getCallProcedure("RFC.INSERT_RFC_ODS", 2);
		    for (DmalmRfc rfc : staging_rfc) {
		    	if(listPk.contains(rfc.getDmalmRfcPk()))
					logger.info("Trovata getDmalmRfcPk DUPLICATA!!!"+rfc.getDmalmRfcPk());
				else{
					listPk.add(rfc.getDmalmRfcPk());
			    	Object [] objRfc = rfc.getObject(rfc, true);
			    	Struct structObj = connection.createStruct("RFCTYPE", objRfc);
			    	ocs = (OracleCallableStatement)connection.prepareCall(sql);
					ocs.setObject(1, structObj); 
					ocs.setTimestamp(2, dataEsecuzione);
					ocs.execute();
					ocs.close();
				}
		    }
		    connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static List<DmalmRfc> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		DmalmRfc bean = null;
		List<DmalmRfc> rfc = new LinkedList<DmalmRfc>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			//connection.setAutoCommit(false);

			String sql = QueryUtils.getCallFunction("RFC.GET_ALL_RFC_ODS", 0);
			cs = connection.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setFetchSize(75);
			cs.execute();
			//return the result set
            rs = (ResultSet)cs.getObject(1);
            

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

			//connection.commit();
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if(cs!=null)
				cs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}

		return rfc;

	}

}

