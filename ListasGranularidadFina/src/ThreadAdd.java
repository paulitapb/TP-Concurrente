public class ThreadAdd extends Thread {

    public FineGrainList list;
    public ThreadAdd(FineGrainList list) {
        this.list = list;
    }
    
    public void run(){
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(7);

    }
}
