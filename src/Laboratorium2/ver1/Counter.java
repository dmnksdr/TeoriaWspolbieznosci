package Laboratorium2.ver1;


public class Counter {

    Semafor semafor = new Semafor(1);
    private int cnt = 0;

    void increment() {
        semafor.P();
        cnt++;
        semafor.V();
    }

    void decrement() {
        semafor.P();
        cnt--;
        semafor.V();
    }

    public int getCnt() {
        return cnt;
    }

}



