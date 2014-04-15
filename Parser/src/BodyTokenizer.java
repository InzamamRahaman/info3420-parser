import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;


public class BodyTokenizer implements MyTokenizers
{
	
	private String input;
	private String current;
	private char[] arr;
	private int idx;
	private LinkedList<String> tokens;
	private Stack<String> toConsider;
	
	
	
	public BodyTokenizer(String input)
	{
		this.input = input;
		arr = input.toCharArray();
		idx = 0;
		tokens = new LinkedList<String>();
		toConsider = new Stack<String>();
		tokenizeAccodingToGrammar();
	}
	
	private void tokenizeAccodingToGrammar()
	{
		skipWhiteSpace();
		//System.out.println("Skipped all beginning whitespace");
		while(idx < arr.length)
		{
			
			if(!tryKeywords())
			{
				//System.out.println("Did not find a keyword");
				readUntilStop();
			}
			skipWhiteSpace();
		}
		
//		String[] strs = (String[]) tokens.toArray();
//		for(int i = strs.length - 1; i >= 0; i++)
//			toConsider.push(strs[i]);
		
		Stack<String> A = new Stack<String>();
		
//		for(String S : tokens)
//			A.push(S);
//		
//		for(String S : A)
//		{
//			if(!S.isEmpty())
//			{
//				System.out.println("Pushed " + S);
//				toConsider.push(S);
//			}
//			
//		}
		
	}
	
	private boolean isStopSymbol(char ch)
	{
		return (RegexPack.STOP_SYMBOLS.contains(String.valueOf(ch)) || ch == '"');
	}
	
	private boolean isWhiteSpace(char ch)
	{
		return (ch == '\n' || ch == '\t' || ch == '\r' || ch == ' ');
	}
	
	private void skipWhiteSpace()
	{
		while(idx < input.length() && isWhiteSpace(arr[idx]))
			idx++;
	}
	
	private boolean tryWord(String str)
	{
		//System.out.println("Trying " + str);
		int curr = idx;
		char[] temp = str.toCharArray();
		for(int i = 0; i < str.length(); i++)
		{
			
			if(idx >= input.length())
			{
				idx = curr;
				return false;
			}
			
			if(temp[i] != arr[idx])
			{
				idx = curr;
				return false;
			}
			
			idx++;
				
		}
		
		return true;
		
	}
	
	private boolean tryKeywords()
	{
		String[] keywords = {"set", "branch", "to", "display", "loop", "endProg"};
		for (String S : keywords)
		{
			//System.out.println("Considering " + S);
			if(tryWord(S))
			{
				tokens.add(S);
				return true;
			}
		}
		
		return false;
		
	}
	
	private void readUntilStop()
	{
		String temp = "";
		while(!isStopSymbol(arr[idx]) && !isWhiteSpace(arr[idx]))
		{
			temp = temp + String.valueOf(arr[idx]);
			idx++;
		}
		
		tokens.add(temp);
		if(!isWhiteSpace(arr[idx]))
				tokens.add(String.valueOf(arr[idx]));
		idx++;
	}
	
	
	@Override
	public String nextToken() 
	{
		this.current = tokens.pop();
		while(this.current.isEmpty())
			this.current = tokens.pop();
		//this.current = toConsider.pop();
		//System.out.println(current);
		return current;
		
	}

	@Override
	public String getCurrent()
	{
		return current;
	}

	@Override
	public boolean isEmpty() {
		return tokens.isEmpty();
	}

	@Override
	public String getRemainingStringSeparatedBySpaces() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void pushTokensBack(String str)
	{
		tokens.push(str);
		
	}
	
	
	
	

}
