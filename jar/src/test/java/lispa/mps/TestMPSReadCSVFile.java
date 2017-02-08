package lispa.mps;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.common.collect.Lists;
import com.mysema.query.sql.HSQLDBTemplates;
import com.mysema.query.sql.SQLTemplates;
import com.mysema.query.sql.dml.SQLInsertClause;

import au.com.bytecode.opencsv.CSVReader;
import junit.framework.TestCase;
import lispa.schedulers.constant.DmAlmConstants;
import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.ErrorManager;
import lispa.schedulers.manager.Log4JConfiguration;
import lispa.schedulers.queryimplementation.staging.mps.QDmalmStgMpsAttivita;
import lispa.schedulers.utils.DateUtils;
import lispa.schedulers.utils.MpsUtils;
import lispa.schedulers.utils.NumberUtils;

public class TestMPSReadCSVFile  extends TestCase{
	
	private static QDmalmStgMpsAttivita stgMpsAttivita = QDmalmStgMpsAttivita.dmalmStgMpsAttivita;
	private Connection connection;
	private ConnectionManager cm;

	public void testReadCSVFile(){
		
		try {
			
			Log4JConfiguration.inizialize();
			ConnectionManager.getInstance().getConnectionOracle();
			
			String pathCSV = MpsUtils
					.currentMpsFile(DmAlmConstants.FILENAME_MPS_ATTIVITA);
	
			if (pathCSV == null) {
				Exception exc = new Exception("MPS file non trovato "
						+ DmAlmConstants.FILENAME_MPS_ATTIVITA
						+ ", elaborazione Mps terminata");
				System.out.println("StgMpsAttivitaDAO - exception: " + exc);
	
				ErrorManager.getInstance().exceptionOccurred(true, exc);
			} else {
				HashMap<String, Integer> mapping = mappaColonne(pathCSV);
	
				if (!mapping.isEmpty()) {
					ArrayList<String> columns = null;
					ArrayList<ArrayList<String>> csvTotal = new ArrayList<ArrayList<String>>();
	
					CSVReader reader = null;
	
					cm = ConnectionManager.getInstance();
					connection = cm.getConnectionOracle();
					
					connection.setAutoCommit(false);

					SQLTemplates dialect = new HSQLDBTemplates();
					
					reader = new CSVReader(new InputStreamReader(new FileInputStream(pathCSV), "UTF-8"), DmAlmConstants.MPS_CSV_SEPARATOR);

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
						System.out.println(
						row.get(mapping.get("BO_ATTIVITA.IDATTIVITAPADRE")) + " - " + 
						row.get(mapping.get("ATTIVITA.IDATTIVITA")) + " - " + 
						row.get(mapping.get("BO_ATTIVITA.IDCONTRATTO")) + " - " + 
						row.get(mapping.get("BO_ATTIVITA.CODATTIVITA")) + " - " + 
						row.get(mapping.get("BO_ATTIVITA.TITOLO")) + " - " + 
						row.get(mapping.get("BO_ATTIVITA.DESATTIVITA")) + " - " + 
						DateUtils.stringToTimestamp(row.get(mapping.get("BO_ATTIVITA.DATA_INIZIO"))) + " - " + 
						DateUtils.stringToTimestamp(row.get(mapping.get("BO_ATTIVITA.DATA_FINE"))) + " - " + 
						row.get(mapping.get("Avanzamento")).replace(",", ".") + " - " + 
						DateUtils.stringToTimestamp(row.get(mapping.get("Data Ultimo Avanzamento"))) + " - " + 
						row.get(mapping.get("BO_ATTIVITA.TIPO_ATTIVITA")) + " - " + 
						row.get(mapping.get("BO_ATTIVITA.STATO")) + " - " + 
						NumberUtils.getMaskedValue(row.get(mapping.get("BO_ATTIVITA.INSERITO_DA"))) + " - " + 
						DateUtils.stringToTimestamp(row.get(mapping.get("BO_ATTIVITA.INSERITO_IL"))) + " - " + 
						NumberUtils.getMaskedValue(row.get(mapping.get("BO_ATTIVITA.MODIFICATO_DA"))) + " - " + 
						DateUtils.stringToTimestamp(row.get(mapping.get("BO_ATTIVITA.MODIFICATO_IL"))) + " - " + 
						row.get(mapping.get("BO_ATTIVITA.RECORDSTATUS")));
						
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
					
					System.out.println("StgMpsAttivitaDAO.fillStgMpsAttivita - pathCSV: "
							+ pathCSV + ", righe inserite: " + numRighe);
					
					if (reader != null) {
						reader.close();
					}
	
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
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
			System.out.println(e.getMessage());
			Exception exc = new Exception("MPS file non trovato " + pathCSV
					+ ", elaborazione Mps terminata");

			ErrorManager.getInstance().exceptionOccurred(true, exc);
		} catch (Exception e) {
			System.out.println(e.getMessage());

			ErrorManager.getInstance().exceptionOccurred(true, e);
		}

		return hm;
	}
	
	
}
