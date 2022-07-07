package threads;

public class InterThreadCommunication {
    public static void main(String[] args) {
        PrintResponsibleClass prc = new PrintResponsibleClass(3, 20);
        Thread t1 = new Thread(prc::printValues, "0");
        Thread t2 = new Thread(prc::printValues, "1");
        Thread t3 = new Thread(prc::printValues, "2");
        t1.start();
        t2.start();
        t3.start();
    }
}

class PrintResponsibleClass {
    int THREAD_COUNT;
    int MAX_BOUNDARY_VALUE;

    public PrintResponsibleClass(int THREAD_COUNT, int max_boundary_value) {
        this.THREAD_COUNT = THREAD_COUNT;
        MAX_BOUNDARY_VALUE = max_boundary_value;
    }

    int x = 0;

    void printValues() {
        while (x < MAX_BOUNDARY_VALUE) {
            synchronized (this) {
                if (x % THREAD_COUNT == Integer.parseInt(Thread.currentThread().getName())) {
                    System.out.println("Thread : " + Thread.currentThread().getName() + " x :" + x++);
                    this.notifyAll();
                }
                if (x < MAX_BOUNDARY_VALUE) {
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

