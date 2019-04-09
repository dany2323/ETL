package lispa.schedulers.manager;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

import lispa.schedulers.exception.PropertiesReaderException;

public class PropertiesReader
{
	private Properties properties;
	private static Logger logger = Logger.getLogger(PropertiesReader.class);

	public PropertiesReader(String propertiesFile) throws PropertiesReaderException
	{
		try
		{
			properties = new Properties();
			
			try (FileInputStream fis = new FileInputStream(propertiesFile)) 
			{
				properties.load(fis);				
				
			} catch(Exception eta){
				 logger.debug(eta);
			     eta.printStackTrace();
			}

			
		} catch (Exception e) {
			throw new PropertiesReaderException(e);
		}
	}
	
	public synchronized String getProperty(String property)
	{
		return properties.getProperty(property);
	}
}
