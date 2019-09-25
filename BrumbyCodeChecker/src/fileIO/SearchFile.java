package fileIO;

/**
 * This class searches through the provided directories 
 * and looks for the lang parameters to check which files are of which language
 * and store them into their related ArrayList
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {
	private static ArrayList<String> cppfiles = new ArrayList<String>();
	private static ArrayList<String> javafiles = new ArrayList<String>();
	private static ArrayList<String> adafiles = new ArrayList<String>();
	private String lang;

	/**
	 * @param file - to search for within the directories
	 * @param lang - maps to the corresponding grammar in tokenlister
	 * @return an ArrayList of Files of Strings
	 */
	public static ArrayList<String> searchForFile(String file, String lang) {
		cppfiles.clear();
		javafiles.clear();
		adafiles.clear();
		if (lang.equals(".cpp")) {
			searchDirectory(new File(file), lang);
			return cppfiles;
		} 
		else if (lang.equals(".java")) {
			searchDirectory(new File(file), lang);
			return javafiles;
		} 
		else if (lang.equals(".ada")) {
			searchDirectory(new File(file), lang);
			return adafiles;
		}
		else {
			System.out.print("No file extension provided");
			return null;
		}
	}

	/**
	 * @param directory - directory to search
	 * @param lang      - language to search for in each file in each directory
	 */
	public static void searchDirectory(File directory, String lang) {
		if (directory.isDirectory())
			search(directory, lang);
		// getAbsoluteFile Returns the absolute form of this abstract pathname
		else
			System.out.println(directory.getAbsoluteFile() + " is not a valid directory.");
	}

	/**
	 * @param file - file currently searching for
	 * @param lang - check each file for this file extension
	 */
	private static void search(File file, String lang) {
		if (file.isDirectory())
			if (file.canRead())
				for (File temp : file.listFiles())
					if (temp.isDirectory())
						search(temp, lang);
					// Add cpp & hpp files to ArrayList of CPP Files
					else if ((temp.getName().length() >= 5) && (temp.getName().substring(temp.getName().length() - 4, temp.getName().length())
							.equals(".cpp")) || temp.getName().substring(temp.getName().length() - 4, temp.getName().length())
							.equals(".hpp"))
						cppfiles.add(temp.getAbsoluteFile().toString());
					// Add java files to ArrayList of Java Files
					else if ((temp.getName().length() >= 6) && (temp.getName().substring(temp.getName().length() - 5, temp.getName().length())
							.equals(".java")))
						javafiles.add(temp.getAbsoluteFile().toString());
					// Add ada files to the ArrayList of Ada Files
					else if ((temp.getName().length() >= 5) && (temp.getName().substring(temp.getName().length() - 4, temp.getName().length())
							.equals(".ada")))
						
						adafiles.add(temp.getAbsoluteFile().toString());
	}
}
