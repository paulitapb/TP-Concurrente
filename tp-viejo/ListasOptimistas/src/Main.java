
//import java.lang.Thread;

public class Main{
    
    public static void main(String[] args){
        OptimisticList list = new OptimisticList();
        
        ThreadAddOL add = new ThreadAddOL(list);
        ThreadRemoveOL remove = new ThreadRemoveOL(list);
        list.add(0);
        list.add(15);

        add.start();
        remove.start();
        
        try {
            add.join();
            remove.join();
        } catch (InterruptedException e) {
            System.out.println("Rompiste todo");
            e.printStackTrace();
        }

        list.printList();
    }
}
