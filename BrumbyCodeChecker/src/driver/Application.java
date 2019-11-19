package driver;

import fileIO.*;
import java.util.ArrayList;
import java.util.Scanner;
import sablecc.node.*;
import parser.*;
import tokenlister.*;
import brumby.*;

/**
 * To run this program, right click Application.java Run As -- Run
 * Configurations -- Arguments Tab Copy and paste a pathname to a folder
 * containing java, cpp, or ada files After the pathname is copied, hit 'space'
 * and type ".java", ".cpp", or ".ada" Hit run.
 */

public class Application {

	/**
	 * Method to be called for Checker Plugin
	 * 
	 * @param path - file path for classes to be checked
	 * 
	 * @param type - what is the supported language: .java .cpp
	 * 
	 * Works similar to the application main method, but accepts the user inputted
	 * information rather than the file.io input
	 * 
	 * @return a String containing all of the information that needs to be displayed
	 * to the screen
	 */
	public String plugin(String path, String type) {
		ArrayList<String> directory_contents;
		ArrayList<TokenizedMethod> methods = new ArrayList<TokenizedMethod>();
		ArrayList<Token> file_tokens, method_tokens;

		String current_file;
		String qualified_name;

		int[] method_indices = new int[2];

		boolean isDuplicate;
		double perc = 0.0;
		int count = 0;
		int totalFiles = 0;
		int filesAffected = 0;
		ArrayList<String> filesWithDuplicates = new ArrayList<>();
		Renamer rename;

		// Looks in the Arguments parameters in Run Configurations
		String[] parts = path.split(" ");
		for (String part : parts) {
			// Searches and stores files in a directory with language extensions at the end
			// of each file name
			directory_contents = SearchFile.searchForFile(part, type);
			for (String file : directory_contents) {
				totalFiles++;
				// Take file-path and store it into a String
				current_file = CFilesReader.readFile(file);
				// Clean up unused tokens in all files and store it into an ArrayList of
				// "file_tokens"
				file_tokens = Parser.sanitize(Lister.ConvertToList(current_file, type));
				method_indices[0] = 0;
				while (method_indices[0] != -1) {
					method_indices = Parser.findMethod(file_tokens, method_indices[1]);
					if (method_indices[0] == -1) {
						break;
					} else {
						// Remove method chunk and rename identifiers accordingly
						rename = new Renamer(Parser.subarray(file_tokens, method_indices[0], method_indices[1]));
						qualified_name = file + ":" + rename.getTokens().get(1).getText() + ":"
								+ rename.getTokens().get(1).getLine();
						rename.parseFile();
						method_tokens = rename.getTokens();
						isDuplicate = false;
						for (TokenizedMethod method : methods) {
							// Get the similarity of 2 files or more.
							// If there's 3 files, 2 of which are 100% match, but the 3rd
							// file is not 100% the same as the others, similarity will go down
							// This similarity is for all files in a directory.
							// We may need to come back to change this
							perc = perc + Parser.similarity(method.getTokens(), method_tokens);
							count++;
							// If both methods are exactly the same, (==1) then they are exact duplicates
							if (Parser.similarity(method.getTokens(), method_tokens) == 1) {
								// Duplicate method found
								isDuplicate = true;
								// Record location of this duplicate, but throw away its tokenized
								// representation
								//method.addDuplicate(qualified_name);
								// keeps track of files with duplicates
								if (!filesWithDuplicates.contains(file)) {
									filesWithDuplicates.add(file);
									filesAffected++;
								}
								break;
							}
						} // end of for(TokenizedMethod...)
						if (!isDuplicate) {
							//methods.add(new TokenizedMethod(qualified_name, method_tokens));
						}
					}
				}
			}
		} // end of for(int i =...)
		
		// All directories processed and all methods added
				int locAffected = 0;
				perc = Math.round(perc / count * 100);
				StringBuilder duplicatePaths = new StringBuilder();
				for (TokenizedMethod method : methods) {
					if (method.hasDuplicate()) {// TODO change to file output
						/*
						System.out.println("\nDuplicate found in the following files:");
						System.out.println(method.toString());
						*/
						duplicatePaths.append("\nDuplicate found in the following files:\n")
									  .append(method.toString())
									  .append("\n");
						locAffected += method.linesAffected();
					}
				}

		// percent duplication that is in the files
		String highOrLow;
		if (perc < 79)
			highOrLow = "Low Percent: ";
		else {
			highOrLow = "High Percent: ";
		}

		// built output for information window
		String outputString = new StringBuilder().append(duplicatePaths.toString()).append("\n\n")
				.append("Total Files: " + totalFiles + "\n").append("Affected Files: " + filesAffected + "\n")
				.append("Lined of Code Affected: " + locAffected + "\n").append(highOrLow + perc + "%").toString();

		// return the formatted information to be used by the plugin output window
		return outputString;
	}

	
	
	
	
	
	
	
	

	/*
	 * To be used when running the application as a normal java application
	 * Takes in the file information via configuration and file io
	 */
	public static void main(String[] args) {
		ArrayList<String> directory_contents;
		ArrayList<TokenizedMethod> methods = new ArrayList<TokenizedMethod>(); 
		ArrayList<TokenizedMethod> similarMethods = new ArrayList<TokenizedMethod>();
		ArrayList<sablecc.ada_node.Token> afile_tokens = null, amethod_tokens;
		ArrayList<sablecc.node.Token> file_tokens = null, method_tokens;

		String current_file;
		String qualified_name;

		int[] method_indices = new int[2];

		boolean isDuplicate;
		double perc = 0.0;
		int totalFiles = 0;
		int filesAffected = 0;
		int locAffected = 0;
		Renamer rename;

		//Get close-match percentage from user
        Scanner in = new Scanner(System.in); 
        System.out.print("\nSpecify a percentage for a close match: "); 
        int closeMatch = in.nextInt();
        in.close();
		
		// Looks in the Arguments parameters in Run Configurations
		for (int i = 0; i < args.length - 1; i++) {
			// Searches and stores files in a directory with language extensions at the end
			// of each file name
			directory_contents = SearchFile.searchForFile(args[i], args[args.length - 1]);
			for (String file : directory_contents) {
				totalFiles++;
				// Take file-path and store it into a String
				current_file = CFilesReader.readFile(file);
				// Clean up unused tokens in all files and store it into the ArrayList "file_tokens"
				if( args[args.length - 1].equals(".ada")) {
					afile_tokens = AdaParser.sanitize(AdaLister.ConvertToList(current_file, args[args.length - 1]));
				}
				else {
					file_tokens = Parser.sanitize(Lister.ConvertToList(current_file, args[args.length - 1])); //convert to tokens (lang)
				}
				
				
				method_indices[0] = 0; 
				while (method_indices[0] != -1) {
					//Find method's start and end indexes
					if( args[args.length - 1].equals(".ada")) {
						method_indices = AdaParser.findAdaMethod(afile_tokens, method_indices[1]); 
					}
					else {
					method_indices = Parser.findMethod(file_tokens, method_indices[1]); 
					}
					if (method_indices[0] == -1) {
						break; // Reached end of file
					} else {
						// Remove method chunk and rename identifiers accordingly
						rename = new Renamer(Parser.subarray(file_tokens, method_indices[0], method_indices[1])); //only method
						qualified_name = file + ":  " + rename.getTokens().get(1).getText() + ":  "
								+ rename.getTokens().get(1).getLine();	//saves (filepath : method name : line number) as a string
						rename.parseFile();
						method_tokens = rename.getTokens(); //tokens with id assignments
						isDuplicate = false;
						
						for (TokenizedMethod method : methods) {
							
							//compute similarity percent
							perc = Parser.closeEnough(method.getTokens(), method_tokens);  
							
							// If similarity percentage is equal or more than the specified close match, store it
							if (perc >= closeMatch) {
								isDuplicate = true;
								
								//add to list of similar methods
								similarMethods.add(new TokenizedMethod(qualified_name, method.getLocation(), perc)); 
							}
						}
						// Add to list of unique methods to keep checking
						methods.add(new TokenizedMethod(qualified_name, method_tokens));
					}
				}
			}
		} // end of for(int i =...)

		// All directories processed and all methods added
		for (TokenizedMethod method : similarMethods) {
				System.out.println("\nDuplicate found in the following methods:");
				System.out.println(method.toString());
				//locAffected += method.linesAffected();
		} 
		
		System.out.println("Total Files: " + totalFiles);
		//System.out.println("Affected Files: " + );
		//System.out.println("Lines of code affected: " + locAffected);
	}
}