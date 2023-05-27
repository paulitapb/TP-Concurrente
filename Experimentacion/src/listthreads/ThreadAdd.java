package listthreads;

import concurrentlist.*;
public class ThreadAdd extends Thread {

    public ConcurrentList list;
    public int from;
    public int to;
    public int step;

    public ThreadAdd(ConcurrentList list, int from, int to, int step) {
        this.list = list;
        this.from = from;
        this.to = to;
        this.step = step;
    }

    public void run(){
        int value = from;
        
        while(value < to){
            list.add(value);
            value+=step;
        }
    }

}

