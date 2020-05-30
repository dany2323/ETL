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
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsRilasci;
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

public class StgMpsRilasciDAO {
	private static Logger logger = Logger.getLogger(StgMpsRilasciDAO.class);
	private static QDmalmStgMpsRilasci stgMpsRilasci = QDmalmStgMpsRilasci.dmalmStgMpsRilasci;

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

	public static void fillStgMpsRilasci() throws PropertiesReaderException, DAOException {
		String pathCSV = MpsUtils.currentMpsFile(
				DmAlmConstants.FILENAME_MPS_RILASCI,
				DmAlmConstants.FILENAME_MPS_RILASCI_EXC);

		if (pathCSV == null) {
			Exception exc = new Exception("MPS file non trovato "
					+ DmAlmConstants.FILENAME_MPS_RILASCI
					+ ", elaborazione Mps terminata");
			logger.error("StgMpsRilasciDAO - exception: " + exc);

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

						new SQLInsertClause(connection, dialect, stgMpsRilasci)
								.columns(stgMpsRilasci.idRilascio,
										stgMpsRilasci.idContratto,
										stgMpsRilasci.codRilascio,
										stgMpsRilasci.tipoRilascio,
										stgMpsRilasci.sottoTipoRilascio,
										stgMpsRilasci.titoloRilascio,
										stgMpsRilasci.desAttivita,
										stgMpsRilasci.dataInizio,
										stgMpsRilasci.dataRilascio,
										stgMpsRilasci.dataValidazione,
										stgMpsRilasci.statoFatturazione,
										stgMpsRilasci.statoFinanziamento,
										stgMpsRilasci.importoRilascio,
										stgMpsRilasci.totaleSpalmato,
										stgMpsRilasci.totaleVerbalizzato,
										stgMpsRilasci.totaleRichiesta,
										stgMpsRilasci.totaleFatturato,
										stgMpsRilasci.totaleFatturabile,
										stgMpsRilasci.dataRilascioEffettivo,
										stgMpsRilasci.varianteMigliorativa,
										stgMpsRilasci.statoVerbalizzazione)
								.values(row.get(mapping.get("ID del Rilascio")),
										row.get(mapping
												.get("RILASCI_IDCONTRATTO")),
										row.get(mapping.get("Codice Rilascio")),
										row.get(mapping.get("Tipo Rilascio")),
										row.get(mapping
												.get("Sottotipo Rilascio")),
										row.get(mapping.get("Titolo Rilascio")),
										row.get(mapping
												.get("Descrizione Attivita")),
										DateUtils.stringToTimestamp(row
												.get(mapping.get("Data Inizio"))),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("Data Fine/Rilascio"))),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("Data Validazione"))),
										row.get(mapping
												.get("Stato Fatturazione")),
										row.get(mapping
												.get("Stato Finanziamento")),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Importo Rilascio assoluto"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Spalmato (assoluto)"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Verbalizzato (assoluto)"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Richiesta (assoluto)"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Fatturato  (assoluto)"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Fatturabile (assoluto)"))),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("Data Effettiva Rilascio"))),
										row.get(mapping
												.get("Variante Migliorativa")),
										row.get(mapping
												.get("Stato Verbalizzazione")))
								.execute();
					}

					connection.commit();

					logger.info("StgMpsRilasciDAO.fillStgMpsRilasci - pathCSV: "
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
		QDmalmStgMpsRilasci qstgmpsrilasci = QDmalmStgMpsRilasci.dmalmStgMpsRilasci;
		new SQLDeleteClause(connection, dialect, qstgmpsrilasci).execute();

		cm.closeQuietly(connection);
	}

	public static void recoverStgMpsRilasci() throws SQLException, DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		cm = ConnectionManager.getInstance();
		connection = cm.getConnectionOracle();

		SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect
		QDmalmStgMpsRilasci qstgmpsrilasci = QDmalmStgMpsRilasci.dmalmStgMpsRilasci;
		new SQLDeleteClause(connection, dialect, qstgmpsrilasci).execute();

		connection.commit();
		cm.closeQuietly(connection);
	}
}
