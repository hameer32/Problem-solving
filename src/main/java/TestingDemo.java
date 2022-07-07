import org.junit.jupiter.api.Test;

public class TestingDemo {
    @Test
    public void printValues() {
        PrintClass printClass = new PrintClass(3);
        new DemoThread(printClass, "0").start();
        new DemoThread(printClass, "1").start();
        new DemoThread(printClass, "2").start();

    }
}

class DemoThread extends Thread {
    PrintClass printClass;

    DemoThread(PrintClass printClass, String name) {
        this.printClass = printClass;
        this.setName(name);
    }

    @Override
    public void run() {
        printClass.printValuesMethod();
    }
}

class PrintClass {
    static int x = 0;
    final int THREAD_COUNT_VALUE;

    PrintClass(int thread_count_value) {
        THREAD_COUNT_VALUE = thread_count_value;
    }

    void printValuesMethod() {
        synchronized (this) {
            while (x < 20) {
                if (x % THREAD_COUNT_VALUE == Integer.parseInt(Thread.currentThread().getName())) {
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