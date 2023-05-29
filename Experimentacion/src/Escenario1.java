import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.io.File; 
import java.util.concurrent.*; 

public class Escenario1 {
    /* Para cada estructura concurrente creamos n threads que agregan 
    concurrentemente 250 elementos cada uno intercaladamente */

    public static void main(String[] args) throws Exception {
        
        /* File file = new File("src/logs/timeEscenario1.txt");
        file.createNewFile();  */
          
        int reps = 10;
        /* FileWriter fileTime = new FileWriter("src/logs/timeEscenario1.txt"); */

        for(int i = 0; i < reps; i++){

        /*     //Lista con granularidad fina
            FineGrainList   listFGL     = new FineGrainList(); 
            Thread[] threadsFGL = ThreadsUtils.createThreadsAdd(listFGL, 2, 500);
            //Thread[] threadsFGL = ThreadsUtils.createThreadsAdd(listFGL, 4, 1000);
            //Thread[] threadsFGL = ThreadsUtils.createThreadsAdd(listFGL, 8, 2000);

            long executionTimeFGL = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
            
            //Lista optimista
            OptimisticList  listOP      = new OptimisticList(); 
            Thread[] threadsOP = ThreadsUtils.createThreadsAdd(listOP, 2, 500);
            //Thread[] threadsOP = ThreadsUtils.createThreadsAdd(listOP, 4, 1000);
            //Thread[] threadsOP = ThreadsUtils.createThreadsAdd(listOP, 8, 2000);
            long executionTimeOP = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
         */    
            //Lista sin locks
            LockFreeList    listLFL     = new LockFreeList();
            //Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 2, 500);
            //Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 4, 1000);
            //Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 8, 2000);

            // TOD: hacer un numbers of threads 
            CountDownLatch latchLFL = new CountDownLatch(4);
            Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 4, 10, latchLFL); //despues borrar esta linea
            long executionTimeLFL = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks", latchLFL);
 
            
            if(!listLFL.checkListInvariant()){
                System.out.println("falllo inv");
                //listLFL.printList();
            }
            
            //listLFL.printList();
            /* if(!listFGL.checkListInvariant() || !listOP.checkListInvariant() || !listLFL.checkListInvariant()){
            
                System.out.println("Se rompio alguna lista");
                break;
            }

            fileTime.write(Long.toString(executionTimeFGL) + " " + 
                            Long.toString(executionTimeOP) + " "  + 
                            Long.toString(executionTimeLFL) + "\n"  ); */
        }
        
        //fileTime.close(); 
    }
}
