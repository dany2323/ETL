package lispa.schedulers.dao.target;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;
import lispa.schedulers.bean.target.fatti.DmalmRichiestaSupporto;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;

public class RichiestaSupportoOdsDAO {

	private static Logger logger = Logger
			.getLogger(RichiestaSupportoOdsDAO.class);

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

	public static void insert(List<DmalmRichiestaSupporto> staging_richieste,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			
	        String sql = QueryUtils.getCallProcedure("RICHIESTA_SUPPORTO.INSERT_RICHIESTA_SUPPORTO_ODS", 2);
		    for (DmalmRichiestaSupporto richiesta : staging_richieste) {
//		    		DmalmRichiestaSupporto r = new DmalmRichiestaSupporto(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", richiesta);
			    	Object [] objRichSupp = richiesta.getObject(richiesta);
			    	// Now Declare a descriptor to associate the host object type with the
			    	// record type in the database.
			    	StructDescriptor structDesc = StructDescriptor.createDescriptor(DmAlmConstants.DMALM_TARGET_SCHEMA.toUpperCase()+".RICHSUPPTYPE", connection);
			    	// Now create the STRUCT objects to associate the host objects
			    	// with the database records.
			    	STRUCT structObj = new STRUCT(structDesc, connection, objRichSupp);
			    	// Declare the callable statement.
			    	// This has to be of type OracleCallableStatement to use:
			    	// setOracleObject(
			    	// and
			    	// registerOutParameter(position,type,oracletype)
			    	ocs = (OracleCallableStatement)connection.prepareCall(sql);
				ocs.setObject(1, structObj);
				ocs.setTimestamp(2, dataEsecuzione);
				ocs.execute();
		    }

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}
	}

	public static List<DmalmRichiestaSupporto> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		DmalmRichiestaSupporto bean = null;
		List<DmalmRichiestaSupporto> richieste = new LinkedList<DmalmRichiestaSupporto>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallFunction("RICHIESTA_SUPPORTO.GET_ALL_RICHIESTA_SUPPORTO_ODS", 0);
			cs = connection.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
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
				bean.setDataStoricizzazione(rs.getTimestamp("DATA_STORICIZZAZIONE"));
				bean.setDataCaricamento(rs.getTimestamp("DATA_CARICAMENTO"));
				richieste.add(bean);
			}

			connection.commit();

		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if (cm != null)
				cm.closeConnection(connection);
		}

		return richieste;

	}

}

