package driver;
import fileIO.*;
import java.io.File;
import java.util.ArrayList;
import analysis.*;
import lexer.*;
import node.*;
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
		for(int i = 0; i < args.length; i++) {
			//directory_contents = SearchFile.searchForFile(args[i]);//ANDREW AND KEVIN- your code replaces this line
			directory_contents = directoryplaceholder(args[i]);
			for(String file : directory_contents) {
				current_file = CFilesReader.readFile(file);//Take filepath and load it into a String
				file_tokens = Lister.ConvertToList(current_file);
				method_indices[0] = 0;
				while(method_indices[0] != -1) {
					method_indices = Parser.findMethod(file_tokens, method_indices[0]);
					if(method_indices[0] == -1) {
						break;
					}else {
						//Remove method chunk and rename identifiers accordingly
						rename = new Renamer(Parser.subarray(file_tokens, method_indices[0], method_indices[1]));
						method_tokens = rename.getTokens();
						isDuplicate = false;
						for(TokenizedMethod method : methods) {
							if(Parser.similarity(method.getTokens(), method_tokens) == 1) {
								//Duplicate method found
								isDuplicate = true;
								//Record location of this duplicate, but throw away its tokenized representation
								method.addDuplicate(file + ":" + method_tokens.get(1).getText());
								break;
							}
						}
						if(!isDuplicate) {
							methods.add(new TokenizedMethod(file+":"+method_tokens.get(1).getText(), method_tokens));
						}
					}
				}
			}		
		}
		//All directories processed and all methods added
		for(TokenizedMethod method : methods) {
			if(method.hasDuplicate()) {
				System.out.println("Duplicate found in the following files:");
				System.out.println(method.toString());
			}
		}
	}
	
	public static ArrayList<String> directoryplaceholder(String s){
		return null;
	}
}
