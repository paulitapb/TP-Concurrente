import listthreads.*; 
import concurrentlist.*;

public class Escenario4 {
    /* Para cada estructura concurrente creamos 4 threads que eliminar 
    concurrentemente 250 elementos cada uno */

    
    public static void main(String[] args) throws Exception {
        
        //Lista con granularidad fina
        FineGrainList   listFGL     = new FineGrainList(); 
        Thread[] threadsFGL = new Thread[2];
        threadsFGL[0] = ThreadsUtils.createThreadsAdd(listFGL, 1, 1000)[0];
        threadsFGL[1] = ThreadsUtils.createThreadsRemove(listFGL, 1, 1000)[0];
        ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
        
        //Lista optimista
        OptimisticList  listOP      = new OptimisticList(); 
        Thread[] threadsOP = new Thread[2];
        threadsOP[0] = ThreadsUtils.createThreadsAdd(listOP, 1, 1000)[0];
        threadsOP[1] = ThreadsUtils.createThreadsRemove(listOP, 1, 1000)[0];
        ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
        
        //Lista sin locks
        LockFreeList    listLFL     = new LockFreeList();
        Thread[] threadsLFL = new Thread[2];
        threadsLFL[0] = ThreadsUtils.createThreadsAdd(listLFL, 1, 1000)[0];
        threadsLFL[1] = ThreadsUtils.createThreadsRemove(listLFL, 1, 1000)[0];
        ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");
        
    }
}
