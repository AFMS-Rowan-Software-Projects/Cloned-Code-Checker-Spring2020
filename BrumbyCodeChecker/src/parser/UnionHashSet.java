package parser;

import java.util.ArrayList;
import java.util.Map;

public class UnionHashSet{
	
	private Map<String, Double> map1;
	private Map<String, Double> map2;

	public UnionHashSet(Map<String, Double> map1, Map<String, Double> map2) {
		this.map1 = map1;
		this.map2 = map2;
	}
	
	/**
	 * Return the union set of both lists
	 */
	public ArrayList<String> createUnionSet() {
		ArrayList<String> union = new ArrayList<>();
		
		for(String s: map1.keySet()) {
			if(!union.contains(s)) {
				union.add(s);
			}
		}
		for(String s: map2.keySet()) {
			if(!union.contains(s)) {
				union.add(s);
			}
		}
		return union;
	}
}
