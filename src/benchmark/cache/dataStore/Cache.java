package benchmark.cache.dataStore;
import benchmark.cache.elementDefinitions.Node;
import benchmark.cache.elementDefinitions.ValueNode;
import java.util.ArrayList;

public class Cache {
	int _nKeys;
	private static int _branchFactor;	
	private Node[] _map;			
	public static int getBranchFactor() { return _branchFactor; }
	public int getSize() { return _nKeys; }
	public Node[] getCache() { return _map; }
	
	public Cache(int nKeys, int fanout){
		_nKeys = nKeys;
		_branchFactor = fanout;
		_map = new Node[nKeys];
		for(int count=0; count < nKeys; count++){
			_map[count] = new Node(_branchFactor);
			_map[count].createRandomValueNodes();
		}
	}
	
	public int getKey(int key1, int key2){
		Node n = _map[key1];
		ArrayList<ValueNode> v = n.getEdgeList();
		return v.get(key2).getId();
	}
	
	public void putKey(int key1, int key2, int key3){
		Node n = _map[key1];
		ArrayList<ValueNode> v = n.getEdgeList();
		v.remove(key2);
		v.add(new ValueNode(0));
	}
}
