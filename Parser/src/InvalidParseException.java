
public class InvalidParseException extends Exception
{
	
	public InvalidParseException()
	{
		super("A parse tree was not constructed");
	}
	
	public InvalidParseException(String message)
	{
		super(message);
	}

}
