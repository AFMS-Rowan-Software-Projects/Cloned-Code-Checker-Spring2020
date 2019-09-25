package driver;

import fileIO.*;
import java.io.File;
import java.util.ArrayList;
import sablecc.analysis.*;
import sablecc.lexer.*;
import sablecc.node.*;
import parser.*;
import tokenlister.*;
import brumby.*;

/**
 * To run this program, right click Application.java 
 * Run As -- Run Configurations -- Arguments Tab
 * Copy and paste a pathname to a folder containing java, cpp, or ada files
 * After the pathname is copied, hit 'space' and type ".java", ".cpp", or ".ada"
 * Hit run.
 */
public class Application {
	public static void main(String[] args) {
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
		for (int i = 0; i < args.length - 1; i++) {
			// Searches and stores files in a directory with language extensions at the end of each file name
			directory_contents = SearchFile.searchForFile(args[i], args[args.length - 1]);
			for (String file : directory_contents) {
				totalFiles++;
				// Take file-path and store it into a String
				current_file = CFilesReader.readFile(file);
				// Clean up unused tokens in all files and store it into an ArrayList of "file_tokens"
				file_tokens = Parser.sanitize(Lister.ConvertToList(current_file, args[args.length - 1]));
				method_indices[0] = 0;
				while (method_indices[0] != -1) {
					method_indices = Parser.findMethod(file_tokens, method_indices[1]);
					if (method_indices[0] == -1) {	
						break;	
					} 
					else {
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
							if (Parser.similarity(method
									.getTokens(), method_tokens) == 1) {
								// Duplicate method found
								isDuplicate = true;
								// Record location of this duplicate, but throw away its tokenized
								// representation
								method.addDuplicate(qualified_name);
								// keeps track of files with duplicates
								if (!filesWithDuplicates.contains(file)) {
									filesWithDuplicates.add(file);
									filesAffected++;
								}
								break;
							}
						} // end of for(TokenizedMethod...)
						if (!isDuplicate) {
							methods.add(new TokenizedMethod(qualified_name, method_tokens));
						}
					}
				}
			}
		} // end of for(int i =...)
		
		// All directories processed and all methods added
		int locAffected = 0;
		perc = Math.round(perc/count * 100);
		for (TokenizedMethod method : methods) {
			if (method.hasDuplicate()) {// TODO change to file output
				System.out.println("\nDuplicate found in the following files:");
				System.out.println(method.toString());
				locAffected += method.linesAffected();
			}
		}
		System.out.println("Total Files: " + totalFiles);
		System.out.println("Affected Files: " + filesAffected);
		System.out.println("Lines of code affected: " + locAffected);
		// Will need to change this later.
		if(perc < 79)
			System.out.println("Low Percent: " + perc + "%");
		else {
			System.out.println("High Percent: " + perc + "%");
		}
	}
}

