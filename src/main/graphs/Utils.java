package main.graphs;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Utils {
	
	/**
	 * Help function 
	 * 
	 * @param l: a list with vertices
	 * @return the reversed list of the input list
	 * 
	 * Reverse input list of vertices.
	 * If list is empty, return list is also empty.
	 * If list has one element l and returnList are the same
	 * If list is null then NullPointerException
	 */
	public static List<GKAVertex> reverse(List<GKAVertex> list){
		if (list == null){throw new NullPointerException("the input is null");}
		
		List<GKAVertex> reverseList = new ArrayList<>();
		ListIterator<GKAVertex> li = list.listIterator(list.size());
		
		while(li.hasPrevious())
			reverseList.add(li.previous());
		
//			for (int i=l.size()-1 ; i>=0 ; i--){
//				GKAVertex lastElem = l.get(i);
//				reverseList.add(lastElem);
//			}
			
		return reverseList;
	}

}
