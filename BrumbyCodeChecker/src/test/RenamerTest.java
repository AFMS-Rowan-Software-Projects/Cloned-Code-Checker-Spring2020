package test;

import java.util.ArrayList;
import org.junit.*;
import brumby.Renamer;
import fileIO.CFilesReader;
import sablecc.node.Token;
import tokenlister.Lister;

public class RenamerTest {

	private Renamer renamer;
	
	@Test
	public void testParseFile()
	{
		CFilesReader cfr = new CFilesReader();
		String testFile = cfr.readFile("src/cpp_data/coins.txt");
		Lister list = new Lister();
		ArrayList<Token> testFileTokens = list.ConvertToList(testFile);
		renamer = new Renamer(testFileTokens);
		renamer.parseFile();
		printList(renamer.getTokens());
	}
	
	public void printList(ArrayList<Token> tokens)
	{
		for(int i=0; i<tokens.size();i++)
		{
			System.out.println(tokens.get(i));
		}
	}
}
