
import concurrentlist.*;

public class Escenario2 {
    public static void main(String[] args) throws Exception {
        
        //Inicializar las listas con elementos
        FineGrainList   listFGL     = new FineGrainList(); 
        OptimisticList  listOP      = new OptimisticList(); 
        LockFreeList    listLFL     = new LockFreeList();

        ThreadAdd addFGL = new ThreadAdd(listFGL, 0, 10, 1);
        ThreadAdd addOP = new ThreadAdd(listFGL, 0, 10, 1);
        ThreadAdd addLFL = new ThreadAdd(listFGL, 0, 10, 1);

        addFGL.start();
        addOP.start();
        addLFL.start();

        addFGL.join();
        addOP.join();
        addLFL.join();
        
        
        //Lista con granularidad fina
        ThreadRemove removeFGL1 = new ThreadRemove(listFGL, 0, 10, 4);
        ThreadRemove removeFGL2 = new ThreadRemove(listFGL, 1, 10, 4); 
        ThreadRemove removeFGL3 = new ThreadRemove(listFGL, 2, 10, 4); 
        ThreadRemove removeFGL4 = new ThreadRemove(listFGL, 3, 10, 4); 

        long start = System.currentTimeMillis();

        removeFGL1.start();
        removeFGL2.start();
        removeFGL3.start();
        removeFGL4.start();

        removeFGL1.join();
        removeFGL2.join();
        removeFGL3.join();
        removeFGL4.join();

        long finish = System.currentTimeMillis();
        long timeElapsed = start - finish;
        System.out.println("Time: ");
        System.out.println(timeElapsed);

        System.out.println("Lista de granularidad fina");
        listFGL.printList();

        //Lista optimista
        ThreadRemove removeOP1 = new ThreadRemove(listOP, 0, 10, 4);
        ThreadRemove removeOP2 = new ThreadRemove(listOP, 1, 10, 4); 
        ThreadRemove removeOP3 = new ThreadRemove(listOP, 2, 10, 4); 
        ThreadRemove removeOP4 = new ThreadRemove(listOP, 3, 10, 4); 
        
        removeOP1.start();
        removeOP2.start();
        removeOP3.start();
        removeOP4.start();

        removeOP1.join();
        removeOP2.join();
        removeOP3.join();
        removeOP4.join();
        
        System.out.println("Lista optimista");
        listOP.printList();

        //Lista sin locks
        ThreadRemove removeLFL1 = new ThreadRemove(listLFL, 0, 10, 4);
        ThreadRemove removeLFL2 = new ThreadRemove(listLFL, 1, 10, 4); 
        ThreadRemove removeLFL3 = new ThreadRemove(listLFL, 2, 10, 4); 
        ThreadRemove removeLFL4 = new ThreadRemove(listLFL, 3, 10, 4); 
        
        removeLFL1.start();
        removeLFL2.start();
        removeLFL3.start();
        removeLFL4.start();

        removeLFL1.join();
        removeLFL2.join();
        removeLFL3.join();
        removeLFL4.join();
        
        System.out.println("Lista sin locks");
        listLFL.printList();
        
    }
}
