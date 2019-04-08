package tokenlister;
import java.util.ArrayList;
import java.io.*;
import sablecc.node.*;
import sablecc.lexer.*;



public class Lister {
	private static Lexer lex;
	private static ArrayList<Token> tokens;

	

public static ArrayList<Token> ConvertToList(String a) {
		//InputStream StrStream = new 	ByteArrayInputStream(a.getBytes(Charset.forName("UTF-8")));
		
		lex = new Lexer
				(new PushbackReader
				(new StringReader(a), 1024)); 
		tokens = new ArrayList<Token>();
		Token T;
		try {
			T = lex.next();
			while (!(T instanceof EOF)) {
				//System.out.println(T.getClass() + ": " + T.getText());
				tokens.add(T);
				T = lex.next();
			}
		}
		catch (LexerException le)
		{ System.out.println ("Lexer Exception " + le); }
		catch (IOException ioe)
		{ System.out.println ("IO Exception " +ioe); }
		return tokens;
	} 
	
	
	
}
