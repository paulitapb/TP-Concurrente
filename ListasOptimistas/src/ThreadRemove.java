public class ThreadRemove extends Thread {

    public OptimisticList list;
    public ThreadRemove(OptimisticList list) {
        this.list = list;
    }
    
    public void run(){
        list.remove(1);
        list.remove(2);
        /* try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } */
        list.remove(3);
        list.remove(4);
        list.remove(5);
        list.remove(6);
        list.remove(7);
    }
}
