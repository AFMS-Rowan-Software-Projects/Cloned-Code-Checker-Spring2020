package parser;

/**
 * Keeps track of the number of lines affected within a list of methods
 */
public class LinesAffected {

	private static int lines;
	
	public LinesAffected(int lines) {
		LinesAffected.lines = lines;
	}
	
	public static void addLinesAffected(int moreLines) {
		lines +=  moreLines;
	}
	
	public static int getLinesAffected(){
		return lines;
	}
}
