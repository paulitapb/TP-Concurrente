import listthreads.*;
import concurrentlist.*;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;


public class Escenario5 {
    /* Para cada estructura concurrente creamos threads, donde se agregan y eliminan 1000 elementos 
    variamos la cantidad de threads dedicados a cada operación */

    public static void main(String[] args) throws Exception {
        
        File file = new File("src/logs/timeEscenario4.txt");
        file.createNewFile(); 
          
        int reps = 1000;
        int numberOfThreadsAdd = 1;     //(Posibles combinaciones (1,1), (1,4) (4,1) cambiar variables para ver otros resultados)
        int numberOfThreadsRemove = 4; 
        int numberOfThreads = numberOfThreadsAdd + numberOfThreadsRemove;
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario4.txt");

        for(int i = 0; i < reps; i++){

            //Lista con granularidad fina
            FineGrainList   listFGL = new FineGrainList(); 
            CountDownLatch latchFGL = new CountDownLatch(numberOfThreads);
            Thread[] threadsFGL     = ThreadsUtils.createThreadsAddAndThreadsRemove(listFGL, numberOfThreadsAdd, numberOfThreadsRemove, 1000, latchFGL);
            long executionTimeFGL   = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL, latchFGL);
            
            //Lista optimista
            OptimisticList  listOP  = new OptimisticList();
            CountDownLatch latchOP  = new CountDownLatch(numberOfThreads);  
            Thread[] threadsOP      = ThreadsUtils.createThreadsAddAndThreadsRemove(listOP, numberOfThreadsAdd, numberOfThreadsRemove, 1000, latchOP);
            long executionTimeOP    = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, latchOP);
            
            //Lista sin locks
            LockFreeList    listLFL = new LockFreeList();
            CountDownLatch latchLFL = new CountDownLatch(numberOfThreads);
            Thread[] threadsLFL     = ThreadsUtils.createThreadsAddAndThreadsRemove(listOP, numberOfThreadsAdd, numberOfThreadsRemove, 1000, latchLFL);
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
