import java.util.regex.Pattern;


public class HeaderParser extends Parser
{

	//private TokenizeInput t;
	
	public HeaderParser(MyTokenizers t)
	{
		super(t);
	}
	
	@Override
	public void parse() throws InvalidParseException
	{
		
		
		// A file could have several lines before the programme header
		// so we skip these lines until we encounter a token that isn't 
		// a newline character
		String curr = this.skipAllLines();
		
		// If the token we encounter is not 
		// prog:, then the header is improperly formed
		if(!curr.equals("prog:"))
		{
			throw (new InvalidParseException("Header was ill-formed"));
		}
		curr = t.nextToken();
		// A valid programme name has a particular form
		// if the name specified does not match the form
		// then it is an invalid name
		if(!Pattern.matches(RegexPack.PROGRAMME_NAME, curr ))
		{
			throw (new InvalidParseException("Invalid Programme name used"));
		}
		
		
		curr = t.nextToken();
		// The next token is supposed to be a newline, after which the declaration block is formed
		if(!curr.equals("\n"))
		{
			throw (new InvalidParseException("Declarations start on the same line as header"));
		}
		
		
		
		
	}

}
