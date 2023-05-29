package listthreads;

import java.util.concurrent.CountDownLatch;
import concurrentlist.*;

public class ThreadRemove extends Thread {

    public ConcurrentList list;
    public int from;
    public int to;
    public int step;
    public CountDownLatch latch;

    public ThreadRemove (ConcurrentList list, int from, int to, int step, CountDownLatch latch) {
        this.list = list;
        this.from = from;
        this.to = to;
        this.step = step;
        this.latch = latch;
    }

    public void run(){
        int value = from;
        while(value < to){
            list.remove(value);
            value+=step;
        }
    }

}

