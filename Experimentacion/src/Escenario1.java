
import concurrentlist.*;

public class Escenario1 {


    public static ThreadAdd[] createThreadsAdd(ConcurrentList list, int numberOfThreads, int to){
        ThreadAdd[] threads = new ThreadAdd[numberOfThreads]; 
        
        for(int t = 0; t < numberOfThreads; t++){
            threads[t] = new ThreadAdd(list, t, to, numberOfThreads);
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

    public static void experiment(ConcurrentList list, int numberOfThreads, 
                                    int to, String listName){
        ThreadAdd[] threads = createThreadsAdd(list, numberOfThreads, to);

        long start = System.currentTimeMillis();

        startThreads(threads);
        joinThreads(threads);
        
        long finish = System.currentTimeMillis();
        long timeElapsed = finish -start;
        
        System.out.println("Time: ");
        System.out.println(timeElapsed);

        System.out.println(listName);
        list.printList();
    }
    public static void main(String[] args) throws Exception {
        
        //Lista con granularidad fina
        FineGrainList   listFGL     = new FineGrainList(); 
        experiment(listFGL, 4, 10, "Lista granularidad fina");
    
        //Lista optimista
        OptimisticList  listOP      = new OptimisticList(); 
        experiment(listOP, 4, 10, "Lista optimista");

        //Lista sin locks
        LockFreeList    listLFL     = new LockFreeList();
        experiment(listLFL, 4, 10, "Lista sin locks");
        
    }
}
