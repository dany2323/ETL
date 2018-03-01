package lispa.schedulers.dao.target.fatti;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import com.mysema.query.Tuple;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import lispa.schedulers.bean.target.DmalmProject;
import lispa.schedulers.bean.target.fatti.DmalmReleaseDiProgetto;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaSupporto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.target.fatti.QDmalmReleaseDiProgetto;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

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
		OracleCallableStatement ocs = null;
		ResultSet rs = null;
		DmalmRichiestaSupporto bean = null;
		List<DmalmRichiestaSupporto> richieste = new LinkedList<DmalmRichiestaSupporto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.GET_RICHIESTA_SUPPORTO", 1);
			Object [] objRichSupp = richiesta.getObject(richiesta);
		    	// Now Declare a descriptor to associate the host object type with the
		    	// record type in the database.
		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
		    	// Now create the STRUCT objects to associate the host objects
		    	// with the database records.
		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);

		    	ocs = (OracleCallableStatement)connection.prepareCall(sql);
			ocs.registerOutParameter(1, OracleTypes.CURSOR);
			ocs.setObject(2, structObj);
			ocs.execute();
			
			//return the result set
            rs = (ResultSet)ocs.getObject(1);
            
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

	public static void insertRichiestaSupporto(DmalmRichiestaSupporto richiesta, Timestamp dataEsecuzione)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			
			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.INSERT_RICHIESTA_SUPPORTO", 2);
			Object [] objRichSupp = richiesta.getObject(richiesta);
		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
		    	ocs = (OracleCallableStatement)connection.prepareCall(sql);
			ocs.setObject(1, structObj);
			ocs.setTimestamp(2, dataEsecuzione);
			ocs.execute();
			
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateRank(DmalmRichiestaSupporto richiesta, Double double1)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			
			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.UPDATE_RANK", 2);
			Object [] objRichSupp = richiesta.getObject(richiesta);
		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
		    	ocs = (OracleCallableStatement)connection.prepareCall(sql);
			ocs.setObject(1, structObj);
			ocs.setDouble(2, double1);
			ocs.execute();
			
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void insertReleaseDiProgettoUpdate(Timestamp dataEsecuzione,
			DmalmRichiestaSupporto richiesta, boolean pkValue)
			throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			
			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.INSERT_UPDATE_RICH_SUPPORTO", 2);
			Object [] objRichSupp = richiesta.getObject(richiesta);
		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
		    	ocs = (OracleCallableStatement)connection.prepareCall(sql);
			ocs.setObject(1, structObj);
			ocs.setTimestamp(2, dataEsecuzione);
			ocs.execute();
			
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static void updateReleaseDiProgetto(DmalmRichiestaSupporto richiesta)
			throws DAOException {
		
		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			
			String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.UPDATE_RICH_SUPPORTO", 1);
			Object [] objRichSupp = richiesta.getObject(richiesta);
		    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
		    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
		    	ocs = (OracleCallableStatement)connection.prepareCall(sql);
			ocs.setObject(1, structObj);
			ocs.execute();
			
			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static DmalmRichiestaSupporto getRichiestaSupporto(Integer pk)
			throws DAOException {
		
		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		ResultSet rs = null;
		DmalmRichiestaSupporto bean = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.GET_RICHIESTA_SUPPORTO_BY_PK", 1);

		    	ocs = (OracleCallableStatement)connection.prepareCall(sql);
			ocs.registerOutParameter(1, OracleTypes.CURSOR);
			ocs.setInt(2, pk);
			ocs.execute();
			
			//return the result set
            rs = (ResultSet)ocs.getObject(1);
            
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
				bean.setAnnullato(rs.getString("ANNULLATO"));
				bean.setDataAnnullamento(rs.getTimestamp("DATA_ANNULLAMENTO"));
				bean.setDataStoricizzazione(rs.getTimestamp("DATA_STORICIZZAZIONE"));
				
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return bean;
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
