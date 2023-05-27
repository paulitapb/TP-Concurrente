import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.io.File;

public class Escenario6 {
    /* Para cada estructura concurrente creamos 5 threads, donde uno agrega 1000 elementos 
    y los otros 4 los eliminan (250 elementos cada uno) */

    
    public static void main(String[] args) throws Exception {
        File file = new File("src/logs/timeEscenario6.txt");
        file.createNewFile(); 
          
        int reps = 1000;
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario6.txt");

        for(int i = 0; i < reps; i++){

            //Lista con granularidad fina
            FineGrainList   listFGL     = new FineGrainList(); 
            Thread[] threadsFGL = ThreadsUtils.createThreadsAddAndThreadsRemove(listFGL, 1, 4, 1000);
            long executionTimeFGL = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
            
            //Lista optimista
            OptimisticList  listOP      = new OptimisticList(); 
            Thread[] threadsOP = ThreadsUtils.createThreadsAddAndThreadsRemove(listOP, 1, 4, 1000);
            long executionTimeOP = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
            
            //Lista sin locks
            LockFreeList    listLFL     = new LockFreeList();
            Thread[] threadsLFL = ThreadsUtils.createThreadsAddAndThreadsRemove(listOP, 1, 4, 1000);
            long executionTimeLFL =ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");
            
            fileTime.write(Long.toString(executionTimeFGL) + " " + 
            Long.toString(executionTimeOP) + " "  + 
            Long.toString(executionTimeLFL) + "\n"  );
        }

        fileTime.close(); 
    }
}
