package lispa.schedulers.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import lispa.schedulers.queryimplementation.staging.oreste.QStgAmbienteTecnologico;
import lispa.schedulers.queryimplementation.staging.sgr.siss.current.QSissCurrentProject;

import com.mysema.query.Tuple;

public class LogUtils {
	protected static final String SEPARATOR = ", ";
	protected static final String NA = "n.a.";
	protected static final String EXCEPTION_LOGGING = "exception logging";
	private static final String EMPTY = "empty";

	public static String objectToString(Object object) {
		String string = "";
		try {
			string = exceptionAffectedObjectToString(object);
		} catch (Exception e) {
			string = EXCEPTION_LOGGING;
		}

		return string;
	}

	public static String ambienteTecnologicoOrestetoString(Tuple row) {

		QStgAmbienteTecnologico ambiente = QStgAmbienteTecnologico.stgAmbienteTecnologico;

		String record = "";

		if (row != null) {

			record += "[ AmbienteTecnologicoPK: "
					+ row.get(ambiente.idAmbTecnologicoPk) + "§ ";

			record += "Id: " + row.get(ambiente.idAmbienteTecnologico) + "§ ";

			record += "IdEdma: " + row.get(ambiente.idEdma) + "§ ";

			record += "IdEdmaPadre: " + row.get(ambiente.idEdmaPadre) + "§ ";

			record += "Nome: " + row.get(ambiente.nomeAmbienteTecnologico)
					+ "§ ";

			record += "SiglaModulo: " + row.get(ambiente.siglaModulo) + "§ ";

			record += "SiglaProdotto: " + row.get(ambiente.siglaProdotto)
					+ "§ ";

			record += "Clasf_ArchiRiferimento:"
					+ row.get(ambiente.clasfArchiRiferimento) + "§ ";

			record += "Clasf_Infrastrutture: "
					+ row.get(ambiente.clasfInfrastrutture) + "§ ";

			record += "Clasf_So: " + row.get(ambiente.clasfSo) + "§ ";

			record += "Clasf_TipiLayer: " + row.get(ambiente.clasfTipiLayer)
					+ "§ ";

			record += "Descrizione: "
					+ row.get(ambiente.descrAmbienteTecnologico) + "§ ";

			record += "VersioneSO: " + row.get(ambiente.versioneSo) + " ]";

		}

		return record;

	}

	public static String projectSGRtoString(Tuple row) {

		QSissCurrentProject project = QSissCurrentProject.sissCurrentProject;

		String record = "";

		if (row != null) {

			record += "[ ProjectDmAlmPK: "
					+ row.get(project.sissCurrentProjectPk) + "§ ";

			record += "Id : " + row.get(project.cId) + "§ ";

			record += "Pk : " + row.get(project.cPk) + "§ ";

			record += "Uri : " + row.get(project.cUri) + "§ ";

			record += "Name : " + row.get(project.cName) + "§ ";

			record += "Revisione : " + row.get(project.cRev) + "§ ";

			record += "DtStart : " + row.get(project.cStart) + "§ ";

			record += "DtFinish : " + row.get(project.cFinish) + "§ ";

			record += "Location : " + row.get(project.cLocation) + "§ ";

			record += "Active : " + row.get(project.cActive) + "§ ";

			record += "Delete : " + row.get(project.cDeleted) + "§ ";

			record += "IsLocal : " + row.get(project.cIsLocal) + "§ ";

			record += "LockWorkRecordsDate : "
					+ row.get(project.cLockworkrecordsdate) + "§ ";

			record += "TrackerPrefix : " + row.get(project.cTrackerprefix)
					+ "§ ";

			record += "FK Lead : " + row.get(project.fkLead) + "§ ";

			record += "FK ProjectGroup : " + row.get(project.fkProjectgroup)
					+ "§ ";

			record += "FK UriLead : " + row.get(project.fkUriLead) + "§ ";

			record += "FK UriProjectGroup : "
					+ row.get(project.fkUriProjectgroup) + "§ ";

			record += "Data Caricamento : " + row.get(project.dataCaricamento)
					+ " ] ";

		}

		return record;

	}

	@SuppressWarnings("unchecked")
	private static String exceptionAffectedObjectToString(Object object) {
		if (object == null)
			return EMPTY;

		String string = "";

		if (object instanceof String || object instanceof Integer
				|| object instanceof Long || object instanceof Double
				|| object instanceof Boolean || object instanceof BigDecimal
				|| object instanceof java.sql.Date || object instanceof Date
				|| object.getClass().isPrimitive()
				|| object.getClass().isEnum() || object instanceof Enum) {

			return object + "";
		}

		if (object instanceof Tuple) {
			Tuple t = (Tuple) object;
			return t.toString();
		}

		if (object instanceof Calendar) {
			return ((Calendar) object).getTime() + "";
		}

		for (Method method : object.getClass().getMethods()) {
			String methodAsString = method.getName();
			if ((methodAsString.startsWith("get")
					&& !methodAsString.equals("getClass") && method
					.getParameterTypes().length == 0)
					|| (methodAsString.startsWith("is") && method
							.getParameterTypes().length == 0)) {
				string += methodAsString.substring(methodAsString
						.startsWith("is") ? 2 : 3);
				string += ": ";
				try {
					if (method.getReturnType() == String.class
							|| method.getReturnType() == Integer.class
							|| method.getReturnType() == Integer.TYPE
							|| method.getReturnType() == Long.class
							|| method.getReturnType() == Long.TYPE
							|| method.getReturnType() == Double.class
							|| method.getReturnType() == Double.TYPE
							|| method.getReturnType() == Boolean.class
							|| method.getReturnType() == Boolean.TYPE
							|| method.getReturnType() == java.sql.Date.class
							|| method.getReturnType() == Date.class
							|| method.getReturnType() == Enum.class
							|| method.getReturnType().isEnum()
							|| method.getReturnType() == BigDecimal.class) {
						String value = method.invoke(object, new Object[0])
								+ "";

						if (methodAsString.toUpperCase().contains("PAN")
								&& value.length() > 4) {
							value = value.substring(value.length() - 4);
						}
						string += value;
					} else if (method.getReturnType() == Calendar.class) {
						string += ((Calendar) method.invoke(object,
								new Object[0])).getTime();
					} else if (method.getReturnType() == List.class) {
						string += "("
								+ listToString((List) method.invoke(object,
										new Object[0])) + ")";
					} else {
						string += "("
								+ exceptionAffectedObjectToString(method
										.invoke(object, new Object[0])) + ")";
					}
				} catch (IllegalArgumentException e) {
					string += NA;
				} catch (IllegalAccessException e) {
					string += NA;
				} catch (InvocationTargetException e) {
					string += NA;
				}
				string += SEPARATOR;
			}
		}
		if (string.length() > SEPARATOR.length()) {
			string = string.substring(0, string.length() - SEPARATOR.length());
		}

		return string;
	}

	public static String pivotToString(Object pivot) {
		String string = "";
		try {
			string = exceptionAffectedPivotToString(pivot);
		} catch (Exception e) {
			string = EXCEPTION_LOGGING;
		}

		return string;
	}

	private static String exceptionAffectedPivotToString(Object pivot) {
		if (pivot == null)
			return EMPTY;

		return pivot.getClass().getName() + ": "
				+ exceptionAffectedObjectToString(pivot);
	}

	public static <T> String listToString(List<T> objects) {
		String string = "";
		try {
			string = exceptionAffectedListToString(objects);
		} catch (Exception e) {
			string = EXCEPTION_LOGGING;
		}

		return string;
	}

	private static <T> String exceptionAffectedListToString(List<T> objects) {
		if (objects == null)
			return EMPTY;

		String string = "";

		for (Object object : objects) {
			string += exceptionAffectedObjectToString(object) + " ";
		}

		return string;
	}
}
