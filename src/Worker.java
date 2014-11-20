import java.util.HashMap;
import java.util.Random;

public class Worker implements Runnable {
	private Cache _cache;
	private int _numberSamplesPerThread;
	private int _workerId;
	
	private void getKeys(){
		int size = (int) ((int)_cache.getSize() * 0.01);
		System.out.println("Size:" + size);
		HashMap<Integer, String> map = _cache.getMap();
		Random random = new Random();
		int key, sizeS = 0;
		String value;
//		int c;
		for (int count = 0; count < _numberSamplesPerThread; count++){
//			c = 10000000;
			if((count %100000) == 0) 
				System.out.println("Count" + count);
			key = random.nextInt(size);
			value = map.get(key);
			sizeS += value.length();
//			while((c--)>0);
		}
		System.out.println("Size  " + sizeS);
	}
	
//	private void generateSamples(){
//		System.out.println("Generating Samples For Thread -" + _workerId);
//		int numberNodes = _graph.getNumNodes();
//		
//		int sample = -1;
//		_samples = new int[_numberSamplesPerThread];
//		for (int count = 0; count < _numberSamplesPerThread; count++){
////			System.out.println("Count:" + count);
//			do{
//				sample = random.nextInt(numberNodes);
//			} while(_graph.getNumberEdges(sample) == 0 && sample != _graph.getRoot().getNodeId());
//			_samples[count] = sample;
//		}
//	}
	
	@Override
	public void run() {
//		System.out.println("Running Thread -" + Integer.toString(_workerId));
//		if(_workerId==0){		
//			System.out.println("Triggering system.gc().");
//			System.gc();
//			System.out.println("system.gc() triggered.");
//		} 
//			generateSamples();
//			searchGraph();
		getKeys();
	}
	
	public Worker(Cache c, int samples, int workerId){
		_cache = c; 
		_numberSamplesPerThread = samples;
		_workerId = workerId;
	}
}
