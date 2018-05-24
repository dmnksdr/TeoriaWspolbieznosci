package Laboratorium2.ver1;

public class Test {

    public static void main(String[] args) throws InterruptedException {

        Counter counter = new Counter();

        Thread up = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10e6; i++) {
                    counter.increment();
                }
            }
        });

        Thread down = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10e6; i++) {
                    counter.decrement();
                }
            }
        });

        Long start = System.currentTimeMillis();

        up.start();
        down.start();

        up.join();
        down.join();

        Long end = System.currentTimeMillis();

        System.out.println("Counter = " + counter.getCnt());
        System.out.println("Time needed = " + (end - start));

    }

}
