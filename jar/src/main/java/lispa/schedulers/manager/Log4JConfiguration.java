package lispa.schedulers.manager;

import org.apache.log4j.PropertyConfigurator;
import lispa.schedulers.exception.PropertiesReaderException;

public class Log4JConfiguration
{

	private static String  LOG4J_ROOT_FILE_PATH = lispa.schedulers.manager.DmAlmConfigReaderProperties.LOG4J_ROOT_FILE_PATH;

	public static void inizialize() throws PropertiesReaderException
	{
		String pathSeparator = "";
		String root 		 = "";
		String configFile 	 = "";
		
		try
		{
			pathSeparator = System.getProperty("file.separator");
			root 		 = DmAlmConfigReader.getInstance().getProperty(LOG4J_ROOT_FILE_PATH);
			configFile 	 = root + pathSeparator + "log4j.properties";
			
			PropertyConfigurator.configure(configFile);			
		}
		catch (Exception e)
		{
			throw new PropertiesReaderException(e);
		}
	}
}
