package lispa.schedulers.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.DataEsecuzione;
import lispa.schedulers.manager.QueryManager;
import lispa.schedulers.utils.enums.Workitem_Type.EnumWorkitemType;
import org.apache.log4j.Logger;

import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;

public class QueryUtils {
	private static Logger logger = Logger.getLogger(QueryUtils.class);
	
	public static int getCountByWIType(EnumWorkitemType type) {
		String wi = type.toString();
		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		int count = 10;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			ps = connection.prepareStatement(QueryManager.getInstance().getQuery(DmAlmConstants.COUNT_WORKITEM_BY_TYPE));
			ps.setString(1, wi);
			ps.setString(3, wi);
			ps.setTimestamp(2, DataEsecuzione.getInstance().getDataEsecuzione());
			ps.setTimestamp(4, DataEsecuzione.getInstance().getDataEsecuzione());

			rs = ps.executeQuery();

			if (rs.next()) {
				count = rs.getInt("cardinalita");
				logger.debug("Count by Type eseguito, # rows : " + count + "");
			}

			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			try {
				if (cm != null) {
					cm.closeConnection(connection);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		return count;
	}

	public static int getCountAttachments() {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement psCount = null;
		ResultSet rs = null;
		int count = 10;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql_count = QueryManager.getInstance().getQuery(DmAlmConstants.COUNT_ATTACHMENTS);
			psCount = connection.prepareStatement(sql_count);

			psCount.setTimestamp(1, DataEsecuzione.getInstance()
					.getDataEsecuzione());
			psCount.setTimestamp(2, DataEsecuzione.getInstance()
					.getDataEsecuzione());

			rs = psCount.executeQuery();

			if (rs.next()) {
				count = rs.getInt("cardinalita");
				logger.debug("Count by Type eseguito, # rows : " + count + "");
			}
			
			if (rs != null) {
				rs.close();
			}
			if (psCount != null) {
				psCount.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			try {
				if (cm != null) {
					cm.closeConnection(connection);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		return count;
	}

	public static int getCountHyperlinks() {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement psCount = null;
		ResultSet rs = null;
		int count = 10;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql_count = QueryManager.getInstance().getQuery(
					DmAlmConstants.COUNT_HYPERLINKS);
			psCount = connection.prepareStatement(sql_count);

			psCount.setTimestamp(1, DataEsecuzione.getInstance()
					.getDataEsecuzione());
			psCount.setTimestamp(2, DataEsecuzione.getInstance()
					.getDataEsecuzione());

			rs = psCount.executeQuery();

			if (rs.next()) {
				count = rs.getInt("cardinalita");
				logger.debug("Count by Type eseguito, # rows : " + count + "");
			}
			
			if (rs != null) {
				rs.close();
			}
			if (psCount != null) {
				psCount.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			try {
				if (cm != null) {
					cm.closeConnection(connection);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		return count;
	}

	public static int getCountLinkedWorkitems() {

		ConnectionManager cm = null;
		Connection connection = null;
		PreparedStatement psCount = null;
		ResultSet rs = null;
		int count = 10;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			String sql_count = QueryManager.getInstance().getQuery(
					DmAlmConstants.COUNT_LINKED_WORKITEMS);
			psCount = connection.prepareStatement(sql_count);

			psCount.setTimestamp(1, DataEsecuzione.getInstance()
					.getDataEsecuzione());
			psCount.setTimestamp(2, DataEsecuzione.getInstance()
					.getDataEsecuzione());

			rs = psCount.executeQuery();

			if (rs.next()) {
				count = rs.getInt("cardinalita");
				logger.debug("Count by Type eseguito, # rows : " + count + "");
			}
			
			if (rs != null) {
				rs.close();
			}
			if (psCount != null) {
				psCount.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());

		} finally {
			try {
				if (cm != null) {
					cm.closeConnection(connection);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		return count;
	}
	
	public static String getCallFunction(String function, Integer numberInParameters) {
		
		String parameters = " ";
		while (numberInParameters > 0) {
			parameters += "?,";
			numberInParameters--;
		}
		return "{? = call "+ function +"(" + parameters.substring(0, parameters.length()-1) + ")}";
	}
	
	public static String getCallProcedure(String procedure, Integer numberInParameters) {
		
		String parameters = " ";
		while (numberInParameters > 0) {
			parameters += "?,";
			numberInParameters--;
		}
		return "{call "+ procedure +"(" + parameters.substring(0, parameters.length()-1) + ")}";
	}
	
	public static SQLQuery queryConnOracle(Connection connOracle,
			OracleTemplates dialect) {
		return new SQLQuery(connOracle, dialect);
	}
}
