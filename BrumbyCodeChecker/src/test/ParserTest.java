package test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import parser.Parser;
import tokenlister.Lister;
import fileIO.CFilesReader;
import sablecc.node.*;
import org.junit.Test;

public class ParserTest {

	
	private int[] indicies = new int[2];
	ArrayList<Token> List;
	
	@Test
	public void testFindMethod() {
		//first want to input example
		//then run some ifs to check if the returned int[] is correct
		CFilesReader cfr = new CFilesReader();
		String tester = cfr.readFile(".//src//cpp_data//identicalTest//Coins.cpp");
		List = Parser.sanitize(Lister.ConvertToList(tester));
		if( !(Lister.ConvertToList(tester).size() > List.size()))
			System.out.println("Sanitize not Working properly");
		indicies[0] = 0;
		while(indicies[0] != -1) {
			indicies = Parser.findMethod(List, indicies[1]);
			System.out.println("indicies " + indicies[1]);
			if(indicies[0] == -1) {
				System.out.println(-1 + ", EOF");
				break;
			}else {
				System.out.println("between indicies " + indicies[0] + " and " + indicies[1] + " is " + Parser.subarray(List, indicies[0], indicies[1]));
			}
		}
	}
}
