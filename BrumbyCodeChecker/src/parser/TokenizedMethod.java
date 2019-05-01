package parser;

import sablecc.node.Token;
import ogn
import java.util.ArrayList;

public class TokenizedMethod {
	private String fileLocation;
	private ArrayList<Token> tokens;
	private boolean hasDuplicate;
	private int duplicates=0;
	
	public TokenizedMethod(String filePath, ArrayList<Token> tokenlist) {
		fileLocation = filePath;
		tokens = tokenlist;
		hasDuplicate = false;
	}
	
	public void addDuplicate(String filePath) {
		hasDuplicate = true;
		fileLocation += "\n" + filePath;
		return;
	}

	public int linesAffected()
	{
		int lines;

		lines = tokens.get(tokens.size()-1).getLine() - tokens.get(0).getLine();
		
		if (duplicates>0)
			return lines*duplicates;
		else
			return lines;
	}
	
	public ArrayList<Token> getTokens(){
		return tokens;
	}
	
	public String toString() {
		return fileLocation;
	}
	
	public boolean hasDuplicate() {
		if (hasDuplicate)
			duplicates ++;
		
		return hasDuplicate;
	}

}
