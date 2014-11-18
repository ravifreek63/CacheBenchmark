import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class Benchmark {
	private static Cache _cache;
	private static int _numberThreads;
	private static int _numberSamplesPerThread;
	
	public Cache getGraph(){
		return _cache;
	}
	
	public void createCache(int keys){
		_cache = new Cache(keys);
	}
	
	public static void main(String[] args){
		Benchmark benchmark = new Benchmark();
		if(args.length<3){
			System.out.println("Insufficient number of arguments");
			System.exit(-1);
		}
		int numberKeys = Integer.parseInt(args[0]);
		benchmark.createCache(numberKeys);
		if(args.length < 2){
			_numberThreads = 8;
		} else {
			try{
				_numberThreads = Integer.parseInt(args[1]);
			}catch (NumberFormatException e){
				System.out.print(e.toString());
			}
		}
		if(args.length < 3){
			_numberSamplesPerThread = 64;
		} else {
			try{
				_numberSamplesPerThread = Integer.parseInt(args[2]);
			}catch (NumberFormatException e){
				System.out.print(e.toString());
			}
		}	
//		Statistics.setNumberThreads(_numberThreads);
		// Starting Worker Threads 
		System.out.println("Starting Threads ..... ");
		long lStartTime = System.nanoTime();
		ExecutorService executor = Executors.newFixedThreadPool(_numberThreads);		
		for(int count = 0; count < _numberThreads; count++){
			Worker worker = new Worker(_cache, _numberSamplesPerThread, count);
			executor.execute(worker);
		}
		executor.shutdown();
		while(!executor.isTerminated());
		long lEndTime = System.nanoTime();
//		long difference = lEndTime - lStartTime;
//		Statistics.setGraphSearchTime(difference);
//		Statistics.printStats();
	}
}
