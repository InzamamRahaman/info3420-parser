
public interface MyTokenizers
{
	
	public String nextToken();
	
	
	public String getCurrent();
	
	
	public boolean isEmpty();
	
	
	
	public String getRemainingStringSeparatedBySpaces();
	
	public void pushTokensBack(String str);
	

}
