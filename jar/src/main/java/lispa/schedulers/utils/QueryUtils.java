package lispa.schedulers.utils;

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
		
		return "{call "+ procedure +"(" + parameters.substring(0, parameters.length()-1) + ")}";
	}
}
