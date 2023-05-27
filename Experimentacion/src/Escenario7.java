import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.io.File;

public class Escenario7 {
    /* Para cada estructura concurrente creamos 8 threads que agregan 
    concurrentemente 250 elementos cada uno */

    
    public static void main(String[] args) throws Exception {
        
        File file = new File("src/logs/timeEscenario7.txt");
        file.createNewFile(); 
          
        int reps = 1000;
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario7.txt");

        for(int i = 0; i < reps; i++){

            //Lista con granularidad fina
            FineGrainList   listFGL     = new FineGrainList(); 
            Thread[] threadsFGL = ThreadsUtils.createThreadsAdd(listFGL, 8, 2000);
            long executionTimeFGL =ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
            
            //Lista optimista
            OptimisticList  listOP      = new OptimisticList(); 
            Thread[] threadsOP = ThreadsUtils.createThreadsAdd(listOP, 8, 2000);
            long executionTimeOP =ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
            
            //Lista sin locks
            LockFreeList    listLFL     = new LockFreeList();
            Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 8, 2000);
            long executionTimeLFL = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");
            
            
            fileTime.write(Long.toString(executionTimeFGL) + " " + 
            Long.toString(executionTimeOP) + " "  + 
            Long.toString(executionTimeLFL) + "\n"  );
        }

        fileTime.close(); 
    }
}
