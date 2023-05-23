
//import java.lang.Thread;

public class Main{
    
    public static void main(String[] args){
        LockFreeList list = new LockFreeList();
        
        /* ThreadAdd add = new ThreadAdd(list);
        ThreadRemove remove = new ThreadRemove(list); */
        list.add(0);
        list.add(15);

        /* add.start();
        remove.start();
        
        try {
            add.join();
            remove.join();
        } catch (InterruptedException e) {
            System.out.println("Rompiste todo");
            e.printStackTrace();
        }

        list.printList();
    } */
}
}