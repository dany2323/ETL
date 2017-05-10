package lispa.schedulers.manager;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SIRE_CURRENT_PSW;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SIRE_CURRENT_USERNAME;
import static lispa.schedulers.manager.DmAlmConfigReaderProperties.SIRE_HISTORY_URL;

public class DmAlmConstants {
	
	public static String GetDbLinkPolarionCurrentSiss()
	{
		return GetDbLinkPolarionCurrent(ConnectionManager.GetPropertiesReader(), true);
	}
	
	public static String GetDbLinkPolarionCurrentSire()
	{
		return GetDbLinkPolarionCurrent(ConnectionManager.GetPropertiesReader(), false);
	}	
	
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
		
		String select =
			"(select c_pk, c_id from dblink( " +
			"'hostaddr=" + hostName + " dbname=" + dbName + " user=" + userName + " password=" + password + "', " +
			"'select c_pk, c_id from polarion.subterra_uri_map') " +
			"as t1( " +
			"c_pk text, " +
			"c_id bigint " +
			")) "
			;
		
		return select;
	}
}
