import listthreads.*; 
import concurrentlist.*;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;

public class Escenario4{
    /* Misma cantidad de operaciones y misma cantidad de threads. Varian los elementos asignados para ser agregados por cada thread.
       Aclaracion: La linea comentada es repetir el experimento 1 con 4 threads, lo dejamos aca ya que va a ser contra el cual se haga la comparación.
       */
    public static void main(String[] args) throws Exception {
                  
        int reps = 1000;
        int numberOfThreads = 4;

        File file = new File("src/logs/timeEscenario4AddBlock.txt");
        file.createNewFile();
          
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario4AddBlock.txt");

        for(int i = 0; i < reps; i++){
            //Lista con granularidad fina
            FineGrainList   listFGL = new FineGrainList(); 
            CountDownLatch latchFGL = new CountDownLatch(numberOfThreads);
            Thread[] threadsFGL     = ThreadsUtils.createLessConcurrentThreadsAdd(listFGL, numberOfThreads, 1000, latchFGL);
            //Thread[] threadsFGL   = ThreadsUtils.createThreadsAdd(listFGL, numberOfThreads, 1000, latchFGL);
            long executionTimeFGL   = ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL, latchFGL);
            

            //Lista optimista
            OptimisticList  listOP  = new OptimisticList();
            CountDownLatch latchOP  = new CountDownLatch(numberOfThreads); 
            Thread[] threadsOP      = ThreadsUtils.createLessConcurrentThreadsAdd(listOP, numberOfThreads, 1000, latchOP);
            //Thread[] threadsOP    = ThreadsUtils.createThreadsAdd(listOP, numberOfThreads, 1000, latchOP);
            long executionTimeOP    = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, latchOP);
            
            //Lista sin locks
            LockFreeList    listLFL = new LockFreeList();
            CountDownLatch latchLFL = new CountDownLatch(numberOfThreads);
            Thread[] threadsLFL     = ThreadsUtils.createLessConcurrentThreadsAdd(listLFL, numberOfThreads, 1000, latchLFL);
            //Thread[] threadsLFL   = ThreadsUtils.createThreadsAdd(listLFL, numberOfThreads, 1000, latchLFL);
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
