import java.util.HashMap;

public class Cache {
	int _nKeys;
	HashMap<Integer, String> _map = new HashMap<Integer,String>();
	String _value = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	
	public int getSize() { return _nKeys; }
	public HashMap<Integer, String> getMap() { return _map; }
	
	public Cache(int nKeys){
		_nKeys = nKeys;
		for(int count=0; count < nKeys; count++){
			_map.put(count, _value);
		}
	}
}
