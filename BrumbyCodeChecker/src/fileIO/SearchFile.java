package fileIO;

/**
 * This class searches through the provided directories and adds .cpp files to 'cppfiles' and adds .java files to 'javafiles'
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {
	private static ArrayList<String> cppfiles = new ArrayList<String>();
	private static ArrayList<String> javafiles = new ArrayList<String>();
	private String lang;

	/**
	 * @param file file to search for within the directories
	 * @return
	 */
	public static ArrayList<String> searchForFile(String file, String lang) {
		cppfiles.clear();
		javafiles.clear();
		if (lang.equals(".cpp")) {
			searchDirectory(new File(file), lang);
			return cppfiles;
		} else if (lang.equals(".java")) {
			searchDirectory(new File(file), lang);
			return javafiles;
		} else {
			System.out.print("No file extention provided");
			return null;
		}
	}

	/**
	 * @param directory directory to search
	 * @param lang      language to search for in each file in each directory
	 */
	public static void searchDirectory(File directory, String lang) {
		if (directory.isDirectory())
			search(directory, lang);
		else
			System.out.println(directory.getAbsoluteFile() + " is not a valid directory.");
	}

	/**
	 * @param file file currently searching for
	 * @param lang check each file for this file extension
	 */
	private static void search(File file, String lang) {
		if (file.isDirectory())
			if (file.canRead())
				for (File temp : file.listFiles())
					if (temp.isDirectory())
						search(temp, lang);
					else if (temp.getName().substring(temp.getName().length() - 4, temp.getName().length())
							.equals(".cpp"))
						cppfiles.add(temp.getAbsoluteFile().toString());
					else if (temp.getName().substring(temp.getName().length() - 5, temp.getName().length())
							.equals(".java"))
						javafiles.add(temp.getAbsoluteFile().toString());
	}

}
