package Laboratorium1.ver3;


public class Main2 {
	
	private static Counter counter = new Counter();

	public static void main(String[] args) throws InterruptedException {
		
		FirstThread firstThread = new FirstThread(counter);
		SecondThread secondThread = new SecondThread(counter);
		
		firstThread.start();
		secondThread.start();

		firstThread.join();
		secondThread.join();
		
		System.out.println(counter);

	}

}
