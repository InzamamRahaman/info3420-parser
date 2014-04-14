import java.util.StringTokenizer;
import java.util.regex.Pattern;


public class BodyParser extends Parser
{

	public BodyParser(TokenizeInput t) {
		super(t);
		// TODO Auto-generated constructor stub
	}
	
	private void parseSet() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
		{
			throw (new InvalidParseException("Bad variable name in body"));
		}
		
		cmp = t.nextToken();
		
		if(!cmp.matches("to"))
		{
			throw (new InvalidParseException("Missing 'to' in set statement"));
		}
		
		cmp = t.nextToken();
		
		if(!Pattern.matches(RegexPack.POSTF, cmp))
		{
			throw (new InvalidParseException("Improper use of set statement"));
		}
		
	}
	
	private void parseInternalPrint(StringTokenizer st) throws InvalidParseException
	{
		String cmp = st.nextToken();
		boolean phraseLeading = true;
		if(Pattern.matches(RegexPack.POSTF, cmp))
			phraseLeading = false;
		else 
		{
			if(!Pattern.matches(RegexPack.ANY_STRING, cmp))
			{
				throw (new InvalidParseException("Incorrectly formed print statement"));
			}
		}
		
		while(!st.hasMoreElements())
		{
			cmp = st.nextToken();
			if(phraseLeading)
			{
				if(!Pattern.matches(RegexPack.POSTF, cmp))
				{
					throw (new InvalidParseException("Incorrectly formed print statement"));
				}
				phraseLeading = false;
			}
			else
			{
				if(!Pattern.matches(RegexPack.ANY_STRING, cmp))
				{
					throw (new InvalidParseException("Incorrectly formed print statement"));
					
				}
				phraseLeading = true;
			}
		}
			
	}
	
	private void parsePrint(boolean connected) throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("{") && !connected)
		{
			throw (new InvalidParseException("Print statemet missing opening bracket"));
		}
		
		cmp = t.nextToken();
		
		StringBuilder sb = new StringBuilder();
		while(!cmp.equals("}"))
		{
			sb.append(cmp);
			cmp = t.nextToken();
		}
		
		if(sb.length() <= 0)
		{
			throw (new InvalidParseException("Print statements cannot be empty"));
		}
		
		StringTokenizer temp = new StringTokenizer(sb.toString(), "-");
		
		parseInternalPrint(temp);
	}
	
	private void parseInBracketExpression() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(cmp.equals("("))
		{
			throw (new InvalidParseException("Missing start bracket in comparison"));
		}
		
		cmp = t.nextToken();
		
		if(!Pattern.matches(RegexPack.POSTF, cmp))
		{
			throw (new InvalidParseException("Bad comparison"));
		}
		
		cmp = t.nextToken();
		
		if(!cmp.equals(")"))
		{
			throw (new InvalidParseException("Missing closing bracket in relational expression"));
		}
		
	}
	
	private void parseRelationalExpressions() throws InvalidParseException
	{
		
		parseInBracketExpression();
		
		String cmp = t.nextToken();
		
		if(!Pattern.matches(RegexPack.REL_OP, cmp))
		{
			throw (new InvalidParseException("Invalid relational operation used"));
		}
		
		parseInBracketExpression();
				
	}
	
	private void parseBooleanExpression() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("("))
		{
			throw (new InvalidParseException("Missing an opening bracket in boolean expression"));
		}
		
		parseRelationalExpressions();
		
		cmp = t.nextToken();
		
		if(!cmp.equals(")"))
		{
			throw (new InvalidParseException("Missing an closing bracket in a relational expression"));
		}
		
		cmp = t.nextToken();
		
		if(cmp.equals("@") || cmp.equals("^"))
		{
			parseBooleanExpression();
		}
		else if(!cmp.equals(")"))
		{
			throw (new InvalidParseException("Invalid boolean expression"));
		}
		
			
		
		
		
		
//		StringBuilder sb = new StringBuilder();
//		sb.append(cmp);
//		int bracketCount = 1;
//		while(bracketCount > 0 && !t.isEmpty())
//		{
//			cmp = t.nextToken();
//			if(cmp.equals("("))
//				bracketCount++;
//			else if(cmp.equals(")"))
//				bracketCount--;
//			sb.append(" " + cmp);
//		}
	}
	
	
	private void parseMicroBody() throws InvalidParseException
	{
		String s = constructWithinDelimeters();
		StringTokenizer st = new StringTokenizer(s, "(\\s)");
		TokenizeInput t2 = new TokenizeInput(st);
		BodyParser bp = new BodyParser(t2);
		bp.parse();
	}
	
	private String constructWithinDelimeters() throws InvalidParseException
	{
		StringBuilder sb = new StringBuilder();
		String cmp = t.nextToken();
		int d = 1;
		while(d > 0 && !t.isEmpty())
		{
			if(cmp.equals("["))
			{
				d++;
			}
			else if(cmp.equals("]"))
			{
				d--;
			}
			
			if(d > 0)
			{
				sb.append(" " + cmp);
			}
			
			cmp = t.nextToken();
		}
		
		if(t.isEmpty())
			throw (new InvalidParseException("Missing ]"));
		return sb.toString();
	}
	
	public void parseBranch() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("("))
		{
			throw (new InvalidParseException("Missing opening parenthesis of branch"));
		}
		
		parseBooleanExpression();
		
		cmp = t.nextToken();
		
		if(!cmp.equals(")"))
		{
			throw (new InvalidParseException("Missing closing parenthesis of branch"));
		}
		
		
		
		cmp = t.nextToken();
		if(!cmp.equals("["))
		{
			throw (new InvalidParseException("Missing the opening parenthesis of a branch"));
		}
		
		parseMicroBody();
		cmp = t.nextToken();
		if(!cmp.equals("else"))
		{
			throw (new InvalidParseException("Missing else for branch"));
		}
		
		cmp = t.nextToken();
		
		if(!cmp.equals("["))
		{
			throw (new InvalidParseException("Missing the closing bracket of branch"));
		}
		
		parseMicroBody();
		
		
	}
	
	public void parseLoop() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("("))
		{
			throw (new InvalidParseException("Missing opening parenthesis of branch"));
		}
		
		parseBooleanExpression();
		
		cmp = t.nextToken();
		
		if(!cmp.equals(")"))
		{
			throw (new InvalidParseException("Missing closing parenthesis of branch"));
		}
		
		if(!cmp.equals("["))
		{
			throw (new InvalidParseException("Missing the opening parenthesis of a branch"));
		}
		
		parseMicroBody();
		cmp = t.nextToken();
		
		if(!cmp.equals("["))
		{
			throw (new InvalidParseException("Missing the closing bracket of branch"));
		}
	}
	
	
	public void parse() throws InvalidParseException
	{
		String cmp = t.getCurrent();
		
		if(cmp.equals("set"))
		{
			parseSet();
		}
		else if(cmp.equals("display"))
		{
			parsePrint(true);
		}
		else if(cmp.equals("branch"))
		{
			parseBranch();
		}
		else if(cmp.equals("loop"))
		{
			parseBooleanExpression();
		}
		else if(cmp.equals("endProg"))
		{
			if(t.isEmpty())
			{
				System.out.println("Successfully parsed");
				return;
			}
			
			throw (new InvalidParseException("Programme has contents after endProg"));
		}
		
			
		
	}
	

}
