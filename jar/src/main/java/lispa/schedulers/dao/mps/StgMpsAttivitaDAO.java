package lispa.schedulers.dao.mps;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsAttivita;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.MpsUtils;
import lispa.schedulers.utils.NumberUtils;
import org.apache.log4j.Logger;
import au.com.bytecode.opencsv.CSVReader;
import com.google.common.collect.Lists;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsAttivitaDAO {

	private static Logger logger = Logger.getLogger(StgMpsAttivitaDAO.class);
	private static QDmalmStgMpsAttivita stgMpsAttivita = QDmalmStgMpsAttivita.dmalmStgMpsAttivita;

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

			if ((nextLine = reader.readNext()) != null) {
				fieldsNames = Lists.newArrayList(nextLine);
			}
			
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

	public static void fillStgMpsAttivita() throws PropertiesReaderException, DAOException {

		String pathCSV = MpsUtils
				.currentMpsFile(DmAlmConstants.FILENAME_MPS_ATTIVITA);

		if (pathCSV == null) {
			Exception exc = new Exception("MPS file non trovato "
					+ DmAlmConstants.FILENAME_MPS_ATTIVITA
					+ ", elaborazione Mps terminata");
			logger.error("StgMpsAttivitaDAO - exception: " + exc);

			ErrorManager.getInstance().exceptionOccurred(true, exc);
		} else {
			HashMap<String, Integer> mapping = mappaColonne(pathCSV);

			if (!mapping.isEmpty()) {
				ArrayList<String> columns = null;
				ArrayList<ArrayList<String>> csvTotal = new ArrayList<ArrayList<String>>();

				CSVReader reader = null;

				try {
					reader = new CSVReader(new InputStreamReader(new FileInputStream(pathCSV), "UTF-8"), DmAlmConstants.MPS_CSV_SEPARATOR);

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

						new SQLInsertClause(connection, dialect, stgMpsAttivita)
								.columns(stgMpsAttivita.idAttivitaPadre,
										stgMpsAttivita.idAttivita,
										stgMpsAttivita.idContratto,
										stgMpsAttivita.codAttivita,
										stgMpsAttivita.titolo,
										stgMpsAttivita.desAttivita,
										stgMpsAttivita.dataInizio,
										stgMpsAttivita.dataFine,
										stgMpsAttivita.avanzamento,
										stgMpsAttivita.dataUltimoAvanzamento,
										stgMpsAttivita.tipoAttivita,
										stgMpsAttivita.stato,
										stgMpsAttivita.inseritoDa,
										stgMpsAttivita.inseritoIl,
										stgMpsAttivita.modificatoDa,
										stgMpsAttivita.modificatoIl,
										stgMpsAttivita.recordStatus)
								.values(row.get(mapping
										.get("BO_ATTIVITA.IDATTIVITAPADRE")),
										row.get(mapping
												.get("ATTIVITA.IDATTIVITA")),
										row.get(mapping
												.get("BO_ATTIVITA.IDCONTRATTO")),
										row.get(mapping
												.get("BO_ATTIVITA.CODATTIVITA")),
										row.get(mapping
												.get("BO_ATTIVITA.TITOLO")),
										row.get(mapping
												.get("BO_ATTIVITA.DESATTIVITA")),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("BO_ATTIVITA.DATA_INIZIO"))),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("BO_ATTIVITA.DATA_FINE"))),
										row.get(mapping.get("Avanzamento")).replace(",", "."),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("Data Ultimo Avanzamento"))),
										row.get(mapping
												.get("BO_ATTIVITA.TIPO_ATTIVITA")),
										row.get(mapping
												.get("BO_ATTIVITA.STATO")),
										NumberUtils.getMaskedValue(row.get(mapping.get("BO_ATTIVITA.INSERITO_DA"))),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("BO_ATTIVITA.INSERITO_IL"))),
										NumberUtils.getMaskedValue(row.get(mapping.get("BO_ATTIVITA.MODIFICATO_DA"))),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("BO_ATTIVITA.MODIFICATO_IL"))),
										row.get(mapping
												.get("BO_ATTIVITA.RECORDSTATUS")))
								.execute();
					}

					connection.commit();

					logger.info("StgMpsAttivitaDAO.fillStgMpsAttivita - pathCSV: "
							+ pathCSV + ", righe inserite: " + numRighe);
					
					if (reader != null) {
						reader.close();
					}
				} catch (IOException | SQLException e) {
					logger.error(e.getMessage(), e);
					throw new DAOException();
				} finally {
					cm.closeQuietly(connection);
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
		QDmalmStgMpsAttivita qstgmpsattivita = QDmalmStgMpsAttivita.dmalmStgMpsAttivita;
		new SQLDeleteClause(connection, dialect, qstgmpsattivita).execute();

		cm.closeQuietly(connection);
	}

	public static void recoverStgMpsAttivita() throws SQLException, DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		cm = ConnectionManager.getInstance();
		connection = cm.getConnectionOracle();

		SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

		QDmalmStgMpsAttivita qstgmpsattivita = QDmalmStgMpsAttivita.dmalmStgMpsAttivita;

		new SQLDeleteClause(connection, dialect, qstgmpsattivita).execute();

		connection.commit();
		cm.closeQuietly(connection);
	}
}
