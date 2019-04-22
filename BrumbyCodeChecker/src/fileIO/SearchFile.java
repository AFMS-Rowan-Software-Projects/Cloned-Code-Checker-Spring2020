package fileIO;

/**
 * This class searches through the provided directories and adds .cpp files to the 'files' ArrayList
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {
	private static ArrayList<String> cppfiles = new ArrayList<String>();
	private static ArrayList<String> javafiles = new ArrayList<String>();

	/**
	 * Constructor for SearchFile
	 */
	// public SearchFile(String fileToSearch) {
	// SearchFile.fileToSearch = fileToSearch;
	// }

	/**
	 * @param file file to search for within the directories
	 * @return
	 */
	public static ArrayList<String> searchForFile(String file) {
		cppfiles.clear();
		javafiles.clear();
		if (file.substring(file.length() - 4).equals(".cpp")) {
			searchDirectory(new File(file.substring(0, file.length() - 5)), file.substring(0, file.length() - 5));
			return cppfiles;
		} else if (file.substring(file.length() - 5).equals(".java")) {
			searchDirectory(new File(file.substring(0, file.length() - 6)), file.substring(0, file.length() - 6));
			return javafiles;
		} else {
			System.out.print("No file extention provided");
			return null;
		}
	}

	/**
	 * @param directory  directory to search
	 * @param fileSearch file to search for in each directory
	 */
	public static void searchDirectory(File directory, String fileToSearch) {
		// setFiletoSearch(fileToSearch);
		if (directory.isDirectory())
			search(directory);
		else
			System.out.println(directory.getAbsoluteFile() + " is not a valid directory.");
	}

	/**
	 * @param file file currently searching for
	 */
	private static void search(File file) {
		if (file.isDirectory())
			if (file.canRead())
				for (File temp : file.listFiles())
					if (temp.isDirectory())
						search(temp);
					else if (temp.getName().substring(temp.getName().length() - 4, temp.getName().length())
							.equals(".cpp"))
						cppfiles.add(temp.getAbsoluteFile().toString());
					else if (temp.getName().substring(temp.getName().length() - 5, temp.getName().length())
							.equals(".java"))
						javafiles.add(temp.getAbsoluteFile().toString());
	}

	/**
	 * @param fileSearch file to search
	 */
	// public static void setFiletoSearch(String fileToSearch) {
	// SearchFile.fileToSearch = fileToSearch;
	// }

}
