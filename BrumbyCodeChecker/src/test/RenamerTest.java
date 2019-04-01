package test;

import java.util.ArrayList;
import org.junit.*;
import brumby.Renamer;
import node.Token;

public class RenamerTest {

	private Renamer renamer;
	private ArrayList<Token> tokens;
	
	@Test
	public void testParseFile()
	{
		renamer = new Renamer(tokens);
		
	}
}
