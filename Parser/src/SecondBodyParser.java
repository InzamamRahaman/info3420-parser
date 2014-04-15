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
		if(!Pattern.matches(RegexPack.OP, temp))
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
		parseAssignment();
		parse();
		
		
		
	}
	
	private void parseVariableLeading() throws InvalidParseException
	{
		String cmp = t.nextToken();
		
		if(!Pattern.matches(RegexPack.VARIABLE_NAME, cmp))
		{
			throw (new InvalidParseException("Bad variable name in display"));
		}
		
		cmp = t.nextToken();
		
		if(cmp.equals("}"))
		{
			t.pushTokensBack(cmp);
			return;
		}
		
		if(cmp.equals("-"))
		{
			parseVariableLeading();
			return;
		}
		
		throw (new InvalidParseException("Bad display statement"));
		
	}
	
	private void parsePhraseLeading() throws InvalidParseException
	{
		String cmp = t.nextToken();
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
			parseVariableLeading();
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
		String cmp = t.nextToken();
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
	
	public void parseRelExp() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("("))
			throw (new InvalidParseException("Missing ( in conditional"));
		cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.POSTF + ")", cmp))
			throw (new InvalidParseException("Bad operand in conditional"));
		cmp = t.nextToken();
		if(!cmp.equals(")"))
			throw (new InvalidParseException("Missing ) in conditional"));
		cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.REL_OP, cmp))
		{
			throw (new InvalidParseException("Bad relational operator in conditional"));
		}
		
		if(!cmp.equals("("))
			throw (new InvalidParseException("Bad ( in conditional"));
		cmp = t.nextToken();
		if(!Pattern.matches(RegexPack.POSTF + ")", cmp))
			throw (new InvalidParseException("Bad operand in conditional"));
		cmp = t.nextToken();
		if(!cmp.equals(")"))
			throw (new InvalidParseException("Bad ( in conditional"));
	}
	
	public void parseBoolExp() throws InvalidParseException
	{
		String cmp = t.nextToken();
		if(!cmp.equals("("))
			throw (new InvalidParseException("Missing bracket in conditional"));
		cmp = t.nextToken();
		if(cmp.equals("("))
		{
			t.pushTokensBack(cmp);
			parseBoolExp();
			cmp = t.nextToken();
			if(!cmp.equals("@") && !cmp.equals("^"))
				throw (new InvalidParseException("Bad boolean operator"));
			parseRelExp();
		}
		else
		{
			t.pushTokensBack(cmp);
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
		
		if(!cmp.equals("["))
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
			throw (new InvalidParseException("Missing ) in loop"));
		}
		parseBoolExp();
		cmp = t.nextToken();
		if(!cmp.equals(")"))
		{
			throw (new InvalidParseException("Missing ) in loop"));
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
		
		if(cmp.equals("set"))
		{
			parseSet();
		}
		else if(cmp.equals("display"))
		{
			parseDisplay();
		}
		else if(cmp.equals("loop"))
		{
			parseLoop();
		}
		else if(cmp.equals("branch"))
		{
			parseBranch();
		}
		else 
		{
			t.pushTokensBack(cmp);
			return;
		}
		
		
	}
	
	

}
