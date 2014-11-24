import java.util.Random;


public class GraphGenerator implements Runnable {
	private Graph _graph;
	private int _workerId;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		System.out.println("Generating the graph for worker :: " + _workerId);
		int num_nodes = _graph.getNumNodes();
		int partitionSize = _graph.getEdgeCount() / _graph.getNumberThreads();
		int partitionStart = _workerId * partitionSize;
		int partitionEnd = partitionStart + partitionSize;
		Random random = new Random();
		int from, to;
		for(int index = partitionStart; index < partitionEnd; index++){
			do{
				from = random.nextInt(num_nodes);
				to = random.nextInt(num_nodes);
			} while(from == to); // Avoiding self loops
			_graph.setEdge(from, to, index);
		}
	}

	public GraphGenerator(Graph g, int id){
		_graph = g;
		_workerId = id;
	}
}
