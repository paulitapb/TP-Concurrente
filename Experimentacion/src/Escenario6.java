import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;
import java.io.File;

public class Escenario6 {
    /* Para cada estructura concurrente creamos 1 threads que agrega 1000 elementos 
    y otro que lo elimina. El thread que los elimina duerme un segundo en algun momento de la ejecución*/

    public static void main(String[] args) throws Exception {

        File file = new File("src/logs/timeEscenario9.txt");
        file.createNewFile(); 

        int reps = 1000;
        int sleepBeforeAddingElement = 0; //(Cambiar a 500)
        int numberOfThreadsAdd = 1;   
        int numberOfThreadsRemove = 1; 
        int numberOfThreads = numberOfThreadsAdd + numberOfThreadsRemove;

        FileWriter fileTime = new FileWriter("src/logs/timeEscenario9.txt");

        for(int i = 0; i < reps; i++){

            //Lista con granularidad fina
            FineGrainList   listFGL = new FineGrainList(); 
            CountDownLatch latchFGL = new CountDownLatch(numberOfThreads);
            Thread[] threadsFGL     = ThreadsUtils.createThreadsAddAndThreadsRemoveThatSleeps(listFGL, numberOfThreadsAdd, numberOfThreadsRemove, 1000, sleepBeforeAddingElement, latchFGL);
            long executionTimeFGL   = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL, latchFGL);

            //Lista optimista
            OptimisticList  listOP  = new OptimisticList(); 
            CountDownLatch latchOP  = new CountDownLatch(numberOfThreads);  
            Thread[] threadsOP      = ThreadsUtils.createThreadsAddAndThreadsRemoveThatSleeps(listOP, numberOfThreadsAdd, numberOfThreadsRemove, 1000, sleepBeforeAddingElement, latchOP);
            long executionTimeOP    = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, latchOP);

            //Lista sin locks
            LockFreeList    listLFL = new LockFreeList();
            CountDownLatch latchLFL = new CountDownLatch(numberOfThreads);
            Thread[] threadsLFL     = ThreadsUtils.createThreadsAddAndThreadsRemoveThatSleeps(listOP, numberOfThreadsAdd, numberOfThreadsRemove, 1000, sleepBeforeAddingElement, latchLFL);
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