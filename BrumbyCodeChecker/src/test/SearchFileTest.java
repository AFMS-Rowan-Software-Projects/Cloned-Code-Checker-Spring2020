package test;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.junit.Test;

import brumby.Renamer;
import fileIO.CFilesReader;
import fileIO.SearchFile;
import sablecc.node.Token;
import tokenlister.Lister;

public class SearchFileTest {
	
	// Reads user input
	static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
	
	SearchFile sf = new SearchFile();
	String file;
	int exit = 1;
	
	
	@Test
	public void searchForFileTest() throws IOException {
		while(exit != 0) {
			System.out.println("Enter Directory, press 0 to exit"); 
			try {
				file = stdin.readLine();
			}
			catch(Exception e) {
				System.out.println("Not a vaild string, try again");
			}
			if(file.equals("0")) {
				exit = 0;
			}
			else {
				printList((sf.searchForFile(file)));
			}
		}
	}
	
	@Test
	public void searchDirectoryTest() throws IOException {
		
	}
	
	public void printList(ArrayList<String> files)
	{
		for(int i=0; i<files.size();i++)
		{
			System.out.println(files.get(i));
		}
	}

}
