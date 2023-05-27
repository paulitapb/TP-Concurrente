import listthreads.*; 
import concurrentlist.*;

public class Escenario5 {
    /* Para cada estructura concurrente creamos 4 threads que eliminar 
    concurrentemente 250 elementos cada uno */

    
    public static void main(String[] args) throws Exception {
        
        //Lista con granularidad fina
        FineGrainList   listFGL     = new FineGrainList(); 
        Thread[] threadsFGL = ThreadsUtils.createThreadsAddAndThreadsRemove(listFGL, 4, 1, 1000);
        ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
        
        //Lista optimista
        OptimisticList  listOP      = new OptimisticList(); 
        Thread[] threadsOP = ThreadsUtils.createThreadsAddAndThreadsRemove(listOP, 4, 1, 1000);
        ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
        
        //Lista sin locks
        LockFreeList    listLFL     = new LockFreeList();
        Thread[] threadsLFL = ThreadsUtils.createThreadsAddAndThreadsRemove(listOP, 4, 1, 1000);
        ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");
        
    }
}
