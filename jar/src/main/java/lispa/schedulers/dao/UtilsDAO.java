package lispa.schedulers.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.utils.DateUtils;
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
				cm.closeQuietly(connection);
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
			cm.closeQuietly(connection);
		}
		
		return maxValue;
	}
	public static void killsBOSessions() {
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
				cm.closeQuietly(conn);
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			cm.closeQuietly(conn);
		}
	}
	
	public static void deleteDatiFonteTabelle() {
		ConnectionManager cm = null;
		Connection conn = null;
		try {
			cm = ConnectionManager.getInstance();
			conn = cm.getConnectionOracle();
			String procedure=QueryUtils.getCallProcedure(DmAlmConstants.DELETE_DATI_FONTE_TABELLE, 1);
			try(CallableStatement callableStatement=conn.prepareCall(procedure) ){
				callableStatement.setTimestamp(1, DateUtils.addMonthsToTimestamp(DataEsecuzione.getInstance().getDataEsecuzione(), -1));
				callableStatement.execute();
			} catch (Exception e) {
				logger.error(e.getMessage());
			} finally {
				cm.closeQuietly(conn);
			}
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			cm.closeQuietly(conn);
		}
	}
	
	public static void setCaricamentoFonte(String fonte, String statoCaricamento) {
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
			logger.error(e.getMessage());

		} finally {
			cm.closeQuietly(connection);
		}
	}
}
