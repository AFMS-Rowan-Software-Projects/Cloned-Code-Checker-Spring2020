package tokenlister;
import java.util.*;
import node.*;
public class Lister {
	public List<Token> tokens = new ArrayList<Token>();
	
	public void AddToList(Token sablecctokens) {	tokens.add(sablecctokens);	} 
	
	public List<Token> GetList() {	return tokens;	}
	
	
}
