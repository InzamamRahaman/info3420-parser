
public class RegexPack 
{
	
	public final static String OP = "(\\+|-|\\*|\\)";
	public final static String PROGRAMME_NAME = "[a-zA-Z]{1,16}";
	public final static String NUMBERS = "((\\+|-)?\\d+(\\.\\d+)?)";
	public final static String VARIABLE_NAME = "([a-z](([a-zA-Z]|[0-9]){0,7})";
	public final static String POSTFIX_ELEMENT = 
			"("+ NUMBERS + "|" + VARIABLE_NAME + ")";
	public final static String POSTFIX_EXP = 
			"(" + POSTFIX_ELEMENT  + "(\\s)+" + POSTFIX_ELEMENT + "(\\s)+" + OP + ")+";
	public final static String POSTF = 
			"(" + POSTFIX_ELEMENT + "|" + POSTFIX_EXP + ")";
	public final static String POSTFIX = 
			"(" + POSTF + "(\\s)+" + POSTF + "(\\s)+" + POSTF + ")";
	public final static String ANY_STRING = 
			"(\"" + "(\\w|\\d|\\s)+" + "\")";
	public final static String REL_OP = "(>|<|=)";
	
	

}
