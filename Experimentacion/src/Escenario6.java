import listthreads.*; 
import concurrentlist.*;
import java.io.FileWriter;
import java.util.concurrent.CountDownLatch;
import java.io.File;

public class Escenario6 {
    /* Para cada estructura concurrente creamos 1 threads que agrega 1000 elementos 
    y otro que lo elimina. El thread que los elimina duerme un segundo en algun momento de la ejecución*/

    public static void main(String[] args) throws Exception {

        int reps = 100;
        int sleepBeforeAddingElement = 250; //(Cambiar a 500)
        int numberOfThreadsAdd = 1;   
        int numberOfThreadsRemove = 1; 
        int numberOfThreads = numberOfThreadsAdd + numberOfThreadsRemove;
        int numberOfElements = 1000; 

        File file = new File("src/logs/sizeEscenario6SleepBeforeAddingElement"+ Integer.toString(sleepBeforeAddingElement) +".txt");
        file.createNewFile();
          
        FileWriter fileTime = new FileWriter("src/logs/sizeEscenario6SleepBeforeAddingElement"+ Integer.toString(sleepBeforeAddingElement) +".txt");


        for(int i = 0; i < reps; i++){

            //Lista con granularidad fina
            FineGrainList   listFGL = new FineGrainList(); 
            CountDownLatch latchFGL = new CountDownLatch(numberOfThreads);
            Thread[] threadsFGL     = ThreadsUtils.createThreadsAddAndThreadsRemoveThatSleeps(listFGL, numberOfThreadsAdd, numberOfThreadsRemove, numberOfElements, sleepBeforeAddingElement, latchFGL);
            ThreadsUtils.measureThreadExcecutionTime(threadsFGL, listFGL, latchFGL);
            int sizeFGL = listFGL.size(); 

            //Lista optimista
            OptimisticList  listOP  = new OptimisticList(); 
            CountDownLatch latchOP  = new CountDownLatch(numberOfThreads);  
            Thread[] threadsOP      = ThreadsUtils.createThreadsAddAndThreadsRemoveThatSleeps(listOP, numberOfThreadsAdd, numberOfThreadsRemove, numberOfElements, sleepBeforeAddingElement, latchOP);
            ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, latchOP);
            int sizeOP = listOP.size(); 

            //Lista sin locks
            LockFreeList    listLFL = new LockFreeList();
            CountDownLatch latchLFL = new CountDownLatch(numberOfThreads);
            Thread[] threadsLFL     = ThreadsUtils.createThreadsAddAndThreadsRemoveThatSleeps(listOP, numberOfThreadsAdd, numberOfThreadsRemove, numberOfElements, sleepBeforeAddingElement, latchLFL);
            ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, latchLFL);
            int sizeLFL = listLFL.size(); 

            if(!listFGL.checkListInvariant()){
                System.out.println("Fallo el invariante para alguna lista GRANOS FINOS");
            }

            if(!listOP.checkListInvariant()){
                System.out.println("Fallo el invariante para alguna lista SOMOS OPTIMISTAS");
            }

            if(!listLFL.checkListInvariant()){
                System.out.println("Fallo el invariante para alguna lista MI CORAZON TINEE EL CANDADO ABIERTO");
            }

            fileTime.write(Long.toString(sizeFGL) + " " + 
            Long.toString(sizeOP) + " "  + 
            Long.toString(sizeLFL) + "\n"  );
        }
        fileTime.close(); 
    }
}