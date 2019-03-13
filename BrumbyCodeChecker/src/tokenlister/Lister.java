package tokenlister;
import java.util.ArrayList;
import java.io.*;
import node.*;
import lexer.*;
import java.nio.charset.*;



public class Lister {
	private Lexer lex;
	public ArrayList<Token> tokens;

	public Lister(){
	}
	
	public ArrayList<Token> AddToList(String a) {
		InputStream StrStream = new ByteArrayInputStream(a.getBytes(Charset.forName("UTF-8")));
		lex = new Lexer
				(new PushbackReader
				((Reader) StrStream, 1024)); //cant cast from inputstream to reader.
		tokens = new ArrayList<Token>();
		Token T = lex.next();
		while (!(T instanceof EOF)) {
			tokens.add(lex.next());
		}
		return tokens;
	} 
	
	
	
}
