
public class ParseInput 
{
	
	
	private String input;

	public ParseInput(String input)
	{
		this.input = input;
	}
	
	private String reconstructFile(String[] data, int idx)
	{
		StringBuilder sb = new StringBuilder();
		for(int i = idx; i < data.length; i++)
		{
			sb.append(data[i] + " ");
		}
		
		return sb.toString();
		
	}
	
	public void parse() throws InvalidParseException
	{
		String[] byLines = input.split("\n");
		String rest = reconstructFile(byLines, 1);
		//System.out.println(rest.toString());
		TokenizeInput tok = new TokenizeInput(byLines[0]);
		HeaderParser hp = new HeaderParser(tok);
		hp.parse();
		//String rest = tok.getRemainingStringSeparatedBySpaces();
		tok = new TokenizeInput(rest);
		DeclarationParser dp = new DeclarationParser(tok);
		dp.parse();
		rest = tok.getRemainingStringSeparatedBySpaces();
		//System.out.println("Started tokenizing");
		MyTokenizers mt = new BodyTokenizer(rest);
		//System.out.println("Finished tokenizing");
		SecondBodyParser bp = new SecondBodyParser(mt);
		bp.parse();
		System.out.println("Finished parsing body");
		if(mt.getCurrent().equals("endProg"))
		{
			System.out.println("Successfully parsed input file");
		}
	}
	

}
