package fileIO;

/**
 * This class searches through the provided directories and adds .cpp files to the 'files' ArrayList
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {
	private static ArrayList<String> files = new ArrayList<String>();

	/**
	 * Constructor for SearchFile
	 */
	//public SearchFile(String fileToSearch) {
		//SearchFile.fileToSearch = fileToSearch;
	//}
	
	/**
	 * @param file file to search for within the directories
	 * @return
	 */
	public static ArrayList<String> searchForFile(String file) {
		files.clear();
		searchDirectory(new File(file), file);
		return files;
	}

	/**
	 * @param directory  directory to search
	 * @param fileSearch file to search for in each directory
	 */
	public static void searchDirectory(File directory, String fileToSearch) {
		//setFiletoSearch(fileToSearch);
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
					else if (temp.getName().substring(temp.getName().length() - 4, temp.getName().length()).equals(".cpp"))
						files.add(temp.getAbsoluteFile().toString());
	}

	/**
	 * @param fileSearch file to search
	 */
	//public static void setFiletoSearch(String fileToSearch) {
		//SearchFile.fileToSearch = fileToSearch;
	//}

	

}
