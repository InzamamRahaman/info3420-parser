import java.util.StringTokenizer;


public class TokenizeInput implements MyTokenizers
{
	
	private StringTokenizer st;
	private String current;
	
	
	
	
	public TokenizeInput(String input)
	{
		st = new StringTokenizer(input);
	}
	
	public TokenizeInput(String input, String delim)
	{
		st = new StringTokenizer(input, delim);
	}
	
	public TokenizeInput(StringTokenizer st2)
	{
		st = st2;
	}

	public String nextToken()
	{
		current = st.nextToken();
		System.out.println(current);
		return current;
	}
	
	public String getCurrent()
	{
		return current;
	}
	
	
	public boolean isEmpty()
	{
		return !st.hasMoreElements();
	}
	
	
	public String getRemainingStringSeparatedBySpaces()
	{
		StringBuilder sb = new StringBuilder();
		while(st.hasMoreElements())
		{
			
			sb.append(st.nextToken() + " ");
		}
		
		return sb.toString();
	}
	
	public String getAll()
	{
		return st.toString();
	}

	@Override
	public void pushTokensBack(String str) {
		// TODO Auto-generated method stub
		
	}
	
	

}
