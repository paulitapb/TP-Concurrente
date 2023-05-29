import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.io.File;

public class Escenario3 {
    /* Para cada estructura concurrente creamos n threads que agregan 
    concurrentemente 1000 elementos en total */
    
    public static void main(String[] args) throws Exception {
        
        File file = new File("src/logs/timeEscenario3.txt");
        file.createNewFile(); 
          
        int reps = 1000;
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario3.txt");

        for(int i = 0; i < reps; i++){
            //Lista con granularidad fina
            FineGrainList   listFGL     = new FineGrainList(); 
            Thread[] threadsFGL = ThreadsUtils.createThreadsAdd(listFGL, 2, 1000);
            //Thread[] threadsFGL = ThreadsUtils.createThreadsAdd(listFGL, 4, 1000);
            //Thread[] threadsFGL = ThreadsUtils.createThreadsAdd(listFGL, 8, 1000);
            long executionTimeFGL = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
            
            //Lista optimista
            OptimisticList  listOP      = new OptimisticList(); 
            Thread[] threadsOP = ThreadsUtils.createThreadsAdd(listOP, 2, 1000);
            //Thread[] threadsOP = ThreadsUtils.createThreadsAdd(listOP, 4, 1000);
            //Thread[] threadsOP = ThreadsUtils.createThreadsAdd(listOP, 8, 1000);
            long executionTimeOP = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
            
            //Lista sin locks
            LockFreeList    listLFL     = new LockFreeList();
            Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 2, 1000);
            //Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 4, 1000);
            //Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 8, 1000);
            long executionTimeLFL = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");

            fileTime.write(Long.toString(executionTimeFGL) + " " + 
                            Long.toString(executionTimeOP) + " "  + 
                            Long.toString(executionTimeLFL) + "\n"  );
        }

        fileTime.close(); 
    }
}
