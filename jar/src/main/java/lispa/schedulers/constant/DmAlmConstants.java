package lispa.schedulers.constant;

/*
 * SonarQube, open source software quality management tool.
 * Copyright (C) 2008-2013 SonarSource
 * mailto:contact AT sonarsource DOT com
 *
 * SonarQube is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * SonarQube is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

import lispa.schedulers.exception.PropertiesReaderException;
import lispa.schedulers.manager.DmAlmConfigReader;
import lispa.schedulers.manager.DmAlmConfigReaderProperties;

public final class DmAlmConstants {

	public static final String POLARION_SCHEMA = getProperty(DmAlmConfigReaderProperties.DMALM_SCHEMA_POLARION);
	public static final String DMALM_STAGING_SCHEMA = getProperty(DmAlmConfigReaderProperties.DMALM_SCHEMA_DATAMART_STAGING);
	public static final String DMALM_TARGET_SCHEMA = getProperty(DmAlmConfigReaderProperties.DMALM_SCHEMA_DATAMART_TARGET);
	public static final String FONTE_ELETTRA_SCHEMA = getProperty(DmAlmConfigReaderProperties.DMALM_SCHEMA_FONTE_ELETTRA);

	public static final String GLOBAL = "GLOBAL";
	public static final String SVILUPPO = "SVILUPPO";
	public static final String DEMAND = "DEMAND";
	public static final String DEMAND2016 = "DEMAND2016";
	public static final String IT = "IT";
	public static final String ASSISTENZA = "ASSISTENZA";
	public static final String SERDEP = "SERDEP";
	
	// FONTE
	public static final String FONTE_SGR = "SGR";
	public static final String FONTE_SFERA = "SFERA";
	public static final String FONTE_MPS = "MPS";
	public static final String FONTE_CALIPSO = "CALIPSO";
		
	// STATI CARICAMENTO FONTE
	public static final String CARICAMENTO_FONTE_INIT = "INIT";
	public static final String CARICAMENTO_FONTE_OK = "OK";
	public static final String CARICAMENTO_FONTE_PENDING = "PENDING";
	public static final String CARICAMENTO_FONTE_KO = "KO";
	
	// TABELLE FONTE
	public static final String DMALM_FONTE_CARICAMENTO = "DMALM_FONTE_CARICAMENTO";
	
	public static final String DATE_FORMAT_EDMA_UO = "dd/MM/yyyy";
	public static final String DATE_FORMAT_DB = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_SFERA_FILENAME = "yyyy_MM_dd";
	public static final String TIMESTAMP_SFERA_FILENAME = "yyyy_MM_dd_HH_mm_ss";
	public static final int MAX_VARCHAR2_LENGHT = 4000;
	public static final int BATCH_SIZE = 1000;

	public static final String REPOSITORY_SIRE = "SIRE";
	public static final String REPOSITORY_SISS = "SISS";
	public static final String SCHEMA_CURRENT = "CURRENT";
	public static final String SCHEMA_HISTORY = "HISTORY";

	public static final String DM_ALM_ESECUZIONE = "true";
	
	//SFERA
	public static final String FILENAME_EXPORT = "Sfera_Export_Massive_" ;
	public static final String EXP_SFERA_EXTENSION = ".xls" ;
	public static final String SFERA_PATH = getProperty(DmAlmConfigReaderProperties.DMALM_SFERA_PATH);
	public static final String SFERA_CURRENT = getProperty(DmAlmConfigReaderProperties.DMALM_SFERA_CURRENT_PATH);
	public static final String SFERA_CSV = getProperty(DmAlmConfigReaderProperties.DMALM_SFERA_CSV);

	//MPS
	public static final String MPS_PATH = getProperty(DmAlmConfigReaderProperties.DMALM_MPS_PATH);
	public static final String MPS_PREFISSO_AMBIENTE = getProperty(DmAlmConfigReaderProperties.DMALM_MPS_PREFISSO_AMBIENTE);
	public static final String MPS_FILENAME_DATE_FORMAT = "yyyy-MM-dd-HH-mm-ss";
	public static final String MPS_CSV_EXTENSION = ".csv" ;
	public static final char MPS_CSV_SEPARATOR = ',';
	public static final String FILENAME_MPS_ATTIVITA = "MPS_Tabelle_Attivita_" ;
	public static final String FILENAME_MPS_CONTRATTI = "MPS_Tabelle_Contratti_" ;
	public static final String FILENAME_MPS_FIRM_VERBALE = "MPS_Tabelle_Firmatari_Verbale_" ;
	public static final String FILENAME_MPS_RESP_CONTRATTO = "MPS_Tabelle_Responsabili_Contratto_" ;
	public static final String FILENAME_MPS_RESP_OFFERTA = "MPS_Tabelle_Responsabili_Offerta_" ;
	public static final String FILENAME_MPS_RILASCI = "MPS_Tabelle_Rilasci_" ;
	public static final String FILENAME_MPS_RILASCI_EXC = "MPS_Tabelle_Rilasci_Verbali" ;
	public static final String FILENAME_MPS_RILASCI_VERBALI = "MPS_Tabelle_Rilasci_Verbali_" ;
	public static final String FILENAME_MPS_VERBALI = "MPS_Tabelle_Verbali_" ;
	
	// Kill Session
	public static final String STORED_PROCEDURE_KILL_BO_SESSIONS="KILL_BO_SESSIONS";
	
	// Calipso Sheet Name Scheda Servizio
	public static String CALIPSO_SHEET_NAME_SCHEDA_SERVIZIO = "Portfolio_esteso";
	
	public static String DELETE_DATI_FONTE_TABELLE = "DELETE_DATI_FONTE_TABELLE";
	
	public static String getProperty(String property) {

		String props = "";
		try {
			props = DmAlmConfigReader.getInstance().getProperty(property);
		} catch (PropertiesReaderException e) {
			
		}

		return props;
	}
}
