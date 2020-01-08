package lispa.schedulers.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.manager.ConnectionManager;
import org.apache.log4j.Logger;

public class QueryUtils {
	private static Logger logger = Logger.getLogger(QueryUtils.class);

	public static String getCallFunction(String function, Integer numberInParameters) {
		
		String parameters = " ";
		while (numberInParameters > 0) {
			parameters += "?,";
			numberInParameters--;
		}
		
		logger.info("{? = call "+ function +"(" + parameters.substring(0, parameters.length()-1) + ")}");
		
		return "{? = call "+ function +"(" + parameters.substring(0, parameters.length()-1) + ")}";
	}
	
	public static String getCallProcedure(String procedure, Integer numberInParameters) {
		
		String parameters = " ";
		while (numberInParameters > 0) {
			parameters += "?,";
			numberInParameters--;
		}
		
		logger.info("{call "+ procedure +"(" + parameters.substring(0, parameters.length()-1) + ")}");
		
		return "{call "+ procedure +"(" + parameters.substring(0, parameters.length()-1) + ")}";
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
			try {
				if (cm != null) {
					cm.closeConnection(connection);
				}
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}
}
