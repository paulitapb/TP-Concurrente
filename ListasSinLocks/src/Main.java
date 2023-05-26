
//import java.lang.Thread;

public class Main{
    
    public static void main(String[] args){
        LockFreeList list = new LockFreeList();
        
        ThreadAddLFL add = new ThreadAddLFL(list);
        ThreadRemoveLFL remove = new ThreadRemoveLFL(list);
        
        add.start();
        remove.start();
        
        try {
            add.join();
            remove.join();
            list.printList();
        } catch (InterruptedException e) {
            System.out.println("Rompiste todo");
            e.printStackTrace();
        }
        
    }
}