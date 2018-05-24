package Laboratorium4.ver1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Przypadek z faworyzowaniem czytelników
 */
@SuppressWarnings("Duplicates")
public class ReadersPreference {

    static Semaphore readerMutex = new Semaphore(1);
    static Semaphore resourceLock = new Semaphore(1);
    static volatile int readCount = 0;


    static class Reader implements Runnable {
        @Override
        public void run() {
            try {
                //Ensure that no other reader can execute the <Entry> section while you are in it
                readerMutex.acquire();
                readCount++;
                if (readCount == 1) {
                    resourceLock.acquire();
                }
                readerMutex.release();

                //Reading section
                System.out.println("Wątek "+Thread.currentThread().getName() + " czyta");
                Thread.sleep(1000);
                System.out.println("Wątek "+Thread.currentThread().getName() + " skończył czytać");

                //Releasing section
                readerMutex.acquire();
                readCount--;
                if(readCount == 0) {
                    resourceLock.release();
                }
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
                resourceLock.acquire();
                System.out.println("Wątek "+Thread.currentThread().getName() + " pisze");
                Thread.sleep(1000);
                System.out.println("Wątek "+Thread.currentThread().getName() + " skończył pisać");
                resourceLock.release();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void main(String[] args) throws Exception {

        System.out.println("Faworyzowanie czytelników\n================================");

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
