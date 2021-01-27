package lispa.schedulers.utils;

import java.sql.Connection;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import com.mysema.query.sql.OracleTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.utils.QDmalmConfig;

public class ConfigUtils {
	
	private static Logger logger = Logger.getLogger(ConfigUtils.class);
	
	private static String getEnvironment() throws DAOException {
		String environment = "";
		ConnectionManager cm = ConnectionManager.getInstance();
		Connection connection = null;

		try{
			connection = cm.getConnectionOracle();
			connection.setAutoCommit(true);
			
			SQLTemplates dialect = new OracleTemplates(); // SQL-dialect
			
			environment = new SQLQuery(connection, dialect)
					.from(QDmalmConfig.dmalmConfig)
					.where(QDmalmConfig.dmalmConfig.chiave.eq("env"))
					.singleResult(QDmalmConfig.dmalmConfig.valore);			
		
		} catch (SQLException | DAOException e) {
			logger.error(e.getMessage(), e);
		} finally {
			cm.closeConnection(connection);
		}
		
		return environment;
		
	}
	
	public static boolean isSviluppo() throws DAOException {
		return getEnvironment().equals("svi");
	}
	
	public static boolean isPreProd() throws DAOException {
		return getEnvironment().equals("pre-prod");
	}
	
	public static boolean isProd() throws DAOException {
		return getEnvironment().equals("prod");
	}
}