package driver;
import fileIO.CFilesReader;
import java.io.File;
import java.util.ArrayList;
import analysis.*;
import lexer.*;
import node.*;
import parser.*;

public class Application {
	public static void main(String[] args) {
		ArrayList<String> directory_contents;
		ArrayList<TokenizedMethod> methods;
		String current_file;
		for(int i = 0; i < args.length; i++) {
			directory_contents = directoryplaceholder(args[i]);//ANDREW AND KEVIN- your code replaces this line
			for(String file : directory_contents) {
				current_file = CFilesReader.readFile(file);//Take filepath and load it into a String
				//TODO find methods within file and save them as TokenizedMethods
			}
		}
	}
	
	public static ArrayList<String> directoryplaceholder(String s){
		return null;
	}
}
