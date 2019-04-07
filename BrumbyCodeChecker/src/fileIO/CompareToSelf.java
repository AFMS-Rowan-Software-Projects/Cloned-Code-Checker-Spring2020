package fileIO;

import java.util.ArrayList;
import fileIO.CFilesReader;
import sablecc.node.Token;
import parser.Parser;
import tokenlister.Lister;

public class CompareToSelf {
	
	static ArrayList<Token> file1Tokens;
	static ArrayList<Token> file2Tokens;

	public static double compareToSelf(String file1, String file2)
	{
		String[] files = {file1, file2};
		CFilesReader cfr = new CFilesReader();
		cfr.readFiles(files);
		
		Lister lister = new Lister();
		file1Tokens = lister.ConvertToList(cfr.getNewFile());
		file2Tokens = lister.ConvertToList(cfr.getRefFile());
		
		Parser parser = new Parser();
		return parser.similarity(file1Tokens, file2Tokens);
	}
}
