package lispa.schedulers.exception;

public class DAOException
extends Exception
{

	private static final long serialVersionUID = 3656645208280247649L;

	public DAOException()
	{
	}

	public DAOException(String message)
	{
		super(message);
	}

	public DAOException(Throwable cause)
	{
		super(cause);
	}

	public DAOException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
