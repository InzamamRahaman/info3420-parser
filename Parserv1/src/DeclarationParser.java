import java.util.regex.Pattern;


public class DeclarationParser extends Parser
{

	public DeclarationParser(TokenizeInput t) {
		super(t);
		// TODO Auto-generated constructor stub
	}
	
	private void parseUntilEnd() throws InvalidParseException
	{
		String cmp = t.nextToken();
		while(cmp.equals("endDeclar") && !t.isEmpty())
		{
			if(!Pattern.matches("([a-z](([a-zA-Z]|[0-9]){0,7})\\$)", cmp))
			{
				throw (new InvalidParseException("Invalid variable name"));
			}
			
			cmp = t.nextToken();
		}
		
		if(t.isEmpty())
		{
			throw (new InvalidParseException("Declaration block missing endDeclar"));
		}
	}
	
	public void parse() throws InvalidParseException
	{
		
		String cmp = t.getCurrent();
		
		if(!cmp.equals("startDeclar"))
		{
			throw (new InvalidParseException("Declaration block does not start properly"));
		}
		
		cmp = t.nextToken();
		if(cmp.equals("\n"))
		{
			throw (new InvalidParseException("If empty, the declaration block must have an endDeclar statement immediately after the startDeclar keyword"));
	
		}
		
		try{
			this.parseUntilEnd();
		}
		catch(InvalidParseException e)
		{
			throw (new InvalidParseException(e.getMessage()));
		}
		
		
	}
	

}
