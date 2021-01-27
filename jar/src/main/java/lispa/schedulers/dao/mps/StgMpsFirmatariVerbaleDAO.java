package lispa.schedulers.dao.mps;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.DAOException;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsFirmatariVerbale;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.MpsUtils;
import lispa.schedulers.utils.NumberUtils;
import lispa.schedulers.utils.StringUtils;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.collect.Lists;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsFirmatariVerbaleDAO {
	private static Logger logger = Logger
			.getLogger(StgMpsFirmatariVerbaleDAO.class);
	private static QDmalmStgMpsFirmatariVerbale stgMpsFirmatariVerbale = QDmalmStgMpsFirmatariVerbale.dmalmStgMpsFirmatariVerbale;

	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

	public static HashMap<String, Integer> mappaColonne(String pathCSV)
			throws PropertiesReaderException {
		String[] nextLine;
		ArrayList<String> fieldsNames = null;

		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		CSVReader reader = null;
		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(
					pathCSV), "ISO-8859-1"), DmAlmConstants.MPS_CSV_SEPARATOR);

			if ((nextLine = reader.readNext()) != null)
				fieldsNames = Lists.newArrayList(nextLine);
			int i = 0;
			for (String field : fieldsNames) {
				hm.put(field, i);
				i++;
			}

			if (reader != null) {
				reader.close();
			}
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage(), e);
			Exception exc = new Exception("MPS file non trovato " + pathCSV
					+ ", elaborazione Mps terminata");

			ErrorManager.getInstance().exceptionOccurred(true, exc);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			ErrorManager.getInstance().exceptionOccurred(true, e);
		}

		return hm;
	}

	public static void fillStgMpsFirmatariVerbale() throws PropertiesReaderException, DAOException {

		String pathCSV = MpsUtils
				.currentMpsFile(DmAlmConstants.FILENAME_MPS_FIRM_VERBALE);

		if (pathCSV == null) {
			Exception exc = new Exception("MPS file non trovato "
					+ DmAlmConstants.FILENAME_MPS_FIRM_VERBALE
					+ ", elaborazione Mps terminata");
			logger.error("StgMpsFirmatariVerbaleDAO - exception: " + exc);

			ErrorManager.getInstance().exceptionOccurred(true, exc);
		} else {
			HashMap<String, Integer> mapping = mappaColonne(pathCSV);

			if (!mapping.isEmpty()) {
				ArrayList<String> columns = null;
				ArrayList<ArrayList<String>> csvTotal = new ArrayList<ArrayList<String>>();

				CSVReader reader = null;

				try {
					reader = new CSVReader(new InputStreamReader(
							new FileInputStream(pathCSV), "UTF-8"),
							DmAlmConstants.MPS_CSV_SEPARATOR);

					connection = cm.getConnectionOracle();

					SQLTemplates dialect = new HSQLDBTemplates();
					String[] nextLine;
					nextLine = reader.readNext(); // riga intestazione da
													// scartare
					while ((nextLine = reader.readNext()) != null) {
						columns = Lists.newArrayList(nextLine);

						csvTotal.add(columns);
					}

					int numRighe = 0;
					for (ArrayList<String> row : csvTotal) {
						numRighe++;

						new SQLInsertClause(connection, dialect,
								stgMpsFirmatariVerbale)
								.columns(
										stgMpsFirmatariVerbale.idVerbaleValidazione,
										stgMpsFirmatariVerbale.idUtente,
										stgMpsFirmatariVerbale.firmatarioVerbale,
										stgMpsFirmatariVerbale.tipologiaResponsabile,
										stgMpsFirmatariVerbale.ordineFirma,
										stgMpsFirmatariVerbale.firmato,
										stgMpsFirmatariVerbale.dataFirma,
										stgMpsFirmatariVerbale.idEnte,
										stgMpsFirmatariVerbale.ente)
								.values(row.get(mapping.get("BO_FIRMATARI_VERBALE.IDVERBALEVAIDAZIONE")),
										NumberUtils.getMaskedValue(row.get(mapping.get("BO_FIRMATARI_VERBALE.IDUTENTE"))),
										StringUtils.getMaskedValue(row.get(mapping.get("Firmatario Verbale"))),
										row.get(mapping.get("Tipologia responsabile")),
										row.get(mapping.get("Ordine Firma")),
										row.get(mapping.get("Firmato")),
										DateUtils.stringToTimestamp(row.get(mapping.get("Data Firma"))),
										NumberUtils.getMaskedValue(row.get(mapping.get("BO_FIRMATARI_VERBALE.IDENTE"))),
										StringUtils.getMaskedValue(row.get(mapping.get("Ente")))).execute();
					}

					connection.commit();

					logger.info("StgMpsFirmatariVerbaleDAO.fillStgMpsFirmatariVerbale - pathCSV: "
							+ pathCSV + ", righe inserite: " + numRighe);

					if (reader != null) {
						reader.close();
					}
				} catch (Exception e) {
					ErrorManager.getInstance().exceptionOccurred(true, e);
				} finally {
					cm.closeConnection(connection);
				}
			}
		}
	}

	public static void delete() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		cm = ConnectionManager.getInstance();
		connection = cm.getConnectionOracle();

		SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
		QDmalmStgMpsFirmatariVerbale qstgmpsfirmatariverbale = QDmalmStgMpsFirmatariVerbale.dmalmStgMpsFirmatariVerbale;
		new SQLDeleteClause(connection, dialect, qstgmpsfirmatariverbale)
				.execute();

		cm.closeConnection(connection);
	}

	public static void recoverStgMpsFirmatariVerbale() throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		cm = ConnectionManager.getInstance();
		connection = cm.getConnectionOracle();

		SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

		QDmalmStgMpsFirmatariVerbale qstgmpsfirmatariverbale = QDmalmStgMpsFirmatariVerbale.dmalmStgMpsFirmatariVerbale;

		new SQLDeleteClause(connection, dialect, qstgmpsfirmatariverbale)
				.execute();

		connection.commit();
		cm.closeConnection(connection);
	}
}
