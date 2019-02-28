package lispa.schedulers.dao;

import java.sql.Array;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;

import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.QueryManager;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.CURRENT_CURSOR_NUMBER;;


public class UtilsDAO {
	
	private static Logger logger = Logger.getLogger(UtilsDAO.class);
	
	public static int getActualOpenCursor() {
		ConnectionManager cm = null;
		Connection connection = null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		int result=-1;
		try{
			String query = QueryManager.getInstance().getQuery(CURRENT_CURSOR_NUMBER);
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			ps = connection.prepareStatement(query);

			rs = ps.executeQuery();
			
			while(rs.next()){
				result=rs.getInt(1);
			}
			return result;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			if(rs!=null)
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(ps!=null)
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(cm!=null)
				try {
					cm.closeConnection(connection);
				} catch (DAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;
	}
	public List<String> getLogFromStoredProcedureByTimestamp(String nameStoredProcedure, Timestamp dataEsecuzione) 
		throws Exception {
		
		ConnectionManager cm = null;
		Connection connection = null;
		Statement stmt = null;
		java.util.List<String> stringArray = null;
		Array array = null;
		String sql = "{call "+nameStoredProcedure+"(?, ?)}";
		try {
			
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			stmt = connection.createStatement();
			
			logger.debug("Inizio chiamata alla Stored Procedure "+nameStoredProcedure);
	        stmt.executeUpdate("begin dbms_output.enable(); end;");

	        CallableStatement call = connection.prepareCall(sql);
	        
            call.registerOutParameter(1, Types.ARRAY, "DBMSOUTPUT_LINESARRAY");
        		call.setObject(2, dataEsecuzione);
            call.execute();

            
            array = call.getArray(1);
            if (array != null) {
            		stringArray = Arrays.asList((String[]) array.getArray());
            }
		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
			if (array != null) {
        			array.free();
			}
			stmt.executeUpdate("begin dbms_output.disable(); end;");
			logger.debug("Fine chiamata alla Stored Procedure "+nameStoredProcedure);
		}
		
		return stringArray;
	}
	
	public static Integer getSequenceNextval(String sequenceName) throws Exception {
	
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs =null;
		Integer seqId = 0;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			String sqlSequenceName = "select "+sequenceName+" from dual";
			ps = connection.prepareStatement(sqlSequenceName);
			rs = ps.executeQuery(sqlSequenceName);
			if (rs.next()) {
				seqId = rs.getInt(1);
			}
		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		
		return seqId;
	}
	
	public static Integer getMaxValueByTable(String tableName, String fieldName) throws Exception {
		
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs=null;
		Integer maxValue = 0;
		
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();
			String sqlMaxValue = "select max("+fieldName+") from "+tableName;
			ps = connection.prepareStatement(sqlMaxValue);
			rs = ps.executeQuery(sqlMaxValue);
			if (rs.next()) {
				maxValue = rs.getInt(1);
			}
		} catch (SQLException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} catch (DAOException e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} catch (Exception e) {
			ErrorManager.getInstance().exceptionOccurred(true, e);
		} finally {
			if(rs!=null)
				rs.close();
			if(ps!=null)
				ps.close();
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
		
		return maxValue;
	}
}
