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

public class Application {
	public static void main(String[] args) {
		ArrayList<String> directory_contents;
		ArrayList<TokenizedMethod> methods = new ArrayList<TokenizedMethod>();
		ArrayList<Token> file_tokens, method_tokens;
		String current_file;
		int[] method_indices = new int[2];
		boolean isDuplicate;
		Renamer rename;
		String qualified_name;
		for(int i = 0; i < args.length; i++) {
			directory_contents = SearchFile.searchForFile(args[i]);//ANDREW AND KEVIN- your code replaces this line
			//directory_contents = directoryplaceholder(args[i]);
			//System.out.println("Files: " + directory_contents.size());
			for(String file : directory_contents) {
				//System.out.println(file);
				current_file = CFilesReader.readFile(file);//Take filepath and load it into a String
				//System.out.println("File loaded in");
				//call sanitize method below
				file_tokens = Parser.sanitize(Lister.ConvertToList(current_file));
				/*for(int k = 0; k < file_tokens.size(); k++) {
					System.out.println(k + " " +file_tokens.get(k).getClass() + ": " + file_tokens.get(k).getText() );
				}*/
				//System.out.println("File Tokenized");
				method_indices[0] = 0;
				while(method_indices[0] != -1) {
					method_indices = Parser.findMethod(file_tokens, method_indices[1]);
					//System.out.println("Found method");
					if(method_indices[0] == -1) {
						break;
					}else {
						//Remove method chunk and rename identifiers accordingly
						//System.out.println("Method indices: " + method_indices[0] + ", " + method_indices[1]);
						rename = new Renamer(Parser.subarray(file_tokens, method_indices[0], method_indices[1]));
						qualified_name = file + ":" + rename.getTokens().get(1).getText();
						rename.parseFile();
						method_tokens = rename.getTokens();
						//System.out.println("method_tokens size: " + method_tokens.size());
						//System.out.println("methods size: " + methods.size());
						isDuplicate = false;
						for(TokenizedMethod method : methods) {
							if(Parser.similarity(method.getTokens(), method_tokens) == 1) {
								//Duplicate method found
								isDuplicate = true;
								//Record location of this duplicate, but throw away its tokenized representation
								method.addDuplicate(qualified_name);
								break;
							}
						}
						if(!isDuplicate) {
							//System.out.println("method tokens being added");
							/*for(Token T : method_tokens) {
								System.out.println(T.getText());
							}*/
							methods.add(new TokenizedMethod(qualified_name, method_tokens));
						}
					}
				}
			}		
		}
		//All directories processed and all methods added
		for(TokenizedMethod method : methods) {
			if(method.hasDuplicate()) {//TODO change to file output
				System.out.println("Duplicate found in the following files:");
				System.out.println(method.toString());
			}
		}
	}
	
}
