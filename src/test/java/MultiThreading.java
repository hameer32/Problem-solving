import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

class MultiThreading {

    @Test
    void exampleForMultiThreading() {
        PrintValues pv = new PrintValues(3, 0);
        ExecutorService es = Executors.newCachedThreadPool();
        IntStream.range(0,3).forEach(s->es.execute(new CustomThread(pv,s)));
        es.shutdown();

    }

    static class CustomThread implements Runnable {
        private final PrintValues printValues;
        private final String name;

        public CustomThread(PrintValues printValues,int name) {
            this.printValues = printValues;
            this.name=String.valueOf(name);
        }

        @Override
        public void run() {
            Thread.currentThread().setName(name);
            printValues.printXValue();
        }
    }

    static class PrintValues {
        private final int THREAD_COUNT;
        private int x;

        public PrintValues(int threadCount, int x) {
            THREAD_COUNT = threadCount;
            this.x = x;
        }

        void printXValue() {
           // System.out.println(Thread.currentThread().getName());
            while (x <= 20) {
                synchronized (this) {
                    if (x % THREAD_COUNT == Integer.parseInt(Thread.currentThread().getName())) {
                        System.out.println("Thread : " + Thread.currentThread().getName() + " x : " + x++);
                        this.notifyAll();
                    }
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}