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
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsContratti;
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

public class StgMpsContrattiDAO {

	private static Logger logger = Logger.getLogger(StgMpsContrattiDAO.class);
	private static QDmalmStgMpsContratti stgMpsContratti = QDmalmStgMpsContratti.dmalmStgMpsContratti;

	private static ConnectionManager cm = ConnectionManager.getInstance();
	private static Connection connection;

	public static HashMap<String, Integer> mappaColonne(String pathCSV)
			throws PropertiesReaderException {
		String[] nextLine;
		ArrayList<String> fieldsNames = null;

		boolean doppiaColonnaDirezione = false;

		HashMap<String, Integer> hm = new HashMap<String, Integer>();
		CSVReader reader = null;
		try {
			reader = new CSVReader(new InputStreamReader(new FileInputStream(
					pathCSV), "ISO-8859-1"), DmAlmConstants.MPS_CSV_SEPARATOR);

			if ((nextLine = reader.readNext()) != null)
				fieldsNames = Lists.newArrayList(nextLine);

			int i = 0;

			for (String field : fieldsNames) {
				if (field.equalsIgnoreCase("Direzione")) {
					if (!doppiaColonnaDirezione) {
						doppiaColonnaDirezione = true;
					} else {
						field = "REPOSITORY";
					}
				}

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

	public static void fillStgMpsContratti() throws PropertiesReaderException,
			DAOException, SQLException {
		String pathCSV = MpsUtils
				.currentMpsFile(DmAlmConstants.FILENAME_MPS_CONTRATTI);

		if (pathCSV == null) {
			Exception exc = new Exception("MPS file non trovato "
					+ DmAlmConstants.FILENAME_MPS_CONTRATTI
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
								stgMpsContratti)
								.columns(stgMpsContratti.idContratto,
										stgMpsContratti.codContratto,
										stgMpsContratti.titoloContratto,
										stgMpsContratti.annoRiferimento,
										stgMpsContratti.dataInizio,
										stgMpsContratti.dataFine,
										stgMpsContratti.tipo,
										stgMpsContratti.stato,
										stgMpsContratti.firmaDigitale,
										stgMpsContratti.variato,
										stgMpsContratti.numVariazioni,
										stgMpsContratti.codDirezione,
										stgMpsContratti.desDirezione,
										stgMpsContratti.codUo,
										stgMpsContratti.desUo,
										stgMpsContratti.codStruttura,
										stgMpsContratti.desStruttura,
										stgMpsContratti.totaleContratto,
										stgMpsContratti.totaleImpegnato,
										stgMpsContratti.totaleSpalmato,
										stgMpsContratti.totaleVerbalizzato,
										stgMpsContratti.totaleRichiesto,
										stgMpsContratti.totaleFatturato,
										stgMpsContratti.totaleFatturabile,
										stgMpsContratti.prossimoFirmatario,
										stgMpsContratti.inCorsoIl,
										stgMpsContratti.numeroRilasci,
										stgMpsContratti.numeroRilasciForfait,
										stgMpsContratti.numeroRilasciCanone,
										stgMpsContratti.numeroRilasciConsumo,
										stgMpsContratti.numeroVerbali,
										stgMpsContratti.numeroVerbaliForfait,
										stgMpsContratti.numeroVerbaliConsumo,
										stgMpsContratti.desMotivoVariazione,
										stgMpsContratti.repository,
										stgMpsContratti.priorita,
										stgMpsContratti.idSm,
										stgMpsContratti.serviceManager)
								.values(row
										.get(mapping.get("ID del Contratto")),
										row.get(mapping.get("Codice Contratto")),
										row.get(mapping.get("Titolo Contratto")),
										row.get(mapping.get("Anno Contratto")),
										DateUtils.stringToTimestamp(row
												.get(mapping.get("Data Inizio"))),
										DateUtils.stringToTimestamp(row
												.get(mapping.get("Data Fine"))),
										row.get(mapping.get("Tipo")),
										row.get(mapping
												.get("Stato del Contratto")),
										row.get(mapping.get("Firma Digitale")),
										row.get(mapping.get("Variato")),
										row.get(mapping
												.get("Numero Variazioni")),
										row.get(mapping.get("Codice Direzione")),
										row.get(mapping.get("Direzione")),
										row.get(mapping.get("Codice U.O.")),
										row.get(mapping.get("U.O.")),
										row.get(mapping.get("Codice Struttura")),
										row.get(mapping.get("Struttura")),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Contratto (assoluto)"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Impegnato (assoluto)"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Spalmato (assoluto)"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Verbalizzato (assoluto)"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Richiesto (assoluto)"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Fatturato (assoluto)"))),
										NumberUtils.fromStringToDouble(row.get(mapping
												.get("Totale Fatturabile (assoluto)"))),
										row.get(mapping
												.get("Prossimo Firmatario")),
										DateUtils.stringToTimestamp(row
												.get(mapping.get("In Corso il"))),
										row.get(mapping.get("Numero Rilasci")),
										row.get(mapping
												.get("Numero Rilasci Forfait")),
										row.get(mapping
												.get("Numero Rilasci Canone")),
										row.get(mapping
												.get("Numero Rilasci Consumo")),
										row.get(mapping.get("Numero Verbali")),
										row.get(mapping
												.get("Numero Verbali Forfait")),
										row.get(mapping
												.get("Numero Verbali Consumo")),
										row.get(mapping
												.get("Motivo Variazione")),
										row.get(mapping.get("REPOSITORY")),
										row.get(mapping.get("Priorita")),
										row.get(mapping
												.get("BO_CONTRATTI.ID_SM")),
										row.get(mapping.get("Service Manager")))
								.execute();
					}

					connection.commit();
					
					logger.info("StgMpsContrattiDAO.fillStgMpsContratti - pathCSV: "
							+ pathCSV + ", righe inserite: " + numRighe);
					
					if (reader != null) {
						reader.close();
					}
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					ErrorManager.getInstance().exceptionOccurred(true, e);
					throw new DAOException(e);
				} finally {
					if (cm != null) {
						cm.closeConnection(connection);
					}
				}
			}
		}
	}

	public static void delete() throws DAOException {
		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsContratti qstgmpscontratti = QDmalmStgMpsContratti.dmalmStgMpsContratti;

			new SQLDeleteClause(connection, dialect, qstgmpscontratti)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMpsContratti() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsContratti qstgmpscontratti = QDmalmStgMpsContratti.dmalmStgMpsContratti;

			new SQLDeleteClause(connection, dialect, qstgmpscontratti)
					.execute();

			connection.commit();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

			throw new DAOException(e);
		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}
}
