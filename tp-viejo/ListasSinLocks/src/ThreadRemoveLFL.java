public class ThreadRemoveLFL extends Thread {

    public LockFreeList list;
    public ThreadRemoveLFL(LockFreeList list) {
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