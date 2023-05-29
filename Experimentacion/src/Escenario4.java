import listthreads.*; 
import concurrentlist.*;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;

public class Escenario4{
    /* DEBERIA SER ESCENARIO 4
    Misma cantidad de operaciones y mismca cantidad de threads. Varian los elementos asignados para ser agregados por cada thread.
    */
    public static void main(String[] args) throws Exception {
        
        File file = new File("src/logs/timeEscenario8.txt");
        file.createNewFile(); 
          
        int reps = 1000;
        int numberOfThreads = 4;

        FileWriter fileTime = new FileWriter("src/logs/timeEscenario8.txt");

        for(int i = 0; i < reps; i++){
            //Lista con granularidad fina
            FineGrainList   listFGL = new FineGrainList(); 
            CountDownLatch latchFGL = new CountDownLatch(numberOfThreads);
            Thread[] threadsFGL     = ThreadsUtils.createLessConcurrentThreadsAdd(listFGL, numberOfThreads, 1000, latchFGL);
            //Thread[] threadsFGL   = ThreadsUtils.createThreadsAdd(listFGL, numberOfThreads, 1000, latchFGL);
            long executionTimeFGL   = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina", latchFGL);
            

            //Lista optimista
            OptimisticList  listOP  = new OptimisticList();
            CountDownLatch latchOP  = new CountDownLatch(numberOfThreads); 
            Thread[] threadsOP      = ThreadsUtils.createLessConcurrentThreadsAdd(listOP, numberOfThreads, 1000, latchOP);
            //Thread[] threadsOP    = ThreadsUtils.createThreadsAdd(listOP, numberOfThreads, 1000, latchOP);
            long executionTimeOP    = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista", latchOP);
            
            //Lista sin locks
            LockFreeList    listLFL = new LockFreeList();
            CountDownLatch latchLFL = new CountDownLatch(numberOfThreads);
            Thread[] threadsLFL     = ThreadsUtils.createLessConcurrentThreadsAdd(listLFL, numberOfThreads, 1000, latchLFL);
            //Thread[] threadsLFL   = ThreadsUtils.createThreadsAdd(listLFL, numberOfThreads, 1000, latchLFL);
            long executionTimeLFL   = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks", latchLFL);
            
            fileTime.write(Long.toString(executionTimeFGL) + " " + 
                            Long.toString(executionTimeOP) + " "  + 
                            Long.toString(executionTimeLFL) + "\n"  );
        }
        
        fileTime.close(); 
    }
}
