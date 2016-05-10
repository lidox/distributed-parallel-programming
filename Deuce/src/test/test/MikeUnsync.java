package test;

import org.deuce.Atomic;
import java.util.Random;

public class MikeUnsync {

	private static final int NUMBER_OF_THREADS = 2;
	private static MySharedCounter counter = new MySharedCounter();

	public static void main (String[] args) {
		Thread[] testThreads = new Thread[NUMBER_OF_THREADS];

		for ( int i = 0; i<NUMBER_OF_THREADS; i++)
		{
			testThreads[i] = new Thread(new MyIncThread());
			testThreads[i].start();
		}
		//wait when all threads are done
		for ( int i = 0; i<NUMBER_OF_THREADS; i++)
		{
			try {
				testThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("program finished, counter="+counter.cnt);
	}

	private static class MySharedCounter {
		public int cnt = 0;

		public void increment() {
			cnt = cnt + 1;
		}
	}

	private static class MyIncThread implements Runnable {
		private int iterations=10000;
		

		public void run()
		{	long id;


			try{
				Thread.sleep(1000);	
				id = Thread.currentThread().getId();
				System.out.println(id + " started ");

				for( int i=0; i<iterations; i++)
				{
					counter.increment();
				}
				System.out.println(id + " done ");
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}

}
