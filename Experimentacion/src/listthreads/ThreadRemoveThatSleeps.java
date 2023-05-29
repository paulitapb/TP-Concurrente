package listthreads;

import java.util.concurrent.CountDownLatch;
import concurrentlist.*;

public class ThreadRemoveThatSleeps extends Thread {

    public ConcurrentList list;
    public int from;
    public int to;
    public int step;
    public int sleepBeforeAddingElement;
    public CountDownLatch latch;

    public ThreadRemoveThatSleeps (ConcurrentList list, int from, int to, int step, int sleepBeforeAddingElement, CountDownLatch latch) {
        this.list = list;
        this.from = from;
        this.to = to;
        this.step = step;
        this.sleepBeforeAddingElement = sleepBeforeAddingElement;
        this.latch = latch;
    }

    public void run(){
        int value = from;
        while(value < to){
            if(value == sleepBeforeAddingElement)
				try {
					ThreadRemoveThatSleeps.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            list.remove(value);
            value+=step;
        }
    }

}

