package fileIO;

/**
 * This class searches through the provided directories and adds 
 */

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SearchFile {
	private String fileSearch;
	private ArrayList<String> files = new ArrayList<String>();

	/**
	 * @param file file to search for within the directories
	 * @return
	 */
	public ArrayList<String> searchForFile(String file) {
		SearchFile searchFile = new SearchFile();
		searchFile.searchDirectory(new File("Directory to search"), file);
		return files;
	}

	/**
	 * @param directory  directory to search
	 * @param fileSearch file to search for in each directory
	 */
	public void searchDirectory(File directory, String fileSearch) {
		setFiletoSearch(fileSearch);
		if (directory.isDirectory())
			search(directory);
		else
			System.out.println(directory.getAbsoluteFile() + " is not a valid directory.");
	}

	/**
	 * @param file file currently searching for
	 */
	private void search(File file) {
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
	public void setFiletoSearch(String fileSearch) {
		this.fileSearch = fileSearch;
	}

	public String getFileToSearch() {
		return fileSearch;
	}

}
