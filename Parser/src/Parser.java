
public abstract class Parser
{
	
	protected MyTokenizers t;
	
	public Parser(MyTokenizers t)
	{
		this.t = t;
	}
	
	
	protected String skipAllLines()
	{
		String comp = t.nextToken();
		while(comp.equals("\n"))
		{
			comp = t.nextToken();
		}
		return comp;
	}
	
	public abstract void parse() throws InvalidParseException;

}
