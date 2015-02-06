package benchmark.cache.workers;
import benchmark.cache.monitors.StatsMonitor;
import benchmark.cache.dataStore.Cache;

import java.util.Random;

public class Worker implements Runnable {
	private Cache _cache;
	private int _numberSamplesPerThread;
	private int _workerId;
	private int _cacheHit;
	private int _getsPerPut;
	
	private void getKeys(){
		int count=0;
		double cacheHitRatio = ((double)_cacheHit)/100;
		int size = (int) ((int)_cache.getSize() * cacheHitRatio);
		int range = (int)(size / StatsMonitor.numberWorkerThreads);
		int startIndex = _workerId*range;
		Random random = new Random();
		int key1, key2, fanout = Cache.getBranchFactor();
		int setOfKeys=1000;
		while(true){
				count++;				
				//long lStartTime = System.nanoTime();
				//for(int counter=0; counter<setOfKeys; counter++){
					key1 = random.nextInt(range)+startIndex;
					key2 = random.nextInt(fanout);
					_cache.getKey(key1, key2);
				//}
				//long lEndTime = System.nanoTime();
				//long difference = (long) (lEndTime - lStartTime);
				//StatsMonitor.addQuery(difference);
				if((count%_getsPerPut)==0){
					key1 = random.nextInt(range)+startIndex;
					key2 = random.nextInt(fanout);
					_cache.putKey(key1, key2, 0);
					count=0;
					StatsMonitor.incrementQueries(1, _workerId);
				}
				StatsMonitor.incrementQueries(1, _workerId);
				if(StatsMonitor._shouldStop){
					return;
				}
		}
	}
	
	@Override
	public void run() {
		if(_workerId==0){
			StatsMonitor s = new StatsMonitor();
			s.run();
		} else {
			getKeys();
		}
	}
	
	public Worker(Cache c, int samples, int workerId, int cacheHit, int getsPerPut){
			_cache = c; 
			_numberSamplesPerThread = samples;
			_workerId = workerId;
			_cacheHit = cacheHit;
			_getsPerPut = getsPerPut;
	}
}
