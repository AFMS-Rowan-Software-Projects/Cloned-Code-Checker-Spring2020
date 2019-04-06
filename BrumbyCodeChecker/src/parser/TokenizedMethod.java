package parser;

import java.util.ArrayList;

import node.Token;

public class TokenizedMethod {
	private String fileLocation;
	private ArrayList<Token> tokens;
	private boolean hasDuplicate;
	
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
	
	public ArrayList<Token> getTokens(){
		return tokens;
	}
	
	public String toString() {
		return fileLocation;
	}
	
	public boolean hasDuplicate() {
		return hasDuplicate;
	}


}
