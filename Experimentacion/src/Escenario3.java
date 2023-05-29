import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;
import java.io.File;

public class Escenario3 {
    /* Para cada estructura concurrente creamos n threads que agregan 
    concurrentemente 1000 elementos en total */
    
    public static void main(String[] args) throws Exception {
        
        File file = new File("src/logs/timeEscenario3.txt");
        file.createNewFile(); 
          
        int reps = 1000;
        int numberOfThreads     = 4;     // (Cambiar a 2 o 8)
        int numberOfElements    = 1000;

        FileWriter fileTime = new FileWriter("src/logs/timeEscenario3.txt");

        for(int i = 0; i < reps; i++){
            //Lista con granularidad fina
            FineGrainList   listFGL = new FineGrainList(); 
            CountDownLatch latchFGL = new CountDownLatch(numberOfThreads);
            Thread[] threadsFGL     = ThreadsUtils.createThreadsAdd(listFGL, numberOfThreads, numberOfElements, latchFGL);
            long executionTimeFGL   = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina", latchFGL);
            
            //Lista optimista
            OptimisticList  listOP  = new OptimisticList();
            CountDownLatch latchOP  = new CountDownLatch(numberOfThreads); 
            Thread[] threadsOP      = ThreadsUtils.createThreadsAdd(listOP, numberOfThreads, numberOfElements, latchOP);
            long executionTimeOP    = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista", latchOP);
            
            //Lista sin locks
            LockFreeList    listLFL = new LockFreeList();
            CountDownLatch latchLFL = new CountDownLatch(numberOfThreads);
            Thread[] threadsLFL     = ThreadsUtils.createThreadsAdd(listLFL, numberOfThreads, numberOfElements, latchLFL);
            long executionTimeLFL   = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks", latchLFL);

            fileTime.write(Long.toString(executionTimeFGL) + " " + 
                            Long.toString(executionTimeOP) + " "  + 
                            Long.toString(executionTimeLFL) + "\n"  );
        }

        fileTime.close(); 
    }
}
