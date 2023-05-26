import java.lang.Thread;

public class MainTP{
    
    public static void main(String[] args){
        FineGrainList list = new FineGrainList();
        ThreadAddFGL add = new ThreadAddFGL(list);
        ThreadRemoveFGL remove = new ThreadRemoveFGL(list);
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