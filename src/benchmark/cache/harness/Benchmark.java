package benchmark.cache.harness;
import benchmark.cache.dataStore.Cache;
import benchmark.cache.monitors.StatsMonitor;
import benchmark.cache.workers.Worker;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Benchmark {
	public static Cache _cache;
	public static int _numberThreads;
	public static int _numberSamplesPerThread;
	
	public Cache getCache(){
		return _cache;
	}
	
	public void createCache(int keys, int fanout){
		_cache = new Cache(keys, fanout);
	}
	
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException{
		Benchmark benchmark = new Benchmark();		
		int numberKeys = Integer.parseInt(args[0]);
		int fanout = Integer.parseInt(args[1]);
		_numberThreads = Integer.parseInt(args[2]);
		_numberSamplesPerThread = Integer.parseInt(args[3]);
		int cacheHit = Integer.parseInt(args[4]);
		int totalTime = Integer.parseInt(args[5]);
		int getsPerPut = Integer.parseInt(args[6]);
		String collectorType = args[7];
		benchmark.createCache(numberKeys, fanout);
		StatsMonitor.init(_numberThreads, totalTime, collectorType, numberKeys/(1000000));		
		System.out.println("Starting Threads ..... ");
		System.gc();
		long lStartTime = System.nanoTime();
		ExecutorService executor = Executors.newFixedThreadPool(_numberThreads);		
		for(int count = 0; count < _numberThreads; count++){
			Worker worker = new Worker(_cache, _numberSamplesPerThread, count, cacheHit, getsPerPut);
			executor.execute(worker);
		}
		executor.shutdown();
		while(!executor.isTerminated());
		long lEndTime = System.nanoTime();
		long difference = (long) ((lEndTime - lStartTime)/Math.pow(10, 6));
		long totalQueries = (long)(StatsMonitor.findTotalQueries());
		System.out.println("Total Number of queries/second:" + (totalQueries)/(difference) + " k."); 
	}
}
