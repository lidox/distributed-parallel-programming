package lsg;

//Aufgabe 2b

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;


public class BitVectorBenchmark{

	public static Set<Long> createRandomBits(long numOfBits, long vecLength){
		Set<Long> set = Collections.newSetFromMap(new ConcurrentHashMap<Long, Boolean>());

		for(long i=0; i<numOfBits; i++){
			long randomBit = -1;
			do{
				randomBit = (long)(Math.random()*vecLength)%vecLength;
			} while(set.contains(randomBit));
			set.add(randomBit);
		}

		return set;
	}

	public static long calcSum(Set<Long> set){
		long output = 0;
		for(long l : set){
			output += l;
		}
		return output;
	}

	public static void printBenchmark(long sumBefore, long sumAfter, long start, long end){
		System.out.println("Checksumme der EintrÃ¤ge in der zufÃ¤llig erzeugten Liste: " + sumBefore);
		System.out.println("Checksumme der EintrÃ¤ge im Vektor : " + sumAfter);
		System.out.println("Zeit: "+ (end - start) / 1000.0 / 1000.0 +" ms\n");
	}

	public static void testBitVector(int id, Set<Long> set, int numThreads, long vecLength) throws InterruptedException{
		Set<Long> randomSet = set;

		long sumBefore = calcSum(randomSet);
		AtomicLong sumAfter = new AtomicLong(0);

		FrontierList bitVector; // = new ConcurrentBitVectorLock(vecLength);;
		if(id == 0)
			bitVector = new ConcurrentBitVectorLock(vecLength);
		 else //if(id == 1) 
			bitVector = new ConcurrentBitVector(vecLength);


		Runnable r = () -> {
			for(long l : randomSet){
				bitVector.set(l);
			}
		};

		Runnable r2 = () -> {
			long tmpSum = 0;
			while(!bitVector.isEmpty()){
				long tmp = bitVector.popFront();
				if(tmp != -1)
					tmpSum += tmp;
			}
			sumAfter.addAndGet(tmpSum);
		};

		// Thread section
		long start = System.nanoTime();
		Thread[] th = new Thread[numThreads];
		//push section
		for(int i=0; i<numThreads; i++){
			th[i] = new Thread(r);
			th[i].start();
		}

		for(int i=0; i<numThreads; i++){
			th[i].join();
		}

		//pop section
		for(int i=0; i<numThreads; i++){
			th[i] = new Thread(r2);
			th[i].start();
		}

		for(int i=0; i<numThreads; i++){
			th[i].join();
		}

		long end = System.nanoTime();
		if(id == 0)
			System.out.println("\nConcurrentBitVector locked:");
		else
			System.out.println("\nConcurrentBitVector lock-free:");
		printBenchmark(sumBefore,  sumAfter.get(),  start,  end);
	}


	public static void main(String[] args) throws InterruptedException{

		/**
		if(args.length != 3){
			System.err.println("usage BitVectorBenchmark [Vec-length] [#numBits] [#numThreads]");
			System.exit(1);
		}
		 */
		
		long vecLength = 100000; //Integer.parseInt(args[0]);
		long numBits = 2000; //Integer.parseInt(args[1]);
		int numThreads = 1000; //Integer.parseInt(args[2]);
		
		if(vecLength < numBits){
			System.err.println("Error: vec-length has to be bigger then numBits");
			System.exit(2);
		}

		System.out.println("Erzeuge Testdaten...");
		Set<Long> randomSet = createRandomBits(numBits, vecLength);
		Set<Long> randomSet2 = Collections.newSetFromMap(new ConcurrentHashMap<Long, Boolean>());
		randomSet2.addAll(randomSet);
		
		System.out.println("BitVector Locked...");
		testBitVector(2, randomSet, numThreads, vecLength);
		System.out.println("BitVector Lock Free...");
		
		testBitVector(1, randomSet2, numThreads, vecLength);

	}

}
