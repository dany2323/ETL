package lispa.schedulers.utils;


public class NumberUtils {
	//private static Logger logger = Logger.getLogger(NumberUtils.class);

	public static Double fromStringToDouble(String stringa) {
		Double numero = 0D;

		try {
			numero = Double.parseDouble(stringa.replace(",", "."));
		} catch (Exception e) {
			//logger.error("NumberUtils.fromStringToDouble - exception: " + e.getMessage() + ", return 0D");
			numero = 0D;
		}

		return numero;
	}
}
