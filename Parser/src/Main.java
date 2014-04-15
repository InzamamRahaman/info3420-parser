import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class Main
{
	
	
	public static void main(String[] args)
	{
		try {
			
			String input = readFile("C://Users/Administrator/Documents/GitHub/info3420-parser/Parser/src/test1.txt");
			//String input = readFile("C://Users/Administrator/Documents/GitHub/info3420-parser/Parser/src/test1.txt",Charset.defaultCharset());
			MainParser mp = new MainParser(new TokenizeInput(input));
			
			try {
				mp.parse();
			} catch (InvalidParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public static String readFile( String file ) throws IOException {
	    BufferedReader reader = new BufferedReader( new FileReader (file));
	    String         line = null;
	    StringBuilder  stringBuilder = new StringBuilder();
	    String         ls = System.getProperty("line.separator");

	    while( ( line = reader.readLine() ) != null ) {
	        stringBuilder.append( line );
	        stringBuilder.append("\n");
	    }

	    return stringBuilder.toString();
	}
	/*static String readFile(String path, Charset encoding) throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}*/


}
