package lispa.schedulers.manager;

import lispa.schedulers.manager.ConnectionManager;
import lispa.schedulers.manager.PropertiesReader;

public class DmAlmConstants {
	
	private static String dbLinkPolarionCurrentSiss, dbLinkPolarionCurrentSire;
	private static String dbLinkPolarionCurrentRevisionSiss, dbLinkPolarionCurrentRevisionSire;

	
//	public static String GetDbLinkPolarionCurrentSiss()
//	{
//		if(dbLinkPolarionCurrentSiss == null)
//			dbLinkPolarionCurrentSiss = GetDbLinkPolarionCurrent(ConnectionManager.GetPropertiesReader(), true);
//		return dbLinkPolarionCurrentSiss;
//	}
//	
//	public static String GetDbLinkPolarionCurrentSire()
//	{
//		if(dbLinkPolarionCurrentSire == null)
//			dbLinkPolarionCurrentSire = GetDbLinkPolarionCurrent(ConnectionManager.GetPropertiesReader(), false);
//		return dbLinkPolarionCurrentSire;
//	}	
	
	private static String GetDbLinkPolarionCurrent(PropertiesReader propertiesReader, boolean siss)
	{
		String url, userName, password;
		if(siss)
		{
			url = propertiesReader.getProperty(DmAlmConfigReaderProperties.SISS_CURRENT_URL);
			userName = propertiesReader.getProperty(DmAlmConfigReaderProperties.SISS_CURRENT_USERNAME);
			password = propertiesReader.getProperty(DmAlmConfigReaderProperties.SISS_CURRENT_PSW);
		}
		else
		{
			url = propertiesReader.getProperty(DmAlmConfigReaderProperties.SIRE_CURRENT_URL);
			userName = propertiesReader.getProperty(DmAlmConfigReaderProperties.SIRE_CURRENT_USERNAME);
			password = propertiesReader.getProperty(DmAlmConfigReaderProperties.SIRE_CURRENT_PSW);
		}
		
		int index = url.lastIndexOf('/');
		String dbName = url.substring(index + 1, url.length());
		
		int index2 = url.lastIndexOf('/', index - 1);
		String hostName = url.substring(index2 + 1, index);
		
		String port = hostName.split(":")[1];
		hostName = hostName.split(":")[0];

		String select =
			"(select c_pk, c_id from dblink( " +
			"'hostaddr=" + hostName + " port="+ port + " dbname=" + dbName + " user=" + userName + " password=" + password + "', " +
			"'select c_pk, c_id from polarion.subterra_uri_map') " +
			"as t1( " +
			"c_pk text, " +
			"c_id bigint " +
			")) "
			;
		
		return select;
	}
	
	public static String GetPolarionSchemaSissHistory()
	{
		return lispa.schedulers.constant.DmAlmConstants.POLARION_SCHEMA;
	}
	
	public static String GetPolarionSchemaSireHistory()
	{
		return lispa.schedulers.constant.DmAlmConstants.POLARION_SCHEMA;
	}

//	public static String GetDbLinkPolarionCurrentRevisionSiss() {
//		if(dbLinkPolarionCurrentRevisionSiss == null)
//			dbLinkPolarionCurrentRevisionSiss = GetDbLinkPolarionCurrentRevision(ConnectionManager.GetPropertiesReader(), true);
//		return dbLinkPolarionCurrentRevisionSiss;
//	}
//	
//	public static String GetDbLinkPolarionCurrentRevisionSire() {
//		if(dbLinkPolarionCurrentRevisionSire == null)
//			dbLinkPolarionCurrentRevisionSire = GetDbLinkPolarionCurrentRevision(ConnectionManager.GetPropertiesReader(), false);
//		return dbLinkPolarionCurrentRevisionSire;
//	}

	private static String GetDbLinkPolarionCurrentRevision(PropertiesReader propertiesReader, boolean siss) {
		String url, userName, password;
		if(siss)
		{
			url = propertiesReader.getProperty(DmAlmConfigReaderProperties.SISS_CURRENT_URL);
			userName = propertiesReader.getProperty(DmAlmConfigReaderProperties.SISS_CURRENT_USERNAME);
			password = propertiesReader.getProperty(DmAlmConfigReaderProperties.SISS_CURRENT_PSW);
		}
		else
		{
			url = propertiesReader.getProperty(DmAlmConfigReaderProperties.SIRE_CURRENT_URL);
			userName = propertiesReader.getProperty(DmAlmConfigReaderProperties.SIRE_CURRENT_USERNAME);
			password = propertiesReader.getProperty(DmAlmConfigReaderProperties.SIRE_CURRENT_PSW);
		}
		
		int index = url.lastIndexOf('/');
		String dbName = url.substring(index + 1, url.length());
		
		int index2 = url.lastIndexOf('/', index - 1);
		String hostName = url.substring(index2 + 1, index);
		
		String port = hostName.split(":")[1];
		hostName = hostName.split(":")[0];

		String select =
			"(select c_name, c_created from dblink( " +
			"'hostaddr=" + hostName + " port="+ port + " dbname=" + dbName + " user=" + userName + " password=" + password + "', " +
			"'select c_name, c_created from polarion.revision') " +
			"as t1( " +
			"c_name text, " +
			"c_created timestamp without time zone " +
			")) "
			;
				
		return select;
	}
	
}