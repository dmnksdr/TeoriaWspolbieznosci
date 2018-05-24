package Laboratorium3.ver0;

import java.util.LinkedList;

//PKmon.java

class Producer extends Thread {

	private Buffer _buf;

	public Producer(Buffer buffer) {
//		try {
//			Thread.sleep(2);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		this._buf = buffer;
	}

	public void run() {
		for (int i = 0; i < 100; ++i) {
//			try {
//				Thread.sleep(2);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
			System.out.println("Produced " + i);
			_buf.put(i);
		}
	}
}

class Consumer extends Thread {

	private Buffer _buf;

	public Consumer(Buffer buffer) {
		this._buf = buffer;
	}

	public void run() {
		for (int i = 0; i < 100; ++i) {
			System.out.println("   Consumed " + _buf.get());
		}
	}
}

class Buffer {

	LinkedList<Integer> list = new LinkedList<>();
	boolean available = false;

	
	public synchronized void put(int i) {
		System.out.println("Producer waiting");
		list.addLast(i);
		available = true;
		notifyAll();
	}

	public synchronized int get() {
		while(!available) {
			try {
				System.out.println("   Consumer waiting");
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		int ret = list.getLast();
		list.removeLast();
		if(list.isEmpty())
			available = false;
		notifyAll();
		return ret;
	}

}

public class PKmon {
	public static void main(String[] args) {

		Buffer buffer = new Buffer();
		
		//start producers
		for(int i=0; i<10; i++) {
			Producer producer = new Producer(buffer);
			producer.start();
		}
		
		//start consumers
		for(int i=0; i<1; i++) {
			Consumer consumer = new Consumer(buffer);
			consumer.start();
		}
		
		

	}
}
