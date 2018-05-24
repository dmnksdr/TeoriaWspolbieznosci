package Laboratorium3.ver3;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *  Wersja z wieloma producentami i konsumentami, ze sleep.
 *  Liczbę konsumentów i producentów ustawić można poprzez zmienne numberOfProducers i numberOfProducers
 */


@SuppressWarnings("Duplicates")
class Producer extends Thread {
    private Buffer _buf;

    public Producer(Buffer buffer) {
        this._buf = buffer;
    }


    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            synchronized (_buf) {
                _buf.put();
                _buf.notify();
            }

        }
    }
}

@SuppressWarnings("Duplicates")
class Consumer extends Thread {

    private Buffer _buf;

    public Consumer(Buffer buffer) {
        this._buf = buffer;
    }

    public void run() {
        for (int i = 0; i < 100; ++i) {
            try {
                sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

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

    private int idx = 0;
    private LinkedList<Integer> list = new LinkedList<>();

    public void put() {
        System.out.println("Produced " + idx);
        list.addLast(idx++);
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

@SuppressWarnings("Duplicates")
public class PKmon3 {
    public static void main(String[] args) throws InterruptedException {

        Buffer buffer = new Buffer();

        int numberOfProducers = 3;
        int numberOfConsumers = 2;

        List<Producer> producers = new ArrayList<>(numberOfProducers);
        List<Consumer> consumers = new ArrayList<>(numberOfConsumers);

        for (int i = 0; i < numberOfProducers; i++) {
            producers.add(new Producer(buffer));
        }

        for (int i = 0; i < numberOfConsumers; i++) {
            consumers.add(new Consumer(buffer));
        }

        Long start = System.currentTimeMillis();

        for (Producer producer : producers)
            producer.start();

        for (Consumer consumer : consumers)
            consumer.start();

        for (Producer producer : producers)
            producer.join();

        for (Consumer consumer : consumers)
            consumer.join();

        Long end = System.currentTimeMillis();

        System.out.println("Time taken: " + (end - start));

    }
}
