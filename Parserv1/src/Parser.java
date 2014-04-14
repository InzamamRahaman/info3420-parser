
public abstract class Parser
{
	
	protected TokenizeInput t;
	
	public Parser(TokenizeInput t)
	{
		this.t = t;
	}
	
	
	protected String skipAllLines()
	{
		String comp = t.nextToken();
		while(comp.equals("\n"))
			comp = t.nextToken();
		return comp;
	}
	
	public abstract void parse() throws InvalidParseException;

}
