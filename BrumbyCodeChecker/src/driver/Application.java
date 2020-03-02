package driver;

import fileIO.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
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
	 *             Works similar to the application main method, but accepts the
	 *             user inputted information rather than the file.io input
	 * 
	 * @return a String containing all of the information that needs to be displayed
	 *         to the screen
	 */

	public String plugin(String path, String type, int closeMatch) {
		ArrayList<String> directory_contents;
		ArrayList<TokenizedMethod> methods = new ArrayList<TokenizedMethod>();
		ArrayList<TokenizedMethod> similarMethods = new ArrayList<TokenizedMethod>();
		ArrayList<Token> file_tokens, method_tokens;

		String current_file;
		String qualified_name;
		String fileCompared = " ";
		String methodCompared = " ";
	
		Renamer rename;

		double perc = 0.0;
		int[] method_indices = new int[2];		
		int totalFiles = 0;
		int filesAffected = 0;
		int methodsAffected = 0;
		
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
					// Find method's start and end indexes
					method_indices = Parser.findMethod(file_tokens, method_indices[1]);

					if (method_indices[0] == -1) {
						break; // Reached end of file
					} else {
						// Remove method chunk and rename identifiers accordingly
						rename = new Renamer(Parser.subarray(file_tokens, method_indices[0], method_indices[1]));
						qualified_name = file + ":  " + rename.getTokens().get(1).getText() + ":  "
								+ rename.getTokens().get(1).getLine();
						rename.parseFile();
						method_tokens = rename.getTokens(); // tokens with id assignments

						for (TokenizedMethod method : methods) {
							// compute similarity percent
							perc = Parser.similarity(method.getTokens(), method_tokens);

							// If similarity percentage is equal or more than the specified close match,
							// store it
							if (perc >= closeMatch) {
								
								// Update count of affected files
								if (fileCompared.equals(file)) {
									filesAffected++;
									fileCompared = " ";
								}
								// Update count of affected methods
								if (methodCompared.equals(qualified_name)) {
									if(methodsAffected == 0) {
										methodsAffected = 2;
									}
									else {
										methodsAffected++;
									}
									methodCompared = " ";
								}
								
								// add to list of similar methods
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
		StringBuilder duplicatePaths = new StringBuilder();
		duplicatePaths.append("\nDuplicate found in the following files:\n");
		for (TokenizedMethod method : similarMethods) {
			duplicatePaths.append("\n").append(method.toString()).append("\n");
		}

		FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter("c:/temp/BCC_Output.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());

		PrintWriter printWriter = new PrintWriter(fileWriter);
		printWriter.print("BRUMBY CODE CHECKER");
		printWriter.print("\n Time Stamp: " + formatter.format(date));
		printWriter.print("\n File Path: " + path);
		printWriter.print("\n Language: " + type);
		printWriter.print("\n Min Percent: " + closeMatch + "\n");
		printWriter.print(duplicatePaths);
		printWriter.close();

		// built output for information window
		String outputString = new StringBuilder().append("Total Files: " + totalFiles + "\n")
				.append("Affected Methods: " + methodsAffected + "\n")
				.append("Affected Lines: " + LinesAffected.getLinesAffected() + "\n").toString();

		// return the formatted information to be used by the plugin output window
		return outputString;
	}

	/*
	 * To be used when running the application as a normal java application Takes in
	 * the file information via configuration and file io
	 */
	public static void main(String[] args) {
		ArrayList<String> directory_contents;
		ArrayList<TokenizedMethod> methods = new ArrayList<TokenizedMethod>();
		ArrayList<TokenizedMethod> similarMethods = new ArrayList<TokenizedMethod>();
		ArrayList<Token> file_tokens, method_tokens;
		//Cluster list implementation
		ArrayList<ArrayList<Integer>> clusterList = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> innerCluster = new ArrayList<>();
		//Distance Matrix
		HashMap<Integer,HashMap<Integer,Double>> distanceMatrix = new HashMap<Integer,HashMap<Integer,Double>>();

		String current_file;
		String qualified_name;
		String fileCompared = " ";
		String methodCompared = " ";
	
		Renamer rename;

		double perc = 0.0;
		int[] method_indices = new int[2];		
		int totalFiles = 0;
		int filesAffected = 0;
		int methodsAffected = 0;
		

		// Get close-match percentage from user
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
				// Clean up unused tokens in all files and store it into the ArrayList
				// "file_tokens"
				file_tokens = Parser.sanitize(Lister.ConvertToList(current_file, args[args.length - 1])); 
																											
				fileCompared = file;

				method_indices[0] = 0;
				while (method_indices[0] != -1) {
					// Find method's start and end indexes
					method_indices = Parser.findMethod(file_tokens, method_indices[1]);

					if (method_indices[0] == -1) {
						break; // Reached end of file
					} else {
						// Remove method chunk and rename identifiers accordingly
						rename = new Renamer(Parser.subarray(file_tokens, method_indices[0], method_indices[1])); // only
																													// method
						qualified_name = file + ":  " + rename.getTokens().get(1).getText() + ":  "
								+ rename.getTokens().get(1).getLine(); // saves (filepath : method name : line number)
																		// as a string
						methodCompared = qualified_name;
						rename.parseFile();
						method_tokens = rename.getTokens(); // tokens with id assignments

						for (TokenizedMethod method : methods) {

							// compute similarity percent
							perc = Parser.similarity(method.getTokens(), method_tokens);

							// If similarity percentage is equal or more than the specified close match,
							// store it
							if (perc >= closeMatch) {
								
								// Update count of affected files
								if (fileCompared.equals(file)) {
									filesAffected++;
									fileCompared = " ";
								}
								// Update count of affected methods
								if (methodCompared.equals(qualified_name)) {
									if(methodsAffected == 0) {
										methodsAffected = 2;
									}
									else {
										methodsAffected++;
									}
									methodCompared = " ";
								}
								
								// add to list of similar methods
								similarMethods.add(new TokenizedMethod(qualified_name, method.getLocation(), perc));
								
								//Add all unique tokenized methods identifiers to an Array List
								if(!(innerCluster.contains(method.getIdentifier()))) {
									innerCluster.add(method.getIdentifier());
									
								//creating a inner HashMap that will be applied to a new key
								HashMap<Integer,Double> innerHashMap =new HashMap<Integer,Double>();
								//Putting the similar method and percent in the inner hashMap
								innerHashMap.put(method_indices[0], perc);
								//Putting the method and the hashMap together
								distanceMatrix.put(method_indices[1], innerHashMap);
								}
							}
						}
						// Add to list of unique methods to keep checking
						methods.add(new TokenizedMethod(qualified_name, method_tokens));
					}
				}
			}
		} // end of for(int i =...)

		//Add the ArrayList of method identifiers to the cluster list
		clusterList.add(innerCluster);
	
		// All directories processed and all methods added
		for (TokenizedMethod method : similarMethods) {
			System.out.println("\nDuplicate found in the following methods:");
			System.out.println(method.toString());
		}

		System.out.println("Total Files: " + totalFiles);
		System.out.println("Affected Files: " + filesAffected);
		System.out.println("Affected Methods: " + methodsAffected);
		System.out.println("Affected Lines: " + LinesAffected.getLinesAffected());
	}
}
