package Laboratorium1.ver3;

public class Counter {
	
	private int count = 0;
	
	public void dec() {
		count--;
	}
	
	public void inc() {
		count++;
	}

	@Override
	public String toString() {
		return "" + count;
	}
	
	

}
