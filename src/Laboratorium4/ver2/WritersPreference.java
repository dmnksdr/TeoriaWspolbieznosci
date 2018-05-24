package Laboratorium4.ver2;

import java.util.*;
import java.util.concurrent.Semaphore;

/**
 * Przypadek z faworyzowaniem pisarzy
 */
@SuppressWarnings("Duplicates")
public class WritersPreference {

    static Semaphore readerMutex = new Semaphore(1);
    static Semaphore writerMutex = new Semaphore(1);
    static Semaphore readTry = new Semaphore(1);
    static Semaphore resourceLock = new Semaphore(1);
    static volatile int readCount = 0;
    static volatile int writeCount = 0;


    static class Reader implements Runnable {
        @Override
        public void run() {
            try {
                readTry.acquire();
                readerMutex.acquire();
                readCount++;
                if(readCount == 1)
                    resourceLock.acquire();
                readerMutex.release();
                readTry.release();

                System.out.println(Thread.currentThread().getName() + " czyta");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " skończył czytać");

                readerMutex.acquire();
                readCount--;
                if(readCount == 0)
                    resourceLock.release();
                readerMutex.release();

            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Writer implements Runnable {
        @Override
        public void run() {
            try {
                writerMutex.acquire();
                writeCount++;
                if(writeCount == 1)
                    readTry.acquire();
                writerMutex.release();

                resourceLock.acquire();
                System.out.println(Thread.currentThread().getName() + " pisze");
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " skończył pisać");
                resourceLock.release();

                writerMutex.acquire();
                writeCount--;
                if(writeCount == 0)
                    readTry.release();
                writerMutex.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Faworyzowanie pisarzy\n================================");

        int readersNumber = 10;
        int writersNumber = 4;

        List<Thread> readers = new ArrayList<>();
        List<Thread> writers = new ArrayList<>();

        for(int i=1; i<=readersNumber; i++) {
            Thread thread = new Thread(new Reader());
            thread.setName("Czytelnik " + i);
            readers.add(thread);
        }

        for(int i=1; i<=writersNumber; i++) {
            Thread thread = new Thread(new Writer());
            thread.setName("Pisarz " + i);
            writers.add(thread);
        }

        List<Thread> all = new ArrayList<>();
        all.addAll(readers);
        all.addAll(writers);
        Collections.shuffle(all);

        Long start = System.currentTimeMillis();

        for(Thread thread : all)
            thread.start();

        for(Thread thread : all)
            thread.join();

        Long end = System.currentTimeMillis();

        System.out.println("================================\n Potrzebny czas: " + (end-start));

    }
}
