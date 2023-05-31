import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.io.File; 
import java.util.concurrent.*; 

public class Escenario1 {
    /* Para cada estructura concurrente creamos n threads que agregan 
    concurrentemente 250 elementos cada uno intercaladamente */

    public static void main(String[] args) throws Exception {
        
        int reps = 1000;
        int numberOfThreads     = 2;     // (Cambiar a 2 o 8)
        int numberOfElements    = 250*numberOfThreads;  // (Cambiar a 500 o 2000 respectivo a la linea anterior)

        File file = new File("src/logs/timeEscenario1Threads"+ Integer.toString(numberOfThreads) +".txt");
        file.createNewFile();
          
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario1Threads"+ Integer.toString(numberOfThreads) +".txt");

        
        for(int i = 0; i < reps; i++){

            //Lista con granularidad fina
            FineGrainList   listFGL = new FineGrainList(); 
            CountDownLatch latchFGL = new CountDownLatch(numberOfThreads);
            Thread[] threadsFGL     = ThreadsUtils.createThreadsAdd(listFGL, numberOfThreads, numberOfElements, latchFGL);
            long executionTimeFGL   = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL, latchFGL);
            
            //Lista optimista
            OptimisticList  listOP  = new OptimisticList(); 
            CountDownLatch latchOP  = new CountDownLatch(numberOfThreads);  
            Thread[] threadsOP      = ThreadsUtils.createThreadsAdd(listOP, numberOfThreads, numberOfElements, latchOP);
            long executionTimeOP    = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, latchOP);
            
            //Lista lock-free
            LockFreeList    listLFL = new LockFreeList();
            CountDownLatch latchLFL = new CountDownLatch(numberOfThreads);
            Thread[] threadsLFL     = ThreadsUtils.createThreadsAdd(listLFL, numberOfThreads, numberOfElements, latchLFL);
            long executionTimeLFL   = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, latchLFL);
             
            if(!listFGL.checkListInvariant() || !listOP.checkListInvariant() || !listLFL.checkListInvariant()){
                System.out.println("Fallo el invariante para alguna lista");
            }
            
            fileTime.write(Long.toString(executionTimeFGL) + " " + 
            Long.toString(executionTimeOP) + " "  + 
            Long.toString(executionTimeLFL) + "\n"  );

        }
        fileTime.close(); 
    }
}
