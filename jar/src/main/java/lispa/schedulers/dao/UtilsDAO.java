package lispa.schedulers.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.log4j.Logger;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
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
				if (cm != null) {
					cm.closeConnection(connection);
				}
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
			if (cm != null) {
				cm.closeConnection(connection);
			}
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
			} catch (Exception e) {
				logger.debug(e.getMessage());
			}
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
			throw new DAOException(e);
		}
		
	}
}
