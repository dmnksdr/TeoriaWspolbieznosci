package Laboratorium3.ver1;

import java.util.LinkedList;

/**
 *  Wersja z pojedynczym producentem i konsumentem, bez sleep
 */


class Producer extends Thread {
    private Buffer _buf;

    public Producer(Buffer buffer) {
        this._buf = buffer;
    }


    public void run() {
        for (int i = 0; i < 100; ++i) {
            synchronized (_buf) {
                _buf.put(i);
                System.out.println("Produced " + i);
                _buf.notify();
            }

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
            synchronized (_buf) {
                while (_buf.isEmpty()) {
                    try {
                        _buf.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Consumed " + _buf.get());
            }
        }
    }
}

class Buffer {

    private LinkedList<Integer> list = new LinkedList<>();

    public void put(int i) {
        list.addLast(i);
    }

    public int get() {
        int ret = list.getFirst();
        list.removeFirst();
        return ret;
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }
}

public class PKmon {
    public static void main(String[] args) throws InterruptedException {

        Buffer buffer = new Buffer();
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);

        Long start = System.currentTimeMillis();

        producer.start();
        consumer.start();

        producer.join();
        consumer.join();

        Long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end-start));

    }
}
