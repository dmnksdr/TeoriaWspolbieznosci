package Laboratorium1.ver3;

public class SecondThread extends Thread {
	
	public Counter counter;
	
	public SecondThread (Counter counter) {
		this.counter = counter;
	}
	
	public void run() {
		for(long i=0; i<10e6; i++) {
//			System.out.println("dec " + counter);
			counter.dec();
		}
	}

}
