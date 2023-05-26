import finegrainedlist.*;

public class MainTP{
    
    public static void main(String[] args){
        FineGrainList list = new FineGrainList();
        ThreadAddFGL add = new ThreadAddFGL(list);
        ThreadRemoveFGL remove = new ThreadRemoveFGL(list);
        long start = System.currentTimeMillis();

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
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.println(timeElapsed);
        list.printList();
    }

}