package Escenario1;

import finegrainedlist.*;
import optimisticlist.*;
import lockfreelist.*;

public class Escenario1{
    
    public static void main(String[] args){
        FineGrainList   list1 = new FineGrainList();
        OptimisticList  list2 = new OptimisticList();
        LockFreeList    list3 = new LockFreeList();

        ThreadAdd add1 = new ThreadAdd(list1,0,10,1);
        ThreadAdd add2 = new ThreadAdd(list1,10,20,1);
        ThreadAdd add3 = new ThreadAdd(list1,20,30,1);

        long start = System.currentTimeMillis();

        add1.start();
        add2.start();
        add3.start();
        
        try {
            add1.join();
            add2.join();
            add3.join();
        } catch (InterruptedException e) {
            System.out.println("Rompiste todo");
            e.printStackTrace();
        }
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        list1.printList();
    }
}