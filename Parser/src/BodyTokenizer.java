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
		while(idx < arr.length)
		{
			if(!tryKeywords())
			{
				readUntilStop();
			}
			skipWhiteSpace();
		}
		
		String[] strs = (String[]) tokens.toArray();
		for(int i = strs.length - 1; i >= 0; i++)
			toConsider.push(strs[i]);
		
	}
	
	private boolean isStopSymbol(char ch)
	{
		return RegexPack.STOP_SYMBOLS.contains(String.valueOf(ch));
	}
	
	private boolean isWhiteSpace(char ch)
	{
		return (ch == '\n' || ch == '\t' || ch == '\r' || ch == ' ');
	}
	
	private void skipWhiteSpace()
	{
		while(isWhiteSpace(arr[idx]))
			idx++;
	}
	
	private boolean tryWord(String str)
	{
		int curr = idx;
		char[] temp = str.toCharArray();
		for(int i = 0; i < (str.length() - curr); i++)
		{
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
		this.current = toConsider.pop();
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
		toConsider.push(str);
		
	}
	
	
	
	

}
