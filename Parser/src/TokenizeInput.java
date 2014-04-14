import java.util.StringTokenizer;


public class TokenizeInput
{
	
	private StringTokenizer st;
	private String current;
	
	
	public TokenizeInput(String input)
	{
		st = new StringTokenizer(input, "\\ ");
	}
	
	public TokenizeInput(StringTokenizer st2)
	{
		st = st2;
	}

	public String nextToken()
	{
		current = st.nextToken();
		return current;
	}
	
	public String getCurrent()
	{
		return current;
	}
	
	
	public boolean isEmpty()
	{
		return st.hasMoreElements();
	}
	
	
	

}
