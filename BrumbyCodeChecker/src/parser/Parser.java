package parser;

import sablecc.node.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parser {

	/**
	 * Combines the results between cosine similarity and longest common subsequence;
	 * adjust weight according to size dissimilarity.
	 *
	 * @param list1 	first method
	 * @param list2 	second method
	 * @return 			overall similarity percentage
	 */
	public static double similarity(ArrayList<Token> list1, ArrayList<Token> list2) {
		
		return  ((cosineSimilarity(list1, list2) + LCSLength(list1, list2)) / 2) 				
			* naiveWeight(list1, list2);
	}

	/*
	 * Naive weight computation for dissimilar sized methods
	 * @param list1 	first method
	 * @param list2 	second method
	 * @return 			ratio of methods' sizes
	 */
	public static double naiveWeight(ArrayList<Token> list1, ArrayList<Token> list2) {
		
		ArrayList<Token> largerList;
		ArrayList<Token> smallerList;

		// Save the smaller and larger list
		if (list1.size() < list2.size()) {
			largerList = list2;
			smallerList = list1;
		} else {
			largerList = list1;
			smallerList = list2;
		}
		
		double weight = smallerList.size() *100 / largerList.size();
		
		return weight/100;
	}


	/**
	 * Compute the cosine similarity of two methods
	 * @param list1 	first method
	 * @param list2 	second method
	 * @return 			similarity percentage
	 */
	public static double cosineSimilarity(ArrayList<Token> list1, ArrayList<Token> list2) {
		
		final Map<String, Double> map1 = new HashMap<>();
		final Map<String, Double> map2 = new HashMap<>();
		double freq;

		// Map tokens to their frequency
		for (Token t : list1) { // Map first list
			if (map1.containsKey(t.getText())) {
				freq = map1.get(t.getText()) + 1.0;
				map1.replace(t.getText(), freq);
			} else {
				map1.put(t.getText(), 1.0);
			}
		}

		for (Token t : list2) { // Map second list
			if (map2.containsKey(t.getText())) {
				freq = map2.get(t.getText()) + 1.0;
				map2.replace(t.getText(), freq);
			} else {
				map2.put(t.getText(), 1.0);
			}
		}

		// Calculate Term-Frequency (TF = frequency of token / total number of tokens)
		map1.forEach((k, v) -> map1.replace(k, v.doubleValue() / map1.size()));
		map2.forEach((k, v) -> map2.replace(k, v.doubleValue() / map2.size()));

		// Get the token union of both lists
		UnionHashSet union = new UnionHashSet(map1, map2);
		ArrayList<String> unionSet = union.createUnionSet();

		/*
		 * Multiply TF by Inverse Document Frequency (IDF) IDF = 1 + log (total number
		 * of methods / number of methods with specified token)
		 * 
		 * In one list -> 1 + log(2/1) = 1.6931472 
		 * In both lists -> 1 + log(2/2) = 1
		 */
		double cosine, v1, v2, length_v1 = 0, length_v2 = 0, dotProduct = 0;

		for (String s : unionSet) {
			// Set current vector pairs' frequencies to 0)
			v1 = 0;
			v2 = 0;

			if (map1.containsKey(s)) {
				v1 = map1.get(s).doubleValue();
				if (!map2.containsKey(s)) { // Token unique to map1
					v1 *= 1.6931472;
				}
				length_v1 += v1 * v1;
			}
			if (map2.containsKey(s)) {
				v2 = map2.get(s).doubleValue();
				if (!map1.containsKey(s)) { // Token unique to map2
					v2 *= 1.6931472;
				}
				length_v2 += v2 * v2;
			}
			dotProduct += v1 * v2;
		}

		// Compute the cosine similarity
		cosine = dotProduct / (Math.sqrt(length_v1) * (Math.sqrt(length_v2)));

		// Return percentage
		return (cosine * 100);
	}

	/**
	 * Compute the longest common subsequence between two methods
	 * @param list1 	first method
	 * @param list2 	second method
	 * @return 			overall similarity percentage
	 */
	public static double LCSLength(ArrayList<Token> list1, ArrayList<Token> list2)
	{
		int prev;
		int m = list1.size(), n = list2.size();
		int[] curr = new int[n + 1];
	
		//to store lines affected of each method
		ArrayList<Integer>lines1 = new ArrayList<>(); 
		ArrayList<Integer>lines2 = new ArrayList<>();
		
		// fill the lookup table in bottom-up manner
		for (int i = 0; i <= m; i++)
		{
			prev = curr[0];
			for (int j = 0; j <= n; j++)
			{
				int backup = curr[j];
				if (i == 0 || j == 0) {
					curr[j] = 0;
				}
				else
				{
					// if current character of X and Y matches
					if (list1.get(i - 1).getText().equals(list2.get(j - 1).getText())) {
						curr[j] = prev + 1;
						
						// Compute number of affected lines
						if(!lines1.contains(list1.get(i-1).getLine())) {
							lines1.add(list1.get(i-1).getLine());
						}
						if(!lines2.contains(list2.get(j-1).getLine())) {
							lines2.add(list2.get(j-1).getLine());
						}
					}
					// else if current character of X and Y don't match
					else {
						curr[j] = Integer.max(curr[j], curr[j - 1]);
					}
				}
				prev = backup;
			}
		}
		//Pick smallest number of lines affected
		int lines = (lines1.size() < lines2.size()) ? lines1.size() : lines2.size();
		
		//Add affected lines to counter
		LinesAffected.addLinesAffected(lines);
		
		// Adjust Longest Common Subsequence ratio by picking smallest list
		double smallerSize = (list1.size() < list2.size()) ? list1.size() : list2.size();
		
		return (curr[n]/smallerSize)*100;
	}

	/**
	 * Looks in the Token ArrayList. Looks for features that signifies the start of
	 * a method. I.E public, private (or none), then a return type, name, and
	 * parameters (if any) (Finds the start and end of a method) 
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
				} else {
					// Match up braces with one another and only terminate when finding the method's
					// '}'
					lbracecounter = 1;
					for (indices[1]++; (lbracecounter != 0) && (indices[1] < file.size()); indices[1]++) {

						if (file.get(indices[1]) instanceof TLBrace) {
							lbracecounter++;
						} else if (file.get(indices[1]) instanceof TRBrace) {
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
	 * 
	 * @param t_large	An ArrayList of Tokens
	 * @return tlist	A cleaned up ArrayList of unused Tokens
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

	/**
	 * Takes in an array of tokens with a start and end point and returns a smaller
	 * array between those two points
	 * 
	 * @param rec		ArrayList of tokens
	 * @param start		starting point
	 * @param end		ending point
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