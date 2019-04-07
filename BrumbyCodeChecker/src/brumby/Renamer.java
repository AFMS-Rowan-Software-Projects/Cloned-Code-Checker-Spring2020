package brumby;

import java.util.ArrayList;
import java.util.HashMap;

import sablecc.node.*;

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
				if (!dictionary.containsKey(token.getText()))
				{
					dictionary.put(token, "id" + counter);
					counter++;
				}
				token.setText(dictionary.get(token.getText()));
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
