
public class Main {

	public static void main(String[] args) {
		
		Thread t1 = new Thread(new SpinLock(), "Thread1");
		Thread t2 = new Thread(new SpinLock(), "Thread2");
		
		t1.start();
		t2.start();
	}

}
