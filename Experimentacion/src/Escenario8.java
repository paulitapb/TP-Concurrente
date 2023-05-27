import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.io.File;

public class Escenario8 {
    /* Para cada estructura concurrente creamos 4 threads que agregan 
    concurrentemente 250 elementos cada uno, donde cada thread agrega un "bloque" de numeros
    Thread 1: 0 -  249
    Thread 2: 250 - 499
    Thread 3: 500 - 749 
    Thread 4: 750 - 1000*/

    
    public static void main(String[] args) throws Exception {
        
        File file = new File("src/logs/timeEscenario8.txt");
        file.createNewFile(); 
          
        int reps = 1000;
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario8.txt");

        for(int i = 0; i < reps; i++){
            //Lista con granularidad fina
            FineGrainList   listFGL     = new FineGrainList(); 
            Thread[] threadsFGL = ThreadsUtils.createLessConcurrentThreadsAdd(listFGL, 4, 1000);
            long executionTimeFGL = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
            
            //Lista optimista
            OptimisticList  listOP      = new OptimisticList(); 
            Thread[] threadsOP = ThreadsUtils.createLessConcurrentThreadsAdd(listOP, 4, 1000);
            long executionTimeOP = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
            
            //Lista sin locks
            LockFreeList    listLFL     = new LockFreeList();
            Thread[] threadsLFL = ThreadsUtils.createLessConcurrentThreadsAdd(listLFL, 4, 1000);
            long executionTimeLFL = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");
            
            fileTime.write(Long.toString(executionTimeFGL) + " " + 
            Long.toString(executionTimeOP) + " "  + 
            Long.toString(executionTimeLFL) + "\n"  );
        }

        fileTime.close(); 
    }
}
