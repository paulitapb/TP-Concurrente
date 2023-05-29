import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.io.File;

public class Escenario2 {
    /* Para cada estructura concurrente creamos 4 threads que eliminan
    concurrentemente 250 elementos cada uno */

    public static void main(String[] args) throws Exception {
        
        File file = new File("src/logs/timeEscenario2.txt");
        file.createNewFile(); 
        
        int reps = 1000;
        
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario2.txt");

        for(int j = 0; j < reps; j++){
            FineGrainList   listFGL     = new FineGrainList(); 
            OptimisticList  listOP      = new OptimisticList(); 
            LockFreeList    listLFL     = new LockFreeList();

            for(int i = 0; i<1000; i++){
                listFGL.add(i);
                listOP.add(i);
                listLFL.add(i);
            }

            //Lista con granularidad fina
            Thread[] threadsFGL = ThreadsUtils.createThreadsRemove(listFGL, 2, 500);
            //Thread[] threadsFGL = ThreadsUtils.createThreadsRemove(listFGL, 4, 1000);
            //Thread[] threadsFGL = ThreadsUtils.createThreadsRemove(listFGL, 8, 2000);
            long executionTimeFGL = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
        
            //Lista optimista
            Thread[] threadsOP = ThreadsUtils.createThreadsRemove(listOP, 2, 500);
            //Thread[] threadsOP = ThreadsUtils.createThreadsRemove(listOP, 4, 1000);
            //Thread[] threadsOP = ThreadsUtils.createThreadsRemove(listOP, 8, 2000);
            long executionTimeOP = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");

            //Lista sin locks
            Thread[] threadsLFL = ThreadsUtils.createThreadsRemove(listLFL, 2, 500);
            //Thread[] threadsLFL = ThreadsUtils.createThreadsRemove(listLFL, 4, 1000);
            //Thread[] threadsLFL = ThreadsUtils.createThreadsRemove(listLFL, 8, 2000);
            long executionTimeLFL = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");

            fileTime.write(Long.toString(executionTimeFGL) + " " + 
                            Long.toString(executionTimeOP) + " "  + 
                            Long.toString(executionTimeLFL) + "\n"  );
        }
        fileTime.close(); 
    }
}
