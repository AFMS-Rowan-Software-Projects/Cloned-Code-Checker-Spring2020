package test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.junit.*;
import brumby.Renamer;
import fileIO.CFilesReader;
import sablecc.node.Token;
import tokenlister.Lister;

public class RenamerTest {

	private Renamer renamer;
	
	// Reads user input
	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	
	@Test
	public void testParseFile() throws NumberFormatException, IOException
	{	   
		
		/*
		 * @Var testChoice
		 * Use this variable to decide which file to run
		 */
		String testFile = "";
		int quitTest = 1;
		CFilesReader cfr = new CFilesReader();
		Lister list = new Lister();
		
		
		while(quitTest != 0) {
			System.out.println("Enter file to read, press 0 to exit"); 
		try {
		testFile = stdin.readLine();
		}
		catch(Exception e) {
			System.out.println("Not a vaild string");
		}
		
		if(testFile.equals("0")) {
			quitTest = 0;
		}
		
		/*
		 * User inputs file to be tested in this form
		 * .\\src\\cpp_data\\identicalTest\\Coins.cpp
		 */
			String firstTestFile = CFilesReader.readFile(testFile);
			ArrayList<Token> firstTestFileTokens = Lister.ConvertToList(firstTestFile);
			renamer = new Renamer(firstTestFileTokens);
			renamer.parseFile();
			printList(renamer.getTokens());

		}
		
		//assertTrue(3 > 4);
		//assertTrue(4 == 5);
	}
	
	public void printList(ArrayList<Token> tokens)
	{
		for(int i=0; i<tokens.size();i++)
		{
			System.out.println(tokens.get(i));
		}
	}
}
