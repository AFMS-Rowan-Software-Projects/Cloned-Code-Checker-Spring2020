package test;

import org.junit.*;
import tokenlister.Lister;
import sablecc.node.*;
import java.util.ArrayList;

public class ListerTest {
	
	private ArrayList<Token> finalList;
	private String tester = "? Thi*s be @ might-y fin3 te~st!";
	
	@Test
	public void testLister() {
		finalList = Lister.ConvertToList(tester);
		for(int i = 0; i < finalList.size(); i++) {
			if(!tester.contains(finalList.get(i).getText())) {
				System.out.println("not the same at " + i + " = " + finalList.get(i).getText());
			}
		}
		printList(finalList);
		System.out.println("\nwe good");
	}
	
	public void printList(ArrayList<Token> tokenlist) {
		for(int i = 0; i < tokenlist.size(); i++)
			System.out.print("\n" + tokenlist.get(i).getText());
	}
	
	
}
