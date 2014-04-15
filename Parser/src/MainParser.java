import java.util.StringTokenizer;


public class MainParser extends Parser
{
	
	private HeaderParser hp;
	private DeclarationParser dp;
	private SecondBodyParser bp;
	

	public MainParser(TokenizeInput t) 
	{
		super(t);
		hp = new HeaderParser(t);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void parse() throws InvalidParseException 
	{
		hp.parse();
		String s = t.getRemainingStringSeparatedBySpaces();
		StringTokenizer st = new StringTokenizer(s, "(\\s)");
		MyTokenizers t = new TokenizeInput(st);
		dp = new DeclarationParser(t);
		s = t.getRemainingStringSeparatedBySpaces();
		t = new BodyTokenizer(s);
		bp = new SecondBodyParser(t);
		dp.parse();
		bp.parse();
		if(t.isEmpty())
		{
			throw (new InvalidParseException("missing endProg"));
		}
		
		s = t.nextToken();
		
		if(t.equals("endProg"))
		{
			System.out.println("Successfully parsed input !!!");
		}
		else
		{
			throw (new InvalidParseException("Missing endProg"));
		}
	}
	
	
	
	
	

}
