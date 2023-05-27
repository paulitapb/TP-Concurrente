import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.io.File;

public class Escenario9 {
    /* Para cada estructura concurrente creamos 1 threads que agrega 1000 elementos 
    y otro que lo elimina. El thread que los elimina duerme un segundo antes de empezar a eliminar*/

    
    public static void main(String[] args) throws Exception {
        
        File file = new File("src/logs/timeEscenario9.txt");
        file.createNewFile(); 
          
        int reps = 1000;
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario9.txt");

        for(int i = 0; i < reps; i++){

            //Lista con granularidad fina
            FineGrainList   listFGL     = new FineGrainList(); 
            Thread[] threadsFGL = ThreadsUtils.createThreadsAddAndThreadsRemoveThatSleeps(listFGL, 1, 1, 1000, 0);
            long executionTimeFGL =ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
            
            //Lista optimista
            OptimisticList  listOP      = new OptimisticList(); 
            Thread[] threadsOP = ThreadsUtils.createThreadsAddAndThreadsRemoveThatSleeps(listOP, 1, 1, 1000, 0);
            long executionTimeOP =ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
            
            //Lista sin locks
            LockFreeList    listLFL     = new LockFreeList();
            Thread[] threadsLFL = ThreadsUtils.createThreadsAddAndThreadsRemoveThatSleeps(listOP, 1, 1, 1000, 0);
            long executionTimeLFL =ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");
            
            fileTime.write(Long.toString(executionTimeFGL) + " " + 
            Long.toString(executionTimeOP) + " "  + 
            Long.toString(executionTimeLFL) + "\n"  );
        }

        fileTime.close(); 
    }
}
