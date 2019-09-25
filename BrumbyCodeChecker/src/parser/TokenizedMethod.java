package parser;

import sablecc.node.Token;
import java.util.ArrayList;

/**
 * Holds tokens from each method within a file
 */

public class TokenizedMethod {
	private String fileLocation;
	private ArrayList<Token> tokens;
	private boolean hasDuplicate;
	private int duplicates = 1;
	
	/**
	 * 
	 * @param filePath
	 * @param tokenlist
	 */
	public TokenizedMethod(String filePath, ArrayList<Token> tokenlist) {
		fileLocation = filePath;
		tokens = tokenlist;
		hasDuplicate = false;
	}
	
	/**
	 * 
	 * @param filePath
	 */
	public void addDuplicate(String filePath) {
		hasDuplicate = true;
		fileLocation += "\n" + filePath;
		return;
	}

	/**
	 * 
	 * @return lines - the number of lines that are duplicate from multiple files within a directory
	 */
	public int linesAffected() {
		int lines;
		lines = tokens.get(tokens.size()-1).getLine() - tokens.get(0).getLine();
		
		if (duplicates > 0) {
			return lines * duplicates;
		}
		else {
			return lines;
		}
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
