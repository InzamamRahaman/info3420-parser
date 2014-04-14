
public class ParseInput 
{
	
	
	private String input;

	public ParseInput(String input)
	{
		this.input = input;
	}
	
	public void parse() throws InvalidParseException
	{
		TokenizeInput tok = new TokenizeInput(input, "(\\ )");
		HeaderParser hp = new HeaderParser(tok);
		hp.parse();
		String rest = tok.getRemainingStringSeparatedBySpaces();
		tok = new TokenizeInput(rest);
		DeclarationParser dp = new DeclarationParser(tok);
		dp.parse();
		BodyParser bp = new BodyParser(tok);
		bp.parse();
	}
	

}
