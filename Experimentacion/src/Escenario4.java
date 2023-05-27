import listthreads.*;

import java.io.File;
import java.io.FileWriter;

import concurrentlist.*;

public class Escenario4 {
    /* Para cada estructura concurrente creamos 2 threads, donde uno agrega 1000 elementos 
    y el otro los elimina */

    
    public static void main(String[] args) throws Exception {
        
        File file = new File("src/logs/timeEscenario4.txt");
        file.createNewFile(); 
          
        int reps = 1000;
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario4.txt");

        for(int i = 0; i < reps; i++){

            //Lista con granularidad fina
            FineGrainList   listFGL     = new FineGrainList(); 
            Thread[] threadsFGL = ThreadsUtils.createThreadsAddAndThreadsRemove(listFGL, 1, 1, 1000);
            long executionTimeFGL = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
            
            //Lista optimista
            OptimisticList  listOP      = new OptimisticList(); 
            Thread[] threadsOP = ThreadsUtils.createThreadsAddAndThreadsRemove(listOP, 1, 1, 1000);
            long executionTimeOP = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
            
            //Lista sin locks
            LockFreeList    listLFL     = new LockFreeList();
            Thread[] threadsLFL = ThreadsUtils.createThreadsAddAndThreadsRemove(listOP, 1, 1, 1000);
            long executionTimeLFL = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");
            
            fileTime.write(Long.toString(executionTimeFGL) + " " + 
                            Long.toString(executionTimeOP) + " "  + 
                            Long.toString(executionTimeLFL) + "\n"  );
        }
        
        fileTime.close(); 
    }
}
