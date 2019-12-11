package parser;

import sablecc.node.Token;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Holds tokens from each method within a file
 */
public class TokenizedMethod {
	private String fileLocation;
	private String secondFileLocation;
	private ArrayList<Token> tokens;
	private double similarityPercent;

	/**
	 * To be stored in a list of methods to keep checking for similarity
	 * 
	 * @param filePath		Location of method
	 * @param tokenlist		Method's content
	 */
	public TokenizedMethod(String filePath, ArrayList<Token> tokenlist) {
		fileLocation = filePath;
		tokens = tokenlist;
	}

	/**
	 * To be stored as a duplicate found from the methods compared and the
	 * percentage of similarity
	 * 
	 * @param filePath1 	location of first method
	 * @param filepath2		location of second method
	 * @param percent		computed similarity percentage
	 */
	public TokenizedMethod(String filePath1, String filepath2, double percent) {
		fileLocation = filePath1;
		secondFileLocation = filepath2;
		similarityPercent = percent;
	}
	
	public ArrayList<Token> getTokens() {
		return tokens;
	}

	public String getLocation() {
		return fileLocation;
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("0.00");
		return fileLocation + "\n" + secondFileLocation + "\n----------> " + df.format(similarityPercent) + "%";
	}
}