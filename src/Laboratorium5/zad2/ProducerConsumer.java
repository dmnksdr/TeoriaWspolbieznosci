package Laboratorium5.zad2;

import java.util.LinkedList;
import java.util.Random;

/**
 *  Wersja z pojedynczym producentem i konsumentem, bez sleep
 */


class Producer extends Thread {
    private Buffer _buf;
    private int maxNumberOfProducts;
    private Random rand = new Random();

    public Producer(Buffer buffer) {
        this._buf = buffer;
        this.maxNumberOfProducts = buffer.getSize() / 2;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int numberOfProducts = rand.nextInt(maxNumberOfProducts) + 1;
            synchronized (_buf) {
                while(_buf.getAvailableSpace() == 0) {
                    try {
                        _buf.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int availableSpace = Math.min(numberOfProducts, _buf.getAvailableSpace());
                for(int j=0; j<availableSpace; j++) {
                    String product = "" + i + "_" + j;
                    _buf.put(product);
                    System.out.println("Produced " + product);
                }
                _buf.notify();

            }

        }
    }
}

class Consumer extends Thread {

    private Buffer _buf;
    private int maxNumberOfProducts;
    private Random rand = new Random();

    public Consumer(Buffer buffer) {
        this._buf = buffer;
        this.maxNumberOfProducts = buffer.getSize() / 2;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int numberOfProducts = rand.nextInt(maxNumberOfProducts) + 1;
            synchronized (_buf) {
                while (_buf.isEmpty()) {
                    try {
                        _buf.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int availableProducts = Math.min(numberOfProducts, _buf.getNumberOfAvailableProducts());
                for(int j=0; j<availableProducts; j++) {
                    System.out.println("\tConsumed " + _buf.get());
                }
            }
        }
    }
}

class Buffer {

    private LinkedList<String> list = new LinkedList<>();
    private int maxSize;

    public Buffer(int M) {
        this.maxSize = 2*M;
    }

    public void put(String i) {
        if(list.size() >= maxSize) {
            throw new RuntimeException("Buffer size exceeded" + "Buffer size: " + list.size());
        }
        list.addLast(i);
    }

    public String get() {
        String ret = list.getFirst();
        list.removeFirst();
        return ret;
    }

    public int getAvailableSpace() {
        return maxSize-list.size();
    }

    public int getSize() {
        return this.maxSize;
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public int getNumberOfAvailableProducts() {
        return list.size();
    }
}

public class ProducerConsumer {
    public static void main(String[] args) throws InterruptedException {

        int M = 5;

        Buffer buffer = new Buffer(M);
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
