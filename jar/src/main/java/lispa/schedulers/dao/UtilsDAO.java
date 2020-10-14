package lispa.schedulers.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.sgr.DmAlmCFIdWorkitem;
import lispa.schedulers.queryimplementation.staging.sgr.DmAlmIdWorkitem;
import lispa.schedulers.queryimplementation.staging.sgr.DmAlmTemplateWorkitem;
import lispa.schedulers.utils.QueryUtils;

public class UtilsDAO {
	
	private static Logger logger = Logger.getLogger(UtilsDAO.class);
	
	public static Integer getSequenceNextval(String sequenceName) throws Exception {
	
		ConnectionManager cm = null;
		Connection connection = null;
		Integer seqId = 0;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			String sqlSequenceName = "select "+sequenceName+" from dual";
			try(PreparedStatement ps = connection.prepareStatement(sqlSequenceName);){
				try(ResultSet rs = ps.executeQuery(sqlSequenceName);){
					if (rs.next()) {
						seqId = rs.getInt(1);
					}
				}
			}
		
			} catch (Exception e) {
				ErrorManager.getInstance().exceptionOccurred(true, e);
			} finally {
				cm.closeConnection(connection);
			}
		
		return seqId;
	}
	
	public static Integer getMaxValueByTable(String tableName, String fieldName) throws Exception {
		
		ConnectionManager cm = null;
		Connection connection = null;
		Integer maxValue = 0;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			String sqlMaxValue = "select max("+fieldName+") from "+tableName;
			try(PreparedStatement ps = connection.prepareStatement(sqlMaxValue);){
				try(ResultSet rs = ps.executeQuery(sqlMaxValue);){
					if (rs.next()) {
						maxValue = rs.getInt(1);
					}
				}
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			cm.closeConnection(connection);
		}
		
		return maxValue;
	}
	public static void killsBOSessions() throws DAOException {
		ConnectionManager cm = null;
		Connection conn = null;
		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			String procedure=QueryUtils.getCallProcedure(DmAlmConstants.STORED_PROCEDURE_KILL_BO_SESSIONS,0);
			try(CallableStatement callableStatement=conn.prepareCall(procedure) ){
				callableStatement.execute();
			} catch (SQLException e) {
				logger.debug(e.getMessage());
			} finally {
				cm.closeConnection(conn);
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			cm.closeConnection(conn);
		}
	}
	
	public static void deleteDatiFonteTabelle(String fonte) throws DAOException{
		ConnectionManager cm = null;
		Connection conn = null;
		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			String procedure=QueryUtils.getCallProcedure(DmAlmConstants.DELETE_DATI_FONTE_TABELLE, 1);
			try(CallableStatement callableStatement=conn.prepareCall(procedure)){
				callableStatement.setString(1, fonte);
				callableStatement.execute();
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			} finally {
				cm.closeConnection(conn);
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			cm.closeConnection(conn);
		}
	}
	
	public static void setCaricamentoFonte(String fonte, String statoCaricamento) throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement psFonte = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql_fonte = "UPDATE "+DmAlmConstants.DMALM_CARICAMENTO_FONTE+" SET STATO_CARICAMENTO = ? WHERE COD_FONTE = ?";
			psFonte = connection.prepareStatement(sql_fonte);

			psFonte.setString(1, statoCaricamento);
			psFonte.setString(2, fonte);
			
			psFonte.executeUpdate();

			if (psFonte != null) {
				psFonte.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			cm.closeConnection(connection);
		}
	}
	
	public static List<String> getIdWorkitem() throws DAOException {
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection oracle = null;
		List<String> listIdWorkitem = new ArrayList<String>();
		DmAlmIdWorkitem stg_DmAlmIdWorkitem = DmAlmIdWorkitem.dmAlmIdWorkitem;
		try {
			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(oracle, dialect);

			listIdWorkitem = query.from(stg_DmAlmIdWorkitem)
					.where(stg_DmAlmIdWorkitem.flagCaricamento.eq("Y"))
					.list(stg_DmAlmIdWorkitem.idWorkitem);
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			cm.closeConnection(oracle);
		}
		
		return listIdWorkitem;
	}
	
	public static List<String> getCfByWorkitem(String type) throws DAOException {
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection oracle = null;
		List<String> listCFIdWorkitem = new ArrayList<String>();
		DmAlmCFIdWorkitem stg_DmAlmCFIdWorkitem = DmAlmCFIdWorkitem.dmAlmCFIdWorkitem;
		try {
			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(oracle, dialect);

			listCFIdWorkitem = query.from(stg_DmAlmCFIdWorkitem)
					.where(stg_DmAlmCFIdWorkitem.flagCaricamento.eq("Y"))
					.where(stg_DmAlmCFIdWorkitem.idWorkitem.equalsIgnoreCase(type))
					.list(stg_DmAlmCFIdWorkitem.customField);
		
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			cm.closeConnection(oracle);
		}
		
		return listCFIdWorkitem;
	}
	
	public static String getTemplateByWorkitem(String type) throws DAOException {
		SQLTemplates dialect = new HSQLDBTemplates();
		ConnectionManager cm = null;
		Connection oracle = null;
		List<String> listTemplateWorkitem = new ArrayList<String>();
		String templateWorkitem = "";
		DmAlmTemplateWorkitem stg_DmAlmTemplateWorkitem = DmAlmTemplateWorkitem.dmAlmTemplateWorkitem;
		try {
			cm = ConnectionManager.getInstance();
			oracle = cm.getConnectionOracle();
			SQLQuery query = new SQLQuery(oracle, dialect);

			listTemplateWorkitem = query.from(stg_DmAlmTemplateWorkitem)
					.where(stg_DmAlmTemplateWorkitem.flagCaricamento.eq("Y"))
					.where(stg_DmAlmTemplateWorkitem.idWorkitem.equalsIgnoreCase(type))
					.list(stg_DmAlmTemplateWorkitem.template);
		
			if (listTemplateWorkitem.size() > 0) {
				templateWorkitem = listTemplateWorkitem.get(0);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new DAOException(e);
		} finally {
			cm.closeConnection(oracle);
		}
		
		return templateWorkitem;
	}
}
