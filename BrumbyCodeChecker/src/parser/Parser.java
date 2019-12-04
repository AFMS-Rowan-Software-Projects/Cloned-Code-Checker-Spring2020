package parser;

import sablecc.node.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Parser {

	/**
	 * Get the average between cosine similarity, longest common subsequence and
	 * naive algorithms
	 */
	public static double similarity(ArrayList<Token> list1, ArrayList<Token> list2) {
		// Assume sanitize has been called on the input already, getting rid of
		// Whitespace and Comments

		// Adjust Longest Common Subsequence ratio
		double smallerSize = (list1.size() < list2.size()) ? list1.size() : list2.size();
		Map<String, Integer> lookup = new HashMap<>();

		//System.out.println("\nCosine " + cosineSimilarity(list1, list2));
		//System.out.println("LCS " + LCSLength(list1, list2, list1.size(),
		//list2.size(), lookup) / smallerSize * 100);
		//System.out.println("Naive: " + naiveSimilarity(list1, list2));
		//System.out.println("Weighted Cosine: " + ((cosineSimilarity(list1, list2)*(naiveSimilarity(list1, list2)))));
		//System.out.println("Weighted LCS: " + (((LCSLength(list1, list2, list1.size(), list2.size(), lookup) / smallerSize) * 100) * (naiveSimilarity(list1, list2))));


		// Average of all approaches
		return (/*(naiveSimilarity(list1, list2) * .2)*/ +  ((cosineSimilarity(list1, list2)*(naiveSimilarity(list1, list2)))  * .5) 
				+ (((LCSLength(list1, list2, list1.size(), list2.size(), lookup) / smallerSize) * 100) * (naiveSimilarity(list1, list2))) * .5);
	}

	/*
	 * Naive comparison for dissimilar sized methods Maintains code ordered structes
	 * And computes a 1 to 1 mapping
	 */
	public static double naiveSimilarity(ArrayList<Token> list1, ArrayList<Token> list2) {
		// Assume sanitize has been called on the input already, getting rid of
		// Whitespace and Comments
		ArrayList<Token> largerList;
		ArrayList<Token> smallerList;
		String smallestFirstToken = null;
		int startPoint = 0;
		int matches = 0;
		int smallStart = 0;
		int largeStart = 0;

		// save the smaller and larger list
		if (list1.size() < list2.size()) {
			largerList = list2;
			smallerList = list1;
		} else {
			largerList = list1;
			smallerList = list2;
		}
		

		// Naive 1 to 1 Similarity, Works on small sample sets but has flaws on
		// large input sizes, decided not to implement, but can be used if accuracy 
		// needs to be more specific
		
		/*
		// find the first non header token in the smaller list
		// first token after first "{"
		for (int i = 0; i < 30; i++) {
			if (smallerList.get(i).getText().equals("{")) {
				smallestFirstToken = smallerList.get(i + 1).getText();
				smallStart = i + 1;
				break;
			}
		}
		System.out.println("small method start: " + smallerList.get(smallStart).getText());

		// exclude everything before { and NOT THE  }
		int total = (smallerList.size()) - (smallStart);
		System.out.println("total small list " + smallerList.size());
		System.out.println("total size of small method: " + total);

		for (int i = 0; i < 30; i++) {
			if (largerList.get(i).getText().equals("{")) {
				largeStart = i + 1;
				break;
			}
		}
		System.out.println();
		System.out.println("Large Method no header Start: " + largeStart);

		// find where to start comparing token to token in the big list
		for (int i = largeStart; i < largerList.size(); i++) {
			if (largerList.get(i).getText().equals(smallestFirstToken)
					&& largerList.get(i + 3).getText().equals(smallerList.get(smallStart + 3).getText())) {
				startPoint = i;
				break;
			}
		}
		System.out.println("\nLarge Method first hit: " + startPoint + " : " + largerList.get(startPoint).getText());

		// comparison between the larger list and the smaller list
		// starting at the first token of the smaller list within the larger one
		for (int i = startPoint; i < (startPoint + total); i++) {
			if (largerList.get(i).getText().equals(smallerList.get(smallStart).getText())
					|| (largerList.get(i).getText().startsWith("id")
							&& smallerList.get(smallStart).getText().startsWith("id"))) {
				matches++;
				// System.out.println(largerList.get(i).getText());
			}
			smallStart++;
		}

		double percent = (matches * 100) / total;
		*/
		
		double weight = smallerList.size() *100 / largerList.size();
		weight = weight / 100;
		//System.out.println(weight);
		if (weight > .5) {
			weight = 1;
		}
		// weighted percent to method size differences 
		return weight;
	}
	
	

	/**
	 * Compute the cosine similarity of two methods
	 */
	public static double cosineSimilarity(ArrayList<Token> list1, ArrayList<Token> list2) {
		// Assume sanitize has been called on the input already, getting rid of
		// Whitespace and Comments

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
		 * In one list -> 1 + log(2/1) = 1.6931472 In both lists -> 1 + log(2/2) = 1
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
	 */
	public static int LCSLength(ArrayList<Token> X, ArrayList<Token> Y, int m, int n, Map<String, Integer> lookup) {
		// return if we have reached the end of either string
		if (m == 0 || n == 0)
			return 0;

		// construct a unique map key from dynamic elements of the input
		String key = m + "|" + n;

		// if sub-problem is seen for the first time, solve it and
		// store its result in a map
		if (!lookup.containsKey(key)) {
			// if last character of X and Y matches
			if (X.get(m - 1).getText().equals(Y.get(n - 1).getText())) {
				lookup.put(key, LCSLength(X, Y, m - 1, n - 1, lookup) + 1);
			} else {
				// else if last character of X and Y don't match
				lookup.put(key, Integer.max(LCSLength(X, Y, m, n - 1, lookup), LCSLength(X, Y, m - 1, n, lookup)));
			}
		}
		// return the subproblem solution from the map
		return lookup.get(key);
	}

	/**
	 * Looks in the Token ArrayList. Looks for features that signifies the start of
	 * a method. I.E public, private (or none), then a return type, name, and
	 * parameters (if any) (Finds the start and end of a method) TODO some methods
	 * are being omitted
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
	 * @param t_large - An ArrayList of Tokens
	 * @return tlist - A cleaned up ArrayList of unused Tokens
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