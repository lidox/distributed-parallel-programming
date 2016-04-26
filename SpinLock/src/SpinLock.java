import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SpinLock implements Lock{
	
	// standard = unfair. per constructor set fair
	ReentrantLock lock = new ReentrantLock();
	
	public SpinLock(){}

	public void lock(){
		lock.lock();
	}

	public void unlock(){
		lock.unlock();
	}

	@Override
	public boolean tryLock() {
		return lock.tryLock();
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return lock.tryLock(time, unit);
	}

	@Override
	public Condition newCondition() {
		// Muss nicht implementiert werden.
		return null;
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// Muss nicht implementiert werden.
	}

}

/*
 // Blatt 1, Aufgabe 1a-d - Muster Lösung

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;


public class SpinLock implements Lock{
	// false - not locked
	// true - locked
	private AtomicBoolean isLocked;

	public SpinLock(){
		isLocked = new AtomicBoolean(false);
	}

	public void lock(){
		while(true){
	    	if(isLocked.compareAndSet(false, true))
	    		break;
	    }
	}

	public void unlock(){
		isLocked.compareAndSet(true, false);
	}

	@Override
	public boolean tryLock() {
		if(isLocked.compareAndSet(false, true)){
			return true;
		}
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		long end = System.nanoTime() + unit.toNanos(time);
		while(System.nanoTime() < end){
			if(isLocked.compareAndSet(false, true)){
				return true;
			}
		}
		return false;
	}

	@Override
	public Condition newCondition() {
		// Muss nicht implementiert werden.
		return null;
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		// Muss nicht implementiert werden.
	}

}
 * */

