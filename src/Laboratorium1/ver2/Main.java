package Laboratorium1.ver2;

public class Main {
	
	static long count = 0;

	public static void main(String[] args) throws InterruptedException {
		
		FirstThread firstThread = new FirstThread();
		SecondThread secondThread = new SecondThread();
		
		firstThread.start();
		secondThread.start();

		firstThread.join();
		secondThread.join();
		
		System.out.println(count);

	}

}
