package listthreads;
import concurrentlist.*;

public class ThreadsUtils{
    
    public static Thread[] createThreadsAdd(ConcurrentList list, int numberOfThreads, int to){
        Thread[] threads = new ThreadAdd[numberOfThreads]; 
        
        for(int t = 0; t < numberOfThreads; t++){
            threads[t] = new ThreadAdd(list, t, to, numberOfThreads);
        }
        return threads; 
    }

    public static Thread[] createThreadsRemove(ConcurrentList list, int numberOfThreads, int to){
        Thread[] threads = new ThreadRemove[numberOfThreads]; 
        
        for(int t = 0; t < numberOfThreads; t++){
            threads[t] = new ThreadRemove(list, t, to, numberOfThreads);
        }
        return threads; 
    }

    public static Thread[] createThreadsAddAndThreadsRemove(ConcurrentList list, int numberOfThreadsAdd, int numberOfThreadsRemove, int to){
        int numberOfThreads = numberOfThreadsAdd + numberOfThreadsRemove;
        Thread[] threads = new ThreadRemove[numberOfThreads]; 
        
        for(int t = 0; t < numberOfThreadsAdd; t++){
            threads[t] = new ThreadAdd(list, t, to, numberOfThreadsAdd);
        }

        for(int t = 0; t < numberOfThreadsRemove; t++){
            threads[t + numberOfThreadsAdd] = new ThreadRemove(list, t, to, numberOfThreadsRemove);
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

    public static long measureThreadExcecutionTime(Thread[] threads, ConcurrentList list, String listName){

        long start = System.currentTimeMillis();

        startThreads(threads);
        joinThreads(threads);
        
        long finish = System.currentTimeMillis();
        long timeElapsed = finish -start;
        
        System.out.println(listName);
        System.out.println("Time: " + timeElapsed);
        
        System.out.println("The final list size is " + list.size());
        System.out.println("");
        //list.printList();
        return timeElapsed; 
    }
}