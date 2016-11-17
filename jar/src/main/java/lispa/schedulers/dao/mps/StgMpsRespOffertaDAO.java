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
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsRespOfferta;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.MpsUtils;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.collect.Lists;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLDeleteClause;
import com.mysema.query.sql.dml.SQLInsertClause;

public class StgMpsRespOffertaDAO {
	private static Logger logger = Logger.getLogger(StgMpsRespOffertaDAO.class);
	private static QDmalmStgMpsRespOfferta stgMpsRespOfferta = QDmalmStgMpsRespOfferta.dmalmStgMpsRespOfferta;

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

	public static void fillStgMpsRespOfferta()
			throws PropertiesReaderException, DAOException, SQLException {
		String pathCSV = MpsUtils
				.currentMpsFile(DmAlmConstants.FILENAME_MPS_RESP_OFFERTA);

		if (pathCSV == null) {
			Exception exc = new Exception("MPS file non trovato "
					+ DmAlmConstants.FILENAME_MPS_RESP_OFFERTA
					+ ", elaborazione Mps terminata");
			logger.error("StgMpsRespOffertaDAO - exception: " + exc);

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
								stgMpsRespOfferta)
								.columns(
										stgMpsRespOfferta.idContratto,
										stgMpsRespOfferta.amNome,
										stgMpsRespOfferta.amDataFirma,
										stgMpsRespOfferta.amFirmato,
										stgMpsRespOfferta.samNome,
										stgMpsRespOfferta.samDataFirma,
										stgMpsRespOfferta.samFirmato,
										stgMpsRespOfferta.vdgNome,
										stgMpsRespOfferta.vdgDataFirma,
										stgMpsRespOfferta.vdgFirmato,
										stgMpsRespOfferta.dgNome,
										stgMpsRespOfferta.dgDataFirma,
										stgMpsRespOfferta.dgFirmato,
										stgMpsRespOfferta.cdNome,
										stgMpsRespOfferta.cdDataFirma,
										stgMpsRespOfferta.cdFirmato,
										stgMpsRespOfferta.copertinaFirmata,
										stgMpsRespOfferta.dataFirmaCopertina,
										stgMpsRespOfferta.statoRazionale,
										stgMpsRespOfferta.controller,
										stgMpsRespOfferta.dataVerifica,
										stgMpsRespOfferta.prossimoFirmatarioRazionale,
										stgMpsRespOfferta.razionaleDigitale,
										stgMpsRespOfferta.notaRazionale,
										stgMpsRespOfferta.motivazioneRigetto)
								.values(row
										.get(mapping
												.get("BO_RESPONSABILI_OFFERTA.IDCONTRATTO")),
										row.get(mapping.get("Account Manager")),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("Data di firma dell'A.M."))),
										row.get(mapping
												.get("BO_RESPONSABILI_OFFERTA.AM_FIRMATO")),
										row.get(mapping
												.get("Senior Account Manager")),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("Data di firma del S.A.M"))),
										row.get(mapping
												.get("BO_RESPONSABILI_OFFERTA.SAM_FIRMATO")),
										row.get(mapping
												.get("Vice Direttore Generale")),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("Data di firma del V.D.G."))),
										row.get(mapping
												.get("BO_RESPONSABILI_OFFERTA.VDG_FIRMATO")),
										row.get(mapping
												.get("Direttore Generale")),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("Data di firma del D.G."))),
										row.get(mapping
												.get("BO_RESPONSABILI_OFFERTA.DG_FIRMATO")),
										row.get(mapping
												.get("Consigliere Delegato")),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("Data di firma del C.D."))),
										row.get(mapping
												.get("BO_RESPONSABILI_OFFERTA.CD_FIRMATO")),
										row.get(mapping
												.get("Copertina Firmata")),
										DateUtils.stringToTimestamp(row.get(mapping
												.get("Data Firma Copertina"))),
										row.get(mapping.get("Stato Razionale")),
										row.get(mapping.get("Controller")),
										DateUtils.stringToTimestamp(row
												.get(mapping
														.get("Data Verifica"))),
										row.get(mapping
												.get("Prossimo Firmatario Razionale")),
										row.get(mapping
												.get("Razionale Digitale")),
										row.get(mapping.get("Nota Razionale")),
										row.get(mapping
												.get("Motivazione Rigetto od Approvazione")))
								.execute();
					}

					connection.commit();

					logger.info("StgMpsRespOffertaDAO.fillStgMpsRespOfferta - pathCSV: "
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

			QDmalmStgMpsRespOfferta qstgmpsrespofferta = QDmalmStgMpsRespOfferta.dmalmStgMpsRespOfferta;

			new SQLDeleteClause(connection, dialect, qstgmpsrespofferta)
					.execute();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		} finally {
			if (cm != null) {
				cm.closeConnection(connection);
			}
		}
	}

	public static void recoverStgMpsRespOfferta() throws DAOException {

		ConnectionManager cm = null;
		Connection connection = null;

		try {
			cm = ConnectionManager.getInstance();
			connection = cm.getConnectionOracle();

			SQLTemplates dialect = new HSQLDBTemplates(); // SQL-dialect

			QDmalmStgMpsRespOfferta qstgmpsrespofferta = QDmalmStgMpsRespOfferta.dmalmStgMpsRespOfferta;

			new SQLDeleteClause(connection, dialect, qstgmpsrespofferta)
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
