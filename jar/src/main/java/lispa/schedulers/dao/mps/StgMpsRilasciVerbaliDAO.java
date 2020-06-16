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
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsRilasciVerbali;
import lispa.schedulers.utils.MpsUtils;
import lispa.schedulers.utils.NumberUtils;
import org.apache.log4j.Logger;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsRilasciVerbaliDAO {

	private static Logger logger = Logger
			.getLogger(StgMpsRilasciVerbaliDAO.class);
	private static QDmalmStgMpsRilasciVerbali stgMpsRilasciVerbali = QDmalmStgMpsRilasciVerbali.dmalmStgMpsRilasciVerbali;

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

	public static void fillStgMpsRilasciVerbali() throws PropertiesReaderException, DAOException {
		String pathCSV = MpsUtils
				.currentMpsFile(DmAlmConstants.FILENAME_MPS_RILASCI_VERBALI);

		if (pathCSV == null) {
			Exception exc = new Exception("MPS file non trovato "
					+ DmAlmConstants.FILENAME_MPS_RILASCI_VERBALI
					+ ", elaborazione Mps terminata");
			logger.error("StgMpsRilasciVerbaliDAO - exception: " + exc);

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
								stgMpsRilasciVerbali)
								.columns(
										stgMpsRilasciVerbali.codVerbale,
										stgMpsRilasciVerbali.idVerbaleValidazione,
										stgMpsRilasciVerbali.idRilascio,
										stgMpsRilasciVerbali.importo)
								.values(row.get(mapping
										.get("BO_RILASCI_VERBALI.CODVERBALE")),
										row.get(mapping.get("ID del Verbale")),
										row.get(mapping
												.get("BO_RILASCI_VERBALI.IDRILASCIO")),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Importo Rilascio Verbalizzato"))))
								.execute();
					}

					connection.commit();

					logger.info("StgMpsRilasciVerbaliDAO.fillStgMpsRilasciVerbali - pathCSV: "
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
		QDmalmStgMpsRilasciVerbali qstgmpsrilasciverbali = QDmalmStgMpsRilasciVerbali.dmalmStgMpsRilasciVerbali;
		new SQLDeleteClause(connection, dialect, qstgmpsrilasciverbali)
				.execute();

		cm.closeConnection(connection);
	}

	public static void recoverStgMpsRilasciVerbali() throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		cm = ConnectionManager.getInstance();
		connection = cm.getConnectionOracle();

		SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

		QDmalmStgMpsRilasciVerbali qstgmpsrilasciverbali = QDmalmStgMpsRilasciVerbali.dmalmStgMpsRilasciVerbali;

		new SQLDeleteClause(connection, dialect, qstgmpsrilasciverbali)
				.execute();

		connection.commit();
		cm.closeConnection(connection);
	}
}
