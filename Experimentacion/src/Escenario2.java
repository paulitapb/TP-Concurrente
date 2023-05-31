import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;
import java.io.File;

public class Escenario2 {
    /* Para cada estructura concurrente creamos 4 threads que eliminan
    concurrentemente 250 elementos cada uno */

    public static void main(String[] args) throws Exception {
        
        int reps = 1000;
        int numberOfThreads     = 8;     // (Cambiar a 2 o 8)
        int numberOfElements    = 250*numberOfThreads;  // (Cambiar a 500 o 2000 respectivo a la linea anterior)

        File file = new File("src/logs/timeEscenario2Threads"+ Integer.toString(numberOfThreads) +".txt");
        file.createNewFile(); 
        
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario2Threads"+ Integer.toString(numberOfThreads) +".txt");

        for(int j = 0; j < reps; j++){
            FineGrainList   listFGL = new FineGrainList(); 
            OptimisticList  listOP  = new OptimisticList(); 
            LockFreeList    listLFL = new LockFreeList();

            for(int i = 0; i<numberOfElements; i++){
                listFGL.add(i);
                listOP.add(i);
                listLFL.add(i);
            }
            
            
            //Lista con granularidad fina
            CountDownLatch latchFGL = new CountDownLatch(numberOfThreads);
            Thread[] threadsFGL     = ThreadsUtils.createThreadsRemove(listFGL, numberOfThreads, numberOfElements, latchFGL);
            long executionTimeFGL   = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL, latchFGL);           

            //Lista optimista
            CountDownLatch latchOP  = new CountDownLatch(numberOfThreads);
            Thread[] threadsOP      = ThreadsUtils.createThreadsRemove(listOP, numberOfThreads, numberOfElements, latchOP);
            long executionTimeOP    = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, latchOP);

            //Lista sin locks
            CountDownLatch latchLFL = new CountDownLatch(numberOfThreads);
            Thread[] threadsLFL     = ThreadsUtils.createThreadsRemove(listLFL, numberOfThreads, numberOfElements, latchLFL);
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
