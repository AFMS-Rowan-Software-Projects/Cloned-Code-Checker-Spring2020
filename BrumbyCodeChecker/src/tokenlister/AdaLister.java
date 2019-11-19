package tokenlister;


import java.util.ArrayList;
import java.util.Collection;

import sablecc.ada_node.*;

import java.io.*;

public class AdaLister {
	private static sablecc.lexer.Lexer lex;
	private static sablecc.java_lexer.Lexer jlex;
	private static sablecc.ada_lexer.Lexer alex;
	private static ArrayList<Token> tokens;

	

public static ArrayList<Token> ConvertToList(String a, String lang) {
		//InputStream StrStream = new 	ByteArrayInputStream(a.getBytes(Charset.forName("UTF-8")));
	tokens = new ArrayList<Token>();
	Token T;

		if(lang.equals(".ada")) {
			alex = new sablecc.ada_lexer.Lexer
					(new PushbackReader
					(new StringReader(a), 1024));
			try {
				T =  alex.next();
				while (!(T instanceof EOF)) {
					//System.out.println(T.getClass() + ": " + T.getText());
					tokens.add(T);
					T = alex.next();
				}
				System.out.println(tokens);

			}
			catch (sablecc.ada_lexer.LexerException le)
			{ System.out.println ("Lexer Exception " + le); }
			catch (IOException ioe)
			{ System.out.println ("IO Exception " + ioe); }
		}
	
		return tokens;
	} 
}
