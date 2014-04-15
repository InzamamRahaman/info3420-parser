import java.util.regex.Pattern;


public class DeclarationParser extends Parser
{

	public DeclarationParser(MyTokenizers t) {
		super(t);
		// TODO Auto-generated constructor stub
	}
	
	private void parseUntilEnd() throws InvalidParseException
	{
		String cmp = t.nextToken();
		while(!cmp.equals("endDeclar") && !t.isEmpty())
		{
			if(!Pattern.matches("([a-z](([a-zA-Z]|[0-9]){0,7})\\$)+", cmp))
			{
				//throw (new InvalidParseException("Invalid variable name"));
				if(!Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
				{
					throw (new InvalidParseException("Invalid variable name"));
				}
				else
				{
					String temp = cmp;
					cmp = t.nextToken();
					if(!cmp.equals("$"))
					{
						throw (new InvalidParseException("Missing $ in declaration statement for " + temp));
					}
				}
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
		
		
		String cmp = t.nextToken();
		
		// The declaration block is supposed to start with
		// startDeclar
		if(!cmp.equals("startDeclar"))
		{
			throw (new InvalidParseException("Declaration block does not start properly"));
		}
		
		// We parse until we hit endDeclar or the tokenizer is empty
		try{
			this.parseUntilEnd();
		}
		catch(InvalidParseException e)
		{
			throw (new InvalidParseException(e.getMessage()));
		}
		
		
	}
	

}
