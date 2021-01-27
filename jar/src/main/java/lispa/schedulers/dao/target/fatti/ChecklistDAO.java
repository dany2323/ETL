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
import lispa.schedulers.bean.target.fatti.DmalmChecklist;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.QueryUtils;
import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

public class ChecklistDAO {

	private static Logger logger = Logger.getLogger(ChecklistDAO.class);

	public static List<DmalmChecklist> getAllChecklist(
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		DmalmChecklist bean = null;
		List<DmalmChecklist> checklists = new LinkedList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			logger.info("START - ChecklistDAO.getAllChecklist");
			String sql = QueryUtils.getCallFunction(
					"CHECKLIST.GET_ALL_CHECKLIST", 1);
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
				}
			}
			logger.info("STOP - ChecklistDAO.getAllChecklist");
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {

			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return checklists;
	}

//	public static List<DmalmChecklist> getChecklist(DmalmChecklist checklist)
//			throws DAOException, SQLException {
//
//		ConnectionManager cm = null;
//		Connection connection = null;
//		DmalmChecklist bean = null;
//		List<DmalmChecklist> checklists = new LinkedList<>();
//
//		try {
//			cm = ConnectionManager.getInstance();
//			connection = cm.getConnectionOracle();
//
//			String sql = QueryUtils.getCallFunction(
//					"CHECKLIST.GET_CHECKLIST", 1);
//			Object[] objChecklist = checklist.getObject(checklist, true);
//			Struct structObj = connection.createStruct("CHECKLISTTYPE",
//					objChecklist);
//			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
//					.prepareCall(sql);) {
//				ocs.registerOutParameter(1, OracleTypes.CURSOR);
//				ocs.setObject(2, structObj);
//				ocs.execute();
//
//				try (ResultSet rs = (ResultSet) ocs.getObject(1);) {
//					while (rs.next()) {
//						// Elabora il risultato
//						bean = new DmalmChecklist();
//						bean.setDmalmChecklistPk(rs.getInt("DMALM_CHECKLIST_PK"));
//						bean.setDmalmProjectFk02(rs.getInt("DMALM_PROJECT_FK_02"));
//						bean.setDmalmStatoWorkitemFk03(rs.getInt("DMALM_STATO_WORKITEM_FK_03"));
//						bean.setCdChecklist(rs.getString("CD_CHECKLIST"));
//						bean.setDescrizioneChecklist(rs.getString("DESCRIZIONE_CHECKLIST"));
//						bean.setIdRepository(rs.getString("ID_REPOSITORY"));
//						bean.setDtCreazioneChecklist(rs.getTimestamp("DT_CREAZIONE_CHECKLIST"));
//						bean.setDtRisoluzioneChecklist(rs.getTimestamp("DT_RISOLUZIONE_CHECKLIST"));
//						bean.setDtChiusuraChecklist(rs.getTimestamp("DT_CHIUSURA_CHECKLIST"));
//						bean.setDtModificaChecklist(rs.getTimestamp("DT_MODIFICA_CHECKLIST"));
//						bean.setIdAutoreChecklist(rs.getString("ID_AUTORE_CHECKLIST"));
//						bean.setDsAutoreChecklist(rs.getString("DS_AUTORE_CHECKLIST"));
//						bean.setDmalmUserFk06(rs.getInt("DMALM_USER_FK_06"));
//						bean.setStgPk(rs.getString("STG_PK"));
//						bean.setUri(rs.getString("URI_CHECKLIST"));
//						checklists.add(bean);
//					}
//				}
//			}
//		} catch (Exception e) {
//			ErrorManager.getInstance().exceptionOccurred(true, e);
//
//		} finally {
//			if (cm != null) {
//				cm.closeConnection(connection);
//			}
//		}
//		return checklists;
//	}

	public static void insertChecklist(DmalmChecklist checklist,
			Timestamp dataEsecuzione) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure(
					"CHECKLIST.INSERT_CHECKLIST", 2);
			Object[] objChecklist = checklist.getObject(checklist, true);
			Struct structObj = connection.createStruct("CHECKLISTTYPE",
					objChecklist);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.setObject(1, structObj);
				ocs.setTimestamp(2, checklist.getDtModificaChecklist());
				ocs.setTimestamp(3, dataEsecuzione);
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

	public static void updateRank(DmalmChecklist checklist,
			Double rank_value) throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			String sql = QueryUtils
					.getCallProcedure("CHECKLIST.UPDATE_RANK", 2);
			Object[] objChecklist = checklist.getObject(checklist, true);
			Struct structObj = connection.createStruct("CHECKLISTTYPE",
					objChecklist);
			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.setObject(1, structObj);
				ocs.setDouble(2, rank_value);
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

	public static void insertUpdateChecklist(Timestamp dataEsecuzione,
			DmalmChecklist checklist, boolean pkValue)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure("CHECKLIST.INSERT_UPDATE_CHECKLIST_DT_CAM", 2);
			Object[] objChecklist = checklist.getObject(checklist, pkValue);
			Struct structObj = connection.createStruct("CHECKLISTTYPE",	objChecklist);
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

	public static void updateChecklist(DmalmChecklist checklist)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(false);

			String sql = QueryUtils.getCallProcedure(
					"CHECKLIST.UPDATE_CHECKLIST", 1);
			Object[] objChecklist = checklist.getObject(checklist, true);
			Struct structObj = connection.createStruct("CHECKLISTTYPE", objChecklist);
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

	public static DmalmChecklist getChecklist(Integer pk)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		DmalmChecklist bean = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = QueryUtils.getCallFunction(
					"CHECKLIST.GET_CHECKLIST_BY_PK", 1);

			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.registerOutParameter(1, OracleTypes.CURSOR);
				ocs.setInt(2, pk);
				ocs.execute();

				try (ResultSet rs = (ResultSet) ocs.getObject(1);) {

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

	public static List<DmalmChecklist> getChecklist(Integer pk_proj, Integer typeQuery)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;
		List<DmalmChecklist> checklists = new LinkedList<>();

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql = "";
			switch (typeQuery) {
				case 0 :
					sql = QueryUtils.getCallFunction(
							"CHECKLIST.GET_PK_CHECKLIST_BY_PK_HIS", 1);
					break;
				case 1 :
					sql = QueryUtils.getCallFunction(
							"CHECKLIST.GET_PK_CHECKLIST_BY_PK_HIS_SUB",	1);
					break;
				default :
					break;
			}

			try (OracleCallableStatement ocs = (OracleCallableStatement) connection
					.prepareCall(sql);) {
				ocs.registerOutParameter(1, OracleTypes.CURSOR);
				ocs.setInt(2, pk_proj);
				ocs.execute();

				try (ResultSet rs = (ResultSet) ocs.getObject(1);) {

					while (rs.next()) {
						DmalmChecklist bean = new DmalmChecklist();
						bean.setIdRepository(rs.getString("ID_REPOSITORY"));
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
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}

		return checklists;
	}

	// verifica se una Checklist:
	// non è stata inserita, quindi effettua una insert in tabella
	// è stata inserita, quindi aggiorna il rank, effettua una update della
	// Checklist per storicizzarla
	// ed infine effettua una insert con i i nuovi dati
	public static void checkAlteredRowChecklist(List<DmalmChecklist> checklist, Timestamp dataEsecuzione)
			throws DAOException, SQLException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			
			String sql = QueryUtils.getCallProcedure(
					"CHECKLIST.CHECK_ALTERED_ROW_CHECKLIST", 3);
			for (DmalmChecklist check : checklist) {
				Object[] objChecklist = check.getObject(check, true);
				Struct structObj = connection.createStruct("CHECKLISTTYPE",
						objChecklist);
				try (OracleCallableStatement ocs = (OracleCallableStatement) connection
						.prepareCall(sql);) {
					ocs.setObject(1, structObj);
					ocs.setTimestamp(2, dataEsecuzione);
					ocs.setString(3, DmAlmConstants.DMALM_TARGET_SCHEMA);
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