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
import lispa.schedulers.bean.target.fatti.DmalmChecklist;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ChecklistOdsDAO {

	private static Logger logger = Logger.getLogger(ChecklistOdsDAO.class);
	public static void delete() throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
			String sql = QueryUtils.getCallProcedure("CHECKLIST.DELETE_CHECKLIST_ODS", 0);

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

	public static void insert(List<DmalmChecklist> staging_checklist,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		OracleCallableStatement ocs = null;
		List <Integer> listPk= new ArrayList<Integer>();
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);
	        String sql = QueryUtils.getCallProcedure("CHECKLIST.INSERT_CHECKLIST_ODS", 2);
		    for (DmalmChecklist checklist : staging_checklist) {
		    	if(listPk.contains(checklist.getDmalmChecklistPk()))
					logger.info("Trovata DmalmChecklistPk DUPLICATA!!!"+checklist.getDmalmChecklistPk());
				else{
					listPk.add(checklist.getDmalmChecklistPk());
			    	Object [] objChecklist = checklist.getObject(checklist, true);
			    	Struct structObj = connection.createStruct("CHECKLISTTYPE", objChecklist);
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

	public static List<DmalmChecklist> getAll() throws DAOException,
			SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		DmalmChecklist bean = null;
		List<DmalmChecklist> checklists = new LinkedList<DmalmChecklist>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryUtils.getCallFunction("CHECKLIST.GET_ALL_CHECKLIST_ODS", 0);
			cs = connection.prepareCall(sql);
			cs.registerOutParameter(1, OracleTypes.CURSOR);
			cs.setFetchSize(75);
			cs.execute();
            rs = (ResultSet)cs.getObject(1);

			while (rs.next()) {
				bean = new DmalmChecklist();
				bean.setDmalmChecklistPk(rs.getInt("DMALM_CHECKLIST_PK"));
				bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
				bean.setDmalmStatoWorkitemFk03(rs.getInt("DMALM_STATO_WORKITEM_FK_03"));
				bean.setCdChecklist(rs.getString("CD_CHECKLIST"));
				bean.setDsChecklist(rs.getString("DS_CHECKLIST"));
				bean.setIdRepository(rs.getString("ID_REPOSITORY"));
				bean.setDtCreazioneChecklist(rs.getTimestamp("DT_CREAZIONE_CHECKLIST"));
				bean.setDtRisoluzioneChecklist(rs.getTimestamp("DT_RISOLUZIONE_CHECKLIST"));
				bean.setDtChiusuraChecklist(rs.getTimestamp("DT_CHIUSURA_CHECKLIST"));
				bean.setDtModificaChecklist(rs.getTimestamp("DT_MODIFICA_CHECKLIST"));
				bean.setIdAutoreChecklist(rs.getString("ID_AUTORE_CHECKLIST"));
				bean.setDsAutoreChecklist(rs.getString("DS_AUTORE_CHECKLIST"));
				bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
				bean.setStgPk(rs.getString("STG_PK"));
				bean.setUri(rs.getString("URI_CHECKLIST"));
				checklists.add(bean);
			}
			
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

			throw new DAOException(e);
		} finally {
			if(cs!=null)
				cs.close();
			if (cm != null)
				cm.closeConnection(connection);
		}

		return checklists;
	}
}
