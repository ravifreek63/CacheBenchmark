import java.util.HashMap;
import java.util.Random;

public class Worker implements Runnable {
	private Cache _cache;
	private int _numberSamplesPerThread;
	private int _workerId;
	private int _cacheHit;
	
	private void getKeys(){
		double cacheHitRatio = ((double)_cacheHit)/100;
		int size = (int) ((int)_cache.getSize() * cacheHitRatio);
		System.out.println("Size:" + size);
		HashMap<Integer, String> map = _cache.getMap();
		Random random = new Random();
		int key, sizeS = 0;
		String value;
		for (int count = 0; count < _numberSamplesPerThread; count++){
			for(int workC=0; workC<100; workC++){
				key = random.nextInt(size);
				value = map.get(key);
				sizeS += value.length();
			}
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
	
	public Worker(Cache c, int samples, int workerId, int cacheHit){
		_cache = c; 
		_numberSamplesPerThread = samples;
		_workerId = workerId;
		_cacheHit = cacheHit;
	}
}
