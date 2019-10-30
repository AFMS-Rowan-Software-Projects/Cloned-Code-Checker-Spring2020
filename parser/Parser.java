package parser;

import sablecc.node.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Parser {

	// TODO For % similarity. Not implemented yet
	public static double similarity(ArrayList<Token> list1, ArrayList<Token> list2) {
		// Assume sanitize has been called on the input already, getting rid of
		// Whitespace and Comments
		if (list1.size() != list2.size()) {
			return 0.1;
		}
		double len = list1.size();
		double matches = 0;
		for (int i = 0; i < len; i++) {
			if (list1.get(i).getText().equals(list2.get(i).getText())) {
				matches++;
			}
		}
		return matches / len;
	}

	public static double closeEnough(ArrayList<Token> list1, ArrayList<Token> list2) {
		// Assume sanitize has been called on the input already, getting rid of
		// Whitespace and Comments

		Set<Token> unionList = new HashSet<>(); // Stores the union of both lists
		final Map<String,AtomicLong> map1 = new ConcurrentHashMap<>();
		final Map<String,AtomicLong> map2 = new ConcurrentHashMap<>();
		
		
		//Map tokens to their frequency 
		for(int i = 0; i < list1.size(); i++) { //Map first list
			map1.computeIfAbsent(list1.get(i).getText(), k->new AtomicLong(0)).incrementAndGet();
		}
		
		for(int i = 0; i < list2.size(); i++) { //Map second list
			map2.computeIfAbsent(list2.get(i).getText(), k->new AtomicLong(0)).incrementAndGet();
		}
		
		//Get the token union of both lists
		unionList.addAll(list1);
		unionList.addAll(list2);
		
		//Calculate union
		double cosine, v1, v2, length_v1 = 0, length_v2 = 0, dotProduct = 0;
		
		for(Token s: unionList) {
			//Set current vector pairs' frequencies to 0)
			v1 = 0;
			v2 = 0;
			
			//If the key is in the list, assign frequency stored 
			if(map1.containsKey(s.getText())) {
				v1 = map1.get(s.getText()).doubleValue(); 
				length_v1 += v1*v1;  
			}
			if(map2.containsKey(s.getText())) {
				v2 = map2.get(s.getText()).doubleValue();
				length_v2 += v2*v2; 
			}
			dotProduct += v1*v2;
		} 
		
		//Compute the cosine similarity
		cosine = dotProduct/( Math.sqrt(length_v1)*(Math.sqrt(length_v2)));
		
		//Return percentage
		return (cosine*100);
	}

	/**
	 * Looks in the Token ArrayList. Looks for features that signifies the start of
	 * a method. I.E public, private (or none), then a return type, name, and parameters (if
	 * any) (Finds the start and end of a method)
	 * TODO some methods are being omitted 
	 * 
	 * @param file
	 * @param start
	 * @return indices
	 */
	public static int[] findMethod(ArrayList<Token> file, int start) {
		// Returns start and end index (inclusively) of a method within a file
		int[] indices = new int[2];
		int lbracecounter;
		// Smallest possible method is 6 tokens long
		for (indices[0] = start; indices[0] < (file.size() - 6); indices[0]++) {
			// Check if methods starts with data type (primitive or non-primitive)
			// must be followed by identifier (name) and parenthesis
			indices[1] = indices[0] + 3;
			// Match starting pattern of method - [DataType Identifier Parenthesis]
			if (((file.get(indices[0]) instanceof TDataType) || (file.get(indices[0]) instanceof TIdentifier))
					&& (file.get(indices[0] + 1) instanceof TIdentifier)
					&& (file.get(indices[0] + 2) instanceof TLParen)) {
				// Capture everything between ( and ) of a method
				while (!(file.get(indices[1]) instanceof TRParen) && (indices[1] < (file.size() - 3))) {
					indices[1]++;
				}
				indices[1]++;
				// EOF reached (End of File)
				if (indices[1] > (file.size() - 2)) {
					// Record that method was not found
					indices[0] = file.size();
					break;
				}
				if (!(file.get(indices[1]) instanceof TLBrace)) { // { should be immediately after )
					continue;
				} 
				else {
					// Match up braces with one another and only terminate when finding the method's '}'
					lbracecounter = 1;
					for (indices[1]++; (lbracecounter != 0) && (indices[1] < file.size()); indices[1]++) {
						
						if (file.get(indices[1]) instanceof TLBrace) {
							lbracecounter++;
						}
						else if (file.get(indices[1]) instanceof TRBrace) {
							lbracecounter--;
						}
					}
					if (lbracecounter == 0) {
						indices[1]--;
						break;// Successfully found a method
					}
				}
			}
		} // end of for(indices[0]...)

		// Sentinel value indicates no method was found between starting position and
		// EOF
		if (indices[0] >= (file.size() - 6)) {
			indices[0] = -1;
		}
		return indices; // returns the line number where the method starts
	}

	/**
	 * Cleans up tokens/gets rid of unused tokens
	 * @param t_large - An ArrayList of Tokens
	 * @return tlist  - A cleaned up ArrayList of unused Tokens 
	 */
	public static ArrayList<Token> sanitize(ArrayList<Token> t_large) {
		// NOTE: this method may become unnecessary if Ignored Tokens aren't produced by
		// the Scanner
		ArrayList<Token> tlist = (ArrayList<Token>) t_large.clone();
		for (int i = 0; i < tlist.size(); i++) {
			if ((tlist.get(i) instanceof InvalidToken) || (tlist.get(i) instanceof TBlank)
					|| (tlist.get(i) instanceof TDocumentationComment) || (tlist.get(i) instanceof TEndOfLineComment)
					|| (tlist.get(i) instanceof TTraditionalComment) || (tlist.get(i) instanceof TUnknown)) {
				tlist.remove(i);
				i--;// Reposition the iterator correctly
			}
		}
		return tlist;
	}

	/** Takes in an array of tokens with a start and end point and returns a smaller
	 * array between those two points
	 * @param rec   - An ArrayList of tokens
	 * @param start - starting point 
	 * @param end   - ending point
	 */ 
	public static ArrayList<Token> subarray(ArrayList<Token> rec, int start, int end) {
		int s = start;
		int e = end;
		ArrayList<Token> bleh = new ArrayList<Token>();

		for (int i = s; i <= e; i++) {
			bleh.add(rec.get(i));
		}

		return bleh;
	}

}
