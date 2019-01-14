package lispa.schedulers.utils;

import java.sql.Connection;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.queryimplementation.staging.QDmalmEsitiCaricamenti;

import org.apache.log4j.Logger;

import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLQuery;
import com.mysema.query.sql.SQLTemplates;

public class DateUtils {

	private static SQLTemplates dialect = new HSQLDBTemplates();
	private static Logger logger = Logger.getLogger(DateUtils.class);

	public static boolean checkDataAnnullamentoFormat(String annullamento,
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

				annullamento = annullamento.replaceAll("#", "");

				if (annullamento.length() == 8) {

					SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
					try {

						if (annullamento.length() != 8)
							throw new ParseException("", 0);

						format.setLenient(false);
						format.parse(annullamento);

						return true;
					} catch (ParseException exception) {

						logger.debug(exception);
						return false;
					}
				} else {

					return false;
				}
			} else {

				return false;
			}
		} else {

			return false;
		}
	}

	public static Date getDataAnnullamento(String annullamento, Logger logger)
			throws Exception {

		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		Date dataAnnullamento = null;

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

				annullamento = annullamento.replaceAll("#", "");

				if (annullamento.length() == 8) {
					try {
						if (annullamento.length() != 8) {
							throw new ParseException("", 0);
						}
						format.setLenient(false);
						dataAnnullamento = format.parse(annullamento);
					} catch (ParseException e) {

						return dataAnnullamento;
					}
				}
			}
		}

		return dataAnnullamento;
	}

	public static Date stringToDate(String data, String ft) {
		SimpleDateFormat format = new SimpleDateFormat(ft);
		Date dataFineValidita = null;
		
		if (StringUtils.hasText(data)) {
			try {
				format.setLenient(false);
				dataFineValidita = format.parse(data);
			} catch (ParseException exception) {

			}
		}

		return dataFineValidita;
	}

	public static Timestamp stringToTimestamp(String tsParameter) {
		// tsParameter in the format yyyy-mm-dd hh:mm:ss[.f...]

		Timestamp timeStamp = null;

		if (StringUtils.hasText(tsParameter)) {
			try {
				timeStamp = Timestamp.valueOf(tsParameter.trim().replace("/",
						"-"));
			} catch (Exception exception) {
				logger.error("DateUtils.stringToTimestamp - tsParameter: " + tsParameter + ", exception: " + exception);
			}
		}

		return timeStamp;
	}

	public static Timestamp stringToTimestamp(String data, String ft) {
		SimpleDateFormat format = new SimpleDateFormat(ft);
		
		Timestamp timeStamp = null;
		try {
			format.setLenient(false);
			timeStamp = new Timestamp(format.parse(data).getTime());
		} catch (ParseException exception) {
			logger.error("DateUtils.stringToTimestamp - data: " + data + ", ft: " + ft + ", exception: " + exception);
		}

		return timeStamp;
	}

	public static String dateToString(Date date, String format) {

		if (date == null) {
			return null;
		}

		SimpleDateFormat formatter = new SimpleDateFormat(format);

		return formatter.format(date);
	}

	public static Timestamp dateToTimestamp(Date date) {
		if (date == null) 
			return null;
		
		Timestamp timeStamp = null;
		try {
			timeStamp = new Timestamp(date.getTime());
		} catch (Exception exception) {
		}

		return timeStamp;
	}
	
	public static Date addDaysToDate(java.util.Date date, int days)
			throws Exception {
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(GregorianCalendar.DAY_OF_YEAR, days);

		date = gc.getTime();

		return date;
	}

	public static Date addSecondsToDate(java.util.Date date, int seconds)
			{
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(GregorianCalendar.SECOND, seconds);

		date = gc.getTime();

		return date;
	}

	public static Date setTimeToDate(Date date, int hour, int minute, int second) {
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.set(Calendar.HOUR_OF_DAY, hour);
		gc.set(Calendar.MINUTE, minute);
		gc.set(Calendar.SECOND, second);

		return gc.getTime();
	}

	public static Timestamp addDaysToTimestamp(Timestamp t, int days)
			throws Exception {
		String s = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(t);
		Date d = stringToDate(s, "yyyy-MM-dd hh:mm:ss");
		d = addDaysToDate(d, days);
		d = setTimeToDate(d, 23, 59, 59);
		return new Timestamp(d.getTime());
	}

	public static Timestamp addSecondsToTimestamp(Timestamp t, int seconds) throws DAOException
			{
		String s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(t);
		Date d = stringToDate(s, "yyyy-MM-dd HH:mm:ss");
		try {
			d = addSecondsToDate(d, seconds);
		} catch (Exception e) {
			throw new DAOException("Impossibile convertire in data");
		}
		return new Timestamp(d.getTime());
	}

	public static Date addMonthsToDate(java.util.Date date, int months)
			throws Exception {
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(GregorianCalendar.MONTH, months);

		date = gc.getTime();

		return date;
	}

	public static Timestamp addMonthsToTimestamp(Timestamp ts, int months)
			throws Exception {
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(ts);
		gc.add(GregorianCalendar.MONTH, months);

		return new Timestamp(gc.getTimeInMillis());
	}

	public static Date addYearsToDate(java.util.Date date, int years)
			throws Exception {
		GregorianCalendar gc = new GregorianCalendar();

		gc.setTime(date);
		gc.add(GregorianCalendar.YEAR, years);

		date = gc.getTime();

		return date;
	}

	public static Timestamp getFistDayMonth(Timestamp ts) throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.setTime(ts);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);

		return new Timestamp(cal.getTimeInMillis());
	}

	/**
	 * @param t
	 * @return il Timestamp t con ore - minuti - secondi settati a 0
	 * @throws Exception
	 */
	public static Timestamp formatDataEsecuzione(Timestamp t) throws Exception {

		Timestamp res = null;

		try {

			Date d = DateUtils
					.stringToDate(t.toString(), "yyyy-MM-dd HH:mm:ss");

			GregorianCalendar gc = new GregorianCalendar();
			gc.setTime(d);
			gc.set(GregorianCalendar.HOUR_OF_DAY, 0);
			gc.set(GregorianCalendar.MINUTE, 0);
			gc.set(GregorianCalendar.SECOND, 0);

			res = new Timestamp(gc.getTimeInMillis());
		} catch (Exception e) {

		}

		return res;

	}

	public static Timestamp getDataEsecuzione() {

		String oggiStr = DateUtils.dateToString(new Date(),
				"yyyy-MM-dd HH:mm:00");
		Date todayDt = DateUtils.stringToDate(oggiStr, "yyyy-MM-dd HH:mm:00");

		Timestamp dataCaricamento = new Timestamp(todayDt.getTime());

		return dataCaricamento;
	}

	public static Timestamp getAddDayToDate(int day) throws Exception {

		String oggi_str = DateUtils.dateToString(new Date(),
				"yyyy-MM-dd 00:00:00");
		Date today_dt = DateUtils.stringToDate(oggi_str, "yyyy-MM-dd 00:00:00");

		Date newdate = DateUtils.addDaysToDate(today_dt, day);

		Timestamp dataCaricamento = new Timestamp(newdate.getTime());

		return dataCaricamento;
	}

	public static Timestamp setDtFineValidita9999() throws Exception {

		Date today_dt = DateUtils.stringToDate("9999-12-31 00:00:00",
				"yyyy-MM-dd 00:00:00");

		Timestamp dataFineValidita = new Timestamp(today_dt.getTime());

		return dataFineValidita;
	}

	public static Timestamp setDtInizioValidita1900() throws Exception {

		Date today_dt = DateUtils.stringToDate("1900-01-01 00:00:00",
				"yyyy-MM-dd 00:00:00");

		Timestamp dataFineValidita = new Timestamp(today_dt.getTime());

		return dataFineValidita;
	}

	/**
	 * @author schiarav
	 * @param now
	 *            The anchor date by mean of is calculated the minor latest
	 *            DATA_CARICAMENTO compared to now
	 * @return the minor latest DATA_CARICAMENTO compared to now
	 */
	public static Timestamp getDtPrecedente(Timestamp now) {
		QDmalmEsitiCaricamenti esiti = QDmalmEsitiCaricamenti.dmalmEsitiCaricamenti;
		ConnectionManager cm = null;
		Connection connection = null;
		List<Timestamp> annullati = new ArrayList<Timestamp>();
		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLQuery query = new SQLQuery(connection, dialect);
			annullati = query.from(esiti).where(esiti.dataCaricamento.lt(now))
					.where(esiti.entitaTarget.eq(DmAlmConstants.TARGET_ALL))
					.where(esiti.statoEsecuzione.eq(DmAlmConstants.CARICAMENTO_TERMINATO_CORRETTAMENTE))
					.list(esiti.dataCaricamento.max());

		} catch (Exception e) {

		}
		if (annullati != null && annullati.size() > 0)
			return annullati.get(0);
		return DateUtils.stringToTimestamp("1900-01-01 00:00:00",
				"yyyy-MM-dd HH:mm:00");

	}

}
