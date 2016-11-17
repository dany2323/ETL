package lispa.schedulers.manager;

import static lispa.schedulers.manager.DmAlmConfigReaderProperties.*;
import lispa.schedulers.exception.PropertiesReaderException;

public class DmAlmConfigReader
{
	private static DmAlmConfigReader instance;

	private PropertiesReader propertiesReader;
	
	private DmAlmConfigReader() throws PropertiesReaderException
	{
		propertiesReader = new PropertiesReader(PROPERTIES_READER_FILE_NAME);
	}
	
	public synchronized static DmAlmConfigReader getInstance()
	throws PropertiesReaderException
	{
		if (instance == null)
		{
			instance = new DmAlmConfigReader();
		}
		return instance;
	}
	
	public synchronized String getProperty(String property)
	{
		return propertiesReader.getProperty(property);
	}
	

}
