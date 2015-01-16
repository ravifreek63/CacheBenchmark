package benchmark.cache.elementDefinitions;

public class ValueNode {
	private int _id;
	private char[] array;
	public ValueNode(int id){
		_id = id;
		array = new char[100];
	}
	public int getId(){
		return _id;
	}
}
