import java.lang.Thread;
/* import ListasOptimistas; 

public class MainTP{
    
    public static void main(String[] args){
        FineGrainList list = new FineGrainList();
        ThreadAdd add = new ThreadAdd(list);
        ThreadRemove remove = new ThreadRemove(list);
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

} */