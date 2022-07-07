import org.junit.jupiter.api.Test;

public class ThreadInterviewQ {

    @Test
    public void interviewQuestion() {
        PrtValues pv = new PrtValues(3, 20); // This will be Monitor(contain sync block)
        new Thread(pv::printValues, "0").start();
        new Thread(pv::printValues, "1").start();
        new Thread(pv::printValues, "2").start();
    }
}

class PrtValues {
    final int THREAD_COUNT;
    final int MAX_BOUNDARY_VALUE;

    PrtValues(int thread_count, int max_boundary_value) {
        THREAD_COUNT = thread_count;
        MAX_BOUNDARY_VALUE = max_boundary_value;
    }

    int x = 0;

    void printValues() {
        //Something
        while (x < MAX_BOUNDARY_VALUE) {
            synchronized (this) {
                if (x % THREAD_COUNT == Integer.parseInt(Thread.currentThread().getName())) {
                    System.out.println("Thread : " + Thread.currentThread().getName() + " x value : " + x++);
                    this.notifyAll();
                }
                if (x < MAX_BOUNDARY_VALUE) { // this condition check , because which ever thread runs last should not go in waiting state, or else
                    // program will keep on running
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
