package lispa.schedulers.utils;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StringUtils {
	public static String truncate(String value, int length) {
		if (value != null && value.length() > length)
			value = value.substring(0, length);
		return value;
	}

	public static String removeBlankInside(String value) {
		if (hasText(value)) {
			value = value.replaceAll("\\s+", "").trim();
		}

		return value;
	}

	public static String toUppercase(String value) {
		if (hasText(value))
			value = value.toUpperCase();

		return value;
	}

	public static boolean hasText(String value) {
		boolean result = false;

		if (value != null && value.trim().length() > 0)
			result = true;

		return result;
	}

	public static String ListToString(List<String> list) throws Exception {
		StringBuilder sb = null;
		int index = 0;
		String sm = "";

		if (list != null && list.size() > 0) {
			index = 0;
			sm = "";
			sb = new StringBuilder();

			for (Object obj : list) {
				sb.append(obj.toString());
				sb.append(", ");
			}

			sm = sb.toString();
			index = sm.toString().lastIndexOf(',');

			if (index != -1) {
				sm = sm.substring(0, index);
			}
		}
		return sb != null ? sm : null;
	}

	public static boolean checkStringAnnullamentoFormat(String annullamento,
			Logger logger) throws Exception {
		if (annullamento != null && !annullamento.equals("")) {
			if (annullamento.toLowerCase().contains("ANNULLATO".toLowerCase())) {
				if (annullamento.toLowerCase().contains(
						"ASCENDENTE".toLowerCase())) {
					if (annullamento.indexOf("#", 1) != -1
							&& annullamento.indexOf("#", 37) != -1)
						annullamento = annullamento.substring(
								annullamento.indexOf("#", 1) + 1,
								annullamento.indexOf("#", 37) + 1);
					else
						annullamento = annullamento.substring(36, 45);
				} else {
					if (annullamento.indexOf("#", 1) != -1
							&& annullamento.indexOf("#", 24) != -1)
						annullamento = annullamento.substring(
								annullamento.indexOf("#", 1) + 1,
								annullamento.indexOf("#", 24) + 1);
					else
						annullamento = annullamento.substring(23, 32);
				}

				if (!annullamento.endsWith("#")) {
					return false;
				} else if (!annullamento.substring(0, 1).equals("#")) {
					return false;
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static String getTestata(String cod_intervento) {
		String testata = "";

		if (cod_intervento != null && cod_intervento.indexOf(",") != -1) {
			testata = cod_intervento.substring(0, cod_intervento.indexOf(","));

		} else {
			testata = null;
		}

		return testata;
	}

	public static String getLinea(String cod_intervento) {
		String linea = "";

		if (cod_intervento != null && cod_intervento.indexOf(",") != -1) {
			linea = cod_intervento.substring(cod_intervento.indexOf(",") + 1,
					cod_intervento.length());

		} else {
			linea = null;
		}

		return linea;
	}

	public static boolean matchRegex(String stringa, String regex) {
		if (stringa != null) {
			Pattern p = Pattern.compile(regex);
			return p.matcher(stringa).matches();
		}
		return false;
	}

	public static boolean findRegex(String stringa, String regex) {
		Pattern p = Pattern.compile(regex);
		return p.matcher(stringa).find();
	}

	public static String[] splitByRegex(String stringa, String regex) {
		Pattern p = Pattern.compile(regex);
		String[] splittedstring = new String[1];
		splittedstring[0] = stringa;
		if (p.matcher(stringa).find()) {
			splittedstring = stringa.split(regex);
		}
		return splittedstring;

	}

	public static int getCloserIndex(String[] s, String marker,
			String regexByTemplate) {
		int target_index = 0;
		int i = 0;
		for (String uo : s) {

			if (StringUtils.matchRegex(uo, marker))
				break;
			if (StringUtils.findRegex(uo, regexByTemplate))
				target_index = i;
			i++;
		}

		return target_index;
	}

}
