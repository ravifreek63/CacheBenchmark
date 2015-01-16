package benchmark.cache.monitors;

import benchmark.cache.harness.Benchmark;

public class StatsMonitor implements Runnable {
	public static int[] totalQueries;
	public static int numberWorkerThreads;
	private long _startTime;
	public static boolean _shouldStop;
	private static int _totalTime;
	
	public static void incrementQueries(int value, int id){
		totalQueries[id] += value;
	}
	
	public static int findTotalQueries(){
		int queries = 0;
		for(int count=0; count < numberWorkerThreads; count++){
			queries += totalQueries[count];
		}
		return queries;
	}
	
	public static void init(int nThreads, int totalTime){
		numberWorkerThreads = nThreads;
		totalQueries = new int[nThreads];
		_shouldStop = false;
		_totalTime = totalTime;
	}

	@Override
	public void run() {
		System.out.println("Started the StatsMonitor thread.");
		_startTime = System.nanoTime();
    	double timeDifference;
    	double rate;
    	int totalQueries, maxQueries = (Benchmark._numberThreads-1) * (Benchmark._numberSamplesPerThread) * 200;
        try {
			while(true){
				long currentTime = System.nanoTime();
				timeDifference = (((double)(currentTime - _startTime))/(Math.pow(10, 9)));
				Thread.sleep(1000);
				totalQueries = findTotalQueries();
				rate = (double)totalQueries/timeDifference; 
				System.out.println("Queries Done:" + findTotalQueries() + ", rate:" + rate + " time:" + timeDifference);
				if(timeDifference>_totalTime){
					System.out.println("Exiting in the stats monitor thread");
					_shouldStop = true;
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	
}
