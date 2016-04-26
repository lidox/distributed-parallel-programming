import java.util.concurrent.locks.Lock;

public class Main {

	public long counter = 0; 
	private static Lock lock = new SpinLock();

	
	public static void main(String[] args) {
		
		int numOfThreads = 100;
		int numOfRunsPerThread = 1000000;

		Main mainObject = new Main();
		
		Runnable jobToRun = () -> {
			for (int i=0; i<numOfRunsPerThread; i++){
				mainObject.add(1);
			}
		}; 

		Thread[] threadList = new Thread[numOfThreads];
		for (int i=0; i<numOfThreads; i++){
			threadList[i] = new Thread(jobToRun);
			threadList[i].start();
		}

		for(int i=0; i<numOfThreads; i++){
			try {
				threadList[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("count: " + mainObject.counter);
	}
	
	public void add(int value){
		// uncomment here to remove the lock and see the difference
		lock.lock();
		try{
			counter += value;
		} finally{
			// uncomment here to remove the unlock and see the difference
			lock.unlock();
		}
	}

}
