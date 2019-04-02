package brumby;

import java.util.ArrayList;
import java.util.HashMap;

import node.TIdentifier;
import node.Token;

public class Renamer {

	private HashMap<Token, String> dictionary;
	private ArrayList<Token> tokens;
	
	public Renamer(ArrayList<Token> tokens)
	{
		dictionary = new HashMap<>();
		this.tokens = tokens;
	}
	public void parseFile()
	{
		int counter = 0;
		for(Token token : tokens)
		{
			if (token instanceof TIdentifier)
			{
				if (!dictionary.containsKey(token))
				{
					dictionary.put(token, "id" + counter);
					counter++;
				}
				token.setText(dictionary.get(token));
			}	
		}
	}
	
	public ArrayList<Token> getTokens()
	{
		return tokens;
	}
	
	public HashMap<Token,String> getDictionary()
	{
		return dictionary;
	}
}
