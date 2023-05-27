import listthreads.*; 
import concurrentlist.*;

public class Escenario2 {
    /* Para cada estructura concurrente creamos 4 threads que eliminar 
    concurrentemente 250 elementos cada uno */

    
    public static void main(String[] args) throws Exception {
        
        FineGrainList   listFGL     = new FineGrainList(); 
        OptimisticList  listOP      = new OptimisticList(); 
        LockFreeList    listLFL     = new LockFreeList();

        for(int i = 0; i<1000; i++){
            listFGL.add(i);
            listOP.add(i);
            listLFL.add(i);
        }

        //Lista con granularidad fina
        Thread[] threadsFGL = ThreadsUtils.createThreadsRemove(listFGL, 4, 1000);
        ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
    
        //Lista optimista
        Thread[] threadsOP = ThreadsUtils.createThreadsRemove(listOP, 4, 1000);
        ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");

        //Lista sin locks
        Thread[] threadsLFL = ThreadsUtils.createThreadsRemove(listLFL, 4, 1000);
        ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");
        
    }
}
