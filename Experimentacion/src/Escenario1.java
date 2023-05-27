import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.io.IOException; 

public class Escenario1 {
    /* Para cada estructura concurrente creamos 4 threads que agregan 
    concurrentemente 250 elementos cada uno intercaladamente */

    
    public static void main(String[] args) throws Exception {
        
        int reps = 1000;
        FileWriter fileTime = new FileWriter("logs/timeFGLEscenario1.txt");

        for(int i = 0; i < reps; i++){

            //Lista con granularidad fina
            FineGrainList   listFGL     = new FineGrainList(); 
            Thread[] threadsFGL = ThreadsUtils.createThreadsAdd(listFGL, 4, 1000);
            long executionTimeFGL = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
            fileTime.write(Long.toString(executionTimeFGL) + "\n");
        
            //Lista optimista
            OptimisticList  listOP      = new OptimisticList(); 
            Thread[] threadsOP = ThreadsUtils.createThreadsAdd(listOP, 4, 1000);
            long executionTimeOP = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
            fileTime.write(Long.toString(executionTimeOP) + "\n");
            

            //Lista sin locks
            LockFreeList    listLFL     = new LockFreeList();
            Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 4, 1000);
            long executionTimeLFL = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");
            fileTime.write(Long.toString(executionTimeLFL) + "\n");

        }
        
        fileTime.close(); 
    }
}
