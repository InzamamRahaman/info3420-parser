import java.util.regex.Pattern;


public class HeaderParser extends Parser
{

	//private TokenizeInput t;
	
	public HeaderParser(TokenizeInput t)
	{
		super(t);
	}
	
	@Override
	public void parse() throws InvalidParseException
	{
		
		String curr = this.skipAllLines();
		if(!curr.equals("prog:"))
		{
			throw (new InvalidParseException("Header was ill-formed"));
		}
		curr = t.nextToken();
		
		if(!Pattern.matches(RegexPack.PROGRAMME_NAME, curr ))
		{
			throw (new InvalidParseException("Invalid Programme name used"));
		}
		
		curr = t.nextToken();
		if(!curr.equals("\n"))
		{
			throw (new InvalidParseException("Declarations start on the same line as header"));
		}
		
		
		
		
	}

}
