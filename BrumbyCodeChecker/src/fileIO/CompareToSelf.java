package fileIO;

import java.util.ArrayList;
import fileIO.CFilesReader;
import node.Token;
import parser.Parser;
import tokenlister.Lister;

public class CompareToSelf {
	
	ArrayList<Token> file1Tokens;
	ArrayList<Token> file2Tokens;

	public void compareToSelf(String file1, String file2)
	{
		String[] files = {file1, file2};
		CFilesReader cfr = new CFilesReader();
		cfr.readFiles(files);
		
		Lister lister = new Lister();
		file1Tokens = lister.AddToList(cfr.getNewFile());
		file2Tokens = lister.AddToList(cfr.getRefFile());
		
		Parser parser = new Parser();
		parser.similarity(file1Tokens, file2Tokens);
	}
}
