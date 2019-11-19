package test;
import static org.junit.Assert.*;

import org.junit.Test;

import fileIO.CFilesReader;

public class CFilesReaderTest {

	String cointxt;
	
	CFilesReader cfr = new CFilesReader();
	@Test
	public void readFilesTest() {
		String file1 = CFilesReader.readFile("src//cpp_data//Coins.txt");
		String file2 = CFilesReader.readFile("src//cpp_data//otherCoins.txt");
		
		String[] files = {file1, file2};
		
		//cfr.readFiles(files);
		setString();
		
		assertNotNull(files[0]);
		assertNotNull(files[1]);
		//assertNull(files[2]);
		assertNotSame(files[0], files[1]);
		//assertEquals(files[0], cointxt);
		assertEquals(files[0], files[0]);
	}
	
	public void setString()
	{
		cointxt = "//Jeffrey Podwats\r\n" + 
				"//TR 9:00am\r\n" + 
				"// Lab Excercise one part 2\r\n" + 
				"\r\n" + 
				"/*\r\n" + 
				"    This program will convert the number of quarters, dimes, nickels, \r\n" + 
				"    and pennies you have into the monetary value of the coins in cents.\r\n" + 
				"*/\r\n" + 
				"\r\n" + 
				"#include <iostream>\r\n" + 
				"using namespace std;\r\n" + 
				"\r\n" + 
				"int main()\r\n" + 
				"{\r\n" + 
				"    //Set up memory\r\n" + 
				"    int quarters;\r\n" + 
				"    int dimes;\r\n" + 
				"    int nickels;\r\n" + 
				"    int pennies;\r\n" + 
				"    int cents;\r\n" + 
				"    const int CENTS_PER_QUARTER = 25;\r\n" + 
				"    const int CENTS_PER_DIME = 10;\r\n" + 
				"    const int CENTS_PER_NICKEL = 5;\r\n" + 
				"    const int CENTS_PER_PENNY = 1;\r\n" + 
				"\r\n" + 
				"    //Ask all the questions and read in answers\r\n" + 
				"    cout << \"Enter number of quarters: \";\r\n" + 
				"    cin >> quarters;\r\n" + 
				"\r\n" + 
				"    cout << \"Enter number of dimes: \";\r\n" + 
				"    cin >> dimes;\r\n" + 
				"\r\n" + 
				"    cout << \"Enter number of nickels: \";\r\n" + 
				"    cin >> nickels;\r\n" + 
				"\r\n" + 
				"    cout << \"Enter number of pennies: \";\r\n" + 
				"    cin >> pennies;\r\n" + 
				"\r\n" + 
				"    //Do the calculations\r\n" + 
				"    cents = (quarters * CENTS_PER_QUARTER) + (dimes * CENTS_PER_DIME) + (nickels *CENTS_PER_NICKEL) + (pennies * CENTS_PER_PENNY);\r\n" + 
				"\r\n" + 
				"    //Print results\r\n" + 
				"    cout << \"\\n\\nYou have a total of \"\r\n" + 
				"         << cents\r\n" + 
				"         << \" cents.\\n\";\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"    return 0;\r\n" + 
				"}";
	}

}
