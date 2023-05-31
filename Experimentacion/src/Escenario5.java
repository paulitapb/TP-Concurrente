import listthreads.*;
import concurrentlist.*;
import java.io.File;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;


public class Escenario5 {
    /* Para cada estructura concurrente creamos threads, donde se agregan y eliminan 1000 elementos 
    variamos la cantidad de threads dedicados a cada operación */

    public static void main(String[] args) throws Exception {
        
        int reps = 1000;
        int numberOfThreadsAdd = 4;     //(Posibles combinaciones (1,1), (1,4) (4,1) cambiar variables para ver otros resultados)
        int numberOfThreadsRemove = 1; 
        int numberOfThreads = numberOfThreadsAdd + numberOfThreadsRemove;
        
        File file = new File("src/logs/sizeEscenario5ThreadsAdd"+ Integer.toString(numberOfThreadsAdd) + 
                                "ThreadsRemove"+ Integer.toString(numberOfThreadsRemove) +".txt");
        file.createNewFile();
          
        FileWriter fileTime = new FileWriter("src/logs/sizeEscenario5ThreadsAdd"+ Integer.toString(numberOfThreadsAdd) + 
                                "ThreadsRemove"+ Integer.toString(numberOfThreadsRemove) +".txt");

        for(int i = 0; i < reps; i++){

            //Lista con granularidad fina
            FineGrainList   listFGL = new FineGrainList(); 
            CountDownLatch latchFGL = new CountDownLatch(numberOfThreads);
            Thread[] threadsFGL     = ThreadsUtils.createThreadsAddAndThreadsRemove(listFGL, numberOfThreadsAdd, numberOfThreadsRemove, 1000, latchFGL);
            ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL, latchFGL);
            int sizeFGL   = listFGL.size();
            
            //Lista optimista
            OptimisticList  listOP  = new OptimisticList();
            CountDownLatch latchOP  = new CountDownLatch(numberOfThreads);  
            Thread[] threadsOP      = ThreadsUtils.createThreadsAddAndThreadsRemove(listOP, numberOfThreadsAdd, numberOfThreadsRemove, 1000, latchOP);
            ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, latchOP);
            int sizeOP   = listOP.size();

            
            //Lista sin locks
            LockFreeList    listLFL = new LockFreeList();
            CountDownLatch latchLFL = new CountDownLatch(numberOfThreads);
            Thread[] threadsLFL     = ThreadsUtils.createThreadsAddAndThreadsRemove(listOP, numberOfThreadsAdd, numberOfThreadsRemove, 1000, latchLFL);
            ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, latchLFL);
            int sizeLFL   = listLFL.size();

            
            if(!listFGL.checkListInvariant() || !listOP.checkListInvariant() || !listLFL.checkListInvariant()){
                System.out.println("Fallo el invariante para alguna lista");
            }

            fileTime.write(Long.toString(sizeFGL) + " " + 
                            Long.toString(sizeOP) + " "  + 
                            Long.toString(sizeLFL) + "\n"  );
        }
        fileTime.close(); 
    }
}
