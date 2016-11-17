package lispa.schedulers.utils;

import java.sql.Timestamp;
import java.util.Date;

public class BeanUtils {

	public static boolean areDifferent(Object attr1, Object attr2) {
		if (attr1 != null && attr2 != null) {
			if (attr1 instanceof Timestamp && attr2 instanceof Timestamp) {
				return !((Timestamp) attr1).equals((Timestamp) attr2);
			} else if (attr1 instanceof Timestamp && attr2 instanceof Date) {
				Date data2 = (Date) attr2;
				Timestamp timestamp2 = new Timestamp(data2.getTime());
				return !((Timestamp) attr1).equals(timestamp2);
			} else if (attr1 instanceof String) {
				// confronto case sensitive
				return !((String) attr1).equals((String) attr2);
			} else if (attr1 instanceof Integer) {
				return !((Integer) attr1).equals(((Integer) attr2));
			} else if (attr1 instanceof Short) {
				if (attr2 instanceof Integer) {
					Short attr1Short = (Short) attr1;
					Integer attr1Integer = new Integer(attr1Short);

					return !attr1Integer.equals((Integer) attr2);
				} else
					return !((Short) attr1).equals(((Short) attr2));
			} else if (attr1 instanceof Boolean) {
				return !((Boolean) attr1).equals(((Boolean) attr2));
			} else if (attr1 instanceof Date) {
				return !((Date) attr1).equals(((Date) attr2));
			} else if (attr1 instanceof Float) {
				return !((Float) attr1).equals(((Float) attr2));
			}
		}
		return (attr1 == null ^ attr2 == null);
	}
}
