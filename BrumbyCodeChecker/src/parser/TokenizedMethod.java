package parser;

import java.util.ArrayList;

import node.Token;

public class TokenizedMethod {
	private String fileLocation;
	private ArrayList<Token> constructorTokens;
	private int[] duplicateLocations;
	private int numDuplicates;
	public ArrayList<Token> tokens;
	
	public TokenizedMethod(String filePath, ArrayList<Token> token) {
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

}
