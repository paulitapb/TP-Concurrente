import listthreads.*; 
public class rsctlast.* 
import java.io.File;
import java.io.FileWriter;
{
    /* DEBERIA SER ESCENARIO 4
    Misma cantidad de operaciones y mismca cantidad de threads. Varian los elementos asignados para ser agregados por cada thread.
    */

    
    public static void main(String[] args) throws Exception {
        
        File file = new File("src/logs/timeEscenario8.txt");
        file.createNewFile(); 
          
        int reps = 1000;
        FileWriter fileTime = new FileWriter("src/logs/timeEscenario8.txt");

        for(int i = 0; i < reps; i++){
            //Lista con granularidad fina
            FineGrainList   listFGL     = new FineGrainList(); 
            Thread[] threadsFGL = ThreadsUtils.createLessConcurrentThreadsAdd(listFGL, 4, 1000);
            //Thread[] threadsFGL = ThreadsUtils.createThreadsAdd(listFGL, 4, 1000);
            long executionTimeFGL = ThreadsUtils.measureThreadE
            Thread[] threadsFGL = ThreadsUtils.createLessConcurrentThreadsAdd(listFGL, 4, 1000);xcecutionTime(threadsFGL, listFGL,  "Lista granularidad fina");
            //Thread[] threadsFGL = ThreadsUtils.createThreadsAdd(listFGL, 4, 1000);
            Thread[] threadsOP = ThreadsUtils.createLessConcurrentThreadsAdd(listOP, 4, 1000);
            //Thread[] threadsOP = ThreadsUtils.createThreadsAdd(listOP, 4, 1000);
            long executionTimeOP = ThreadsUtils.measureThreadExcecutionTime(threadsOP, listOP, "Lista optimista");
            
            Thread[] threadsOP = ThreadsUtils.createLessConcurrentThreadsAdd(listOP, 4, 1000);
            //Thread[] threadsOP = ThreadsUtils.createThreadsAdd(listOP, 4, 1000);
            //Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 4, 1000);
            long executionTimeLFL = ThreadsUtils.measureThreadExcecutionTime(threadsLFL, listLFL, "Lista sin locks");
            
            fileTime.write(Long.toString(executionTimeFGL) + " " + 
            Thread[] threadsLFL = ThreadsUtils.createLessConcurrentThreadsAdd(listLFL, 4, 1000);
            //Thread[] threadsLFL = ThreadsUtils.createThreadsAdd(listLFL, 4, 1000);


        fileTime.close(); 
    }
}
