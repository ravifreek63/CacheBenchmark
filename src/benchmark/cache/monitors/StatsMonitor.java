package benchmark.cache.monitors;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class StatsMonitor implements Runnable {
	public static long[] totalQueries;
	public static int numberWorkerThreads;
	private long _startTime;
	public static boolean _shouldStop;
	private static int _totalTime;
	private static PrintWriter writer;
	private static String _collectorType;
	private static int _nKeys;
	private static int _maxQueries;
	
	// The time that is added here is in microseconds
	public static void addQuery(long time){
		writer.println(time);
	}
	
	public static void incrementQueries(int value, int id){
		totalQueries[id] += value;
	}
	
	public static long findTotalQueries(){
		long queries = 0;
		for(int count=0; count < numberWorkerThreads; count++){
			queries += totalQueries[count];
		}
		return queries;
	}
	
	private static void closeLogFile(){
		writer.close();
	}
	
	private static void openLogFile() throws FileNotFoundException, UnsupportedEncodingException{
		String fileName = "/home/tandon/latencyDataNew2/latency_" + _collectorType + "_" + String.valueOf(_nKeys) + ".txt";
		writer = new PrintWriter(fileName, "UTF-8");
	}
	
	public static void init(int nThreads, int totalTime, String cType, int nKeys, int maxQueries) throws FileNotFoundException, UnsupportedEncodingException{
		numberWorkerThreads = nThreads;
		totalQueries = new long[nThreads];
		_shouldStop = false;
		_totalTime = totalTime;
		_collectorType = cType; 
		_nKeys = nKeys;
		_maxQueries = maxQueries;
		//openLogFile();
	}

	@Override
	public void run() {
		System.out.println("Started the StatsMonitor thread.");
		_startTime = System.nanoTime();
    	double timeDifference;
    	double rate;
    	long totalQueries;
        try {
			while(true){
				long currentTime = System.nanoTime();
				timeDifference = (((double)(currentTime - _startTime))/(Math.pow(10, 9)));
				Thread.sleep(1000);
				totalQueries = findTotalQueries();			
				rate = (double)totalQueries/timeDifference/1000; 			
				if(timeDifference>_totalTime){// || (_maxQueries<=totalQueries && _maxQueries>0)){					
					System.out.println("Exiting in the stats monitor thread");
					_shouldStop = true;
					System.out.println("Queries Done:" + findTotalQueries() + ", rate:" + rate + " k, time:" + timeDifference);
					//closeLogFile();
					break;
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	
}
