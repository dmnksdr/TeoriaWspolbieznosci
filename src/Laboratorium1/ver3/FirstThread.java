package Laboratorium1.ver3;

public class FirstThread extends Thread {
	
	public Counter counter;
	
	public FirstThread (Counter counter) {
		this.counter = counter;
	}
	
	public void run() {
		for(long i=0; i<10e6; i++) {
//			System.out.println("inc " + counter);
			counter.inc();
		}
	}

}
