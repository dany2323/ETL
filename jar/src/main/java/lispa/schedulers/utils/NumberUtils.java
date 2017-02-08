package lispa.schedulers.utils;

import lispa.schedulers.exception.DAOException;

public class NumberUtils {
	//private static Logger logger = Logger.getLogger(NumberUtils.class);
	
	private static final int MASKED_INT_VALUE = -1;

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
	
	public static Integer getMaskedValue(Integer value) throws DAOException {
		if (value == null) {
			return null;
		}
		if(ConfigUtils.isSviluppo()){
			return new Integer(MASKED_INT_VALUE);
		} else {
			return value;
		}
	}
	
	public static Integer getMaskedValue(String value) throws DAOException {
		if (value == null) {
			return null;
		}
		if (value.equals("")) {
			return null;
		}
		if(ConfigUtils.isSviluppo()){
			return new Integer(MASKED_INT_VALUE);
		} else {
			try {
				return Integer.valueOf(value);
			}
			catch (NumberFormatException ex) {
				return null;
			}
		}
	}
	
	public static int getMaskedValue(int value) throws DAOException {
		if(ConfigUtils.isSviluppo()){
			return MASKED_INT_VALUE;
		} else {
			return value;
		}
	}
}
