package tokenlister;
import java.util.*;
import node.*;



public class Lister {
	Scanner scnr = new Scanner;
	public List<Token> tokens = new ArrayList<Token>();

	public static void main(String[] args){
		while(scnr.hasNext())
			AddtoList(scnr.next());
	}
	
	public void AddToList(Token sablecctokens) {
		
		tokens.add(sablecctokens);	
	} 
	
	public List<Token> GetList() {	return tokens;	}
	
	
}
