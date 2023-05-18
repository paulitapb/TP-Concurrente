public class MainTP{
    
    public static void main(String[] args){
        FineGrainList list = new FineGrainList(); 
        
        new ThreadAdd(list).start();
        new ThreadRemove(list).start();

        list.printList();
    }

}