package listthreads;

import concurrentlist.*;

public class ThreadRemove extends Thread {

    public ConcurrentList list;
    public int from;
    public int to;
    public int step;

    public ThreadRemove (ConcurrentList list, int from, int to, int step) {
        this.list = list;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    public void run(){
        int value = from;
        while(value < to){
            list.remove(value);
            value+=step;
        }
    }

}

