package lispa.schedulers.exception;

public class PropertiesReaderException
extends Exception
{
	private static final long serialVersionUID = 5105685957930671794L;

	public PropertiesReaderException()
	{
	}

	public PropertiesReaderException(String message)
	{
		super(message);
	}

	public PropertiesReaderException(Throwable cause)
	{
		super(cause);
	}

	public PropertiesReaderException(String message, Throwable cause)
	{
		super(message, cause);
	}

}
