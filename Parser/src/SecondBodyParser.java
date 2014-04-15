import java.util.regex.Pattern;

import org.omg.CORBA.DynAnyPackage.Invalid;


public class SecondBodyParser extends Parser
{

	public SecondBodyParser(MyTokenizers t)
	{
		super(t);
		// TODO Auto-generated constructor stub
	}
	
	private void parseAssignment() throws InvalidParseException
	{
		boolean corr = parseNumberOrVariable(true);
		if(!corr)
		{
			parsePostfix(false);
			return;
		}
		
		String cmp = t.nextToken();
		
		if(!cmp.equals("$"))
		{
			throw (new InvalidParseException("Missing $ in set statement"));
		}
	}
	
	private void parsePostfix(boolean pushBack) throws InvalidParseException
	{
		boolean correct = parseNumberOrVariable(pushBack);
		correct = parseNumberOrVariable(pushBack);
		String temp = t.nextToken();
		if(!Pattern.matches(RegexPack.OP + ")", temp))
		{
			throw (new InvalidParseException("Bad postfix expression"));
		}
		
		temp = t.nextToken();
		
		if(!temp.equals("$"))
		{
			t.pushTokensBack(temp);
			parsePostfix(pushBack);
		}
		
		
		
	}
	
	void parseVariable(boolean pushBack) throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
		{
			if(pushBack)
			{
				t.pushTokensBack(cmp);
				return;
			}
			
			throw (new InvalidParseException("Bad variable declaration"));
			
		}
	}
	
	private boolean parseNumberOrVariable(boolean pushBack) throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.NUMBERS, cmp))
		{
			if(!Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
			{
				if(pushBack)
				{
					t.pushTokensBack(cmp);
					return false;
				}
				throw (new InvalidParseException("Bad variable or number"));
				
			}
			
		}
		
		return true;
	}
	
	
	
	
	
	private void parseSet() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
		{
			throw (new InvalidParseException("Bad variable name in set statement"));
		}
		
		cmp = t.nextToken();
		if(!cmp.equals("to"))
		{
			throw (new InvalidParseException("Missing to in set statement"));
		}
		
		//cmp = t.nextToken();
		//parseAssignment();
		parseCalculable();
		cmp = t.nextToken();
		if(!cmp.equals("$"))
		{
			throw (new InvalidParseException("Missing $ at the end of an assignment statement"));
		}
		parse();
		
		
		
	}
	
	private void parseVariableLeading() throws InvalidParseException
	{
		String cmp = t.nextToken();
		
		if(!Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
		{
			//System.out.println(cmp);
			throw (new InvalidParseException("Bad variable name in display: " + cmp));
		}
		
		cmp = t.nextToken();
		
		if(cmp.equals("}"))
		{
			t.pushTokensBack(cmp);
			return;
		}
		
		if(cmp.equals("-"))
		{
			cmp = t.nextToken();
			t.pushTokensBack(cmp);
			if(cmp.equals("\""))
			{
				parsePhraseLeading();
			}
			else
			{
				parseVariableLeading();
			}
			return;
		}
		
		throw (new InvalidParseException("Bad display statement"));
		
	}
	
	private void parsePhraseLeading() throws InvalidParseException
	{
		String cmp = t.nextToken();
		//System.out.println(cmp);
		if(!cmp.equals("\""))
		{
			throw (new InvalidParseException("Missing \" in display statment"));
		}
		
		cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.ANY_STRING, cmp))
		{
			throw (new InvalidParseException("Bad string in display"));
		}
		
		cmp = t.nextToken();
		
		while(!cmp.equals("\"") && !t.isEmpty())
		{
			if(!Pattern.matches(RegexPack.ANY_STRING, cmp))
			{
				throw (new InvalidParseException("Bad string in display"));
			}
			
			cmp = t.nextToken();
		}
		
		if(t.isEmpty())
		{
			throw (new InvalidParseException("Bad display statmenet"));
		}
		
		//t.pushTokensBack(cmp);
		
		cmp = t.nextToken();
		
		if(cmp.equals("}"))
		{
			t.pushTokensBack(cmp);
			return;
		}
		else if(cmp.equals("-"))
		{
			cmp = t.nextToken();
			t.pushTokensBack(cmp);
			if(cmp.equals("\""))
			{
				parsePhraseLeading();
			}
			else
			{
				parseVariableLeading();
			}
			return;
		}
		
		throw (new InvalidParseException("Invalid display statement"));
		
		
		
		
	}
	
	private void parseDisplayInternals() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
		{
			t.pushTokensBack(cmp);
			parseVariableLeading();
		}
		else
		{
			t.pushTokensBack(cmp);
			parsePhraseLeading();
		}
		
	}
	
	private void parseDisplay() throws InvalidParseException
	{
		//System.out.println("Checking display");
		String cmp = t.nextToken();
		System.out.println(cmp);
		if(!cmp.equals("{"))
		{
			throw (new InvalidParseException("Missing opening { of display statement"));
		}
		
		parseDisplayInternals();
		
		cmp = t.nextToken();
		
		if(!cmp.equals("}"))
		{
			throw (new InvalidParseException("Missing } in display statement"));
		}
		
		cmp =  t.nextToken();
		if(!cmp.equals("$"))
			throw (new InvalidParseException("Missing $ after a display statement"));
		parse();
	}
	
	public void parseCalculable() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.NUMBERS, cmp) && !Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
		{
			throw (new InvalidParseException("Bad variable or number in calculable " + cmp));
		}
		
		cmp = t.nextToken();
		
		// We are not in a postfix expression
		if(!Pattern.matches(RegexPack.NUMBERS, cmp) && !Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
		{
			System.out.println("Pushed back " + cmp);
			t.pushTokensBack(cmp);
			return;
		}
		
		cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.OP + ")", cmp))
		{
			System.out.println("OP : " + cmp);
			throw (new InvalidParseException("Bad postfix expression with bad operation " + cmp));
			
		}
		
		cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.NUMBERS, cmp) && !Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
		{
			//System.out.println("Pushed back "+cmp);
			t.pushTokensBack(cmp);
			return;
		}
		else
		{
			//System.out.println("Continueing expression");
			t.pushTokensBack(cmp);
			parseCalculable();
		}
		
		
	}
	
	public void parseRelExp() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("("))
			throw (new InvalidParseException("Missing ( in conditional"));
		parseCalculable();
		cmp = t.nextToken();
		if(!cmp.equals(")"))
			throw (new InvalidParseException("Missing ) in conditional"));
		cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.REL_OP, cmp))
		{
			throw (new InvalidParseException("Bad relational operator in conditional"));
		}
		cmp = t.nextToken();
		if(!cmp.equals("("))
			throw (new InvalidParseException("Bad ( in conditional"));
		//System.out.println(cmp);
		parseCalculable();
		//if(!Pattern.matches(RegexPack.POSTF + ")", cmp))
			//throw (new InvalidParseException("Bad operand in conditional"));
		cmp = t.nextToken();
		if(!cmp.equals(")"))
			throw (new InvalidParseException("Missing ) in conditional"));
	}
	
	public void parseBoolExp() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("("))
			throw (new InvalidParseException("Missing bracket in conditional"));
		cmp = t.nextToken();
		t.pushTokensBack(cmp);
		if(cmp.equals("("))
		{
			//t.pushTokensBack(cmp);
			parseBoolExp();
			cmp = t.nextToken();
			if(!cmp.equals("@") && !cmp.equals("^"))
				throw (new InvalidParseException("Bad boolean operator"));
			parseRelExp();
		}
		else
		{
			t.pushTokensBack("(");
			parseRelExp();
		}
	}
	
	public void parseNested() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("["))
			throw (new InvalidParseException("Missing ["));
		parse();
		
		cmp = t.nextToken();
		if(!cmp.equals("]"))
			throw (new InvalidParseException("Missing ]"));
		
		
	}
	
	public void parseLoop() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("("))
		{
			throw (new InvalidParseException("Missing ) in loop"));
		}
		parseBoolExp();
		cmp = t.nextToken();
		if(!cmp.equals(")"))
		{
			throw (new InvalidParseException("Missing ) in loop"));
		}
		
		parseNested();
		parse();
		
	}
	
	public void parseBranch() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("("))
		{
			throw (new InvalidParseException("Missing ( in branch"));
		}
		parseBoolExp();
		cmp = t.nextToken();
		if(!cmp.equals(")"))
		{
			throw (new InvalidParseException("Missing ) in branch"));
		}
		
		parseNested();
		
		cmp = t.nextToken();
		if(!cmp.equals("else"))
			throw (new InvalidParseException("Missing else in branch"));
		parseNested();
		parse();
		
	}
	
	public void parse() throws InvalidParseException
	{
		
		String cmp = t.nextToken();
		//System.out.println(cmp);
		if(cmp.equals("set"))
		{
			parseSet();
			System.out.println("Successfully parsed set");
		}
		else if(cmp.equals("display"))
		{
			parseDisplay();
			System.out.println("Successfully parsed display");
		}
		else if(cmp.equals("loop"))
		{
			parseLoop();
			System.out.println("Successfully parsed loop");
		}
		else if(cmp.equals("branch"))
		{
			parseBranch();
			System.out.println("Successfully parsed branch");
		}
		else 
		{
			System.out.println("Pushed back " + cmp);
			t.pushTokensBack(cmp);
			return;
		}
		
		
	}
	
	

}
