package listthreads;
import concurrentlist.*;
import java.util.concurrent.CountDownLatch;

public class ThreadsUtils{
    
    public static Thread[] createThreadsAdd(ConcurrentList list, int numberOfThreads, int to, CountDownLatch latch){
        Thread[] threads = new ThreadAdd[numberOfThreads]; 
        
        for(int t = 0; t < numberOfThreads; t++){
            threads[t] = new ThreadAdd(list, t, to, numberOfThreads, latch);
        }
        return threads; 
    }

    public static Thread[] createThreadsRemove(ConcurrentList list, int numberOfThreads, int to, CountDownLatch latch){
        Thread[] threads = new ThreadRemove[numberOfThreads]; 
        
        for(int t = 0; t < numberOfThreads; t++){
            threads[t] = new ThreadRemove(list, t, to, numberOfThreads, latch);
        }
        return threads; 
    }

    public static Thread[] createThreadsAddAndThreadsRemove(ConcurrentList list, int numberOfThreadsAdd, int numberOfThreadsRemove, 
                                                        int to, CountDownLatch latch){
        int numberOfThreads = numberOfThreadsAdd + numberOfThreadsRemove;
        Thread[] threads = new Thread[numberOfThreads]; 
        
        for(int t = 0; t < numberOfThreadsAdd; t++){
            threads[t] = new ThreadAdd(list, t, to, numberOfThreadsAdd, latch);
        }

        for(int t = 0; t < numberOfThreadsRemove; t++){
            threads[t + numberOfThreadsAdd] = new ThreadRemove(list, t, to, numberOfThreadsRemove, latch);
        }
        return threads; 
    }

    public static Thread[] createThreadsAddAndThreadsRemoveThatSleeps(ConcurrentList list, int numberOfThreadsAdd, int numberOfThreadsRemoveThatSleeps, int to, 
                                                                int sleepBeforeAddingElement, CountDownLatch latch){
        int numberOfThreads = numberOfThreadsAdd + numberOfThreadsRemoveThatSleeps;
        Thread[] threads = new Thread[numberOfThreads]; 
        
        for(int t = 0; t < numberOfThreadsAdd; t++){
            threads[t] = new ThreadAdd(list, t, to, numberOfThreadsAdd, latch);
        }

        for(int t = 0; t < numberOfThreadsRemoveThatSleeps; t++){
            threads[t + numberOfThreadsAdd] = new ThreadRemoveThatSleeps(list, t, to, numberOfThreadsRemoveThatSleeps, sleepBeforeAddingElement, latch);
        }
        return threads; 
    }

    public static Thread[] createLessConcurrentThreadsAdd(ConcurrentList list, int numberOfThreads, int to, CountDownLatch latch){
        Thread[] threads = new ThreadAdd[numberOfThreads]; 
        
        for(int t = 0; t < numberOfThreads; t++){
            int from_t = (to/numberOfThreads) * t;
            int to_t = (to/numberOfThreads) * (t+1);
            threads[t] = new ThreadAdd(list, from_t, to_t, 1, latch);
        }
        return threads; 
    }


    public static void startThreads(Thread[] ts){
        for(int t = 0; t < ts.length; t++){
            ts[t].start();
        }
    }

    public static void joinThreads(Thread[] ts){
        for(int t = 0; t < ts.length; t++){
            try {
                ts[t].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static long measureThreadExcecutionTime(Thread[] threads, ConcurrentList list,
                                                    CountDownLatch latch ){
       
        long start = System.currentTimeMillis();

        startThreads(threads);
        
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        
        return timeElapsed; 
    }
}