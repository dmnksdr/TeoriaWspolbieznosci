package Laboratorium1.ver2;

public class FirstThread extends Thread {
	
	
	public void run() {
		for(long i=0; i<10e8; i++) {
			Main.count++;
		}
	}

}
