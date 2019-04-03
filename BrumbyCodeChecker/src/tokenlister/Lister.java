package tokenlister;
import java.util.ArrayList;
import java.io.*;
import node.*;
import lexer.*;



public class Lister {
	private Lexer lex;
	private String fileLocation;
	private ArrayList<Token> constructorTokens;
	private int[] duplicateLocations;
	private int numDuplicates;
	public ArrayList<Token> tokens;

	public Lister(){
	}
	
	public Lister(String filePath, ArrayList<Token> token) {
		fileLocation = filePath;
		constructorTokens = token;
		duplicateLocations = new int[3];	}

public String getFileLocation() {
	return fileLocation;
}

public int getNumDuplicates() {
	return numDuplicates;
}

public int[] getDuplicateLocations() {
	return duplicateLocations;
}

public void addDuplicateLocation(int indexOfDuplicate) {
	if(numDuplicates == duplicateLocations.length) {
		int[] tempArray = duplicateLocations;
		duplicateLocations = new int[(numDuplicates+3)];
		for(int i = 0; i < numDuplicates; i++) {
			duplicateLocations[i] = tempArray[i];
		}
	}
	duplicateLocations[numDuplicates] = indexOfDuplicate;
	numDuplicates++;
}

public ArrayList<Token> AddToList(String a) {
		//InputStream StrStream = new 	ByteArrayInputStream(a.getBytes(Charset.forName("UTF-8")));
		
		lex = new Lexer
				(new PushbackReader
				(new StringReader(a), 1024)); 
		tokens = new ArrayList<Token>();
		Token T;
		try {
			T = lex.next();
			while (!(T instanceof EOF)) {
				tokens.add(lex.next());
			}
		}
		catch (LexerException le)
		{ System.out.println ("Lexer Exception " + le); }
		catch (IOException ioe)
		{ System.out.println ("IO Exception " +ioe); }
		return tokens;
	} 
	
	
	
}
