import java.util.StringTokenizer;


public class MainParser extends Parser
{
	
	private HeaderParser hp;
	private DeclarationParser dp;
	private BodyParser bp;
	

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
		String s = t.toString();
		StringTokenizer st = new StringTokenizer(s, "(\\s)");
		TokenizeInput t = new TokenizeInput(st);
		dp = new DeclarationParser(t);
		bp = new BodyParser(t);
		dp.parse();
		bp.parse();
		
	}
	
	
	
	
	

}
