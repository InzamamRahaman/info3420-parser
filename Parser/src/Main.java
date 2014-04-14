
public class Main
{
	
	
	public static void main(String[] args)
	{
		String input = "display{\"some\"-some- \"something\"-sn23} $";
		try
		{
			//ParseInput parser = new ParseInput(input);
			//parser.parse();
			TokenizeInput tok = new TokenizeInput(input);
			BodyParser parser = new BodyParser(tok);
			//System.out.println(tok.getAll());
			parser.parse();
		}
		catch(InvalidParseException e)
		{
			System.out.println("Failure!\n" + e.getMessage());
			System.exit(1);
		}
		
		System.out.println("Success");
	}

}
