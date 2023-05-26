public class ThreadRemoveOL extends Thread {

    public OptimisticList list;
    public ThreadRemoveOL(OptimisticList list) {
        this.list = list;
    }
    
    public void run(){
        list.remove(1);
        list.remove(3);
        list.remove(5);
        list.remove(0);
        list.remove(15);
    }
}
