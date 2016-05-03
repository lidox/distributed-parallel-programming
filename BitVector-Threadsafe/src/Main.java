public class Main {

	public static void main(String[] args) {
		System.out.println("BitVector with Lock started...");
		
		int numOfThreads = 2000;
		int numOfRunsPerThread = 100;
		
		MyBitVector vector = new MyBitVector(1000000);
		
		Runnable pushBackJob = () -> {
			for (int i=0; i<numOfRunsPerThread; i++){
				vector.set(i);
			}
		}; 
		
		Thread[] threadList = new Thread[numOfThreads];
		for (int i=0; i<numOfThreads; i++){
			threadList[i] = new Thread(pushBackJob);
			threadList[i].start();
		}
		
		
		for(int i=0; i<numOfThreads; i++){
			try {
				threadList[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("vector size: " + vector.size());

	}

}
