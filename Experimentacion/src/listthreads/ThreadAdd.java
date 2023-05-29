package listthreads;

import concurrentlist.*;
import java.util.concurrent.CountDownLatch;

public class ThreadAdd extends Thread {

    public ConcurrentList list;
    public int from;
    public int to;
    public int step;
    public CountDownLatch latch; 

    public ThreadAdd(ConcurrentList list, int from, int to, int step, CountDownLatch latch) {
        this.list = list;
        this.from = from;
        this.to = to;
        this.step = step;
        this.latch = latch; 
    }

    public void run(){
        int value = (Integer)from;
        
        while(value < to){
            list.add(value);
            value+=step;
        }

        latch.countDown(); 
    }

}

