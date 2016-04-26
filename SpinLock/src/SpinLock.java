import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class SpinLock implements Lock, Runnable{

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

	@Override
	public void run() {
		System.out.println("I do work! :");
	}

}

