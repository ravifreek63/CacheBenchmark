package benchmark.cache.elementDefinitions;
import benchmark.cache.dataStore.Cache;

import java.util.ArrayList;

public class Node {	
	private char[] array;
	private ArrayList<ValueNode> _edgeList;
	public ArrayList<ValueNode> getEdgeList() { return _edgeList; }
	public Node(int branchFactor){		
		_edgeList = new ArrayList<ValueNode>(branchFactor);
		array = new char[100];
	}
	public void createRandomValueNodes(){
		int branchFactor = Cache.getBranchFactor();			
		for(int count = 0; count < branchFactor; count++){
			_edgeList.add(new ValueNode(0));
		}
	}
}
