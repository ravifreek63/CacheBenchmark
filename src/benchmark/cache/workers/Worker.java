package benchmark.cache.workers;
import benchmark.cache.monitors.StatsMonitor;
import benchmark.cache.dataStore.Cache;
import java.util.Random;

public class Worker implements Runnable {
	private Cache _cache;
	private int _numberSamplesPerThread;
	private int _workerId;
	private int _cacheHit;
	
	private void getKeys(){
		double cacheHitRatio = ((double)_cacheHit)/100;
		int size = (int) ((int)_cache.getSize() * cacheHitRatio);
		int range = (int)(size / StatsMonitor.numberWorkerThreads);
		int startIndex = _workerId*range;
		Random random = new Random();
		int key1, key2, fanout = Cache.getBranchFactor();
		for (int count = 0; count < _numberSamplesPerThread; count++){
			for(int workC=0; workC<100; workC++){
				key1 = random.nextInt(range)+startIndex;
				key2 = random.nextInt(fanout);
				_cache.getKey(key1, key2);
				_cache.putKey(key1, key2, 0);
				StatsMonitor.incrementQueries(2, _workerId);
				if(StatsMonitor._shouldStop){
					return;
				}
			}
		}
	}
	
	@Override
	public void run() {
		getKeys();
	}
	
	public Worker(Cache c, int samples, int workerId, int cacheHit){
		_cache = c; 
		_numberSamplesPerThread = samples;
		_workerId = workerId;
		_cacheHit = cacheHit;
	}
}
